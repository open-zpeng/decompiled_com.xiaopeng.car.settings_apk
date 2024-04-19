package com.xiaopeng.car.settingslibrary.vm.speech;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.manager.sound.PlaySpeechSoundHelper;
/* loaded from: classes.dex */
public class SpeechPlayViewModel extends AndroidViewModel {
    Application mApplication;
    MutableLiveData<String> mTtsPlayDoneLiveData;

    public void releaseTextSpeech() {
    }

    public SpeechPlayViewModel(Application application) {
        super(application);
        this.mTtsPlayDoneLiveData = new MutableLiveData<>();
        this.mApplication = application;
        this.mTtsPlayDoneLiveData.postValue("");
        createTextSpeech();
    }

    public void createTextSpeech() {
        PlaySpeechSoundHelper.getInstance().createTextSpeech(this.mApplication, new PlaySpeechSoundHelper.OnTtsEndCallbackListener() { // from class: com.xiaopeng.car.settingslibrary.vm.speech.-$$Lambda$SpeechPlayViewModel$x0fD3auD8-j0I2H7fGffMVL7hDU
            @Override // com.xiaopeng.car.settingslibrary.manager.sound.PlaySpeechSoundHelper.OnTtsEndCallbackListener
            public final void endCallback(String str) {
                SpeechPlayViewModel.this.lambda$createTextSpeech$0$SpeechPlayViewModel(str);
            }
        });
    }

    public /* synthetic */ void lambda$createTextSpeech$0$SpeechPlayViewModel(String str) {
        this.mTtsPlayDoneLiveData.postValue(str);
    }

    public void playText(String str) {
        PlaySpeechSoundHelper.getInstance().playText(str, null, str);
    }

    public void stopPlay() {
        PlaySpeechSoundHelper.getInstance().stopPlay();
    }

    public MutableLiveData<String> getTtsPlayDoneLiveData() {
        return this.mTtsPlayDoneLiveData;
    }
}
