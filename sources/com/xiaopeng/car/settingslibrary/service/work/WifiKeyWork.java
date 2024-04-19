package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.common.utils.WifiAuthUtils;
import com.xiaopeng.car.settingslibrary.common.utils.WifiUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IPowerChangeListener;
import com.xiaopeng.car.settingslibrary.manager.emergency.EmergencyKeyManager;
import com.xiaopeng.car.settingslibrary.manager.hotspot.XpHotSpotManager;
import com.xiaopeng.lib.utils.MD5Utils;
import java.util.concurrent.CompletableFuture;
/* loaded from: classes.dex */
public class WifiKeyWork implements WorkService, IMcuChangeListener, XpHotSpotManager.Listener, IPowerChangeListener {
    public static final String ACTION = "com.xiaopeng.intent.action.WIFI_KEY";
    public static final String TAG = "WifiKeyWork";
    private EmergencyKeyManager mEmergencyKeyManager;
    private boolean mIsUseWifiKey = false;
    private XpHotSpotManager mXpHotSpotManager;

    @Override // com.xiaopeng.car.settingslibrary.manager.hotspot.XpHotSpotManager.Listener
    public void startTetheringResult(boolean z) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mXpHotSpotManager = new XpHotSpotManager(CarSettingsApp.getContext(), this);
        this.mEmergencyKeyManager = new EmergencyKeyManager();
        CarSettingsManager.getInstance().addMcuChangeListener(this);
        CarSettingsManager.getInstance().addPowerChangeListener(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && ACTION.equals(intent.getAction())) {
            Logs.d("WifiKeyWorkstart hotspot action way");
            startHotSpot();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        this.mXpHotSpotManager.unregisterApReceiver();
        CarSettingsManager.getInstance().removeMcuChangeListener(this);
        CarSettingsManager.getInstance().removePowerChangeListener(this);
    }

    private void startHotSpot() {
        if (this.mXpHotSpotManager.isTetherProcessing()) {
            return;
        }
        WifiUtils.exitAirplaneMode(CarSettingsApp.getContext());
        this.mXpHotSpotManager.registerApReceiver();
        boolean isTetherable = this.mXpHotSpotManager.isTetherable();
        String vin = CarFunction.isWifiKeybyVin() ? Config.getVin() : MD5Utils.getMD5(Config.getHardwareId());
        if (!TextUtils.isEmpty(vin)) {
            if (!CarFunction.isWifiKeybyVin()) {
                vin = vin.toLowerCase();
            }
            String vin2WifiEssid = WifiAuthUtils.vin2WifiEssid(vin);
            String vin2WifiPassword = WifiAuthUtils.vin2WifiPassword(vin);
            if (!Utils.isUserRelease()) {
                Logs.d("WifiKeyWork factor:" + vin);
            }
            Logs.d("WifiKeyWork current tetherable:" + isTetherable + " name:" + vin2WifiEssid + " pwd:" + vin2WifiPassword);
            if (isTetherable) {
                this.mXpHotSpotManager.setNameAndPwd(vin2WifiEssid, vin2WifiPassword);
                if (CarSettingsManager.getInstance().getIgStatusFromMcu() != 1) {
                    CarSettingsManager.getInstance().sendOpenWifiHotspotResponse(true);
                }
            } else {
                this.mXpHotSpotManager.setNameAndPwd(vin2WifiEssid, vin2WifiPassword);
                this.mXpHotSpotManager.startTethering();
            }
            this.mIsUseWifiKey = true;
            return;
        }
        Logs.d("WifiKeyWork md5HardwareId empty!!!");
    }

    private void recoverHotSpot() {
        Logs.log(TAG, "power prepare shut down, force stopping the tethering and exiting emergency! " + this.mIsUseWifiKey);
        if (this.mIsUseWifiKey) {
            this.mIsUseWifiKey = false;
            this.mEmergencyKeyManager.exitEmergencyProcess();
            if (this.mXpHotSpotManager.isTetherable()) {
                Logs.d("WifiKeyWorksettings stop tethering, because sleep");
                this.mXpHotSpotManager.stopTethering();
            }
            if (Config.IS_SDK_HIGHER_P) {
                return;
            }
            WifiUtils.enterAirplaneMode(CarSettingsApp.getContext());
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onOpenWifiHotSpot() {
        Logs.d("WifiKeyWork onOpenWifiHotSpot");
        startHotSpot();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.hotspot.XpHotSpotManager.Listener
    public void handleWifiApStateChanged(int i) {
        Logs.d("WifiKeyWork xptether handleWifiApStateChanged state:" + i);
        if (i != 11) {
            if (i == 13) {
                if (CarSettingsManager.getInstance().getIgStatusFromMcu() != 1) {
                    CarSettingsManager.getInstance().sendOpenWifiHotspotResponse(true);
                    this.mEmergencyKeyManager.enterEmergencyProcess();
                    return;
                }
                return;
            } else if (i != 14) {
                return;
            }
        }
        if (CarSettingsManager.getInstance().getIgStatusFromMcu() != 1) {
            CarSettingsManager.getInstance().sendOpenWifiHotspotResponse(false);
            this.mEmergencyKeyManager.exitEmergencyProcess();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onIgOn() {
        Logs.log(TAG, "ig-on,exit the emergency process and stop tethering..." + this.mIsUseWifiKey);
        if (this.mIsUseWifiKey) {
            this.mIsUseWifiKey = false;
            this.mEmergencyKeyManager.exitEmergencyProcess();
            if (this.mXpHotSpotManager.isTetherable()) {
                this.mXpHotSpotManager.stopTethering();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IPowerChangeListener
    public void onPowerStateChange(int i, CompletableFuture<Void> completableFuture) {
        Logs.log(TAG, "Power manager,enter state: " + i);
        if (!Config.IS_SDK_HIGHER_P) {
            if (i != 2) {
                return;
            }
            recoverHotSpot();
        } else if (i != 7) {
        } else {
            recoverHotSpot();
            if (completableFuture != null) {
                completableFuture.complete(null);
            }
        }
    }
}
