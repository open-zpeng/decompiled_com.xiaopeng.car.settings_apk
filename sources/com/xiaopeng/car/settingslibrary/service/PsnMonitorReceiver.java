package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class PsnMonitorReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.xiaopeng.xui.passenger.status";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            int intExtra = intent.getIntExtra("status", 0);
            Logs.d("PsnMonitorReceiver receiver status:" + intExtra);
            Intent intent2 = new Intent(context, GlobalService.class);
            if (intExtra == 1) {
                intent2.setAction(Config.HEADREST_ACTION_SWITCH);
            } else {
                intent2.setAction(Config.HEADREST_ACTION_RESTORE);
            }
            context.startService(intent2);
        }
    }
}
