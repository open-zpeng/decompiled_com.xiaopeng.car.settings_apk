package com.xiaopeng.car.settingslibrary.manager.bluetooth;
/* loaded from: classes.dex */
public interface IProjectorBluetoothCallback {
    void onProjectorBoundStatus(int i, int i2);

    void onProjectorConnectFailed(int i);

    void onProjectorConnectStatus(int i, int i2);

    void onProjectorFounded();
}
