package com.xiaopeng.car.settingslibrary.ui.common;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.List;
/* loaded from: classes.dex */
public class ExternalBluetoothOperation {
    private Context mContext;
    private ExternalBluetoothDeviceInfo mDialogDeviceInfo;
    private ExternalBluetoothViewModel mExternalBluetoothViewModel;
    private IntervalControl mIntervalControl = new IntervalControl("bluetooth-click");
    private XDialog mXDialog;

    public ExternalBluetoothOperation(Context context) {
        this.mContext = context;
    }

    public void setViewModel(ExternalBluetoothViewModel externalBluetoothViewModel) {
        this.mExternalBluetoothViewModel = externalBluetoothViewModel;
    }

    public boolean connectingDevice(BaseAdapter.ViewHolder viewHolder, ExternalBluetoothTypeBean externalBluetoothTypeBean, List<ExternalBluetoothTypeBean> list) {
        if (this.mIntervalControl.isFrequently(500)) {
            return false;
        }
        ExternalBluetoothDeviceInfo data = externalBluetoothTypeBean.getData();
        if (data == null || list == null) {
            Logs.d("xpbluetooth nf device null " + externalBluetoothTypeBean);
            return false;
        } else if (isBusy(viewHolder, externalBluetoothTypeBean, list)) {
            return false;
        } else {
            this.mExternalBluetoothViewModel.connectUsb(data);
            return true;
        }
    }

    private boolean isBusy(BaseAdapter.ViewHolder viewHolder, ExternalBluetoothTypeBean externalBluetoothTypeBean, List<ExternalBluetoothTypeBean> list) {
        int i = 0;
        int i2 = 0;
        for (ExternalBluetoothTypeBean externalBluetoothTypeBean2 : list) {
            ExternalBluetoothDeviceInfo data = externalBluetoothTypeBean2.getData();
            if (data != null) {
                if (data.isConnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + externalBluetoothTypeBean2);
                    i++;
                }
                if (data.isDisconnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + externalBluetoothTypeBean2);
                    i2++;
                }
            }
        }
        if (i > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_connecting), 3);
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 1, getString(R.string.bluetooth_error_device_connecting));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else if (i2 > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_disconnecting), 3);
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 3, getString(R.string.bluetooth_error_device_disconnecting));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean disconnectDevice(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        ExternalBluetoothViewModel externalBluetoothViewModel = this.mExternalBluetoothViewModel;
        if (externalBluetoothViewModel != null) {
            return externalBluetoothViewModel.disUsbConnect(externalBluetoothDeviceInfo.getDeviceAddr());
        }
        return false;
    }

    private void ignoreDevice(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        ExternalBluetoothViewModel externalBluetoothViewModel = this.mExternalBluetoothViewModel;
        if (externalBluetoothViewModel != null) {
            externalBluetoothViewModel.unpair(externalBluetoothDeviceInfo);
        }
    }

    public void showConnectOperateError(String str) {
        if (!TextUtils.isEmpty(str) && str.length() > 15) {
            String str2 = str.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_operate_error_tips), 3);
    }

    public void showConnectError(String str, String str2) {
        ExternalBluetoothDeviceInfo bluetoothDeviceInfo;
        if (!TextUtils.isEmpty(str) && (bluetoothDeviceInfo = this.mExternalBluetoothViewModel.getBluetoothDeviceInfo(str)) != null && this.mExternalBluetoothViewModel.isConnected(bluetoothDeviceInfo)) {
            Logs.d("xpbluetooth already connected!");
            return;
        }
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_error_msg, str2), 3);
    }

    public void showBondError(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        if (TextUtils.isEmpty(str) || !str.equals(str2)) {
            if (!TextUtils.isEmpty(str)) {
                str2 = this.mExternalBluetoothViewModel.getBluetoothDeviceInfo(str).getDeviceName();
            }
            ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_bond_error_msg, str2), 3);
        }
    }

    public void showDevicePopup(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        String string;
        this.mDialogDeviceInfo = externalBluetoothDeviceInfo;
        if (this.mExternalBluetoothViewModel.isConnected(externalBluetoothDeviceInfo)) {
            string = getString(R.string.bluetooth_popup_connected_title);
        } else {
            string = getString(R.string.bluetooth_popup_disconnect_title);
        }
        showPop(string, externalBluetoothDeviceInfo.getDeviceName(), externalBluetoothDeviceInfo);
    }

    private void showPop(String str, String str2, final ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        if (this.mXDialog == null) {
            this.mXDialog = new XDialog(this.mContext);
            if (CarFunction.isNonSelfPageUI()) {
                this.mXDialog.setSystemDialog(2008);
            }
        }
        Utils.popupToScreenId(this.mXDialog.getDialog(), 1);
        if (this.mExternalBluetoothViewModel.isConnected(externalBluetoothDeviceInfo)) {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_bluetoothlink);
        } else {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_nobluetooth);
        }
        this.mXDialog.setMessage(str2);
        this.mXDialog.setTitle(str).setNegativeButton(getString(R.string.cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getString(R.string.bluetooth_popup_ignore_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$ExternalBluetoothOperation$GkM-NqXZBoX7rL-Pxq84pqlfNmQ
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ExternalBluetoothOperation.this.lambda$showPop$0$ExternalBluetoothOperation(externalBluetoothDeviceInfo, xDialog, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$ExternalBluetoothOperation$OjQtqtGqw0uI70Z_7KUS5xwaXP4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ExternalBluetoothOperation.this.lambda$showPop$1$ExternalBluetoothOperation(dialogInterface);
            }
        }).show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_BLUETOOTH_DETAIL_DIALOG);
        if (externalBluetoothDeviceInfo != null && (externalBluetoothDeviceInfo.isConnectingBusy() || externalBluetoothDeviceInfo.isDisconnectingBusy())) {
            this.mXDialog.setPositiveButtonEnable(false);
        } else {
            this.mXDialog.setPositiveButtonEnable(true);
        }
    }

    public /* synthetic */ void lambda$showPop$0$ExternalBluetoothOperation(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo, XDialog xDialog, int i) {
        if (externalBluetoothDeviceInfo != null) {
            ignoreDevice(externalBluetoothDeviceInfo);
        }
    }

    public /* synthetic */ void lambda$showPop$1$ExternalBluetoothOperation(DialogInterface dialogInterface) {
        this.mDialogDeviceInfo = null;
    }

    public void refreshDevicePopup() {
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo;
        XDialog xDialog = this.mXDialog;
        if (xDialog == null || !xDialog.isShowing() || (externalBluetoothDeviceInfo = this.mDialogDeviceInfo) == null) {
            return;
        }
        boolean isConnected = this.mExternalBluetoothViewModel.isConnected(externalBluetoothDeviceInfo);
        this.mXDialog.setTitle(getString(isConnected ? R.string.bluetooth_popup_connected_title : R.string.bluetooth_popup_disconnect_title));
        this.mXDialog.setIcon(isConnected ? R.drawable.x_ic_xxlarge_bluetoothlink : R.drawable.x_ic_xxlarge_nobluetooth);
        ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo2 = this.mDialogDeviceInfo;
        if (externalBluetoothDeviceInfo2 != null && (externalBluetoothDeviceInfo2.isConnectingBusy() || this.mDialogDeviceInfo.isDisconnectingBusy())) {
            this.mXDialog.setPositiveButtonEnable(false);
        } else {
            this.mXDialog.setPositiveButtonEnable(true);
        }
    }

    private String getString(int i) {
        return this.mContext.getString(i);
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
}
