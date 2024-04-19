package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.about.IRestoreCheckCallback;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.ui.dialog.RestoreCheckedDialog;
/* loaded from: classes.dex */
public class RestoreCheckedDialogWork implements WorkService, IRestoreCheckCallback {
    public static final String ACTION = "com.xiaopeng.intent.action.RESTORE_CHECK";
    private Context mContext;
    private RestoreCheckedDialog mXDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDialog() {
        if (this.mXDialog == null) {
            this.mXDialog = new RestoreCheckedDialog(this, this.mContext);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && ACTION.equals(intent.getAction())) {
            Logs.d("RestoreCheckedDialogWork action");
            showRestoreCheckedDialog();
        }
    }

    private void showRestoreCheckedDialog() {
        initDialog();
        if (!CarSettingsManager.getInstance().isEbsBatterySatisfied()) {
            this.mXDialog.showRestoreWarning(2);
        } else if (!CarSettingsManager.getInstance().isCarGearP()) {
            this.mXDialog.showRestoreWarning(1);
        } else {
            this.mXDialog.showSafetyTips();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.IRestoreCheckCallback
    public void reChecked(final int i) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork.1
            @Override // java.lang.Runnable
            public void run() {
                RestoreCheckedDialogWork.this.initDialog();
                if (i == 3) {
                    RestoreCheckedDialogWork.this.mXDialog.showSafetyTips();
                } else {
                    RestoreCheckedDialogWork.this.mXDialog.showRestoreWarning(i);
                }
            }
        }, 50L);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        RestoreCheckedDialog restoreCheckedDialog = this.mXDialog;
        if (restoreCheckedDialog != null) {
            restoreCheckedDialog.dismiss();
            this.mXDialog = null;
        }
        Logs.d("RestoreCheckedDialogWork onDestroy");
    }
}
