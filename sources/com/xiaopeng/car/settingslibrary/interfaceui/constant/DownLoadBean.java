package com.xiaopeng.car.settingslibrary.interfaceui.constant;

import android.text.TextUtils;
import java.util.Objects;
/* loaded from: classes.dex */
public class DownLoadBean {
    public static final int STATUS_DOING = 1;
    public static final int STATUS_FAIL = 5;
    public static final int STATUS_PAUSE = 3;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_WAITING = 2;
    private int buttonStatus;
    private int id;
    private int progress;
    private int progressMax;
    private int remainingTime;
    private long size;
    private long startTime;
    private int status;
    private String key = "";
    private String title = "";
    private String actionTitle = "";
    private String startTimeString = "";
    private String sizeString = "";
    private String statusDesc = "";
    private String iconUrl = "";

    public void setActionTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            this.actionTitle = "";
        } else {
            this.actionTitle = str;
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        if (TextUtils.isEmpty(str)) {
            this.key = "";
        } else {
            this.key = str;
        }
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
        if (TextUtils.isEmpty(str)) {
            this.title = "";
        } else {
            this.title = str;
        }
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

    public String getStartTimeString() {
        return this.startTimeString;
    }

    public void setStartTimeString(String str) {
        if (TextUtils.isEmpty(str)) {
            this.startTimeString = "";
        } else {
            this.startTimeString = str;
        }
    }

    public String getSizeString() {
        return this.sizeString;
    }

    public void setSizeString(String str) {
        if (TextUtils.isEmpty(str)) {
            this.sizeString = "";
        } else {
            this.sizeString = str;
        }
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
        if (TextUtils.isEmpty(str)) {
            this.statusDesc = "";
        } else {
            this.statusDesc = str;
        }
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            this.iconUrl = "";
        } else {
            this.iconUrl = str;
        }
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
        return this.key.equals(((DownLoadBean) obj).key);
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
        return "DownLoadBean{startTime=" + this.startTime + ", key='" + this.key + "', id=" + this.id + ", title='" + this.title + "', status=" + this.status + ", size=" + this.size + ", remainingTime=" + this.remainingTime + ", progress=" + this.progress + ", progressMax=" + this.progressMax + ", iconUrl=" + this.iconUrl + ", actionTitle='" + this.actionTitle + "', startTimeString='" + this.startTimeString + "', sizeString='" + this.sizeString + "', buttonStatus=" + this.buttonStatus + ", statusDesc='" + this.statusDesc + "'}";
    }
}
