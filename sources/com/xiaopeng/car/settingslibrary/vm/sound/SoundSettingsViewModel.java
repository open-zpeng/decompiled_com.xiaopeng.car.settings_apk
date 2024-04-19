package com.xiaopeng.car.settingslibrary.vm.sound;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.MeterCallback;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
/* loaded from: classes.dex */
public class SoundSettingsViewModel extends AndroidViewModel implements SoundManager.OnVolumeChangedListener, MeterCallback, SoundManager.SystemSoundChangeListener {
    Application mApplication;
    MutableLiveData<Integer> mAvasLiveData;
    CarSettingsManager mCarSettingsManager;
    MutableLiveData<Integer> mMediaLiveData;
    MutableLiveData<Integer> mNavigationLiveData;
    MutableLiveData<Integer> mPsnBtLiveData;
    MutableLiveData<Integer> mSafetySoundLiveData;
    private int mSlidingVolumeType;
    SoundInfo mSoundInfo;
    SoundManager mSoundManager;
    MutableLiveData<Integer> mSpeechLiveData;
    MutableLiveData<Boolean> mSystemSoundChangeLiveData;

    public SoundSettingsViewModel(Application application) {
        super(application);
        this.mCarSettingsManager = CarSettingsManager.getInstance();
        this.mMediaLiveData = new MutableLiveData<>();
        this.mNavigationLiveData = new MutableLiveData<>();
        this.mSpeechLiveData = new MutableLiveData<>();
        this.mAvasLiveData = new MutableLiveData<>();
        this.mPsnBtLiveData = new MutableLiveData<>();
        this.mSafetySoundLiveData = new MutableLiveData<>();
        this.mSystemSoundChangeLiveData = new MutableLiveData<>();
        this.mSoundInfo = new SoundInfo();
        this.mSlidingVolumeType = -1;
        this.mApplication = application;
        this.mSoundManager = SoundManager.getInstance();
    }

    public void refreshVolume() {
        this.mSoundInfo.setNavigationVolume(this.mSoundManager.getStreamVolume(9));
        this.mSoundInfo.setMusicVolume(this.mSoundManager.getStreamVolume(3));
        this.mSoundInfo.setSpeechVolume(this.mSoundManager.getStreamVolume(10));
        if (CarFunction.isSupportDoubleScreen()) {
            this.mSoundInfo.setPsnBTVolume(this.mSoundManager.getStreamVolume(13));
        }
        if (Config.IS_SDK_HIGHER_P) {
            this.mSoundInfo.setAvasVolume(this.mSoundManager.getStreamVolume(Config.AVAS_STREAM));
        }
        this.mMediaLiveData.setValue(Integer.valueOf(this.mSoundInfo.getMusicVolume()));
        this.mNavigationLiveData.setValue(Integer.valueOf(this.mSoundInfo.getNavigationVolume()));
        this.mSpeechLiveData.setValue(Integer.valueOf(this.mSoundInfo.getSpeechVolume()));
        this.mAvasLiveData.setValue(Integer.valueOf(this.mSoundInfo.getAvasVolume()));
        if (CarFunction.isSupportDoubleScreen()) {
            this.mPsnBtLiveData.setValue(Integer.valueOf(this.mSoundInfo.getPsnBTVolume()));
        }
        getSafetyVolume();
    }

    public int getStreamVolume(int i) {
        return this.mSoundManager.getStreamVolume(i);
    }

    public void onCreateVM() {
        Logs.d("xpsound vm onCreate");
    }

    public void onStartVM() {
        this.mCarSettingsManager.registerIcmVolume(this);
        refreshVolume();
        registerListener();
        addSystemSoundChangeListener();
        this.mSoundManager.registerMicReceiver();
    }

    public void onStopVM() {
        releaseListener();
        removeSystemSoundChangeListener();
        this.mCarSettingsManager.unregisterIcmVolume(this);
        this.mSoundManager.unregisterMicReceiver();
    }

    public void setSlidingVolumeType(int i) {
        this.mSlidingVolumeType = i;
    }

    public void onDestroyVM() {
        Logs.d("xpsound vm onDestroy");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getSystemSoundChangeLiveData() {
        return this.mSystemSoundChangeLiveData;
    }

    public MutableLiveData<Integer> getMediaLiveData() {
        return this.mMediaLiveData;
    }

    public MutableLiveData<Integer> getNavigationLiveData() {
        return this.mNavigationLiveData;
    }

    public MutableLiveData<Integer> getSpeechLiveData() {
        return this.mSpeechLiveData;
    }

    public MutableLiveData<Integer> getAvasLiveData() {
        return this.mAvasLiveData;
    }

    public MutableLiveData<Integer> getPsnBtLiveData() {
        return this.mPsnBtLiveData;
    }

    public void setLowerSoundWhenOpenDoor(boolean z) {
        this.mSoundManager.setVolumeDownWithDoorOpen(z, true);
    }

    public boolean isLowerSoundWhenOpenDoor() {
        return this.mSoundManager.getVolumeDownWithDoorOpen();
    }

    public boolean isDecreaseVolumeNavigating() {
        return SoundManager.getInstance().isDecreaseVolNavigating();
    }

    public void setDecreaseVolumeNavigating(boolean z) {
        SoundManager.getInstance().setVolumeDownWithNavigating(z, true);
    }

    public void setLowerSoundWhenRstall(boolean z) {
        this.mSoundManager.setVolumeDownInReverseMode(z, true);
    }

    public boolean isLowerSoundWhenRstall() {
        return this.mSoundManager.getVolumeDownInReverseMode();
    }

    public MutableLiveData<Integer> getSafetySoundLiveData() {
        return this.mSafetySoundLiveData;
    }

    public void setVolume(int i, int i2) {
        if (i != 3) {
            if (i != 9) {
                if (i == 10) {
                    if (this.mSoundInfo.getSpeechVolume() == i2) {
                        return;
                    }
                    this.mSoundInfo.setSpeechVolume(i2);
                }
            } else if (this.mSoundInfo.getNavigationVolume() == i2) {
                return;
            } else {
                this.mSoundInfo.setNavigationVolume(i2);
            }
        } else if (this.mSoundInfo.getMusicVolume() == i2) {
            return;
        } else {
            this.mSoundInfo.setMusicVolume(i2);
        }
        this.mSoundManager.setStreamVolume(i, i2, 4);
    }

    public void setVolumeForce(int i, int i2) {
        if (i == 3) {
            this.mSoundInfo.setMusicVolume(i2);
        } else if (i == 9) {
            this.mSoundInfo.setNavigationVolume(i2);
        } else if (i == 10) {
            this.mSoundInfo.setSpeechVolume(i2);
        }
        this.mSoundManager.setStreamVolume(i, i2, 4);
    }

    public void playSoundForStreamType(int i) {
        this.mSoundManager.playSoundForStreamType(i);
    }

    public int getMaxVolume(int i) {
        return this.mSoundManager.getStreamMaxVolume(i);
    }

    public void registerListener() {
        this.mSoundManager.addOnVolumeChangedListener(this);
    }

    public void releaseListener() {
        this.mSoundManager.removeOnVolumeChangedListener(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.OnVolumeChangedListener
    public void onVolumeChanged(int i, int i2, int i3) {
        Logs.d("xpsound onVolumeChanged streamType:" + i + " volume:" + i2 + " mSlidingVolumeType:" + this.mSlidingVolumeType);
        if (i == 3) {
            if (this.mSoundInfo.getMusicVolume() == i2 || this.mSlidingVolumeType == 3) {
                return;
            }
            this.mMediaLiveData.postValue(Integer.valueOf(i2));
            this.mSoundInfo.setMusicVolume(i2);
        } else if (i == 10) {
            if (this.mSoundInfo.getSpeechVolume() == i2 || this.mSlidingVolumeType == 10) {
                return;
            }
            this.mSpeechLiveData.postValue(Integer.valueOf(i2));
            this.mSoundInfo.setSpeechVolume(i2);
        } else if (i == 9) {
            if (this.mSoundInfo.getNavigationVolume() == i2 || this.mSlidingVolumeType == 9) {
                return;
            }
            this.mNavigationLiveData.postValue(Integer.valueOf(i2));
            this.mSoundInfo.setNavigationVolume(i2);
        } else if (i == Config.AVAS_STREAM) {
            if (this.mSoundInfo.getAvasVolume() == i2 || this.mSlidingVolumeType == Config.AVAS_STREAM) {
                return;
            }
            this.mAvasLiveData.postValue(Integer.valueOf(i2));
            this.mSoundInfo.setAvasVolume(i2);
        } else if (i != 13 || this.mSoundInfo.getPsnBTVolume() == i2 || this.mSlidingVolumeType == 13) {
        } else {
            this.mPsnBtLiveData.postValue(Integer.valueOf(i2));
            this.mSoundInfo.setPsnBTVolume(i2);
        }
    }

    public void setSystemSoundEnable(boolean z) {
        this.mSoundManager.setSystemSoundEnable(z, false, true);
    }

    public void addSystemSoundChangeListener() {
        this.mSoundManager.addSystemSoundChangeListener(this);
    }

    public void removeSystemSoundChangeListener() {
        this.mSoundManager.removeSystemSoundChangeListener(this);
    }

    public boolean isSystemSoundEnabled() {
        return this.mSoundManager.isSystemSoundEnabled();
    }

    public void setSafetyVolume(int i) {
        int i2 = 2;
        if (i == 0) {
            i2 = 0;
        } else if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            i2 = -1;
        }
        Logs.d("xpsound type:" + i + " level:" + i2);
        this.mSoundManager.setSafetyVolume(i2, true);
    }

    public void getSafetyVolume() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.sound.SoundSettingsViewModel.1
            @Override // java.lang.Runnable
            public void run() {
                SoundSettingsViewModel.this.mSafetySoundLiveData.postValue(Integer.valueOf(SoundSettingsViewModel.this.mSoundManager.getSafetyVolume()));
            }
        });
    }

    public int getSafetyVol() {
        return this.mSoundManager.getSafetyVolume();
    }

    public boolean isSpeechRoundEnable() {
        return this.mSoundManager.isSpeechRoundEnable();
    }

    public void setSpeechRoundEnable(boolean z) {
        this.mSoundManager.setSpeechRoundEnable(z, true);
    }

    public boolean supportSurroundSoundEffect() {
        return this.mSoundManager.supportSurroundSoundEffect();
    }

    public void setMainDriverExclusive(int i, boolean z) {
        this.mSoundManager.setMainDriverExclusive(i, true, true, z);
    }

    public int getMainDriverExclusive() {
        return this.mSoundManager.getMainDriverExclusive();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.MeterCallback
    public void meterAlarmSoundChange(int i) {
        Logs.d("xpsound meterAlarmSound change " + i);
        Logs.d("xpsound meterAlarmSound change notify ui");
        this.mSafetySoundLiveData.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.SystemSoundChangeListener
    public void onSystemSoundChange() {
        this.mSystemSoundChangeLiveData.postValue(true);
    }

    public int getCurrentSoundEffectMode() {
        return this.mSoundManager.getSoundEffectMode();
    }

    public int getSoundEffectType(int i) {
        return this.mSoundManager.getSoundEffectType(i);
    }

    public int getSoundEffectScene(int i) {
        return this.mSoundManager.getSoundEffectScene(i);
    }

    public void setSoundPositionEnable(boolean z) {
        this.mSoundManager.setSoundPositionEnable(z, false);
    }

    public boolean getSoundPositionEnable() {
        return this.mSoundManager.getSoundPositionEnable();
    }

    public int getEqualizerSwitch(int i) {
        return this.mSoundManager.getEqualizerSwitch(i);
    }

    public int getEqualizerCustomValue(int i) {
        return this.mSoundManager.getEqualizerCustomValue(i);
    }
}
