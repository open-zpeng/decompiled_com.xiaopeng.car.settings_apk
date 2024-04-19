package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
/* loaded from: classes.dex */
public class SetDeviceWork implements WorkService, IMcuChangeListener {
    Context mContext;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        CarSettingsManager.getInstance().addMcuChangeListener(this);
        Logs.d("xpservice SetDeviceWork onCreate");
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        CarSettingsManager.getInstance().removeMcuChangeListener(this);
        Logs.d("xpservice SetDeviceWork onDestroy");
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onIgOn() {
        if (CarFunction.isSupportRemotePark()) {
            CarSettingsManager.getInstance().reStoreD21KeyPark(this.mContext);
        }
        if (CarFunction.isSupportLCC()) {
            DataRepository.getInstance().setFollowedVehicleLostConfig(this.mContext, "2");
        }
        if (CarFunction.isSupportSoldierModeLevel()) {
            LaboratoryManager.getInstance().getSoldierThreshold();
        }
    }
}
