package com.xiaopeng.car.settingslibrary.interfaceui;

import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
/* loaded from: classes.dex */
public class ConfigUIManager extends ServerBaseManager {
    public static final String TAG = "ConfigUIManager";

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final ConfigUIManager instance = new ConfigUIManager();

        private InnerFactory() {
        }
    }

    public static ConfigUIManager get_instance() {
        return InnerFactory.instance;
    }

    public boolean isSupportAMP() {
        boolean hasAMP = CarConfigHelper.hasAMP();
        debugLog("ConfigUIManager isSupportAMP " + hasAMP);
        return hasAMP;
    }

    public boolean hasCiu() {
        boolean hasCiu = CarConfigHelper.hasCiu();
        debugLog("ConfigUIManager hasCiu " + hasCiu);
        return hasCiu;
    }

    public boolean hasXpu() {
        boolean hasXpu = CarConfigHelper.hasXpu();
        debugLog("ConfigUIManager hasXpu " + hasXpu);
        return hasXpu;
    }

    public boolean hasAutoBrightness() {
        boolean hasAutoBrightness = CarConfigHelper.hasAutoBrightness();
        debugLog("ConfigUIManager hasAutoBrightness " + hasAutoBrightness);
        return hasAutoBrightness;
    }

    public boolean isSupportXTitle() {
        boolean isSupportXTitle = CarFunction.isSupportXTitle();
        debugLog("ConfigUIManager isSupportXTitle " + isSupportXTitle);
        return isSupportXTitle;
    }

    public boolean isSupportRemotePark() {
        boolean isSupportRemotePark = CarFunction.isSupportRemotePark();
        debugLog("ConfigUIManager isSupportRemotePark " + isSupportRemotePark);
        return isSupportRemotePark;
    }

    public boolean isSupportSoldierModeCamera() {
        boolean isSupportSoldierModeCamera = CarFunction.isSupportSoldierModeCamera();
        debugLog("ConfigUIManager isSupportSoldierModeCamera " + isSupportSoldierModeCamera);
        return isSupportSoldierModeCamera;
    }

    public boolean isSupportSoldierModeLevel() {
        boolean isSupportSoldierModeLevel = CarFunction.isSupportSoldierModeLevel();
        debugLog("ConfigUIManager isSupportSoldierModeLevel " + isSupportSoldierModeLevel);
        return isSupportSoldierModeLevel;
    }

    public boolean isSupportLowSpeedVolumeSwitch() {
        boolean isSupportLowSpeedVolumeSwitch = CarFunction.isSupportLowSpeedVolumeSwitch();
        debugLog("ConfigUIManager isSupportLowSpeedVolumeSwitch " + isSupportLowSpeedVolumeSwitch);
        return isSupportLowSpeedVolumeSwitch;
    }

    public boolean isSupportLowSpeedVolumeSlider() {
        boolean isSupportLowSpeedVolumeSlider = CarFunction.isSupportLowSpeedVolumeSlider();
        debugLog("ConfigUIManager isSupportLowSpeedVolumeSlider " + isSupportLowSpeedVolumeSlider);
        return isSupportLowSpeedVolumeSlider;
    }

    public boolean isSupportNearUnlock() {
        boolean isSupportNearUnlock = CarFunction.isSupportNearUnlock();
        debugLog("ConfigUIManager isSupportNearUnlock " + isSupportNearUnlock);
        return isSupportNearUnlock;
    }

    public boolean isSupportLeaveLock() {
        boolean isSupportLeaveLock = CarFunction.isSupportLeaveLock();
        debugLog("ConfigUIManager isSupportLeaveLock " + isSupportLeaveLock);
        return isSupportLeaveLock;
    }

    public boolean isSupportEffectFixPoint() {
        boolean isSupportEffectFixPoint = CarFunction.isSupportEffectFixPoint();
        debugLog("ConfigUIManager isSupportEffectFixPoint " + isSupportEffectFixPoint);
        return isSupportEffectFixPoint;
    }

    public boolean isSupportKeyPark() {
        boolean isSupportKeyPark = CarFunction.isSupportKeyPark();
        debugLog("ConfigUIManager isSupportKeyPark " + isSupportKeyPark);
        return isSupportKeyPark;
    }

    public boolean isSupportRemoteParkAdvanced() {
        boolean isSupportRemoteParkAdvanced = CarFunction.isSupportRemoteParkAdvanced();
        debugLog("ConfigUIManager isSupportRemoteParkAdvanced " + isSupportRemoteParkAdvanced);
        return isSupportRemoteParkAdvanced;
    }

    public boolean isSupportCarCallAdvanced() {
        boolean isSupportCarCallAdvanced = CarFunction.isSupportCarCallAdvanced();
        debugLog("ConfigUIManager isSupportCarCallAdvanced " + isSupportCarCallAdvanced);
        return isSupportCarCallAdvanced;
    }

    public boolean isSupportInductionLock() {
        boolean isSupportInductionLock = CarFunction.isSupportInductionLock();
        debugLog("ConfigUIManager isSupportInductionLock " + isSupportInductionLock);
        return isSupportInductionLock;
    }

    public boolean isSupportAutoDriveTts() {
        boolean isSupportAutoDriveTts = CarFunction.isSupportAutoDriveTts();
        debugLog("ConfigUIManager isSupportInductionLock " + isSupportAutoDriveTts);
        return isSupportAutoDriveTts;
    }

    public boolean isDSeries() {
        boolean isDSeries = CarFunction.isDSeries();
        debugLog("ConfigUIManager isDSeries " + isDSeries);
        return isDSeries;
    }

    public boolean isSupportWaitMode() {
        boolean isSupportWaitMode = CarFunction.isSupportWaitMode();
        debugLog("ConfigUIManager isSupportWaitMode " + isSupportWaitMode);
        return isSupportWaitMode;
    }

    public boolean isSoundEffectLowSpeaker() {
        boolean z = !CarConfigHelper.hasAMP();
        debugLog("ConfigUIManager isSoundEffectLowSpeaker " + z);
        return z;
    }

    public boolean isSoundEffectHighSpeaker() {
        boolean hasAMP = CarConfigHelper.hasAMP();
        debugLog("ConfigUIManager isSoundEffectHighSpeaker " + hasAMP);
        return hasAMP;
    }

    public boolean isSupportDoubleScreen() {
        boolean isSupportDoubleScreen = CarFunction.isSupportDoubleScreen();
        debugLog("ConfigUIManager isSupportDoubleScreen " + isSupportDoubleScreen);
        return isSupportDoubleScreen;
    }

    public boolean isSupportOneKeyCleanCaches() {
        boolean isSupportOneKeyCleanCaches = CarFunction.isSupportOneKeyCleanCaches();
        debugLog("ConfigUIManager isSupportOneKeyCleanCaches " + isSupportOneKeyCleanCaches);
        return isSupportOneKeyCleanCaches;
    }

    public boolean isSupportSharedDisplay() {
        boolean isSupportSharedDisplay = CarFunction.isSupportSharedDisplay();
        log("ConfigUIManager isSupportSharedDisplay " + isSupportSharedDisplay);
        return isSupportSharedDisplay;
    }

    public boolean isSupportAvasSpeaker() {
        boolean isSupportAvasSpeaker = CarFunction.isSupportAvasSpeaker();
        log("ConfigUIManager isSupportAvasSpeaker " + isSupportAvasSpeaker);
        return isSupportAvasSpeaker;
    }

    public boolean isSupportAppScreenFlow() {
        boolean isSupportAppScreenFlow = CarFunction.isSupportAppScreenFlow();
        log("ConfigUIManager isSupportAppScreenFlow " + isSupportAppScreenFlow);
        return isSupportAppScreenFlow;
    }

    public boolean isSupportRearRowReminder() {
        boolean isSupportRearRowReminder = CarFunction.isSupportRearRowReminder();
        log("ConfigUIManager isSupportRearRowReminder " + isSupportRearRowReminder);
        return isSupportRearRowReminder;
    }

    public int getAudioVendor() {
        return CarConfigHelper.getAudioVendor();
    }

    public boolean hasDolby() {
        return CarConfigHelper.hasDolby();
    }

    public boolean isSupportLanguageSet() {
        boolean isSupportLanguageSet = CarFunction.isSupportLanguageSet();
        log("ConfigUIManager isSupportLanguageSet " + isSupportLanguageSet);
        return isSupportLanguageSet;
    }

    public boolean isSupportRegionSet() {
        boolean isSupportRegionSet = CarFunction.isSupportRegionSet();
        log("ConfigUIManager isSupportRegionSet " + isSupportRegionSet);
        return isSupportRegionSet;
    }

    public boolean isSupportSayHi() {
        boolean isSupportSayHi = CarFunction.isSupportSayHi();
        log("ConfigUIManager isSupportSayHi " + isSupportSayHi);
        return isSupportSayHi;
    }

    public boolean isSupportRearScreen() {
        boolean hasRearScreen = CarConfigHelper.hasRearScreen();
        log("ConfigUIManager isSupportRearScreen " + hasRearScreen);
        return hasRearScreen;
    }

    public boolean isDecreaseVolume() {
        boolean isDecreaseVolume = CarFunction.isDecreaseVolume();
        log("ConfigUIManager isDecreaseVolume " + isDecreaseVolume);
        return isDecreaseVolume;
    }
}
