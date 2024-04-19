package com.xiaopeng.car.settingslibrary.direct.support;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
/* loaded from: classes.dex */
public class LeaveLockCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction
    public boolean checkSupport() {
        return LaboratoryManager.getInstance().getNetConfigValues().isLeaveLockEnable() && CarFunction.isSupportLeaveLock();
    }
}
