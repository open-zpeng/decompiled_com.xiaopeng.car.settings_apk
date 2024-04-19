package com.xiaopeng.car.settingslibrary.manager.sound;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import java.util.Locale;
/* loaded from: classes.dex */
public class PlaySpeechSoundHelper {
    private static final String TAG = "PlaySpeechSoundHelper";
    private static volatile PlaySpeechSoundHelper sInstance;
    private boolean mIsPlaying = false;
    private TextToSpeech mTextToSpeech;

    /* loaded from: classes.dex */
    public interface OnTtsEndCallbackListener {
        void endCallback(String str);
    }

    public static PlaySpeechSoundHelper getInstance() {
        if (sInstance == null) {
            synchronized (PlaySpeechSoundHelper.class) {
                if (sInstance == null) {
                    sInstance = new PlaySpeechSoundHelper();
                }
            }
        }
        return sInstance;
    }

    public void createTextSpeech(Context context, final OnTtsEndCallbackListener onTtsEndCallbackListener) {
        if (this.mTextToSpeech != null) {
            Logs.d("PlaySpeechSoundHelper init already!");
        } else {
            this.mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$PlaySpeechSoundHelper$QFmLzCNSDkMV0sTX8VrKDZ3cLd4
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public final void onInit(int i) {
                    PlaySpeechSoundHelper.this.lambda$createTextSpeech$0$PlaySpeechSoundHelper(onTtsEndCallbackListener, i);
                }
            });
        }
    }

    public /* synthetic */ void lambda$createTextSpeech$0$PlaySpeechSoundHelper(final OnTtsEndCallbackListener onTtsEndCallbackListener, int i) {
        Logs.d("PlaySpeechSoundHelperTextToSpeech onInit status = " + i);
        if (i == 0) {
            TextToSpeech textToSpeech = this.mTextToSpeech;
            if (textToSpeech == null) {
                Logs.d("PlaySpeechSoundHelperTextToSpeech null");
                return;
            }
            int language = textToSpeech.setLanguage(Locale.CHINESE);
            Logs.d("PlaySpeechSoundHelpersetLanguage CHINESE " + language);
            this.mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.PlaySpeechSoundHelper.1
                @Override // android.speech.tts.UtteranceProgressListener
                public void onStart(String str) {
                    Logs.d("PlaySpeechSoundHelper onStart " + str);
                    PlaySpeechSoundHelper.this.mIsPlaying = true;
                }

                @Override // android.speech.tts.UtteranceProgressListener
                public void onDone(String str) {
                    Logs.d("PlaySpeechSoundHelper onDone " + str + " listener " + onTtsEndCallbackListener);
                    PlaySpeechSoundHelper.this.mIsPlaying = false;
                    OnTtsEndCallbackListener onTtsEndCallbackListener2 = onTtsEndCallbackListener;
                    if (onTtsEndCallbackListener2 != null) {
                        onTtsEndCallbackListener2.endCallback(str);
                    }
                }

                @Override // android.speech.tts.UtteranceProgressListener
                public void onError(String str) {
                    Logs.d("PlaySpeechSoundHelper onError " + str);
                    PlaySpeechSoundHelper.this.mIsPlaying = false;
                }

                @Override // android.speech.tts.UtteranceProgressListener
                public void onStop(String str, boolean z) {
                    Logs.d("PlaySpeechSoundHelper onStop " + str);
                    PlaySpeechSoundHelper.this.mIsPlaying = false;
                }
            });
        }
    }

    public void playText(String str, Bundle bundle, String str2) {
        TextToSpeech textToSpeech = this.mTextToSpeech;
        if (textToSpeech == null) {
            Logs.d("PlaySpeechSoundHelper no init tts!");
            return;
        }
        textToSpeech.speak(str, 0, bundle, str2);
        Logs.d("PlaySpeechSoundHelper speak id:" + str2);
    }

    public void stopPlay() {
        if (this.mTextToSpeech != null) {
            Logs.d("PlaySpeechSoundHelper stopPlay");
            this.mTextToSpeech.stop();
        }
    }

    public void releaseTextSpeech() {
        if (this.mTextToSpeech != null) {
            Logs.d("PlaySpeechSoundHelper shutdown");
            this.mTextToSpeech.shutdown();
            this.mTextToSpeech = null;
        }
    }

    public boolean isPlaying() {
        return this.mIsPlaying;
    }
}
