package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import java.util.ArrayList;
/* loaded from: classes.dex */
public interface IXpBluetoothInterface extends IProjectorInterface {
    boolean connect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void connectAndRetry(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean disConnect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted();

    int getBondState(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted();

    String getName();

    XpBluetoothDeviceInfo getXpBluetoothDevice(String str);

    boolean isA2dpConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isBtDiscoverable();

    boolean isBtPhoneConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isBusy(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isConnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isCurrentScanning();

    boolean isDisconnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    boolean isEnable();

    boolean isHasPhoneConnected();

    boolean isParing(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void notifyItemChange(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void notifyRefreshListCallback();

    boolean pair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, String str);

    void realDeviceBondStateChanged(String str, String str2, int i, int i2, boolean z);

    void registerBluetoothCallback();

    void registerBluetoothOnChange();

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    void registerProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback);

    void registerServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback);

    void registerStateCallback(IXpBluetoothCallback iXpBluetoothCallback);

    void reqBtDevicePairedDevices();

    boolean setBluetoothEnabled(boolean z);

    void setBtDiscoverable(boolean z);

    void setConnectOperateErrorListener(XpBluetoothManger.ConnectErrorListener connectErrorListener);

    boolean setName(String str);

    boolean startScanList();

    void stopScanDevice();

    boolean unpair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);

    void unregisterBluetoothCallback();

    void unregisterBluetoothOnChange();

    void unregisterProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback);

    void unregisterServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback);

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    void unregisterStateCallback(IXpBluetoothCallback iXpBluetoothCallback);
}
