package com.xiaopeng.car.settingslibrary.vm.bluetooth;

import android.app.Application;
import android.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class BluetoothSettingsViewModel extends AndroidViewModel implements XpBluetoothManger.ConnectErrorListener, IXpBluetoothCallback, XpBluetoothManger.ServiceConnectedCallback, XpBluetoothManger.NameChangeListener, XpBluetoothManger.ButtonClickListener {
    private static final int CONNECT_ERROR = 1;
    private static final int CONNECT_OPERATE_ERROR = 3;
    private static final int PAIR_ERROR = 2;
    private static final String TAG = "bluetooth";
    static boolean mIsBtChanging = false;
    Application mApplication;
    private MutableLiveData<Boolean> mBluetoothConnectedCompleted;
    private MutableLiveData<String> mBluetoothNameLiveData;
    private MutableLiveData<Integer> mBluetoothStatusLiveData;
    private MutableLiveData<Boolean> mBluetoothVisibilityLiveData;
    List<IBluetoothViewCallback> mIBluetoothViewCallback;
    boolean mIsBtOn;
    private MutableLiveData<Pair<Boolean, XpBluetoothDeviceInfo>> mIsShowConnectDialogLiveData;
    private MutableLiveData<XpBluetoothDeviceInfo> mItemChangeLiveData;
    private MutableLiveData<Boolean> mListLiveData;
    private MutableLiveData<XpBluetoothDeviceInfo> mOnDeviceBondChangedLiveData;
    private MutableLiveData<XpBluetoothDeviceInfo> mOnDeviceFoundLiveData;
    private MutableLiveData<Boolean> mOnRetPairDeivesLiveData;
    private MutableLiveData<Boolean> mPairLiveData;
    private MutableLiveData<Boolean> mRealScanningLiveData;
    private MutableLiveData<Boolean> mScanningLiveData;
    XpBluetoothManger mXpBluetoothManger;

    public BluetoothSettingsViewModel(Application application) {
        super(application);
        this.mIBluetoothViewCallback = new CopyOnWriteArrayList();
        this.mBluetoothNameLiveData = new MutableLiveData<>();
        this.mBluetoothVisibilityLiveData = new MutableLiveData<>();
        this.mBluetoothStatusLiveData = new MutableLiveData<>();
        this.mIsShowConnectDialogLiveData = new MutableLiveData<>();
        this.mScanningLiveData = new MutableLiveData<>();
        this.mRealScanningLiveData = new MutableLiveData<>();
        this.mListLiveData = new MutableLiveData<>();
        this.mPairLiveData = new MutableLiveData<>();
        this.mItemChangeLiveData = new MutableLiveData<>();
        this.mOnDeviceFoundLiveData = new MutableLiveData<>();
        this.mOnDeviceBondChangedLiveData = new MutableLiveData<>();
        this.mBluetoothConnectedCompleted = new MutableLiveData<>();
        this.mOnRetPairDeivesLiveData = new MutableLiveData<>();
        this.mIsBtOn = false;
        this.mApplication = application;
        this.mXpBluetoothManger = XpBluetoothManger.getInstance();
    }

    public void onStartVM() {
        registerServiceConnectCallback();
        registerNFBluetoothCallback();
        registerNameAndButtonChangeListener();
        registerStateCallback();
        registerBluetoothSourceListener();
        registerConnectOperateErrorListener();
        viewOnStart();
    }

    public void registerServiceConnectCallback() {
        this.mXpBluetoothManger.registerServiceConnectCallback(this);
    }

    public void registerNFBluetoothCallback() {
        this.mXpBluetoothManger.registerNFBluetoothCallback();
    }

    public void registerConnectOperateErrorListener() {
        this.mXpBluetoothManger.setConnectOperateErrorListener(this);
    }

    public void viewOnStart() {
        refreshBTdata();
        onRefreshData();
        if (this.mIsBtOn) {
            startScanList();
            this.mXpBluetoothManger.reqBtDevicePairedDevices();
        }
    }

    public void onStopVM() {
        unregisterNFBluetoothCallback();
        unregisterServiceConnectCallback();
        unregisterNameAndButtonChangeListener();
        unregisterStateCallback();
        unRegisterBluetoothSourceListener();
        unregisterConnectOperateErrorListener();
    }

    public void unregisterNFBluetoothCallback() {
        this.mXpBluetoothManger.unregisterNFBluetoothCallback();
    }

    public void unregisterServiceConnectCallback() {
        this.mXpBluetoothManger.unregisterServiceConnectCallback(this);
    }

    public void unregisterConnectOperateErrorListener() {
        this.mXpBluetoothManger.setConnectOperateErrorListener(null);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger.ServiceConnectedCallback
    public void connectCompleted() {
        Logs.d("xpbluetooth connectCompleted ");
        this.mBluetoothConnectedCompleted.postValue(true);
    }

    public void refreshBTdata() {
        this.mIsBtOn = isEnable();
        if (this.mIsBtOn) {
            this.mBluetoothStatusLiveData.setValue(12);
            if (mIsBtChanging) {
                setVisibility(true);
                mIsBtChanging = false;
            }
        } else {
            this.mBluetoothStatusLiveData.setValue(10);
        }
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.1
            @Override // java.lang.Runnable
            public void run() {
                boolean isVisibility = BluetoothSettingsViewModel.this.isVisibility();
                Logs.d("xpbluetooth nf isVisibility:" + isVisibility);
                BluetoothSettingsViewModel.this.mBluetoothVisibilityLiveData.postValue(Boolean.valueOf(isVisibility));
                BluetoothSettingsViewModel.this.mBluetoothNameLiveData.postValue(BluetoothSettingsViewModel.this.getName());
                BluetoothSettingsViewModel.this.mScanningLiveData.postValue(Boolean.valueOf(BluetoothSettingsViewModel.this.isCurrentScanning()));
            }
        });
    }

    public Object isScanning() {
        return this.mScanningLiveData.getValue();
    }

    public boolean isCurrentScanning() {
        return this.mXpBluetoothManger.isCurrentScanning();
    }

    public MutableLiveData<String> getBluetoothNameLiveData() {
        return this.mBluetoothNameLiveData;
    }

    public MutableLiveData<Boolean> getBluetoothConnectedCompleted() {
        return this.mBluetoothConnectedCompleted;
    }

    public MutableLiveData<Boolean> getOnRetPairDeivesLiveData() {
        return this.mOnRetPairDeivesLiveData;
    }

    public MutableLiveData<XpBluetoothDeviceInfo> getOnDeviceFoundLiveData() {
        return this.mOnDeviceFoundLiveData;
    }

    public MutableLiveData<XpBluetoothDeviceInfo> getOnDeviceBondChangedLiveData() {
        return this.mOnDeviceBondChangedLiveData;
    }

    public MutableLiveData<Boolean> getBluetoothVisibilityLiveData() {
        return this.mBluetoothVisibilityLiveData;
    }

    public MutableLiveData<Integer> getBluetoothStatusLiveData() {
        return this.mBluetoothStatusLiveData;
    }

    public MutableLiveData<Boolean> getPairLiveData() {
        return this.mPairLiveData;
    }

    public MutableLiveData<Boolean> getListLiveData() {
        return this.mListLiveData;
    }

    public MutableLiveData<Boolean> getScanningLiveData() {
        return this.mScanningLiveData;
    }

    public MutableLiveData<Boolean> getRealScanningLiveData() {
        return this.mRealScanningLiveData;
    }

    public boolean isEnable() {
        this.mIsBtOn = this.mXpBluetoothManger.isEnable();
        return this.mIsBtOn;
    }

    public boolean isBtOn() {
        return this.mIsBtOn;
    }

    public String getName() {
        return this.mXpBluetoothManger.getName();
    }

    public boolean isVisibility() {
        return this.mXpBluetoothManger.isBtDiscoverable();
    }

    public void setVisibility(boolean z) {
        Logs.d("xpbluetooth setVisibility:" + z);
        this.mXpBluetoothManger.setBtDiscoverable(z);
    }

    public boolean changeBTStatus(boolean z) {
        Logs.d("xpbluetooth open bluetooth " + z);
        mIsBtChanging = true;
        return this.mXpBluetoothManger.setBluetoothEnabled(z);
    }

    public void stopScanning() {
        this.mXpBluetoothManger.stopScanDevice();
    }

    public boolean setName(String str) {
        return this.mXpBluetoothManger.setDeviceName(str, true);
    }

    public void registerStateCallback() {
        this.mXpBluetoothManger.registerCallback(this);
    }

    public void unregisterStateCallback() {
        this.mXpBluetoothManger.unregisterCallback(this);
    }

    public void unpair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mXpBluetoothManger.unpair(xpBluetoothDeviceInfo);
    }

    public boolean disConnect(boolean z, XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.disConnect(xpBluetoothDeviceInfo);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onBluetoothStateChanged(int i, int i2) {
        this.mBluetoothStatusLiveData.postValue(Integer.valueOf(i2));
        if (i2 == 12) {
            this.mBluetoothNameLiveData.postValue(getName());
            mIsBtChanging = false;
            this.mIsBtOn = true;
            this.mXpBluetoothManger.reqBtDevicePairedDevices();
        } else if (i2 == 10) {
            mIsBtChanging = false;
            this.mIsBtOn = false;
        }
        if (i2 == 12 || i2 == 10) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.2
                @Override // java.lang.Runnable
                public void run() {
                    boolean isVisibility = BluetoothSettingsViewModel.this.isVisibility();
                    Logs.d("xpbluetooth get visibility:" + isVisibility);
                    BluetoothSettingsViewModel.this.mBluetoothVisibilityLiveData.postValue(Boolean.valueOf(isVisibility));
                    BluetoothSettingsViewModel.this.mListLiveData.postValue(true);
                }
            }, 300L);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onScanningStateChanged(boolean z) {
        this.mScanningLiveData.postValue(Boolean.valueOf(z));
        this.mRealScanningLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onDeviceBondStateChanged(final String str, final String str2, int i, int i2) {
        if (i2 == 10 && i == 11) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.3
                @Override // java.lang.Runnable
                public void run() {
                    BluetoothSettingsViewModel.this.notifyErrorCallback(str, str2, 2);
                }
            });
        } else if (i2 == 12 && i == 11) {
            this.mPairLiveData.postValue(true);
        }
        Logs.d("settings bluetoothvm onDeviceBondStateChanged " + str + " " + getBluetoothDeviceInfo(str));
        this.mOnDeviceBondChangedLiveData.postValue(getBluetoothDeviceInfo(str));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onConnectionStateChanged(final String str, final String str2, int i, int i2) {
        if (i == 1 && i2 == 0) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.4
                @Override // java.lang.Runnable
                public void run() {
                    BluetoothSettingsViewModel.this.notifyErrorCallback(str, str2, 1);
                }
            });
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onDiscoverableModeChanged(int i, int i2) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.5
            @Override // java.lang.Runnable
            public void run() {
                boolean isVisibility = BluetoothSettingsViewModel.this.isVisibility();
                Logs.d("xpbluetooth ModeChanged get visibility:" + isVisibility);
                BluetoothSettingsViewModel.this.mBluetoothVisibilityLiveData.postValue(Boolean.valueOf(isVisibility));
            }
        });
    }

    public boolean startScanList() {
        boolean startScanList = this.mXpBluetoothManger.startScanList();
        if (!startScanList) {
            this.mScanningLiveData.postValue(false);
        }
        return startScanList;
    }

    public ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mXpBluetoothManger.getBondedDevicesSorted();
    }

    public ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mXpBluetoothManger.getAvailableDevicesSorted();
    }

    public void registerNameAndButtonChangeListener() {
        this.mXpBluetoothManger.addNameChangeListener(this);
        this.mXpBluetoothManger.addButtonClickListener(this);
    }

    public void unregisterNameAndButtonChangeListener() {
        this.mXpBluetoothManger.removeNameChangeListener(this);
        this.mXpBluetoothManger.removeButtonClickListener(this);
    }

    public void registerBluetoothSourceListener() {
        this.mXpBluetoothManger.registerBluetoothSourceListener();
    }

    public void unRegisterBluetoothSourceListener() {
        this.mXpBluetoothManger.unRegisterBluetoothSourceListener();
    }

    public boolean isSourceInBluetooth() {
        return this.mXpBluetoothManger.isSourceInBluetooth();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onRefreshData() {
        if (ThreadUtils.isMainThread()) {
            this.mListLiveData.setValue(true);
        } else {
            this.mListLiveData.postValue(true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onRetPairedDevices() {
        this.mOnRetPairDeivesLiveData.postValue(true);
    }

    public void connectAndRetry(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mXpBluetoothManger.connectAndRetry(xpBluetoothDeviceInfo);
    }

    public boolean pair(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, String str) {
        return this.mXpBluetoothManger.pair(xpBluetoothDeviceInfo, str);
    }

    public boolean isBtPhoneConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isBtPhoneConnected(xpBluetoothDeviceInfo.getDeviceAddr());
    }

    public boolean isA2dpConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isA2dpConnected(xpBluetoothDeviceInfo.getDeviceAddr());
    }

    public boolean isBtPhoneConnected(String str) {
        return this.mXpBluetoothManger.isBtPhoneConnected(str);
    }

    public boolean isA2dpConnected(String str) {
        return this.mXpBluetoothManger.isA2dpConnected(str);
    }

    public boolean isBusy(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isBusy(xpBluetoothDeviceInfo);
    }

    public boolean isParing(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isParing(xpBluetoothDeviceInfo);
    }

    public boolean isConnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isConnecting(xpBluetoothDeviceInfo);
    }

    public boolean isDisconnecting(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isDisconnecting(xpBluetoothDeviceInfo);
    }

    public int getBondState(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.getBondState(xpBluetoothDeviceInfo);
    }

    public boolean isConnected(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        return this.mXpBluetoothManger.isConnected(xpBluetoothDeviceInfo);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger.NameChangeListener
    public void onNameChange(String str) {
        this.mBluetoothNameLiveData.postValue(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger.ButtonClickListener
    public void onButtonClick(boolean z) {
        for (IBluetoothViewCallback iBluetoothViewCallback : this.mIBluetoothViewCallback) {
            iBluetoothViewCallback.onBluetoothSwitching(z);
        }
    }

    public MutableLiveData<XpBluetoothDeviceInfo> getItemChangeLiveData() {
        return this.mItemChangeLiveData;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void notifyItemChanged(final XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.6
            @Override // java.lang.Runnable
            public void run() {
                BluetoothSettingsViewModel.this.mItemChangeLiveData.setValue(xpBluetoothDeviceInfo);
            }
        });
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothCallback
    public void onDeviceFounded(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mOnDeviceFoundLiveData.postValue(xpBluetoothDeviceInfo);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger.ConnectErrorListener
    public void notifyConnectError(final XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel.7
            @Override // java.lang.Runnable
            public void run() {
                BluetoothSettingsViewModel.this.notifyErrorCallback(xpBluetoothDeviceInfo.getDeviceAddr(), xpBluetoothDeviceInfo.getDeviceName(), 3);
            }
        });
    }

    public void registerErrorCallback(IBluetoothViewCallback iBluetoothViewCallback) {
        if (iBluetoothViewCallback != null) {
            this.mIBluetoothViewCallback.add(iBluetoothViewCallback);
        }
    }

    public void unregisterErrorCallback(IBluetoothViewCallback iBluetoothViewCallback) {
        if (iBluetoothViewCallback != null) {
            this.mIBluetoothViewCallback.remove(iBluetoothViewCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyErrorCallback(String str, String str2, int i) {
        if (i == 1) {
            for (IBluetoothViewCallback iBluetoothViewCallback : this.mIBluetoothViewCallback) {
                iBluetoothViewCallback.onConnectError(str, str2);
            }
        }
        if (i == 2) {
            for (IBluetoothViewCallback iBluetoothViewCallback2 : this.mIBluetoothViewCallback) {
                iBluetoothViewCallback2.onPairError(str, str2);
            }
        }
        if (i == 3) {
            for (IBluetoothViewCallback iBluetoothViewCallback3 : this.mIBluetoothViewCallback) {
                iBluetoothViewCallback3.onConnectOperateError(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        Logs.d("xpbluetooth vm onCleared");
    }

    public XpBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        return this.mXpBluetoothManger.getXpBluetoothDeviceInfo(str);
    }
}
