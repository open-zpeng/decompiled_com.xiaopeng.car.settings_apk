package com.xiaopeng.car.settingslibrary.manager.about;
/* loaded from: classes.dex */
public interface StorageOptionCallBack {
    void onFinishAllCacheClean();

    void onFinishAppClean(String str);

    void onFinishUnInstall(String str);

    void onStartAllCacheClean();

    void onStartAppClean(String str);

    void onStartUnInstall(String str);
}
