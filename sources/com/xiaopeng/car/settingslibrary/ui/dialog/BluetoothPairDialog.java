package com.xiaopeng.car.settingslibrary.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.pair.BluetoothPairingManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
/* loaded from: classes.dex */
public class BluetoothPairDialog {
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_PAIR_TYPE = "device_pair_type";
    Context mContext;
    private String mDeviceAddress;
    private String mDeviceName;
    private int mPairType;
    BluetoothPairingManager mPairingManager;
    XDialog mXDialog;

    public BluetoothPairDialog(Context context, Intent intent) {
        this.mContext = context;
        this.mDeviceName = intent.getStringExtra(DEVICE_NAME);
        this.mDeviceAddress = intent.getStringExtra(DEVICE_ADDRESS);
        this.mPairType = intent.getIntExtra(DEVICE_PAIR_TYPE, -1);
        this.mPairingManager = new BluetoothPairingManager(intent, this.mDeviceAddress, this.mPairType);
    }

    public void show() {
        Logs.d("xpsettings dialog show");
        this.mContext.setTheme(R.style.AppTheme);
        this.mXDialog = new XDialog(this.mContext);
        this.mXDialog.getDialog().setCanceledOnTouchOutside(true);
        this.mXDialog.getDialog().getWindow().setType(2008);
        this.mXDialog.setScreenId(0);
        XDialog title = this.mXDialog.setTitle(this.mContext.getString(R.string.bluetooth_pairing_popup_title));
        Context context = this.mContext;
        int i = R.string.bluetooth_pairing_request;
        Object[] objArr = new Object[1];
        objArr[0] = TextUtils.isEmpty(this.mDeviceName) ? this.mPairingManager.getDeviceName() : this.mDeviceName;
        title.setMessage(context.getString(i, objArr)).setPositiveButton(this.mContext.getString(R.string.pair), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$BluetoothPairDialog$kccEXxwWErQVB0jX7TCjgVhZ2uU
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i2) {
                BluetoothPairDialog.this.lambda$show$0$BluetoothPairDialog(xDialog, i2);
            }
        }).setNegativeButton(this.mContext.getString(R.string.refuse), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$BluetoothPairDialog$BGYfbvHd8GoXjTgUD8j-T7msI38
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i2) {
                BluetoothPairDialog.this.lambda$show$1$BluetoothPairDialog(xDialog, i2);
            }
        });
        this.mXDialog.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.BluetoothPairDialog.1
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                Logs.d("xpsettings dialog onCancel pair");
                BluetoothPairDialog.this.mPairingManager.cancel();
            }
        });
        this.mXDialog.show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_BLUETOOTH_BOUND_DIALOG);
    }

    public /* synthetic */ void lambda$show$0$BluetoothPairDialog(XDialog xDialog, int i) {
        this.mPairingManager.onPair(null);
        dismiss();
    }

    public /* synthetic */ void lambda$show$1$BluetoothPairDialog(XDialog xDialog, int i) {
        this.mPairingManager.cancel();
        dismiss();
    }

    public void dismiss() {
        Logs.d("xpsettings dialog dismiss");
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }
}
