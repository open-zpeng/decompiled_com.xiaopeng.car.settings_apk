package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog;
/* loaded from: classes.dex */
public class InputDialogWork implements WorkService {
    public static final String INPUT_DIALOG = "xiaopeng.settings.input.dialog";

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && INPUT_DIALOG.equals(intent.getAction())) {
            Logs.d("InputDialogWork action");
            new InputDialog(context.getApplicationContext(), intent).show();
        }
    }
}
