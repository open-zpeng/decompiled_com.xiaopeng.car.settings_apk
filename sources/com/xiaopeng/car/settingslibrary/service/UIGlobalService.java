package com.xiaopeng.car.settingslibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.ArraySet;
import com.xiaopeng.car.settings.service.work.AutoPowerOffWork;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.service.work.BluetoothPairDialogWork;
import com.xiaopeng.car.settingslibrary.service.work.DropdownPopDialogWork;
import com.xiaopeng.car.settingslibrary.service.work.InputDialogWork;
import com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork;
import com.xiaopeng.car.settingslibrary.service.work.SoundEffectWork;
import com.xiaopeng.car.settingslibrary.service.work.StorageMainWork;
import com.xiaopeng.car.settingslibrary.service.work.WorkService;
import com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogWork;
import java.util.Iterator;
/* loaded from: classes.dex */
public class UIGlobalService extends Service {
    private ArraySet<WorkService> mWorkServices;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void init() {
        this.mWorkServices = new ArraySet<>();
        this.mWorkServices.add(new BluetoothPairDialogWork());
        this.mWorkServices.add(new AutoPowerOffWork());
        this.mWorkServices.add(new InputDialogWork());
        this.mWorkServices.add(new SoundEffectWork());
        this.mWorkServices.add(new RestoreCheckedDialogWork());
        this.mWorkServices.add(new StorageMainWork());
        this.mWorkServices.add(new XDialogWork());
        if (CarFunction.isNonSelfPageUI()) {
            this.mWorkServices.add(new DropdownPopDialogWork());
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        init();
        Logs.d("uiglobalservice onCreate");
        Iterator<WorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onCreate(this);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logs.d("uiglobalservice onDestroy");
        Iterator<WorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onDestroy(this);
        }
        this.mWorkServices.clear();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Logs.d("uiglobalservice onStartCommand");
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
}
