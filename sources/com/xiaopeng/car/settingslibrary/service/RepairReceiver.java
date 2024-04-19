package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class RepairReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("RepairReceiver receiver action:" + intent.getAction());
        if (Config.REPAIR_REQUEST_QUIT_ACTION.equals(intent.getAction())) {
            AppServerManager.getInstance().onRepairQuestQuit();
            Intent intent2 = new Intent(context, GlobalService.class);
            intent2.setAction(Config.REPAIR_REQUEST_QUIT_ACTION);
            context.startService(intent2);
        }
    }
}
