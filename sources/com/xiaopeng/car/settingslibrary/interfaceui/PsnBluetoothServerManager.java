package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SettingsPsnBluetoothBean;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class PsnBluetoothServerManager extends ServerBaseManager implements IBluetoothViewCallback {
    private static final String TAG = "PsnBluetoothServerManager";
    private PsnBluetoothViewModel mPsnBluetoothViewModel;

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(boolean z) {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final PsnBluetoothServerManager instance = new PsnBluetoothServerManager();

        private InnerFactory() {
        }
    }

    public static PsnBluetoothServerManager get_instance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getBluetoothStatusLiveData().setValue(-1);
        getViewModel().getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$PsnBluetoothServerManager$_G6gDcU_bWPCtM-91b42JsR8dLI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothServerManager.this.lambda$observeData$0$PsnBluetoothServerManager((Integer) obj);
            }
        });
        getViewModel().getScanningLiveData().setValue(null);
        getViewModel().getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$PsnBluetoothServerManager$e-l68ouH96TJLbcoqqFzYmluTAI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothServerManager.this.lambda$observeData$1$PsnBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getPairLiveData().setValue(null);
        getViewModel().getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$PsnBluetoothServerManager$5iHDluRnyNuU8JMXliLmrYpKk28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothServerManager.this.lambda$observeData$2$PsnBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getListLiveData().setValue(null);
        getViewModel().getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$PsnBluetoothServerManager$RsnvdXUGGlWTbhEPnZiUEQ_06S4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothServerManager.this.lambda$observeData$3$PsnBluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getItemChangeLiveData().setValue(null);
        getViewModel().getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$PsnBluetoothServerManager$Wy9QBgWoMgCDmM-qtJDko9G7CJA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothServerManager.this.lambda$observeData$4$PsnBluetoothServerManager((PsnBluetoothDeviceInfo) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$PsnBluetoothServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("PsnBluetoothServerManager onBluetoothStatusChanged " + num);
        psnBluetoothCallback("onBluetoothStatusChanged", String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$PsnBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("PsnBluetoothServerManager onScanningChanged " + bool);
        psnBluetoothCallback("onScanningChanged", String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$2$PsnBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("PsnBluetoothServerManager onPairedChanged ");
        psnBluetoothCallback("onPairedChanged", "");
    }

    public /* synthetic */ void lambda$observeData$3$PsnBluetoothServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("PsnBluetoothServerManager onListChanged ");
        psnBluetoothCallback("onListChanged", "");
    }

    public /* synthetic */ void lambda$observeData$4$PsnBluetoothServerManager(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        if (psnBluetoothDeviceInfo == null) {
            return;
        }
        debugLog("PsnBluetoothServerManager onItemChanged ");
        psnBluetoothCallback("onItemChanged", JsonUtils.toJSONString(convertToBean(psnBluetoothDeviceInfo)));
    }

    private SettingsPsnBluetoothBean convertToBean(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        SettingsPsnBluetoothBean settingsPsnBluetoothBean = new SettingsPsnBluetoothBean();
        settingsPsnBluetoothBean.setAddress(psnBluetoothDeviceInfo.getDeviceAddr());
        settingsPsnBluetoothBean.setDeviceName(psnBluetoothDeviceInfo.getDeviceName());
        settingsPsnBluetoothBean.setConnectingBusy(psnBluetoothDeviceInfo.isConnectingBusy());
        settingsPsnBluetoothBean.setDisconnectingBusy(psnBluetoothDeviceInfo.isDisconnectingBusy());
        settingsPsnBluetoothBean.setConnectStatus(psnBluetoothDeviceInfo.getConnectStatus());
        return settingsPsnBluetoothBean;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized PsnBluetoothViewModel getViewModel() {
        if (this.mPsnBluetoothViewModel == null) {
            this.mPsnBluetoothViewModel = new PsnBluetoothViewModel(CarSettingsApp.getApp());
        }
        return this.mPsnBluetoothViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("PsnBluetoothServerManager startVm");
        getViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("PsnBluetoothServerManager stopVm");
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
        debugLog("PsnBluetoothServerManager onConnectError address:" + str + " name:" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        psnBluetoothCallback("onConnectError", sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        debugLog("PsnBluetoothServerManager onPairError address:" + str + " name:" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        psnBluetoothCallback("onPairError", sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        debugLog("PsnBluetoothServerManager onConnectOperateError name:" + str);
        psnBluetoothCallback("onConnectOperateError", str);
    }

    public void registerErrorCallback() {
        Logs.d("PsnBluetoothServerManager registerErrorCallback");
        getViewModel().registerErrorCallback(this);
    }

    public void unregisterErrorCallback() {
        Logs.d("PsnBluetoothServerManager unregisterErrorCallback");
        getViewModel().unregisterErrorCallback(this);
    }

    public boolean isUsbEnable() {
        boolean isUsbEnable = PsnBluetoothManger.getInstance().isUsbEnable();
        Logs.d("PsnBluetoothServerManager isUsbEnable " + isUsbEnable);
        return isUsbEnable;
    }

    public boolean setUsbEnable(boolean z) {
        Logs.d("PsnBluetoothServerManager setUsbEnable check:" + z);
        return PsnBluetoothManger.getInstance().setUsbBluetoothEnabled(z);
    }

    public boolean usbConnect(String str) {
        Logs.d("PsnBluetoothServerManager usbConnect addr:" + str);
        return PsnBluetoothManger.getInstance().usbConnect(str);
    }

    public ArrayList<PsnBluetoothDeviceInfo> getBondedDevicesSorted() {
        Logs.d("PsnBluetoothServerManager getBondedDevicesSorted");
        return PsnBluetoothManger.getInstance().getBondedDevicesSorted();
    }

    public ArrayList<PsnBluetoothDeviceInfo> getAvailableDevicesSorted() {
        Logs.d("PsnBluetoothServerManager getAvailableDevicesSorted");
        return PsnBluetoothManger.getInstance().getAvailableDevicesSorted();
    }

    public boolean disUsbConnect(String str) {
        Logs.d("PsnBluetoothServerManager disUsbConnect address:" + str);
        return PsnBluetoothManger.getInstance().disUsbConnect(str);
    }

    public boolean isUsbDiscovering() {
        Logs.d("PsnBluetoothServerManager isUsbDiscovering");
        return PsnBluetoothManger.getInstance().isUsbCurrentScanning();
    }

    public boolean isConnected(String str) {
        Logs.d("PsnBluetoothServerManager isConnected");
        PsnBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isConnected(bluetoothDeviceInfo);
    }

    public boolean usbSearch() {
        Logs.d("PsnBluetoothServerManager usbSearch");
        return PsnBluetoothManger.getInstance().usbSearch();
    }

    public boolean stopScanning() {
        Logs.d("PsnBluetoothServerManager stopScanning");
        return PsnBluetoothManger.getInstance().usbSearchStop();
    }

    public String getUsbBluetoothName() {
        Logs.d("PsnBluetoothServerManager getUsbBluetoothName");
        return PsnBluetoothManger.getInstance().getUsbBluetoothName();
    }

    public PsnBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        Logs.d("PsnBluetoothServerManager getBluetoothDeviceInfo");
        return PsnBluetoothManger.getInstance().getBluetoothDeviceInfo(str);
    }

    public boolean isConnecting(String str) {
        Logs.d("PsnBluetoothServerManager isConnecting addr:" + str);
        PsnBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isConnecting(bluetoothDeviceInfo);
    }

    public boolean isDisconnecting(String str) {
        Logs.d("PsnBluetoothServerManager isDisconnecting addr:" + str);
        PsnBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return getViewModel().isDisconnecting(bluetoothDeviceInfo);
    }

    public boolean unpair(String str) {
        Logs.d("PsnBluetoothServerManager unpair addr:" + str);
        PsnBluetoothDeviceInfo bluetoothDeviceInfo = getBluetoothDeviceInfo(str);
        if (bluetoothDeviceInfo == null) {
            return false;
        }
        return PsnBluetoothManger.getInstance().usbUnpair(bluetoothDeviceInfo);
    }

    public String getUsbAddr() {
        Logs.d("PsnBluetoothServerManager getUsbAddr");
        return PsnBluetoothManger.getInstance().getUsbAddr();
    }

    public void setPsnTtsHeadsetOut(boolean z) {
        DataRepository.getInstance().setPsnTtsHeadsetOut(z);
    }

    public boolean getPsnTtsHeadsetOut() {
        return DataRepository.getInstance().getPsnTtsHeadsetOut();
    }

    public String getUsbConnectedDevice() {
        return PsnBluetoothManger.getInstance().getUsbConnectedDevice();
    }
}
