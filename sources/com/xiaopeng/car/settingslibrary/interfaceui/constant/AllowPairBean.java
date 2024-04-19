package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class AllowPairBean {
    private String address;
    private int type;

    public AllowPairBean() {
    }

    public AllowPairBean(String str, int i) {
        this.address = str;
        this.type = i;
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
        return "AllowPairBean{address='" + this.address + "', type=" + this.type + '}';
    }
}
