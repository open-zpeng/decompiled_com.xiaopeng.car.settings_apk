package com.xiaopeng.car.settingslibrary.ui.common;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.List;
/* loaded from: classes.dex */
public class PsnBluetoothOperation {
    private Context mContext;
    private PsnBluetoothDeviceInfo mDialogDeviceInfo;
    private IntervalControl mIntervalControl = new IntervalControl("bluetooth-click");
    private PsnBluetoothViewModel mPsnBluetoothViewModel;
    private XDialog mXDialog;

    public PsnBluetoothOperation(Context context) {
        this.mContext = context;
    }

    public void setViewModel(PsnBluetoothViewModel psnBluetoothViewModel) {
        this.mPsnBluetoothViewModel = psnBluetoothViewModel;
    }

    public boolean connectingDevice(BaseAdapter.ViewHolder viewHolder, PsnBluetoothTypeBean psnBluetoothTypeBean, List<PsnBluetoothTypeBean> list) {
        if (this.mIntervalControl.isFrequently(500)) {
            return false;
        }
        PsnBluetoothDeviceInfo data = psnBluetoothTypeBean.getData();
        if (data == null || list == null) {
            Logs.d("xpbluetooth nf device null " + psnBluetoothTypeBean);
            return false;
        } else if (isBusy(viewHolder, psnBluetoothTypeBean, list)) {
            return false;
        } else {
            this.mPsnBluetoothViewModel.connectUsb(data);
            return true;
        }
    }

    private boolean isBusy(BaseAdapter.ViewHolder viewHolder, PsnBluetoothTypeBean psnBluetoothTypeBean, List<PsnBluetoothTypeBean> list) {
        int i = 0;
        int i2 = 0;
        for (PsnBluetoothTypeBean psnBluetoothTypeBean2 : list) {
            PsnBluetoothDeviceInfo data = psnBluetoothTypeBean2.getData();
            if (data != null) {
                if (data.isConnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + psnBluetoothTypeBean2);
                    i++;
                }
                if (data.isDisconnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + psnBluetoothTypeBean2);
                    i2++;
                }
            }
        }
        if (i > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_connecting), 1);
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 1, getString(R.string.bluetooth_error_device_connecting));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else if (i2 > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_disconnecting), 1);
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

    public boolean disconnectDevice(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        PsnBluetoothViewModel psnBluetoothViewModel = this.mPsnBluetoothViewModel;
        if (psnBluetoothViewModel != null) {
            return psnBluetoothViewModel.disUsbConnect(psnBluetoothDeviceInfo.getDeviceAddr());
        }
        return false;
    }

    private void ignoreDevice(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        PsnBluetoothViewModel psnBluetoothViewModel = this.mPsnBluetoothViewModel;
        if (psnBluetoothViewModel != null) {
            psnBluetoothViewModel.unpair(psnBluetoothDeviceInfo);
        }
    }

    public void showConnectOperateError(String str) {
        if (!TextUtils.isEmpty(str) && str.length() > 15) {
            String str2 = str.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_operate_error_tips), 1);
    }

    public void showConnectError(String str, String str2) {
        PsnBluetoothDeviceInfo bluetoothDeviceInfo;
        if (!TextUtils.isEmpty(str) && (bluetoothDeviceInfo = this.mPsnBluetoothViewModel.getBluetoothDeviceInfo(str)) != null && this.mPsnBluetoothViewModel.isConnected(bluetoothDeviceInfo)) {
            Logs.d("xpbluetooth already connected!");
            return;
        }
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_error_msg, str2), 1);
    }

    public void showBondError(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        if (TextUtils.isEmpty(str) || !str.equals(str2)) {
            if (!TextUtils.isEmpty(str)) {
                str2 = this.mPsnBluetoothViewModel.getBluetoothDeviceInfo(str).getDeviceName();
            }
            ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_bond_error_msg, str2), 1);
        }
    }

    public void showDevicePopup(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        String string;
        this.mDialogDeviceInfo = psnBluetoothDeviceInfo;
        if (this.mPsnBluetoothViewModel.isConnected(psnBluetoothDeviceInfo)) {
            string = getString(R.string.bluetooth_popup_connected_title);
        } else {
            string = getString(R.string.bluetooth_popup_disconnect_title);
        }
        showPop(string, psnBluetoothDeviceInfo.getDeviceName(), psnBluetoothDeviceInfo);
    }

    private void showPop(String str, String str2, final PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        if (this.mXDialog == null) {
            this.mXDialog = new XDialog(this.mContext);
            if (CarFunction.isNonSelfPageUI()) {
                this.mXDialog.setSystemDialog(2008);
            }
        }
        Utils.popupToScreenId(this.mXDialog.getDialog(), 1);
        if (this.mPsnBluetoothViewModel.isConnected(psnBluetoothDeviceInfo)) {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_bluetoothlink);
        } else {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_nobluetooth);
        }
        this.mXDialog.setMessage(str2);
        this.mXDialog.setTitle(str).setNegativeButton(getString(R.string.cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getString(R.string.bluetooth_popup_ignore_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$PsnBluetoothOperation$rzWtyZk70X81YmjJnx00GIFuK9k
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                PsnBluetoothOperation.this.lambda$showPop$0$PsnBluetoothOperation(psnBluetoothDeviceInfo, xDialog, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$PsnBluetoothOperation$TTbqJUBrZ0tL47WCBc_ElV43dv8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PsnBluetoothOperation.this.lambda$showPop$1$PsnBluetoothOperation(dialogInterface);
            }
        }).show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_BLUETOOTH_DETAIL_DIALOG);
        if (psnBluetoothDeviceInfo != null && (psnBluetoothDeviceInfo.isConnectingBusy() || psnBluetoothDeviceInfo.isDisconnectingBusy())) {
            this.mXDialog.setPositiveButtonEnable(false);
        } else {
            this.mXDialog.setPositiveButtonEnable(true);
        }
    }

    public /* synthetic */ void lambda$showPop$0$PsnBluetoothOperation(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo, XDialog xDialog, int i) {
        if (psnBluetoothDeviceInfo != null) {
            ignoreDevice(psnBluetoothDeviceInfo);
        }
    }

    public /* synthetic */ void lambda$showPop$1$PsnBluetoothOperation(DialogInterface dialogInterface) {
        this.mDialogDeviceInfo = null;
    }

    public void refreshDevicePopup() {
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo;
        XDialog xDialog = this.mXDialog;
        if (xDialog == null || !xDialog.isShowing() || (psnBluetoothDeviceInfo = this.mDialogDeviceInfo) == null) {
            return;
        }
        boolean isConnected = this.mPsnBluetoothViewModel.isConnected(psnBluetoothDeviceInfo);
        this.mXDialog.setTitle(getString(isConnected ? R.string.bluetooth_popup_connected_title : R.string.bluetooth_popup_disconnect_title));
        this.mXDialog.setIcon(isConnected ? R.drawable.x_ic_xxlarge_bluetoothlink : R.drawable.x_ic_xxlarge_nobluetooth);
        PsnBluetoothDeviceInfo psnBluetoothDeviceInfo2 = this.mDialogDeviceInfo;
        if (psnBluetoothDeviceInfo2 != null && (psnBluetoothDeviceInfo2.isConnectingBusy() || this.mDialogDeviceInfo.isDisconnectingBusy())) {
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
