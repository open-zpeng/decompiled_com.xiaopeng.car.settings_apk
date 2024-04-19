package com.xiaopeng.car.settingslibrary.manager.car;

import android.car.hardware.power.CarPowerManager;
import android.car.hardware.xpu.CarXpuManager;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import java.util.concurrent.CompletableFuture;
/* loaded from: classes.dex */
public class CarApi {
    public static final int ID_XPU_AP_REMOTE_SW = 557856788;
    public static final int SHUTDOWN_PREPARE = 7;
    public static final int XPU_AP_REMOTE_SW_ON = 1;

    public static boolean isCarCallAdvancedEnable(CarXpuManager carXpuManager) {
        return false;
    }

    public static void setCarCallAdvancedEnable(CarXpuManager carXpuManager, boolean z) {
    }

    public static void registerPmListener(CarPowerManager carPowerManager, final CarSettingsManager.PmListenerCallback pmListenerCallback) {
        Logs.d("xpsettings carapi carPowerManager.setListenerWithCompletion");
        try {
            carPowerManager.setListenerWithCompletion(new CarPowerManager.CarPowerStateListenerWithCompletion() { // from class: com.xiaopeng.car.settingslibrary.manager.car.-$$Lambda$CarApi$E2diI-jxDKFM-9n8yxThMeQzeo0
                public final void onStateChanged(int i, CompletableFuture completableFuture) {
                    CarSettingsManager.PmListenerCallback.this.callback(i, completableFuture);
                }
            });
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
