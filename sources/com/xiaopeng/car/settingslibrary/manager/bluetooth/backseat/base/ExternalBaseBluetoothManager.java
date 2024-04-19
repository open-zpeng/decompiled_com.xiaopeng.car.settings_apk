package com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.nforetek.bt.aidl.XpBluetoothDevice;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.btservice.base.AbstractConnection;
import com.xiaopeng.btservice.external.XPBluetoothControl;
import com.xiaopeng.btservice.util.BtBoxesUtil;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.ExternalBluetoothManger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class ExternalBaseBluetoothManager implements AbstractConnection.ConnecteCompletedCallback {
    private static final String HEADSET_COD = "0404";
    private static final String HEADSET_COD_2 = "0418";
    private static final String TAG = "ExternalBaseBluetoothManager";
    private boolean mIsRegisterNF;
    ArrayList<IXpExternalBluetoothCallback> mExternalBluetoothCallbacks = new ArrayList<>();
    Map<String, ExternalBluetoothDeviceInfo> mXpExternalNewBluetoothDeviceCacheInfos = new ConcurrentHashMap();
    ArrayList<ExternalBluetoothDeviceInfo> mXpTmpExternalFoundBluetoothDeviceInfos = new ArrayList<>();
    Map<String, ExternalBluetoothDeviceInfo> mXpExternalBondedBluetoothDeviceCacheInfos = new ConcurrentHashMap();
    private final List<ExternalBluetoothManger.NFServiceConnectedCallback> mServiceConnectedCallback = new ArrayList();
    public final Comparator<ExternalBluetoothDeviceInfo> ALPHA_COMPARATOR = new Comparator() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.-$$Lambda$ExternalBaseBluetoothManager$LWyRO2aaR4lxvcGxSI2A1CtEbA4
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ExternalBaseBluetoothManager.lambda$new$1((ExternalBluetoothDeviceInfo) obj, (ExternalBluetoothDeviceInfo) obj2);
        }
    };
    BluetoothReceiver mReceiver = new BluetoothReceiver();
    List<ItemChangeListener> mItemChangeListenerList = new CopyOnWriteArrayList();
    BtBoxesUtil mBtBoxesUtil = CarSettingsApp.getBtBoxesUtil();
    XPBluetoothControl mExternalBluetoothControl = this.mBtBoxesUtil.getXPBluetoothControl(2);

    /* loaded from: classes.dex */
    public interface ItemChangeListener {
        void notifyItemChanged(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo);
    }

    public boolean isBtPhoneConnected(String str) {
        return false;
    }

    public void registerExternalNFBluetoothCallback() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth external nf NFBluetoothCallback re nfcallback registerexternalNFBluetoothCallback ");
        if (this.mIsRegisterNF) {
            return;
        }
        this.mBtBoxesUtil.registerCallback((AbsControl) this.mExternalBluetoothControl);
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
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf nfcallback registerExternalNFBluetoothCallback complete!");
    }

    public void unregisterExternalNFBluetoothCallback() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf NFBluetoothCallback un nfcallback unregisterExternalNFBluetoothCallback " + this.mIsRegisterNF);
        if (this.mIsRegisterNF) {
            this.mBtBoxesUtil.unRegisterCallback((AbsControl) this.mExternalBluetoothControl);
            this.mIsRegisterNF = false;
            Logs.d("ExternalBaseBluetoothManager xpbluetooth nf nfcallback unregisterExternalNFBluetoothCallback complete!");
            CarSettingsApp.getContext().unregisterReceiver(this.mReceiver);
        }
    }

    public void registerServiceConnectCallback(ExternalBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            if (!this.mServiceConnectedCallback.contains(nFServiceConnectedCallback)) {
                this.mServiceConnectedCallback.add(nFServiceConnectedCallback);
            }
        }
        this.mExternalBluetoothControl.mConnection.registerConnecteCompletedCallback(this);
    }

    public void unregisterServiceConnectCallback(ExternalBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            this.mServiceConnectedCallback.remove(nFServiceConnectedCallback);
        }
        this.mExternalBluetoothControl.mConnection.unregisterConnecteCompletedCallback(this);
    }

    private void notifyCompleted() {
        synchronized (this.mServiceConnectedCallback) {
            for (ExternalBluetoothManger.NFServiceConnectedCallback nFServiceConnectedCallback : this.mServiceConnectedCallback) {
                nFServiceConnectedCallback.connectCompleted();
            }
        }
    }

    public boolean startSearch() {
        return this.mExternalBluetoothControl.startSearch();
    }

    public boolean isDeviceEnable() {
        boolean isDeviceEnabled = this.mExternalBluetoothControl.isDeviceEnabled();
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf device enable:" + isDeviceEnabled);
        return isDeviceEnabled;
    }

    public boolean setDeviceEnabled(final boolean z) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.-$$Lambda$ExternalBaseBluetoothManager$4o9EmNo9BGneRtQ-MUHm5L-GCMc
            @Override // java.lang.Runnable
            public final void run() {
                ExternalBaseBluetoothManager.this.lambda$setDeviceEnabled$0$ExternalBaseBluetoothManager(z);
            }
        });
        Logs.d("settings nf setDeviceEnabled end");
        return true;
    }

    public /* synthetic */ void lambda$setDeviceEnabled$0$ExternalBaseBluetoothManager(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        this.mExternalBluetoothControl.enableDevice(z);
        Logs.d("settings nf setDeviceEnabled " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public boolean isDiscovering() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf isDiscovering");
        return this.mExternalBluetoothControl.isDiscovering();
    }

    public void registerExternalStateCallback(IXpExternalBluetoothCallback iXpExternalBluetoothCallback) {
        if (iXpExternalBluetoothCallback == null || this.mExternalBluetoothCallbacks.contains(iXpExternalBluetoothCallback)) {
            return;
        }
        this.mExternalBluetoothCallbacks.add(iXpExternalBluetoothCallback);
    }

    public void unregisterExternalStateCallback(IXpExternalBluetoothCallback iXpExternalBluetoothCallback) {
        if (iXpExternalBluetoothCallback != null) {
            this.mExternalBluetoothCallbacks.remove(iXpExternalBluetoothCallback);
        }
    }

    public boolean unpair(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        boolean unpair = this.mExternalBluetoothControl.unpair(externalBluetoothDeviceInfo.getDeviceAddr());
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf unpair " + unpair + " " + externalBluetoothDeviceInfo.getDeviceAddr());
        return unpair;
    }

    public boolean disConnectDevice(String str) {
        if (this.mExternalBluetoothControl.isDiscovering()) {
            this.mExternalBluetoothControl.stopSearch();
        }
        boolean disConnectDevice = this.mExternalBluetoothControl.disConnectDevice(str);
        Logs.d("xpbluetooth nf disConnectDevice:" + disConnectDevice);
        return disConnectDevice;
    }

    public boolean connectDevice(String str) {
        if (this.mExternalBluetoothControl.isDiscovering()) {
            this.mExternalBluetoothControl.stopSearch();
        }
        boolean connectDevice = this.mExternalBluetoothControl.connectDevice(str);
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf connectDevice:" + connectDevice);
        return connectDevice;
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getAvailableDevicesSorted() {
        ArrayList<ExternalBluetoothDeviceInfo> arrayList = new ArrayList<>();
        arrayList.addAll(this.mXpExternalNewBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf getAvailableDevicesSorted " + arrayList.size());
        return arrayList;
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getBondedDevicesSorted() {
        ArrayList<ExternalBluetoothDeviceInfo> arrayList = new ArrayList<>();
        List<XpBluetoothDevice> reqXpBtPairedDevices = this.mExternalBluetoothControl.reqXpBtPairedDevices();
        if (reqXpBtPairedDevices != null && reqXpBtPairedDevices.size() > 0) {
            for (XpBluetoothDevice xpBluetoothDevice : reqXpBtPairedDevices) {
                Logs.d("ExternalBaseBluetoothManager external bluetooth getBondedDevicesSorted:" + xpBluetoothDevice.getAddress() + " " + xpBluetoothDevice.getName());
                ExternalBluetoothDeviceInfo xpExternalBluetoothDevice = getXpExternalBluetoothDevice(xpBluetoothDevice.getAddress());
                if (xpExternalBluetoothDevice == null) {
                    xpExternalBluetoothDevice = new ExternalBluetoothDeviceInfo(xpBluetoothDevice.getName(), xpBluetoothDevice.getAddress());
                }
                xpExternalBluetoothDevice.setConnectStatus(getConnectionState(xpBluetoothDevice.getAddress()));
                this.mXpExternalBondedBluetoothDeviceCacheInfos.put(xpBluetoothDevice.getAddress(), xpExternalBluetoothDevice);
                this.mXpExternalNewBluetoothDeviceCacheInfos.remove(xpBluetoothDevice.getAddress());
            }
            arrayList.addAll(this.mXpExternalBondedBluetoothDeviceCacheInfos.values());
            Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        } else {
            Logs.d("ExternalBaseBluetoothManager external bluetooth getBondedDevicesSorted empty");
            this.mXpExternalBondedBluetoothDeviceCacheInfos.clear();
        }
        return arrayList;
    }

    private int getConnectionState(String str) {
        int connectionState = this.mExternalBluetoothControl.getConnectionState(str);
        Logs.d("ExternalBaseBluetoothManager external nf state:" + connectionState + " address:" + str);
        return connectionState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ int lambda$new$1(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo, ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo2) {
        int compareTo;
        int compareTo2;
        String deviceAddr = externalBluetoothDeviceInfo.getDeviceAddr();
        String deviceAddr2 = externalBluetoothDeviceInfo2.getDeviceAddr();
        String deviceName = externalBluetoothDeviceInfo.getDeviceName();
        String deviceName2 = externalBluetoothDeviceInfo2.getDeviceName();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRefreshListCallback() {
        synchronized (this.mExternalBluetoothCallbacks) {
            Iterator<IXpExternalBluetoothCallback> it = this.mExternalBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onRefreshData();
            }
        }
    }

    @Override // com.xiaopeng.btservice.base.AbstractConnection.ConnecteCompletedCallback
    public void connectServiceCompleted() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf connect service completed!");
        notifyCompleted();
    }

    public String getConnectedDevice() {
        return this.mExternalBluetoothControl.getConnectedDevice();
    }

    public boolean stopSearch() {
        return this.mExternalBluetoothControl.stopSearch();
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
            Logs.d("ExternalBaseBluetoothManager xpbluetooth onReceive action:" + intent.getAction());
            int intExtra = intent.getIntExtra(Config.External.ACTION_EXTRA_DEVICE_ID, -1);
            if (intExtra != 2) {
                Logs.d("ExternalBaseBluetoothManagerxpbluetooth deviceId is " + intExtra + ", pass");
                return;
            }
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
                    ExternalBaseBluetoothManager.this.onAdapterStateChanged(intent.getIntExtra("state", -1));
                    return;
                case 1:
                    ExternalBaseBluetoothManager.this.onAdapterDiscoveryStarted();
                    return;
                case 2:
                    ExternalBaseBluetoothManager.this.onAdapterDiscoveryFinished();
                    return;
                case 3:
                    ExternalBaseBluetoothManager.this.onDeviceFound(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getStringExtra("name"), intent.getIntExtra(Config.External.ACTION_DEVICE_FOUND_CATEGORY, -1));
                    return;
                case 4:
                    ExternalBaseBluetoothManager.this.onDeviceBondStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getStringExtra("name"), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case 5:
                    ExternalBaseBluetoothManager.this.onA2dpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case 6:
                    ExternalBaseBluetoothManager.this.notifyRefreshListCallback();
                    return;
                case 7:
                    ExternalBaseBluetoothManager.this.onAvrcpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case '\b':
                    ExternalBaseBluetoothManager.this.onHfpStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                case '\t':
                    ExternalBaseBluetoothManager.this.onConnectionStateChanged(intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS), intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1), intent.getIntExtra("state", -1));
                    return;
                default:
                    return;
            }
        }
    }

    public void onAdapterStateChanged(int i) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf onAdapterStateChanged newState:" + i);
        if (i == 10) {
            this.mXpTmpExternalFoundBluetoothDeviceInfos.clear();
            this.mXpExternalBondedBluetoothDeviceCacheInfos.clear();
            this.mXpExternalNewBluetoothDeviceCacheInfos.clear();
        }
        Iterator<IXpExternalBluetoothCallback> it = this.mExternalBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onBluetoothStateChanged(i);
        }
    }

    public void onAdapterDiscoveryStarted() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf onAdapterDiscoveryStarted");
        this.mXpTmpExternalFoundBluetoothDeviceInfos.clear();
        Iterator<IXpExternalBluetoothCallback> it = this.mExternalBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onScanningStateChanged(true);
        }
    }

    public void onAdapterDiscoveryFinished() {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf onAdapterDiscoveryFinished ");
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBaseBluetoothManager.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator<IXpExternalBluetoothCallback> it = ExternalBaseBluetoothManager.this.mExternalBluetoothCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().onScanningStateChanged(false);
                }
                ExternalBaseBluetoothManager.this.checkRemovedDevices();
                ExternalBaseBluetoothManager.this.notifyRefreshListCallback();
            }
        });
    }

    private boolean isHeadsetDevice(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        String hexString = Integer.toHexString(externalBluetoothDeviceInfo.getCategory());
        if (TextUtils.isEmpty(hexString) || hexString.length() < 4) {
            return false;
        }
        String substring = hexString.substring(hexString.length() - 4);
        return HEADSET_COD.equals(substring) || HEADSET_COD_2.equals(substring);
    }

    public void onDeviceFound(String str, String str2, int i) {
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo;
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf onDeviceFound xpBluetoothDeviceInfo:" + str + " name:" + str2 + " category:" + i);
        Iterator<ExternalBluetoothDeviceInfo> it = this.mXpExternalBondedBluetoothDeviceCacheInfos.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                externalBluetoothDeviceInfo = null;
                break;
            }
            externalBluetoothDeviceInfo = it.next();
            if (str.equals(externalBluetoothDeviceInfo.getDeviceAddr())) {
                this.mXpTmpExternalFoundBluetoothDeviceInfos.add(externalBluetoothDeviceInfo);
                break;
            }
        }
        if (externalBluetoothDeviceInfo == null) {
            ExternalBluetoothDeviceInfo xpExternalBluetoothDevice = getXpExternalBluetoothDevice(str);
            if (xpExternalBluetoothDevice == null) {
                xpExternalBluetoothDevice = new ExternalBluetoothDeviceInfo(str2, str);
                if (i != 0 && i != -1) {
                    xpExternalBluetoothDevice.setCategory(i);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = str;
            }
            xpExternalBluetoothDevice.setDeviceName(str2);
            xpExternalBluetoothDevice.setDeviceAddr(str);
            if (i != 0 && i != -1) {
                xpExternalBluetoothDevice.setCategory(i);
            }
            if (!this.mXpExternalBondedBluetoothDeviceCacheInfos.containsKey(str) && isHeadsetDevice(xpExternalBluetoothDevice)) {
                this.mXpExternalNewBluetoothDeviceCacheInfos.put(str, xpExternalBluetoothDevice);
                this.mXpTmpExternalFoundBluetoothDeviceInfos.add(xpExternalBluetoothDevice);
            }
            notifyRefreshListCallback();
        }
    }

    public void onDeviceBondStateChanged(String str, String str2, int i, int i2) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf onDeviceBondStateChanged address:" + str + " name:" + str2 + " prevState:" + i + " newState:" + i2);
        Iterator<IXpExternalBluetoothCallback> it = this.mExternalBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceBondStateChanged(str, str2, i, i2);
        }
        ExternalBluetoothDeviceInfo xpExternalBluetoothDevice = getXpExternalBluetoothDevice(str);
        if (i2 == 12) {
            if (xpExternalBluetoothDevice != null) {
                this.mXpExternalBondedBluetoothDeviceCacheInfos.put(xpExternalBluetoothDevice.getDeviceAddr(), xpExternalBluetoothDevice);
                this.mXpExternalNewBluetoothDeviceCacheInfos.remove(xpExternalBluetoothDevice.getDeviceAddr());
            }
        } else if (xpExternalBluetoothDevice != null) {
            this.mXpExternalNewBluetoothDeviceCacheInfos.put(str, xpExternalBluetoothDevice);
            this.mXpTmpExternalFoundBluetoothDeviceInfos.add(xpExternalBluetoothDevice);
            this.mXpExternalBondedBluetoothDeviceCacheInfos.remove(xpExternalBluetoothDevice.getDeviceAddr());
        }
    }

    public void onA2dpStateChanged(String str, int i, int i2) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf profile onA2dpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onAvrcpStateChanged(String str, int i, int i2) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf profile onAvrcpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onHfpStateChanged(String str, int i, int i2) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf profile onHfpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
    }

    public void onConnectionStateChanged(String str, int i, int i2) {
        Logs.d("ExternalBaseBluetoothManager xpbluetooth nf profile onConnectionStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
        ExternalBluetoothDeviceInfo xpExternalBluetoothDevice = getXpExternalBluetoothDevice(str);
        if (xpExternalBluetoothDevice != null) {
            xpExternalBluetoothDevice.setConnectStatus(i2);
            Iterator<IXpExternalBluetoothCallback> it = this.mExternalBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onConnectionStateChanged(str, xpExternalBluetoothDevice.getDeviceName(), i, i2);
            }
        }
        notifyRefreshListCallback();
        notifyItemChange(getXpExternalBluetoothDevice(str));
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

    public void notifyItemChange(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        if (externalBluetoothDeviceInfo == null) {
            return;
        }
        for (ItemChangeListener itemChangeListener : this.mItemChangeListenerList) {
            itemChangeListener.notifyItemChanged(externalBluetoothDeviceInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRemovedDevices() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mXpExternalNewBluetoothDeviceCacheInfos.values());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo = (ExternalBluetoothDeviceInfo) it.next();
            if (!this.mXpTmpExternalFoundBluetoothDeviceInfos.contains(externalBluetoothDeviceInfo)) {
                if (externalBluetoothDeviceInfo.isConnectingBusy() || externalBluetoothDeviceInfo.isDisconnectingBusy()) {
                    Logs.d("ExternalBaseBluetoothManager xpbluetooth nf can not remove device:" + externalBluetoothDeviceInfo);
                } else {
                    this.mXpExternalNewBluetoothDeviceCacheInfos.remove(externalBluetoothDeviceInfo.getDeviceAddr());
                }
            }
        }
    }

    public ExternalBluetoothDeviceInfo getXpExternalBluetoothDevice(String str) {
        for (ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo : this.mXpExternalBondedBluetoothDeviceCacheInfos.values()) {
            if (externalBluetoothDeviceInfo.getDeviceAddr().equals(str)) {
                return externalBluetoothDeviceInfo;
            }
        }
        for (ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo2 : this.mXpExternalNewBluetoothDeviceCacheInfos.values()) {
            if (externalBluetoothDeviceInfo2.getDeviceAddr().equals(str)) {
                return externalBluetoothDeviceInfo2;
            }
        }
        return null;
    }
}
