package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
/* loaded from: classes.dex */
public class XpAccessPoint<T> implements Comparable<XpAccessPoint> {
    public static final int SECURITY_EAP = 3;
    public static final int SECURITY_NONE = 0;
    public static final int SIGNAL_LEVELS = 5;
    private boolean mActive;
    private String mBssid;
    private WifiConfiguration mConfig;
    private boolean mConnected;
    private boolean mConnecting;
    private boolean mEverConnected;
    private String mKey;
    private int mLevel;
    private boolean mPasspoint;
    private int mRssi;
    private boolean mSaved;
    private int mSecurity;
    private String mSecurityString;
    private String mSummary;
    private T rawData;
    private String ssid;

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public String getSettingsSummary() {
        return this.mSummary;
    }

    public void setSummary(String str) {
        this.mSummary = str;
    }

    public boolean isSaved() {
        return this.mSaved;
    }

    public void setSaved(boolean z) {
        this.mSaved = z;
    }

    public WifiConfiguration getConfig() {
        return this.mConfig;
    }

    public void setConfig(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    public void setConnected(boolean z) {
        this.mConnected = z;
    }

    public boolean isConnecting() {
        return this.mConnecting;
    }

    public void setConnecting(boolean z) {
        this.mConnecting = z;
    }

    public String getBssid() {
        return this.mBssid;
    }

    public void setBssid(String str) {
        this.mBssid = str;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public void setLevel(int i) {
        this.mLevel = i;
    }

    public int getSecurity() {
        return this.mSecurity;
    }

    public void setSecurity(int i) {
        this.mSecurity = i;
    }

    public boolean isEverConnected() {
        return this.mEverConnected;
    }

    public void setEverConnected(boolean z) {
        this.mEverConnected = z;
    }

    public boolean isPasspoint() {
        return this.mPasspoint;
    }

    public void setPasspoint(boolean z) {
        this.mPasspoint = z;
    }

    public String getSecurityString() {
        return this.mSecurityString;
    }

    public void setSecurityString(String str) {
        this.mSecurityString = str;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public void setActive(boolean z) {
        this.mActive = z;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public void setRssi(int i) {
        this.mRssi = i;
    }

    public static String convertToQuotedString(String str) {
        return "\"" + str + "\"";
    }

    public T getRawData() {
        return this.rawData;
    }

    public void setRawData(T t) {
        this.rawData = t;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof XpAccessPoint) && getKey().equals(((XpAccessPoint) obj).getKey());
    }

    @Override // java.lang.Comparable
    public int compareTo(XpAccessPoint xpAccessPoint) {
        if (!isConnected() || xpAccessPoint.isConnected()) {
            if (isConnected() || !xpAccessPoint.isConnected()) {
                if (!isActive() || xpAccessPoint.isActive()) {
                    if (isActive() || !xpAccessPoint.isActive()) {
                        if (!isEverConnected() || xpAccessPoint.isEverConnected()) {
                            if (isEverConnected() || !xpAccessPoint.isEverConnected()) {
                                int calculateSignalLevel = WifiManager.calculateSignalLevel(xpAccessPoint.mRssi, 5) - WifiManager.calculateSignalLevel(this.mRssi, 5);
                                return calculateSignalLevel != 0 ? calculateSignalLevel : getSsid().compareToIgnoreCase(xpAccessPoint.getSsid());
                            }
                            return 1;
                        }
                        return -1;
                    }
                    return 1;
                }
                return -1;
            }
            return 1;
        }
        return -1;
    }
}
