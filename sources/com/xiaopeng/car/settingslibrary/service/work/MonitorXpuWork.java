package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
/* loaded from: classes.dex */
public class MonitorXpuWork implements WorkService, IXpuChangeListener {
    Context mContext;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        CarSettingsManager.getInstance().addXpuChangeListener(this);
        Logs.d("xpservice MonitorXpuWork onCreate");
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        CarSettingsManager.getInstance().removeXpuChangeListener(this);
        Logs.d("xpservice MonitorXpuWork onDestroy");
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener
    public void onXpuStatusChange(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3 && i != 4 && i != 5) {
                        return;
                    }
                }
            }
            if (CarConfigHelper.autoBrightness() == 0) {
                if (DataRepository.getInstance().isAdptiveBrightness(this.mContext)) {
                    DataRepository.getInstance().setAdaptiveBrightness(this.mContext, false, false);
                    Logs.d("xpservice MonitorXpuWork XPU_ON isRestoreAutoBrightness:");
                    GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.BRIGHTNESS_ADPTIVE_RESTORE, true);
                }
                if (CarFunction.isNonSelfPageUI()) {
                    if (DataRepository.getInstance().isMainAutoBrightnessModeEnable()) {
                        DataRepository.getInstance().setMainAdaptiveBrightness(false, false);
                        Logs.d("xpservice MonitorXpuWork XPU_ON main isRestoreAutoBrightness:");
                        GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.MAIN_BRIGHTNESS_ADPTIVE_RESTORE, true);
                    }
                    if (DataRepository.getInstance().isMeterAutoBrightnessModeEnable()) {
                        DataRepository.getInstance().setMeterAdaptiveBrightness(false, false);
                        Logs.d("xpservice MonitorXpuWork XPU_ON main isRestoreMeterAutoBrightness:");
                        GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.METER_BRIGHTNESS_ADPTIVE_RESTORE, true);
                    }
                    if (DataRepository.getInstance().isPsnAutoBrightnessModeEnable()) {
                        DataRepository.getInstance().setPsnAdaptiveBrightness(false);
                        Logs.d("xpservice MonitorXpuWork XPU_ON psn isRestoreAutoBrightness:");
                        GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.PSN_BRIGHTNESS_ADPTIVE_RESTORE, true);
                    }
                }
            }
            if (CarFunction.isSupportKeyPark() && CarSettingsManager.getInstance().isKeyParkingEnable()) {
                CarSettingsManager.getInstance().setKeyParkingEnableOnly(false);
                Logs.d("xpservice MonitorXpuWork XPU_ON isRestoreKeyParking:");
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.KEY_PARKING_RESTORE, true);
            }
            if (CarFunction.isSupportRemoteParkAdvanced() && CarSettingsManager.getInstance().isRemoteParkingAdvancedEnable()) {
                CarSettingsManager.getInstance().setRemoteParkingAdvancedEnable(false);
                Logs.d("xpservice MonitorXpuWork XPU_ON isRestoreParkingAdvanced:");
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.PARKING_ADVANCED_RESTORE, true);
            }
            if (CarFunction.isSupportCarCallAdvanced() && CarSettingsManager.getInstance().isCarCallAdvancedEnable()) {
                CarSettingsManager.getInstance().setCarCallAdvancedEnable(false);
                Logs.d("xpservice MonitorXpuWork XPU_ON isRestoreCarCallAdvanced:");
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.CAR_CALL_ADVANCED_RESTORE, true);
                return;
            }
            return;
        }
        boolean preferenceForKey = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.BRIGHTNESS_ADPTIVE_RESTORE, false);
        Logs.d("xpservice MonitorXpuWork XPU_OFF isRestoreBrightness:" + preferenceForKey);
        if (preferenceForKey) {
            DataRepository.getInstance().setAdaptiveBrightness(this.mContext, true, false);
            GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.BRIGHTNESS_ADPTIVE_RESTORE, false);
        }
        if (CarFunction.isSupportKeyPark()) {
            boolean preferenceForKey2 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.KEY_PARKING_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF isRestoreParking:" + preferenceForKey2);
            if (preferenceForKey2) {
                if (CarSettingsManager.getInstance().getAutoParkSwEnable()) {
                    CarSettingsManager.getInstance().setKeyParkingEnableOnly(true);
                } else {
                    Logs.d("xpservice MonitorXpuWork XPU_OFF getAutoParkSwEnable false!");
                }
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.KEY_PARKING_RESTORE, false);
            }
        }
        if (CarFunction.isSupportRemoteParkAdvanced()) {
            boolean preferenceForKey3 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.PARKING_ADVANCED_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF isRestoreParkingAdvanced:" + preferenceForKey3);
            if (preferenceForKey3) {
                if (CarSettingsManager.getInstance().isParkByMemoryEnable()) {
                    CarSettingsManager.getInstance().setRemoteParkingAdvancedEnable(true);
                } else {
                    Logs.d("xpservice MonitorXpuWork XPU_OFF isParkByMemoryEnable false!");
                }
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.PARKING_ADVANCED_RESTORE, false);
            }
        }
        if (CarFunction.isSupportCarCallAdvanced()) {
            boolean preferenceForKey4 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.CAR_CALL_ADVANCED_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF isCarCallAdvanced:" + preferenceForKey4);
            if (preferenceForKey4) {
                if (CarSettingsManager.getInstance().isParkByMemoryEnable()) {
                    CarSettingsManager.getInstance().setCarCallAdvancedEnable(true);
                } else {
                    Logs.d("xpservice MonitorXpuWork XPU_OFF carcall isParkByMemoryEnable false!");
                }
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.CAR_CALL_ADVANCED_RESTORE, false);
            }
        }
        if (CarFunction.isNonSelfPageUI()) {
            boolean preferenceForKey5 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.MAIN_BRIGHTNESS_ADPTIVE_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF main isMainRestoreBrightness:" + preferenceForKey5);
            if (preferenceForKey5) {
                DataRepository.getInstance().setMainAdaptiveBrightness(true, false);
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.MAIN_BRIGHTNESS_ADPTIVE_RESTORE, false);
            }
            boolean preferenceForKey6 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.PSN_BRIGHTNESS_ADPTIVE_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF psn isPsnRestoreBrightness:" + preferenceForKey6);
            if (preferenceForKey6) {
                DataRepository.getInstance().setPsnAdaptiveBrightness(true);
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.PSN_BRIGHTNESS_ADPTIVE_RESTORE, false);
            }
            boolean preferenceForKey7 = GlobalSettingsSharedPreference.getPreferenceForKey(this.mContext, Config.METER_BRIGHTNESS_ADPTIVE_RESTORE, false);
            Logs.d("xpservice MonitorXpuWork XPU_OFF psn isMeterRestoreBrightness:" + preferenceForKey6);
            if (preferenceForKey7) {
                DataRepository.getInstance().setMeterAdaptiveBrightness(true, false);
                GlobalSettingsSharedPreference.setPreferenceForKey(this.mContext, Config.METER_BRIGHTNESS_ADPTIVE_RESTORE, false);
            }
        }
    }
}
