package com.xiaopeng.car.settingslibrary.manager.speech.bean;

import java.util.List;
/* loaded from: classes.dex */
public class BluetoothDevicesBean {
    private List<DeviceBean> device;

    public List<DeviceBean> getDevice() {
        return this.device;
    }

    public void setDevice(List<DeviceBean> list) {
        this.device = list;
    }

    /* loaded from: classes.dex */
    public static class DeviceBean {
        private String mac;
        private String name;
        private int type;

        /* loaded from: classes.dex */
        public static class DeviceType {
            public static final int PERIPHERAL = 2;
            public static final int PHONE = 1;
            public static final int UNKNOWN = 3;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String str) {
            this.mac = str;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }
    }
}
