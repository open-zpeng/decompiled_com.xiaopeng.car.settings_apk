package com.xiaopeng.car.settingslibrary.ui.common;

import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/* loaded from: classes.dex */
public class WifiTypeBean {
    private XpAccessPoint accessPoint;
    private boolean isConnected;
    private boolean isConnecting;
    private boolean isSave;
    private int level;
    private String summary = "";

    public static List<WifiTypeBean> list(List<XpAccessPoint> list) {
        if (list == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (XpAccessPoint xpAccessPoint : list) {
            arrayList.add(item(xpAccessPoint));
        }
        return arrayList;
    }

    private static WifiTypeBean item(XpAccessPoint xpAccessPoint) {
        WifiTypeBean wifiTypeBean = new WifiTypeBean();
        wifiTypeBean.accessPoint = xpAccessPoint;
        wifiTypeBean.isConnected = xpAccessPoint.isConnected();
        return wifiTypeBean;
    }

    public void setAccessPoint(XpAccessPoint xpAccessPoint) {
        this.accessPoint = xpAccessPoint;
    }

    public boolean isChanged() {
        return (this.isConnecting == this.accessPoint.isConnecting() && this.level == this.accessPoint.getLevel() && this.isSave == this.accessPoint.isSaved() && this.summary == this.accessPoint.getSettingsSummary()) ? false : true;
    }

    public void refreshStatus() {
        this.isConnecting = this.accessPoint.isConnecting();
        this.level = this.accessPoint.getLevel();
        this.isSave = this.accessPoint.isSaved();
        this.summary = this.accessPoint.getSettingsSummary();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WifiTypeBean wifiTypeBean = (WifiTypeBean) obj;
        return this.isConnected == wifiTypeBean.isConnected && Objects.equals(this.accessPoint, wifiTypeBean.accessPoint);
    }

    public int hashCode() {
        return Objects.hash(this.accessPoint, Boolean.valueOf(this.isConnected));
    }

    public XpAccessPoint getAccessPoint() {
        return this.accessPoint;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isConnecting() {
        return this.isConnecting;
    }

    public String getSummary() {
        return this.summary;
    }

    public String toString() {
        return "WifiTypeBean{accessPoint=" + this.accessPoint + ", isConnected=" + this.isConnected + ", isConnecting=" + this.isConnecting + ", summary='" + this.summary + "', isSave=" + this.isSave + ", level=" + this.level + '}';
    }
}
