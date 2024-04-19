package com.xiaopeng.car.settingslibrary.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.adapter.BluetoothAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.ui.common.BluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.view.BluetoothView;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class BluetoothView extends BaseListView implements IBluetoothViewCallback {
    private static final String TAG = "BluetoothView";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothOperation mBluetoothOperation;
    private BluetoothSettingsViewModel mBluetoothSettingsViewModel;
    private FooterView mFooterView;
    private HeaderView mHeaderView;
    private boolean mIsRegisterFresh;

    public BluetoothView(Context context) {
        super(context);
        this.mIsRegisterFresh = false;
    }

    public BluetoothView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsRegisterFresh = false;
    }

    public BluetoothView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsRegisterFresh = false;
    }

    public BluetoothView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsRegisterFresh = false;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void init(Context context, View view) {
        this.mBluetoothOperation = new BluetoothOperation(getContext(), this);
        this.mBluetoothAdapter = new BluetoothAdapter(this.mBluetoothOperation, this.mIsDialog, this);
        initVm();
    }

    public void createView() {
        this.mHeaderView = new HeaderView();
        this.mBluetoothAdapter.setHeaderView(this.mHeaderView.initView());
        if (!this.mIsDialog) {
            this.mFooterView = new FooterView();
            this.mBluetoothAdapter.setFooterView(this.mFooterView.initView());
        }
        this.mRecyclerView.setAdapter(this.mBluetoothAdapter);
        this.mRecyclerView.initVuiAttr(this.sceneId, VuiEngine.getInstance(CarSettingsApp.getContext()), true);
        this.mHeaderView.init();
        FooterView footerView = this.mFooterView;
        if (footerView != null) {
            footerView.init();
        }
        super.paddingRecyclerView();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        refreshBluetoothData();
        this.mHeaderView.initObserve();
        FooterView footerView = this.mFooterView;
        if (footerView != null) {
            footerView.initObserve();
        }
        initObserve();
        this.mBluetoothAdapter.updateAllBusyStatusAndRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        unRefreshBluetoothData();
        this.mBluetoothOperation.dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
        this.sceneId = str;
    }

    public void onConfigurationChanged() {
        this.mBluetoothAdapter.notifyDataSetChanged();
        this.mBluetoothOperation.cleanDialog();
    }

    public void refreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            return;
        }
        this.mBluetoothSettingsViewModel.registerErrorCallback(this);
        this.mBluetoothSettingsViewModel.onStartVM();
        this.mIsRegisterFresh = true;
        Logs.d("xpbluetooth fragment fresh refreshBluetoothData ");
    }

    private void unRefreshBluetoothData() {
        if (this.mIsRegisterFresh) {
            this.mBluetoothSettingsViewModel.unregisterErrorCallback(this);
            this.mBluetoothAdapter.cancelPairConnectTimeout();
            this.mBluetoothSettingsViewModel.onStopVM();
            this.mIsRegisterFresh = false;
            Logs.d("xpbluetooth fragment fresh unRefreshBluetoothData ");
        }
    }

    private void initVm() {
        this.mBluetoothSettingsViewModel = new BluetoothSettingsViewModel(CarSettingsApp.getApp());
        this.mBluetoothOperation.setViewModel(this.mBluetoothSettingsViewModel);
        this.mBluetoothAdapter.setViewModel(this.mBluetoothSettingsViewModel);
    }

    private void initObserve() {
        this.mBluetoothSettingsViewModel.getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$uCQdJvBVqVEC--kMU5u7q01lh0Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothView.this.lambda$initObserve$0$BluetoothView((Boolean) obj);
            }
        });
        this.mBluetoothSettingsViewModel.getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$tgPgYefd50Pt_lRFgiXjT1v8p8M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothView.this.lambda$initObserve$1$BluetoothView((Boolean) obj);
            }
        });
        this.mBluetoothSettingsViewModel.getItemChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$CuCj7usA5CQeTQXsIsvciAXljLs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothView.this.lambda$initObserve$2$BluetoothView((XpBluetoothDeviceInfo) obj);
            }
        });
        this.mBluetoothSettingsViewModel.getPairLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$lrN3Wc_XvnDWx9eHsyP133wyVVQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BluetoothView.this.lambda$initObserve$3$BluetoothView((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initObserve$0$BluetoothView(Boolean bool) {
        this.mBluetoothAdapter.setScanning(bool.booleanValue());
        bool.booleanValue();
    }

    public /* synthetic */ void lambda$initObserve$1$BluetoothView(Boolean bool) {
        this.mBluetoothAdapter.onRefreshData();
        VuiManager.instance().updateVuiScene(this.sceneId, CarSettingsApp.getContext(), this.mRecyclerView);
    }

    public /* synthetic */ void lambda$initObserve$2$BluetoothView(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        this.mBluetoothAdapter.refreshItemChange(xpBluetoothDeviceInfo);
    }

    public /* synthetic */ void lambda$initObserve$3$BluetoothView(Boolean bool) {
        this.mRecyclerView.scrollToPosition(0);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectError(String str, String str2) {
        this.mBluetoothOperation.showConnectError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onPairError(String str, String str2) {
        this.mBluetoothOperation.showBondError(str, str2);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onConnectOperateError(String str) {
        this.mBluetoothOperation.showConnectOperateError(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.vm.bluetooth.IBluetoothViewCallback
    public void onBluetoothSwitching(final boolean z) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.BluetoothView.1
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothView.this.mHeaderView != null) {
                    BluetoothView.this.mHeaderView.bluetoothSwitching(z);
                }
            }
        });
    }

    public void setDialog(boolean z) {
        this.mIsDialog = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class HeaderView {
        private XSwitch mBlueSwitch;

        private HeaderView() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public View initView() {
            View headerOrFooterView = BluetoothView.this.getHeaderOrFooterView(R.layout.bluetooth_recycler_header);
            View findViewById = headerOrFooterView.findViewById(R.id.bluetooth_switch_header);
            if (BluetoothView.this.mIsDialog) {
                findViewById.setVisibility(8);
            }
            this.mBlueSwitch = (XSwitch) ((XListSingle) headerOrFooterView.findViewById(R.id.bluetooth_switch)).findViewById(R.id.x_list_action_switch);
            this.mBlueSwitch.setChecked(BluetoothView.this.mBluetoothSettingsViewModel.isEnable());
            this.mBlueSwitch.setVuiLabel(BluetoothView.this.getContext().getString(R.string.bluetooth_power));
            return headerOrFooterView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            this.mBlueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$HeaderView$eSHU-CKeD49l88XXtEbiF1gI8wk
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    BluetoothView.HeaderView.this.lambda$init$0$BluetoothView$HeaderView(compoundButton, z);
                }
            });
        }

        public /* synthetic */ void lambda$init$0$BluetoothView$HeaderView(CompoundButton compoundButton, boolean z) {
            if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
                this.mBlueSwitch.setEnabled(false);
                if (BluetoothView.this.mFooterView != null) {
                    BluetoothView.this.mFooterView.onBluetoothChangingRefreshUI(false);
                }
                BluetoothView.this.mBluetoothSettingsViewModel.changeBTStatus(z);
                if (!z) {
                    BluetoothView.this.mBluetoothAdapter.onRefreshData();
                    VuiManager.instance().updateVuiScene(BluetoothView.this.sceneId, CarSettingsApp.getContext(), BluetoothView.this.mRecyclerView);
                }
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.BLUETOOTH_PAGE_ID, "B001", z);
            }
            BluetoothView bluetoothView = BluetoothView.this;
            bluetoothView.updateVuiScene(bluetoothView.sceneId, BluetoothView.this.mRecyclerView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initObserve() {
            BluetoothView.this.mBluetoothSettingsViewModel.getBluetoothStatusLiveData().observe(BluetoothView.this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$HeaderView$IbYkql2ZQm9TkkIVxTxK5mr9ZhI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BluetoothView.HeaderView.this.lambda$initObserve$1$BluetoothView$HeaderView((Integer) obj);
                }
            });
        }

        public /* synthetic */ void lambda$initObserve$1$BluetoothView$HeaderView(Integer num) {
            switch (num.intValue()) {
                case 10:
                    this.mBlueSwitch.setChecked(false);
                    this.mBlueSwitch.setEnabled(true);
                    BluetoothView.this.mBluetoothAdapter.setScanning(false);
                    BluetoothView.this.mBluetoothOperation.dismissDialog();
                    BluetoothView.this.mBluetoothAdapter.setClickingBt(false);
                    break;
                case 11:
                    this.mBlueSwitch.setChecked(true);
                    this.mBlueSwitch.setEnabled(false);
                    break;
                case 12:
                    this.mBlueSwitch.setChecked(true);
                    this.mBlueSwitch.setEnabled(true);
                    BluetoothView.this.mBluetoothAdapter.setClickingBt(false);
                    BluetoothView.this.mBluetoothSettingsViewModel.startScanList();
                    break;
                case 13:
                    this.mBlueSwitch.setEnabled(false);
                    this.mBlueSwitch.setChecked(false);
                    break;
            }
            if (BluetoothView.this.mFooterView != null) {
                BluetoothView.this.mFooterView.onBluetoothChangingRefreshUI(num.intValue() == 12);
            }
        }

        public void bluetoothSwitching(boolean z) {
            Logs.d("xpbluetooth onBluetoothSwitching:" + z);
            if (z) {
                this.mBlueSwitch.setChecked(true);
                this.mBlueSwitch.setEnabled(false);
                BluetoothView.this.mBluetoothAdapter.setClickingBt(true);
                return;
            }
            this.mBlueSwitch.setEnabled(false);
            this.mBlueSwitch.setChecked(false);
            BluetoothView.this.mBluetoothAdapter.setClickingBt(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class FooterView {
        private String editNameLabel;
        private XListTwo mBlueName;
        private XListTwo mBlueVisible;
        private XSwitch mBlueVisibleSwitch;
        private IntervalControl mIntervalControl;
        private XGroupHeader mXGroupHeader;

        private FooterView() {
            this.editNameLabel = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public View initView() {
            View headerOrFooterView = BluetoothView.this.getHeaderOrFooterView(R.layout.bluetooth_recycler_footer);
            this.mXGroupHeader = (XGroupHeader) headerOrFooterView.findViewById(R.id.bluetooth_footer_header);
            this.mBlueVisible = (XListTwo) headerOrFooterView.findViewById(R.id.bluetooth_visible);
            this.mBlueName = (XListTwo) headerOrFooterView.findViewById(R.id.bluetooth_name);
            this.mBlueVisibleSwitch = (XSwitch) this.mBlueVisible.findViewById(R.id.x_list_action_switch);
            this.mBlueVisibleSwitch.setVuiLabel(BluetoothView.this.getContext().getString(R.string.bluetooth_visible));
            XSwitch xSwitch = this.mBlueVisibleSwitch;
            xSwitch.setVuiElementId(this.mBlueVisible.getId() + "_" + this.mBlueVisibleSwitch.getId());
            VuiManager.instance().supportFeedback(this.mBlueVisibleSwitch);
            boolean z = BluetoothView.this.mIsDialog;
            return headerOrFooterView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            this.editNameLabel = BluetoothView.this.getContext().getString(R.string.bluetooth_car_name);
            this.mBlueVisibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$FooterView$WidPAoEs1tAkqF4S3Zv9EXU6tTM
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    BluetoothView.FooterView.this.lambda$init$0$BluetoothView$FooterView(compoundButton, z);
                }
            });
            XButton xButton = (XButton) this.mBlueName.findViewById(R.id.x_list_action_button);
            xButton.setText(BluetoothView.this.getContext().getText(R.string.edit));
            VuiManager.instance().supportFeedback(xButton);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$FooterView$Z4QEZybetaaJ4Iu6-1jim9iQkK0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BluetoothView.FooterView.this.lambda$init$1$BluetoothView$FooterView(view);
                }
            });
            this.mBlueName.setVuiLabel(this.editNameLabel);
            this.mIntervalControl = new IntervalControl("bluetooth-name");
        }

        public /* synthetic */ void lambda$init$0$BluetoothView$FooterView(CompoundButton compoundButton, boolean z) {
            BluetoothView bluetoothView = BluetoothView.this;
            bluetoothView.updateVuiScene(bluetoothView.sceneId, this.mBlueVisibleSwitch);
            if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
                BluetoothView.this.mBluetoothSettingsViewModel.setVisibility(z);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.BLUETOOTH_PAGE_ID, "B002", z);
            }
        }

        public /* synthetic */ void lambda$init$1$BluetoothView$FooterView(View view) {
            changeName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initObserve() {
            BluetoothView.this.mBluetoothSettingsViewModel.getBluetoothNameLiveData().observe(BluetoothView.this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$FooterView$m_GaKsckOtVYwcwE1fv3RtVtHmo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BluetoothView.FooterView.this.lambda$initObserve$2$BluetoothView$FooterView((String) obj);
                }
            });
            BluetoothView.this.mBluetoothSettingsViewModel.getBluetoothVisibilityLiveData().observe(BluetoothView.this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$FooterView$pyjfw1xuGKwiXdsav5DOp1ZkNRw
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BluetoothView.FooterView.this.lambda$initObserve$3$BluetoothView$FooterView((Boolean) obj);
                }
            });
            BluetoothView.this.mBluetoothSettingsViewModel.getBluetoothConnectedCompleted().observe(BluetoothView.this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$BluetoothView$FooterView$eqT9HkI8D8JIv5qUoe9RY6oTNas
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BluetoothView.FooterView.this.lambda$initObserve$4$BluetoothView$FooterView((Boolean) obj);
                }
            });
        }

        public /* synthetic */ void lambda$initObserve$2$BluetoothView$FooterView(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mBlueName.setText(str);
            if (this.editNameLabel.contains(str)) {
                return;
            }
            this.editNameLabel += "|" + str;
            this.mBlueName.setVuiLabel(this.editNameLabel);
            BluetoothView bluetoothView = BluetoothView.this;
            bluetoothView.updateVuiScene(bluetoothView.sceneId, this.mBlueName);
        }

        public /* synthetic */ void lambda$initObserve$3$BluetoothView$FooterView(Boolean bool) {
            this.mBlueVisibleSwitch.setChecked(bool.booleanValue());
        }

        public /* synthetic */ void lambda$initObserve$4$BluetoothView$FooterView(Boolean bool) {
            BluetoothView.this.mBluetoothSettingsViewModel.viewOnStart();
        }

        private void changeName() {
            if (this.mIntervalControl.isFrequently(1000)) {
                return;
            }
            Utils.popupBluetoothNameEdit();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onBluetoothChangingRefreshUI(boolean z) {
            this.mBlueVisible.setEnabled(z);
            this.mBlueVisibleSwitch.setEnabled(z);
            this.mBlueName.setEnabled(z);
            this.mBlueName.findViewById(R.id.x_list_action_button).setEnabled(z);
            BluetoothView bluetoothView = BluetoothView.this;
            bluetoothView.updateVuiScene(bluetoothView.sceneId, this.mBlueVisible, this.mBlueName);
        }
    }

    public void dismissShowingDialog() {
        BluetoothOperation bluetoothOperation = this.mBluetoothOperation;
        if (bluetoothOperation != null) {
            bluetoothOperation.dismissDialog();
        }
    }
}
