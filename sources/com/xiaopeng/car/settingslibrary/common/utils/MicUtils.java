package com.xiaopeng.car.settingslibrary.common.utils;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.speech.speechwidget.ListWidget;
/* loaded from: classes.dex */
public class MicUtils {
    public static final String CAR_CONTROL_PACKAGE = "com.xiaopeng.carcontrol";
    public static final String SHOW_MICROPHONE_UNMUTE_DIALOG = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_MICROPHONE_UNMUTE_DIALOG";
    private static AudioManager sAudioManager;

    public static AudioManager getAudio() {
        if (sAudioManager == null) {
            sAudioManager = (AudioManager) CarSettingsApp.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        }
        return sAudioManager;
    }

    public static void setMicrophoneMute(boolean z) {
        Logs.d("set mic:" + z);
        getAudio().setMicrophoneMute(z);
    }

    public static boolean isMicrophoneMute() {
        return getAudio().isMicrophoneMute();
    }

    public static void openMicDialog(Context context) {
        if (context == null) {
            return;
        }
        Logs.d("openMicDialog");
        Intent intent = new Intent();
        intent.setAction(SHOW_MICROPHONE_UNMUTE_DIALOG);
        intent.setPackage("com.xiaopeng.carcontrol");
        intent.addFlags(20971552);
        context.sendBroadcast(intent);
    }
}
