package com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base;
/* loaded from: classes.dex */
public interface IXpExternalBluetoothCallback {
    void onBluetoothStateChanged(int i);

    void onConnectionStateChanged(String str, String str2, int i, int i2);

    void onDeviceBondStateChanged(String str, String str2, int i, int i2);

    void onRefreshData();

    void onScanningStateChanged(boolean z);
}
