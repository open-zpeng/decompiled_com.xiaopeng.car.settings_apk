package com.xiaopeng.car.settingslibrary.manager.bluetooth.nf;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.btservice.base.AbsBluetoothControlCallback;
import com.xiaopeng.btservice.base.AbstractConnection;
import com.xiaopeng.btservice.bluetooth.BluetoothControl;
import com.xiaopeng.btservice.util.BtBoxesUtil;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.airouter.SpeechSettingsOverAllObserver;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HidProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.ProjectorAutoConnectManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller;
import com.xiaopeng.car.settingslibrary.manager.speech.bean.BluetoothDevicesBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class NFBluetoothManager extends AbsBluetoothManager implements AbstractConnection.ConnecteCompletedCallback {
    private static final int BLUETOOTH_CONNECT_MSG = 1001;
    private static final int CONNECT_TIMEOUT = 2000;
    private static final String TAG = "NFBluetooth";
    public static final boolean sIsSupportedHID = true;
    public final Comparator<XpBluetoothDeviceInfo> ALPHA_COMPARATOR;
    private IntentFilter mAdapterIntentFilter;
    CopyOnWriteArrayList<IXpBluetoothCallback> mBluetoothCallbacks;
    BluetoothControl mBluetoothControl;
    AbsBluetoothControlCallback mBluetoothControlCallback;
    BtBoxesUtil mBtBoxesUtil;
    private XpBluetoothManger.ConnectErrorListener mConnectErrorListener;
    private ConnectHandler mHandler;
    HidProfile mHidProfile;
    boolean mIsNeedUpdateConnectState;
    boolean mIsRegisterNF;
    boolean mIsRegisteredReceiver;
    private Boolean mLastBTVisibilityOperate;
    private final BroadcastReceiver mNameBroadcastReceiver;
    private final BroadcastReceiver mProfileBroadcastReceiver;
    private IntentFilter mProfileIntentFilter;
    private ProjectorAutoConnectManager mProjectorAutoConnectManager;
    List<XpBluetoothManger.ServiceConnectedCallback> mServiceConnectedCallback;
    private int mStartCount;
    Map<String, XpBluetoothDeviceInfo> mXpBondedBluetoothDeviceCacheInfos;
    Map<String, XpBluetoothDeviceInfo> mXpNewBluetoothDeviceCacheInfos;
    ArrayList<XpBluetoothDeviceInfo> mXpTmpFoundBluetoothDeviceInfos;

    /* JADX INFO: Access modifiers changed from: private */
    public int convertNF2OSBTState(int i) {
        switch (i) {
            case 300:
                return 10;
            case 301:
                return 11;
            case 302:
                return 12;
            case 303:
                return 13;
            default:
                return 10;
        }
    }

    private int convertNF2OSBondState(int i) {
        if (i != 331) {
            return i != 332 ? 10 : 12;
        }
        return 11;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int convertNF2OSConnectState(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 110) {
                            return 0;
                        }
                        if (i != 120) {
                            if (i != 125) {
                                if (i != 140) {
                                    return -1;
                                }
                            }
                        }
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private int convertOsToNfState(int i) {
        if (i != 11) {
            return i != 12 ? 330 : 332;
        }
        return 331;
    }

    private int getBluetoothCategory(byte b) {
        return b != 2 ? -1 : 1;
    }

    public NFBluetoothManager(Context context) {
        super(context);
        this.mIsRegisteredReceiver = false;
        this.mIsNeedUpdateConnectState = false;
        this.mIsRegisterNF = false;
        this.mLastBTVisibilityOperate = null;
        this.mBluetoothCallbacks = new CopyOnWriteArrayList<>();
        this.mXpNewBluetoothDeviceCacheInfos = new ConcurrentHashMap();
        this.mXpTmpFoundBluetoothDeviceInfos = new ArrayList<>();
        this.mXpBondedBluetoothDeviceCacheInfos = new ConcurrentHashMap();
        this.mStartCount = 0;
        this.mServiceConnectedCallback = new ArrayList();
        this.ALPHA_COMPARATOR = new Comparator<XpBluetoothDeviceInfo>() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager.2
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
        this.mBluetoothControlCallback = new AnonymousClass3();
        this.mNameBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice == null) {
                    Logs.d("xpbluetooth nf name receiver device:null");
                    return;
                }
                String name = bluetoothDevice.getName();
                String address = bluetoothDevice.getAddress();
                Logs.d("xpbluetooth nf name receiver name:" + name + " address:" + address);
                XpBluetoothDeviceInfo xpBluetoothDevice = NFBluetoothManager.this.getXpBluetoothDevice(address);
                if (xpBluetoothDevice == null || TextUtils.isEmpty(name) || name.equals(xpBluetoothDevice.getDeviceName())) {
                    return;
                }
                xpBluetoothDevice.setDeviceName(name);
                NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
                if (nFBluetoothDevice != null) {
                    nFBluetoothDevice.setName(name);
                }
                NFBluetoothManager.this.notifyItemChange(xpBluetoothDevice);
            }
        };
        this.mProfileBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                NFBluetoothDevice nFBluetoothDevice;
                String action = intent.getAction();
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                Logs.d("xpbluetooth nf profile action:" + action);
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
                if (bluetoothDevice == null || (nFBluetoothDevice = NFBluetoothManager.this.getNFBluetoothDevice(bluetoothDevice.getAddress())) == null) {
                    return;
                }
                if (action.equals("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED")) {
                    nFBluetoothDevice.checkConnectedProfile(256);
                    nFBluetoothDevice.setProfileConnectState(256, intExtra2, intExtra);
                    NFBluetoothManager.this.onDeviceConnectStateChange(nFBluetoothDevice, intExtra2, intExtra, 256);
                    NFBluetoothManager.this.mProjectorAutoConnectManager.refreshBleConnectedData(intExtra, NFBluetoothManager.this.getXpBluetoothDevice(bluetoothDevice.getAddress()), 3);
                    Logs.d("xpbluetooth nf profile hid oldState:" + intExtra2 + " newState:" + intExtra + " " + nFBluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
                    if (intExtra == 2) {
                        NFBluetoothManager.this.conectedSendToPrivate(nFBluetoothDevice);
                    }
                    if (intExtra == 0 && nFBluetoothDevice.isReady()) {
                        NFBluetoothManager.this.notConectedSendToPrivate(nFBluetoothDevice);
                    }
                }
                NFBluetoothManager.this.notifyRefreshListCallback();
                NFBluetoothManager nFBluetoothManager = NFBluetoothManager.this;
                nFBluetoothManager.notifyItemChange(nFBluetoothManager.getXpBluetoothDevice(bluetoothDevice.getAddress()));
            }
        };
        this.mBtBoxesUtil = CarSettingsApp.getBtBoxesUtil();
        this.mBluetoothControl = this.mBtBoxesUtil.getBluetoothControl(this.mBluetoothControlCallback);
        this.mHidProfile = new HidProfile(context, this);
        this.mProfileIntentFilter = new IntentFilter();
        this.mAdapterIntentFilter = new IntentFilter();
        this.mAdapterIntentFilter.addAction("android.bluetooth.device.action.NAME_CHANGED");
        this.mProfileIntentFilter.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        HandlerThread handlerThread = new HandlerThread("xpsettings-ble");
        handlerThread.start();
        this.mHandler = new ConnectHandler(handlerThread.getLooper());
        this.mProjectorAutoConnectManager = new ProjectorAutoConnectManager(this);
        this.mProjectorAutoConnectManager.setConnectedBleDeviceUpdate();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerBluetoothCallback() {
        this.mStartCount++;
        Logs.d("xpbluetooth nf NFBluetoothCallback re nfcallback registerNFBluetoothCallback " + this.mStartCount);
        if (!this.mIsRegisterNF) {
            this.mBtBoxesUtil.registerCallback(this.mBluetoothControl);
            this.mIsRegisterNF = true;
            Logs.d("xpbluetooth nf nfcallback registerNFBluetoothCallback complete!");
        }
        registerProfileIntentReceiver();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterBluetoothCallback() {
        Logs.d("xpbluetooth nf NFBluetoothCallback un nfcallback unregisterNFBluetoothCallback " + this.mIsRegisterNF + " " + this.mStartCount);
        int i = this.mStartCount;
        if (i > 1) {
            this.mStartCount = i - 1;
            return;
        }
        if (this.mIsRegisterNF) {
            this.mBtBoxesUtil.unRegisterCallback(this.mBluetoothControl);
            this.mIsRegisterNF = false;
            Logs.d("xpbluetooth nf nfcallback unregisterNFBluetoothCallback complete!");
        }
        unregisterProfileIntentReceiver();
        this.mStartCount--;
    }

    public void setAutoConnect() {
        this.mBluetoothControl.setBtAutoConnect(7, 300);
    }

    public void registerProfileIntentReceiver() {
        if (this.mIsRegisteredReceiver) {
            return;
        }
        this.mContext.registerReceiver(this.mProfileBroadcastReceiver, this.mProfileIntentFilter);
        this.mContext.registerReceiver(this.mNameBroadcastReceiver, this.mAdapterIntentFilter);
        this.mIsRegisteredReceiver = true;
    }

    public void unregisterProfileIntentReceiver() {
        if (this.mIsRegisteredReceiver) {
            this.mContext.unregisterReceiver(this.mProfileBroadcastReceiver);
            this.mContext.unregisterReceiver(this.mNameBroadcastReceiver);
            this.mIsRegisteredReceiver = false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            if (!this.mServiceConnectedCallback.contains(serviceConnectedCallback)) {
                this.mServiceConnectedCallback.add(serviceConnectedCallback);
            }
        }
        this.mBluetoothControl.mConnection.registerConnecteCompletedCallback(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterServiceConnectCallback(XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback) {
        synchronized (this.mServiceConnectedCallback) {
            this.mServiceConnectedCallback.remove(serviceConnectedCallback);
        }
        this.mBluetoothControl.mConnection.unregisterConnecteCompletedCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCompleted() {
        synchronized (this.mServiceConnectedCallback) {
            for (XpBluetoothManger.ServiceConnectedCallback serviceConnectedCallback : this.mServiceConnectedCallback) {
                serviceConnectedCallback.connectCompleted();
            }
        }
    }

    @Override // com.xiaopeng.btservice.base.AbstractConnection.ConnecteCompletedCallback
    public void connectServiceCompleted() {
        Logs.d("xpbluetooth nf connect service completed!");
        notifyCompleted();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isEnable() {
        boolean isBtEnabled = this.mBluetoothControl.isBtEnabled();
        Logs.d("xpbluetooth nf isEnable service:" + this.mBluetoothControl.getBtService() + " enable:" + isBtEnabled);
        return isBtEnabled;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean setBluetoothEnabled(boolean z) {
        return this.mBluetoothControl.setBtEnabled(z);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isCurrentScanning() {
        Logs.d("xpbluetooth nf isCurrentScanning");
        return this.mBluetoothControl.isBtDiscovering();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void stopScanDevice() {
        LogUtils.d("xpbluetooth nf stop scan " + this.mStartCount);
        if (this.mStartCount <= 2) {
            this.mBluetoothControl.stopBtDiscovery();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean setName(String str) {
        return this.mBluetoothControl.setBtLocalName(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public String getName() {
        String btLocalName = this.mBluetoothControl.getBtLocalName();
        Logs.d("xpbluetooth nf name:" + btLocalName);
        return TextUtils.isEmpty(btLocalName) ? BluetoothAdapter.getDefaultAdapter().getName() : btLocalName;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isBtDiscoverable() {
        return this.mBluetoothControl.isBtDiscoverable();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void setBtDiscoverable(boolean z) {
        Boolean bool = this.mLastBTVisibilityOperate;
        if (bool != null && z == bool.booleanValue()) {
            Logs.d("bluetooth nf setBtDiscoverable " + this.mLastBTVisibilityOperate);
            return;
        }
        if (z) {
            this.mBluetoothControl.setBtDiscoverableTimeout(0);
        } else {
            this.mBluetoothControl.setBtDiscoverableTimeout(-1);
        }
        this.mLastBTVisibilityOperate = Boolean.valueOf(z);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerStateCallback(IXpBluetoothCallback iXpBluetoothCallback) {
        if (iXpBluetoothCallback == null || this.mBluetoothCallbacks.contains(iXpBluetoothCallback)) {
            return;
        }
        this.mBluetoothCallbacks.add(iXpBluetoothCallback);
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
        BluetoothDevice remoteDevice;
        boolean z = false;
        if (xpBluetoothDeviceInfo == null) {
            return false;
        }
        NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (nFBluetoothDevice != null && (nFBluetoothDevice.isHFPA2DPSupportedProfile() || nFBluetoothDevice.getCategory() == 1)) {
            z = this.mBluetoothControl.reqBtUnpair(xpBluetoothDeviceInfo.getDeviceAddr());
        } else {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && (remoteDevice = defaultAdapter.getRemoteDevice(xpBluetoothDeviceInfo.getDeviceAddr())) != null) {
                if (xpBluetoothDeviceInfo.getPairState() == 11) {
                    remoteDevice.cancelBondProcess();
                }
                if (xpBluetoothDeviceInfo.getPairState() != 10) {
                    z = remoteDevice.removeBond();
                }
            }
        }
        Logs.d("xpbluetooth nf unpair " + z + " " + xpBluetoothDeviceInfo.getDeviceAddr());
        return z;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean disConnect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        boolean isA2dpConnected = nFBluetoothDevice.isA2dpConnected();
        boolean isBtPhoneConnected = nFBluetoothDevice.isBtPhoneConnected();
        int category = nFBluetoothDevice.getCategory();
        Logs.d("xpbluetooth nf profile isPhone:" + isBtPhoneConnected + " isA2dp:" + isA2dpConnected + " category:" + category + " name:" + nFBluetoothDevice.getName());
        if (isA2dpConnected || isBtPhoneConnected || category == 1) {
            int reqBtDisconnectAll = this.mBluetoothControl.reqBtDisconnectAll();
            Logs.d("xpbluetooth nf profile disconnect:" + reqBtDisconnectAll);
            if (reqBtDisconnectAll > 0) {
                return true;
            }
        }
        return nFBluetoothDevice.disconnectHid();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean connect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null || !ensurePaired(nFBluetoothDevice)) {
            return false;
        }
        Logs.d("xpbluetooth nf connect " + nFBluetoothDevice);
        if (nFBluetoothDevice.isConnecting() || nFBluetoothDevice.isConnected()) {
            return true;
        }
        return nFBluetoothDevice.connect(true);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void connectAndRetry(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (connect(xpBluetoothDeviceInfo)) {
            return;
        }
        this.mHandler.removeMessages(1001);
        Message obtainMessage = this.mHandler.obtainMessage(1001);
        obtainMessage.obj = xpBluetoothDeviceInfo;
        this.mHandler.sendMessageDelayed(obtainMessage, 2000L);
    }

    public void removeDelayConnectMsg() {
        ConnectHandler connectHandler = this.mHandler;
        if (connectHandler != null) {
            connectHandler.removeMessages(1001);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ConnectHandler extends Handler {
        public ConnectHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1001) {
                return;
            }
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo = (XpBluetoothDeviceInfo) message.obj;
            if (NFBluetoothManager.this.connect(xpBluetoothDeviceInfo)) {
                return;
            }
            NFBluetoothManager.this.notifyItemChange(xpBluetoothDeviceInfo);
            NFBluetoothManager.this.notifyConnectError(xpBluetoothDeviceInfo);
        }
    }

    private boolean ensurePaired(NFBluetoothDevice nFBluetoothDevice) {
        if (nFBluetoothDevice.getBondState() == 330) {
            pair(getXpBluetoothDevice(nFBluetoothDevice.getAddress()), nFBluetoothDevice.getAddress());
            Logs.d("xpbluetooth nf " + nFBluetoothDevice.getAddress());
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean pair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, String str) {
        BluetoothDevice remoteDevice;
        BluetoothDevice remoteDevice2;
        if (isCurrentScanning()) {
            this.mBluetoothControl.stopBtDiscovery();
        }
        boolean z = false;
        if (xpBluetoothDeviceInfo == null) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && (remoteDevice2 = defaultAdapter.getRemoteDevice(str)) != null) {
                z = remoteDevice2.createBond();
            }
            Logs.d("xpbluetooth nf reqPair:" + z + " addr:" + str);
            return z;
        }
        xpBluetoothDeviceInfo.setParingBusy(true);
        NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (nFBluetoothDevice != null && (nFBluetoothDevice.isHFPA2DPSupportedProfile() || nFBluetoothDevice.getCategory() == 1)) {
            z = this.mBluetoothControl.reqBtPair(xpBluetoothDeviceInfo.getDeviceAddr());
        } else {
            BluetoothAdapter defaultAdapter2 = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter2 != null && (remoteDevice = defaultAdapter2.getRemoteDevice(xpBluetoothDeviceInfo.getDeviceAddr())) != null) {
                z = remoteDevice.createBond();
            }
        }
        Logs.d("xpbluetooth nf reqPair:" + z + " " + xpBluetoothDeviceInfo.getDeviceAddr());
        return z;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void reqBtDevicePairedDevices() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager.1
            @Override // java.lang.Runnable
            public void run() {
                NFBluetoothManager.this.mBluetoothControl.reqBtPairedDevices();
            }
        });
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean startScanList() {
        boolean startBtDiscovery = this.mBluetoothControl.startBtDiscovery();
        Logs.d("xpbluetooth nf start scan " + startBtDiscovery);
        return startBtDiscovery;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted() {
        ArrayList<XpBluetoothDeviceInfo> arrayList = new ArrayList<>();
        Logs.d("xpbluetooth nf getBondedDevicesSorted:" + this.mIsNeedUpdateConnectState + " " + this.mXpBondedBluetoothDeviceCacheInfos.size());
        if (this.mXpBondedBluetoothDeviceCacheInfos.size() > 0) {
            boolean z = this.mIsNeedUpdateConnectState;
        }
        arrayList.addAll(this.mXpBondedBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        return arrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted() {
        ArrayList<XpBluetoothDeviceInfo> arrayList = new ArrayList<>();
        arrayList.addAll(this.mXpNewBluetoothDeviceCacheInfos.values());
        Collections.sort(arrayList, this.ALPHA_COMPARATOR);
        Logs.d("xpbluetooth nf getAvailableDevicesSorted " + arrayList.size());
        return arrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void notifyRefreshListCallback() {
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onRefreshData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRetPairedDevicesCallback() {
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onRetPairedDevices();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceFounded(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
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
    public boolean isBtPhoneConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isBtPhoneConnected();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isA2dpConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isA2dpConnected();
    }

    public boolean isBtPhoneConnected(String str) {
        String hfpConnectedAddress = this.mBluetoothControl.getHfpConnectedAddress();
        Logs.d("xpbluetooth nf isBtPhoneConnected:" + str + " hfpAddress:" + hfpConnectedAddress);
        if (str.equals(hfpConnectedAddress)) {
            int hfpConnectionState = this.mBluetoothControl.getHfpConnectionState();
            Logs.d("xpbluetooth nf address:" + str + " isBtPhoneConnected:" + hfpConnectionState);
            return hfpConnectionState >= 140;
        }
        return false;
    }

    public boolean isA2dpConnected(String str) {
        String a2dpConnectedAddress = this.mBluetoothControl.getA2dpConnectedAddress();
        Logs.d("xpbluetooth nf isA2dpConnected:" + str + " a2dpAddress:" + a2dpConnectedAddress);
        if (str.equals(a2dpConnectedAddress)) {
            int a2dpConnectionState = this.mBluetoothControl.getA2dpConnectionState();
            Logs.d("xpbluetooth nf address:" + str + " isA2dpConnected:" + a2dpConnectionState);
            return a2dpConnectionState >= 140;
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isBusy(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isBusy();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isParing(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isParing();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isConnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isConnecting();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isDisconnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return false;
        }
        return nFBluetoothDevice.isDisConnecting();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public int getBondState(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice;
        if (xpBluetoothDeviceInfo == null || (nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice()) == null) {
            return 10;
        }
        return convertNF2OSBondState(nFBluetoothDevice.getBondState());
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
        if (nFBluetoothDevice != null) {
            return nFBluetoothDevice.isConnected();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBluetoothCategory(byte b, BluetoothClass bluetoothClass) {
        if (b != 2) {
            return getBluetoothCategory(bluetoothClass);
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBluetoothCategory(BluetoothClass bluetoothClass) {
        return Utils.getBluetoothCategory(bluetoothClass);
    }

    public void updateBondState(String str, int i) {
        NFBluetoothDevice nFBluetoothDevice = getNFBluetoothDevice(str);
        if (nFBluetoothDevice != null) {
            nFBluetoothDevice.setBondState(i);
        }
    }

    public void updateBondStateForData(String str, String str2, int i, boolean z) {
        boolean z2;
        NFBluetoothDevice nFBluetoothDevice = getNFBluetoothDevice(str);
        if (nFBluetoothDevice != null) {
            nFBluetoothDevice.setBondState(i);
            notifyItemChange(getXpBluetoothDevice(str));
        }
        XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(str);
        if (i == 330) {
            if (xpBluetoothDevice != null) {
                this.mXpNewBluetoothDeviceCacheInfos.put(str, xpBluetoothDevice);
                this.mXpTmpFoundBluetoothDeviceInfos.add(xpBluetoothDevice);
                this.mXpBondedBluetoothDeviceCacheInfos.remove(str);
                xpBluetoothDevice.setParingBusy(false);
            }
            if (!z) {
                this.mBluetoothControl.reqBtPairedDevices();
            }
            changePhoneConnectDevices();
        } else if (i != 332) {
        } else {
            if (xpBluetoothDevice != null) {
                this.mXpNewBluetoothDeviceCacheInfos.remove(xpBluetoothDevice.getDeviceAddr());
                this.mXpBondedBluetoothDeviceCacheInfos.put(str, xpBluetoothDevice);
                xpBluetoothDevice.setParingBusy(false);
            }
            if (!z) {
                this.mBluetoothControl.reqBtPairedDevices();
            }
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(str);
                StringBuilder sb = new StringBuilder();
                sb.append("xpbluetooth nf isBondingInitiatedLocally:");
                sb.append(remoteDevice != null && remoteDevice.isBondingInitiatedLocally());
                Logs.d(sb.toString());
                if (remoteDevice != null && remoteDevice.isBondingInitiatedLocally()) {
                    if (!Utils.isHidDevice(remoteDevice)) {
                        if (xpBluetoothDevice != null) {
                            z2 = connect(xpBluetoothDevice);
                        } else {
                            int reqBtConnectHfpA2dp = this.mBluetoothControl.reqBtConnectHfpA2dp(str);
                            Logs.d("xpbluetooth nf bond and connect profile:" + reqBtConnectHfpA2dp);
                            z2 = reqBtConnectHfpA2dp > 0;
                        }
                        if (!z2 && this.mProjectorAutoConnectManager.isActiveProjectorDevice(str2)) {
                            this.mProjectorAutoConnectManager.retryPairOrConnect();
                        }
                    } else {
                        Logs.d("xpbluetooth nf is Hid device!");
                    }
                }
            }
            changePhoneConnectDevices();
        }
    }

    public void changePhoneConnectDevices() {
        SpeechSettingsOverAllObserver.sendToPrivateCustom("change.phone.connect.devices", JsonUtils.toJSONString(SpeechSystemCaller.convertToBeans(getBondedDevicesSorted())));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void realDeviceBondStateChanged(String str, String str2, int i, int i2, boolean z) {
        if (z) {
            i = convertOsToNfState(i);
            i2 = convertOsToNfState(i2);
        }
        updateBondStateForData(str, str2, i2, z);
        Iterator<IXpBluetoothCallback> it = this.mBluetoothCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceBondStateChanged(str, str2, convertNF2OSBondState(i), convertNF2OSBondState(i2));
        }
        if (this.mProjectorAutoConnectManager.isActiveProjectorDevice(str2)) {
            this.mProjectorAutoConnectManager.notifyBondChanged(convertNF2OSBondState(i), convertNF2OSBondState(i2));
            if (i == 331 && i2 == 330) {
                Logs.d("xpbluetooth nf now in capsule mode, retry pair:" + str2 + " addr:" + str);
                this.mProjectorAutoConnectManager.retryPairOrConnect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 extends AbsBluetoothControlCallback {
        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onAvrcpStateChanged(String str, int i, int i2) {
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onDeviceAclDisconnected(String str) {
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onDeviceOutOfRange(String str) {
        }

        AnonymousClass3() {
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onBluetoothServiceReady() {
            Logs.log(NFBluetoothManager.TAG, "xpbluetooth nf onBluetoothServiceReady");
            NFBluetoothManager.this.notifyCompleted();
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void retPairedDevices(int i, String[] strArr, String[] strArr2, int[] iArr, byte[] bArr) {
            NFBluetoothDevice nFBluetoothDevice;
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo;
            HashMap hashMap = new HashMap();
            Logs.d("xpbluetooth refreshPairedDevice :" + i);
            if (i > 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    String str = strArr2[i2];
                    String str2 = strArr[i2];
                    byte b = bArr[i2];
                    BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str2);
                    BluetoothClass bluetoothClass = remoteDevice != null ? remoteDevice.getBluetoothClass() : null;
                    XpBluetoothDeviceInfo xpBluetoothDevice = NFBluetoothManager.this.getXpBluetoothDevice(str2);
                    Logs.d("xpbluetooth nf refreshPairedDevice xpBluetoothDeviceInfo:" + xpBluetoothDevice + " deviceAddress:" + str2 + " deviceName:" + str + " deviceCategory:" + ((int) b));
                    int bluetoothCategory = NFBluetoothManager.this.getBluetoothCategory(b, bluetoothClass);
                    if (xpBluetoothDevice == null) {
                        xpBluetoothDeviceInfo = new XpBluetoothDeviceInfo(str, str2);
                        nFBluetoothDevice = new NFBluetoothDevice(str, str2, bluetoothCategory, NFBluetoothManager.this.mBluetoothControl, NFBluetoothManager.this.mHidProfile);
                    } else {
                        nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
                        nFBluetoothDevice.setName(str);
                        nFBluetoothDevice.setCategory(bluetoothCategory);
                        nFBluetoothDevice.setSupportProfiles(iArr[i2]);
                        if (NFBluetoothManager.this.mIsNeedUpdateConnectState) {
                            nFBluetoothDevice.updateConnectState();
                        }
                        xpBluetoothDeviceInfo = xpBluetoothDevice;
                    }
                    nFBluetoothDevice.setBondState(332);
                    xpBluetoothDeviceInfo.setCategory(bluetoothCategory);
                    xpBluetoothDeviceInfo.setDeviceName(str);
                    xpBluetoothDeviceInfo.setDeviceAddr(str2);
                    xpBluetoothDeviceInfo.setBluetoothDevice(nFBluetoothDevice);
                    hashMap.put(str2, xpBluetoothDeviceInfo);
                    NFBluetoothManager.this.mXpNewBluetoothDeviceCacheInfos.remove(xpBluetoothDeviceInfo.getDeviceAddr());
                }
                NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.clear();
                NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.putAll(hashMap);
            } else {
                NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.clear();
            }
            if (NFBluetoothManager.this.mIsNeedUpdateConnectState) {
                NFBluetoothManager.this.mIsNeedUpdateConnectState = false;
            }
            NFBluetoothManager.this.notifyRefreshListCallback();
            NFBluetoothManager.this.mProjectorAutoConnectManager.setConnectedBleDeviceUpdate();
            NFBluetoothManager.this.notifyRetPairedDevicesCallback();
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onDeviceFound(String str, String str2, byte b) {
            NFBluetoothDevice nFBluetoothDevice;
            if (NFBluetoothDevice.sDebugLog) {
                Logs.d("xpbluetooth nf onDeviceFound address:" + str + " name:" + str2 + " category:" + ((int) b));
            }
            if (str.equals(str2)) {
                return;
            }
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo = null;
            Iterator<XpBluetoothDeviceInfo> it = NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                XpBluetoothDeviceInfo next = it.next();
                Logs.v("xpbluetooth nf onDeviceFound xpBluetoothDeviceInfo:" + next);
                if (str.equals(next.getDeviceAddr())) {
                    NFBluetoothManager.this.mXpTmpFoundBluetoothDeviceInfos.add(next);
                    xpBluetoothDeviceInfo = next;
                    break;
                }
            }
            Logs.v("xpbluetooth nf onDeviceFound bondDeviceInfo:" + xpBluetoothDeviceInfo + " address:" + str + " name:" + str2 + " category:" + ((int) b));
            if (xpBluetoothDeviceInfo == null) {
                XpBluetoothDeviceInfo xpBluetoothDevice = NFBluetoothManager.this.getXpBluetoothDevice(str);
                BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str);
                if (remoteDevice == null || Utils.isFilterDevice(remoteDevice)) {
                    return;
                }
                Logs.v("xpbluetooth nf onDeviceFound xpBluetoothDeviceInfo:" + xpBluetoothDevice);
                BluetoothClass bluetoothClass = remoteDevice.getBluetoothClass();
                if (xpBluetoothDevice == null) {
                    xpBluetoothDevice = new XpBluetoothDeviceInfo(str2, str);
                    nFBluetoothDevice = new NFBluetoothDevice(str2, str, NFBluetoothManager.this.getBluetoothCategory(bluetoothClass), NFBluetoothManager.this.mBluetoothControl, NFBluetoothManager.this.mHidProfile);
                } else {
                    nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
                    nFBluetoothDevice.setName(str2);
                    nFBluetoothDevice.setAddress(str);
                    nFBluetoothDevice.setCategory(NFBluetoothManager.this.getBluetoothCategory(bluetoothClass));
                }
                nFBluetoothDevice.setBondState(330);
                xpBluetoothDevice.setCategory(NFBluetoothManager.this.getBluetoothCategory(bluetoothClass));
                xpBluetoothDevice.setDeviceName(str2);
                xpBluetoothDevice.setDeviceAddr(str);
                xpBluetoothDevice.setBluetoothDevice(nFBluetoothDevice);
                if (!NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.containsKey(str)) {
                    NFBluetoothManager.this.mXpNewBluetoothDeviceCacheInfos.put(str, xpBluetoothDevice);
                    NFBluetoothManager.this.mXpTmpFoundBluetoothDeviceInfos.add(xpBluetoothDevice);
                }
                NFBluetoothManager.this.notifyRefreshListCallback();
                NFBluetoothManager.this.onDeviceFounded(xpBluetoothDevice);
            }
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onAdapterStateChanged(int i, int i2) {
            Logs.d("xpbluetooth nf onAdapterStateChanged prevState:" + i + " newState:" + i2);
            if (i2 == 300) {
                NFBluetoothManager.this.removeDelayConnectMsg();
                NFBluetoothManager.this.mXpTmpFoundBluetoothDeviceInfos.clear();
                NFBluetoothManager.this.mXpBondedBluetoothDeviceCacheInfos.clear();
                NFBluetoothManager.this.mXpNewBluetoothDeviceCacheInfos.clear();
            }
            Iterator<IXpBluetoothCallback> it = NFBluetoothManager.this.mBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onBluetoothStateChanged(NFBluetoothManager.this.convertNF2OSBTState(i), NFBluetoothManager.this.convertNF2OSBTState(i2));
            }
            if (i2 == 300) {
                NFBluetoothManager.this.setBtDiscoverable(false);
            }
            if (i2 == 302) {
                NFBluetoothManager.this.setBtDiscoverable(true);
                if (NFBluetoothManager.this.mProjectorAutoConnectManager.isInWhiteListModeAndCinema()) {
                    NFBluetoothManager.this.reqBtDevicePairedDevices();
                    NFBluetoothManager.this.mProjectorAutoConnectManager.retryScan();
                }
            }
            NFBluetoothManager.this.mProjectorAutoConnectManager.setConnectedBleDeviceUpdate();
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onAdapterDiscoveryStarted() {
            Logs.d("xpbluetooth nf onAdapterDiscoveryStarted");
            NFBluetoothManager.this.mXpTmpFoundBluetoothDeviceInfos.clear();
            Iterator<IXpBluetoothCallback> it = NFBluetoothManager.this.mBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onScanningStateChanged(true);
            }
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onAdapterDiscoveryFinished() {
            Logs.d("xpbluetooth nf onAdapterDiscoveryFinished ");
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager.3.1
                @Override // java.lang.Runnable
                public void run() {
                    NFBluetoothManager.this.mProjectorAutoConnectManager.parseScanResult(NFBluetoothManager.this.mXpTmpFoundBluetoothDeviceInfos);
                    Iterator<IXpBluetoothCallback> it = NFBluetoothManager.this.mBluetoothCallbacks.iterator();
                    while (it.hasNext()) {
                        it.next().onScanningStateChanged(false);
                    }
                    NFBluetoothManager.this.checkRemovedDevices();
                    NFBluetoothManager.this.notifyRefreshListCallback();
                }
            });
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onDeviceBondStateChanged(String str, String str2, int i, int i2) {
            Logs.d("xpbluetooth nf onDeviceBondStateChanged address:" + str + " name:" + str2 + " prevState:" + i + " newState:" + i2);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                boolean isHidDevice = Utils.isHidDevice(defaultAdapter.getRemoteDevice(str));
                Logs.d("xpbluetooth nf onDeviceBondStateChanged isHid:" + isHidDevice);
                if (isHidDevice) {
                    NFBluetoothManager.this.mBluetoothControl.reqBtPairedDevices();
                } else {
                    NFBluetoothManager.this.realDeviceBondStateChanged(str, str2, i, i2, false);
                }
            }
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onDeviceUuidsUpdated(String str, String str2, int i) {
            Logs.d("xpbluetooth nf onDeviceUuidsUpdated address:" + str + " name:" + str2 + " supportProfile:" + i);
            XpBluetoothDeviceInfo xpBluetoothDevice = NFBluetoothManager.this.getXpBluetoothDevice(str);
            if (xpBluetoothDevice != null) {
                NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
                if (nFBluetoothDevice == null) {
                    Logs.d("xpbluetooth nf onDeviceUuidsUpdated nfBluetoothDevice null");
                } else {
                    nFBluetoothDevice.setSupportProfiles(i);
                }
            }
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onHfpStateChanged(final String str, int i, int i2) {
            NFBluetoothDevice nFBluetoothDevice = NFBluetoothManager.this.getNFBluetoothDevice(str);
            Logs.d("xpbluetooth nf profile onHfpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
            if (nFBluetoothDevice != null) {
                nFBluetoothDevice.checkConnectedProfile(1);
                nFBluetoothDevice.setProfileConnectState(1, i, i2);
                if (i2 == 140) {
                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.-$$Lambda$NFBluetoothManager$3$BLC07dwuyS51bdLHvx_LNGdFZuA
                        @Override // java.lang.Runnable
                        public final void run() {
                            NFBluetoothManager.AnonymousClass3.this.lambda$onHfpStateChanged$0$NFBluetoothManager$3(str);
                        }
                    });
                    NFBluetoothManager.this.conectedSendToPrivate(nFBluetoothDevice);
                }
                if (i2 == 110 && nFBluetoothDevice.isReady()) {
                    NFBluetoothManager.this.notConectedSendToPrivate(nFBluetoothDevice);
                }
                NFBluetoothManager.this.notifyRefreshListCallback();
                NFBluetoothManager nFBluetoothManager = NFBluetoothManager.this;
                nFBluetoothManager.notifyItemChange(nFBluetoothManager.getXpBluetoothDevice(str));
                NFBluetoothManager.this.onDeviceConnectStateChange(nFBluetoothDevice, i, i2, 1);
                NFBluetoothManager.this.mProjectorAutoConnectManager.refreshBleConnectedData(NFBluetoothManager.this.convertNF2OSConnectState(i2), NFBluetoothManager.this.getXpBluetoothDevice(str), 1);
            }
        }

        public /* synthetic */ void lambda$onHfpStateChanged$0$NFBluetoothManager$3(String str) {
            NFBluetoothManager.this.refreshDeviceName(str);
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onA2dpStateChanged(final String str, int i, int i2) {
            Logs.d("xpbluetooth nf profile onA2dpStateChanged address:" + str + " prevState:" + i + " newState:" + i2);
            NFBluetoothDevice nFBluetoothDevice = NFBluetoothManager.this.getNFBluetoothDevice(str);
            if (nFBluetoothDevice != null) {
                nFBluetoothDevice.checkConnectedProfile(2);
                nFBluetoothDevice.setProfileConnectState(2, i, i2);
                if (i2 == 140) {
                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.-$$Lambda$NFBluetoothManager$3$GVpjcapIiF9FHKwaMaBH835nNH4
                        @Override // java.lang.Runnable
                        public final void run() {
                            NFBluetoothManager.AnonymousClass3.this.lambda$onA2dpStateChanged$1$NFBluetoothManager$3(str);
                        }
                    });
                    NFBluetoothManager.this.conectedSendToPrivate(nFBluetoothDevice);
                }
                if (i2 == 110 && nFBluetoothDevice.isReady()) {
                    NFBluetoothManager.this.notConectedSendToPrivate(nFBluetoothDevice);
                }
                NFBluetoothManager.this.notifyRefreshListCallback();
                NFBluetoothManager nFBluetoothManager = NFBluetoothManager.this;
                nFBluetoothManager.notifyItemChange(nFBluetoothManager.getXpBluetoothDevice(str));
                NFBluetoothManager.this.onDeviceConnectStateChange(nFBluetoothDevice, i, i2, 2);
                NFBluetoothManager.this.mProjectorAutoConnectManager.a2dpChanged(str, NFBluetoothManager.this.convertNF2OSConnectState(i), NFBluetoothManager.this.convertNF2OSConnectState(i2));
                NFBluetoothManager.this.mProjectorAutoConnectManager.refreshBleConnectedData(NFBluetoothManager.this.convertNF2OSConnectState(i2), NFBluetoothManager.this.getXpBluetoothDevice(str), 2);
            }
        }

        public /* synthetic */ void lambda$onA2dpStateChanged$1$NFBluetoothManager$3(String str) {
            NFBluetoothManager.this.refreshDeviceName(str);
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onBtAutoConnectStateChanged(String str, int i, int i2) {
            Logs.v("xpbluetooth nf onBtAutoConnectStateChanged address:" + str + " BaseState:" + i + " -> " + i2);
        }

        @Override // com.xiaopeng.btservice.base.AbsBluetoothControlCallback
        public void onAdapterDiscoverableModeChanged(int i, int i2) {
            Logs.d("xpbluetooth nf onAdapterDiscoverableModeChanged prevState:" + i + " newState:" + i2);
            Iterator<IXpBluetoothCallback> it = NFBluetoothManager.this.mBluetoothCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onDiscoverableModeChanged(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDeviceName(String str) {
        BluetoothDevice remoteDevice;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || (remoteDevice = defaultAdapter.getRemoteDevice(str)) == null) {
            return;
        }
        String name = remoteDevice.getName();
        Logs.d("xpbluetooth nf refreshDeviceName name:" + name + " address:" + str);
        XpBluetoothDeviceInfo xpBluetoothDevice = getXpBluetoothDevice(str);
        if (TextUtils.isEmpty(name) || xpBluetoothDevice == null || name.equals(xpBluetoothDevice.getDeviceName())) {
            return;
        }
        xpBluetoothDevice.setDeviceName(name);
        NFBluetoothDevice nFBluetoothDevice = (NFBluetoothDevice) xpBluetoothDevice.getAbsBluetoothDevice();
        if (nFBluetoothDevice != null) {
            nFBluetoothDevice.setName(name);
        }
        notifyItemChange(xpBluetoothDevice);
    }

    public void onDeviceConnectStateChange(NFBluetoothDevice nFBluetoothDevice, int i, int i2, int i3) {
        if (!nFBluetoothDevice.isConnectedProfile(i3)) {
            Logs.d("xpbluetooth nf profile connect return " + i3);
            return;
        }
        Iterator it = nFBluetoothDevice.getConnectedProfile().iterator();
        while (true) {
            boolean z = false;
            if (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num.intValue() != 4) {
                    NFConnectState connectState = nFBluetoothDevice.getConnectState(num.intValue());
                    if (connectState != null && ((connectState.getPrevState() == 1 && connectState.getNewState() == 0) || (connectState.getPrevState() == 120 && connectState.getNewState() == 110))) {
                        z = true;
                    }
                    if (!z) {
                        return;
                    }
                }
            } else {
                Logs.d("xpbluetooth nf profile connect all profile completed! ");
                Iterator<IXpBluetoothCallback> it2 = this.mBluetoothCallbacks.iterator();
                while (it2.hasNext()) {
                    it2.next().onConnectionStateChanged(nFBluetoothDevice.getAddress(), nFBluetoothDevice.getName(), 1, 0);
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRemovedDevices() {
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

    /* JADX INFO: Access modifiers changed from: private */
    public NFBluetoothDevice getNFBluetoothDevice(String str) {
        for (XpBluetoothDeviceInfo xpBluetoothDeviceInfo : this.mXpBondedBluetoothDeviceCacheInfos.values()) {
            if (xpBluetoothDeviceInfo.getDeviceAddr().equals(str)) {
                return (NFBluetoothDevice) xpBluetoothDeviceInfo.getAbsBluetoothDevice();
            }
        }
        for (XpBluetoothDeviceInfo xpBluetoothDeviceInfo2 : this.mXpNewBluetoothDeviceCacheInfos.values()) {
            if (xpBluetoothDeviceInfo2.getDeviceAddr().equals(str)) {
                return (NFBluetoothDevice) xpBluetoothDeviceInfo2.getAbsBluetoothDevice();
            }
        }
        return null;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void setConnectOperateErrorListener(XpBluetoothManger.ConnectErrorListener connectErrorListener) {
        this.mConnectErrorListener = connectErrorListener;
    }

    public void notifyConnectError(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        XpBluetoothManger.ConnectErrorListener connectErrorListener = this.mConnectErrorListener;
        if (connectErrorListener != null) {
            connectErrorListener.notifyConnectError(xpBluetoothDeviceInfo);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public boolean isHasPhoneConnected() {
        String[] strArr;
        for (String str : new String[]{this.mBluetoothControl.getHfpConnectedAddress(), this.mBluetoothControl.getA2dpConnectedAddress()}) {
            Logs.d("xpbluetooth nf isHasPhoneConnected s:" + str);
            if (!TextUtils.isEmpty(str) && !NfDef.DEFAULT_ADDRESS.equals(str) && isPhone(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str))) {
                return true;
            }
        }
        return false;
    }

    public boolean isPhone(BluetoothDevice bluetoothDevice) {
        return Utils.isPhone(bluetoothDevice);
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

    public void conectedSendToPrivate(NFBluetoothDevice nFBluetoothDevice) {
        BluetoothDevicesBean.DeviceBean deviceBean = new BluetoothDevicesBean.DeviceBean();
        deviceBean.setName(nFBluetoothDevice.getName());
        deviceBean.setMac(nFBluetoothDevice.getAddress());
        deviceBean.setType(SpeechSystemCaller.typeToCategory(nFBluetoothDevice.getCategory()));
        SpeechSettingsOverAllObserver.sendToPrivateCustom("trigger.bluetooth.connect", JsonUtils.toJSONString(deviceBean));
    }

    public void notConectedSendToPrivate(NFBluetoothDevice nFBluetoothDevice) {
        BluetoothDevicesBean.DeviceBean deviceBean = new BluetoothDevicesBean.DeviceBean();
        deviceBean.setName(nFBluetoothDevice.getName());
        deviceBean.setMac(nFBluetoothDevice.getAddress());
        deviceBean.setType(SpeechSystemCaller.typeToCategory(nFBluetoothDevice.getCategory()));
        SpeechSettingsOverAllObserver.sendToPrivateCustom("trigger.bluetooth.disconnect", JsonUtils.toJSONString(deviceBean));
    }
}
