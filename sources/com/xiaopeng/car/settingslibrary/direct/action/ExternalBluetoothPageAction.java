package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
/* loaded from: classes.dex */
public class ExternalBluetoothPageAction implements PageAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        AppServerManager.getInstance().startPopDialog("externalBluetooth", 1);
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public boolean isShowing(Context context, Uri uri) {
        if (CarFunction.isSupportDoubleScreen()) {
            return AppServerManager.getInstance().isShowingPopupDialog(Config.BACK_SEAT_BLUETOOTH);
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void closePage(Context context, Uri uri) {
        if (CarFunction.isSupportDoubleScreen()) {
            AppServerManager.getInstance().closePopupDialog(Config.BACK_SEAT_BLUETOOTH);
        }
    }
}
