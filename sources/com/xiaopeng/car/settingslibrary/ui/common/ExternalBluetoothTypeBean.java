package com.xiaopeng.car.settingslibrary.ui.common;

import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class ExternalBluetoothTypeBean {
    public static final int TYPE_BONDED_DEVICE = 3;
    public static final int TYPE_CONNECTED_DEVICE = 2;
    public static final int TYPE_HEADER_BONDED = 0;
    public static final int TYPE_HEADER_NEW = 1;
    public static final int TYPE_NEW_DEVICE = 4;
    public static final int TYPE_NONE_DEVICE_TIP = 5;
    private ExternalBluetoothDeviceInfo data;
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

    private ExternalBluetoothTypeBean(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo, int i) {
        this.type = i;
        this.data = externalBluetoothDeviceInfo;
    }

    public static ArrayList<ExternalBluetoothTypeBean> list(ArrayList<ExternalBluetoothDeviceInfo> arrayList, int i) {
        ArrayList<ExternalBluetoothTypeBean> arrayList2;
        if (arrayList == null) {
            return new ArrayList<>();
        }
        synchronized (arrayList) {
            arrayList2 = new ArrayList<>(arrayList.size());
            Iterator<ExternalBluetoothDeviceInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new ExternalBluetoothTypeBean(it.next(), i));
            }
        }
        return arrayList2;
    }

    public boolean equals(Object obj) {
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo;
        if (this == obj) {
            return true;
        }
        if (obj instanceof ExternalBluetoothTypeBean) {
            ExternalBluetoothTypeBean externalBluetoothTypeBean = (ExternalBluetoothTypeBean) obj;
            return this.type == externalBluetoothTypeBean.type && (externalBluetoothDeviceInfo = this.data) != null && externalBluetoothDeviceInfo.equals(externalBluetoothTypeBean.data);
        }
        return false;
    }

    public static ExternalBluetoothTypeBean item(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo, int i) {
        return new ExternalBluetoothTypeBean(externalBluetoothDeviceInfo, i);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public ExternalBluetoothDeviceInfo getData() {
        return this.data;
    }

    public void setData(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        this.data = externalBluetoothDeviceInfo;
    }

    public String toString() {
        return "BluetoothTypeBean{type=" + this.type + ", data=" + this.data + '}';
    }

    public boolean isChanged() {
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo = this.data;
        if (externalBluetoothDeviceInfo == null) {
            return false;
        }
        if (this.isInit) {
            this.isInit = false;
            return false;
        } else if (this.pairState == externalBluetoothDeviceInfo.getPairState()) {
            return ((TextUtils.isEmpty(this.name) || this.name == this.data.getDeviceName()) && this.hfpConnected == this.data.isHfpConnected() && this.a2dpConnected == this.data.isA2dpConnected() && this.hidConnected == this.data.isHidConnected() && this.isConnectingBusy == this.data.isConnectingBusy() && this.isDisconnectingBusy == this.data.isDisconnectingBusy()) ? false : true;
        } else {
            return true;
        }
    }

    public void refreshStatus() {
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo = this.data;
        if (externalBluetoothDeviceInfo == null) {
            return;
        }
        this.pairState = externalBluetoothDeviceInfo.getPairState();
        this.hfpConnected = this.data.isHfpConnected();
        this.a2dpConnected = this.data.isA2dpConnected();
        this.hidConnected = this.data.isHidConnected();
        this.isConnectingBusy = this.data.isConnectingBusy();
        this.isDisconnectingBusy = this.data.isDisconnectingBusy();
        this.name = this.data.getDeviceName();
    }
}
