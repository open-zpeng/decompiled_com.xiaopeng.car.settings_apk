package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class ScreenBean {
    private String action;
    private int screenId;

    public ScreenBean() {
    }

    public ScreenBean(int i, String str) {
        this.screenId = i;
        this.action = str;
    }

    public int getScreenId() {
        return this.screenId;
    }

    public void setScreenId(int i) {
        this.screenId = i;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }
}
