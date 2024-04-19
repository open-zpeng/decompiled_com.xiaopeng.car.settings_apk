package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* loaded from: classes.dex */
public class AuthModeWork implements WorkService {
    private XDialog mXDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (Config.AUTH_MODE_ACTION_CLOSE.equals(action)) {
            Logs.d("AuthModeWork ACTION_CLOSE ");
            XDialog xDialog = this.mXDialog;
            if (xDialog != null) {
                xDialog.dismiss();
            }
        } else if (Config.AUTH_MODE_ACTION.equals(action)) {
            String stringExtra = intent.getStringExtra(Config.AUTH_MODE_EXTRA_KEY);
            Logs.d("AuthModeWork time: " + stringExtra);
            if (TextUtils.isEmpty(stringExtra)) {
                return;
            }
            if (this.mXDialog == null) {
                context.setTheme(R.style.AppTheme);
                this.mXDialog = new XDialog(context);
                this.mXDialog.setSystemDialog(2008);
                this.mXDialog.setScreenId(0);
                this.mXDialog.setTitle(context.getString(R.string.auth_mode_dialog_title)).setMessage(context.getString(R.string.auth_mode_dialog_msg, stringExtra)).setPositiveButton(context.getString(R.string.auth_mode_dialog_quit), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$AuthModeWork$RBshK7nfTi7NPXx2eKsnYikh2Zk
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog2, int i) {
                        AppServerManager.getInstance().exitAuthMode();
                    }
                }).setNegativeButton(context.getString(R.string.cancel));
                this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$AuthModeWork$CYPzIJbVUJEsDPCc32ZRKialwLI
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AuthModeWork.this.lambda$onStartCommand$1$AuthModeWork(dialogInterface);
                    }
                });
            }
            XDialog xDialog2 = this.mXDialog;
            if (xDialog2 != null) {
                xDialog2.show();
            }
        }
    }

    public /* synthetic */ void lambda$onStartCommand$1$AuthModeWork(DialogInterface dialogInterface) {
        Logs.d("AuthModeWork onDismiss");
        this.mXDialog = null;
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mXDialog = null;
        }
        Logs.d("AuthModeWork onDestroy");
    }
}
