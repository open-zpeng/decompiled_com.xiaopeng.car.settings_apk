package com.xiaopeng.car.settingslibrary.common.utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import com.xiaopeng.libtheme.ThemeManager;
/* loaded from: classes.dex */
public class XpThemeUtils {
    public static boolean isThemeChanged(Configuration configuration) {
        if (configuration != null) {
            return ThemeManager.isThemeChanged(configuration);
        }
        return false;
    }

    public static void setThemeChanged(Context context, View view, String str, Configuration configuration) {
        ThemeManager.create(context, view, str, null).onConfigurationChanged(configuration);
    }

    public static void setDayNightMode(Context context, int i) {
        ((UiModeManager) context.getSystemService("uimode")).applyDayNightMode(i);
    }

    public static boolean isThemeSwitching(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).isThemeWorking();
    }

    public static int getDayNightAutoMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightAutoMode();
    }

    public static int getDayNightMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightMode();
    }

    public static void setWindowBackgroundResource(Configuration configuration, Window window, int i) {
        ThemeManager.setWindowBackgroundResource(configuration, window, i);
    }

    public static void setThemeMode(Context context, int i) {
        ((UiModeManager) context.getSystemService("uimode")).applyThemeMode(i);
    }

    public static int getThemeMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getThemeMode();
    }

    public static boolean isNightMode(Context context) {
        return ThemeManager.isNightMode(context);
    }
}
