package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.UsbServerManager;
/* loaded from: classes.dex */
public class UsbExitEnterWork implements WorkService {
    private UsbServerManager mUsbServerManager;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mUsbServerManager = UsbServerManager.get_instance();
        this.mUsbServerManager.enterScene();
        Logs.d("Usb onCreate");
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        this.mUsbServerManager.exitScene();
        Logs.d("Usb onDestroy");
    }
}
