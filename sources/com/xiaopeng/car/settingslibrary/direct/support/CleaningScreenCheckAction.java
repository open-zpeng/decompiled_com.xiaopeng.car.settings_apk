package com.xiaopeng.car.settingslibrary.direct.support;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
/* loaded from: classes.dex */
public class CleaningScreenCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarSettingsManager.getInstance().isCarGearP()) {
            String requestEnterUserScenario = XuiSettingsManager.getInstance().requestEnterUserScenario(XuiSettingsManager.USER_SCENARIO_CINEMA_MODE, 0);
            Logs.d("CleaningScreenCheckAction ret:" + requestEnterUserScenario);
            if (XuiSettingsManager.RET_SUCCESS.equals(requestEnterUserScenario)) {
                return true;
            }
        }
        return false;
    }
}
