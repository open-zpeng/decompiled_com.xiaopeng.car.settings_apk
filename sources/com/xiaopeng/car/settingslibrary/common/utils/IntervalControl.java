package com.xiaopeng.car.settingslibrary.common.utils;
/* loaded from: classes.dex */
public class IntervalControl {
    private static final int MIN_OPERATION_INTERVAL = 300;
    private static final String TAG = "Operate";
    private long mLastPairingTime;
    private String mUser;

    public IntervalControl() {
        this("");
    }

    public IntervalControl(String str) {
        this.mUser = str;
        this.mLastPairingTime = 0L;
    }

    public boolean isFrequently() {
        return isFrequently(300);
    }

    public boolean isFrequently(int i) {
        if (Math.abs(System.currentTimeMillis() - this.mLastPairingTime) < i) {
            Logs.log(TAG, String.format("%s-- two operation is too short", this.mUser));
            return true;
        }
        this.mLastPairingTime = System.currentTimeMillis();
        return false;
    }

    public void updateLastTime() {
        this.mLastPairingTime = System.currentTimeMillis();
    }
}
