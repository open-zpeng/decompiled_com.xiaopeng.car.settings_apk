package com.xiaopeng.btservice.base;
/* loaded from: classes.dex */
public abstract class AbsBluetoothControlCallback {
    public void onA2dpStateChanged(String str, int i, int i2) {
    }

    public void onAdapterDiscoverableModeChanged(int i, int i2) {
    }

    public void onAdapterDiscoveryFinished() {
    }

    public void onAdapterDiscoveryStarted() {
    }

    public void onAdapterStateChanged(int i, int i2) {
    }

    public void onAvrcpStateChanged(String str, int i, int i2) {
    }

    public void onBluetoothServiceReady() {
    }

    public void onBtAutoConnectStateChanged(String str, int i, int i2) {
    }

    public void onBtRoleModeChanged(int i) {
    }

    public void onDeviceAclDisconnected(String str) {
    }

    public void onDeviceBondStateChanged(String str, String str2, int i, int i2) {
    }

    public void onDeviceFound(String str, String str2, byte b) {
    }

    public void onDeviceOutOfRange(String str) {
    }

    public void onDeviceUuidsUpdated(String str, String str2, int i) {
    }

    public void onHfpStateChanged(String str, int i, int i2) {
    }

    public void onLocalAdapterNameChanged(String str) {
    }

    public void retPairedDevices(int i, String[] strArr, String[] strArr2, int[] iArr, byte[] bArr) {
    }
}
