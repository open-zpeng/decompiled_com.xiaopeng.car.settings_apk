package com.xiaopeng.car.settingslibrary.common;
/* loaded from: classes.dex */
public class XPSettingsConfig {
    public static final String APPSTORE_NOTIFICATION_ENABLED = "appstore_notification_enabled";
    public static final String APP_SCREEN_FLOW = "app_screen_flow";
    public static final String ASSIST_DRIVING_NOTIFICATION_ENABLED = "assist_driving_notification_enabled";
    public static final String AVAS_LOUD_SPEAKER_SW = "avas_speaker";
    public static final String CARCONTROL_NOTIFICATION_ENABLED = "carcontrol_notification_enabled";
    public static final String CMS_AUTO_BRITHTNESS = "screen_brightness_mode_3";
    public static final String CONNECTED_BLE_DEVICE = "connected_ble_device";
    public static final String DECREASE_VOLUME_NAVIGATING = "decrease_volume_navigating";
    public static final String External_TTS_HEADSET_OUT = "external_tts_headset_out";
    public static final String FOLLOWED_VEHICLE_LOST_CONFIG = "followed_vehicle_lost_config";
    public static final String KEY_SAYHI_SW = "isSayHiEnable";
    public static final String MAIN_SCREEN_BRIGHTNESS = "screen_brightness";
    public static final String MAIN_SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode_0";
    public static final String MEMORY_PARKING_NOTIFICATION_ENABLED = "memory_parking_notification_enabled";
    public static final String METER_AUTO_BRIGHTNESS_MODE;
    public static final String MUSIC_NOTIFICATION_ENABLED = "xpmusic_notification_enabled";
    public static final String OPERATING_ACTIVITIES_NOTIFICATION_ENABLED = "operating_activities_notification_enabled";
    public static final String OTA_NOTIFICATION_ENABLED = "ota_notification_enabled";
    public static final String PSN_SCREEN_BRIGHTNESS = "screen_brightness_1";
    public static final String PSN_SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode_1";
    public static final String PSN_TTS_HEADSET_OUT = "psn_tts_headset_out";
    public static final String QS_KEY_WIPER_GEAR_AUTO_EXIST = "wiper_gear_auto_exist_switch";
    public static final String REAR_ROW_REMINDER = "rear_row_reminder";
    public static final String REAR_SCREEN_ANGLE = "screen_angle_3";
    public static final String REAR_SCREEN_BRITHTNESS = "screen_brightness_3";
    public static final String REAR_SCREEN_CALLBACK_ANGLE = "screen_angle_callback_3";
    public static final String REAR_SCREEN_CONTROL = "screen_state_control_3";
    public static final String REAR_SCREEN_LOCK = "res_lock";
    public static final String REAR_SCREEN_STATE = "screen_state_3";
    public static final String SAY_HI_AVAS_SW = "avasGearAvhSayhiEnable";
    public static final String SCREEN_BRIGHTNESS_MODE_SYNC = "screen_brightness_mode_sync";
    public static final String TTS_BROADCAST_NOTIFICATION_ENABLED = "tts_broadcast_notification_enabled";
    public static final String VEHICLE_DETECTION_NOTIFICATION_ENABLED = "vehicle_detection_notification_enabled";
    public static final String XP_CENTRAL_METER_LINKAGE = "xp_central_meter_linkage";
    public static final String XP_DARK_STATE_BRIGHTNESS = "screen_brightness_dark_state";
    public static final String XP_DARK_STATE_BRIGHTNESS_ADJ = "screen_brightness_dark_adj_type";
    public static final String XP_DYNAMIC_WALL_PAPER_SWITCH = "xp_dynamic_wall_paper";
    public static final String XP_HEADREST_INTELLIGENT_SWITCH = "headrest_deputy_intelligent_switch";
    public static final String XP_ICM_BRIGHTNESS;
    public static final String XP_ICM_BRIGHTNESS_CALLBACK;
    public static final String XP_LOW_SPEED_SOUND = "xp_low_speed_sound";
    public static final String XP_RESET_ELECTRONICS_KEY = "reset_electronics_only_for_settings";
    public static final String XP_RESET_ELECTRONICS_VALUE = "already_reset";
    public static final String XP_THEME = "key_theme_type";
    public static final String XP_THEME_DB = "ui_night_mode";
    public static final String XP_TTS_BROADCAST_TYPE = "tts_broadcast_type";
    public static final int XP_TTS_BROADCAST_TYPE_CONCISE = 1;
    public static final int XP_TTS_BROADCAST_TYPE_DETAIL = 0;

    static {
        XP_ICM_BRIGHTNESS = Config.IS_SDK_HIGHER_P ? "screen_brightness_2" : "icm_brightness";
        XP_ICM_BRIGHTNESS_CALLBACK = Config.IS_SDK_HIGHER_P ? "screen_brightness_callback_2" : "icm_brightness_callback";
        METER_AUTO_BRIGHTNESS_MODE = Config.IS_SDK_HIGHER_P ? "screen_brightness_mode_2" : "icm_brightness_mode";
    }
}
