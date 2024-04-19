package com.xiaopeng.car.settingslibrary.service.work.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class XDialogReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("XDialogReceiver receiver action:" + intent.getAction());
        if (Config.XUI_DIALOG_ACTION.equals(intent.getAction())) {
            AppServerManager.getInstance().onPopupDialog(intent);
        }
    }
}
