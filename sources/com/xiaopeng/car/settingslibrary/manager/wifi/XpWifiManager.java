package com.xiaopeng.car.settingslibrary.manager.wifi;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.NetworkUtils;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class XpWifiManager implements WifiTracker.WifiListener {
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9A-F]+$");
    public static final int HIDDEN_NETWORK = 1;
    public static final int NOT_HIDDEN_NETWORK = 0;
    private static volatile XpWifiManager sInstance;
    private final Context mContext;
    private boolean mStarted;
    private WifiManager mWifiManager;
    private WifiTracker mWifiTracker;
    private List<Listener> mListenerList = new ArrayList();
    CopyOnWriteArrayList<XpAccessPoint> mAccessPointList = new CopyOnWriteArrayList<>();
    private int mStartCount = 0;

    /* loaded from: classes.dex */
    public interface Listener {
        void onAccessPointsChanged();

        void onWifiStateChanged(int i);
    }

    /* loaded from: classes.dex */
    public interface PopupPwdDialogCallback {
        void popupPwdDialog(XpAccessPoint xpAccessPoint);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
    public void onConnectedChanged() {
    }

    public void stopTethering() {
    }

    public static XpWifiManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (XpWifiManager.class) {
                if (sInstance == null) {
                    sInstance = new XpWifiManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private XpWifiManager(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        this.mWifiTracker = new WifiTracker(context, this, true, true);
    }

    public void addListener(Listener listener) {
        if (this.mListenerList.contains(listener)) {
            return;
        }
        this.mListenerList.add(listener);
    }

    public void removeListener(Listener listener) {
        this.mListenerList.remove(listener);
    }

    public void disableEphemeralNetwork(String str) {
        this.mWifiManager.disableEphemeralNetwork(str);
    }

    private static boolean isHexString(String str) {
        return HEX_PATTERN.matcher(str).matches();
    }

    public boolean isTetherable() {
        return this.mWifiManager.getWifiApState() == 13;
    }

    public void start() {
        this.mStartCount++;
        Logs.d("xpwifi manager start " + this.mStartCount);
        if (this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mWifiTracker.onStart();
    }

    public void stop() {
        Logs.d("xpwifi manager stop:" + this.mStartCount);
        int i = this.mStartCount;
        if (i > 1) {
            this.mStartCount = i - 1;
            return;
        }
        if (this.mStarted) {
            this.mStarted = false;
            this.mWifiTracker.onStop();
        }
        this.mStartCount--;
    }

    public void destroy() {
        this.mWifiTracker.onDestroy();
    }

    public List<XpAccessPoint> getAllAccessPoints() {
        List<AccessPoint> accessPoints = getAccessPoints(false);
        if (accessPoints.size() >= 0) {
            this.mAccessPointList.clear();
            this.mAccessPointList.addAll(convertRawData(accessPoints));
        }
        return this.mAccessPointList;
    }

    private List<XpAccessPoint> convertRawData(List<AccessPoint> list) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (AccessPoint accessPoint : list) {
            XpAccessPoint xpAccessPoint = new XpAccessPoint();
            xpAccessPoint.setSsid(accessPoint.getSsid().toString());
            xpAccessPoint.setKey(accessPoint.getKey());
            xpAccessPoint.setSummary(accessPoint.getSettingsSummary());
            xpAccessPoint.setSaved(accessPoint.isSaved());
            xpAccessPoint.setConfig(accessPoint.getConfig());
            xpAccessPoint.setConnected(accessPoint.isConnected());
            xpAccessPoint.setConnecting(accessPoint.isConnecting());
            xpAccessPoint.setBssid(accessPoint.getBssid());
            xpAccessPoint.setLevel(accessPoint.getLevel());
            xpAccessPoint.setSecurity(accessPoint.getSecurity());
            xpAccessPoint.setEverConnected(accessPoint.isEverConnected());
            xpAccessPoint.setPasspoint(accessPoint.isPasspoint());
            xpAccessPoint.setSecurityString(accessPoint.getSecurityString(false));
            xpAccessPoint.setRssi(accessPoint.getRssi());
            xpAccessPoint.setActive(accessPoint.isActive());
            xpAccessPoint.setRawData(accessPoint);
            copyOnWriteArrayList.add(xpAccessPoint);
        }
        return copyOnWriteArrayList;
    }

    public List<AccessPoint> getSavedAccessPoints() {
        return getAccessPoints(true);
    }

    private List<AccessPoint> getAccessPoints(boolean z) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        if (this.mWifiManager.isWifiEnabled()) {
            for (AccessPoint accessPoint : this.mWifiTracker.getAccessPoints()) {
                if (shouldIncludeAp(accessPoint, z)) {
                    copyOnWriteArrayList.add(accessPoint);
                }
            }
        }
        return copyOnWriteArrayList;
    }

    private boolean shouldIncludeAp(AccessPoint accessPoint, boolean z) {
        if (z) {
            return accessPoint.isReachable() && accessPoint.isSaved();
        }
        return accessPoint.isReachable();
    }

    public XpAccessPoint getConnectedAccessPoint() {
        for (XpAccessPoint xpAccessPoint : getAllAccessPoints()) {
            if (((AccessPoint) xpAccessPoint.getRawData()).getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                return xpAccessPoint;
            }
        }
        return null;
    }

    public boolean isWifiEnabled() {
        return this.mWifiManager.isWifiEnabled();
    }

    public int getWifiState() {
        return this.mWifiManager.getWifiState();
    }

    public boolean setWifiEnabled(boolean z) {
        return this.mWifiManager.setWifiEnabled(z);
    }

    public void connectToPublicWifi(XpAccessPoint xpAccessPoint, WifiManager.ActionListener actionListener) {
        this.mWifiManager.connect(((AccessPoint) xpAccessPoint.getRawData()).getConfig(), actionListener);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
    public void onWifiStateChanged(int i) {
        for (Listener listener : this.mListenerList) {
            listener.onWifiStateChanged(i);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.wifi.WifiTracker.WifiListener
    public void onAccessPointsChanged() {
        for (Listener listener : this.mListenerList) {
            listener.onAccessPointsChanged();
        }
    }

    public void forget(XpAccessPoint xpAccessPoint) {
        AccessPoint accessPoint = (AccessPoint) xpAccessPoint.getRawData();
        if (!accessPoint.isSaved()) {
            StringBuilder sb = new StringBuilder();
            sb.append("xpwifi forgetAP getNetworkInfo:");
            sb.append(accessPoint.getNetworkInfo());
            sb.append(" getState:");
            sb.append(accessPoint.getNetworkInfo() != null ? accessPoint.getNetworkInfo().getState() : -1);
            sb.append(" ap:");
            sb.append(accessPoint);
            Logs.d(sb.toString());
            if (accessPoint.getNetworkInfo() != null && accessPoint.getNetworkInfo().getState() != NetworkInfo.State.DISCONNECTED) {
                this.mWifiManager.disableEphemeralNetwork(XpAccessPoint.convertToQuotedString(accessPoint.getSsid().toString()));
                return;
            }
            Log.e("wen", "Failed to forget invalid network " + accessPoint);
            return;
        }
        this.mWifiManager.forget(accessPoint.getConfig().networkId, null);
    }

    public void connect(XpAccessPoint xpAccessPoint, PopupPwdDialogCallback popupPwdDialogCallback) {
        AccessPoint accessPoint = (AccessPoint) xpAccessPoint.getRawData();
        if (accessPoint.getSecurity() == 0) {
            accessPoint.generateOpenNetworkConfig();
        }
        this.mWifiManager.connect(accessPoint.getConfig(), null);
    }

    public void add(WifiConfiguration wifiConfiguration) {
        this.mWifiManager.addNetwork(wifiConfiguration);
    }

    private static String ipv4PrefixLengthToSubnetMask(int i) {
        try {
            return NetworkUtils.getNetworkPart(InetAddress.getByAddress(new byte[]{-1, -1, -1, -1}), i).getHostAddress();
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    public static boolean addAccessPoint(Context context, XpAccessPoint xpAccessPoint, String str, String str2, String str3, int i) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        WifiConfiguration wifiConfig = WifiUtils.getWifiConfig((AccessPoint) xpAccessPoint.getRawData(), str, str2, str3, i);
        int addNetwork = wifiManager.addNetwork(wifiConfig);
        Logs.d("xpwifi input addNetwork netId:" + addNetwork + " wifiConfig:" + wifiConfig);
        if (addNetwork != -1) {
            boolean enableNetwork = wifiManager.enableNetwork(addNetwork, true);
            Logs.d("xpwifi input enableNetwork enable:" + enableNetwork);
            return enableNetwork;
        }
        return false;
    }

    public boolean isApSaved(XpAccessPoint xpAccessPoint) {
        AccessPoint accessPoint = (AccessPoint) xpAccessPoint.getRawData();
        boolean isAccessPointDisabledByWrongPassword = com.xiaopeng.car.settingslibrary.common.utils.WifiUtils.isAccessPointDisabledByWrongPassword(xpAccessPoint);
        Logs.d("wifi isap save:" + accessPoint.isSaved() + " isAccessPointDisabledByWrongPassword:" + isAccessPointDisabledByWrongPassword + " ap:" + accessPoint);
        return accessPoint.isSaved() && !isAccessPointDisabledByWrongPassword;
    }

    public static boolean isWrongPassword(XpAccessPoint xpAccessPoint) {
        WifiConfiguration.NetworkSelectionStatus networkSelectionStatus;
        WifiConfiguration config = xpAccessPoint.getConfig();
        return (config == null || (networkSelectionStatus = config.getNetworkSelectionStatus()) == null || networkSelectionStatus.isNetworkEnabled() || networkSelectionStatus.getNetworkSelectionDisableReason() != WifiUtils.WifiConfigurationConstant.NetworkSelectionStatusConstant.DISABLED_BY_WRONG_PASSWORD) ? false : true;
    }

    public static boolean shouldEditBeforeConnect(XpAccessPoint xpAccessPoint) {
        return isWrongPassword(xpAccessPoint);
    }

    public boolean isEditPwdBeforeConnect(XpAccessPoint xpAccessPoint) {
        return !((AccessPoint) xpAccessPoint.getRawData()).isSaved() || isWrongPassword(xpAccessPoint);
    }

    public boolean connectOrPopDialog(XpAccessPoint xpAccessPoint, PopupPwdDialogCallback popupPwdDialogCallback) {
        if (xpAccessPoint.getSecurity() == 0) {
            connect(xpAccessPoint, popupPwdDialogCallback);
            return false;
        }
        Logs.d("xpwifi accessPoint save:" + xpAccessPoint.isSaved() + " getHasEverConnected:" + xpAccessPoint.isEverConnected() + " " + xpAccessPoint.getSsid());
        if (isApSaved(xpAccessPoint)) {
            connect(xpAccessPoint, popupPwdDialogCallback);
            return false;
        }
        return true;
    }
}
