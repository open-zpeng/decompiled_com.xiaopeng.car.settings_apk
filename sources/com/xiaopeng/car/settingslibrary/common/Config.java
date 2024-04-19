package com.xiaopeng.car.settingslibrary.common;

import android.graphics.Point;
import android.media.AudioManager;
import android.os.Build;
import android.os.SystemProperties;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.xvs.xid.XId;
/* loaded from: classes.dex */
public class Config {
    public static final String ACCOUNT_PKGNAME = "com.xiaopeng.caraccount";
    public static final String ACTION_CLEAN_APP_CACHES = "com.xiaopeng.intent.action.CLEAN_APP_CACHES";
    public static final String ACTION_CLEAN_CAMERA_APP_CACHES = "com.xiaopeng.intent.action.CLEAN_CAMERA_APP_CACHES";
    public static final String ACTION_CONNECT_XPAUTO = "com.xiaopeng.intent.action.CONNECT_XPAUTO";
    public static final String ACTION_DEVICE_STORAGE_AUTO = "com.xiaopeng.intent.action.ACTION_DEVICE_STORAGE_AUTO";
    public static final String ACTION_DEVICE_STORAGE_FULL = "com.xiaopeng.intent.action.ACTION_DEVICE_STORAGE_FULL";
    public static final String ACTION_DEVICE_STORAGE_LOW = "com.xiaopeng.intent.action.ACTION_DEVICE_STORAGE_LOW";
    public static final String ACTION_DEVICE_STORAGE_OK = "com.xiaopeng.intent.action.ACTION_DEVICE_STORAGE_OK";
    public static final String ACTION_OTA_VERSION_CHANGED = "com.xiaopeng.intent.action.ota.CAMPAIGN_CHANGED";
    public static final String ACTION_PAIRING_REQUEST_CANCEL = "com.xiaopeng.intent.action.cancel_pairing_request";
    public static final String ACTION_REQUEST_CLEAN_CACHES = "com.xiaopeng.intent.action.REQUEST_CLEAN_CACHES";
    public static final String ACTION_SCREEN_STATUS_CHANGE = "com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE";
    public static final String ACTION_SCREEN_STATUS_CHANGE_DEVICE_EXTRA = "device";
    public static final String ACTION_SCREEN_STATUS_CHANGE_STATUS_EXTRA = "status";
    public static final String ACTION_STORAGE_OPEN = "com.xiaopeng.intent.action.ACTION_STORAGE_OPEN";
    public static final int AI_MSG_ID_FOR_STORE = 29001;
    public static final String API_SECRET = "dd004c4f0e954d529ec778e69f674dfe";
    private static final String APPID_D20 = "xp_system_setting_car";
    public static final String APP_ID;
    public static final String AUTH_MODE_ACTION = "com.xiaopeng.intent.action.OPEN_QUIT_AUTH_MODE_DIALOG";
    public static final String AUTH_MODE_ACTION_CLOSE = "com.xiaopeng.intent.action.CLOSE_QUIT_AUTH_MODE_DIALOG";
    public static final String AUTH_MODE_EXTRA_KEY = "AUTO_CLOSE_TIME";
    public static final String AUTO_POWER_OFF_ACTION = "com.xiaopeng.intent.action.AUTO_POWER_OFF";
    public static final String AUTO_POWER_OFF_CLOSE_ACTION = "com.xiaopeng.intent.action.AUTO_POWER_OFF_CLOSE";
    public static final String AUTO_POWER_OFF_TIME_ACTION = "com.xiaopeng.intent.action.AUTO_POWER_OFF_TIME";
    public static final int AVAS_STREAM;
    public static final String BACK_SEAT_BLUETOOTH = "com.xiaopeng.intent.action.POPUP_BACK_SEAT_BLUETOOTH";
    public static final int BLUETOOTH_NAME_TOAST_LIMIT = 15;
    public static final String BRIGHTNESS_ADPTIVE_RESTORE = "brightness_adptive_restore";
    public static final int BRIGHTNESS_DETAL_SPEECH = 10;
    public static final int BRIGHTNESS_TO_MAX_PROGRESS = 100;
    public static final int BRIGHTNESS_TO_MAX_VALUE = 255;
    public static final int BRIGHTNESS_TO_MIN_PROGRESS = 1;
    public static final int BRIGHTNESS_TO_MIN_VALUE = 1;
    public static final String BURIED_POINT_LAST_UPLOAD_TIME = "bi_last_upload_time";
    public static final int CALL_STREAM = 0;
    public static final String CAR_CALL_ADVANCED_RESTORE = "car_call_advanced_restore";
    public static final String CLEAN_MODE_FINISH_ACTION = "com.xiaopeng.intent.action.CLEAN_MODE_FINISH";
    public static final String CLOSE_POPUP_DIALOG = "com.xiaopeng.intent.action.POPUP_DIALOG_CLOSE";
    public static final String CONFIG_ENABLE = "1";
    public static String CONFIG_SERVER_HOST = null;
    public static final String CONFIG_SHOW = "1";
    public static final String CO_DRIVER_BLUETOOTH = "com.xiaopeng.intent.action.POPUP_PSN_BLUETOOTH";
    public static final String D21_REMOTE_PARKING = "d21_remote_parking";
    public static final int DARK_BRIGHTNESS_DEFAULT = 20;
    public static final String DYNA_SOUND_EFFECT_EQUALIZER_MODE = "dyna_sound_effect_equalizer_mode";
    public static final String DYNA_SOUND_EFFECT_EQUALIZER_SWITCH = "dyna_sound_effect_equalizer_switch";
    public static final String DYNA_SOUND_EFFECT_EQUALIZER_VALUE1 = "dyna_sound_effect_equalizer_value1";
    public static final String DYNA_SOUND_EFFECT_EQUALIZER_VALUE2 = "dyna_sound_effect_equalizer_value2";
    public static final String EMERGENCY_IG_OFFACTION = "com.xiaopeng.intent.action.emergency.igoff";
    public static final String EQUALIZER_VALUE_SPLIT = ",";
    public static final int EXIT_TIME_SECONDS = 3;
    public static final String EXTRA_AUTO_POWER_OFF = "auto_power_off_time";
    public static final String EXTRA_POPUP_FINISH = "activity_name";
    public static final String EXTRA_POPUP_SCREEN = "popup_screen";
    public static final String EXTRA_POPUP_SOURCE = "popup_source";
    public static final String EXTRA_WHICH_DIALOG = "which_dialog";
    public static final String FEEDBACK_INPUT_CONTENT = "input_feedback";
    public static final String FEEDBACK_PROPERTITY = "sys.xiaopeng.feedback_debug";
    public static final String FEED_BACK_ACCESSKEY = "51baf03a-30fe-45e1-98cc-c0e8ce274b29";
    public static final String FEED_BACK_APPID = "holly";
    public static final float FONT_LAGER = 1.0f;
    public static final float FONT_SMALLER = 0.9f;
    public static final boolean FORCE_DAY_MODE = false;
    public static final String HEADREST_ACTION_RESTORE = "com.xiaopeng.intent.action.HEADREST_RESTORE";
    public static final String HEADREST_ACTION_SWITCH = "com.xiaopeng.intent.action.HEADREST_SWITCH";
    public static final int ICM_BRIGHTNESS_DETAL_SPEECH = 10;
    public static final boolean IS_SDK_HIGHER_P;
    public static final boolean IS_SDK_HIGHER_Q;
    public static final String KEY_INTELLIGENT_HEADREST_RESTORE = "key_intelligent_headrest_restore";
    public static final String KEY_LOW_SPEED_VOLUME = "key_low_speed_volume";
    public static final String KEY_MEMORY_METER_VOLUME = "key_meter_volume";
    public static final String KEY_PARKING_RESTORE = "key_parking_restore";
    public static final String LABORATORY_PROPERTITY = "sys.xiaopeng.laboratory_debug";
    public static final String LAST_PUSH_TO_AI_STORE_ID = "last_push_ai_store_id";
    public static final String LAST_PUSH_TO_AI_STORE_TIME = "last_push_ai_store_time";
    public static final int LOW_SPD_VOLUME_MAX_PROGRESS = 100;
    public static final int LOW_SPD_VOLUME_MIN_PROGRESS = 1;
    public static final int LOW_SPEED_VOLUME_DEFAULT;
    public static final int LOW_SPEED_VOLUME_DEFAULT_1 = 89;
    public static final int LOW_SPEED_VOLUME_DEFAULT_2 = 77;
    public static final int LOW_SPEED_VOLUME_DEFAULT_3 = 84;
    public static final String MAIN_BRIGHTNESS_ADPTIVE_RESTORE = "main_brightness_adptive_restore";
    public static final String MEDIA_PATH = "/system/media/audio/xiaopeng/cdu/wav/CDU_headrest.wav";
    public static final int MEDIA_STREAM = 3;
    public static final String METER_BRIGHTNESS_ADPTIVE_RESTORE = "meter_brightness_adptive_restore";
    public static final int METER_DISPLAY_MAX_LEVEL = 100;
    public static final int METER_DISPLAY_MAX_PROGRESS = 100;
    public static final int METER_DISPLAY_MIN_PROGRESS = 1;
    public static final int NAVIGATION_STREAM = 9;
    public static final String NOTIFY_CHANNEL = "com.xiaopeng.car.settings";
    public static final int NOTIFY_STORAGE_ID = 0;
    public static final String PARKING_ADVANCED_RESTORE = "parking_advanced_restore";
    public static final String POPUP_BLUETOOTH = "com.xiaopeng.intent.action.POPUP_BLUETOOTH";
    public static final String POPUP_DOWNLOAD = "com.xiaopeng.intent.action.POPUP_DOWNLOAD";
    public static final String POPUP_FINISH_ACTION = "com.xiaopeng.intent.action.POPUP_ACTIVITY_FINISH";
    public static final String POPUP_STORAGE = "com.xiaopeng.intent.action.POPUP_STORAGE";
    public static final String POPUP_USB = "com.xiaopeng.intent.action.POPUP_USB";
    public static final String POPUP_WLAN = "com.xiaopeng.intent.action.POPUP_WLAN";
    public static final String PROPERTIES_FACTORY_MODE = "persist.factory.mode";
    public static final int PSN_BLUETOOTH_STREAM = 13;
    public static final String PSN_BRIGHTNESS_ADPTIVE_RESTORE = "psn_brightness_adptive_restore";
    public static final String PUSH_STORAGE_FULL_TIME = "push_storage_full_time";
    public static final String QS_TILE_VERSION = "tile_version";
    public static final String REPAIR_REQUEST_QUIT_ACTION = "com.xiaopeng.intent.action.REPAIR_REQUEST_QUIT";
    public static final int SCREEN_BACK_SEAT_ID = 3;
    public static final int SCREEN_MAIN_ID = 0;
    public static final int SCREEN_PSN_ID = 1;
    public static final String SECRET_NO_PUB;
    private static final String SECRET_NO_PUB_D20 = "ZSnLb9KY5Pa73SgW";
    public static final String SECRET_PUB;
    private static final String SECRET_PUB_D20 = "2UwzvAQHBBSTh6mJ";
    public static final String SOUND_EFFECT_ACTION = "com.xiaopeng.intent.action.SOUND_EFFECT";
    public static final String SOUND_EFFECT_DISMISS_ACTION = "com.xiaopeng.intent.action.SOUND_EFFECT_DISMISS";
    public static final int SOUND_EFFECT_EQUALIZER_MODE1 = 1;
    public static final int SOUND_EFFECT_EQUALIZER_MODE2 = 2;
    public static final int SOUND_EFFECT_EQUALIZER_SWITCH_OFF = 0;
    public static final int SOUND_EFFECT_EQUALIZER_SWITCH_ON = 1;
    public static final int SOUND_FIELD_MAX = 100;
    public static final int SPEECH_STREAM = 10;
    public static final String SYS_PROP_VIDEO_RECORDER = "persist.sys.xp.recorder_ssid";
    public static final int THEME_A = 0;
    public static final int THEME_AUTO = 0;
    public static final int THEME_B = 1;
    public static final int THEME_NIGHT = 2;
    public static final int THEME_NORMAL = 1;
    public static final String WAIT_MODE_FINISH_ACTION = "com.xiaopeng.intent.action.WAIT_MODE_FINISH";
    public static final String WIFI_DEBUG = "xpsettings.wifi.debug";
    public static final String XP_AUTO = "XP-AUTO";
    public static final String XP_CHECK = "XP-CHECK";
    public static final String XP_SOUND_EFFECT_EQUALIZER_MODE = "xp_sound_effect_equalizer_mode";
    public static final String XP_SOUND_EFFECT_EQUALIZER_SWITCH = "xp_sound_effect_equalizer_switch";
    public static final String XP_SOUND_EFFECT_EQUALIZER_VALUE1 = "xp_sound_effect_equalizer_value1";
    public static final String XP_SOUND_EFFECT_EQUALIZER_VALUE2 = "xp_sound_effect_equalizer_value2";
    public static final String XUI_DIALOG_ACTION = "com.xiaopeng.intent.action.XUI_DIALOG";
    public static Point[] mD21RealRecommendationPoint;
    public static Point[] mRealAdsorptionPoint;
    public static Point[] mRealRecommendationPoint;
    public static Point[] mThreeRecommendationPoint;
    public static Point[] mUnityRealRecommendationPoint;

    /* loaded from: classes.dex */
    public static class External {
        public static final String ACTION_A2DP_CONNECTION_STATE_CHANGED = "xiaopeng.bluetooth.a2dp.action.CONNECTION_STATE_CHANGED";
        public static final String ACTION_AVRCP_CONNECTION_STATE_CHANGED = "xiaopeng.bluetooth.avrcp.action.CONNECTION_STATE_CHANGED";
        public static final String ACTION_BOND_LIST_CHANGED = "xiaopeng.bluetooth.action.BOND_LIST_CHANGED";
        public static final String ACTION_BOND_STATE_CHANGED = "xiaopeng.bluetooth.action.BOND_STATE_CHANGED";
        public static final String ACTION_CONNECTION_STATE_CHANGED = "xiaopeng.bluetooth.action.CONNECTION_STATE_CHANGED";
        public static final String ACTION_DEVICE_ADDRESS = "address";
        public static final String ACTION_DEVICE_FOUND = "xiaopeng.bluetooth.action.FOUND";
        public static final String ACTION_DEVICE_FOUND_CATEGORY = "category";
        public static final String ACTION_DEVICE_FOUND_RSSI = "rssi";
        public static final String ACTION_DEVICE_NAME = "name";
        public static final String ACTION_DISCOVERY_FINISHED = "xiaopeng.bluetooth.action.DISCOVERY_FINISHED";
        public static final String ACTION_DISCOVERY_STARTED = "xiaopeng.bluetooth.action.DISCOVERY_STARTED";
        public static final String ACTION_EXTRA_DEVICE_ID = "deviceId";
        public static final String ACTION_EXTRA_PRESTATE = "prestate";
        public static final String ACTION_EXTRA_STATE = "state";
        public static final String ACTION_HFP_CONNECTION_STATE_CHANGED = "xiaopeng.bluetooth.hfp.action.CONNECTION_STATE_CHANGED";
        public static final String ACTION_STATE_CHANGED = "xiaopeng.bluetooth.action.ACTION_STATE_CHANGED";
        public static final int STATE_CONNECTED = 2;
        public static final int STATE_CONNECTING = 1;
        public static final int STATE_DISCONNECTED = 0;
        public static final int STATE_DISCONNECTING = 3;
    }

    /* loaded from: classes.dex */
    public static class LaboratoryNetConfig {
        public static final String APP_SCREEN_FLOW = "app_screen_flow";
        public static final String APP_USED_LIMIT = "app_used_limit";
        public static final String AUTO_POWER_OFF = "auto_power_off";
        public static final String CAR_CALL_ADVANCED = "car_call_advanced";
        public static final String FOLLOWED_VEHICLE_LOST = "followed_vehicle_lost";
        public static final String FOLLOWED_VEHICLE_LOST_NEW = "2";
        public static final String FOLLOWED_VEHICLE_LOST_OLD = "1";
        public static final String INDUCTION_LOCK = "d21_induction_lock";
        public static final String LEAVE_LOCK = "leave_lock";
        public static final String LOW_SPEED_ALARM_SOUND = "low_speed_alarm_sound";
        public static final String NEAR_UNLOCK = "near_unlock";
        public static final String REAR_ROW_REMINDER = "rear_row_reminder";
        public static final String REMOTE_CONTROL_KEY_PARKING = "remote_control_key_parking";
        public static final String REMOTE_CONTROL_PARKING = "remote_control_parking";
        public static final String REMOTE_PARKING_ADVANCED = "remote_parking_advanced";
        public static final String SAY_HI_LIGHT_LANGUAGE = "say_hi_light_language";
        public static final String SOLDIER_CAMERA = "soldier_camera";
        public static final String SOLDIER_MODE = "soldier_mode";
        public static final String SOLIDER_THRESHOLD = "solider_threshold";
        public static final String TTS_BROADCAST = "tts_broadcast";
    }

    static {
        APP_ID = CarFunction.isDSeries() ? "xp_system_setting_car" : XId.APP_ID_CAR_SETTINGS;
        SECRET_PUB = CarFunction.isDSeries() ? SECRET_PUB_D20 : "k9hddf3lim2JfdLa";
        SECRET_NO_PUB = CarFunction.isDSeries() ? SECRET_NO_PUB_D20 : "Z9hHgh2dkkaJf2dW";
        AVAS_STREAM = ReflectUtils.getIntValue("STREAM_AVAS", AudioManager.class);
        CONFIG_SERVER_HOST = "http://ota.test.xiaopeng.local/";
        if (SystemProperties.getBoolean(LABORATORY_PROPERTITY, false)) {
            CONFIG_SERVER_HOST = "https://10.0.13.28:8553/";
        } else {
            CONFIG_SERVER_HOST = "https://xmart.xiaopeng.com/";
        }
        LOW_SPEED_VOLUME_DEFAULT = CarFunction.getLowSpeedVolumeDefaultValue();
        IS_SDK_HIGHER_P = Build.VERSION.SDK_INT > 28;
        IS_SDK_HIGHER_Q = Build.VERSION.SDK_INT > 29;
        mThreeRecommendationPoint = new Point[]{new Point(50, 25), new Point(50, 50), new Point(50, 75)};
        mUnityRealRecommendationPoint = new Point[]{new Point(25, 25), new Point(75, 25), new Point(50, 50), new Point(50, 75)};
        mD21RealRecommendationPoint = new Point[]{new Point(25, 25), new Point(75, 25), new Point(50, 50), new Point(25, 75), new Point(75, 75)};
        mRealRecommendationPoint = new Point[]{new Point(9, 24), new Point(89, 24), new Point(50, 43), new Point(30, 88), new Point(70, 88)};
        mRealAdsorptionPoint = new Point[]{new Point(25, 30), new Point(75, 30), new Point(50, 60), new Point(50, 90)};
    }

    public static String getFeedbackBaseUrl() {
        Logs.d("xpsettings getFeedbackBaseUrl user:" + Utils.isUserRelease());
        return Utils.isUserRelease() ? "https://cs-api-ext.xiaopeng.com/" : "http://test-cs-api-ext.xiaopeng.com/";
    }

    public static String getHardwareId() {
        return SystemProperties.get("persist.sys.mcu.hardwareId");
    }

    public static String getVin() {
        return SystemProperties.get("sys.xiaopeng.vin", "LMVHFEFZ0JA999999");
    }

    private static int getBrightnessMax() {
        return CarSettingsApp.getContext().getResources().getInteger(17694887);
    }

    private static int getBrightnessMin() {
        return CarSettingsApp.getContext().getResources().getInteger(17694888);
    }
}
