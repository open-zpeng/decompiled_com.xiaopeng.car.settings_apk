package com.xiaopeng.car.settingslibrary.ui.common;

import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class PsnBluetoothTypeBean {
    public static final int TYPE_BONDED_DEVICE = 3;
    public static final int TYPE_CONNECTED_DEVICE = 2;
    public static final int TYPE_HEADER_BONDED = 0;
    public static final int TYPE_HEADER_NEW = 1;
    public static final int TYPE_NEW_DEVICE = 4;
    public static final int TYPE_NONE_DEVICE_TIP = 5;
    private PsnBluetoothDeviceInfo data;
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

    private PsnBluetoothTypeBean(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo, int i) {
        this.type = i;
        this.data = psnBluetoothDeviceInfo;
    }

    public static ArrayList<PsnBluetoothTypeBean> list(ArrayList<PsnBluetoothDeviceInfo> arrayList, int i) {
        ArrayList<PsnBluetoothTypeBean> arrayList2;
        if (arrayList == null) {
            return new ArrayList<>();
        }
        synchronized (arrayList) {
            arrayList2 = new ArrayList<>(arrayList.size());
            Iterator<PsnBluetoothDeviceInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new PsnBluetoothTypeBean(it.next(), i));
            }
        }
        return arrayList2;
    }

    public boolean equals(Object obj) {
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo;
        if (this == obj) {
            return true;
        }
        if (obj instanceof PsnBluetoothTypeBean) {
            PsnBluetoothTypeBean psnBluetoothTypeBean = (PsnBluetoothTypeBean) obj;
            return this.type == psnBluetoothTypeBean.type && (psnBluetoothDeviceInfo = this.data) != null && psnBluetoothDeviceInfo.equals(psnBluetoothTypeBean.data);
        }
        return false;
    }

    public static PsnBluetoothTypeBean item(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo, int i) {
        return new PsnBluetoothTypeBean(psnBluetoothDeviceInfo, i);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public PsnBluetoothDeviceInfo getData() {
        return this.data;
    }

    public void setData(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        this.data = psnBluetoothDeviceInfo;
    }

    public String toString() {
        return "BluetoothTypeBean{type=" + this.type + ", data=" + this.data + '}';
    }

    public boolean isChanged() {
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo = this.data;
        if (psnBluetoothDeviceInfo == null) {
            return false;
        }
        if (this.isInit) {
            this.isInit = false;
            return false;
        } else if (this.pairState == psnBluetoothDeviceInfo.getPairState()) {
            return ((TextUtils.isEmpty(this.name) || this.name == this.data.getDeviceName()) && this.hfpConnected == this.data.isHfpConnected() && this.a2dpConnected == this.data.isA2dpConnected() && this.hidConnected == this.data.isHidConnected() && this.isConnectingBusy == this.data.isConnectingBusy() && this.isDisconnectingBusy == this.data.isDisconnectingBusy()) ? false : true;
        } else {
            return true;
        }
    }

    public void refreshStatus() {
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo = this.data;
        if (psnBluetoothDeviceInfo == null) {
            return;
        }
        this.pairState = psnBluetoothDeviceInfo.getPairState();
        this.hfpConnected = this.data.isHfpConnected();
        this.a2dpConnected = this.data.isA2dpConnected();
        this.hidConnected = this.data.isHidConnected();
        this.isConnectingBusy = this.data.isConnectingBusy();
        this.isDisconnectingBusy = this.data.isDisconnectingBusy();
        this.name = this.data.getDeviceName();
    }
}
