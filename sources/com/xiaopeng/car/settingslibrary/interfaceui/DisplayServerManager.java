package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.datetime.DatetimeSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.display.DisplaySettingsViewModel;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes.dex */
public class DisplayServerManager extends ServerBaseManager {
    private static final String TAG = "DisplayServerManager";
    private DatetimeSettingsViewModel mDatetimeSettingsViewModel;
    private DisplaySettingsViewModel mDisplaySettingsViewModel;
    private final CopyOnWriteArraySet<IDisplayServerListener> mDisplayUIListeners = new CopyOnWriteArraySet<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final DisplayServerManager instance = new DisplayServerManager();

        private InnerFactory() {
        }
    }

    public static DisplayServerManager getInstance() {
        return InnerFactory.instance;
    }

    public void addDisplayUIListener(IDisplayServerListener iDisplayServerListener) {
        this.mDisplayUIListeners.add(iDisplayServerListener);
    }

    public void removeDisplayUIListener(IDisplayServerListener iDisplayServerListener) {
        this.mDisplayUIListeners.remove(iDisplayServerListener);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getAutoBrightnessLiveData().setValue(null);
        getViewModel().getAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$XsqLY_apX88WC7ywB4kowt6vHNM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$0$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getDayNightChangeComplete().setValue(null);
        getViewModel().getDayNightChangeComplete().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$KCl_N00zXNxpcooc4O2AggnUFQY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$1$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getFontScaleLiveData().setValue(null);
        getViewModel().getFontScaleLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$eiM81jufiVmA2OOQ3xzmz_Ul3M0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$2$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getIsDarkStateLiveData().setValue(null);
        getViewModel().getIsDarkStateLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$IsL78ooxnaaVyCfClropEFtYhsI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$3$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getMeterBrightnessLiveData().setValue(-1);
        getViewModel().getMeterBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$f6dIHl8RrMngP56IMJA4DHVU8KQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$4$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getScreenBrightnessLiveData().setValue(-1);
        getViewModel().getScreenBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$4rv-iLOceg_HuiVA22pJK2UdQTc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$5$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getPsnScreenBrightnessLiveData().setValue(-1);
        getViewModel().getPsnScreenBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$7b-6IocGUcOkLfAXReac2R68kUg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$6$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getThemeSwitchInfoLiveData().setValue(-1);
        getViewModel().getThemeSwitchInfoLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$3JqlbIcbgCzrr6CS3ToNm6AYc3Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$7$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getTimeFormatLiveData().setValue(null);
        getViewModel().getTimeFormatLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$88Uv2Y7AkbgmZh1VWunK6mtU6tU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$8$DisplayServerManager((Boolean) obj);
            }
        });
        getDatetimeViewModel().getDatetimeModelMutableLiveData().setValue(null);
        getDatetimeViewModel().getDatetimeModelMutableLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$niLozpAYNt0v79jgrmwDxon7pEY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$9$DisplayServerManager((String) obj);
            }
        });
        getViewModel().getMainAutoBrightnessLiveData().setValue(null);
        getViewModel().getMainAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$dtLa4csgv3xaiHdOLRmH_8a81Bw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$10$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().mMeterAutoBrightnessLiveData().setValue(null);
        getViewModel().mMeterAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$gOnR_9zDTLAAJMt2S9BapsFrog0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$11$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getPsnAutoBrightnessLiveData().setValue(null);
        getViewModel().getPsnAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$SYCoYC2zL59Gs80MLAgIK5Zpnuo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$12$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getCmsAutoBrightnessLiveData().setValue(null);
        getViewModel().getCmsAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$9n6m3P3Kl-NzYaZyGi6nLDBP1Ec
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$13$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getRearScreenBrightnessLiveData().setValue(-1);
        getViewModel().getRearScreenBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$I5rm8y-GXctD4OtwD4Ix3t9gNqI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$14$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getRearScreenStateLiveData().setValue(null);
        getViewModel().getRearScreenStateLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$J1p3WrT3ykzv17R270Hm49dRCIU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$15$DisplayServerManager((Boolean) obj);
            }
        });
        getViewModel().getRearScreenAngleLiveData().setValue(null);
        getViewModel().getRearScreenAngleLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$O1ptngDyxw03ap2sBTVbb19p-v4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$16$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getRearScreenStateDetialLiveData().setValue(-1);
        getViewModel().getRearScreenStateDetialLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$IWXuCuH3xt9gHlGYz9LPduX9eaE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$17$DisplayServerManager((Integer) obj);
            }
        });
        getViewModel().getRearScreenAngleCallbackLiveData().setValue(-1);
        getViewModel().getRearScreenAngleCallbackLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DisplayServerManager$VIfKflMzRSSNd9ULOzH1SM5Ji2Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayServerManager.this.lambda$observeData$18$DisplayServerManager((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onAutoBrightnessChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onAutoBrightnessChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_AUTO_BRIGHTNESS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$1$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DisplayServerManager onDayNightChangeWork ");
        sb.append(!bool.booleanValue());
        debugLog(sb.toString());
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onDayNightChangeWork(!bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_DAY_NIGHT_WORK_CHANGED, String.valueOf(!bool.booleanValue()));
    }

    public /* synthetic */ void lambda$observeData$2$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onFontScaleChanged ");
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onFontScaleChanged();
        }
        displayCallback(InterfaceConstant.ON_FONT_SCALE_CHANGED, "");
    }

    public /* synthetic */ void lambda$observeData$3$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onIsDarkState " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onIsDarkState(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_IS_DARK_STATE, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$4$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onMeterBrightnessChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onMeterBrightnessChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_METER_BRIGHTNESS_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$5$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onScreenBrightnessChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onScreenBrightnessChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_SCREEN_BRIGHTNESS_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$6$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onPsnScreenBrightnessChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onPsnScreenBrightnessChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_PSN_SCREEN_BRIGHTNESS_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$7$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onThemeSwitchInfoChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onThemeSwitchInfoChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_THEME_SWITCH_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$8$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onIs24HourChanged " + XpAccountManager.getInstance().is24HourFormat());
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onIs24HourChanged(XpAccountManager.getInstance().is24HourFormat());
        }
        displayCallback(InterfaceConstant.ON_IS_24_HOUR_CHANGED, String.valueOf(XpAccountManager.getInstance().is24HourFormat()));
    }

    public /* synthetic */ void lambda$observeData$9$DisplayServerManager(String str) {
        if (str == null) {
            return;
        }
        debugLog("DisplayServerManager onDatetimeModelChanged " + str);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onDatetimeModelChanged(str);
        }
        displayCallback(InterfaceConstant.ON_DATE_TIME_CHANGED, str);
    }

    public /* synthetic */ void lambda$observeData$10$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onMainAutoBrightnessModeChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onMainAutoBrightnessModeChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_MAIN_AUTO_BRIGHTNESS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$11$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onMeterAutoBrightnessModeChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onMeterAutoBrightnessModeChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_METER_AUTO_BRIGHTNESS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$12$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onPsnAutoBrightnessModeChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onPsnAutoBrightnessModeChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_PSN_AUTO_BRIGHTNESS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$13$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onCmsAutoBrightnessModeChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onCmsAutoBrightnessModeChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_CMS_AUTO_BRIGHTNESS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$14$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onCmsBrightnessModeChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onRearScreenBrightnessChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_REAR_SCREEN_BRIGHTNESS_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$15$DisplayServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        debugLog("DisplayServerManager onRearScreenStatusChanged " + bool);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onRearScreenStatusChanged(bool.booleanValue());
        }
        displayCallback(InterfaceConstant.ON_REAR_SCREEN_STATUS_CHANGED, String.valueOf(bool));
    }

    public /* synthetic */ void lambda$observeData$16$DisplayServerManager(Integer num) {
        if (num == null) {
            return;
        }
        debugLog("DisplayServerManager onRearScreenAngleChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onRearScreenAngleChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_REAR_SCREEN_ANGLE_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$17$DisplayServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("DisplayServerManager onRearScreenStateDetailChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onRearScreenStateDetailChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_REAR_SCREEN_STATE_DETAIL_CHANGED, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$18$DisplayServerManager(Integer num) {
        if (num == null) {
            return;
        }
        debugLog("DisplayServerManager onRearScreenAngleCallbackChanged " + num);
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onRearScreenAngleCallbackChanged(num.intValue());
        }
        displayCallback(InterfaceConstant.ON_REAR_SCREEN_ANGLE_CALLBACK_CHANGED, String.valueOf(num));
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
        getDatetimeViewModel();
    }

    private synchronized DisplaySettingsViewModel getViewModel() {
        if (this.mDisplaySettingsViewModel == null) {
            this.mDisplaySettingsViewModel = new DisplaySettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mDisplaySettingsViewModel;
    }

    private synchronized DatetimeSettingsViewModel getDatetimeViewModel() {
        if (this.mDatetimeSettingsViewModel == null) {
            this.mDatetimeSettingsViewModel = new DatetimeSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mDatetimeSettingsViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("DisplayServerManager startVm");
        getViewModel().onStartVM();
        getDatetimeViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("DisplayServerManager stopVm");
        getViewModel().onStopVM();
        getDatetimeViewModel().onStopVM();
    }

    public void onAccountChanged() {
        debugLog("DisplayServerManager onAccountChanged");
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onAccountChanged();
        }
        displayCallback(InterfaceConstant.ON_ACCOUNT_CHANGED, "");
    }

    public void onDayNightChanged() {
        debugLog("DisplayServerManager onDayNightChanged");
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onDayNightChanged();
        }
        displayCallback(InterfaceConstant.ON_DAY_NIGHT_CHANGED, "");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    public int getNedcSwitchStatus() {
        int nedcSwitchStatus = getViewModel().getNedcSwitchStatus();
        log("DisplayServerManager getNedcSwitchStatus " + nedcSwitchStatus);
        return nedcSwitchStatus;
    }

    public int getScreenBrightness() {
        int screenBrightness = getViewModel().getScreenBrightness();
        log("DisplayServerManager getScreenBrightness " + screenBrightness);
        return screenBrightness;
    }

    public int getMeterDisplayProgress() {
        int meterDisplayProgress = getViewModel().getMeterDisplayProgress();
        log("DisplayServerManager getMeterDisplayProgress " + meterDisplayProgress);
        return meterDisplayProgress;
    }

    public boolean getAutoBrightness() {
        boolean autoBrightness = XpAccountManager.getInstance().getAutoBrightness();
        log("DisplayServerManager getAutoBrightness " + autoBrightness);
        return autoBrightness;
    }

    public boolean getMainAutoScreenBrightnessMode() {
        boolean autoBrightness = CarConfigHelper.hasAutoBrightness() ? XpAccountManager.getInstance().getAutoBrightness() : XpAccountManager.getInstance().getDarkLightAdaptation();
        log("DisplayServerManager getMainAutoScreenBrightnessMode " + autoBrightness);
        return autoBrightness;
    }

    public boolean isMeterAutoBrightnessModeEnable() {
        boolean meterAutoBrightness = CarConfigHelper.hasAutoBrightness() ? XpAccountManager.getInstance().getMeterAutoBrightness() : XpAccountManager.getInstance().getMeterDarkLightAdaptation();
        log("DisplayServerManager isMeterAutoBrightnessModeEnable " + meterAutoBrightness);
        return meterAutoBrightness;
    }

    public boolean getPsnAutoScreenBrightnessMode() {
        boolean isPsnAutoBrightnessModeEnable = getViewModel().isPsnAutoBrightnessModeEnable();
        log("DisplayServerManager getPsnAutoScreenBrightnessMode " + isPsnAutoBrightnessModeEnable);
        return isPsnAutoBrightnessModeEnable;
    }

    public void setMainAutoBrightnessModeEnable(boolean z) {
        log("DisplayServerManager setMainAutoBrightnessModeEnable enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B002", z);
        getViewModel().setMainAutoBrightnessModeEnable(z);
    }

    public void setMeterAdaptiveBrightness(boolean z) {
        log("DisplayServerManager setMeterAdaptiveBrightness enable:" + z);
        getViewModel().setMeterAdaptiveBrightness(z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B005", z);
    }

    public void setPsnAutoBrightnessModeEnable(boolean z) {
        log("DisplayServerManager setPsnAutoBrightnessModeEnable enable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B003", z);
        getViewModel().setPsnAutoBrightnessModeEnable(z);
    }

    public boolean getDarkLightAdaptation() {
        boolean darkLightAdaptation = XpAccountManager.getInstance().getDarkLightAdaptation();
        log("DisplayServerManager getDarkLightAdaptation " + darkLightAdaptation);
        return darkLightAdaptation;
    }

    public float getFontSize() {
        float fontSize = XpAccountManager.getInstance().getFontSize();
        log("DisplayServerManager getFontSize " + fontSize);
        return fontSize;
    }

    public boolean is24HourFormat() {
        boolean is24HourFormat = XpAccountManager.getInstance().is24HourFormat();
        log("DisplayServerManager is24HourFormat " + is24HourFormat);
        return is24HourFormat;
    }

    public void setScreenBrightness(int i) {
        log("DisplayServerManager setScreenBrightness uiBrightness:" + i);
        getViewModel().setScreenBrightness(i);
    }

    public void setPsnScreenBrightness(int i) {
        log("DisplayServerManager setPsnScreenBrightness uiBrightness:" + i);
        getViewModel().setPsnScreenBrightness(i);
    }

    public int getPsnScreenBrightness() {
        int psnScreenBrightness = getViewModel().getPsnScreenBrightness();
        log("DisplayServerManager getPsnScreenBrightness " + psnScreenBrightness);
        return psnScreenBrightness;
    }

    public void unregisterBrightness() {
        log("DisplayServerManager unregisterBrightness");
        getViewModel().unregisterBrightness();
        if (CarFunction.isSupportDoubleScreen()) {
            getViewModel().unregisterPsnBrightness();
        }
    }

    public void registerBrightness() {
        log("DisplayServerManager registerBrightness");
        getViewModel().registerBrightness();
        if (CarFunction.isSupportDoubleScreen()) {
            getViewModel().registerPsnBrightness();
        }
    }

    public void unregisterPsnBrightness() {
        log("DisplayServerManager unregisterPsnBrightness");
        getViewModel().unregisterPsnBrightness();
    }

    public void registerPsnBrightness() {
        log("DisplayServerManager registerPsnBrightness");
        getViewModel().registerPsnBrightness();
    }

    public void registerMainAutoBrightnessMode() {
        log("DisplayServerManager registerMainAutoBrightnessMode");
        getViewModel().registerMainAutoBrightnessMode();
    }

    public void registerMeterAutoBrightnessChange() {
        log("DisplayServerManager registerMeterAutoBrightnessChange");
        getViewModel().registerMeterAutoBrightnessChange();
    }

    public void registerPsnAutoBrightnessMode() {
        log("DisplayServerManager registerPsnAutoBrightnessMode");
        getViewModel().registerPsnAutoBrightnessMode();
    }

    public void unRegisterMainAutoBrightnessMode() {
        log("DisplayServerManager unRegisterMainAutoBrightnessMode");
        getViewModel().unRegisterMainAutoBrightnessMode();
    }

    public void unregisterMeterAutoBrightnessChange() {
        log("DisplayServerManager unregisterMeterAutoBrightnessChange");
        getViewModel().unregisterMeterAutoBrightnessChange();
    }

    public void unRegisterPsnAutoBrightnessMode() {
        log("DisplayServerManager unRegisterPsnAutoBrightnessMode");
        getViewModel().unRegisterPsnAutoBrightnessMode();
    }

    public void notifyMeterBrightnessChanged(int i, boolean z) {
        log("DisplayServerManager notifyMeterBrightnessChanged");
        getViewModel().notifyMeterBrightnessChanged(i, z);
    }

    public void unregisterICMBrightness() {
        log("DisplayServerManager unregisterICMBrightness");
        getViewModel().unregisterICMBrightness();
    }

    public void registerCarCallback() {
        log("DisplayServerManager registerCarCallback");
        getViewModel().registerCarCallback();
    }

    public void releaseCarCallback() {
        log("DisplayServerManager releaseCarCallback");
        getViewModel().releaseCarCallback();
    }

    public void registerICMBrightness(boolean z) {
        log("DisplayServerManager registerICMBrightness");
        getViewModel().registerICMBrightness(z);
    }

    public String requestEnterUserScenario(String str, int i) {
        String requestEnterUserScenario = getViewModel().requestEnterUserScenario(str, i);
        if (XuiSettingsManager.USER_SCENARIO_CLEAN_MODE.equals(str)) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B006");
        } else if (XuiSettingsManager.USER_SCENARIO_WAITING_MODE.equals(str)) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B001");
        }
        log("DisplayServerManager requestEnterUserScenario mode:" + str + " " + requestEnterUserScenario);
        return requestEnterUserScenario;
    }

    public String requestExitUserScenario(String str) {
        String requestExitUserScenario = getViewModel().requestExitUserScenario(str);
        log("DisplayServerManager requestExitUserScenario mode:" + str + " " + requestExitUserScenario);
        return requestExitUserScenario;
    }

    public void setAdaptiveBrightness(boolean z) {
        log("DisplayServerManager setAdaptiveBrightness isChecked:" + z);
        getViewModel().setAdaptiveBrightness(z);
    }

    public boolean setThemeDayNightMode(int i) {
        boolean themeDayNightMode = getViewModel().setThemeDayNightMode(i);
        log("DisplayServerManager setThemeDayNightMode mode:" + i + " " + themeDayNightMode);
        return themeDayNightMode;
    }

    public int getDayNightMode() {
        int dayNightMode = getViewModel().getDayNightMode();
        log("DisplayServerManager getDayNightMode " + dayNightMode);
        return dayNightMode;
    }

    public boolean isDayNightSwitching() {
        return XpThemeUtils.isThemeSwitching(CarSettingsApp.getContext());
    }

    public void setFontSize(float f) {
        log("DisplayServerManager setFontSize size:" + f);
        getViewModel().setFontSize(f);
    }

    public void set24HourFormat(boolean z) {
        log("DisplayServerManager set24HourFormat isChecked:" + z);
        getDatetimeViewModel().set24HourFormat(z);
    }

    public void onPsnScreenStatusOn(boolean z) {
        Iterator<IDisplayServerListener> it = this.mDisplayUIListeners.iterator();
        while (it.hasNext()) {
            it.next().onPsnScreenOnStatusChange(z);
        }
    }

    public String getCurrentLanguage() {
        String currentLanguage = getViewModel().getCurrentLanguage();
        log("DisplayServerManagercurrentLanguage:" + currentLanguage);
        return currentLanguage;
    }

    public void setLanguage(String str) {
        log("DisplayServerManagerlanguageName:" + str);
        getViewModel().setLanguage(str);
    }

    public String getCurrentRegion() {
        String currentRegion = getViewModel().getCurrentRegion();
        log("DisplayServerManagercurrentRegion:" + currentRegion);
        return currentRegion;
    }

    public void setRegion(String str) {
        log("DisplayServerManager countryCode:" + str);
        getViewModel().setRegion(str);
    }

    public String getDataSample(String str) {
        String dateSample = getViewModel().getDateSample(str);
        log("DisplayServerManager dataSample:" + dateSample);
        return dateSample;
    }

    public String getNumSample(String str) {
        String numSample = getViewModel().getNumSample(str);
        log("DisplayServerManager numSample:" + numSample);
        return numSample;
    }

    public String getRegionSample(String str) {
        String str2 = getDataSample(str) + "   " + getNumSample(str);
        log("DisplayServerManager countryCode:" + str + " regionSample:" + str2);
        return str2;
    }

    public String getDateAndTime() {
        return getDatetimeViewModel().getDateAndTime();
    }

    public int getDarkState() {
        return getViewModel().getDarkState();
    }

    public void registerDarkStateBrightness() {
        getViewModel().registerDarkStateBrightness();
    }

    public void registerDayNight() {
        getViewModel().registerDayNight();
    }

    public void unregisterDayNight() {
        getViewModel().unregisterDayNight();
    }

    public void unregisterDarkStateBrightness() {
        getViewModel().unregisterDarkStateBrightness();
    }

    public void registerFontScaleChange() {
        getViewModel().registerFontScaleChange();
    }

    public void registerTimeFormatChange() {
        getViewModel().registerTimeFormatChange();
    }

    public void unregisterFontScaleChange() {
        getViewModel().unregisterFontScaleChange();
    }

    public void unregisterTimeFormatChange() {
        getViewModel().unregisterTimeFormatChange();
    }

    public void registerCMSAutoBrightness() {
        getViewModel().registerCMSAutoBrightness();
    }

    public void registerRearScreenBrightness() {
        getViewModel().registerRearScreenBrightness();
    }

    public void unregisterCMSAutoBrightness() {
        getViewModel().unregisterCMSAutoBrightness();
    }

    public void unregisterRearScreenBrightness() {
        getViewModel().unregisterRearScreenBrightness();
    }

    public boolean isCMSAutoBrightnessModeEnable() {
        return DataRepository.getInstance().isCMSAutoBrightnessModeEnable();
    }

    public void setCMSAdaptiveBrightness(boolean z) {
        DataRepository.getInstance().setCMSAdaptiveBrightness(z);
    }

    public void setRearScreenBrightness(int i) {
        DataRepository.getInstance().setRearScreenBrightness(CarSettingsApp.getContext(), i);
    }

    public int getRearScreenBrightness() {
        return DataRepository.getInstance().getRearScreenBrightness(CarSettingsApp.getContext());
    }

    public void registerRearScreenSwitch() {
        getViewModel().registerRearScreenSwitch();
    }

    public void unregisterRearScreenSwitch() {
        getViewModel().unregisterRearScreenSwitch();
    }

    public void setRearScreenState(boolean z) {
        getViewModel().setRearScreenControl(z);
    }

    public boolean getRearScreenState() {
        return getViewModel().getRearScreenControl();
    }

    public void registerRearScreenState() {
        getViewModel().registerRearScreenState();
    }

    public void unregisterRearScreenState() {
        getViewModel().unregisterRearScreenState();
    }

    public int getRearScreenStateDetail() {
        return getViewModel().getRearScreenStateDetail();
    }

    public void registerRearScreenAngle() {
        getViewModel().registerRearScreenAngle();
    }

    public void unregisterRearScreenAngle() {
        getViewModel().unregisterRearScreenAngle();
    }

    public int getRearScreenAngle() {
        return DataRepository.getInstance().getRearScreenAngle(CarSettingsApp.getContext());
    }

    public void setRearScreenAngle(int i) {
        DataRepository.getInstance().setRearScreenAngle(CarSettingsApp.getContext(), i);
    }

    public void registerRearScreenAngleCallback() {
        getViewModel().registerRearScreenAngleCallback();
    }

    public void unregisterRearScreenAngleCallback() {
        getViewModel().unregisterRearScreenAngleCallback();
    }

    public int getRearScreenAngleCallback() {
        return DataRepository.getInstance().getRearScreenAngleCallback(CarSettingsApp.getContext());
    }

    public void setRearScreenLock(boolean z) {
        getViewModel().setRearScreenLock(z);
    }

    public boolean getRearScreenLock() {
        return getViewModel().getRearScreenLock();
    }
}
