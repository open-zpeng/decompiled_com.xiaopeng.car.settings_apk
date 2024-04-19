package com.xiaopeng.car.settingslibrary.direct.support;

import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
/* loaded from: classes.dex */
public class DriverVipCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction
    public boolean checkSupport() {
        return CarConfigHelper.hasMainDriverVIP();
    }
}
