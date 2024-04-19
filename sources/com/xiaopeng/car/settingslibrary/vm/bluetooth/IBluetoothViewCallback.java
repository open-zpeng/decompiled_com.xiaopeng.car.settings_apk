package com.xiaopeng.car.settingslibrary.vm.bluetooth;
/* loaded from: classes.dex */
public interface IBluetoothViewCallback {
    void onBluetoothSwitching(boolean z);

    void onConnectError(String str, String str2);

    void onConnectOperateError(String str);

    void onPairError(String str, String str2);
}
