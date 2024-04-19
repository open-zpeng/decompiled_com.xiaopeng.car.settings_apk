package com.xiaopeng.car.settingslibrary.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.xiaopeng.btservice.util.BtBoxesUtil;
import com.xiaopeng.car.settingslibrary.common.utils.CarStatusUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.demo.DemoManager;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.direct.UnityDirectData;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.net.feedback.FeedbackApiClient;
import com.xiaopeng.car.settingslibrary.repository.net.feedback.FeedbackNetService;
import com.xiaopeng.car.settingslibrary.service.GlobalService;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.configuration.ConfigurationModuleEntry;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xvs.tools.Tools;
import com.xiaopeng.xvs.xid.BuildConfig;
import com.xiaopeng.xvs.xid.XId;
/* loaded from: classes.dex */
public class ModuleInit {
    private static final String TAG = "ModuleInit";
    public static boolean isHiddenRefresh = true;
    public static Application mApp;
    private static BtBoxesUtil mBtBoxesUtil;
    private static CarSettingsManager mCarManager;
    private static Context mContext;
    private static FeedbackNetService mFeedbackNetService;
    private static XuiSettingsManager mXuiManager;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final ModuleInit instance = new ModuleInit();

        private InnerFactory() {
        }
    }

    public static ModuleInit getInstance() {
        return InnerFactory.instance;
    }

    public void init(Context context) {
        if (mApp != null) {
            Logs.d("xpsettings already init!");
            return;
        }
        mApp = (Application) context.getApplicationContext();
        mContext = context;
        Logs.d("xpsettings init app " + SystemClock.uptimeMillis());
        Xui.init(mApp);
        Xui.setDialogFullScreen(true);
        Xui.setFontScaleDynamicChangeEnable(true);
        mBtBoxesUtil = BtBoxesUtil.getInstance(context);
        mBtBoxesUtil.connectBluetooth();
        initSpeech();
        initAccountSync();
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(mContext));
        initXuiCarService();
        initFeedbackService();
        context.startService(new Intent(context, GlobalService.class));
        if (CarFunction.isNonSelfPageUI()) {
            ElementDirectManager.get().loadData(UnityDirectData.loadPageData(), UnityDirectData.loadItemData());
        }
        XpAutoManualManager.getInstance().init();
        configurationModuleInit();
        Logs.d("xpsettings configuration init");
        DemoManager.init(mApp);
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(context));
        ((IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class)).init();
        Logs.d("settings app init!");
    }

    private void initSpeech() {
        SpeechClient.instance().init(mContext);
        SpeechClient.instance().setAppName("设置", "系统设置");
        Logs.d("settings app initSpeech!");
    }

    public static synchronized FeedbackNetService initFeedbackService() {
        FeedbackNetService feedbackNetService;
        synchronized (ModuleInit.class) {
            if (mFeedbackNetService == null) {
                mFeedbackNetService = (FeedbackNetService) FeedbackApiClient.initService(FeedbackNetService.class);
            }
            feedbackNetService = mFeedbackNetService;
        }
        return feedbackNetService;
    }

    private void initAccountSync() {
        initSync();
        XpAccountManager.getInstance();
    }

    private void initSync() {
        String hardwareCarType = CarStatusUtils.getHardwareCarType();
        if (hardwareCarType == null) {
            LogUtils.e(TAG, "getHardwareCarType failed");
            hardwareCarType = BuildConfig.LIB_PRODUCT;
        }
        XId.init(mApp, Config.APP_ID, getAppSecret(), hardwareCarType);
        XId.getSyncApi().init();
        Tools.getSyncTransferTools().initDb(mApp);
    }

    private String getAppSecret() {
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.xiaopeng.com")) {
            return Config.SECRET_PUB;
        }
        return Config.SECRET_NO_PUB;
    }

    private void configurationModuleInit() {
        Module.register(ConfigurationModuleEntry.class, new ConfigurationModuleEntry());
        IConfiguration configurationController = getConfigurationController();
        if (configurationController != null) {
            configurationController.init(mApp, "xp_xui_car_settings");
        }
    }

    public static IConfiguration getConfigurationController() {
        try {
            return (IConfiguration) Module.get(ConfigurationModuleEntry.class).get(IConfiguration.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initXuiCarService() {
        mXuiManager = XuiSettingsManager.getInstance();
        mCarManager = CarSettingsManager.getInstance();
    }

    public static Application getApp() {
        return mApp;
    }

    public static Context getContext() {
        return mContext;
    }

    public static BtBoxesUtil getBtBoxesUtil() {
        if (mBtBoxesUtil == null) {
            mBtBoxesUtil = BtBoxesUtil.getInstance(mContext);
        }
        return mBtBoxesUtil;
    }
}
