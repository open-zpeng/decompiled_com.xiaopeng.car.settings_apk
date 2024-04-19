package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class AuthModeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("AuthModeReceiver receiver action:" + intent.getAction() + " , time " + intent.getStringExtra(Config.AUTH_MODE_EXTRA_KEY));
        if (Config.AUTH_MODE_ACTION.equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra(Config.AUTH_MODE_EXTRA_KEY);
            AppServerManager.getInstance().onAuthModeAction(stringExtra);
            Intent intent2 = new Intent(context, GlobalService.class);
            intent2.putExtra(Config.AUTH_MODE_EXTRA_KEY, stringExtra);
            intent2.setAction(Config.AUTH_MODE_ACTION);
            context.startService(intent2);
        } else if (Config.AUTH_MODE_ACTION_CLOSE.equals(intent.getAction())) {
            AppServerManager.getInstance().onAuthModeCloseAction();
            Intent intent3 = new Intent(context, GlobalService.class);
            intent3.setAction(Config.AUTH_MODE_ACTION_CLOSE);
            context.startService(intent3);
        }
    }
}
