package com.xiaopeng.car.settingslibrary.manager.speech;

import android.content.Context;
import android.graphics.Point;
import android.media.AudioSystem;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.display.XpDisplayManager;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.manager.speech.bean.BluetoothDevicesBean;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class SpeechSystemCaller {
    XpBluetoothManger mBluetoothManager;
    Context mContext;
    DataRepository mDataRepository;
    LaboratoryManager mLaboratoryManager;
    SoundManager mSoundManager;
    XpDisplayManager mXpDisplayManager;

    public static int typeToCategory(int i) {
        if (i != -1) {
            int i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    return -1;
                }
            }
            return i2;
        }
        return 3;
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final SpeechSystemCaller instance = new SpeechSystemCaller(CarSettingsApp.getContext());

        private InnerFactory() {
        }
    }

    public static SpeechSystemCaller getInstance() {
        return InnerFactory.instance;
    }

    private SpeechSystemCaller(Context context) {
        this.mDataRepository = DataRepository.getInstance();
        this.mContext = context;
        this.mSoundManager = SoundManager.getInstance();
        this.mXpDisplayManager = XpDisplayManager.getInstance();
        this.mLaboratoryManager = LaboratoryManager.getInstance();
        this.mBluetoothManager = XpBluetoothManger.getInstance();
    }

    public int getCurScreenBrightness() {
        int uIProgressByReal = this.mXpDisplayManager.getUIProgressByReal(this.mDataRepository.getScreenBrightness(this.mContext));
        Logs.d("speech getCurScreenBrightness " + uIProgressByReal);
        return uIProgressByReal;
    }

    public int getMaxScreenBrightnessValue() {
        Logs.d("speech getMaxScreenBrightnessValue ");
        return 100;
    }

    public int getMinScreenBrightnessValue() {
        Logs.d("speech getMinScreenBrightnessValue ");
        return 1;
    }

    public int getCurIcmBrightness() {
        int meterBrightness = this.mXpDisplayManager.getMeterBrightness(this.mContext.getApplicationContext());
        Logs.d("speech getCurIcmBrightness " + meterBrightness);
        return meterBrightness;
    }

    public int getMaxIcmBrightnessValue() {
        Logs.d("speech getMaxIcmBrightnessValue ");
        return 100;
    }

    public int getMinIcmBrightnessValue() {
        Logs.d("speech getMinIcmBrightnessValue ");
        return 1;
    }

    public int getCurVolume(int i) {
        int streamVolume = this.mSoundManager.getStreamVolume(i);
        Logs.d("speech getCurVolume " + streamVolume);
        return streamVolume;
    }

    public int getMaxVolumeValue(int i) {
        int streamMaxVolume = this.mSoundManager.getStreamMaxVolume(i);
        Logs.d("speech getMaxVolumeValue " + streamMaxVolume);
        return streamMaxVolume;
    }

    public int getMinVolumeValue(int i) {
        int streamMinVolume = this.mSoundManager.getStreamMinVolume(i);
        Logs.d("speech getMinVolumeValue " + streamMinVolume);
        return streamMinVolume;
    }

    public int getTipsVolume() {
        int safetyVolume = this.mSoundManager.getSafetyVolume();
        Logs.d("speech getSafetyVolume " + safetyVolume);
        return safetyVolume;
    }

    public int getUnityPageOpenState(String str) {
        String urlFillHeader = urlFillHeader(str);
        if (ElementDirectManager.get() != null && ElementDirectManager.get().checkUnitySupport(urlFillHeader)) {
            Logs.d("getUnityPageOpenState 0 : " + urlFillHeader);
            return 0;
        }
        Logs.d("getUnityPageOpenState 1 : " + urlFillHeader);
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getUnityPageShowState(String str) {
        if (CarFunction.isNonSelfPageUI()) {
            Logs.d("getUnityPageShowState supportMultiParams: " + str);
            UnityPageDirectParams unityPageDirectParams = (UnityPageDirectParams) JsonUtils.toBean(str, UnityPageDirectParams.class);
            int i = -1;
            i = -1;
            if (unityPageDirectParams.getDisplay_location() != null && !unityPageDirectParams.getDisplay_location().isEmpty()) {
                i = Integer.parseInt(unityPageDirectParams.getDisplay_location());
            } else if (unityPageDirectParams.getSound_location() != null && !unityPageDirectParams.getSound_location().isEmpty()) {
                i = "2".equals(unityPageDirectParams.getSound_location());
            }
            String urlFillHeader = urlFillHeader(unityPageDirectParams.getPage_id());
            if (!urlFillHeader.contains("?screenId=") && !urlFillHeader.contains("&screenId=")) {
                StringBuilder sb = new StringBuilder();
                sb.append(urlFillHeader);
                sb.append(urlFillHeader.contains("?") ? "&" : "?");
                sb.append(ISpeechContants.KEY_SCREEN_ID);
                sb.append("=");
                sb.append(i);
                urlFillHeader = sb.toString();
            }
            if (ElementDirectManager.get() != null && ElementDirectManager.get().checkUnitySupport(urlFillHeader)) {
                Logs.d("getUnityPageShowState 0 : " + urlFillHeader);
                return !ElementDirectManager.get().isShowingUnityPage(this.mContext, urlFillHeader);
            }
            Logs.d("getUnityPageOpenState 1 : " + urlFillHeader);
        }
        return 1;
    }

    private String urlFillHeader(String str) {
        return str.equals("CarControl.BluetoothDialog?from=speech&isSystemDlg=true") ? "XPPlugin_CarControlApp://CarControl.BluetoothDialog?from=speech&isSystemDlg=true" : str.equals("XPPlugin_SystemUIApp.BluetoothEarphoneParingListUI?from=speech&isSystemDlg=true") ? "XPPlugin_CarControlApp://XPPlugin_SystemUIApp.BluetoothEarphoneParingListUI?from=speech&isSystemDlg=true" : str;
    }

    public int closeUnityPage(String str) {
        if (CarFunction.isNonSelfPageUI()) {
            String page_id = ((UnityPageDirectParams) JsonUtils.toBean(str, UnityPageDirectParams.class)).getPage_id();
            Logs.d("closeUnityPage data: " + str + " pageUrl:" + page_id);
            String urlFillHeader = urlFillHeader(page_id);
            if (ElementDirectManager.get() != null && ElementDirectManager.get().checkUnitySupport(urlFillHeader)) {
                Logs.d("closeUnityPage 0 : " + urlFillHeader);
                ElementDirectManager.get().closeUnityPage(this.mContext, urlFillHeader);
                return 0;
            }
            Logs.d("closeUnityPage 1 : " + urlFillHeader);
        }
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getGuiPageOpenState(String str) {
        if (CarFunction.isNonSelfPageUI()) {
            Logs.d("getGuiPageOpenState supportMultiParams: " + str);
            UnityPageDirectParams unityPageDirectParams = (UnityPageDirectParams) JsonUtils.toBean(str, UnityPageDirectParams.class);
            int i = -1;
            i = -1;
            if (unityPageDirectParams.getDisplay_location() != null && !unityPageDirectParams.getDisplay_location().isEmpty()) {
                i = Integer.parseInt(unityPageDirectParams.getDisplay_location());
            } else if (unityPageDirectParams.getSound_location() != null && !unityPageDirectParams.getSound_location().isEmpty()) {
                i = "2".equals(unityPageDirectParams.getSound_location());
            }
            String urlFillHeader = urlFillHeader(unityPageDirectParams.getPage_id());
            if (!urlFillHeader.contains("?screenId=") && !urlFillHeader.contains("&screenId=")) {
                StringBuilder sb = new StringBuilder();
                sb.append(urlFillHeader);
                sb.append(urlFillHeader.contains("?") ? "&" : "?");
                sb.append(ISpeechContants.KEY_SCREEN_ID);
                sb.append("=");
                sb.append(i);
                urlFillHeader = sb.toString();
            }
            return unityPageDirect(urlFillHeader);
        }
        return pageDirect(str);
    }

    private int pageDirect(String str) {
        if (ElementDirectManager.get() != null && ElementDirectManager.get().checkSupport(str)) {
            Logs.d("getGuiPageOpenState 0 : " + str);
            ElementDirectManager.get().showPageDirect(this.mContext, str);
            return 0;
        }
        Logs.d("getGuiPageOpenState 1 : " + str);
        return 1;
    }

    private int unityPageDirect(String str) {
        if (ElementDirectManager.get() != null && ElementDirectManager.get().checkUnitySupport(str)) {
            Logs.d("unityPageDirect 0 : " + str);
            ElementDirectManager.get().showUnityPageDirect(this.mContext, str);
            return 0;
        }
        Logs.d("unityPageDirect 1 : " + str);
        return 1;
    }

    public int getCurrentMusicActive() {
        boolean isStreamActive = AudioSystem.isStreamActive(3, 0);
        Logs.d("xpspeech getCurrentMusicActive:" + isStreamActive);
        return isStreamActive ? 1 : 0;
    }

    public int getCurrentSoundEffectMode() {
        int soundEffectMode;
        int i = 1;
        if (!CarFunction.isDSeries() && (soundEffectMode = this.mSoundManager.getSoundEffectMode()) != 1 && soundEffectMode == 3) {
            i = 2;
        }
        Logs.d("xpspeech getCurrentSoundEffectMode speechMode:" + i);
        return i;
    }

    public int getCurrentNedcStatus() {
        int nedcSwitchStatus = CarSettingsManager.getInstance().getNedcSwitchStatus();
        Logs.d("xpspeech getCurrentNedcStatus status:" + nedcSwitchStatus);
        return nedcSwitchStatus;
    }

    public int getCurrentHeadsetMode() {
        int mainDriverExclusive = this.mSoundManager.getMainDriverExclusive();
        Logs.d("xpspeech getCurrentHeadsetMode type:" + mainDriverExclusive);
        return mainDriverExclusive;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003e, code lost:
        if (r0 != 3) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
        r1 = 11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x008f, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarConfigHelper.isLowSpeaker() == false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b1, code lost:
        if (r8 == com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_4_HIFI) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getCurrentSoundEffectStyle() {
        /*
            r11 = this;
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isDSeries()
            r1 = 10
            r2 = 2
            r3 = 1
            r4 = -1
            if (r0 == 0) goto L28
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r0 = r11.mSoundManager
            int r1 = r0.getSoundEffectType(r3)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "xpspeech getCurrentSoundEffectStyle style:"
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)
            goto Lb6
        L28:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRecentEffectMode()
            r5 = 11
            r6 = 0
            r7 = 3
            if (r0 == 0) goto L4c
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r0 = r11.mSoundManager
            int r0 = r0.getSoundEffectType(r3)
            if (r0 == 0) goto L49
            if (r0 == r3) goto Lb6
            if (r0 == r2) goto L45
            if (r0 == r7) goto L42
            goto Lb5
        L42:
            r1 = r5
            goto Lb6
        L45:
            r1 = 12
            goto Lb6
        L49:
            r1 = r6
            goto Lb6
        L4c:
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r0 = r11.mSoundManager
            int r0 = r0.getSoundEffectMode()
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r8 = r11.mSoundManager
            int r8 = r8.getSoundEffectType(r0)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "xpspeech getCurrentSoundEffectStyle: mode:"
            r9.append(r10)
            r9.append(r0)
            java.lang.String r10 = " style:"
            r9.append(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r9)
            if (r0 != r3) goto L9a
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 == 0) goto L87
            if (r8 == r3) goto L85
            if (r8 == r2) goto L82
            goto Lb5
        L82:
            r0 = 7
        L83:
            r1 = r0
            goto Lb6
        L85:
            r1 = r3
            goto Lb6
        L87:
            switch(r8) {
                case 0: goto L49;
                case 1: goto L85;
                case 2: goto Lb3;
                case 3: goto L98;
                case 4: goto L96;
                case 5: goto L94;
                case 6: goto L92;
                case 7: goto L8b;
                default: goto L8a;
            }
        L8a:
            goto Lb5
        L8b:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.isLowSpeaker()
            if (r0 != 0) goto Lb5
            goto L42
        L92:
            r0 = 6
            goto L83
        L94:
            r0 = 5
            goto L83
        L96:
            r0 = 4
            goto L83
        L98:
            r1 = r7
            goto Lb6
        L9a:
            if (r0 != r7) goto Lb5
            int r0 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_1_HIFI
            if (r8 != r0) goto La3
            r1 = 8
            goto Lb6
        La3:
            int r0 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_2_HIFI
            if (r8 != r0) goto Laa
            r1 = 9
            goto Lb6
        Laa:
            int r0 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_3_HIFI
            if (r8 != r0) goto Laf
            goto Lb6
        Laf:
            int r0 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_4_HIFI
            if (r8 != r0) goto Lb5
        Lb3:
            r1 = r2
            goto Lb6
        Lb5:
            r1 = r4
        Lb6:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "xpspeech getCurrentSoundEffectStyle: type:"
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller.getCurrentSoundEffectStyle():int");
    }

    public int isSupportHeadsetMode() {
        boolean hasMainDriverVIP = CarConfigHelper.hasMainDriverVIP();
        Logs.d("xpspeech isSupportHeadsetMode:" + hasMainDriverVIP + " support:" + (hasMainDriverVIP ? 1 : 0));
        return hasMainDriverVIP ? 1 : 0;
    }

    public int isSupportSoundEffectMode(int i) {
        if (CarFunction.isDSeries()) {
            int i2 = i == 1 ? 1 : 0;
            Logs.d("xpspeech d21 isSupportSoundEffectMode:" + i + " support:" + i2);
            return i2;
        }
        int i3 = i == 1 ? 1 : 0;
        if (i == 2) {
            i3 = CarConfigHelper.hasHifiSound() ? 1 : 0;
        }
        Logs.d("xpspeech isSupportSoundEffectMode:" + i + " support:" + i3);
        return i3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0044, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSoundEffectHighSpeaker() == false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0060, code lost:
        if (r5 != r4.mContext.getResources().getInteger(com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style6)) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound() == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0085, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound() != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008c, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound() != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int isSupportSoundEffectStyle(int r5) {
        /*
            r4 = this;
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isDSeries()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L64
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style1
            int r0 = r0.getInteger(r3)
            if (r5 == r0) goto L62
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style2
            int r0 = r0.getInteger(r3)
            if (r5 == r0) goto L62
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style3
            int r0 = r0.getInteger(r3)
            if (r5 == r0) goto L62
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style4
            int r0 = r0.getInteger(r3)
            if (r5 != r0) goto L46
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSoundEffectHighSpeaker()
            if (r0 != 0) goto L62
        L46:
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style5
            int r0 = r0.getInteger(r3)
            if (r5 == r0) goto L62
            android.content.Context r0 = r4.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r3 = com.xiaopeng.car.settingslibrary.R.integer.sound_effect_style6
            int r0 = r0.getInteger(r3)
            if (r5 != r0) goto L8e
        L62:
            r1 = r2
            goto L8e
        L64:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRecentEffectMode()
            if (r0 == 0) goto L70
            if (r5 == 0) goto L62
            switch(r5) {
                case 10: goto L62;
                case 11: goto L62;
                case 12: goto L62;
                default: goto L6f;
            }
        L6f:
            goto L8e
        L70:
            switch(r5) {
                case 0: goto L88;
                case 1: goto L62;
                case 2: goto L62;
                case 3: goto L88;
                case 4: goto L88;
                case 5: goto L88;
                case 6: goto L88;
                case 7: goto L81;
                case 8: goto L81;
                case 9: goto L81;
                case 10: goto L81;
                case 11: goto L74;
                default: goto L73;
            }
        L73:
            goto L8e
        L74:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.isLowSpeaker()
            if (r0 != 0) goto L8e
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 != 0) goto L8e
            goto L62
        L81:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 == 0) goto L8e
            goto L62
        L88:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 == 0) goto L62
        L8e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "xpspeech isSupportSoundEffectStyle: style:"
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = " support:"
            r0.append(r5)
            r0.append(r1)
            java.lang.String r5 = r0.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller.isSupportSoundEffectStyle(int):int");
    }

    public int isSupportSoundEffectScene(int i) {
        int i2 = 0;
        if (CarFunction.isDSeries() || CarFunction.isSupportRecentEffectMode()) {
            Logs.d("xpspeech isSupportSoundEffectScene return");
            return 0;
        }
        if (!CarConfigHelper.isLowSpeaker() && (i == 3 || i == 1 || i == 4 || i == 2)) {
            i2 = 1;
        }
        Logs.d("xpspeech isSupportSoundEffectScene:" + i + " support:" + i2);
        return i2;
    }

    public int getCurrentSoundEffectScene() {
        int soundEffectMode;
        int i = -1;
        if (CarFunction.isDSeries() || CarFunction.isSupportRecentEffectMode()) {
            Logs.d("xpspeech getCurrentSoundEffectScene return");
            return -1;
        }
        if (!CarConfigHelper.isLowSpeaker() && (soundEffectMode = this.mSoundManager.getSoundEffectMode()) == 1) {
            int soundEffectScene = this.mSoundManager.getSoundEffectScene(soundEffectMode);
            if (soundEffectScene == 1) {
                i = 1;
            } else if (soundEffectScene == 2) {
                i = 2;
            } else if (soundEffectScene == 3) {
                i = 3;
            } else if (soundEffectScene == 4) {
                i = 4;
            }
        }
        Logs.d("xpspeech getCurrentSoundEffectScene:" + i);
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0014, code lost:
        if (r6 != 6) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSoundEffectLowSpeaker() != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0035, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isMainPsnSharedSoundField() == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x004a, code lost:
        if (3 == com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.getInstance().getSoundEffectMode()) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0061, code lost:
        if (3 != com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.getInstance().getSoundEffectMode()) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int isSupportSoundEffectField(int r6) {
        /*
            r5 = this;
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isDSeries()
            r1 = 2
            r2 = 3
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L1e
            if (r6 == r4) goto L64
            if (r6 == r1) goto L64
            if (r6 == r2) goto L64
            r0 = 4
            if (r6 == r0) goto L17
            r0 = 6
            if (r6 == r0) goto L17
            goto L65
        L17:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSoundEffectLowSpeaker()
            if (r0 != 0) goto L65
            goto L64
        L1e:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRecentEffectMode()
            if (r0 == 0) goto L38
            if (r6 == r4) goto L64
            if (r6 == r1) goto L64
            if (r6 == r2) goto L64
            r0 = 5
            if (r6 == r0) goto L64
            r0 = 7
            if (r6 == r0) goto L31
            goto L65
        L31:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isMainPsnSharedSoundField()
            if (r0 == 0) goto L65
            goto L64
        L38:
            switch(r6) {
                case 1: goto L64;
                case 2: goto L64;
                case 3: goto L64;
                case 4: goto L4d;
                case 5: goto L3c;
                case 6: goto L4d;
                default: goto L3b;
            }
        L3b:
            goto L65
        L3c:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 == 0) goto L65
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r0 = com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.getInstance()
            int r0 = r0.getSoundEffectMode()
            if (r2 != r0) goto L65
            goto L64
        L4d:
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.isLowSpeaker()
            if (r0 != 0) goto L65
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            if (r0 == 0) goto L64
            com.xiaopeng.car.settingslibrary.manager.sound.SoundManager r0 = com.xiaopeng.car.settingslibrary.manager.sound.SoundManager.getInstance()
            int r0 = r0.getSoundEffectMode()
            if (r2 != r0) goto L64
            goto L65
        L64:
            r3 = r4
        L65:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "xpspeech isSupportSoundEffectField:"
            r0.append(r1)
            r0.append(r3)
            java.lang.String r1 = " position:"
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r6)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller.isSupportSoundEffectField(int):int");
    }

    public int getCurrentSoundEffectField() {
        int i = 0;
        int i2 = 2;
        int i3 = -1;
        if (CarFunction.isDSeries()) {
            SoundFieldValues soundField = this.mSoundManager.getSoundField(1);
            Logs.d("xpsound effect type:" + soundField.getType() + " x:" + soundField.getXSound() + " y:" + soundField.getYSound());
            while (i < Config.mD21RealRecommendationPoint.length) {
                Point point = Config.mD21RealRecommendationPoint[i];
                if (point.x == soundField.getXSound() && point.y == soundField.getYSound()) {
                    if (i == 0) {
                        i3 = 1;
                    } else if (i == 1) {
                        i3 = 2;
                    } else if (i == 2) {
                        i3 = 3;
                    } else if (i == 3) {
                        i3 = 4;
                    } else if (i == 4) {
                        i3 = 6;
                    }
                }
                i++;
            }
        } else if (CarFunction.isSupportRecentEffectMode()) {
            SoundFieldValues soundField2 = this.mSoundManager.getSoundField(1);
            Logs.d("xpspeech getCurrentSoundEffectField:" + soundField2);
            if (CarFunction.isMainPsnSharedSoundField()) {
                int i4 = 0;
                while (true) {
                    if (i4 >= Config.mThreeRecommendationPoint.length) {
                        i4 = 0;
                        break;
                    }
                    Point point2 = Config.mThreeRecommendationPoint[i4];
                    if (point2.x == soundField2.getXSound() && point2.y == soundField2.getYSound()) {
                        break;
                    }
                    i4++;
                }
                i2 = i4 != 0 ? i4 != 1 ? i4 != 2 ? -1 : 5 : 3 : 7;
            } else {
                int i5 = 0;
                while (true) {
                    if (i5 >= Config.mUnityRealRecommendationPoint.length) {
                        i5 = 0;
                        break;
                    }
                    Point point3 = Config.mUnityRealRecommendationPoint[i5];
                    if (point3.x == soundField2.getXSound() && point3.y == soundField2.getYSound()) {
                        break;
                    }
                    i5++;
                }
                if (i5 == 0) {
                    i3 = 1;
                } else if (i5 != 1) {
                    if (i5 == 2) {
                        i3 = 3;
                    } else if (i5 == 3) {
                        i3 = 5;
                    }
                }
            }
            i3 = i2;
        } else {
            int soundEffectMode = this.mSoundManager.getSoundEffectMode();
            SoundFieldValues soundField3 = this.mSoundManager.getSoundField(soundEffectMode);
            Logs.d("xpspeech getCurrentSoundEffectField:" + soundField3);
            if (soundEffectMode == 1) {
                while (i < Config.mRealRecommendationPoint.length) {
                    Point point4 = Config.mRealRecommendationPoint[i];
                    if (point4.x == soundField3.getXSound() && point4.y == soundField3.getYSound()) {
                        if (i == 0) {
                            i3 = 1;
                        } else if (i == 1) {
                            i3 = 2;
                        } else if (i == 2) {
                            i3 = 3;
                        } else if (i == 3) {
                            i3 = 4;
                        } else if (i == 4) {
                            i3 = 6;
                        }
                    }
                    i++;
                }
            } else if (soundEffectMode == 3) {
                while (i < Config.mRealAdsorptionPoint.length) {
                    Point point5 = Config.mRealAdsorptionPoint[i];
                    if (point5.x == soundField3.getXSound() && point5.y == soundField3.getYSound()) {
                        if (i == 0) {
                            i3 = 1;
                        } else if (i == 1) {
                            i3 = 2;
                        } else if (i == 2) {
                            i3 = 3;
                        } else if (i == 3) {
                            i3 = 5;
                        }
                    }
                    i++;
                }
            }
        }
        Logs.d("xpspeech getCurrentSoundEffectField field:" + i3);
        return i3;
    }

    public int getScreenSaverStatus() {
        boolean isPsnIdleOn = Utils.isPsnIdleOn(CarSettingsApp.getContext());
        if (AppServerManager.getInstance().isSubScreenOn()) {
            int i = isPsnIdleOn ? 2 : -1;
            if (isPsnIdleOn) {
                return i;
            }
            return 1;
        }
        return 0;
    }

    public int getAutoScreenStatus() {
        boolean isAdptiveBrightness = DataRepository.getInstance().isAdptiveBrightness(this.mContext);
        Logs.d("xpspeech getAutoScreenStatus:" + isAdptiveBrightness);
        return isAdptiveBrightness ? 1 : 0;
    }

    public int getIntelligentDarkLightAdaptStatus() {
        int i = !CarConfigHelper.hasAutoBrightness() ? 1 : 0;
        Logs.d("xpspeech getIntelligentDarkLightAdaptStatus " + i);
        return i;
    }

    public int getLowSpeedAnalogtoneCurrentValue() {
        int lowSpeedVolume = this.mLaboratoryManager.getLowSpeedVolume();
        Logs.d("xpspeech getLowSpeedAnalogtoneCurrentValue " + lowSpeedVolume);
        return lowSpeedVolume;
    }

    public int getLowSpeedAnalogtoneMaxValue() {
        Logs.d("xpspeech getLowSpeedAnalogtoneMaxValue 100");
        return 100;
    }

    public int getLowSpeedAnalogtoneMinValue() {
        Logs.d("xpspeech getLowSpeedAnalogtoneMinValue 0");
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRemoteParkAdvanced() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportAutoDriveTts() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRemotePark() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0050, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportKeyPark() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0057, code lost:
        if (r0.isAutoPowerOff() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0064, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportInductionLock() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0071, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLeaveLock() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x007e, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportNearUnlock() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x008b, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportSoldierModeLevel() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0098, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLowSpeedVolumeSwitch() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00a5, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLowSpeedVolumeSlider() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ac, code lost:
        if (r0.isAppUsedLimitNetEnable() != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
        if (com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportSoldierModeCamera() != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getXpLabSupport(int r4) {
        /*
            r3 = this;
            com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager r0 = com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager.getInstance()
            com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues r0 = r0.getNetConfigValues()
            r1 = 1
            switch(r4) {
                case 0: goto La8;
                case 1: goto L9b;
                case 2: goto L8e;
                case 3: goto L81;
                case 4: goto L74;
                case 5: goto L67;
                case 6: goto L5a;
                case 7: goto L53;
                case 8: goto L46;
                case 9: goto L38;
                case 10: goto L2a;
                case 11: goto L1c;
                case 12: goto Lc;
                case 13: goto Le;
                default: goto Lc;
            }
        Lc:
            goto Laf
        Le:
            boolean r0 = r0.isSoldierCameraEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportSoldierModeCamera()
            if (r0 == 0) goto Laf
            goto Lb0
        L1c:
            boolean r0 = r0.isRemoteControlAdvancedEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRemoteParkAdvanced()
            if (r0 == 0) goto Laf
            goto Lb0
        L2a:
            boolean r0 = r0.isTtsBroadcastEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportAutoDriveTts()
            if (r0 == 0) goto Laf
            goto Lb0
        L38:
            boolean r0 = r0.isRemoteControlEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportRemotePark()
            if (r0 == 0) goto Laf
            goto Lb0
        L46:
            boolean r0 = r0.isRemoteControlKeyEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportKeyPark()
            if (r0 == 0) goto Laf
            goto Lb0
        L53:
            boolean r0 = r0.isAutoPowerOff()
            if (r0 == 0) goto Laf
            goto Lb0
        L5a:
            boolean r0 = r0.isInductionLockEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportInductionLock()
            if (r0 == 0) goto Laf
            goto Lb0
        L67:
            boolean r0 = r0.isLeaveLockEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLeaveLock()
            if (r0 == 0) goto Laf
            goto Lb0
        L74:
            boolean r0 = r0.isNearUnlockEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportNearUnlock()
            if (r0 == 0) goto Laf
            goto Lb0
        L81:
            boolean r0 = r0.isSoldierEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportSoldierModeLevel()
            if (r0 == 0) goto Laf
            goto Lb0
        L8e:
            boolean r0 = r0.isLowSpeedSoundNetEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLowSpeedVolumeSwitch()
            if (r0 == 0) goto Laf
            goto Lb0
        L9b:
            boolean r0 = r0.isLowSpeedSoundNetEnable()
            if (r0 == 0) goto Laf
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarFunction.isSupportLowSpeedVolumeSlider()
            if (r0 == 0) goto Laf
            goto Lb0
        La8:
            boolean r0 = r0.isAppUsedLimitNetEnable()
            if (r0 == 0) goto Laf
            goto Lb0
        Laf:
            r1 = 0
        Lb0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "xpspeech getXpLabSupport mode:"
            r0.append(r2)
            r0.append(r4)
            java.lang.String r4 = " support:"
            r0.append(r4)
            r0.append(r1)
            java.lang.String r4 = r0.toString()
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller.getXpLabSupport(int):int");
    }

    public int getSignalLossFrameState(int i) {
        Logs.d("xpspeech getSignalLossFrameState ");
        if (i != 0) {
            if (i != 1) {
                return 0;
            }
            return CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_PHONE_PK_BTN) ? 1 : 0;
        }
        return CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_REMOTE_PK_BTN) ? 1 : 0;
    }

    public int getSentryModeValue() {
        Logs.d("xpspeech getSentryModeValue ");
        return CarSettingsManager.getInstance().getSoldierSwState();
    }

    public int getCurPsnScreenBrightness() {
        int uIProgressByReal = this.mXpDisplayManager.getUIProgressByReal(this.mDataRepository.getPsnScreenBrightness(this.mContext));
        Logs.d("xpspeech getCurPsnScreenBrightness " + uIProgressByReal);
        return uIProgressByReal;
    }

    public int getMaxPsnScreenBrightnessValue() {
        Logs.d("xpspeech getMaxPsnScreenBrightnessValue ");
        return 100;
    }

    public int getMinPsnScreenBrightnessValue() {
        Logs.d("speech getMinPsnScreenBrightnessValue ");
        return 1;
    }

    public int getPositionReminderSupport() {
        Logs.d("speech getPositionReminderSupport ");
        return CarConfigHelper.hasAMP() ? 1 : 0;
    }

    public int getImmerseSoundSupport() {
        Logs.d("speech getImmerseSoundSupport ");
        return CarConfigHelper.hasAMP() ? 1 : 0;
    }

    public int getImmerseSoundCurrentMode() {
        Logs.d("speech getImmerseSoundCurrentMode ");
        return 0;
    }

    public int getSupportSystemSoundExternal() {
        Logs.d("speech getSupportSystemSoundExternal ");
        return CarFunction.isSupportAvasSpeaker() ? 1 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getBluetoothStatus() {
        /*
            r3 = this;
            java.lang.String r0 = "get bonded device Bluetooth State "
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)
            com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger r0 = r3.mBluetoothManager
            java.util.ArrayList r0 = r0.getBondedDevicesSorted()
            java.util.Iterator r0 = r0.iterator()
        Lf:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L2f
            java.lang.Object r1 = r0.next()
            com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo r1 = (com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo) r1
            boolean r2 = r1.isA2dpConnected()
            if (r2 != 0) goto L2d
            boolean r2 = r1.isHfpConnected()
            if (r2 != 0) goto L2d
            boolean r1 = r1.isHidConnected()
            if (r1 == 0) goto Lf
        L2d:
            r0 = 1
            goto L30
        L2f:
            r0 = 0
        L30:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller.getBluetoothStatus():int");
    }

    public boolean getBluetoothSwitchStatus() {
        Logs.d("getBluetoothSwitchStatus");
        return XpBluetoothManger.getInstance().isEnable();
    }

    public int getBluetoothConnectStatus(String str) {
        Logs.d("check assigned device Bluetooth State " + str);
        if (!TextUtils.isEmpty(str)) {
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo = XpBluetoothManger.getInstance().getXpBluetoothDeviceInfo(str);
            if (xpBluetoothDeviceInfo == null) {
                Logs.d("Device is NULL");
            } else if (XpBluetoothManger.getInstance().isConnected(xpBluetoothDeviceInfo)) {
                return 1;
            }
        }
        return 0;
    }

    public String getBondedDevice() {
        return JsonUtils.toJSONString(convertToBeans(this.mBluetoothManager.getBondedDevicesSorted()));
    }

    public static BluetoothDevicesBean convertToBeans(ArrayList<XpBluetoothDeviceInfo> arrayList) {
        BluetoothDevicesBean bluetoothDevicesBean = new BluetoothDevicesBean();
        ArrayList arrayList2 = new ArrayList();
        Iterator<XpBluetoothDeviceInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            BluetoothDevicesBean.DeviceBean deviceBean = new BluetoothDevicesBean.DeviceBean();
            deviceBean.setName(next.getDeviceName());
            deviceBean.setMac(next.getDeviceAddr());
            deviceBean.setType(typeToCategory(next.getCategory()));
            arrayList2.add(deviceBean);
        }
        bluetoothDevicesBean.setDevice(arrayList2);
        return bluetoothDevicesBean;
    }

    public int getSoundEffectConfig() {
        int i;
        if (CarConfigHelper.hasHifiSound()) {
            i = 3;
        } else {
            i = CarConfigHelper.isLowSpeaker() ? 1 : 2;
        }
        Logs.d("xpspeech getSoundEffectConfig " + i);
        return i;
    }

    public int getCurrentSoundEffectEqualizerMode() {
        SoundManager soundManager = this.mSoundManager;
        int i = soundManager.getEqualizerCustomValue(soundManager.getSoundEffectMode()) != 2 ? 1 : 2;
        Logs.d("xpspeech getCurrentSoundEffectEqualizerMode " + i);
        return i;
    }

    public int getRearScreenSupport() {
        return CarConfigHelper.hasRearScreen() ? 1 : 0;
    }

    public int getRearScreenStatus() {
        return this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.REAR_SCREEN_CONTROL, false) ? 1 : 0;
    }

    public int getRearScreenLockStatus() {
        return this.mDataRepository.getSettingProvider(this.mContext, XPSettingsConfig.REAR_SCREEN_LOCK, false) ? 1 : 0;
    }
}
