package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* loaded from: classes.dex */
public class RepairWork implements WorkService {
    private XDialog mXDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent != null && Config.REPAIR_REQUEST_QUIT_ACTION.equals(intent.getAction())) {
            Logs.d("RepairWork action");
            if (this.mXDialog == null) {
                context.setTheme(R.style.AppTheme);
                this.mXDialog = new XDialog(context);
                this.mXDialog.setSystemDialog(2008);
                this.mXDialog.setScreenId(0);
                this.mXDialog.setTitle(context.getString(R.string.repair_dialog_title)).setMessage(context.getString(R.string.repair_dialog_msg)).setPositiveButton(context.getString(R.string.confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$RepairWork$GxHrlUL2CasJvsfGjGb2_W1KNF0
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        AppServerManager.getInstance().exitRepairMode();
                    }
                }).setNegativeButton(context.getString(R.string.cancel));
                this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$RepairWork$foQ6UDScBi84SLiZry9zFJjb3YA
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        RepairWork.this.lambda$onStartCommand$1$RepairWork(dialogInterface);
                    }
                });
            }
            XDialog xDialog = this.mXDialog;
            if (xDialog != null) {
                xDialog.show();
            }
        }
    }

    public /* synthetic */ void lambda$onStartCommand$1$RepairWork(DialogInterface dialogInterface) {
        Logs.d("RepairWork onDismiss");
        this.mXDialog = null;
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mXDialog = null;
        }
        Logs.d("RepairWork onDestroy");
    }
}
