package com.xiaopeng.car.settingslibrary.interfaceui.constant;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class WifiBean {
    private boolean connected;
    private boolean connecting;
    private int level;
    private boolean saved;
    private int security;
    private String ssid = "";
    private String securityString = "";
    private String bssid = "";

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean z) {
        this.connected = z;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        if (TextUtils.isEmpty(str)) {
            this.ssid = "";
        } else {
            this.ssid = str;
        }
    }

    public int getSecurity() {
        return this.security;
    }

    public void setSecurity(int i) {
        this.security = i;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public String getSecurityString() {
        return this.securityString;
    }

    public void setSecurityString(String str) {
        if (TextUtils.isEmpty(str)) {
            this.securityString = "";
        } else {
            this.securityString = str;
        }
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        if (TextUtils.isEmpty(str)) {
            this.bssid = "";
        } else {
            this.bssid = str;
        }
    }

    public boolean isConnecting() {
        return this.connecting;
    }

    public void setConnecting(boolean z) {
        this.connecting = z;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public void setSaved(boolean z) {
        this.saved = z;
    }

    public String toString() {
        return "WifiBean{connected=" + this.connected + ", ssid='" + this.ssid + "', security=" + this.security + ", level=" + this.level + ", securityString='" + this.securityString + "', bssid='" + this.bssid + "', connecting=" + this.connecting + ", saved=" + this.saved + '}';
    }
}
