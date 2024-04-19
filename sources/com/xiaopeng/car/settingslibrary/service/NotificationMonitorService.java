package com.xiaopeng.car.settingslibrary.service;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.download.DownLoadMonitorManager;
/* loaded from: classes.dex */
public class NotificationMonitorService extends NotificationListenerService {
    private static boolean sIsConnected;
    private static NotificationMonitorService sNotificationListenerInstance;

    public static NotificationMonitorService getInstanceIfConnected() {
        if (sIsConnected) {
            return sNotificationListenerInstance;
        }
        return null;
    }

    public NotificationMonitorService() {
        log("create---");
        sNotificationListenerInstance = this;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        DownLoadMonitorManager.get().onNotificationPosted(statusBarNotification);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
        DownLoadMonitorManager.get().onNotificationRemoved(statusBarNotification);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        super.onListenerConnected();
        log("onListenerConnected---");
        DownLoadMonitorManager.get().setConnect(true);
        sIsConnected = true;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        log("onListenerDisconnected---");
        DownLoadMonitorManager.get().setConnect(false);
        sIsConnected = false;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public IBinder onBind(Intent intent) {
        log("onBind---");
        return super.onBind(intent);
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy---");
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        log("onCreate---");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        log("onStartCommand---" + intent.getAction());
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        log("onUnbind---");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        log("onRebind---");
        super.onRebind(intent);
    }

    protected void finalize() throws Throwable {
        log("finalize---");
        super.finalize();
    }

    private void log(String str) {
        Logs.log("xpdownload-MonitorService", str + " " + hashCode());
    }
}
