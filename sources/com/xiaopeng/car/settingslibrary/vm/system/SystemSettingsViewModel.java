package com.xiaopeng.car.settingslibrary.vm.system;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
/* loaded from: classes.dex */
public class SystemSettingsViewModel extends AndroidViewModel {
    private static final String TAG = "SystemSettingsViewModel";
    private Application mApplication;
    ConnectivityManager mCM;
    private DataRepository mDataRepository;
    MutableLiveData<SystemInfo> mInfoLivedata;
    private SystemInfo mSystemInfo;
    WifiManager mWifiManager;

    private String getIP() {
        return "";
    }

    public SystemSettingsViewModel(Application application) {
        super(application);
        this.mSystemInfo = new SystemInfo();
        this.mDataRepository = DataRepository.getInstance();
        this.mInfoLivedata = new MutableLiveData<>();
        this.mApplication = application;
        this.mWifiManager = (WifiManager) this.mApplication.getSystemService(WifiManager.class);
        this.mCM = (ConnectivityManager) this.mApplication.getSystemService(ConnectivityManager.class);
    }

    public MutableLiveData<SystemInfo> getInfoLivedata() {
        return this.mInfoLivedata;
    }

    private String getBluetoothMac() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            String address = defaultAdapter.isEnabled() ? defaultAdapter.getAddress() : null;
            if (!TextUtils.isEmpty(address)) {
                return address.toLowerCase();
            }
            return this.mApplication.getResources().getString(R.string.status_unavailable);
        }
        return this.mApplication.getResources().getString(R.string.status_unavailable);
    }

    private String getWifiMac() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        int macRandomizationMode = this.mDataRepository.getMacRandomizationMode(this.mApplication);
        String macAddress = connectionInfo == null ? null : connectionInfo.getMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            return this.mApplication.getResources().getString(R.string.status_unavailable);
        }
        return (macRandomizationMode == 1 && "02:00:00:00:00:00".equals(macAddress)) ? this.mApplication.getResources().getString(R.string.wifi_status_mac_randomized) : macAddress;
    }

    private String getModel() {
        return CarFunction.getProduct();
    }

    public String getBaseBandVersion() {
        return this.mDataRepository.getBaseBandVersion();
    }

    public String getFirmwareVersion() {
        return this.mDataRepository.getFirmwareVersion();
    }

    public String getMcuVersion() {
        return this.mDataRepository.getMcuVersion();
    }

    public String getFormattedKernelVersion(Context context) {
        return this.mDataRepository.getFormattedKernelVersion(context);
    }

    public String getSoftwareVersion() {
        return this.mDataRepository.getSoftwareVersion();
    }

    public SystemInfo getSystemInfo() {
        return this.mSystemInfo;
    }

    public void refreshDeviceInfo() {
        String baseBandVersion = getBaseBandVersion();
        this.mSystemInfo.setModel(getModel());
        SystemInfo systemInfo = this.mSystemInfo;
        if (TextUtils.isEmpty(baseBandVersion)) {
            baseBandVersion = this.mApplication.getApplicationContext().getResources().getString(R.string.device_info_default);
        }
        systemInfo.setBaseBandVersion(baseBandVersion);
        this.mSystemInfo.setFirmwareVersion(getFirmwareVersion());
        this.mSystemInfo.setKernelVersion(getFormattedKernelVersion(this.mApplication));
        this.mSystemInfo.setMcuVersion(getMcuVersion());
        this.mSystemInfo.setSoftwareVersion(getSoftwareVersion());
        this.mSystemInfo.setWifiMac(getWifiMac());
        this.mSystemInfo.setBluetoothMac(getBluetoothMac());
        this.mSystemInfo.setIP(getIP());
        this.mSystemInfo.setTboxVersion(getTboxVersion());
        this.mSystemInfo.setTboxMcu(getTboxMcuVersion());
        LogUtils.d("xpabout systemInfo:" + this.mSystemInfo);
        this.mInfoLivedata.postValue(this.mSystemInfo);
    }

    private String getTboxMcuVersion() {
        return this.mDataRepository.getTboxMcuVersion();
    }

    private String getTboxVersion() {
        return this.mDataRepository.getTboxVersion();
    }
}
