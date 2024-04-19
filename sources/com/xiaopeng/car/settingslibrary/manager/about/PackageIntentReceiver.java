package com.xiaopeng.car.settingslibrary.manager.about;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class PackageIntentReceiver extends BroadcastReceiver {
    Runnable mRunnable;
    UnInstallListener unInstallListener;

    /* loaded from: classes.dex */
    public interface UnInstallListener {
        void onUnInstalled();
    }

    public void registerReceiver(Context context, Runnable runnable) {
        this.mRunnable = runnable;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(this, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        context.registerReceiver(this, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_ADDED");
        intentFilter3.addAction("android.intent.action.USER_REMOVED");
        context.registerReceiver(this, intentFilter3);
    }

    public void unregisterReceiver(Context context) {
        context.unregisterReceiver(this);
        this.mRunnable = null;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logs.d("xpapp receiver actionStr:" + action);
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
        if (this.unInstallListener == null || !"android.intent.action.PACKAGE_REMOVED".equals(action)) {
            return;
        }
        this.unInstallListener.onUnInstalled();
    }

    public void registerUnInstallListener(UnInstallListener unInstallListener) {
        this.unInstallListener = unInstallListener;
    }

    public void unregisterUnInstallListener() {
        this.unInstallListener = null;
    }
}
