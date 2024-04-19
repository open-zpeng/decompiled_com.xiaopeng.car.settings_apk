package com.xiaopeng.car.settingslibrary.manager.download;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/* loaded from: classes.dex */
class DownLoadNotificationAnalysis {
    static final String EXTRA_ACTION_CANCEL = "xp.download.action.cancel";
    private static final String EXTRA_ACTION_PAUSE = "xp.download.action.pause";
    private static final String EXTRA_ACTION_RESUME = "xp.download.action.resume";
    private static final String EXTRA_ACTION_RETRY = "xp.download.action.retry";
    private static final String EXTRA_ACTION_SUCCESS = "xp.download.action.success";
    static final String EXTRA_BUTTON_STATUS = "xp.download.button.status";
    static final String EXTRA_ICON_URL = "xp.download.iconUrl";
    private static final String EXTRA_PROGRESS = "xp.download.progress";
    private static final String EXTRA_PROGRESS_MAX = "xp.download.progressMax";
    private static final String EXTRA_REMAINING_TIME = "xp.download.remaining.time";
    private static final String EXTRA_SIZE = "xp.download.size";
    public static final String EXTRA_STATUS = "xp.download.status";
    static final String EXTRA_STATUS_DESC = "xp.download.status.desc";
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    DownLoadNotificationAnalysis() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DownLoadNotificationBean dataAnalysis(StatusBarNotification statusBarNotification) {
        if (check(statusBarNotification)) {
            Notification notification = statusBarNotification.getNotification();
            Bundle bundle = notification.extras;
            DownLoadNotificationBean downLoadNotificationBean = new DownLoadNotificationBean();
            downLoadNotificationBean.setId(statusBarNotification.getId());
            downLoadNotificationBean.setKey(statusBarNotification.getKey());
            downLoadNotificationBean.setIcon(notification.getSmallIcon());
            downLoadNotificationBean.setTitle(bundle.getString(NotificationCompat.EXTRA_TITLE));
            downLoadNotificationBean.setStartTime(notification.creationTime);
            downLoadNotificationBean.setStatus(bundle.getInt(EXTRA_STATUS));
            downLoadNotificationBean.setSize(bundle.getLong(EXTRA_SIZE));
            downLoadNotificationBean.setRemainingTime(bundle.getInt(EXTRA_REMAINING_TIME));
            downLoadNotificationBean.setPausePendingIntent((PendingIntent) bundle.getParcelable(EXTRA_ACTION_PAUSE));
            downLoadNotificationBean.setResumePendingIntent((PendingIntent) bundle.getParcelable(EXTRA_ACTION_RESUME));
            downLoadNotificationBean.setRetryPendingIntent((PendingIntent) bundle.getParcelable(EXTRA_ACTION_RETRY));
            downLoadNotificationBean.setCancelPendingIntent((PendingIntent) bundle.getParcelable(EXTRA_ACTION_CANCEL));
            downLoadNotificationBean.setSuccessAction((Notification.Action) bundle.getParcelable(EXTRA_ACTION_SUCCESS));
            downLoadNotificationBean.setProgressMax(bundle.getInt(EXTRA_PROGRESS_MAX));
            downLoadNotificationBean.setProgress(bundle.getInt(EXTRA_PROGRESS));
            downLoadNotificationBean.setButtonStatus(bundle.getInt(EXTRA_BUTTON_STATUS, 1));
            downLoadNotificationBean.setStatusDesc(bundle.getString(EXTRA_STATUS_DESC));
            downLoadNotificationBean.setIconUrl(bundle.getString(EXTRA_ICON_URL));
            downLoadNotificationBean.setSizeString(Utils.getAppSizeString(downLoadNotificationBean.getSize()));
            downLoadNotificationBean.setStartTimeString(formatTime(downLoadNotificationBean.getStartTime()));
            return downLoadNotificationBean;
        }
        return null;
    }

    private static boolean check(StatusBarNotification statusBarNotification) {
        Notification notification = statusBarNotification.getNotification();
        Bundle bundle = notification.extras;
        String string = bundle.getString(NotificationCompat.EXTRA_TITLE);
        int i = bundle.getInt("android.displayFlag");
        bundle.getLong(EXTRA_SIZE, -1L);
        return (i != 64 || TextUtils.isEmpty(string) || notification.getSmallIcon() == null || bundle.getInt(EXTRA_STATUS, -1) == -1) ? false : true;
    }

    private static String formatTime(long j) {
        return sSimpleDateFormat.format(new Date(j));
    }
}
