package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class PopDialogReceiver extends BroadcastReceiver {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        Logs.d("PopDialogReceiver action:" + intent.getAction());
        int intExtra = intent.getIntExtra("screen_id", 0);
        String action = intent.getAction();
        switch (action.hashCode()) {
            case -1867240113:
                if (action.equals(Config.POPUP_WLAN)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1841656914:
                if (action.equals(Config.POPUP_STORAGE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1546017387:
                if (action.equals(Config.POPUP_DOWNLOAD)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -894562305:
                if (action.equals(Config.BACK_SEAT_BLUETOOTH)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -644932339:
                if (action.equals(Config.CO_DRIVER_BLUETOOTH)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -25721055:
                if (action.equals(Config.POPUP_BLUETOOTH)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 134764189:
                if (action.equals(Config.ACTION_STORAGE_OPEN)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1602332727:
                if (action.equals(Config.POPUP_USB)) {
                    c = 5;
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
                Logs.d("PopDialogReceiver POPUP_BLUETOOTH screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("bluetooth", intExtra);
                return;
            case 1:
                Logs.d("PopDialogReceiver POPUP_WLAN screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("wifi", intExtra);
                return;
            case 2:
                Logs.d("PopDialogReceiver CO_DRIVER_BLUETOOTH screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("psnBluetooth", intExtra);
                return;
            case 3:
                Logs.d("PopDialogReceiver BACK_SEAT_BLUETOOTH screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("externalBluetooth", intExtra);
                return;
            case 4:
                Logs.d("PopDialogReceiver POPUP_DOWNLOAD screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("download", intExtra);
                return;
            case 5:
                Logs.d("PopDialogReceiver POPUP_USB screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("usb", intExtra);
                return;
            case 6:
                Logs.d("PopDialogReceiver POPUP_STORAGE screenId: " + intExtra);
                AppServerManager.getInstance().startPopDialog("storage", intExtra);
                return;
            case 7:
                AppServerManager.getInstance().startPopDialog("storage", 0, BuriedPointUtils.DisplaySource.TYPE_CLICK);
                return;
            default:
                return;
        }
    }
}
