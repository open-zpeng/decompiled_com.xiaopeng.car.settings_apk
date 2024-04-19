package com.xiaopeng.car.settingslibrary.manager.bluetooth.os;

import android.bluetooth.BluetoothDevice;
/* loaded from: classes.dex */
public interface LocalBluetoothProfile {
    boolean accessProfileEnabled();

    boolean connect(BluetoothDevice bluetoothDevice);

    boolean disconnect(BluetoothDevice bluetoothDevice);

    int getConnectionStatus(BluetoothDevice bluetoothDevice);

    int getOrdinal();

    int getProfileId();

    boolean isAutoConnectable();

    boolean isProfileReady();
}
