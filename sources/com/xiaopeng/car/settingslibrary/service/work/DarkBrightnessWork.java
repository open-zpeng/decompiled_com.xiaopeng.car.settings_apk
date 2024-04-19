package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper;
/* loaded from: classes.dex */
public class DarkBrightnessWork implements WorkService, DisplayEventMonitorHelper.DisplayEventMonitorListener {
    private static final int ICM = 4;
    private static final int PSN_SCREEN = 2;
    private static final int SCREEN = 1;
    private DisplayEventMonitorHelper mDisplayEventMonitorHelper;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mDisplayEventMonitorHelper = new DisplayEventMonitorHelper(context, this);
        this.mDisplayEventMonitorHelper.registerDarkBrightnessAdj();
        this.mDisplayEventMonitorHelper.registerAutoBrightnessSync();
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        this.mDisplayEventMonitorHelper.unRegisterDarkBrightnessAdj();
        this.mDisplayEventMonitorHelper.unRegisterAutoBrightnessSync();
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.DisplayEventMonitorListener
    public void onDisplayEventMonitorChange(Uri uri) {
        if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS_ADJ))) {
            if (CarConfigHelper.hasAutoBrightness()) {
                Logs.d("DarkBrightness onChange no feature!");
                return;
            }
            int i = Settings.System.getInt(CarSettingsApp.getContext().getContentResolver(), XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS_ADJ, -1);
            Logs.d("DarkBrightness onChange darkStateAdj:" + i);
            if (i == 1) {
                AppServerManager.getInstance().onPopupToast(CarSettingsApp.getContext().getString(R.string.dark_brightness_toast));
            } else if (i == 4) {
                AppServerManager.getInstance().onPopupToast(CarSettingsApp.getContext().getString(R.string.dark_brightness_toast));
            } else if (i == 2) {
                ToastUtils.get().showText(CarSettingsApp.getContext().getString(R.string.dark_brightness_toast), 1);
            }
        } else if (uri.equals(Settings.System.getUriFor(XPSettingsConfig.SCREEN_BRIGHTNESS_MODE_SYNC))) {
            Logs.d("sync auto brightness to account!");
            if (CarConfigHelper.hasAutoBrightness()) {
                XpAccountManager.getInstance().saveAutoBrightness(CarFunction.isNonSelfPageUI() ? DataRepository.getInstance().isMainAutoBrightnessModeEnable() : DataRepository.getInstance().isAdptiveBrightness(CarSettingsApp.getContext()), true);
            } else {
                XpAccountManager.getInstance().saveDarkLightAdaptation(CarFunction.isNonSelfPageUI() ? DataRepository.getInstance().isMainAutoBrightnessModeEnable() : DataRepository.getInstance().isAdptiveBrightness(CarSettingsApp.getContext()), true);
            }
        }
    }
}
