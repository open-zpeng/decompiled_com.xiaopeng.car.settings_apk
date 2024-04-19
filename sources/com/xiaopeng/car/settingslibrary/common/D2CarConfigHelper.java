package com.xiaopeng.car.settingslibrary.common;

import android.car.Car;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class D2CarConfigHelper {
    private static final int CFC_CODE_HIGH = 3;
    private static final int CFC_CODE_INVALID = 0;
    private static final int CFC_CODE_LOW = 1;
    private static final int CFC_CODE_MIDDLE = 2;
    private static final int CFC_CODE_TRIP = 4;
    private static final boolean IS_DEVELOPING = false;
    private static final String PROPERTY_CFC = "persist.sys.xiaopeng.configCode";
    private static final String TAG = "CarConfigHelper";
    private static final String TYPE_A1 = "A1";
    private static final String TYPE_A2 = "A2";
    private static final String TYPE_A3 = "A3";
    private static final String TYPE_Q1 = "Q1";
    private static final String TYPE_Q2 = "Q2";
    private static Integer sCfcCode;

    private static int getCfcCode() {
        if (sCfcCode == null) {
            String str = SystemProperties.get(PROPERTY_CFC, (String) null);
            int i = 0;
            if (!TextUtils.isEmpty(str)) {
                try {
                    i = Integer.parseInt(str);
                } catch (Exception unused) {
                }
            }
            if (i == 0) {
                i = 1;
            }
            sCfcCode = Integer.valueOf(i);
        }
        Logs.d("CarConfigHelper getCfcCode: " + sCfcCode);
        return sCfcCode.intValue();
    }

    public static String getCarType() {
        try {
            return Car.getXpCduType();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not getXpCduType error = " + e);
            return "Q1";
        }
    }

    public static boolean isLowConfig() {
        return getCfcCode() == 1;
    }

    public static boolean isMiddleConfig() {
        return getCfcCode() == 2;
    }

    public static boolean isHighConfig() {
        return getCfcCode() == 3;
    }

    public static boolean isTripVersion() {
        try {
            String hardwareCarType = Car.getHardwareCarType();
            String xpCduType = Car.getXpCduType();
            String hardwareCarStage = Car.getHardwareCarStage();
            LogUtils.d(TAG, "isTripVersion carType: " + hardwareCarType + ", cduType: " + xpCduType + ", carStage: " + hardwareCarStage, false);
            if (getCfcCode() == 4) {
                if ("A2".equals(xpCduType) || "A3".equals(xpCduType)) {
                    return true;
                }
                if ("Q2".equals(xpCduType)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), false);
            return false;
        }
    }

    public static boolean isSupportMirrorDown() {
        String xpCduType = Car.getXpCduType();
        return getCfcCode() == 3 && ("A2".equals(xpCduType) || "A3".equals(xpCduType) || "Q2".equals(xpCduType));
    }

    public static boolean isSupportAutoPark() {
        int cfcCode = getCfcCode();
        String xpCduType = Car.getXpCduType();
        return (cfcCode == 3 || cfcCode == 2) && ("A2".equals(xpCduType) || "A3".equals(xpCduType) || "Q2".equals(xpCduType));
    }
}
