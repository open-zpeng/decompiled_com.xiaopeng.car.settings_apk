package com.xiaopeng.car.settingslibrary.ui.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.ui.adapter.WifiAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.WifiAdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.common.WifiOperation;
import com.xiaopeng.car.settingslibrary.ui.common.WifiTypeBean;
import com.xiaopeng.car.settingslibrary.vm.wifi.WifiSettingsViewModel;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import java.util.List;
/* loaded from: classes.dex */
public class WifiAdapter extends BaseAdapter<WifiTypeBean> {
    private Context mContext;
    private boolean mIsDialog;
    private RecyclerView mRecyclerView;
    private final WifiAdapterRefreshControl mWifiAdapterRefreshControl = new WifiAdapterRefreshControl(this);
    private final WifiOperation mWifiOperation;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean isControlInterval() {
        return true;
    }

    public WifiAdapter(WifiOperation wifiOperation, String str, boolean z) {
        this.mIsDialog = z;
        this.mWifiOperation = wifiOperation;
        this.mWifiAdapterRefreshControl.setOnConnectedWifiChangedListener(new WifiAdapterRefreshControl.OnConnectedWifiChangedListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$WifiAdapter$8jPmui6IjWC2BoLmuwwzdBhfwrI
            @Override // com.xiaopeng.car.settingslibrary.ui.common.WifiAdapterRefreshControl.OnConnectedWifiChangedListener
            public final void OnConnectedWifiChanged(WifiTypeBean wifiTypeBean) {
                WifiAdapter.this.lambda$new$0$WifiAdapter(wifiTypeBean);
            }
        });
        setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$WifiAdapter$aCB0ZyhlVboOGxDgv4-QWAJBFpc
            @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
            public final void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, Object obj) {
                WifiAdapter.this.lambda$new$1$WifiAdapter(baseAdapter, viewHolder, (WifiTypeBean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$WifiAdapter(WifiTypeBean wifiTypeBean) {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    public /* synthetic */ void lambda$new$1$WifiAdapter(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, WifiTypeBean wifiTypeBean) {
        this.mWifiOperation.connect(wifiTypeBean.getAccessPoint());
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        this.mContext = recyclerView.getContext();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
        final WifiTypeBean item = getItem(i);
        XListSingle xListSingle = (XListSingle) viewHolder.getView(R.id.wifi_item_singe);
        XListTwo xListTwo = (XListTwo) viewHolder.getView(R.id.wifi_item_two);
        if (!Utils.isUserRelease()) {
            Logs.d("xpwifi convert ui isConnected:" + item.isConnected() + " ssid:" + item.getAccessPoint().getSsid() + " sec:" + item.getAccessPoint().getSecurityString());
        }
        if (item.isConnected()) {
            xListTwo.setVisibility(0);
            xListSingle.setVisibility(8);
            xListTwo.setText(item.getAccessPoint().getSsid());
            if (item.getAccessPoint().getSecurity() == 0) {
                xListTwo.setTextSub(this.mContext.getString(R.string.wlan_connected_no_security));
            } else {
                xListTwo.setTextSub(this.mContext.getString(R.string.connected));
            }
            xListTwo.setSelected(true);
            XImageButton xImageButton = (XImageButton) xListTwo.findViewById(R.id.x_list_action_button_icon);
            xImageButton.setVuiDisableHitEffect(true);
            xImageButton.setImageDrawable(this.mContext.getDrawable(R.drawable.x_ic_small_doubt_blue));
            if (this.mIsDialog && !CarFunction.isSupportBlueToothWifiDialogRightInfoIcon()) {
                xImageButton.setVisibility(8);
            } else {
                xImageButton.setVisibility(0);
                xImageButton.setOnClickListener(new AnonymousClass1(item));
                if (this.mWifiOperation.isXPAuto(item.getAccessPoint()) || this.mWifiOperation.isXPCheck(item.getAccessPoint())) {
                    xImageButton.setVisibility(8);
                } else {
                    xImageButton.setVisibility(0);
                }
            }
            setVuiConnectedAttrs(xListSingle, xListTwo, xImageButton, i, item.getAccessPoint());
            this.mWifiOperation.refreshIcon(item.getAccessPoint(), (ImageView) xListTwo.findViewById(R.id.wlan_list_action_icon));
            return;
        }
        xListTwo.setVisibility(8);
        xListSingle.setVisibility(0);
        xListSingle.setText(item.getAccessPoint().getSsid());
        ImageView imageView = (ImageView) xListSingle.findViewById(R.id.wlan_list_action_icon);
        if (item.getAccessPoint().getSecurity() == 0) {
            imageView.setImageResource(R.drawable.wifi_connecting);
        } else {
            imageView.setImageResource(R.drawable.wifi_connecting_lock);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        XImageButton xImageButton2 = (XImageButton) xListSingle.findViewById(R.id.x_list_action_button_icon);
        xImageButton2.setVuiDisableHitEffect(true);
        xImageButton2.setImageDrawable(this.mContext.getDrawable(R.drawable.x_ic_small_doubt_blue));
        xImageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$WifiAdapter$WWIOPoRWx9SAEXXv4d7DFQwKraQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WifiAdapter.this.lambda$convert$3$WifiAdapter(item, view);
            }
        });
        if ((this.mIsDialog && !CarFunction.isSupportBlueToothWifiDialogRightInfoIcon()) || this.mWifiOperation.isXPAuto(item.getAccessPoint()) || this.mWifiOperation.isXPCheck(item.getAccessPoint())) {
            xImageButton2.setVisibility(8);
        } else if (item.getAccessPoint().isConnecting()) {
            xImageButton2.setVisibility(0);
        } else if (this.mWifiOperation.isApSaved(item.getAccessPoint())) {
            xImageButton2.setVisibility(0);
        } else {
            xImageButton2.setVisibility(8);
        }
        if (!Utils.isUserRelease()) {
            Logs.d("wifi button status connecting:" + item.getAccessPoint().isConnecting() + " save:" + this.mWifiOperation.isApSaved(item.getAccessPoint()) + " ap:" + item.getAccessPoint());
        }
        if (item.getAccessPoint().isConnecting()) {
            animationDrawable.stop();
            animationDrawable.start();
        } else {
            animationDrawable.stop();
            imageView.setSelected(false);
            this.mWifiOperation.refreshIcon(item.getAccessPoint(), imageView);
        }
        setVuiDisconnectAttrs(xListSingle, xListTwo, xImageButton2, i, item.getAccessPoint(), item.isConnecting());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.ui.adapter.WifiAdapter$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements View.OnClickListener {
        final /* synthetic */ WifiTypeBean val$data;

        AnonymousClass1(WifiTypeBean wifiTypeBean) {
            this.val$data = wifiTypeBean;
        }

        public /* synthetic */ void lambda$onClick$0$WifiAdapter$1() {
            WifiAdapter.this.refreshAll();
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            WifiAdapter.this.mWifiOperation.showDevicePopup(this.val$data.getAccessPoint(), new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$WifiAdapter$1$oLW1RFQZw1q3gVuY1OSluKgumA0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiAdapter.AnonymousClass1.this.lambda$onClick$0$WifiAdapter$1();
                }
            });
        }
    }

    public /* synthetic */ void lambda$convert$3$WifiAdapter(WifiTypeBean wifiTypeBean, View view) {
        this.mWifiOperation.showDevicePopup(wifiTypeBean.getAccessPoint(), new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$WifiAdapter$0xRBN3Yb0kuJZFrF-MHCaQkp6UQ
            @Override // java.lang.Runnable
            public final void run() {
                WifiAdapter.this.lambda$null$2$WifiAdapter();
            }
        });
    }

    public /* synthetic */ void lambda$null$2$WifiAdapter() {
        refreshAll();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected int layoutId(int i) {
        return R.layout.wlan_recycler_item_all;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean itemClickable(int i) {
        WifiTypeBean item = getItem(i);
        return (item.isConnected() || item.getAccessPoint().isConnecting()) ? false : true;
    }

    public void setPointList(List<XpAccessPoint> list) {
        this.mWifiAdapterRefreshControl.setPointList(WifiTypeBean.list(list));
    }

    public void setViewModel(WifiSettingsViewModel wifiSettingsViewModel) {
        this.mWifiOperation.setViewModel(wifiSettingsViewModel);
    }

    private void setVuiConnectedAttrs(XListSingle xListSingle, XListTwo xListTwo, XImageButton xImageButton, int i, XpAccessPoint xpAccessPoint) {
        if (!CarSettingsApp.isVuiFeatureEnable()) {
            xListTwo.showNum(false);
        } else {
            xListTwo.showNum(true);
        }
        xListSingle.setVuiMode(VuiMode.DISABLED);
        xListTwo.setVuiMode(VuiMode.NORMAL);
        xListSingle.setVuiBizId(xpAccessPoint.getBssid());
        xListTwo.setVuiBizId(xpAccessPoint.getBssid());
        xListTwo.setVuiElementId(xListTwo.getId() + "_" + i);
        int i2 = i + 1;
        xListTwo.setVuiLabel(String.format(xListTwo.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i2), xpAccessPoint.getSsid()));
        xListTwo.setVuiAction(xListTwo.getContext().getString(R.string.vui_action_click_connect));
        xImageButton.setVuiElementId(xImageButton.getId() + "_" + i);
        xImageButton.setVuiLabel(xImageButton.getContext().getString(R.string.vui_label_detail));
        xListTwo.setNum(i2);
        if (this.mWifiOperation.isXPAuto(xpAccessPoint) || this.mWifiOperation.isXPCheck(xpAccessPoint)) {
            xImageButton.setVuiMode(VuiMode.DISABLED);
        } else {
            xImageButton.setVuiMode(VuiMode.NORMAL);
        }
    }

    private void setVuiDisconnectAttrs(XListSingle xListSingle, XListTwo xListTwo, XImageButton xImageButton, int i, XpAccessPoint xpAccessPoint, boolean z) {
        if (!CarSettingsApp.isVuiFeatureEnable()) {
            xListSingle.showNum(false);
        } else {
            xListSingle.showNum(true);
        }
        xListTwo.setVuiMode(VuiMode.DISABLED);
        xListSingle.setVuiMode(VuiMode.NORMAL);
        xListSingle.setVuiBizId(xpAccessPoint.getBssid());
        xListTwo.setVuiBizId(xpAccessPoint.getBssid());
        int i2 = i + 1;
        xListSingle.setVuiLabel(String.format(xListSingle.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i2), xpAccessPoint.getSsid()));
        xListSingle.setVuiElementId(xListSingle.getId() + "_" + i);
        if (!z) {
            xListSingle.setVuiAction(xListSingle.getContext().getString(R.string.vui_action_click_connect));
        }
        xImageButton.setVuiElementId(xImageButton.getId() + "_" + i);
        xImageButton.setVuiLabel(this.mContext.getString(R.string.vui_label_detail));
        xListSingle.setNum(i2);
        if ((this.mIsDialog && !CarFunction.isSupportBlueToothWifiDialogRightInfoIcon()) || this.mWifiOperation.isXPAuto(xpAccessPoint) || this.mWifiOperation.isXPCheck(xpAccessPoint)) {
            xImageButton.setVuiMode(VuiMode.DISABLED);
        } else if (xpAccessPoint.isConnecting()) {
            xImageButton.setVuiMode(VuiMode.NORMAL);
        } else if (this.mWifiOperation.isApSaved(xpAccessPoint)) {
            xImageButton.setVuiMode(VuiMode.NORMAL);
        } else {
            xImageButton.setVuiMode(VuiMode.DISABLED);
        }
    }
}
