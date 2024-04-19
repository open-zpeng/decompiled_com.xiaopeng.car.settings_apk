package com.xiaopeng.car.settingslibrary.interfaceui;

import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.WifiUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.WifiBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.WifiKeyBean;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import com.xiaopeng.car.settingslibrary.vm.wifi.WifiSettingsViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class WifiServerManager extends ServerBaseManager implements WlanResultWork.WlanResultListener {
    private static final String TAG = "WifiServerManager";
    private CopyOnWriteArrayList<XpAccessPoint> mApList = new CopyOnWriteArrayList<>();
    private WifiSettingsViewModel mWifiSettingsViewModel;

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final WifiServerManager instance = new WifiServerManager();

        private InnerFactory() {
        }
    }

    public static WifiServerManager get_instance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        debugLog("WifiServerManager observeData ");
        getViewModel().getWifiListLiveData().postValue(null);
        getViewModel().getWifiListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$WifiServerManager$uvjtJ6hjAXRsoULPsEpN1J1_IQ8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WifiServerManager.this.lambda$observeData$0$WifiServerManager((List) obj);
            }
        });
        getViewModel().getWifiStatusLiveData().setValue(-1);
        getViewModel().getWifiStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$WifiServerManager$Oeb5T-tT1CGF4mgPUtV9RaDc3N8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WifiServerManager.this.lambda$observeData$1$WifiServerManager((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$WifiServerManager(List list) {
        debugLog("WifiServerManager onAccessPointsChanged " + list);
        if (list == null) {
            return;
        }
        wifiCallback(InterfaceConstant.ON_ACCESS_POINT_CHANGED, "");
    }

    public /* synthetic */ void lambda$observeData$1$WifiServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("WifiServerManager onWifiStateChanged " + num);
        wifiCallback(InterfaceConstant.ON_WIFI_STATE_CHANGED, String.valueOf(num));
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized WifiSettingsViewModel getViewModel() {
        if (this.mWifiSettingsViewModel == null) {
            this.mWifiSettingsViewModel = new WifiSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mWifiSettingsViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        debugLog("WifiServerManager startVm ");
        start();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        debugLog("WifiServerManager stopVm ");
        stop();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        debugLog("WifiServerManager enterScene ");
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        debugLog("WifiServerManager exitScene ");
        super.exitScene();
    }

    public void setEnable(boolean z) {
        log("WifiServerManager setEnable isEnable:" + z);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.WLAN_PAGE_ID, "B001", z);
        getViewModel().setEnable(z);
    }

    public int getWifiState() {
        log("WifiServerManager getWifiState ");
        return getViewModel().getWifiStateAp();
    }

    public List<WifiBean> getAllAccessPoints() {
        List<XpAccessPoint> wifiListAp = getViewModel().getWifiListAp();
        log("WifiServerManager getAllAccessPoints " + wifiListAp.size());
        this.mApList.clear();
        this.mApList.addAll(sortAps(wifiListAp));
        return apsToWifiBean(this.mApList);
    }

    private List<XpAccessPoint> sortAps(List<XpAccessPoint> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                XpAccessPoint xpAccessPoint = list.get(i);
                if (xpAccessPoint.isConnected()) {
                    arrayList2.add(xpAccessPoint);
                } else if (xpAccessPoint.isConnecting()) {
                    arrayList3.add(xpAccessPoint);
                } else if (xpAccessPoint.isSaved() && !WifiUtils.isAccessPointDisabledByWrongPassword(xpAccessPoint)) {
                    arrayList4.add(xpAccessPoint);
                } else {
                    arrayList5.add(xpAccessPoint);
                }
            }
        }
        arrayList.clear();
        if (arrayList2.size() > 0) {
            arrayList.addAll(arrayList2);
        }
        if (arrayList3.size() > 0) {
            arrayList.addAll(arrayList3);
        }
        if (arrayList4.size() > 0) {
            arrayList.addAll(arrayList4);
        }
        if (arrayList5.size() > 0) {
            arrayList.addAll(arrayList5);
        }
        return arrayList;
    }

    private List<WifiBean> apsToWifiBean(CopyOnWriteArrayList<XpAccessPoint> copyOnWriteArrayList) {
        CopyOnWriteArrayList copyOnWriteArrayList2 = new CopyOnWriteArrayList();
        Iterator<XpAccessPoint> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            XpAccessPoint next = it.next();
            WifiBean wifiBean = new WifiBean();
            wifiBean.setBssid(next.getBssid());
            wifiBean.setSsid(next.getSsid().toString());
            wifiBean.setConnected(next.isConnected());
            wifiBean.setConnecting(next.isConnecting());
            wifiBean.setLevel(next.getLevel());
            wifiBean.setSaved(next.isSaved());
            wifiBean.setSecurity(next.getSecurity());
            wifiBean.setSecurityString(next.getSecurityString());
            log("WifiServerManager wifiBean:" + wifiBean);
            copyOnWriteArrayList2.add(wifiBean);
        }
        return copyOnWriteArrayList2;
    }

    private WifiBean apToWifiBean(XpAccessPoint xpAccessPoint) {
        WifiBean wifiBean = new WifiBean();
        wifiBean.setBssid(xpAccessPoint.getBssid());
        wifiBean.setSsid(xpAccessPoint.getSsid().toString());
        wifiBean.setConnected(xpAccessPoint.isConnected());
        wifiBean.setConnecting(xpAccessPoint.isConnecting());
        wifiBean.setLevel(xpAccessPoint.getLevel());
        wifiBean.setSaved(xpAccessPoint.isSaved());
        wifiBean.setSecurity(xpAccessPoint.getSecurity());
        wifiBean.setSecurityString(xpAccessPoint.getSecurityString());
        log("WifiServerManager apToWifiBean:" + wifiBean);
        return wifiBean;
    }

    public boolean isEnable() {
        boolean isEnable = getViewModel().isEnable();
        log("WifiServerManager isEnable " + isEnable);
        return isEnable;
    }

    private void start() {
        log("WifiServerManager start");
        getViewModel().start();
    }

    private void stop() {
        log("WifiServerManager stop");
        getViewModel().stop();
    }

    public void connectToPublicWifi(String str, int i, String str2) {
        log("WifiServerManager connectToPublicWifi ssid:" + str + " security:" + i + " bssid:" + str2);
        getViewModel().connect(getAp(str, i, str2), null);
    }

    private XpAccessPoint getAp(String str, int i, String str2) {
        for (XpAccessPoint xpAccessPoint : getViewModel().getWifiListAp()) {
            log("WifiServerManager getAp ssid:" + str + " security:" + i + " bssid:" + str2 + " ap:" + xpAccessPoint.getKey());
            if ((!TextUtils.isEmpty(xpAccessPoint.getSsid()) && xpAccessPoint.getSsid().equals(str) && xpAccessPoint.getSecurity() == i) || (!TextUtils.isEmpty(xpAccessPoint.getBssid()) && xpAccessPoint.getBssid().equals(str2) && xpAccessPoint.getSecurity() == i)) {
                return xpAccessPoint;
            }
        }
        return null;
    }

    private String getKey(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
        } else {
            sb.append(str);
        }
        sb.append(',');
        sb.append(i);
        return sb.toString();
    }

    public void connect(String str, int i, String str2) {
        log("WifiServerManager connect ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            getViewModel().connect(ap, new XpWifiManager.PopupPwdDialogCallback() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$WifiServerManager$fa5OYVUQOVzkDti4ZEgg8J9VvR0
                @Override // com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager.PopupPwdDialogCallback
                public final void popupPwdDialog(XpAccessPoint xpAccessPoint) {
                    WifiServerManager.this.lambda$connect$2$WifiServerManager(xpAccessPoint);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showPwdDialog */
    public void lambda$connect$2$WifiServerManager(XpAccessPoint xpAccessPoint) {
        debugLog("WifiServerManager OnShowPwdDialog " + xpAccessPoint.getSsid());
        wifiCallback(InterfaceConstant.ON_WIFI_SHOW_PWD_DIALOG, JsonUtils.toJSONString(apToWifiBean(xpAccessPoint)));
    }

    public boolean isEditPwdBeforeConnect(String str, int i, String str2) {
        log("WifiServerManager isEditPwdBeforeConnect ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            return this.mWifiSettingsViewModel.isEditPwdBeforeConnect(ap);
        }
        return false;
    }

    public void forgetAP(String str, int i, String str2) {
        log("WifiServerManager forgetAP ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            getViewModel().forgetAP(ap);
        }
    }

    public boolean addAccessPoint(String str, String str2, String str3, String str4, int i) {
        log("WifiServerManager addAccessPoint ssid:" + str2 + " security:" + i + " bssid:" + str + " identify:" + str3 + " pwd:" + str4);
        XpAccessPoint ap = getAp(str2, i, str);
        WlanResultWork.getInstance().launchTimeout();
        return getViewModel().addAccessPoint(CarSettingsApp.getContext(), ap, str2, str3, str4, i);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onResultReceiveFail(String str, int i) {
        debugLog("WifiServerManager notifyWlanResultError " + str + " " + i);
        wifiCallback(InterfaceConstant.ON_RESULT_RECEIVE_FAIL, JsonUtils.toJSONString(new WifiKeyBean(str, i)));
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onResultSuccess(String str) {
        debugLog("WifiServerManager notifyWlanResultSuccess " + str);
        wifiCallback(InterfaceConstant.ON_RESULT_SUCCESS, str);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onRefreshSummaryMsg(String str, String str2, int i) {
        debugLog("WifiServerManager onRefreshSummaryMsg ssid:" + str + " summary:" + str2 + " error:" + i);
        onResultReceiveFail(str, i);
        WlanResultWork.getInstance().removeTimeout(str);
    }

    public void enterWifiPwdDialog(String str, int i, String str2) {
        WlanResultWork.getInstance().setTargetAccessPoint(getAp(str, i, str2));
        WlanResultWork.getInstance().addWlanResultListener(str, this);
    }

    public void exitWifiPwdDialog() {
        WlanResultWork.getInstance().removeWlanResultListener(this);
        WlanResultWork.getInstance().setTargetAccessPoint(null);
    }

    public boolean isSaved(String str, int i, String str2) {
        log("WifiServerManager isSaved ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            boolean isSaved = ap.isSaved();
            debugLog("WifiServerManager isSaved:" + isSaved + " ssid:" + str);
            return isSaved;
        }
        return false;
    }

    public boolean isEverConnected(String str, int i, String str2) {
        log("WifiServerManager isEverConnected ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            boolean isEverConnected = ap.isEverConnected();
            debugLog("WifiServerManager isEverConnected:" + isEverConnected + " ssid:" + str);
            return isEverConnected;
        }
        return false;
    }

    public boolean isAccessPointDisabledByWrongPassword(String str, int i, String str2) {
        log("WifiServerManager isAccessPointDisabledByWrongPassword ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            boolean isAccessPointDisabledByWrongPassword = WifiUtils.isAccessPointDisabledByWrongPassword(ap);
            log("WifiServerManager isAccessPointDisabledByWrongPassword ssid:" + str + " wpwd:" + isAccessPointDisabledByWrongPassword);
            return isAccessPointDisabledByWrongPassword;
        }
        return false;
    }

    public boolean isPasspoint(String str, int i, String str2) {
        log("WifiServerManager isPasspoint ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        if (ap != null) {
            return ap.isPasspoint();
        }
        return false;
    }

    public boolean isXPAuto(String str, int i, String str2) {
        log("WifiServerManager isXPAuto ssid:" + str + " security:" + i + " bssid:" + str2);
        XpAccessPoint ap = getAp(str, i, str2);
        return ap != null && Config.XP_AUTO.equals(ap.getSsid()) && ap.getSecurity() == 3;
    }

    public void connectXpAuto() {
        log("WifiServerManager connectXpAuto");
        XpAutoManualManager.getInstance().checkAndConnect();
    }
}
