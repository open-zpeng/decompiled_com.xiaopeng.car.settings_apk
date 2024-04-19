package com.xiaopeng.car.settingslibrary.ui.common;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import java.util.List;
/* loaded from: classes.dex */
public class WifiAdapterRefreshControl extends AdapterRefreshControl {
    private static final String TAG = "wifi-data";
    private List<WifiTypeBean> mAllAccessPointList;
    private WifiTypeBean mLastConnectedWifi;
    private OnConnectedWifiChangedListener mOnConnectedWifiChangedListener;
    private BaseAdapter mWifiAdapter;

    /* loaded from: classes.dex */
    public interface OnConnectedWifiChangedListener {
        void OnConnectedWifiChanged(WifiTypeBean wifiTypeBean);
    }

    public WifiAdapterRefreshControl(BaseAdapter baseAdapter) {
        this.mWifiAdapter = baseAdapter;
    }

    public void setPointList(List<WifiTypeBean> list) {
        this.mAllAccessPointList = list;
        Logs.log(TAG, "setPointList-size :" + list.size());
        checkRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl
    protected void refreshData() {
        XpAccessPoint accessPoint;
        this.mWifiAdapter.refreshDataWithContrast(this.mAllAccessPointList);
        List data = this.mWifiAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            WifiTypeBean wifiTypeBean = (WifiTypeBean) data.get(i);
            if (this.mAllAccessPointList.contains(wifiTypeBean)) {
                WifiTypeBean wifiTypeBean2 = this.mAllAccessPointList.get(this.mAllAccessPointList.indexOf(wifiTypeBean));
                if (wifiTypeBean2 != null && (accessPoint = wifiTypeBean2.getAccessPoint()) != null && accessPoint != wifiTypeBean.getAccessPoint()) {
                    wifiTypeBean.setAccessPoint(accessPoint);
                    Logs.d("xpwifi list update accesspoint:" + accessPoint.getSsid() + " " + accessPoint.getLevel());
                }
            }
            if (wifiTypeBean.isChanged()) {
                Logs.logv(TAG, "old-" + wifiTypeBean.toString());
                wifiTypeBean.refreshStatus();
                Logs.logv(TAG, "new-" + wifiTypeBean.toString());
                this.mWifiAdapter.lambda$refreshItem$10$BaseAdapter(i);
            }
            if ((wifiTypeBean.isConnected() || wifiTypeBean.isConnecting()) && !wifiTypeBean.equals(this.mLastConnectedWifi)) {
                this.mLastConnectedWifi = wifiTypeBean;
                Logs.log(TAG, "ConnectedWifiChanged-" + wifiTypeBean.toString());
                OnConnectedWifiChangedListener onConnectedWifiChangedListener = this.mOnConnectedWifiChangedListener;
                if (onConnectedWifiChangedListener != null) {
                    onConnectedWifiChangedListener.OnConnectedWifiChanged(wifiTypeBean);
                }
            }
        }
    }

    public void setOnConnectedWifiChangedListener(OnConnectedWifiChangedListener onConnectedWifiChangedListener) {
        this.mOnConnectedWifiChangedListener = onConnectedWifiChangedListener;
    }
}
