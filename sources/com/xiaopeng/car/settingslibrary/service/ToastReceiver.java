package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes.dex */
public class ToastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("ToastReceiver receiver action:" + intent.getAction());
        if ("com.xiaopeng.intent.action.XUI_TOAST".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("text");
            String stringExtra2 = intent.getStringExtra(VuiConstants.ELEMENT_ID);
            Logs.d("ToastReceiver text:" + stringExtra + " id:" + stringExtra2);
            if (TextUtils.isEmpty(stringExtra2) || TextUtils.isEmpty(stringExtra)) {
                return;
            }
            int i = 0;
            try {
                i = intent.getIntExtra("duration", 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i != 1) {
                AppServerManager.getInstance().onPopupToastShort(stringExtra);
            } else {
                AppServerManager.getInstance().onPopupToastLong(stringExtra);
            }
        }
    }
}
