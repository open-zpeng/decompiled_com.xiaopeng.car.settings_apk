package com.xiaopeng.car.settingslibrary.ui.common;

import android.os.Handler;
/* loaded from: classes.dex */
public class DebugUtils implements Runnable {
    private int count;
    private Handler mHandler;
    private int maxCount;

    public DebugUtils() {
        this(10);
    }

    public DebugUtils(int i) {
        this.mHandler = new Handler();
        this.maxCount = i;
    }

    public boolean onClick() {
        this.mHandler.removeCallbacks(this);
        this.mHandler.postDelayed(this, 300L);
        int i = this.count;
        if (i >= this.maxCount) {
            this.count = 0;
            return true;
        }
        this.count = i + 1;
        return false;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.count = 0;
    }
}
