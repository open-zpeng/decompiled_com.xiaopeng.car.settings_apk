package com.xiaopeng.car.settingslibrary.manager.account;

import android.accounts.AccountManager;
import android.content.Context;
import android.text.format.DateFormat;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.AboutSysServerManager;
import com.xiaopeng.car.settingslibrary.interfaceui.DisplayServerManager;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.xvs.tools.Tools;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.account.api.AccountInfo;
import com.xiaopeng.xvs.xid.account.api.IAccount;
import com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener;
import com.xiaopeng.xvs.xid.sync.api.SyncType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes.dex */
public class XpAccountManager implements OnSyncChangedListener {
    static final String EMPTY = "";
    private static final String KEY_24_HOUR_FORMAT = "is_24_hour_format";
    private static final String KEY_ANNOUNCEMENT_VOICE = "KEY_ANNOUNCEMENT_VOICE";
    private static final String KEY_APP_STORE_NOTIFY = "KEY_APP_STORE_NOTIFY";
    private static final String KEY_AUTO_BRIGHTNESS = "KEY_AUTO_BRIGHTNESS";
    private static final String KEY_CAR_CONTROL_NOTIFY = "KEY_CAR_CONTROL_NOTIFY";
    private static final String KEY_DARK_LIGHT_ADAPTATION = "KEY_DARK_LIGHT_ADAPTATION";
    private static final String KEY_FONT_SIZE = "KEY_FONT_SIZE";
    private static final String KEY_MAIN_DRIVER_EXCLUSIVE = "main_driver_exclusive";
    private static final String KEY_METER_AUTO_BRIGHTNESS = "KEY_METER_AUTO_BRIGHTNESS";
    private static final String KEY_METER_DARK_LIGHT_ADAPTATION = "KEY_METER_DARK_LIGHT_ADAPTATION";
    private static final String KEY_OPERATING_NOTIFY = "KEY_OPERATING_NOTIFY";
    private static final String KEY_OTA_NOTIFY = "KEY_OTA_NOTIFY";
    private static final String KEY_REAR_SCREEN_CHILD_LOCK = "KEY_REAR_SCREEN_CHILD_LOCK";
    private static final String KEY_SAFETY_VOLUME = "meter_volume";
    private static final String KEY_SYSTEM_SOUND_ENABLE = "system_sound_enable";
    private static final String KEY_VALET_PARKING_ASSISTANCE_NOTIFY = "KEY_VALET_PARKING_ASSISTANCE_NOTIFY";
    private static final String KEY_VEHICLE_CHECK_NOTIFY = "KEY_VEHICLE_CHECK_NOTIFY";
    private static final String KEY_VOLUME_DOWN_DOOR_OPEN = "KEY_DOOR_OPEN";
    private static final String KEY_VOLUME_DOWN_GEAR_R = "KEY_R_STALL";
    private static final String KEY_VOLUME_DOWN_NAVI_MEDIA = "KEY_NAVI_MEDIA";
    private static final String KEY_XPILOT_NOTIFY = "KEY_XPILOT_NOTIFY";
    private static final String KEY_XP_MUSIC_NOTIFY = "KEY_XP_MUSIC_NOTIFY";
    public static final String TAG = "XpAccountManager-xld";
    private static volatile XpAccountManager sInstance;
    private static ArrayList<String> sSyncItems = new ArrayList<>();
    private AccountManager mAccountManager;
    private Context mContext;
    private DataRepository mDataRepository = DataRepository.getInstance();
    private Float mFontSize = null;
    private Boolean mIs24HourFormat = null;
    private Boolean mRearScreenChildLock = null;
    private Boolean mSystemSoundEnable = null;
    private Integer mSafetyVolume = null;
    private Boolean mVolumeDownWithDoorOpen = null;
    private Boolean mVolumeDownWithGearR = null;
    private Boolean mVolumeDownWithNavigating = null;
    private Boolean mAutoBrightness = null;
    private Boolean mDarkLightAdaptation = null;
    private Boolean mMeterAutoBrightness = null;
    private Boolean mMeterDarkLightAdaptation = null;
    private Boolean mMusicNotify = null;
    private Boolean mOtaNotify = null;
    private Boolean mAppStoreNotify = null;
    private Boolean mCarControlNotify = null;
    private Boolean mXpilotNotify = null;
    private Boolean mParkingNotify = null;
    private Boolean mVehicleCheckNotify = null;
    private Boolean mOperatingNotify = null;
    private Boolean mTtsBroadcastNotify = null;
    private Integer mMainDriverExclusive = null;
    private IAccount mIAccount = XId.getAccountApi();
    private SoundManager mSoundManager = SoundManager.getInstance();
    private ISync mSync = XId.getSyncApi();

    /* loaded from: classes.dex */
    public static class SettingUpdateEvent {
    }

    private boolean isGuest(long j) {
        return j == -1;
    }

    private int saveFontSwitch(float f) {
        return f == 1.0f ? 1 : 0;
    }

    private float switchFontSize(int i) {
        return i == 1 ? 1.0f : 0.9f;
    }

    static {
        sSyncItems.add("KEY_FONT_SIZE");
        sSyncItems.add("is_24_hour_format");
        sSyncItems.add("system_sound_enable");
        sSyncItems.add("meter_volume");
        sSyncItems.add("main_driver_exclusive");
        sSyncItems.add(KEY_VOLUME_DOWN_NAVI_MEDIA);
        sSyncItems.add("KEY_DOOR_OPEN");
        sSyncItems.add("KEY_R_STALL");
        sSyncItems.add("KEY_AUTO_BRIGHTNESS");
        sSyncItems.add("KEY_DARK_LIGHT_ADAPTATION");
        sSyncItems.add("KEY_METER_AUTO_BRIGHTNESS");
        sSyncItems.add("KEY_METER_DARK_LIGHT_ADAPTATION");
        sSyncItems.add("KEY_XP_MUSIC_NOTIFY");
        sSyncItems.add("KEY_OTA_NOTIFY");
        sSyncItems.add("KEY_APP_STORE_NOTIFY");
        sSyncItems.add("KEY_CAR_CONTROL_NOTIFY");
        sSyncItems.add("KEY_XPILOT_NOTIFY");
        sSyncItems.add("KEY_VALET_PARKING_ASSISTANCE_NOTIFY");
        sSyncItems.add("KEY_VEHICLE_CHECK_NOTIFY");
        sSyncItems.add("KEY_OPERATING_NOTIFY");
        sSyncItems.add("KEY_ANNOUNCEMENT_VOICE");
        sSyncItems.add("KEY_REAR_SCREEN_CHILD_LOCK");
    }

    public static XpAccountManager getInstance() {
        if (sInstance == null) {
            synchronized (XpAccountManager.class) {
                if (sInstance == null) {
                    sInstance = new XpAccountManager(CarSettingsApp.getContext());
                }
            }
        }
        return sInstance;
    }

    private XpAccountManager(Context context) {
        this.mContext = context;
        this.mAccountManager = AccountManager.get(context);
        addAccountListener();
        Logs.d("XpAccountManager-xld init");
    }

    public void addAccountListener() {
        ISync iSync = this.mSync;
        if (iSync != null) {
            try {
                iSync.setSyncChangedListener(this, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getAccountName() {
        AccountInfo accountInfo = this.mIAccount.getAccountInfo();
        return accountInfo != null ? accountInfo.getName() : "";
    }

    public void saveFontSize(float f, boolean z) {
        Logs.d("XpAccountManager-xld fontSize " + f);
        this.mFontSize = Float.valueOf(f);
        if (z) {
            save("KEY_FONT_SIZE", String.valueOf(saveFontSwitch(this.mFontSize.floatValue())));
        }
    }

    public void save24HourFormat(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld is24HourFormat " + z);
        this.mIs24HourFormat = Boolean.valueOf(z);
        if (z2) {
            save("is_24_hour_format", String.valueOf(this.mIs24HourFormat));
        }
    }

    public void saveSystemSoundEnable(boolean z) {
        Logs.d("XpAccountManager-xld systemSoundEnable " + z);
        this.mSystemSoundEnable = Boolean.valueOf(z);
        save("system_sound_enable", String.valueOf(this.mSystemSoundEnable));
    }

    public void saveRearScreenLockEnable(boolean z) {
        Logs.d("XpAccountManager-xld saveRearScreenLockEnable " + z);
        this.mRearScreenChildLock = Boolean.valueOf(z);
        save("KEY_REAR_SCREEN_CHILD_LOCK", String.valueOf(this.mRearScreenChildLock));
    }

    public void saveSafetyVolume(int i, boolean z) {
        Logs.d("XpAccountManager-xld safetyVolume " + i);
        this.mSafetyVolume = Integer.valueOf(i);
        if (z) {
            save("meter_volume", String.valueOf(this.mSafetyVolume));
        }
    }

    public void saveMainDriverExclusive(int i, boolean z) {
        Logs.d("XpAccountManager-xld saveMainDriverExclusive " + i);
        this.mMainDriverExclusive = Integer.valueOf(i);
        if (z) {
            save("main_driver_exclusive", String.valueOf(this.mMainDriverExclusive));
        }
    }

    public void saveVolumeDownWithNavigating(boolean z) {
        Logs.d("XpAccountManager-xldVolumeDownWithNavigating" + z);
        this.mVolumeDownWithNavigating = Boolean.valueOf(z);
        save(KEY_VOLUME_DOWN_NAVI_MEDIA, String.valueOf(this.mVolumeDownWithNavigating));
    }

    public void saveVolumeDownWithDoorOpen(boolean z) {
        Logs.d("XpAccountManager-xld volumeDownWithDoorOpen " + z);
        this.mVolumeDownWithDoorOpen = Boolean.valueOf(z);
        save("KEY_DOOR_OPEN", String.valueOf(this.mVolumeDownWithDoorOpen));
    }

    public void saveVolumeDownWithGearR(boolean z) {
        Logs.d("XpAccountManager-xld volumeDownWithGearR " + z);
        this.mVolumeDownWithGearR = Boolean.valueOf(z);
        save("KEY_R_STALL", String.valueOf(this.mVolumeDownWithGearR));
    }

    public void saveAutoBrightness(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveAutoBrightness " + z);
        this.mAutoBrightness = Boolean.valueOf(z);
        if (z2) {
            save("KEY_AUTO_BRIGHTNESS", String.valueOf(this.mAutoBrightness));
        }
    }

    public void saveDarkLightAdaptation(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveDarkLightAdaptation " + z);
        this.mDarkLightAdaptation = Boolean.valueOf(z);
        if (z2) {
            save("KEY_DARK_LIGHT_ADAPTATION", String.valueOf(this.mDarkLightAdaptation));
        }
    }

    public void saveMeterAutoBrightness(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveMeterAutoBrightness " + z);
        this.mMeterAutoBrightness = Boolean.valueOf(z);
        if (z2) {
            save("KEY_METER_AUTO_BRIGHTNESS", String.valueOf(this.mMeterAutoBrightness));
        }
    }

    public void saveMeterDarkLightAdaptation(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveMeterDarkLightAdaptation " + z);
        this.mMeterDarkLightAdaptation = Boolean.valueOf(z);
        if (z2) {
            save("KEY_METER_DARK_LIGHT_ADAPTATION", String.valueOf(this.mMeterDarkLightAdaptation));
        }
    }

    public void saveMusicNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveMusicNotify " + z);
        this.mMusicNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_XP_MUSIC_NOTIFY", String.valueOf(this.mMusicNotify));
        }
    }

    public void saveOtaNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveOtaNotify " + z);
        this.mOtaNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_OTA_NOTIFY", String.valueOf(this.mOtaNotify));
        }
    }

    public void saveAppStoreNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveAppStoreNotify " + z);
        this.mAppStoreNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_APP_STORE_NOTIFY", String.valueOf(this.mAppStoreNotify));
        }
    }

    public void saveCarControlNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveCarControlNotify " + z);
        this.mCarControlNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_CAR_CONTROL_NOTIFY", String.valueOf(this.mCarControlNotify));
        }
    }

    public void saveXpilotNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveXpilotNotify " + z);
        this.mXpilotNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_XPILOT_NOTIFY", String.valueOf(this.mXpilotNotify));
        }
    }

    public void saveParkingNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveParkingNotify " + z);
        this.mParkingNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_VALET_PARKING_ASSISTANCE_NOTIFY", String.valueOf(this.mParkingNotify));
        }
    }

    public void saveVehicleCheckNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveVehicleCheckNotify " + z);
        this.mVehicleCheckNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_VEHICLE_CHECK_NOTIFY", String.valueOf(this.mVehicleCheckNotify));
        }
    }

    public void saveOperatingNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveOperatingNotify " + z);
        this.mOperatingNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_OPERATING_NOTIFY", String.valueOf(this.mOperatingNotify));
        }
    }

    public void saveTtsBroadcastNotify(boolean z, boolean z2) {
        Logs.d("XpAccountManager-xld saveOperatingNotify " + z);
        this.mTtsBroadcastNotify = Boolean.valueOf(z);
        if (z2) {
            save("KEY_ANNOUNCEMENT_VOICE", String.valueOf(this.mTtsBroadcastNotify));
        }
    }

    private void save(String str, String str2) {
        Logs.d("XpAccountManager-xld save key = " + str + ", value = " + str2);
        if (str2 == null) {
            str2 = "";
        }
        if (this.mSync == null) {
            Logs.d("XpAccountManager-xld setting mSync null");
            return;
        }
        ICarSettingsSync settingsSync = getSettingsSync();
        if (settingsSync == null) {
            Logs.d("XpAccountManager-xld settingsync null");
            return;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -2093427174:
                if (str.equals("KEY_ANNOUNCEMENT_VOICE")) {
                    c = 20;
                    break;
                }
                break;
            case -1905148246:
                if (str.equals("KEY_XP_MUSIC_NOTIFY")) {
                    c = '\f';
                    break;
                }
                break;
            case -1888864757:
                if (str.equals("KEY_METER_AUTO_BRIGHTNESS")) {
                    c = '\n';
                    break;
                }
                break;
            case -1730496710:
                if (str.equals("KEY_VALET_PARKING_ASSISTANCE_NOTIFY")) {
                    c = 17;
                    break;
                }
                break;
            case -1701946266:
                if (str.equals("KEY_REAR_SCREEN_CHILD_LOCK")) {
                    c = 21;
                    break;
                }
                break;
            case -1441169423:
                if (str.equals("KEY_FONT_SIZE")) {
                    c = 0;
                    break;
                }
                break;
            case -1291095493:
                if (str.equals("KEY_DOOR_OPEN")) {
                    c = 6;
                    break;
                }
                break;
            case -565668568:
                if (str.equals("KEY_XPILOT_NOTIFY")) {
                    c = 16;
                    break;
                }
                break;
            case -403953755:
                if (str.equals("KEY_DARK_LIGHT_ADAPTATION")) {
                    c = '\t';
                    break;
                }
                break;
            case -393880432:
                if (str.equals("meter_volume")) {
                    c = 3;
                    break;
                }
                break;
            case -241725462:
                if (str.equals("is_24_hour_format")) {
                    c = 1;
                    break;
                }
                break;
            case -218063135:
                if (str.equals("KEY_AUTO_BRIGHTNESS")) {
                    c = '\b';
                    break;
                }
                break;
            case -146741268:
                if (str.equals("KEY_OTA_NOTIFY")) {
                    c = '\r';
                    break;
                }
                break;
            case -103766771:
                if (str.equals("main_driver_exclusive")) {
                    c = 4;
                    break;
                }
                break;
            case 185962195:
                if (str.equals("KEY_VEHICLE_CHECK_NOTIFY")) {
                    c = 18;
                    break;
                }
                break;
            case 410132459:
                if (str.equals(KEY_VOLUME_DOWN_NAVI_MEDIA)) {
                    c = 5;
                    break;
                }
                break;
            case 461953654:
                if (str.equals("KEY_CAR_CONTROL_NOTIFY")) {
                    c = 15;
                    break;
                }
                break;
            case 661526211:
                if (str.equals("system_sound_enable")) {
                    c = 2;
                    break;
                }
                break;
            case 666518355:
                if (str.equals("KEY_R_STALL")) {
                    c = 7;
                    break;
                }
                break;
            case 668594727:
                if (str.equals("KEY_OPERATING_NOTIFY")) {
                    c = 19;
                    break;
                }
                break;
            case 1280194383:
                if (str.equals("KEY_METER_DARK_LIGHT_ADAPTATION")) {
                    c = 11;
                    break;
                }
                break;
            case 1754560517:
                if (str.equals("KEY_APP_STORE_NOTIFY")) {
                    c = 14;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                settingsSync.putFontScale(str2);
                return;
            case 1:
                settingsSync.put24Format(str2);
                return;
            case 2:
                settingsSync.putSystemSoundEnabled(str2);
                return;
            case 3:
                settingsSync.putMeterVolume(str2);
                return;
            case 4:
                settingsSync.putMainDriverExclusive(str2);
                return;
            case 5:
                settingsSync.putKeyLbsMediumVoice(str2);
                return;
            case 6:
                settingsSync.putVolumeDownWithDoorOpen(str2);
                return;
            case 7:
                settingsSync.putVolumeDownInReverseMode(str2);
                return;
            case '\b':
                settingsSync.putAutoBrightnessEnable(str2);
                return;
            case '\t':
                settingsSync.putDarkLightAdaptation(str2);
                return;
            case '\n':
                settingsSync.putMeterAutoBrightness(str2);
                return;
            case 11:
                settingsSync.putMeterDarkLightAdaptation(str2);
                return;
            case '\f':
                settingsSync.putKeyXpMusicNotify(str2);
                return;
            case '\r':
                settingsSync.setKeyOtaNotify(str2);
                return;
            case 14:
                settingsSync.setKeyAppStoreNotify(str2);
                return;
            case 15:
                settingsSync.setKeyCarControlNotify(str2);
                return;
            case 16:
                settingsSync.setKeyXpilotNotify(str2);
                return;
            case 17:
                settingsSync.setKeyValetParkingAssistanceNotify(str2);
                return;
            case 18:
                settingsSync.setKeyVehicleCheckNotify(str2);
                return;
            case 19:
                settingsSync.setKeyOperatingNotify(str2);
                return;
            case 20:
                settingsSync.setKeyAnnouncementVoice(str2);
                return;
            case 21:
                settingsSync.setKeyRearScreenChildLock(str2);
                return;
            default:
                Logs.d("XpAccountManager-xld save no key:" + str);
                return;
        }
    }

    private ICarSettingsSync getSettingsSync() {
        try {
            return this.mSync.getCarSettingsSync();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSyncValue(String str) {
        String str2 = "";
        if (this.mSync == null) {
            return "";
        }
        ICarSettingsSync settingsSync = getSettingsSync();
        if (settingsSync == null) {
            Logs.d("XpAccountManager-xld settingsync null");
            return "";
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -2093427174:
                if (str.equals("KEY_ANNOUNCEMENT_VOICE")) {
                    c = 20;
                    break;
                }
                break;
            case -1905148246:
                if (str.equals("KEY_XP_MUSIC_NOTIFY")) {
                    c = '\f';
                    break;
                }
                break;
            case -1888864757:
                if (str.equals("KEY_METER_AUTO_BRIGHTNESS")) {
                    c = '\n';
                    break;
                }
                break;
            case -1730496710:
                if (str.equals("KEY_VALET_PARKING_ASSISTANCE_NOTIFY")) {
                    c = 17;
                    break;
                }
                break;
            case -1701946266:
                if (str.equals("KEY_REAR_SCREEN_CHILD_LOCK")) {
                    c = 21;
                    break;
                }
                break;
            case -1441169423:
                if (str.equals("KEY_FONT_SIZE")) {
                    c = 0;
                    break;
                }
                break;
            case -1291095493:
                if (str.equals("KEY_DOOR_OPEN")) {
                    c = 6;
                    break;
                }
                break;
            case -565668568:
                if (str.equals("KEY_XPILOT_NOTIFY")) {
                    c = 16;
                    break;
                }
                break;
            case -403953755:
                if (str.equals("KEY_DARK_LIGHT_ADAPTATION")) {
                    c = '\t';
                    break;
                }
                break;
            case -393880432:
                if (str.equals("meter_volume")) {
                    c = 3;
                    break;
                }
                break;
            case -241725462:
                if (str.equals("is_24_hour_format")) {
                    c = 1;
                    break;
                }
                break;
            case -218063135:
                if (str.equals("KEY_AUTO_BRIGHTNESS")) {
                    c = '\b';
                    break;
                }
                break;
            case -146741268:
                if (str.equals("KEY_OTA_NOTIFY")) {
                    c = '\r';
                    break;
                }
                break;
            case -103766771:
                if (str.equals("main_driver_exclusive")) {
                    c = 4;
                    break;
                }
                break;
            case 185962195:
                if (str.equals("KEY_VEHICLE_CHECK_NOTIFY")) {
                    c = 18;
                    break;
                }
                break;
            case 410132459:
                if (str.equals(KEY_VOLUME_DOWN_NAVI_MEDIA)) {
                    c = 5;
                    break;
                }
                break;
            case 461953654:
                if (str.equals("KEY_CAR_CONTROL_NOTIFY")) {
                    c = 15;
                    break;
                }
                break;
            case 661526211:
                if (str.equals("system_sound_enable")) {
                    c = 2;
                    break;
                }
                break;
            case 666518355:
                if (str.equals("KEY_R_STALL")) {
                    c = 7;
                    break;
                }
                break;
            case 668594727:
                if (str.equals("KEY_OPERATING_NOTIFY")) {
                    c = 19;
                    break;
                }
                break;
            case 1280194383:
                if (str.equals("KEY_METER_DARK_LIGHT_ADAPTATION")) {
                    c = 11;
                    break;
                }
                break;
            case 1754560517:
                if (str.equals("KEY_APP_STORE_NOTIFY")) {
                    c = 14;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str2 = settingsSync.getFontScale("");
                break;
            case 1:
                str2 = settingsSync.get24Format("");
                break;
            case 2:
                str2 = settingsSync.getSystemSoundEnabled("");
                break;
            case 3:
                str2 = settingsSync.getMeterVolume("");
                break;
            case 4:
                str2 = settingsSync.getMainDriverExclusive("");
                break;
            case 5:
                str2 = settingsSync.getKeyLbsMediumVoice("");
                break;
            case 6:
                str2 = settingsSync.getVolumeDownWithDoorOpen("");
                break;
            case 7:
                str2 = settingsSync.getVolumeDownInReverseMode("");
                break;
            case '\b':
                str2 = settingsSync.getAutoBrightnessEnable("");
                break;
            case '\t':
                str2 = settingsSync.getDarkLightAdaptation("");
                break;
            case '\n':
                str2 = settingsSync.getMeterAutoBrightness("");
                break;
            case 11:
                str2 = settingsSync.getMeterDarkLightAdaptation("");
                break;
            case '\f':
                str2 = settingsSync.getKeyXpMusicNotify("");
                break;
            case '\r':
                str2 = settingsSync.getKeyOtaNotify("");
                break;
            case 14:
                str2 = settingsSync.getKeyAppStoreNotify("");
                break;
            case 15:
                str2 = settingsSync.getKeyCarControlNotify("");
                break;
            case 16:
                str2 = settingsSync.getKeyXpilotNotify("");
                break;
            case 17:
                str2 = settingsSync.getKeyValetParkingAssistanceNotify("");
                break;
            case 18:
                str2 = settingsSync.getKeyVehicleCheckNotify("");
                break;
            case 19:
                str2 = settingsSync.getKeyOperatingNotify("");
                break;
            case 20:
                str2 = settingsSync.getKeyAnnouncementVoice("");
                break;
            case 21:
                str2 = settingsSync.getKeyRearScreenChildLock("");
                break;
            default:
                Logs.d("XpAccountManager-xld get no key:" + str);
                break;
        }
        Logs.d("XpAccountManager-xld get key = " + str + ", value = " + str2);
        return str2;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnSyncChanged(final long j, final SyncType syncType) {
        Logs.d("XpAccountManager-xld OnSyncChanged uid=" + j + " SyncType:" + syncType);
        if (isGuest(j) && !CarFunction.isSupportGuestGroup()) {
            Logs.d("XpAccountManager-xld OnSyncChanged uid=-1 return");
        } else {
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager.1
                @Override // java.lang.Runnable
                public void run() {
                    XpAccountManager.this.syncChangedData(j, syncType);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void syncChangedData(long j, SyncType syncType) {
        char c;
        char c2;
        Map<String, String> uidSyncDbOnWorkThread;
        XpAccountManager xpAccountManager = this;
        if (syncType == SyncType.ACCOUNT_CHANGED && (uidSyncDbOnWorkThread = Tools.getSyncTransferTools().getUidSyncDbOnWorkThread(j)) != null) {
            for (Map.Entry<String, String> entry : uidSyncDbOnWorkThread.entrySet()) {
                Logs.d("XpAccountManager-xld offline data key:" + entry.getKey() + " value:" + entry.getValue());
                xpAccountManager.save(entry.getKey(), entry.getValue());
            }
        }
        Iterator<String> it = sSyncItems.iterator();
        boolean z = true;
        while (it.hasNext()) {
            String next = it.next();
            Logs.d("XpAccountManager-xld settings-account syncItem:" + next);
            String syncValue = xpAccountManager.getSyncValue(next);
            Iterator<String> it2 = it;
            boolean z2 = z;
            if (!"".equals(syncValue)) {
                switch (next.hashCode()) {
                    case -2093427174:
                        if (next.equals("KEY_ANNOUNCEMENT_VOICE")) {
                            c2 = 20;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1905148246:
                        if (next.equals("KEY_XP_MUSIC_NOTIFY")) {
                            c2 = '\f';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1888864757:
                        if (next.equals("KEY_METER_AUTO_BRIGHTNESS")) {
                            c2 = '\n';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1730496710:
                        if (next.equals("KEY_VALET_PARKING_ASSISTANCE_NOTIFY")) {
                            c2 = 17;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1701946266:
                        if (next.equals("KEY_REAR_SCREEN_CHILD_LOCK")) {
                            c2 = 21;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1441169423:
                        if (next.equals("KEY_FONT_SIZE")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1291095493:
                        if (next.equals("KEY_DOOR_OPEN")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -565668568:
                        if (next.equals("KEY_XPILOT_NOTIFY")) {
                            c2 = 16;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -403953755:
                        if (next.equals("KEY_DARK_LIGHT_ADAPTATION")) {
                            c2 = '\t';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -393880432:
                        if (next.equals("meter_volume")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -241725462:
                        if (next.equals("is_24_hour_format")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -218063135:
                        if (next.equals("KEY_AUTO_BRIGHTNESS")) {
                            c2 = '\b';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -146741268:
                        if (next.equals("KEY_OTA_NOTIFY")) {
                            c2 = '\r';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -103766771:
                        if (next.equals("main_driver_exclusive")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 185962195:
                        if (next.equals("KEY_VEHICLE_CHECK_NOTIFY")) {
                            c2 = 18;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 410132459:
                        if (next.equals(KEY_VOLUME_DOWN_NAVI_MEDIA)) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 461953654:
                        if (next.equals("KEY_CAR_CONTROL_NOTIFY")) {
                            c2 = 15;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 661526211:
                        if (next.equals("system_sound_enable")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 666518355:
                        if (next.equals("KEY_R_STALL")) {
                            c2 = 7;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 668594727:
                        if (next.equals("KEY_OPERATING_NOTIFY")) {
                            c2 = 19;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1280194383:
                        if (next.equals("KEY_METER_DARK_LIGHT_ADAPTATION")) {
                            c2 = 11;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1754560517:
                        if (next.equals("KEY_APP_STORE_NOTIFY")) {
                            c2 = 14;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        xpAccountManager = this;
                        try {
                            xpAccountManager.mFontSize = Float.valueOf(xpAccountManager.switchFontSize(Integer.valueOf(syncValue).intValue()));
                            Logs.d("XpAccountManager-xld setFontSize = " + xpAccountManager.mFontSize);
                            xpAccountManager.mDataRepository.setFontSize(xpAccountManager.mContext, xpAccountManager.mFontSize.floatValue(), false);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    case 1:
                        xpAccountManager = this;
                        xpAccountManager.mIs24HourFormat = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld set24HourFormat = " + xpAccountManager.mIs24HourFormat);
                        xpAccountManager.mDataRepository.set24HourFormat(xpAccountManager.mContext, xpAccountManager.mIs24HourFormat.booleanValue(), false);
                        break;
                    case 2:
                        xpAccountManager = this;
                        xpAccountManager.mSystemSoundEnable = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld mSystemSoundEnable = " + xpAccountManager.mSystemSoundEnable);
                        xpAccountManager.mSoundManager.setSystemSoundEnable(xpAccountManager.mSystemSoundEnable.booleanValue(), false, false);
                        break;
                    case 3:
                        xpAccountManager = this;
                        xpAccountManager.mSafetyVolume = Integer.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setSafetyVolume = " + xpAccountManager.mSafetyVolume);
                        xpAccountManager.mSoundManager.setSafetyVolume(xpAccountManager.mSafetyVolume.intValue(), false);
                        break;
                    case 4:
                        xpAccountManager = this;
                        if (CarConfigHelper.hasMainDriverVIP()) {
                            xpAccountManager.mMainDriverExclusive = Integer.valueOf(syncValue);
                            Logs.d("XpAccountManager-xld setMainDriverExclusive = " + xpAccountManager.mMainDriverExclusive);
                            xpAccountManager.mSoundManager.setMainDriverExclusive(xpAccountManager.mMainDriverExclusive.intValue(), false, true, false);
                            break;
                        }
                        break;
                    case 5:
                        xpAccountManager = this;
                        xpAccountManager.mVolumeDownWithNavigating = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setVolumeDownWithNavigating = " + xpAccountManager.mVolumeDownWithNavigating);
                        xpAccountManager.mSoundManager.setVolumeDownWithNavigating(xpAccountManager.mVolumeDownWithNavigating.booleanValue(), false);
                        xpAccountManager.mVolumeDownWithDoorOpen = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setVolumeDownWithDoorOpen = " + xpAccountManager.mVolumeDownWithDoorOpen);
                        xpAccountManager.mSoundManager.setVolumeDownWithDoorOpen(xpAccountManager.mVolumeDownWithDoorOpen.booleanValue(), false);
                        break;
                    case 6:
                        xpAccountManager = this;
                        xpAccountManager.mVolumeDownWithDoorOpen = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setVolumeDownWithDoorOpen = " + xpAccountManager.mVolumeDownWithDoorOpen);
                        xpAccountManager.mSoundManager.setVolumeDownWithDoorOpen(xpAccountManager.mVolumeDownWithDoorOpen.booleanValue(), false);
                        break;
                    case 7:
                        xpAccountManager = this;
                        xpAccountManager.mVolumeDownWithGearR = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setVolumeDownInReverseMode = " + xpAccountManager.mVolumeDownWithGearR);
                        xpAccountManager.mSoundManager.setVolumeDownInReverseMode(xpAccountManager.mVolumeDownWithGearR.booleanValue(), false);
                        break;
                    case '\b':
                        xpAccountManager = this;
                        if (!Utils.isPowerSavingMode()) {
                            xpAccountManager.mAutoBrightness = Boolean.valueOf(syncValue);
                            if (CarConfigHelper.hasAutoBrightness()) {
                                Logs.d("XpAccountManager-xldxpu setAdaptiveBrightness = " + xpAccountManager.mAutoBrightness);
                                if (CarFunction.isNonSelfPageUI()) {
                                    xpAccountManager.mDataRepository.setMainAdaptiveBrightness(xpAccountManager.mAutoBrightness.booleanValue(), false);
                                } else {
                                    xpAccountManager.mDataRepository.setAdaptiveBrightness(xpAccountManager.mContext, xpAccountManager.mAutoBrightness.booleanValue(), false);
                                }
                            }
                            break;
                        } else {
                            return;
                        }
                    case '\t':
                        xpAccountManager = this;
                        xpAccountManager.mDarkLightAdaptation = Boolean.valueOf(syncValue);
                        if (!CarConfigHelper.hasAutoBrightness()) {
                            Logs.d("XpAccountManager-xld setAdaptiveBrightness = " + xpAccountManager.mDarkLightAdaptation);
                            if (CarFunction.isNonSelfPageUI()) {
                                xpAccountManager.mDataRepository.setMainAdaptiveBrightness(xpAccountManager.mDarkLightAdaptation.booleanValue(), false);
                            } else {
                                xpAccountManager.mDataRepository.setAdaptiveBrightness(xpAccountManager.mContext, xpAccountManager.mDarkLightAdaptation.booleanValue(), false);
                            }
                        }
                        break;
                    case '\n':
                        xpAccountManager = this;
                        if (!Utils.isPowerSavingMode()) {
                            xpAccountManager.mMeterAutoBrightness = Boolean.valueOf(syncValue);
                            if (CarConfigHelper.hasAutoBrightness()) {
                                Logs.d("XpAccountManager-xldxpu setMeterAdaptiveBrightness = " + xpAccountManager.mMeterAutoBrightness);
                                xpAccountManager.mDataRepository.setMeterAdaptiveBrightness(xpAccountManager.mMeterAutoBrightness.booleanValue(), false);
                            }
                            break;
                        } else {
                            return;
                        }
                    case 11:
                        xpAccountManager = this;
                        xpAccountManager.mMeterDarkLightAdaptation = Boolean.valueOf(syncValue);
                        if (!CarConfigHelper.hasAutoBrightness()) {
                            Logs.d("XpAccountManager-xld setAdaptiveBrightness = " + xpAccountManager.mMeterDarkLightAdaptation);
                            xpAccountManager.mDataRepository.setMeterAdaptiveBrightness(xpAccountManager.mMeterDarkLightAdaptation.booleanValue(), false);
                        }
                        break;
                    case '\f':
                        xpAccountManager = this;
                        xpAccountManager.mMusicNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setMusicNotify = " + xpAccountManager.mMusicNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.MUSIC_NOTIFICATION_ENABLED, xpAccountManager.mMusicNotify.booleanValue());
                        break;
                    case '\r':
                        xpAccountManager = this;
                        xpAccountManager.mOtaNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setOtaNotify = " + xpAccountManager.mOtaNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.OTA_NOTIFICATION_ENABLED, xpAccountManager.mOtaNotify.booleanValue());
                        break;
                    case 14:
                        xpAccountManager = this;
                        xpAccountManager.mAppStoreNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setAppStoreNotify = " + xpAccountManager.mAppStoreNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.APPSTORE_NOTIFICATION_ENABLED, xpAccountManager.mAppStoreNotify.booleanValue());
                        break;
                    case 15:
                        xpAccountManager = this;
                        xpAccountManager.mCarControlNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setCarControlNotify = " + xpAccountManager.mCarControlNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.CARCONTROL_NOTIFICATION_ENABLED, xpAccountManager.mCarControlNotify.booleanValue());
                        break;
                    case 16:
                        xpAccountManager = this;
                        xpAccountManager.mXpilotNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setXpilotNotify = " + xpAccountManager.mXpilotNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.ASSIST_DRIVING_NOTIFICATION_ENABLED, xpAccountManager.mXpilotNotify.booleanValue());
                        break;
                    case 17:
                        xpAccountManager = this;
                        xpAccountManager.mParkingNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setParkingNotify = " + xpAccountManager.mParkingNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.MEMORY_PARKING_NOTIFICATION_ENABLED, xpAccountManager.mParkingNotify.booleanValue());
                        break;
                    case 18:
                        xpAccountManager = this;
                        xpAccountManager.mVehicleCheckNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setVehicleCheckNotify = " + xpAccountManager.mVehicleCheckNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.VEHICLE_DETECTION_NOTIFICATION_ENABLED, xpAccountManager.mVehicleCheckNotify.booleanValue());
                        break;
                    case 19:
                        xpAccountManager = this;
                        xpAccountManager.mOperatingNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setOperatingNotify = " + xpAccountManager.mOperatingNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.OPERATING_ACTIVITIES_NOTIFICATION_ENABLED, xpAccountManager.mOperatingNotify.booleanValue());
                        break;
                    case 20:
                        xpAccountManager = this;
                        xpAccountManager.mTtsBroadcastNotify = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld setTtsBroadcastNotify = " + xpAccountManager.mTtsBroadcastNotify);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.TTS_BROADCAST_NOTIFICATION_ENABLED, xpAccountManager.mTtsBroadcastNotify.booleanValue());
                        break;
                    case 21:
                        if (!CarConfigHelper.hasRearScreen()) {
                            Logs.d("XpAccountManager-xld no rear screen!");
                            return;
                        }
                        xpAccountManager = this;
                        xpAccountManager.mRearScreenChildLock = Boolean.valueOf(syncValue);
                        Logs.d("XpAccountManager-xld mRearScreenChildLock = " + xpAccountManager.mRearScreenChildLock);
                        xpAccountManager.mDataRepository.setSettingProvider(xpAccountManager.mContext, XPSettingsConfig.REAR_SCREEN_LOCK, xpAccountManager.mRearScreenChildLock.booleanValue());
                        break;
                    default:
                        xpAccountManager = this;
                        break;
                }
                z = false;
            } else {
                xpAccountManager = this;
                switch (next.hashCode()) {
                    case -2093427174:
                        if (next.equals("KEY_ANNOUNCEMENT_VOICE")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1905148246:
                        if (next.equals("KEY_XP_MUSIC_NOTIFY")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1888864757:
                        if (next.equals("KEY_METER_AUTO_BRIGHTNESS")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1730496710:
                        if (next.equals("KEY_VALET_PARKING_ASSISTANCE_NOTIFY")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1701946266:
                        if (next.equals("KEY_REAR_SCREEN_CHILD_LOCK")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1441169423:
                        if (next.equals("KEY_FONT_SIZE")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1291095493:
                        if (next.equals("KEY_DOOR_OPEN")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -565668568:
                        if (next.equals("KEY_XPILOT_NOTIFY")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case -403953755:
                        if (next.equals("KEY_DARK_LIGHT_ADAPTATION")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -393880432:
                        if (next.equals("meter_volume")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -241725462:
                        if (next.equals("is_24_hour_format")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -218063135:
                        if (next.equals("KEY_AUTO_BRIGHTNESS")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -146741268:
                        if (next.equals("KEY_OTA_NOTIFY")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case -103766771:
                        if (next.equals("main_driver_exclusive")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 185962195:
                        if (next.equals("KEY_VEHICLE_CHECK_NOTIFY")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 410132459:
                        if (next.equals(KEY_VOLUME_DOWN_NAVI_MEDIA)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 461953654:
                        if (next.equals("KEY_CAR_CONTROL_NOTIFY")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 661526211:
                        if (next.equals("system_sound_enable")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 666518355:
                        if (next.equals("KEY_R_STALL")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 668594727:
                        if (next.equals("KEY_OPERATING_NOTIFY")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1280194383:
                        if (next.equals("KEY_METER_DARK_LIGHT_ADAPTATION")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1754560517:
                        if (next.equals("KEY_APP_STORE_NOTIFY")) {
                            c = 14;
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
                        xpAccountManager.save(KEY_VOLUME_DOWN_NAVI_MEDIA, String.valueOf(getVolumeDownWithNavigating()));
                        break;
                    case 1:
                        xpAccountManager.save("KEY_R_STALL", String.valueOf(getVolumeDownWithGearR()));
                        break;
                    case 2:
                        xpAccountManager.save("KEY_DOOR_OPEN", String.valueOf(getVolumeDownWithDoorOpen()));
                        break;
                    case 3:
                        if (CarConfigHelper.hasMainDriverVIP()) {
                            xpAccountManager.save("main_driver_exclusive", String.valueOf(getMainDriverExclusive()));
                            break;
                        }
                        break;
                    case 4:
                        xpAccountManager.save("meter_volume", String.valueOf(getSafetyVolume()));
                        break;
                    case 5:
                        xpAccountManager.save("system_sound_enable", String.valueOf(getSystemSoundEnable()));
                        break;
                    case 6:
                        xpAccountManager.save("is_24_hour_format", String.valueOf(is24HourFormat()));
                        break;
                    case 7:
                        xpAccountManager.save("KEY_FONT_SIZE", String.valueOf(xpAccountManager.saveFontSwitch(getFontSize())));
                        break;
                    case '\b':
                        if (Utils.isPowerSavingMode()) {
                            return;
                        }
                        if (CarConfigHelper.hasAutoBrightness()) {
                            xpAccountManager.save("KEY_AUTO_BRIGHTNESS", String.valueOf(getAutoBrightness()));
                            break;
                        }
                        break;
                    case '\t':
                        if (!CarConfigHelper.hasAutoBrightness()) {
                            xpAccountManager.save("KEY_DARK_LIGHT_ADAPTATION", String.valueOf(getAutoBrightness()));
                            break;
                        }
                        break;
                    case '\n':
                        if (Utils.isPowerSavingMode()) {
                            return;
                        }
                        if (CarConfigHelper.hasAutoBrightness()) {
                            xpAccountManager.save("KEY_METER_AUTO_BRIGHTNESS", String.valueOf(getMeterAutoBrightness()));
                            break;
                        }
                        break;
                    case 11:
                        if (!CarConfigHelper.hasAutoBrightness()) {
                            xpAccountManager.save("KEY_METER_DARK_LIGHT_ADAPTATION", String.valueOf(getMeterDarkLightAdaptation()));
                            break;
                        }
                        break;
                    case '\f':
                        xpAccountManager.save("KEY_XP_MUSIC_NOTIFY", String.valueOf(getMusicNotify()));
                        break;
                    case '\r':
                        xpAccountManager.save("KEY_OTA_NOTIFY", String.valueOf(getOtaNotify()));
                        break;
                    case 14:
                        xpAccountManager.save("KEY_APP_STORE_NOTIFY", String.valueOf(getAppStoreNotify()));
                        break;
                    case 15:
                        xpAccountManager.save("KEY_CAR_CONTROL_NOTIFY", String.valueOf(getCarControlNotify()));
                        break;
                    case 16:
                        xpAccountManager.save("KEY_XPILOT_NOTIFY", String.valueOf(getXpilotNotify()));
                        break;
                    case 17:
                        xpAccountManager.save("KEY_VALET_PARKING_ASSISTANCE_NOTIFY", String.valueOf(getParkingNotify()));
                        break;
                    case 18:
                        xpAccountManager.save("KEY_VEHICLE_CHECK_NOTIFY", String.valueOf(getVehicleCheckNotify()));
                        break;
                    case 19:
                        xpAccountManager.save("KEY_OPERATING_NOTIFY", String.valueOf(getOperatingNotify()));
                        break;
                    case 20:
                        xpAccountManager.save("KEY_ANNOUNCEMENT_VOICE", String.valueOf(getTtsBroadcastNotify()));
                        break;
                    case 21:
                        xpAccountManager.save("KEY_REAR_SCREEN_CHILD_LOCK", String.valueOf(getScreenChildLock()));
                        break;
                }
                z = z2;
            }
            it = it2;
        }
        if (z) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager.2
            @Override // java.lang.Runnable
            public void run() {
                DisplayServerManager.getInstance().onAccountChanged();
                SoundServerManager.getInstance().onAccountChanged();
                AboutSysServerManager.getInstance().onAccountChanged();
            }
        });
    }

    public float getFontSize() {
        if (this.mFontSize == null) {
            this.mFontSize = Float.valueOf(this.mDataRepository.getCurrentFontSize(this.mContext));
        }
        return this.mFontSize.floatValue();
    }

    public boolean is24HourFormat() {
        if (this.mIs24HourFormat == null) {
            this.mIs24HourFormat = Boolean.valueOf(DateFormat.is24HourFormat(this.mContext.getApplicationContext()));
        }
        return this.mIs24HourFormat.booleanValue();
    }

    public boolean getSystemSoundEnable() {
        if (this.mSystemSoundEnable == null) {
            this.mSystemSoundEnable = Boolean.valueOf(this.mSoundManager.isSystemSoundEnabled());
        }
        return this.mSystemSoundEnable.booleanValue();
    }

    public int getSafetyVolume() {
        if (this.mSafetyVolume == null) {
            this.mSafetyVolume = Integer.valueOf(this.mSoundManager.getSafetyVolume());
        }
        return this.mSafetyVolume.intValue();
    }

    public int getMeterVolume() {
        return getSafetyVolume();
    }

    public int getMainDriverExclusive() {
        if (this.mMainDriverExclusive == null) {
            this.mMainDriverExclusive = Integer.valueOf(this.mSoundManager.getMainDriverExclusive());
        }
        return this.mMainDriverExclusive.intValue();
    }

    public boolean getVolumeDownWithDoorOpen() {
        if (this.mVolumeDownWithDoorOpen == null) {
            this.mVolumeDownWithDoorOpen = Boolean.valueOf(this.mSoundManager.getVolumeDownWithDoorOpen());
        }
        return this.mVolumeDownWithDoorOpen.booleanValue();
    }

    public boolean getVolumeDownWithGearR() {
        if (this.mVolumeDownWithGearR == null) {
            this.mVolumeDownWithGearR = Boolean.valueOf(this.mSoundManager.getVolumeDownInReverseMode());
        }
        return this.mVolumeDownWithGearR.booleanValue();
    }

    public boolean getVolumeDownWithNavigating() {
        if (this.mVolumeDownWithNavigating == null) {
            this.mVolumeDownWithNavigating = Boolean.valueOf(this.mSoundManager.isDecreaseVolNavigating());
        }
        return this.mVolumeDownWithNavigating.booleanValue();
    }

    public boolean getAutoBrightness() {
        if (this.mAutoBrightness == null) {
            this.mAutoBrightness = Boolean.valueOf(CarFunction.isNonSelfPageUI() ? this.mDataRepository.isMainAutoBrightnessModeEnable() : this.mDataRepository.isAdptiveBrightness(this.mContext));
        }
        return this.mAutoBrightness.booleanValue();
    }

    public boolean getDarkLightAdaptation() {
        if (this.mDarkLightAdaptation == null) {
            this.mDarkLightAdaptation = Boolean.valueOf(CarFunction.isNonSelfPageUI() ? this.mDataRepository.isMainAutoBrightnessModeEnable() : this.mDataRepository.isAdptiveBrightness(this.mContext));
        }
        return this.mDarkLightAdaptation.booleanValue();
    }

    public boolean getMeterAutoBrightness() {
        if (this.mMeterAutoBrightness == null) {
            this.mMeterAutoBrightness = Boolean.valueOf(this.mDataRepository.isMeterAutoBrightnessModeEnable());
        }
        return this.mMeterAutoBrightness.booleanValue();
    }

    public boolean getMeterDarkLightAdaptation() {
        if (this.mMeterDarkLightAdaptation == null) {
            this.mMeterDarkLightAdaptation = Boolean.valueOf(this.mDataRepository.isMeterAutoBrightnessModeEnable());
        }
        return this.mMeterDarkLightAdaptation.booleanValue();
    }

    public boolean getMusicNotify() {
        if (this.mMusicNotify == null) {
            this.mMusicNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.MUSIC_NOTIFICATION_ENABLED, true));
        }
        return this.mMusicNotify.booleanValue();
    }

    public void setMusicNotify(boolean z) {
        Logs.d("XpAccountManager-xld setMusicNotify = " + z);
        saveMusicNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.MUSIC_NOTIFICATION_ENABLED, this.mMusicNotify.booleanValue());
    }

    public boolean getOtaNotify() {
        if (this.mOtaNotify == null) {
            this.mOtaNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.OTA_NOTIFICATION_ENABLED, true));
        }
        return this.mOtaNotify.booleanValue();
    }

    public void setOtaNotify(boolean z) {
        Logs.d("XpAccountManager-xld setOtaNotify = " + z);
        saveOtaNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.OTA_NOTIFICATION_ENABLED, this.mOtaNotify.booleanValue());
    }

    public boolean getAppStoreNotify() {
        if (this.mAppStoreNotify == null) {
            this.mAppStoreNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.APPSTORE_NOTIFICATION_ENABLED, true));
        }
        return this.mAppStoreNotify.booleanValue();
    }

    public void setAppStoreNotify(boolean z) {
        Logs.d("XpAccountManager-xld setAppStoreNotify = " + z);
        saveAppStoreNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.APPSTORE_NOTIFICATION_ENABLED, this.mAppStoreNotify.booleanValue());
    }

    public boolean getCarControlNotify() {
        if (this.mCarControlNotify == null) {
            this.mCarControlNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.CARCONTROL_NOTIFICATION_ENABLED, true));
        }
        return this.mCarControlNotify.booleanValue();
    }

    public void setCarControlNotify(boolean z) {
        Logs.d("XpAccountManager-xld setCarControlNotify = " + z);
        saveCarControlNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.CARCONTROL_NOTIFICATION_ENABLED, this.mCarControlNotify.booleanValue());
    }

    public boolean getXpilotNotify() {
        if (this.mXpilotNotify == null) {
            this.mXpilotNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.ASSIST_DRIVING_NOTIFICATION_ENABLED, true));
        }
        return this.mXpilotNotify.booleanValue();
    }

    public void setXpilotNotify(boolean z) {
        Logs.d("XpAccountManager-xld setXpilotNotify = " + z);
        saveXpilotNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.ASSIST_DRIVING_NOTIFICATION_ENABLED, this.mXpilotNotify.booleanValue());
    }

    public boolean getParkingNotify() {
        if (this.mParkingNotify == null) {
            this.mParkingNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.MEMORY_PARKING_NOTIFICATION_ENABLED, true));
        }
        return this.mParkingNotify.booleanValue();
    }

    public void setParkingNotify(boolean z) {
        Logs.d("XpAccountManager-xld setParkingNotify = " + z);
        saveParkingNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.MEMORY_PARKING_NOTIFICATION_ENABLED, this.mParkingNotify.booleanValue());
    }

    public boolean getVehicleCheckNotify() {
        if (this.mVehicleCheckNotify == null) {
            this.mVehicleCheckNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.VEHICLE_DETECTION_NOTIFICATION_ENABLED, true));
        }
        return this.mVehicleCheckNotify.booleanValue();
    }

    public void setVehicleCheckNotify(boolean z) {
        Logs.d("XpAccountManager-xld setVehicleCheckNotify = " + z);
        saveVehicleCheckNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.VEHICLE_DETECTION_NOTIFICATION_ENABLED, this.mVehicleCheckNotify.booleanValue());
    }

    public boolean getOperatingNotify() {
        if (this.mOperatingNotify == null) {
            this.mOperatingNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.OPERATING_ACTIVITIES_NOTIFICATION_ENABLED, true));
        }
        return this.mOperatingNotify.booleanValue();
    }

    public void setOperatingNotify(boolean z) {
        Logs.d("XpAccountManager-xld setVehicleCheckNotify = " + z);
        saveOperatingNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.OPERATING_ACTIVITIES_NOTIFICATION_ENABLED, this.mOperatingNotify.booleanValue());
    }

    public boolean getTtsBroadcastNotify() {
        if (this.mTtsBroadcastNotify == null) {
            this.mTtsBroadcastNotify = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.TTS_BROADCAST_NOTIFICATION_ENABLED, true));
        }
        return this.mTtsBroadcastNotify.booleanValue();
    }

    public boolean getScreenChildLock() {
        if (this.mRearScreenChildLock == null) {
            this.mRearScreenChildLock = Boolean.valueOf(this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.REAR_SCREEN_LOCK, false));
        }
        return this.mRearScreenChildLock.booleanValue();
    }

    public void setTtsBroadcastNotify(boolean z) {
        Logs.d("XpAccountManager-xld setVehicleCheckNotify = " + z);
        saveTtsBroadcastNotify(z, true);
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.TTS_BROADCAST_NOTIFICATION_ENABLED, this.mTtsBroadcastNotify.booleanValue());
    }
}
