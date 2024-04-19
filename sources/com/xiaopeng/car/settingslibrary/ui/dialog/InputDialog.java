package com.xiaopeng.car.settingslibrary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAccessPoint;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpWifiManager;
import com.xiaopeng.car.settingslibrary.service.work.WlanResultWork;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XTextFields;
/* loaded from: classes.dex */
public class InputDialog implements WlanResultWork.WlanResultListener, IVuiElementListener {
    public static final String EXTRA_INPUT_TYPE = "input_type";
    public static final String EXTRA_REQUEST_TITLE_NAME = "title_name";
    public static final String EXTRA_REQUEST_WLAN_SECURITY = "wifi_security";
    public static final int TYPE_BLUETOOTH = 1;
    public static final int TYPE_WIFI = 0;
    Context mContext;
    XTextFields mEdit1;
    XTextFields mEdit2;
    int mInputType;
    View mInputView;
    private boolean mIsConnecting = false;
    String mTitleName;
    int mWifiSecurity;
    WlanResultWork mWlanResultWork;
    XDialog mXDialog;
    XpBluetoothManger mXpBluetoothManger;

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        return null;
    }

    public InputDialog(Context context, Intent intent) {
        this.mInputType = -1;
        this.mContext = context;
        if (intent != null) {
            this.mInputType = intent.getIntExtra(EXTRA_INPUT_TYPE, -1);
            this.mTitleName = intent.getStringExtra(EXTRA_REQUEST_TITLE_NAME);
            this.mWifiSecurity = intent.getIntExtra(EXTRA_REQUEST_WLAN_SECURITY, -1);
            if (this.mInputType == 0) {
                this.mWlanResultWork = WlanResultWork.getInstance();
            } else {
                this.mXpBluetoothManger = XpBluetoothManger.getInstance();
            }
        }
    }

    public void show() {
        Context context;
        int i;
        WlanResultWork wlanResultWork;
        Logs.d("xpsettings dialog show");
        this.mIsConnecting = false;
        this.mContext.setTheme(R.style.AppTheme);
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.input_dialog_view, (ViewGroup) null);
        this.mXDialog = new XDialog(this.mContext, R.style.XDialogView_input);
        this.mXDialog.getDialog().getWindow().setType(2008);
        this.mXDialog.getDialog().setCanceledOnTouchOutside(true);
        this.mXDialog.setScreenId(0);
        Utils.setInputMethodPosition(0);
        correctedLocation(this.mXDialog.getDialog());
        initView(inflate);
        XDialog customView = this.mXDialog.setTitle(this.mInputType == 0 ? this.mTitleName : this.mContext.getString(R.string.input_title_bluetooth)).setCustomView(inflate);
        if (this.mInputType == 0) {
            context = this.mContext;
            i = R.string.connect;
        } else {
            context = this.mContext;
            i = R.string.ok;
        }
        customView.setPositiveButton(context.getString(i), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$InputDialog$DVL9gu7I6K3JAwxAMQliMbiVZIk
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i2) {
                InputDialog.this.lambda$show$0$InputDialog(xDialog, i2);
            }
        }).setNegativeButton(this.mContext.getString(R.string.cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$InputDialog$ZkFjfEVMRIRtFl0ReAFXGVAhCZM
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i2) {
                InputDialog.this.lambda$show$1$InputDialog(xDialog, i2);
            }
        });
        if (this.mInputType == 0) {
            this.mXDialog.setPositiveButtonInterceptDismiss(true);
        } else {
            this.mXDialog.setPositiveButtonInterceptDismiss(false);
        }
        this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                Logs.d("xpinput dialog onDismiss");
                if (InputDialog.this.mInputType != 0 || InputDialog.this.mWlanResultWork == null) {
                    return;
                }
                InputDialog.this.mWlanResultWork.removeWlanResultListener(InputDialog.this);
                WlanResultWork.getInstance().setTargetAccessPoint(null);
                InputDialog.this.mIsConnecting = false;
            }
        });
        if (this.mInputType == 0 && (wlanResultWork = this.mWlanResultWork) != null) {
            wlanResultWork.addWlanResultListener(this.mTitleName, this);
        }
        this.mXDialog.show();
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, this.mInputType == 0 ? VuiManager.SCENE_WIFI_CONNECT_DIALOG : VuiManager.SCENE_BLUETOOTH_EDIT_NAME_DIALOG, this.mInputType == 0 ? this : null);
    }

    public /* synthetic */ void lambda$show$0$InputDialog(XDialog xDialog, int i) {
        if (this.mInputType == 0) {
            this.mEdit2.setErrorMsg("");
            if (connectToWifi()) {
                this.mXDialog.setPositiveButtonEnable(false);
                connectWifiUpdateUI(true);
                ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InputDialog.this.mIsConnecting = true;
                    }
                }, Config.IS_SDK_HIGHER_P ? 1000L : 0L);
                this.mWlanResultWork.launchTimeout();
            }
        } else {
            XpBluetoothManger xpBluetoothManger = this.mXpBluetoothManger;
            if (xpBluetoothManger != null) {
                xpBluetoothManger.setDeviceName(this.mEdit1.getText().toString().trim(), true);
            }
        }
        hidenInputMethod();
    }

    public /* synthetic */ void lambda$show$1$InputDialog(XDialog xDialog, int i) {
        hidenInputMethod();
    }

    private void correctedLocation(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.softInputMode = 16;
            window.setAttributes(attributes);
        }
    }

    private void initView(View view) {
        this.mEdit1 = (XTextFields) view.findViewById(R.id.edit1);
        this.mEdit2 = (XTextFields) view.findViewById(R.id.edit2);
        if (this.mInputType == 0) {
            if (this.mWifiSecurity == 3) {
                this.mEdit1.setVisibility(0);
                this.mEdit1.setEditHint(this.mContext.getString(R.string.input_hint_wifi_account));
                this.mEdit1.getEditText().requestFocus();
                showInputMethod(this.mEdit1.getEditText());
                this.mEdit2.setVuiLabel(this.mContext.getString(R.string.vui_label_account));
            } else {
                this.mEdit1.setVisibility(8);
                this.mEdit2.getEditText().requestFocus();
                showInputMethod(this.mEdit2.getEditText());
                this.mEdit2.setVuiLabel(this.mContext.getString(R.string.vui_label_password));
            }
            this.mXDialog.setPositiveButtonEnable(false);
            this.mEdit2.setEditHint(this.mContext.getString(R.string.input_hint_wifi_pass));
            this.mEdit2.addTextChangedListener(new SimpleTextWatcher(8));
            this.mEdit2.setVuiScene(VuiEngine.getInstance(this.mContext), VuiManager.SCENE_WIFI_CONNECT_DIALOG);
            this.mEdit2.setCheckStateChangeListener(new XTextFields.CheckStateChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog.3
                @Override // com.xiaopeng.xui.widget.XTextFields.CheckStateChangeListener
                public void onCheckStateChanged() {
                    VuiManager.instance().updateVuiScene(VuiManager.SCENE_WIFI_CONNECT_DIALOG, InputDialog.this.mContext, InputDialog.this.mEdit2);
                }
            });
            this.mEdit1.setVuiMode(VuiMode.DISABLED);
            return;
        }
        this.mEdit1.setMaxLength(16);
        this.mEdit1.getEditText().setText(this.mTitleName);
        this.mEdit1.addTextChangedListener(new SimpleTextWatcher(1));
        this.mEdit1.setVisibility(0);
        this.mEdit2.setVisibility(8);
        if (!TextUtils.isEmpty(this.mTitleName)) {
            this.mEdit1.getEditText().setSelection(this.mEdit1.getText().toString().length());
        } else {
            this.mXDialog.setPositiveButtonEnable(false);
        }
        this.mEdit1.getEditText().requestFocus();
        ((XImageButton) this.mEdit1.findViewById(R.id.x_text_fields_pass)).setVuiMode(VuiMode.DISABLED);
        this.mEdit2.setVuiMode(VuiMode.DISABLED);
        this.mEdit1.setVuiLabel(this.mContext.getString(R.string.vui_label_name));
    }

    private boolean connectToWifi() {
        Logs.d("xpwifi input connectToWifi:" + this.mWifiSecurity + " " + this.mTitleName);
        return XpWifiManager.addAccessPoint(this.mContext.getApplicationContext(), WlanResultWork.getInstance().getTargetAccessPoint(), this.mTitleName.toString(), this.mEdit1.getEditText().getText().toString(), this.mEdit2.getEditText().getText().toString(), this.mWifiSecurity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectWifiUpdateUI(boolean z) {
        XTextFields xTextFields;
        if (this.mEdit2 == null || (xTextFields = this.mEdit1) == null || this.mXDialog == null) {
            return;
        }
        if (z) {
            xTextFields.setEnabled(false);
            this.mEdit2.setEnabled(false);
            this.mXDialog.setPositiveButton(this.mContext.getString(R.string.input_wifi_connecting));
            return;
        }
        xTextFields.setEnabled(true);
        this.mEdit2.setEnabled(true);
        this.mXDialog.setPositiveButton(this.mContext.getString(R.string.connect));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInputMethod(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
            this.mInputView = view;
        }
    }

    private void switchInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager == null || !isSoftShowing()) {
            return;
        }
        inputMethodManager.toggleSoftInput(2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSoftShowing() {
        XDialog xDialog = this.mXDialog;
        if (xDialog == null) {
            return false;
        }
        int height = xDialog.getDialog().getWindow().getDecorView().getHeight();
        Rect rect = new Rect();
        this.mXDialog.getDialog().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        Logs.d("xpinput screenHeight:" + height + " bottom:" + rect.bottom);
        return height > rect.bottom;
    }

    private void hidenInputMethod() {
        InputMethodManager inputMethodManager;
        if (this.mInputView == null || (inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(this.mInputView.getWindowToken(), 2);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onResultReceiveFail(String str, final int i) {
        Logs.d("xpinput onResultReceiveFail " + str + " error:" + i);
        if (TextUtils.isEmpty(str) || !str.equals(this.mTitleName)) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog.4
            @Override // java.lang.Runnable
            public void run() {
                if (InputDialog.this.mXDialog != null) {
                    InputDialog.this.mXDialog.setPositiveButtonEnable(true);
                    InputDialog.this.connectWifiUpdateUI(false);
                }
                if (i != WifiUtils.WifiManagerConstant.ERROR_AUTH_FAILURE_WRONG_PSWD) {
                    if (InputDialog.this.isSoftShowing()) {
                        return;
                    }
                    InputDialog.this.mEdit2.getEditText().requestFocus();
                    InputDialog inputDialog = InputDialog.this;
                    inputDialog.showInputMethod(inputDialog.mEdit2.getEditText());
                } else if (InputDialog.this.mXDialog == null || InputDialog.this.mEdit2 == null) {
                } else {
                    if (InputDialog.this.mContext != null) {
                        InputDialog.this.mEdit2.setErrorMsg(InputDialog.this.mContext.getString(R.string.wlan_password_error));
                    }
                    if (InputDialog.this.isSoftShowing()) {
                        return;
                    }
                    InputDialog.this.mEdit2.getEditText().requestFocus();
                    InputDialog inputDialog2 = InputDialog.this;
                    inputDialog2.showInputMethod(inputDialog2.mEdit2.getEditText());
                }
            }
        });
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onResultSuccess(String str) {
        Logs.d("xpinput onResultSuccess ssid:" + str + " mTitleName:" + this.mTitleName);
        if (TextUtils.isEmpty(str) || !str.equals(this.mTitleName)) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.InputDialog.5
            @Override // java.lang.Runnable
            public void run() {
                if (InputDialog.this.mXDialog != null) {
                    InputDialog.this.connectWifiUpdateUI(false);
                    InputDialog.this.mXDialog.setPositiveButtonEnable(true);
                    InputDialog.this.mXDialog.dismiss();
                }
            }
        });
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WlanResultWork.WlanResultListener
    public void onRefreshSummaryMsg(String str, String str2, int i) {
        XpAccessPoint targetAccessPoint;
        if (Config.IS_SDK_HIGHER_P) {
            Logs.d("xpinput onRefreshSummaryMsg mIsConnecting:" + this.mIsConnecting + " summary:" + str2);
            if (!this.mIsConnecting || (targetAccessPoint = this.mWlanResultWork.getTargetAccessPoint()) == null) {
                return;
            }
            Logs.d("xpinput onRefreshSummaryMsg " + str2 + " ssid:" + targetAccessPoint.getSsid());
            if (i == 2) {
                onResultReceiveFail(targetAccessPoint.getSsid().toString(), WifiUtils.WifiManagerConstant.ERROR_AUTH_FAILURE_WRONG_PSWD);
                this.mWlanResultWork.removeTimeout(targetAccessPoint.getSsid().toString());
                Context context = this.mContext;
                if (context != null) {
                    this.mEdit2.setErrorMsg(context.getString(R.string.wlan_password_error));
                }
                this.mIsConnecting = false;
            } else if (i == 1) {
                this.mIsConnecting = false;
                onResultReceiveFail(targetAccessPoint.getSsid().toString(), 0);
                this.mWlanResultWork.removeTimeout(targetAccessPoint.getSsid().toString());
                Context context2 = this.mContext;
                if (context2 != null) {
                    this.mEdit2.setErrorMsg(context2.getString(R.string.wlan_popup_error_title));
                }
            } else if (i == 0) {
                Context context3 = this.mContext;
                if (context3 != null) {
                    this.mEdit2.setErrorMsg(context3.getString(R.string.wlan_popup_error_title));
                }
                this.mWlanResultWork.removeTimeout(targetAccessPoint.getSsid().toString());
                onResultReceiveFail(targetAccessPoint.getSsid().toString(), 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SimpleTextWatcher implements TextWatcher {
        private int min;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        SimpleTextWatcher(int i) {
            this.min = i;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String trim;
            if (InputDialog.this.mInputType == 0) {
                trim = InputDialog.this.mEdit2.getText().toString();
                InputDialog.this.mEdit2.setErrorMsg("");
            } else {
                trim = InputDialog.this.mEdit1.getText().toString().trim();
            }
            if (trim.getBytes().length >= this.min) {
                if (InputDialog.this.mXDialog != null) {
                    InputDialog.this.mXDialog.setPositiveButtonEnable(true);
                }
            } else if (InputDialog.this.mXDialog != null) {
                InputDialog.this.mXDialog.setPositiveButtonEnable(false);
            }
            if (InputDialog.this.mInputType != 0 || trim.getBytes().length <= 63) {
                return;
            }
            String substring = trim.substring(0, trim.length() - 1);
            editable.clear();
            editable.append((CharSequence) substring);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return this.mEdit2.onVuiElementEvent(view, vuiEvent);
    }
}
