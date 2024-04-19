package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.MicUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SoundEffectBootResourceBean;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SoundEffectResourceBean;
import com.xiaopeng.car.settingslibrary.interfaceui.beans.SoundEffectThemeBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.SoundBean;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.sound.HeadrestPlayEffectHelper;
import com.xiaopeng.car.settingslibrary.manager.sound.MediaPlayerHelper;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundSettingsViewModel;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectTheme;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes.dex */
public class SoundServerManager extends ServerBaseManager {
    private static final String TAG = "SoundServerManager";
    private HeadrestPlayEffectHelper mHeadrestPlayEffectHelper;
    private SoundSettingsViewModel mSoundSettingsViewModel;
    private final CopyOnWriteArraySet<ISoundServerListener> mSoundUIListeners = new CopyOnWriteArraySet<>();
    MediaPlayerHelper.OnPlayStateChangeListener mPlayStateChangeListener = new MediaPlayerHelper.OnPlayStateChangeListener() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$64EXNjAPkx_U6aN0CuP_IujpTN0
        @Override // com.xiaopeng.car.settingslibrary.manager.sound.MediaPlayerHelper.OnPlayStateChangeListener
        public final void onPlayStateChanged(String str, int i) {
            SoundServerManager.this.lambda$new$7$SoundServerManager(str, i);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final SoundServerManager instance = new SoundServerManager();

        private InnerFactory() {
        }
    }

    public static SoundServerManager getInstance() {
        return InnerFactory.instance;
    }

    public void addSoundUIListener(ISoundServerListener iSoundServerListener) {
        this.mSoundUIListeners.add(iSoundServerListener);
    }

    public void removeSoundUIListener(ISoundServerListener iSoundServerListener) {
        this.mSoundUIListeners.remove(iSoundServerListener);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getSafetySoundLiveData().setValue(-1);
        getViewModel().getSafetySoundLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$wsoekjG2kveEtSmkbzhDWznC_es
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$0$SoundServerManager((Integer) obj);
            }
        });
        getViewModel().getSystemSoundChangeLiveData().setValue(null);
        getViewModel().getSystemSoundChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$6iA8ziJWto2SJODnnnJOLlfpFLY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$1$SoundServerManager((Boolean) obj);
            }
        });
        this.mSoundSettingsViewModel.getMediaLiveData().setValue(-1);
        this.mSoundSettingsViewModel.getMediaLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$ggmI4EsQezsjXK-xgwJVNOYUpHs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$2$SoundServerManager((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getSpeechLiveData().setValue(-1);
        this.mSoundSettingsViewModel.getSpeechLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$lYHY0STZHff5otpUGZxQwNSYJrs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$3$SoundServerManager((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getNavigationLiveData().setValue(-1);
        this.mSoundSettingsViewModel.getNavigationLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$uTbNwmGnJF51nIpJhfDGTCekHMo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$4$SoundServerManager((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getAvasLiveData().setValue(-1);
        this.mSoundSettingsViewModel.getAvasLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$k8fm2JYABsU14Kx164A65imwpVE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$5$SoundServerManager((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getPsnBtLiveData().setValue(-1);
        this.mSoundSettingsViewModel.getPsnBtLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$SoundServerManager$q4i8nxC8-v76mDU-PQiTvjeBzMw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundServerManager.this.lambda$observeData$6$SoundServerManager((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$SoundServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        log("SoundServerManager meterAlarmSoundChanged " + num);
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().meterAlarmSoundChanged(num.intValue());
        }
        soundCallback(InterfaceConstant.ON_METER_ALARM_SOUND_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$SoundServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        log("SoundServerManager notifySystemSoundChanged ");
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().notifySystemSoundChanged();
        }
        soundCallback(InterfaceConstant.ON_SYSTEM_SOUND_CHANGED, "");
    }

    public /* synthetic */ void lambda$observeData$2$SoundServerManager(Integer num) {
        log("SoundServerManager onVolumeChanged music " + num);
        if (num.intValue() == -1) {
            return;
        }
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(3, num.intValue());
        }
        soundCallback(InterfaceConstant.ON_VOLUME_CHANGED, JsonUtils.toJSONString(new SoundBean(3, num.intValue())));
    }

    public /* synthetic */ void lambda$observeData$3$SoundServerManager(Integer num) {
        log("SoundServerManager onVolumeChanged tts " + num);
        if (num.intValue() == -1) {
            return;
        }
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(10, num.intValue());
        }
        soundCallback(InterfaceConstant.ON_VOLUME_CHANGED, JsonUtils.toJSONString(new SoundBean(10, num.intValue())));
    }

    public /* synthetic */ void lambda$observeData$4$SoundServerManager(Integer num) {
        log("SoundServerManager onVolumeChanged navigation " + num);
        if (num.intValue() == -1) {
            return;
        }
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(9, num.intValue());
        }
        soundCallback(InterfaceConstant.ON_VOLUME_CHANGED, JsonUtils.toJSONString(new SoundBean(9, num.intValue())));
    }

    public /* synthetic */ void lambda$observeData$5$SoundServerManager(Integer num) {
        if (!CarFunction.isSupportAvasSpeaker()) {
            log("SoundServerManager onVolumeChanged avas " + num + "not support");
            return;
        }
        log("SoundServerManager onVolumeChanged avas " + num);
        if (num.intValue() == -1) {
            return;
        }
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(Config.AVAS_STREAM, num.intValue());
        }
        soundCallback(InterfaceConstant.ON_VOLUME_CHANGED, JsonUtils.toJSONString(new SoundBean(Config.AVAS_STREAM, num.intValue())));
    }

    public /* synthetic */ void lambda$observeData$6$SoundServerManager(Integer num) {
        if (!CarFunction.isSupportDoubleScreen()) {
            log("SoundServerManager onVolumeChanged psn " + num + "not support");
            return;
        }
        log("SoundServerManager onVolumeChanged psn " + num);
        if (num.intValue() == -1) {
            return;
        }
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(13, num.intValue());
        }
        soundCallback(InterfaceConstant.ON_VOLUME_CHANGED, JsonUtils.toJSONString(new SoundBean(13, num.intValue())));
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized SoundSettingsViewModel getViewModel() {
        if (this.mSoundSettingsViewModel == null) {
            this.mSoundSettingsViewModel = new SoundSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mSoundSettingsViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        getViewModel().onStartVM();
        registerAvasSpeakerChange("sound-page");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        getViewModel().onStopVM();
        unregisterAvasSpeakerChange("sound-page");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    public void registerAvasSpeakerChange(String str) {
        if (CarFunction.isSupportAvasSpeaker()) {
            SoundManager.getInstance().registerAvasSpeakerChange(str);
        }
    }

    public void unregisterAvasSpeakerChange(String str) {
        if (CarFunction.isSupportAvasSpeaker()) {
            SoundManager.getInstance().unregisterAvasSpeakerChange(str);
        }
    }

    public void onAvasSpeakerChanged(boolean z) {
        log("SoundServerManager onAvasSpeakerChanged " + z);
        soundCallback(InterfaceConstant.ON_AVAS_SPEAKER_CHANGED, String.valueOf(z));
    }

    public void onSoundEffectChange(boolean z) {
        debugLog("SoundServerManager onSoundEffectChange " + z);
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onSoundEffectChange(z);
        }
        soundCallback(InterfaceConstant.ON_EFFECT_CHANGED, String.valueOf(z));
    }

    public void onHeadRestModeChange() {
        debugLog("SoundServerManager onHeadRestModeChange ");
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onHeadRestModeChange();
        }
        soundCallback(InterfaceConstant.ON_HEADREST_MODE_CHANGED, "");
    }

    public void onTtsTipsChange() {
        debugLog("SoundServerManager onTtsTipsChange ");
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onTtsTipsChange();
        }
        soundCallback(InterfaceConstant.ON_TTS_TIPS_CHANGED, "");
    }

    public void onAccountChanged() {
        debugLog("SoundServerManager onAccountChanged");
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onAccountChanged();
        }
        soundCallback(InterfaceConstant.ON_ACCOUNT_CHANGED, "");
    }

    public void onSoundPositionEnable(boolean z) {
        debugLog("SoundServerManager onSoundPositionEnable");
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onSoundPositionEnable(z);
        }
        soundCallback(InterfaceConstant.ON_SOUND_POSITION_CHANGED, String.valueOf(z));
    }

    public void setMainDriverExclusive(int i, boolean z) {
        log("SoundServerManager setMainDriverExclusive type:" + i + " " + z);
        getViewModel().setMainDriverExclusive(i, z);
    }

    public int getMainDriverExclusive() {
        int mainDriverExclusive = XpAccountManager.getInstance().getMainDriverExclusive();
        log("SoundServerManager getMainDriverExclusive " + mainDriverExclusive);
        return mainDriverExclusive;
    }

    public int getMaxVolume(int i) {
        int maxVolume = getViewModel().getMaxVolume(i);
        log("SoundServerManager getMaxVolume streamType:" + i + " " + maxVolume);
        return maxVolume;
    }

    public int getStreamVolume(int i) {
        int streamVolume = getViewModel().getStreamVolume(i);
        log("SoundServerManager getStreamVolume type:" + i + " volume:" + streamVolume);
        return streamVolume;
    }

    public int getSafetyVolume() {
        int safetyVolume = XpAccountManager.getInstance().getSafetyVolume();
        log("SoundServerManager getSafetyVolume " + safetyVolume);
        return safetyVolume;
    }

    public boolean isSystemSoundEnabled() {
        boolean systemSoundEnable = XpAccountManager.getInstance().getSystemSoundEnable();
        log("SoundServerManager isSystemSoundEnabled " + systemSoundEnable);
        return systemSoundEnable;
    }

    public void setLowerSoundWhenOpenDoor(boolean z) {
        log("SoundServerManager setLowerSoundWhenOpenDoor enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B004", z);
        getViewModel().setLowerSoundWhenOpenDoor(z);
    }

    public boolean getVolumeDownWithDoorOpen() {
        boolean volumeDownWithDoorOpen = XpAccountManager.getInstance().getVolumeDownWithDoorOpen();
        log("SoundServerManager getVolumeDownWithDoorOpen " + volumeDownWithDoorOpen);
        return volumeDownWithDoorOpen;
    }

    public boolean getVolumeDownWithGearR() {
        boolean volumeDownWithGearR = XpAccountManager.getInstance().getVolumeDownWithGearR();
        log("SoundServerManager getVolumeDownWithGearR " + volumeDownWithGearR);
        return volumeDownWithGearR;
    }

    public void setLowerSoundWhenRstall(boolean z) {
        log("SoundServerManager setLowerSoundWhenRstall enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B003", z);
        getViewModel().setLowerSoundWhenRstall(z);
    }

    public boolean getVolumeDownWithNavigating() {
        boolean volumeDownWithNavigating = XpAccountManager.getInstance().getVolumeDownWithNavigating();
        log("SoundServerManager getVolumeDownWithNavigating " + volumeDownWithNavigating);
        return volumeDownWithNavigating;
    }

    public void setLowerSoundWhenNavigating(boolean z) {
        log("SoundServerManager setVolumeDownWithNavigating " + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B010", z);
        getViewModel().setDecreaseVolumeNavigating(z);
    }

    public void setSystemSoundEnable(boolean z) {
        log("SoundServerManager setSystemSoundEnable enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B008", z);
        getViewModel().setSystemSoundEnable(z);
    }

    public void setSafetyVolume(int i) {
        log("SoundServerManager setSafetyVolume type:" + i);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B001", i);
        getViewModel().setSafetyVolume(i);
    }

    public void releaseListener() {
        log("SoundServerManager releaseListener");
        getViewModel().releaseListener();
    }

    public void registerListener() {
        log("SoundServerManager registerListener");
        getViewModel().registerListener();
    }

    public int getCurrentSoundEffectMode() {
        log("SoundServerManager getCurrentSoundEffectMode");
        return getViewModel().getCurrentSoundEffectMode();
    }

    public int getSoundEffectType(int i) {
        log("SoundServerManager getSoundEffectType mode:" + i);
        return getViewModel().getSoundEffectType(i);
    }

    public int getSoundEffectScene(int i) {
        log("SoundServerManager getSoundEffectScene mode:" + i);
        return getViewModel().getSoundEffectScene(i);
    }

    public void setVolume(int i, int i2) {
        log("SoundServerManager setVolume streamType:" + i + " progress:" + i2);
        getViewModel().setVolume(i, i2);
    }

    public void playSoundForStreamType(int i) {
        log("SoundServerManager playSoundForStreamType streamType:" + i);
        getViewModel().playSoundForStreamType(i);
    }

    public void enterHeadrestScene() {
        log("SoundServerManager enterHeadrestScene");
        if (this.mHeadrestPlayEffectHelper == null) {
            this.mHeadrestPlayEffectHelper = new HeadrestPlayEffectHelper();
            this.mHeadrestPlayEffectHelper.createSpeech();
        }
    }

    public void playHeadrestEffect(String str) {
        log("SoundServerManager playHeadrestEffect " + str);
        HeadrestPlayEffectHelper headrestPlayEffectHelper = this.mHeadrestPlayEffectHelper;
        if (headrestPlayEffectHelper != null) {
            headrestPlayEffectHelper.playEffect(str);
        }
    }

    public void exitHeadrestScene() {
        log("SoundServerManager exitHeadrestScene");
        HeadrestPlayEffectHelper headrestPlayEffectHelper = this.mHeadrestPlayEffectHelper;
        if (headrestPlayEffectHelper != null) {
            headrestPlayEffectHelper.destroySpeech();
        }
    }

    public void setHeadrestDeputyIntelligentSwitch(boolean z) {
        log("SoundServerManager setHeadrestDeputyIntelligentSwitch check:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B002", z);
        DataRepository.getInstance().setSettingProvider(CarSettingsApp.getContext(), XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, z);
    }

    public boolean getHeadrestDeputyIntelligentSwitch() {
        log("SoundServerManager getHeadrestDeputyIntelligentSwitch");
        return DataRepository.getInstance().getSettingProvider(CarSettingsApp.getContext(), XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, false);
    }

    public SoundFieldValues getSoundField(int i) {
        SoundFieldValues soundField = SoundManager.getInstance().getSoundField(i);
        log("SoundServerManager getSoundField mode:" + i + " values:" + soundField);
        return soundField;
    }

    public void setDefaultEffect() {
        log("SoundServerManager setDefaultEffect");
        SoundManager.getInstance().setDefaultEffect();
    }

    public void setSoundField(int i, int i2, int i3, boolean z) {
        log("SoundServerManager setSoundField mode:" + i + " xSound:" + i2 + " ySound:" + i3 + " fromUI:" + z);
        SoundManager.getInstance().setSoundField(i, i2, i3, z);
    }

    public boolean isMainDriverVip() {
        boolean isMainDriverVip = SoundManager.getInstance().isMainDriverVip();
        log("SoundServerManager isMainDriverVip " + isMainDriverVip);
        return isMainDriverVip;
    }

    public void setSoundEffectScene(int i, int i2, boolean z) {
        log("SoundServerManager setSoundEffectScene mode:" + i + " type:" + i2 + " fromUI:" + z);
        SoundManager.getInstance().setSoundEffectScene(i, i2, z);
    }

    public void setSoundEffectType(int i, int i2, boolean z) {
        log("SoundServerManager setSoundEffectType mode:" + i + " type:" + i2 + " fromUI:" + z);
        SoundManager.getInstance().setSoundEffectType(i, i2, z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B004", "type", i2);
    }

    public void setSoundEffectMode(int i, boolean z) {
        log("SoundServerManager setSoundEffectMode mode:" + i + " fromUI:" + z);
        SoundManager.getInstance().setSoundEffectMode(i, z);
    }

    public boolean isMicrophoneMute() {
        boolean isMicrophoneMute = MicUtils.isMicrophoneMute();
        log("SoundServerManager isMicrophoneMute " + isMicrophoneMute);
        return isMicrophoneMute;
    }

    public void setSoundPositionEnable(boolean z) {
        log("SoundServerManager setSoundPositionEnable " + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B008", z);
        SoundManager.getInstance().setSoundPositionEnable(z, false);
    }

    public boolean getSoundPositionEnable() {
        boolean soundPositionEnable = SoundManager.getInstance().getSoundPositionEnable();
        log("SoundServerManager getSoundPositionEnable " + soundPositionEnable);
        return soundPositionEnable;
    }

    public void setAvasSpeakerSw(boolean z) {
        log("SoundServerManager setAvasSpeakerSw " + z);
        SoundManager.getInstance().setAvasSpeakerSw(z);
    }

    public boolean getAvasSpeakerSw() {
        boolean avasSpeakerSw = SoundManager.getInstance().getAvasSpeakerSw();
        log("SoundServerManager getAvasSpeakerSw " + avasSpeakerSw);
        return avasSpeakerSw;
    }

    public void setSoundSpeedLinkLevel(int i) {
        log("SoundServerManager setSoundSpeedLinkLevel " + i);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B007", "Result", i);
        SoundManager.getInstance().setSoundSpeedLinkLevel(i);
    }

    public int getSoundSpeedLinkLevel() {
        int soundSpeedLinkLevel = SoundManager.getInstance().getSoundSpeedLinkLevel();
        log("SoundServerManager getSoundSpeedLinkLevel " + soundSpeedLinkLevel);
        return soundSpeedLinkLevel;
    }

    public void setDyn3dEffectLevel(int i) {
        log("SoundServerManager setDyn3dEffectLevel " + i);
        SoundManager.getInstance().setDyn3dEffectLevel(i);
    }

    public int getDyn3dEffectLevel() {
        int dyn3dEffectLevel = SoundManager.getInstance().getDyn3dEffectLevel();
        log("SoundServerManager getDyn3dEffectLevel " + dyn3dEffectLevel);
        return dyn3dEffectLevel;
    }

    public void setXpCustomizeEffect(int i, int i2) {
        log("SoundServerManager setXpCustomizeEffect " + i + ", " + i2);
        SoundManager.getInstance().setXpCustomizeEffect(i, i2);
    }

    public int getXpCustomizeEffect(int i) {
        int xpCustomizeEffect = SoundManager.getInstance().getXpCustomizeEffect(i);
        log("SoundServerManager getXpCustomizeEffect " + xpCustomizeEffect);
        return xpCustomizeEffect;
    }

    public ArrayList<SoundEffectThemeBean> getAvailableSoundEffectThemes() {
        log("SoundServerManager getAvailableSoundEffectThemes ");
        ArrayList<SoundEffectThemeBean> arrayList = new ArrayList<>();
        SoundEffectTheme[] availableThemes = SoundManager.getInstance().getAvailableThemes();
        if (availableThemes != null && availableThemes.length > 0) {
            for (SoundEffectTheme soundEffectTheme : availableThemes) {
                SoundEffectThemeBean soundEffectThemeBean = new SoundEffectThemeBean();
                soundEffectThemeBean.setSubName(soundEffectTheme.getSubName());
                soundEffectThemeBean.setFriendlyName(soundEffectTheme.getFriendlyName());
                soundEffectThemeBean.setPlaybillPath(soundEffectTheme.getPlaybillPath());
                soundEffectThemeBean.setThemeId(soundEffectTheme.getThemeId());
                arrayList.add(soundEffectThemeBean);
            }
        }
        return arrayList;
    }

    public int getActiveSoundEffectTheme() {
        return SoundManager.getInstance().getActiveSoundEffectTheme();
    }

    public ArrayList<SoundEffectResourceBean> getSoundEffectPreviewResource(int i) {
        ArrayList<SoundEffectResourceBean> arrayList = new ArrayList<>();
        SoundEffectResource[] soundEffectPreviewResource = SoundManager.getInstance().getSoundEffectPreviewResource(i);
        if (soundEffectPreviewResource != null && soundEffectPreviewResource.length > 0) {
            for (SoundEffectResource soundEffectResource : soundEffectPreviewResource) {
                SoundEffectResourceBean soundEffectResourceBean = new SoundEffectResourceBean();
                soundEffectResourceBean.setFriendlyName(soundEffectResource.getFriendlyName());
                soundEffectResourceBean.setResId(soundEffectResource.getResId());
                soundEffectResourceBean.setResPath(soundEffectResource.getResPath());
                soundEffectResourceBean.setResType(soundEffectResource.getResourceType());
                arrayList.add(soundEffectResourceBean);
            }
        }
        return arrayList;
    }

    public int selectSoundEffectTheme(int i) {
        return SoundManager.getInstance().selectSoundEffectTheme(i);
    }

    public SoundEffectBootResourceBean getActiveBootSoundResource() {
        BootSoundResource activeBootSoundResource = SoundManager.getInstance().getActiveBootSoundResource();
        if (activeBootSoundResource != null) {
            SoundEffectBootResourceBean soundEffectBootResourceBean = new SoundEffectBootResourceBean();
            soundEffectBootResourceBean.setFriendlyName(activeBootSoundResource.getFriendlyName());
            soundEffectBootResourceBean.setResId(activeBootSoundResource.getResId());
            soundEffectBootResourceBean.setResPath(activeBootSoundResource.getResPath());
            return soundEffectBootResourceBean;
        }
        return null;
    }

    public int getBootSoundOnOff() {
        return SoundManager.getInstance().getBootSoundOnOff();
    }

    public ArrayList<SoundEffectBootResourceBean> getBootSoundResource() {
        ArrayList<SoundEffectBootResourceBean> arrayList = new ArrayList<>();
        BootSoundResource[] bootSoundResource = SoundManager.getInstance().getBootSoundResource();
        if (bootSoundResource != null && bootSoundResource.length > 0) {
            for (BootSoundResource bootSoundResource2 : bootSoundResource) {
                SoundEffectBootResourceBean soundEffectBootResourceBean = new SoundEffectBootResourceBean();
                soundEffectBootResourceBean.setFriendlyName(bootSoundResource2.getFriendlyName());
                soundEffectBootResourceBean.setResId(bootSoundResource2.getResId());
                soundEffectBootResourceBean.setResPath(bootSoundResource2.getResPath());
                arrayList.add(soundEffectBootResourceBean);
            }
        }
        return arrayList;
    }

    public int setBootSoundOnOff(boolean z) {
        return SoundManager.getInstance().setBootSoundOnOff(z);
    }

    public int setBootSoundResource(int i) {
        return SoundManager.getInstance().setBootSoundResource(i);
    }

    public void startPlayEffectPreview(String str) {
        SoundManager.getInstance().setOnPlayStateChangeListener(this.mPlayStateChangeListener);
        SoundManager.getInstance().startPlayEffectPreview(str);
    }

    public boolean isEffectPreviewPlaying(String str) {
        return SoundManager.getInstance().isPlaying(str);
    }

    public void stopPlayEffectPreview() {
        SoundManager.getInstance().stopPlayEffectPreview();
        SoundManager.getInstance().releaseOnPlayStateChangeListener();
    }

    public /* synthetic */ void lambda$new$7$SoundServerManager(String str, int i) {
        Logs.d("SoundServerManager OnPlayStateChangeListener path:" + str + " playState:" + i);
        Iterator<ISoundServerListener> it = this.mSoundUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onSoundEffectPlayStateChange(str, i);
        }
        soundCallback(InterfaceConstant.ON_SOUND_EFFECT_PLAY_STATE_CHANGED, JsonUtils.toJSONString(new SoundBean(str, i)));
    }
}
