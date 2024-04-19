package com.xiaopeng.car.settingslibrary.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
/* loaded from: classes.dex */
public class FileUtils {

    /* loaded from: classes.dex */
    public interface ICallback<T, K> {
        void onFail(K k);

        void onSuccess(T t);
    }

    public static void readAssertResource(final Context context, final String str, final ICallback<SpannableString, String> iCallback) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.common.utils.FileUtils.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final SpannableString stringFromInputStream = FileUtils.getStringFromInputStream(context.getAssets().open(str));
                    ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.common.utils.FileUtils.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            iCallback.onSuccess(stringFromInputStream);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003a, code lost:
        if (r2 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.text.SpannableString getStringFromInputStream(java.io.InputStream r10) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L39
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L39
            r3.<init>(r10)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L39
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L39
        L10:
            java.lang.String r10 = r2.readLine()     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            if (r10 == 0) goto L2b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            r1.<init>()     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            r1.append(r10)     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            java.lang.String r10 = "\n"
            r1.append(r10)     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            java.lang.String r10 = r1.toString()     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            r0.append(r10)     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L3a
            goto L10
        L2b:
            r2.close()     // Catch: java.io.IOException -> L3d
            goto L3d
        L2f:
            r10 = move-exception
            goto L33
        L31:
            r10 = move-exception
            r2 = r1
        L33:
            if (r2 == 0) goto L38
            r2.close()     // Catch: java.io.IOException -> L38
        L38:
            throw r10
        L39:
            r2 = r1
        L3a:
            if (r2 == 0) goto L3d
            goto L2b
        L3d:
            java.lang.String r10 = "<b>"
            java.lang.String r1 = "</b>"
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = ""
            java.lang.String r3 = r0.replace(r10, r2)
            java.lang.String r2 = r3.replace(r1, r2)
            android.text.SpannableString r3 = new android.text.SpannableString
            r3.<init>(r2)
            int r2 = r0.indexOf(r10)
            r4 = 0
        L59:
            r5 = -1
            if (r2 == r5) goto L81
            int r5 = r4 * 7
            int r5 = r2 - r5
            android.text.style.StyleSpan r6 = new android.text.style.StyleSpan
            r7 = 1
            r6.<init>(r7)
            int r7 = r5 + 1
            int r7 = r0.indexOf(r1, r7)
            int r8 = r4 + 1
            int r9 = r8 * 3
            int r7 = r7 - r9
            int r4 = r4 * 4
            int r7 = r7 - r4
            r4 = 33
            r3.setSpan(r6, r5, r7, r4)
            int r2 = r2 + 1
            int r2 = r0.indexOf(r10, r2)
            r4 = r8
            goto L59
        L81:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.common.utils.FileUtils.getStringFromInputStream(java.io.InputStream):android.text.SpannableString");
    }

    public static void deleteRecursively(File file, List<String> list) {
        if (!TextUtils.isEmpty(file.getPath()) && list != null && list.contains(file.getPath())) {
            Logs.d("deleteRecursively no delete file:" + file.getPath());
            return;
        }
        if (file.isDirectory()) {
            try {
                for (File file2 : file.listFiles()) {
                    deleteRecursively(file2, list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Logs.d("deleteRecursively delete file:" + file + " success:" + file.delete());
    }

    public static long computeFiles(List<String> list, List<String> list2) {
        long j = 0;
        try {
            for (String str : list) {
                File file = new File(str);
                if (file.exists()) {
                    j += computeRecursively(file, list2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    public static long computeRecursively(File file, List<String> list) {
        File[] listFiles;
        long fileSize;
        long j = 0;
        if (!TextUtils.isEmpty(file.getPath()) && list.contains(file.getPath())) {
            Logs.d("computeRecursively no compute file:" + file.getPath());
            return 0L;
        }
        Logs.d("computeRecursively file:" + file);
        if (!file.exists()) {
            Logs.d("computeRecursively no file:" + file);
        } else if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    fileSize = computeRecursively(file2, list);
                } else {
                    fileSize = getFileSize(file2);
                }
                j += fileSize;
            }
        } else {
            j = 0 + getFileSize(file);
        }
        Logs.d("computeRecursively size:" + j + " - computeRecursively file:" + file);
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
        if (0 == 0) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0033, code lost:
        if (r1 != null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static long getFileSize(java.io.File r6) {
        /*
            java.lang.String r0 = "getFileSize IOException"
            r1 = 0
            r2 = 0
            boolean r4 = r6.exists()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            if (r4 == 0) goto L1f
            boolean r4 = r6.isFile()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            if (r4 == 0) goto L1f
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            java.nio.channels.FileChannel r1 = r4.getChannel()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            long r2 = r1.size()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            goto L33
        L1f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            r4.<init>()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            java.lang.String r5 = "getFileSize file doesn't exist or is not a file "
            r4.append(r5)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            r4.append(r6)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r6)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L45
        L33:
            if (r1 == 0) goto L4d
        L35:
            r1.close()     // Catch: java.io.IOException -> L39
            goto L4d
        L39:
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)
            goto L4d
        L3d:
            r6 = move-exception
            goto L4e
        L3f:
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)     // Catch: java.lang.Throwable -> L3d
            if (r1 == 0) goto L4d
            goto L35
        L45:
            java.lang.String r6 = "getFileSize FileNotFoundException"
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r6)     // Catch: java.lang.Throwable -> L3d
            if (r1 == 0) goto L4d
            goto L35
        L4d:
            return r2
        L4e:
            if (r1 == 0) goto L57
            r1.close()     // Catch: java.io.IOException -> L54
            goto L57
        L54:
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r0)
        L57:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.common.utils.FileUtils.getFileSize(java.io.File):long");
    }

    public static long getFileLength(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        Logs.d("getFileSize file doesn't exist or is not a file");
        return 0L;
    }

    /* renamed from: com.xiaopeng.car.settingslibrary.common.utils.FileUtils$2  reason: invalid class name */
    /* loaded from: classes.dex */
    static class AnonymousClass2 extends IPackageDataObserver.Stub {
        final /* synthetic */ StorageOptionCallBack val$storageOptionCallBack;

        AnonymousClass2(StorageOptionCallBack storageOptionCallBack) {
            this.val$storageOptionCallBack = storageOptionCallBack;
        }

        public void onRemoveCompleted(final String str, boolean z) throws RemoteException {
            Logs.d("clearApplicationData onRemoveCompleted:" + str + " succeeded:" + z);
            final StorageOptionCallBack storageOptionCallBack = this.val$storageOptionCallBack;
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.common.utils.-$$Lambda$FileUtils$2$gulpJwiK6v-LG-Rn3gzYHOkAxsk
                @Override // java.lang.Runnable
                public final void run() {
                    FileUtils.AnonymousClass2.lambda$onRemoveCompleted$0(StorageOptionCallBack.this, str);
                }
            }, 1000L);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onRemoveCompleted$0(StorageOptionCallBack storageOptionCallBack, String str) {
            if (storageOptionCallBack != null) {
                storageOptionCallBack.onFinishAppClean(str);
            }
        }
    }

    public static void clearApplicationData(String str, StorageOptionCallBack storageOptionCallBack) {
        ((ActivityManager) CarSettingsApp.getContext().getSystemService(XuiSettingsManager.USER_SCENARIO_SOURCE_ACTIVITY)).clearApplicationUserData(str, new AnonymousClass2(storageOptionCallBack));
    }

    public static void clearApplicationCache(String str) {
        CarSettingsApp.getContext().getPackageManager().deleteApplicationCacheFiles(str, new IPackageDataObserver.Stub() { // from class: com.xiaopeng.car.settingslibrary.common.utils.FileUtils.3
            public void onRemoveCompleted(String str2, boolean z) throws RemoteException {
                Logs.d("clearApplicationCache onRemoveCompleted:" + str2 + " succeeded:" + z);
            }
        });
    }

    public static void clearFiles(List<String> list, List<String> list2) {
        try {
            for (String str : list) {
                File file = new File(str);
                if (file.exists()) {
                    for (File file2 : file.listFiles()) {
                        deleteRecursively(file2, list2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileSizeDescription(long j) {
        StringBuffer stringBuffer = new StringBuffer();
        DecimalFormat decimalFormat = new DecimalFormat("###.0");
        if (j >= com.xiaopeng.lib.utils.FileUtils.SIZE_1GB) {
            stringBuffer.append(decimalFormat.format(j / 1.073741824E9d));
            stringBuffer.append("GB");
        } else if (j >= 1048576) {
            stringBuffer.append(decimalFormat.format(j / 1048576.0d));
            stringBuffer.append("MB");
        } else {
            int i = (j > 1024L ? 1 : (j == 1024L ? 0 : -1));
            if (i >= 0) {
                stringBuffer.append(decimalFormat.format(j / 1024.0d));
                stringBuffer.append("KB");
            } else if (i < 0) {
                if (j <= 0) {
                    stringBuffer.append("0B");
                } else {
                    stringBuffer.append((int) j);
                    stringBuffer.append("B");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static long parseByte2KB(long j) {
        if (j <= 0) {
            return 0L;
        }
        return j / 1024;
    }
}
