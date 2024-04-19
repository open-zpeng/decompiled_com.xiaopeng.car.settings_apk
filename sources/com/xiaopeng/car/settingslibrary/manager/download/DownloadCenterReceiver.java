package com.xiaopeng.car.settingslibrary.manager.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
/* loaded from: classes.dex */
public class DownloadCenterReceiver extends BroadcastReceiver {
    private static final String CLEAR_COMPLETE = "com.xiaopeng.download.intent.action.CLEAR_COMPLETE";
    private static final String TAG = "DownloadCenterReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.i(TAG, "receiver:" + action);
        if (CLEAR_COMPLETE.equals(action)) {
            DownLoadMonitorManager.get().removeCompleteNotifications();
        }
    }
}
