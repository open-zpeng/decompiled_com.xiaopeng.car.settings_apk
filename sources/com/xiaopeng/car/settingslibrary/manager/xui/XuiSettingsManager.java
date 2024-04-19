package com.xiaopeng.car.settingslibrary.manager.xui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.speech.ISpeechContants;
import com.xiaopeng.car.settingslibrary.vm.laboratory.ISayHiListener;
import com.xiaopeng.car.settingslibrary.vm.laboratory.LluSayHiEffect;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.karaoke.KaraokeManager;
import com.xiaopeng.xuimanager.lightlanuage.LightLanuageManager;
import com.xiaopeng.xuimanager.mediacenter.MediaCenterManager;
import com.xiaopeng.xuimanager.smart.SmartManager;
import com.xiaopeng.xuimanager.soundresource.SoundResourceManager;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectResource;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectTheme;
import com.xiaopeng.xuimanager.userscenario.UserScenarioManager;
import com.xiaopeng.xuimanager.xapp.XAppManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class XuiSettingsManager {
    private static final int CONNECT_RETRY_COUNT = 5;
    public static final int MIC_POWER_OFF = 6;
    public static final int MIC_POWER_ON = 5;
    public static final String RET_FAIL_DOOR_OPEN = "doorOpen";
    public static final String RET_FAIL_GEAR_NOT_P = "gearNotP";
    public static final String RET_REMOTE_EXCEPTION = "remoteException";
    public static final String RET_SCENARIO_CONFLICT = "conflict#";
    public static final String RET_SCENARIO_INVALID = "scenarioInvalid";
    public static final String RET_SCENARIO_UNAVAILABLE = "scenarioUnavailable";
    public static final String RET_SUCCESS = "success";
    private static final String TAG = "XuiSettingsManager";
    public static final int UDB_DONGLE_OFF = 4;
    public static final int UDB_DONGLE_ON = 3;
    public static final String USER_SCENARIO_CINEMA_MODE = "spacecapsule_mode_movie";
    public static final String USER_SCENARIO_CLEAN_MODE = "cleaning_mode";
    public static final String USER_SCENARIO_SOURCE_ACTIVITY = "activity";
    public static final int USER_SCENARIO_STATUS_IDLE = 0;
    public static final int USER_SCENARIO_STATUS_RUNNING = 2;
    public static final String USER_SCENARIO_WAITING_MODE = "waiting_mode";
    private static volatile XuiSettingsManager sInstance;
    private XAppManager mAppManager;
    private LightLanuageManager.EventListener mLLuEventCallback;
    private LightLanuageManager mLluManager;
    private MediaCenterManager mMediaCenterManager;
    private KaraokeManager mMicSdk;
    private SmartManager mSmartManager;
    private SoundResourceManager mSoundResourceManager;
    private UserScenarioManager mUserScenarioManager;
    private ArrayList<ISayHiListener> mSayHiListeners = new ArrayList<>();
    private int mRetry = 0;
    private final ServiceConnection mXuiConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            XuiSettingsManager.this.initSmartManager();
            XuiSettingsManager.this.initAppManager();
            XuiSettingsManager.this.initMediaManager();
            XuiSettingsManager.this.initKaraokeManager();
            XuiSettingsManager.this.initUserScenarioManager();
            XuiSettingsManager.this.initSoundResourceManager();
            XuiSettingsManager.this.initLightLanguageManager();
            Logs.d("xpxui onServiceConnected init completed!");
            XuiSettingsManager.this.notifyServiceConnectComplete();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Logs.d("xpxui onServiceDisconnected! " + XuiSettingsManager.this.mRetry);
            if (XuiSettingsManager.this.mRetry >= 5) {
                return;
            }
            XuiSettingsManager.this.connectXui();
            XuiSettingsManager.access$308(XuiSettingsManager.this);
        }
    };
    private List<OnServiceConnectCompleteListener> mOnServiceConnectCompleteListenerList = new ArrayList();
    protected XUIManager mXUIManager = XUIManager.createXUIManager(CarSettingsApp.getContext(), this.mXuiConnectionCb);

    /* loaded from: classes.dex */
    public interface OnServiceConnectCompleteListener {
        void onServiceConnectComplete();
    }

    static /* synthetic */ int access$308(XuiSettingsManager xuiSettingsManager) {
        int i = xuiSettingsManager.mRetry;
        xuiSettingsManager.mRetry = i + 1;
        return i;
    }

    public static XuiSettingsManager getInstance() {
        if (sInstance == null) {
            synchronized (XuiSettingsManager.class) {
                if (sInstance == null) {
                    sInstance = new XuiSettingsManager();
                }
            }
        }
        return sInstance;
    }

    private XuiSettingsManager() {
        connectXui();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectXui() {
        try {
            if (this.mXUIManager != null) {
                Logs.d("xpxui connect");
                this.mXUIManager.connect();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void releaseAll() {
        releaseXui();
    }

    public void releaseXui() {
        if (this.mXUIManager != null) {
            LogUtils.d("xpxui disconnect");
            this.mXUIManager.disconnect();
        }
    }

    void handleException(Exception exc) {
        if (exc instanceof IllegalArgumentException) {
            LogUtils.d("IllegalArgumentException:" + exc);
            return;
        }
        LogUtils.d(exc.toString());
    }

    public void initSmartManager() {
        try {
            this.mSmartManager = (SmartManager) this.mXUIManager.getXUIServiceManager("smart");
            Logs.d("xpxui init complete mSmartManager:" + this.mSmartManager);
        } catch (XUIServiceNotConnectedException unused) {
            Logs.d("XUI Service not connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initKaraokeManager() {
        try {
            this.mMicSdk = (KaraokeManager) this.mXUIManager.getXUIServiceManager("karaoke");
            Logs.d("xpxui init complete KaraokManager:" + this.mMicSdk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initUserScenarioManager() {
        try {
            this.mUserScenarioManager = (UserScenarioManager) this.mXUIManager.getXUIServiceManager("userscenario");
            Logs.d("xpxui init complete UserScenarioManager:" + this.mUserScenarioManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            userScenarioManager.registerListener(new UserScenarioManager.UserScenarioListener() { // from class: com.xiaopeng.car.settingslibrary.manager.xui.-$$Lambda$XuiSettingsManager$3SMSqgQgDlXM2SrLUw_fipwtZGY
                public final void onUserScenarioStateChanged(String str, int i) {
                    Logs.d("XuiSettingsManagerlistener scenario=" + str + ",status=" + i);
                }
            });
        }
    }

    public void initSoundResourceManager() {
        try {
            this.mSoundResourceManager = (SoundResourceManager) this.mXUIManager.getXUIServiceManager("sndresource");
            Logs.d("xpxui init complete SoundResourceManager:" + this.mSoundResourceManager);
            if (this.mSoundResourceManager != null) {
                this.mSoundResourceManager.registerListener(new SoundResourceManager.SoundResourceListener() { // from class: com.xiaopeng.car.settingslibrary.manager.xui.-$$Lambda$XuiSettingsManager$dvOJ2AUeXpKyTMOLsr8vW5svK-E
                    public final void onResourceEvent(int i, int i2) {
                        XuiSettingsManager.lambda$initSoundResourceManager$1(i, i2);
                    }
                });
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initSoundResourceManager$1(int i, int i2) {
        if (i2 != 1) {
            return;
        }
        Logs.d("XuiSettingsManager SoundResourceManager onEvent,id=" + i + ",event=" + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAppManager() {
        try {
            this.mAppManager = (XAppManager) this.mXUIManager.getXUIServiceManager("xapp");
            Logs.d("xpxui init commplete appmanager " + this.mAppManager);
        } catch (XUIServiceNotConnectedException e) {
            Logs.d("XUI Service not connected");
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMediaManager() {
        try {
            this.mMediaCenterManager = (MediaCenterManager) this.mXUIManager.getXUIServiceManager("mediacenter");
        } catch (XUIServiceNotConnectedException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLightLanguageManager() {
        try {
            this.mLluManager = (LightLanuageManager) this.mXUIManager.getXUIServiceManager("lightlanuage");
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e, false);
        }
    }

    public void registerSayHiListener(ISayHiListener iSayHiListener) {
        if (this.mSayHiListeners.contains(iSayHiListener)) {
            return;
        }
        this.mSayHiListeners.add(iSayHiListener);
    }

    public void unregisterSayHiListener(ISayHiListener iSayHiListener) {
        this.mSayHiListeners.remove(iSayHiListener);
    }

    public void addServiceConnectCompleteListener(OnServiceConnectCompleteListener onServiceConnectCompleteListener) {
        if (this.mOnServiceConnectCompleteListenerList.contains(onServiceConnectCompleteListener)) {
            return;
        }
        this.mOnServiceConnectCompleteListenerList.add(onServiceConnectCompleteListener);
    }

    public void removeServiceConnectCompleteListener(OnServiceConnectCompleteListener onServiceConnectCompleteListener) {
        this.mOnServiceConnectCompleteListenerList.remove(onServiceConnectCompleteListener);
    }

    public void notifyServiceConnectComplete() {
        for (OnServiceConnectCompleteListener onServiceConnectCompleteListener : this.mOnServiceConnectCompleteListenerList) {
            onServiceConnectCompleteListener.onServiceConnectComplete();
        }
    }

    public MediaCenterManager getMediaCenterManager() {
        return this.mMediaCenterManager;
    }

    public LightLanuageManager getLluManager() {
        return this.mLluManager;
    }

    public int isSourceInBluetooth() {
        if (this.mMediaCenterManager == null) {
            Logs.d("xpxui isSourceInBluetooth null!");
            initMediaManager();
            if (this.mMediaCenterManager == null) {
                return -1;
            }
        }
        try {
            if (this.mMediaCenterManager != null) {
                int btStatus = this.mMediaCenterManager.getBtStatus();
                Logs.d("xpbluetooth source in bluetooth:" + btStatus);
                return btStatus;
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setVolumeDownWithDoorOpen(boolean z) {
        if (this.mSmartManager == null) {
            initSmartManager();
            if (this.mSmartManager == null) {
                return;
            }
        }
        try {
            this.mSmartManager.setVolumeDownWithDoorOpen(z);
            Logs.d("xpxui setVolumeDownWithDoorOpen " + z);
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean registerOfficialKaraokePowerListener(KaraokeManager.MicCallBack micCallBack) {
        if (this.mMicSdk == null) {
            initKaraokeManager();
            if (this.mMicSdk == null) {
                return false;
            }
        }
        try {
            this.mMicSdk.XMA_registerCallback(micCallBack);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KaraokeManager getMicSdk() {
        return this.mMicSdk;
    }

    public void unRegisterOfficialKaraokePowerListener() {
        if (this.mMicSdk == null) {
            initKaraokeManager();
            if (this.mMicSdk == null) {
                return;
            }
        }
        try {
            this.mMicSdk.XMA_unRegisterCallback();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getMicPowerStatus() {
        if (this.mMicSdk == null) {
            Logs.d("xuiservice getMicPowerStatus -1");
            initKaraokeManager();
            if (this.mMicSdk == null) {
                return -1;
            }
        }
        return this.mMicSdk.XMA_getMicPowerStatus();
    }

    public int getMicStatus() {
        if (this.mMicSdk == null) {
            Logs.d("xuiservice getMicStatus -1");
            initKaraokeManager();
            if (this.mMicSdk == null) {
                return -1;
            }
        }
        return this.mMicSdk.XMA_getMicStatus();
    }

    public String getBuyMicUrl() {
        if (this.mMicSdk == null) {
            Logs.d("xuiservice getBuyMicUrl -1");
            initKaraokeManager();
            if (this.mMicSdk == null) {
                return "";
            }
        }
        return this.mMicSdk.XMA_getBuyMicUrl();
    }

    public void setVolumeDownInReverseMode(boolean z) {
        if (this.mSmartManager == null) {
            initSmartManager();
            if (this.mSmartManager == null) {
                return;
            }
        }
        try {
            this.mSmartManager.setVolumeDownInReverseMode(z);
            Logs.d("xpxui setVolumeDownInReverseMode " + z);
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean getVolumeDownWithDoorOpen() {
        if (this.mSmartManager == null) {
            initSmartManager();
            if (this.mSmartManager == null) {
                return true;
            }
        }
        try {
            boolean volumeDownWithDoorOpen = this.mSmartManager.getVolumeDownWithDoorOpen();
            Logs.d("xpxui getVolumeDownWithDoorOpen " + volumeDownWithDoorOpen);
            return volumeDownWithDoorOpen;
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public boolean getVolumeDownInReverseMode() {
        if (this.mSmartManager == null) {
            initSmartManager();
            if (this.mSmartManager == null) {
                return true;
            }
        }
        try {
            boolean volumeDownInReverseMode = this.mSmartManager.getVolumeDownInReverseMode();
            Logs.d("xpxui getVolumeDownInReverseMode " + volumeDownInReverseMode);
            return volumeDownInReverseMode;
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public void setAppUsedLimitEnable(boolean z) {
        if (this.mAppManager == null) {
            initAppManager();
            if (this.mAppManager == null) {
                return;
            }
        }
        try {
            this.mAppManager.setAppUsedLimitEnable(z);
            Logs.d("xuiservice setAppUsedLimitEnable " + z);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean getAppUsedLimitEnable() {
        if (this.mAppManager == null) {
            initAppManager();
            if (this.mAppManager == null) {
                return true;
            }
        }
        try {
            boolean appUsedLimitEnable = this.mAppManager.getAppUsedLimitEnable();
            Logs.d("xuiservice getAppUsedLimitEnable " + appUsedLimitEnable);
            return appUsedLimitEnable;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public void uploadAlipayLog() {
        try {
            if (this.mAppManager == null) {
                Logs.d("uploadAlipayLog mAppManager is null");
                initAppManager();
                if (this.mAppManager == null) {
                    return;
                }
            }
            if (this.mAppManager != null) {
                Logs.d("xuiservice uploadAlipayLog");
                this.mAppManager.uploadAlipayLog();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String requestEnterUserScenario(String str, int i) {
        if (this.mUserScenarioManager == null) {
            initUserScenarioManager();
            if (this.mUserScenarioManager == null) {
                return "";
            }
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ISpeechContants.KEY_SCREEN_ID, i);
        } catch (Exception e) {
            Log.w(TAG, "enter e=" + e);
        }
        String enterUserScenario = this.mUserScenarioManager.enterUserScenario(str, USER_SCENARIO_SOURCE_ACTIVITY, jSONObject.toString());
        Logs.d("XuiSettingsManager UserScenario enter ret =" + enterUserScenario + " mode:" + str);
        return enterUserScenario;
    }

    public String requestExitUserScenario(String str) {
        if (this.mUserScenarioManager == null) {
            initUserScenarioManager();
            if (this.mUserScenarioManager == null) {
                return "";
            }
        }
        String exitUserScenario = this.mUserScenarioManager.exitUserScenario(str);
        Logs.d("XuiSettingsManager UserScenario exit ret =" + exitUserScenario + " mode:" + str);
        return exitUserScenario;
    }

    public boolean isInMode(String str) {
        if (this.mUserScenarioManager == null) {
            initUserScenarioManager();
            if (this.mUserScenarioManager == null) {
                return false;
            }
        }
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            int userScenarioStatus = userScenarioManager.getUserScenarioStatus(str);
            Logs.d("XuiSettingsManager UserScenario isInMode:" + userScenarioStatus + " mode:" + str);
            return userScenarioStatus != 0;
        }
        return false;
    }

    public void registerBinder() {
        if (this.mUserScenarioManager == null) {
            initUserScenarioManager();
            if (this.mUserScenarioManager == null) {
                return;
            }
        }
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            userScenarioManager.registerBinderObserver(new Binder());
        }
    }

    public void reportModeStatus(String str, int i) {
        if (this.mUserScenarioManager == null) {
            initUserScenarioManager();
            if (this.mUserScenarioManager == null) {
                return;
            }
        }
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            userScenarioManager.reportStatus(str, i);
            Logs.d("XuiSettingsManager UserScenario reportStatus:" + i + " mode:" + str);
        }
    }

    public void playBtMedia() {
        try {
            if (this.mMediaCenterManager == null) {
                initMediaManager();
                if (this.mMediaCenterManager == null) {
                    return;
                }
            }
            if (this.mMediaCenterManager != null && this.mMediaCenterManager.isBtDeviceAvailable()) {
                this.mMediaCenterManager.playBtMedia();
                LogUtils.i(TAG, "playBtMedia");
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
        ((AudioManager) CarSettingsApp.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO)).requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.2
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public void onAudioFocusChange(int i) {
                Logs.d("xpsettings onAudioFocusChange " + i);
            }
        }, 3, 1);
    }

    public SoundEffectTheme[] getAvailableThemes() {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return null;
            }
        }
        if (this.mSoundResourceManager != null) {
            Logs.d("XuiSettingsManager SoundResource getAvailableThemes");
            try {
                return this.mSoundResourceManager.getAvailableThemes();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public int getActiveSoundEffectTheme() {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return -1;
            }
        }
        SoundResourceManager soundResourceManager = this.mSoundResourceManager;
        if (soundResourceManager != null) {
            try {
                int activeSoundEffectTheme = soundResourceManager.getActiveSoundEffectTheme();
                Logs.d("XuiSettingsManager SoundResource getActiveSoundEffectTheme " + activeSoundEffectTheme);
                return activeSoundEffectTheme;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return -1;
    }

    public SoundEffectResource[] getSoundEffectPreviewResource(int i) {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return null;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource getSoundEffectPreviewResource");
                return this.mSoundResourceManager.getSoundEffectPreviewResource(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public int selectSoundEffectTheme(int i) {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return -1;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource selectSoundEffectTheme " + i);
                return this.mSoundResourceManager.selectSoundEffectTheme(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return -1;
    }

    public BootSoundResource getActiveBootSoundResource() {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return null;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource getActiveBootSoundResource ");
                return this.mSoundResourceManager.getActiveBootSoundResource();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public int getBootSoundOnOff() {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return -1;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource getBootSoundOnOff ");
                return this.mSoundResourceManager.getBootSoundOnOff();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return -1;
    }

    public BootSoundResource[] getBootSoundResource() {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return null;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource getBootSoundResource");
                return this.mSoundResourceManager.getBootSoundResource();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public int setBootSoundOnOff(boolean z) {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return -1;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource setBootSoundOnOff");
                return this.mSoundResourceManager.setBootSoundOnOff(z);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return -1;
    }

    public int setBootSoundResource(int i) {
        if (this.mSoundResourceManager == null) {
            initSoundResourceManager();
            if (this.mSoundResourceManager == null) {
                return -1;
            }
        }
        if (this.mSoundResourceManager != null) {
            try {
                Logs.d("XuiSettingsManager SoundResource setBootSoundResource");
                return this.mSoundResourceManager.setBootSoundResource(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return -1;
    }

    public void setSayHiEffect(LluSayHiEffect lluSayHiEffect) {
        if (lluSayHiEffect != null) {
            try {
                if (this.mLluManager == null) {
                    initLightLanguageManager();
                    if (this.mLluManager == null) {
                        LogUtils.d(TAG, "mLluManager is null!!!");
                        return;
                    }
                }
                if (this.mSmartManager == null) {
                    initSmartManager();
                    if (this.mSmartManager == null) {
                        LogUtils.d(TAG, "mSmartManager is null!!!");
                        return;
                    }
                }
                this.mLluManager.setMcuEffect(10, lluSayHiEffect.toLluCmd());
                this.mSmartManager.setSayHiEffect(lluSayHiEffect.toAvasType());
                Iterator<ISayHiListener> it = this.mSayHiListeners.iterator();
                while (it.hasNext()) {
                    it.next().onLluSayHiTypeChanged(getLluSayHiEffect());
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setLluEffect: " + e.getMessage(), false);
            }
        }
    }

    public int getLluSayHiEffect() {
        if (this.mSmartManager == null) {
            initSmartManager();
            if (this.mSmartManager == null) {
                return 1;
            }
        }
        try {
            return this.mSmartManager.getSayHiEffect();
        } catch (Exception e) {
            LogUtils.e(TAG, "getFriendSoundType: " + e.getMessage(), false);
            return 1;
        }
    }

    public void setSayHiEnable(boolean z) {
        try {
            if (this.mLluManager == null) {
                initLightLanguageManager();
                if (this.mLluManager == null) {
                    LogUtils.d(TAG, "mLluManager is null!!!");
                    return;
                }
            }
            this.mLluManager.setSayhiEffectEnable(z);
            setSayHiAvasEnable(z);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluEffect: " + e.getMessage(), false);
        }
    }

    public boolean getSayHiEnable() {
        try {
            if (this.mLluManager == null) {
                initLightLanguageManager();
                if (this.mLluManager == null) {
                    LogUtils.d(TAG, "mLluManager is null!!!");
                    return false;
                }
            }
            return this.mLluManager.getSayhiEffectEnable();
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluEffect: " + e.getMessage(), false);
            return false;
        }
    }

    public void setSayHiAvasEnable(boolean z) {
        Settings.System.putInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.SAY_HI_AVAS_SW, z ? 1 : 0);
    }

    public boolean getSayHiAvasEnable() {
        return Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.SAY_HI_AVAS_SW, 1) == 1;
    }
}
