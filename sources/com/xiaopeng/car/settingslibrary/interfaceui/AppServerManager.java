package com.xiaopeng.car.settingslibrary.interfaceui;

import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaopeng.app.ActivityManagerFactory;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.CountDownBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.BluetoothUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.pair.BluetoothPairingManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.AutoPowerOffAdapter;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class AppServerManager extends ServerBaseManager implements AutoPowerOffAdapter.IAutoPowerOffChangeListener {
    public static final String TAG = "AppServerManager";
    private IAppUIListener mAppUIListener;
    private ConcurrentHashMap<String, Integer> mCurrentShowingDialogs = new ConcurrentHashMap<>();

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final AppServerManager instance = new AppServerManager();

        private InnerFactory() {
        }
    }

    public static AppServerManager getInstance() {
        return InnerFactory.instance;
    }

    public void setListener(IAppUIListener iAppUIListener) {
        this.mAppUIListener = iAppUIListener;
    }

    public void setCarOutSideVolume(int i) {
        if (Config.IS_SDK_HIGHER_P) {
            debugLog("AppServerManager setCarOutSideVolume " + i);
            SoundManager.getInstance().setStreamVolume(Config.AVAS_STREAM, i, 4);
            return;
        }
        debugLog("AppServerManager setCarOutSideVolume " + i + "not support: " + Build.VERSION.SDK_INT);
    }

    public int getCarOutSideVolume() {
        if (Config.IS_SDK_HIGHER_P) {
            int streamVolume = SoundManager.getInstance().getStreamVolume(Config.AVAS_STREAM);
            debugLog("AppServerManager getCarOutSideVolume " + streamVolume);
            return streamVolume;
        }
        debugLog("AppServerManager getCarOutSideVolume, not support " + Build.VERSION.SDK_INT);
        return 0;
    }

    public void onAuthModeAction(String str) {
        log("AppServerManager onAuthModeAction " + str);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onAuthModeAction(str);
        }
        appCallback(InterfaceConstant.ON_AUTH_MODE_ACTION, str);
    }

    public void onAuthModeCloseAction() {
        log("AppServerManager onAuthModeCloseAction ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onAuthModeCloseAction();
        }
        appCallback(InterfaceConstant.ON_AUTH_MODE_CLOSE_ACTION, "");
    }

    public void onAutoPowerOffAction() {
        log("AppServerManager onAutoPowerOffAction ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onAutoPowerOffAction();
        }
        appCallback(InterfaceConstant.ON_AUTO_POWER_OFF_ACTION, "");
    }

    public void onPopupToast(String str) {
        log("AppServerManager onPopupToast " + str);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onPopupToast(str);
        }
    }

    public void onPopupToastShort(String str) {
        log("AppServerManager onPopupToastShort " + str);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onPopupToastShort(str);
        }
        appCallback(InterfaceConstant.ON_POPUP_TOAST_SHORT, str);
    }

    public void onPopupToastLong(String str) {
        log("AppServerManager onPopupToastLong " + str);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onPopupToastLong(str);
        }
        appCallback(InterfaceConstant.ON_POPUP_TOAST_LONG, str);
    }

    public void onPopupDialog(Intent intent) {
        log("AppServerManager onPopupDialog " + intent);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onPopupDialog(intent);
        }
    }

    public void onPopupSoundEffect() {
        log("AppServerManager onPopupSoundEffect ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onPopupSoundEffect();
        }
        appCallback(InterfaceConstant.ON_POPUP_SOUND_EFFECT, "");
    }

    public void onRepairQuestQuit() {
        log("AppServerManager onRepairQuestQuit ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onRepairQuestQuit();
        }
        appCallback(InterfaceConstant.ON_REPAIR_QUEST_QUIT, "");
    }

    public void onEmergencyIgOff() {
        log("AppServerManager onEmergencyIgOff ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onEmergencyIgOff();
        }
        appCallback(InterfaceConstant.ON_EMERGENCY_IG_OFF, "");
    }

    public void onMicDialogShow() {
        log("AppServerManager onMicDialogShow ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onMicDialogShow();
        }
        appCallback(InterfaceConstant.ON_MIC_DIALOG_SHOW, "");
    }

    public void onMicDialogUpdate(int i) {
        log("AppServerManager onMicDialogUpdate " + i);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onMicDialogUpdate(i);
        }
        appCallback(InterfaceConstant.ON_MIC_DIALOG_UPDATE, String.valueOf(i));
    }

    public void onBluetoothPairShow(Intent intent, String str, String str2, int i) {
        log("AppServerManager onBluetoothPairShow " + str + " " + str2 + " " + i);
        final BluetoothPairingManager bluetoothPairingManager = new BluetoothPairingManager(intent, str2, i);
        bluetoothPairingManager.registerReceiver(CarSettingsApp.getContext());
        bluetoothPairingManager.setCancelPairingListener(new BluetoothPairingManager.CancelPairingListener() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.1
            @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.pair.BluetoothPairingManager.CancelPairingListener
            public void cancelPairing() {
                bluetoothPairingManager.unregisterReceiver(CarSettingsApp.getContext());
                AppServerManager.this.log("AppServerManager onBluetoothPairCancel ");
                IAppUIListener iAppUIListener = AppServerManager.this.mAppUIListener;
                if (iAppUIListener != null) {
                    iAppUIListener.onBluetoothPairCancel();
                }
            }
        });
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            if (TextUtils.isEmpty(str)) {
                str = bluetoothPairingManager.getDeviceName();
            }
            iAppUIListener.onBluetoothPairShow(str, str2, i);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.AutoPowerOffAdapter.IAutoPowerOffChangeListener
    public void onCancelAutoPowerOff() {
        log("AppServerManager onCancelAutoPowerOff ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onCancelAutoPowerOff();
        }
        appCallback(InterfaceConstant.ON_CANCEL_AUTO_POWER_OFF, "");
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.AutoPowerOffAdapter.IAutoPowerOffChangeListener
    public void onAutoPowerOffCountdown(int i, int i2) {
        log("AppServerManager onAutoPowerOffCountdown " + i + " " + i2);
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onAutoPowerOffCountdown(i, i2);
        }
        appCallback(InterfaceConstant.ON_AUTO_POWER_OFF_COUNT_DOWN, JsonUtils.toJSONString(new CountDownBean(i, i2)));
    }

    public void onJumpBluetooth() {
        log("AppServerManager onJumpBluetooth ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onJumpBluetooth();
        }
        appCallback(InterfaceConstant.ON_JUMP_BLUETOOTH, "");
    }

    public void onJumpWifi() {
        log("AppServerManager onJumpWifi ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onJumpWifi();
        }
        appCallback(InterfaceConstant.ON_JUMP_WIFI, "");
    }

    public void onJumpSound() {
        log("AppServerManager onJumpSound ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onJumpSound();
        }
        appCallback(InterfaceConstant.ON_JUMP_SOUND, "");
    }

    public void onJumpDisplay() {
        log("AppServerManager onJumpDisplay ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onJumpDisplay();
        }
        appCallback(InterfaceConstant.ON_JUMP_DISPLAY, "");
    }

    public void onJumpAbout() {
        log("AppServerManager onJumpAbout ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onJumpAbout();
        }
        appCallback(InterfaceConstant.ON_JUMP_ABOUT, "");
    }

    public void onEnterWaitMode() {
        log("AppServerManager onEnterWaitMode ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onEnterWaitMode();
        }
        appCallback(InterfaceConstant.ON_ENTER_WAIT_MODE, "");
    }

    public void onExitWaitMode() {
        log("AppServerManager onExitWaitMode ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onExitWaitMode();
        }
        appCallback(InterfaceConstant.ON_EXIT_WAIT_MODE, "");
    }

    public void onEnterCleanMode() {
        log("AppServerManager onEnterCleanMode ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onEnterCleanMode();
        }
        appCallback(InterfaceConstant.ON_ENTER_CLEAN_MODE, "");
    }

    public void onExitCleanMode() {
        log("AppServerManager onExitCleanMode ");
        IAppUIListener iAppUIListener = this.mAppUIListener;
        if (iAppUIListener != null) {
            iAppUIListener.onExitCleanMode();
        }
        appCallback(InterfaceConstant.ON_EXIT_CLEAN_MODE, "");
    }

    public void exitAuthMode() {
        CarSettingsApp.getContext().sendBroadcast(new Intent("com.xiaopeng.systemui.intent.action.QUIT_AUTH_MODE"));
        Logs.d("AppServerManager AuthModeWork sendBroadcast");
    }

    public void cancelPowerOff() {
        AutoPowerOffAdapter.getInstance().cancelPowerOffConfig();
        AutoPowerOffAdapter.getInstance().cancelTimer();
        Logs.d("AppServerManager autopower off user cancelPowerOff");
    }

    public int[] getPowerOffCountdown() {
        int[] powerOffCountdown = AutoPowerOffAdapter.getInstance().getPowerOffCountdown();
        if (powerOffCountdown == null) {
            return new int[]{-1, -1};
        }
        Logs.d("AppServerManager getPowerOffCountdown " + powerOffCountdown[0] + " " + powerOffCountdown[1]);
        return powerOffCountdown;
    }

    public void registerAutoPowerOffChangeListener() {
        Logs.d("AppServerManager registerAutoPowerOffChangeListener");
        AutoPowerOffAdapter.getInstance().registerCarListener(this);
    }

    public void unregisterAutoPowerOffChangeListener() {
        Logs.d("AppServerManager unregisterAutoPowerOffChangeListener");
        AutoPowerOffAdapter.getInstance().unregisterCarListener();
    }

    public void cancelTimer() {
        Logs.d("AppServerManager cancelTimer");
        AutoPowerOffAdapter.getInstance().cancelTimer();
    }

    public void exitRepairMode() {
        Logs.d("AppServerManager exitRepairMode");
        CarSettingsApp.getContext().sendBroadcast(new Intent("com.xiaopeng.systemui.intent.action.QUIT_REPAIR_MODE"));
    }

    public void cancelPairRequest(String str) {
        log("AppServerManager cancelPairRequest");
        BluetoothUtils.cancelPairRequest(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str));
    }

    public void allowPairRequest(String str, int i) {
        log("AppServerManager allowPairRequest " + str + " " + i);
        BluetoothUtils.allowPairRequest(str, i, null);
    }

    public void setSubScreenStatus(boolean z) {
        log("AppServerManager closeSubScreen " + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B004", z);
        Utils.setPsnScreenOn(CarSettingsApp.getContext(), z);
    }

    public void setDoubleScreenOnOff(boolean z) {
        log("AppServerManager setDoubleScreenOnOff " + z);
        Utils.setDoubleScreenOnOff(CarSettingsApp.getContext(), z);
    }

    public boolean isSubScreenOn() {
        boolean isPsnScreenOn = Utils.isPsnScreenOn(CarSettingsApp.getContext());
        log("AppServerManager isSubScreenOn " + isPsnScreenOn);
        return isPsnScreenOn;
    }

    public void setXpIcmScreenOnOff(boolean z) {
        log("AppServerManager setDoubleScreenOnOff " + z);
        Utils.setXpIcmScreenOnOff(z);
    }

    public void onPsnScreenStatus(boolean z) {
        log("AppServerManager onPsnScreenStatus " + z);
        appCallback(InterfaceConstant.ON_PSN_SCREEN_STATUS, String.valueOf(z));
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        if (r1.equals("XOperaSound") != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void dataLog(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = ","
            java.lang.String[] r6 = r6.split(r0)
            r0 = 0
            r1 = r6[r0]
            int r2 = r1.hashCode()
            r3 = -1545539724(0xffffffffa3e0ef74, float:-2.4387541E-17)
            r4 = 1
            if (r2 == r3) goto L23
            r0 = -1383729685(0xffffffffad85f5eb, float:-1.5229559E-11)
            if (r2 == r0) goto L19
            goto L2c
        L19:
            java.lang.String r0 = "avasLowSpdEffect"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L2c
            r0 = r4
            goto L2d
        L23:
            java.lang.String r2 = "XOperaSound"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L2c
            goto L2d
        L2c:
            r0 = -1
        L2d:
            java.lang.String r1 = "P10003"
            if (r0 == 0) goto L40
            if (r0 == r4) goto L34
            goto L45
        L34:
            r6 = r6[r4]
            int r6 = java.lang.Integer.parseInt(r6)
            java.lang.String r0 = "B009"
            com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils.sendButtonDataLog(r1, r0, r6)
            goto L45
        L40:
            java.lang.String r6 = "B005"
            com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils.sendButtonDataLog(r1, r6)
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.dataLog(java.lang.String):void");
    }

    public void startPopDialog(String str, int i) {
        startPopDialog(str, i, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void startPopDialog(String str, int i, String str2) {
        char c;
        switch (str.hashCode()) {
            case -1884274053:
                if (str.equals("storage")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 116100:
                if (str.equals("usb")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 3649301:
                if (str.equals("wifi")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 617620515:
                if (str.equals("psnBluetooth")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1315935011:
                if (str.equals("externalBluetooth")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1427818632:
                if (str.equals("download")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1511466880:
                if (str.equals("soundEffect")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1968882350:
                if (str.equals("bluetooth")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                jumpToPopDialog(Config.POPUP_BLUETOOTH, i);
                return;
            case 1:
                jumpToPopDialog(Config.POPUP_WLAN, i);
                return;
            case 2:
                jumpToPopDialog(Config.CO_DRIVER_BLUETOOTH, i);
                return;
            case 3:
                jumpToPopDialog(Config.BACK_SEAT_BLUETOOTH, i);
                return;
            case 4:
                jumpToPopDialog(Config.POPUP_DOWNLOAD, i);
                return;
            case 5:
                jumpToPopDialog(Config.POPUP_USB, i);
                return;
            case 6:
                jumpToPopDialog(Config.POPUP_STORAGE, i, str2);
                return;
            case 7:
                jumpToPopDialog(Config.SOUND_EFFECT_ACTION, i);
                return;
            default:
                Logs.d("not found which popup dialog");
                return;
        }
    }

    private void jumpToPopDialog(String str, int i) {
        jumpToPopDialog(str, i, null);
    }

    private void jumpToPopDialog(String str, int i, String str2) {
        Logs.d("AppServerManager jumpToPopDialog action:" + str + " screenId:" + i);
        if (str.equals(Config.CO_DRIVER_BLUETOOTH) || str.equals(Config.BACK_SEAT_BLUETOOTH) || str.equals(Config.POPUP_BLUETOOTH) || str.equals(Config.POPUP_WLAN) || str.equals(Config.POPUP_DOWNLOAD) || str.equals(Config.POPUP_USB) || str.equals(Config.POPUP_STORAGE) || str.equals(Config.SOUND_EFFECT_ACTION)) {
            Intent intent = new Intent(CarSettingsApp.getContext(), UIGlobalService.class);
            intent.setAction(str);
            intent.putExtra(Config.EXTRA_POPUP_SCREEN, i);
            intent.putExtra(Config.EXTRA_POPUP_SOURCE, str2);
            CarSettingsApp.getContext().startService(intent);
            return;
        }
        try {
            Intent intent2 = new Intent(str);
            intent2.setFlags(268435456);
            ActivityManagerFactory.startActivity(CarSettingsApp.getContext(), intent2, (Bundle) null, i);
        } catch (ActivityNotFoundException e) {
            Logs.d("no activity found:" + e);
            e.printStackTrace();
        }
    }

    public void closePopupDialog(String str) {
        Intent intent = new Intent(CarSettingsApp.getContext(), UIGlobalService.class);
        intent.setAction(Config.CLOSE_POPUP_DIALOG);
        intent.putExtra(Config.EXTRA_WHICH_DIALOG, str);
        CarSettingsApp.getContext().startService(intent);
    }

    public void addPopupDialogShowing(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCurrentShowingDialogs.put(str, Integer.valueOf(i));
    }

    public void removePopupDialogShowing(String str) {
        this.mCurrentShowingDialogs.remove(str);
    }

    public boolean isShowingPopupDialog(String str) {
        return this.mCurrentShowingDialogs.containsKey(str);
    }

    public ConcurrentHashMap<String, Integer> getCurrentShowingDialogs() {
        return this.mCurrentShowingDialogs;
    }
}
