package com.xiaopeng.car.settingslibrary.direct.support;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues;
/* loaded from: classes.dex */
public class KeyParkingCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction
    public boolean checkSupport() {
        NetConfigValues netConfigValues = LaboratoryManager.getInstance().getNetConfigValues();
        if (CarFunction.isSupportRemotePark()) {
            return netConfigValues.isRemoteControlEnable();
        }
        if (CarFunction.isSupportKeyPark()) {
            return netConfigValues.isRemoteControlKeyEnable();
        }
        return false;
    }
}
