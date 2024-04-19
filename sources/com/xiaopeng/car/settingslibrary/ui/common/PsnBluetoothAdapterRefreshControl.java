package com.xiaopeng.car.settingslibrary.ui.common;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class PsnBluetoothAdapterRefreshControl extends AdapterRefreshControl {
    private static final String TAG = "bluetooth-data";
    private BaseAdapter mBluetoothAdapter;
    private PsnBluetoothViewModel mBluetoothViewModel;
    private List<PsnBluetoothTypeBean> mDevices;

    public PsnBluetoothAdapterRefreshControl(BaseAdapter baseAdapter) {
        this.mBluetoothAdapter = baseAdapter;
    }

    public void setBluetoothSettingsViewModel(PsnBluetoothViewModel psnBluetoothViewModel) {
        this.mBluetoothViewModel = psnBluetoothViewModel;
    }

    public void onRefreshData(ArrayList<PsnBluetoothTypeBean> arrayList) {
        this.mDevices = arrayList;
        checkRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl
    protected void refreshData() {
        this.mBluetoothAdapter.refreshDataWithContrast(this.mDevices);
        List data = this.mBluetoothAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            PsnBluetoothTypeBean psnBluetoothTypeBean = (PsnBluetoothTypeBean) data.get(i);
            if (psnBluetoothTypeBean.isChanged()) {
                onRefreshItem(psnBluetoothTypeBean);
            }
        }
    }

    public void onRefreshItem(PsnBluetoothTypeBean psnBluetoothTypeBean) {
        int indexOf = this.mBluetoothAdapter.getData().indexOf(psnBluetoothTypeBean);
        if (indexOf >= 0) {
            Logs.log(TAG, String.format("onRefreshItem--index:%s", Integer.valueOf(indexOf), psnBluetoothTypeBean.getData().getDeviceName()));
            psnBluetoothTypeBean.refreshStatus();
            this.mBluetoothAdapter.lambda$refreshItem$10$BaseAdapter(indexOf);
        }
    }
}
