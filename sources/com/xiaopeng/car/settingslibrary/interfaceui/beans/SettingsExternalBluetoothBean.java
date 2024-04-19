package com.xiaopeng.car.settingslibrary.interfaceui.beans;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class SettingsExternalBluetoothBean {
    public int connectStatus;
    public boolean connectingBusy;
    public boolean disconnectingBusy;
    public int pairState;
    public String address = "";
    public String deviceName = "";

    public int getPairState() {
        return this.pairState;
    }

    public void setPairState(int i) {
        this.pairState = i;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            this.address = "";
        } else {
            this.address = str;
        }
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        if (TextUtils.isEmpty(str)) {
            this.deviceName = "";
        } else {
            this.deviceName = str;
        }
    }

    public boolean isConnectingBusy() {
        return this.connectingBusy;
    }

    public void setConnectingBusy(boolean z) {
        this.connectingBusy = z;
    }

    public boolean isDisconnectingBusy() {
        return this.disconnectingBusy;
    }

    public void setDisconnectingBusy(boolean z) {
        this.disconnectingBusy = z;
    }

    public int getConnectStatus() {
        return this.connectStatus;
    }

    public void setConnectStatus(int i) {
        this.connectStatus = i;
    }

    public String toString() {
        return "SettingsPsnBluetoothBean{address='" + this.address + "', deviceName='" + this.deviceName + "', connectingBusy=" + this.connectingBusy + ", disconnectingBusy=" + this.disconnectingBusy + ", connectStatus=" + this.connectStatus + ", pairState=" + this.pairState + '}';
    }
}
