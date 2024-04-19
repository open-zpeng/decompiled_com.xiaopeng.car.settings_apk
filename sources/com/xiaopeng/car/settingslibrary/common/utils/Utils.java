package com.xiaopeng.car.settingslibrary.common.utils;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.WindowManager;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.IpcRouterService;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.service.GlobalService;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.InputDialogWork;
import com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog;
import com.xiaopeng.libconfig.ipc.bean.MessageCenterBean;
import com.xiaopeng.libconfig.ipc.bean.MessageContentBean;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.view.WindowManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class Utils {
    public static final String ACTION_UNINSTALL_COMPLETE = "com.xiaopeng.xpstore.UNINSTALL_COMPLETE";
    private static final String AUTHORITY_AI = "com.xiaopeng.aiassistant.IpcRouterService";
    private static final int BIZTYPE = 35;
    private static final String BUNDLE = "bundle";
    private static final long DEFAULT_MESSAGE_VALID_TIME = 1800000;
    private static final String ID = "id";
    private static final String PATH = "onReceiverData";
    public static final String SIZE_FORMAT = "%.1f";
    private static final String STRING_MSG = "string_msg";
    public static final int UI_MODE_THEME_CHANGED = 128;
    public static final int UI_MODE_THEME_MASK = 192;
    private static String sPermissionControllerPackageName;
    private static String sServicesSystemSharedLibPackageName;
    private static String sSharedSystemSharedLibPackageName;
    private static Signature[] sSystemSignature;

    /* loaded from: classes.dex */
    public static abstract class PermissionsResultCallback {
        public void onAppWithPermissionsCountsResult(int i, int i2) {
        }

        public void onPermissionSummaryResult(int i, int i2, int i3, List<CharSequence> list) {
        }
    }

    public static int getManagedProfileId(UserManager userManager, int i) {
        int[] profileIdsWithDisabled;
        for (int i2 : userManager.getProfileIdsWithDisabled(i)) {
            if (i2 != i) {
                return i2;
            }
        }
        return -10000;
    }

    public static boolean isSystemPackage(Resources resources, PackageManager packageManager, PackageInfo packageInfo) {
        if (sSystemSignature == null) {
            sSystemSignature = new Signature[]{getSystemSignature(packageManager)};
        }
        if (sPermissionControllerPackageName == null) {
            sPermissionControllerPackageName = packageManager.getPermissionControllerPackageName();
        }
        if (sServicesSystemSharedLibPackageName == null) {
            sServicesSystemSharedLibPackageName = packageManager.getServicesSystemSharedLibraryPackageName();
        }
        if (sSharedSystemSharedLibPackageName == null) {
            sSharedSystemSharedLibPackageName = packageManager.getSharedSystemSharedLibraryPackageName();
        }
        Signature[] signatureArr = sSystemSignature;
        return (signatureArr[0] != null && signatureArr[0].equals(getFirstSignature(packageInfo))) || packageInfo.packageName.equals(sPermissionControllerPackageName) || packageInfo.packageName.equals(sServicesSystemSharedLibPackageName) || packageInfo.packageName.equals(sSharedSystemSharedLibPackageName) || packageInfo.packageName.equals("com.android.printspooler") || isDeviceProvisioningPackage(resources, packageInfo.packageName);
    }

    private static Signature getSystemSignature(PackageManager packageManager) {
        try {
            return getFirstSignature(packageManager.getPackageInfo("android", 64));
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    private static Signature getFirstSignature(PackageInfo packageInfo) {
        if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
            return null;
        }
        return packageInfo.signatures[0];
    }

    public static boolean isDeviceProvisioningPackage(Resources resources, String str) {
        String string = resources.getString(17039720);
        return string != null && string.equals(str);
    }

    public static String getAppSizeString(double d) {
        if (d <= 0.0d) {
            return "";
        }
        double d2 = 1024;
        double d3 = d / d2;
        if (d3 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d)) + "B";
        }
        double d4 = d3 / d2;
        if (d4 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d3)) + "KB";
        }
        double d5 = d4 / d2;
        if (d5 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d4)) + "MB";
        } else if (d5 / d2 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d5)) + "GB";
        } else {
            return "";
        }
    }

    public static String getFormatSize(double d) {
        return getFormatSize(d, 2);
    }

    public static String getFormatSize(double d, int i) {
        if (d <= 0.0d) {
            return "";
        }
        double d2 = 1024;
        double d3 = d / d2;
        if (d3 < 1.0d) {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
            return bigDecimal.setScale(i, 1).toPlainString() + "B";
        }
        double d4 = d3 / d2;
        if (d4 < 1.0d) {
            BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d3));
            return bigDecimal2.setScale(i, 1).toPlainString() + "KB";
        }
        double d5 = d4 / d2;
        if (d5 < 1.0d) {
            BigDecimal bigDecimal3 = new BigDecimal(Double.toString(d4));
            return bigDecimal3.setScale(i, 1).toPlainString() + "MB";
        } else if (d5 / d2 < 1.0d) {
            BigDecimal bigDecimal4 = new BigDecimal(Double.toString(d5));
            return bigDecimal4.setScale(i, 1).toPlainString() + "GB";
        } else {
            return "";
        }
    }

    public static void startCleanMode(Context context) {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        try {
            Intent intent = new Intent("xiaopeng.car.settings.clean_mode");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("no activity found:" + e);
            e.printStackTrace();
        }
    }

    public static void startWaitMode(Context context) {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        try {
            Intent intent = new Intent("xiaopeng.car.settings.wait_mode");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("no activity found:" + e);
            e.printStackTrace();
        }
    }

    public static void startSoundEffectSetting() {
        AppServerManager.getInstance().onPopupSoundEffect();
    }

    public static void dismissSoundEffectDialog(Context context) {
        try {
            Intent intent = new Intent(context, GlobalService.class);
            intent.setAction(Config.SOUND_EFFECT_DISMISS_ACTION);
            context.startService(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("no activity found:" + e);
            e.printStackTrace();
        }
    }

    public static long getLocalVersion(Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getLocalVersionName(Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isUserRelease() {
        return "user".equals(Build.TYPE);
    }

    public static boolean isMonkeyRunning() {
        return ActivityManager.isUserAMonkey();
    }

    public static boolean isDeviceProvisioned(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static void startToSpeechSet(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("xiaopeng://carspeechservice/speechmain?from=setting"));
            intent.addFlags(268435968);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int convertDangerousTtsVolume(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    Logs.d("this is a error:" + i);
                    return i;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static boolean isPowerSavingMode() {
        int nedcSwitchStatus = CarSettingsManager.getInstance().getNedcSwitchStatus();
        if (CarConfigHelper.autoBrightness() == 0) {
            if (nedcSwitchStatus == 1 || nedcSwitchStatus == 3 || nedcSwitchStatus == 4 || nedcSwitchStatus == 5) {
                Logs.d("isXpuNedcEnable return!");
                return true;
            }
            return false;
        }
        return false;
    }

    public static String formatBytesSize(Context context, long j) {
        return Formatter.formatFileSize(context, j);
    }

    public static boolean isSystemApp(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (str != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                if (packageInfo != null && packageInfo.applicationInfo != null) {
                    if ((packageInfo.applicationInfo.flags & 1) != 0) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean isPhone(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && getBluetoothCategory(bluetoothDevice.getBluetoothClass()) == 1;
    }

    public static boolean isHidDevice(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && getBluetoothCategory(bluetoothDevice.getBluetoothClass()) == 2;
    }

    public static int getBluetoothCategory(BluetoothClass bluetoothClass) {
        if (bluetoothClass == null) {
            return -1;
        }
        int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
        Logs.d("xpbluetooth nf getBluetoothCategory:" + majorDeviceClass);
        if (majorDeviceClass != 512) {
            if (majorDeviceClass != 1280) {
                return -1;
            }
            Logs.d("xpbluetooth nf getBluetoothCategory:" + bluetoothClass.getDeviceClass());
            return 2;
        }
        return 1;
    }

    public static boolean isAppInMainScreen(Context context, String str) {
        return WindowManager.isPrimaryId(((WindowManager) context.getSystemService("window")).getSharedId(str));
    }

    public static boolean isAppInPsnScreen(Context context, String str) {
        return ((WindowManager) context.getSystemService("window")).getSharedId(str) == 1;
    }

    public static boolean isCurrentAppInMainScreen(Context context) {
        return isAppInMainScreen(context, context.getPackageName());
    }

    public static boolean isCurrentAppInPsnScreen(Context context) {
        return isAppInPsnScreen(context, context.getPackageName());
    }

    public static void setPsnScreenOn(Context context, boolean z) {
        if (Config.IS_SDK_HIGHER_P) {
            PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
            if (z) {
                powerManager.setXpScreenOn("xp_mt_psg", SystemClock.uptimeMillis());
            } else {
                powerManager.setXpScreenOff("xp_mt_psg", SystemClock.uptimeMillis());
            }
        }
    }

    public static void setDoubleScreenOnOff(Context context, boolean z) {
        if (Config.IS_SDK_HIGHER_P) {
            PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
            if (z) {
                powerManager.setXpScreenOn("xp_mt_ivi", SystemClock.uptimeMillis());
                powerManager.setXpScreenOn("xp_mt_psg", SystemClock.uptimeMillis());
            } else {
                powerManager.setXpScreenOff("xp_mt_ivi", SystemClock.uptimeMillis());
                powerManager.setXpScreenOff("xp_mt_psg", SystemClock.uptimeMillis());
            }
            Logs.d("Utils setDoubleScreenOnOff " + z);
        }
    }

    public static boolean isPsnScreenOn(Context context) {
        if (Config.IS_SDK_HIGHER_P) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).isScreenOn("xp_mt_psg");
        }
        return false;
    }

    public static boolean isPsnIdleOn(Context context) {
        if (Config.IS_SDK_HIGHER_P) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).xpIsScreenIdle("xp_mt_psg");
        }
        return false;
    }

    public static void slideCurrentAppToScreen(Context context, int i) {
        WindowManagerFactory.create(context).setSharedEvent(0, i);
    }

    public static void setXpIcmScreenOnOff(boolean z) {
        PowerManager powerManager = (PowerManager) CarSettingsApp.getContext().getSystemService(PowerManager.class);
        if (z) {
            powerManager.setXpIcmScreenOn(SystemClock.uptimeMillis());
        } else {
            powerManager.setXpIcmScreenOff(SystemClock.uptimeMillis());
        }
    }

    public static boolean isFilterDevice(BluetoothDevice bluetoothDevice) {
        BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
        if (bluetoothClass == null) {
            return false;
        }
        if (isUserRelease() || !"1".equals(SystemProperties.get("xpsettings.bluetooth.no.filter"))) {
            Logs.d("xpbluetooth nf onDeviceFound filter device " + bluetoothDevice.getAddress() + " MajorDeviceClass:" + bluetoothClass.getMajorDeviceClass() + " DeviceClass:" + bluetoothClass.getDeviceClass());
            return bluetoothClass.getMajorDeviceClass() == 2304 || bluetoothClass.getMajorDeviceClass() == 1792 || bluetoothClass.getMajorDeviceClass() == 7936;
        }
        return false;
    }

    public static boolean isTopActivity(String str, int i) {
        String topActivity = WindowManagerFactory.create(CarSettingsApp.getContext()).getTopActivity(1, i);
        return !TextUtils.isEmpty(topActivity) && topActivity.contains(str);
    }

    public static void popupBluetoothNameEdit() {
        Intent intent = new Intent(CarSettingsApp.getContext(), UIGlobalService.class);
        intent.setAction(InputDialogWork.INPUT_DIALOG);
        intent.putExtra(InputDialog.EXTRA_INPUT_TYPE, 1);
        intent.putExtra(InputDialog.EXTRA_REQUEST_TITLE_NAME, XpBluetoothManger.getInstance().getName());
        CarSettingsApp.getContext().startService(intent);
    }

    public static void popupToScreenId(Dialog dialog, int i) {
        ReflectUtils.invokeMethod(dialog, "setScreenId", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }

    public static int getPopScreenId(Dialog dialog) {
        return ((Integer) ReflectUtils.invokeMethod(dialog, "getScreenId", null, null)).intValue();
    }

    public static void setInputMethodPosition(int i) {
        try {
            WindowManagerFactory create = WindowManagerFactory.create(CarSettingsApp.getContext());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("function", "setInputMethodPosition");
            jSONObject.put("position", i);
            jSONObject.put(VuiConstants.SCENE_PACKAGE_NAME, "com.xiaopeng.car.settings");
            Logs.d("setInputMethodPosition " + i);
            create.setSharedEvent(8, -1, jSONObject.toString());
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static boolean isVideoRecorder(String str) {
        return convertToQuotedString(str).matches(SystemProperties.get(Config.SYS_PROP_VIDEO_RECORDER, ""));
    }

    private static String convertToQuotedString(String str) {
        return "\"" + str + "\"";
    }

    public static void sendMessageToMessageCenter(int i, String str, String str2, String str3, String str4, String str5, String str6, boolean z, long j, boolean z2) {
        MessageContentBean createContent = MessageContentBean.createContent();
        createContent.setType(1);
        createContent.setValidTime(0L);
        createContent.addTitle(str);
        createContent.addTitle(str2);
        createContent.setTts(str3);
        String packageName = CarSettingsApp.getContext().getPackageName();
        MessageContentBean.MsgButton create = MessageContentBean.MsgButton.create(str6, packageName, i + "");
        create.setSpeechResponse(true);
        create.setResponseWord(str4);
        create.setResponseTTS(str5);
        createContent.addButton(create);
        long currentTimeMillis = System.currentTimeMillis();
        if (j <= 0) {
            j = DEFAULT_MESSAGE_VALID_TIME;
        }
        long j2 = j + currentTimeMillis;
        if (!z) {
            createContent.setValidTime(j2);
        }
        if (z2) {
            createContent.setPermanent(1);
        }
        MessageCenterBean create2 = MessageCenterBean.create(35, createContent);
        create2.setScene(i);
        create2.setValidStartTs(currentTimeMillis);
        create2.setValidEndTs(j2);
        IpcRouterService.sendMessageToMessageCenter(i, create2);
    }

    public static void sendMessageToNotification(int i, String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Class<?> cls) {
        String[] strArr = {str4};
        NotificationManager notificationManager = (NotificationManager) CarSettingsApp.getContext().getSystemService("notification");
        NotificationChannel notificationChannel = new NotificationChannel("com.xiaopeng.car.settings", "com.xiaopeng.car.settings", 3);
        Intent intent = new Intent(CarSettingsApp.getContext(), cls);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, new Notification.Builder(CarSettingsApp.getContext(), "com.xiaopeng.car.settings").setAppName(CarSettingsApp.getContext().getString(R.string.notify_app_name)).setDisplayFlag(32).setContentTitle(str).setContentText(str2).setTtsText(str3).setSpeechWordListForActionOne(strArr).setSmallIcon(R.mipmap.ic_launcher).setSystemMsg(1).setMajorType("default").setMsgTmp(z ? 1 : 0).setShowLong(z2 ? 1 : 0).setViewStyle(new Notification.ViewStyle().setStyle(1)).addAction(0, str5, PendingIntent.getActivity(CarSettingsApp.getContext(), 0, intent, NfDef.PBAP_PROPERTY_MASK_CLASS)).setAutoCancel(false).build());
    }

    public static void cancelStoreMsgToMessageCenter() {
        String preferenceForKeyString = GlobalSettingsSharedPreference.getPreferenceForKeyString(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_ID, "");
        if (TextUtils.isEmpty(preferenceForKeyString)) {
            Logs.d("xpsettings cancelSendInfoFlowMsg null");
            return;
        }
        IpcRouterService.cancelSendMsgToMessageCenter(preferenceForKeyString);
        GlobalSettingsSharedPreference.setPreferenceForKeyString(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_ID, "");
    }

    public static boolean uninstall(Context context, String str, int i) {
        Logs.d("uninstall start, pn is [%s]." + str);
        context.getPackageManager().getPackageInstaller().uninstall(str, createUninstallIntentSender(context, str, i));
        Logs.d("uninstall finish, pn is [%s]." + str);
        return true;
    }

    private static IntentSender createUninstallIntentSender(Context context, String str, int i) {
        Intent intent = new Intent(ACTION_UNINSTALL_COMPLETE);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        return PendingIntent.getBroadcast(context, i, intent, NfDef.PBAP_PROPERTY_MASK_SORT_STRING).getIntentSender();
    }
}
