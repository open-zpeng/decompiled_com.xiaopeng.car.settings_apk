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
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.adapter.PsnBluetoothAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.ui.common.PsnBluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.view.PsnBluetoothView;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class PsnBluetoothView extends BaseListView implements IBluetoothViewCallback {
    public static final String TAG = "PsnBluetoothView";
    private XSwitch mBlueSwitch;
    private HeaderView mHeaderView;
    private boolean mIsRegisterFresh;
    private PsnBluetoothAdapter mPsnBluetoothAdapter;
    private PsnBluetoothOperation mPsnBluetoothOperation;
    private PsnBluetoothViewModel mPsnBluetoothViewModel;
    private XListSingle mPsnTtsList;
    private XSwitch mPsnTtsSwitch;

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(boolean z) {
    }

    public PsnBluetoothView(Context context) {
        super(context);
        this.mIsRegisterFresh = false;
    }

    public PsnBluetoothView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsRegisterFresh = false;
    }

    public PsnBluetoothView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsRegisterFresh = false;
    }

    public PsnBluetoothView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsRegisterFresh = false;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void init(Context context, View view) {
        this.sceneId = VuiManager.SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG;
        this.mPsnBluetoothOperation = new PsnBluetoothOperation(context);
        this.mPsnBluetoothAdapter = new PsnBluetoothAdapter(this.mPsnBluetoothOperation);
        initVm();
    }

    public void createView() {
        this.mHeaderView = new HeaderView();
        this.mPsnBluetoothAdapter.setHeaderView(this.mHeaderView.initView());
        this.mRecyclerView.setAdapter(this.mPsnBluetoothAdapter);
        this.mRecyclerView.initVuiAttr(this.sceneId, VuiEngine.getInstance(CarSettingsApp.getContext()), true);
        this.mHeaderView.init();
        super.paddingRecyclerView();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        refreshBluetoothData();
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$NXzSM_lWqvYjPQkoPgPDA56mOtk
            @Override // java.lang.Runnable
            public final void run() {
                PsnBluetoothView.this.lambda$start$0$PsnBluetoothView();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$start$0$PsnBluetoothView() {
        this.mPsnBluetoothAdapter.updateAllBusyStatusAndRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        unRefreshBluetoothData();
        this.mPsnBluetoothOperation.dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
        this.sceneId = str;
    }

    public void onConfigurationChanged() {
        this.mPsnBluetoothAdapter.notifyDataSetChanged();
        this.mPsnBluetoothOperation.cleanDialog();
    }

    public void dismissDialog() {
        PsnBluetoothOperation psnBluetoothOperation = this.mPsnBluetoothOperation;
        if (psnBluetoothOperation != null) {
            psnBluetoothOperation.dismissDialog();
        }
    }

    public void refreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            return;
        }
        this.mPsnBluetoothViewModel.registerErrorCallback(this);
        this.mPsnBluetoothViewModel.onStartVM();
        this.mIsRegisterFresh = true;
        Logs.d("PsnBluetoothViewxpPsnBluetooth fragment fresh refreshBluetoothData ");
    }

    private void unRefreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            this.mPsnBluetoothViewModel.unregisterErrorCallback(this);
            this.mPsnBluetoothAdapter.cancelPairConnectTimeout();
            this.mPsnBluetoothViewModel.onStopVM();
            this.mIsRegisterFresh = false;
            Logs.d("PsnBluetoothViewxpPsnBluetooth fragment fresh unRefreshBluetoothData ");
        }
    }

    private void initVm() {
        this.mPsnBluetoothViewModel = new PsnBluetoothViewModel(CarSettingsApp.getApp());
        this.mPsnBluetoothOperation.setViewModel(this.mPsnBluetoothViewModel);
        this.mPsnBluetoothAdapter.setViewModel(this.mPsnBluetoothViewModel);
        this.mPsnBluetoothViewModel.getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$xQJs6eJJ5NY80SEOWcbGwrY2jbQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$1$PsnBluetoothView((Boolean) obj);
            }
        });
        this.mPsnBluetoothViewModel.getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$plcsZCTDz40YUikbKlvWL0KqyTY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$2$PsnBluetoothView((Boolean) obj);
            }
        });
        this.mPsnBluetoothViewModel.getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$s-PU5QFwvg7OHSXqY4XEzMKF_sg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$3$PsnBluetoothView((PsnBluetoothDeviceInfo) obj);
            }
        });
        this.mPsnBluetoothViewModel.getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$qJ4bAzhFCRXUHNYfjkATSdPFHTI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$4$PsnBluetoothView((Boolean) obj);
            }
        });
        this.mPsnBluetoothViewModel.getShowTtsLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$d3OGYrY5EZCgFIZn0wrGUdM7des
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$5$PsnBluetoothView((Boolean) obj);
            }
        });
        this.mPsnBluetoothViewModel.getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$7_V3wtGeWcSpOaCIlOR2eHwauFU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PsnBluetoothView.this.lambda$initVm$6$PsnBluetoothView((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initVm$1$PsnBluetoothView(Boolean bool) {
        this.mPsnBluetoothAdapter.setScanning(bool.booleanValue());
    }

    public /* synthetic */ void lambda$initVm$2$PsnBluetoothView(Boolean bool) {
        this.mPsnBluetoothAdapter.onRefreshData();
    }

    public /* synthetic */ void lambda$initVm$3$PsnBluetoothView(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        this.mPsnBluetoothAdapter.refreshItemChange(psnBluetoothDeviceInfo);
    }

    public /* synthetic */ void lambda$initVm$4$PsnBluetoothView(Boolean bool) {
        this.mRecyclerView.scrollToPosition(0);
    }

    public /* synthetic */ void lambda$initVm$5$PsnBluetoothView(Boolean bool) {
        this.mPsnTtsList.setVisibility(bool.booleanValue() ? 0 : 8);
        if (bool.booleanValue()) {
            this.mPsnTtsSwitch.setChecked(this.mPsnBluetoothViewModel.getPsnTtsHeadsetOut());
        }
        updateVuiScene(this.sceneId, this.mPsnTtsList);
    }

    public /* synthetic */ void lambda$initVm$6$PsnBluetoothView(Integer num) {
        switch (num.intValue()) {
            case 10:
                this.mBlueSwitch.setChecked(false);
                this.mBlueSwitch.setEnabled(true);
                this.mPsnBluetoothAdapter.setScanning(false);
                this.mPsnBluetoothOperation.dismissDialog();
                this.mPsnBluetoothAdapter.setClickingBt(false);
                return;
            case 11:
                this.mBlueSwitch.setChecked(true);
                this.mBlueSwitch.setEnabled(false);
                return;
            case 12:
                this.mBlueSwitch.setChecked(true);
                this.mBlueSwitch.setEnabled(true);
                this.mPsnBluetoothAdapter.setClickingBt(false);
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
        this.mPsnBluetoothOperation.showConnectError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        this.mPsnBluetoothOperation.showBondError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        this.mPsnBluetoothOperation.showConnectOperateError(str);
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
            View headerOrFooterView = PsnBluetoothView.this.getHeaderOrFooterView(R.layout.psn_bluetooth_recycler_header);
            PsnBluetoothView.this.mBlueSwitch = (XSwitch) ((XListSingle) headerOrFooterView.findViewById(R.id.psn_bluetooth_switch)).findViewById(R.id.x_list_action_switch);
            PsnBluetoothView.this.mBlueSwitch.setChecked(PsnBluetoothView.this.mPsnBluetoothViewModel.isUsbEnable());
            PsnBluetoothView.this.mBlueSwitch.setVuiLabel(PsnBluetoothView.this.getContext().getString(R.string.co_driver_bluetooth_power));
            PsnBluetoothView.this.mPsnTtsList = (XListSingle) headerOrFooterView.findViewById(R.id.psn_tts_switch);
            PsnBluetoothView psnBluetoothView = PsnBluetoothView.this;
            psnBluetoothView.mPsnTtsSwitch = (XSwitch) psnBluetoothView.mPsnTtsList.findViewById(R.id.x_list_action_switch);
            PsnBluetoothView.this.mPsnTtsSwitch.setVuiLabel(PsnBluetoothView.this.getContext().getString(R.string.psn_tts_switch));
            return headerOrFooterView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            PsnBluetoothView.this.mBlueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$HeaderView$oiDLtTwW-nyPFBLaPRWkvaBsvRQ
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    PsnBluetoothView.HeaderView.this.lambda$init$0$PsnBluetoothView$HeaderView(compoundButton, z);
                }
            });
            PsnBluetoothView.this.mPsnTtsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$PsnBluetoothView$HeaderView$JSQbwDWhUdNvOXgsi1Xsip7xwy8
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    PsnBluetoothView.HeaderView.this.lambda$init$1$PsnBluetoothView$HeaderView(compoundButton, z);
                }
            });
        }

        public /* synthetic */ void lambda$init$0$PsnBluetoothView$HeaderView(CompoundButton compoundButton, boolean z) {
            if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
                PsnBluetoothView.this.mBlueSwitch.setEnabled(false);
                PsnBluetoothView.this.mPsnBluetoothAdapter.setClickingBt(true);
                PsnBluetoothView.this.mPsnBluetoothViewModel.setUsbEnable(z);
                if (!z) {
                    PsnBluetoothView.this.mPsnBluetoothAdapter.onRefreshData();
                }
            }
            PsnBluetoothView psnBluetoothView = PsnBluetoothView.this;
            psnBluetoothView.updateVuiScene(psnBluetoothView.sceneId, PsnBluetoothView.this.mRecyclerView);
        }

        public /* synthetic */ void lambda$init$1$PsnBluetoothView$HeaderView(CompoundButton compoundButton, boolean z) {
            PsnBluetoothView psnBluetoothView = PsnBluetoothView.this;
            psnBluetoothView.updateVuiScene(psnBluetoothView.sceneId, PsnBluetoothView.this.mPsnTtsSwitch);
            if (((XSwitch) compoundButton).isFromUser()) {
                PsnBluetoothView.this.mPsnBluetoothViewModel.setPsnTtsHeadsetOut(z);
            }
        }
    }
}
