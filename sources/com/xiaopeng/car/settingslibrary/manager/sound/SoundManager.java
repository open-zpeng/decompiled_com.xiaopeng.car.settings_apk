package com.xiaopeng.car.settingslibrary.manager.sound;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.audioConfig.AudioConfig;
import android.media.audioConfig.SoundFieldData;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.sound.MediaPlayerHelper;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectTheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes.dex */
public class SoundManager implements DisplayEventMonitorHelper.DisplayEventMonitorListener {
    public static final int MAIN_DRIVER_EXCLUSIVE_OFF = 0;
    public static final int MAIN_DRIVER_EXCLUSIVE_QUIET = 2;
    public static final int MAIN_DRIVER_EXCLUSIVE_VIP = 1;
    public static final int SAFETY_VOLUME_HIGH = 2;
    public static final int SAFETY_VOLUME_LOW = 0;
    public static final int SAFETY_VOLUME_NORMAL = 1;
    private static final String TAG = "SoundManager";
    private static volatile SoundManager sInstance;
    private AudioConfig mAudioConfig;
    private AudioManager mAudioManager;
    private Context mContext;
    private DisplayEventMonitorHelper mDisplayEventMonitorHelper;
    private boolean mIsInCall;
    private int[] mLastEqualizerArray;
    PlaySoundHelper[] mPlaySoundHelpers;
    private SoundEventReceiver mReceiver;
    private final List<OnVolumeChangedListener> mListeners = new ArrayList();
    private XuiSettingsManager mManager = XuiSettingsManager.getInstance();
    private CarSettingsManager mCarManager = CarSettingsManager.getInstance();
    private List<SystemSoundChangeListener> mSystemSoundChangeListenerList = new ArrayList();
    private List<String> mRegisterAvasPages = new ArrayList();
    private int mLastEqualizerType = -1;
    private int mLastEqualizerValue = -1;
    private MicReceiver mMicReceiver = new MicReceiver();

    /* loaded from: classes.dex */
    public interface OnVolumeChangedListener {
        void onVolumeChanged(int i, int i2, int i3);
    }

    /* loaded from: classes.dex */
    public interface SystemSoundChangeListener {
        void onSystemSoundChange();
    }

    public static SoundManager getInstance() {
        if (sInstance == null) {
            synchronized (SoundManager.class) {
                if (sInstance == null) {
                    sInstance = new SoundManager(CarSettingsApp.getContext());
                }
            }
        }
        return sInstance;
    }

    private SoundManager(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        this.mAudioConfig = new AudioConfig(context);
        this.mPlaySoundHelpers = new PlaySoundHelper[]{new PlaySoundHelper(context, 3), new PlaySoundHelper(context, 10), new PlaySoundHelper(context, 9), new PlaySoundHelper(context, Config.AVAS_STREAM), new PlaySoundHelper(context, 13)};
        this.mDisplayEventMonitorHelper = new DisplayEventMonitorHelper(context, this);
    }

    public void playSoundForStreamType(int i) {
        PlaySoundHelper[] playSoundHelperArr;
        for (PlaySoundHelper playSoundHelper : this.mPlaySoundHelpers) {
            if (i == playSoundHelper.getStreamType()) {
                playSoundHelper.postStartSample();
                return;
            }
        }
    }

    public void soundPlayerStart() {
        for (PlaySoundHelper playSoundHelper : this.mPlaySoundHelpers) {
            playSoundHelper.start();
        }
    }

    public void soundPlayerStop() {
        for (PlaySoundHelper playSoundHelper : this.mPlaySoundHelpers) {
            playSoundHelper.stop();
        }
    }

    public int getStreamMaxVolume(int i) {
        return this.mAudioManager.getStreamMaxVolume(i);
    }

    public int getStreamMinVolume(int i) {
        return this.mAudioManager.getStreamMinVolume(i);
    }

    public int getStreamVolume(int i) {
        return this.mAudioManager.getStreamVolume(i);
    }

    public void setStreamVolume(int i, int i2, int i3) {
        int streamMaxVolume = getStreamMaxVolume(i);
        if (i2 > streamMaxVolume) {
            i2 = streamMaxVolume;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        Logs.d("xpsound audiomanager index:" + i2 + " streamType:" + i);
        long currentTimeMillis = System.currentTimeMillis();
        this.mAudioManager.setStreamVolume(i, i2, i3);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 50) {
            Logs.d("xpsound audiomanager index time:" + currentTimeMillis2);
        }
    }

    public boolean isStreamMute(int i) {
        return this.mAudioManager.isStreamMute(i);
    }

    public int getRingerMode() {
        return this.mAudioManager.getRingerMode();
    }

    public void setRingerMode(int i) {
        this.mAudioManager.setRingerMode(i);
    }

    public void setSystemMute(boolean z) {
        if (z) {
            setRingerMode(0);
        } else {
            setRingerMode(2);
        }
    }

    public void addOnVolumeChangedListener(OnVolumeChangedListener onVolumeChangedListener) {
        synchronized (this.mListeners) {
            if (this.mListeners.size() == 0 && this.mReceiver == null) {
                this.mReceiver = new SoundEventReceiver();
                this.mReceiver.register(this.mContext);
            }
            if (!this.mListeners.contains(onVolumeChangedListener)) {
                this.mListeners.add(onVolumeChangedListener);
            }
        }
    }

    public void removeOnVolumeChangedListener(OnVolumeChangedListener onVolumeChangedListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(onVolumeChangedListener);
            if (this.mListeners.size() == 0 && this.mReceiver != null) {
                this.mReceiver.unregister(this.mContext);
                this.mReceiver = null;
            }
        }
    }

    void notifyVolumeChanged(int i, int i2, int i3) {
        synchronized (this.mListeners) {
            for (OnVolumeChangedListener onVolumeChangedListener : this.mListeners) {
                onVolumeChangedListener.onVolumeChanged(i, i2, i3);
            }
        }
    }

    public void setStreamMute(int i, boolean z) {
        if (z) {
            this.mAudioManager.adjustStreamVolume(i, -100, 0);
        } else {
            this.mAudioManager.adjustStreamVolume(i, 100, BasicMeasure.EXACTLY);
        }
    }

    public boolean isMainDriverVip() {
        return getMainDriverExclusive() == 2;
    }

    public int getMainDriverExclusive() {
        return this.mAudioConfig.getMainDriverMode();
    }

    public void setMainDriverExclusive(int i, boolean z, boolean z2, boolean z3) {
        if (!CarConfigHelper.hasMainDriverVIP()) {
            Logs.d("xpsound no this feature ! setMainDriverVip");
            return;
        }
        Logs.d("xpsound setMainDriverVip type:" + i + " needRestore:" + z2 + " isSyncAccount:" + z);
        if (z2) {
            GlobalSettingsSharedPreference.setIntelligentHeadrestRestore(this.mContext, i);
        }
        this.mAudioConfig.setMainDriverMode(i);
        XpAccountManager.getInstance().saveMainDriverExclusive(i, z);
        if (z3) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$JXR4JAPrmlcyNWsOm0IgASIwgmg
                @Override // java.lang.Runnable
                public final void run() {
                    SoundServerManager.getInstance().onHeadRestModeChange();
                }
            });
        }
    }

    public boolean isSpeechRoundEnable() {
        boolean isSpeechSurroundOn = this.mAudioConfig.isSpeechSurroundOn();
        Logs.d("xuiservice SpeechRoundEnable get " + isSpeechSurroundOn);
        return isSpeechSurroundOn;
    }

    public void setSpeechRoundEnable(boolean z, boolean z2) {
        if (!CarConfigHelper.hasSoundArround()) {
            Logs.d("xpsound no this feature SpeechRoundEnable!");
        } else {
            this.mAudioConfig.setSpeechSurround(z);
        }
    }

    public boolean supportSurroundSoundEffect() {
        boolean supportSurroundSoundEffect = this.mAudioConfig.supportSurroundSoundEffect();
        Logs.d("xuiservice supportSurroundSoundEffect " + supportSurroundSoundEffect);
        return supportSurroundSoundEffect;
    }

    public int getSoundEffectMode() {
        int soundEffectMode = this.mAudioConfig.getSoundEffectMode();
        Logs.d("xpsound getSoundEffectMode " + soundEffectMode);
        return soundEffectMode;
    }

    public void notifySoundEffectChange(final boolean z) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$l7lKAbh-bnVRfSgR87WLGcLgBlo
            @Override // java.lang.Runnable
            public final void run() {
                SoundServerManager.getInstance().onSoundEffectChange(z);
            }
        });
    }

    public void setSoundEffectMode(final int i, final boolean z) {
        Logs.d("xpsound setSoundEffectMode " + i);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$BnFoTsuxKbgjsX1GjimpH8ZBMMM
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$setSoundEffectMode$2$SoundManager(i, z);
            }
        });
    }

    public /* synthetic */ void lambda$setSoundEffectMode$2$SoundManager(int i, boolean z) {
        this.mAudioConfig.setSoundEffectMode(i);
        notifySoundEffectChange(z);
    }

    public void setSoundEffectType(final int i, final int i2, final boolean z) {
        Logs.d("xpsound setSoundEffectType mode:" + i + " type:" + i2);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$dGL--WZP3429sBBGvQd5llYcm4s
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$setSoundEffectType$3$SoundManager(i, i2, z);
            }
        });
    }

    public /* synthetic */ void lambda$setSoundEffectType$3$SoundManager(int i, int i2, boolean z) {
        this.mAudioConfig.setSoundEffectType(i, i2);
        notifySoundEffectChange(z);
    }

    public int getSoundEffectType(int i) {
        int soundEffectType = this.mAudioConfig.getSoundEffectType(i);
        Logs.d("xpsound getSoundEffectType mode:" + i + " type:" + soundEffectType);
        return soundEffectType;
    }

    public void setSoundEffectScene(final int i, final int i2, final boolean z) {
        Logs.d("xpsound setSoundEffectScene mode:" + i + " scene:" + i2);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$wqcSwJ0SmlN97kCF5UTKAFmmBJE
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$setSoundEffectScene$4$SoundManager(i, i2, z);
            }
        });
    }

    public /* synthetic */ void lambda$setSoundEffectScene$4$SoundManager(int i, int i2, boolean z) {
        this.mAudioConfig.setSoundEffectScene(i, i2);
        notifySoundEffectChange(z);
    }

    public int getSoundEffectScene(int i) {
        int soundEffectScene = this.mAudioConfig.getSoundEffectScene(i);
        Logs.d("xpsound getSoundEffectScene mode:" + i + " scene:" + soundEffectScene);
        return soundEffectScene;
    }

    public void setDangerousTtsVolLevel(int i) {
        this.mAudioConfig.setDangerousTtsVolLevel(i);
    }

    public int getDangerousTtsVolLevel() {
        return this.mAudioConfig.getDangerousTtsVolLevel();
    }

    /* loaded from: classes.dex */
    private class SoundEventReceiver extends BroadcastReceiver {
        private static final int STREAM_INVALID = -1;

        private SoundEventReceiver() {
        }

        public void register(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            context.registerReceiver(this, intentFilter);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra;
            String action = intent.getAction();
            Log.d(SoundManager.TAG, "onReceive: action = " + action + " pkgName:" + intent.getStringExtra("android.media.vol_change.PACKAGE_NAME") + " current:" + context.getPackageName());
            if (!"android.media.VOLUME_CHANGED_ACTION".equals(action) || (intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1)) == -1) {
                return;
            }
            if (intExtra == 3 || intExtra == 9 || intExtra == 10 || intExtra == 0 || ((CarFunction.isSupportAvasSpeaker() && intExtra == Config.AVAS_STREAM) || (CarFunction.isSupportDoubleScreen() && intExtra == 13))) {
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                int intExtra3 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
                Logs.d("xpsound receiver sound change:" + intExtra2 + " pre:" + intExtra3 + " streamType:" + intExtra);
                if (intExtra == 0) {
                    intExtra = 10;
                }
                SoundManager.this.notifyVolumeChanged(intExtra, intExtra2, intExtra3);
            }
        }
    }

    public void setVolumeDownWithNavigating(boolean z, boolean z2) {
        setDecreaseVolNavigating(z);
        if (z2) {
            XpAccountManager.getInstance().saveVolumeDownWithNavigating(z);
        }
    }

    public void setVolumeDownWithDoorOpen(boolean z, boolean z2) {
        this.mManager.setVolumeDownWithDoorOpen(z);
        if (z2) {
            XpAccountManager.getInstance().saveVolumeDownWithDoorOpen(z);
        }
    }

    public void setVolumeDownInReverseMode(boolean z, boolean z2) {
        this.mManager.setVolumeDownInReverseMode(z);
        if (z2) {
            XpAccountManager.getInstance().saveVolumeDownWithGearR(z);
        }
    }

    public boolean getVolumeDownInReverseMode() {
        return this.mManager.getVolumeDownInReverseMode();
    }

    public boolean getVolumeDownWithDoorOpen() {
        return this.mManager.getVolumeDownWithDoorOpen();
    }

    public void addSystemSoundChangeListener(SystemSoundChangeListener systemSoundChangeListener) {
        if (this.mSystemSoundChangeListenerList.contains(systemSoundChangeListener)) {
            return;
        }
        this.mSystemSoundChangeListenerList.add(systemSoundChangeListener);
    }

    public void removeSystemSoundChangeListener(SystemSoundChangeListener systemSoundChangeListener) {
        this.mSystemSoundChangeListenerList.remove(systemSoundChangeListener);
    }

    private void notifySystemSoundChange() {
        for (SystemSoundChangeListener systemSoundChangeListener : this.mSystemSoundChangeListenerList) {
            systemSoundChangeListener.onSystemSoundChange();
        }
    }

    public void setSystemSoundEnable(boolean z, boolean z2, boolean z3) {
        if (z) {
            this.mAudioConfig.enableSystemSound();
        } else {
            this.mAudioConfig.disableSystemSound();
        }
        if (z2) {
            notifySystemSoundChange();
        }
        if (z3) {
            XpAccountManager.getInstance().saveSystemSoundEnable(z);
        }
    }

    public boolean isSystemSoundEnabled() {
        try {
            return this.mAudioConfig.isSystemSoundEnabled();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public void setSafetyVolume(int i, boolean z) {
        if (i == 0) {
            this.mCarManager.setAlarmVolume(0);
            this.mAudioConfig.setDangerousTtsVolLevel(1);
        } else if (i == 1) {
            this.mCarManager.setAlarmVolume(1);
            this.mAudioConfig.setDangerousTtsVolLevel(2);
        } else if (i == 2) {
            this.mCarManager.setAlarmVolume(2);
            this.mAudioConfig.setDangerousTtsVolLevel(3);
        }
        GlobalSettingsSharedPreference.setPreferenceForKeyInt(this.mContext, Config.KEY_MEMORY_METER_VOLUME, i);
        XpAccountManager.getInstance().saveSafetyVolume(i, z);
    }

    private int getAlarmVolume() {
        return this.mCarManager.getAlarmVolume();
    }

    public int getSafetyVolume() {
        return getAlarmVolume();
    }

    public void setSoundField(final int i, int i2, int i3, final boolean z) {
        Logs.d("xpsound soundField " + i + " xSound:" + i2 + " ySound:" + i3);
        if (i2 < 0) {
            i2 = 0;
        }
        final int i4 = i2 > 100 ? 100 : i2;
        if (i3 < 0) {
            i3 = 0;
        }
        final int i5 = i3 > 100 ? 100 : i3;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$7xFshrDhvXg1wJu82jVXi8918EI
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$setSoundField$5$SoundManager(i, i4, i5, z);
            }
        });
    }

    public /* synthetic */ void lambda$setSoundField$5$SoundManager(int i, int i2, int i3, boolean z) {
        this.mAudioConfig.setSoundField(i, i2, i3);
        notifySoundEffectChange(z);
    }

    public SoundFieldValues getSoundField(int i) {
        SoundFieldData soundField = this.mAudioConfig.getSoundField(i);
        if (soundField != null) {
            SoundFieldValues soundFieldValues = new SoundFieldValues();
            soundFieldValues.setType(i);
            soundFieldValues.setXSound(soundField.x);
            soundFieldValues.setYSound(soundField.y);
            Logs.d("xpsound soundField get " + i + " xSound:" + soundField.x + " ySound:" + soundField.y);
            return soundFieldValues;
        }
        return null;
    }

    public void setDefaultEffect() {
        if (CarConfigHelper.hasHifiSound()) {
            setSoundEffectMode(1, true);
            setSoundEffectType(1, 1, true);
            return;
        }
        setSoundEffectMode(1, true);
        setSoundEffectType(1, 1, true);
        setSoundEffectScene(1, 4, true);
    }

    public int getMainDriverMode() {
        return this.mAudioConfig.getMainDriverMode();
    }

    public int getBtCallMode() {
        return this.mAudioConfig.getBtCallMode();
    }

    public void registerMicReceiver() {
        this.mContext.registerReceiver(this.mMicReceiver, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"));
    }

    public void unregisterMicReceiver() {
        this.mContext.unregisterReceiver(this.mMicReceiver);
    }

    /* loaded from: classes.dex */
    private class MicReceiver extends BroadcastReceiver {
        private MicReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("xpsound MicReceiver sound action:" + action);
            if ("android.media.action.MICROPHONE_MUTE_CHANGED".equals(action)) {
                SoundServerManager.getInstance().onTtsTipsChange();
            }
        }
    }

    public void setSoundPositionEnable(final boolean z, boolean z2) {
        try {
            this.mAudioManager.setSoundPositionEnable(z);
            if (z2) {
                ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$Glqc3YERP8GjNhNxS7wGhVUEHo4
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoundServerManager.getInstance().onSoundPositionEnable(z);
                    }
                });
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean getSoundPositionEnable() {
        try {
            return this.mAudioManager.getSoundPositionEnable();
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public void setAvasSpeakerSw(boolean z) {
        DataRepository.getInstance().setAvasSpeakerSw(z);
    }

    public boolean getAvasSpeakerSw() {
        return DataRepository.getInstance().getAvasSpeakerSw();
    }

    public void setSoundSpeedLinkLevel(int i) {
        this.mAudioManager.setSoundSpeedLinkLevel(i);
    }

    public int getSoundSpeedLinkLevel() {
        return this.mAudioManager.getSoundSpeedLinkLevel();
    }

    public void setDyn3dEffectLevel(int i) {
        this.mAudioManager.setDyn3dEffectLevel(i);
    }

    public int getDyn3dEffectLevel() {
        return this.mAudioManager.getDyn3dEffectLevel();
    }

    public SoundEffectTheme[] getAvailableThemes() {
        return XuiSettingsManager.getInstance().getAvailableThemes();
    }

    public int getActiveSoundEffectTheme() {
        return XuiSettingsManager.getInstance().getActiveSoundEffectTheme();
    }

    public SoundEffectResource[] getSoundEffectPreviewResource(int i) {
        return XuiSettingsManager.getInstance().getSoundEffectPreviewResource(i);
    }

    public int selectSoundEffectTheme(int i) {
        return XuiSettingsManager.getInstance().selectSoundEffectTheme(i);
    }

    public BootSoundResource getActiveBootSoundResource() {
        return XuiSettingsManager.getInstance().getActiveBootSoundResource();
    }

    public int getBootSoundOnOff() {
        return XuiSettingsManager.getInstance().getBootSoundOnOff();
    }

    public BootSoundResource[] getBootSoundResource() {
        return XuiSettingsManager.getInstance().getBootSoundResource();
    }

    public int setBootSoundOnOff(boolean z) {
        return XuiSettingsManager.getInstance().setBootSoundOnOff(z);
    }

    public int setBootSoundResource(int i) {
        return XuiSettingsManager.getInstance().setBootSoundResource(i);
    }

    public void setOnPlayStateChangeListener(MediaPlayerHelper.OnPlayStateChangeListener onPlayStateChangeListener) {
        MediaPlayerHelper.getInstance().setOnPlayStateChangeListener(onPlayStateChangeListener);
    }

    public void releaseOnPlayStateChangeListener() {
        MediaPlayerHelper.getInstance().releaseOnPlayStateChangeListener();
    }

    public void startPlayEffectPreview(String str) {
        MediaPlayerHelper.getInstance().play(str);
    }

    public boolean isPlaying(String str) {
        return MediaPlayerHelper.getInstance().isPlaying(str);
    }

    public void stopPlayEffectPreview() {
        MediaPlayerHelper.getInstance().stop();
    }

    public void registerAvasSpeakerChange(String str) {
        if (this.mRegisterAvasPages.isEmpty()) {
            this.mDisplayEventMonitorHelper.registerAvasSpeakerChange();
        }
        if (this.mRegisterAvasPages.contains(str)) {
            return;
        }
        this.mRegisterAvasPages.add(str);
    }

    public void unregisterAvasSpeakerChange(String str) {
        this.mRegisterAvasPages.remove(str);
        if (this.mRegisterAvasPages.isEmpty()) {
            this.mDisplayEventMonitorHelper.unregisterAvasSpeakerChange();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.DisplayEventMonitorListener
    public void onDisplayEventMonitorChange(Uri uri) {
        Logs.d("xpsound onDisplayEventMonitorChange uri:" + uri);
        if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.AVAS_LOUD_SPEAKER_SW))) {
            SoundServerManager.getInstance().onAvasSpeakerChanged(getAvasSpeakerSw());
        }
    }

    public boolean isDecreaseVolNavigating() {
        try {
            return this.mAudioManager.getNavVolDecreaseEnable();
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public void setDecreaseVolNavigating(boolean z) {
        try {
            this.mAudioManager.setNavVolDecreaseEnable(z);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void flushXpCustomizeEffects(final int[] iArr) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$PiibntUs2losmIORM_qCWGyrxos
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$flushXpCustomizeEffects$7$SoundManager(iArr);
            }
        });
    }

    public /* synthetic */ void lambda$flushXpCustomizeEffects$7$SoundManager(int[] iArr) {
        try {
            if (Arrays.equals(this.mLastEqualizerArray, iArr)) {
                Logs.d("xpsound effect equalizer flushXpCustomizeEffects repeat!");
                return;
            }
            Logs.d("xpsound effect equalizer flushXpCustomizeEffects value:" + Arrays.toString(iArr));
            this.mAudioManager.flushXpCustomizeEffects(iArr);
            this.mLastEqualizerArray = iArr;
            if (iArr == null || iArr.length <= 0) {
                return;
            }
            for (int i : iArr) {
                if (this.mLastEqualizerType == i) {
                    this.mLastEqualizerValue = iArr[i];
                    return;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public int getXpCustomizeEffect(int i) {
        try {
            int xpCustomizeEffect = this.mAudioManager.getXpCustomizeEffect(i);
            Logs.d("xpsound effect equalizer getXpCustomizeEffect type:" + i + " , value:" + xpCustomizeEffect);
            return xpCustomizeEffect;
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public void setXpCustomizeEffect(final int i, final int i2) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$SoundManager$ao-hvcN3xC4p7Ch9xI8F_sebWvk
            @Override // java.lang.Runnable
            public final void run() {
                SoundManager.this.lambda$setXpCustomizeEffect$8$SoundManager(i2, i);
            }
        });
    }

    public /* synthetic */ void lambda$setXpCustomizeEffect$8$SoundManager(int i, int i2) {
        try {
            if (this.mLastEqualizerValue == i && this.mLastEqualizerType == i2) {
                Logs.d("xpsound effect equalizer setXpCustomizeEffect repeat!");
                return;
            }
            Logs.d("xpsound effect equalizer setXpCustomizeEffect type:" + i2 + " , value:" + i);
            this.mAudioManager.setXpCustomizeEffect(i2, i);
            this.mLastEqualizerType = i2;
            this.mLastEqualizerValue = i;
            if (this.mLastEqualizerArray == null || this.mLastEqualizerArray.length <= i2) {
                return;
            }
            this.mLastEqualizerArray[i2] = i;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void saveEqualizerSwitch(int i, boolean z, boolean z2) {
        GlobalSettingsSharedPreference.setPreferenceForKeyInt(this.mContext, i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_SWITCH : Config.XP_SOUND_EFFECT_EQUALIZER_SWITCH, z ? 1 : 0);
        notifySoundEffectChange(z2);
    }

    public int getEqualizerSwitch(int i) {
        return GlobalSettingsSharedPreference.getPreferenceForKeyInt(this.mContext, i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_SWITCH : Config.XP_SOUND_EFFECT_EQUALIZER_SWITCH, 1);
    }

    public void saveEqualizerCustom(int i, int i2, boolean z) {
        GlobalSettingsSharedPreference.setPreferenceForKeyInt(this.mContext, i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_MODE : Config.XP_SOUND_EFFECT_EQUALIZER_MODE, i2);
        notifySoundEffectChange(z);
    }

    public int getEqualizerCustomValue(int i) {
        return GlobalSettingsSharedPreference.getPreferenceForKeyInt(this.mContext, i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_MODE : Config.XP_SOUND_EFFECT_EQUALIZER_MODE, 1);
    }

    public String getEqualizerValues(int i, int i2) {
        String str = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE1 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE1;
        if (i2 == 1) {
            str = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE1 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE1;
        } else if (i2 == 2) {
            str = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE2 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE2;
        }
        return GlobalSettingsSharedPreference.getPreferenceForKeyString(this.mContext, str, "");
    }

    public void saveEqualizerValues(int i, int i2, String str) {
        String str2 = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE1 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE1;
        if (i2 == 1) {
            str2 = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE1 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE1;
        } else if (i2 == 2) {
            str2 = i == 3 ? Config.DYNA_SOUND_EFFECT_EQUALIZER_VALUE2 : Config.XP_SOUND_EFFECT_EQUALIZER_VALUE2;
        }
        Logs.d("xpsound effect equalizer saveEqualizerValues " + str2 + " " + str);
        GlobalSettingsSharedPreference.setPreferenceForKeyString(this.mContext, str2, str);
    }

    public int[] convertEqualizerValues(int i, int i2) {
        String equalizerValues = getEqualizerValues(i, i2);
        if (TextUtils.isEmpty(equalizerValues)) {
            return new int[9];
        }
        String[] split = equalizerValues.split(",");
        int[] iArr = new int[split.length];
        for (int i3 = 0; i3 < split.length; i3++) {
            try {
                iArr[i3] = Integer.parseInt(split[i3]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return iArr;
    }
}
