package com.xiaopeng.car.settingslibrary.service.cb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class MicConnectReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        Log.i("MicConnectReceiver", "appConfig ready");
        String action = intent.getAction();
        int hashCode = action.hashCode();
        if (hashCode != -1031794906) {
            if (hashCode == -909695921 && action.equals("com.test.intent.action.TEST_MIC")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (action.equals("android.karaoke.recommend.CONNECT_ACTION")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            AppServerManager.getInstance().onMicDialogShow();
        } else if (c != 1) {
        } else {
            AppServerManager.getInstance().onMicDialogUpdate(intent.getIntExtra("step", 4));
        }
    }
}
