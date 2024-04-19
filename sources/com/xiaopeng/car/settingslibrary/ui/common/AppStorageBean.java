package com.xiaopeng.car.settingslibrary.ui.common;

import android.graphics.drawable.Drawable;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
/* loaded from: classes.dex */
public class AppStorageBean {
    private long appData;
    private long appSize;
    private Drawable icon;
    private String name;
    private String packageName;
    private long totalSize;

    public AppStorageBean() {
    }

    public AppStorageBean(String str, String str2, long j) {
        this.name = str;
        this.packageName = str2;
        this.totalSize = j;
        this.appSize = j;
        this.appData = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long j) {
        this.totalSize = j;
    }

    public long getAppSize() {
        return this.appSize;
    }

    public void setAppSize(long j) {
        this.appSize = j;
    }

    public long getAppData() {
        return this.appData;
    }

    public void setAppData(long j) {
        this.appData = j;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public String toDataLogInfo() {
        return this.name + "," + FileUtils.parseByte2KB(this.totalSize) + ";";
    }
}
