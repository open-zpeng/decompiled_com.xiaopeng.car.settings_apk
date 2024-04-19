package com.xiaopeng.car.settingslibrary.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
/* loaded from: classes.dex */
public class GlobalSettingsSharedPreference {
    public static final String DTS_DEFAULT_SET = "dts_default_set";
    public static final String PREF_FILE = "XpSettings";
    public static final String VERSION_NAME = "version_name";

    public static SharedPreferences getGlobalPreference(Context context) {
        return context.getSharedPreferences(PREF_FILE, 0);
    }

    public static void setFeedbackInputContent(Context context, String str) {
        getGlobalPreference(context).edit().putString(Config.FEEDBACK_INPUT_CONTENT, str).apply();
    }

    public static String getFeedbackInputContent(Context context) {
        return getGlobalPreference(context).getString(Config.FEEDBACK_INPUT_CONTENT, "");
    }

    public static void setD21RemoteParking(Context context, boolean z) {
        getGlobalPreference(context).edit().putBoolean(Config.D21_REMOTE_PARKING, z).apply();
    }

    public static boolean getD21RemoteParking(Context context) {
        return getGlobalPreference(context).getBoolean(Config.D21_REMOTE_PARKING, CarFunction.isSupportChangeCdu());
    }

    public static void setPreferenceForKey(Context context, String str, boolean z) {
        getGlobalPreference(context).edit().putBoolean(str, z).commit();
    }

    public static boolean getPreferenceForKey(Context context, String str, boolean z) {
        return getGlobalPreference(context).getBoolean(str, z);
    }

    public static int getIntelligentHeadrestRestore(Context context) {
        return getGlobalPreference(context).getInt(Config.KEY_INTELLIGENT_HEADREST_RESTORE, -1);
    }

    public static void setIntelligentHeadrestRestore(Context context, int i) {
        getGlobalPreference(context).edit().putInt(Config.KEY_INTELLIGENT_HEADREST_RESTORE, i).apply();
    }

    public static void setPreferenceForKeyInt(Context context, String str, int i) {
        getGlobalPreference(context).edit().putInt(str, i).commit();
    }

    public static int getPreferenceForKeyInt(Context context, String str, int i) {
        return getGlobalPreference(context).getInt(str, i);
    }

    public static void setPreferenceForKeyString(Context context, String str, String str2) {
        getGlobalPreference(context).edit().putString(str, str2).commit();
    }

    public static String getPreferenceForKeyString(Context context, String str, String str2) {
        return getGlobalPreference(context).getString(str, str2);
    }

    public static void setPreferenceForKeyLong(Context context, String str, long j) {
        getGlobalPreference(context).edit().putLong(str, j).commit();
    }

    public static long getPreferenceForKeyLong(Context context, String str, long j) {
        return getGlobalPreference(context).getLong(str, j);
    }
}
