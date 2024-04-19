package com.xiaopeng.car.settingslibrary.common.utils;

import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
/* loaded from: classes.dex */
public class Logs {
    private static final String TAG = "xpsettings";

    public static void log(String str, String str2) {
        LogUtils.i(TAG, str + WifiKeyParser.MESSAGE_SPLIT + str2);
    }

    public static void logv(String str, String str2) {
        LogUtils.v(TAG, str + WifiKeyParser.MESSAGE_SPLIT + str2);
    }

    public static void logObj(String str, String str2, Object obj) {
        if (obj == null) {
            log(str, str2);
            return;
        }
        LogUtils.i(TAG, str + WifiKeyParser.MESSAGE_SPLIT + obj.getClass().getSimpleName() + " " + str2 + " " + obj.hashCode());
    }

    public static void d(String str) {
        LogUtils.i(str);
    }

    public static void v(String str) {
        LogUtils.v(str);
    }
}
