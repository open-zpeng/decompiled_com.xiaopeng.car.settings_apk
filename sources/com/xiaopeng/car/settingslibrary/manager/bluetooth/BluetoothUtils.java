package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class BluetoothUtils {
    public static boolean isSupportHfp(ParcelUuid[] parcelUuidArr, ParcelUuid[] parcelUuidArr2) {
        return BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Handsfree_AG) && BluetoothUuid.isUuidPresent(parcelUuidArr2, BluetoothUuid.Handsfree);
    }

    public static boolean isSupportHid(ParcelUuid[] parcelUuidArr, ParcelUuid[] parcelUuidArr2) {
        return BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Hid) || BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Hogp);
    }

    public static void cancelPairRequest(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            bluetoothDevice.cancelPairingUserInput();
        }
    }

    public static void allowPairRequest(String str, int i, String str2) {
        BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str);
        switch (i) {
            case 0:
            case 7:
                byte[] convertPinToBytes = BluetoothDevice.convertPinToBytes(str2);
                if (convertPinToBytes == null) {
                    return;
                }
                remoteDevice.setPin(convertPinToBytes);
                return;
            case 1:
                remoteDevice.setPasskey(Integer.parseInt(str2));
                return;
            case 2:
            case 3:
                remoteDevice.setPairingConfirmation(true);
                return;
            case 4:
            case 5:
                return;
            case 6:
                remoteDevice.setRemoteOutOfBandData();
                return;
            default:
                Logs.d("Incorrect pairing type received");
                return;
        }
    }

    public static String getName(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return "";
        }
        if (!TextUtils.isEmpty(bluetoothDevice.getName())) {
            return bluetoothDevice.getName();
        }
        if (!TextUtils.isEmpty(bluetoothDevice.getAliasName())) {
            return bluetoothDevice.getAliasName();
        }
        return bluetoothDevice.getAddress();
    }
}
