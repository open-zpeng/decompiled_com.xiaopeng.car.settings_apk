package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class AutoPowerOffReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("autoPower receiver action:" + intent.getAction());
        if (Config.AUTO_POWER_OFF_ACTION.equals(intent.getAction())) {
            AppServerManager.getInstance().onAutoPowerOffAction();
        }
    }
}
