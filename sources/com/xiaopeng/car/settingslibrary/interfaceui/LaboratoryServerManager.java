package com.xiaopeng.car.settingslibrary.interfaceui;

import android.net.Uri;
import android.provider.Settings;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper;
import com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel;
import com.xiaopeng.car.settingslibrary.vm.laboratory.LluSayHiEffect;
import com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues;
/* loaded from: classes.dex */
public class LaboratoryServerManager extends ServerBaseManager implements DisplayEventMonitorHelper.DisplayEventMonitorListener {
    private static final String TAG = "LaboratoryServerManager";
    private DisplayEventMonitorHelper mDisplayEventMonitorHelper;
    private LaboratoryViewModel mLaboratoryViewModel;

    private void registerSignalChange() {
    }

    private void unregisterSignalChange() {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final LaboratoryServerManager instance = new LaboratoryServerManager();

        private InnerFactory() {
        }
    }

    public static LaboratoryServerManager getInstance() {
        return InnerFactory.instance;
    }

    private LaboratoryServerManager() {
        this.mDisplayEventMonitorHelper = new DisplayEventMonitorHelper(CarSettingsApp.getContext(), this);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.DisplayEventMonitorListener
    public void onDisplayEventMonitorChange(Uri uri) {
        Logs.d("xplaboratory onDisplayEventMonitorChange uri:" + uri);
        if (uri.equals(Settings.System.getUriFor("app_screen_flow"))) {
            laboratoryCallback(InterfaceConstant.ON_APP_SCREEN_FLOW_CHANGED, String.valueOf(getScreenFlow()));
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getAppLimitLiveData().setValue(null);
        getViewModel().getAppLimitLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$ZcWhi1jmIQXJncs0SsA4aIZQIeo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$0$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getAutoPowerOffLiveData().setValue(null);
        getViewModel().getAutoPowerOffLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$LAo5LUypK6c6JDVZTTV4I-Uh8Kg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$1$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getCarCallAdvancedLiveData().setValue(null);
        getViewModel().getCarCallAdvancedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$RvO3AZxcZxIDOMaqwHFhL03nqFY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$2$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getInductionLockEnable().setValue(null);
        getViewModel().getInductionLockEnable().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$9IZsiETEYOzLIZ7TvAGqh27XlKI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$3$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getKeyParkAdvancedLiveData().setValue(null);
        getViewModel().getKeyParkAdvancedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$GIXIrS4koeED4vV9WcY4lVPX2LU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$4$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getKeyParkLiveData().setValue(null);
        getViewModel().getKeyParkLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$9p_5stDCSCarVC9Gs21qiMiBrsM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$5$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getLeaveLockLiveData().setValue(null);
        getViewModel().getLeaveLockLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$HXPbvorAPup4zxN3goF3yCoPYEQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$6$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getLowSpeedLiveData().setValue(null);
        getViewModel().getLowSpeedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$wdaSGC8hMcu_8aIn0bNoik1lfKc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$7$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getLowSpeedVolumeLiveData().setValue(-1);
        getViewModel().getLowSpeedVolumeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$Lqp8AKtN7lrBHs7VeDC_-Jxjj9s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$8$LaboratoryServerManager((Integer) obj);
            }
        });
        getViewModel().getNearUnlockLiveData().setValue(null);
        getViewModel().getNearUnlockLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$LjUJZoA9T_7CMsph5xW2xsMBVDU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$9$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getNedcModeStatus().setValue(-1);
        getViewModel().getNedcModeStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$rtwY4p8XSW_cI_saQVv5Gwc0iGo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$10$LaboratoryServerManager((Integer) obj);
            }
        });
        getViewModel().getNetConfigValuesLiveData().setValue(null);
        getViewModel().getNetConfigValuesLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$cuAouuixaojLUz737WhKzJ5LIhY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$11$LaboratoryServerManager((NetConfigValues) obj);
            }
        });
        getViewModel().getRemoteParkLiveData().setValue(null);
        getViewModel().getRemoteParkLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$tHZTAT-AvjERGC9ygP4KeegMasg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$12$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getSoldierCameraEnableLiveData().setValue(null);
        getViewModel().getSoldierCameraEnableLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$9V1uhxu6OLe52tm5NVhD83uWAH8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$13$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getSoldierCameraLiveData().setValue(null);
        getViewModel().getSoldierCameraLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$Nhzgd1PnMk0-m_7LEQWYL6lLD6w
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$14$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getSoldierStatusLiveData().setValue(-1);
        getViewModel().getSoldierStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$TV3DSe6PmnIzOhQ00Ob6aMl6HtA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$15$LaboratoryServerManager((Integer) obj);
            }
        });
        getViewModel().getRearRowReminderLiveData().setValue(null);
        getViewModel().getRearRowReminderLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$mNRBlijeZ3xw9QNJD_lZdnw48GQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$16$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getLluSayHiSwLiveData().setValue(null);
        getViewModel().getLluSayHiSwLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$CQytOoYuBHNBr3OFkNCMqSQmQkE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$17$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getAvasSayHiSwLiveData().setValue(null);
        getViewModel().getAvasSayHiSwLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$qP3PYfa1hKoW1hd4U1tWIdK9GCU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$18$LaboratoryServerManager((Boolean) obj);
            }
        });
        getViewModel().getLluSayHiEffectLiveData().setValue(null);
        getViewModel().getLluSayHiEffectLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$LaboratoryServerManager$yAgP7RjMcUWfVliqqvBd7hHI8mo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryServerManager.this.lambda$observeData$19$LaboratoryServerManager((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onAppLimitChanged ");
        laboratoryCallback(InterfaceConstant.ON_APP_LIMIT_CHANGED, String.valueOf(this.mLaboratoryViewModel.isAppLimitEnable()));
    }

    public /* synthetic */ void lambda$observeData$1$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onAutoPowerOffChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_AUTO_POWER_OFF_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$2$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onCarCallAdvancedChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_CAR_CALL_ADVANCED_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$3$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onInductionLock " + bool);
        laboratoryCallback(InterfaceConstant.ON_INDUCTION_LOCK_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$4$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onKeyParkAdvancedChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_KEY_PARK_ADVANCED_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$5$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onKeyParkChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_KEY_PARK_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$6$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onLeaveLockChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_LEAVE_LOCK_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$7$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onLowSpeedVolumeSwitchChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_LSP_SWITCH_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$8$LaboratoryServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("LaboratoryServerManager onLowSpeedVolumeChanged " + num);
        laboratoryCallback(InterfaceConstant.ON_LSP_VOL_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$9$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onNearUnlockChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_NEAR_UNLOCK_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$10$LaboratoryServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("LaboratoryServerManager getNedcModeStatus " + num);
        laboratoryCallback(InterfaceConstant.ON_GET_NEDC_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$11$LaboratoryServerManager(NetConfigValues netConfigValues) {
        if (netConfigValues == null) {
            return;
        }
        debugLog("LaboratoryServerManager onConfigChanged " + netConfigValues);
        laboratoryCallback(InterfaceConstant.ON_CONFIG_CHANGED, JsonUtils.toJSONString(netConfigValues));
    }

    public /* synthetic */ void lambda$observeData$12$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onRemoteParkChanged " + bool);
        laboratoryCallback(InterfaceConstant.ON_REMOTE_PARK_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$13$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onSoldierCameraEnable " + bool);
        laboratoryCallback(InterfaceConstant.ON_SOLDIER_CAMERA_ENABLE, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$14$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager onSoldierCamera " + bool);
        laboratoryCallback(InterfaceConstant.ON_SOLDIER_CAMERA, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$15$LaboratoryServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("LaboratoryServerManager onSoldierStatus " + num);
        laboratoryCallback(InterfaceConstant.ON_SOLDIER_STATUS, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$16$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager RearRowReminder ");
        laboratoryCallback(InterfaceConstant.ON_REAR_ROW_REMINDER_CHANGED, String.valueOf(this.mLaboratoryViewModel.isRearRowReminder()));
    }

    public /* synthetic */ void lambda$observeData$17$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager sayHiSw " + bool);
        laboratoryCallback(InterfaceConstant.ON_SAY_HI_SWITCH_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$18$LaboratoryServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("LaboratoryServerManager sayHiAvasSw " + bool);
        laboratoryCallback(InterfaceConstant.ON_SAY_HI_AVAS_SWITCH_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$19$LaboratoryServerManager(Integer num) {
        if (num == null) {
            return;
        }
        debugLog("LaboratoryServerManager sayHiEffect " + num);
        laboratoryCallback(InterfaceConstant.ON_SAY_HI_EFFECT_CHANGED, String.valueOf(num));
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized LaboratoryViewModel getViewModel() {
        if (this.mLaboratoryViewModel == null) {
            this.mLaboratoryViewModel = new LaboratoryViewModel(CarSettingsApp.getApp());
        }
        return this.mLaboratoryViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("LaboratoryServerManager startVm ");
        getViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("LaboratoryServerManager stopVm ");
        getViewModel().onStopVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
        this.mDisplayEventMonitorHelper.registerAppScreenFlowChange();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
        this.mDisplayEventMonitorHelper.unregisterAppScreenFlowChange();
    }

    public void setSoldierSw(int i) {
        log("LaboratoryServerManager setSoldierSw status:" + i);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B004", i);
        getViewModel().setSoldierSw(i);
    }

    public void setTtsBroadcastType(int i) {
        log("LaboratoryServerManager setTtsBroadcastType type:" + i);
        getViewModel().setTtsBroadcastType(i);
    }

    public boolean isParkByMemoryEnable() {
        boolean isParkByMemoryEnable = getViewModel().isParkByMemoryEnable();
        log("LaboratoryServerManager isParkByMemoryEnable " + isParkByMemoryEnable);
        return isParkByMemoryEnable;
    }

    public int getNedcSwitchStatus() {
        int nedcSwitchStatus = getViewModel().getNedcSwitchStatus();
        log("LaboratoryServerManager getNedcSwitchStatus " + nedcSwitchStatus);
        return nedcSwitchStatus;
    }

    public int getSoldierSwState() {
        int soldierSwState = getViewModel().getSoldierSwState();
        log("LaboratoryServerManager getSoldierSwState " + soldierSwState);
        return soldierSwState;
    }

    public boolean isSoldierCameraEnable() {
        boolean isSoldierCameraEnable = getViewModel().isSoldierCameraEnable();
        log("LaboratoryServerManager isSoldierCameraEnable " + isSoldierCameraEnable);
        return isSoldierCameraEnable;
    }

    public void setSoldierCameraSw(boolean z) {
        log("LaboratoryServerManager setSoldierCameraSw enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B005", z);
        getViewModel().setSoldierCameraSw(z);
    }

    public void setInductionLock(boolean z) {
        log("LaboratoryServerManager setInductionLock enable:" + z);
        getViewModel().setInductionLock(z);
    }

    public void setRemoteParkingAdvancedEnable(boolean z) {
        log("LaboratoryServerManager setRemoteParkingAdvancedEnable check:" + z);
        getViewModel().setRemoteParkingAdvancedEnable(z);
    }

    public void setCarCallAdvancedEnable(boolean z) {
        log("LaboratoryServerManager setCarCallAdvancedEnable enable:" + z);
        getViewModel().setCarCallAdvancedEnable(z);
    }

    public void enableAppLimit(boolean z) {
        log("LaboratoryServerManager enableAppLimit check:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B010", z);
        getViewModel().enableAppLimit(z);
    }

    public NetConfigValues getAllConfiguration() {
        NetConfigValues allConfiguration = getViewModel().getAllConfiguration();
        log("LaboratoryServerManager getAllConfiguration " + allConfiguration);
        return allConfiguration;
    }

    public int getLowSpeedVolume() {
        int lowSpeedVolume = getViewModel().getLowSpeedVolume();
        log("LaboratoryServerManager getLowSpeedVolume " + lowSpeedVolume);
        return lowSpeedVolume;
    }

    public boolean isAppLimitEnable() {
        boolean isAppLimitEnable = getViewModel().isAppLimitEnable();
        log("LaboratoryServerManager isAppLimitEnable " + isAppLimitEnable);
        return isAppLimitEnable;
    }

    public boolean getAutoPowerOff() {
        boolean autoPowerOff = getViewModel().getAutoPowerOff();
        log("LaboratoryServerManager getAutoPowerOff " + autoPowerOff);
        return autoPowerOff;
    }

    public boolean isLowSpeedSoundEnable() {
        boolean isLowSpeedSoundEnable = getViewModel().isLowSpeedSoundEnable();
        log("LaboratoryServerManager isLowSpeedSoundEnable " + isLowSpeedSoundEnable);
        return isLowSpeedSoundEnable;
    }

    public boolean isRemoteParkingEnable() {
        boolean isRemoteParkingEnable = getViewModel().isRemoteParkingEnable();
        log("LaboratoryServerManager isRemoteParkingEnable " + isRemoteParkingEnable);
        return isRemoteParkingEnable;
    }

    public boolean getLeaveLockEnable() {
        boolean leaveLockEnable = getViewModel().getLeaveLockEnable();
        log("LaboratoryServerManager getLeaveLockEnable " + leaveLockEnable);
        return leaveLockEnable;
    }

    public boolean getNearUnlockEnable() {
        boolean nearUnlockEnable = getViewModel().getNearUnlockEnable();
        log("LaboratoryServerManager getNearUnlockEnable " + nearUnlockEnable);
        return nearUnlockEnable;
    }

    public boolean getSoldierCameraSw() {
        boolean soldierCameraSw = getViewModel().getSoldierCameraSw();
        log("LaboratoryServerManager getSoldierCameraSw " + soldierCameraSw);
        return soldierCameraSw;
    }

    public boolean isKeyParkingEnable() {
        boolean isKeyParkingEnable = getViewModel().isKeyParkingEnable();
        log("LaboratoryServerManager isKeyParkingEnable " + isKeyParkingEnable);
        return isKeyParkingEnable;
    }

    public boolean isRemoteParkingAdvancedEnable() {
        boolean isRemoteParkingAdvancedEnable = getViewModel().isRemoteParkingAdvancedEnable();
        log("LaboratoryServerManager isRemoteParkingAdvancedEnable " + isRemoteParkingAdvancedEnable);
        return isRemoteParkingAdvancedEnable;
    }

    public boolean isCarCallAdvancedEnable() {
        boolean isCarCallAdvancedEnable = getViewModel().isCarCallAdvancedEnable();
        log("LaboratoryServerManager isCarCallAdvancedEnable " + isCarCallAdvancedEnable);
        return isCarCallAdvancedEnable;
    }

    public boolean isInductionLockEnable() {
        boolean isInductionLock = getViewModel().isInductionLock();
        log("LaboratoryServerManager isInductionLockEnable " + isInductionLock);
        return isInductionLock;
    }

    public int getTtsBroadcastType() {
        int ttsBroadcastType = getViewModel().getTtsBroadcastType();
        log("LaboratoryServerManager getTtsBroadcastType " + ttsBroadcastType);
        return ttsBroadcastType;
    }

    public void enableLowSpeedSound(boolean z) {
        log("LaboratoryServerManager enableLowSpeedSound check:" + z);
        getViewModel().enableLowSpeedSound(z);
    }

    public void enableAutoPowerOff(boolean z) {
        log("LaboratoryServerManager enableAutoPowerOff enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B009", z);
        getViewModel().enableAutoPowerOff(z);
    }

    public void setRemoteParkingEnable(boolean z) {
        log("LaboratoryServerManager setRemoteParkingEnable enable:" + z);
        getViewModel().setRemoteParkingEnable(z);
    }

    public void setNearUnlockEnable(boolean z) {
        log("LaboratoryServerManager setNearUnlockEnable check:" + z);
        getViewModel().setNearUnlockEnable(z);
    }

    public void setLeaveLockEnable(boolean z) {
        log("LaboratoryServerManager setLeaveLockEnable check:" + z);
        getViewModel().setLeaveLockEnable(z);
    }

    public void setKeyParkingEnable(boolean z) {
        log("LaboratoryServerManager setKeyParkingEnable enable:" + z);
        getViewModel().setKeyParkingEnable(z);
    }

    public void setLowSpeedVolume(int i) {
        log("LaboratoryServerManager setLowSpeedVolume lowSpeedVolume:" + i);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B003", BuriedPointUtils.COUNT_KEY, i);
        getViewModel().setLowSpeedVolume(i);
    }

    public void setScreenFlow(boolean z) {
        log("LaboratoryServerManager setScreenFlow flow:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B011", z);
        DataRepository.getInstance().setScreenFlow(z);
    }

    public boolean getScreenFlow() {
        boolean screenFlow = DataRepository.getInstance().getScreenFlow();
        log("LaboratoryServerManager getScreenFlow flow:" + screenFlow);
        return screenFlow;
    }

    public boolean isRearRowReminder() {
        return getViewModel().isRearRowReminder();
    }

    public void setRearRowReminder(boolean z) {
        getViewModel().setRearRowReminder(z);
    }

    public void setSayHiEffect(int i) {
        LluSayHiEffect lluSayHiEffect;
        if (i == 1) {
            lluSayHiEffect = LluSayHiEffect.EffectA;
        } else if (i == 2) {
            lluSayHiEffect = LluSayHiEffect.EffectB;
        } else {
            lluSayHiEffect = i != 3 ? null : LluSayHiEffect.EffectC;
        }
        if (lluSayHiEffect != null) {
            getViewModel().setSayHiEffect(lluSayHiEffect);
        }
    }

    public int getLluSayHiEffect() {
        return getViewModel().getLluSayHiEffect();
    }

    public void setSayHiEnable(boolean z) {
        getViewModel().setSayHiEnable(z);
    }

    public boolean getSayHiEnable() {
        return getViewModel().getSayHiEnable();
    }

    public void setSayHiAvasEnable(boolean z) {
        getViewModel().setSayHiAvasEnable(z);
    }

    public boolean getSayHiAvasEnable() {
        return getSayHiEnable() && getViewModel().getSayHiAvasEnable();
    }
}
