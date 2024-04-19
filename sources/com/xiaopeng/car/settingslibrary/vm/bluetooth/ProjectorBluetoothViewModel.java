package com.xiaopeng.car.settingslibrary.vm.bluetooth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
/* loaded from: classes.dex */
public class ProjectorBluetoothViewModel extends AndroidViewModel implements IProjectorBluetoothCallback {
    private static final String TAG = "ProjectorBluetoothViewModel";
    Application mApplication;
    MutableLiveData<Integer> mProjectorStatus;
    XpBluetoothManger mXpBluetoothManger;

    public ProjectorBluetoothViewModel(Application application) {
        super(application);
        this.mProjectorStatus = new MutableLiveData<>();
        this.mApplication = application;
        this.mXpBluetoothManger = XpBluetoothManger.getInstance();
    }

    public void setProjectorBluetoothDoing(boolean z) {
        this.mXpBluetoothManger.setBleWhiteListMode(z);
    }

    public void registerProjectorStateCallback() {
        this.mXpBluetoothManger.registerProjectorCallback(this);
    }

    public void unregisterProjectorStateCallback() {
        this.mXpBluetoothManger.unregisterProjectorCallback(this);
    }

    public boolean isProjectorAlreadyConnected() {
        return this.mXpBluetoothManger.isAlreadyConnectedWhiteList();
    }

    public void startScanProjector() {
        this.mXpBluetoothManger.forceStartScan();
    }

    public void reqBtDevicePairedDevices() {
        this.mXpBluetoothManger.reqBtDevicePairedDevices();
    }

    public void stopScanProjector() {
        this.mXpBluetoothManger.forceStopScan();
    }

    public boolean isProjectorFounded() {
        return this.mXpBluetoothManger.isProjectorFounded();
    }

    public MutableLiveData<Integer> getProjectStatus() {
        return this.mProjectorStatus;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback
    public void onProjectorBoundStatus(int i, int i2) {
        if (i2 == 11) {
            this.mProjectorStatus.postValue(5);
        }
        if (i == 11 && i2 == 12) {
            this.mProjectorStatus.postValue(6);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback
    public void onProjectorFounded() {
        this.mProjectorStatus.postValue(4);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback
    public void onProjectorConnectStatus(int i, int i2) {
        if (i == 0 && i2 == 1) {
            this.mProjectorStatus.postValue(7);
        }
        if ((i == 1 || i == 0) && i2 == 2) {
            this.mProjectorStatus.postValue(8);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IProjectorBluetoothCallback
    public void onProjectorConnectFailed(int i) {
        this.mProjectorStatus.postValue(Integer.valueOf(i));
    }

    public boolean isProjectorAlreadyBonded() {
        return this.mXpBluetoothManger.isAlreadyBoundedWhiteList();
    }

    public void stopPairOrConnectProjector() {
        this.mXpBluetoothManger.stopPairOrConnectProjector();
    }

    public void startPairOrConnectProjector() {
        this.mXpBluetoothManger.startPairOrConnectProjector();
    }

    public boolean isProjectorConnecting() {
        return this.mXpBluetoothManger.isConnectingWhiteList();
    }

    public boolean isProjectorBonding() {
        return this.mXpBluetoothManger.isBondingWhiteList();
    }
}
