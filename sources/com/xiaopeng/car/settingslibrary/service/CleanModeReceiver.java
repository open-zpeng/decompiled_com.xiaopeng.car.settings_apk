package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.display.XpDisplayManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
/* loaded from: classes.dex */
public class CleanModeReceiver extends BroadcastReceiver {
    public static final String TAG = "CleanModeReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String requestEnterUserScenario = XpDisplayManager.getInstance().requestEnterUserScenario(XuiSettingsManager.USER_SCENARIO_CLEAN_MODE, 0);
        Logs.d("CleanModeReceiver enter clean mode ret:" + requestEnterUserScenario);
    }
}
