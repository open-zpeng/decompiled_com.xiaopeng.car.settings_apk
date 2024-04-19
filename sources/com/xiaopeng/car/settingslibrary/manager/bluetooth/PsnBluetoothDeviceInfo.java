package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class PsnBluetoothDeviceInfo {
    private int category;
    private String deviceAddr;
    private String deviceName;
    private boolean isConnectingBusy = false;
    private boolean isDisconnectingBusy = false;
    private int connectStatus = -1;

    public int getPairState() {
        return 0;
    }

    public boolean isA2dpConnected() {
        return false;
    }

    public boolean isHfpConnected() {
        return false;
    }

    public boolean isHidConnected() {
        return false;
    }

    public PsnBluetoothDeviceInfo(String str, String str2) {
        this.deviceName = "";
        this.deviceAddr = "";
        this.deviceName = TextUtils.isEmpty(str) ? str2 : str;
        this.deviceAddr = str2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public String getDeviceAddr() {
        return this.deviceAddr;
    }

    public void setDeviceAddr(String str) {
        this.deviceAddr = str;
    }

    public boolean isConnectingBusy() {
        return this.isConnectingBusy;
    }

    public void setConnectingBusy(boolean z) {
        this.isConnectingBusy = z;
    }

    public boolean isDisconnectingBusy() {
        return this.isDisconnectingBusy;
    }

    public void setDisconnectingBusy(boolean z) {
        this.isDisconnectingBusy = z;
    }

    public int getCategory() {
        return this.category;
    }

    public int getConnectStatus() {
        return this.connectStatus;
    }

    public void setConnectStatus(int i) {
        this.connectStatus = i;
        setConnectingBusy(i == 1);
        setDisconnectingBusy(i == 3);
    }

    public void setCategory(int i) {
        this.category = i;
    }

    public String toString() {
        return "PsnBluetoothDeviceInfo{deviceName='" + this.deviceName + "', deviceAddr='" + this.deviceAddr + "', category=" + this.category + ", isConnectingBusy=" + this.isConnectingBusy + ", isDisconnectingBusy=" + this.isDisconnectingBusy + ", connectStatus=" + this.connectStatus + '}';
    }
}
