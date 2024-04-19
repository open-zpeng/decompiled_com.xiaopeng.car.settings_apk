package com.xiaopeng.car.settingslibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.adapter.WifiAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.ui.common.WifiOperation;
import com.xiaopeng.car.settingslibrary.vm.wifi.WifiSettingsViewModel;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.List;
/* loaded from: classes.dex */
public class WlanView extends BaseListView {
    private boolean mIsRegisterFresh;
    private WifiAdapter mWifiAdapter;
    private WifiOperation mWifiOperation;
    private WifiSettingsViewModel mWifiSettingsViewModel;
    private XGroupHeader mWlanGroupHeader;
    private XSwitch mWlanSwitch;

    public WlanView(Context context) {
        super(context);
        this.mIsRegisterFresh = false;
    }

    public WlanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsRegisterFresh = false;
    }

    public WlanView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsRegisterFresh = false;
    }

    public WlanView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsRegisterFresh = false;
    }

    public void setDialog(boolean z) {
        this.mIsDialog = z;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void init(Context context, View view) {
        this.mWifiOperation = new WifiOperation(context);
        this.mWifiAdapter = new WifiAdapter(this.mWifiOperation, this.sceneId, this.mIsDialog);
        this.mWifiSettingsViewModel = new WifiSettingsViewModel(CarSettingsApp.getApp());
        this.mWifiAdapter.setViewModel(this.mWifiSettingsViewModel);
    }

    private void initObserve() {
        this.mWifiSettingsViewModel.getWifiStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$WlanView$EHW_X01hlDcgHfbnm2QNmTqvSBw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WlanView.this.lambda$initObserve$0$WlanView((Integer) obj);
            }
        });
        this.mWifiSettingsViewModel.getWifiListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$WlanView$WzQpmHzh0MvqykJWCnEliI-4lkA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WlanView.this.lambda$initObserve$1$WlanView((List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initObserve$0$WlanView(Integer num) {
        if (num.intValue() == 1) {
            this.mWlanSwitch.setEnabled(true);
            this.mWlanSwitch.setChecked(false);
            this.mWlanGroupHeader.setVisibility(4);
            this.mWlanGroupHeader.showLoading(false);
            this.mWifiAdapter.setPointList(null);
            this.mWifiOperation.dismissDialog();
        } else if (num.intValue() == 0) {
            this.mWlanSwitch.setEnabled(false);
            this.mWlanSwitch.setChecked(false);
            this.mWlanGroupHeader.setVisibility(4);
            this.mWlanGroupHeader.showLoading(false);
        } else if (num.intValue() == 3) {
            this.mWlanSwitch.setEnabled(true);
            this.mWlanSwitch.setChecked(true);
            this.mWlanGroupHeader.setVisibility(0);
            if (this.mWifiAdapter.getData().size() == 0) {
                this.mWlanGroupHeader.showLoading(true);
            }
        } else if (num.intValue() == 2) {
            this.mWlanSwitch.setEnabled(false);
            this.mWlanSwitch.setChecked(true);
            this.mWlanGroupHeader.setVisibility(4);
            this.mWlanGroupHeader.showLoading(false);
        }
    }

    public /* synthetic */ void lambda$initObserve$1$WlanView(List list) {
        if (list != null) {
            this.mWifiAdapter.setPointList(list);
            if (list.size() == 0 || (list.size() == 1 && ((XpAccessPoint) list.get(0)).isConnected())) {
                this.mWlanGroupHeader.showLoading(true);
            } else if (list.size() > 0) {
                this.mWlanGroupHeader.showLoading(false);
            }
        }
        updateVuiScene(this.sceneId, this.mRecyclerView);
    }

    public void createView() {
        initVui();
        View headerOrFooterView = getHeaderOrFooterView(R.layout.wlan_recycler_header);
        View findViewById = headerOrFooterView.findViewById(R.id.wlan_title_set);
        if (this.mIsDialog) {
            findViewById.setVisibility(8);
        }
        this.mWlanSwitch = (XSwitch) ((XListSingle) headerOrFooterView.findViewById(R.id.wlan_switch)).findViewById(R.id.x_list_action_switch);
        this.mWlanSwitch.setVuiLabel(getContext().getString(R.string.wlan_power));
        this.mWlanSwitch.setChecked(this.mWifiSettingsViewModel.isEnable());
        this.mWlanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$WlanView$QPL0Fs_WFa-dhflUdM6cqp4gAnU
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                WlanView.this.lambda$createView$2$WlanView(compoundButton, z);
            }
        });
        this.mWlanGroupHeader = (XGroupHeader) headerOrFooterView.findViewById(R.id.wlan_groupheader);
        this.mWlanGroupHeader.setVisibility(4);
        this.mWlanGroupHeader.showLoading(false);
        this.mWifiAdapter.setHeaderView(headerOrFooterView);
        this.mRecyclerView.setAdapter(this.mWifiAdapter);
        super.paddingRecyclerView();
    }

    public /* synthetic */ void lambda$createView$2$WlanView(CompoundButton compoundButton, boolean z) {
        if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
            compoundButton.setEnabled(false);
            this.mWifiSettingsViewModel.setEnable(z);
            if (!z) {
                this.mWlanGroupHeader.setVisibility(4);
                this.mWlanGroupHeader.showLoading(false);
                this.mWifiAdapter.setPointList(null);
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.WLAN_PAGE_ID, "B001", z);
        }
        updateVuiScene(this.sceneId, this.mRecyclerView);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        refreshWlanData();
        initObserve();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        unRefreshWlanData();
        this.mWifiOperation.dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
        this.sceneId = str;
    }

    private void refreshWlanData() {
        if (this.mIsRegisterFresh) {
            return;
        }
        this.mWifiSettingsViewModel.getWifiState();
        this.mWifiSettingsViewModel.start();
        this.mWifiSettingsViewModel.getWifiList();
        this.mIsRegisterFresh = true;
        Logs.d("xpwifi fragment fresh refreshWlanData");
    }

    private void unRefreshWlanData() {
        if (this.mIsRegisterFresh) {
            this.mWifiSettingsViewModel.stop();
            this.mIsRegisterFresh = false;
            Logs.d("xpwifi fragment fresh unRefreshWlanData");
        }
    }

    public void onConfigurationChanged() {
        WifiAdapter wifiAdapter = this.mWifiAdapter;
        if (wifiAdapter != null) {
            wifiAdapter.notifyDataSetChanged();
        }
        WifiOperation wifiOperation = this.mWifiOperation;
        if (wifiOperation != null) {
            wifiOperation.cleanDialog();
        }
    }

    private void initVui() {
        this.mRecyclerView.setVuiLabel(getContext().getString(R.string.vui_label_wifi));
        this.mRecyclerView.initVuiAttr(this.sceneId, VuiEngine.getInstance(CarSettingsApp.getContext()), true);
    }
}
