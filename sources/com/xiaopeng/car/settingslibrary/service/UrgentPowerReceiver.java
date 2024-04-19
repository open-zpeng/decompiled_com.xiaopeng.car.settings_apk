package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class UrgentPowerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("UrgentPower receiver action:" + intent.getAction());
        if (Config.EMERGENCY_IG_OFFACTION.equals(intent.getAction())) {
            AppServerManager.getInstance().onEmergencyIgOff();
            Intent intent2 = new Intent(context, GlobalService.class);
            intent2.setAction(Config.EMERGENCY_IG_OFFACTION);
            context.startService(intent2);
        }
    }
}
