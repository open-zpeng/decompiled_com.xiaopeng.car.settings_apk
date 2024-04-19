package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
/* loaded from: classes.dex */
public class LaboratoryWork implements WorkService {
    public static final String ACTION = "com.xiaopeng.intent.action.LABORATORY_CONFIG";
    private LaboratoryManager mLaboratoryManager;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mLaboratoryManager = LaboratoryManager.getInstance();
        this.mLaboratoryManager.registerEventBus();
        Logs.d("LaboratoryWork onCreate");
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && ACTION.equals(intent.getAction())) {
            Logs.d("LaboratoryWork action");
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.LaboratoryWork.1
                @Override // java.lang.Runnable
                public void run() {
                    LaboratoryWork.this.mLaboratoryManager.getAllConfiguration();
                }
            });
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        this.mLaboratoryManager.unregisterEventBus();
        Logs.d("LaboratoryWork onDestroy");
    }
}
