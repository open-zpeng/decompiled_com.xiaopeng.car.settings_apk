package com.xiaopeng.car.settingslibrary.interfaceui.beans;

import java.util.Arrays;
/* loaded from: classes.dex */
public class SoundEffectBootResourceBean {
    private String friendlyName;
    private int resId;
    private String[] resPath;

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

    public String[] getResPath() {
        return this.resPath;
    }

    public void setResPath(String[] strArr) {
        this.resPath = strArr;
    }

    public String toString() {
        return "SoundEffectBootResourceBean{friendlyName='" + this.friendlyName + "', resId=" + this.resId + ", resPath=" + Arrays.toString(this.resPath) + '}';
    }
}
