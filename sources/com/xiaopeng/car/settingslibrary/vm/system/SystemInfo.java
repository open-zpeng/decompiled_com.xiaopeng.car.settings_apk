package com.xiaopeng.car.settingslibrary.vm.system;
/* loaded from: classes.dex */
public class SystemInfo {
    private String IP;
    private String baseBandVersion;
    private String bluetoothMac;
    private String firmwareVersion;
    private String kernelVersion;
    private String mcuVersion;
    private String model;
    private String softwareVersion;
    private String tboxMcu;
    private String tboxVersion;
    private String wifiMac;

    public String getIP() {
        return this.IP;
    }

    public void setIP(String str) {
        this.IP = str;
    }

    public String getBluetoothMac() {
        return this.bluetoothMac;
    }

    public void setBluetoothMac(String str) {
        this.bluetoothMac = str;
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public void setWifiMac(String str) {
        this.wifiMac = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getMcuVersion() {
        return this.mcuVersion;
    }

    public void setMcuVersion(String str) {
        this.mcuVersion = str;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String str) {
        this.firmwareVersion = str;
    }

    public String getBaseBandVersion() {
        return this.baseBandVersion;
    }

    public void setBaseBandVersion(String str) {
        this.baseBandVersion = str;
    }

    public String getKernelVersion() {
        return this.kernelVersion;
    }

    public void setKernelVersion(String str) {
        this.kernelVersion = str;
    }

    public String getSoftwareVersion() {
        return this.softwareVersion;
    }

    public void setSoftwareVersion(String str) {
        this.softwareVersion = str;
    }

    public String getTboxVersion() {
        return this.tboxVersion;
    }

    public void setTboxVersion(String str) {
        this.tboxVersion = str;
    }

    public String getTboxMcu() {
        return this.tboxMcu;
    }

    public void setTboxMcu(String str) {
        this.tboxMcu = str;
    }

    public String toString() {
        return "SystemInfo{model='" + this.model + "', mcuVersion='" + this.mcuVersion + "', firmwareVersion='" + this.firmwareVersion + "', baseBandVersion='" + this.baseBandVersion + "', kernelVersion='" + this.kernelVersion + "', softwareVersion='" + this.softwareVersion + "', wifiMac='" + this.wifiMac + "', bluetoothMac='" + this.bluetoothMac + "', IP='" + this.IP + "'}";
    }
}
