package com.xiaopeng.car.settingslibrary.service.work.dialog;

import android.app.PendingIntent;
import android.content.DialogInterface;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class XDialogRequest {
    private XDialogInfo info;
    private OnDismissListener mOnDismissListener;
    private XDialog mXDialog;

    /* loaded from: classes.dex */
    interface OnDismissListener {
        void onDismiss(XDialogInfo xDialogInfo);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XDialogRequest(XDialogInfo xDialogInfo, XDialog xDialog) {
        this.info = xDialogInfo;
        this.mXDialog = xDialog;
        init();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    private void init() {
        this.mXDialog.setSystemDialog(2008).setTitle(this.info.title).setMessage(this.info.msg).setCancelable(this.info.cancelable).setPositiveButton(this.info.positiveButton, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.dialog.-$$Lambda$XDialogRequest$d42CTIiL0jbWxYITnl-0mpE5GP8
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XDialogRequest.this.lambda$init$0$XDialogRequest(xDialog, i);
            }
        }).setNegativeButton(this.info.negativeButton, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.dialog.-$$Lambda$XDialogRequest$qRfDcGL3RP1rVMyPFznMu_6gHk0
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XDialogRequest.this.lambda$init$1$XDialogRequest(xDialog, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.dialog.-$$Lambda$XDialogRequest$9mzll7xIooPQTsCfPAPMZa-CG8A
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                XDialogRequest.this.lambda$init$2$XDialogRequest(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$XDialogRequest(XDialog xDialog, int i) {
        if (this.info.positiveIntent != null) {
            try {
                this.info.positiveIntent.send();
                Logs.log("XDialogWork-onClick", "positiveIntent " + this.info.positiveIntent.getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        Logs.d("XDialogWork positiveIntent");
    }

    public /* synthetic */ void lambda$init$1$XDialogRequest(XDialog xDialog, int i) {
        if (this.info.negativeIntent != null) {
            try {
                this.info.negativeIntent.send();
                Logs.log("XDialogWork-onClick", "negativeIntent " + this.info.negativeIntent.getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        Logs.d("XDialogWork negativeIntent");
    }

    public /* synthetic */ void lambda$init$2$XDialogRequest(DialogInterface dialogInterface) {
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this.info);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dismiss() {
        this.mXDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void show() {
        if (this.info.duration > 0) {
            int i = this.info.durationType;
            if (i == 0) {
                this.mXDialog.show(this.info.duration, 0);
                return;
            } else if (i == 1) {
                this.mXDialog.show(0, this.info.duration);
                return;
            } else {
                this.mXDialog.show();
                return;
            }
        }
        this.mXDialog.show();
    }
}
