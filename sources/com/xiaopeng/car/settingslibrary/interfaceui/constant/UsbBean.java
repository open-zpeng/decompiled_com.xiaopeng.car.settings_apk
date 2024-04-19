package com.xiaopeng.car.settingslibrary.interfaceui.constant;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class UsbBean {
    private long capacity;
    private int category;
    private boolean connected;
    private boolean official;
    private long usedSpace;
    private String deviceName = "";
    private String capacityContent = "";

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

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int i) {
        this.category = i;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(long j) {
        this.capacity = j;
    }

    public long getUsedSpace() {
        return this.usedSpace;
    }

    public void setUsedSpace(long j) {
        this.usedSpace = j;
    }

    public boolean isOfficial() {
        return this.official;
    }

    public void setOfficial(boolean z) {
        this.official = z;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean z) {
        this.connected = z;
    }

    public String getCapacityContent() {
        return this.capacityContent;
    }

    public void setCapacityContent(String str) {
        if (TextUtils.isEmpty(str)) {
            this.capacityContent = "";
        } else {
            this.capacityContent = str;
        }
    }

    public String toString() {
        return "UsbBean{deviceName='" + this.deviceName + "', category=" + this.category + ", capacity=" + this.capacity + ", usedSpace=" + this.usedSpace + ", official=" + this.official + ", connected=" + this.connected + ", capacityContent='" + this.capacityContent + "'}";
    }
}
