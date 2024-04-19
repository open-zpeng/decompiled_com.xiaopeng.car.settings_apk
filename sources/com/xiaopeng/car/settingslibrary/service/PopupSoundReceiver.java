package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
/* loaded from: classes.dex */
public class PopupSoundReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logs.d("xpreceiver SoundEffectWork action:" + action);
        if ("com.xiaopeng.intent.action.POPUP_SOUND_EFFECT".equals(action)) {
            Utils.startSoundEffectSetting();
        }
    }
}
