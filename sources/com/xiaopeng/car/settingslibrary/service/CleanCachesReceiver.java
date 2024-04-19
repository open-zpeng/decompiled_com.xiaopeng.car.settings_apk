package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.libconfig.ipc.IpcConfig;
/* loaded from: classes.dex */
public class CleanCachesReceiver extends BroadcastReceiver {
    private static final long CACHE_AUTO_CLEAN_SIZE = 1073741824;
    boolean isAutoCleaning = false;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("CleanCachesReceiver action:" + intent.getAction());
        if (Config.ACTION_REQUEST_CLEAN_CACHES.equals(intent.getAction())) {
            XpAboutManager.getInstance().cleanCaches();
        } else if (Config.ACTION_DEVICE_STORAGE_AUTO.equals(intent.getAction())) {
            if (this.isAutoCleaning) {
                Logs.d("CleanCachesReceiver auto clean is running, break.");
            } else {
                startAutoClean();
            }
        }
    }

    private void startAutoClean() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.-$$Lambda$CleanCachesReceiver$-5hEnwPE9H0IaPh4aZr7zD_XtMY
            @Override // java.lang.Runnable
            public final void run() {
                CleanCachesReceiver.this.lambda$startAutoClean$0$CleanCachesReceiver();
            }
        });
    }

    public /* synthetic */ void lambda$startAutoClean$0$CleanCachesReceiver() {
        final long computeFiles = XpAboutManager.computeFiles();
        if (computeFiles > 1073741824) {
            this.isAutoCleaning = true;
            Logs.d("CleanCachesReceiver AutoCleaning");
            XpAboutManager.getInstance().cleanCachesWithOutMusic(new XpAboutManager.CachesChangeListener() { // from class: com.xiaopeng.car.settingslibrary.service.CleanCachesReceiver.1
                @Override // com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager.CachesChangeListener
                public void onStorageSizeChange() {
                }

                @Override // com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager.CachesChangeListener
                public void onCleanFinish() {
                    Logs.d("CleanCachesReceiver onCleanFinish");
                    long computeFiles2 = XpAboutManager.computeFiles();
                    long j = computeFiles - computeFiles2;
                    if (j <= 0) {
                        j = 0;
                    }
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B017", BuriedPointUtils.COUNT_KEY, Long.valueOf(j));
                    if (computeFiles2 > 1073741824) {
                        Logs.d("CleanCachesReceiver clearMusic");
                        XpAboutManager.clearMusic();
                        long computeFiles3 = computeFiles2 - XpAboutManager.computeFiles();
                        if (computeFiles3 <= 0) {
                            computeFiles3 = 0;
                        }
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, BuriedPointUtils.STORAGE_AUTO_CLEAN_MUSIC_ID, BuriedPointUtils.COUNT_KEY, Long.valueOf(computeFiles3));
                    }
                    CleanCachesReceiver.this.autoCleanCallback();
                    CleanCachesReceiver.this.isAutoCleaning = false;
                    Logs.d("CleanCachesReceiver AutoCleaned");
                }
            });
            return;
        }
        Logs.d("CleanCachesReceiver used cache < 1G");
        autoCleanCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autoCleanCallback() {
        Intent intent = new Intent();
        intent.setPackage(IpcConfig.App.APP_CAR_DIAGNOSIS);
        intent.setAction("com.xiaopeng.intent.action.ACTION_DEVICE_STORAGE_AUTO_RETURN");
        intent.setFlags(16777216);
        CarSettingsApp.getContext().sendBroadcast(intent);
        this.isAutoCleaning = false;
    }
}
