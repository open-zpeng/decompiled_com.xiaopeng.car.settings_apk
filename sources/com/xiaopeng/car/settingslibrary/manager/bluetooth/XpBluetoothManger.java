package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.content.Context;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.NFBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.os.OsBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.mediacenter.MediaCenterManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class XpBluetoothManger {
    public static final int PROJECTOR_BONDED = 6;
    public static final int PROJECTOR_BONDING = 5;
    public static final int PROJECTOR_CONNECTED = 8;
    public static final int PROJECTOR_CONNECTING = 7;
    public static final int PROJECTOR_FAILED_CONNECT = 3;
    public static final int PROJECTOR_FAILED_PAIR = 2;
    public static final int PROJECTOR_FAILED_SCAN = 1;
    public static final int PROJECTOR_FOUNDED = 4;
    private static volatile XpBluetoothManger sInstance;
    public static boolean sUseOsBluetooth = Config.IS_SDK_HIGHER_Q;
    IXpBluetoothInterface mBluetoothInterface;
    Context mContext;
    private int mIsSourceInBlue = -1;
    List<NameChangeListener> mNameChangeListeners = new ArrayList();
    List<ButtonClickListener> mButtonClickListeners = new ArrayList();
    MediaCenterManager.BTStatusListener mBtStatusListener = new MediaCenterManager.BTStatusListener() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger.1
        public void onBtStatusChanged(int i) {
            Logs.d("xpbluetooth source onBtStatusChanged:" + i);
            XpBluetoothManger.this.mIsSourceInBlue = i;
            XpBluetoothManger.this.refreshConnectedDevice();
        }
    };

    /* loaded from: classes.dex */
    public interface ButtonClickListener {
        void onButtonClick(boolean z);
    }

    /* loaded from: classes.dex */
    public interface ConnectErrorListener {
        void notifyConnectError(XpBluetoothDeviceInfo xpBluetoothDeviceInfo);
    }

    /* loaded from: classes.dex */
    public interface NameChangeListener {
        void onNameChange(String str);
    }

    /* loaded from: classes.dex */
    public interface ServiceConnectedCallback {
        void connectCompleted();
    }

    public static XpBluetoothManger getInstance() {
        if (sInstance == null) {
            synchronized (XpBluetoothManger.class) {
                if (sInstance == null) {
                    sInstance = new XpBluetoothManger(CarSettingsApp.getContext());
                }
            }
        }
        return sInstance;
    }

    private XpBluetoothManger(Context context) {
        this.mContext = context;
        if (sUseOsBluetooth) {
            this.mBluetoothInterface = new OsBluetoothManager(context);
        } else {
            this.mBluetoothInterface = new NFBluetoothManager(context);
        }
    }

    public void registerNFBluetoothCallback() {
        this.mBluetoothInterface.registerBluetoothCallback();
    }

    public void unregisterNFBluetoothCallback() {
        this.mBluetoothInterface.unregisterBluetoothCallback();
    }

    public void registerServiceConnectCallback(ServiceConnectedCallback serviceConnectedCallback) {
        this.mBluetoothInterface.registerServiceConnectCallback(serviceConnectedCallback);
    }

    public void unregisterServiceConnectCallback(ServiceConnectedCallback serviceConnectedCallback) {
        this.mBluetoothInterface.unregisterServiceConnectCallback(serviceConnectedCallback);
    }

    public void registerBluetoothOnChange() {
        this.mBluetoothInterface.registerBluetoothOnChange();
    }

    public void unregisterBluetoothOnChange() {
        this.mBluetoothInterface.unregisterBluetoothOnChange();
    }

    public void setConnectOperateErrorListener(ConnectErrorListener connectErrorListener) {
        this.mBluetoothInterface.setConnectOperateErrorListener(connectErrorListener);
    }

    public boolean isSourceInBluetooth() {
        if (this.mIsSourceInBlue == -1) {
            this.mIsSourceInBlue = XuiSettingsManager.getInstance().isSourceInBluetooth();
        }
        return this.mIsSourceInBlue == 6;
    }

    public void registerBluetoothSourceListener() {
        try {
            MediaCenterManager mediaCenterManager = XuiSettingsManager.getInstance().getMediaCenterManager();
            if (mediaCenterManager != null) {
                mediaCenterManager.registerBtStatusListener(this.mBtStatusListener);
                Logs.d("xpbluetooth source register listener ");
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void unRegisterBluetoothSourceListener() {
        try {
            MediaCenterManager mediaCenterManager = XuiSettingsManager.getInstance().getMediaCenterManager();
            if (mediaCenterManager != null) {
                mediaCenterManager.unRegisterBtStatusListener(this.mBtStatusListener);
                Logs.d("xpbluetooth source unregister listener");
                this.mIsSourceInBlue = -1;
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshConnectedDevice() {
        Iterator<XpBluetoothDeviceInfo> it = getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            AbsBluetoothDevice absBluetoothDevice = next.getAbsBluetoothDevice();
            if (absBluetoothDevice != null && absBluetoothDevice.isConnected()) {
                this.mBluetoothInterface.notifyItemChange(next);
            }
        }
    }

    public void setBleWhiteListMode(boolean z) {
        this.mBluetoothInterface.setBleWhiteListMode(z);
    }

    public void forceStartScan() {
        this.mBluetoothInterface.forceStartScanRetry();
    }

    public boolean isAlreadyConnectedWhiteList() {
        return this.mBluetoothInterface.isAlreadyConnectedWhiteList();
    }

    public void forceStopScan() {
        this.mBluetoothInterface.timeoutForceStopScanRetry();
    }

    public boolean isProjectorFounded() {
        return this.mBluetoothInterface.isFoundWhiteListDevice();
    }

    public boolean isAlreadyBoundedWhiteList() {
        return this.mBluetoothInterface.isAlreadyBoundedWhiteList();
    }

    public boolean isConnectingWhiteList() {
        return this.mBluetoothInterface.isConnectingWhiteList();
    }

    public boolean isBondingWhiteList() {
        return this.mBluetoothInterface.isBondingWhiteList();
    }

    public void stopPairOrConnectProjector() {
        this.mBluetoothInterface.timeoutForceStopPairOrConnectRetry();
    }

    public void startPairOrConnectProjector() {
        this.mBluetoothInterface.forceStartPairOrConnectRetry();
    }

    public boolean isEnable() {
        return this.mBluetoothInterface.isEnable();
    }

    public boolean isCurrentScanning() {
        return this.mBluetoothInterface.isCurrentScanning();
    }

    public boolean isBtDiscoverable() {
        return this.mBluetoothInterface.isBtDiscoverable();
    }

    public void setBtDiscoverable(boolean z) {
        this.mBluetoothInterface.setBtDiscoverable(z);
    }

    public boolean setBluetoothEnabled(boolean z) {
        for (ButtonClickListener buttonClickListener : this.mButtonClickListeners) {
            buttonClickListener.onButtonClick(z);
        }
        return this.mBluetoothInterface.setBluetoothEnabled(z);
    }

    public void stopScanDevice() {
        this.mBluetoothInterface.stopScanDevice();
    }

    public void addNameChangeListener(NameChangeListener nameChangeListener) {
        if (this.mNameChangeListeners.contains(nameChangeListener)) {
            return;
        }
        this.mNameChangeListeners.add(nameChangeListener);
    }

    public void removeNameChangeListener(NameChangeListener nameChangeListener) {
        this.mNameChangeListeners.remove(nameChangeListener);
    }

    public void addButtonClickListener(ButtonClickListener buttonClickListener) {
        if (this.mButtonClickListeners.contains(buttonClickListener)) {
            return;
        }
        this.mButtonClickListeners.add(buttonClickListener);
    }

    public void removeButtonClickListener(ButtonClickListener buttonClickListener) {
        this.mButtonClickListeners.remove(buttonClickListener);
    }

    public boolean setDeviceName(String str, boolean z) {
        boolean name = this.mBluetoothInterface.setName(str);
        if (z && name) {
            for (NameChangeListener nameChangeListener : this.mNameChangeListeners) {
                nameChangeListener.onNameChange(str);
            }
        }
        return name;
    }

    public String getName() {
        return this.mBluetoothInterface.getName();
    }

    public void registerCallback(IXpBluetoothCallback iXpBluetoothCallback) {
        this.mBluetoothInterface.registerStateCallback(iXpBluetoothCallback);
    }

    public void unregisterCallback(IXpBluetoothCallback iXpBluetoothCallback) {
        this.mBluetoothInterface.unregisterStateCallback(iXpBluetoothCallback);
    }

    public void registerProjectorCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        this.mBluetoothInterface.registerProjectorStateCallback(iProjectorBluetoothCallback);
    }

    public void unregisterProjectorCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        this.mBluetoothInterface.unregisterProjectorStateCallback(iProjectorBluetoothCallback);
    }

    public void unpair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mBluetoothInterface.unpair(xpBluetoothDeviceInfo);
    }

    public boolean disConnect(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.disConnect(xpBluetoothDeviceInfo);
    }

    public boolean startScanList() {
        return this.mBluetoothInterface.startScanList();
    }

    public void reqBtDevicePairedDevices() {
        this.mBluetoothInterface.reqBtDevicePairedDevices();
    }

    public ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mBluetoothInterface.getBondedDevicesSorted();
    }

    public ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mBluetoothInterface.getAvailableDevicesSorted();
    }

    public XpBluetoothDeviceInfo getXpBluetoothDeviceInfo(String str) {
        return this.mBluetoothInterface.getXpBluetoothDevice(str);
    }

    public void connectAndRetry(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mBluetoothInterface.connectAndRetry(xpBluetoothDeviceInfo);
    }

    public boolean pair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, String str) {
        return this.mBluetoothInterface.pair(xpBluetoothDeviceInfo, str);
    }

    public boolean isBtPhoneConnected(String str) {
        return this.mBluetoothInterface.isBtPhoneConnected(getXpBluetoothDeviceInfo(str));
    }

    public boolean isA2dpConnected(String str) {
        return this.mBluetoothInterface.isA2dpConnected(getXpBluetoothDeviceInfo(str));
    }

    public boolean isBusy(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.isBusy(xpBluetoothDeviceInfo);
    }

    public boolean isParing(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.isParing(xpBluetoothDeviceInfo);
    }

    public boolean isConnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.isConnecting(xpBluetoothDeviceInfo);
    }

    public boolean isDisconnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.isDisconnecting(xpBluetoothDeviceInfo);
    }

    public int getBondState(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.getBondState(xpBluetoothDeviceInfo);
    }

    public boolean isConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mBluetoothInterface.isConnected(xpBluetoothDeviceInfo);
    }

    public boolean isHasPhoneConnected() {
        return this.mBluetoothInterface.isHasPhoneConnected();
    }

    public void realDeviceBondStateChanged(String str, String str2, int i, int i2, boolean z) {
        this.mBluetoothInterface.realDeviceBondStateChanged(str, str2, i, i2, z);
    }
}
