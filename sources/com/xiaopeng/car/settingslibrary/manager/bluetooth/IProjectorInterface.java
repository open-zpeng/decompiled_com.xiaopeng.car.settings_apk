package com.xiaopeng.car.settingslibrary.manager.bluetooth;
/* loaded from: classes.dex */
public interface IProjectorInterface {
    void forceStartPairOrConnectRetry();

    void forceStartScanRetry();

    boolean isAlreadyBoundedWhiteList();

    boolean isAlreadyConnectedWhiteList();

    boolean isBondingWhiteList();

    boolean isConnectingWhiteList();

    boolean isFoundWhiteListDevice();

    void registerProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback);

    void setBleWhiteListMode(boolean z);

    void timeoutForceStopPairOrConnectRetry();

    void timeoutForceStopScanRetry();

    void unregisterStateCallback(IXpBluetoothCallback iXpBluetoothCallback);
}
