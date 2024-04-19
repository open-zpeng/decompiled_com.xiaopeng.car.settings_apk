package com.xiaopeng.car.settingslibrary.manager.bluetooth.nf;
/* loaded from: classes.dex */
public class NFConnectState {
    private int newState;
    private int prevState;

    public NFConnectState(int i, int i2) {
        this.prevState = i;
        this.newState = i2;
    }

    public int getPrevState() {
        return this.prevState;
    }

    public void setPrevState(int i) {
        this.prevState = i;
    }

    public int getNewState() {
        return this.newState;
    }

    public void setNewState(int i) {
        this.newState = i;
    }
}
