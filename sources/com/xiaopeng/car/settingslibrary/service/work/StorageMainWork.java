package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.activity.PopupStorageActivity;
import com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageMainDialog;
/* loaded from: classes.dex */
public class StorageMainWork implements WorkService {
    IntervalControl intervalControl;
    private Context mContext;
    private StorageMainDialog storageMainDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        this.intervalControl = new IntervalControl("StorageMainWork");
    }

    private void initDialog(String str) {
        if (CarFunction.isXmartOS5()) {
            PopupStorageActivity.start(this.mContext, str);
            return;
        }
        this.storageMainDialog = new StorageMainDialog(this.mContext, str);
        this.storageMainDialog.show();
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent == null || this.intervalControl.isFrequently(1000)) {
            return;
        }
        String action = intent.getAction();
        if (Config.CLOSE_POPUP_DIALOG.equals(action)) {
            if (Config.POPUP_STORAGE.equals(intent.getStringExtra(Config.EXTRA_WHICH_DIALOG))) {
                if (CarFunction.isXmartOS5()) {
                    finishStorageActivity(context);
                    return;
                }
                StorageMainDialog storageMainDialog = this.storageMainDialog;
                if (storageMainDialog != null) {
                    storageMainDialog.dismiss();
                }
            }
        } else if (Config.POPUP_STORAGE.equals(action)) {
            Logs.d("StorageMainWork action");
            initDialog(intent.getStringExtra(Config.EXTRA_POPUP_SOURCE));
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        Logs.d("RestoreCheckedDialogWork onDestroy");
        if (CarFunction.isXmartOS5()) {
            finishStorageActivity(context);
        } else {
            this.storageMainDialog.dismiss();
        }
    }

    public static void finishStorageActivity(Context context) {
        if (context == null) {
            return;
        }
        Logs.d("finishStorageActivity");
        Intent intent = new Intent();
        intent.setAction(Config.POPUP_FINISH_ACTION);
        context.sendBroadcast(intent);
    }
}
