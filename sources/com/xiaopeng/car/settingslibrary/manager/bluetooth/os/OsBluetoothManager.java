package com.xiaopeng.car.settingslibrary.manager.bluetooth.os;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.android.internal.util.CollectionUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.A2dpSinkProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.BluetoothUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HfpClientProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HidProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.ProjectorAutoConnectManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class OsBluetoothManager extends AbsBluetoothManager {
    public final Comparator<XpBluetoothDeviceInfo> ALPHA_COMPARATOR;
    private A2dpSinkProfile mA2dpSinkProfile;
    private BluetoothAdapter mAdapter;
    private IntentFilter mAdapterIntentFilter;
    CopyOnWriteArrayList<IXpBluetoothCallback> mBluetoothCallbacks;
    private final BroadcastReceiver mBroadcastReceiver;
    private HfpClientProfile mHfpClientProfile;
    private HidProfile mHidProfile;
    private final BroadcastReceiver mProfileBroadcastReceiver;
    private IntentFilter mProfileIntentFilter;
    private ProjectorAutoConnectManager mProjectorAutoConnectManager;
    Map<String, XpBluetoothDeviceInfo> mXpBondedBluetoothDeviceCacheInfos;
    Map<String, XpBluetoothDeviceInfo> mXpNewBluetoothDeviceCacheInfos;
    ArrayList<XpBluetoothDeviceInfo> mXpTmpFoundBluetoothDeviceInfos;

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioModeStateChanged() {
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerBluetoothCallback() {
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback) {
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void setConnectOperateErrorListener(XpBluetoothManger.ConnectErrorListener connectErrorListener) {
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterBluetoothCallback() {
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback) {
    }

    public OsBluetoothManager(Context context) {
        super(context);
        this.mBluetoothCallbacks = new CopyOnWriteArrayList<>();
        this.mProfileBroadcastReceiver = new ProfileBroadcastReceiver();
        this.mBroadcastReceiver = new BluetoothBroadcastReceiver();
        this.mXpNewBluetoothDeviceCacheInfos = new ConcurrentHashMap();
        this.mXpTmpFoundBluetoothDeviceInfos = new ArrayList<>();
        this.mXpBondedBluetoothDeviceCacheInfos = new ConcurrentHashMap();
        this.ALPHA_COMPARATOR = new Comparator<XpBluetoothDeviceInfo>() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.1
            @Override // java.util.Comparator
            public int compare(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, XpBluetoothDeviceInfo xpBluetoothDeviceInfo2) {
                int compareTo;
                int compareTo2;
                String deviceAddr = xpBluetoothDeviceInfo.getDeviceAddr();
                String deviceAddr2 = xpBluetoothDeviceInfo2.getDeviceAddr();
                String deviceName = xpBluetoothDeviceInfo.getDeviceName();
                String deviceName2 = xpBluetoothDeviceInfo2.getDeviceName();
                if (!TextUtils.isEmpty(deviceAddr) && !TextUtils.isEmpty(deviceAddr2)) {
                    if (deviceAddr.equals(deviceName) && !deviceAddr2.equals(deviceName2)) {
                        return 1;
                    }
                    if (deviceAddr2.equals(deviceName2) && !deviceAddr.equals(deviceName)) {
                        return -1;
                    }
                }
                if (TextUtils.isEmpty(deviceName) || TextUtils.isEmpty(deviceName2) || (compareTo2 = deviceName.compareTo(deviceName2)) == 0) {
                    if (TextUtils.isEmpty(deviceAddr) || TextUtils.isEmpty(deviceAddr2) || (compareTo = deviceAddr.compareTo(deviceAddr2)) == 0) {
                        return 0;
                    }
                    return compareTo;
                }
                return compareTo2;
            }
        };
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        registerBluetoothBroadcast();
        updateLocalProfiles();
        this.mProjectorAutoConnectManager = new ProjectorAutoConnectManager(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isEnable() {
        return this.mAdapter.isEnabled();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean setBluetoothEnabled(boolean z) {
        if (z) {
            return this.mAdapter.enable();
        }
        return this.mAdapter.disable();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean startScanList() {
        if (this.mAdapter.isDiscovering()) {
            return true;
        }
        return this.mAdapter.startDiscovery();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isCurrentScanning() {
        return this.mAdapter.isDiscovering();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void stopScanDevice() {
        if (this.mAdapter.isDiscovering()) {
            this.mAdapter.cancelDiscovery();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean setName(String str) {
        return this.mAdapter.setName(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public String getName() {
        return this.mAdapter.getName();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isBtDiscoverable() {
        return this.mAdapter.getScanMode() == 23;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void setBtDiscoverable(boolean z) {
        this.mAdapter.setDiscoverableTimeout(0);
        if (z) {
            this.mAdapter.setScanMode(23);
        } else {
            this.mAdapter.setScanMode(21);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerStateCallback(IXpBluetoothCallback iXpBluetoothCallback) {
        if (iXpBluetoothCallback == null || this.mBluetoothCallbacks.contains(iXpBluetoothCallback)) {
            return;
        }
        this.mBluetoothCallbacks.add(iXpBluetoothCallback);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void setBleWhiteListMode(boolean z) {
        this.mProjectorAutoConnectManager.setBleWhiteListMode(z);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void forceStartScanRetry() {
        this.mProjectorAutoConnectManager.forceStartScanRetry();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public boolean isAlreadyConnectedWhiteList() {
        return this.mProjectorAutoConnectManager.isAlreadyConnectedWhiteList();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void timeoutForceStopScanRetry() {
        this.mProjectorAutoConnectManager.timeoutForceStopScanRetry();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public boolean isFoundWhiteListDevice() {
        return this.mProjectorAutoConnectManager.isFoundWhiteListDevice();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public boolean isAlreadyBoundedWhiteList() {
        return this.mProjectorAutoConnectManager.isAlreadyBoundedWhiteList();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public boolean isConnectingWhiteList() {
        return this.mProjectorAutoConnectManager.isConnectingWhiteList();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public boolean isBondingWhiteList() {
        return this.mProjectorAutoConnectManager.isBondingWhiteList();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void timeoutForceStopPairOrConnectRetry() {
        this.mProjectorAutoConnectManager.timeoutForceStopPairOrConnectRetry();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void forceStartPairOrConnectRetry() {
        this.mProjectorAutoConnectManager.forceStartPairOrConnectRetry();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface, com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void unregisterStateCallback(IXpBluetoothCallback iXpBluetoothCallback) {
        if (iXpBluetoothCallback != null) {
            this.mBluetoothCallbacks.remove(iXpBluetoothCallback);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface, com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorInterface
    public void registerProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        this.mProjectorAutoConnectManager.registerProjectorStateCallback(iProjectorBluetoothCallback);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        this.mProjectorAutoConnectManager.unregisterProjectorStateCallback(iProjectorBluetoothCallback);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean unpair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        BluetoothDevice bluetoothDevice;
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            int bondState = getBondState(xpBluetoothDeviceInfo);
            Logs.d("xpbluetooth os unpair state:" + bondState);
            if (bondState == 11) {
                osBluetoothDevice.mDevice.cancelBondProcess();
            }
            if (bondState == 10 || (bluetoothDevice = osBluetoothDevice.mDevice) == null) {
                return false;
            }
            boolean removeBond = bluetoothDevice.removeBond();
            Logs.d("xpbluetooth os unpair " + removeBond);
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void notifyRefreshListCallback() {
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onRefreshData();
        }
    }

    private void notifyRetPairedDevicesCallback() {
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onRetPairedDevices();
        }
    }

    private void onDeviceFounded(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceFounded(xpBluetoothDeviceInfo);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void notifyItemChange(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().notifyItemChanged(xpBluetoothDeviceInfo);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void realDeviceBondStateChanged(String str, String str2, int i, int i2, boolean z) {
        updateBondStateForData(str, str2, i2, z);
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceBondStateChanged(str, str2, i, i2);
        }
        if (this.mProjectorAutoConnectManager.isActiveProjectorDevice(str2)) {
            this.mProjectorAutoConnectManager.notifyBondChanged(i, i2);
            if (i == 11 && i2 == 10) {
                Logs.d("xpbluetooth OS now in capsule mode, retry pair:" + str2 + " addr:" + str);
                this.mProjectorAutoConnectManager.retryPairOrConnect();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean disConnect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.disConnect();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean connect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        connectAndRetry(xpBluetoothDeviceInfo);
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void connectAndRetry(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice;
        if (ensurePaired(xpBluetoothDeviceInfo) && (osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) != null) {
            boolean connectAllEnabledProfiles = osBluetoothDevice.connectAllEnabledProfiles();
            Logs.d("xpbluetooth os connectAndRetry " + connectAllEnabledProfiles);
        }
    }

    private boolean ensurePaired(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (getBondState(xpBluetoothDeviceInfo) == 10) {
            pair(xpBluetoothDeviceInfo, xpBluetoothDeviceInfo.getDeviceAddr());
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean pair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, String str) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            if (this.mAdapter.isDiscovering()) {
                this.mAdapter.cancelDiscovery();
            }
            return osBluetoothDevice.mDevice.createBond();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted() {
        ArrayList<XpBluetoothDeviceInfo> arrayList = new ArrayList<>();
        Logs.d("xpbluetooth os getBondedDevicesSorted:" + this.mXpBondedBluetoothDeviceCacheInfos.size());
        arrayList.addAll(this.mXpBondedBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        return arrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted() {
        ArrayList<XpBluetoothDeviceInfo> arrayList = new ArrayList<>();
        arrayList.addAll(this.mXpNewBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        Logs.d("xpbluetooth os getAvailableDevicesSorted " + arrayList.size());
        return arrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isBtPhoneConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        return osBluetoothDevice != null && this.mHfpClientProfile.getConnectionStatus(osBluetoothDevice.mDevice) == 2;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isA2dpConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        return osBluetoothDevice != null && this.mA2dpSinkProfile.getConnectionStatus(osBluetoothDevice.mDevice) == 2;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isBusy(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.isBusy();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isParing(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.isParing();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isConnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.isConnecting();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isDisconnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.isDisconnecting();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public int getBondState(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.getBondState();
        }
        return 10;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        OsBluetoothDevice osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (osBluetoothDevice != null) {
            return osBluetoothDevice.isConnected();
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isHasPhoneConnected() {
        for (BluetoothDevice bluetoothDevice : this.mAdapter.getBondedDevices()) {
            if (bluetoothDevice.isConnected() && Utils.isPhone(bluetoothDevice)) {
                HfpClientProfile hfpClientProfile = this.mHfpClientProfile;
                if (hfpClientProfile != null && hfpClientProfile.getConnectionStatus(bluetoothDevice) == 2) {
                    return true;
                }
                A2dpSinkProfile a2dpSinkProfile = this.mA2dpSinkProfile;
                if (a2dpSinkProfile != null && a2dpSinkProfile.getConnectionStatus(bluetoothDevice) == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void reqBtDevicePairedDevices() {
        OsBluetoothDevice osBluetoothDevice;
        Set<BluetoothDevice> bondedDevices = this.mAdapter.getBondedDevices();
        HashMap hashMap = new HashMap();
        Logs.d("xpbluetooth os refreshPairedDevice :" + bondedDevices);
        if (bondedDevices != null && bondedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                String name = getName(bluetoothDevice);
                String address = bluetoothDevice.getAddress();
                XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(address);
                Logs.d("xpbluetooth os refreshPairedDevice xpBluetoothDeviceInfo:" + xpBluetoothDevice + " deviceAddress:" + address + " deviceName:" + name);
                if (xpBluetoothDevice == null) {
                    xpBluetoothDevice = new XpBluetoothDeviceInfo(name, address);
                    osBluetoothDevice = new OsBluetoothDevice(this.mAdapter, bluetoothDevice, this);
                } else {
                    osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
                }
                xpBluetoothDevice.setCategory(Utils.getBluetoothCategory(bluetoothDevice.getBluetoothClass()));
                xpBluetoothDevice.setDeviceName(name);
                xpBluetoothDevice.setDeviceAddr(address);
                xpBluetoothDevice.setBluetoothDevice(osBluetoothDevice);
                hashMap.put(address, xpBluetoothDevice);
                this.mXpNewBluetoothDeviceCacheInfos.remove(xpBluetoothDevice.getDeviceAddr());
            }
            this.mXpBondedBluetoothDeviceCacheInfos.clear();
            this.mXpBondedBluetoothDeviceCacheInfos.putAll(hashMap);
        } else {
            this.mXpBondedBluetoothDeviceCacheInfos.clear();
        }
        notifyRefreshListCallback();
        this.mProjectorAutoConnectManager.setConnectedBleDeviceUpdate();
        notifyRetPairedDevicesCallback();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public XpBluetoothDeviceInfo getXpBluetoothDevice(String str) {
        for (XpBluetoothDeviceInfo xpBluetoothDeviceInfo : this.mXpBondedBluetoothDeviceCacheInfos.values()) {
            if (xpBluetoothDeviceInfo.getDeviceAddr().equals(str)) {
                return xpBluetoothDeviceInfo;
            }
        }
        for (XpBluetoothDeviceInfo xpBluetoothDeviceInfo2 : this.mXpNewBluetoothDeviceCacheInfos.values()) {
            if (xpBluetoothDeviceInfo2.getDeviceAddr().equals(str)) {
                return xpBluetoothDeviceInfo2;
            }
        }
        return null;
    }

    public BluetoothDevice getRemoteDevice(String str) {
        return this.mAdapter.getRemoteDevice(str);
    }

    public List<Integer> getSupportedProfiles() {
        return this.mAdapter.getSupportedProfiles();
    }

    void updateLocalProfiles() {
        this.mProfileIntentFilter = new IntentFilter();
        List supportedProfiles = BluetoothAdapter.getDefaultAdapter().getSupportedProfiles();
        if (CollectionUtils.isEmpty(supportedProfiles)) {
            Logs.d("xpbluetooth os supportedList is null");
            return;
        }
        Iterator it = supportedProfiles.iterator();
        while (it.hasNext()) {
            Logs.d("xpbluetooth os supportedList profile:" + ((Integer) it.next()));
        }
        if (this.mA2dpSinkProfile == null && supportedProfiles.contains(11)) {
            Logs.d("xpbluetooth os Adding local A2DP SINK profile");
            this.mA2dpSinkProfile = new A2dpSinkProfile(this.mContext, this);
            this.mProfileIntentFilter.addAction("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mHfpClientProfile == null && supportedProfiles.contains(16)) {
            Logs.d("xpbluetooth os Adding local HfpClient profile");
            this.mHfpClientProfile = new HfpClientProfile(this.mContext, this);
            this.mProfileIntentFilter.addAction("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED");
            this.mProfileIntentFilter.addAction("android.bluetooth.headsetclient.profile.action.AUDIO_STATE_CHANGED");
        }
        if (this.mHidProfile == null && supportedProfiles.contains(4)) {
            Logs.d("xpbluetooth os Adding local HID_HOST profile");
            this.mHidProfile = new HidProfile(this.mContext, this);
            this.mProfileIntentFilter.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        }
        this.mContext.registerReceiver(this.mProfileBroadcastReceiver, this.mProfileIntentFilter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updateProfiles(BluetoothDevice bluetoothDevice, ParcelUuid[] parcelUuidArr, ParcelUuid[] parcelUuidArr2, Collection<LocalBluetoothProfile> collection) {
        collection.clear();
        if (parcelUuidArr == null) {
            LogUtils.d("xpBluetooth os profiles uuids == null");
            return;
        }
        if (this.mHfpClientProfile != null && BluetoothUtils.isSupportHfp(parcelUuidArr, parcelUuidArr2)) {
            Logs.d("xpbluetooth os device add hfp:" + bluetoothDevice.getAddress() + " " + bluetoothDevice.getName());
            collection.add(this.mHfpClientProfile);
        }
        if (BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpSinkProfile.SRC_UUIDS) && this.mA2dpSinkProfile != null) {
            Logs.d("xpbluetooth os device add a2dp:" + bluetoothDevice.getAddress() + " " + bluetoothDevice.getName());
            collection.add(this.mA2dpSinkProfile);
        }
        if (BluetoothUtils.isSupportHid(parcelUuidArr, parcelUuidArr2) && this.mHidProfile != null) {
            Logs.d("xpbluetooth os device add hid:" + bluetoothDevice.getAddress() + " " + bluetoothDevice.getName());
            collection.add(this.mHidProfile);
        }
    }

    /* loaded from: classes.dex */
    private class ProfileBroadcastReceiver extends BroadcastReceiver {
        private ProfileBroadcastReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0061, code lost:
            if (r8.equals("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED") != false) goto L6;
         */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                java.lang.String r8 = r9.getAction()
                java.lang.String r0 = "android.bluetooth.device.extra.DEVICE"
                android.os.Parcelable r0 = r9.getParcelableExtra(r0)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                r1 = 0
                java.lang.String r2 = "android.bluetooth.profile.extra.STATE"
                int r2 = r9.getIntExtra(r2, r1)
                java.lang.String r3 = "android.bluetooth.profile.extra.PREVIOUS_STATE"
                int r9 = r9.getIntExtra(r3, r1)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "xpbluetooth os onReceive action:"
                r3.append(r4)
                r3.append(r8)
                java.lang.String r4 = " newState:"
                r3.append(r4)
                r3.append(r2)
                java.lang.String r4 = " oldState:"
                r3.append(r4)
                r3.append(r9)
                java.lang.String r4 = " device:"
                r3.append(r4)
                r3.append(r0)
                java.lang.String r3 = r3.toString()
                com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r3)
                int r3 = r8.hashCode()
                r4 = 3
                r5 = 2
                r6 = 1
                switch(r3) {
                    case -1067080054: goto L6e;
                    case -1021360715: goto L64;
                    case 40146574: goto L5b;
                    case 448318136: goto L51;
                    default: goto L50;
                }
            L50:
                goto L78
            L51:
                java.lang.String r1 = "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"
                boolean r8 = r8.equals(r1)
                if (r8 == 0) goto L78
                r1 = r6
                goto L79
            L5b:
                java.lang.String r3 = "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED"
                boolean r8 = r8.equals(r3)
                if (r8 == 0) goto L78
                goto L79
            L64:
                java.lang.String r1 = "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED"
                boolean r8 = r8.equals(r1)
                if (r8 == 0) goto L78
                r1 = r4
                goto L79
            L6e:
                java.lang.String r1 = "android.bluetooth.headsetclient.profile.action.AUDIO_STATE_CHANGED"
                boolean r8 = r8.equals(r1)
                if (r8 == 0) goto L78
                r1 = r5
                goto L79
            L78:
                r1 = -1
            L79:
                if (r1 == 0) goto L9b
                if (r1 == r6) goto L8f
                if (r1 == r5) goto L8f
                if (r1 == r4) goto L83
                r8 = 0
                goto La6
            L83:
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                com.xiaopeng.car.settingslibrary.manager.bluetooth.HidProfile r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.access$500(r8)
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r1 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                r1.onHidStateChanged(r0, r9, r2)
                goto La6
            L8f:
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                com.xiaopeng.car.settingslibrary.manager.bluetooth.HfpClientProfile r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.access$400(r8)
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r1 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                r1.onHfpStateChanged(r0, r9, r2)
                goto La6
            L9b:
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                com.xiaopeng.car.settingslibrary.manager.bluetooth.A2dpSinkProfile r8 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.access$200(r8)
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager r1 = com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.this
                com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.access$300(r1, r0, r9, r2)
            La6:
                if (r2 != 0) goto Lc4
                if (r9 != r6) goto Lc4
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r0 = "xpbluetooth os Failed to connect "
                r9.append(r0)
                r9.append(r8)
                java.lang.String r8 = " device"
                r9.append(r8)
                java.lang.String r8 = r9.toString()
                com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r8)
            Lc4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager.ProfileBroadcastReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onA2dpStateChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        Logs.d("xpbluetooth os profile onA2dpStateChanged address:" + address + " name:" + getName(bluetoothDevice) + " prevState:" + i + " newState:" + i2);
        notifyRefreshListCallback();
        notifyItemChange(getXpBluetoothDevice(address));
        this.mProjectorAutoConnectManager.a2dpChanged(address, i, i2);
        this.mProjectorAutoConnectManager.refreshBleConnectedData(i2, getXpBluetoothDevice(address), 2);
    }

    public void onHfpStateChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        Logs.d("xpbluetooth os profile onHfpStateChanged address:" + address + " name:" + getName(bluetoothDevice) + " prevState:" + i + " newState:" + i2);
        notifyRefreshListCallback();
        notifyItemChange(getXpBluetoothDevice(address));
        this.mProjectorAutoConnectManager.refreshBleConnectedData(i2, getXpBluetoothDevice(address), 1);
    }

    public void onHidStateChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        Logs.d("xpbluetooth os profile onHidStateChanged address:" + address + " prevState:" + i + " newState:" + i2);
        notifyRefreshListCallback();
        notifyItemChange(getXpBluetoothDevice(address));
        this.mProjectorAutoConnectManager.refreshBleConnectedData(i2, getXpBluetoothDevice(bluetoothDevice.getAddress()), 3);
    }

    public void registerBluetoothBroadcast() {
        this.mAdapterIntentFilter = new IntentFilter();
        this.mAdapterIntentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.FOUND");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.NAME_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.ALIAS_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.CLASS_CHANGED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.UUID");
        this.mAdapterIntentFilter.addAction("android.intent.action.PHONE_STATE");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, this.mAdapterIntentFilter);
    }

    /* loaded from: classes.dex */
    private class BluetoothBroadcastReceiver extends BroadcastReceiver {
        private BluetoothBroadcastReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Logs.d("xpbluetooth os receiver " + bluetoothDevice + " action:" + action);
            switch (action.hashCode()) {
                case -1780914469:
                    if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1530327060:
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1458768370:
                    if (action.equals("android.bluetooth.device.action.CLASS_CHANGED")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1435586571:
                    if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1326089125:
                    if (action.equals("android.intent.action.PHONE_STATE")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -377527494:
                    if (action.equals("android.bluetooth.device.action.UUID")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -301431627:
                    if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 6759640:
                    if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1123270207:
                    if (action.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1167529923:
                    if (action.equals("android.bluetooth.device.action.FOUND")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1174571750:
                    if (action.equals("android.bluetooth.device.action.ALIAS_CHANGED")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1821585647:
                    if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 2047137119:
                    if (action.equals("android.bluetooth.device.action.NAME_CHANGED")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2116862345:
                    if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    OsBluetoothManager.this.onAdapterStateChanged(intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", Integer.MIN_VALUE), intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE));
                    return;
                case 1:
                    OsBluetoothManager.this.onConnectedStateChanged(bluetoothDevice, intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE", Integer.MIN_VALUE), intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE));
                    return;
                case 2:
                    OsBluetoothManager.this.onScanningStateChanged(true);
                    return;
                case 3:
                    OsBluetoothManager.this.onScanningStateChanged(false);
                    return;
                case 4:
                    intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MIN_VALUE);
                    intent.getStringExtra("android.bluetooth.device.extra.NAME");
                    OsBluetoothManager.this.onDeviceFoundChanged(bluetoothDevice);
                    return;
                case 5:
                case 6:
                    OsBluetoothManager.this.onDeviceNameChanged(bluetoothDevice);
                    return;
                case 7:
                    OsBluetoothManager.this.onDeviceBondChanged(bluetoothDevice, intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", Integer.MIN_VALUE), intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE));
                    return;
                case '\b':
                default:
                    return;
                case '\t':
                    OsBluetoothManager.this.onUuidChanged(bluetoothDevice);
                    return;
                case '\n':
                case 11:
                    OsBluetoothManager.this.onAudioModeStateChanged();
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdapterStateChanged(int i, int i2) {
        Logs.d("xpbluetooth os onAdapterStateChanged state:" + i2);
        if (i2 == 10) {
            this.mXpTmpFoundBluetoothDeviceInfos.clear();
            this.mXpBondedBluetoothDeviceCacheInfos.clear();
            this.mXpNewBluetoothDeviceCacheInfos.clear();
        }
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onBluetoothStateChanged(i, i2);
        }
        if (i2 == 10) {
            setBtDiscoverable(false);
        }
        if (i2 == 12) {
            updateLocalProfiles();
            setBtDiscoverable(true);
            if (this.mProjectorAutoConnectManager.isInWhiteListModeAndCinema()) {
                reqBtDevicePairedDevices();
                this.mProjectorAutoConnectManager.retryScan();
            }
        }
        this.mProjectorAutoConnectManager.setConnectedBleDeviceUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectedStateChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        Logs.d("xpbluetooth os onConnectedStateChanged " + i2 + " prev:" + i);
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onConnectionStateChanged(bluetoothDevice.getAddress(), bluetoothDevice.getName(), i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanningStateChanged(boolean z) {
        Logs.d("xpbluetooth os onScanningStateChanged " + z);
        if (z) {
            this.mXpTmpFoundBluetoothDeviceInfos.clear();
            Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onScanningStateChanged(true);
            }
            return;
        }
        this.mProjectorAutoConnectManager.parseScanResult(this.mXpTmpFoundBluetoothDeviceInfos);
        Iterator<IXpBluetoothCallback> it2 = this.mBluetoothCallbacks.iterator();
        while (it2.hasNext()) {
            it2.next().onScanningStateChanged(false);
        }
        checkRemovedDevices();
        notifyRefreshListCallback();
    }

    private void checkRemovedDevices() {
        if (this.mXpNewBluetoothDeviceCacheInfos.size() == this.mXpTmpFoundBluetoothDeviceInfos.size()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mXpNewBluetoothDeviceCacheInfos.values());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo = (XpBluetoothDeviceInfo) it.next();
            if (!this.mXpTmpFoundBluetoothDeviceInfos.contains(xpBluetoothDeviceInfo)) {
                if (xpBluetoothDeviceInfo.isConnectingBusy() || isConnecting(xpBluetoothDeviceInfo) || xpBluetoothDeviceInfo.isParingBusy() || isParing(xpBluetoothDeviceInfo) || xpBluetoothDeviceInfo.isDisconnectingBusy() || isDisconnecting(xpBluetoothDeviceInfo)) {
                    Logs.d("xpbluetooth nf can not remove device:" + xpBluetoothDeviceInfo);
                } else {
                    this.mXpNewBluetoothDeviceCacheInfos.remove(xpBluetoothDeviceInfo.getDeviceAddr());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceFoundChanged(BluetoothDevice bluetoothDevice) {
        OsBluetoothDevice osBluetoothDevice;
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        String name = getName(bluetoothDevice);
        if (address.equals(name)) {
            return;
        }
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo = null;
        Iterator<XpBluetoothDeviceInfo> it = this.mXpBondedBluetoothDeviceCacheInfos.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            XpBluetoothDeviceInfo next = it.next();
            Logs.v("xpbluetooth nf onDeviceFound xpBluetoothDeviceInfo:" + next);
            if (address.equals(next.getDeviceAddr())) {
                this.mXpTmpFoundBluetoothDeviceInfos.add(next);
                xpBluetoothDeviceInfo = next;
                break;
            }
        }
        if (xpBluetoothDeviceInfo == null) {
            XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(address);
            BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
            if (remoteDevice == null || Utils.isFilterDevice(remoteDevice)) {
                return;
            }
            BluetoothClass bluetoothClass = remoteDevice.getBluetoothClass();
            if (xpBluetoothDevice == null) {
                xpBluetoothDevice = new XpBluetoothDeviceInfo(name, address);
                osBluetoothDevice = new OsBluetoothDevice(this.mAdapter, bluetoothDevice, this);
            } else {
                osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
            }
            xpBluetoothDevice.setCategory(Utils.getBluetoothCategory(bluetoothClass));
            xpBluetoothDevice.setDeviceName(name);
            xpBluetoothDevice.setDeviceAddr(address);
            xpBluetoothDevice.setBluetoothDevice(osBluetoothDevice);
            if (!this.mXpBondedBluetoothDeviceCacheInfos.containsKey(address)) {
                this.mXpNewBluetoothDeviceCacheInfos.put(address, xpBluetoothDevice);
                this.mXpTmpFoundBluetoothDeviceInfos.add(xpBluetoothDevice);
            }
            notifyRefreshListCallback();
            onDeviceFounded(xpBluetoothDevice);
        }
    }

    private String getName(BluetoothDevice bluetoothDevice) {
        String alias = bluetoothDevice.getAlias();
        return TextUtils.isEmpty(alias) ? bluetoothDevice.getAddress() : alias;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceNameChanged(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        String name = getName(bluetoothDevice);
        Logs.d("xpbluetooth os refreshDeviceName name:" + name + " address:" + bluetoothDevice.getAddress());
        XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(bluetoothDevice.getAddress());
        if (!TextUtils.isEmpty(name) && xpBluetoothDevice != null && !name.equals(xpBluetoothDevice.getDeviceName())) {
            xpBluetoothDevice.setDeviceName(name);
        }
        notifyItemChange(xpBluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceBondChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        String name = bluetoothDevice.getName();
        Logs.d("xpbluetooth os onDeviceBondStateChanged address:" + address + " name:" + name + " prevState:" + i + " newState:" + i2);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            boolean isHidDevice = Utils.isHidDevice(defaultAdapter.getRemoteDevice(address));
            Logs.d("xpbluetooth nf onDeviceBondStateChanged isHid:" + isHidDevice);
            if (isHidDevice) {
                return;
            }
            realDeviceBondStateChanged(address, name, i, i2, false);
        }
    }

    public void updateBondStateForData(String str, String str2, int i, boolean z) {
        OsBluetoothDevice osBluetoothDevice;
        notifyItemChange(getXpBluetoothDevice(str));
        XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(str);
        if (xpBluetoothDevice != null && (osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice()) != null) {
            osBluetoothDevice.onBondingStateChanged(i);
        }
        if (i == 10) {
            if (xpBluetoothDevice != null) {
                this.mXpNewBluetoothDeviceCacheInfos.put(str, xpBluetoothDevice);
                this.mXpTmpFoundBluetoothDeviceInfos.add(xpBluetoothDevice);
                this.mXpBondedBluetoothDeviceCacheInfos.remove(str);
            }
            reqBtDevicePairedDevices();
        } else if (i != 12) {
        } else {
            if (xpBluetoothDevice != null) {
                this.mXpNewBluetoothDeviceCacheInfos.remove(xpBluetoothDevice.getDeviceAddr());
                this.mXpBondedBluetoothDeviceCacheInfos.put(str, xpBluetoothDevice);
            }
            reqBtDevicePairedDevices();
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(str);
                StringBuilder sb = new StringBuilder();
                sb.append("xpbluetooth nf isBondingInitiatedLocally:");
                boolean z2 = false;
                sb.append(remoteDevice != null && remoteDevice.isBondingInitiatedLocally());
                Logs.d(sb.toString());
                if (remoteDevice == null || !remoteDevice.isBondingInitiatedLocally()) {
                    return;
                }
                if (!Utils.isHidDevice(remoteDevice)) {
                    if (xpBluetoothDevice != null) {
                        z2 = connect(xpBluetoothDevice);
                    } else {
                        Logs.d("xpbluetooth os bond and connect profile error!");
                    }
                    if (z2 || !this.mProjectorAutoConnectManager.isActiveProjectorDevice(str2)) {
                        return;
                    }
                    this.mProjectorAutoConnectManager.retryPairOrConnect();
                    return;
                }
                Logs.d("xpbluetooth nf is Hid device!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUuidChanged(BluetoothDevice bluetoothDevice) {
        OsBluetoothDevice osBluetoothDevice;
        XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(bluetoothDevice.getAddress());
        if (xpBluetoothDevice == null || (osBluetoothDevice = (OsBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice()) == null) {
            return;
        }
        osBluetoothDevice.onUuidChanged();
    }
}
