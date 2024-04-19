package com.xiaopeng.car.settingslibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.ArraySet;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.service.work.AuthModeWork;
import com.xiaopeng.car.settingslibrary.service.work.DarkBrightnessWork;
import com.xiaopeng.car.settingslibrary.service.work.GearChangeWork;
import com.xiaopeng.car.settingslibrary.service.work.HeadrestSwitchWork;
import com.xiaopeng.car.settingslibrary.service.work.LaboratoryWork;
import com.xiaopeng.car.settingslibrary.service.work.MonitorXpuWork;
import com.xiaopeng.car.settingslibrary.service.work.NFBluetoothWork;
import com.xiaopeng.car.settingslibrary.service.work.RepairWork;
import com.xiaopeng.car.settingslibrary.service.work.ScreenWork;
import com.xiaopeng.car.settingslibrary.service.work.SetDeviceWork;
import com.xiaopeng.car.settingslibrary.service.work.SpeechWork;
import com.xiaopeng.car.settingslibrary.service.work.UrgentPowerWork;
import com.xiaopeng.car.settingslibrary.service.work.UsbExitEnterWork;
import com.xiaopeng.car.settingslibrary.service.work.WifiKeyWork;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import com.xiaopeng.car.settingslibrary.service.work.WorkService;
import com.xiaopeng.car.settingslibrary.service.work.XpAutoWork;
import java.util.Iterator;
/* loaded from: classes.dex */
public class GlobalService extends Service {
    private ArraySet<WorkService> mWorkServices;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void init() {
        this.mWorkServices = new ArraySet<>();
        this.mWorkServices.add(new SpeechWork());
        this.mWorkServices.add(new LaboratoryWork());
        this.mWorkServices.add(new NFBluetoothWork());
        this.mWorkServices.add(new SetDeviceWork());
        this.mWorkServices.add(new MonitorXpuWork());
        this.mWorkServices.add(new GearChangeWork());
        this.mWorkServices.add(new RepairWork());
        this.mWorkServices.add(new AuthModeWork());
        this.mWorkServices.add(new UrgentPowerWork());
        this.mWorkServices.add(new HeadrestSwitchWork());
        this.mWorkServices.add(new DarkBrightnessWork());
        this.mWorkServices.add(new ScreenWork());
        this.mWorkServices.add(new XpAutoWork());
        this.mWorkServices.add(WlanResultWork.getInstance());
        this.mWorkServices.add(new UsbExitEnterWork());
        if (CarFunction.isSupportWifiKey()) {
            this.mWorkServices.add(new WifiKeyWork());
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        init();
        Logs.d("xpservice onCreate");
        Iterator<WorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onCreate(this);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Logs.d("xpservice onStartCommand");
        Iterator<WorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            try {
                it.next().onStartCommand(this, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logs.d("xpservice onDestroy");
        Iterator<WorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onDestroy(this);
        }
        this.mWorkServices.clear();
    }
}
