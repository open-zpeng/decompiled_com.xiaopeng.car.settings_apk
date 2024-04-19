package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class XpAutoManualManager {
    private static final String TAG = "XpAutoManualManager";
    private static XpAutoManualManager mInstance;
    private WifiManager mWifiManager = (WifiManager) CarSettingsApp.getContext().getApplicationContext().getSystemService("wifi");
    private Wifi mWifi = new Wifi();
    private WifiManager.ActionListener mConnectListener = new WifiManager.ActionListener() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager.1
        public void onSuccess() {
            XpAutoManualManager.this.log("ActionListener onSuccess");
        }

        public void onFailure(int i) {
            XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
            xpAutoManualManager.log("ActionListener onFailure: reason = " + i);
        }
    };

    private XpAutoManualManager() {
    }

    public static XpAutoManualManager getInstance() {
        if (mInstance == null) {
            mInstance = new XpAutoManualManager();
        }
        return mInstance;
    }

    public void init() {
        this.mWifi.logWifiConfiguration();
    }

    public void clearAuto() {
        this.mWifi.clearWifiConfiguration();
    }

    public int checkAndConnect() {
        return checkAndConnect(this.mWifi.convertToQuotedString());
    }

    public int checkAndConnectXpCheck() {
        return checkAndConnect(this.mWifi.convertXpCheckToQuotedString());
    }

    private int checkAndConnect(final String str) {
        log("checkAndConnect");
        if (this.mWifi.getWifiConfiguration(str) == null) {
            if (!isCertificateInstalled()) {
                log("save before ...isCacertInstalled false");
                return 1;
            }
            log("check Configuration not ,now create");
            this.mWifiManager.save(this.mWifi.createWifiInfo(str), new WifiManager.ActionListener() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager.2
                public void onSuccess() {
                    XpAutoManualManager.this.log("save onSuccess ");
                    XpAutoManualManager.this.mWifi.connect(str);
                }

                public void onFailure(int i) {
                    XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                    xpAutoManualManager.log("save onFailure : " + i);
                }
            });
            return 0;
        }
        this.mWifi.connect(str);
        log("check Configuration has");
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCertificateInstalled() {
        return WifiUtils.isCertificateInstalled();
    }

    public boolean isXpAutoConnected() {
        Wifi wifi = this.mWifi;
        return wifi.isConnected(wifi.convertToQuotedString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Wifi {
        private static final String DEFAULT_SSID = "XP-AUTO";
        private static final int EAP_METHOD = 1;
        private static final int PHASE2_METHOD = 0;
        private static final int SECURITY_EAP = 3;
        private static final int SECURITY_NONE = 0;
        private static final int SECURITY_PSK = 2;
        private static final int SECURITY_WEP = 1;
        private static final String XPCHECK_SSID = "XP-CHECK";
        private static final String XP_AUTO_CA = "xp-auto-ca";
        private static final String XP_AUTO_CLIENT = "xp-auto-client";

        /* JADX INFO: Access modifiers changed from: private */
        public String convertToQuotedString() {
            return "\"XP-AUTO\"";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String convertXpCheckToQuotedString() {
            return "\"XP-CHECK\"";
        }

        private Wifi() {
        }

        private boolean checkXpAuto() {
            if (!XpAutoManualManager.this.mWifiManager.isWifiEnabled()) {
                XpAutoManualManager.this.log("checkXpAuto...isWifiEnabled = false");
                return false;
            }
            for (ScanResult scanResult : XpAutoManualManager.this.mWifiManager.getScanResults()) {
                XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                xpAutoManualManager.log("checkXpAuto...XP-AUTO , result : " + scanResult.SSID);
                if ("XP-AUTO".equals(scanResult.SSID)) {
                    XpAutoManualManager.this.log("checkXpAuto...SSID found");
                    if (getSecurity(scanResult) == 3) {
                        XpAutoManualManager.this.log("checkXpAuto...true");
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean connect(String str) {
            if (!XpAutoManualManager.this.mWifiManager.isWifiEnabled()) {
                XpAutoManualManager.this.log("connect...isWifiEnabled = false");
                return false;
            }
            WifiConfiguration wifiConfiguration = getWifiConfiguration(str);
            if (wifiConfiguration == null) {
                XpAutoManualManager.this.log("connect...configuration = null");
                return false;
            } else if (XpAutoManualManager.this.mWifi.isConnected(str)) {
                XpAutoManualManager.this.log("connect...isConnected");
                return true;
            } else if (!XpAutoManualManager.this.isCertificateInstalled()) {
                XpAutoManualManager.this.log("connect...isCacertInstalled false");
                return false;
            } else {
                if (wifiConfiguration.networkId != -1) {
                    XpAutoManualManager.this.mWifiManager.connect(wifiConfiguration.networkId, XpAutoManualManager.this.mConnectListener);
                    XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                    xpAutoManualManager.log("connect...netWorkId = " + wifiConfiguration.networkId + " , network enabled = ");
                } else {
                    XpAutoManualManager.this.mWifiManager.connect(wifiConfiguration, XpAutoManualManager.this.mConnectListener);
                    XpAutoManualManager.this.log("connect...configuration ");
                }
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isConnected(String str) {
            WifiInfo connectionInfo = XpAutoManualManager.this.mWifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                return false;
            }
            switch (AnonymousClass3.$SwitchMap$android$net$wifi$SupplicantState[connectionInfo.getSupplicantState().ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return connectionInfo.getSSID().equals(str);
                default:
                    return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearWifiConfiguration() {
            List<WifiConfiguration> configuredNetworks = XpAutoManualManager.this.mWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                xpAutoManualManager.log("clearWifiConfiguration--size:" + configuredNetworks.size());
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    if (convertToQuotedString().equals(wifiConfiguration.SSID) && getSecurity(wifiConfiguration) == 3) {
                        XpAutoManualManager.this.mWifiManager.forget(wifiConfiguration.networkId, null);
                        XpAutoManualManager xpAutoManualManager2 = XpAutoManualManager.this;
                        xpAutoManualManager2.log("clearWifiConfiguration--" + wifiConfiguration.SSID);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void logWifiConfiguration() {
            List<WifiConfiguration> configuredNetworks = XpAutoManualManager.this.mWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                xpAutoManualManager.log("WifiConfiguration--size:" + configuredNetworks.size());
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (it.hasNext()) {
                    XpAutoManualManager xpAutoManualManager2 = XpAutoManualManager.this;
                    xpAutoManualManager2.log("configuration: " + it.next().SSID);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public WifiConfiguration getWifiConfiguration(String str) {
            List<WifiConfiguration> configuredNetworks = XpAutoManualManager.this.mWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    if (str.equals(wifiConfiguration.SSID)) {
                        XpAutoManualManager xpAutoManualManager = XpAutoManualManager.this;
                        xpAutoManualManager.log("getWifiConfiguration has " + wifiConfiguration.SSID);
                        if (getSecurity(wifiConfiguration) == 3) {
                            return wifiConfiguration;
                        }
                    }
                }
                return null;
            }
            return null;
        }

        private int getSecurity(WifiConfiguration wifiConfiguration) {
            if (wifiConfiguration.allowedKeyManagement.get(1)) {
                return 2;
            }
            if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
                return 3;
            }
            return wifiConfiguration.wepKeys[0] != null ? 1 : 0;
        }

        private int getSecurity(ScanResult scanResult) {
            if (scanResult.capabilities.contains("WEP")) {
                return 1;
            }
            if (scanResult.capabilities.contains("PSK")) {
                return 2;
            }
            return scanResult.capabilities.contains("EAP") ? 3 : 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public WifiConfiguration createWifiInfo(String str) {
            return WifiUtils.createXpAutoWifiInfo(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$android$net$wifi$SupplicantState = new int[SupplicantState.values().length];

        static {
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.AUTHENTICATING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.ASSOCIATING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.ASSOCIATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.FOUR_WAY_HANDSHAKE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.GROUP_HANDSHAKE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$net$wifi$SupplicantState[SupplicantState.COMPLETED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        Log.i(TAG, str);
    }
}
