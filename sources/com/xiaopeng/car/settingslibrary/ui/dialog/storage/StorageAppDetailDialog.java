package com.xiaopeng.car.settingslibrary.ui.dialog.storage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogPure;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.XTitleBarLight;
/* loaded from: classes.dex */
public class StorageAppDetailDialog {
    AppStorageBean appStorageBean;
    private XButton btn_clean;
    private XButton btn_uninstall;
    Context context;
    XImageButton ib_close;
    IntervalControl intervalControl = new IntervalControl("StorageAppDetailDialog");
    private final StorageSettingsViewModel mStorageSettingsViewModel;
    StorageOptionCallBack storageOptionCallBack;
    private XTextView tv_app_size;
    private XTextView tv_clean_tip;
    private XTextView tv_storage_size;
    XTextView tv_title;
    private XTextView tv_uninstall_tip;
    XDialogPure xDialogPure;
    private XTitleBarLight x_title_bar;

    public StorageAppDetailDialog(Context context, AppStorageBean appStorageBean, StorageSettingsViewModel storageSettingsViewModel) {
        this.context = context;
        this.appStorageBean = appStorageBean;
        this.mStorageSettingsViewModel = storageSettingsViewModel;
        XpAboutManager.sendAppDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B011", appStorageBean.getName(), Long.valueOf(FileUtils.parseByte2KB(appStorageBean.getTotalSize())));
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        XDialogPure.Parameters Builder = XDialogPure.Parameters.Builder();
        Builder.setLayoutParams(new ViewGroup.LayoutParams(this.context.getResources().getDimensionPixelSize(R.dimen.storage_dialog_width), -2));
        this.xDialogPure = new XDialogPure(this.context, Builder);
        this.xDialogPure.setSystemDialog(2008);
        this.xDialogPure.setScreenId(0);
        if (CarFunction.isXmartOS5()) {
            this.xDialogPure.setContentView(R.layout.dialog_view_storage_detail_v5);
        } else {
            this.xDialogPure.setContentView(R.layout.dialog_view_storage_detail);
        }
        this.x_title_bar = (XTitleBarLight) this.xDialogPure.getDialog().findViewById(R.id.x_title_bar);
        this.ib_close = (XImageButton) this.xDialogPure.getDialog().findViewById(R.id.ib_close);
        this.tv_title = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_title);
        this.tv_storage_size = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_storage_size);
        this.tv_app_size = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_app_size);
        this.tv_clean_tip = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_clean_tip);
        this.tv_uninstall_tip = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_uninstall_tip);
        this.btn_clean = (XButton) this.xDialogPure.getDialog().findViewById(R.id.btn_clean);
        this.btn_uninstall = (XButton) this.xDialogPure.getDialog().findViewById(R.id.btn_uninstall);
    }

    private void initData() {
        this.x_title_bar.setTitle(this.appStorageBean.getName());
        this.tv_title.setText(this.appStorageBean.getName());
        this.tv_clean_tip.setText(R.string.storage_clean_app_tip);
        this.tv_uninstall_tip.setText(R.string.storage_uninstall_tip);
        this.tv_app_size.setText(FileUtils.getFileSizeDescription(this.appStorageBean.getAppSize()));
        this.tv_storage_size.setText(FileUtils.getFileSizeDescription(this.appStorageBean.getAppData()));
        this.btn_clean.setEnabled(this.appStorageBean.getAppData() != 0);
    }

    private void initEvent() {
        this.x_title_bar.setOnTitleBarClickListener(new XTitleBarLight.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageAppDetailDialog.1
            {
                StorageAppDetailDialog.this = this;
            }

            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                StorageAppDetailDialog.this.dismiss();
            }

            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarBackClick() {
                StorageAppDetailDialog.this.dismiss();
            }
        });
        this.ib_close.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageAppDetailDialog$ZctxSAV62IlzjZk762wdieaEEPI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageAppDetailDialog.this.lambda$initEvent$0$StorageAppDetailDialog(view);
            }
        });
        this.btn_clean.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageAppDetailDialog$sqog8FflFzfGXfm6lnwd5rMpEhM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageAppDetailDialog.this.lambda$initEvent$2$StorageAppDetailDialog(view);
            }
        });
        this.btn_uninstall.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageAppDetailDialog$bYxnK9T4D-AxbvcZXpCoEJwQgZk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageAppDetailDialog.this.lambda$initEvent$4$StorageAppDetailDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$initEvent$0$StorageAppDetailDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initEvent$2$StorageAppDetailDialog(View view) {
        if (!this.mStorageSettingsViewModel.isGearP()) {
            XToast.show(R.string.clean_not_gear_p);
            return;
        }
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B012", "name", this.appStorageBean.getName());
        if (this.storageOptionCallBack != null) {
            new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_clean_btn).setMessage(R.string.storage_clean_app_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageAppDetailDialog$bDorvTRVjp9XPW7ZYObRifmhykg
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    StorageAppDetailDialog.this.lambda$null$1$StorageAppDetailDialog(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).setPositiveButtonInterceptDismiss(true).setCloseVisibility(true).show();
        }
    }

    public /* synthetic */ void lambda$null$1$StorageAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        if (!this.mStorageSettingsViewModel.isGearP()) {
            XToast.show(R.string.clean_not_gear_p);
            return;
        }
        this.mStorageSettingsViewModel.clearData(this.appStorageBean.getPackageName(), this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public /* synthetic */ void lambda$initEvent$4$StorageAppDetailDialog(View view) {
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B014", "name", this.appStorageBean.getName());
        if (this.storageOptionCallBack != null) {
            new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_uninstall_btn).setMessage(R.string.storage_uninstall_app_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageAppDetailDialog$MD7ElyBfY4_S6_bLJfRAaDb6jZA
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    StorageAppDetailDialog.this.lambda$null$3$StorageAppDetailDialog(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).setPositiveButtonInterceptDismiss(true).setCloseVisibility(true).show();
        }
    }

    public /* synthetic */ void lambda$null$3$StorageAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        this.mStorageSettingsViewModel.unInstallApp(this.context, this.appStorageBean.getPackageName(), this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public void dismiss() {
        this.xDialogPure.dismiss();
    }

    public void setStorageOptionCallBack(StorageOptionCallBack storageOptionCallBack) {
        this.storageOptionCallBack = storageOptionCallBack;
    }

    public void show() {
        this.xDialogPure.show();
    }

    public void refreshData(AppStorageBean appStorageBean) {
        if (this.xDialogPure.isShowing()) {
            this.appStorageBean = appStorageBean;
            initData();
        }
    }
}
