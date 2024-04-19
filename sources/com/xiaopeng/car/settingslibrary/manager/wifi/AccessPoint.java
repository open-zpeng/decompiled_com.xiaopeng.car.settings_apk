package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkScoreManager;
import android.net.NetworkScorerAppData;
import android.net.ScoredNetwork;
import android.net.wifi.IWifiManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.internal.util.CollectionUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.AccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
/* loaded from: classes.dex */
public class AccessPoint implements Comparable<AccessPoint> {
    private static final int EAP_UNKNOWN = 0;
    private static final int EAP_WPA = 1;
    private static final int EAP_WPA2_WPA3 = 2;
    public static final int HIGHER_FREQ_24GHZ = 2500;
    public static final int HIGHER_FREQ_5GHZ = 5900;
    static final String KEY_CARRIER_AP_EAP_TYPE = "key_carrier_ap_eap_type";
    static final String KEY_CARRIER_NAME = "key_carrier_name";
    static final String KEY_CONFIG = "key_config";
    static final String KEY_EAPTYPE = "eap_psktype";
    static final String KEY_FQDN = "key_fqdn";
    static final String KEY_IS_CARRIER_AP = "key_is_carrier_ap";
    static final String KEY_IS_OWE_TRANSITION_MODE = "key_is_owe_transition_mode";
    static final String KEY_IS_PSK_SAE_TRANSITION_MODE = "key_is_psk_sae_transition_mode";
    static final String KEY_NETWORKINFO = "key_networkinfo";
    public static final String KEY_PREFIX_AP = "AP:";
    public static final String KEY_PREFIX_FQDN = "FQDN:";
    public static final String KEY_PREFIX_OSU = "OSU:";
    static final String KEY_PROVIDER_FRIENDLY_NAME = "key_provider_friendly_name";
    static final String KEY_PSKTYPE = "key_psktype";
    static final String KEY_SCANRESULTS = "key_scanresults";
    static final String KEY_SCOREDNETWORKCACHE = "key_scorednetworkcache";
    static final String KEY_SECURITY = "key_security";
    static final String KEY_SPEED = "key_speed";
    static final String KEY_SSID = "key_ssid";
    static final String KEY_WIFIINFO = "key_wifiinfo";
    public static final int LOWER_FREQ_24GHZ = 2400;
    public static final int LOWER_FREQ_5GHZ = 4900;
    private static final int PSK_UNKNOWN = 0;
    private static final int PSK_WPA = 1;
    private static final int PSK_WPA2 = 2;
    private static final int PSK_WPA_WPA2 = 3;
    public static final int SECURITY_EAP = 3;
    public static final int SECURITY_EAP_SUITE_B = 6;
    public static final int SECURITY_MAX_VAL = 7;
    public static final int SECURITY_NONE = 0;
    public static final int SECURITY_OWE = 4;
    public static final int SECURITY_PSK = 2;
    public static final int SECURITY_SAE = 5;
    public static final int SECURITY_WEP = 1;
    public static final int SIGNAL_LEVELS = 5;
    static final String TAG = "SettingsLib.AccessPoint";
    public static final int UNREACHABLE_RSSI = Integer.MIN_VALUE;
    static final AtomicInteger sLastId = new AtomicInteger(0);
    private String bssid;
    AccessPointListener mAccessPointListener;
    private int mCarrierApEapType;
    private String mCarrierName;
    private WifiConfiguration mConfig;
    private WifiManager.ActionListener mConnectListener;
    private final Context mContext;
    private int mEapType;
    private final ArraySet<ScanResult> mExtraScanResults;
    private String mFqdn;
    private WifiInfo mInfo;
    private boolean mIsCarrierAp;
    private boolean mIsOweTransitionMode;
    private boolean mIsPskSaeTransitionMode;
    private boolean mIsRoaming;
    private boolean mIsScoredNetworkMetered;
    private String mKey;
    private final Object mLock;
    private NetworkInfo mNetworkInfo;
    private String mOsuFailure;
    private OsuProvider mOsuProvider;
    private boolean mOsuProvisioningComplete;
    private String mOsuStatus;
    private String mProviderFriendlyName;
    private int mRssi;
    private final ArraySet<ScanResult> mScanResults;
    private final Map<String, TimestampedScoredNetwork> mScoredNetworkCache;
    private int mSpeed;
    private Object mTag;
    private WifiManager mWifiManager;
    private int networkId;
    private int pskType;
    private int security;
    private String ssid;

    /* loaded from: classes.dex */
    public interface AccessPointListener {
        void onAccessPointChanged(AccessPoint accessPoint);

        void onLevelChanged(AccessPoint accessPoint);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Speed {
        public static final int FAST = 20;
        public static final int MODERATE = 10;
        public static final int NONE = 0;
        public static final int SLOW = 5;
        public static final int VERY_FAST = 30;
    }

    private static int roundToClosestSpeedEnum(int i) {
        if (i < 5) {
            return 0;
        }
        if (i < 7) {
            return 5;
        }
        if (i < 15) {
            return 10;
        }
        return i < 25 ? 20 : 30;
    }

    public static String securityToString(int i, int i2) {
        return i == 1 ? "WEP" : i == 2 ? i2 == 1 ? "WPA" : i2 == 2 ? "WPA2" : i2 == 3 ? "WPA_WPA2" : "PSK" : i == 3 ? "EAP" : i == 5 ? "SAE" : i == 6 ? "SUITE_B" : i == 4 ? "OWE" : "NONE";
    }

    public AccessPoint(Context context, Bundle bundle) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        if (bundle.containsKey(KEY_CONFIG)) {
            this.mConfig = (WifiConfiguration) bundle.getParcelable(KEY_CONFIG);
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            loadConfig(wifiConfiguration);
        }
        if (bundle.containsKey(KEY_SSID)) {
            this.ssid = bundle.getString(KEY_SSID);
        }
        if (bundle.containsKey(KEY_SECURITY)) {
            this.security = bundle.getInt(KEY_SECURITY);
        }
        if (bundle.containsKey(KEY_SPEED)) {
            this.mSpeed = bundle.getInt(KEY_SPEED);
        }
        if (bundle.containsKey(KEY_PSKTYPE)) {
            this.pskType = bundle.getInt(KEY_PSKTYPE);
        }
        if (bundle.containsKey(KEY_EAPTYPE)) {
            this.mEapType = bundle.getInt(KEY_EAPTYPE);
        }
        this.mInfo = (WifiInfo) bundle.getParcelable(KEY_WIFIINFO);
        if (bundle.containsKey(KEY_NETWORKINFO)) {
            this.mNetworkInfo = (NetworkInfo) bundle.getParcelable(KEY_NETWORKINFO);
        }
        if (bundle.containsKey(KEY_SCANRESULTS)) {
            Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_SCANRESULTS);
            this.mScanResults.clear();
            for (Parcelable parcelable : parcelableArray) {
                this.mScanResults.add((ScanResult) parcelable);
            }
        }
        if (bundle.containsKey(KEY_SCOREDNETWORKCACHE)) {
            Iterator it = bundle.getParcelableArrayList(KEY_SCOREDNETWORKCACHE).iterator();
            while (it.hasNext()) {
                TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) it.next();
                this.mScoredNetworkCache.put(timestampedScoredNetwork.getScore().networkKey.wifiKey.bssid, timestampedScoredNetwork);
            }
        }
        if (bundle.containsKey(KEY_FQDN)) {
            this.mFqdn = bundle.getString(KEY_FQDN);
        }
        if (bundle.containsKey(KEY_PROVIDER_FRIENDLY_NAME)) {
            this.mProviderFriendlyName = bundle.getString(KEY_PROVIDER_FRIENDLY_NAME);
        }
        if (bundle.containsKey(KEY_IS_CARRIER_AP)) {
            this.mIsCarrierAp = bundle.getBoolean(KEY_IS_CARRIER_AP);
        }
        if (bundle.containsKey(KEY_CARRIER_AP_EAP_TYPE)) {
            this.mCarrierApEapType = bundle.getInt(KEY_CARRIER_AP_EAP_TYPE);
        }
        if (bundle.containsKey(KEY_CARRIER_NAME)) {
            this.mCarrierName = bundle.getString(KEY_CARRIER_NAME);
        }
        if (bundle.containsKey(KEY_IS_PSK_SAE_TRANSITION_MODE)) {
            this.mIsPskSaeTransitionMode = bundle.getBoolean(KEY_IS_PSK_SAE_TRANSITION_MODE);
        }
        if (bundle.containsKey(KEY_IS_OWE_TRANSITION_MODE)) {
            this.mIsOweTransitionMode = bundle.getBoolean(KEY_IS_OWE_TRANSITION_MODE);
        }
        update(this.mConfig, this.mInfo, this.mNetworkInfo);
        updateKey();
        updateBestRssiInfo();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        loadConfig(wifiConfiguration);
        updateKey();
    }

    public AccessPoint(Context context, PasspointConfiguration passpointConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        this.mFqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mProviderFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        updateKey();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration, Collection<ScanResult> collection, Collection<ScanResult> collection2) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
        this.mFqdn = wifiConfiguration.FQDN;
        setScanResultsPasspoint(collection, collection2);
        updateKey();
    }

    public AccessPoint(Context context, OsuProvider osuProvider, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        setScanResults(collection);
        updateKey();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AccessPoint(Context context, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mIsCarrierAp = false;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mCarrierApEapType = -1;
        this.mCarrierName = null;
        this.mContext = context;
        setScanResults(collection);
        updateKey();
    }

    void loadConfig(WifiConfiguration wifiConfiguration) {
        this.ssid = wifiConfiguration.SSID == null ? "" : removeDoubleQuotes(wifiConfiguration.SSID);
        this.bssid = wifiConfiguration.BSSID;
        this.security = getSecurity(wifiConfiguration);
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
    }

    private void updateKey() {
        if (isPasspoint()) {
            this.mKey = getKey(this.mConfig);
        } else if (isPasspointConfig()) {
            this.mKey = getKey(this.mFqdn);
        } else if (isOsuProvider()) {
            this.mKey = getKey(this.mOsuProvider);
        } else {
            this.mKey = getKey(getSsidStr(), getBssid(), getSecurity());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(AccessPoint accessPoint) {
        if (!isConnected() || accessPoint.isConnected()) {
            if (isConnected() || !accessPoint.isConnected()) {
                if (!isActive() || accessPoint.isActive()) {
                    if (isActive() || !accessPoint.isActive()) {
                        if (!isEverConnected() || accessPoint.isEverConnected()) {
                            if (isEverConnected() || !accessPoint.isEverConnected()) {
                                if (getSpeed() != accessPoint.getSpeed()) {
                                    return accessPoint.getSpeed() - getSpeed();
                                }
                                int calculateSignalLevel = WifiManager.calculateSignalLevel(accessPoint.mRssi, 5) - WifiManager.calculateSignalLevel(this.mRssi, 5);
                                if (calculateSignalLevel != 0) {
                                    return calculateSignalLevel;
                                }
                                int compareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
                                return compareToIgnoreCase != 0 ? compareToIgnoreCase : getSsidStr().compareTo(accessPoint.getSsidStr());
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AccessPoint) && getKey().equals(((AccessPoint) obj).getKey());
    }

    public int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        return (wifiInfo != null ? 0 + (wifiInfo.hashCode() * 13) : 0) + (this.mRssi * 19) + (this.networkId * 23) + (this.ssid.hashCode() * 29);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessPoint(");
        sb.append(this.ssid);
        if (this.bssid != null) {
            sb.append(":");
            sb.append(this.bssid);
        }
        if (isSaved()) {
            sb.append(',');
            sb.append("saved");
        }
        if (isActive()) {
            sb.append(',');
            sb.append("active");
        }
        if (isEphemeral()) {
            sb.append(',');
            sb.append("ephemeral");
        }
        if (isConnectable()) {
            sb.append(',');
            sb.append("connectable");
        }
        int i = this.security;
        if (i != 0 && i != 4) {
            sb.append(',');
            sb.append(securityToString(this.security, this.pskType));
        }
        sb.append(",level=");
        sb.append(getLevel());
        if (this.mSpeed != 0) {
            sb.append(",speed=");
            sb.append(this.mSpeed);
        }
        sb.append(",metered=");
        sb.append(isMetered());
        if (isVerboseLoggingEnabled()) {
            sb.append(",rssi=");
            sb.append(this.mRssi);
            synchronized (this.mLock) {
                sb.append(",scan cache size=");
                sb.append(this.mScanResults.size() + this.mExtraScanResults.size());
            }
        }
        sb.append(')');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean update(WifiNetworkScoreCache wifiNetworkScoreCache, boolean z, long j) {
        return updateMetered(wifiNetworkScoreCache) || (z ? updateScores(wifiNetworkScoreCache, j) : false);
    }

    private boolean updateScores(WifiNetworkScoreCache wifiNetworkScoreCache, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            Iterator<ScanResult> it = this.mScanResults.iterator();
            while (it.hasNext()) {
                ScanResult next = it.next();
                ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(next);
                if (scoredNetwork != null) {
                    TimestampedScoredNetwork timestampedScoredNetwork = this.mScoredNetworkCache.get(next.BSSID);
                    if (timestampedScoredNetwork == null) {
                        this.mScoredNetworkCache.put(next.BSSID, new TimestampedScoredNetwork(scoredNetwork, elapsedRealtime));
                    } else {
                        timestampedScoredNetwork.update(scoredNetwork, elapsedRealtime);
                    }
                }
            }
        }
        final long j2 = elapsedRealtime - j;
        final Iterator<TimestampedScoredNetwork> it2 = this.mScoredNetworkCache.values().iterator();
        it2.forEachRemaining(new Consumer() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$BELxyIQ7AZ-8RfVKac8lUTavRxM
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AccessPoint.lambda$updateScores$0(j2, it2, (TimestampedScoredNetwork) obj);
            }
        });
        return updateSpeed();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$updateScores$0(long j, Iterator it, TimestampedScoredNetwork timestampedScoredNetwork) {
        if (timestampedScoredNetwork.getUpdatedTimestampMillis() < j) {
            it.remove();
        }
    }

    private boolean updateSpeed() {
        int i = this.mSpeed;
        this.mSpeed = generateAverageSpeedForSsid();
        boolean z = i != this.mSpeed;
        if (isVerboseLoggingEnabled() && z) {
            Log.i(TAG, String.format("%s: Set speed to %d", this.ssid, Integer.valueOf(this.mSpeed)));
        }
        return z;
    }

    private int generateAverageSpeedForSsid() {
        if (this.mScoredNetworkCache.isEmpty()) {
            return 0;
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, String.format("Generating fallbackspeed for %s using cache: %s", getSsidStr(), this.mScoredNetworkCache));
        }
        int i = 0;
        int i2 = 0;
        for (TimestampedScoredNetwork timestampedScoredNetwork : this.mScoredNetworkCache.values()) {
            int calculateBadge = timestampedScoredNetwork.getScore().calculateBadge(this.mRssi);
            if (calculateBadge != 0) {
                i++;
                i2 += calculateBadge;
            }
        }
        int i3 = i == 0 ? 0 : i2 / i;
        if (isVerboseLoggingEnabled()) {
            Log.i(TAG, String.format("%s generated fallback speed is: %d", getSsidStr(), Integer.valueOf(i3)));
        }
        return roundToClosestSpeedEnum(i3);
    }

    private boolean updateMetered(WifiNetworkScoreCache wifiNetworkScoreCache) {
        WifiInfo wifiInfo;
        boolean z = this.mIsScoredNetworkMetered;
        this.mIsScoredNetworkMetered = false;
        if (isActive() && (wifiInfo = this.mInfo) != null) {
            ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork != null) {
                this.mIsScoredNetworkMetered = scoredNetwork.meteredHint | this.mIsScoredNetworkMetered;
            }
        } else {
            synchronized (this.mLock) {
                Iterator<ScanResult> it = this.mScanResults.iterator();
                while (it.hasNext()) {
                    ScoredNetwork scoredNetwork2 = wifiNetworkScoreCache.getScoredNetwork(it.next());
                    if (scoredNetwork2 != null) {
                        this.mIsScoredNetworkMetered = scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
                    }
                }
            }
        }
        return z == this.mIsScoredNetworkMetered;
    }

    public static String getKey(Context context, ScanResult scanResult) {
        return getKey(scanResult.SSID, scanResult.BSSID, getSecurity(context, scanResult));
    }

    public static String getKey(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return getKey(wifiConfiguration.FQDN);
        }
        return getKey(removeDoubleQuotes(wifiConfiguration.SSID), wifiConfiguration.BSSID, getSecurity(wifiConfiguration));
    }

    public static String getKey(String str) {
        return KEY_PREFIX_FQDN + str;
    }

    public static String getKey(OsuProvider osuProvider) {
        return KEY_PREFIX_OSU + osuProvider.getFriendlyName() + ',' + osuProvider.getServerUri();
    }

    private static String getKey(String str, String str2, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(KEY_PREFIX_AP);
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
        } else {
            sb.append(str);
        }
        sb.append(',');
        sb.append(i);
        return sb.toString();
    }

    public String getKey() {
        return this.mKey;
    }

    public boolean matches(AccessPoint accessPoint) {
        if (isPasspoint() || isPasspointConfig() || isOsuProvider()) {
            return getKey().equals(accessPoint.getKey());
        }
        if (isSameSsidOrBssid(accessPoint)) {
            int security = accessPoint.getSecurity();
            if (this.mIsPskSaeTransitionMode) {
                if ((security == 5 && WifiUtils.getBooleanMethod(getWifiManager(), "isWpa3SaeSupported", null, null)) || security == 2) {
                    return true;
                }
            } else {
                int i = this.security;
                if ((i == 5 || i == 2) && accessPoint.isPskSaeTransitionMode()) {
                    return true;
                }
            }
            if (this.mIsOweTransitionMode) {
                if ((security == 4 && WifiUtils.getBooleanMethod(getWifiManager(), "isEnhancedOpenSupported", null, null)) || security == 0) {
                    return true;
                }
            } else {
                int i2 = this.security;
                if ((i2 == 4 || i2 == 0) && accessPoint.isOweTransitionMode()) {
                    return true;
                }
            }
            return this.security == accessPoint.getSecurity();
        }
        return false;
    }

    public boolean matches(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return isPasspoint() && wifiConfiguration.FQDN.equals(this.mConfig.FQDN);
        } else if (this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID))) {
            WifiConfiguration wifiConfiguration2 = this.mConfig;
            if (wifiConfiguration2 == null || wifiConfiguration2.shared == wifiConfiguration.shared) {
                int security = getSecurity(wifiConfiguration);
                if (this.mIsPskSaeTransitionMode && ((security == 5 && WifiUtils.getBooleanMethod(getWifiManager(), "isWpa3SaeSupported", null, null)) || security == 2)) {
                    return true;
                }
                return (this.mIsOweTransitionMode && ((security == 4 && WifiUtils.getBooleanMethod(getWifiManager(), "isEnhancedOpenSupported", null, null)) || security == 0)) || this.security == getSecurity(wifiConfiguration);
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean matches(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo) {
        if (wifiConfiguration == null || wifiInfo == null) {
            return false;
        }
        if (wifiConfiguration.isPasspoint() || isSameSsidOrBssid(wifiInfo)) {
            return matches(wifiConfiguration);
        }
        return false;
    }

    boolean matches(ScanResult scanResult) {
        if (scanResult == null) {
            return false;
        }
        if (isPasspoint() || isOsuProvider()) {
            throw new IllegalStateException("Should not matches a Passpoint by ScanResult");
        }
        if (isSameSsidOrBssid(scanResult)) {
            if (this.mIsPskSaeTransitionMode) {
                if ((scanResult.capabilities.contains("SAE") && WifiUtils.getBooleanMethod(getWifiManager(), "isWpa3SaeSupported", null, null)) || scanResult.capabilities.contains("PSK")) {
                    return true;
                }
            } else {
                int i = this.security;
                if ((i == 5 || i == 2) && isPskSaeTransitionMode(scanResult)) {
                    return true;
                }
            }
            if (this.mIsOweTransitionMode) {
                int security = getSecurity(this.mContext, scanResult);
                if ((security == 4 && WifiUtils.getBooleanMethod(getWifiManager(), "isEnhancedOpenSupported", null, null)) || security == 0) {
                    return true;
                }
            } else {
                int i2 = this.security;
                if ((i2 == 4 || i2 == 0) && isOweTransitionMode(scanResult)) {
                    return true;
                }
            }
            return this.security == getSecurity(this.mContext, scanResult);
        }
        return false;
    }

    public WifiConfiguration getConfig() {
        return this.mConfig;
    }

    public String getPasspointFqdn() {
        return this.mFqdn;
    }

    public void clearConfig() {
        this.mConfig = null;
        this.networkId = -1;
    }

    public WifiInfo getInfo() {
        return this.mInfo;
    }

    public int getLevel() {
        return WifiManager.calculateSignalLevel(this.mRssi, 5);
    }

    public int getRssi() {
        return this.mRssi;
    }

    public Set<ScanResult> getScanResults() {
        ArraySet arraySet = new ArraySet();
        synchronized (this.mLock) {
            arraySet.addAll((Collection) this.mScanResults);
            arraySet.addAll((Collection) this.mExtraScanResults);
        }
        return arraySet;
    }

    public Map<String, TimestampedScoredNetwork> getScoredNetworkCache() {
        return this.mScoredNetworkCache;
    }

    private void updateBestRssiInfo() {
        ScanResult scanResult;
        int i;
        int i2;
        if (isActive()) {
            return;
        }
        synchronized (this.mLock) {
            Iterator<ScanResult> it = this.mScanResults.iterator();
            scanResult = null;
            i = Integer.MIN_VALUE;
            while (it.hasNext()) {
                ScanResult next = it.next();
                if (next.level > i) {
                    i = next.level;
                    scanResult = next;
                }
            }
        }
        if (i != Integer.MIN_VALUE && (i2 = this.mRssi) != Integer.MIN_VALUE) {
            this.mRssi = (i2 + i) / 2;
        } else {
            this.mRssi = i;
        }
        if (scanResult != null) {
            this.ssid = scanResult.SSID;
            this.bssid = scanResult.BSSID;
            this.security = getSecurity(this.mContext, scanResult);
            int i3 = this.security;
            if (i3 == 2 || i3 == 5) {
                this.pskType = getPskType(scanResult);
            }
            if (this.security == 3) {
                this.mEapType = getEapType(scanResult);
            }
            this.mIsPskSaeTransitionMode = isPskSaeTransitionMode(scanResult);
            this.mIsOweTransitionMode = isOweTransitionMode(scanResult);
            this.mIsCarrierAp = scanResult.isCarrierAp;
            this.mCarrierApEapType = scanResult.carrierApEapType;
            this.mCarrierName = scanResult.carrierName;
        }
        if (isPasspoint()) {
            this.mConfig.SSID = convertToQuotedString(this.ssid);
        }
    }

    public boolean isMetered() {
        return this.mIsScoredNetworkMetered || WifiConfiguration.isMetered(this.mConfig, this.mInfo);
    }

    public NetworkInfo getNetworkInfo() {
        return this.mNetworkInfo;
    }

    public int getSecurity() {
        return this.security;
    }

    public String getSecurityString(boolean z) {
        Context context = this.mContext;
        if (isPasspoint() || isPasspointConfig()) {
            if (z) {
                return context.getString(R.string.wifi_security_short_eap);
            }
            return context.getString(R.string.wifi_security_eap);
        } else if (this.mIsPskSaeTransitionMode) {
            if (z) {
                return context.getString(R.string.wifi_security_short_psk_sae);
            }
            return context.getString(R.string.wifi_security_psk_sae);
        } else {
            switch (this.security) {
                case 1:
                    if (z) {
                        return context.getString(R.string.wifi_security_short_wep);
                    }
                    return context.getString(R.string.wifi_security_wep);
                case 2:
                    int i = this.pskType;
                    if (i == 1) {
                        if (z) {
                            return context.getString(R.string.wifi_security_short_wpa);
                        }
                        return context.getString(R.string.wifi_security_wpa);
                    } else if (i == 2) {
                        if (z) {
                            return context.getString(R.string.wifi_security_short_wpa2);
                        }
                        return context.getString(R.string.wifi_security_wpa2);
                    } else if (i != 3) {
                        if (z) {
                            return context.getString(R.string.wifi_security_short_psk_generic);
                        }
                        return context.getString(R.string.wifi_security_psk_generic);
                    } else if (z) {
                        return context.getString(R.string.wifi_security_short_wpa_wpa2);
                    } else {
                        return context.getString(R.string.wifi_security_wpa_wpa2);
                    }
                case 3:
                    int i2 = this.mEapType;
                    if (i2 == 1) {
                        if (z) {
                            return context.getString(R.string.wifi_security_short_eap_wpa);
                        }
                        return context.getString(R.string.wifi_security_eap_wpa);
                    } else if (i2 != 2) {
                        if (z) {
                            return context.getString(R.string.wifi_security_short_eap);
                        }
                        return context.getString(R.string.wifi_security_eap);
                    } else if (z) {
                        return context.getString(R.string.wifi_security_short_eap_wpa2_wpa3);
                    } else {
                        return context.getString(R.string.wifi_security_eap_wpa2_wpa3);
                    }
                case 4:
                    if (z) {
                        return context.getString(R.string.wifi_security_short_owe);
                    }
                    return context.getString(R.string.wifi_security_owe);
                case 5:
                    if (z) {
                        return context.getString(R.string.wifi_security_short_sae);
                    }
                    return context.getString(R.string.wifi_security_sae);
                case 6:
                    if (z) {
                        return context.getString(R.string.wifi_security_short_eap_suiteb);
                    }
                    return context.getString(R.string.wifi_security_eap_suiteb);
                default:
                    return z ? "" : context.getString(R.string.wifi_security_none);
            }
        }
    }

    public String getSsidStr() {
        return this.ssid;
    }

    public String getBssid() {
        return this.bssid;
    }

    public CharSequence getSsid() {
        return this.ssid;
    }

    @Deprecated
    public String getConfigName() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null && wifiConfiguration.isPasspoint()) {
            return this.mConfig.providerFriendlyName;
        }
        if (this.mFqdn != null) {
            return this.mProviderFriendlyName;
        }
        return this.ssid;
    }

    public NetworkInfo.DetailedState getDetailedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            return networkInfo.getDetailedState();
        }
        Log.w(TAG, "NetworkInfo is null, cannot return detailed state");
        return null;
    }

    public boolean isCarrierAp() {
        return this.mIsCarrierAp;
    }

    public int getCarrierApEapType() {
        return this.mCarrierApEapType;
    }

    public String getCarrierName() {
        return this.mCarrierName;
    }

    public String getSavedNetworkSummary() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            String nameForUid = packageManager.getNameForUid(1000);
            int userId = UserHandle.getUserId(wifiConfiguration.creatorUid);
            ApplicationInfo applicationInfo = null;
            if (wifiConfiguration.creatorName != null && wifiConfiguration.creatorName.equals(nameForUid)) {
                applicationInfo = this.mContext.getApplicationInfo();
            } else {
                try {
                    applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(wifiConfiguration.creatorName, 0, userId);
                } catch (RemoteException unused) {
                }
            }
            return (applicationInfo == null || applicationInfo.packageName.equals(this.mContext.getString(R.string.settings_package)) || applicationInfo.packageName.equals(this.mContext.getString(R.string.certinstaller_package))) ? "" : this.mContext.getString(R.string.saved_network, applicationInfo.loadLabel(packageManager));
        }
        return "";
    }

    public String getTitle() {
        if (isPasspoint()) {
            return this.mConfig.providerFriendlyName;
        }
        if (isPasspointConfig()) {
            return this.mProviderFriendlyName;
        }
        if (isOsuProvider()) {
            return this.mOsuProvider.getFriendlyName();
        }
        return getSsidStr();
    }

    public String getSummary() {
        return getSettingsSummary();
    }

    public String getSettingsSummary() {
        return getSettingsSummary(false);
    }

    public String getSettingsSummary(boolean z) {
        StringBuilder sb = new StringBuilder();
        if (isOsuProvider()) {
            if (this.mOsuProvisioningComplete) {
                sb.append(this.mContext.getString(R.string.osu_sign_up_complete));
            } else {
                String str = this.mOsuFailure;
                if (str != null) {
                    sb.append(str);
                } else {
                    String str2 = this.mOsuStatus;
                    if (str2 != null) {
                        sb.append(str2);
                    } else {
                        sb.append(this.mContext.getString(R.string.tap_to_sign_up));
                    }
                }
            }
        } else if (isActive()) {
            if (getDetailedState() == NetworkInfo.DetailedState.CONNECTED && this.mIsCarrierAp) {
                sb.append(String.format(this.mContext.getString(R.string.connected_via_carrier), this.mCarrierName));
            } else {
                Context context = this.mContext;
                NetworkInfo.DetailedState detailedState = getDetailedState();
                WifiInfo wifiInfo = this.mInfo;
                boolean z2 = wifiInfo != null && wifiInfo.isEphemeral();
                WifiInfo wifiInfo2 = this.mInfo;
                sb.append(getSummary(context, null, detailedState, z2, wifiInfo2 != null ? (String) ReflectUtils.invokeMethod(wifiInfo2, "getNetworkSuggestionOrSpecifierPackageName", null, null) : null));
            }
        } else {
            if (this.mConfig != null) {
                Logs.d("XPWifi NetworkSelectionStatus: " + this.mConfig.getNetworkSelectionStatus().toString());
            }
            WifiConfiguration wifiConfiguration = this.mConfig;
            if (wifiConfiguration != null && wifiConfiguration.hasNoInternetAccess()) {
                sb.append(this.mContext.getString(this.mConfig.getNetworkSelectionStatus().isNetworkPermanentlyDisabled() ? R.string.wifi_no_internet_no_reconnect : R.string.wifi_no_internet));
            } else {
                WifiConfiguration wifiConfiguration2 = this.mConfig;
                if (wifiConfiguration2 != null && !wifiConfiguration2.getNetworkSelectionStatus().isNetworkEnabled()) {
                    int networkSelectionDisableReason = this.mConfig.getNetworkSelectionStatus().getNetworkSelectionDisableReason();
                    if (networkSelectionDisableReason == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_AUTHENTICATION_FAILURE) {
                        sb.append(this.mContext.getString(R.string.wifi_disabled_password_failure));
                    } else if (networkSelectionDisableReason == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_BY_WRONG_PASSWORD) {
                        sb.append(this.mContext.getString(R.string.wifi_check_password_try_again));
                    } else if (networkSelectionDisableReason == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_DHCP_FAILURE || networkSelectionDisableReason == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_DNS_FAILURE) {
                        sb.append(this.mContext.getString(R.string.wifi_disabled_network_failure));
                    } else if (networkSelectionDisableReason == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_ASSOCIATION_REJECTION) {
                        sb.append(this.mContext.getString(R.string.wifi_disabled_generic));
                    }
                } else {
                    WifiConfiguration wifiConfiguration3 = this.mConfig;
                    if (wifiConfiguration3 != null && wifiConfiguration3.getNetworkSelectionStatus().isNotRecommended()) {
                        sb.append(this.mContext.getString(R.string.wifi_disabled_by_recommendation_provider));
                    } else {
                        WifiConfiguration wifiConfiguration4 = this.mConfig;
                        if (wifiConfiguration4 != null && !wifiConfiguration4.getNetworkSelectionStatus().isNetworkTemporaryDisabled()) {
                            if (this.mConfig.getNetworkSelectionStatus().getNetworkSelectionDisableReason() == WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_ASSOCIATION_REJECTION) {
                                sb.append(this.mContext.getString(R.string.wifi_disabled_generic));
                            }
                        } else if (this.mIsCarrierAp) {
                            sb.append(String.format(this.mContext.getString(R.string.available_via_carrier), this.mCarrierName));
                        } else if (!isReachable()) {
                            sb.append(this.mContext.getString(R.string.wifi_not_in_range));
                        } else {
                            WifiConfiguration wifiConfiguration5 = this.mConfig;
                            if (wifiConfiguration5 != null) {
                                if (wifiConfiguration5.recentFailure.getAssociationStatus() == WifiUtils.WifiConfigurationConstant.RecentFailureConstant.STATUS_AP_UNABLE_TO_HANDLE_NEW_STA) {
                                    sb.append(this.mContext.getString(R.string.wifi_ap_unable_to_handle_new_sta));
                                } else if (z) {
                                    sb.append(this.mContext.getString(R.string.wifi_disconnected));
                                } else {
                                    sb.append(this.mContext.getString(R.string.wifi_remembered));
                                }
                            }
                        }
                    }
                }
            }
        }
        WifiConfiguration wifiConfiguration6 = this.mConfig;
        if (wifiConfiguration6 != null && (WifiUtils.isMeteredOverridden(wifiConfiguration6) || this.mConfig.meteredHint)) {
            return this.mContext.getResources().getString(R.string.preference_summary_default_combination, WifiUtils.getMeteredLabel(this.mContext, this.mConfig), sb.toString());
        }
        if (getSpeedLabel() != null && sb.length() != 0) {
            return this.mContext.getResources().getString(R.string.preference_summary_default_combination, getSpeedLabel(), sb.toString());
        }
        if (getSpeedLabel() != null) {
            return getSpeedLabel();
        }
        return sb.toString();
    }

    public boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        return (networkInfo == null || (this.networkId == -1 && networkInfo.getState() == NetworkInfo.State.DISCONNECTED)) ? false : true;
    }

    public boolean isConnectable() {
        return getLevel() != -1 && getDetailedState() == null;
    }

    public boolean isEphemeral() {
        NetworkInfo networkInfo;
        WifiInfo wifiInfo = this.mInfo;
        return (wifiInfo == null || !wifiInfo.isEphemeral() || (networkInfo = this.mNetworkInfo) == null || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) ? false : true;
    }

    public boolean isPasspoint() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        return wifiConfiguration != null && wifiConfiguration.isPasspoint();
    }

    public boolean isPasspointConfig() {
        return this.mFqdn != null && this.mConfig == null;
    }

    public boolean isOsuProvider() {
        return this.mOsuProvider != null;
    }

    public void startOsuProvisioning(WifiManager.ActionListener actionListener) {
        this.mConnectListener = actionListener;
    }

    private boolean isInfoForThisAccessPoint(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo) {
        if (WifiUtils.getBooleanMethod(wifiInfo, "isOsuAp", null, null) || this.mOsuStatus != null) {
            return WifiUtils.getBooleanMethod(wifiInfo, "isOsuAp", null, null) && this.mOsuStatus != null;
        } else if (WifiUtils.getBooleanMethod(wifiInfo, "isPasspointAp", null, null) || isPasspoint()) {
            return WifiUtils.getBooleanMethod(wifiInfo, "isPasspointAp", null, null) && isPasspoint() && TextUtils.equals((String) ReflectUtils.invokeMethod(wifiInfo, "getPasspointFqdn", null, null), this.mConfig.FQDN);
        } else {
            int i = this.networkId;
            if (i != -1) {
                return i == wifiInfo.getNetworkId();
            } else if (wifiConfiguration != null) {
                return matches(wifiConfiguration, wifiInfo);
            } else {
                return TextUtils.equals(removeDoubleQuotes(wifiInfo.getSSID()), this.ssid);
            }
        }
    }

    public boolean isSaved() {
        return this.mConfig != null;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public void generateOpenNetworkConfig() {
        int i = this.security;
        if (i != 0 && i != 4) {
            throw new IllegalStateException();
        }
        if (this.mConfig != null) {
            return;
        }
        this.mConfig = new WifiConfiguration();
        this.mConfig.SSID = convertToQuotedString(this.ssid);
        if (this.security == 0 || !WifiUtils.getBooleanMethod(getWifiManager(), "isEasyConnectSupported", null, null)) {
            this.mConfig.allowedKeyManagement.set(0);
            return;
        }
        this.mConfig.allowedKeyManagement.set(WifiUtils.WifiConfigurationConstant.KeyMgmtConstant.OWE);
        this.mConfig.requirePMF = true;
    }

    public void saveWifiState(Bundle bundle) {
        if (this.ssid != null) {
            bundle.putString(KEY_SSID, getSsidStr());
        }
        bundle.putInt(KEY_SECURITY, this.security);
        bundle.putInt(KEY_SPEED, this.mSpeed);
        bundle.putInt(KEY_PSKTYPE, this.pskType);
        bundle.putInt(KEY_EAPTYPE, this.mEapType);
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            bundle.putParcelable(KEY_CONFIG, wifiConfiguration);
        }
        bundle.putParcelable(KEY_WIFIINFO, this.mInfo);
        synchronized (this.mLock) {
            bundle.putParcelableArray(KEY_SCANRESULTS, (Parcelable[]) this.mScanResults.toArray(new Parcelable[this.mScanResults.size() + this.mExtraScanResults.size()]));
        }
        bundle.putParcelableArrayList(KEY_SCOREDNETWORKCACHE, new ArrayList<>(this.mScoredNetworkCache.values()));
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            bundle.putParcelable(KEY_NETWORKINFO, networkInfo);
        }
        String str = this.mFqdn;
        if (str != null) {
            bundle.putString(KEY_FQDN, str);
        }
        String str2 = this.mProviderFriendlyName;
        if (str2 != null) {
            bundle.putString(KEY_PROVIDER_FRIENDLY_NAME, str2);
        }
        bundle.putBoolean(KEY_IS_CARRIER_AP, this.mIsCarrierAp);
        bundle.putInt(KEY_CARRIER_AP_EAP_TYPE, this.mCarrierApEapType);
        bundle.putString(KEY_CARRIER_NAME, this.mCarrierName);
        bundle.putBoolean(KEY_IS_PSK_SAE_TRANSITION_MODE, this.mIsPskSaeTransitionMode);
        bundle.putBoolean(KEY_IS_OWE_TRANSITION_MODE, this.mIsOweTransitionMode);
    }

    public void setListener(AccessPointListener accessPointListener) {
        this.mAccessPointListener = accessPointListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setScanResults(Collection<ScanResult> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            Log.d(TAG, "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint() && !isOsuProvider()) {
            for (ScanResult scanResult : collection) {
                if (!matches(scanResult)) {
                    Log.d(TAG, String.format("ScanResult %s\nkey of %s did not match current AP key %s", scanResult, getKey(this.mContext, scanResult), this.mKey));
                    return;
                }
            }
        }
        int level = getLevel();
        synchronized (this.mLock) {
            this.mScanResults.clear();
            this.mScanResults.addAll(collection);
        }
        updateBestRssiInfo();
        int level2 = getLevel();
        if (level2 > 0 && level2 != level) {
            updateSpeed();
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$nY6EwFneEKJpyRwZVQ7pjIc7cR8
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.this.lambda$setScanResults$1$AccessPoint();
                }
            });
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$crIB53txCchrgKTeSMc9C-mwsuA
            @Override // java.lang.Runnable
            public final void run() {
                AccessPoint.this.lambda$setScanResults$2$AccessPoint();
            }
        });
    }

    public /* synthetic */ void lambda$setScanResults$1$AccessPoint() {
        AccessPointListener accessPointListener = this.mAccessPointListener;
        if (accessPointListener != null) {
            accessPointListener.onLevelChanged(this);
        }
    }

    public /* synthetic */ void lambda$setScanResults$2$AccessPoint() {
        AccessPointListener accessPointListener = this.mAccessPointListener;
        if (accessPointListener != null) {
            accessPointListener.onAccessPointChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setScanResultsPasspoint(Collection<ScanResult> collection, Collection<ScanResult> collection2) {
        synchronized (this.mLock) {
            this.mExtraScanResults.clear();
            if (!CollectionUtils.isEmpty(collection)) {
                this.mIsRoaming = false;
                if (!CollectionUtils.isEmpty(collection2)) {
                    this.mExtraScanResults.addAll(collection2);
                }
                setScanResults(collection);
            } else if (!CollectionUtils.isEmpty(collection2)) {
                this.mIsRoaming = true;
                setScanResults(collection2);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0046, code lost:
        if (r5.getDetailedState() != r7.getDetailedState()) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean update(android.net.wifi.WifiConfiguration r5, android.net.wifi.WifiInfo r6, android.net.NetworkInfo r7) {
        /*
            r4 = this;
            int r0 = r4.getLevel()
            r1 = 0
            r2 = 1
            if (r6 == 0) goto L4e
            boolean r3 = r4.isInfoForThisAccessPoint(r5, r6)
            if (r3 == 0) goto L4e
            android.net.wifi.WifiInfo r3 = r4.mInfo
            if (r3 != 0) goto L13
            r1 = r2
        L13:
            boolean r3 = r4.isPasspoint()
            if (r3 != 0) goto L20
            android.net.wifi.WifiConfiguration r3 = r4.mConfig
            if (r3 == r5) goto L20
            r4.update(r5)
        L20:
            int r5 = r4.mRssi
            int r3 = r6.getRssi()
            if (r5 == r3) goto L38
            int r5 = r6.getRssi()
            r3 = -127(0xffffffffffffff81, float:NaN)
            if (r5 == r3) goto L38
            int r5 = r6.getRssi()
            r4.mRssi = r5
        L36:
            r1 = r2
            goto L49
        L38:
            android.net.NetworkInfo r5 = r4.mNetworkInfo
            if (r5 == 0) goto L49
            if (r7 == 0) goto L49
            android.net.NetworkInfo$DetailedState r5 = r5.getDetailedState()
            android.net.NetworkInfo$DetailedState r3 = r7.getDetailedState()
            if (r5 == r3) goto L49
            goto L36
        L49:
            r4.mInfo = r6
            r4.mNetworkInfo = r7
            goto L58
        L4e:
            android.net.wifi.WifiInfo r5 = r4.mInfo
            if (r5 == 0) goto L58
            r5 = 0
            r4.mInfo = r5
            r4.mNetworkInfo = r5
            r1 = r2
        L58:
            if (r1 == 0) goto L74
            com.xiaopeng.car.settingslibrary.manager.wifi.AccessPoint$AccessPointListener r5 = r4.mAccessPointListener
            if (r5 == 0) goto L74
            com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$nwAUrFT4Nto5-qq-GI0mVYYvqdg r5 = new com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$nwAUrFT4Nto5-qq-GI0mVYYvqdg
            r5.<init>()
            com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils.postOnMainThread(r5)
            int r5 = r4.getLevel()
            if (r0 == r5) goto L74
            com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$ci1dZOUcC2NFVf5R2BZLJMydOVg r5 = new com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$ci1dZOUcC2NFVf5R2BZLJMydOVg
            r5.<init>()
            com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils.postOnMainThread(r5)
        L74:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.wifi.AccessPoint.update(android.net.wifi.WifiConfiguration, android.net.wifi.WifiInfo, android.net.NetworkInfo):boolean");
    }

    public /* synthetic */ void lambda$update$3$AccessPoint() {
        AccessPointListener accessPointListener = this.mAccessPointListener;
        if (accessPointListener != null) {
            accessPointListener.onAccessPointChanged(this);
        }
    }

    public /* synthetic */ void lambda$update$4$AccessPoint() {
        AccessPointListener accessPointListener = this.mAccessPointListener;
        if (accessPointListener != null) {
            accessPointListener.onLevelChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void update(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
        if (this.mConfig != null && !isPasspoint()) {
            this.ssid = removeDoubleQuotes(this.mConfig.SSID);
        }
        this.networkId = wifiConfiguration != null ? wifiConfiguration.networkId : -1;
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$j9nJwkzH4fmKiRd5UrAUareOclA
            @Override // java.lang.Runnable
            public final void run() {
                AccessPoint.this.lambda$update$5$AccessPoint();
            }
        });
    }

    public /* synthetic */ void lambda$update$5$AccessPoint() {
        AccessPointListener accessPointListener = this.mAccessPointListener;
        if (accessPointListener != null) {
            accessPointListener.onAccessPointChanged(this);
        }
    }

    void setRssi(int i) {
        this.mRssi = i;
    }

    void setUnreachable() {
        setRssi(Integer.MIN_VALUE);
    }

    int getSpeed() {
        return this.mSpeed;
    }

    String getSpeedLabel() {
        return getSpeedLabel(this.mSpeed);
    }

    String getSpeedLabel(int i) {
        return getSpeedLabel(this.mContext, i);
    }

    private static String getSpeedLabel(Context context, int i) {
        if (i != 5) {
            if (i != 10) {
                if (i != 20) {
                    if (i != 30) {
                        return null;
                    }
                    return context.getString(R.string.speed_label_very_fast);
                }
                return context.getString(R.string.speed_label_fast);
            }
            return context.getString(R.string.speed_label_okay);
        }
        return context.getString(R.string.speed_label_slow);
    }

    public static String getSpeedLabel(Context context, ScoredNetwork scoredNetwork, int i) {
        return getSpeedLabel(context, roundToClosestSpeedEnum(scoredNetwork.calculateBadge(i)));
    }

    public boolean isReachable() {
        return this.mRssi != Integer.MIN_VALUE;
    }

    private static CharSequence getAppLabel(String str, PackageManager packageManager) {
        try {
            ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(-2));
            return applicationInfoAsUser != null ? applicationInfoAsUser.loadLabel(packageManager) : "";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to get app info", e);
            return "";
        }
    }

    public static String getSummary(Context context, String str, NetworkInfo.DetailedState detailedState, boolean z, String str2) {
        if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
            if (z && !TextUtils.isEmpty(str2)) {
                return context.getString(R.string.connected_via_app, getAppLabel(str2, context.getPackageManager()));
            } else if (z) {
                NetworkScorerAppData activeScorer = ((NetworkScoreManager) context.getSystemService(NetworkScoreManager.class)).getActiveScorer();
                return (activeScorer == null || activeScorer.getRecommendationServiceLabel() == null) ? context.getString(R.string.connected_via_network_scorer_default) : String.format(context.getString(R.string.connected_via_network_scorer), activeScorer.getRecommendationServiceLabel());
            }
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
            NetworkCapabilities networkCapabilities = null;
            try {
                networkCapabilities = connectivityManager.getNetworkCapabilities(IWifiManager.Stub.asInterface(ServiceManager.getService("wifi")).getCurrentNetwork());
            } catch (RemoteException unused) {
            }
            if (networkCapabilities != null) {
                if (networkCapabilities.hasCapability(17)) {
                    return context.getString(context.getResources().getIdentifier("network_available_sign_in", TypedValues.Custom.S_STRING, "android"));
                }
                if (networkCapabilities.hasCapability(WifiUtils.NetworkCapabilitiesConstant.NET_CAPABILITY_PARTIAL_CONNECTIVITY)) {
                    return context.getString(R.string.wifi_limited_connection);
                }
                if (!networkCapabilities.hasCapability(16)) {
                    return context.getString(R.string.wifi_connected_no_internet);
                }
            }
        }
        if (detailedState == null) {
            Log.w(TAG, "state is null, returning empty summary");
            return "";
        }
        String[] stringArray = context.getResources().getStringArray(str == null ? R.array.wifi_status : R.array.wifi_status_with_ssid);
        int ordinal = detailedState.ordinal();
        return (ordinal >= stringArray.length || stringArray[ordinal].length() == 0) ? "" : String.format(stringArray[ordinal], str);
    }

    public static String convertToQuotedString(String str) {
        return "\"" + str + "\"";
    }

    private static int getPskType(ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WPA-PSK");
        boolean contains2 = scanResult.capabilities.contains("RSN-PSK");
        boolean contains3 = scanResult.capabilities.contains("RSN-SAE");
        if (contains2 && contains) {
            return 3;
        }
        if (contains2) {
            return 2;
        }
        if (contains) {
            return 1;
        }
        if (contains3) {
            return 0;
        }
        Log.w(TAG, "Received abnormal flag string: " + scanResult.capabilities);
        return 0;
    }

    private static int getEapType(ScanResult scanResult) {
        if (scanResult.capabilities.contains("RSN-EAP")) {
            return 2;
        }
        return scanResult.capabilities.contains("WPA-EAP") ? 1 : 0;
    }

    private static int getSecurity(Context context, ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WEP");
        boolean contains2 = scanResult.capabilities.contains("SAE");
        boolean contains3 = scanResult.capabilities.contains("PSK");
        boolean contains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean contains5 = scanResult.capabilities.contains("EAP");
        boolean contains6 = scanResult.capabilities.contains("OWE");
        boolean contains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        if (contains2 && contains3) {
            return WifiUtils.getBooleanMethod((WifiManager) context.getSystemService("wifi"), "isWpa3SaeSupported", null, null) ? 5 : 2;
        } else if (contains7) {
            return WifiUtils.getBooleanMethod((WifiManager) context.getSystemService("wifi"), "isEnhancedOpenSupported", null, null) ? 4 : 0;
        } else if (contains) {
            return 1;
        } else {
            if (contains2) {
                return 5;
            }
            if (contains3) {
                return 2;
            }
            if (contains4) {
                return 6;
            }
            if (contains5) {
                return 3;
            }
            return contains6 ? 4 : 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(WifiUtils.WifiConfigurationConstant.KeyMgmtConstant.SAE)) {
            return 5;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(WifiUtils.WifiConfigurationConstant.KeyMgmtConstant.SUITE_B_192)) {
            return 6;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(WifiUtils.WifiConfigurationConstant.KeyMgmtConstant.OWE)) {
            return 4;
        }
        return wifiConfiguration.wepKeys[0] != null ? 1 : 0;
    }

    static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        return this.mWifiManager;
    }

    private static boolean isVerboseLoggingEnabled() {
        return WifiTracker.sVerboseLogging || Log.isLoggable(TAG, 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class AccessPointProvisioningCallback extends ProvisioningCallback {
        AccessPointProvisioningCallback() {
        }

        public void onProvisioningFailure(int i) {
            if (TextUtils.equals(AccessPoint.this.mOsuStatus, AccessPoint.this.mContext.getString(R.string.osu_completing_sign_up))) {
                AccessPoint accessPoint = AccessPoint.this;
                accessPoint.mOsuFailure = accessPoint.mContext.getString(R.string.osu_sign_up_failed);
            } else {
                AccessPoint accessPoint2 = AccessPoint.this;
                accessPoint2.mOsuFailure = accessPoint2.mContext.getString(R.string.osu_connect_failed);
            }
            AccessPoint.this.mOsuStatus = null;
            AccessPoint.this.mOsuProvisioningComplete = false;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$AccessPointProvisioningCallback$6e0bK4wvtwPI5XqUV8bk5KodfXA
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.AccessPointProvisioningCallback.this.lambda$onProvisioningFailure$0$AccessPoint$AccessPointProvisioningCallback();
                }
            });
        }

        public /* synthetic */ void lambda$onProvisioningFailure$0$AccessPoint$AccessPointProvisioningCallback() {
            if (AccessPoint.this.mAccessPointListener != null) {
                AccessPoint.this.mAccessPointListener.onAccessPointChanged(AccessPoint.this);
            }
        }

        public void onProvisioningStatus(int i) {
            String format;
            if (i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_AP_CONNECTING || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_AP_CONNECTED || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_SERVER_CONNECTING || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_SERVER_VALIDATED || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_SERVER_CONNECTED || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_INIT_SOAP_EXCHANGE || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_WAITING_FOR_REDIRECT_RESPONSE) {
                format = String.format(AccessPoint.this.mContext.getString(R.string.osu_opening_provider), AccessPoint.this.mOsuProvider.getFriendlyName());
            } else {
                format = (i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_REDIRECT_RESPONSE_RECEIVED || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_SECOND_SOAP_EXCHANGE || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_THIRD_SOAP_EXCHANGE || i == WifiUtils.ProvisioningCallbackConstant.OSU_STATUS_RETRIEVING_TRUST_ROOT_CERTS) ? AccessPoint.this.mContext.getString(R.string.osu_completing_sign_up) : null;
            }
            boolean z = !TextUtils.equals(AccessPoint.this.mOsuStatus, format);
            AccessPoint.this.mOsuStatus = format;
            AccessPoint.this.mOsuFailure = null;
            AccessPoint.this.mOsuProvisioningComplete = false;
            if (z) {
                ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$AccessPointProvisioningCallback$1Js0tYj35B3kKf27sv7aWtuqpmg
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessPoint.AccessPointProvisioningCallback.this.lambda$onProvisioningStatus$1$AccessPoint$AccessPointProvisioningCallback();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onProvisioningStatus$1$AccessPoint$AccessPointProvisioningCallback() {
            if (AccessPoint.this.mAccessPointListener != null) {
                AccessPoint.this.mAccessPointListener.onAccessPointChanged(AccessPoint.this);
            }
        }

        public void onProvisioningComplete() {
            AccessPoint.this.mOsuProvisioningComplete = true;
            AccessPoint.this.mOsuFailure = null;
            AccessPoint.this.mOsuStatus = null;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$AccessPoint$AccessPointProvisioningCallback$sTh7ujI4qPSTiVbseJSBMb6eH_Y
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.AccessPointProvisioningCallback.this.lambda$onProvisioningComplete$2$AccessPoint$AccessPointProvisioningCallback();
                }
            });
            WifiManager wifiManager = AccessPoint.this.getWifiManager();
            PasspointConfiguration passpointConfiguration = (PasspointConfiguration) ((Map) ReflectUtils.invokeMethod(wifiManager, "getMatchingPasspointConfigsForOsuProviders", new Class[]{Set.class}, new Object[]{Collections.singleton(AccessPoint.this.mOsuProvider)})).get(AccessPoint.this.mOsuProvider);
            if (passpointConfiguration == null) {
                Log.e(AccessPoint.TAG, "Missing PasspointConfiguration for newly provisioned network!");
                if (AccessPoint.this.mConnectListener != null) {
                    AccessPoint.this.mConnectListener.onFailure(0);
                    return;
                }
                return;
            }
            String fqdn = passpointConfiguration.getHomeSp().getFqdn();
            for (Pair pair : (List) ReflectUtils.invokeMethod(wifiManager, "getAllMatchingWifiConfigs", new Class[]{List.class}, new Object[]{wifiManager.getScanResults()})) {
                WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
                if (TextUtils.equals(wifiConfiguration.FQDN, fqdn)) {
                    wifiManager.connect(new AccessPoint(AccessPoint.this.mContext, wifiConfiguration, (List) ((Map) pair.second).get(Integer.valueOf(WifiUtils.WifiManagerConstant.PASSPOINT_HOME_NETWORK)), (List) ((Map) pair.second).get(Integer.valueOf(WifiUtils.WifiManagerConstant.PASSPOINT_ROAMING_NETWORK))).getConfig(), AccessPoint.this.mConnectListener);
                    return;
                }
            }
            if (AccessPoint.this.mConnectListener != null) {
                AccessPoint.this.mConnectListener.onFailure(0);
            }
        }

        public /* synthetic */ void lambda$onProvisioningComplete$2$AccessPoint$AccessPointProvisioningCallback() {
            if (AccessPoint.this.mAccessPointListener != null) {
                AccessPoint.this.mAccessPointListener.onAccessPointChanged(AccessPoint.this);
            }
        }
    }

    public boolean isPskSaeTransitionMode() {
        return this.mIsPskSaeTransitionMode;
    }

    public boolean isOweTransitionMode() {
        return this.mIsOweTransitionMode;
    }

    private static boolean isPskSaeTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains("SAE");
    }

    private static boolean isOweTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("OWE_TRANSITION");
    }

    private boolean isSameSsidOrBssid(ScanResult scanResult) {
        if (scanResult == null) {
            return false;
        }
        if (TextUtils.equals(this.ssid, scanResult.SSID)) {
            return true;
        }
        return scanResult.BSSID != null && TextUtils.equals(this.bssid, scanResult.BSSID);
    }

    private boolean isSameSsidOrBssid(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            return false;
        }
        if (TextUtils.equals(this.ssid, removeDoubleQuotes(wifiInfo.getSSID()))) {
            return true;
        }
        return wifiInfo.getBSSID() != null && TextUtils.equals(this.bssid, wifiInfo.getBSSID());
    }

    private boolean isSameSsidOrBssid(AccessPoint accessPoint) {
        if (accessPoint == null) {
            return false;
        }
        if (TextUtils.equals(this.ssid, accessPoint.getSsid())) {
            return true;
        }
        return accessPoint.getBssid() != null && TextUtils.equals(this.bssid, accessPoint.getBssid());
    }

    public boolean isConnected() {
        NetworkInfo networkInfo;
        return isActive() && (networkInfo = this.mNetworkInfo) != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    public boolean isConnecting() {
        NetworkInfo networkInfo;
        return isActive() && (networkInfo = this.mNetworkInfo) != null && networkInfo.getState() == NetworkInfo.State.CONNECTING;
    }

    public boolean isEverConnected() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        return (wifiConfiguration == null || wifiConfiguration.getNetworkSelectionStatus() == null || !this.mConfig.getNetworkSelectionStatus().getHasEverConnected()) ? false : true;
    }
}
