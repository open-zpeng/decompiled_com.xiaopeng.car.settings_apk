package com.xiaopeng.car.settingslibrary.direct;

import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.direct.action.CleaningScreenPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.DynaudioSoundEffectPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.NormalPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.SoundEffectPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.StoragePageAction;
import com.xiaopeng.car.settingslibrary.direct.action.XiaopengSoundEffectPageAction;
import com.xiaopeng.car.settingslibrary.direct.support.AppUsedLimitCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.AutoPowerOffCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.CleaningScreenCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.DarkBrightnessCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.DriverVipCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.DynaudioCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.InductionLockCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.KeyParkingAdvancedCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.KeyParkingCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.LeaveLockCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.LowSpeedSliderCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.NearUnlockCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.SoldierCameraCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.SoldierCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.TtsBroadcastCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.WaitModeCheckAction;
import com.xiaopeng.car.settingslibrary.ui.fragment.AboutSystemsFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.SoundFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.WlanFragment;
import com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync;
import java.util.HashMap;
/* loaded from: classes.dex */
public class DirectData {
    public static HashMap<String, PageDirectItem> loadPageData() {
        HashMap<String, PageDirectItem> hashMap = new HashMap<>();
        hashMap.put("/sound_effect", new PageDirectItem(new SoundEffectPageAction()));
        hashMap.put("/sound_effect_xiaopeng", new PageDirectItem(new XiaopengSoundEffectPageAction()));
        hashMap.put("/sound_effect_dynaudio", new PageDirectItem(new DynaudioSoundEffectPageAction(), new DynaudioCheckAction()));
        hashMap.put("/cleaning_screen", new PageDirectItem(new CleaningScreenPageAction(), new CleaningScreenCheckAction()));
        hashMap.put("/storage_manager_dialog", new PageDirectItem(new StoragePageAction()));
        NormalPageAction normalPageAction = new NormalPageAction();
        hashMap.put("/bluetooth", new PageDirectItem(BluetoothFragment.class.getName(), normalPageAction));
        hashMap.put("/wifi", new PageDirectItem(WlanFragment.class.getName(), normalPageAction));
        hashMap.put("/sound", new PageDirectItem(SoundFragment.class.getName(), normalPageAction));
        hashMap.put("/display", new PageDirectItem(DisplayFragment.class.getName(), normalPageAction));
        hashMap.put("/laboratory", new PageDirectItem(LaboratoryFragment.class.getName(), normalPageAction));
        hashMap.put("/feedback", new PageDirectItem(FeedbackFragment.class.getName(), normalPageAction));
        hashMap.put("/about", new PageDirectItem(AboutSystemsFragment.class.getName(), normalPageAction));
        hashMap.put("/sound/driver_vip", new PageDirectItem("/sound/driver_vip", SoundFragment.class.getName(), normalPageAction, new DriverVipCheckAction()));
        hashMap.put("/display/dark_brightness", new PageDirectItem("/display/dark_brightness", DisplayFragment.class.getName(), normalPageAction, new DarkBrightnessCheckAction()));
        hashMap.put("/laboratory/agreement_pop", new PageDirectItem("/laboratory/agreement_pop", LaboratoryFragment.class.getName(), normalPageAction));
        hashMap.put("/feedback/history_pop", new PageDirectItem("/feedback/history_pop", FeedbackFragment.class.getName(), normalPageAction));
        hashMap.put("/about/agreement", new PageDirectItem("/about/agreement", AboutSystemsFragment.class.getName(), normalPageAction));
        hashMap.put("/about/privacy", new PageDirectItem("/about/privacy", AboutSystemsFragment.class.getName(), normalPageAction));
        return hashMap;
    }

    public static HashMap<String, ElementDirectItem> loadItemData() {
        HashMap<String, ElementDirectItem> hashMap = new HashMap<>();
        hashMap.put("/bluetooth/switch", new ElementDirectItem("switch", R.id.bluetooth_switch));
        hashMap.put("/wifi/switch", new ElementDirectItem("switch", R.id.wlan_switch));
        hashMap.put("/sound/sound_effect", new ElementDirectItem("sound_effect", R.id.sound_effect));
        hashMap.put("/sound/media_volume", new ElementDirectItem("media_volume", R.id.sound_volume_media));
        hashMap.put("/sound/speech_volume", new ElementDirectItem("speech_volume", R.id.sound_volume_speech));
        hashMap.put("/sound/navigation_volume", new ElementDirectItem("navigation_volume", R.id.sound_volume_tts));
        hashMap.put("/sound/meter_volume", new ElementDirectItem(ICarSettingsSync.METER_VOLUME, R.id.sound_volume_car));
        hashMap.put("/sound/system_volume", new ElementDirectItem("system_volume", R.id.sound_volume_system));
        hashMap.put("/sound/driver_vip", new ElementDirectItem("driver_vip", R.id.sound_headrest_audio_mode, new DriverVipCheckAction()));
        hashMap.put("/sound/door_volume_low", new ElementDirectItem("door_volume_low", R.id.sound_open_door));
        hashMap.put("/sound/reverse_volume_low", new ElementDirectItem("reverse_volume_low", R.id.sound_gear_r));
        hashMap.put("/display/center_brightness", new ElementDirectItem("center_brightness", R.id.display_brightness_center));
        hashMap.put("/display/meter_brightness", new ElementDirectItem("meter_brightness", R.id.display_brightness_meter));
        hashMap.put("/display/auto_brightness", new ElementDirectItem("auto_brightness", R.id.display_brightness_auto));
        hashMap.put("/display/daynight_mode", new ElementDirectItem("daynight_mode", R.id.display_theme_dayNight));
        hashMap.put("/display/font_scale", new ElementDirectItem("font_scale", R.id.display_font_size));
        hashMap.put("/display/time_format", new ElementDirectItem("time_format", R.id.display_time_format));
        hashMap.put("/display/clean_mode", new ElementDirectItem("clean_mode", R.id.display_btn_clear));
        hashMap.put("/display/wait_mode", new ElementDirectItem("wait_mode", R.id.display_wait_mode, new WaitModeCheckAction()));
        hashMap.put("/laboratory/app_limit", new ElementDirectItem("app_limit", R.id.app_limit, new AppUsedLimitCheckAction()));
        hashMap.put("/laboratory/low_speed_volume", new ElementDirectItem("low_speed_volume", R.id.low_speed_e28, new LowSpeedSliderCheckAction()));
        hashMap.put("/laboratory/soldier_sensitivity", new ElementDirectItem("soldier_sensitivity", R.id.soldier_level, new SoldierCheckAction()));
        hashMap.put("/laboratory/soldier_camera", new ElementDirectItem(Config.LaboratoryNetConfig.SOLDIER_CAMERA, R.id.soldier_camera, new SoldierCameraCheckAction()));
        hashMap.put("/laboratory/key_parking", new ElementDirectItem("key_parking", CarFunction.isSupportRemotePark() ? R.id.remote_control_d21 : R.id.remote_control_key, new KeyParkingCheckAction()));
        hashMap.put("/laboratory/key_parking_advanced", new ElementDirectItem("key_parking_advanced", R.id.remote_control_advanced, new KeyParkingAdvancedCheckAction()));
        hashMap.put("/laboratory/induction_lock", new ElementDirectItem("induction_lock", R.id.d21_induction_lock, new InductionLockCheckAction()));
        hashMap.put("/laboratory/near_unlock", new ElementDirectItem(Config.LaboratoryNetConfig.NEAR_UNLOCK, R.id.laboratory_near_unlock, new NearUnlockCheckAction()));
        hashMap.put("/laboratory/leave_lock", new ElementDirectItem(Config.LaboratoryNetConfig.LEAVE_LOCK, R.id.laboratory_leave_lock, new LeaveLockCheckAction()));
        hashMap.put("/laboratory/auto_poweroff", new ElementDirectItem("auto_poweroff", R.id.auto_power_off, new AutoPowerOffCheckAction()));
        hashMap.put("/laboratory/tts_broadcast", new ElementDirectItem(Config.LaboratoryNetConfig.TTS_BROADCAST, R.id.tts_broadcast, new TtsBroadcastCheckAction()));
        hashMap.put("/laboratory/agreement", new ElementDirectItem("agreement", R.id.laboratory_tips_list));
        hashMap.put("/feedback/feature_function", new ElementDirectItem("feature_function", R.id.feedback_feature_function));
        hashMap.put("/feedback/feature_suggest", new ElementDirectItem("feature_suggest", R.id.feedback_feature_suggest));
        hashMap.put("/feedback/feature_other", new ElementDirectItem("feature_other", R.id.feedback_feature_other));
        hashMap.put("/feedback/input_content", new ElementDirectItem("input_content", R.id.feedback_input));
        hashMap.put("/feedback/history", new ElementDirectItem("history", R.id.feedback_history));
        hashMap.put("/about/return_factory", new ElementDirectItem("return_factory", R.id.about_restore));
        hashMap.put("/about/privacy", new ElementDirectItem("privacy", R.id.about_policy));
        hashMap.put("/about/agreement", new ElementDirectItem("agreement", R.id.about_user_agreement));
        return hashMap;
    }
}
