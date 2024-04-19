package com.xiaopeng.car.settingslibrary.service.work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class WlanResultWork implements WorkService {
    private static final int WIFI_TIMEOUT = 90000;
    private static volatile WlanResultWork sInstance;
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    private String mCurrentSSID;
    private XpAccessPoint mTargetAccessPoint;
    private WifiManager mWifiManager;
    private boolean mIsAlreadyRegister = false;
    private ArrayList<WlanResultListener> mWlanResultListenerList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private Runnable mRunnableTimeout = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$WlanResultWork$CouGmLWZA2xDQjBBoeXteOBwQ3M
        @Override // java.lang.Runnable
        public final void run() {
            WlanResultWork.this.timeout();
        }
    };
    ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.1
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            if (WlanResultWork.this.mWifiManager != null) {
                WifiInfo connectionInfo = WlanResultWork.this.mWifiManager.getConnectionInfo();
                Logs.d("WlanResultWork mNetworkCallback onAvailable mCurrentSSID:" + WlanResultWork.this.mCurrentSSID + " info:" + connectionInfo);
                if (TextUtils.isEmpty(WlanResultWork.this.mCurrentSSID) || connectionInfo == null) {
                    return;
                }
                String removeDoubleQuotes = WlanResultWork.this.removeDoubleQuotes(connectionInfo.getSSID());
                Logs.d("WlanResultWork mNetworkCallback onAvailable mCurrentSSID:" + WlanResultWork.this.mCurrentSSID + " ssid:" + removeDoubleQuotes);
                if (WlanResultWork.this.mCurrentSSID.equals(removeDoubleQuotes)) {
                    WlanResultWork.this.removeTimeout(removeDoubleQuotes);
                    WlanResultWork.this.notifyWlanResultSuccess(removeDoubleQuotes);
                    return;
                }
                Logs.d("WlanResultWork mNetworkCallback onAvailable remove timeout");
                WlanResultWork.this.mHandler.removeCallbacks(WlanResultWork.this.mRunnableTimeout);
                if (WlanResultWork.this.mWlanResultListenerList.isEmpty()) {
                    return;
                }
                WlanResultWork wlanResultWork = WlanResultWork.this;
                wlanResultWork.notifyWlanResultError(wlanResultWork.mCurrentSSID, -1);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            Logs.d("WlanResultWork mNetworkCallback  onLost info:" + network);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onUnavailable() {
            Logs.d("WlanResultWork mNetworkCallback onUnavailable");
        }
    };
    final BroadcastReceiver mReceiver = new AnonymousClass2();

    /* loaded from: classes.dex */
    public interface WlanResultListener {
        void onRefreshSummaryMsg(String str, String str2, int i);

        void onResultReceiveFail(String str, int i);

        void onResultSuccess(String str);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    public static WlanResultWork getInstance() {
        if (sInstance == null) {
            synchronized (WlanResultWork.class) {
                if (sInstance == null) {
                    sInstance = new WlanResultWork();
                }
            }
        }
        return sInstance;
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        if (this.mIsAlreadyRegister) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        context.registerReceiver(this.mReceiver, intentFilter);
        this.mIsAlreadyRegister = true;
        registerWifiConnectListener();
    }

    public void registerWifiConnectListener() {
        NetworkRequest build = new NetworkRequest.Builder().build();
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(build, this.mNetworkCallback);
        }
    }

    public void unregisterWifiConnectListener() {
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String removeDoubleQuotes(String str) {
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

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        if (this.mIsAlreadyRegister) {
            context.unregisterReceiver(this.mReceiver);
            this.mIsAlreadyRegister = false;
            unregisterWifiConnectListener();
        }
        this.mHandler.removeCallbacks(this.mRunnableTimeout);
    }

    public void removeTimeout(String str) {
        if (TextUtils.isEmpty(str) || !str.equals(this.mCurrentSSID)) {
            return;
        }
        this.mHandler.removeCallbacks(this.mRunnableTimeout);
    }

    public void launchTimeout() {
        this.mHandler.removeCallbacks(this.mRunnableTimeout);
        this.mHandler.postDelayed(this.mRunnableTimeout, 90000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void timeout() {
        if (TextUtils.isEmpty(this.mCurrentSSID)) {
            return;
        }
        Logs.d("WlanResultWork timeout tips " + this.mCurrentSSID);
        AppServerManager appServerManager = AppServerManager.getInstance();
        appServerManager.onPopupToast(this.mCurrentSSID + this.mContext.getApplicationContext().getString(R.string.wlan_popup_error_timeout));
        if (this.mWlanResultListenerList.isEmpty()) {
            return;
        }
        notifyWlanResultError(this.mCurrentSSID, -1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.service.work.WlanResultWork$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 extends BroadcastReceiver {
        AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("xpservice receiver action:" + action + " ");
            if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                intent.getBooleanExtra("connected", false);
                final int intExtra = intent.getIntExtra("supplicantError", -1);
                String stringExtra = intent.getStringExtra("ssid");
                Logs.d("xpwifi receiver SUPPLICANT_CONNECTION_CHANGE_ACTION error:" + intExtra + " ssid:" + stringExtra);
                final String removeDoubleQuotes = WlanResultWork.this.removeDoubleQuotes(stringExtra);
                if (!WlanResultWork.this.mWlanResultListenerList.isEmpty()) {
                    WlanResultWork.this.removeTimeout(removeDoubleQuotes);
                    WlanResultWork.this.notifyWlanResultError(removeDoubleQuotes, intExtra);
                }
                ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$WlanResultWork$2$XUeTERSJRoql0dezw9m9DPPq5PQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        WlanResultWork.AnonymousClass2.this.lambda$onReceive$0$WlanResultWork$2(removeDoubleQuotes, intExtra);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onReceive$0$WlanResultWork$2(String str, int i) {
            if (Config.XP_AUTO.equals(str)) {
                Logs.d("xp-auto no pop toast!");
            } else if (WifiUtils.WifiManagerConstant.ERROR_AUTH_FAILURE_WRONG_PSWD == i) {
                if (WlanResultWork.this.mWlanResultListenerList.isEmpty()) {
                    WlanResultWork.this.removeTimeout(str);
                    AppServerManager appServerManager = AppServerManager.getInstance();
                    appServerManager.onPopupToast(str + WlanResultWork.this.mContext.getApplicationContext().getString(R.string.wlan_password_error));
                }
            } else if (WifiUtils.WifiManagerConstant.ERROR_AUTH_FAILURE_EAP_FAILURE == i) {
                WlanResultWork.this.removeTimeout(str);
                AppServerManager appServerManager2 = AppServerManager.getInstance();
                appServerManager2.onPopupToast(str + WlanResultWork.this.mContext.getApplicationContext().getString(R.string.wlan_authentication_fail));
            } else if (WifiUtils.isWifiConnectFail(i)) {
                WlanResultWork.this.removeTimeout(str);
                AppServerManager appServerManager3 = AppServerManager.getInstance();
                appServerManager3.onPopupToast(str + WlanResultWork.this.mContext.getApplicationContext().getString(R.string.wlan_popup_error_title));
            }
        }
    }

    public void addWlanResultListener(String str, WlanResultListener wlanResultListener) {
        this.mCurrentSSID = str;
        if (this.mWlanResultListenerList.contains(wlanResultListener)) {
            return;
        }
        this.mWlanResultListenerList.add(wlanResultListener);
    }

    public void removeWlanResultListener(WlanResultListener wlanResultListener) {
        this.mWlanResultListenerList.remove(wlanResultListener);
    }

    public void notifyWlanResultError(String str, int i) {
        Iterator<WlanResultListener> it = this.mWlanResultListenerList.iterator();
        while (it.hasNext()) {
            it.next().onResultReceiveFail(str, i);
        }
    }

    public void notifyWlanResultSuccess(String str) {
        Iterator<WlanResultListener> it = this.mWlanResultListenerList.iterator();
        while (it.hasNext()) {
            it.next().onResultSuccess(str);
        }
    }

    public void setTargetAccessPoint(XpAccessPoint xpAccessPoint) {
        this.mTargetAccessPoint = xpAccessPoint;
    }

    public XpAccessPoint getTargetAccessPoint() {
        return this.mTargetAccessPoint;
    }

    public void notifyItemSummary(String str, String str2) {
        Logs.d("WlanResultWork notifyItemSummary summary:" + str2 + " ssid:" + str);
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        if (str2.contains(this.mContext.getString(R.string.wifi_check_password_try_again)) || str2.contains(this.mContext.getString(R.string.wifitrackerlib_wifi_check_password_try_again))) {
            Iterator<WlanResultListener> it = this.mWlanResultListenerList.iterator();
            while (it.hasNext()) {
                it.next().onRefreshSummaryMsg(str, str2, 2);
            }
        } else if (str2.contains(this.mContext.getString(R.string.wifi_disabled_password_failure)) || str2.contains(this.mContext.getString(R.string.wifi_disabled_network_failure)) || str2.contains(this.mContext.getString(R.string.wifi_disabled_by_recommendation_provider)) || str2.contains(this.mContext.getString(R.string.wifi_not_in_range)) || str2.contains(this.mContext.getString(R.string.wifi_ap_unable_to_handle_new_sta)) || str2.contains(this.mContext.getString(R.string.wifi_disabled_generic))) {
            Iterator<WlanResultListener> it2 = this.mWlanResultListenerList.iterator();
            while (it2.hasNext()) {
                it2.next().onRefreshSummaryMsg(str, str2, 1);
            }
        }
    }
}
