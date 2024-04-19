package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
/* loaded from: classes.dex */
public abstract class ServerBaseManager implements LifecycleOwner {
    private static final String TAG = ServerBaseManager.class.getSimpleName();
    protected boolean DEBUG = true;
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    protected int mEnterCount = 0;

    protected abstract void init();

    protected abstract void observeData();

    protected abstract void startVm();

    protected abstract void stopVm();

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public void enterScene() {
        this.mEnterCount++;
        Logs.d(TAG + "unity 3d enterScene " + this.mEnterCount + " " + getClass().getSimpleName());
        if (this.mEnterCount > 1) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager.1
            @Override // java.lang.Runnable
            public void run() {
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                ServerBaseManager.this.init();
                ServerBaseManager.this.observeData();
                ServerBaseManager.this.startVm();
            }
        });
    }

    public void exitScene() {
        int i = this.mEnterCount;
        if (i == 0) {
            return;
        }
        this.mEnterCount = i - 1;
        Logs.d(TAG + "unity 3d exitScene " + this.mEnterCount + " " + getClass().getSimpleName());
        if (this.mEnterCount > 0) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager.2
            @Override // java.lang.Runnable
            public void run() {
                ServerBaseManager.this.stopVm();
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                ServerBaseManager.this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            }
        });
    }

    public void log(String str) {
        Logs.d(str);
    }

    public void debugLog(String str) {
        if (this.DEBUG) {
            Logs.d(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void appCallback(String str, String str2) {
        callback(InterfaceConstant.APP_MODULE, str, str2);
    }

    public void bluetoothCallback(String str, String str2) {
        callback(InterfaceConstant.BLUETOOTH_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void wifiCallback(String str, String str2) {
        callback(InterfaceConstant.WIFI_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void soundCallback(String str, String str2) {
        callback(InterfaceConstant.SOUND_MODULE, str, str2);
    }

    public void displayCallback(String str, String str2) {
        callback(InterfaceConstant.DISPLAY_MODULE, str, str2);
    }

    public void feedbackCallback(String str, String str2) {
        callback(InterfaceConstant.FEEDBACK_MODULE, str, str2);
    }

    public void laboratoryCallback(String str, String str2) {
        callback(InterfaceConstant.LABORATORY_MODULE, str, str2);
    }

    public void aboutCallback(String str, String str2) {
        callback(InterfaceConstant.ABOUT_MODULE, str, str2);
    }

    public void downloadCallback(String str, String str2) {
        callback(InterfaceConstant.DOWNLOAD_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void karaokeCallback(String str, String str2) {
        callback(InterfaceConstant.KARAOKE_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void projectorCallback(String str, String str2) {
        callback(InterfaceConstant.PROJECTOR_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void usbCallback(String str, String str2) {
        callback(InterfaceConstant.USB_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void psnBluetoothCallback(String str, String str2) {
        callback(InterfaceConstant.PSN_BLUETOOTH_MODULE, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void externalBluetoothCallback(String str, String str2) {
        callback(InterfaceConstant.EXTERNAL_BLUETOOTH_MODULE, str, str2);
    }

    void callback(String str, String str2, String str3) {
        Logs.d(TAG + " onCallback module:" + str + " method:" + str2 + " param:" + str3);
        if (UnityCallbackManager.getInstance().getUnityCallbackListener() != null) {
            UnityCallbackManager.getInstance().getUnityCallbackListener().callback(str, str2, str3);
        }
    }
}
