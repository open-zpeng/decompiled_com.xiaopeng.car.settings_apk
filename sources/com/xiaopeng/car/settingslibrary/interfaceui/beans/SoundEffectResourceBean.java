package com.xiaopeng.car.settingslibrary.interfaceui.beans;

import java.util.Arrays;
/* loaded from: classes.dex */
public class SoundEffectResourceBean {
    private String friendlyName;
    private int resId;
    private String[] resPath;
    private int resType;

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String str) {
        this.friendlyName = str;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int i) {
        this.resId = i;
    }

    public int getResType() {
        return this.resType;
    }

    public void setResType(int i) {
        this.resType = i;
    }

    public String[] getResPath() {
        return this.resPath;
    }

    public void setResPath(String[] strArr) {
        this.resPath = strArr;
    }

    public String toString() {
        return "SoundEffectResourceBean{friendlyName='" + this.friendlyName + "', resId=" + this.resId + ", resType=" + this.resType + ", resPath=" + Arrays.toString(this.resPath) + '}';
    }
}
