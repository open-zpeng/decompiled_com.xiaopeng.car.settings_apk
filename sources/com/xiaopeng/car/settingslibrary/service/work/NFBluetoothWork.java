package com.xiaopeng.car.settingslibrary.service.work;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.BluetoothUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
/* loaded from: classes.dex */
public class NFBluetoothWork implements WorkService {
    final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.service.work.NFBluetoothWork.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("xpservice receiver action:" + action);
            if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
                if (bluetoothDevice == null) {
                    Logs.d("xpservice device:" + bluetoothDevice);
                    return;
                }
                Logs.d("xpservice action:" + bluetoothDevice.getAddress() + " name:" + bluetoothDevice.getName() + " newState:" + intExtra2 + " prevState:" + intExtra);
                if (bluetoothDevice.getBondState() == 10) {
                    BluetoothUtils.cancelPairRequest(bluetoothDevice);
                }
                if (Utils.isHidDevice(bluetoothDevice)) {
                    NFBluetoothWork.this.mXpBluetoothManger.realDeviceBondStateChanged(bluetoothDevice.getAddress(), bluetoothDevice.getName(), intExtra, intExtra2, true);
                }
            }
        }
    };
    XpBluetoothManger mXpBluetoothManger;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mXpBluetoothManger = XpBluetoothManger.getInstance();
        this.mXpBluetoothManger.registerNFBluetoothCallback();
        this.mXpBluetoothManger.registerBluetoothOnChange();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        context.registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XpBluetoothManger xpBluetoothManger = this.mXpBluetoothManger;
        if (xpBluetoothManger != null) {
            xpBluetoothManger.unregisterNFBluetoothCallback();
            this.mXpBluetoothManger.unregisterBluetoothOnChange();
        }
        context.unregisterReceiver(this.mReceiver);
    }
}
