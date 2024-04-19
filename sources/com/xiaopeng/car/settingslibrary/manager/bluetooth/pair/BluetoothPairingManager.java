package com.xiaopeng.car.settingslibrary.manager.bluetooth.pair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.BluetoothUtils;
/* loaded from: classes.dex */
public class BluetoothPairingManager {
    CancelPairingListener mCancelPairingListener;
    private BluetoothDevice mDevice;
    private String mDeviceName;
    private IntentFilter mIntentFilter = new IntentFilter();
    private boolean mIsAlreadyRegister = false;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.pair.BluetoothPairingManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("xpbluetooth pair action:" + action);
            if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
                if (intExtra == 12 || intExtra == 10) {
                    BluetoothPairingManager.this.notifyCancelPairing();
                }
            } else if ("android.bluetooth.device.action.PAIRING_CANCEL".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice == null || BluetoothPairingManager.this.mDevice == bluetoothDevice) {
                    BluetoothPairingManager.this.notifyCancelPairing();
                }
            }
        }
    };
    private int mType;

    /* loaded from: classes.dex */
    public interface CancelPairingListener {
        void cancelPairing();
    }

    public BluetoothPairingManager(Intent intent, String str, int i) {
        BluetoothAdapter defaultAdapter;
        setIntent(intent);
        if (this.mDevice == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
            this.mDevice = defaultAdapter.getRemoteDevice(str);
        }
        if (this.mType == Integer.MIN_VALUE) {
            this.mType = i;
        }
        Logs.d("xpsettings bluetooth mType:" + this.mType);
    }

    public void setIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        this.mDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        this.mType = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
        this.mDeviceName = BluetoothUtils.getName(this.mDevice);
        this.mIntentFilter.addAction("android.bluetooth.device.action.PAIRING_CANCEL");
        this.mIntentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public void onPair(String str) {
        Log.d("Pairing", "Pairing dialog accepted");
        BluetoothUtils.allowPairRequest(this.mDevice.getAddress(), this.mType, str);
    }

    public void cancel() {
        Log.d("Pairing", "Pairing dialog canceled");
        BluetoothUtils.cancelPairRequest(this.mDevice);
    }

    public void registerReceiver(Context context) {
        if (this.mIsAlreadyRegister) {
            return;
        }
        context.registerReceiver(this.mReceiver, this.mIntentFilter);
        this.mIsAlreadyRegister = true;
    }

    public void unregisterReceiver(Context context) {
        if (this.mIsAlreadyRegister) {
            context.unregisterReceiver(this.mReceiver);
            this.mIsAlreadyRegister = false;
        }
    }

    public void setCancelPairingListener(CancelPairingListener cancelPairingListener) {
        this.mCancelPairingListener = cancelPairingListener;
    }

    public void notifyCancelPairing() {
        CancelPairingListener cancelPairingListener = this.mCancelPairingListener;
        if (cancelPairingListener != null) {
            cancelPairingListener.cancelPairing();
        }
    }
}
