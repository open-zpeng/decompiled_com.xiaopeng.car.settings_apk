package com.xiaopeng.car.settingslibrary.manager.bluetooth;
/* loaded from: classes.dex */
public interface IXpBluetoothCallback {
    void notifyItemChanged(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void onBluetoothStateChanged(int i, int i2);

    void onConnectionStateChanged(String str, String str2, int i, int i2);

    void onDeviceBondStateChanged(String str, String str2, int i, int i2);

    void onDeviceFounded(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void onDiscoverableModeChanged(int i, int i2);

    void onRefreshData();

    void onRetPairedDevices();

    void onScanningStateChanged(boolean z);
}
