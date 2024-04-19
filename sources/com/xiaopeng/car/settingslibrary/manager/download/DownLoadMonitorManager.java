package com.xiaopeng.car.settingslibrary.manager.download;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import androidx.collection.ArraySet;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.TimeConsumingLog;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.service.NotificationMonitorService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class DownLoadMonitorManager {
    private static DownLoadMonitorManager mInstance;
    private boolean mIsConnect;
    private IMcuChangeListener mMcuChangeListener = new IMcuChangeListener() { // from class: com.xiaopeng.car.settingslibrary.manager.download.DownLoadMonitorManager.1
        @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
        public void onIgOff() {
            DownLoadMonitorManager.this.removeCompleteNotifications();
        }
    };
    private Context mContext = CarSettingsApp.getContext().getApplicationContext();
    private NotificationManager mNm = (NotificationManager) this.mContext.getSystemService("notification");
    private ComponentName mComponentName = new ComponentName(this.mContext.getPackageName(), NotificationMonitorService.class.getName());
    private ArraySet<OnDataChangeCallBack> mOnDataChangeCallBacks = new ArraySet<>();

    /* loaded from: classes.dex */
    public interface OnDataChangeCallBack {
        void onPost(DownLoadNotificationBean downLoadNotificationBean);

        void onRemove(String str, DownLoadNotificationBean downLoadNotificationBean);
    }

    private DownLoadMonitorManager() {
        CarSettingsManager.getInstance().addMcuChangeListener(this.mMcuChangeListener);
    }

    public static synchronized DownLoadMonitorManager get() {
        DownLoadMonitorManager downLoadMonitorManager;
        synchronized (DownLoadMonitorManager.class) {
            if (mInstance == null) {
                mInstance = new DownLoadMonitorManager();
            }
            downLoadMonitorManager = mInstance;
        }
        return downLoadMonitorManager;
    }

    public ArrayList<DownLoadNotificationBean> load() {
        TimeConsumingLog.get().start("DownLoadMonitorManager-dataAnalysis");
        List<StatusBarNotification> notificationList = this.mNm.getNotificationList(64);
        Logs.log("xpdownload-MonitorManager", "getNotificationList-size:" + notificationList.size());
        ArrayList<DownLoadNotificationBean> arrayList = new ArrayList<>(notificationList.size());
        for (StatusBarNotification statusBarNotification : notificationList) {
            DownLoadNotificationBean dataAnalysis = DownLoadNotificationAnalysis.dataAnalysis(statusBarNotification);
            if (dataAnalysis != null) {
                arrayList.add(dataAnalysis);
                Logs.log("xpdownload-MonitorManager", dataAnalysis.toString());
            }
        }
        TimeConsumingLog.get().end("DownLoadMonitorManager-dataAnalysis");
        return arrayList;
    }

    private List<StatusBarNotification> findCompletedNotifications() {
        List notificationList = this.mNm.getNotificationList(64);
        if (notificationList != null) {
            ArrayList arrayList = new ArrayList(notificationList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((StatusBarNotification) it.next()).getNotification().extras.getInt(DownLoadNotificationAnalysis.EXTRA_STATUS) != 4) {
                    it.remove();
                }
            }
            return arrayList;
        }
        return null;
    }

    public void removeCompleteNotifications() {
        List<StatusBarNotification> findCompletedNotifications = findCompletedNotifications();
        if (findCompletedNotifications != null) {
            for (StatusBarNotification statusBarNotification : findCompletedNotifications) {
                Logs.log("xpdownload-MonitorManager", "removeCompleteNotification id=" + statusBarNotification.getId() + " tag=" + statusBarNotification.getTag() + ", user:" + statusBarNotification.getUser());
                PendingIntent pendingIntent = statusBarNotification.getNotification().deleteIntent;
                NotificationMonitorService instanceIfConnected = NotificationMonitorService.getInstanceIfConnected();
                if (instanceIfConnected != null) {
                    if (pendingIntent != null) {
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                    instanceIfConnected.cancelNotification(statusBarNotification.getKey());
                } else {
                    Logs.log("xpdownload-MonitorManager", "nms is null");
                }
            }
            return;
        }
        Logs.log("xpdownload-MonitorManager", "removeCompleteNotification none");
    }

    public boolean isMonitoring() {
        return this.mNm.isNotificationAssistantAccessGranted(this.mComponentName);
    }

    public void startMonitor(OnDataChangeCallBack onDataChangeCallBack) {
        addDataChangeCallBack(onDataChangeCallBack);
        Logs.log("xpdownload-MonitorManager", "startMonitor-:" + this.mIsConnect);
        if (this.mIsConnect) {
            return;
        }
        this.mNm.setNotificationListenerAccessGranted(this.mComponentName, true);
    }

    public void stopMonitor(OnDataChangeCallBack onDataChangeCallBack) {
        removeDataChangeCallBack(onDataChangeCallBack);
        Logs.log("xpdownload-MonitorManager", "stopMonitor-:" + this.mOnDataChangeCallBacks.size());
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        DownLoadNotificationBean dataAnalysis;
        if (this.mOnDataChangeCallBacks.size() == 0 || (dataAnalysis = DownLoadNotificationAnalysis.dataAnalysis(statusBarNotification)) == null) {
            return;
        }
        Logs.log("xpdownload-MonitorManager-onPosted", dataAnalysis.toString());
        Iterator<OnDataChangeCallBack> it = this.mOnDataChangeCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onPost(dataAnalysis);
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        if (this.mOnDataChangeCallBacks.size() == 0) {
            return;
        }
        Logs.log("xpdownload-MonitorManager-onPosted", statusBarNotification.toString());
        Iterator<OnDataChangeCallBack> it = this.mOnDataChangeCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onRemove(statusBarNotification.getKey(), DownLoadNotificationAnalysis.dataAnalysis(statusBarNotification));
        }
    }

    public void addDataChangeCallBack(OnDataChangeCallBack onDataChangeCallBack) {
        this.mOnDataChangeCallBacks.add(onDataChangeCallBack);
    }

    public void removeDataChangeCallBack(OnDataChangeCallBack onDataChangeCallBack) {
        this.mOnDataChangeCallBacks.remove(onDataChangeCallBack);
    }

    public void setConnect(boolean z) {
        this.mIsConnect = z;
    }
}
