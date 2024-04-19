package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class BluetoothBean {
    private String address;
    private String name;

    public BluetoothBean() {
    }

    public BluetoothBean(String str, String str2) {
        this.address = str;
        this.name = str2;
    }

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

    public String toString() {
        return "BluetoothBean{address='" + this.address + "', name='" + this.name + "'}";
    }
}
