package com.xiaopeng.car.settingslibrary.common.utils;

import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
/* loaded from: classes.dex */
public class TimeConsumingLog {
    private static final String TAG = "TimeConsuming";
    private static TimeConsumingLog sTimeConsumingLog = new TimeConsumingLog();
    private long mLastTime;
    private long mStartTime;
    private StringBuilder mStringBuilder = new StringBuilder();
    private boolean mIsDebug = !Utils.isUserRelease();

    public static TimeConsumingLog get() {
        return sTimeConsumingLog;
    }

    public void start(String str) {
        if (this.mIsDebug) {
            this.mStartTime = System.currentTimeMillis();
            this.mLastTime = this.mStartTime;
            this.mStringBuilder.append(str);
            this.mStringBuilder.append("--start");
        }
    }

    public void end(String str) {
        if (this.mIsDebug) {
            long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
            this.mStringBuilder.append(str);
            this.mStringBuilder.append("--end");
            this.mStringBuilder.append("--time:");
            this.mStringBuilder.append(currentTimeMillis);
            this.mStringBuilder.append(WifiKeyParser.MESSAGE_END);
            log();
            clear();
        }
    }

    public TimeConsumingLog add(String str) {
        if (this.mIsDebug) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - this.mLastTime;
            this.mLastTime = currentTimeMillis;
            this.mStringBuilder.append(str);
            this.mStringBuilder.append("--time:");
            this.mStringBuilder.append(j);
            return this;
        }
        return this;
    }

    public void clear() {
        this.mStringBuilder.setLength(0);
    }

    public void log() {
        Logs.log(TAG, this.mStringBuilder.toString());
    }
}
