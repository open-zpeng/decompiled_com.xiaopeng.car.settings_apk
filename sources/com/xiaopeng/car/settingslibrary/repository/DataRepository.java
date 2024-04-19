package com.xiaopeng.car.settingslibrary.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.system.Os;
import android.system.StructUtsname;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class DataRepository {
    private static final String BASEBAND_VERSION = "gsm.version.baseband";
    private static final int DISABLE = 0;
    private static final int ENABLE = 1;
    private static final String FILENAME_PROC_VERSION = "/proc/version";
    private static final String HOURS_12 = "12";
    private static final String HOURS_24 = "24";
    private static final String MCU_VERSION = "sys.mcu.version";
    private static final String PROPETY_BASEBAND = "ro.sw.embeded.telephony";
    private static final String PROPETY_BUILD_NUMBER = "ro.xiaopeng.software";
    private static final String PROPETY_FIRMWARE_VERSION = "ro.product.firmware";
    private static final String TAG = "DataRepository";
    private static final String VERSION_NAME_POSTFIX = "（测试版）";

    public static DataRepository getInstance() {
        return LocalDataRepositoryHolder.instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class LocalDataRepositoryHolder {
        public static final DataRepository instance = new DataRepository();

        private LocalDataRepositoryHolder() {
        }
    }

    public boolean setAdaptiveBrightness(Context context, boolean z, boolean z2) {
        Logs.d("xpdisplay setAdaptiveBrightness isOpen:" + z);
        if (CarConfigHelper.hasAutoBrightness()) {
            XpAccountManager.getInstance().saveAutoBrightness(z, z2);
        } else {
            XpAccountManager.getInstance().saveDarkLightAdaptation(z, z2);
        }
        return Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE, z ? 1 : 0);
    }

    public boolean setMainAdaptiveBrightness(boolean z, boolean z2) {
        Logs.d("xpdisplay setMainAdaptiveBrightness isOpen:" + z);
        if (CarConfigHelper.hasAutoBrightness()) {
            XpAccountManager.getInstance().saveAutoBrightness(z, z2);
        } else {
            XpAccountManager.getInstance().saveDarkLightAdaptation(z, z2);
        }
        return Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE, z ? 1 : 0);
    }

    public boolean setMeterAdaptiveBrightness(boolean z, boolean z2) {
        Logs.d("xpdisplay setMeterAdaptiveBrightness isOpen:" + z);
        if (CarConfigHelper.hasAutoBrightness()) {
            XpAccountManager.getInstance().saveMeterAutoBrightness(z, z2);
        } else {
            XpAccountManager.getInstance().saveMeterDarkLightAdaptation(z, z2);
        }
        return Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.METER_AUTO_BRIGHTNESS_MODE, z ? 1 : 0);
    }

    public boolean setCMSAdaptiveBrightness(boolean z) {
        Logs.d("xpdisplay setCMSAdaptiveBrightness isOpen:" + z);
        return Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.CMS_AUTO_BRITHTNESS, z ? 1 : 0);
    }

    public boolean setPsnAdaptiveBrightness(boolean z) {
        Logs.d("xpdisplay setPsnAdaptiveBrightness isOpen:" + z);
        return Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.PSN_SCREEN_BRIGHTNESS_MODE, z ? 1 : 0);
    }

    public boolean isAdptiveBrightness(Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE, 1);
        Logs.d("xpdisplay isAdptiveBrightness brightnessMode:" + i);
        return i == 1;
    }

    public boolean isMainAutoBrightnessModeEnable() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE, 1);
        Logs.d("xpdisplay isMainAutoBrightnessModeEnable brightnessMode:" + i);
        return i == 1;
    }

    public boolean isMeterAutoBrightnessModeEnable() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.METER_AUTO_BRIGHTNESS_MODE, 1);
        Logs.d("xpdisplay isMeterAutoBrightnessModeEnable brightnessMode:" + i);
        return i == 1;
    }

    public boolean isCMSAutoBrightnessModeEnable() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.CMS_AUTO_BRITHTNESS, 1);
        Logs.d("xpdisplay isCMSAutoBrightnessModeEnable brightnessMode:" + i);
        return i == 1;
    }

    public boolean isPsnAutoBrightnessModeEnable() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.PSN_SCREEN_BRIGHTNESS_MODE, 1);
        Logs.d("xpdisplay isPsnAutoBrightnessModeEnable brightnessMode:" + i);
        return i == 1;
    }

    public int getAutoScreenBrightness(Context context) {
        float f;
        try {
            f = Settings.System.getFloat(context.getContentResolver(), "screen_auto_brightness_adj");
        } catch (Exception e) {
            e.printStackTrace();
            f = 0.0f;
        }
        return (int) (((f + 1.0f) / 2.0f) * 225.0f);
    }

    public int getScreenBrightness(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getScreenBrightness:" + i);
        return i;
    }

    public int getPsnScreenBrightness(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.PSN_SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getPsnScreenBrightness:" + i);
        return i;
    }

    public void setScreenBrightness(Context context, int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 255) {
            i = 255;
        }
        Logs.d("xpdisplay setScreenBrightness:" + i);
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS, i);
        int autoBrightness = CarConfigHelper.autoBrightness();
        if (autoBrightness == 1 || autoBrightness == 0) {
            if (CarFunction.isNonSelfPageUI()) {
                if (isMainAutoBrightnessModeEnable()) {
                    Logs.d("xpdisplay setMainAdaptiveBrightness close ciu/xpu auto brightness!");
                    setMainAdaptiveBrightness(false, true);
                }
            } else if (isAdptiveBrightness(context)) {
                Logs.d("xpdisplay setScreenBrightness close ciu/xpu auto brightness!");
                setAdaptiveBrightness(context, false, true);
            }
        }
    }

    public void setPsnScreenBrightness(Context context, int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 255) {
            i = 255;
        }
        Logs.d("xpdisplay setPsnScreenBrightness:" + i);
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.PSN_SCREEN_BRIGHTNESS, i);
        int autoBrightness = CarConfigHelper.autoBrightness();
        if ((autoBrightness == 1 || autoBrightness == 0) && isPsnAutoBrightnessModeEnable()) {
            Logs.d("xpdisplay setPsnAdaptiveBrightness close ciu/xpu auto brightness!");
            setPsnAdaptiveBrightness(false);
        }
    }

    public void setIcmBrightness(Context context, int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 100) {
            i = 100;
        }
        Logs.d("xpdisplay meter setIcmBrightness:" + i);
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.XP_ICM_BRIGHTNESS, i);
        int autoBrightness = CarConfigHelper.autoBrightness();
        if (autoBrightness == 1 || autoBrightness == 0) {
            if (CarFunction.isNonSelfPageUI()) {
                if (isMeterAutoBrightnessModeEnable()) {
                    Logs.d("xpdisplay setIcmBrightness close ciu/xpu auto brightness!");
                    setMeterAdaptiveBrightness(false, true);
                }
            } else if (isAdptiveBrightness(context)) {
                Logs.d("xpdisplay setIcmBrightness close ciu/xpu auto brightness!");
                setAdaptiveBrightness(context, false, true);
            }
        }
    }

    public void setRearScreenBrightness(Context context, int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 100) {
            i = 100;
        }
        Logs.d("xpdisplay setRearScreenBrightness:" + i);
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_BRITHTNESS, i);
    }

    public void setDynamicWallPaperSwitch(Context context, boolean z, boolean z2) {
        LogUtils.d("xpdisplay setDynamicWallPaperSwitch:" + z);
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.XP_DYNAMIC_WALL_PAPER_SWITCH, z ? 1 : 0);
    }

    public boolean getDynamicWallPaperSwitch(Context context) {
        LogUtils.d("xpdisplay getDynamicWallPaperSwitch");
        return Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.XP_DYNAMIC_WALL_PAPER_SWITCH, 1) == 1;
    }

    public int getIcmBrightness(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.XP_ICM_BRIGHTNESS);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getIcmBrightness:" + i);
        return i;
    }

    public int getRearScreenBrightness(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_BRITHTNESS);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getRearScreenBrightness:" + i);
        return i;
    }

    public int getIcmBrightnessCallback(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.XP_ICM_BRIGHTNESS_CALLBACK);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay meter getIcmBrightness callback real:" + i);
        return i;
    }

    public boolean set24HourFormat(Context context, boolean z, boolean z2) {
        XpAccountManager.getInstance().save24HourFormat(z, z2);
        boolean putString = Settings.System.putString(context.getContentResolver(), "time_12_24", z ? HOURS_24 : HOURS_12);
        if (CarSettingsApp.getContext() != null) {
            CarSettingsApp.getContext().sendBroadcastAsUser(new Intent("android.intent.action.TIME_SET"), UserHandle.CURRENT);
            Logs.log("display", "send time broadcast ");
        }
        return putString;
    }

    public String getBaseBandVersion() {
        return SystemProperties.get(BASEBAND_VERSION, "");
    }

    public String getFirmwareVersion() {
        return SystemProperties.get(PROPETY_FIRMWARE_VERSION, "Unknown") + VERSION_NAME_POSTFIX;
    }

    public String getFormattedKernelVersion(Context context) {
        return formatKernelVersion(context, Os.uname());
    }

    private String readLine(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(str), 256);
        try {
            return bufferedReader.readLine();
        } finally {
            bufferedReader.close();
        }
    }

    static String formatKernelVersion(Context context, StructUtsname structUtsname) {
        if (structUtsname == null) {
            return "";
        }
        Matcher matcher = Pattern.compile("(#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(structUtsname.version);
        if (!matcher.matches()) {
            Log.e("wen", "Regex did not match on uname version " + structUtsname.version);
            return "";
        }
        return structUtsname.release + WifiKeyParser.MESSAGE_END + matcher.group(1) + " " + matcher.group(2);
    }

    public String getMcuVersion() {
        String str = SystemProperties.get(MCU_VERSION, "UnKnown");
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String[] split = str.split(" +");
        return split.length >= 1 ? split[0] : str;
    }

    public String getSoftwareVersion() {
        return Build.DISPLAY;
    }

    public boolean isAirplaneMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public String getModel() {
        return Build.MODEL + getMsvSuffix();
    }

    private String getMsvSuffix() {
        try {
            return Long.parseLong(readLine("/sys/board_properties/soc/msv"), 16) == 0 ? " (ENGINEERING)" : "";
        } catch (IOException | NumberFormatException unused) {
            return "";
        }
    }

    public int getMacRandomizationMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "wifi_connected_mac_randomization_enabled", 0);
    }

    public void setFontSize(Context context, float f, boolean z) {
        Settings.System.putFloat(context.getContentResolver(), "font_scale", f);
        Logs.d("xpsettings setFontSize " + f);
        XpAccountManager.getInstance().saveFontSize(f, z);
    }

    public float getCurrentFontSize(Context context) {
        return Settings.System.getFloat(context.getContentResolver(), "font_scale", 1.0f);
    }

    public void setSettingProvider(Context context, String str, boolean z) {
        Settings.System.putInt(context.getContentResolver(), str, z ? 1 : 0);
        Logs.d("xpsettings settingprovider set " + str + " " + z);
    }

    public void setUsbSettingProvider(Context context, String str, boolean z) {
        Settings.System.putInt(context.getContentResolver(), str, z ? 0 : -1);
        Logs.d("xpsettings settingprovider set " + str + " " + z);
    }

    public boolean getSettingProvider(Context context, String str, boolean z) {
        int i = Settings.System.getInt(context.getContentResolver(), str, z ? 1 : 0);
        Logs.d("xpsettings settingprovider get " + str + " " + i);
        return i == 1;
    }

    public void setFeedbackInputContent(Context context, String str) {
        GlobalSettingsSharedPreference.setFeedbackInputContent(context, str);
    }

    public String getFeedbackInputContent(Context context) {
        return GlobalSettingsSharedPreference.getFeedbackInputContent(context);
    }

    public String getTboxMcuVersion() {
        return SystemProperties.get("sys.xiaopeng.tboxmcu", "Unknow");
    }

    public String getTboxVersion() {
        return SystemProperties.get("sys.xiaopeng.tbox4G", "Unknow");
    }

    public void setRestElectronics(Context context) {
        Settings.Secure.putString(context.getContentResolver(), XPSettingsConfig.XP_RESET_ELECTRONICS_KEY, XPSettingsConfig.XP_RESET_ELECTRONICS_VALUE);
        Logs.d("xpsettings settingprovider setRestElectronics ");
    }

    public String getRestElectronics(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), XPSettingsConfig.XP_RESET_ELECTRONICS_KEY);
        Logs.d("xpsettings settingprovider getRestElectronics value:" + string);
        return string;
    }

    public void setTtsBroadcastType(Context context, int i) {
        Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.XP_TTS_BROADCAST_TYPE, i);
        Logs.d("xpsettings settingprovider setTtsBroadcastType " + i);
    }

    public int getTtsBroadcastType(Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.XP_TTS_BROADCAST_TYPE, 0);
        Logs.d("xpsettings settingprovider getTtsBroadcastType value:" + i);
        return i;
    }

    public void setRearRowReminder(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), "rear_row_reminder", z ? 1 : 0);
        Logs.d("xpsettings settingprovider setRearRowReminder " + z);
    }

    public boolean getRearRowReminder(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "rear_row_reminder", 0);
        Logs.d("xpsettings settingprovider getRearRowReminder value:" + i);
        return i == 1;
    }

    public boolean hasWiperGearAuto(Context context) {
        String string = Settings.System.getString(context.getContentResolver(), XPSettingsConfig.QS_KEY_WIPER_GEAR_AUTO_EXIST);
        Logs.d("xpsettings settingprovider hasWiperGearAuto value:" + string);
        return string != null && string.length() > 0 && string.charAt(0) == '1';
    }

    public void setFollowedVehicleLostConfig(Context context, String str) {
        Settings.Secure.putString(context.getContentResolver(), XPSettingsConfig.FOLLOWED_VEHICLE_LOST_CONFIG, str);
        Logs.d("xpsettings settingprovider setFollowedVehicleLostConfig value:" + str);
    }

    public String getFollowedVehicleLostConfig(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), XPSettingsConfig.FOLLOWED_VEHICLE_LOST_CONFIG);
        Logs.d("xpsettings settingprovider followedVehicleLostConfig value:" + string);
        return string;
    }

    public void setConnectedBleDevice(Context context, String str) {
        Settings.Secure.putString(context.getContentResolver(), XPSettingsConfig.CONNECTED_BLE_DEVICE, str);
        Logs.d("xpsettings settingprovider setConnectedBleDevice name:" + str);
    }

    public String getConnectedBleDevice(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), XPSettingsConfig.CONNECTED_BLE_DEVICE);
        Logs.d("xpsettings settingprovider getConnectedBleDevice name:" + string);
        return string;
    }

    public void setAvasSpeakerSw(boolean z) {
        Logs.d("xpsettings setAvasSpeakerSw, enable = " + z);
        Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.AVAS_LOUD_SPEAKER_SW, z ? 1 : 0);
    }

    public boolean getAvasSpeakerSw() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.AVAS_LOUD_SPEAKER_SW, 0);
        Logs.d("xpsettings getAvasSpeakerSw, enable = " + i);
        return i == 1;
    }

    public void setScreenFlow(boolean z) {
        Logs.d("xpsettings setScreenFlow, flow = " + z);
        Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), "app_screen_flow", z ? 1 : 0);
    }

    public boolean getScreenFlow() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), "app_screen_flow", 1);
        Logs.d("xpsettings getScreenFlow, enable = " + i);
        return i == 1;
    }

    public void setPsnTtsHeadsetOut(boolean z) {
        Logs.d("xpsettings setPsnTtsHeadsetOut, enable = " + z);
        Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.PSN_TTS_HEADSET_OUT, z ? 1 : 0);
    }

    public boolean getPsnTtsHeadsetOut() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.PSN_TTS_HEADSET_OUT, 0);
        Logs.d("xpsettings getPsnTtsHeadsetOut, enable = " + i);
        return i == 1;
    }

    public void setExternalTtsHeadsetOut(boolean z) {
        Logs.d("xpsettings setExternalTtsHeadsetOut, enable = " + z);
        Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.External_TTS_HEADSET_OUT, z ? 1 : 0);
    }

    public boolean getExternalTtsHeadsetOut() {
        int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.External_TTS_HEADSET_OUT, 0);
        Logs.d("xpsettings getPsnTtsHeadsetOut, enable = " + i);
        return i == 1;
    }

    public int getRearScreenAngle(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_ANGLE);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getRearScreenAngle:" + i);
        return i;
    }

    public void setRearScreenAngle(Context context, int i) {
        try {
            Settings.System.putInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_ANGLE, i);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        Logs.d("xpdisplay setRearScreenAngle:" + i);
    }

    public int getRearScreenStateDetail(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_STATE);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getRearScreenStateDetail:" + i);
        return i;
    }

    public int getRearScreenAngleCallback(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), XPSettingsConfig.REAR_SCREEN_CALLBACK_ANGLE);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            i = 0;
        }
        Logs.d("xpdisplay getRearScreenAngleCallback:" + i);
        return i;
    }
}
