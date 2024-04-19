package com.xiaopeng.car.settingslibrary.vm.wifi;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class WifiSettingsViewModel extends AndroidViewModel implements XpWifiManager.Listener {
    Application mApplication;
    private boolean mIsAlreadyRegister;
    MutableLiveData<List<XpAccessPoint>> mWifiListLiveData;
    XpWifiManager mWifiManager;
    MutableLiveData<Integer> mWifiStatusLiveData;

    public WifiSettingsViewModel(Application application) {
        super(application);
        this.mWifiStatusLiveData = new MutableLiveData<>();
        this.mWifiListLiveData = new MutableLiveData<>();
        this.mIsAlreadyRegister = false;
        this.mApplication = application;
        this.mWifiManager = XpWifiManager.getInstance(this.mApplication.getApplicationContext());
    }

    public int getWifiState() {
        int wifiState = this.mWifiManager.getWifiState();
        Logs.d("xpwifi getWifiState:" + wifiState);
        this.mWifiStatusLiveData.postValue(Integer.valueOf(wifiState));
        return wifiState;
    }

    public int getWifiStateAp() {
        return this.mWifiManager.getWifiState();
    }

    public boolean isEnable() {
        boolean isWifiEnabled = this.mWifiManager.isWifiEnabled();
        Logs.d("xpwifi enable:" + isWifiEnabled);
        return isWifiEnabled;
    }

    public void setEnable(boolean z) {
        Logs.d("xpwifi click setEnable:" + z);
        this.mWifiManager.setWifiEnabled(z);
    }

    public MutableLiveData<Integer> getWifiStatusLiveData() {
        return this.mWifiStatusLiveData;
    }

    public MutableLiveData<List<XpAccessPoint>> getWifiListLiveData() {
        return this.mWifiListLiveData;
    }

    public void getWifiList() {
        List<XpAccessPoint> allAccessPoints = this.mWifiManager.getAllAccessPoints();
        this.mWifiListLiveData.postValue(filterRecoderSsid(allAccessPoints));
        refreshWifiSummary(allAccessPoints);
    }

    private List<XpAccessPoint> filterRecoderSsid(List<XpAccessPoint> list) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (XpAccessPoint xpAccessPoint : list) {
            String ssid = xpAccessPoint.getSsid();
            if (!TextUtils.isEmpty(ssid) && Utils.isVideoRecorder(ssid)) {
                Logs.d("xpwifi find recoder ssid:" + ssid);
            } else {
                copyOnWriteArrayList.add(xpAccessPoint);
            }
        }
        return copyOnWriteArrayList;
    }

    public List<XpAccessPoint> getWifiListAp() {
        return this.mWifiManager.getAllAccessPoints();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager.Listener
    public void onAccessPointsChanged() {
        List<XpAccessPoint> allAccessPoints = this.mWifiManager.getAllAccessPoints();
        int wifiState = this.mWifiManager.getWifiState();
        Logs.d("xpwifi onAccessPointsChanged.size: " + allAccessPoints.size() + " state:" + wifiState);
        if (wifiState != 3) {
            this.mWifiListLiveData.postValue(new ArrayList());
            return;
        }
        this.mWifiListLiveData.postValue(filterRecoderSsid(allAccessPoints));
        refreshWifiSummary(allAccessPoints);
    }

    private void refreshWifiSummary(List<XpAccessPoint> list) {
        if (Config.IS_SDK_HIGHER_P) {
            for (XpAccessPoint xpAccessPoint : list) {
                if (WlanResultWork.getInstance().getTargetAccessPoint() != null && xpAccessPoint.getKey().equals(WlanResultWork.getInstance().getTargetAccessPoint().getKey())) {
                    WlanResultWork.getInstance().notifyItemSummary(xpAccessPoint.getSsid().toString(), xpAccessPoint.getSettingsSummary());
                }
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager.Listener
    public void onWifiStateChanged(int i) {
        Logs.d("xpwifi onWifiStateChanged " + i);
        this.mWifiStatusLiveData.postValue(Integer.valueOf(i));
        if (i != 3) {
            this.mWifiListLiveData.postValue(new ArrayList());
        }
    }

    public void start() {
        Logs.d("xpwifi viewmodel start " + this.mIsAlreadyRegister);
        if (this.mIsAlreadyRegister) {
            return;
        }
        this.mWifiManager.addListener(this);
        this.mWifiManager.start();
        this.mIsAlreadyRegister = true;
    }

    public void stop() {
        Logs.d("xpwifi viewmodel stop " + this.mIsAlreadyRegister);
        if (this.mIsAlreadyRegister) {
            this.mWifiManager.removeListener(this);
            this.mWifiManager.stop();
            this.mIsAlreadyRegister = false;
        }
    }

    public void destroy() {
        Logs.d("xpwifi viewmodel destory ");
        this.mWifiManager.destroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        Logs.d("xpwifi viewmodel onCleared");
    }

    public void forgetAP(XpAccessPoint xpAccessPoint) {
        Logs.d("xpwifi forgetAP save:" + xpAccessPoint.isSaved() + " ap:" + xpAccessPoint);
        this.mWifiManager.forget(xpAccessPoint);
    }

    public void connect(XpAccessPoint xpAccessPoint, XpWifiManager.PopupPwdDialogCallback popupPwdDialogCallback) {
        this.mWifiManager.connect(xpAccessPoint, popupPwdDialogCallback);
    }

    public boolean addAccessPoint(Context context, XpAccessPoint xpAccessPoint, String str, String str2, String str3, int i) {
        return XpWifiManager.addAccessPoint(context, xpAccessPoint, str, str2, str3, i);
    }

    public boolean isApSaved(XpAccessPoint xpAccessPoint) {
        return this.mWifiManager.isApSaved(xpAccessPoint);
    }

    public boolean isEditPwdBeforeConnect(XpAccessPoint xpAccessPoint) {
        return this.mWifiManager.isEditPwdBeforeConnect(xpAccessPoint);
    }

    public boolean connectOrPopDialog(XpAccessPoint xpAccessPoint, XpWifiManager.PopupPwdDialogCallback popupPwdDialogCallback) {
        return this.mWifiManager.connectOrPopDialog(xpAccessPoint, popupPwdDialogCallback);
    }
}
