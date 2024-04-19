package com.xiaopeng.btservice.bluetooth;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.nforetek.bt.aidl.UiCallbackBluetooth;
import com.nforetek.bt.aidl.UiCommand;
import com.xiaopeng.btservice.base.AbsBluetoothControlCallback;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
/* loaded from: classes.dex */
public class BluetoothControl extends AbsControl {
    private static final String TAG = "BluetoothControl";
    private AbsBluetoothControlCallback mCallback;
    private UiCallbackBluetooth mCallbackBluetooth = new UiCallbackBluetooth.Stub() { // from class: com.xiaopeng.btservice.bluetooth.BluetoothControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onBluetoothServiceReady() {
            Log.d(BluetoothControl.TAG, "onBluetoothServiceReady");
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onBluetoothServiceReady();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onAdapterStateChanged(int i, int i2) {
            Log.d(BluetoothControl.TAG, "onAdapterStateChanged newState:" + i2);
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onAdapterStateChanged(i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoverableModeChanged(int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onAdapterDiscoverableModeChanged(i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryStarted() {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onAdapterDiscoveryStarted();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryFinished() {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onAdapterDiscoveryFinished();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void retPairedDevices(int i, String[] strArr, String[] strArr2, int[] iArr, byte[] bArr) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.retPairedDevices(i, strArr, strArr2, iArr, bArr);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onDeviceFound(String str, String str2, byte b) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onDeviceFound(str, str2, b);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onDeviceBondStateChanged(String str, String str2, int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onDeviceBondStateChanged(str, str2, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onDeviceUuidsUpdated(String str, String str2, int i) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onDeviceUuidsUpdated(str, str2, i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onLocalAdapterNameChanged(String str) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onLocalAdapterNameChanged(str);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onDeviceOutOfRange(String str) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onDeviceOutOfRange(str);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onDeviceAclDisconnected(String str) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onDeviceAclDisconnected(str);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onBtRoleModeChanged(int i) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onBtRoleModeChanged(i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onBtAutoConnectStateChanged(String str, int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onBtAutoConnectStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onHfpStateChanged(String str, int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onHfpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onA2dpStateChanged(String str, int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onA2dpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackBluetooth
        public void onAvrcpStateChanged(String str, int i, int i2) {
            if (BluetoothControl.this.mCallback != null) {
                BluetoothControl.this.mCallback.onAvrcpStateChanged(str, i, i2);
            }
        }
    };

    public BluetoothControl(Context context, AbsBluetoothControlCallback absBluetoothControlCallback) {
        this.mContext = context;
        this.mCallback = absBluetoothControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            Log.d(TAG, "bluetooth registerCallback " + this.nForeService);
            uiCommand.registerBtCallback(this.mCallbackBluetooth);
            Log.d(TAG, "bluetooth nf btService registerCallback ");
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            Log.d(TAG, "release");
            if (this.nForeService != null) {
                this.nForeService.unregisterBtCallback(this.mCallbackBluetooth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UiCommand getBtService() {
        return this.nForeService;
    }

    public int getBtState() {
        printLog(TAG, "getBtState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getBtState();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean reqBtPairedDevices() {
        printLog(TAG, "reqBtPairedDevices");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqBtPairedDevices();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean startBtDiscovery() {
        if (this.nForeService == null) {
            Log.d(TAG, "nForeService == null");
        }
        boolean z = false;
        if (this.nForeService == null) {
            return false;
        }
        try {
        } catch (Exception e) {
            printError(TAG, e);
        }
        if (this.nForeService.isBtDiscovering()) {
            Log.d(TAG, "startBtDiscovery isBtDiscovering");
            return true;
        }
        z = this.nForeService.startBtDiscovery();
        StringBuilder sb = new StringBuilder();
        sb.append("app startBtDiscovery pkg:");
        sb.append(this.mContext != null ? this.mContext.getPackageName() : "");
        sb.append(" success:");
        sb.append(z);
        Log.d(TAG, sb.toString());
        return z;
    }

    public boolean stopBtDiscovery() {
        printLog(TAG, "stopBtDiscovery");
        if (this.nForeService == null) {
            return false;
        }
        try {
            if (this.nForeService.isBtDiscovering()) {
                return this.nForeService.cancelBtDiscovery();
            }
            return false;
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean cancelBtDiscovery() {
        printLog(TAG, "cancelBtDiscovery");
        boolean z = false;
        if (this.nForeService == null) {
            return false;
        }
        try {
            if (this.nForeService.isBtDiscovering()) {
                z = this.nForeService.cancelBtDiscovery();
                StringBuilder sb = new StringBuilder();
                sb.append("app cancelBtDiscovery pkg:");
                sb.append(this.mContext != null ? this.mContext.getPackageName() : "");
                sb.append(" success:");
                sb.append(z);
                Log.d(TAG, sb.toString());
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return z;
    }

    public boolean reqBtPair(String str) {
        printLog(TAG, "reqBtPair");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqBtPair(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int reqBtConnectHfpA2dp(String str) {
        printLog(TAG, "reqBtConnectHfpA2dp");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.reqBtConnectHfpA2dp(str);
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public int getHfpConnectionState() {
        printLog(TAG, "getHfpConnectionState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getHfpConnectionState();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public int getA2dpConnectionState() {
        printLog(TAG, "getA2dpConnectionState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getA2dpConnectionState();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean setBtEnabled(boolean z) {
        printLog(TAG, "setBtEnabled");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.setBtEnable(z);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isBtEnabled() {
        printLog(TAG, "isBtEnabled");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isBtEnabled();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isBtDiscovering() {
        printLog(TAG, "isBtDiscovering");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isBtDiscovering();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean setBtLocalName(String str) {
        printLog(TAG, "setBtLocalName");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.setBtLocalName(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public String getBtLocalName() {
        printLog(TAG, "getBtLocalName");
        if (this.nForeService == null) {
            return "";
        }
        try {
            return this.nForeService.getBtLocalName();
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public String getBtRemoteDeviceName() {
        printLog(TAG, "getBtRemoteDeviceName");
        if (this.nForeService == null) {
            return "";
        }
        try {
            String hfpConnectedAddress = getHfpConnectedAddress();
            return !TextUtils.isEmpty(hfpConnectedAddress) ? this.nForeService.getBtRemoteDeviceName(hfpConnectedAddress) : "";
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public boolean reqBtUnpair(String str) {
        printLog(TAG, "reqBtUnpair");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqBtUnpair(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int reqBtDisconnectAll() {
        printLog(TAG, "reqBtDisconnectAll");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.reqBtDisconnectAll();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean isBtDiscoverable() {
        printLog(TAG, "isBtDiscoverable");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isBtDiscoverable();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean setBtDiscoverableTimeout(int i) {
        printLog(TAG, "setBtDiscoverableTimeout");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.setBtDiscoverableTimeout(i);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int getBtRemoteUuids(String str) {
        printLog(TAG, "getBtRemoteUuids");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getBtRemoteUuids(str);
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean isBluetoothServiceReady() {
        printLog(TAG, "isBluetoothServiceReady");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isBluetoothServiceReady();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isHfpConnected() {
        printLog(TAG, "isHfpConnected");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isHfpConnected();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int getHFPConnectState(String str) {
        printLog(TAG, "getHFPConnectState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            if (this.nForeService.isHfpConnected() && str.equals(this.nForeService.getHfpConnectedAddress())) {
                return this.nForeService.getHfpConnectionState();
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return -1;
    }

    public String getHfpConnectedAddress() {
        return super.getHfpConnectedAddress(TAG);
    }

    public String getA2dpConnectedAddress() {
        printLog(TAG, "getA2dpConnectedAddress");
        if (this.nForeService == null) {
            return "";
        }
        try {
            return this.nForeService.getA2dpConnectedAddress();
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public int getA2DPConnectState(String str) {
        printLog(TAG, "getA2DPConnectState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            if (this.nForeService.isA2dpConnected() && str.equals(this.nForeService.getA2dpConnectedAddress())) {
                return this.nForeService.getA2dpConnectionState();
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return -1;
    }

    public boolean isA2dpConnected() {
        printLog(TAG, InterfaceConstant.BLUETOOTH_IS_A2DP_CONNECTED);
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isA2dpConnected();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int getAVRCPConnectState(String str) {
        printLog(TAG, "getAVRCPConnectState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            if (this.nForeService.isAvrcpConnected() && str.equals(this.nForeService.getAvrcpConnectedAddress())) {
                return this.nForeService.getAvrcpConnectionState();
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return -1;
    }

    public boolean isAvrcpConnected() {
        printLog(TAG, "isAvrcpConnected");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isAvrcpConnected();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public void setBtAutoConnect(int i, int i2) {
        printLog(TAG, "setBtAutoConnect");
        if (this.nForeService == null) {
            return;
        }
        try {
            this.nForeService.setBtAutoConnect(i, i2);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    public boolean reqHfpConnect(String str) {
        printLog(TAG, "reqHfpConnect");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpConnect(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reqA2dpConnect(String str) {
        printLog(TAG, "reqA2dpConnect");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqA2dpConnect(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reqHfpDisconnect(String str) {
        printLog(TAG, "reqHfpDisconnect");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpDisconnect(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reqA2dpDisconnect(String str) {
        printLog(TAG, "reqA2dpDisConnect");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqA2dpDisconnect(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
