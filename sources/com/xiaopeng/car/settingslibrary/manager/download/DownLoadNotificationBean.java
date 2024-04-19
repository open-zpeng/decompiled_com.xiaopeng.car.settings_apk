package com.xiaopeng.car.settingslibrary.manager.download;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import java.util.Objects;
/* loaded from: classes.dex */
public class DownLoadNotificationBean {
    public static final int STATUS_DOING = 1;
    public static final int STATUS_FAIL = 5;
    public static final int STATUS_PAUSE = 3;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_WAITING = 2;
    private String actionTitle;
    private int buttonStatus;
    private PendingIntent cancelPendingIntent;
    private Icon icon;
    private String iconUrl;
    private int id;
    private String key;
    private PendingIntent pausePendingIntent;
    private int progress;
    private int progressMax;
    private int remainingTime;
    private PendingIntent resumePendingIntent;
    private PendingIntent retryPendingIntent;
    private long size;
    private String sizeString;
    private long startTime;
    private String startTimeString;
    private int status;
    private String statusDesc;
    private Notification.Action successAction;
    private String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    public void setRemainingTime(int i) {
        this.remainingTime = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public PendingIntent getPausePendingIntent() {
        return this.pausePendingIntent;
    }

    public void setPausePendingIntent(PendingIntent pendingIntent) {
        this.pausePendingIntent = pendingIntent;
    }

    public PendingIntent getResumePendingIntent() {
        return this.resumePendingIntent;
    }

    public void setResumePendingIntent(PendingIntent pendingIntent) {
        this.resumePendingIntent = pendingIntent;
    }

    public PendingIntent getRetryPendingIntent() {
        return this.retryPendingIntent;
    }

    public void setRetryPendingIntent(PendingIntent pendingIntent) {
        this.retryPendingIntent = pendingIntent;
    }

    public PendingIntent getCancelPendingIntent() {
        return this.cancelPendingIntent;
    }

    public void setCancelPendingIntent(PendingIntent pendingIntent) {
        this.cancelPendingIntent = pendingIntent;
    }

    public Notification.Action getSuccessAction() {
        return this.successAction;
    }

    public void setSuccessAction(Notification.Action action) {
        this.successAction = action;
        if (this.successAction != null) {
            this.actionTitle = action.title.toString();
        }
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public int getProgressMax() {
        return this.progressMax;
    }

    public void setProgressMax(int i) {
        this.progressMax = i;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getStartTimeString() {
        return this.startTimeString;
    }

    public void setStartTimeString(String str) {
        this.startTimeString = str;
    }

    public String getSizeString() {
        return this.sizeString;
    }

    public void setSizeString(String str) {
        this.sizeString = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getStatusDesc() {
        return this.statusDesc;
    }

    public void setStatusDesc(String str) {
        this.statusDesc = str;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    public String getActionTitle() {
        return this.actionTitle;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.key.equals(((DownLoadNotificationBean) obj).key);
    }

    public int getButtonStatus() {
        return this.buttonStatus;
    }

    public void setButtonStatus(int i) {
        this.buttonStatus = i;
    }

    public int hashCode() {
        return Objects.hash(this.key);
    }

    public String toString() {
        return "DownLoadNotificationBean{startTime=" + this.startTime + ", key='" + this.key + "', id=" + this.id + ", title='" + this.title + "', icon=" + this.icon + ", status=" + this.status + ", size=" + this.size + ", remainingTime=" + this.remainingTime + ", pausePendingIntent=" + this.pausePendingIntent + ", resumePendingIntent=" + this.resumePendingIntent + ", retryPendingIntent=" + this.retryPendingIntent + ", cancelPendingIntent=" + this.cancelPendingIntent + ", successAction=" + this.successAction + ", progress=" + this.progress + ", progressMax=" + this.progressMax + ", iconUrl=" + this.iconUrl + ", actionTitle='" + this.actionTitle + "', startTimeString='" + this.startTimeString + "', sizeString='" + this.sizeString + "', buttonStatus=" + this.buttonStatus + ", statusDesc='" + this.statusDesc + "'}";
    }
}
