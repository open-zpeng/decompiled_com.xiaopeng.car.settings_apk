package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.ScoredNetwork;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/* loaded from: classes.dex */
public class WifiTracker implements LifecycleObserver {
    private static final long DEFAULT_MAX_CACHED_SCORE_AGE_MILLIS = 1200000;
    static final long MAX_SCAN_RESULT_AGE_MILLIS = 15000;
    private static final String TAG = "WifiTracker";
    private static final int WIFI_RESCAN_INTERVAL_MS = 10000;
    private static final String WIFI_SECURITY_EAP = "EAP";
    private static final String WIFI_SECURITY_OWE = "OWE";
    private static final String WIFI_SECURITY_PSK = "PSK";
    private static final String WIFI_SECURITY_SAE = "SAE";
    private static final String WIFI_SECURITY_SUITE_B_192 = "SUITE_B_192";
    public static boolean sVerboseLogging;
    private final AtomicBoolean mConnected;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private final IntentFilter mFilter;
    private final List<AccessPoint> mInternalAccessPoints;
    private WifiInfo mLastInfo;
    private NetworkInfo mLastNetworkInfo;
    private boolean mLastScanSucceeded;
    private final WifiListenerExecutor mListener;
    private final Object mLock;
    private long mMaxSpeedLabelScoreCacheAge;
    private WifiTrackerNetworkCallback mNetworkCallback;
    private final NetworkRequest mNetworkRequest;
    private final NetworkScoreManager mNetworkScoreManager;
    private boolean mNetworkScoringUiEnabled;
    final BroadcastReceiver mReceiver;
    private boolean mRegistered;
    private final Set<NetworkKey> mRequestedScores;
    private final HashMap<String, ScanResult> mScanResultCache;
    Scanner mScanner;
    private WifiNetworkScoreCache mScoreCache;
    private boolean mStaleScanResults;
    private final WifiManager mWifiManager;
    Handler mWorkHandler;
    private HandlerThread mWorkThread;

    /* loaded from: classes.dex */
    public interface WifiListener {
        void onAccessPointsChanged();

        void onConnectedChanged();

        void onWifiStateChanged(int i);
    }

    static /* synthetic */ boolean access$1000() {
        return isVerboseLoggingEnabled();
    }

    private static final boolean DBG() {
        return Log.isLoggable(TAG, 3);
    }

    private static boolean isVerboseLoggingEnabled() {
        return sVerboseLogging || Log.isLoggable(TAG, 2);
    }

    private static IntentFilter newIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.NETWORK_IDS_CHANGED");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        intentFilter.addAction("android.net.wifi.LINK_CONFIGURATION_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        return intentFilter;
    }

    @Deprecated
    public WifiTracker(Context context, WifiListener wifiListener, boolean z, boolean z2) {
        this(context, wifiListener, (WifiManager) context.getSystemService(WifiManager.class), (ConnectivityManager) context.getSystemService(ConnectivityManager.class), (NetworkScoreManager) context.getSystemService(NetworkScoreManager.class), newIntentFilter());
    }

    public WifiTracker(Context context, WifiListener wifiListener, Lifecycle lifecycle, boolean z, boolean z2) {
        this(context, wifiListener, (WifiManager) context.getSystemService(WifiManager.class), (ConnectivityManager) context.getSystemService(ConnectivityManager.class), (NetworkScoreManager) context.getSystemService(NetworkScoreManager.class), newIntentFilter());
        lifecycle.addObserver(this);
    }

    WifiTracker(Context context, WifiListener wifiListener, WifiManager wifiManager, ConnectivityManager connectivityManager, NetworkScoreManager networkScoreManager, IntentFilter intentFilter) {
        boolean z = false;
        this.mConnected = new AtomicBoolean(false);
        this.mLock = new Object();
        this.mInternalAccessPoints = new ArrayList();
        this.mRequestedScores = new ArraySet();
        this.mStaleScanResults = true;
        this.mLastScanSucceeded = true;
        this.mScanResultCache = new HashMap<>();
        this.mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                    WifiTracker.this.updateWifiState(intent.getIntExtra("wifi_state", 4));
                } else if ("android.net.wifi.SCAN_RESULTS".equals(action)) {
                    WifiTracker.this.mStaleScanResults = false;
                    WifiTracker.this.mLastScanSucceeded = intent.getBooleanExtra("resultsUpdated", true);
                    WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                } else if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action) || "android.net.wifi.LINK_CONFIGURATION_CHANGED".equals(action)) {
                    WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                } else if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                    WifiTracker.this.updateNetworkInfo((NetworkInfo) intent.getParcelableExtra("networkInfo"));
                    WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                } else if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                    WifiTracker.this.updateNetworkInfo(WifiTracker.this.mConnectivityManager.getNetworkInfo(WifiTracker.this.mWifiManager.getCurrentNetwork()));
                }
            }
        };
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mListener = new WifiListenerExecutor(wifiListener);
        this.mConnectivityManager = connectivityManager;
        WifiManager wifiManager2 = this.mWifiManager;
        if (wifiManager2 != null && wifiManager2.getVerboseLoggingLevel() > 0) {
            z = true;
        }
        sVerboseLogging = z;
        this.mFilter = intentFilter;
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
        this.mNetworkScoreManager = networkScoreManager;
        HandlerThread handlerThread = new HandlerThread("WifiTracker{" + Integer.toHexString(System.identityHashCode(this)) + "}", 10);
        handlerThread.start();
        setWorkThread(handlerThread);
    }

    void setWorkThread(HandlerThread handlerThread) {
        this.mWorkThread = handlerThread;
        this.mWorkHandler = new Handler(handlerThread.getLooper());
        this.mScoreCache = new WifiNetworkScoreCache(this.mContext, new WifiNetworkScoreCache.CacheListener(this.mWorkHandler) { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.1
            public void networkCacheUpdated(List<ScoredNetwork> list) {
                if (WifiTracker.this.mRegistered) {
                    if (Log.isLoggable(WifiTracker.TAG, 2)) {
                        Log.v(WifiTracker.TAG, "Score cache was updated with networks: " + list);
                    }
                    WifiTracker.this.updateNetworkScores();
                }
            }
        });
    }

    public void onDestroy() {
        this.mWorkThread.quit();
    }

    private void pauseScanning() {
        synchronized (this.mLock) {
            if (this.mScanner != null) {
                this.mScanner.pause();
                this.mScanner = null;
            }
        }
        this.mStaleScanResults = true;
    }

    public void resumeScanning() {
        synchronized (this.mLock) {
            if (this.mScanner == null) {
                this.mScanner = new Scanner();
            }
            if (isWifiEnabled()) {
                this.mScanner.resume();
            }
        }
    }

    public void onStart() {
        forceUpdate();
        registerScoreCache();
        this.mNetworkScoringUiEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "network_scoring_ui_enabled", 0) == 1;
        this.mMaxSpeedLabelScoreCacheAge = Settings.Global.getLong(this.mContext.getContentResolver(), "speed_label_cache_eviction_age_millis", DEFAULT_MAX_CACHED_SCORE_AGE_MILLIS);
        resumeScanning();
        if (this.mRegistered) {
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, null, this.mWorkHandler);
        this.mNetworkCallback = new WifiTrackerNetworkCallback();
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback, this.mWorkHandler);
        this.mRegistered = true;
    }

    void forceUpdate() {
        this.mLastInfo = this.mWifiManager.getConnectionInfo();
        this.mLastNetworkInfo = this.mConnectivityManager.getNetworkInfo(this.mWifiManager.getCurrentNetwork());
        fetchScansAndConfigsAndUpdateAccessPoints();
    }

    private void registerScoreCache() {
        this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mScoreCache, 2);
    }

    private void requestScoresForNetworkKeys(Collection<NetworkKey> collection) {
        if (collection.isEmpty()) {
            return;
        }
        if (DBG()) {
            Log.d(TAG, "Requesting scores for Network Keys: " + collection);
        }
        this.mNetworkScoreManager.requestScores((NetworkKey[]) collection.toArray(new NetworkKey[collection.size()]));
        synchronized (this.mLock) {
            this.mRequestedScores.addAll(collection);
        }
    }

    public void onStop() {
        if (this.mRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mRegistered = false;
        }
        unregisterScoreCache();
        pauseScanning();
        this.mWorkHandler.removeCallbacksAndMessages(null);
    }

    private void unregisterScoreCache() {
        this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mScoreCache);
        synchronized (this.mLock) {
            this.mRequestedScores.clear();
        }
    }

    public List<AccessPoint> getAccessPoints() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mInternalAccessPoints);
        }
        return arrayList;
    }

    public WifiManager getManager() {
        return this.mWifiManager;
    }

    public boolean isWifiEnabled() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    public int getNumSavedNetworks() {
        return WifiSavedConfigUtils.getAllConfigs(this.mContext, this.mWifiManager).size();
    }

    public boolean isConnected() {
        return this.mConnected.get();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("  - wifi tracker ------");
        Iterator<AccessPoint> it = getAccessPoints().iterator();
        while (it.hasNext()) {
            printWriter.println("  " + it.next());
        }
    }

    private ArrayMap<String, List<ScanResult>> updateScanResultCache(List<ScanResult> list) {
        List<ScanResult> list2;
        for (ScanResult scanResult : list) {
            if (scanResult.SSID != null && !scanResult.SSID.isEmpty()) {
                this.mScanResultCache.put(scanResult.BSSID, scanResult);
            }
        }
        evictOldScans();
        ArrayMap<String, List<ScanResult>> arrayMap = new ArrayMap<>();
        for (ScanResult scanResult2 : this.mScanResultCache.values()) {
            if (scanResult2.SSID != null && scanResult2.SSID.length() != 0 && !scanResult2.capabilities.contains("[IBSS]")) {
                String key = AccessPoint.getKey(this.mContext, scanResult2);
                if (arrayMap.containsKey(key)) {
                    list2 = arrayMap.get(key);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayMap.put(key, arrayList);
                    list2 = arrayList;
                }
                list2.add(scanResult2);
            }
        }
        return arrayMap;
    }

    private void evictOldScans() {
        long j = this.mLastScanSucceeded ? MAX_SCAN_RESULT_AGE_MILLIS : 30000L;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator<ScanResult> it = this.mScanResultCache.values().iterator();
        while (it.hasNext()) {
            if (elapsedRealtime - (it.next().timestamp / 1000) > j) {
                it.remove();
            }
        }
    }

    private WifiConfiguration getWifiConfigurationForNetworkId(int i, List<WifiConfiguration> list) {
        if (list != null) {
            for (WifiConfiguration wifiConfiguration : list) {
                if (this.mLastInfo != null && i == wifiConfiguration.networkId && (!wifiConfiguration.selfAdded || wifiConfiguration.numAssociation != 0)) {
                    return wifiConfiguration;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchScansAndConfigsAndUpdateAccessPoints() {
        List<ScanResult> filterScanResultsByCapabilities = filterScanResultsByCapabilities(this.mWifiManager.getScanResults());
        if (isVerboseLoggingEnabled()) {
            Log.i(TAG, "Fetched scan results: " + filterScanResultsByCapabilities);
        }
        updateAccessPoints(filterScanResultsByCapabilities, this.mWifiManager.getConfiguredNetworks());
    }

    private void updateAccessPoints(List<ScanResult> list, List<WifiConfiguration> list2) {
        boolean z;
        WifiInfo wifiInfo = this.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), list2) : null;
        synchronized (this.mLock) {
            ArrayMap<String, List<ScanResult>> updateScanResultCache = updateScanResultCache(list);
            List<AccessPoint> arrayList = new ArrayList<>(this.mInternalAccessPoints);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (Map.Entry<String, List<ScanResult>> entry : updateScanResultCache.entrySet()) {
                for (ScanResult scanResult : entry.getValue()) {
                    NetworkKey createFromScanResult = NetworkKey.createFromScanResult(scanResult);
                    if (createFromScanResult != null && !this.mRequestedScores.contains(createFromScanResult)) {
                        arrayList3.add(createFromScanResult);
                    }
                }
                final AccessPoint cachedOrCreate = getCachedOrCreate(entry.getValue(), arrayList);
                List list3 = (List) list2.stream().filter(new Predicate() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$WifiTracker$byOfw6R56mW7lPw-K7mMBKLMQM8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean matches;
                        matches = AccessPoint.this.matches((WifiConfiguration) obj);
                        return matches;
                    }
                }).collect(Collectors.toList());
                int size = list3.size();
                if (size == 0) {
                    cachedOrCreate.update(null);
                } else if (size == 1) {
                    cachedOrCreate.update((WifiConfiguration) list3.get(0));
                } else {
                    Optional findFirst = list3.stream().filter(new Predicate() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$WifiTracker$wXMOkK9495YvDbcGnFzWFm0uERk
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isSaeOrOwe;
                            isSaeOrOwe = WifiTracker.isSaeOrOwe((WifiConfiguration) obj);
                            return isSaeOrOwe;
                        }
                    }).findFirst();
                    if (findFirst.isPresent()) {
                        cachedOrCreate.update((WifiConfiguration) findFirst.get());
                    } else {
                        cachedOrCreate.update((WifiConfiguration) list3.get(0));
                    }
                }
                arrayList2.add(cachedOrCreate);
            }
            ArrayList arrayList4 = new ArrayList(this.mScanResultCache.values());
            arrayList2.addAll(updatePasspointAccessPoints((List) ReflectUtils.invokeMethod(this.mWifiManager, "getAllMatchingWifiConfigs", new Class[]{List.class}, new Object[]{arrayList4}), arrayList));
            arrayList2.addAll(updateOsuAccessPoints((Map) ReflectUtils.invokeMethod(this.mWifiManager, "getMatchingOsuProviders", new Class[]{List.class}, new Object[]{arrayList4}), arrayList));
            if (this.mLastInfo != null && this.mLastNetworkInfo != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((AccessPoint) it.next()).update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                }
            }
            if (arrayList2.isEmpty() && wifiConfigurationForNetworkId != null) {
                AccessPoint accessPoint = new AccessPoint(this.mContext, wifiConfigurationForNetworkId);
                accessPoint.update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                arrayList2.add(accessPoint);
                arrayList3.add(NetworkKey.createFromWifiInfo(this.mLastInfo));
            }
            requestScoresForNetworkKeys(arrayList3);
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                ((AccessPoint) it2.next()).update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge);
            }
            Collections.sort(arrayList2);
            if (DBG()) {
                Log.d(TAG, "------ Dumping AccessPoints that were not seen on this scan ------");
                for (AccessPoint accessPoint2 : this.mInternalAccessPoints) {
                    String title = accessPoint2.getTitle();
                    Iterator it3 = arrayList2.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z = false;
                            break;
                        }
                        AccessPoint accessPoint3 = (AccessPoint) it3.next();
                        if (accessPoint3.getTitle() != null && accessPoint3.getTitle().equals(title)) {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        Log.d(TAG, "Did not find " + title + " in this scan");
                    }
                }
                Log.d(TAG, "---- Done dumping AccessPoints that were not seen on this scan ----");
            }
            this.mInternalAccessPoints.clear();
            this.mInternalAccessPoints.addAll(arrayList2);
        }
        conditionallyNotifyListeners();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSaeOrOwe(WifiConfiguration wifiConfiguration) {
        int security = AccessPoint.getSecurity(wifiConfiguration);
        return security == 5 || security == 4;
    }

    List<AccessPoint> updatePasspointAccessPoints(List<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>> list, List<AccessPoint> list2) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        ArraySet arraySet = new ArraySet();
        for (Pair<WifiConfiguration, Map<Integer, List<ScanResult>>> pair : list) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            if (arraySet.add(wifiConfiguration.FQDN)) {
                arrayList.add(getCachedOrCreatePasspoint(wifiConfiguration, (List) ((Map) pair.second).get(Integer.valueOf(WifiUtils.WifiManagerConstant.PASSPOINT_HOME_NETWORK)), (List) ((Map) pair.second).get(Integer.valueOf(WifiUtils.WifiManagerConstant.PASSPOINT_ROAMING_NETWORK)), list2));
            }
        }
        return arrayList;
    }

    List<AccessPoint> updateOsuAccessPoints(Map<OsuProvider, List<ScanResult>> map, List<AccessPoint> list) {
        Map map2;
        ArrayList arrayList = new ArrayList();
        if (map == null || (map2 = (Map) ReflectUtils.invokeMethod(this.mWifiManager, "getMatchingPasspointConfigsForOsuProviders", new Class[]{Set.class}, new Object[]{map.keySet()})) == null) {
            return arrayList;
        }
        Set keySet = map2.keySet();
        for (OsuProvider osuProvider : map.keySet()) {
            if (!keySet.contains(osuProvider)) {
                arrayList.add(getCachedOrCreateOsu(osuProvider, map.get(osuProvider), list));
            }
        }
        return arrayList;
    }

    private AccessPoint getCachedOrCreate(List<ScanResult> list, List<AccessPoint> list2) {
        AccessPoint cachedByKey = getCachedByKey(list2, AccessPoint.getKey(this.mContext, list.get(0)));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, list);
        }
        cachedByKey.setScanResults(list);
        return cachedByKey;
    }

    private AccessPoint getCachedOrCreatePasspoint(WifiConfiguration wifiConfiguration, List<ScanResult> list, List<ScanResult> list2, List<AccessPoint> list3) {
        AccessPoint cachedByKey = getCachedByKey(list3, AccessPoint.getKey(wifiConfiguration));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, wifiConfiguration, list, list2);
        }
        cachedByKey.update(wifiConfiguration);
        cachedByKey.setScanResultsPasspoint(list, list2);
        return cachedByKey;
    }

    private AccessPoint getCachedOrCreateOsu(OsuProvider osuProvider, List<ScanResult> list, List<AccessPoint> list2) {
        AccessPoint cachedByKey = getCachedByKey(list2, AccessPoint.getKey(osuProvider));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, osuProvider, list);
        }
        cachedByKey.setScanResults(list);
        return cachedByKey;
    }

    private AccessPoint getCachedByKey(List<AccessPoint> list, String str) {
        ListIterator<AccessPoint> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            AccessPoint next = listIterator.next();
            if (next.getKey().equals(str)) {
                listIterator.remove();
                return next;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkInfo(NetworkInfo networkInfo) {
        if (!isWifiEnabled()) {
            clearAccessPointsAndConditionallyUpdate();
            return;
        }
        if (networkInfo != null) {
            this.mLastNetworkInfo = networkInfo;
            if (DBG()) {
                Log.d(TAG, "mLastNetworkInfo set: " + this.mLastNetworkInfo);
            }
            if (networkInfo.isConnected() != this.mConnected.getAndSet(networkInfo.isConnected())) {
                Logs.d("wifitraker onConnectedChanged");
                this.mListener.onConnectedChanged();
            }
        }
        this.mLastInfo = this.mWifiManager.getConnectionInfo();
        if (DBG()) {
            Log.d(TAG, "mLastInfo set as: " + this.mLastInfo);
        }
        WifiInfo wifiInfo = this.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), this.mWifiManager.getConfiguredNetworks()) : null;
        synchronized (this.mLock) {
            boolean z = false;
            boolean z2 = false;
            for (int size = this.mInternalAccessPoints.size() - 1; size >= 0; size--) {
                AccessPoint accessPoint = this.mInternalAccessPoints.get(size);
                boolean isActive = accessPoint.isActive();
                if (accessPoint.update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo)) {
                    if (isActive != accessPoint.isActive()) {
                        z = true;
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (accessPoint.update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge)) {
                    z = true;
                    z2 = true;
                }
            }
            if (z) {
                Collections.sort(this.mInternalAccessPoints);
            }
            if (z2) {
                conditionallyNotifyListeners();
            }
        }
    }

    private void clearAccessPointsAndConditionallyUpdate() {
        synchronized (this.mLock) {
            if (!this.mInternalAccessPoints.isEmpty()) {
                this.mInternalAccessPoints.clear();
                conditionallyNotifyListeners();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkScores() {
        synchronized (this.mLock) {
            boolean z = false;
            for (int i = 0; i < this.mInternalAccessPoints.size(); i++) {
                if (this.mInternalAccessPoints.get(i).update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge)) {
                    z = true;
                }
            }
            if (z) {
                Collections.sort(this.mInternalAccessPoints);
                conditionallyNotifyListeners();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWifiState(int i) {
        if (isVerboseLoggingEnabled()) {
            Log.d(TAG, "updateWifiState: " + i);
        }
        if (i == 3) {
            synchronized (this.mLock) {
                if (this.mScanner != null) {
                    this.mScanner.resume();
                }
            }
        } else {
            clearAccessPointsAndConditionallyUpdate();
            this.mLastInfo = null;
            this.mLastNetworkInfo = null;
            synchronized (this.mLock) {
                if (this.mScanner != null) {
                    this.mScanner.pause();
                }
            }
            this.mStaleScanResults = true;
        }
        this.mListener.onWifiStateChanged(i);
    }

    /* loaded from: classes.dex */
    private final class WifiTrackerNetworkCallback extends ConnectivityManager.NetworkCallback {
        private WifiTrackerNetworkCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            if (network.equals(WifiTracker.this.mWifiManager.getCurrentNetwork())) {
                WifiTracker.this.updateNetworkInfo(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class Scanner extends Handler {
        static final int MSG_SCAN = 0;
        private int mRetry = 0;

        Scanner() {
        }

        void resume() {
            if (WifiTracker.access$1000()) {
                Log.d(WifiTracker.TAG, "Scanner resume");
            }
            if (hasMessages(0)) {
                return;
            }
            sendEmptyMessage(0);
        }

        void pause() {
            if (WifiTracker.access$1000()) {
                Log.d(WifiTracker.TAG, "Scanner pause");
            }
            this.mRetry = 0;
            removeMessages(0);
        }

        boolean isScanning() {
            return hasMessages(0);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (WifiTracker.this.mWifiManager.startScan()) {
                this.mRetry = 0;
            } else {
                int i = this.mRetry + 1;
                this.mRetry = i;
                if (i >= 3) {
                    this.mRetry = 0;
                    if (WifiTracker.this.mContext != null) {
                        Toast.makeText(WifiTracker.this.mContext, R.string.wifi_fail_to_scan, 1).show();
                        return;
                    }
                    return;
                }
            }
            sendEmptyMessageDelayed(0, 10000L);
        }
    }

    /* loaded from: classes.dex */
    private static class Multimap<K, V> {
        private final HashMap<K, List<V>> store = new HashMap<>();

        private Multimap() {
        }

        List<V> getAll(K k) {
            List<V> list = this.store.get(k);
            return list != null ? list : Collections.emptyList();
        }

        void put(K k, V v) {
            List<V> list = this.store.get(k);
            if (list == null) {
                list = new ArrayList<>(3);
                this.store.put(k, list);
            }
            list.add(v);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class WifiListenerExecutor implements WifiListener {
        private final WifiListener mDelegatee;

        public WifiListenerExecutor(WifiListener wifiListener) {
            this.mDelegatee = wifiListener;
        }

        public /* synthetic */ void lambda$onWifiStateChanged$0$WifiTracker$WifiListenerExecutor(int i) {
            this.mDelegatee.onWifiStateChanged(i);
        }

        @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
        public void onWifiStateChanged(final int i) {
            runAndLog(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$WifiTracker$WifiListenerExecutor$xbq4vnLo11HXEdo47QabOYkHTkw
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTracker.WifiListenerExecutor.this.lambda$onWifiStateChanged$0$WifiTracker$WifiListenerExecutor(i);
                }
            }, String.format("Invoking onWifiStateChanged callback with state %d", Integer.valueOf(i)));
        }

        @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
        public void onConnectedChanged() {
            final WifiListener wifiListener = this.mDelegatee;
            wifiListener.getClass();
            runAndLog(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$GgLU0cT1gFXIo76n5tjDmmWJW2k
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTracker.WifiListener.this.onConnectedChanged();
                }
            }, "Invoking onConnectedChanged callback");
        }

        @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
        public void onAccessPointsChanged() {
            final WifiListener wifiListener = this.mDelegatee;
            wifiListener.getClass();
            runAndLog(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$ZbHMpEI3aY9Fy4ChWlEwivleOe8
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTracker.WifiListener.this.onAccessPointsChanged();
                }
            }, "Invoking onAccessPointsChanged callback");
        }

        private void runAndLog(final Runnable runnable, final String str) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.wifi.-$$Lambda$WifiTracker$WifiListenerExecutor$pVR4Jy2a5dfMdzNxUzyhtfgxU8s
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTracker.WifiListenerExecutor.this.lambda$runAndLog$1$WifiTracker$WifiListenerExecutor(str, runnable);
                }
            });
        }

        public /* synthetic */ void lambda$runAndLog$1$WifiTracker$WifiListenerExecutor(String str, Runnable runnable) {
            if (WifiTracker.this.mRegistered) {
                if (WifiTracker.access$1000()) {
                    Log.i(WifiTracker.TAG, str);
                }
                runnable.run();
            }
        }
    }

    private void conditionallyNotifyListeners() {
        Logs.d("wifitraker conditionallyNotifyListeners mStaleScanResults:" + this.mStaleScanResults);
        if (this.mStaleScanResults) {
            return;
        }
        this.mListener.onAccessPointsChanged();
    }

    private List<ScanResult> filterScanResultsByCapabilities(List<ScanResult> list) {
        if (list == null) {
            return null;
        }
        boolean booleanMethod = WifiUtils.getBooleanMethod(this.mWifiManager, "isEnhancedOpenSupported", null, null);
        boolean booleanMethod2 = WifiUtils.getBooleanMethod(this.mWifiManager, "isWpa3SaeSupported", null, null);
        boolean booleanMethod3 = WifiUtils.getBooleanMethod(this.mWifiManager, "isWpa3SuiteBSupported", null, null);
        ArrayList arrayList = new ArrayList();
        for (ScanResult scanResult : list) {
            if (scanResult.capabilities.contains(WIFI_SECURITY_PSK)) {
                arrayList.add(scanResult);
            } else if ((scanResult.capabilities.contains(WIFI_SECURITY_SUITE_B_192) && !booleanMethod3) || ((scanResult.capabilities.contains(WIFI_SECURITY_SAE) && !booleanMethod2) || (scanResult.capabilities.contains(WIFI_SECURITY_OWE) && !booleanMethod))) {
                if (isVerboseLoggingEnabled()) {
                    Log.v(TAG, "filterScanResultsByCapabilities: Filtering SSID " + scanResult.SSID + " with capabilities: " + scanResult.capabilities);
                }
            } else {
                arrayList.add(scanResult);
            }
        }
        return arrayList;
    }
}
