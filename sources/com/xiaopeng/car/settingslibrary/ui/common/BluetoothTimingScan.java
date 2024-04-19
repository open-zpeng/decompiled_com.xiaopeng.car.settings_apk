package com.xiaopeng.car.settingslibrary.ui.common;

import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
/* loaded from: classes.dex */
public class BluetoothTimingScan implements Runnable, LifecycleObserver {
    private static final String TAG = "BluetoothTimingScan";
    private static final int TIME_DELAY = 10000;
    private boolean isResume;
    private Handler mHandler = new Handler();
    private BluetoothSettingsViewModel mViewModel;

    public BluetoothTimingScan(BluetoothSettingsViewModel bluetoothSettingsViewModel) {
        this.mViewModel = bluetoothSettingsViewModel;
    }

    public void notifyScan(boolean z) {
        Logs.log(TAG, "notifyScan :" + z);
        this.mHandler.removeCallbacks(this);
        if (z || !this.isResume) {
            return;
        }
        this.mHandler.postDelayed(this, 10000L);
    }

    @Override // java.lang.Runnable
    public void run() {
        BluetoothSettingsViewModel bluetoothSettingsViewModel = this.mViewModel;
        if (bluetoothSettingsViewModel == null || !bluetoothSettingsViewModel.isEnable()) {
            return;
        }
        Logs.log(TAG, "run---startScanList");
        this.mViewModel.startScanList();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Logs.log(TAG, InterfaceConstant.DOWNLOAD_RESUME);
        this.isResume = true;
        run();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.isResume = false;
        this.mHandler.removeCallbacks(this);
        Logs.log(TAG, InterfaceConstant.DOWNLOAD_PAUSE);
    }
}
