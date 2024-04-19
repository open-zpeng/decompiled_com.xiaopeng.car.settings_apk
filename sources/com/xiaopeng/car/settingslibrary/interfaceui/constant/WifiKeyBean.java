package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class WifiKeyBean {
    private String bssid;
    private int error;
    private String identify;
    private String pwd;
    private int security;
    private String ssid;
    private String summary;

    public WifiKeyBean() {
    }

    public WifiKeyBean(String str, int i) {
        this.ssid = str;
        this.error = i;
    }

    public WifiKeyBean(String str, int i, String str2) {
        this.ssid = str;
        this.security = i;
        this.bssid = str2;
    }

    public WifiKeyBean(String str, int i, String str2, String str3, String str4) {
        this.ssid = str;
        this.security = i;
        this.bssid = str2;
        this.identify = str3;
        this.pwd = str4;
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

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getIdentify() {
        return this.identify;
    }

    public void setIdentify(String str) {
        this.identify = str;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String str) {
        this.pwd = str;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int i) {
        this.error = i;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String str) {
        this.summary = str;
    }

    public String toString() {
        return "WifiKeyBean{ssid='" + this.ssid + "', security=" + this.security + ", bssid='" + this.bssid + "', identify='" + this.identify + "', pwd='" + this.pwd + "', error=" + this.error + ", summary='" + this.summary + "'}";
    }
}
