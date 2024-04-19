package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes.dex */
public class UrgentPowerWork implements WorkService {
    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && Config.EMERGENCY_IG_OFFACTION.equals(intent.getAction())) {
            Logs.d("UrgentPowerWork action");
            context.getString(R.string.urgent_power_title);
            XToast.show(context.getString(R.string.urgent_power_msg), 0, 0, R.drawable.ic_mid_powerfailure);
        }
    }
}
