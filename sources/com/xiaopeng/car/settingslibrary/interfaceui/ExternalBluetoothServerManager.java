package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SettingsExternalBluetoothBean;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.ExternalBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class ExternalBluetoothServerManager extends ServerBaseManager implements IBluetoothViewCallback {
    private static final String TAG = "ExternalBluetoothServerManager";
    private ExternalBluetoothViewModel mExternalBluetoothViewModel;

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(boolean z) {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final ExternalBluetoothServerManager instance = new ExternalBluetoothServerManager();

        private InnerFactory() {
        }
    }

    public static ExternalBluetoothServerManager get_instance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getBluetoothStatusLiveData().setValue(-1);
        getViewModel().getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ExternalBluetoothServerManager$2hX197Yh7zeVKrJrem05LZ2clJs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothServerManager.this.lambda$observeData$0$ExternalBluetoothServerManager((Integer) obj);
            }
        });
        getViewModel().getScanningLiveData().setValue(null);
        getViewModel().getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ExternalBluetoothServerManager$RRSPFRz-sAe2nI-UiRHe0Mk3wak
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothServerManager.this.lambda$observeData$1$ExternalBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getPairLiveData().setValue(null);
        getViewModel().getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ExternalBluetoothServerManager$SxWE2DJAzFdpTC0eFewpYvcFICE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothServerManager.this.lambda$observeData$2$ExternalBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getListLiveData().setValue(null);
        getViewModel().getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ExternalBluetoothServerManager$pPpqkBCS0fGL8oq3LWirpO-RxT8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothServerManager.this.lambda$observeData$3$ExternalBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getItemChangeLiveData().setValue(null);
        getViewModel().getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ExternalBluetoothServerManager$0xTxiSz6N8WYexjvVR2niUgI2Xg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothServerManager.this.lambda$observeData$4$ExternalBluetoothServerManager((ExternalBluetoothDeviceInfo) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$ExternalBluetoothServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("ExternalBluetoothServerManager onBluetoothStatusChanged " + num);
        externalBluetoothCallback("onBluetoothStatusChanged", String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$ExternalBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("ExternalBluetoothServerManager onScanningChanged " + bool);
        externalBluetoothCallback("onScanningChanged", String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$2$ExternalBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("ExternalBluetoothServerManager onPairedChanged ");
        externalBluetoothCallback("onPairedChanged", "");
    }

    public /* synthetic */ void lambda$observeData$3$ExternalBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("ExternalBluetoothServerManager onListChanged ");
        externalBluetoothCallback("onListChanged", "");
    }

    public /* synthetic */ void lambda$observeData$4$ExternalBluetoothServerManager(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        if (externalBluetoothDeviceInfo == null) {
            return;
        }
        debugLog("ExternalBluetoothServerManager onItemChanged ");
        externalBluetoothCallback("onItemChanged", JsonUtils.toJSONString(convertToBean(externalBluetoothDeviceInfo)));
    }

    private SettingsExternalBluetoothBean convertToBean(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        SettingsExternalBluetoothBean settingsExternalBluetoothBean = new SettingsExternalBluetoothBean();
        settingsExternalBluetoothBean.setAddress(externalBluetoothDeviceInfo.getDeviceAddr());
        settingsExternalBluetoothBean.setDeviceName(externalBluetoothDeviceInfo.getDeviceName());
        settingsExternalBluetoothBean.setConnectingBusy(externalBluetoothDeviceInfo.isConnectingBusy());
        settingsExternalBluetoothBean.setDisconnectingBusy(externalBluetoothDeviceInfo.isDisconnectingBusy());
        settingsExternalBluetoothBean.setConnectStatus(externalBluetoothDeviceInfo.getConnectStatus());
        return settingsExternalBluetoothBean;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized ExternalBluetoothViewModel getViewModel() {
        if (this.mExternalBluetoothViewModel == null) {
            this.mExternalBluetoothViewModel = new ExternalBluetoothViewModel(CarSettingsApp.getApp());
        }
        return this.mExternalBluetoothViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("ExternalBluetoothServerManager startVm");
        getViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("ExternalBluetoothServerManager stopVm");
        getViewModel().onStopVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectError(String str, String str2) {
        debugLog("ExternalBluetoothServerManager onConnectError address:" + str + " name:" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        externalBluetoothCallback("onConnectError", sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        debugLog("ExternalBluetoothServerManager onPairError address:" + str + " name:" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        externalBluetoothCallback("onPairError", sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        debugLog("ExternalBluetoothServerManager onConnectOperateError name:" + str);
        externalBluetoothCallback("onConnectOperateError", str);
    }

    public void registerErrorCallback() {
        Logs.d("ExternalBluetoothServerManager registerErrorCallback");
        getViewModel().registerErrorCallback(this);
    }

    public void unregisterErrorCallback() {
        Logs.d("ExternalBluetoothServerManager unregisterErrorCallback");
        getViewModel().unregisterErrorCallback(this);
    }

    public boolean isUsbEnable() {
        boolean isDeviceEnable = ExternalBluetoothManger.getInstance().isDeviceEnable();
        Logs.d("ExternalBluetoothServerManager isUsbEnable " + isDeviceEnable);
        return isDeviceEnable;
    }

    public boolean setUsbEnable(boolean z) {
        Logs.d("ExternalBluetoothServerManager setUsbEnable check:" + z);
        return ExternalBluetoothManger.getInstance().isEnable(z);
    }

    public boolean usbConnect(String str) {
        Logs.d("ExternalBluetoothServerManager usbConnect addr:" + str);
        return ExternalBluetoothManger.getInstance().connectDevice(str);
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getBondedDevicesSorted() {
        Logs.d("ExternalBluetoothServerManager getBondedDevicesSorted");
        return ExternalBluetoothManger.getInstance().getBondedDevicesSorted();
    }

    public ArrayList<ExternalBluetoothDeviceInfo> getAvailableDevicesSorted() {
        Logs.d("ExternalBluetoothServerManager getAvailableDevicesSorted");
        return ExternalBluetoothManger.getInstance().getAvailableDevicesSorted();
    }

    public boolean disUsbConnect(String str) {
        Logs.d("ExternalBluetoothServerManager disUsbConnect address:" + str);
        return ExternalBluetoothManger.getInstance().disConnectDevice(str);
    }

    public boolean isUsbDiscovering() {
        Logs.d("ExternalBluetoothServerManager isUsbDiscovering");
        return ExternalBluetoothManger.getInstance().isDiscovering();
    }

    public boolean isConnected(String str) {
        Logs.d("ExternalBluetoothServerManager isConnected");
        ExternalBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isConnected(bluetoothDeviceInfo);
    }

    public boolean usbSearch() {
        Logs.d("ExternalBluetoothServerManager usbSearch");
        return ExternalBluetoothManger.getInstance().startSearch();
    }

    public boolean stopScanning() {
        Logs.d("ExternalBluetoothServerManager stopScanning");
        return ExternalBluetoothManger.getInstance().stopSearch();
    }

    public ExternalBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        Logs.d("ExternalBluetoothServerManager getBluetoothDeviceInfo");
        return ExternalBluetoothManger.getInstance().getBluetoothDeviceInfo(str);
    }

    public boolean isConnecting(String str) {
        Logs.d("ExternalBluetoothServerManager isConnecting addr:" + str);
        ExternalBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isConnecting(bluetoothDeviceInfo);
    }

    public boolean isDisconnecting(String str) {
        Logs.d("ExternalBluetoothServerManager isDisconnecting addr:" + str);
        ExternalBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isDisconnecting(bluetoothDeviceInfo);
    }

    public boolean unpair(String str) {
        Logs.d("ExternalBluetoothServerManager unpair addr:" + str);
        ExternalBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return ExternalBluetoothManger.getInstance().unpair(bluetoothDeviceInfo);
    }

    public void setExternalTtsHeadsetOut(boolean z) {
        DataRepository.getInstance().setExternalTtsHeadsetOut(z);
    }

    public boolean getExternalTtsHeadsetOut() {
        return DataRepository.getInstance().getExternalTtsHeadsetOut();
    }

    public String getUsbConnectedDevice() {
        return ExternalBluetoothManger.getInstance().getConnectedDevice();
    }
}
