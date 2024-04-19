package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.content.Context;
import android.net.IpConfiguration;
import android.net.NetworkCapabilities;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.SystemProperties;
import android.security.KeyStore;
import androidx.core.view.PointerIconCompat;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
/* loaded from: classes.dex */
public class WifiUtils {
    private static final int EAP_METHOD = 1;
    private static final int PHASE2_METHOD = 0;
    private static final String XP_AUTO_CA = "xp-auto-ca";
    private static final String XP_AUTO_CLIENT = "xp-auto-client";

    /* loaded from: classes.dex */
    public static class NetworkCapabilitiesConstant {
        public static final int NET_CAPABILITY_PARTIAL_CONNECTIVITY = ReflectUtils.getIntValue("NET_CAPABILITY_PARTIAL_CONNECTIVITY", NetworkCapabilities.class);
    }

    /* loaded from: classes.dex */
    public static class ProvisioningCallbackConstant {
        public static final int OSU_STATUS_AP_CONNECTING = ReflectUtils.getIntValue("OSU_STATUS_AP_CONNECTING", ProvisioningCallback.class);
        public static final int OSU_STATUS_AP_CONNECTED = ReflectUtils.getIntValue("OSU_STATUS_AP_CONNECTED", ProvisioningCallback.class);
        public static final int OSU_STATUS_SERVER_CONNECTING = ReflectUtils.getIntValue("OSU_STATUS_SERVER_CONNECTING", ProvisioningCallback.class);
        public static final int OSU_STATUS_SERVER_VALIDATED = ReflectUtils.getIntValue("OSU_STATUS_SERVER_VALIDATED", ProvisioningCallback.class);
        public static final int OSU_STATUS_SERVER_CONNECTED = ReflectUtils.getIntValue("OSU_STATUS_SERVER_CONNECTED", ProvisioningCallback.class);
        public static final int OSU_STATUS_INIT_SOAP_EXCHANGE = ReflectUtils.getIntValue("OSU_STATUS_INIT_SOAP_EXCHANGE", ProvisioningCallback.class);
        public static final int OSU_STATUS_WAITING_FOR_REDIRECT_RESPONSE = ReflectUtils.getIntValue("OSU_STATUS_WAITING_FOR_REDIRECT_RESPONSE", ProvisioningCallback.class);
        public static final int OSU_STATUS_REDIRECT_RESPONSE_RECEIVED = ReflectUtils.getIntValue("OSU_STATUS_REDIRECT_RESPONSE_RECEIVED", ProvisioningCallback.class);
        public static final int OSU_STATUS_SECOND_SOAP_EXCHANGE = ReflectUtils.getIntValue("OSU_STATUS_SECOND_SOAP_EXCHANGE", ProvisioningCallback.class);
        public static final int OSU_STATUS_THIRD_SOAP_EXCHANGE = ReflectUtils.getIntValue("OSU_STATUS_THIRD_SOAP_EXCHANGE", ProvisioningCallback.class);
        public static final int OSU_STATUS_RETRIEVING_TRUST_ROOT_CERTS = ReflectUtils.getIntValue("OSU_STATUS_RETRIEVING_TRUST_ROOT_CERTS", ProvisioningCallback.class);
    }

    /* loaded from: classes.dex */
    public static class WifiConfigurationConstant {

        /* loaded from: classes.dex */
        public static class GroupMgmtCipherConstant {
            public static final int BIP_GMAC_256 = ReflectUtils.getIntValue("BIP_GMAC_256", WifiConfiguration.GroupMgmtCipher.class);
        }

        /* loaded from: classes.dex */
        public static class KeyMgmtConstant {
            public static final int SAE = ReflectUtils.getIntValue("SAE", WifiConfiguration.KeyMgmt.class);
            public static final int SUITE_B_192 = ReflectUtils.getIntValue("SUITE_B_192", WifiConfiguration.KeyMgmt.class);
            public static final int OWE = ReflectUtils.getIntValue("OWE", WifiConfiguration.KeyMgmt.class);
        }

        /* loaded from: classes.dex */
        public static class NetworkSelectionStatusConstant {
            public static final int DISABLED_ASSOCIATION_REJECTION = ReflectUtils.getIntValue("DISABLED_ASSOCIATION_REJECTION", WifiConfiguration.NetworkSelectionStatus.class);
            public static final int DISABLED_DNS_FAILURE = ReflectUtils.getIntValue("DISABLED_DNS_FAILURE", WifiConfiguration.NetworkSelectionStatus.class);
            public static final int DISABLED_DHCP_FAILURE = ReflectUtils.getIntValue("DISABLED_DHCP_FAILURE", WifiConfiguration.NetworkSelectionStatus.class);
            public static final int DISABLED_BY_WRONG_PASSWORD = ReflectUtils.getIntValue("DISABLED_BY_WRONG_PASSWORD", WifiConfiguration.NetworkSelectionStatus.class);
            public static final int DISABLED_AUTHENTICATION_FAILURE = ReflectUtils.getIntValue("DISABLED_AUTHENTICATION_FAILURE", WifiConfiguration.NetworkSelectionStatus.class);
        }

        /* loaded from: classes.dex */
        public static class PairwiseCipherConstant {
            public static final int GCMP_256 = ReflectUtils.getIntValue("GCMP_256", WifiConfiguration.PairwiseCipher.class);
        }

        /* loaded from: classes.dex */
        public static class RecentFailureConstant {
            public static final int STATUS_AP_UNABLE_TO_HANDLE_NEW_STA = ReflectUtils.getIntValue("STATUS_AP_UNABLE_TO_HANDLE_NEW_STA", WifiConfiguration.RecentFailure.class);
        }
    }

    /* loaded from: classes.dex */
    public static class WifiManagerConstant {
        public static final int ERROR_AUTH_FAILURE_EAP_FAILURE = ReflectUtils.getIntValue("ERROR_AUTH_FAILURE_EAP_FAILURE", WifiManager.class);
        public static final int ERROR_AUTH_FAILURE_WRONG_PSWD = ReflectUtils.getIntValue("ERROR_AUTH_FAILURE_WRONG_PSWD", WifiManager.class);
        public static final int PASSPOINT_HOME_NETWORK = ReflectUtils.getIntValue("PASSPOINT_HOME_NETWORK", WifiManager.class);
        public static final int PASSPOINT_ROAMING_NETWORK = ReflectUtils.getIntValue("PASSPOINT_ROAMING_NETWORK", WifiManager.class);
    }

    public static boolean isWifiConnectFail(int i) {
        return 1 == i || i == 0 || 1 == i;
    }

    public static void printReflect() {
    }

    public static WifiConfiguration getWifiConfig(AccessPoint accessPoint, String str, String str2, String str3, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", str);
        switch (i) {
            case 0:
                wifiConfiguration.allowedKeyManagement.set(0);
                break;
            case 1:
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                if (str3.length() != 0) {
                    int length = str3.length();
                    if ((length == 10 || length == 26 || length == 58) && str3.matches("[0-9A-Fa-f]*")) {
                        wifiConfiguration.wepKeys[0] = str3;
                        break;
                    } else {
                        String[] strArr = wifiConfiguration.wepKeys;
                        strArr[0] = '\"' + str3 + '\"';
                        break;
                    }
                }
                break;
            case 2:
                wifiConfiguration.allowedKeyManagement.set(1);
                if (str3.length() != 0) {
                    if (str3.matches("[0-9A-Fa-f]{64}")) {
                        wifiConfiguration.preSharedKey = str3;
                        break;
                    } else {
                        wifiConfiguration.preSharedKey = '\"' + str3 + '\"';
                        break;
                    }
                }
                break;
            case 3:
            case 6:
                wifiConfiguration.allowedKeyManagement.set(2);
                wifiConfiguration.allowedKeyManagement.set(3);
                if (i == 6) {
                    wifiConfiguration.allowedKeyManagement.set(WifiConfigurationConstant.KeyMgmtConstant.SUITE_B_192);
                    wifiConfiguration.requirePMF = true;
                    wifiConfiguration.allowedPairwiseCiphers.set(WifiConfigurationConstant.PairwiseCipherConstant.GCMP_256);
                    wifiConfiguration.allowedGroupCiphers.set(WifiConfigurationConstant.PairwiseCipherConstant.GCMP_256);
                    ReflectUtils.invokeMethodSetField(wifiConfiguration, "allowedGroupManagementCiphers", Integer.valueOf(WifiConfigurationConstant.GroupMgmtCipherConstant.BIP_GMAC_256));
                }
                wifiConfiguration.enterpriseConfig = new WifiEnterpriseConfig();
                wifiConfiguration.enterpriseConfig.setEapMethod(0);
                wifiConfiguration.enterpriseConfig.setPhase2Method(0);
                wifiConfiguration.enterpriseConfig.setCaCertificateAliases(null);
                wifiConfiguration.enterpriseConfig.setCaPath(null);
                wifiConfiguration.enterpriseConfig.setDomainSuffixMatch("");
                wifiConfiguration.enterpriseConfig.setClientCertificateAlias("");
                wifiConfiguration.enterpriseConfig.setIdentity(str2);
                wifiConfiguration.enterpriseConfig.setAnonymousIdentity("");
                wifiConfiguration.enterpriseConfig.setPassword(str3);
                wifiConfiguration.setIpConfiguration(new IpConfiguration(IpConfiguration.IpAssignment.UNASSIGNED, IpConfiguration.ProxySettings.UNASSIGNED, (StaticIpConfiguration) null, (ProxyInfo) null));
                break;
            case 4:
                wifiConfiguration.allowedKeyManagement.set(WifiConfigurationConstant.KeyMgmtConstant.OWE);
                wifiConfiguration.requirePMF = true;
                break;
            case 5:
                wifiConfiguration.allowedKeyManagement.set(WifiConfigurationConstant.KeyMgmtConstant.SAE);
                Logs.d("xpwifi SECURITY_SAE:" + WifiConfigurationConstant.KeyMgmtConstant.SAE);
                wifiConfiguration.requirePMF = true;
                if (str3.length() != 0) {
                    wifiConfiguration.preSharedKey = '\"' + str3 + '\"';
                    break;
                }
                break;
            default:
                return null;
        }
        return wifiConfiguration;
    }

    public static String getMeteredLabel(Context context, WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.meteredOverride == 1 || (wifiConfiguration.meteredHint && !isMeteredOverridden(wifiConfiguration))) {
            return context.getString(R.string.wifi_metered_label);
        }
        return context.getString(R.string.wifi_unmetered_label);
    }

    public static boolean isMeteredOverridden(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.meteredOverride != 0;
    }

    public static boolean getBooleanMethod(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        Object invokeMethod = ReflectUtils.invokeMethod(obj, str, clsArr, objArr);
        if (invokeMethod != null) {
            return ((Boolean) invokeMethod).booleanValue();
        }
        return false;
    }

    public static boolean isCertificateInstalled() {
        KeyStore keyStore = KeyStore.getInstance();
        return keyStore.contains("CACERT_xp-auto-ca", (int) PointerIconCompat.TYPE_ALIAS) || keyStore.contains("USRCERT_xp-auto-client", (int) PointerIconCompat.TYPE_ALIAS);
    }

    public static WifiConfiguration createXpAutoWifiInfo(String str) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        String str2 = SystemProperties.get("sys.xiaopeng.vin", "12345678");
        Logs.d("...vin = " + str2);
        wifiConfiguration.allowedKeyManagement.set(2);
        wifiConfiguration.allowedKeyManagement.set(3);
        wifiConfiguration.enterpriseConfig = new WifiEnterpriseConfig();
        wifiConfiguration.enterpriseConfig.setEapMethod(1);
        wifiConfiguration.enterpriseConfig.setPhase2Method(0);
        wifiConfiguration.SSID = str;
        wifiConfiguration.enterpriseConfig.setCaCertificateAlias(XP_AUTO_CA);
        wifiConfiguration.enterpriseConfig.setClientCertificateAlias(XP_AUTO_CLIENT);
        wifiConfiguration.enterpriseConfig.setIdentity(str2);
        wifiConfiguration.enterpriseConfig.setAnonymousIdentity("");
        wifiConfiguration.enterpriseConfig.setPassword("");
        wifiConfiguration.setProxySettings(IpConfiguration.ProxySettings.NONE);
        wifiConfiguration.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
        return wifiConfiguration;
    }
}
