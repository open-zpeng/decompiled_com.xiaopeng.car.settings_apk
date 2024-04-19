package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class BluetoothConnectWhiteList {
    public static final String PROJECTOR_NAME = "NEW Z6X 小鹏定制版";
    public static final String PROJECTOR_NAME_COMMON = "小鹏定制版投影仪";
    public static final String PROJECTOR_NAME_NEW = "NEW Z6X 小鹏定制版";
    private XpBluetoothDeviceInfo mXpBluetoothDeviceInfo;

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final BluetoothConnectWhiteList instance = new BluetoothConnectWhiteList();

        private InnerFactory() {
        }
    }

    public static BluetoothConnectWhiteList getInstance() {
        return InnerFactory.instance;
    }

    public boolean isInWhiteList(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.contains(PROJECTOR_NAME) || str.contains(PROJECTOR_NAME_NEW) || str.contains(PROJECTOR_NAME_COMMON);
    }

    public void fillWhiteList(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mXpBluetoothDeviceInfo = xpBluetoothDeviceInfo;
    }

    public XpBluetoothDeviceInfo getWhiteListDevice() {
        return this.mXpBluetoothDeviceInfo;
    }
}
