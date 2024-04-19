package com.xiaopeng.car.settingslibrary.common.utils;

import android.car.Car;
import android.os.SystemProperties;
import com.xiaopeng.xvs.xid.BuildConfig;
/* loaded from: classes.dex */
public class CarStatusUtils {
    private static final String TAG = "CarVersionUtil";
    private static Boolean mIsE28;

    public static String getHardwareCarType() {
        return SystemProperties.get("ro.product.model", "");
    }

    public static String getCarStageVersion() {
        try {
            return Car.getHardwareCarStage();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not  getCarStageVersion error = " + e);
            return "";
        }
    }

    public static boolean isE28CarType() {
        if (mIsE28 == null) {
            mIsE28 = Boolean.valueOf(BuildConfig.LIB_PRODUCT.equals(getHardwareCarType()));
        }
        return mIsE28.booleanValue();
    }
}
