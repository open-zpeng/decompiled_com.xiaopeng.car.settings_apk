package com.xiaopeng.car.settingslibrary.service.work.dialog;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.service.work.WorkService;
import com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogRequest;
import com.xiaopeng.xui.app.XDialog;
import java.util.HashMap;
/* loaded from: classes.dex */
public class XDialogWork implements WorkService {
    public static final String EXTRA_KEY = "dialog.info";
    private HashMap<String, XDialogRequest> mXDialogRequests = new HashMap<>();

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        context.setTheme(R.style.AppTheme);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        XDialogInfo xDialogInfo;
        if (intent == null || !Config.XUI_DIALOG_ACTION.equals(intent.getAction()) || (xDialogInfo = (XDialogInfo) intent.getParcelableExtra(EXTRA_KEY)) == null) {
            return;
        }
        Logs.d("XDialogWork dialogRequests size " + this.mXDialogRequests.toString());
        XDialogRequest xDialogRequest = this.mXDialogRequests.get(xDialogInfo.id);
        if (xDialogRequest != null) {
            xDialogRequest.setOnDismissListener(null);
            xDialogRequest.dismiss();
        }
        XDialogRequest xDialogRequest2 = new XDialogRequest(xDialogInfo, new XDialog(context));
        this.mXDialogRequests.put(xDialogInfo.id, xDialogRequest2);
        xDialogRequest2.setOnDismissListener(new XDialogRequest.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.dialog.-$$Lambda$XDialogWork$z0GSln7m11iBw7b8zmKbgnCpYsw
            @Override // com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogRequest.OnDismissListener
            public final void onDismiss(XDialogInfo xDialogInfo2) {
                XDialogWork.this.lambda$onStartCommand$0$XDialogWork(xDialogInfo2);
            }
        });
        xDialogRequest2.show();
    }

    public /* synthetic */ void lambda$onStartCommand$0$XDialogWork(XDialogInfo xDialogInfo) {
        Logs.d("XDialogWork onDismiss " + xDialogInfo.id);
        this.mXDialogRequests.remove(xDialogInfo.id);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        for (XDialogRequest xDialogRequest : this.mXDialogRequests.values()) {
            xDialogRequest.dismiss();
        }
        this.mXDialogRequests.clear();
        Logs.d("XDialogWork onDestroy");
    }
}
