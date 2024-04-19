package com.xiaopeng.car.settingslibrary.manager.hotspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiClient;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class XpHotSpotManager {
    private static final String TAG = "XpHotSpotManager";
    ConnectivityManager mConnectivityManager;
    private Context mContext;
    Listener mListener;
    private boolean mRestartWifiApAfterConfigChange;
    WifiManager mWifiManager;
    ArrayList<WifiClient> mWifiClients = new ArrayList<>();
    private boolean mIsAlreadyRegister = false;
    final ConnectivityManager.OnStartTetheringCallback mOnStartTetheringCallback = new ConnectivityManager.OnStartTetheringCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.hotspot.XpHotSpotManager.1
        public void onTetheringFailed() {
            super.onTetheringFailed();
            Logs.d("xptether callback onTetheringStarted  fail " + CarSettingsManager.getInstance().getIgStatusFromMcu());
            if (XpHotSpotManager.this.mListener != null) {
                XpHotSpotManager.this.mListener.startTetheringResult(false);
            }
        }

        public void onTetheringStarted() {
            super.onTetheringStarted();
            Logs.d("xptether callback onTetheringStarted  success " + CarSettingsManager.getInstance().getIgStatusFromMcu());
            if (XpHotSpotManager.this.mListener != null) {
                XpHotSpotManager.this.mListener.startTetheringResult(true);
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.manager.hotspot.XpHotSpotManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("wifi_state", 0);
                Logs.d("xptether action:" + action + " state:" + intExtra);
                if (XpHotSpotManager.this.mListener != null) {
                    XpHotSpotManager.this.mListener.handleWifiApStateChanged(intExtra);
                }
                if (intExtra == 11 && XpHotSpotManager.this.mRestartWifiApAfterConfigChange) {
                    XpHotSpotManager.this.startTethering();
                }
            }
        }
    };
    private final IntentFilter mIntentFilter = new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED");

    /* loaded from: classes.dex */
    public interface Listener {
        void handleWifiApStateChanged(int i);

        void startTetheringResult(boolean z);
    }

    public XpHotSpotManager(Context context, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    }

    public void registerApReceiver() {
        if (this.mIsAlreadyRegister) {
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter);
        this.mIsAlreadyRegister = true;
    }

    public void unregisterApReceiver() {
        if (this.mIsAlreadyRegister) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mIsAlreadyRegister = false;
        }
    }

    public WifiConfiguration getWifiApConfiguration() {
        return this.mWifiManager.getWifiApConfiguration();
    }

    public boolean isTetherable() {
        return this.mWifiManager.getWifiApState() == 13;
    }

    public boolean isTetherProcessing() {
        int wifiApState = this.mWifiManager.getWifiApState();
        Logs.d("xptether isTetherProcessing status:" + wifiApState);
        return wifiApState == 12 || wifiApState == 10;
    }

    public void startTethering() {
        Logs.d("xptether startTethering");
        this.mRestartWifiApAfterConfigChange = false;
        this.mConnectivityManager.startTethering(0, true, this.mOnStartTetheringCallback);
    }

    public void stopTethering() {
        this.mConnectivityManager.stopTethering(0);
    }

    public void setName(String str) {
        WifiConfiguration wifiApConfiguration = getWifiApConfiguration();
        if (TextUtils.isEmpty(str)) {
            wifiApConfiguration.SSID = wifiApConfiguration.SSID;
        } else {
            wifiApConfiguration.SSID = str;
        }
        this.mWifiManager.setWifiApConfiguration(wifiApConfiguration);
        updateTether();
    }

    public void setPassword(String str) {
        WifiConfiguration wifiApConfiguration = getWifiApConfiguration();
        if (TextUtils.isEmpty(str)) {
            wifiApConfiguration.preSharedKey = wifiApConfiguration.preSharedKey;
        } else {
            wifiApConfiguration.preSharedKey = str;
        }
        this.mWifiManager.setWifiApConfiguration(wifiApConfiguration);
        updateTether();
    }

    public void setNameAndPwd(String str, String str2) {
        WifiConfiguration wifiApConfiguration = getWifiApConfiguration();
        if (!TextUtils.isEmpty(wifiApConfiguration.SSID) && wifiApConfiguration.SSID.equals(str) && !TextUtils.isEmpty(wifiApConfiguration.preSharedKey) && wifiApConfiguration.preSharedKey.equals(str2)) {
            Logs.d("xptether setNameAndPwd equals return!");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            wifiApConfiguration.SSID = wifiApConfiguration.SSID;
        } else {
            wifiApConfiguration.SSID = str;
        }
        if (TextUtils.isEmpty(str2)) {
            wifiApConfiguration.preSharedKey = wifiApConfiguration.preSharedKey;
        } else {
            wifiApConfiguration.preSharedKey = str2;
        }
        this.mWifiManager.setWifiApConfiguration(wifiApConfiguration);
        updateTether();
    }

    private void updateTether() {
        if (this.mWifiManager.getWifiApState() == 13) {
            Logs.d("xptether Wifi AP config changed while enabled, stop and restart");
            this.mRestartWifiApAfterConfigChange = true;
            this.mConnectivityManager.stopTethering(0);
        }
    }
}
