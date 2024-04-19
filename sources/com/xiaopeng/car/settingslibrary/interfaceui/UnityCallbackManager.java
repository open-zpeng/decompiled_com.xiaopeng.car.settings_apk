package com.xiaopeng.car.settingslibrary.interfaceui;
/* loaded from: classes.dex */
public class UnityCallbackManager {
    private UnityCallbackListener mUnityCallbackListener;

    /* loaded from: classes.dex */
    public interface UnityCallbackListener {
        void callback(String str, String str2, String str3);
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final UnityCallbackManager instance = new UnityCallbackManager();

        private InnerFactory() {
        }
    }

    public static UnityCallbackManager getInstance() {
        return InnerFactory.instance;
    }

    public void setUnityCallbackListener(UnityCallbackListener unityCallbackListener) {
        this.mUnityCallbackListener = unityCallbackListener;
    }

    public UnityCallbackListener getUnityCallbackListener() {
        return this.mUnityCallbackListener;
    }
}
