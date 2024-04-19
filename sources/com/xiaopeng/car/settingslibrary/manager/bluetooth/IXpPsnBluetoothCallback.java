package com.xiaopeng.car.settingslibrary.manager.bluetooth;
/* loaded from: classes.dex */
public interface IXpPsnBluetoothCallback {
    void onRefreshData();

    void onUsbBluetoothStateChanged(int i);

    void onUsbConnectionStateChanged(String str, String str2, int i, int i2);

    void onUsbDeviceBondStateChanged(String str, String str2, int i, int i2);

    void onUsbScanningStateChanged(boolean z);
}
