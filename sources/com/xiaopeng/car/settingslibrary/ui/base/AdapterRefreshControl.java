package com.xiaopeng.car.settingslibrary.ui.base;

import android.os.Handler;
import android.os.Message;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public abstract class AdapterRefreshControl {
    private static final int REFRESH_INTERVAL = 500;
    private static final String TAG = "RefreshControl";
    private Handler mHandler = new Handler(new Handler.Callback() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$AdapterRefreshControl$C743R2dSYpCANUzxVG8sa39ge1c
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return AdapterRefreshControl.this.lambda$new$0$AdapterRefreshControl(message);
        }
    });
    private long mLastRefreshingTime;

    protected abstract void refreshData();

    public /* synthetic */ boolean lambda$new$0$AdapterRefreshControl(Message message) {
        if (message.what == 1) {
            this.mLastRefreshingTime = System.currentTimeMillis();
            refreshData();
        }
        return true;
    }

    protected boolean isNeedDelay() {
        long currentTimeMillis = System.currentTimeMillis() - this.mLastRefreshingTime;
        if (currentTimeMillis < 500) {
            Logs.log(TAG, "Refreshing too often! : " + currentTimeMillis);
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
            return true;
        }
        this.mLastRefreshingTime = System.currentTimeMillis();
        this.mHandler.removeMessages(1);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void checkRefresh() {
        if (isNeedDelay()) {
            return;
        }
        refreshData();
    }
}
