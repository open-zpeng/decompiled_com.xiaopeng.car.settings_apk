package com.xiaopeng.car.settingslibrary.manager.speech;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.DisplayServerManager;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.display.XpDisplayManager;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.speech.protocol.bean.AdjustValue;
import com.xiaopeng.speech.protocol.bean.VolumeValue;
/* loaded from: classes.dex */
public class SpeechCallbackListener {
    public static final int BRIGHTNESS_DOWN = 1;
    public static final int BRIGHTNESS_DOWN_PERCENT = 5;
    public static final int BRIGHTNESS_MAX = 3;
    public static final int BRIGHTNESS_MIN = 2;
    private static final int BRIGHTNESS_TARGETTO = 6;
    public static final int BRIGHTNESS_UP = 0;
    public static final int BRIGHTNESS_UP_PERCENT = 4;
    private static final int DAY_NIGHT_SWITCH_DELAY = 300;
    public static final int ICM_BRIGHTNESS_DOWN = 1;
    public static final int ICM_BRIGHTNESS_DOWN_PERCENT = 5;
    public static final int ICM_BRIGHTNESS_MAX = 3;
    public static final int ICM_BRIGHTNESS_MIN = 2;
    public static final int ICM_BRIGHTNESS_TARGETTO = 6;
    public static final int ICM_BRIGHTNESS_UP = 0;
    public static final int ICM_BRIGHTNESS_UP_PERCENT = 4;
    private static final String TAG = "SpeechCallbackListener";
    private static final int VOLUME_LOWER = 1;
    private static final int VOLUME_RAISE = 2;
    private static final int VOLUME_TARGETTO = 3;
    BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    DataRepository mDataRepository;
    LaboratoryManager mLaboratoryManager;
    PowerManager mPowerManager;
    SoundManager mSoundManager;
    WifiManager mWifiManager;
    XpDisplayManager mXpDisplayManager;

    public void onVolumeResume() {
    }

    public boolean saveEnable(int i) {
        return i == 0 || i != 1;
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final SpeechCallbackListener instance = new SpeechCallbackListener(CarSettingsApp.getContext());

        private InnerFactory() {
        }
    }

    public static SpeechCallbackListener getInstance() {
        return InnerFactory.instance;
    }

    private SpeechCallbackListener(Context context) {
        this.mDataRepository = DataRepository.getInstance();
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        this.mXpDisplayManager = XpDisplayManager.getInstance();
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mSoundManager = SoundManager.getInstance();
        this.mLaboratoryManager = LaboratoryManager.getInstance();
    }

    public void onScreenBrightnessUp() {
        changeBrightness(0, 10);
    }

    public void onScreenBrightnessMax() {
        changeBrightness(255);
    }

    public void onScreenBrightnessDown() {
        changeBrightness(1, 10);
    }

    public void onScreenBrightnessMin() {
        changeBrightness(1);
    }

    public void onBrightnessUpPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onBrightnessUpPercent " + adjustValue.getValue());
        changeBrightness(4, adjustValue.getValue());
    }

    public void onBrightnessSetPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onBrightnessSetPercent:" + adjustValue.getValue());
        changeBrightness(this.mXpDisplayManager.getRealBrightnessByUI(adjustValue.getValue()));
    }

    public void onBrightnessDownPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
        } else {
            changeBrightness(5, adjustValue.getValue());
        }
    }

    public void onBrightnessSet(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onBrightnessSet:" + adjustValue.getValue());
        changeBrightness(this.mXpDisplayManager.getRealBrightnessByUI(adjustValue.getValue()));
    }

    public void onScreenBrightnessStop() {
        LogUtils.d("xpspeech onScreenBrightnessStop:");
    }

    public void onScreenBrightnessAutoOn() {
        Logs.d("xpspeech onScreenBrightnessAutoOn:");
        if (Utils.isPowerSavingMode()) {
            return;
        }
        this.mDataRepository.setAdaptiveBrightness(this.mContext, true, true);
    }

    public void onScreenBrightnessAutoOff() {
        Logs.d("xpspeech onScreenBrightnessAutoOff:");
        this.mDataRepository.setAdaptiveBrightness(this.mContext, false, true);
    }

    public void onVolumeNotificationMax(int i) {
        Logs.d("xpspeech onVolumeNotificationMax:");
        this.mSoundManager.setSafetyVolume(2, saveEnable(i));
    }

    public void onVolumeNotificationMedium(int i) {
        Logs.d("xpspeech onVolumeNotificationMedium:");
        this.mSoundManager.setSafetyVolume(1, saveEnable(i));
    }

    public void onVolumeNotificationMin(int i) {
        Logs.d("xpspeech onVolumeNotificationMin:");
        this.mSoundManager.setSafetyVolume(0, saveEnable(i));
    }

    public void onVolumeNotificationUp() {
        Logs.d("xpspeech onVolumeNotificationUp:");
        int safetyVolume = this.mSoundManager.getSafetyVolume();
        int i = 2;
        if (safetyVolume == 0) {
            i = 1;
        } else if (safetyVolume != 1) {
            if (safetyVolume == 2) {
                return;
            }
            i = -1;
        }
        if (i > 0) {
            this.mSoundManager.setSafetyVolume(i, true);
        }
    }

    public void onVolumeNotificationDown() {
        Logs.d("xpspeech onVolumeNotificationDown:");
        int safetyVolume = this.mSoundManager.getSafetyVolume();
        if (safetyVolume != 0) {
            int i = safetyVolume != 1 ? safetyVolume != 2 ? -1 : 1 : 0;
            if (i >= 2 || i < 0) {
                return;
            }
            this.mSoundManager.setSafetyVolume(i, true);
        }
    }

    public void onHeadsetMode(int i, int i2) {
        String string;
        Logs.d("xpspeech onHeadsetMode:" + i);
        SoundServerManager.getInstance().enterHeadrestScene();
        this.mSoundManager.setMainDriverExclusive(i, saveEnable(i2), true, true);
        if (i == 0) {
            string = this.mContext.getString(R.string.sound_headrest_sharing_model);
        } else if (i == 1) {
            string = this.mContext.getString(R.string.sound_headrest_enjoy_driving_model);
        } else {
            string = i != 2 ? "" : this.mContext.getString(R.string.sound_headrest_private_model);
        }
        SoundServerManager.getInstance().playHeadrestEffect(string);
    }

    public void onSoundEffectField(int i) {
        Point point;
        Logs.d("xpspeech onSoundEffectField:" + i);
        if (CarFunction.isDSeries()) {
            Point point2 = Config.mD21RealRecommendationPoint[SoundEffectMaps.convertSpeechToLocal(i)];
            Logs.d("xpspeech onSoundEffectField point:" + point2);
            if (point2 != null) {
                this.mSoundManager.setSoundField(1, point2.x, point2.y, false);
                return;
            }
            return;
        }
        Point point3 = null;
        if (CarFunction.isSupportRecentEffectMode()) {
            if (CarFunction.isMainPsnSharedSoundField()) {
                point = Config.mThreeRecommendationPoint[SoundEffectMaps.convertSpeechToLocalField(i)];
            } else {
                point = Config.mUnityRealRecommendationPoint[SoundEffectMaps.convertSpeechToLocalUnity(i)];
            }
            Logs.d("xpspeech onSoundEffectField point:" + point);
            if (point != null) {
                this.mSoundManager.setSoundField(1, point.x, point.y, false);
                return;
            }
            return;
        }
        int soundEffectMode = this.mSoundManager.getSoundEffectMode();
        if (soundEffectMode == 1) {
            point3 = Config.mRealRecommendationPoint[SoundEffectMaps.convertSpeechToLocal(i)];
        } else if (soundEffectMode == 3) {
            point3 = Config.mRealAdsorptionPoint[SoundEffectMaps.convertSpeechToLocal(i)];
        }
        Logs.d("xpspeech onSoundEffectField point:" + point3);
        if (point3 != null) {
            this.mSoundManager.setSoundField(soundEffectMode, point3.x, point3.y, false);
        }
    }

    public void onSoundEffectScene(int i) {
        Logs.d("xpspeech onSoundEffectScene:" + i);
        if (CarFunction.isDSeries() || CarFunction.isSupportRecentEffectMode()) {
            Logs.d("xpspeech onSoundEffectScene return");
        } else if (CarConfigHelper.isLowSpeaker()) {
        } else {
            if (this.mSoundManager.getSoundEffectMode() != 1) {
                this.mSoundManager.setSoundEffectMode(1, false);
            }
            int intValue = SoundEffectMaps.sSoundEffectSceneMap.get(Integer.valueOf(i)).intValue();
            Logs.d("xpspeech setSoundEffectScene targetScene:" + intValue);
            this.mSoundManager.setSoundEffectScene(1, intValue, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00cc, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound() != false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onSoundEffectStyle(int r7) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechCallbackListener.onSoundEffectStyle(int):void");
    }

    public void onSoundEffectMode(int i) {
        if (CarFunction.isDSeries() || CarFunction.isSupportRecentEffectMode()) {
            Logs.d("xpspeech onSoundEffectMode return");
            return;
        }
        int intValue = SoundEffectMaps.sSoundEffectModeMap.get(Integer.valueOf(i)).intValue();
        Logs.d("xpspeech onSoundEffectMode:" + i + " targetMode:" + intValue);
        this.mSoundManager.setSoundEffectMode(intValue, false);
    }

    private void changeBrightness(int i, int i2) {
        int realBrightnessByUI;
        int uIProgressByReal = this.mXpDisplayManager.getUIProgressByReal(this.mDataRepository.getScreenBrightness(this.mContext));
        Logs.d("xpspeech changeBrightness delta:" + i2 + " " + uIProgressByReal);
        if (i != 0) {
            if (i != 1) {
                if (i != 4) {
                    if (i != 5) {
                        realBrightnessByUI = -1;
                        changeBrightness(realBrightnessByUI);
                    }
                }
            }
            realBrightnessByUI = this.mXpDisplayManager.getRealBrightnessByUI(uIProgressByReal - i2);
            changeBrightness(realBrightnessByUI);
        }
        realBrightnessByUI = this.mXpDisplayManager.getRealBrightnessByUI(uIProgressByReal + i2);
        changeBrightness(realBrightnessByUI);
    }

    private void changeBrightness(int i) {
        Logs.d("xpspeech changeBrightness brightness:" + i);
        this.mDataRepository.setScreenBrightness(this.mContext, i);
    }

    public void onThemeModeAuto() {
        themeSwitch(0);
    }

    public void onThemeModeDay() {
        themeSwitch(1);
    }

    public void onThemeModeNight() {
        themeSwitch(2);
    }

    private void themeSwitch(final int i) {
        int dayNightMode = XpThemeUtils.getDayNightMode(this.mContext);
        Logs.d("xpspeech themeSwitch currentMode:" + dayNightMode + " targetMode:" + i);
        if (dayNightMode == i) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.speech.-$$Lambda$SpeechCallbackListener$k95S_8Nri1pV232I1b-YofVHXe4
            @Override // java.lang.Runnable
            public final void run() {
                DisplayServerManager.getInstance().onDayNightChanged();
            }
        });
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.speech.SpeechCallbackListener.1
            @Override // java.lang.Runnable
            public void run() {
                XpThemeUtils.setDayNightMode(SpeechCallbackListener.this.mContext, i);
            }
        }, 300L);
    }

    public void onScreenModeClean() {
        Logs.d("xpspeech cleanmode");
    }

    public void onWifiOff() {
        Logs.d("xpspeech wifi off");
        this.mWifiManager.setWifiEnabled(false);
    }

    public void onWifiOn() {
        Logs.d("xpspeech wifi on");
        this.mWifiManager.setWifiEnabled(true);
    }

    public void onBluetoothOff() {
        Logs.d("xpspeech bluetooth off");
        if (!this.mBluetoothAdapter.isEnabled()) {
            Logs.d("xpspeech bluetooth already off");
        } else {
            this.mBluetoothAdapter.disable();
        }
    }

    public void onBluetoothOn() {
        Logs.d("xpspeech bluetooth on");
        if (this.mBluetoothAdapter.isEnabled()) {
            Logs.d("xpspeech bluetooth already on");
        } else {
            this.mBluetoothAdapter.enable();
        }
        if (this.mContext != null) {
            Intent intent = new Intent(Config.POPUP_BLUETOOTH);
            intent.setFlags(268435456);
            this.mContext.startActivity(intent);
        }
    }

    public void onVolumeUp(VolumeValue volumeValue) {
        if (volumeValue == null) {
            Logs.d("xpspeech onVolume null");
            return;
        }
        Logs.d("xpspeech onVolumeUp " + volumeValue.getStreamType());
        changeVolume(2, volumeValue);
    }

    public void onVolumeDown(VolumeValue volumeValue) {
        if (volumeValue == null) {
            Logs.d("xpspeech onVolume null");
            return;
        }
        Logs.d("xpspeech onVolumeDown " + volumeValue.getStreamType());
        changeVolume(1, volumeValue);
    }

    public void onVolumeMax(int i) {
        Logs.d("xpspeech onVolumeMax " + i);
        changeMediaVolume(SoundManager.getInstance().getStreamMaxVolume(i), i);
    }

    public void onVolumeMin(int i) {
        Logs.d("xpspeech onVolumeMin " + i);
        changeMediaVolume(0, i);
    }

    public void onVolumeMute(int i) {
        Logs.d("xpspeech onVolumeMute " + i);
        if (i == 1) {
            this.mSoundManager.setSystemSoundEnable(false, true, true);
            return;
        }
        if (i == 3 && CarFunction.isSupportAvasSpeaker() && DataRepository.getInstance().getAvasSpeakerSw()) {
            i = Config.AVAS_STREAM;
        }
        this.mSoundManager.setStreamMute(i, true);
    }

    public void onVolumeUnmute(int i) {
        Logs.d("xpspeech onVolumeUnmute " + i);
        if (i == 1) {
            this.mSoundManager.setSystemSoundEnable(true, true, true);
            return;
        }
        if (i == 3 && CarFunction.isSupportAvasSpeaker() && DataRepository.getInstance().getAvasSpeakerSw()) {
            i = Config.AVAS_STREAM;
        }
        this.mSoundManager.setStreamMute(i, false);
    }

    public void onVolumeSet(VolumeValue volumeValue) {
        if (volumeValue == null) {
            Logs.d("xpspeech onVolumeSet null");
            return;
        }
        AdjustValue adjustValue = volumeValue.getAdjustValue();
        if (adjustValue != null && adjustValue.isPercent()) {
            Logs.d("xpspeech onVolumeSet " + adjustValue.isPercent() + " " + adjustValue.getValue());
            changeMediaVolume((int) (((double) this.mSoundManager.getStreamMaxVolume(volumeValue.getStreamType())) * (((double) adjustValue.getValue()) / 100.0d)), volumeValue.getStreamType());
            return;
        }
        changeVolume(3, volumeValue);
    }

    public void onIcmBrightnessUp() {
        Logs.d("xpspeech onIcmBrightnessUp ");
        changeICMBrightness(0, 10);
    }

    public void onIcmBrightnessMax() {
        Logs.d("xpspeech onIcmBrightnessMax ");
        changeICMBrightness(100);
    }

    public void onIcmBrightnessDown() {
        Logs.d("xpspeech onIcmBrightnessDown ");
        changeICMBrightness(1, 10);
    }

    public void onIcmBrightnessMin() {
        Logs.d("xpspeech onIcmBrightnessMin ");
        changeICMBrightness(1);
    }

    public void onOpenWifiPage() {
        StringBuilder sb = new StringBuilder();
        sb.append("xpspeech onOpenWifiPage ");
        sb.append(this.mContext == null ? " context is null " : "");
        Logs.d(sb.toString());
        if (this.mContext != null) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(Uri.parse("xiaopeng://setting/wifi"));
            this.mContext.startActivity(intent);
        }
    }

    public void onOpenSettingPage() {
        StringBuilder sb = new StringBuilder();
        sb.append("xpspeech onOpenSettingPage ");
        sb.append(this.mContext == null ? " context is null " : "");
        Logs.d(sb.toString());
        if (this.mContext != null) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(Uri.parse("xiaopeng://setting/bluetooth"));
            this.mContext.startActivity(intent);
        }
    }

    public void onSettingOpen() {
        Logs.d("xpspeech onSettingOpen");
    }

    public void onIcmBrightnessUpPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech onIcmBrightnessUpPercent adjustValue null");
            return;
        }
        Logs.d("xpspeech onIcmBrightnessUpPercent " + adjustValue.getValue());
        changeICMBrightness(4, adjustValue.getValue());
    }

    public void onIcmBrightnessSetPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            LogUtils.d("xpspeech onIcmBrightnessSetPercent null");
            return;
        }
        Logs.d("xpspeech onIcmBrightnessSetPercent:" + adjustValue.getValue());
        changeICMBrightness(adjustValue.getValue());
    }

    public void onIcmBrightnessDownPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech onIcmBrightnessDownPercent adjustValue null");
            return;
        }
        Logs.d("xpspeech onIcmBrightnessDownPercent ");
        changeICMBrightness(5, adjustValue.getValue());
    }

    private void changeICMBrightness(int i, int i2) {
        int i3;
        int meterBrightness = this.mXpDisplayManager.getMeterBrightness(this.mContext.getApplicationContext());
        Logs.d("xpspeech changeBrightness type=" + i + " delta:" + i2 + " " + meterBrightness);
        if (i != 0) {
            if (i != 1) {
                if (i != 4) {
                    if (i != 5) {
                        i3 = -1;
                        changeICMBrightness(i3);
                    }
                }
            }
            i3 = meterBrightness - i2;
            changeICMBrightness(i3);
        }
        i3 = meterBrightness + i2;
        changeICMBrightness(i3);
    }

    private void changeICMBrightness(int i) {
        Logs.d("xpspeech setMeterDisplayProgress " + i);
        this.mDataRepository.setIcmBrightness(this.mContext, i);
    }

    public void onScreenOff() {
        Logs.d("xpspeech onScreenOff ");
        try {
            this.mPowerManager.setXpScreenOff(SystemClock.uptimeMillis());
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void onScreenOn() {
        Logs.d("xpspeech onScreenOn ");
        try {
            this.mPowerManager.setXpScreenOn(SystemClock.uptimeMillis());
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void onIcmBrightnessSet(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech onIcmBrightnessSet value null ");
            return;
        }
        Logs.d("xpspeech onIcmBrightnessSet:" + adjustValue.getValue());
    }

    private void changeVolume(int i, VolumeValue volumeValue) {
        AdjustValue adjustValue = volumeValue.getAdjustValue();
        if (adjustValue == null) {
            LogUtils.d("xpspeech changeValue null");
            return;
        }
        int streamType = volumeValue.getStreamType();
        int value = adjustValue.getValue();
        this.mSoundManager.setSystemMute(false);
        int streamVolume = this.mSoundManager.getStreamVolume(streamType);
        Logs.d("xpspeech volume_type:" + i + " value:" + value + " curVolume =" + streamVolume);
        if (i == 1) {
            value = streamVolume - value;
        } else if (i == 2) {
            value += streamVolume;
        } else if (i != 3) {
            value = streamVolume;
        }
        changeMediaVolume(value, streamType);
    }

    private void changeMediaVolume(int i, int i2) {
        int i3 = i >= this.mSoundManager.getStreamMaxVolume(i2) ? 0 : 4;
        if (i <= 0) {
            i3 = 0;
        }
        Logs.d("xpspeech--- changeMediaVolume target=" + i);
        if (i2 == 3 && CarFunction.isSupportAvasSpeaker() && DataRepository.getInstance().getAvasSpeakerSw()) {
            i2 = Config.AVAS_STREAM;
        }
        this.mSoundManager.setStreamVolume(i2, i, i3);
        this.mSoundManager.playSoundForStreamType(i2);
    }

    public void onSystemFontSetting(int i, int i2) {
        Logs.d("xpspeech--- onSystemFrontSetting mode=" + i);
        if (i == 0) {
            this.mDataRepository.setFontSize(this.mContext, 0.9f, saveEnable(i2));
        } else if (i != 1) {
        } else {
            this.mDataRepository.setFontSize(this.mContext, 1.0f, saveEnable(i2));
        }
    }

    public void onSystemTimeFormatSetting(int i) {
        Logs.d("xpspeech--- onSystemTimeFormatSetting mode=" + i);
        if (i == 0) {
            this.mDataRepository.set24HourFormat(this.mContext, true, true);
        } else if (i != 1) {
        } else {
            this.mDataRepository.set24HourFormat(this.mContext, false, true);
        }
    }

    public void onSystemTimeFormatSetting(int i, int i2) {
        Logs.d("xpspeech--- onSystemTimeFormatSetting mode=" + i);
        if (i == 0) {
            this.mDataRepository.set24HourFormat(this.mContext, true, saveEnable(i2));
        } else if (i != 1) {
        } else {
            this.mDataRepository.set24HourFormat(this.mContext, false, saveEnable(i2));
        }
    }

    public void onSystemLowSpeedAnalogtoneHigh(int i) {
        int lowSpeedVolume = this.mLaboratoryManager.getLowSpeedVolume();
        Logs.d("xpspeech--- onSystemLowSpeedAnalogtoneHigh value=" + i + " volume:" + lowSpeedVolume);
        this.mLaboratoryManager.setLowSpeedVolume(lowSpeedVolume + i);
    }

    public void onSystemLowSpeedAnalogtoneLow(int i) {
        int lowSpeedVolume = this.mLaboratoryManager.getLowSpeedVolume();
        Logs.d("xpspeech--- onSystemLowSpeedAnalogtoneLow value=" + i + " volume:" + lowSpeedVolume);
        this.mLaboratoryManager.setLowSpeedVolume(lowSpeedVolume - i);
    }

    public void onSystemLowSpeedAnalogtoneMax() {
        Logs.d("xpspeech--- onSystemLowSpeedAnalogtoneMax");
        this.mLaboratoryManager.setLowSpeedVolume(100);
    }

    public void onSystemLowSpeedAnalogtoneMin() {
        Logs.d("xpspeech--- onSystemLowSpeedAnalogtoneMin");
        this.mLaboratoryManager.setLowSpeedVolume(0);
    }

    public void onSystemLowSpeedAnalogtoneSet(int i) {
        Logs.d("xpspeech--- onSystemLowSpeedAnalogtoneSet " + i);
        this.mLaboratoryManager.setLowSpeedVolume(i);
    }

    public void onSystemSentryModeSet(int i) {
        Logs.d("xpspeech--- onSystemSentryModeSet " + i);
        this.mLaboratoryManager.setSoldierSw(i);
    }

    public void onSystemUsageRestrictionsOn() {
        Logs.d("xpspeech--- onSystemUsageRestrictionsOn ");
        this.mLaboratoryManager.setAppUsedLimitEnable(true, false);
    }

    public void onSystemKeyParkingOn() {
        Logs.d("xpspeech--- onSystemKeyParkingOn ");
        if (CarFunction.isSupportKeyPark() && LaboratoryManager.getInstance().getNetConfigValues().isRemoteControlKeyEnable()) {
            this.mLaboratoryManager.setKeyParkingEnable(true);
        } else if (CarFunction.isSupportRemotePark() && LaboratoryManager.getInstance().getNetConfigValues().isRemoteControlEnable()) {
            this.mLaboratoryManager.setRemoteParkingEnable(true);
        } else {
            Logs.d("xpspeech--- onSystemKeyParkingOn no support!");
        }
    }

    public void onSystemKeyParkingOff() {
        Logs.d("xpspeech--- onSystemKeyParkingOff ");
        if (CarFunction.isSupportKeyPark() && LaboratoryManager.getInstance().getNetConfigValues().isRemoteControlKeyEnable()) {
            this.mLaboratoryManager.setKeyParkingEnable(false);
        } else if (CarFunction.isSupportRemotePark() && LaboratoryManager.getInstance().getNetConfigValues().isRemoteControlEnable()) {
            this.mLaboratoryManager.setRemoteParkingEnable(false);
        } else {
            Logs.d("xpspeech--- onSystemKeyParkingOff no support!");
        }
    }

    public void onSystemKeyParkingEnhancedOff() {
        Logs.d("xpspeech--- onSystemKeyParkingEnhancedOff ");
        if (CarFunction.isSupportRemoteParkAdvanced() && LaboratoryManager.getInstance().getNetConfigValues().isRemoteControlAdvancedEnable()) {
            this.mLaboratoryManager.setRemoteParkingAdvancedEnable(false);
        }
    }

    public void onOpenScreen(int i) {
        Logs.d("xpspeech--- onOpenScreen " + i);
        if (i == 0) {
            this.mPowerManager.setXpScreenOn("xp_mt_ivi", SystemClock.uptimeMillis());
            openSubScreen();
        } else if (i == 1) {
            this.mPowerManager.setXpScreenOn("xp_mt_ivi", SystemClock.uptimeMillis());
        } else if (i == 2) {
            openSubScreen();
        }
    }

    private void openSubScreen() {
        if (Utils.isPsnIdleOn(CarSettingsApp.getContext())) {
            this.mPowerManager.xpRestetScreenIdle("xp_mt_psg", false);
        } else {
            this.mPowerManager.setXpScreenOn("xp_mt_psg", SystemClock.uptimeMillis());
        }
    }

    public void onCloseScreen(int i) {
        Logs.d("xpspeech--- onCloseScreen " + i);
        if (i == 0) {
            this.mPowerManager.setXpScreenOff("xp_mt_ivi", SystemClock.uptimeMillis());
            closeSubScreen();
        } else if (i == 1) {
            this.mPowerManager.setXpScreenOff("xp_mt_ivi", SystemClock.uptimeMillis());
        } else if (i == 2) {
            closeSubScreen();
        }
    }

    private void closeSubScreen() {
        boolean isPsnIdleOn = Utils.isPsnIdleOn(CarSettingsApp.getContext());
        Logs.d("xpspeech--- psnIdleState " + isPsnIdleOn);
        this.mPowerManager.setXpScreenOff("xp_mt_psg", SystemClock.uptimeMillis());
    }

    public void onIcmBrightnessAutoOn(int i, int i2) {
        Logs.d("xpspeech--- onIcmBrightnessAutoOn " + i);
        this.mDataRepository.setMeterAdaptiveBrightness(true, saveEnable(i2));
    }

    public void onIcmBrightnessAutoOff(int i, int i2) {
        Logs.d("xpspeech--- onIcmBrightnessAutoOn " + i);
        this.mDataRepository.setMeterAdaptiveBrightness(false, saveEnable(i2));
    }

    public void onPolitScreenBrightnessAutoOn(int i, int i2) {
        Logs.d("xpspeech--- onPolitScreenBrightnessAutoOn " + i);
        if (i == 0) {
            this.mDataRepository.setMainAdaptiveBrightness(true, saveEnable(i2));
            this.mDataRepository.setPsnAdaptiveBrightness(true);
        } else if (i == 1) {
            this.mDataRepository.setMainAdaptiveBrightness(true, saveEnable(i2));
        } else if (i == 2) {
            this.mDataRepository.setPsnAdaptiveBrightness(true);
        }
    }

    public void onPolitScreenBrightnessAutoOff(int i, int i2) {
        Logs.d("xpspeech--- onPolitScreenBrightnessAutoOff " + i);
        if (i == 0) {
            this.mDataRepository.setMainAdaptiveBrightness(false, saveEnable(i2));
            this.mDataRepository.setPsnAdaptiveBrightness(false);
        } else if (i == 1) {
            this.mDataRepository.setMainAdaptiveBrightness(false, saveEnable(i2));
        } else if (i == 2) {
            this.mDataRepository.setPsnAdaptiveBrightness(false);
        }
    }

    public void onPsnScreenBrightnessUp() {
        changePsnBrightness(0, 10);
    }

    public void onPsnBrightnessUpPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onPsnBrightnessUpPercent " + adjustValue.getValue());
        changePsnBrightness(4, adjustValue.getValue());
    }

    private void changePsnBrightness(int i, int i2) {
        int realBrightnessByUI;
        int uIProgressByReal = this.mXpDisplayManager.getUIProgressByReal(this.mDataRepository.getPsnScreenBrightness(this.mContext));
        Logs.d("xpspeech changePsnBrightness delta:" + i2 + " " + uIProgressByReal);
        if (i != 0) {
            if (i != 1) {
                if (i != 4) {
                    if (i != 5) {
                        realBrightnessByUI = -1;
                        this.mDataRepository.setPsnScreenBrightness(this.mContext, realBrightnessByUI);
                    }
                }
            }
            realBrightnessByUI = this.mXpDisplayManager.getRealBrightnessByUI(uIProgressByReal - i2);
            this.mDataRepository.setPsnScreenBrightness(this.mContext, realBrightnessByUI);
        }
        realBrightnessByUI = this.mXpDisplayManager.getRealBrightnessByUI(uIProgressByReal + i2);
        this.mDataRepository.setPsnScreenBrightness(this.mContext, realBrightnessByUI);
    }

    public void onPsnBrightnessSetPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onPsnBrightnessSetPercent:" + adjustValue.getValue());
        this.mDataRepository.setPsnScreenBrightness(this.mContext, this.mXpDisplayManager.getRealBrightnessByUI(adjustValue.getValue()));
    }

    public void onPsnBrightnessSet(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
            return;
        }
        Logs.d("xpspeech onPsnBrightnessSet:" + adjustValue.getValue());
        this.mDataRepository.setPsnScreenBrightness(this.mContext, this.mXpDisplayManager.getRealBrightnessByUI(adjustValue.getValue()));
    }

    public void onPsnScreenBrightnessMax() {
        Logs.d("xpspeech onPsnScreenBrightnessMax");
        this.mDataRepository.setPsnScreenBrightness(this.mContext, 255);
    }

    public void onPsnBrightnessDownPercent(AdjustValue adjustValue) {
        if (adjustValue == null) {
            Logs.d("xpspeech speech value null ");
        } else {
            changePsnBrightness(5, adjustValue.getValue());
        }
    }

    public void onPsnScreenBrightnessDown() {
        changePsnBrightness(1, 10);
    }

    public void onPsnScreenBrightnessMin() {
        this.mDataRepository.setPsnScreenBrightness(this.mContext, 1);
    }

    public void onPositionReminderOn() {
        Logs.d("xpspeech onPositionReminderOn");
        this.mSoundManager.setSoundPositionEnable(true, true);
    }

    public void onPositionReminderOff() {
        Logs.d("xpspeech onPositionReminderOff");
        this.mSoundManager.setSoundPositionEnable(false, true);
    }

    public void onPilotBlueToothOff(int i) {
        Logs.d("xpspeech onPilotBlueToothOff " + i);
        if (i == 1) {
            if (XpBluetoothManger.getInstance().isEnable()) {
                XpBluetoothManger.getInstance().setBluetoothEnabled(false);
            } else {
                Logs.d("xpspeech blueTooth already off");
            }
        } else if (i == 2) {
            if (PsnBluetoothManger.getInstance().isUsbEnable()) {
                PsnBluetoothManger.getInstance().setUsbBluetoothEnabled(false);
            }
        } else {
            Logs.d("xpspeech psn blueTooth already off");
        }
    }

    public void onPilotBlueToothOn(int i) {
        Logs.d("xpspeech onPilotBlueToothOn " + i);
        if (i == 1) {
            if (!XpBluetoothManger.getInstance().isEnable()) {
                XpBluetoothManger.getInstance().setBluetoothEnabled(true);
            } else {
                Logs.d("xpspeech blueTooth already on");
            }
        } else if (i == 2) {
            if (PsnBluetoothManger.getInstance().isUsbEnable()) {
                return;
            }
            PsnBluetoothManger.getInstance().setUsbBluetoothEnabled(true);
        } else {
            Logs.d("xpspeech psn blueTooth already on");
        }
    }

    public void onImmerseSoundSet(int i) {
        Logs.d("xpspeech onImmerseSoundSet " + i);
    }

    public void onRecordVideoPhoneOff() {
        Logs.d("xpspeech onRecordVideoPhoneOff ");
        LaboratoryManager.getInstance().setSoldierCameraSw(false);
    }

    public void onSoundExternalSet(int i) {
        Logs.d("xpspeech onSoundExternalSet " + i);
        if (i == 1) {
            SoundManager.getInstance().setAvasSpeakerSw(true);
        } else {
            SoundManager.getInstance().setAvasSpeakerSw(false);
        }
    }

    public void controlBluetoothConnect(String str) {
        Logs.d("connect assigned device Bluetooth" + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo = XpBluetoothManger.getInstance().getXpBluetoothDeviceInfo(str);
        if (xpBluetoothDeviceInfo == null) {
            Logs.d("Device is NULL");
        } else {
            XpBluetoothManger.getInstance().connectAndRetry(xpBluetoothDeviceInfo);
        }
    }

    public void onCurrentSoundEffectEqualizerMode(int i) {
        Logs.d("xpspeech--- onCurrentSoundEffectEqualizerMode " + i);
        int i2 = i != 2 ? 1 : 2;
        int soundEffectMode = this.mSoundManager.getSoundEffectMode();
        this.mSoundManager.saveEqualizerCustom(soundEffectMode, i2, false);
        if (this.mSoundManager.getEqualizerSwitch(soundEffectMode) == 0) {
            this.mSoundManager.saveEqualizerSwitch(soundEffectMode, true, false);
        }
        SoundManager soundManager = this.mSoundManager;
        soundManager.flushXpCustomizeEffects(soundManager.convertEqualizerValues(soundEffectMode, i2));
    }

    public void onRearScreenOpen() {
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.REAR_SCREEN_CONTROL, true);
    }

    public void onRearScreenClose() {
        this.mDataRepository.setSettingProvider(this.mContext, XPSettingsConfig.REAR_SCREEN_CONTROL, false);
    }

    public void onRearScreenLockOn() {
        this.mXpDisplayManager.setRearScreenLock(true, true);
    }

    public void onRearScreenLockOff() {
        this.mXpDisplayManager.setRearScreenLock(false, true);
    }
}
