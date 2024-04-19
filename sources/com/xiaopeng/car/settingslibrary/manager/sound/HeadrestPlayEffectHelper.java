package com.xiaopeng.car.settingslibrary.manager.sound;

import android.media.AudioSystem;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class HeadrestPlayEffectHelper {
    private final int PLAY_TIME_DELAY = 500;
    private final int PLAY_MSG_WHAT = 1000;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.car.settingslibrary.manager.sound.HeadrestPlayEffectHelper.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1000) {
                HeadrestPlayEffectHelper.this.playMediaAndTts((String) message.obj);
            }
        }
    };
    private PlaySoundHelper mPlaySoundHelper = new PlaySoundHelper(CarSettingsApp.getContext(), 3, Uri.parse(Config.MEDIA_PATH));

    public HeadrestPlayEffectHelper() {
        this.mPlaySoundHelper.setExcludeAnyActive(false);
        this.mPlaySoundHelper.setHeadrest(true);
    }

    public void createSpeech() {
        PlaySpeechSoundHelper.getInstance().createTextSpeech(CarSettingsApp.getContext(), null);
    }

    public void destroySpeech() {
        this.mHandler.removeMessages(1000);
        if (PlaySpeechSoundHelper.getInstance().isPlaying()) {
            PlaySpeechSoundHelper.getInstance().stopPlay();
        }
        PlaySoundHelper playSoundHelper = this.mPlaySoundHelper;
        if (playSoundHelper != null) {
            playSoundHelper.postStopSample();
        }
    }

    public void playEffect(String str) {
        if (this.mPlaySoundHelper.isSamplePlaying()) {
            this.mPlaySoundHelper.onStopSample();
        }
        if (PlaySpeechSoundHelper.getInstance().isPlaying()) {
            PlaySpeechSoundHelper.getInstance().stopPlay();
        }
        this.mHandler.removeMessages(1000);
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 1000;
        obtainMessage.obj = str;
        this.mHandler.sendMessageDelayed(obtainMessage, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playMediaAndTts(String str) {
        boolean isStreamActive = AudioSystem.isStreamActive(3, 0);
        boolean isStreamMute = SoundManager.getInstance().isStreamMute(3);
        Logs.d("xpsound dialog play mute:" + isStreamMute + " musicActive: " + isStreamActive);
        if (!isStreamActive && !isStreamMute) {
            this.mPlaySoundHelper.postStartSample();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("streamType", 9);
        bundle.putInt("priority", 1);
        bundle.putInt("ttsMode", 1);
        bundle.putInt("delayTolerance", 500);
        PlaySpeechSoundHelper.getInstance().playText(str, bundle, str);
    }
}
