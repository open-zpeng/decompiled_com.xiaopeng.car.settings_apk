package com.xiaopeng.car.settingslibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.adapter.ExternalBluetoothAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.ui.common.ExternalBluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.view.ExternalBluetoothView;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class ExternalBluetoothView extends BaseListView implements IBluetoothViewCallback {
    public static final String TAG = "ExternalBluetoothView";
    private ExternalBluetoothAdapter externalBluetoothAdapter;
    private ExternalBluetoothOperation externalBluetoothOperation;
    private ExternalBluetoothViewModel externalBluetoothViewModel;
    private XSwitch mBlueSwitch;
    private XListSingle mExternalTtsList;
    private XSwitch mExternalTtsSwitch;
    private HeaderView mHeaderView;
    private boolean mIsRegisterFresh;

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(boolean z) {
    }

    public ExternalBluetoothView(Context context) {
        super(context);
        this.mIsRegisterFresh = false;
    }

    public ExternalBluetoothView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsRegisterFresh = false;
    }

    public ExternalBluetoothView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsRegisterFresh = false;
    }

    public ExternalBluetoothView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsRegisterFresh = false;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void init(Context context, View view) {
        this.sceneId = VuiManager.SCENE_EXTERNAL_BLUETOOTH_PUBLIC_DIALOG;
        this.externalBluetoothOperation = new ExternalBluetoothOperation(context);
        this.externalBluetoothAdapter = new ExternalBluetoothAdapter(this.externalBluetoothOperation);
        initVm();
    }

    public void createView() {
        this.mHeaderView = new HeaderView();
        this.externalBluetoothAdapter.setHeaderView(this.mHeaderView.initView());
        this.mRecyclerView.setAdapter(this.externalBluetoothAdapter);
        this.mRecyclerView.initVuiAttr(this.sceneId, VuiEngine.getInstance(CarSettingsApp.getContext()), true);
        this.mHeaderView.init();
        super.paddingRecyclerView();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        refreshBluetoothData();
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$fn7Cz2rClcBBfgjDIJ0VwDVWrkc
            @Override // java.lang.Runnable
            public final void run() {
                ExternalBluetoothView.this.lambda$start$0$ExternalBluetoothView();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$start$0$ExternalBluetoothView() {
        this.externalBluetoothAdapter.updateAllBusyStatusAndRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        unRefreshBluetoothData();
        this.externalBluetoothOperation.dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
        this.sceneId = str;
    }

    public void onConfigurationChanged() {
        this.externalBluetoothAdapter.notifyDataSetChanged();
        this.externalBluetoothOperation.cleanDialog();
    }

    public void dismissDialog() {
        ExternalBluetoothOperation externalBluetoothOperation = this.externalBluetoothOperation;
        if (externalBluetoothOperation != null) {
            externalBluetoothOperation.dismissDialog();
        }
    }

    public void refreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            return;
        }
        this.externalBluetoothViewModel.registerErrorCallback(this);
        this.externalBluetoothViewModel.onStartVM();
        this.mIsRegisterFresh = true;
        Logs.d("ExternalBluetoothViewxpExternalBluetooth fragment fresh refreshBluetoothData ");
    }

    private void unRefreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            this.externalBluetoothViewModel.unregisterErrorCallback(this);
            this.externalBluetoothAdapter.cancelPairConnectTimeout();
            this.externalBluetoothViewModel.onStopVM();
            this.mIsRegisterFresh = false;
            Logs.d("ExternalBluetoothViewxpExternalBluetooth fragment fresh unRefreshBluetoothData ");
        }
    }

    private void initVm() {
        this.externalBluetoothViewModel = new ExternalBluetoothViewModel(CarSettingsApp.getApp());
        this.externalBluetoothOperation.setViewModel(this.externalBluetoothViewModel);
        this.externalBluetoothAdapter.setViewModel(this.externalBluetoothViewModel);
        this.externalBluetoothViewModel.getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$d85-FLLe0nePwU2HTX57CgAZSs8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothView.this.lambda$initVm$1$ExternalBluetoothView((Boolean) obj);
            }
        });
        this.externalBluetoothViewModel.getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$q7cK7EYwqoVEEElaiSrUUS3GPxg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothView.this.lambda$initVm$2$ExternalBluetoothView((Boolean) obj);
            }
        });
        this.externalBluetoothViewModel.getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$okQReHZbyMgp-43BkVdBKJb15-k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothView.this.lambda$initVm$3$ExternalBluetoothView((ExternalBluetoothDeviceInfo) obj);
            }
        });
        this.externalBluetoothViewModel.getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$lf66JF0ktywf7t8-1QszlhRFxrY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothView.this.lambda$initVm$4$ExternalBluetoothView((Boolean) obj);
            }
        });
        this.externalBluetoothViewModel.getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$w4JNJHO0RCF-IvTdljwRpi6Cv9c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ExternalBluetoothView.this.lambda$initVm$5$ExternalBluetoothView((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initVm$1$ExternalBluetoothView(Boolean bool) {
        this.externalBluetoothAdapter.setScanning(bool.booleanValue());
    }

    public /* synthetic */ void lambda$initVm$2$ExternalBluetoothView(Boolean bool) {
        this.externalBluetoothAdapter.onRefreshData();
    }

    public /* synthetic */ void lambda$initVm$3$ExternalBluetoothView(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        this.externalBluetoothAdapter.refreshItemChange(externalBluetoothDeviceInfo);
    }

    public /* synthetic */ void lambda$initVm$4$ExternalBluetoothView(Boolean bool) {
        this.mRecyclerView.scrollToPosition(0);
    }

    public /* synthetic */ void lambda$initVm$5$ExternalBluetoothView(Integer num) {
        switch (num.intValue()) {
            case 10:
                this.mBlueSwitch.setChecked(false);
                this.mBlueSwitch.setEnabled(true);
                this.externalBluetoothAdapter.setScanning(false);
                this.externalBluetoothOperation.dismissDialog();
                this.externalBluetoothAdapter.setClickingBt(false);
                return;
            case 11:
                this.mBlueSwitch.setChecked(true);
                this.mBlueSwitch.setEnabled(false);
                return;
            case 12:
                this.mBlueSwitch.setChecked(true);
                this.mBlueSwitch.setEnabled(true);
                this.externalBluetoothAdapter.setClickingBt(false);
                return;
            case 13:
                this.mBlueSwitch.setEnabled(false);
                this.mBlueSwitch.setChecked(false);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectError(String str, String str2) {
        this.externalBluetoothOperation.showConnectError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        this.externalBluetoothOperation.showBondError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        this.externalBluetoothOperation.showConnectOperateError(str);
    }

    public void setDialog(boolean z) {
        this.mIsDialog = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class HeaderView {
        private HeaderView() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public View initView() {
            View headerOrFooterView = ExternalBluetoothView.this.getHeaderOrFooterView(R.layout.external_bluetooth_recycler_header);
            ExternalBluetoothView.this.mBlueSwitch = (XSwitch) ((XListSingle) headerOrFooterView.findViewById(R.id.external_bluetooth_switch)).findViewById(R.id.x_list_action_switch);
            ExternalBluetoothView.this.mBlueSwitch.setChecked(ExternalBluetoothView.this.externalBluetoothViewModel.isUsbEnable());
            ExternalBluetoothView.this.mExternalTtsList = (XListSingle) headerOrFooterView.findViewById(R.id.external_tts_switch);
            ExternalBluetoothView externalBluetoothView = ExternalBluetoothView.this;
            externalBluetoothView.mExternalTtsSwitch = (XSwitch) externalBluetoothView.mExternalTtsList.findViewById(R.id.x_list_action_switch);
            return headerOrFooterView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            ExternalBluetoothView.this.mBlueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$HeaderView$zq_mmE76VV_AR1X3t8Ae6T4RL5k
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ExternalBluetoothView.HeaderView.this.lambda$init$0$ExternalBluetoothView$HeaderView(compoundButton, z);
                }
            });
            ExternalBluetoothView.this.mExternalTtsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$ExternalBluetoothView$HeaderView$J-OcbRmKHxhC_qcIJOyO5CUUw9o
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ExternalBluetoothView.HeaderView.this.lambda$init$1$ExternalBluetoothView$HeaderView(compoundButton, z);
                }
            });
        }

        public /* synthetic */ void lambda$init$0$ExternalBluetoothView$HeaderView(CompoundButton compoundButton, boolean z) {
            if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
                ExternalBluetoothView.this.mBlueSwitch.setEnabled(false);
                ExternalBluetoothView.this.externalBluetoothAdapter.setClickingBt(true);
                ExternalBluetoothView.this.externalBluetoothViewModel.setUsbEnable(z);
                if (!z) {
                    ExternalBluetoothView.this.externalBluetoothAdapter.onRefreshData();
                }
            }
            ExternalBluetoothView externalBluetoothView = ExternalBluetoothView.this;
            externalBluetoothView.updateVuiScene(externalBluetoothView.sceneId, ExternalBluetoothView.this.mRecyclerView);
        }

        public /* synthetic */ void lambda$init$1$ExternalBluetoothView$HeaderView(CompoundButton compoundButton, boolean z) {
            ExternalBluetoothView externalBluetoothView = ExternalBluetoothView.this;
            externalBluetoothView.updateVuiScene(externalBluetoothView.sceneId, ExternalBluetoothView.this.mExternalTtsSwitch);
            if (((XSwitch) compoundButton).isFromUser()) {
                ExternalBluetoothView.this.externalBluetoothViewModel.setExternalTtsHeadsetOut(z);
            }
        }
    }
}
