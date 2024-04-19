package com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat;

import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBaseBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class ExternalBluetoothManger {
    private static volatile ExternalBluetoothManger sInstance;
    ExternalBaseBluetoothManager mNFBluetoothManager = new ExternalBaseBluetoothManager();

    /* loaded from: classes.dex */
    public interface NFServiceConnectedCallback {
        void connectCompleted();
    }

    public static ExternalBluetoothManger getInstance() {
        if (sInstance == null) {
            synchronized (ExternalBluetoothManger.class) {
                if (sInstance == null) {
                    sInstance = new ExternalBluetoothManger();
                }
            }
        }
        return sInstance;
    }

    private ExternalBluetoothManger() {
    }

    public void registerExternalBluetoothCallback() {
        this.mNFBluetoothManager.registerExternalNFBluetoothCallback();
    }

    public void unregisterExternalBluetoothCallback() {
        this.mNFBluetoothManager.unregisterExternalNFBluetoothCallback();
    }

    public boolean startSearch() {
        return this.mNFBluetoothManager.startSearch();
    }

    public boolean connectDevice(String str) {
        return this.mNFBluetoothManager.connectDevice(str);
    }

    public boolean isDeviceEnable() {
        return this.mNFBluetoothManager.isDeviceEnable();
    }

    public boolean isDiscovering() {
        return this.mNFBluetoothManager.isDiscovering();
    }

    public boolean isEnable(boolean z) {
        return this.mNFBluetoothManager.setDeviceEnabled(z);
    }

    public void registerCallback(IXpExternalBluetoothCallback iXpExternalBluetoothCallback) {
        this.mNFBluetoothManager.registerExternalStateCallback(iXpExternalBluetoothCallback);
    }

    public void unregisterCallback(IXpExternalBluetoothCallback iXpExternalBluetoothCallback) {
        this.mNFBluetoothManager.unregisterExternalStateCallback(iXpExternalBluetoothCallback);
    }

    public boolean unpair(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return this.mNFBluetoothManager.unpair(externalBluetoothDeviceInfo);
    }

    public boolean disConnectDevice(String str) {
        return this.mNFBluetoothManager.disConnectDevice(str);
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mNFBluetoothManager.getAvailableDevicesSorted();
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mNFBluetoothManager.getBondedDevicesSorted();
    }

    public boolean connectDevice(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return this.mNFBluetoothManager.connectDevice(externalBluetoothDeviceInfo.getDeviceAddr());
    }

    public void registerServiceConnectCallback(NFServiceConnectedCallback nFServiceConnectedCallback) {
        this.mNFBluetoothManager.registerServiceConnectCallback(nFServiceConnectedCallback);
    }

    public void unregisterServiceConnectCallback(NFServiceConnectedCallback nFServiceConnectedCallback) {
        this.mNFBluetoothManager.unregisterServiceConnectCallback(nFServiceConnectedCallback);
    }

    public void addItemChangeListener(ExternalBaseBluetoothManager.ItemChangeListener itemChangeListener) {
        this.mNFBluetoothManager.addItemChangeListener(itemChangeListener);
    }

    public void removeItemChangeListener(ExternalBaseBluetoothManager.ItemChangeListener itemChangeListener) {
        this.mNFBluetoothManager.removeItemChangeListener(itemChangeListener);
    }

    public String getConnectedDevice() {
        return this.mNFBluetoothManager.getConnectedDevice();
    }

    public boolean stopSearch() {
        return this.mNFBluetoothManager.stopSearch();
    }

    public ExternalBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        return this.mNFBluetoothManager.getXpExternalBluetoothDevice(str);
    }
}
