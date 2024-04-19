package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class StoragePageAction implements PageAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        AppServerManager.getInstance().startPopDialog("storage", 0, "voice");
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public boolean isShowing(Context context, Uri uri) {
        return AppServerManager.getInstance().isShowingPopupDialog(Config.POPUP_STORAGE);
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void closePage(Context context, Uri uri) {
        AppServerManager.getInstance().closePopupDialog(Config.POPUP_STORAGE);
    }
}
