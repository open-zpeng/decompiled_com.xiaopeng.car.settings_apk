package com.xiaopeng.car.settingslibrary.ui.dialog.storage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XDialogUtils;
/* loaded from: classes.dex */
public class StorageLoadingDialog extends Dialog implements Runnable {
    public static final int BOTTOM = 80;
    public static final int CENTER_VERTICAL = 16;
    public static final int TOP = 48;
    private static StorageLoadingDialog dialog;
    private ImageButton mCloseView;
    private Handler mHandler;
    private TextView mMessageView;
    private OnTimeOutListener mOnTimeOutListener;
    private int mTimeOut;
    private boolean mTimeOutCheck;

    /* loaded from: classes.dex */
    public interface OnTimeOutListener {
        void onTimeOut(StorageLoadingDialog storageLoadingDialog);
    }

    public StorageLoadingDialog(Context context, int i) {
        super(context, i);
        this.mHandler = new Handler();
    }

    public static StorageLoadingDialog show(Context context) {
        return show(context, false, null);
    }

    public static StorageLoadingDialog show(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        return show(context, context.getString(R.string.x_loading_dialog_message), z, onCancelListener);
    }

    public static StorageLoadingDialog show(Context context, CharSequence charSequence) {
        return show(context, charSequence, false, (DialogInterface.OnCancelListener) null);
    }

    public static StorageLoadingDialog show(Context context, CharSequence charSequence, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        return show(context, charSequence, z, onCancelListener, false, 0, null);
    }

    public static StorageLoadingDialog show(Context context, boolean z, int i, OnTimeOutListener onTimeOutListener) {
        return show(context, context.getString(R.string.x_loading_dialog_message), z, i, onTimeOutListener);
    }

    public static StorageLoadingDialog show(Context context, CharSequence charSequence, boolean z, int i, OnTimeOutListener onTimeOutListener) {
        return show(context, charSequence, false, null, z, i, onTimeOutListener);
    }

    public static StorageLoadingDialog show(Context context, CharSequence charSequence, boolean z, DialogInterface.OnCancelListener onCancelListener, boolean z2, int i, OnTimeOutListener onTimeOutListener) {
        StorageLoadingDialog storageLoadingDialog = dialog;
        if (storageLoadingDialog != null && storageLoadingDialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new StorageLoadingDialog(context, R.style.XAppTheme_XDialog);
        XDialogUtils.setScreenId(dialog, 0);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setType(2008);
        }
        dialog.create();
        dialog.setMessage(charSequence);
        dialog.setCancelable(z);
        dialog.setOnCancelListener(onCancelListener);
        dialog.setOnTimeOutListener(onTimeOutListener);
        dialog.setTimeOutCheck(z2);
        dialog.setTimeOut(i);
        dialog.show();
        return dialog;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.x_loading_dialog, (ViewGroup) null);
        this.mMessageView = (TextView) inflate.findViewById(R.id.x_loading_dialog_text);
        this.mCloseView = (ImageButton) inflate.findViewById(R.id.x_loading_dialog_close);
        this.mCloseView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageLoadingDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StorageLoadingDialog.this.log("close is click ");
                StorageLoadingDialog.this.cancel();
            }
        });
        setContentView(inflate);
        super.onCreate(bundle);
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessageView.setText(charSequence);
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(false);
        this.mCloseView.setVisibility(z ? 0 : 8);
    }

    public void setOnTimeOutListener(OnTimeOutListener onTimeOutListener) {
        this.mOnTimeOutListener = onTimeOutListener;
    }

    public void setTimeOut(int i) {
        this.mTimeOut = i;
    }

    public void setTimeOutCheck(boolean z) {
        this.mTimeOutCheck = z;
        if (this.mTimeOutCheck) {
            return;
        }
        this.mHandler.removeCallbacks(this);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        if (this.mTimeOutCheck) {
            this.mHandler.removeCallbacks(this);
            this.mHandler.postDelayed(this, this.mTimeOut);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        dialog = null;
        log("dismiss");
        this.mHandler.removeCallbacks(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        OnTimeOutListener onTimeOutListener = this.mOnTimeOutListener;
        if (onTimeOutListener != null) {
            onTimeOutListener.onTimeOut(this);
        }
        log("time out");
        dismiss();
    }

    public void setVerticalTopDefault() {
        setVertical(48, (int) getContext().getResources().getDimension(R.dimen.x_loading_dialog_top));
    }

    public void setVertical(int i) {
        setVertical(48, i);
    }

    public void setVertical(int i, int i2) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = i;
            attributes.y = i2;
            window.setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        LogUtils.i("xpui-XLoadingDialog", str);
    }
}
