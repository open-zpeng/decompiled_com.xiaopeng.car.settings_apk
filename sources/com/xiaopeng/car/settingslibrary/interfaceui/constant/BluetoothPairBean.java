package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class BluetoothPairBean {
    private String address;
    private String name;
    private int type;

    public BluetoothPairBean() {
    }

    public BluetoothPairBean(String str, String str2, int i) {
        this.name = str;
        this.address = str2;
        this.type = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String toString() {
        return "BluetoothPairBean{name='" + this.name + "', address='" + this.address + "', type=" + this.type + '}';
    }
}
