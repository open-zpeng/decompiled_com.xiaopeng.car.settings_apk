package com.xiaopeng.car.settingslibrary.vm.usb;

import android.hardware.usb.UsbDevice;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import java.util.Objects;
/* loaded from: classes.dex */
public class XpUsbDeviceInfo {
    private long capacity;
    private int category;
    private boolean connected;
    private String deviceName = "";
    private boolean official;
    private UsbDevice usbDevice;
    private long usedSpace;
    private VolumeInfo volumeInfo;

    /* loaded from: classes.dex */
    public static class CategoryType {
        public static final int DISK = 2;
        public static final int MICROPHONE = 3;
        public static final int PHONE = 1;
        public static final int PLAYER = 4;
        public static final int USB = 0;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.deviceName = str;
    }

    public UsbDevice getUsbDevice() {
        return this.usbDevice;
    }

    public void setUsbDevice(UsbDevice usbDevice) {
        this.usbDevice = usbDevice;
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

    public VolumeInfo getVolumeInfo() {
        return this.volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean z) {
        this.connected = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        XpUsbDeviceInfo xpUsbDeviceInfo = (XpUsbDeviceInfo) obj;
        UsbDevice usbDevice = this.usbDevice;
        if (usbDevice == null || !Objects.equals(usbDevice, xpUsbDeviceInfo.usbDevice)) {
            VolumeInfo volumeInfo = this.volumeInfo;
            return volumeInfo != null && volumeInfo.equals(xpUsbDeviceInfo.volumeInfo);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.usbDevice);
    }

    public String toString() {
        return "XpUsbDeviceInfo{deviceName=" + this.deviceName + ", category" + this.category + ", capacity=" + this.capacity + ", freeSpace=" + this.usedSpace + ", usbDevice=" + this.usbDevice + ", isOfficial= " + this.official + ",isConnected= " + this.connected + "}";
    }
}
