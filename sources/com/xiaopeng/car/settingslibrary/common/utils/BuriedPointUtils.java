package com.xiaopeng.car.settingslibrary.common.utils;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
/* loaded from: classes.dex */
public class BuriedPointUtils {
    public static final String ABOUT_SYS_PAGE_ID = "P10001";
    public static final String AC_CHARGING_ID = "B008";
    public static final String APP_CLEAN_CONFIRM_ID = "B013";
    public static final String APP_CLEAN_ID = "B012";
    public static final String APP_LIMIT_BUTTON_ID = "B002";
    public static final String APP_LIMIT_BUTTON_NEW_ID = "B010";
    public static final String APP_LIST_KEY = "appList";
    public static final String APP_UNINSTALL_CONFIRM_ID = "B015";
    public static final String APP_UNINSTALL_ID = "B014";
    public static final String AUTO_BRIGHTNESS_HIGH_ID = "B001";
    public static final String AUTO_BRIGHTNESS_LOW_ID = "B002";
    public static final String AUTO_HOLD_ID = "B011";
    public static final String AUTO_POWER_OFF_BUTTON_ID = "B003";
    public static final String AUTO_POWER_OFF_BUTTON_NEW_ID = "B009";
    public static final String BACK_BOX_ID = "B003";
    public static final String BLUETOOTH_BUTTON_ID = "B001";
    public static final String BLUETOOTH_PAGE_ID = "P00002";
    public static final String BLUETOOTH_VISIABLE_BUTTON_ID = "B002";
    public static final String BRIGHTNESS_ID = "B004";
    public static final String CACHE_CLEAN_CONFIRM_ID = "B004";
    public static final String CACHE_CLEAN_ID = "B003";
    public static final String CAMERA_CLEAN_CONFIRM_ID = "B007";
    public static final String CAMERA_CLEAN_ID = "B006";
    public static final String CAPSULE_PAGE = "P10138";
    public static final String CAR_WARNING_BUTTON_ID = "B001";
    public static final String CINEMA_GUIDE_BT = "B002";
    public static final int CINEMA_GUIDE_BT_STATUS_BOUNDED = 2;
    public static final int CINEMA_GUIDE_BT_STATUS_CONNECTED = 3;
    public static final int CINEMA_GUIDE_BT_STATUS_UNBOUND = 1;
    public static final String CINEMA_GUIDE_ENTER_BTN_ID = "B005";
    public static final String CINEMA_GUIDE_EXIT_BTN_ID = "B006";
    public static final String CINEMA_GUIDE_PAGE = "P10140";
    public static final String CLEAN_CACHE = "B004";
    public static final String CLEAN_MODE_BUTTON_ID = "B006";
    public static final String CLEAN_MODE_ID = "B015";
    public static final String COUNT_KEY = "count";
    public static final String DC_CHARGING_ID = "B009";
    public static final String DECREASE_VOLUME_BUTTON_ID = "B010";
    public static final String DEPUTY_BRIGHTNESS_ID = "B001";
    public static final String DEPUTY_DRIVER_PAGE_ID = "P00005";
    public static final String DIAL_02 = "B002";
    public static final String DIAL_400 = "B003";
    public static final String DIRECTION_ALERTS = "B008";
    public static final int DISPLAY_3D_STATE_ID = 5;
    public static final String DISPLAY_APP_ID = "B011";
    public static final int DISPLAY_AUTO_BRIGHTNESS_LOW_STATE_ID = 3;
    public static final int DISPLAY_AUTO_BRIGHTNESS_STATE_ID = 2;
    public static final int DISPLAY_BRIGHTNESS_STATE_ID = 0;
    public static final String DISPLAY_CACHE_PAGE_ID = "B002";
    public static final String DISPLAY_CAMERA_ID = "B005";
    public static final int DISPLAY_LINK_BRIGHTNESS_STATE_ID = 4;
    public static final int DISPLAY_METER_BRIGHTNESS_STATE_ID = 1;
    public static final String DISPLAY_MUSIC_ID = "B008";
    public static final String DISPLAY_PAGE_ID = "P00006";
    public static final String DISPLAY_PAGE_NEW_ID = "P10010";
    public static final String DISPLAY_STATE_ID = "B004";
    public static final String DISPLAY_STORAGE_MANAGE_PAGE_ID = "B001";
    public static final String DOOR_VOLUME_BUTTON_ID = "B004";
    public static final String DRIVER_MODE_ID = "B002";
    public static final int DROP_DOWN_MENU_OFF = 2;
    public static final int DROP_DOWN_MENU_ON = 1;
    public static final String ENERGY_RECOVERY_ID = "B007";
    public static final int HEADREST_STATUS = 1;
    public static final String HEADREST_STATUS_BTN = "B001";
    public static final String HEADREST_SWITCH = "B002";
    public static final int HEADREST_SWITCH_STATUS = 2;
    public static final String HEADSET_BUTTON_ID = "B010";
    private static final String ID_KEY = "id";
    public static final String INTELLIGENT_DEODORIZATION_ID = "B013";
    public static final String KEY_REMOTE_CONTROL_BUTTON_ID = "B015";
    public static final int LABORATORY_APP_LIMIT_STATE_ID = 0;
    public static final int LABORATORY_AUTO_POWER_OFF_ID = 2;
    public static final int LABORATORY_KEY_PARKING_ID = 4;
    public static final int LABORATORY_LEAVE_LOCK_ID = 5;
    public static final int LABORATORY_LOW_SPEED_STATE_ID = 1;
    public static final int LABORATORY_LSP_ID = 8;
    public static final int LABORATORY_NEAR_UNLOCK_ID = 6;
    public static final String LABORATORY_PAGE_ID = "P00008";
    public static final String LABORATORY_PAGE_NEW_ID = "P10012";
    public static final int LABORATORY_SOLDIER_MODE_ID = 7;
    public static final String LABORATORY_STATE_ID = "B007";
    public static final String LABORATORY_STATE_NEW_ID = "B012";
    public static final int LABORATORY_TTS_CONCISE = 0;
    public static final int LABORATORY_TTS_DETAIL = 1;
    public static final int LABORATORY_TTS_ID = 9;
    public static final String LEAVE_LOCK_BUTTON_ID = "B011";
    public static final String LOW_SPEED_VOLUME_BUTTON_ID = "B005";
    public static final String LOW_SPEED_VOLUME_BUTTON_NEW_ID = "B003";
    public static final String LOW_SPEED_VOLUME_SLIDER_BUTTON_ID = "B014";
    public static final String MAIN_AUTO_BRIGHTNESS = "B002";
    public static final String MAIN_DRIVER_BUTTON_ID = "B005";
    public static final String MAIN_DRIVER_PAGE_ID = "P00004";
    public static final String MEDITATION_MODE_ID = "B014";
    public static final String METER_AUTO_BRIGHTNESS = "B005";
    public static final String METER_LINK_ID = "B003";
    public static final String MODULE = "settings";
    public static final String MUSIC_CLEAN_CONFIRM_ID = "B010";
    public static final String MUSIC_CLEAN_ID = "B009";
    public static final String MUSIC_NEXT_ID = "B006";
    public static final String MUSIC_PLAY_PAUSE_ID = "B007";
    public static final String MUSIC_PRE_ID = "B005";
    public static final String MUSIC_SEEK_ID = "B008";
    public static final String NAME_KEY = "name";
    public static final String NEAR_UNLOCK_BUTTON_ID = "B010";
    private static final int OFF = 0;
    private static final int ON = 1;
    public static final String Outside_Sound_Mode = "B009";
    public static final String PAGE_STATE_ID = "B002";
    public static final String PERSONAL_CENTER_ID = "B001";
    public static final String PSN_AUTO_BRIGHTNESS = "B003";
    public static final String PSN_SCREEN_OFF = "B004";
    public static final String RAPID_COOLING_ID = "B012";
    public static final String REAR_MIRROR_ID = "B005";
    public static final String RESET_SYS = "B001";
    public static final String RESULT_KEY = "result";
    public static final String R_VOLUME_BUTTON_ID = "B003";
    public static final String SCREEN_FLOW_NEW_ID = "B011";
    public static final String SEAT_MEMORY_ID = "B006";
    public static final String SIZE_KEY = "size";
    public static final String SOLIDER_CAMERA_BUTTON_ID = "B013";
    public static final String SOLIDER_CAMERA_BUTTON_NEW_ID = "B005";
    public static final String SOLIDER_SENSITIVITY_BUTTON_ID = "B012";
    public static final String SOLIDER_SENSITIVITY_BUTTON_NEW_ID = "B004";
    public static final int SOUND_CAR_WARNING_STATE_ID = 4;
    public static final int SOUND_DOOR_OPEN_STATE_ID = 8;
    public static final int SOUND_EFFECT_AMP_EQUALIZER_CUSTOM1 = 1;
    public static final int SOUND_EFFECT_AMP_EQUALIZER_CUSTOM2 = 2;
    public static final int SOUND_EFFECT_AMP_EQUALIZER_OPEN = 0;
    public static final int SOUND_EFFECT_AMP_EQUALIZER_RESTORE_DEFAULT = 3;
    public static final String SOUND_EFFECT_CHOOSE = "B004";
    public static final int SOUND_EFFECT_CLASSIC = 5;
    public static final int SOUND_EFFECT_COMMEN = 0;
    public static final int SOUND_EFFECT_CUSTOM = 0;
    public static final int SOUND_EFFECT_DISCO = 4;
    public static final int SOUND_EFFECT_DYNAMIC = 2;
    public static final int SOUND_EFFECT_EQUALIZER_RESTORE_DEFAULT = 1;
    public static final int SOUND_EFFECT_HIFI = 1;
    public static final String SOUND_EFFECT_HIFI_PAGE_ID = "P10002";
    public static final String SOUND_EFFECT_ID = "B016";
    public static final int SOUND_EFFECT_JAZZ = 2;
    public static final int SOUND_EFFECT_KTV = 8;
    public static final int SOUND_EFFECT_LIVE = 7;
    public static final int SOUND_EFFECT_LIVE_DYNAMIC = 2;
    public static final int SOUND_EFFECT_MOVIE = 9;
    public static final int SOUND_EFFECT_MUSIC = 6;
    public static final int SOUND_EFFECT_ORIGINAL = 10;
    public static final int SOUND_EFFECT_ORIGINAL_D21 = 4;
    public static final String SOUND_EFFECT_PAGE_ID = "P00005";
    public static final int SOUND_EFFECT_PEOPLE = 1;
    public static final int SOUND_EFFECT_REAL = 0;
    public static final int SOUND_EFFECT_ROCK = 3;
    public static final String SOUND_EFFECT_SETTINGS_HIFI_ID = "B002";
    public static final String SOUND_EFFECT_SETTINGS_ID = "B001";
    public static final String SOUND_EFFECT_SETTINGS_ID_D21 = "B003";
    public static final int SOUND_EFFECT_SOFT = 1;
    public static final int SOUND_EFFECT_SOUNDDING = 0;
    public static final String SOUND_EFFECT_STATUS = "B005";
    public static final int SOUND_EFFECT_STRESS = 1;
    public static final String SOUND_EFFECT_STYLE = "B001";
    public static final int SOUND_EFFECT_VOICE = 3;
    public static final int SOUND_EFFECT_VOICE_HIFI = 3;
    public static final String SOUND_EQUALIZER_BUTTON_ID = "B011";
    public static final String SOUND_EQUALIZER_DYNA_BUTTON_ID = "B015";
    public static final String SOUND_EQUALIZER_DYNA_SWITCH_ID = "B017";
    public static final String SOUND_EQUALIZER_XP_BUTTON_ID = "B014";
    public static final String SOUND_EQUALIZER_XP_SWITCH_ID = "B016";
    public static final int SOUND_HEADSET_STATE1_ID = 0;
    public static final int SOUND_HEADSET_STATE2_ID = 1;
    public static final int SOUND_HEADSET_STATE3_ID = 2;
    public static final String SOUND_ID = "B004";
    public static final int SOUND_MEDIA_VOLUME_STATE_ID = 0;
    public static final int SOUND_NAVIG_VOLUME_STATE_ID = 2;
    public static final String SOUND_PAGE_ID = "P00009";
    public static final int SOUND_R_STALL_STATE_ID = 9;
    public static final int SOUND_SPEECH_VOLUME_STATE_ID = 1;
    public static final String SOUND_STATUS_PAGE_ID = "P10003";
    public static final int SOUND_SYSTEM_STATE_ID = 3;
    public static final String SOURCE_KEY = "source";
    public static final String SPEECH_ROUND_BUTTON_ID = "B006";
    public static final String SPEED_LINK_LEVEL = "B007";
    public static final String STORAGE_AUTO_CLEAN_ID = "B017";
    public static final String STORAGE_AUTO_CLEAN_MUSIC_ID = "B019";
    public static final String STORAGE_DAILY_UPLOAD_ID = "B016";
    public static final String STORAGE_MANAGE_PAGE_ID = "P11060";
    public static final String STORAGE_STORE_FULL_ID = "B018";
    public static final String SWITCH_3D_BUTTON_ID = "B005";
    public static final String SYSTEMUI_MODULE = "systemui";
    public static final String SYSTEM_SOUND_XOPERA_CLICK = "B005";
    public static final String SYSTEM_SOUND_XOPERA_STATUS = "B006";
    public static final String SYSTEM_VOLUME_BUTTON_ID = "B008";
    public static final String TEMPERATURE_ID = "B003";
    public static final String TTS_BROADCAST_TYPE_ID = "B001";
    public static final String TYPE_KEY = "type";
    public static final String WAIT_MODE_BUTTON_ID = "B001";
    public static final String WARNING_BUTTON_ID = "B007";
    public static final String WELCOME_BUTTON_ID = "B001";
    public static final String WELCOME_PAGE_ID = "P00003";
    public static final String WIND_ID = "B002";
    public static final String WIPER_SPEED_ID = "B010";
    public static final String WLAN_BUTTON_ID = "B001";
    public static final String WLAN_PAGE_ID = "P00001";
    public static final int XP_TTS_BROADCAST_TYPE_CONCISE = 1;
    public static final int XP_TTS_BROADCAST_TYPE_DETAIL = 0;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface DisplaySource {
        public static final String TYPE_CLICK = "click";
        public static final String TYPE_VOICE = "voice";
    }

    public static void sendButtonDataLog(String str, String str2) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2);
        sendDataLog(MODULE, str, str2, new HashMap());
    }

    public static void sendButtonDataLog(String str, String str2, boolean z) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2 + "," + z);
        HashMap hashMap = new HashMap();
        hashMap.put("result", Integer.valueOf(z ? 1 : 0));
        sendDataLog(MODULE, str, str2, hashMap);
    }

    public static void sendButtonDataLog(String str, String str2, int i) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2 + "," + i);
        HashMap hashMap = new HashMap();
        hashMap.put("result", Integer.valueOf(i));
        sendDataLog(MODULE, str, str2, hashMap);
    }

    public static void sendButtonDataLog(String str, String str2, String str3, int i) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2 + "," + str3 + "," + i);
        HashMap hashMap = new HashMap();
        hashMap.put(str3, Integer.valueOf(i));
        sendDataLog(MODULE, str, str2, hashMap);
    }

    public static void sendButtonDataLog(String str, String str2, String str3, Object obj) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2 + "," + str3 + "," + obj);
        HashMap hashMap = new HashMap();
        hashMap.put(str3, obj);
        sendDataLog(MODULE, str, str2, hashMap);
    }

    public static void sendPageStateDataLog(String str, String str2, int i, String str3, int i2) {
        Logs.d("xpburiedpoint sendPageStateDataLog:" + str + "," + str2 + "," + i + "," + str3 + "," + i2);
        HashMap hashMap = new HashMap();
        hashMap.put("id", Integer.valueOf(i));
        hashMap.put(str3, Integer.valueOf(i2));
        sendDataLog(MODULE, str, str2, hashMap);
    }

    public static void sendPageStateDataLog(String str, String str2, int i, boolean z) {
        if (CarFunction.isSupportEventTracking()) {
            Logs.d("xpburiedpoint sendPageStateDataLog:" + str + "," + str2 + "," + i + "," + z);
            HashMap hashMap = new HashMap();
            hashMap.put("id", Integer.valueOf(i));
            hashMap.put("type", Integer.valueOf(z ? 1 : 0));
            sendDataLog(MODULE, str, str2, hashMap);
        }
    }

    public static void sendPageStateDataLog(String str, String str2, int i, int i2) {
        if (CarFunction.isSupportEventTracking()) {
            Logs.d("xpburiedpoint sendPageStateDataLog:" + str + "," + str2 + "," + i + "," + i2);
            HashMap hashMap = new HashMap();
            hashMap.put("id", Integer.valueOf(i));
            hashMap.put(COUNT_KEY, Integer.valueOf(i2));
            sendDataLog(MODULE, str, str2, hashMap);
        }
    }

    public static void sendDataLog(final String str, final String str2, final String str3, final Map<String, Object> map) {
        if (CarFunction.isSupportEventTracking()) {
            Logs.d("xpburiedpoint sendDataLog:" + str2 + "," + str3);
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.common.utils.-$$Lambda$BuriedPointUtils$tixtI-M4NsnIp7kzEGNfOvmJ3Xs
                @Override // java.lang.Runnable
                public final void run() {
                    BuriedPointUtils.lambda$sendDataLog$1(str, str2, str3, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendDataLog$1(String str, final String str2, final String str3, Map map) {
        IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        final IMoleEventBuilder buttonId = iDataLog.buildMoleEvent().setModule(str).setPageId(str2).setButtonId(str3);
        map.forEach(new BiConsumer() { // from class: com.xiaopeng.car.settingslibrary.common.utils.-$$Lambda$BuriedPointUtils$8E4Gp1xSd4n_ldsL1YswpL4gKBQ
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BuriedPointUtils.lambda$null$0(str2, str3, buttonId, (String) obj, obj2);
            }
        });
        IMoleEvent build = buttonId.build();
        if (build != null) {
            iDataLog.sendStatData(build);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$0(String str, String str2, IMoleEventBuilder iMoleEventBuilder, String str3, Object obj) {
        Logs.d("xpburiedpoint sendDataLog:" + str + "," + str2 + "," + str3 + "," + obj);
        if (obj instanceof String) {
            iMoleEventBuilder.setProperty(str3, (String) obj);
        } else if (obj instanceof Number) {
            iMoleEventBuilder.setProperty(str3, (Number) obj);
        } else if (obj instanceof Boolean) {
            iMoleEventBuilder.setProperty(str3, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Character) {
            iMoleEventBuilder.setProperty(str3, ((Character) obj).charValue());
        } else {
            Logs.d("xpburiedpoint sendDataLog: can not case type");
        }
    }
}
