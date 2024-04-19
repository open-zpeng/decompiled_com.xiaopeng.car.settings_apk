package com.xiaopeng.car.settingslibrary.interfaceui.beans;

import java.util.Arrays;
/* loaded from: classes.dex */
public class SoundEffectThemeBean {
    private String friendlyName;
    private String[] playbillPath;
    private String subName;
    private int themeId;

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String str) {
        this.subName = str;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String str) {
        this.friendlyName = str;
    }

    public String[] getPlaybillPath() {
        return this.playbillPath;
    }

    public void setPlaybillPath(String[] strArr) {
        this.playbillPath = strArr;
    }

    public int getThemeId() {
        return this.themeId;
    }

    public void setThemeId(int i) {
        this.themeId = i;
    }

    public String toString() {
        return "SoundEffectThemeBean{friendlyName='" + this.friendlyName + "', subName='" + this.subName + "', playbillPath=" + Arrays.toString(this.playbillPath) + ", themeId=" + this.themeId + '}';
    }
}
