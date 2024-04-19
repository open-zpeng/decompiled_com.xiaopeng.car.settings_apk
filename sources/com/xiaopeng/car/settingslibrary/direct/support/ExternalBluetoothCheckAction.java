package com.xiaopeng.car.settingslibrary.direct.support;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
/* loaded from: classes.dex */
public class ExternalBluetoothCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction
    public boolean checkSupport() {
        return CarFunction.isBackSeatScreen();
    }
}
