package com.xiaopeng.car.settingslibrary.common;

import android.car.Car;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import java.util.HashMap;
/* loaded from: classes.dex */
public class CarConfigHelper {
    public static final int CONFIG_CODE_01 = 1;
    public static final int CONFIG_CODE_02 = 2;
    public static final int CONFIG_CODE_03 = 3;
    public static final int CONFIG_CODE_04 = 4;
    public static final int CONFIG_CODE_05 = 5;
    public static final int CONFIG_CODE_06 = 6;
    public static final int CONFIG_CODE_07 = 7;
    public static final int CONFIG_CODE_08 = 8;
    public static final int CONFIG_CODE_09 = 9;
    public static final int HAS_CIU = 1;
    public static final int HAS_XPU = 0;
    public static final int INVALID = 0;
    public static final String PROPERTY_AMP = "persist.sys.xiaopeng.AMP";
    public static final String PROPERTY_AQS = "persist.sys.xiaopeng.AQS";
    public static final String PROPERTY_ATLS = "persist.sys.xiaopeng.ATLS";
    public static final String PROPERTY_AUDIO_VENDOR = "persist.sys.xiaopeng.audio.vendor";
    public static final String PROPERTY_AVM = "persist.sys.xiaopeng.AVM";
    private static final String PROPERTY_CFC = "persist.sys.xiaopeng.cfcIndex";
    public static final String PROPERTY_CWC = "persist.sys.xiaopeng.CWC";
    private static final String PROPERTY_DOLBY = "persist.sys.xiaopeng.DOLBY";
    public static final String PROPERTY_DTS = "persist.audio.dts.support";
    public static final String PROPERTY_IMU = "persist.sys.xiaopeng.IMU";
    public static final String PROPERTY_IPUF = "persist.sys.xiaopeng.IPUF";
    public static final String PROPERTY_IPUR = "persist.sys.xiaopeng.IPUR";
    private static final String PROPERTY_LEVEL = "persist.sys.xiaopeng.cfcVehicleLevel";
    public static final String PROPERTY_LLU = "persist.sys.xiaopeng.LLU";
    public static final String PROPERTY_MIRROR = "persist.sys.xiaopeng.MIRROR";
    public static final String PROPERTY_MRR = "persist.sys.xiaopeng.MRR";
    public static final String PROPERTY_MSB = "persist.sys.xiaopeng.MSB";
    public static final String PROPERTY_MSMD = "persist.sys.xiaopeng.MSMD";
    public static final String PROPERTY_MSMP = "persist.sys.xiaopeng.MSMP";
    public static final String PROPERTY_NFC = "persist.sys.xiaopeng.NFC";
    private static final String PROPERTY_NOT_SUPPORT = "0";
    public static final String PROPERTY_PACKAGE_1 = "persist.sys.xiaopeng.Package1";
    public static final String PROPERTY_PACKAGE_2 = "persist.sys.xiaopeng.Package2";
    public static final String PROPERTY_PACKAGE_3 = "persist.sys.xiaopeng.Package3";
    public static final String PROPERTY_PACKAGE_4 = "persist.sys.xiaopeng.Package4";
    public static final String PROPERTY_PAS = "persist.sys.xiaopeng.PAS";
    private static final String PROPERTY_REAR_SCREEN = "persist.sys.xiaopeng.SFM";
    public static final String PROPERTY_RLS = "persist.sys.xiaopeng.RLS";
    public static final String PROPERTY_SCU = "persist.sys.xiaopeng.SCU";
    public static final String PROPERTY_SHC = "persist.sys.xiaopeng.SHC";
    private static final String PROPERTY_SPEAKER = "persist.sys.xiaopeng.SPEAKER";
    public static final String PROPERTY_SRR_FL = "persist.sys.xiaopeng.SRR_FL";
    public static final String PROPERTY_SRR_FR = "persist.sys.xiaopeng.SRR_FR";
    public static final String PROPERTY_SRR_RL = "persist.sys.xiaopeng.SRR_RL";
    public static final String PROPERTY_SRR_RR = "persist.sys.xiaopeng.SRR_RR";
    private static final String PROPERTY_SUPPORT = "1";
    public static final String PROPERTY_VPM = "persist.sys.xiaopeng.VPM";
    public static final String PROPERTY_XPU = "persist.sys.xiaopeng.XPU";
    private static final String TAG = "CarConfigHelper";
    public static final int UNKNOW = -1;
    private static Integer sCfcCode;
    private static HashMap<String, Boolean> sFeatureSupport = new HashMap<>();
    private static Boolean mHasCiuDevice = null;
    private static String sCarLevel = null;

    public static boolean hasFeature(String str) {
        if (SystemProperties.getBoolean("xiaopeng.settings.testmode", false)) {
            return true;
        }
        Boolean bool = sFeatureSupport.get(str);
        if (bool == null) {
            bool = Boolean.valueOf("1".equals(SystemProperties.get(str, "0")));
            sFeatureSupport.put(str, bool);
            StringBuilder sb = new StringBuilder();
            sb.append("The car");
            sb.append(bool.booleanValue() ? " support feature: " : " not support feature: ");
            sb.append(str);
            LogUtils.i(TAG, sb.toString(), false);
        }
        return bool.booleanValue();
    }

    public static int autoBrightness() {
        if (hasFeature(PROPERTY_XPU)) {
            return 0;
        }
        return hasCiu() ? 1 : -1;
    }

    public static boolean hasCiu() {
        if (mHasCiuDevice == null) {
            mHasCiuDevice = Boolean.valueOf(CarSettingsManager.getInstance().hasCiuDevice());
        }
        return mHasCiuDevice.booleanValue();
    }

    public static boolean hasXpu() {
        return hasFeature(PROPERTY_XPU);
    }

    public static boolean hasAutoBrightness() {
        return -1 != autoBrightness();
    }

    public static String getCarLevel() {
        if (sCarLevel == null) {
            sCarLevel = SystemProperties.get(PROPERTY_LEVEL);
            Logs.d("CarConfigHelper getCarLevel:" + sCarLevel);
        }
        return sCarLevel;
    }

    public static boolean hasSoundArround() {
        return hasFeature(PROPERTY_AMP);
    }

    public static boolean hasHifiSound() {
        return hasFeature(PROPERTY_AMP);
    }

    public static boolean hasMainDriverVIP() {
        return hasFeature(PROPERTY_AMP);
    }

    public static boolean hasAMP() {
        return hasFeature(PROPERTY_AMP);
    }

    public static boolean hasKeyRemotePark() {
        return hasFeature(PROPERTY_SCU);
    }

    public static boolean hasAutoDrive() {
        return hasFeature(PROPERTY_SCU);
    }

    public static boolean hasSayHi() {
        return hasFeature(PROPERTY_LLU);
    }

    public static boolean hasAvm() {
        return hasFeature(PROPERTY_AVM);
    }

    public static boolean hasParkLightRelatedFMBLight() {
        return hasFeature(PROPERTY_LLU);
    }

    public static boolean hasSeatMemeory() {
        return hasFeature(PROPERTY_MSMD);
    }

    public static boolean hasRearMirrorFold() {
        return hasFeature(PROPERTY_MIRROR);
    }

    public static boolean hasPackage1() {
        return hasFeature(PROPERTY_PACKAGE_1);
    }

    public static boolean hasPackage2() {
        return hasFeature(PROPERTY_PACKAGE_2);
    }

    public static boolean hasPackage3() {
        return hasFeature(PROPERTY_PACKAGE_3);
    }

    public static boolean hasDtsScenes() {
        return SystemProperties.getBoolean(PROPERTY_DTS, false);
    }

    public static int getAudioVendor() {
        return SystemProperties.getInt(PROPERTY_AUDIO_VENDOR, 0);
    }

    public static boolean isLowSpeaker() {
        String str = SystemProperties.get(PROPERTY_SPEAKER);
        return !TextUtils.isEmpty(str) && str.equals("4");
    }

    public static String getSpeakerCount() {
        return SystemProperties.get(PROPERTY_SPEAKER);
    }

    public static String getHardwareCarType() {
        try {
            return Car.getHardwareCarType();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not get HardwareCarType error = " + e);
            return "";
        }
    }

    public static String getXpCduType() {
        try {
            return Car.getXpCduType();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not get HardwareCarType error = " + e);
            return "";
        }
    }

    public static String getRegionType() {
        try {
            return Car.getVersionInCountryCode();
        } catch (Throwable th) {
            LogUtils.e(TAG, "can not get VersionInCountryCode error = " + th);
            return "";
        }
    }

    public static boolean hasDolby() {
        return "1".equals(SystemProperties.get(PROPERTY_DOLBY));
    }

    public static boolean hasRearScreen() {
        return hasFeature(PROPERTY_REAR_SCREEN);
    }

    public static boolean hasLightLanguage() {
        return hasFeature(PROPERTY_LLU);
    }
}
