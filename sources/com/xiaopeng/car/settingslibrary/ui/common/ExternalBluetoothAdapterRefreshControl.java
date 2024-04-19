package com.xiaopeng.car.settingslibrary.ui.common;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class ExternalBluetoothAdapterRefreshControl extends AdapterRefreshControl {
    private static final String TAG = "bluetooth-data";
    private BaseAdapter mBluetoothAdapter;
    private ExternalBluetoothViewModel mBluetoothViewModel;
    private List<ExternalBluetoothTypeBean> mDevices;

    public ExternalBluetoothAdapterRefreshControl(BaseAdapter baseAdapter) {
        this.mBluetoothAdapter = baseAdapter;
    }

    public void setBluetoothSettingsViewModel(ExternalBluetoothViewModel externalBluetoothViewModel) {
        this.mBluetoothViewModel = externalBluetoothViewModel;
    }

    public void onRefreshData(ArrayList<ExternalBluetoothTypeBean> arrayList) {
        this.mDevices = arrayList;
        checkRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl
    protected void refreshData() {
        this.mBluetoothAdapter.refreshDataWithContrast(this.mDevices);
        List data = this.mBluetoothAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            ExternalBluetoothTypeBean externalBluetoothTypeBean = (ExternalBluetoothTypeBean) data.get(i);
            if (externalBluetoothTypeBean.isChanged()) {
                onRefreshItem(externalBluetoothTypeBean);
            }
        }
    }

    public void onRefreshItem(ExternalBluetoothTypeBean externalBluetoothTypeBean) {
        int indexOf = this.mBluetoothAdapter.getData().indexOf(externalBluetoothTypeBean);
        if (indexOf >= 0) {
            Logs.log(TAG, String.format("onRefreshItem--index:%s", Integer.valueOf(indexOf), externalBluetoothTypeBean.getData().getDeviceName()));
            externalBluetoothTypeBean.refreshStatus();
            this.mBluetoothAdapter.lambda$refreshItem$10$BaseAdapter(indexOf);
        }
    }
}
