package com.xiaopeng.car.settingslibrary.interfaceui;

import android.content.Intent;
import android.net.Uri;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsInfo;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.privacymanager.PrivacyServiceManager;
/* loaded from: classes.dex */
public class AboutSysServerManager extends ServerBaseManager {
    private static final String TAG = "AboutSysServerManager";
    private PrivacyServiceManager mPrivacyServiceManager;
    private StorageSettingsViewModel mStorageSettingsViewModel;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final AboutSysServerManager instance = new AboutSysServerManager();

        private InnerFactory() {
        }
    }

    public static AboutSysServerManager getInstance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getCleanFinishLiveData().setValue(null);
        getViewModel().getCleanFinishLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$AboutSysServerManager$3BnKiee5iwN-Kp9K2hHUGFwpWV8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AboutSysServerManager.this.lambda$observeData$0$AboutSysServerManager((Boolean) obj);
            }
        });
        getViewModel().getStorageSettingsInfoLiveData().setValue(null);
        getViewModel().getStorageSettingsInfoLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$AboutSysServerManager$7ZMD2ZsrV3fQhQmKjvK2iRc8lF0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AboutSysServerManager.this.lambda$observeData$1$AboutSysServerManager((StorageSettingsInfo) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$AboutSysServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("AboutSysServerManager onCleanFinish");
        aboutCallback(InterfaceConstant.ON_CLEAN_FINISH, "");
    }

    public /* synthetic */ void lambda$observeData$1$AboutSysServerManager(StorageSettingsInfo storageSettingsInfo) {
        if (storageSettingsInfo == null) {
            return;
        }
        debugLog("AboutSysServerManager onStorageProgressChanged " + storageSettingsInfo.getUsedSize() + " " + storageSettingsInfo.getTotalSize());
        StringBuilder sb = new StringBuilder();
        sb.append(storageSettingsInfo.getUsedSize());
        sb.append(",");
        sb.append(storageSettingsInfo.getTotalSize());
        aboutCallback(InterfaceConstant.ON_STORAGE_CHANGED, sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getPrivacyServiceManager();
        getViewModel();
    }

    private synchronized StorageSettingsViewModel getViewModel() {
        if (this.mStorageSettingsViewModel == null) {
            this.mStorageSettingsViewModel = new StorageSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mStorageSettingsViewModel;
    }

    private synchronized PrivacyServiceManager getPrivacyServiceManager() {
        if (this.mPrivacyServiceManager == null) {
            this.mPrivacyServiceManager = new PrivacyServiceManager(CarSettingsApp.getContext());
        }
        return this.mPrivacyServiceManager;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("AboutSysServerManager startVm");
        getPrivacyServiceManager().connect(null);
        getViewModel().startVm();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("AboutSysServerManager stopVm");
        getPrivacyServiceManager().disconnect();
        getViewModel().stopVm();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    public String requestUserProtocol() {
        log("AboutSysServerManager requestUserProtocol");
        if (getPrivacyServiceManager().isConnected()) {
            return getPrivacyServiceManager().getPrivacyPath(102);
        }
        log("AboutSysServerManager requestUserProtocol no connected");
        return "";
    }

    public String requestPrivacyProtocol() {
        log("AboutSysServerManager requestPrivacyProtocol");
        if (getPrivacyServiceManager().isConnected()) {
            return getPrivacyServiceManager().getPrivacyPath(101);
        }
        log("AboutSysServerManager requestPrivacyProtocol no connected");
        return "";
    }

    public boolean isEbsBatterySatisfied() {
        log("AboutSysServerManager isEbsBatterySatisfied");
        return CarSettingsManager.getInstance().isEbsBatterySatisfied();
    }

    public boolean isCarGearP() {
        log("AboutSysServerManager isCarGearP");
        return CarSettingsManager.getInstance().isCarGearP();
    }

    public void restoreFactory() {
        log("AboutSysServerManager restoreFactory");
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B001");
        XpAboutManager.getInstance().restoreFactory();
    }

    public void checkAndRestoreFactory() {
        log("AboutSysServerManager checkAndRestoreFactory");
        Intent intent = new Intent(CarSettingsApp.getContext(), UIGlobalService.class);
        intent.setAction(RestoreCheckedDialogWork.ACTION);
        CarSettingsApp.getContext().startService(intent);
    }

    public void diaPhoneNum(String str) {
        log("AboutSysServerManager diaPhoneNum phone:" + str);
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setFlags(268435456);
        intent.setData(Uri.parse("tel:" + str));
        CarSettingsApp.getContext().startActivity(intent);
        if ("400-819-3388".equals(str)) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B003");
        } else if ("020-37520800".equals(str)) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B002");
        }
    }

    public boolean isGearP() {
        boolean isGearP = getViewModel().isGearP();
        log("AboutSysServerManager isGearP:" + isGearP);
        return isGearP;
    }

    public void cleanFiles(String str, String str2) {
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B004");
        getViewModel().cleanFiles(str, str2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean getNotifyEnable(String str) {
        char c;
        switch (str.hashCode()) {
            case -1965036692:
                if (str.equals(XPSettingsConfig.APPSTORE_NOTIFICATION_ENABLED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1586402815:
                if (str.equals(XPSettingsConfig.OPERATING_ACTIVITIES_NOTIFICATION_ENABLED)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -730660646:
                if (str.equals(XPSettingsConfig.VEHICLE_DETECTION_NOTIFICATION_ENABLED)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -685891717:
                if (str.equals(XPSettingsConfig.ASSIST_DRIVING_NOTIFICATION_ENABLED)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -427066525:
                if (str.equals(XPSettingsConfig.CARCONTROL_NOTIFICATION_ENABLED)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 712462015:
                if (str.equals(XPSettingsConfig.MUSIC_NOTIFICATION_ENABLED)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 880334128:
                if (str.equals(XPSettingsConfig.OTA_NOTIFICATION_ENABLED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1544479890:
                if (str.equals(XPSettingsConfig.MEMORY_PARKING_NOTIFICATION_ENABLED)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1743557751:
                if (str.equals(XPSettingsConfig.TTS_BROADCAST_NOTIFICATION_ENABLED)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return XpAccountManager.getInstance().getMusicNotify();
            case 1:
                return XpAccountManager.getInstance().getOtaNotify();
            case 2:
                return XpAccountManager.getInstance().getAppStoreNotify();
            case 3:
                return XpAccountManager.getInstance().getCarControlNotify();
            case 4:
                return XpAccountManager.getInstance().getXpilotNotify();
            case 5:
                return XpAccountManager.getInstance().getParkingNotify();
            case 6:
                return XpAccountManager.getInstance().getVehicleCheckNotify();
            case 7:
                return XpAccountManager.getInstance().getOperatingNotify();
            case '\b':
                return XpAccountManager.getInstance().getTtsBroadcastNotify();
            default:
                return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void setNotifyEnable(String str, boolean z) {
        char c;
        switch (str.hashCode()) {
            case -1965036692:
                if (str.equals(XPSettingsConfig.APPSTORE_NOTIFICATION_ENABLED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1586402815:
                if (str.equals(XPSettingsConfig.OPERATING_ACTIVITIES_NOTIFICATION_ENABLED)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -730660646:
                if (str.equals(XPSettingsConfig.VEHICLE_DETECTION_NOTIFICATION_ENABLED)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -685891717:
                if (str.equals(XPSettingsConfig.ASSIST_DRIVING_NOTIFICATION_ENABLED)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -427066525:
                if (str.equals(XPSettingsConfig.CARCONTROL_NOTIFICATION_ENABLED)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 712462015:
                if (str.equals(XPSettingsConfig.MUSIC_NOTIFICATION_ENABLED)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 880334128:
                if (str.equals(XPSettingsConfig.OTA_NOTIFICATION_ENABLED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1544479890:
                if (str.equals(XPSettingsConfig.MEMORY_PARKING_NOTIFICATION_ENABLED)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1743557751:
                if (str.equals(XPSettingsConfig.TTS_BROADCAST_NOTIFICATION_ENABLED)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                XpAccountManager.getInstance().setMusicNotify(z);
                return;
            case 1:
                XpAccountManager.getInstance().setOtaNotify(z);
                return;
            case 2:
                XpAccountManager.getInstance().setAppStoreNotify(z);
                return;
            case 3:
                XpAccountManager.getInstance().setCarControlNotify(z);
                return;
            case 4:
                XpAccountManager.getInstance().setXpilotNotify(z);
                return;
            case 5:
                XpAccountManager.getInstance().setParkingNotify(z);
                return;
            case 6:
                XpAccountManager.getInstance().setVehicleCheckNotify(z);
                return;
            case 7:
                XpAccountManager.getInstance().setOperatingNotify(z);
                return;
            case '\b':
                XpAccountManager.getInstance().setTtsBroadcastNotify(z);
                return;
            default:
                return;
        }
    }

    public void onAccountChanged() {
        debugLog("AboutSysServerManager onAccountChanged");
        aboutCallback(InterfaceConstant.ON_ACCOUNT_CHANGED, "");
    }
}
