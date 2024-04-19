package com.xiaopeng.car.settingslibrary.vm.display;

import android.app.Application;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.MeterCallback;
import com.xiaopeng.car.settingslibrary.manager.display.XpDisplayManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper;
import com.xiaopeng.libtheme.ThemeController;
/* loaded from: classes.dex */
public class DisplaySettingsViewModel extends AndroidViewModel implements ThemeController.OnThemeListener, DisplayEventMonitorHelper.DisplayEventMonitorListener, MeterCallback {
    private static final String TAG = "DisplaySettingsViewModel";
    private Application mApplication;
    private MutableLiveData<Boolean> mAutoBrightnessLiveData;
    private MutableLiveData<Boolean> mCmsAutoBrightnessLiveData;
    private DataRepository mDataRepository;
    private MutableLiveData<Boolean> mDayNightChangeEnable;
    private DisplayEventMonitorHelper mDisplayEventMonitorHelper;
    private MutableLiveData<Boolean> mFontScaleLiveData;
    private Handler mHandler;
    private boolean mIsAlreadyRegisterMeterCallback;
    private MutableLiveData<Boolean> mIsDarkStateLiveData;
    private boolean mLastMeterProcessDoing;
    private int mLastMeterSetValue;
    private MutableLiveData<Boolean> mMainAutoBrightnessLiveData;
    private MutableLiveData<Boolean> mMeterAutoBrightnessLiveData;
    private int mMeterBrightnessDragging;
    private MutableLiveData<Integer> mMeterBrightnessLiveData;
    private MutableLiveData<Boolean> mPsnAutoBrightnessLiveData;
    private MutableLiveData<Integer> mPsnScreenBrightnessLiveData;
    private MutableLiveData<Integer> mRearScreenAngleCallbackLiveData;
    private MutableLiveData<Integer> mRearScreenAngleLiveData;
    private MutableLiveData<Integer> mRearScreenBrightnessLiveData;
    private MutableLiveData<Integer> mRearScreenStateDetailLiveData;
    private MutableLiveData<Boolean> mRearScreenStateLiveData;
    private Runnable mRegisterRunnable;
    private MutableLiveData<Integer> mScreenBrightnessLiveData;
    private ThemeController mThemeController;
    private MutableLiveData<Integer> mThemeSwitchInfoLiveData;
    private MutableLiveData<Boolean> mTimeFormatLiveData;
    XpDisplayManager mXpDisplayManager;

    public DisplaySettingsViewModel(Application application) {
        super(application);
        this.mDataRepository = DataRepository.getInstance();
        this.mAutoBrightnessLiveData = new MutableLiveData<>();
        this.mMainAutoBrightnessLiveData = new MutableLiveData<>();
        this.mMeterAutoBrightnessLiveData = new MutableLiveData<>();
        this.mPsnAutoBrightnessLiveData = new MutableLiveData<>();
        this.mCmsAutoBrightnessLiveData = new MutableLiveData<>();
        this.mRearScreenBrightnessLiveData = new MutableLiveData<>();
        this.mScreenBrightnessLiveData = new MutableLiveData<>();
        this.mPsnScreenBrightnessLiveData = new MutableLiveData<>();
        this.mMeterBrightnessLiveData = new MutableLiveData<>();
        this.mThemeSwitchInfoLiveData = new MutableLiveData<>();
        this.mDayNightChangeEnable = new MutableLiveData<>();
        this.mIsDarkStateLiveData = new MutableLiveData<>();
        this.mFontScaleLiveData = new MutableLiveData<>();
        this.mTimeFormatLiveData = new MutableLiveData<>();
        this.mRearScreenStateLiveData = new MutableLiveData<>();
        this.mRearScreenAngleLiveData = new MutableLiveData<>();
        this.mRearScreenStateDetailLiveData = new MutableLiveData<>();
        this.mRearScreenAngleCallbackLiveData = new MutableLiveData<>();
        this.mMeterBrightnessDragging = -1;
        this.mLastMeterProcessDoing = false;
        this.mLastMeterSetValue = -1;
        this.mIsAlreadyRegisterMeterCallback = false;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mRegisterRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.display.-$$Lambda$DisplaySettingsViewModel$Ypt1QlVNRXQcKJrkU2wpSZPve6Q
            @Override // java.lang.Runnable
            public final void run() {
                DisplaySettingsViewModel.this.registerMeterCallback();
            }
        };
        this.mApplication = application;
        this.mXpDisplayManager = XpDisplayManager.getInstance();
        this.mDisplayEventMonitorHelper = new DisplayEventMonitorHelper(this.mApplication, this);
        this.mThemeController = ThemeController.getInstance(this.mApplication);
    }

    public void onStartVM() {
        if (CarConfigHelper.hasAutoBrightness()) {
            this.mAutoBrightnessLiveData.setValue(Boolean.valueOf(XpAccountManager.getInstance().getAutoBrightness()));
        } else {
            this.mAutoBrightnessLiveData.setValue(Boolean.valueOf(XpAccountManager.getInstance().getDarkLightAdaptation()));
        }
        this.mScreenBrightnessLiveData.setValue(Integer.valueOf(getScreenBrightness()));
        if (CarFunction.isSupportDoubleScreen()) {
            this.mPsnScreenBrightnessLiveData.setValue(Integer.valueOf(getPsnScreenBrightness()));
        }
        refreshTheme();
        try {
            boolean isThemeWorking = this.mThemeController.isThemeWorking();
            Logs.d("xpdisplay daynight get working=" + isThemeWorking);
            this.mDayNightChangeEnable.setValue(Boolean.valueOf(!isThemeWorking));
        } catch (Exception unused) {
        }
        registerBrightnessChange();
        registerPsnBrightness();
        this.mDisplayEventMonitorHelper.registerFontScaleChange();
        this.mDisplayEventMonitorHelper.registerTimeFormatChange();
    }

    public void registerCMSAutoBrightness() {
        this.mDisplayEventMonitorHelper.registerCMSAutoBrightness();
    }

    public void registerRearScreenBrightness() {
        this.mDisplayEventMonitorHelper.registerRearScreenBrightness();
    }

    public void registerFontScaleChange() {
        this.mDisplayEventMonitorHelper.registerFontScaleChange();
    }

    public void registerTimeFormatChange() {
        this.mDisplayEventMonitorHelper.registerTimeFormatChange();
    }

    public void registerCarCallback() {
        this.mDisplayEventMonitorHelper.registerIcmBrightnessCallback();
        Logs.d("xpdisplay meter register callback");
    }

    public void releaseCarCallback() {
        this.mDisplayEventMonitorHelper.unRegisterIcmBrightnessCallback();
        Logs.d("xpdisplay meter unregister callback");
    }

    public void onStopVM() {
        unRegisterBrightnessChange();
        unregisterPsnBrightness();
        this.mDisplayEventMonitorHelper.unregisterFontScaleChange();
        this.mDisplayEventMonitorHelper.unregisterTimeFormatChange();
    }

    public void unregisterCMSAutoBrightness() {
        this.mDisplayEventMonitorHelper.unregisterCMSAutoBrightness();
    }

    public void unregisterRearScreenBrightness() {
        this.mDisplayEventMonitorHelper.unregisterRearScreenBrightness();
    }

    public void unregisterFontScaleChange() {
        this.mDisplayEventMonitorHelper.unregisterFontScaleChange();
    }

    public void unregisterTimeFormatChange() {
        this.mDisplayEventMonitorHelper.unregisterTimeFormatChange();
    }

    public void refreshTheme() {
        int dayNightMode = XpThemeUtils.getDayNightMode(this.mApplication);
        Logs.d("xpdisplay uim dayNight:" + dayNightMode);
        this.mThemeSwitchInfoLiveData.postValue(Integer.valueOf(dayNightMode));
    }

    public int getDayNightMode() {
        return XpThemeUtils.getDayNightMode(this.mApplication);
    }

    public void refreshThemeAndBrightness() {
        int dayNightMode = XpThemeUtils.getDayNightMode(this.mApplication);
        Logs.d("xpdisplay refreshThemeAndBrightness dayNight:" + dayNightMode);
        this.mThemeSwitchInfoLiveData.postValue(Integer.valueOf(dayNightMode));
        this.mScreenBrightnessLiveData.postValue(Integer.valueOf(getScreenBrightness()));
        this.mPsnScreenBrightnessLiveData.postValue(Integer.valueOf(getPsnScreenBrightness()));
        this.mMeterBrightnessLiveData.postValue(Integer.valueOf(getMeterDisplayProgress()));
    }

    public boolean isDayModeInAuto() {
        return XpThemeUtils.getDayNightMode(this.mApplication) == 0 && XpThemeUtils.getDayNightAutoMode(this.mApplication) == 1;
    }

    public boolean isNightModeInAuto() {
        return XpThemeUtils.getDayNightMode(this.mApplication) == 0 && XpThemeUtils.getDayNightAutoMode(this.mApplication) == 2;
    }

    public MutableLiveData<Integer> getScreenBrightnessLiveData() {
        return this.mScreenBrightnessLiveData;
    }

    public MutableLiveData<Integer> getPsnScreenBrightnessLiveData() {
        return this.mPsnScreenBrightnessLiveData;
    }

    public MutableLiveData<Integer> getMeterBrightnessLiveData() {
        return this.mMeterBrightnessLiveData;
    }

    public MutableLiveData<Boolean> getAutoBrightnessLiveData() {
        return this.mAutoBrightnessLiveData;
    }

    public MutableLiveData<Boolean> getMainAutoBrightnessLiveData() {
        return this.mMainAutoBrightnessLiveData;
    }

    public MutableLiveData<Boolean> mMeterAutoBrightnessLiveData() {
        return this.mMeterAutoBrightnessLiveData;
    }

    public MutableLiveData<Boolean> getPsnAutoBrightnessLiveData() {
        return this.mPsnAutoBrightnessLiveData;
    }

    public MutableLiveData<Boolean> getIsDarkStateLiveData() {
        return this.mIsDarkStateLiveData;
    }

    public MutableLiveData<Boolean> getFontScaleLiveData() {
        return this.mFontScaleLiveData;
    }

    public MutableLiveData<Boolean> getTimeFormatLiveData() {
        return this.mTimeFormatLiveData;
    }

    public MutableLiveData<Integer> getThemeSwitchInfoLiveData() {
        return this.mThemeSwitchInfoLiveData;
    }

    public MutableLiveData<Boolean> getCmsAutoBrightnessLiveData() {
        return this.mCmsAutoBrightnessLiveData;
    }

    public MutableLiveData<Integer> getRearScreenBrightnessLiveData() {
        return this.mRearScreenBrightnessLiveData;
    }

    public MutableLiveData<Boolean> getDayNightChangeComplete() {
        return this.mDayNightChangeEnable;
    }

    public MutableLiveData<Boolean> getRearScreenStateLiveData() {
        return this.mRearScreenStateLiveData;
    }

    public MutableLiveData<Integer> getRearScreenStateDetialLiveData() {
        return this.mRearScreenStateDetailLiveData;
    }

    public MutableLiveData<Integer> getRearScreenAngleLiveData() {
        return this.mRearScreenAngleLiveData;
    }

    public MutableLiveData<Integer> getRearScreenAngleCallbackLiveData() {
        return this.mRearScreenAngleCallbackLiveData;
    }

    public void setAdaptiveBrightness(boolean z) {
        this.mDataRepository.setAdaptiveBrightness(this.mApplication.getApplicationContext(), z, true);
    }

    public boolean isAdaptiveBrightness() {
        return this.mDataRepository.isAdptiveBrightness(this.mApplication.getApplicationContext());
    }

    public int getScreenBrightness() {
        return getCentralUIProgress(this.mDataRepository.getScreenBrightness(this.mApplication.getApplicationContext()));
    }

    public int getPsnScreenBrightness() {
        return getCentralUIProgress(this.mDataRepository.getPsnScreenBrightness(this.mApplication.getApplicationContext()));
    }

    public void setScreenBrightness(int i) {
        if (i > 100) {
            i = 100;
        } else if (i < 1) {
            i = 1;
        }
        this.mDataRepository.setScreenBrightness(this.mApplication.getApplicationContext(), getCentralLocalProgress(i));
    }

    public void setPsnScreenBrightness(int i) {
        if (i > 100) {
            i = 100;
        } else if (i < 1) {
            i = 1;
        }
        this.mDataRepository.setPsnScreenBrightness(this.mApplication.getApplicationContext(), getCentralLocalProgress(i));
    }

    public int getMeterDisplayProgress() {
        return this.mXpDisplayManager.getMeterBrightness(this.mApplication);
    }

    public void registerBrightnessChange() {
        this.mDisplayEventMonitorHelper.registerAutoScreenBrightness();
        this.mThemeController.register(this);
        registerBrightness();
        if (CarFunction.isSupportDoubleScreen()) {
            registerPsnBrightness();
        }
        registerICMBrightness(false);
        if (CarConfigHelper.autoBrightness() == -1) {
            refreshDarkState();
            registerDarkStateBrightness();
        }
    }

    public void registerDarkStateBrightness() {
        this.mDisplayEventMonitorHelper.registerDarkBrightness();
    }

    public void registerDayNight() {
        this.mThemeController.register(this);
    }

    public void registerBrightness() {
        this.mDisplayEventMonitorHelper.registerScreenBrightness();
    }

    public void registerPsnBrightness() {
        this.mDisplayEventMonitorHelper.registerPsnScreenBrightness();
    }

    public void registerICMBrightness(boolean z) {
        this.mHandler.removeCallbacks(this.mRegisterRunnable);
        if (z) {
            this.mHandler.postDelayed(this.mRegisterRunnable, 1000L);
        } else {
            this.mHandler.post(this.mRegisterRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerMeterCallback() {
        if (this.mIsAlreadyRegisterMeterCallback) {
            return;
        }
        this.mDisplayEventMonitorHelper.registerIcmScreenBrightness();
        this.mMeterBrightnessLiveData.postValue(Integer.valueOf(getMeterDisplayProgress()));
        Logs.d("xpdisplay meterBrightness register ");
        this.mIsAlreadyRegisterMeterCallback = true;
    }

    public void unRegisterBrightnessChange() {
        this.mDisplayEventMonitorHelper.unRegisterAutoScreenBrightness();
        this.mThemeController.unregister(this);
        unregisterBrightness();
        if (CarFunction.isSupportDoubleScreen()) {
            unregisterPsnBrightness();
        }
        unregisterICMBrightness();
        if (CarConfigHelper.autoBrightness() == -1) {
            unregisterDarkStateBrightness();
        }
    }

    public void unregisterDayNight() {
        this.mThemeController.unregister(this);
    }

    public boolean isMainAutoBrightnessModeEnable() {
        return this.mDataRepository.isMainAutoBrightnessModeEnable();
    }

    public boolean isMeterAutoBrightnessModeEnable() {
        return this.mDataRepository.isMeterAutoBrightnessModeEnable();
    }

    public void setMainAutoBrightnessModeEnable(boolean z) {
        this.mDataRepository.setMainAdaptiveBrightness(z, true);
    }

    public void setMeterAdaptiveBrightness(boolean z) {
        this.mDataRepository.setMeterAdaptiveBrightness(z, true);
    }

    public void setPsnAutoBrightnessModeEnable(boolean z) {
        this.mDataRepository.setPsnAdaptiveBrightness(z);
    }

    public boolean isPsnAutoBrightnessModeEnable() {
        return this.mDataRepository.isPsnAutoBrightnessModeEnable();
    }

    public void registerMainAutoBrightnessMode() {
        this.mDisplayEventMonitorHelper.registerMainAutoScreenBrightness();
    }

    public void registerMeterAutoBrightnessChange() {
        this.mDisplayEventMonitorHelper.registerMeterAutoBrightnessChange();
    }

    public void registerPsnAutoBrightnessMode() {
        this.mDisplayEventMonitorHelper.registerPsnAutoScreenBrightness();
    }

    public void unRegisterMainAutoBrightnessMode() {
        this.mDisplayEventMonitorHelper.unRegisterMainAutoScreenBrightness();
    }

    public void unregisterMeterAutoBrightnessChange() {
        this.mDisplayEventMonitorHelper.unregisterMeterAutoBrightnessChange();
    }

    public void unRegisterPsnAutoBrightnessMode() {
        this.mDisplayEventMonitorHelper.unRegisterPsnAutoScreenBrightness();
    }

    public void unregisterDarkStateBrightness() {
        this.mDisplayEventMonitorHelper.unRegisterDarkBrightness();
    }

    public void unregisterBrightness() {
        this.mDisplayEventMonitorHelper.unRegisterScreenBrightness();
    }

    public void unregisterPsnBrightness() {
        this.mDisplayEventMonitorHelper.unRegisterPsnScreenBrightness();
    }

    public void unregisterICMBrightness() {
        this.mHandler.removeCallbacks(this.mRegisterRunnable);
        if (this.mIsAlreadyRegisterMeterCallback) {
            this.mDisplayEventMonitorHelper.unRegisterIcmScreenBrightness();
            Logs.d("xpdisplay meterBrightness unregister ");
            this.mIsAlreadyRegisterMeterCallback = false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.DisplayEventMonitorListener
    public void onDisplayEventMonitorChange(Uri uri) {
        Logs.d("xpdisplay eventMonitorData uri；" + uri);
        if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS))) {
            boolean isThemeWorking = this.mThemeController.isThemeWorking();
            Logs.d("xpdisplay onDisplayEventMonitorChange brightness current daynight working:" + isThemeWorking);
            if (isThemeWorking) {
                return;
            }
            processBrightnessCallback();
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE))) {
            this.mAutoBrightnessLiveData.postValue(Boolean.valueOf(isAdaptiveBrightness()));
            if (CarFunction.isSupportDoubleScreen()) {
                this.mMainAutoBrightnessLiveData.postValue(Boolean.valueOf(isMainAutoBrightnessModeEnable()));
                this.mPsnAutoBrightnessLiveData.postValue(Boolean.valueOf(isPsnAutoBrightnessModeEnable()));
            }
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE))) {
            this.mMainAutoBrightnessLiveData.postValue(Boolean.valueOf(isMainAutoBrightnessModeEnable()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS_MODE))) {
            this.mPsnAutoBrightnessLiveData.postValue(Boolean.valueOf(isPsnAutoBrightnessModeEnable()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS))) {
            boolean isThemeWorking2 = this.mThemeController.isThemeWorking();
            Logs.d("xpdisplay onDisplayEventMonitorChange icm current daynight working:" + isThemeWorking2);
            if (isThemeWorking2) {
                return;
            }
            processIcmBrightnessCallback();
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS))) {
            refreshDarkState();
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS_CALLBACK))) {
            meterBrightnessChange(this.mDataRepository.getIcmBrightnessCallback(this.mApplication));
        } else if (uri.equals(Settings.System.getUriFor("font_scale"))) {
            this.mFontScaleLiveData.postValue(true);
        } else if (uri.equals(Settings.System.getUriFor("time_12_24"))) {
            this.mTimeFormatLiveData.postValue(true);
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS))) {
            boolean isThemeWorking3 = this.mThemeController.isThemeWorking();
            Logs.d("xpdisplay onDisplayEventMonitorChange PSN-brightness current daynight working:" + isThemeWorking3);
            if (isThemeWorking3) {
                return;
            }
            processPsnBrightnessCallback();
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.METER_AUTO_BRIGHTNESS_MODE))) {
            this.mMeterAutoBrightnessLiveData.postValue(Boolean.valueOf(isMeterAutoBrightnessModeEnable()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.CMS_AUTO_BRITHTNESS))) {
            this.mCmsAutoBrightnessLiveData.postValue(Boolean.valueOf(DataRepository.getInstance().isCMSAutoBrightnessModeEnable()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_BRITHTNESS))) {
            this.mRearScreenBrightnessLiveData.postValue(Integer.valueOf(DataRepository.getInstance().getRearScreenBrightness(this.mApplication)));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CONTROL))) {
            this.mRearScreenStateLiveData.postValue(Boolean.valueOf(getRearScreenControl()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_ANGLE))) {
            this.mRearScreenAngleLiveData.postValue(Integer.valueOf(getRearScreenAngle()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_STATE))) {
            this.mRearScreenStateDetailLiveData.postValue(Integer.valueOf(getRearScreenStateDetail()));
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CALLBACK_ANGLE))) {
            this.mRearScreenAngleCallbackLiveData.postValue(Integer.valueOf(getRearScreenAngleCallback()));
        }
    }

    private void refreshDarkState() {
        int i = Settings.System.getInt(this.mApplication.getContentResolver(), XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS, -1);
        Logs.d("processDarkBrightnessCallback darkState:" + i);
        this.mIsDarkStateLiveData.postValue(Boolean.valueOf(i == 1));
    }

    public int getDarkState() {
        return Settings.System.getInt(this.mApplication.getContentResolver(), XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS, -1);
    }

    private void processBrightnessCallback() {
        int screenBrightness = getScreenBrightness();
        Logs.d("xpdisplay processBrightnessCallback uiBrightness:" + screenBrightness);
        this.mScreenBrightnessLiveData.postValue(Integer.valueOf(screenBrightness));
    }

    private void processPsnBrightnessCallback() {
        int psnScreenBrightness = getPsnScreenBrightness();
        Logs.d("xpdisplay processPsnBrightnessCallback uiBrightness:" + psnScreenBrightness);
        this.mPsnScreenBrightnessLiveData.postValue(Integer.valueOf(psnScreenBrightness));
    }

    private void processIcmBrightnessCallback() {
        int meterDisplayProgress = getMeterDisplayProgress();
        Logs.d("xpdisplay icm callback brightness " + meterDisplayProgress);
        this.mMeterBrightnessLiveData.postValue(Integer.valueOf(meterDisplayProgress));
    }

    public void notifyMeterBrightnessChanged(int i, boolean z) {
        if (i > 100) {
            i = 100;
        } else if (i < 1) {
            i = 1;
        }
        bufferMeterBrightness(i, z);
    }

    private void bufferMeterBrightness(int i, boolean z) {
        Logs.d("xpdisplay meter bufferMeterBrightness uiProgress:" + i + " forceSetMeter:" + z + " mMeterBrightnessDragging:" + this.mMeterBrightnessDragging + " mLastMeterProcessDoing:" + this.mLastMeterProcessDoing);
        if (z) {
            this.mXpDisplayManager.setMeterBrightness(this.mApplication, i);
            this.mLastMeterSetValue = -1;
            this.mLastMeterProcessDoing = false;
            this.mMeterBrightnessDragging = -1;
        } else if (this.mMeterBrightnessDragging == -1) {
            this.mMeterBrightnessDragging = i;
            this.mXpDisplayManager.setMeterBrightness(this.mApplication, i);
            this.mLastMeterSetValue = i;
            this.mLastMeterProcessDoing = true;
        } else {
            this.mMeterBrightnessDragging = i;
            if (!CarFunction.isSupportAdjustMeterBrightnessProtect()) {
                this.mLastMeterProcessDoing = false;
            }
            Logs.d("xpdisplay meter mLastMeterProcessDoing:" + this.mLastMeterProcessDoing + " last:" + this.mLastMeterSetValue);
            if (this.mLastMeterProcessDoing || i == this.mLastMeterSetValue) {
                return;
            }
            this.mXpDisplayManager.setMeterBrightness(this.mApplication, i);
            this.mLastMeterSetValue = i;
            this.mLastMeterProcessDoing = true;
        }
    }

    public boolean setThemeDayNightMode(int i) {
        int dayNightMode = XpThemeUtils.getDayNightMode(this.mApplication);
        boolean isThemeSwitching = XpThemeUtils.isThemeSwitching(this.mApplication);
        Logs.d("xpdisplay setThemeDayNightMode curMode:" + dayNightMode + " mode:" + i + " " + isThemeSwitching);
        if (dayNightMode != i) {
            if (dayNightMode == 0) {
                int dayNightAutoMode = XpThemeUtils.getDayNightAutoMode(this.mApplication);
                Logs.d("xpdisplay setThemeDayNightMode dayNightInAuto:" + dayNightAutoMode);
                if (dayNightAutoMode == i) {
                    XpThemeUtils.setDayNightMode(this.mApplication, i);
                    return true;
                }
            }
            XpThemeUtils.setDayNightMode(this.mApplication, i);
            return true;
        }
        return false;
    }

    public void setFontSize(float f) {
        this.mDataRepository.setFontSize(this.mApplication, f, true);
    }

    public float getFontSize() {
        return this.mDataRepository.getCurrentFontSize(this.mApplication);
    }

    public void setDynamicWallPaperSwitch(boolean z) {
        this.mDataRepository.setDynamicWallPaperSwitch(this.mApplication, z, true);
    }

    public boolean getDynamicWallPaperSwitch() {
        return this.mDataRepository.getDynamicWallPaperSwitch(this.mApplication);
    }

    private int getCentralUIProgress(int i) {
        return this.mXpDisplayManager.getUIProgressByReal(i);
    }

    private int getCentralLocalProgress(int i) {
        return this.mXpDisplayManager.getRealBrightnessByUI(i);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.MeterCallback
    public void meterBrightnessChange(final int i) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.display.-$$Lambda$DisplaySettingsViewModel$2hhV_wMbKhymPu2gFi2NwTkcsLQ
            @Override // java.lang.Runnable
            public final void run() {
                DisplaySettingsViewModel.this.lambda$meterBrightnessChange$0$DisplaySettingsViewModel(i);
            }
        });
    }

    public /* synthetic */ void lambda$meterBrightnessChange$0$DisplaySettingsViewModel(int i) {
        this.mLastMeterProcessDoing = false;
        Logs.d("xpdisplay meter callback BrightnessChange change mMeterBrightnessDragging ui:" + this.mMeterBrightnessDragging + " mLastMeterSetValue:" + this.mLastMeterSetValue + " level:" + i);
        int i2 = this.mMeterBrightnessDragging;
        if (i2 == -1 || i2 == this.mLastMeterSetValue) {
            return;
        }
        this.mXpDisplayManager.setMeterBrightness(this.mApplication, i2);
        this.mLastMeterSetValue = this.mMeterBrightnessDragging;
        this.mLastMeterProcessDoing = true;
    }

    public void setMeterBrightness(int i) {
        this.mXpDisplayManager.setMeterBrightness(this.mApplication, i);
    }

    @Override // com.xiaopeng.libtheme.ThemeController.OnThemeListener
    public void onStateChanged(boolean z) {
        Logs.d("xpdisplay onStateChanged disable:" + z);
        if (!z) {
            refreshThemeAndBrightness();
        }
        this.mDayNightChangeEnable.postValue(Boolean.valueOf(!z));
    }

    @Override // com.xiaopeng.libtheme.ThemeController.OnThemeListener
    public void onThemeChanged(boolean z, Uri uri) {
        Logs.d("xpdisplay onThemeChanged uri:" + uri);
    }

    public int getNedcSwitchStatus() {
        return this.mXpDisplayManager.getNedcSwitchStatus();
    }

    public String requestEnterUserScenario(String str, int i) {
        return this.mXpDisplayManager.requestEnterUserScenario(str, i);
    }

    public String requestExitUserScenario(String str) {
        return this.mXpDisplayManager.requestExitUserScenario(str);
    }

    public String getCurrentLanguage() {
        return this.mXpDisplayManager.getCurrentLanguage();
    }

    public void setLanguage(String str) {
        this.mXpDisplayManager.setLanguage(str);
    }

    public String getCurrentRegion() {
        return this.mXpDisplayManager.getCurrentRegion();
    }

    public void setRegion(String str) {
        this.mXpDisplayManager.setRegion(str);
    }

    public String getDateSample(String str) {
        return this.mXpDisplayManager.getDateSample(str);
    }

    public String getNumSample(String str) {
        return this.mXpDisplayManager.getNumSample(str);
    }

    public void registerRearScreenSwitch() {
        this.mDisplayEventMonitorHelper.registerRearScreenSwitch();
    }

    public void unregisterRearScreenSwitch() {
        this.mDisplayEventMonitorHelper.unregisterRearScreenSwitch();
    }

    public void setRearScreenControl(boolean z) {
        this.mDataRepository.setSettingProvider(this.mApplication, XPSettingsConfig.REAR_SCREEN_CONTROL, z);
    }

    public boolean getRearScreenControl() {
        return this.mDataRepository.getSettingProvider(this.mApplication, XPSettingsConfig.REAR_SCREEN_CONTROL, false);
    }

    public void registerRearScreenState() {
        this.mDisplayEventMonitorHelper.registerRearScreenState();
    }

    public void unregisterRearScreenState() {
        this.mDisplayEventMonitorHelper.unregisterRearScreenState();
    }

    public int getRearScreenStateDetail() {
        return this.mDataRepository.getRearScreenStateDetail(this.mApplication);
    }

    public int getRearScreenAngleCallback() {
        return this.mDataRepository.getRearScreenAngleCallback(this.mApplication);
    }

    public void registerRearScreenAngle() {
        this.mDisplayEventMonitorHelper.registerRearScreenAngle();
    }

    public void unregisterRearScreenAngle() {
        this.mDisplayEventMonitorHelper.unregisterRearScreenAngle();
    }

    public void registerRearScreenAngleCallback() {
        this.mDisplayEventMonitorHelper.registerRearScreenAngleCallback();
    }

    public void unregisterRearScreenAngleCallback() {
        this.mDisplayEventMonitorHelper.unregisterRearScreenAngleCallback();
    }

    public int getRearScreenAngle() {
        return this.mDataRepository.getRearScreenAngle(this.mApplication);
    }

    public void setRearScreenAngle(int i) {
        this.mDataRepository.setRearScreenAngle(this.mApplication, i);
    }

    public void setRearScreenLock(boolean z) {
        this.mXpDisplayManager.setRearScreenLock(z, true);
    }

    public boolean getRearScreenLock() {
        return this.mXpDisplayManager.getRearScreenLock();
    }
}
