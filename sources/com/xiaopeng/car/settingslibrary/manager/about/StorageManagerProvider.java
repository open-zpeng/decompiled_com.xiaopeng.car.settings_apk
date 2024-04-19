package com.xiaopeng.car.settingslibrary.manager.about;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Process;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.car.settingslibrary.vm.app.entry.StorageSize;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class StorageManagerProvider {
    private static final String TAG = "StorageManagerProvider";
    private VolumeInfo mCurrentVolumeInfo;
    private StorageManager mStorageManager;
    private StorageStatsManager mStorageStatsManager;

    public StorageManagerProvider(Context context) {
        this.mStorageManager = (StorageManager) context.getSystemService(StorageManager.class);
        this.mStorageStatsManager = (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
    }

    public long getPrimaryStorageSize() {
        return this.mStorageManager.getPrimaryStorageSize();
    }

    public VolumeInfo findEmulatedForPrivate(VolumeInfo volumeInfo) {
        return this.mStorageManager.findEmulatedForPrivate(volumeInfo);
    }

    public long getTotalBytes(StorageStatsManager storageStatsManager, VolumeInfo volumeInfo) throws IOException {
        return storageStatsManager.getTotalBytes(StorageManager.convert(volumeInfo.getFsUuid()));
    }

    public long getFreeBytes(StorageStatsManager storageStatsManager, VolumeInfo volumeInfo) throws IOException {
        return storageStatsManager.getFreeBytes(StorageManager.convert(volumeInfo.getFsUuid()));
    }

    public void registerListener(StorageEventListener storageEventListener) {
        this.mStorageManager.registerListener(storageEventListener);
    }

    public void unregisterListener(StorageEventListener storageEventListener) {
        this.mStorageManager.unregisterListener(storageEventListener);
    }

    public StorageSize getPrivateStorageInfo() {
        long totalBytes = getTotalBytes();
        long j = 0;
        for (VolumeInfo volumeInfo : this.mStorageManager.getVolumes()) {
            LogUtils.d("xpstorage info:" + volumeInfo);
            if (volumeInfo.getType() == 1 && volumeInfo.isMountedReadable()) {
                this.mCurrentVolumeInfo = volumeInfo;
                try {
                    j += getFreeBytes(this.mStorageStatsManager, volumeInfo);
                } catch (IOException e) {
                    Log.w(TAG, e);
                }
            }
        }
        return new StorageSize(totalBytes, j);
    }

    private static long getTotalBytes() {
        if (CarFunction.isSupportStorageManage()) {
            return Environment.getDataDirectory().getTotalSpace();
        }
        return Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace();
    }

    public static long[] appStorage(Context context, String str) {
        try {
            StorageStats queryStatsForPackage = ((StorageStatsManager) context.getSystemService("storagestats")).queryStatsForPackage(StorageManager.UUID_DEFAULT, str, Process.myUserHandle());
            return new long[]{queryStatsForPackage.getAppBytes(), queryStatsForPackage.getCacheBytes() + queryStatsForPackage.getDataBytes(), queryStatsForPackage.getAppBytes() + queryStatsForPackage.getCacheBytes() + queryStatsForPackage.getDataBytes()};
        } catch (Exception e) {
            e.printStackTrace();
            return new long[]{0, 0, 0};
        }
    }

    public static List<AppStorageBean> getAppList(Context context) {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 131072);
        for (int i = 0; i < queryIntentActivities.size(); i++) {
            String str = queryIntentActivities.get(i).getComponentInfo().packageName;
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
                String charSequence = packageManager.getApplicationLabel(applicationInfo).toString();
                Drawable loadIcon = applicationInfo.loadIcon(packageManager);
                long[] appStorage = appStorage(context, str);
                AppStorageBean appStorageBean = new AppStorageBean();
                appStorageBean.setPackageName(str);
                appStorageBean.setName(charSequence);
                appStorageBean.setAppSize(appStorage[0]);
                appStorageBean.setAppData(appStorage[1]);
                appStorageBean.setTotalSize(appStorage[2]);
                appStorageBean.setIcon(loadIcon);
                arrayList.add(appStorageBean);
                Log.d(TAG, "getAllAppInfo: " + charSequence + "pkg: " + str);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public List<VolumeInfo> getPublicVolumeInfos() {
        ArrayList arrayList = new ArrayList();
        for (VolumeInfo volumeInfo : this.mStorageManager.getVolumes()) {
            if (volumeInfo.getType() == 0 && volumeInfo.isMountedReadable()) {
                arrayList.add(volumeInfo);
            }
        }
        return arrayList;
    }

    public VolumeInfo getCurrentVolumeInfo() {
        return this.mCurrentVolumeInfo;
    }

    public long getTotalSize(VolumeInfo volumeInfo, long j) {
        try {
            return this.mStorageStatsManager.getTotalBytes(volumeInfo.getFsUuid());
        } catch (IOException e) {
            Log.w(TAG, e);
            return 0L;
        }
    }

    public StorageSize getVolumeStorageInfo(VolumeInfo volumeInfo) {
        long j;
        long j2;
        long j3 = 0;
        if (volumeInfo.isMountedReadable()) {
            File path = volumeInfo.getPath();
            if (volumeInfo.getType() == 1) {
                try {
                    j2 = this.mStorageStatsManager.getTotalBytes(volumeInfo.getFsUuid());
                } catch (IOException e) {
                    e = e;
                    j2 = 0;
                }
                try {
                    j3 = this.mStorageStatsManager.getFreeBytes(volumeInfo.getFsUuid());
                } catch (IOException e2) {
                    e = e2;
                    Logs.d("xpsdstorage getSDSize " + e);
                    long j4 = j3;
                    j3 = j2;
                    j = j4;
                    return new StorageSize(j3, j);
                }
                long j42 = j3;
                j3 = j2;
                j = j42;
            } else {
                j3 = path.getTotalSpace();
                j = path.getFreeSpace();
            }
        } else {
            j = 0;
        }
        return new StorageSize(j3, j);
    }

    public StorageManager getStorageManager() {
        return this.mStorageManager;
    }

    public String getBestVolumeDescription(VolumeInfo volumeInfo) {
        return this.mStorageManager.getBestVolumeDescription(volumeInfo);
    }
}
