package com.xiaopeng.car.settingslibrary.vm.bluetooth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class PsnBluetoothViewModel extends AndroidViewModel implements IXpPsnBluetoothCallback, PsnBluetoothManger.NFServiceConnectedCallback, PsnNFBluetoothManager.ItemChangeListener {
    private static final int CONNECT_ERROR = 1;
    private static final int CONNECT_OPERATE_ERROR = 3;
    private static final int PAIR_ERROR = 2;
    private MutableLiveData<Integer> mBluetoothStatusLiveData;
    List<IBluetoothViewCallback> mIBluetoothViewCallback;
    private MutableLiveData<PsnBluetoothDeviceInfo> mItemChangeLiveData;
    private MutableLiveData<Boolean> mListLiveData;
    private MutableLiveData<Boolean> mPairLiveData;
    PsnBluetoothManger mPsnBluetoothManger;
    private MutableLiveData<Boolean> mScanningLiveData;
    private MutableLiveData<Boolean> mShowTtsLiveData;

    public PsnBluetoothViewModel(Application application) {
        super(application);
        this.mIBluetoothViewCallback = new CopyOnWriteArrayList();
        this.mBluetoothStatusLiveData = new MutableLiveData<>();
        this.mScanningLiveData = new MutableLiveData<>();
        this.mShowTtsLiveData = new MutableLiveData<>();
        this.mPairLiveData = new MutableLiveData<>();
        this.mListLiveData = new MutableLiveData<>();
        this.mItemChangeLiveData = new MutableLiveData<>();
        this.mPsnBluetoothManger = PsnBluetoothManger.getInstance();
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

    public MutableLiveData<PsnBluetoothDeviceInfo> getItemChangeLiveData() {
        return this.mItemChangeLiveData;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback
    public void onUsbBluetoothStateChanged(int i) {
        this.mBluetoothStatusLiveData.postValue(Integer.valueOf(i));
        if (i == 12) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.-$$Lambda$PsnBluetoothViewModel$OY-6L6KEKHC5lglefL3j4nBPjik
                @Override // java.lang.Runnable
                public final void run() {
                    PsnBluetoothViewModel.this.lambda$onUsbBluetoothStateChanged$0$PsnBluetoothViewModel();
                }
            }, 2000L);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback
    public void onUsbScanningStateChanged(boolean z) {
        this.mScanningLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback
    public void onUsbDeviceBondStateChanged(final String str, final String str2, int i, int i2) {
        if (i2 == 10 && i == 11) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel.1
                @Override // java.lang.Runnable
                public void run() {
                    PsnBluetoothViewModel.this.notifyErrorCallback(str, str2, 2);
                }
            });
        } else if (i2 == 12) {
            this.mPairLiveData.postValue(true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback
    public void onUsbConnectionStateChanged(final String str, final String str2, int i, int i2) {
        if (i == 1 && i2 == 0) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel.2
                @Override // java.lang.Runnable
                public void run() {
                    PsnBluetoothViewModel.this.notifyErrorCallback(str, str2, 1);
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

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpPsnBluetoothCallback
    public void onRefreshData() {
        this.mListLiveData.postValue(true);
    }

    public boolean isUsbEnable() {
        return this.mPsnBluetoothManger.isUsbEnable();
    }

    public boolean setUsbEnable(boolean z) {
        return this.mPsnBluetoothManger.setUsbBluetoothEnabled(z);
    }

    public boolean usbConnect(String str) {
        return this.mPsnBluetoothManger.usbConnect(str);
    }

    public ArrayList<PsnBluetoothDeviceInfo> getBondedDevicesSorted() {
        return this.mPsnBluetoothManger.getBondedDevicesSorted();
    }

    public ArrayList<PsnBluetoothDeviceInfo> getAvailableDevicesSorted() {
        return this.mPsnBluetoothManger.getAvailableDevicesSorted();
    }

    public boolean disUsbConnect(String str) {
        return this.mPsnBluetoothManger.disUsbConnect(str);
    }

    public void onStartVM() {
        this.mPsnBluetoothManger.registerServiceConnectCallback(this);
        this.mPsnBluetoothManger.registerPsnNFBluetoothCallback();
        this.mPsnBluetoothManger.registerCallback(this);
        this.mPsnBluetoothManger.addItemChangeListener(this);
    }

    public void onStopVM() {
        this.mPsnBluetoothManger.unregisterServiceConnectCallback(this);
        this.mPsnBluetoothManger.unregisterPsnNFBluetoothCallback();
        this.mPsnBluetoothManger.unregisterCallback(this);
        this.mPsnBluetoothManger.removeItemChangeListener(this);
        if (isUsbEnable()) {
            usbSearchStop();
        }
    }

    public boolean usbSearchStop() {
        return this.mPsnBluetoothManger.usbSearchStop();
    }

    public boolean isUsbDiscovering() {
        return this.mPsnBluetoothManger.isUsbCurrentScanning();
    }

    public boolean isConnected(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return psnBluetoothDeviceInfo.getConnectStatus() == 2;
    }

    /* renamed from: usbSearch */
    public boolean lambda$onUsbBluetoothStateChanged$0$PsnBluetoothViewModel() {
        return this.mPsnBluetoothManger.usbSearch();
    }

    public String getUsbBluetoothName() {
        return this.mPsnBluetoothManger.getUsbBluetoothName();
    }

    public PsnBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        return this.mPsnBluetoothManger.getBluetoothDeviceInfo(str);
    }

    public boolean isConnecting(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return psnBluetoothDeviceInfo.getConnectStatus() == 1;
    }

    public boolean isDisconnecting(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return psnBluetoothDeviceInfo.getConnectStatus() == 3;
    }

    public boolean unpair(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return this.mPsnBluetoothManger.usbUnpair(psnBluetoothDeviceInfo);
    }

    public boolean connectUsb(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        return this.mPsnBluetoothManger.usbConnect(psnBluetoothDeviceInfo);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger.NFServiceConnectedCallback
    public void connectCompleted() {
        this.mBluetoothStatusLiveData.postValue(Integer.valueOf(isUsbEnable() ? 12 : 10));
        if (isUsbEnable()) {
            lambda$onUsbBluetoothStateChanged$0$PsnBluetoothViewModel();
            onRefreshData();
        }
    }

    public String getUsbAddr() {
        return this.mPsnBluetoothManger.getUsbAddr();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.nf.PsnNFBluetoothManager.ItemChangeListener
    public void notifyItemChanged(final PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel.3
            @Override // java.lang.Runnable
            public void run() {
                PsnBluetoothViewModel.this.mItemChangeLiveData.setValue(psnBluetoothDeviceInfo);
            }
        });
    }

    public void setPsnTtsHeadsetOut(boolean z) {
        DataRepository.getInstance().setPsnTtsHeadsetOut(z);
    }

    public boolean getPsnTtsHeadsetOut() {
        return DataRepository.getInstance().getPsnTtsHeadsetOut();
    }

    public void showTtsSwitch(boolean z) {
        this.mShowTtsLiveData.postValue(Boolean.valueOf(z));
    }
}
