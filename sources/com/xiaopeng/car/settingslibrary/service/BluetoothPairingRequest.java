package com.xiaopeng.car.settingslibrary.service;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.BluetoothUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
/* loaded from: classes.dex */
public final class BluetoothPairingRequest extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        BluetoothClass bluetoothClass;
        abortBroadcast();
        String action = intent.getAction();
        Logs.d("xpbluetooth pair receiver:" + action);
        if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Logs.d("xpbluetooth pair islocal:" + bluetoothDevice.isBondingInitiatedLocally());
            if (bluetoothDevice.isBondingInitiatedLocally()) {
                bluetoothDevice.setPairingConfirmation(true);
                if (CarFunction.isNonSelfPageUI() || (bluetoothClass = bluetoothDevice.getBluetoothClass()) == null || bluetoothClass.getMajorDeviceClass() != 512) {
                    return;
                }
                String name = bluetoothDevice.getName();
                if (!TextUtils.isEmpty(name) && name.length() > 15) {
                    name = name.substring(0, 15) + "...";
                }
                AppServerManager.getInstance().onPopupToast(context.getString(R.string.bluetooth_pairing_request_local, name));
            } else if (Utils.isPhone(bluetoothDevice) && XpBluetoothManger.getInstance().isHasPhoneConnected()) {
                BluetoothUtils.cancelPairRequest(bluetoothDevice);
                Logs.d("xpbluetooth cancelPairingUserInput!");
            } else {
                AppServerManager.getInstance().onBluetoothPairShow(intent, bluetoothDevice.getName(), bluetoothDevice.getAddress(), intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE));
            }
        }
    }
}
