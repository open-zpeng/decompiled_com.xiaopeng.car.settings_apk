package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.display.XpDisplayManager;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import java.util.Calendar;
/* loaded from: classes.dex */
public class GearChangeWork implements WorkService, IVcuChangeListener {
    private Context mContext;
    private boolean mIsCarGearP = false;
    PowerManager mPowerManager;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        Logs.d("GearChangeWork addListener");
        this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        CarSettingsManager.getInstance().addVcuChangeListener(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        Logs.d("GearChangeWork removeListener");
        CarSettingsManager.getInstance().removeVcuChangeListener(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener
    public void onValueChange(int i, int i2) {
        Logs.d("GearChangeWork onValueChange:" + i + " value:" + i2);
        if (i != 557847045) {
            return;
        }
        if (i2 == 4) {
            if (!this.mIsCarGearP) {
                long preferenceForKeyLong = GlobalSettingsSharedPreference.getPreferenceForKeyLong(this.mContext, Config.BURIED_POINT_LAST_UPLOAD_TIME, -1L);
                Logs.d("GearChangeWork lastUploadTime:" + preferenceForKeyLong);
                if (!isSameDay(preferenceForKeyLong, System.currentTimeMillis())) {
                    uploadBI();
                    GlobalSettingsSharedPreference.setPreferenceForKeyLong(this.mContext, Config.BURIED_POINT_LAST_UPLOAD_TIME, System.currentTimeMillis());
                    pushStorageFull();
                }
            }
            this.mIsCarGearP = true;
            return;
        }
        this.mIsCarGearP = false;
        if (i2 == 3) {
            Logs.d("xpspeech onScreenOn R gear ");
            try {
                this.mPowerManager.setXpScreenOn(SystemClock.uptimeMillis());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private boolean isSameDay(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = calendar.get(1);
        int i2 = calendar.get(6);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        return i == calendar2.get(1) && i2 == calendar2.get(6);
    }

    private void uploadBI() {
        String str;
        String str2;
        Logs.d("GearChangeWork uploadBI");
        SoundManager soundManager = SoundManager.getInstance();
        XpAccountManager xpAccountManager = XpAccountManager.getInstance();
        XpDisplayManager xpDisplayManager = XpDisplayManager.getInstance();
        DataRepository dataRepository = DataRepository.getInstance();
        LaboratoryManager laboratoryManager = LaboratoryManager.getInstance();
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 0, soundManager.getStreamVolume(3));
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 1, soundManager.getStreamVolume(10));
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 2, soundManager.getStreamVolume(9));
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 3, soundManager.isSystemSoundEnabled());
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 8, xpAccountManager.getVolumeDownWithDoorOpen());
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 9, xpAccountManager.getVolumeDownWithGearR());
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B002", 4, xpAccountManager.getSafetyVolume());
        if (CarConfigHelper.hasMainDriverVIP()) {
            BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B001", 1, "type", xpAccountManager.getMainDriverExclusive());
            BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B001", 2, DataRepository.getInstance().getSettingProvider(this.mContext, XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, false));
        }
        if (CarFunction.isNonSelfPageUI()) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B005", soundManager.getSoundEffectType(soundManager.getSoundEffectMode()));
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B006", soundManager.getActiveSoundEffectTheme());
            str = BuriedPointUtils.LABORATORY_PAGE_NEW_ID;
            str2 = "B012";
        } else {
            str = BuriedPointUtils.LABORATORY_PAGE_ID;
            str2 = "B007";
        }
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B004", 0, xpDisplayManager.getUIProgressByReal(dataRepository.getScreenBrightness(this.mContext)));
        BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B004", 1, xpDisplayManager.getMeterBrightness(this.mContext));
        int autoBrightness = CarConfigHelper.autoBrightness();
        if (autoBrightness == -1) {
            BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B004", 3, xpAccountManager.getDarkLightAdaptation());
        } else if (autoBrightness == 0 || autoBrightness == 1) {
            BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B004", 2, xpAccountManager.getAutoBrightness());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.APP_USED_LIMIT)) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 0, laboratoryManager.getAppUsedLimitEnable());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.LOW_SPEED_ALARM_SOUND) && CarFunction.isSupportLowSpeedVolumeSwitch()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 1, laboratoryManager.getLowSpeedVolumeEnable(this.mContext));
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.AUTO_POWER_OFF)) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 2, laboratoryManager.isAutoPowerOff());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.REMOTE_CONTROL_KEY_PARKING) && CarFunction.isSupportKeyPark()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 4, laboratoryManager.isKeyParkingEnable());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.LEAVE_LOCK) && CarFunction.isSupportLeaveLock()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 5, laboratoryManager.getLeaveLockEnable());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.NEAR_UNLOCK) && CarFunction.isSupportNearUnlock()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 6, laboratoryManager.getNearUnlockEnable());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.SOLDIER_MODE) && CarFunction.isSupportSoldierModeLevel()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 7, "type", laboratoryManager.getSoldierSwState());
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.LOW_SPEED_ALARM_SOUND) && CarFunction.isSupportLowSpeedVolumeSlider()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 8, BuriedPointUtils.COUNT_KEY, laboratoryManager.packageLspVolume(laboratoryManager.getLowSpeedVolume()));
        }
        if (laboratoryManager.isConfigShow(Config.LaboratoryNetConfig.TTS_BROADCAST) && CarFunction.isSupportAutoDriveTts()) {
            BuriedPointUtils.sendPageStateDataLog(str, str2, 9, "type", laboratoryManager.getTtsBroadcastType() != 1 ? 1 : 0);
        }
        XpAboutManager.getInstance().uploadDailyDatalog();
    }

    private void pushStorageFull() {
        LogUtils.d("GearChangeWork pushStorageFull");
        if (CarFunction.isSupportAutoClean()) {
            if (XpAboutManager.isNeedPushStorageFull()) {
                if (!isSameDay(XpAboutManager.getPushStorageFullTime(), System.currentTimeMillis())) {
                    if (XpAboutManager.isFullStorage(95)) {
                        LogUtils.d("GearChangeWork pushStorageFull start pushAIStorageFull");
                        XpAboutManager.pushAIStorageFull();
                        XpAboutManager.setPushStorageFullTime(System.currentTimeMillis());
                        return;
                    }
                    LogUtils.d("GearChangeWork pushStorageFull storage not full,cancel push");
                    XpAboutManager.setPushStorageFullTime(-1L);
                    return;
                }
                LogUtils.d("GearChangeWork pushStorageFull is same day");
                return;
            }
            LogUtils.d("GearChangeWork pushStorageFull not need push storage full");
        } else if (CarFunction.isSupportStorageManage() && XpAboutManager.isFullStorage(100)) {
            XpAboutManager.pushAIStorageFull();
        }
    }
}
