package com.xiaopeng.car.settingslibrary.vm.wifi;

import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
/* loaded from: classes.dex */
public class WifiBean {
    private XpAccessPoint accessPoint;
    private String bssid;
    private boolean connected;
    private boolean isConnecting;
    private int level;
    private boolean saved;
    private int security;
    private String securityString;
    private String ssid;

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
        this.ssid = str;
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
        this.securityString = str;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public boolean isConnecting() {
        return this.isConnecting;
    }

    public void setConnecting(boolean z) {
        this.isConnecting = z;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public void setSaved(boolean z) {
        this.saved = z;
    }

    public XpAccessPoint getAccessPoint() {
        return this.accessPoint;
    }

    public void setAccessPoint(XpAccessPoint xpAccessPoint) {
        this.accessPoint = xpAccessPoint;
    }

    public String toString() {
        return "WifiBean{connected=" + this.connected + ", ssid='" + this.ssid + "', security=" + this.security + ", level=" + this.level + ", securityString='" + this.securityString + "', bssid='" + this.bssid + "', isConnecting=" + this.isConnecting + ", saved=" + this.saved + '}';
    }
}
