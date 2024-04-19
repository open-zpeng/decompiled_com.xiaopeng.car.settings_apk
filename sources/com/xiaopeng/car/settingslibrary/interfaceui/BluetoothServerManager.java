package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SettingsBluetoothBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.BluetoothBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class BluetoothServerManager extends ServerBaseManager implements IBluetoothViewCallback {
    private static final String TAG = "BluetoothServerManager";
    private BluetoothSettingsViewModel mBluetoothSettingsViewModel;

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(boolean z) {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final BluetoothServerManager instance = new BluetoothServerManager();

        private InnerFactory() {
        }
    }

    public static BluetoothServerManager getInstance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getBluetoothStatusLiveData().setValue(-1);
        getViewModel().getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$96IERjQ5gOLfzywe50WSxNYPVvU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$0$BluetoothServerManager((Integer) obj);
            }
        });
        getViewModel().getListLiveData().setValue(null);
        getViewModel().getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$OBXis_asXO718qbEfr47uNwfqjc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$1$BluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getOnRetPairDeivesLiveData().setValue(null);
        getViewModel().getOnRetPairDeivesLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$_9gy7mBHWOLDCoxmXQwsqTAsyXY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$2$BluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getBluetoothNameLiveData().setValue(null);
        getViewModel().getBluetoothNameLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$HQMPKWvitX3dLX0o8r531YR1fkU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$3$BluetoothServerManager((String) obj);
            }
        });
        getViewModel().getBluetoothVisibilityLiveData().setValue(null);
        getViewModel().getBluetoothVisibilityLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$Iq870195siTRjHpMOkGDoAy7cyc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$4$BluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getPairLiveData().setValue(null);
        getViewModel().getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$6TIDd-mxd-pvoF26O-QdPRW-Cu0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$5$BluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getItemChangeLiveData().setValue(null);
        getViewModel().getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$_wMt0-Z5LB-XH4boXnuGW_u5vFA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$6$BluetoothServerManager((XpBluetoothDeviceInfo) obj);
            }
        });
        getViewModel().getOnDeviceFoundLiveData().setValue(null);
        getViewModel().getOnDeviceFoundLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$zJYmmeuG_cTnvCl6TDD0cEtWPgk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$7$BluetoothServerManager((XpBluetoothDeviceInfo) obj);
            }
        });
        getViewModel().getOnDeviceBondChangedLiveData().setValue(null);
        getViewModel().getOnDeviceBondChangedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$MZIwx2cUVtDk-h8fT24ZJR5nYKE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$8$BluetoothServerManager((XpBluetoothDeviceInfo) obj);
            }
        });
        getViewModel().getRealScanningLiveData().setValue(null);
        getViewModel().getRealScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$UGTPQYkE6gbtjMn09fqVDH-a3fc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$9$BluetoothServerManager((Boolean) obj);
            }
        });
        getViewModel().getBluetoothConnectedCompleted().setValue(null);
        getViewModel().getBluetoothConnectedCompleted().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$BluetoothServerManager$r5U2YLR6I9XU2uHXFic1PxrTmaw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothServerManager.this.lambda$observeData$10$BluetoothServerManager((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$BluetoothServerManager(Integer num) {
        debugLog("BluetoothServerManager onBluetoothStateChanged state:" + num);
        if (num.intValue() == -1) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_BLUETOOTH_STATE_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onRefreshData " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_REFRESH_DATA, "");
    }

    public /* synthetic */ void lambda$observeData$2$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onRetPairDevices " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_RET_PAIRED_DEVICES, "");
    }

    public /* synthetic */ void lambda$observeData$3$BluetoothServerManager(String str) {
        debugLog("BluetoothServerManager onNameChange name:" + str);
        if (str == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_NAME_CHANGE, str);
    }

    public /* synthetic */ void lambda$observeData$4$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onDiscoverableModeChanged " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_DISCOVERABLE_MODE_CHANGED, "");
    }

    public /* synthetic */ void lambda$observeData$5$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onDeviceBondStateChanged " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_DEVICE_BOND_STATE_CHANGED, "");
    }

    public /* synthetic */ void lambda$observeData$6$BluetoothServerManager(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        debugLog("BluetoothServerManager notifyItemChanged " + xpBluetoothDeviceInfo);
        bluetoothCallback(InterfaceConstant.ON_ITEM_CHANGED, JsonUtils.toJSONString(convertToBean(xpBluetoothDeviceInfo)));
    }

    public /* synthetic */ void lambda$observeData$7$BluetoothServerManager(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        debugLog("BluetoothServerManager onDeviceFound " + xpBluetoothDeviceInfo);
        bluetoothCallback(InterfaceConstant.ON_DEVICE_FOUND, JsonUtils.toJSONString(convertToBean(xpBluetoothDeviceInfo)));
    }

    public /* synthetic */ void lambda$observeData$8$BluetoothServerManager(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        debugLog("BluetoothServerManager onDeviceBound " + xpBluetoothDeviceInfo);
        bluetoothCallback(InterfaceConstant.ON_DEVICE_BOUND, JsonUtils.toJSONString(convertToBean(xpBluetoothDeviceInfo)));
    }

    public /* synthetic */ void lambda$observeData$9$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onScanningStateChanged " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_SCANNING_STATE_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$10$BluetoothServerManager(Boolean bool) {
        debugLog("BluetoothServerManager onBluetoothNFConnectedCompleted " + bool);
        if (bool == null) {
            return;
        }
        bluetoothCallback(InterfaceConstant.ON_NF_CONNECTED_COMPLETED, "");
    }

    private SettingsBluetoothBean convertToBean(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        SettingsBluetoothBean settingsBluetoothBean = new SettingsBluetoothBean();
        if (xpBluetoothDeviceInfo != null) {
            settingsBluetoothBean.setAddress(xpBluetoothDeviceInfo.getDeviceAddr());
            settingsBluetoothBean.setName(xpBluetoothDeviceInfo.getDeviceName());
            settingsBluetoothBean.setConnectingBusy(xpBluetoothDeviceInfo.getAbsBluetoothDevice().isConnecting());
            settingsBluetoothBean.setDisconnectingBusy(xpBluetoothDeviceInfo.getAbsBluetoothDevice().isDisConnecting());
            settingsBluetoothBean.setParingBusy(xpBluetoothDeviceInfo.getAbsBluetoothDevice().isParing());
            settingsBluetoothBean.setType(xpBluetoothDeviceInfo.getCategory());
            boolean z = true;
            settingsBluetoothBean.setPair(xpBluetoothDeviceInfo.getPairState() == 12);
            if (!xpBluetoothDeviceInfo.isA2dpConnected() && !xpBluetoothDeviceInfo.isHfpConnected() && !xpBluetoothDeviceInfo.isHidConnected()) {
                z = false;
            }
            settingsBluetoothBean.setConnect(z);
        }
        return settingsBluetoothBean;
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectError(String str, String str2) {
        debugLog("BluetoothServerManager onConnectError address:" + str + " name:" + str2);
        bluetoothCallback("onConnectError", JsonUtils.toJSONString(new BluetoothBean(str, str2)));
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        debugLog("BluetoothServerManager onPairError address:" + str + " name:" + str2);
        bluetoothCallback("onPairError", JsonUtils.toJSONString(new BluetoothBean(str, str2)));
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        debugLog("BluetoothServerManager onConnectOperateError name:" + str);
        bluetoothCallback("onConnectOperateError", str);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized BluetoothSettingsViewModel getViewModel() {
        if (this.mBluetoothSettingsViewModel == null) {
            this.mBluetoothSettingsViewModel = new BluetoothSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mBluetoothSettingsViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        debugLog("BluetoothServerManager startVm ");
        getViewModel().registerErrorCallback(this);
        getViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        debugLog("BluetoothServerManager stopVm ");
        getViewModel().unregisterErrorCallback(this);
        getViewModel().onStopVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
        debugLog("BluetoothServerManager enterScene ");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        debugLog("BluetoothServerManager exitScene ");
        super.exitScene();
    }

    public boolean changeBTStatus(boolean z) {
        log("BluetoothServerManager changeBTStatus:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.BLUETOOTH_PAGE_ID, "B001", z);
        return getViewModel().changeBTStatus(z);
    }

    public boolean getEnable() {
        boolean isEnable = getViewModel().isEnable();
        log("BluetoothServerManager getEnable " + isEnable);
        return isEnable;
    }

    public void setVisibility(boolean z) {
        log("BluetoothServerManager setVisibility:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.BLUETOOTH_PAGE_ID, "B002", z);
        getViewModel().setVisibility(z);
    }

    public boolean getVisibility() {
        log("BluetoothServerManager getVisibility");
        return getViewModel().isVisibility();
    }

    public String getName() {
        log("BluetoothServerManager getName");
        return getViewModel().getName();
    }

    public void setName(String str) {
        log("BluetoothServerManager setName name:" + str);
        getViewModel().setName(str);
    }

    public void connectAndRetry(String str) {
        log("BluetoothServerManager connectAndRetry address:" + str);
        getViewModel().connectAndRetry(getBluetoothDeviceInfo(str));
    }

    public boolean pair(String str) {
        log("BluetoothServerManager pair address:" + str);
        return getViewModel().pair(getBluetoothDeviceInfo(str), str);
    }

    public boolean disConnect(String str) {
        log("BluetoothServerManager disConnect address:" + str);
        return getViewModel().disConnect(true, getBluetoothDeviceInfo(str));
    }

    public void unpair(String str) {
        log("BluetoothServerManager unpair address:" + str);
        getViewModel().unpair(getBluetoothDeviceInfo(str));
    }

    public XpBluetoothDeviceInfo getBluetoothDeviceInfo(String str) {
        log("BluetoothServerManager getBluetoothDeviceInfo address:" + str);
        return getViewModel().getBluetoothDeviceInfo(str);
    }

    public boolean isConnected(String str) {
        log("BluetoothServerManager isConnected address:" + str);
        return getViewModel().isConnected(getBluetoothDeviceInfo(str));
    }

    public boolean startScanList() {
        boolean startScanList = getViewModel().startScanList();
        log("BluetoothServerManager startScanList " + startScanList);
        return startScanList;
    }

    public ArrayList<XpBluetoothDeviceInfo> getBondedDevicesSorted() {
        log("BluetoothServerManager getBondedDevicesSorted");
        return getViewModel().getBondedDevicesSorted();
    }

    public ArrayList<XpBluetoothDeviceInfo> getAvailableDevicesSorted() {
        log("BluetoothServerManager getAvailableDevicesSorted");
        return getViewModel().getAvailableDevicesSorted();
    }

    public boolean isConnecting(String str) {
        log("BluetoothServerManager isConnecting:" + str);
        return getViewModel().isConnecting(getBluetoothDeviceInfo(str));
    }

    public boolean isDisconnecting(String str) {
        log("BluetoothServerManager isDisconnecting:" + str);
        return getViewModel().isDisconnecting(getBluetoothDeviceInfo(str));
    }

    public boolean isParing(String str) {
        log("BluetoothServerManager isParing:" + str);
        return getViewModel().isParing(getBluetoothDeviceInfo(str));
    }

    public void stopScanning() {
        log("BluetoothServerManager stopScanning");
        getViewModel().stopScanning();
    }

    public boolean isScanning() {
        log("BluetoothServerManager isScanning");
        return getViewModel().isCurrentScanning();
    }

    public int getBondState(String str) {
        log("BluetoothServerManager getBondState address:" + str);
        return getViewModel().getBondState(getBluetoothDeviceInfo(str));
    }

    public boolean isBusy(String str) {
        log("BluetoothServerManager isBusy address:" + str);
        return getViewModel().isBusy(getBluetoothDeviceInfo(str));
    }

    public boolean isSourceInBluetooth() {
        boolean isSourceInBluetooth = getViewModel().isSourceInBluetooth();
        log("BluetoothServerManager isSourceInBluetooth " + isSourceInBluetooth);
        return isSourceInBluetooth;
    }

    public boolean isBtPhoneConnected(String str) {
        return getViewModel().isBtPhoneConnected(str);
    }

    public boolean isA2dpConnected(String str) {
        return getViewModel().isA2dpConnected(str);
    }

    public void popupBluetoothNameEdit() {
        Utils.popupBluetoothNameEdit();
    }
}
