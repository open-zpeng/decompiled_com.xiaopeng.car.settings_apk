package com.xiaopeng.car.settingslibrary.manager.about;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.oemlock.OemLockManager;
import android.service.persistentdata.PersistentDataBlockManager;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.about.PackageIntentReceiver;
import com.xiaopeng.car.settingslibrary.manager.about.beans.OTAInfo;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.ui.activity.PopupStorageActivity;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.car.settingslibrary.vm.app.entry.StorageSize;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.protocol.query.speech.hardware.bean.StreamType;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/* loaded from: classes.dex */
public class XpAboutManager {
    public static final String CACHE_CLEAN = "cache";
    public static final String CAMERA_APP = "camera";
    public static final String MUSIC_APP = "music";
    public static final String MUSIC_PACKAGENAME = "com.xiaopeng.musicradio";
    public static final String NETEASE_MUSIC_PACKAGENAME = "com.netease.cloudmusic.iot";
    public static final long TIME = 604800000;
    private PackageIntentReceiver mPackageIntentReceiver;
    public static List<String> sExcludeFileList = Arrays.asList("/data/Log/log0", "/data/Log/ota");
    public static List<String> sComputeExcludeFileList = Arrays.asList("/data/Log/log0");
    private static List<String> sFileList = getCachedFolders();
    private boolean mEraseSdCard = false;
    private boolean mEraseEsims = false;
    private IBinder mWakeBinder = new Binder();
    private ArrayList<CachesChangeListener> mCachesChangeListeners = new ArrayList<>();
    private final Runnable mLoaderRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager.1
        @Override // java.lang.Runnable
        public void run() {
            Logs.d("storage mLoaderRunnable");
            XpAboutManager.this.notifyStorageSizeChange();
        }
    };
    private final List<OnOTAInfoChangedListener> mListeners = new ArrayList();
    private OTAVersionChangedReceiver mOTAReceiver = new OTAVersionChangedReceiver();
    private StorageManagerProvider mStorageManagerProvider = new StorageManagerProvider(CarSettingsApp.getContext());

    /* loaded from: classes.dex */
    public interface CachesChangeListener {
        void onCleanFinish();

        void onStorageSizeChange();
    }

    /* loaded from: classes.dex */
    public interface OnOTAInfoChangedListener {
        void onOTAInfoChanged(OTAInfo oTAInfo);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface SystemAppStorageBeanType {
        public static final int CACHE = 3;
        public static final int CAMERA = 2;
        public static final int MUSIC = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final XpAboutManager instance = new XpAboutManager();

        private InnerFactory() {
        }
    }

    public static XpAboutManager getInstance() {
        return InnerFactory.instance;
    }

    public static List<String> getCachedFolders() {
        ArrayList arrayList = new ArrayList();
        if (CarFunction.isEU()) {
            arrayList.add("/data/Log");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/appresources");
            arrayList.add("/data/data/com.xiaopeng.ota/app_fota/log");
            arrayList.add("/data/data/com.xiaopeng.ota/app_fota/cache");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/appresources");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/resource");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.xiaopeng.appstore/files/download");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Download");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/music");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/netradio");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/program");
            arrayList.add("/mnt/sdcard/hdmaps");
            arrayList.add("/mnt/sdcard/fullmap");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/aiassistant/cache/media");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/factoryUpload/");
            arrayList.add("/cache/qnxlog/");
            arrayList.add("/cache/tboxlog/");
            arrayList.add("/cache/avmlog/");
            arrayList.add("/cache/modemlog/");
            arrayList.add("/cache/aftersales");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.xiaopeng.appstore/files/download");
        } else {
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/appresources");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/resource");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.xiaopeng.appstore/files/download");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Download/resources");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/Download");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/music");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/netradio");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/program");
            arrayList.add("/mnt/sdcard/hdmaps");
            arrayList.add("/mnt/sdcard/fullmap");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/aiassistant/cache/media");
            arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/factoryUpload");
            arrayList.add("/cache/qnxlog");
            arrayList.add("/cache/tboxlog");
            arrayList.add("/cache/avmlog");
            arrayList.add("/cache/modemlog");
            arrayList.add("/cache/aftersales");
            arrayList.add("/data/Log");
        }
        return arrayList;
    }

    public static List<String> getAllCachedFolders() {
        new ArrayList();
        List<String> cachedFolders = getCachedFolders();
        cachedFolders.addAll(getCameraCachesFiles());
        return cachedFolders;
    }

    public static List<String> getCameraFolders() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getCameraCachesFiles());
        arrayList.addAll(getCameraUserFiles());
        return arrayList;
    }

    public static List<String> getCameraCachesFiles() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/data/data/com.xiaopeng.napa/files/gallery_thumbnail_cache");
        arrayList.add("/data/data/com.xiaopeng.xmart.camera/cache/image_manager_disk_cache");
        arrayList.add("/data/data/com.xiaopeng.xmart.cargallery/cache/image_manager_disk_cache");
        return arrayList;
    }

    public static List<String> getCameraUserFiles() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/DCIM/360Camera");
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/DCIM/shockCamera");
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/DCIM/camera_top");
        return arrayList;
    }

    public static List<String> getMusicFolders() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/music");
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/netradio");
        arrayList.add(Environment.getExternalStorageDirectory().getPath() + "/xiaopengmusic/cache/program");
        return arrayList;
    }

    public synchronized StorageSize getStorageSize() {
        return this.mStorageManagerProvider.getPrivateStorageInfo();
    }

    public List<AppStorageBean> getAppList() {
        StorageManagerProvider storageManagerProvider = this.mStorageManagerProvider;
        return StorageManagerProvider.getAppList(CarSettingsApp.getContext());
    }

    public long[] appStorage(String str) {
        StorageManagerProvider storageManagerProvider = this.mStorageManagerProvider;
        return StorageManagerProvider.appStorage(CarSettingsApp.getContext(), str);
    }

    public void registerReceiver() {
        if (this.mPackageIntentReceiver == null) {
            this.mPackageIntentReceiver = new PackageIntentReceiver();
            this.mPackageIntentReceiver.registerReceiver(CarSettingsApp.getContext(), this.mLoaderRunnable);
        }
    }

    public void registerUnInstallListener(PackageIntentReceiver.UnInstallListener unInstallListener) {
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            packageIntentReceiver.registerUnInstallListener(unInstallListener);
        } else {
            unInstallListener.onUnInstalled();
        }
    }

    public void unregisterUnInstallListener() {
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            packageIntentReceiver.unregisterUnInstallListener();
        }
    }

    public void unregisterReceiver() {
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            packageIntentReceiver.unregisterReceiver(CarSettingsApp.getContext());
            this.mPackageIntentReceiver = null;
        }
    }

    public boolean isGearP() {
        return CarSettingsManager.getInstance().isCarGearP();
    }

    public void cleanFiles(final String str, final String str2) {
        Intent intent = new Intent(Config.ACTION_CLEAN_APP_CACHES);
        intent.setFlags(16777216);
        CarSettingsApp.getContext().sendBroadcast(intent);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.about.-$$Lambda$XpAboutManager$MgtOQvoruvPIndo7GADSrHuFaO8
            @Override // java.lang.Runnable
            public final void run() {
                XpAboutManager.this.lambda$cleanFiles$0$XpAboutManager(str, str2);
            }
        });
    }

    public /* synthetic */ void lambda$cleanFiles$0$XpAboutManager(String str, String str2) {
        Logs.d("xpabout disableSpeech");
        disableSpeech(str, str2);
        deleteFiles(false);
        clearAppCaches();
        wakeupSpeech(str);
        Logs.d("xpabout wakeupSpeech");
        notifyCleanFinish();
        notifyStorageSizeChange();
    }

    public void addCachesChangeListener(CachesChangeListener cachesChangeListener) {
        if (this.mCachesChangeListeners.contains(cachesChangeListener)) {
            return;
        }
        this.mCachesChangeListeners.add(cachesChangeListener);
    }

    public void removeCachesChangeListener(CachesChangeListener cachesChangeListener) {
        this.mCachesChangeListeners.remove(cachesChangeListener);
    }

    public void notifyCleanFinish() {
        Iterator<CachesChangeListener> it = this.mCachesChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCleanFinish();
        }
    }

    public void notifyStorageSizeChange() {
        Iterator<CachesChangeListener> it = this.mCachesChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onStorageSizeChange();
        }
    }

    public void cleanCaches() {
        deleteFiles(false);
        clearAppCaches();
        notifyStorageSizeChange();
    }

    public void cleanCachesWithOutMusic(CachesChangeListener cachesChangeListener) {
        deleteFiles(true);
        clearAppCaches();
        notifyStorageSizeChange();
        if (cachesChangeListener != null) {
            cachesChangeListener.onCleanFinish();
        }
    }

    private void deleteFiles(boolean z) {
        ArrayList arrayList = new ArrayList(sExcludeFileList);
        if (z) {
            arrayList.addAll(getMusicFolders());
        }
        try {
            for (String str : sFileList) {
                File file = new File(str);
                if (file.exists()) {
                    for (File file2 : file.listFiles()) {
                        FileUtils.deleteRecursively(file2, arrayList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearAppCaches() {
        for (PackageInfo packageInfo : CarSettingsApp.getContext().getPackageManager().getInstalledPackages(0)) {
            if (!Utils.isSystemApp(CarSettingsApp.getContext(), packageInfo.packageName)) {
                FileUtils.clearApplicationCache(packageInfo.packageName);
            }
        }
    }

    public void disableSpeech(String str, String str2) {
        if (CarFunction.isNeedStopXiaoP() && SpeechClient.instance().getSpeechState().isDMStarted()) {
            SpeechClient.instance().getWakeupEngine().stopDialog();
        }
        SpeechClient.instance().getWakeupEngine().disableWakeupWithInfo(this.mWakeBinder, 1, str, str2, 2);
    }

    public void wakeupSpeech(String str) {
        SpeechClient.instance().getWakeupEngine().enableWakeupWithInfo(this.mWakeBinder, 1, str, 2);
    }

    public void restoreFactory() {
        if (Utils.isMonkeyRunning()) {
            Logs.d("xpabout monkey running!");
            return;
        }
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) CarSettingsApp.getContext().getSystemService("persistent_data_block");
        OemLockManager oemLockManager = (OemLockManager) CarSettingsApp.getContext().getSystemService("oem_lock");
        if (persistentDataBlockManager != null && !oemLockManager.isOemUnlockAllowed() && Utils.isDeviceProvisioned(CarSettingsApp.getContext())) {
            new RestoreAsyncTask(this, persistentDataBlockManager).execute(new Void[0]);
        } else {
            doMasterClear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class RestoreAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersistentDataBlockManager pdbManager;
        private final WeakReference<XpAboutManager> reference;

        public RestoreAsyncTask(XpAboutManager xpAboutManager, PersistentDataBlockManager persistentDataBlockManager) {
            this.reference = new WeakReference<>(xpAboutManager);
            this.pdbManager = persistentDataBlockManager;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            if (this.reference.get() == null) {
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            this.pdbManager.wipe();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
            XpAboutManager xpAboutManager = this.reference.get();
            if (xpAboutManager == null) {
                return;
            }
            xpAboutManager.doMasterClear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doMasterClear() {
        Intent intent = new Intent("android.intent.action.FACTORY_RESET");
        intent.setPackage("android");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", this.mEraseSdCard);
        intent.putExtra("com.android.internal.intent.extra.WIPE_ESIMS", this.mEraseEsims);
        CarSettingsApp.getContext().sendBroadcast(intent);
        Logs.d("restore system send ");
    }

    public List<AppStorageBean> fillerAppsInfo(List<AppStorageBean> list) {
        return (List) list.stream().filter(new Predicate() { // from class: com.xiaopeng.car.settingslibrary.manager.about.-$$Lambda$XpAboutManager$pPaMuPbq_0Gqx_Xi-OaTOHnTcdY
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return XpAboutManager.lambda$fillerAppsInfo$1((AppStorageBean) obj);
            }
        }).sorted(new Comparator() { // from class: com.xiaopeng.car.settingslibrary.manager.about.-$$Lambda$XpAboutManager$rGBVVdqSyIJ1NtGLu0E7GpmHczY
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Long.compare(((AppStorageBean) obj2).getTotalSize(), ((AppStorageBean) obj).getTotalSize());
                return compare;
            }
        }).collect(Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$fillerAppsInfo$1(AppStorageBean appStorageBean) {
        return !appStorageBean.getPackageName().contains("com.xiaopeng");
    }

    public static AppStorageBean getSysStorageBean(int i) {
        AppStorageBean appStorageBean;
        PackageManager packageManager = CarSettingsApp.getContext().getPackageManager();
        Drawable drawable = null;
        if (i == 1) {
            try {
                drawable = packageManager.getApplicationInfo("com.xiaopeng.musicradio", 128).loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appStorageBean = new AppStorageBean(CarSettingsApp.getContext().getString(R.string.storage_music_app), "music", FileUtils.computeFiles(getMusicFolders(), new ArrayList()));
            appStorageBean.setIcon(drawable);
        } else if (i == 2) {
            try {
                drawable = packageManager.getApplicationInfo(IpcConfig.App.CAR_CAMERA, 128).loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
            appStorageBean = new AppStorageBean(CarSettingsApp.getContext().getString(R.string.storage_camera_app), CAMERA_APP, FileUtils.computeFiles(getCameraFolders(), new ArrayList()));
        } else {
            appStorageBean = i != 3 ? null : new AppStorageBean("缓存", "cache", 0L);
        }
        appStorageBean.setIcon(drawable);
        return appStorageBean;
    }

    public static boolean isCacheClean(String str) {
        return "cache".equals(str);
    }

    public static boolean isMusicApp(String str) {
        return "music".equals(str) || "com.xiaopeng.musicradio".equals(str);
    }

    public static boolean isNeteaseMusicApp(String str) {
        if (CarFunction.isSharedMusicData()) {
            return str.contains(NETEASE_MUSIC_PACKAGENAME);
        }
        return false;
    }

    public static boolean isCameraApp(String str) {
        return CAMERA_APP.equals(str);
    }

    public static boolean isSystemApp(String str) {
        return isMusicApp(str) || isNeteaseMusicApp(str) || isCameraApp(str);
    }

    public static long computeFiles() {
        return FileUtils.computeFiles(getAllCachedFolders(), sComputeExcludeFileList);
    }

    public static long computeMusicAppSize() {
        return FileUtils.computeFiles(getMusicFolders(), new ArrayList());
    }

    public static long computeCameraAppSize() {
        return FileUtils.computeFiles(getCameraFolders(), new ArrayList());
    }

    public static void clearCamera() {
        FileUtils.clearFiles(getCameraFolders(), null);
        Intent intent = new Intent(Config.ACTION_CLEAN_CAMERA_APP_CACHES);
        intent.setFlags(16777216);
        CarSettingsApp.getContext().sendBroadcast(intent);
    }

    public static void clearMusic() {
        FileUtils.clearFiles(getMusicFolders(), null);
    }

    public static void clearAppUserData(String str, StorageOptionCallBack storageOptionCallBack) {
        FileUtils.clearApplicationData(str, storageOptionCallBack);
    }

    public static void sendAppDataLog(String str, String str2, String str3, Number number) {
        Logs.d("xpburiedpoint sendButtonDataLog:" + str + "," + str2 + "," + str3 + "," + number);
        HashMap hashMap = new HashMap();
        hashMap.put("name", str3);
        hashMap.put(BuriedPointUtils.SIZE_KEY, number);
        BuriedPointUtils.sendDataLog(BuriedPointUtils.MODULE, str, str2, hashMap);
    }

    public void uploadMainData(String str, int i, String str2) {
        HashMap hashMap = new HashMap();
        if (str != null) {
            hashMap.put(BuriedPointUtils.SOURCE_KEY, str);
        }
        hashMap.put(BuriedPointUtils.COUNT_KEY, Integer.valueOf(i));
        hashMap.put(BuriedPointUtils.APP_LIST_KEY, str2);
        BuriedPointUtils.sendDataLog(BuriedPointUtils.MODULE, BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B001", hashMap);
    }

    public void uploadDailyDatalog() {
        HashMap hashMap = new HashMap();
        List<AppStorageBean> appList = StorageManagerProvider.getAppList(CarSettingsApp.getContext());
        appList.add(getSysStorageBean(1));
        appList.add(getSysStorageBean(2));
        AppStorageBean sysStorageBean = getSysStorageBean(3);
        sysStorageBean.setTotalSize(computeFiles());
        List<AppStorageBean> fillerAppsInfo = getInstance().fillerAppsInfo(appList);
        fillerAppsInfo.add(sysStorageBean);
        StringBuilder sb = new StringBuilder();
        for (AppStorageBean appStorageBean : fillerAppsInfo) {
            sb.append(appStorageBean.toDataLogInfo());
        }
        hashMap.put(BuriedPointUtils.APP_LIST_KEY, sb.toString());
        StorageSize privateStorageInfo = new StorageManagerProvider(CarSettingsApp.getApp()).getPrivateStorageInfo();
        hashMap.put(BuriedPointUtils.COUNT_KEY, Integer.valueOf(Math.round((((float) privateStorageInfo.getUsedSize()) * 100.0f) / ((float) privateStorageInfo.getTotalSize()))));
        BuriedPointUtils.sendDataLog(BuriedPointUtils.MODULE, BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B016", hashMap);
    }

    public static boolean isFullStorage(int i) {
        StorageSize privateStorageInfo = new StorageManagerProvider(CarSettingsApp.getApp()).getPrivateStorageInfo();
        return Math.round((((float) privateStorageInfo.getUsedSize()) * 100.0f) / ((float) privateStorageInfo.getTotalSize())) >= i;
    }

    public static void pushAIStorageLow() {
        long preferenceForKeyLong = GlobalSettingsSharedPreference.getPreferenceForKeyLong(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_TIME, -1L);
        Logs.d("StoreWarningReceiver lastPushTime:" + preferenceForKeyLong + " cur:" + System.currentTimeMillis());
        if (Math.abs(System.currentTimeMillis() - preferenceForKeyLong) > TIME) {
            if (CarFunction.isXmartOS5()) {
                Utils.sendMessageToNotification(0, CarSettingsApp.getContext().getString(R.string.about_store_warning_title), CarSettingsApp.getContext().getString(R.string.about_store_warning_content), CarSettingsApp.getContext().getString(R.string.about_store_warning_content_tts), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), false, false, PopupStorageActivity.class);
            } else {
                Utils.sendMessageToMessageCenter(Config.AI_MSG_ID_FOR_STORE, CarSettingsApp.getContext().getString(R.string.about_store_warning_title), CarSettingsApp.getContext().getString(R.string.about_store_warning_content), CarSettingsApp.getContext().getString(R.string.about_store_warning_content_tts), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), "", CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), false, TIME, false);
            }
            GlobalSettingsSharedPreference.setPreferenceForKeyLong(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_TIME, System.currentTimeMillis());
            return;
        }
        Logs.d("StoreWarningReceiver push time not enough!");
    }

    public static void pushAIStorageFull() {
        if (CarFunction.isSupportAutoClean()) {
            if (CarFunction.isXmartOS5()) {
                Utils.sendMessageToNotification(0, CarSettingsApp.getContext().getString(R.string.about_store_full_title2), CarSettingsApp.getContext().getString(R.string.about_store_full_content2), CarSettingsApp.getContext().getString(R.string.about_store_full_content_tts2), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), false, true, PopupStorageActivity.class);
                return;
            } else {
                Utils.sendMessageToMessageCenter(Config.AI_MSG_ID_FOR_STORE, CarSettingsApp.getContext().getString(R.string.about_store_full_title2), CarSettingsApp.getContext().getString(R.string.about_store_full_content2), CarSettingsApp.getContext().getString(R.string.about_store_full_content_tts2), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), "", CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), false, TIME, false);
                return;
            }
        }
        Utils.sendMessageToMessageCenter(Config.AI_MSG_ID_FOR_STORE, CarSettingsApp.getContext().getString(R.string.about_store_full_title), CarSettingsApp.getContext().getString(R.string.about_store_full_content), CarSettingsApp.getContext().getString(R.string.about_store_full_content_tts), CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), "", CarSettingsApp.getContext().getString(R.string.about_store_warning_ok), false, TIME, false);
    }

    public static boolean isNeedPushStorageFull() {
        return getPushStorageFullTime() != -1;
    }

    public static void setPushStorageFullTime(long j) {
        GlobalSettingsSharedPreference.setPreferenceForKeyLong(CarSettingsApp.getContext(), Config.PUSH_STORAGE_FULL_TIME, j);
    }

    public static long getPushStorageFullTime() {
        return GlobalSettingsSharedPreference.getPreferenceForKeyLong(CarSettingsApp.getContext(), Config.PUSH_STORAGE_FULL_TIME, -1L);
    }

    public OTAInfo getOTAInfo() {
        try {
            return (OTAInfo) JsonUtils.toBean((String) ApiRouter.route(new Uri.Builder().authority("com.xiaopeng.ota.OTAService").path("getCampaignState").build()), OTAInfo.class);
        } catch (RemoteException e) {
            Logs.d("xpabout ota-info: " + e.getMessage());
            return null;
        }
    }

    public void jumpToOtaPage(String str) {
        Intent intent = new Intent("com.xiaopeng.ota.main");
        intent.setFlags(268435456);
        intent.putExtra("page", TextUtils.isEmpty(str) ? StreamType.SYSTEM : "upgrade");
        CarSettingsApp.getContext().startActivity(intent);
    }

    public void addOTAInfoChangedListener(OnOTAInfoChangedListener onOTAInfoChangedListener) {
        synchronized (this.mListeners) {
            if (this.mListeners.size() == 0) {
                this.mOTAReceiver.registerOTAVersionChanged();
            }
            if (!this.mListeners.contains(onOTAInfoChangedListener)) {
                this.mListeners.add(onOTAInfoChangedListener);
            }
        }
    }

    public void removeOnOTAInfoChangedListener(OnOTAInfoChangedListener onOTAInfoChangedListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(onOTAInfoChangedListener);
            if (this.mListeners.size() == 0) {
                this.mOTAReceiver.unregisterOTAVersionChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOTAInfoChanged(OTAInfo oTAInfo) {
        synchronized (this.mListeners) {
            for (OnOTAInfoChangedListener onOTAInfoChangedListener : this.mListeners) {
                onOTAInfoChangedListener.onOTAInfoChanged(oTAInfo);
            }
        }
    }

    /* loaded from: classes.dex */
    private class OTAVersionChangedReceiver extends BroadcastReceiver {
        private boolean isRegisterOTAReceiver;

        private OTAVersionChangedReceiver() {
            this.isRegisterOTAReceiver = false;
        }

        public void registerOTAVersionChanged() {
            if (this.isRegisterOTAReceiver) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Config.ACTION_OTA_VERSION_CHANGED);
            CarSettingsApp.getContext().registerReceiver(XpAboutManager.this.mOTAReceiver, intentFilter);
            this.isRegisterOTAReceiver = true;
        }

        public void unregisterOTAVersionChanged() {
            if (this.isRegisterOTAReceiver) {
                CarSettingsApp.getContext().unregisterReceiver(XpAboutManager.this.mOTAReceiver);
                this.isRegisterOTAReceiver = false;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Config.ACTION_OTA_VERSION_CHANGED.equals(intent.getAction())) {
                OTAInfo oTAInfo = (OTAInfo) JsonUtils.toBean(intent.getStringExtra("campaignState"), OTAInfo.class);
                Logs.d("xpabout ota info changed " + oTAInfo);
                XpAboutManager.this.notifyOTAInfoChanged(oTAInfo);
            }
        }
    }
}
