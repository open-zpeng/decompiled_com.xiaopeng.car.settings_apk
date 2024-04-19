package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.UsbBean;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.usb.XpUsbDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class UsbServerManager extends ServerBaseManager {
    private static final String TAG = "UsbServerManager";
    private UsbSettingsViewModel mUsbSettingViewModel;

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final UsbServerManager instance = new UsbServerManager();

        private InnerFactory() {
        }
    }

    public static UsbServerManager get_instance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getListLiveData().postValue(null);
        getViewModel().getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$UsbServerManager$le9EsAjKAv7HVorLQNoaDKUo8Ls
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UsbServerManager.this.lambda$observeData$0$UsbServerManager((List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$UsbServerManager(List list) {
        String str;
        if (list == null) {
            return;
        }
        debugLog("UsbServerManager onUsbListChanged");
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            XpUsbDeviceInfo xpUsbDeviceInfo = (XpUsbDeviceInfo) it.next();
            UsbBean usbBean = new UsbBean();
            usbBean.setCapacity(xpUsbDeviceInfo.getCapacity());
            usbBean.setCategory(xpUsbDeviceInfo.getCategory());
            usbBean.setConnected(xpUsbDeviceInfo.isConnected());
            usbBean.setDeviceName(xpUsbDeviceInfo.getDeviceName());
            usbBean.setOfficial(xpUsbDeviceInfo.isOfficial());
            usbBean.setUsedSpace(xpUsbDeviceInfo.getUsedSpace());
            String str2 = "-- GB";
            if (xpUsbDeviceInfo.isConnected()) {
                str2 = Utils.getFormatSize(xpUsbDeviceInfo.getCapacity());
                str = Utils.getFormatSize(xpUsbDeviceInfo.getUsedSpace());
            } else {
                str = "-- GB";
            }
            if (xpUsbDeviceInfo.isOfficial()) {
                usbBean.setDeviceName(CarSettingsApp.getContext().getString(R.string.sm_device_panel_officialmic));
            }
            usbBean.setCapacityContent(str.replaceAll(" ", "") + MqttTopic.TOPIC_LEVEL_SEPARATOR + str2.replaceAll(" ", ""));
            arrayList.add(usbBean);
        }
        usbCallback(InterfaceConstant.ON_USB_LIST_CHANGED, JsonUtils.toJSONString(arrayList));
        if (arrayList.size() == 0) {
            DataRepository.getInstance().setUsbSettingProvider(CarSettingsApp.getContext(), "xp_usb_status", false);
        } else {
            DataRepository.getInstance().setUsbSettingProvider(CarSettingsApp.getContext(), "xp_usb_status", true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void init() {
        getViewModel();
    }

    private synchronized UsbSettingsViewModel getViewModel() {
        if (this.mUsbSettingViewModel == null) {
            this.mUsbSettingViewModel = new UsbSettingsViewModel(CarSettingsApp.getApp());
        }
        return this.mUsbSettingViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("UsbServerManager startVm");
        registerListener();
        startQuery();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("UsbServerManager stopVm");
        unRegisterListener();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    private void registerListener() {
        getViewModel().registerBroadcast();
        getViewModel().registerListener();
        getViewModel().registerOfficialKaraokPowerListener();
    }

    public /* synthetic */ void lambda$startQuery$1$UsbServerManager() {
        getViewModel().startQuery();
    }

    public void startQuery() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$UsbServerManager$a9GtTeyw1QAR-dwZOwVLId5bQZY
            @Override // java.lang.Runnable
            public final void run() {
                UsbServerManager.this.lambda$startQuery$1$UsbServerManager();
            }
        });
    }

    private void unRegisterListener() {
        getViewModel().unRegisterListener();
        getViewModel().unRegisterBroadcast();
        getViewModel().unRegisterOfficiallKaraokPowerListener();
        getViewModel().clear();
    }
}
