package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.app.ActivityManagerFactory;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.speech.ISpeechContants;
/* loaded from: classes.dex */
public class NormalPageAction implements PageAction {
    static final String tag = "NormalPageAction";

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setData(uri);
        if (CarFunction.isSupportDoubleScreen()) {
            String str = null;
            try {
                str = uri.getQueryParameter(ISpeechContants.KEY_SCREEN_ID);
            } catch (Exception e) {
                Logs.log(tag, "getQueryParameter: " + e);
            }
            if ("0".equals(str)) {
                ActivityManagerFactory.startActivity(context, intent, 0);
                return;
            } else if ("1".equals(str)) {
                ActivityManagerFactory.startActivity(context, intent, 1);
                return;
            }
        }
        context.startActivity(intent);
    }
}
