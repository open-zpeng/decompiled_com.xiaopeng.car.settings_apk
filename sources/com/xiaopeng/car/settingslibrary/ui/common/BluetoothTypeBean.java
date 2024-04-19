package com.xiaopeng.car.settingslibrary.ui.common;

import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class BluetoothTypeBean {
    public static final int TYPE_BONDED_DEVICE = 3;
    public static final int TYPE_CONNECTED_DEVICE = 2;
    public static final int TYPE_HEADER_BONDED = 0;
    public static final int TYPE_HEADER_NEW = 1;
    public static final int TYPE_NEW_DEVICE = 4;
    public static final int TYPE_NONE_DEVICE_TIP = 5;
    private XpBluetoothDeviceInfo data;
    private int type;
    private String name = "";
    private boolean isInit = true;
    private int pairState = 10;
    private boolean hfpConnected = false;
    private boolean a2dpConnected = false;
    private boolean hidConnected = false;
    private boolean isParingBusy = false;
    private boolean isConnectingBusy = false;
    private boolean isDisconnectingBusy = false;

    private BluetoothTypeBean(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, int i) {
        this.type = i;
        this.data = xpBluetoothDeviceInfo;
    }

    public static ArrayList<BluetoothTypeBean> list(ArrayList<XpBluetoothDeviceInfo> arrayList, int i) {
        ArrayList<BluetoothTypeBean> arrayList2;
        if (arrayList == null) {
            return new ArrayList<>();
        }
        synchronized (arrayList) {
            arrayList2 = new ArrayList<>(arrayList.size());
            Iterator<XpBluetoothDeviceInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new BluetoothTypeBean(it.next(), i));
            }
        }
        return arrayList2;
    }

    public boolean equals(Object obj) {
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo;
        if (this == obj) {
            return true;
        }
        if (obj instanceof BluetoothTypeBean) {
            BluetoothTypeBean bluetoothTypeBean = (BluetoothTypeBean) obj;
            return this.type == bluetoothTypeBean.type && (xpBluetoothDeviceInfo = this.data) != null && xpBluetoothDeviceInfo.equals(bluetoothTypeBean.data);
        }
        return false;
    }

    public static BluetoothTypeBean item(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, int i) {
        return new BluetoothTypeBean(xpBluetoothDeviceInfo, i);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public XpBluetoothDeviceInfo getData() {
        return this.data;
    }

    public void setData(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.data = xpBluetoothDeviceInfo;
    }

    public String toString() {
        return "BluetoothTypeBean{type=" + this.type + ", data=" + this.data + '}';
    }

    public boolean isChanged() {
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo = this.data;
        if (xpBluetoothDeviceInfo == null) {
            return false;
        }
        if (this.isInit) {
            this.isInit = false;
            return false;
        } else if (this.pairState == xpBluetoothDeviceInfo.getPairState()) {
            return ((TextUtils.isEmpty(this.name) || this.name == this.data.getDeviceName()) && this.hfpConnected == this.data.isHfpConnected() && this.a2dpConnected == this.data.isA2dpConnected() && this.hidConnected == this.data.isHidConnected() && this.isParingBusy == this.data.isParingBusy() && this.isConnectingBusy == this.data.isConnectingBusy() && this.isDisconnectingBusy == this.data.isDisconnectingBusy()) ? false : true;
        } else {
            return true;
        }
    }

    public void refreshStatus() {
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo = this.data;
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        this.pairState = xpBluetoothDeviceInfo.getPairState();
        this.hfpConnected = this.data.isHfpConnected();
        this.a2dpConnected = this.data.isA2dpConnected();
        this.hidConnected = this.data.isHidConnected();
        this.isParingBusy = this.data.isParingBusy();
        this.isConnectingBusy = this.data.isConnectingBusy();
        this.isDisconnectingBusy = this.data.isDisconnectingBusy();
        this.name = this.data.getDeviceName();
    }
}
