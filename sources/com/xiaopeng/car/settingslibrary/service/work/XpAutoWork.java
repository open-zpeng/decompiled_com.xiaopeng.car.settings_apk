package com.xiaopeng.car.settingslibrary.service.work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
/* loaded from: classes.dex */
public class XpAutoWork implements WorkService, IMcuChangeListener {
    BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.service.work.XpAutoWork.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("wifi_state", 4);
                Logs.d("XpAutoWork receive state:" + intExtra);
                if (intExtra == 3 && CarSettingsManager.getInstance().isInFactoryMode()) {
                    XpAutoWork.this.connectXpAuto();
                }
            }
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        if (CarFunction.isSupportXpAutoConnect()) {
            CarSettingsManager.getInstance().addMcuChangeListener(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            context.registerReceiver(this.mReceiver, intentFilter);
            Logs.d("xpservice XpAutoWork onCreate");
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onIgOn() {
        if (CarFunction.isSupportXpAutoConnect()) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$XpAutoWork$eNA3lQePH68vR4ZDNB3E6cfh-SY
                @Override // java.lang.Runnable
                public final void run() {
                    XpAutoWork.this.lambda$onIgOn$0$XpAutoWork();
                }
            }, 10000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: startWifiAndConnectXpAuto */
    public void lambda$onIgOn$0$XpAutoWork() {
        if (CarSettingsManager.getInstance().isInFactoryMode()) {
            if (XpWifiManager.getInstance(CarSettingsApp.getContext()).isWifiEnabled()) {
                connectXpAuto();
            } else {
                XpWifiManager.getInstance(CarSettingsApp.getContext()).setWifiEnabled(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectXpAuto() {
        if (XpAutoManualManager.getInstance().isXpAutoConnected()) {
            Logs.d("settings xpauto already connect xpauto ");
            return;
        }
        int checkAndConnect = XpAutoManualManager.getInstance().checkAndConnect();
        Logs.d("settings xpauto autoconnect " + checkAndConnect);
        if (checkAndConnect == 1) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$XpAutoWork$Y42NaKrqobQP2gI0YCHyVIhfrwA
                @Override // java.lang.Runnable
                public final void run() {
                    XpAutoWork.lambda$connectXpAuto$1();
                }
            }, 20000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$connectXpAuto$1() {
        int checkAndConnect = XpAutoManualManager.getInstance().checkAndConnect();
        Logs.d("settings xpauto retryconnect r:" + checkAndConnect);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && Config.ACTION_CONNECT_XPAUTO.equals(intent.getAction())) {
            Logs.d("settings xpauto onStartCommand ");
            lambda$onIgOn$0$XpAutoWork();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        if (CarFunction.isSupportXpAutoConnect()) {
            CarSettingsManager.getInstance().removeMcuChangeListener(this);
            context.unregisterReceiver(this.mReceiver);
            Logs.d("xpservice XpAutoWork onDestroy");
        }
    }
}
