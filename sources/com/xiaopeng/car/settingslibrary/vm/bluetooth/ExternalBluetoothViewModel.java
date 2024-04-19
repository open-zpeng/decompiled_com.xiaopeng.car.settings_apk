package com.xiaopeng.car.settingslibrary.vm.bluetooth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.ExternalBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBaseBluetoothManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class ExternalBluetoothViewModel extends AndroidViewModel implements IXpExternalBluetoothCallback, ExternalBluetoothManger.NFServiceConnectedCallback, ExternalBaseBluetoothManager.ItemChangeListener {
    private static final int CONNECT_ERROR = 1;
    private static final int CONNECT_OPERATE_ERROR = 3;
    private static final int PAIR_ERROR = 2;
    private MutableLiveData<Integer> mBluetoothStatusLiveData;
    ExternalBluetoothManger mExternalBluetoothManger;
    List<IBluetoothViewCallback> mIBluetoothViewCallback;
    private MutableLiveData<ExternalBluetoothDeviceInfo> mItemChangeLiveData;
    private MutableLiveData<Boolean> mListLiveData;
    private MutableLiveData<Boolean> mPairLiveData;
    private MutableLiveData<Boolean> mScanningLiveData;
    private MutableLiveData<Boolean> mShowTtsLiveData;

    public ExternalBluetoothViewModel(Application application) {
        super(application);
        this.mIBluetoothViewCallback = new CopyOnWriteArrayList();
        this.mBluetoothStatusLiveData = new MutableLiveData<>();
        this.mScanningLiveData = new MutableLiveData<>();
        this.mShowTtsLiveData = new MutableLiveData<>();
        this.mPairLiveData = new MutableLiveData<>();
        this.mListLiveData = new MutableLiveData<>();
        this.mItemChangeLiveData = new MutableLiveData<>();
        this.mExternalBluetoothManger = ExternalBluetoothManger.getInstance();
    }

    public MutableLiveData<Integer> getBluetoothStatusLiveData() {
        return this.mBluetoothStatusLiveData;
    }

    public MutableLiveData<Boolean> getShowTtsLiveData() {
        return this.mShowTtsLiveData;
    }

    public MutableLiveData<Boolean> getScanningLiveData() {
        return this.mScanningLiveData;
    }

    public MutableLiveData<Boolean> getPairLiveData() {
        return this.mPairLiveData;
    }

    public MutableLiveData<Boolean> getListLiveData() {
        return this.mListLiveData;
    }

    public MutableLiveData<ExternalBluetoothDeviceInfo> getItemChangeLiveData() {
        return this.mItemChangeLiveData;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback
    public void onBluetoothStateChanged(int i) {
        this.mBluetoothStatusLiveData.postValue(Integer.valueOf(i));
        if (i == 12) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.-$$Lambda$ExternalBluetoothViewModel$LWlU954YjK_F5z53P6ys2wBrr70
                @Override // java.lang.Runnable
                public final void run() {
                    ExternalBluetoothViewModel.this.lambda$onBluetoothStateChanged$0$ExternalBluetoothViewModel();
                }
            }, 2000L);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback
    public void onScanningStateChanged(boolean z) {
        this.mScanningLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback
    public void onDeviceBondStateChanged(final String str, final String str2, int i, int i2) {
        if (i2 == 10 && i == 11) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel.1
                @Override // java.lang.Runnable
                public void run() {
                    ExternalBluetoothViewModel.this.notifyErrorCallback(str, str2, 2);
                }
            });
        } else if (i2 == 12) {
            this.mPairLiveData.postValue(true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback
    public void onConnectionStateChanged(final String str, final String str2, int i, int i2) {
        if (i == 1 && i2 == 0) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel.2
                @Override // java.lang.Runnable
                public void run() {
                    ExternalBluetoothViewModel.this.notifyErrorCallback(str, str2, 1);
                }
            });
        }
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

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.IXpExternalBluetoothCallback
    public void onRefreshData() {
        this.mListLiveData.postValue(true);
    }

    public boolean isUsbEnable() {
        return this.mExternalBluetoothManger.isDeviceEnable();
    }

    public boolean setUsbEnable(boolean z) {
        return this.mExternalBluetoothManger.isEnable(z);
    }

    public boolean usbConnect(String str) {
        return this.mExternalBluetoothManger.connectDevice(str);
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mExternalBluetoothManger.getBondedDevicesSorted();
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mExternalBluetoothManger.getAvailableDevicesSorted();
    }

    public boolean disUsbConnect(String str) {
        return this.mExternalBluetoothManger.disConnectDevice(str);
    }

    public void onStartVM() {
        this.mExternalBluetoothManger.registerServiceConnectCallback(this);
        this.mExternalBluetoothManger.registerExternalBluetoothCallback();
        this.mExternalBluetoothManger.registerCallback(this);
        this.mExternalBluetoothManger.addItemChangeListener(this);
    }

    public void onStopVM() {
        this.mExternalBluetoothManger.unregisterServiceConnectCallback(this);
        this.mExternalBluetoothManger.unregisterExternalBluetoothCallback();
        this.mExternalBluetoothManger.unregisterCallback(this);
        this.mExternalBluetoothManger.removeItemChangeListener(this);
        if (isUsbEnable()) {
            usbSearchStop();
        }
    }

    public boolean usbSearchStop() {
        return this.mExternalBluetoothManger.stopSearch();
    }

    public boolean isUsbDiscovering() {
        return this.mExternalBluetoothManger.isDiscovering();
    }

    public boolean isConnected(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return externalBluetoothDeviceInfo.getConnectStatus() == 2;
    }

    /* renamed from: usbSearch */
    public boolean lambda$onBluetoothStateChanged$0$ExternalBluetoothViewModel() {
        return this.mExternalBluetoothManger.startSearch();
    }

    public ExternalBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        return this.mExternalBluetoothManger.getBluetoothDeviceInfo(str);
    }

    public boolean isConnecting(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return externalBluetoothDeviceInfo.getConnectStatus() == 1;
    }

    public boolean isDisconnecting(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return externalBluetoothDeviceInfo.getConnectStatus() == 3;
    }

    public boolean unpair(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return this.mExternalBluetoothManger.unpair(externalBluetoothDeviceInfo);
    }

    public boolean connectUsb(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        return this.mExternalBluetoothManger.connectDevice(externalBluetoothDeviceInfo);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.ExternalBluetoothManger.NFServiceConnectedCallback
    public void connectCompleted() {
        this.mBluetoothStatusLiveData.postValue(Integer.valueOf(isUsbEnable() ? 12 : 10));
        if (isUsbEnable()) {
            lambda$onBluetoothStateChanged$0$ExternalBluetoothViewModel();
            onRefreshData();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBaseBluetoothManager.ItemChangeListener
    public void notifyItemChanged(final ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel.3
            @Override // java.lang.Runnable
            public void run() {
                ExternalBluetoothViewModel.this.mItemChangeLiveData.setValue(externalBluetoothDeviceInfo);
            }
        });
    }

    public void setExternalTtsHeadsetOut(boolean z) {
        DataRepository.getInstance().setExternalTtsHeadsetOut(z);
    }

    public boolean getExternalTtsHeadsetOut() {
        return DataRepository.getInstance().getExternalTtsHeadsetOut();
    }

    public void showTtsSwitch(boolean z) {
        this.mShowTtsLiveData.postValue(Boolean.valueOf(z));
    }
}
