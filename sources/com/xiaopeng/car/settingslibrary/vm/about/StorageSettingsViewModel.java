package com.xiaopeng.car.settingslibrary.vm.about;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.about.PackageIntentReceiver;
import com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.app.entry.StorageSize;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class StorageSettingsViewModel extends AndroidViewModel implements XpAboutManager.CachesChangeListener {
    private final MutableLiveData<Long> allCacheSize;
    public List<AppStorageBean> appStorageBeans;
    private final MutableLiveData<Boolean> appsInfoLoaded;
    private final Application mApplication;
    private final MutableLiveData<Boolean> mCleanFinishLiveData;
    private final StorageSettingsInfo mStorageSettingsInfo;
    private final MutableLiveData<StorageSettingsInfo> mStorageSettingsInfoLiveData;

    public StorageSettingsViewModel(Application application) {
        super(application);
        this.mStorageSettingsInfo = new StorageSettingsInfo();
        this.mStorageSettingsInfoLiveData = new MutableLiveData<>();
        this.mCleanFinishLiveData = new MutableLiveData<>();
        this.appsInfoLoaded = new MutableLiveData<>();
        this.allCacheSize = new MutableLiveData<>();
        this.appStorageBeans = new ArrayList();
        this.mApplication = application;
    }

    public MutableLiveData<StorageSettingsInfo> getStorageSettingsInfoLiveData() {
        return this.mStorageSettingsInfoLiveData;
    }

    public MutableLiveData<Boolean> getCleanFinishLiveData() {
        return this.mCleanFinishLiveData;
    }

    public void startVm() {
        XpAboutManager.getInstance().registerReceiver();
        XpAboutManager.getInstance().addCachesChangeListener(this);
        refresh();
    }

    public void stopVm() {
        this.mCleanFinishLiveData.postValue(false);
        XpAboutManager.getInstance().unregisterReceiver();
        XpAboutManager.getInstance().removeCachesChangeListener(this);
    }

    private synchronized void refresh() {
        StorageSize storageSize = XpAboutManager.getInstance().getStorageSize();
        long totalSize = storageSize.getTotalSize();
        long usedSize = storageSize.getUsedSize();
        long freeSize = storageSize.getFreeSize();
        Logs.d("storage refresh:" + totalSize + " " + freeSize);
        this.mStorageSettingsInfo.setTotalSize(totalSize);
        this.mStorageSettingsInfo.setUsedSize(usedSize);
        this.mStorageSettingsInfo.setFreeSize(freeSize);
        this.mStorageSettingsInfoLiveData.postValue(this.mStorageSettingsInfo);
    }

    public void getAppsInfo() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$37dKKJXeoVMtadgOZA4gDNCkcGY
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.this.lambda$getAppsInfo$0$StorageSettingsViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$getAppsInfo$0$StorageSettingsViewModel() {
        this.appStorageBeans.clear();
        List<AppStorageBean> appList = XpAboutManager.getInstance().getAppList();
        appList.add(XpAboutManager.getSysStorageBean(1));
        appList.add(XpAboutManager.getSysStorageBean(2));
        this.appStorageBeans = XpAboutManager.getInstance().fillerAppsInfo(appList);
        this.appsInfoLoaded.postValue(true);
    }

    public AppStorageBean getCacheStorageBean() {
        AppStorageBean sysStorageBean = XpAboutManager.getSysStorageBean(3);
        sysStorageBean.setTotalSize(this.allCacheSize.getValue() == null ? 0L : this.allCacheSize.getValue().longValue());
        return sysStorageBean;
    }

    @Deprecated
    public void updateAppSize(AppStorageBean appStorageBean, String str) {
        long[] appStorage = XpAboutManager.getInstance().appStorage(str);
        appStorageBean.setAppSize(appStorage[0]);
        appStorageBean.setAppData(appStorage[1]);
        appStorageBean.setTotalSize(appStorage[2]);
    }

    public void updateCleanedAppSize(AppStorageBean appStorageBean) {
        appStorageBean.setAppData(0L);
        appStorageBean.setTotalSize(appStorageBean.getAppSize());
    }

    public void updateSysAppSize(AppStorageBean appStorageBean, long j) {
        appStorageBean.setTotalSize(j);
    }

    public MutableLiveData<Boolean> isAppsInfoLoaded() {
        return this.appsInfoLoaded;
    }

    public List<AppStorageBean> getAppStorageBeans() {
        return this.appStorageBeans;
    }

    public boolean isGearP() {
        return XpAboutManager.getInstance().isGearP();
    }

    public void cleanFiles(String str, String str2) {
        XpAboutManager.getInstance().cleanFiles(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager.CachesChangeListener
    public void onCleanFinish() {
        this.mCleanFinishLiveData.postValue(true);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager.CachesChangeListener
    public void onStorageSizeChange() {
        refresh();
        this.allCacheSize.postValue(Long.valueOf(XpAboutManager.computeFiles()));
    }

    public void cleanAllCache(final StorageOptionCallBack storageOptionCallBack) {
        storageOptionCallBack.onStartAllCacheClean();
        Intent intent = new Intent(Config.ACTION_CLEAN_APP_CACHES);
        intent.setFlags(16777216);
        CarSettingsApp.getContext().sendBroadcast(intent);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$HGmXt8zqM-daxzRAYcgDfAlFx4g
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.this.lambda$cleanAllCache$1$StorageSettingsViewModel(storageOptionCallBack);
            }
        });
    }

    public /* synthetic */ void lambda$cleanAllCache$1$StorageSettingsViewModel(final StorageOptionCallBack storageOptionCallBack) {
        FileUtils.clearFiles(XpAboutManager.getAllCachedFolders(), XpAboutManager.sExcludeFileList);
        XpAboutManager.clearAppCaches();
        refresh();
        this.allCacheSize.postValue(0L);
        storageOptionCallBack.getClass();
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$DIS_8WSWLSbdQT_2-6bN7VcvSU4
            @Override // java.lang.Runnable
            public final void run() {
                StorageOptionCallBack.this.onFinishAllCacheClean();
            }
        }, 1000L);
    }

    public void computeFilesBackground() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$gqcIBBZzJBE2GabTtPYXAZtcvdw
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.this.lambda$computeFilesBackground$2$StorageSettingsViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$computeFilesBackground$2$StorageSettingsViewModel() {
        this.allCacheSize.postValue(Long.valueOf(XpAboutManager.computeFiles()));
    }

    public MutableLiveData<Long> getAllCacheSize() {
        return this.allCacheSize;
    }

    public void clearCameraBackground(final StorageOptionCallBack storageOptionCallBack) {
        storageOptionCallBack.onStartAppClean(XpAboutManager.CAMERA_APP);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$1onFX6txN7Y4Q8-sUkHVFtgj5ZA
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.lambda$clearCameraBackground$4(StorageOptionCallBack.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$clearCameraBackground$4(final StorageOptionCallBack storageOptionCallBack) {
        XpAboutManager.clearCamera();
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$7bLRNUlXWxPbzB7cJJJ9TB4dDdk
            @Override // java.lang.Runnable
            public final void run() {
                StorageOptionCallBack.this.onFinishAppClean(XpAboutManager.CAMERA_APP);
            }
        }, 1000L);
    }

    public void clearMusicBackground(final StorageOptionCallBack storageOptionCallBack) {
        storageOptionCallBack.onStartAppClean("music");
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$Pn-e5cFSO8mJb2CQX0EpEn6iZEc
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.lambda$clearMusicBackground$5(StorageOptionCallBack.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$clearMusicBackground$5(StorageOptionCallBack storageOptionCallBack) {
        XpAboutManager.clearMusic();
        XpAboutManager.clearAppUserData("com.xiaopeng.musicradio", storageOptionCallBack);
    }

    public void clearData(String str, StorageOptionCallBack storageOptionCallBack) {
        storageOptionCallBack.onStartAppClean(str);
        XpAboutManager.clearAppUserData(str, storageOptionCallBack);
    }

    public void unInstallApp(Context context, final String str, final StorageOptionCallBack storageOptionCallBack) {
        if (CarFunction.isXmartOS5()) {
            XpAboutManager.getInstance().registerUnInstallListener(new PackageIntentReceiver.UnInstallListener() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$QzyRS6KgCJhtyvixo9FbM5xhShI
                @Override // com.xiaopeng.car.settingslibrary.manager.about.PackageIntentReceiver.UnInstallListener
                public final void onUnInstalled() {
                    StorageSettingsViewModel.lambda$unInstallApp$7(str, storageOptionCallBack);
                }
            });
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Utils.ACTION_UNINSTALL_COMPLETE);
            context.registerReceiver(new AnonymousClass1(str, storageOptionCallBack), intentFilter);
        }
        storageOptionCallBack.onStartUnInstall(str);
        Utils.uninstall(context, str, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$unInstallApp$7(final String str, final StorageOptionCallBack storageOptionCallBack) {
        LogUtils.d("StorageSettingsViewModel uninstall onUnInstalled");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$Jee8SIKF_2rIz_TdJqV9KF-z440
            @Override // java.lang.Runnable
            public final void run() {
                StorageSettingsViewModel.lambda$null$6(StorageOptionCallBack.this, str);
            }
        }, 1000L);
        XpAboutManager.getInstance().unregisterUnInstallListener();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$6(StorageOptionCallBack storageOptionCallBack, String str) {
        if (storageOptionCallBack != null) {
            storageOptionCallBack.onFinishUnInstall(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 extends BroadcastReceiver {
        final /* synthetic */ String val$packageName;
        final /* synthetic */ StorageOptionCallBack val$storageOptionCallBack;

        AnonymousClass1(String str, StorageOptionCallBack storageOptionCallBack) {
            this.val$packageName = str;
            this.val$storageOptionCallBack = storageOptionCallBack;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils.d("StorageSettingsViewModel uninstall receiver" + intent.getAction());
            if (TextUtils.isEmpty(this.val$packageName) || !this.val$packageName.equals(intent.getStringExtra("android.intent.extra.PACKAGE_NAME"))) {
                return;
            }
            final StorageOptionCallBack storageOptionCallBack = this.val$storageOptionCallBack;
            final String str = this.val$packageName;
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.about.-$$Lambda$StorageSettingsViewModel$1$-J4Uq3BHnrZ_UNA8NDFgwUYriNc
                @Override // java.lang.Runnable
                public final void run() {
                    StorageSettingsViewModel.AnonymousClass1.lambda$onReceive$0(StorageOptionCallBack.this, str);
                }
            }, 1000L);
            context.unregisterReceiver(this);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onReceive$0(StorageOptionCallBack storageOptionCallBack, String str) {
            if (storageOptionCallBack != null) {
                storageOptionCallBack.onFinishUnInstall(str);
            }
        }
    }

    public void uploadDataLog(String str, int i, List<AppStorageBean> list, long j) {
        AppStorageBean sysStorageBean = XpAboutManager.getSysStorageBean(3);
        sysStorageBean.setTotalSize(j);
        StringBuilder sb = new StringBuilder();
        for (AppStorageBean appStorageBean : list) {
            sb.append(appStorageBean.toDataLogInfo());
        }
        sb.append(sysStorageBean.toDataLogInfo());
        XpAboutManager.getInstance().uploadMainData(str, i, sb.toString());
    }
}
