package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class PsnBluetoothManger {
    private static volatile PsnBluetoothManger sInstance;
    PsnNFBluetoothManager mNFBluetoothManager = new PsnNFBluetoothManager();

    /* loaded from: classes.dex */
    public interface NFServiceConnectedCallback {
        void connectCompleted();
    }

    public static PsnBluetoothManger getInstance() {
        if (sInstance == null) {
            synchronized (PsnBluetoothManger.class) {
                if (sInstance == null) {
                    sInstance = new PsnBluetoothManger();
                }
            }
        }
        return sInstance;
    }

    private PsnBluetoothManger() {
    }

    public void registerPsnNFBluetoothCallback() {
        this.mNFBluetoothManager.registerPsnNFBluetoothCallback();
    }

    public void unregisterPsnNFBluetoothCallback() {
        this.mNFBluetoothManager.unregisterPsnNFBluetoothCallback();
    }

    public boolean usbSearch() {
        return this.mNFBluetoothManager.usbSearch();
    }

    public String getUsbBluetoothName() {
        return this.mNFBluetoothManager.usbName();
    }

    public boolean usbConnect(String str) {
        return this.mNFBluetoothManager.usbConnect(str);
    }

    public boolean isUsbEnable() {
        return this.mNFBluetoothManager.isUsbEnable();
    }

    public int getProfileConnectionState(int i) {
        return this.mNFBluetoothManager.getProfileConnectionState(i);
    }

    public int getLocalUsbConnectionState() {
        return this.mNFBluetoothManager.getLocalUsbConnectionState();
    }

    public boolean isUsbCurrentScanning() {
        return this.mNFBluetoothManager.isUsbCurrentScanning();
    }

    public boolean setUsbBluetoothEnabled(boolean z) {
        return this.mNFBluetoothManager.setUsbBluetoothEnabled(z);
    }

    public void registerCallback(IXpPsnBluetoothCallback iXpPsnBluetoothCallback) {
        this.mNFBluetoothManager.registerPsnStateCallback(iXpPsnBluetoothCallback);
    }

    public void unregisterCallback(IXpPsnBluetoothCallback iXpPsnBluetoothCallback) {
        this.mNFBluetoothManager.unregisterPsnStateCallback(iXpPsnBluetoothCallback);
    }

    public boolean usbUnpair(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return this.mNFBluetoothManager.usbUnpair(psnBluetoothDeviceInfo);
    }

    public boolean disUsbConnect(String str) {
        return this.mNFBluetoothManager.disUsbConnect(str);
    }

    public ArrayList<PsnBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mNFBluetoothManager.getAvailableUsbDevicesSorted();
    }

    public ArrayList<PsnBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mNFBluetoothManager.getBondedDevicesSorted();
    }

    public boolean usbConnect(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return this.mNFBluetoothManager.usbConnect(psnBluetoothDeviceInfo.getDeviceAddr());
    }

    public void registerServiceConnectCallback(NFServiceConnectedCallback nFServiceConnectedCallback) {
        this.mNFBluetoothManager.registerServiceConnectCallback(nFServiceConnectedCallback);
    }

    public void unregisterServiceConnectCallback(NFServiceConnectedCallback nFServiceConnectedCallback) {
        this.mNFBluetoothManager.unregisterServiceConnectCallback(nFServiceConnectedCallback);
    }

    public void addItemChangeListener(PsnNFBluetoothManager.ItemChangeListener itemChangeListener) {
        this.mNFBluetoothManager.addItemChangeListener(itemChangeListener);
    }

    public void removeItemChangeListener(PsnNFBluetoothManager.ItemChangeListener itemChangeListener) {
        this.mNFBluetoothManager.removeItemChangeListener(itemChangeListener);
    }

    public String getUsbAddr() {
        return this.mNFBluetoothManager.getUsbAddr();
    }

    public String getUsbConnectedDevice() {
        return this.mNFBluetoothManager.getUsbConnectedDevice();
    }

    public boolean usbSearchStop() {
        return this.mNFBluetoothManager.usbSearchStop();
    }

    public PsnBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        return this.mNFBluetoothManager.getXpPsnBluetoothDevice(str);
    }
}
