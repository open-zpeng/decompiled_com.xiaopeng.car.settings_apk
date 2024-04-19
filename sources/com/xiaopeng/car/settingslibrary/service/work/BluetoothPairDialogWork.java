package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.dialog.BluetoothPairDialog;
/* loaded from: classes.dex */
public class BluetoothPairDialogWork implements WorkService {
    BluetoothPairDialog mPairDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(action)) {
            Logs.d("BluetoothPairDialogWork action");
            this.mPairDialog = new BluetoothPairDialog(context.getApplicationContext(), intent);
            this.mPairDialog.show();
        } else if (Config.ACTION_PAIRING_REQUEST_CANCEL.equals(action)) {
            Logs.d("BluetoothPairDialogWork action cancel");
            BluetoothPairDialog bluetoothPairDialog = this.mPairDialog;
            if (bluetoothPairDialog != null) {
                bluetoothPairDialog.dismiss();
            }
        }
    }
}
