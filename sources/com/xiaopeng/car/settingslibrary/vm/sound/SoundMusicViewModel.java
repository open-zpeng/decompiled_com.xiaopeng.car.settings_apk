package com.xiaopeng.car.settingslibrary.vm.sound;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
/* loaded from: classes.dex */
public class SoundMusicViewModel extends AndroidViewModel implements LifecycleObserver {
    Application mApplication;
    MutableLiveData<Integer> mCurrentEQStyleLiveData;
    MutableLiveData<Integer> mCurrentScenesLiveData;
    MutableLiveData<Integer> mCurrentSoundEffectLiveData;
    MutableLiveData<SoundFieldValues> mHifiSoundFieldLiveData;
    MutableLiveData<Integer> mHifiStyleLiveData;
    MutableLiveData<SoundFieldValues> mOriginalSoundFieldLiveData;
    MutableLiveData<SoundFieldValues> mRoundSoundFieldLiveData;
    MutableLiveData<Integer> mRoundStyleLiveData;
    SoundFieldValues mSoundFieldValuesHifi;
    SoundFieldValues mSoundFieldValuesOriginal;
    SoundFieldValues mSoundFieldValuesRound;
    SoundManager mSoundManager;

    public int getCurrentEqStyle() {
        return 3;
    }

    public SoundMusicViewModel(Application application) {
        super(application);
        this.mCurrentEQStyleLiveData = new MutableLiveData<>();
        this.mCurrentSoundEffectLiveData = new MutableLiveData<>();
        this.mCurrentScenesLiveData = new MutableLiveData<>();
        this.mRoundStyleLiveData = new MutableLiveData<>();
        this.mHifiStyleLiveData = new MutableLiveData<>();
        this.mRoundSoundFieldLiveData = new MutableLiveData<>();
        this.mHifiSoundFieldLiveData = new MutableLiveData<>();
        this.mOriginalSoundFieldLiveData = new MutableLiveData<>();
        this.mSoundFieldValuesRound = new SoundFieldValues();
        this.mSoundFieldValuesHifi = new SoundFieldValues();
        this.mSoundFieldValuesOriginal = new SoundFieldValues();
        this.mApplication = application;
        this.mSoundManager = SoundManager.getInstance();
    }

    public MutableLiveData<Integer> getCurrentEQStyleLiveData() {
        return this.mCurrentEQStyleLiveData;
    }

    public MutableLiveData<Integer> getCurrentSoundEffectLiveData() {
        return this.mCurrentSoundEffectLiveData;
    }

    public MutableLiveData<Integer> getCurrentScenesLiveData() {
        return this.mCurrentScenesLiveData;
    }

    public MutableLiveData<Integer> getRoundStyleLiveData() {
        return this.mRoundStyleLiveData;
    }

    public MutableLiveData<Integer> getHifiStyleLiveData() {
        return this.mHifiStyleLiveData;
    }

    public MutableLiveData<SoundFieldValues> getRoundSoundFieldLiveData() {
        return this.mRoundSoundFieldLiveData;
    }

    public MutableLiveData<SoundFieldValues> getHifiSoundFieldLiveData() {
        return this.mHifiSoundFieldLiveData;
    }

    public MutableLiveData<SoundFieldValues> getOriginalSoundFieldLiveData() {
        return this.mOriginalSoundFieldLiveData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        refreshEffectRound();
        if (CarConfigHelper.hasHifiSound()) {
            refreshEffectHifi();
        } else {
            refreshEffectOriginal();
        }
        refreshEffectEQ();
        this.mCurrentSoundEffectLiveData.setValue(Integer.valueOf(getSoundEffectMode()));
    }

    public void refreshEffectRound() {
        this.mCurrentScenesLiveData.setValue(Integer.valueOf(getSoundEffectScene(1)));
        this.mRoundStyleLiveData.setValue(Integer.valueOf(getSoundEffectType(1)));
        this.mRoundSoundFieldLiveData.setValue(getSoundField(1));
    }

    public void refreshEffectHifi() {
        this.mHifiStyleLiveData.setValue(Integer.valueOf(getSoundEffectType(3)));
        this.mHifiSoundFieldLiveData.setValue(getSoundField(3));
    }

    public void refreshEffectEQ() {
        this.mCurrentEQStyleLiveData.setValue(3);
    }

    public void refreshEffectOriginal() {
        this.mOriginalSoundFieldLiveData.setValue(getSoundField(4));
    }

    private int getSoundEffectMode() {
        return this.mSoundManager.getSoundEffectMode();
    }

    public void setSoundField(int i, int i2, int i3) {
        if (i == 1) {
            if (i2 == this.mSoundFieldValuesRound.getXSound() && i3 == this.mSoundFieldValuesRound.getYSound()) {
                return;
            }
            this.mSoundFieldValuesRound.setXSound(i2);
            this.mSoundFieldValuesRound.setYSound(i3);
        } else if (i == 3) {
            if (i2 == this.mSoundFieldValuesHifi.getXSound() && i3 == this.mSoundFieldValuesHifi.getYSound()) {
                return;
            }
            this.mSoundFieldValuesHifi.setXSound(i2);
            this.mSoundFieldValuesHifi.setYSound(i3);
        } else if (i2 == this.mSoundFieldValuesOriginal.getXSound() && i3 == this.mSoundFieldValuesOriginal.getYSound()) {
            return;
        } else {
            this.mSoundFieldValuesOriginal.setXSound(i2);
            this.mSoundFieldValuesOriginal.setYSound(i3);
        }
        this.mSoundManager.setSoundField(i, i2, i3, true);
    }

    public SoundFieldValues getSoundField(int i) {
        SoundFieldValues soundField = this.mSoundManager.getSoundField(i);
        if (i == 1) {
            this.mSoundFieldValuesRound = soundField;
        } else if (i == 3) {
            this.mSoundFieldValuesHifi = soundField;
        } else {
            this.mSoundFieldValuesOriginal = soundField;
        }
        return soundField;
    }

    public int getSoundEffectType(int i) {
        return this.mSoundManager.getSoundEffectType(i);
    }

    public void setSoundEffectScene(int i, int i2, boolean z) {
        this.mSoundManager.setSoundEffectScene(i, i2, z);
    }

    public int getSoundEffectScene(int i) {
        return this.mSoundManager.getSoundEffectScene(i);
    }

    public boolean isMainDriverVip() {
        return this.mSoundManager.isMainDriverVip();
    }
}
