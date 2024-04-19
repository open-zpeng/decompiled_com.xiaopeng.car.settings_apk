package com.xiaopeng.car.settingslibrary.manager.bluetooth.nf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.nforetek.bt.aidl.UsbBluetoothDevice;
import com.xiaopeng.btservice.base.AbstractConnection;
import com.xiaopeng.btservice.usb.UsbBluetoothControl;
import com.xiaopeng.btservice.util.BtBoxesUtil;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class PsnNFBluetoothManager implements AbstractConnection.ConnecteCompletedCallback {
    private static final String HEADSET_COD = "0404";
    private static final String HEADSET_COD_2 = "0418";
    private static final String TAG = "Psn-NFBluetooth";
    private boolean mIsRegisterNF;
    ArrayList<IXpPsnBluetoothCallback> mPsnBluetoothCallbacks = new ArrayList<>();
    Map<String, PsnBluetoothDeviceInfo> mXpPsnNewBluetoothDeviceCacheInfos = new ConcurrentHashMap();
    ArrayList<PsnBluetoothDeviceInfo> mXpTmpPsnFoundBluetoothDeviceInfos = new ArrayList<>();
    Map<String, PsnBluetoothDeviceInfo> mXpPsnBondedBluetoothDeviceCacheInfos = new ConcurrentHashMap();
    List<PsnBluetoothManger.NFServiceConnectedCallback> mServiceConnectedCallback = new ArrayList();
    public final Comparator<PsnBluetoothDeviceInfo> ALPHA_COMPARATOR = new Comparator<PsnBluetoothDeviceInfo>() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager.2
        @Override // java.util.Comparator
        public int compare(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo, PsnBluetoothDeviceInfo psnBluetoothDeviceInfo2) {
            int compareTo;
            int compareTo2;
            String deviceAddr = psnBluetoothDeviceInfo.getDeviceAddr();
            String deviceAddr2 = psnBluetoothDeviceInfo2.getDeviceAddr();
            String deviceName = psnBluetoothDeviceInfo.getDeviceName();
            String deviceName2 = psnBluetoothDeviceInfo2.getDeviceName();
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
    BluetoothReceiver mReceiver = new BluetoothReceiver();
    List<ItemChangeListener> mItemChangeListenerList = new CopyOnWriteArrayList();
    BtBoxesUtil mBtBoxesUtil = CarSettingsApp.getBtBoxesUtil();
    UsbBluetoothControl mUsbBluetoothControl = this.mBtBoxesUtil.getUsbBluetoothControl(null);

    /* loaded from: classes.dex */
    public interface ItemChangeListener {
        void notifyItemChanged(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo);
    }

    public boolean isBtPhoneConnected(String str) {
        return false;
    }

    public void registerPsnNFBluetoothCallback() {
        Logs.d("Psn-NFBluetooth xpbluetooth psn nf NFBluetoothCallback re nfcallback registerpsnNFBluetoothCallback ");
        if (this.mIsRegisterNF) {
            return;
        }
        this.mBtBoxesUtil.registerCallback(this.mUsbBluetoothControl);
        this.mIsRegisterNF = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.External.ACTION_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(Config.External.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(Config.External.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_DEVICE_FOUND);
        intentFilter.addAction(Config.External.ACTION_A2DP_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_BOND_LIST_CHANGED);
        intentFilter.addAction(Config.External.ACTION_AVRCP_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_HFP_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_CONNECTION_STATE_CHANGED);
        CarSettingsApp.getContext().registerReceiver(this.mReceiver, intentFilter);
        Logs.d("Psn-NFBluetooth xpbluetooth nf nfcallback registerpsnNFBluetoothCallback complete!");
    }

    public void unregisterPsnNFBluetoothCallback() {
        Logs.d("Psn-NFBluetooth xpbluetooth nf NFBluetoothCallback un nfcallback unregisterpsnNFBluetoothCallback " + this.mIsRegisterNF);
        if (this.mIsRegisterNF) {
            this.mBtBoxesUtil.unRegisterCallback(this.mUsbBluetoothControl);
            this.mIsRegisterNF = false;
            Logs.d("Psn-NFBluetooth xpbluetooth nf nfcallback unregisterpsnNFBluetoothCallback complete!");
            CarSettingsApp.getContext().unregisterReceiver(this.mReceiver);
        }
    }

    public void registerServiceConnectCallback(PsnBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            if (!this.mServiceConnectedCallback.contains(nFServiceConnectedCallback)) {
                this.mServiceConnectedCallback.add(nFServiceConnectedCallback);
            }
        }
        this.mUsbBluetoothControl.mConnection.registerConnecteCompletedCallback(this);
    }

    public void unregisterServiceConnectCallback(PsnBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            this.mServiceConnectedCallback.remove(nFServiceConnectedCallback);
        }
        this.mUsbBluetoothControl.mConnection.unregisterConnecteCompletedCallback(this);
    }

    private void notifyCompleted() {
        synchronized (this.mServiceConnectedCallback) {
            for (PsnBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback : this.mServiceConnectedCallback) {
                nFServiceConnectedCallback.connectCompleted();
            }
        }
    }

    public boolean usbSearch() {
        return this.mUsbBluetoothControl.usbSearch();
    }

    public boolean isUsbEnable() {
        boolean isUsbEnabled = this.mUsbBluetoothControl.isUsbEnabled();
        Logs.d("Psn-NFBluetooth xpbluetooth nf usb enable:" + isUsbEnabled);
        return isUsbEnabled;
    }

    public int getProfileConnectionState(int i) {
        return this.mUsbBluetoothControl.getProfileConnectionState(i);
    }

    public int getLocalUsbConnectionState() {
        return this.mUsbBluetoothControl.getLocalUsbConnectionState();
    }

    public boolean setUsbBluetoothEnabled(final boolean z) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager.1
            @Override // java.lang.Runnable
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                PsnNFBluetoothManager.this.mUsbBluetoothControl.setUsbEnabled(z);
                Logs.d("settings nf setUsbBluetoothEnabled " + (System.currentTimeMillis() - currentTimeMillis));
            }
        });
        Logs.d("settings nf setUsbBluetoothEnabled end");
        return true;
    }

    public boolean isUsbCurrentScanning() {
        Logs.d("Psn-NFBluetooth xpbluetooth nf isUsbCurrentScanning");
        return this.mUsbBluetoothControl.isUsbDiscovering();
    }

    public boolean setUsbName(String str) {
        return this.mUsbBluetoothControl.setUsbBtLocalName(str);
    }

    public void registerPsnStateCallback(IXpPsnBluetoothCallback iXpPsnBluetoothCallback) {
        if (iXpPsnBluetoothCallback == null || this.mPsnBluetoothCallbacks.contains(iXpPsnBluetoothCallback)) {
            return;
        }
        this.mPsnBluetoothCallbacks.add(iXpPsnBluetoothCallback);
    }

    public void unregisterPsnStateCallback(IXpPsnBluetoothCallback iXpPsnBluetoothCallback) {
        if (iXpPsnBluetoothCallback != null) {
            this.mPsnBluetoothCallbacks.remove(iXpPsnBluetoothCallback);
        }
    }

    public boolean usbUnpair(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        boolean usbReqUnpair = this.mUsbBluetoothControl.usbReqUnpair(psnBluetoothDeviceInfo.getDeviceAddr());
        Logs.d("Psn-NFBluetooth xpbluetooth nf usbUnpair " + usbReqUnpair + " " + psnBluetoothDeviceInfo.getDeviceAddr());
        return usbReqUnpair;
    }

    public boolean disUsbConnect(String str) {
        if (this.mUsbBluetoothControl.isUsbDiscovering()) {
            this.mUsbBluetoothControl.usbStopSearch();
        }
        boolean usbDisConnectDevice = this.mUsbBluetoothControl.usbDisConnectDevice(str);
        Logs.d("xpbluetooth nf usb disUsbConnect:" + usbDisConnectDevice);
        return usbDisConnectDevice;
    }

    public String usbName() {
        return this.mUsbBluetoothControl.getUsbBtLocalName();
    }

    public boolean usbConnect(String str) {
        if (this.mUsbBluetoothControl.isUsbDiscovering()) {
            this.mUsbBluetoothControl.usbStopSearch();
        }
        boolean usbConnect = this.mUsbBluetoothControl.usbConnect(str);
        Logs.d("Psn-NFBluetooth xpbluetooth nf usb connectUsb:" + usbConnect);
        return usbConnect;
    }

    public ArrayList<PsnBluetoothDeviceInfo> getAvailableUsbDevicesSorted() {
        ArrayList<PsnBluetoothDeviceInfo> arrayList = new ArrayList<>();
        arrayList.addAll(this.mXpPsnNewBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        Logs.d("Psn-NFBluetooth xpbluetooth nf getAvailableUsbDevicesSorted " + arrayList.size());
        return arrayList;
    }

    public ArrayList<PsnBluetoothDeviceInfo> getBondedDevicesSorted() {
        ArrayList<PsnBluetoothDeviceInfo> arrayList = new ArrayList<>();
        List<UsbBluetoothDevice> reqUsbBtPairedDevices = this.mUsbBluetoothControl.reqUsbBtPairedDevices();
        if (reqUsbBtPairedDevices != null && reqUsbBtPairedDevices.size() > 0) {
            for (UsbBluetoothDevice usbBluetoothDevice : reqUsbBtPairedDevices) {
                Logs.d("Psn-NFBluetooth psn bluetooth getBondedDevicesSorted:" + usbBluetoothDevice.getAddress() + " " + usbBluetoothDevice.getName());
                PsnBluetoothDeviceInfo xpPsnBluetoothDevice = getXpPsnBluetoothDevice(usbBluetoothDevice.getAddress());
                if (xpPsnBluetoothDevice == null) {
                    xpPsnBluetoothDevice = new PsnBluetoothDeviceInfo(usbBluetoothDevice.getName(), usbBluetoothDevice.getAddress());
                }
                xpPsnBluetoothDevice.setConnectStatus(getUsbConnectionState(usbBluetoothDevice.getAddress()));
                this.mXpPsnBondedBluetoothDeviceCacheInfos.put(usbBluetoothDevice.getAddress(), xpPsnBluetoothDevice);
                this.mXpPsnNewBluetoothDeviceCacheInfos.remove(usbBluetoothDevice.getAddress());
            }
            arrayList.addAll(this.mXpPsnBondedBluetoothDeviceCacheInfos.values());
            Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        } else {
            Logs.d("Psn-NFBluetooth psn bluetooth getBondedDevicesSorted empty");
            this.mXpPsnBondedBluetoothDeviceCacheInfos.clear();
        }
        return arrayList;
    }

    private int getUsbConnectionState(String str) {
        int usbConnectionState = this.mUsbBluetoothControl.getUsbConnectionState(str);
        Logs.d("Psn-NFBluetooth psn nf state:" + usbConnectionState + " address:" + str);
        return usbConnectionState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRefreshListCallback() {
        synchronized (this.mPsnBluetoothCallbacks) {
            Iterator<IXpPsnBluetoothCallback> it = this.mPsnBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onRefreshData();
            }
        }
    }

    @Override // com.xiaopeng.btservice.base.AbstractConnection.ConnecteCompletedCallback
    public void connectServiceCompleted() {
        Logs.d("Psn-NFBluetooth xpbluetooth nf connect service completed!");
        notifyCompleted();
    }

    public String getUsbAddr() {
        return this.mUsbBluetoothControl.getUsbAddress();
    }

    public String getUsbConnectedDevice() {
        return this.mUsbBluetoothControl.getUsbConnectedDevice();
    }

    public boolean usbSearchStop() {
        return this.mUsbBluetoothControl.usbStopSearch();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class BluetoothReceiver extends BroadcastReceiver {
        BluetoothReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            Logs.d("Psn-NFBluetooth xpbluetooth onReceive action:" + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1837432277:
                    if (action.equals(Config.External.ACTION_DEVICE_FOUND)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1096566248:
                    if (action.equals(Config.External.ACTION_DISCOVERY_FINISHED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -684268904:
                    if (action.equals(Config.External.ACTION_AVRCP_CONNECTION_STATE_CHANGED)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -611508076:
                    if (action.equals(Config.External.ACTION_STATE_CHANGED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -517387976:
                    if (action.equals(Config.External.ACTION_BOND_LIST_CHANGED)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -158400479:
                    if (action.equals(Config.External.ACTION_BOND_STATE_CHANGED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 279629229:
                    if (action.equals(Config.External.ACTION_A2DP_CONNECTION_STATE_CHANGED)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 772517248:
                    if (action.equals(Config.External.ACTION_HFP_CONNECTION_STATE_CHANGED)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1817178044:
                    if (action.equals(Config.External.ACTION_CONNECTION_STATE_CHANGED)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 2107045371:
                    if (action.equals(Config.External.ACTION_DISCOVERY_STARTED)) {
                        c = 1;
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
                    PsnNFBluetoothManager.this.onUsbAdapterStateChanged(intent.getIntExtra("state", -1));
                    return;
                case 1:
                    PsnNFBluetoothManager.this.onUsbAdapterDiscoveryStarted();
                    return;
                case 2:
                    PsnNFBluetoothManager.this.onUsbAdapterDiscoveryFinished();
                    return;
                case 3:
                    PsnNFBluetoothManager.this.onUsbDeviceFound(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getStringExtra("name"), intent.getIntExtra(Config.External.ACTION_DEVICE_FOUND_CATEGORY, -1));
                    return;
                case 4:
                    PsnNFBluetoothManager.this.onUsbDeviceBondStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getStringExtra("name"), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case 5:
                    PsnNFBluetoothManager.this.onUsbA2dpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case 6:
                    PsnNFBluetoothManager.this.notifyRefreshListCallback();
                    return;
                case 7:
                    PsnNFBluetoothManager.this.onUsbAvrcpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case '\b':
                    PsnNFBluetoothManager.this.onUsbHfpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case '\t':
                    PsnNFBluetoothManager.this.onUsbConnectionStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                default:
                    return;
            }
        }
    }

    public void onUsbAdapterStateChanged(int i) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf onUsbAdapterStateChanged newState:" + i);
        if (i == 10) {
            this.mXpTmpPsnFoundBluetoothDeviceInfos.clear();
            this.mXpPsnBondedBluetoothDeviceCacheInfos.clear();
            this.mXpPsnNewBluetoothDeviceCacheInfos.clear();
        }
        Iterator<IXpPsnBluetoothCallback> it = this.mPsnBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUsbBluetoothStateChanged(i);
        }
    }

    public void onUsbAdapterDiscoveryStarted() {
        Logs.d("Psn-NFBluetooth xpbluetooth nf onUsbAdapterDiscoveryStarted");
        this.mXpTmpPsnFoundBluetoothDeviceInfos.clear();
        Iterator<IXpPsnBluetoothCallback> it = this.mPsnBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUsbScanningStateChanged(true);
        }
    }

    public void onUsbAdapterDiscoveryFinished() {
        Logs.d("Psn-NFBluetooth xpbluetooth nf onUsbAdapterDiscoveryFinished ");
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager.3
            @Override // java.lang.Runnable
            public void run() {
                Iterator<IXpPsnBluetoothCallback> it = PsnNFBluetoothManager.this.mPsnBluetoothCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().onUsbScanningStateChanged(false);
                }
                PsnNFBluetoothManager.this.checkRemovedDevices();
                PsnNFBluetoothManager.this.notifyRefreshListCallback();
            }
        });
    }

    private boolean isHeadsetDevice(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        String hexString = Integer.toHexString(psnBluetoothDeviceInfo.getCategory());
        if (TextUtils.isEmpty(hexString) || hexString.length() < 4) {
            return false;
        }
        String substring = hexString.substring(hexString.length() - 4);
        return HEADSET_COD.equals(substring) || HEADSET_COD_2.equals(substring);
    }

    public void onUsbDeviceFound(String str, String str2, int i) {
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo;
        Logs.d("Psn-NFBluetooth xpbluetooth nf onUsbDeviceFound xpBluetoothDeviceInfo:" + str + " name:" + str2 + " category:" + i);
        Iterator<PsnBluetoothDeviceInfo> it = this.mXpPsnBondedBluetoothDeviceCacheInfos.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                psnBluetoothDeviceInfo = null;
                break;
            }
            psnBluetoothDeviceInfo = it.next();
            if (str.equals(psnBluetoothDeviceInfo.getDeviceAddr())) {
                this.mXpTmpPsnFoundBluetoothDeviceInfos.add(psnBluetoothDeviceInfo);
                break;
            }
        }
        if (psnBluetoothDeviceInfo == null) {
            PsnBluetoothDeviceInfo xpPsnBluetoothDevice = getXpPsnBluetoothDevice(str);
            if (xpPsnBluetoothDevice == null) {
                xpPsnBluetoothDevice = new PsnBluetoothDeviceInfo(str2, str);
                if (i != 0 && i != -1) {
                    xpPsnBluetoothDevice.setCategory(i);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = str;
            }
            xpPsnBluetoothDevice.setDeviceName(str2);
            xpPsnBluetoothDevice.setDeviceAddr(str);
            if (i != 0 && i != -1) {
                xpPsnBluetoothDevice.setCategory(i);
            }
            if (!this.mXpPsnBondedBluetoothDeviceCacheInfos.containsKey(str) && isHeadsetDevice(xpPsnBluetoothDevice)) {
                this.mXpPsnNewBluetoothDeviceCacheInfos.put(str, xpPsnBluetoothDevice);
                this.mXpTmpPsnFoundBluetoothDeviceInfos.add(xpPsnBluetoothDevice);
            }
            notifyRefreshListCallback();
        }
    }

    public void onUsbDeviceBondStateChanged(String str, String str2, int i, int i2) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf onUsbDeviceBondStateChanged address:" + str + " name:" + str2 + " prevState:" + i + " newState:" + i2);
        Iterator<IXpPsnBluetoothCallback> it = this.mPsnBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUsbDeviceBondStateChanged(str, str2, i, i2);
        }
        PsnBluetoothDeviceInfo xpPsnBluetoothDevice = getXpPsnBluetoothDevice(str);
        if (i2 == 12) {
            if (xpPsnBluetoothDevice != null) {
                this.mXpPsnBondedBluetoothDeviceCacheInfos.put(xpPsnBluetoothDevice.getDeviceAddr(), xpPsnBluetoothDevice);
                this.mXpPsnNewBluetoothDeviceCacheInfos.remove(xpPsnBluetoothDevice.getDeviceAddr());
            }
        } else if (xpPsnBluetoothDevice != null) {
            this.mXpPsnNewBluetoothDeviceCacheInfos.put(str, xpPsnBluetoothDevice);
            this.mXpTmpPsnFoundBluetoothDeviceInfos.add(xpPsnBluetoothDevice);
            this.mXpPsnBondedBluetoothDeviceCacheInfos.remove(xpPsnBluetoothDevice.getDeviceAddr());
        }
    }

    public void onUsbA2dpStateChanged(String str, int i, int i2) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf profile onUsbA2dpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onUsbAvrcpStateChanged(String str, int i, int i2) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf profile onUsbAvrcpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onUsbHfpStateChanged(String str, int i, int i2) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf profile onUsbHfpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onUsbConnectionStateChanged(String str, int i, int i2) {
        Logs.d("Psn-NFBluetooth xpbluetooth nf profile onUsbConnectionStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
        PsnBluetoothDeviceInfo xpPsnBluetoothDevice = getXpPsnBluetoothDevice(str);
        if (xpPsnBluetoothDevice != null) {
            xpPsnBluetoothDevice.setConnectStatus(i2);
            Iterator<IXpPsnBluetoothCallback> it = this.mPsnBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onUsbConnectionStateChanged(str, xpPsnBluetoothDevice.getDeviceName(), i, i2);
            }
        }
        notifyRefreshListCallback();
        notifyItemChange(getXpPsnBluetoothDevice(str));
    }

    public void addItemChangeListener(ItemChangeListener itemChangeListener) {
        if (this.mItemChangeListenerList.contains(itemChangeListener)) {
            return;
        }
        this.mItemChangeListenerList.add(itemChangeListener);
    }

    public void removeItemChangeListener(ItemChangeListener itemChangeListener) {
        this.mItemChangeListenerList.remove(itemChangeListener);
    }

    public void notifyItemChange(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        if (psnBluetoothDeviceInfo == null) {
            return;
        }
        for (ItemChangeListener itemChangeListener : this.mItemChangeListenerList) {
            itemChangeListener.notifyItemChanged(psnBluetoothDeviceInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRemovedDevices() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mXpPsnNewBluetoothDeviceCacheInfos.values());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            PsnBluetoothDeviceInfo psnBluetoothDeviceInfo = (PsnBluetoothDeviceInfo) it.next();
            if (!this.mXpTmpPsnFoundBluetoothDeviceInfos.contains(psnBluetoothDeviceInfo)) {
                if (psnBluetoothDeviceInfo.isConnectingBusy() || psnBluetoothDeviceInfo.isDisconnectingBusy()) {
                    Logs.d("Psn-NFBluetooth xpbluetooth nf can not remove device:" + psnBluetoothDeviceInfo);
                } else {
                    this.mXpPsnNewBluetoothDeviceCacheInfos.remove(psnBluetoothDeviceInfo.getDeviceAddr());
                }
            }
        }
    }

    public PsnBluetoothDeviceInfo getXpPsnBluetoothDevice(String str) {
        for (PsnBluetoothDeviceInfo psnBluetoothDeviceInfo : this.mXpPsnBondedBluetoothDeviceCacheInfos.values()) {
            if (psnBluetoothDeviceInfo.getDeviceAddr().equals(str)) {
                return psnBluetoothDeviceInfo;
            }
        }
        for (PsnBluetoothDeviceInfo psnBluetoothDeviceInfo2 : this.mXpPsnNewBluetoothDeviceCacheInfos.values()) {
            if (psnBluetoothDeviceInfo2.getDeviceAddr().equals(str)) {
                return psnBluetoothDeviceInfo2;
            }
        }
        return null;
    }
}
