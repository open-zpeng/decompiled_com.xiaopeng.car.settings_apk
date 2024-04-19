package com.xiaopeng.car.settingslibrary.ui.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.InputDialogWork;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog;
import com.xiaopeng.car.settingslibrary.vm.wifi.WifiSettingsViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* loaded from: classes.dex */
public class WifiOperation {
    private Context mContext;
    private IntervalControl mIntervalControl = new IntervalControl("wifi-dialog");
    private WifiSettingsViewModel mWifiSettingsViewModel;
    private XDialog mXDialog;

    public WifiOperation(Context context) {
        this.mContext = context;
    }

    public boolean connect(XpAccessPoint xpAccessPoint) {
        Logs.d("xpwifi click connect accessPoint:" + xpAccessPoint);
        if (xpAccessPoint.isConnecting()) {
            return false;
        }
        if (isXPAuto(xpAccessPoint)) {
            XpAutoManualManager.getInstance().checkAndConnect();
            return true;
        } else if (isXPCheck(xpAccessPoint)) {
            XpAutoManualManager.getInstance().checkAndConnectXpCheck();
            return true;
        } else if (this.mWifiSettingsViewModel.connectOrPopDialog(xpAccessPoint, new XpWifiManager.PopupPwdDialogCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$WifiOperation$e1vZsBZh3BSsAA47wGykn6SdcKk
            @Override // com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager.PopupPwdDialogCallback
            public final void popupPwdDialog(XpAccessPoint xpAccessPoint2) {
                WifiOperation.this.lambda$connect$0$WifiOperation(xpAccessPoint2);
            }
        })) {
            IntervalControl intervalControl = this.mIntervalControl;
            if (intervalControl != null && intervalControl.isFrequently(500)) {
                Logs.d("wifi dialog pop frequently!");
                return false;
            }
            lambda$connect$0$WifiOperation(xpAccessPoint);
            return true;
        } else {
            return true;
        }
    }

    public boolean isApSaved(XpAccessPoint xpAccessPoint) {
        return this.mWifiSettingsViewModel.isApSaved(xpAccessPoint);
    }

    public void showDevicePopup(final XpAccessPoint xpAccessPoint, final Runnable runnable) {
        if (this.mXDialog == null) {
            this.mXDialog = new XDialog(this.mContext);
            if (CarFunction.isNonSelfPageUI()) {
                this.mXDialog.setSystemDialog(2008);
            }
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.wlan_wifi_popup_device_detail, (ViewGroup) null);
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.wifi_signals);
        TextView textView = (TextView) inflate.findViewById(R.id.signal_strength);
        TextView textView2 = (TextView) inflate.findViewById(R.id.security);
        if (xpAccessPoint != null) {
            textView.setText(stringArray[xpAccessPoint.getLevel()]);
            textView2.setText(xpAccessPoint.getSecurityString());
        }
        this.mXDialog.setScreenId(0);
        this.mXDialog.setTitle(xpAccessPoint.getSsid()).setPositiveButton(this.mContext.getString(R.string.wlan_popup_ignore_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$WifiOperation$0sCxEs5nWOTno0kA4Tp49EkVlnU
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                WifiOperation.this.lambda$showDevicePopup$1$WifiOperation(xpAccessPoint, runnable, xDialog, i);
            }
        }).setNegativeButton(this.mContext.getString(R.string.cancel), (XDialogInterface.OnClickListener) null).setCustomView(inflate).show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_WIFI_DETAIL_DIALOG);
    }

    public /* synthetic */ void lambda$showDevicePopup$1$WifiOperation(XpAccessPoint xpAccessPoint, Runnable runnable, XDialog xDialog, int i) {
        this.mWifiSettingsViewModel.forgetAP(xpAccessPoint);
        ThreadUtils.postOnMainThreadDelay(runnable, 500L);
    }

    public void dismissDialog() {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }

    public void cleanDialog() {
        this.mXDialog = null;
    }

    public boolean isXPAuto(XpAccessPoint xpAccessPoint) {
        return Config.XP_AUTO.equals(xpAccessPoint.getSsid()) && xpAccessPoint.getSecurity() == 3;
    }

    public boolean isXPCheck(XpAccessPoint xpAccessPoint) {
        return Config.XP_CHECK.equals(xpAccessPoint.getSsid()) && xpAccessPoint.getSecurity() == 3 && CarSettingsManager.getInstance().isInFactoryMode();
    }

    public void refreshIcon(XpAccessPoint xpAccessPoint, ImageView imageView) {
        if (xpAccessPoint.getSecurity() == 0 || isXPAuto(xpAccessPoint) || isXPCheck(xpAccessPoint)) {
            imageView.setImageResource(R.drawable.wifi_signal);
        } else {
            imageView.setImageResource(R.drawable.wifi_signal_lock);
        }
        Drawable drawable = imageView.getDrawable();
        drawable.setLevel(xpAccessPoint.getLevel());
        drawable.invalidateSelf();
    }

    public void setViewModel(WifiSettingsViewModel wifiSettingsViewModel) {
        this.mWifiSettingsViewModel = wifiSettingsViewModel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showPassPopup */
    public void lambda$connect$0$WifiOperation(XpAccessPoint xpAccessPoint) {
        Intent intent = new Intent(this.mContext, UIGlobalService.class);
        intent.setAction(InputDialogWork.INPUT_DIALOG);
        intent.putExtra(InputDialog.EXTRA_INPUT_TYPE, 0);
        intent.putExtra(InputDialog.EXTRA_REQUEST_TITLE_NAME, xpAccessPoint.getSsid().toString());
        intent.putExtra(InputDialog.EXTRA_REQUEST_WLAN_SECURITY, xpAccessPoint.getSecurity());
        this.mContext.startService(intent);
        WlanResultWork.getInstance().setTargetAccessPoint(xpAccessPoint);
    }
}
