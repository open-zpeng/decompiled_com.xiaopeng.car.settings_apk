package com.xiaopeng.car.settingslibrary.interfaceui.beans;
/* loaded from: classes.dex */
public class SettingsBluetoothBean {
    public String address;
    public boolean connect;
    public boolean connectingBusy;
    public boolean disconnectingBusy;
    public String name;
    public boolean pair;
    public boolean paringBusy;
    public int type;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public boolean isParingBusy() {
        return this.paringBusy;
    }

    public void setParingBusy(boolean z) {
        this.paringBusy = z;
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

    public boolean isPair() {
        return this.pair;
    }

    public void setPair(boolean z) {
        this.pair = z;
    }

    public boolean isConnect() {
        return this.connect;
    }

    public void setConnect(boolean z) {
        this.connect = z;
    }

    public String toString() {
        return "SettingsBluetoothBean{address='" + this.address + "', name='" + this.name + "', type=" + this.type + ", paringBusy=" + this.paringBusy + ", connectingBusy=" + this.connectingBusy + ", disconnectingBusy=" + this.disconnectingBusy + ", pair=" + this.pair + ", connect=" + this.connect + '}';
    }
}
