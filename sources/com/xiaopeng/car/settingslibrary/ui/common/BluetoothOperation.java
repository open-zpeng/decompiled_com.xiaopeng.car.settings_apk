package com.xiaopeng.car.settingslibrary.ui.common;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.view.BluetoothView;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.List;
/* loaded from: classes.dex */
public class BluetoothOperation {
    private BluetoothSettingsViewModel mBluetoothSettingsViewModel;
    private BluetoothView mBluetoothView;
    private Context mContext;
    private XpBluetoothDeviceInfo mDialogDeviceInfo;
    private IntervalControl mIntervalControl = new IntervalControl("bluetooth-click");
    private XDialog mXDialog;

    public BluetoothOperation(Context context, BluetoothView bluetoothView) {
        this.mBluetoothView = bluetoothView;
        this.mContext = context;
    }

    public void setViewModel(BluetoothSettingsViewModel bluetoothSettingsViewModel) {
        this.mBluetoothSettingsViewModel = bluetoothSettingsViewModel;
    }

    public boolean connectingDevice(BaseAdapter.ViewHolder viewHolder, BluetoothTypeBean bluetoothTypeBean, List<BluetoothTypeBean> list) {
        if (this.mIntervalControl.isFrequently(500)) {
            return false;
        }
        XpBluetoothDeviceInfo data = bluetoothTypeBean.getData();
        if (data == null || list == null) {
            Logs.d("xpbluetooth nf device null " + bluetoothTypeBean);
            return false;
        } else if (isBusy(viewHolder, bluetoothTypeBean, list)) {
            return false;
        } else {
            this.mBluetoothSettingsViewModel.connectAndRetry(data);
            return true;
        }
    }

    private boolean isBusy(BaseAdapter.ViewHolder viewHolder, BluetoothTypeBean bluetoothTypeBean, List<BluetoothTypeBean> list) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (BluetoothTypeBean bluetoothTypeBean2 : list) {
            XpBluetoothDeviceInfo data = bluetoothTypeBean2.getData();
            if (data != null) {
                if (data.isConnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + bluetoothTypeBean2);
                    i++;
                }
                if (data.isDisconnectingBusy()) {
                    Logs.d("xpbluetooth nf device isConnectingBusy " + bluetoothTypeBean2);
                    i2++;
                }
                if (data.isParingBusy()) {
                    Logs.d("xpbluetooth nf device isParingBusy " + bluetoothTypeBean2);
                    i3++;
                }
            }
        }
        if (i > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_connecting), this.mBluetoothView.getScreenId());
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 1, getString(R.string.bluetooth_error_device_connecting));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else if (i2 > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_error_device_disconnecting), this.mBluetoothView.getScreenId());
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 3, getString(R.string.bluetooth_error_device_disconnecting));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else if (i3 > 0) {
            ToastUtils.get().showText(getString(R.string.bluetooth_pairing_request_busy), this.mBluetoothView.getScreenId());
            if ((viewHolder.itemView instanceof IVuiElement) && ((IVuiElement) viewHolder.itemView).isPerformVuiAction()) {
                VuiManager.instance();
                VuiManager.vuiFeedback(viewHolder.itemView, 11, getString(R.string.bluetooth_pairing_request_busy));
                VuiManager.instance().isVuiAction(viewHolder.itemView);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean bondingDevice(BaseAdapter.ViewHolder viewHolder, BluetoothTypeBean bluetoothTypeBean, List<BluetoothTypeBean> list) {
        XpBluetoothDeviceInfo data = bluetoothTypeBean.getData();
        if (data == null || list == null) {
            Logs.d("xpbluetooth nf device null");
            return false;
        } else if (isBusy(viewHolder, bluetoothTypeBean, list)) {
            return false;
        } else {
            if (this.mBluetoothSettingsViewModel.pair(data, data.getDeviceAddr())) {
                return true;
            }
            showBondError(data.getDeviceAddr(), data.getDeviceName());
            return false;
        }
    }

    public boolean disconnectDevice(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        BluetoothSettingsViewModel bluetoothSettingsViewModel = this.mBluetoothSettingsViewModel;
        if (bluetoothSettingsViewModel != null) {
            return bluetoothSettingsViewModel.disConnect(false, xpBluetoothDeviceInfo);
        }
        return false;
    }

    private void ignoreDevice(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        BluetoothSettingsViewModel bluetoothSettingsViewModel = this.mBluetoothSettingsViewModel;
        if (bluetoothSettingsViewModel != null) {
            bluetoothSettingsViewModel.unpair(xpBluetoothDeviceInfo);
        }
    }

    public void showConnectOperateError(String str) {
        if (!TextUtils.isEmpty(str) && str.length() > 15) {
            String str2 = str.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_operate_error_tips), this.mBluetoothView.getScreenId());
    }

    public void showConnectError(String str, String str2) {
        XpBluetoothDeviceInfo bluetoothDeviceInfo;
        if (!TextUtils.isEmpty(str) && (bluetoothDeviceInfo = this.mBluetoothSettingsViewModel.getBluetoothDeviceInfo(str)) != null && this.mBluetoothSettingsViewModel.isConnected(bluetoothDeviceInfo)) {
            Logs.d("xpbluetooth already connected!");
            return;
        }
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_connect_error_msg, str2), this.mBluetoothView.getScreenId());
    }

    public void showBondError(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && str2.length() > 15) {
            str2 = str2.substring(0, 15) + "...";
        }
        if (TextUtils.isEmpty(str) || !str.equals(str2)) {
            ToastUtils.get().showText(this.mContext.getString(R.string.bluetooth_popup_bond_error_msg, str2), this.mBluetoothView.getScreenId());
        }
    }

    public void showDevicePopup(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        String string;
        this.mDialogDeviceInfo = xpBluetoothDeviceInfo;
        if (this.mBluetoothSettingsViewModel.isConnected(xpBluetoothDeviceInfo)) {
            string = getString(R.string.bluetooth_popup_connected_title);
        } else {
            string = getString(R.string.bluetooth_popup_disconnect_title);
        }
        showPop(string, xpBluetoothDeviceInfo.getDeviceName(), xpBluetoothDeviceInfo);
    }

    private void showPop(String str, String str2, final XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (this.mXDialog == null) {
            this.mXDialog = new XDialog(this.mContext);
            if (CarFunction.isNonSelfPageUI()) {
                this.mXDialog.setSystemDialog(2008);
            }
        }
        Utils.popupToScreenId(this.mXDialog.getDialog(), this.mBluetoothView.getScreenId());
        if (this.mBluetoothSettingsViewModel.isConnected(xpBluetoothDeviceInfo)) {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_bluetoothlink);
        } else {
            this.mXDialog.setIcon(R.drawable.x_ic_xxlarge_nobluetooth);
        }
        this.mXDialog.setMessage(str2);
        this.mXDialog.setTitle(str).setNegativeButton(getString(R.string.cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getString(R.string.bluetooth_popup_ignore_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$BluetoothOperation$CuxnxMLETyL7Y_MRdiL-Wm8TvsE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                BluetoothOperation.this.lambda$showPop$0$BluetoothOperation(xpBluetoothDeviceInfo, xDialog, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.common.-$$Lambda$BluetoothOperation$vDhbAGhhjSQQFd2YyoLNTIpKXcc
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BluetoothOperation.this.lambda$showPop$1$BluetoothOperation(dialogInterface);
            }
        }).show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_BLUETOOTH_DETAIL_DIALOG);
        if (xpBluetoothDeviceInfo != null && (xpBluetoothDeviceInfo.isConnectingBusy() || xpBluetoothDeviceInfo.isDisconnectingBusy())) {
            this.mXDialog.setPositiveButtonEnable(false);
        } else {
            this.mXDialog.setPositiveButtonEnable(true);
        }
    }

    public /* synthetic */ void lambda$showPop$0$BluetoothOperation(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, XDialog xDialog, int i) {
        if (xpBluetoothDeviceInfo != null) {
            ignoreDevice(xpBluetoothDeviceInfo);
        }
    }

    public /* synthetic */ void lambda$showPop$1$BluetoothOperation(DialogInterface dialogInterface) {
        this.mDialogDeviceInfo = null;
    }

    public void refreshDevicePopup() {
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo;
        XDialog xDialog = this.mXDialog;
        if (xDialog == null || !xDialog.isShowing() || (xpBluetoothDeviceInfo = this.mDialogDeviceInfo) == null) {
            return;
        }
        boolean isConnected = this.mBluetoothSettingsViewModel.isConnected(xpBluetoothDeviceInfo);
        this.mXDialog.setTitle(getString(isConnected ? R.string.bluetooth_popup_connected_title : R.string.bluetooth_popup_disconnect_title));
        this.mXDialog.setIcon(isConnected ? R.drawable.x_ic_xxlarge_bluetoothlink : R.drawable.x_ic_xxlarge_nobluetooth);
        XpBluetoothDeviceInfo xpBluetoothDeviceInfo2 = this.mDialogDeviceInfo;
        if (xpBluetoothDeviceInfo2 != null && (xpBluetoothDeviceInfo2.isConnectingBusy() || this.mDialogDeviceInfo.isDisconnectingBusy())) {
            this.mXDialog.setPositiveButtonEnable(false);
        } else {
            this.mXDialog.setPositiveButtonEnable(true);
        }
    }

    public int deviceIcon(XpBluetoothDeviceInfo xpBluetoothDeviceInfo, boolean z) {
        if (xpBluetoothDeviceInfo != null) {
            int category = xpBluetoothDeviceInfo.getCategory();
            if (category == 1) {
                return R.drawable.ic_mid_phone;
            }
            if (category == 2) {
                return R.drawable.ic_mid_handle;
            }
        }
        return z ? R.drawable.ic_mid_bluetoothhistory : R.drawable.ic_mid_bluetooth;
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
