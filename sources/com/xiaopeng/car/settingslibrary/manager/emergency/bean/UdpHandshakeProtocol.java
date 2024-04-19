package com.xiaopeng.car.settingslibrary.manager.emergency.bean;
/* loaded from: classes.dex */
public class UdpHandshakeProtocol {
    protected int action;
    protected boolean connecting;
    protected String deviceName;
    protected String ip;
    protected int platform;
    protected int port;
    protected int version;

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String str) {
        this.ip = str;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int i) {
        this.action = i;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public boolean isConnecting() {
        return this.connecting;
    }

    public void setConnecting(boolean z) {
        this.connecting = z;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public int getPlatform() {
        return this.platform;
    }

    public void setPlatform(int i) {
        this.platform = i;
    }

    public String toString() {
        return "UdpHandshakeProtocol{port=" + this.port + ", ip='" + this.ip + "', action=" + this.action + ", version=" + this.version + ", platform=" + this.platform + ", deviceName='" + this.deviceName + "'}";
    }
}
