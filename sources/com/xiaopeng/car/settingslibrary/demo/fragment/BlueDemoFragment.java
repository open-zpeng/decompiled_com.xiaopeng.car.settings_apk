package com.xiaopeng.car.settingslibrary.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ProjectorBluetoothViewModel;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* loaded from: classes.dex */
public class BlueDemoFragment extends BaseFragment {
    private static final int NEXT_TIMEOUT_BLE = 180000;
    private static final int PAIR_CONNECT_TIMEOUT_BLE = 80000;
    private static final int SCAN_TIMEOUT_BLE = 60000;
    private Handler mPairAndConnectTimeoutHandler;
    private Handler mScanTimeoutHandler;
    ProjectorBluetoothViewModel mViewModel;
    XDialog xDialog;
    Handler mToNextTimeoutHandler = new Handler();
    Runnable mToNextRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$BlueDemoFragment$j1NSCi16ShdVB8tSfZcY2pvIV5I
        @Override // java.lang.Runnable
        public final void run() {
            BlueDemoFragment.this.toNext();
        }
    };
    private Runnable mScanTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$BlueDemoFragment$PRdBvppQljKNRwektJSCItrfiZ8
        @Override // java.lang.Runnable
        public final void run() {
            BlueDemoFragment.this.searchTimeout();
        }
    };
    private Runnable mPairAndConnectTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$BlueDemoFragment$468Mp-7MwRRunTqK407qVTKd6-4
        @Override // java.lang.Runnable
        public final void run() {
            BlueDemoFragment.this.pairAndConnectTimeout();
        }
    };
    private boolean mIsConnectTtsPlayed = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void searchTimeout() {
        this.mViewModel.stopScanProjector();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pairAndConnectTimeout() {
        this.mViewModel.stopPairOrConnectProjector();
    }

    private void startScanTimeout() {
        if (this.mScanTimeoutHandler == null) {
            this.mScanTimeoutHandler = new Handler();
        }
        this.mScanTimeoutHandler.removeCallbacks(this.mScanTimeoutRunnable);
        this.mScanTimeoutHandler.postDelayed(this.mScanTimeoutRunnable, 60000L);
    }

    private void cancelScanTimeout() {
        Handler handler = this.mScanTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mScanTimeoutRunnable);
        }
        this.mViewModel.stopScanProjector();
    }

    private void cancelNextTimeout() {
        Handler handler = this.mToNextTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mToNextRunnable);
        }
    }

    private void startPairAndConnectTimeout() {
        if (this.mPairAndConnectTimeoutHandler == null) {
            this.mPairAndConnectTimeoutHandler = new Handler();
        }
        this.mPairAndConnectTimeoutHandler.removeCallbacks(this.mPairAndConnectTimeoutRunnable);
        this.mPairAndConnectTimeoutHandler.postDelayed(this.mPairAndConnectTimeoutRunnable, 80000L);
    }

    private void cancelPairAndConnectTimeout() {
        Handler handler = this.mPairAndConnectTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mPairAndConnectTimeoutRunnable);
        }
        this.mViewModel.stopPairOrConnectProjector();
    }

    private void manual() {
        XDialog xDialog = this.xDialog;
        if (xDialog != null) {
            xDialog.setMessage("蓝牙自动连接失败，请手动连接投影仪");
            SpeechClient.instance().getTTSEngine().speak("蓝牙自动连接失败，请手动连接投影仪");
            this.xDialog.setPositiveButtonEnable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toNext() {
        this.mViewModel.stopScanProjector();
        showDialog("");
        SpeechClient.instance().getTTSEngine().speak("投影仪开启后，请进⼊下⼀步");
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_blue_demo;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.BlueDemoFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BlueDemoFragment.this.mViewModel.setProjectorBluetoothDoing(true);
                BlueDemoFragment.this.mViewModel.registerProjectorStateCallback();
                BlueDemoFragment.this.mViewModel.startScanProjector();
                BlueDemoFragment.this.mToNextTimeoutHandler.removeCallbacks(BlueDemoFragment.this.mToNextRunnable);
                BlueDemoFragment.this.mToNextTimeoutHandler.postDelayed(BlueDemoFragment.this.mToNextRunnable, 180000L);
            }
        });
        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.BlueDemoFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BlueDemoFragment.this.showDialog("");
                BlueDemoFragment.this.mToNextTimeoutHandler.removeCallbacks(BlueDemoFragment.this.mToNextRunnable);
            }
        });
    }

    public void showDialog(String str) {
        this.xDialog = new XDialog(getContext());
        this.xDialog.setTitle("蓝牙状态");
        this.xDialog.setPositiveButton("手动连接入口", new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.BlueDemoFragment.3
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public void onClick(XDialog xDialog, int i) {
                Intent intent = new Intent(Config.POPUP_BLUETOOTH);
                intent.setFlags(268435456);
                BlueDemoFragment.this.getContext().startActivity(intent);
            }
        });
        this.xDialog.setPositiveButtonEnable(false);
        this.xDialog.show();
        if (TextUtils.isEmpty(str)) {
            this.xDialog.setMessage("蓝牙信号搜索中");
            SpeechClient.instance().getTTSEngine().speak("正在搜索蓝牙信号，请保证投影仪已开启");
            this.mViewModel.startScanProjector();
            startScanTimeout();
            return;
        }
        this.xDialog.setMessage(str);
        this.mViewModel.startPairOrConnectProjector();
        startPairAndConnectTimeout();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mViewModel = (ProjectorBluetoothViewModel) ViewModelUtils.getViewModel(this, ProjectorBluetoothViewModel.class);
        this.mViewModel.getProjectStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$BlueDemoFragment$FcqE3hgJs8Wsa8xYQKMAhosCSfI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BlueDemoFragment.this.lambda$init$0$BlueDemoFragment((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$BlueDemoFragment(Integer num) {
        Logs.d("bluedemo projector status:" + num);
        if (num.intValue() == 4) {
            showDialog("发现投影仪");
            cancelNextTimeout();
            cancelScanTimeout();
        } else if (num.intValue() == 5) {
            this.xDialog.setMessage("蓝牙配对中，请在投影仪确认配对");
            SpeechClient.instance().getTTSEngine().speak("蓝牙配对中，请在投影仪确认配对");
            cancelScanTimeout();
        } else if (num.intValue() == 8) {
            XDialog xDialog = this.xDialog;
            if (xDialog != null) {
                xDialog.setMessage("已连接投影仪");
            }
            cancelPairAndConnectTimeout();
            cancelScanTimeout();
            SpeechClient.instance().getTTSEngine().speak("已经连接投影仪，开始使用座舱音响");
        } else if (num.intValue() == 7 || num.intValue() == 6) {
            if (!this.mIsConnectTtsPlayed && this.mViewModel.isProjectorAlreadyBonded()) {
                this.mIsConnectTtsPlayed = true;
                SpeechClient.instance().getTTSEngine().speak("正在连接投影仪");
                XDialog xDialog2 = this.xDialog;
                if (xDialog2 != null) {
                    xDialog2.setMessage("正在连接投影仪");
                }
            }
            cancelScanTimeout();
            this.mViewModel.stopScanProjector();
        } else if (num.intValue() == 1 || num.intValue() == 2 || num.intValue() == 3) {
            manual();
            cancelPairAndConnectTimeout();
            cancelScanTimeout();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        enter();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            leave();
        } else {
            enter();
        }
    }

    private void leave() {
        this.mViewModel.setProjectorBluetoothDoing(false);
        this.mViewModel.unregisterProjectorStateCallback();
        cancelPairAndConnectTimeout();
        cancelScanTimeout();
        cancelNextTimeout();
    }

    private void enter() {
        this.mViewModel.reqBtDevicePairedDevices();
        if (this.mViewModel.isProjectorAlreadyConnected()) {
            SpeechClient.instance().getTTSEngine().speak("已连接投影仪");
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        leave();
    }
}
