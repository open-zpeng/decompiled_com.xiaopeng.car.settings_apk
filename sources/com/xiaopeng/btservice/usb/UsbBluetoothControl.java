package com.xiaopeng.btservice.usb;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.nforetek.bt.aidl.UiCallbackUsbBluetooth;
import com.nforetek.bt.aidl.UiCommand;
import com.nforetek.bt.aidl.UsbBluetoothDevice;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.btservice.base.AbsUsbBluetoothCallback;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import java.util.List;
/* loaded from: classes.dex */
public class UsbBluetoothControl extends AbsControl {
    private static final String TAG = "UsbBluetoothControl";
    private AbsUsbBluetoothCallback mCallback;
    private UiCallbackUsbBluetooth mCallbackUsbBluetooth = new UiCallbackUsbBluetooth.Stub() { // from class: com.xiaopeng.btservice.usb.UsbBluetoothControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbPairedDevices(int i, String str, String str2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbPairedDevices(i, str, str2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbDeviceFound(String str, String str2, byte b) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbDeviceFound(str, str2, b);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbAdapterStateChanged(int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbAdapterStateChanged(i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbAdapterDiscoveryStarted() throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbAdapterDiscoveryStarted();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbAdapterDiscoveryFinished() throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbAdapterDiscoveryFinished();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbDeviceBondStateChanged(String str, String str2, int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbDeviceBondStateChanged(str, str2, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbHfpStateChanged(String str, int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbHfpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbA2dpStateChanged(String str, int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbA2dpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbAvrcpStateChanged(String str, int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbAvrcpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbDeviceState(int i) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbDeviceState(i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackUsbBluetooth
        public void onUsbConnectionStateChanged(String str, int i, int i2) throws RemoteException {
            if (UsbBluetoothControl.this.mCallback != null) {
                UsbBluetoothControl.this.mCallback.onUsbConnectionStateChanged(str, i, i2);
            }
        }
    };

    public UsbBluetoothControl(Context context, AbsUsbBluetoothCallback absUsbBluetoothCallback) {
        this.mContext = context;
        this.mCallback = absUsbBluetoothCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            Log.d(TAG, "bluetooth registerCallback " + this.nForeService);
            boolean registerUsbCallback = uiCommand.registerUsbCallback(this.mCallbackUsbBluetooth);
            Log.d(TAG, "bluetooth nf btService registerCallback " + registerUsbCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            Log.d(TAG, "release");
            if (this.nForeService != null) {
                this.nForeService.unregisterUsbCallback(this.mCallbackUsbBluetooth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean usbSearch() {
        printLog(TAG, InterfaceConstant.PSN_BLUETOOTH_SEARCH);
        boolean z = false;
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
        } catch (Exception e) {
            printError(TAG, e);
        }
        if (this.nForeService.isUsbDiscovering()) {
            Log.d(TAG, "startBtDiscovery isUsbDiscovering");
            return true;
        }
        z = this.nForeService.usbSearch();
        StringBuilder sb = new StringBuilder();
        sb.append("app usbSearch pkg:");
        sb.append(this.mContext != null ? this.mContext.getPackageName() : "");
        sb.append(" success:");
        sb.append(z);
        Log.d(TAG, sb.toString());
        return z;
    }

    public boolean usbStopSearch() {
        printLog(TAG, "usbStopSearch");
        boolean z = false;
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            z = this.nForeService.usbStopSearch();
            StringBuilder sb = new StringBuilder();
            sb.append("app usbStopSearch pkg:");
            sb.append(this.mContext != null ? this.mContext.getPackageName() : "");
            sb.append(" success:");
            sb.append(z);
            Log.d(TAG, sb.toString());
        } catch (Exception e) {
            printError(TAG, e);
        }
        return z;
    }

    public boolean isUsbDiscovering() {
        printLog(TAG, InterfaceConstant.PSN_BLUETOOTH_IS_DISCOVERING);
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.isUsbDiscovering();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean usbConnect(String str) {
        printLog(TAG, InterfaceConstant.PSN_BLUETOOTH_CONNECT);
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.usbConnect(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean usbDisConnect() {
        printLog(TAG, "usbDisConnect");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.usbDisConnect();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean usbDisConnectDevice(String str) {
        printLog(TAG, "usbDisConnectDevice " + str);
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.usbDisConnectDevice(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean usbReqUnpair(String str) {
        printLog(TAG, "usbReqUnpair");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.usbReqUnpair(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean setUsbEnabled(boolean z) {
        printLog(TAG, "setUsbEnabled");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.setUsbEnabled(z);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isUsbEnabled() {
        printLog(TAG, "isUsbEnabled");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.isUsbEnabled();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isUsbHfpConnected(String str) {
        printLog(TAG, "isUsbHfpConnected");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.isUsbHfpConnected(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean isUsbA2dpConnected(String str) {
        printLog(TAG, "isUsbA2dpConnected");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.isUsbA2dpConnected(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int getUsbConnectionState(String str) {
        printLog(TAG, "getUsbConnectionState");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return -1;
        }
        try {
            return this.nForeService.getUsbConnectionState(str);
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean isUsbAvrcpConnected(String str) {
        printLog(TAG, "isUsbAvrcpConnected");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.isUsbAvrcpConnected(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public List<UsbBluetoothDevice> reqUsbBtPairedDevices() {
        printLog(TAG, "reqUsbBtPairedDevices");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return null;
        }
        try {
            return this.nForeService.reqUsbBtPairedDevices();
        } catch (Exception e) {
            printError(TAG, e);
            return null;
        }
    }

    public String getUsbBtLocalName() {
        printLog(TAG, "getUsbBtLocalName");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return "";
        }
        try {
            return this.nForeService.getUsbBtLocalName();
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public boolean setUsbBtLocalName(String str) {
        printLog(TAG, "setUsbBtLocalName");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.setUsbBtLocalName(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public String getUsbAddress() {
        printLog(TAG, "getUsbAddress");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return "";
        }
        try {
            return this.nForeService.getUsbAddress();
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public String getUsbConnectedDevice() {
        printLog(TAG, InterfaceConstant.PSN_GET_USB_CONNECTED_DEVICE);
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return "";
        }
        try {
            return this.nForeService.getUsbConnectedDevice();
        } catch (Exception e) {
            printError(TAG, e);
            return "";
        }
    }

    public int getLocalUsbConnectionState() {
        printLog(TAG, "getLocalUsbConnectionState");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return -1;
        }
        try {
            return this.nForeService.getLocalUsbConnectionState();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public int getProfileConnectionState(int i) {
        printLog(TAG, "getProfileConnectionState");
        if (this.nForeService == null) {
            printLog(TAG, "nForeService == null");
            return -1;
        }
        try {
            return this.nForeService.getProfileConnectionState(i);
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }
}
