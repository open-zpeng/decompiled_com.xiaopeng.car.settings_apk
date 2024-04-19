package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.speech.ISpeechContants;
/* loaded from: classes.dex */
public class BluetoothPageAction implements PageAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        String str;
        Logs.d("BluetoothPageAction doAction:" + uri);
        try {
            str = uri.getQueryParameter(ISpeechContants.KEY_SCREEN_ID);
        } catch (Exception e) {
            Logs.d("getQueryParameter: " + e);
            str = null;
        }
        Logs.d("bluetooth page shareId:" + str);
        if ("0".equals(str)) {
            AppServerManager.getInstance().startPopDialog("bluetooth", 0);
        } else if ("1".equals(str)) {
            AppServerManager.getInstance().startPopDialog("bluetooth", 1);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public boolean isShowing(Context context, Uri uri) {
        return AppServerManager.getInstance().isShowingPopupDialog(Config.POPUP_BLUETOOTH);
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void closePage(Context context, Uri uri) {
        AppServerManager.getInstance().closePopupDialog(Config.POPUP_BLUETOOTH);
    }
}
