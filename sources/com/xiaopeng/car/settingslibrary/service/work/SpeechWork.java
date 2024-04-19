package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
/* loaded from: classes.dex */
public class SpeechWork implements WorkService {
    private Context mContext;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        Logs.d("xpservice subscribe speech");
        if (Utils.isUserRelease()) {
            return;
        }
        SystemProperties.set(Config.FEEDBACK_PROPERTITY, OOBEEvent.STRING_TRUE);
        SystemProperties.set(Config.LABORATORY_PROPERTITY, OOBEEvent.STRING_TRUE);
    }
}
