package com.xiaopeng.car.settingslibrary.demo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothManger;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.List;
/* loaded from: classes.dex */
public class UsbBlueDemoFragment extends BaseFragment {
    TextView bluetoothAddr;
    TextView bluetoothName;
    XSwitch mBleSwitch;
    BluetoothReceiver mReceiver = new BluetoothReceiver();
    Button mSearchBtn;
    PsnBluetoothViewModel mViewModel;
    UsbAdapter usbAdapter;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$1(Integer num) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.usb_bluetooth_demo;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mBleSwitch = (XSwitch) view.findViewById(R.id.ble_switch);
        this.mBleSwitch.setText("副驾蓝牙开关");
        this.mBleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                UsbBlueDemoFragment.this.mViewModel.setUsbEnable(z);
            }
        });
        this.mSearchBtn = (Button) view.findViewById(R.id.btn);
        this.mSearchBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                UsbBlueDemoFragment.this.mViewModel.lambda$onUsbBluetoothStateChanged$0$PsnBluetoothViewModel();
            }
        });
        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                UsbBlueDemoFragment.this.mViewModel.usbConnect("FC:58:FA:DE:41:AB");
            }
        });
        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ToastUtils.get().showLongText(PsnBluetoothManger.getInstance().getUsbConnectedDevice());
            }
        });
        view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                UsbBlueDemoFragment.this.mViewModel.disUsbConnect("FC:58:FA:DE:41:AB");
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemViewCacheSize(0);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        this.usbAdapter = new UsbAdapter();
        recyclerView.setAdapter(this.usbAdapter);
        this.bluetoothName = (TextView) view.findViewById(R.id.ble_name);
        this.bluetoothAddr = (TextView) view.findViewById(R.id.ble_addr);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mViewModel = (PsnBluetoothViewModel) ViewModelUtils.getViewModel(this, PsnBluetoothViewModel.class);
        this.mViewModel.getScanningLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$UsbBlueDemoFragment$JXjVLrorTJBFITGxhxpBMwkTTCA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UsbBlueDemoFragment.this.lambda$init$0$UsbBlueDemoFragment((Boolean) obj);
            }
        });
        this.mViewModel.getBluetoothStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$UsbBlueDemoFragment$8R328tacHYZQb2dcsdSCzU9DAao
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UsbBlueDemoFragment.lambda$init$1((Integer) obj);
            }
        });
        this.mBleSwitch.setChecked(this.mViewModel.isUsbEnable());
        this.mViewModel.getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$UsbBlueDemoFragment$m4TtWh987WUK19Q2S6lsnKaavS0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UsbBlueDemoFragment.this.lambda$init$2$UsbBlueDemoFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$UsbBlueDemoFragment(Boolean bool) {
        this.mSearchBtn.setText(bool.booleanValue() ? "搜索中" : "搜索");
    }

    public /* synthetic */ void lambda$init$2$UsbBlueDemoFragment(Boolean bool) {
        this.usbAdapter.lambda$addData$4$BaseAdapter((List) this.mViewModel.getAvailableDevicesSorted());
        this.usbAdapter.lambda$addData$4$BaseAdapter((List) this.mViewModel.getBondedDevicesSorted());
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        register();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            unregister();
        } else {
            register();
        }
    }

    private void register() {
        this.mViewModel.onStartVM();
        this.mBleSwitch.setChecked(this.mViewModel.isUsbEnable());
        this.bluetoothName.setText(this.mViewModel.getUsbBluetoothName());
        this.bluetoothAddr.setText(this.mViewModel.getUsbAddr());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.External.ACTION_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(Config.External.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(Config.External.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(Config.External.ACTION_DEVICE_FOUND);
        intentFilter.addAction(Config.External.ACTION_A2DP_CONNECTION_STATE_CHANGED);
        getContext().registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        unregister();
    }

    private void unregister() {
        this.mViewModel.onStopVM();
        getContext().unregisterReceiver(this.mReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class UsbAdapter extends BaseAdapter<PsnBluetoothDeviceInfo> {
        public UsbAdapter() {
            setOnItemClickListener(new BaseAdapter.OnItemClickListener<PsnBluetoothDeviceInfo>() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.UsbBlueDemoFragment.UsbAdapter.1
                @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
                public void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
                    UsbBlueDemoFragment.this.mViewModel.usbConnect(psnBluetoothDeviceInfo.getDeviceAddr());
                    ToastUtils toastUtils = ToastUtils.get();
                    toastUtils.showLongText("连接中请稍后 " + psnBluetoothDeviceInfo.getDeviceName());
                }
            });
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.fragment_demo_item_tv;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            PsnBluetoothDeviceInfo item = getItem(i);
            ((TextView) viewHolder.getView(R.id.text)).setText(item.getDeviceName() + "_" + item.getDeviceAddr());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class BluetoothReceiver extends BroadcastReceiver {
        BluetoothReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logs.d("onReceiveonReceiveonReceiveonReceive " + intent.getAction());
            if (intent.getAction().equals(Config.External.ACTION_STATE_CHANGED)) {
                if (intent.getIntExtra("state", -1) == 12) {
                    ToastUtils.get().showLongText("副驾蓝牙打开了");
                } else {
                    ToastUtils.get().showLongText("副驾蓝牙关闭了");
                }
            } else if (intent.getAction().equals(Config.External.ACTION_DISCOVERY_STARTED)) {
                ToastUtils.get().showLongText("副驾蓝牙开始搜索");
                UsbBlueDemoFragment.this.mSearchBtn.setText("副驾蓝牙开始搜索");
            } else if (intent.getAction().equals(Config.External.ACTION_DISCOVERY_FINISHED)) {
                ToastUtils.get().showLongText("副驾蓝牙搜索完成");
                UsbBlueDemoFragment.this.mSearchBtn.setText("副驾蓝牙搜索完成");
            } else if (intent.getAction().equals(Config.External.ACTION_BOND_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1);
                int intExtra2 = intent.getIntExtra("state", -1);
                String stringExtra = intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS);
                ToastUtils toastUtils = ToastUtils.get();
                toastUtils.showLongText(stringExtra + "绑定状态变化 " + intExtra + " > " + intExtra2);
            } else if (intent.getAction().equals(Config.External.ACTION_A2DP_CONNECTION_STATE_CHANGED)) {
                int intExtra3 = intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1);
                int intExtra4 = intent.getIntExtra("state", -1);
                String stringExtra2 = intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS);
                ToastUtils toastUtils2 = ToastUtils.get();
                toastUtils2.showLongText(stringExtra2 + "连接状态变化 " + intExtra3 + " > " + intExtra4);
            } else if (intent.getAction().equals(Config.External.ACTION_DEVICE_FOUND)) {
                int intExtra5 = intent.getIntExtra(Config.External.ACTION_EXTRA_PRESTATE, -1);
                int intExtra6 = intent.getIntExtra("state", -1);
                String stringExtra3 = intent.getStringExtra(Config.External.ACTION_DEVICE_ADDRESS);
                String stringExtra4 = intent.getStringExtra("name");
                ToastUtils toastUtils3 = ToastUtils.get();
                toastUtils3.showLongText(stringExtra3 + "发现设备 " + intExtra5 + " > " + intExtra6);
                UsbBlueDemoFragment.this.usbAdapter.addData((UsbAdapter) new PsnBluetoothDeviceInfo(stringExtra4, stringExtra3));
            }
        }
    }
}
