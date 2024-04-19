package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class DisplayBean {
    private boolean forceSetMeter;
    private int uiProgress;

    public DisplayBean() {
    }

    public DisplayBean(int i, boolean z) {
        this.uiProgress = i;
        this.forceSetMeter = z;
    }

    public int getUiProgress() {
        return this.uiProgress;
    }

    public void setUiProgress(int i) {
        this.uiProgress = i;
    }

    public boolean isForceSetMeter() {
        return this.forceSetMeter;
    }

    public void setForceSetMeter(boolean z) {
        this.forceSetMeter = z;
    }

    public String toString() {
        return "DisplayBean{uiProgress=" + this.uiProgress + ", forceSetMeter=" + this.forceSetMeter + '}';
    }
}
