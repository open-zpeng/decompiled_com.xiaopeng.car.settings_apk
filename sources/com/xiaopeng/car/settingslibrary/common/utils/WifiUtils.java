package com.xiaopeng.car.settingslibrary.common.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiConfiguration;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
/* loaded from: classes.dex */
public class WifiUtils {
    private static final int PASSWORD_MAX_LENGTH = 63;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int SSID_ASCII_MAX_LENGTH = 32;
    private static final int SSID_ASCII_MIN_LENGTH = 1;
    private static final String TAG = "WifiUtils";

    public static boolean isSSIDTooLong(String str) {
        return !TextUtils.isEmpty(str) && str.length() > 32;
    }

    public static boolean isSSIDTooShort(String str) {
        return TextUtils.isEmpty(str) || str.length() < 1;
    }

    public static boolean isHotspotPasswordValid(String str) {
        int length;
        return !TextUtils.isEmpty(str) && (length = str.length()) >= 8 && length <= 63;
    }

    public static boolean isNetworkLockedDown(Context context, WifiConfiguration wifiConfiguration) {
        boolean z;
        ComponentName deviceOwnerComponentOnAnyUser;
        if (wifiConfiguration == null) {
            return false;
        }
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature("android.software.device_admin") && devicePolicyManager == null) {
            return true;
        }
        if (devicePolicyManager != null && (deviceOwnerComponentOnAnyUser = devicePolicyManager.getDeviceOwnerComponentOnAnyUser()) != null) {
            if (packageManager.getPackageUidAsUser(deviceOwnerComponentOnAnyUser.getPackageName(), devicePolicyManager.getDeviceOwnerUserId()) == wifiConfiguration.creatorUid) {
                z = true;
                return (z || Settings.Global.getInt(context.getContentResolver(), "wifi_device_owner_configs_lockdown", 0) == 0) ? false : true;
            }
        }
        z = false;
        if (z) {
            return false;
        }
    }

    public static boolean canSignIntoNetwork(NetworkCapabilities networkCapabilities) {
        return networkCapabilities != null && networkCapabilities.hasCapability(17);
    }

    public static void enterAirplaneMode(Context context) {
        if (!isAirplaneModeOn(context)) {
            setAirplaneModeOn(context, true);
            Logs.log(TAG, "entering air plane mode...");
            return;
        }
        Logs.log(TAG, "already in air plane mode!");
    }

    public static void exitAirplaneMode(Context context) {
        if (isAirplaneModeOn(context)) {
            setAirplaneModeOn(context, false);
            Logs.log(TAG, "exiting air plane mode...");
            return;
        }
        Logs.log(TAG, "already out of air plane mode!");
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    private static void setAirplaneModeOn(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", z);
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public static boolean isAccessPointDisabledByWrongPassword(XpAccessPoint xpAccessPoint) {
        return XpWifiManager.shouldEditBeforeConnect(xpAccessPoint);
    }
}
