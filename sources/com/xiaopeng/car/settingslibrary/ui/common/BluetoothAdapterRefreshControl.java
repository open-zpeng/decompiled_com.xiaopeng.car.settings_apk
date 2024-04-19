package com.xiaopeng.car.settingslibrary.ui.common;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class BluetoothAdapterRefreshControl extends AdapterRefreshControl {
    private static final String TAG = "bluetooth-data";
    private BaseAdapter mBluetoothAdapter;
    private BluetoothSettingsViewModel mBluetoothSettingsViewModel;
    private List<BluetoothTypeBean> mDevices;

    public BluetoothAdapterRefreshControl(BaseAdapter baseAdapter) {
        this.mBluetoothAdapter = baseAdapter;
    }

    public void setBluetoothSettingsViewModel(BluetoothSettingsViewModel bluetoothSettingsViewModel) {
        this.mBluetoothSettingsViewModel = bluetoothSettingsViewModel;
    }

    public void onRefreshData(ArrayList<BluetoothTypeBean> arrayList) {
        this.mDevices = arrayList;
        checkRefresh();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.AdapterRefreshControl
    protected void refreshData() {
        this.mBluetoothAdapter.refreshDataWithContrast(this.mDevices);
        List data = this.mBluetoothAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            BluetoothTypeBean bluetoothTypeBean = (BluetoothTypeBean) data.get(i);
            if (bluetoothTypeBean.isChanged()) {
                onRefreshItem(bluetoothTypeBean);
            }
        }
    }

    public void onRefreshItem(BluetoothTypeBean bluetoothTypeBean) {
        int indexOf = this.mBluetoothAdapter.getData().indexOf(bluetoothTypeBean);
        if (indexOf >= 0) {
            Logs.log(TAG, String.format("onRefreshItem--index:%s,%s,%s,%s", Integer.valueOf(indexOf), Integer.valueOf(this.mBluetoothSettingsViewModel.getBondState(bluetoothTypeBean.getData())), Boolean.valueOf(this.mBluetoothSettingsViewModel.isBusy(bluetoothTypeBean.getData())), bluetoothTypeBean.getData().getDeviceName()));
            bluetoothTypeBean.refreshStatus();
            this.mBluetoothAdapter.lambda$refreshItem$10$BaseAdapter(indexOf);
        }
    }
}
