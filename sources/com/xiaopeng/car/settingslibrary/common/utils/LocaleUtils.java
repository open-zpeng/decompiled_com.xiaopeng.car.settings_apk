package com.xiaopeng.car.settingslibrary.common.utils;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.res.Configuration;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
/* loaded from: classes.dex */
public class LocaleUtils {
    private static final String TAG = "localeUtils";
    private static String[] sCurrentCalls;

    /* loaded from: classes.dex */
    public static class CALLS {
        public static final String[] RESCUE_CALLS_NO = {"+47 80017060"};
        public static final String[] RESCUE_CALLS_SE = {"+46 812160608"};
        public static final String[] RESCUE_CALLS_DK = {"+45 78724343"};
        public static final String[] RESCUE_CALLS_NL = {"+31 202626822"};
    }

    public static void setLocale(String str) {
        setSystemLocale(new Locale(Locale.getDefault().getLanguage(), str));
    }

    public static void setLanguage(String str) {
        setSystemLocale(new Locale(str, Locale.getDefault().getCountry()));
    }

    public static String getCurrentLanguageName() {
        return Locale.getDefault().getLanguage();
    }

    public static String getCountryCode() {
        return Locale.getDefault().getCountry();
    }

    public static String getNumberSample(Locale locale) {
        return NumberFormat.getNumberInstance(locale).format(123456L);
    }

    public static String getDateSample(Locale locale) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 8, 13);
        return DateFormat.getDateInstance(2, locale).format(calendar.getTime());
    }

    public static void setSystemLocale(Locale locale) {
        try {
            IActivityManager service = ActivityManager.getService();
            Configuration configuration = service.getConfiguration();
            configuration.setLocale(locale);
            configuration.userSetLocale = true;
            service.updatePersistentConfiguration(configuration);
            BackupManager.dataChanged("com.android.providers.settings");
            LogUtils.d(TAG, "change language", "success!");
        } catch (Exception e) {
            LogUtils.d(TAG, "change language", "error-->", e);
        }
    }

    public static boolean isNorway() {
        return "NO".equalsIgnoreCase(Locale.getDefault().getCountry());
    }

    public static String[] getRescueCalls() {
        char c;
        String country = Locale.getDefault().getCountry();
        int hashCode = country.hashCode();
        if (hashCode == 2183) {
            if (country.equals("DK")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 2494) {
            if (country.equals("NL")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 2497) {
            if (hashCode == 2642 && country.equals("SE")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (country.equals("NO")) {
                c = 3;
            }
            c = 65535;
        }
        if (c == 0) {
            sCurrentCalls = CALLS.RESCUE_CALLS_DK;
        } else if (c == 1) {
            sCurrentCalls = CALLS.RESCUE_CALLS_NL;
        } else if (c == 2) {
            sCurrentCalls = CALLS.RESCUE_CALLS_SE;
        } else {
            sCurrentCalls = CALLS.RESCUE_CALLS_NO;
        }
        return sCurrentCalls;
    }
}
