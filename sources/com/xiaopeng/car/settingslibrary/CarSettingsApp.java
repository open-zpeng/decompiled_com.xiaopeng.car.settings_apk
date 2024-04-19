package com.xiaopeng.car.settingslibrary;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.xiaopeng.btservice.util.BtBoxesUtil;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.ModuleInit;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.demo.XpUIDemoService;
import com.xiaopeng.car.settingslibrary.direct.DirectData;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.repository.net.feedback.FeedbackNetService;
import com.xiaopeng.car.settingslibrary.ui.listener.AppUIListener;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.lib.bughunter.BugHunter;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.Xui;
/* loaded from: classes.dex */
public class CarSettingsApp extends Application {
    public static final int CONFIGURATION_TIMEOUT = 15000;
    private static final String TAG = "CarSettingsApp";

    @Override // android.app.Application
    public void onCreate() {
        StartPerfUtils.appOnCreateBegin();
        super.onCreate();
        Logs.d("CarSettingsApp onCreate " + Build.VERSION.SDK_INT);
        Logs.d("CarSettingsApp this is a flag for platform: 13");
        ModuleInit.getInstance().init(this);
        BugHunter.init(this);
        Xui.setVuiEnable(true);
        ToastUtils.init(this);
        VuiEngine.getInstance(this).subscribe("com.xiaopeng.car.settings.SettingsVuiObserver");
        LogUtils.e(TAG, "isVuiFeatureDisabled " + VuiEngine.getInstance(this).isVuiFeatureDisabled());
        if (Utils.isUserRelease()) {
            VuiEngine.getInstance(this).setLoglevel(com.xiaopeng.speech.vui.utils.LogUtils.LOG_INFO_LEVEL);
        } else {
            VuiEngine.getInstance(this).setLoglevel(com.xiaopeng.speech.vui.utils.LogUtils.LOG_VERBOSE_LEVEL);
        }
        if (!CarFunction.isNonSelfPageUI()) {
            ElementDirectManager.get().loadData(DirectData.loadPageData(), DirectData.loadItemData());
        }
        AppServerManager.getInstance().setListener(AppUIListener.getInstance());
        if (!Utils.isUserRelease()) {
            startService(new Intent(this, XpUIDemoService.class));
        }
        StartPerfUtils.appOnCreateEnd();
    }

    public static Application getApp() {
        return ModuleInit.getApp();
    }

    public static Context getContext() {
        return ModuleInit.getContext();
    }

    public static IConfiguration getConfigurationController() {
        return ModuleInit.getConfigurationController();
    }

    public static BtBoxesUtil getBtBoxesUtil() {
        return ModuleInit.getBtBoxesUtil();
    }

    public static FeedbackNetService initFeedbackService() {
        return ModuleInit.initFeedbackService();
    }

    public static boolean isVuiFeatureEnable() {
        if (CarFunction.isNonSelfPageUI()) {
            return VuiEngine.getInstance(getContext()).isSpeechShowNumber();
        }
        return !VuiEngine.getInstance(getContext()).isVuiFeatureDisabled();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Logs.d("xpsettings onLowMemory");
    }
}
