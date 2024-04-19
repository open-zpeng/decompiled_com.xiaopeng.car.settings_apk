package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class XpBluetoothDeviceInfo {
    private int category;
    private String deviceAddr;
    private String deviceName;
    AbsBluetoothDevice mAbsBluetoothDevice;
    private boolean paringBusy = false;
    private boolean connectingBusy = false;
    private boolean disconnectingBusy = false;

    /* loaded from: classes.dex */
    public static class CategoryType {
        public static final int COMPUTER = 3;
        public static final int PERIPHERAL = 2;
        public static final int PHONE = 1;
        public static final int UNKNOWN = -1;
    }

    private int convertNFBondStateToOs(int i) {
        if (i != 11) {
            if (i != 12) {
                if (i != 331) {
                    if (i != 332) {
                        return 10;
                    }
                }
            }
            return 12;
        }
        return 11;
    }

    public int getPairState() {
        return convertNFBondStateToOs(this.mAbsBluetoothDevice.getBondState());
    }

    public boolean isHfpConnected() {
        return this.mAbsBluetoothDevice.isBtPhoneConnected();
    }

    public boolean isA2dpConnected() {
        return this.mAbsBluetoothDevice.isA2dpConnected();
    }

    public boolean isHidConnected() {
        return this.mAbsBluetoothDevice.isHidConnected();
    }

    public boolean isA2dpConnecting() {
        return this.mAbsBluetoothDevice.isA2dpConnecting();
    }

    public boolean isA2DPSupportedProfile() {
        return this.mAbsBluetoothDevice.isA2DPSupportedProfile();
    }

    public XpBluetoothDeviceInfo(String str, String str2) {
        this.deviceName = "";
        this.deviceAddr = "";
        if (!TextUtils.isEmpty(str)) {
            this.deviceName = str;
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.deviceAddr = str2;
    }

    public void setBluetoothDevice(AbsBluetoothDevice absBluetoothDevice) {
        this.mAbsBluetoothDevice = absBluetoothDevice;
    }

    public AbsBluetoothDevice getAbsBluetoothDevice() {
        return this.mAbsBluetoothDevice;
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

    public String getDeviceAddr() {
        return this.deviceAddr;
    }

    public void setDeviceAddr(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.deviceAddr = str;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int i) {
        this.category = i;
    }

    public String toString() {
        return "XpBluetoothDeviceInfo{deviceName='" + this.deviceName + "', deviceAddr='" + this.deviceAddr + "', category=" + this.category + ", paringBusy=" + this.paringBusy + ", connectingBusy=" + this.connectingBusy + ", disconnectingBusy=" + this.disconnectingBusy + '}';
    }

    public boolean isConnectingBusy() {
        return this.connectingBusy;
    }

    public void setConnectingBusy(boolean z) {
        this.connectingBusy = z;
    }

    public boolean isParingBusy() {
        return this.mAbsBluetoothDevice.isParing();
    }

    public void setParingBusy(boolean z) {
        this.paringBusy = z;
    }

    public boolean isDisconnectingBusy() {
        return this.disconnectingBusy;
    }

    public void setDisconnectingBusy(boolean z) {
        this.disconnectingBusy = z;
    }
}
