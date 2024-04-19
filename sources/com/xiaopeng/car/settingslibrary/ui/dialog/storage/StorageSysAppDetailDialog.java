package com.xiaopeng.car.settingslibrary.ui.dialog.storage;

import android.content.Context;
import android.content.DialogInterface;
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
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
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
public class StorageSysAppDetailDialog {
    AppStorageBean appStorageBean;
    XButton btn_clean;
    Context context;
    XImageButton ib_close;
    StorageSettingsViewModel mStorageSettingsViewModel;
    private StorageOptionCallBack storageOptionCallBack;
    XTextView tv_apps_name;
    XTextView tv_storage_size;
    XTextView tv_tip;
    XTextView tv_title;
    XDialogPure xDialogPure;
    XTitleBarLight x_title_bar;
    boolean isCleaned = false;
    IntervalControl intervalControl = new IntervalControl("StorageSysAppDetailDialog");

    public StorageSysAppDetailDialog(Context context, AppStorageBean appStorageBean, StorageSettingsViewModel storageSettingsViewModel) {
        this.context = context;
        this.appStorageBean = appStorageBean;
        this.mStorageSettingsViewModel = storageSettingsViewModel;
        initBP();
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        XDialogPure.Parameters Builder = XDialogPure.Parameters.Builder();
        Builder.setLayoutParams(new ViewGroup.LayoutParams(this.context.getResources().getDimensionPixelSize(R.dimen.storage_dialog_width), -2));
        this.xDialogPure = new XDialogPure(this.context, Builder);
        this.xDialogPure.setSystemDialog(2008);
        this.xDialogPure.setScreenId(0);
        if (CarFunction.isXmartOS5()) {
            this.xDialogPure.setContentView(R.layout.dialog_view_storage_detail_sys_v5);
        } else {
            this.xDialogPure.setContentView(R.layout.dialog_view_storage_detail_sys);
        }
        this.x_title_bar = (XTitleBarLight) this.xDialogPure.getDialog().findViewById(R.id.x_title_bar);
        this.ib_close = (XImageButton) this.xDialogPure.getDialog().findViewById(R.id.ib_close);
        this.tv_title = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_title);
        this.tv_apps_name = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_apps_name);
        this.tv_storage_size = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_storage_size);
        this.tv_tip = (XTextView) this.xDialogPure.getDialog().findViewById(R.id.tv_tip);
        this.btn_clean = (XButton) this.xDialogPure.getDialog().findViewById(R.id.btn_clean);
    }

    private void initBP() {
        if (XpAboutManager.isMusicApp(this.appStorageBean.getPackageName())) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B008", BuriedPointUtils.SIZE_KEY, Long.valueOf(FileUtils.parseByte2KB(this.appStorageBean.getTotalSize())));
        } else if (XpAboutManager.isNeteaseMusicApp(this.appStorageBean.getPackageName())) {
            XpAboutManager.sendAppDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B011", this.appStorageBean.getName(), Long.valueOf(FileUtils.parseByte2KB(this.appStorageBean.getTotalSize())));
        } else if (XpAboutManager.isCameraApp(this.appStorageBean.getPackageName())) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B005", BuriedPointUtils.SIZE_KEY, Long.valueOf(FileUtils.parseByte2KB(this.appStorageBean.getTotalSize())));
        } else if (XpAboutManager.isCacheClean(this.appStorageBean.getPackageName())) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B002");
        }
    }

    private void initData() {
        this.x_title_bar.setTitle(this.appStorageBean.getName());
        this.tv_title.setText(this.appStorageBean.getName());
        this.tv_storage_size.setText(FileUtils.getFileSizeDescription(this.appStorageBean.getTotalSize()));
        this.btn_clean.setEnabled(this.appStorageBean.getTotalSize() != 0);
        this.btn_clean.setText(R.string.storage_clean_btn);
        if (XpAboutManager.isMusicApp(this.appStorageBean.getPackageName())) {
            this.tv_apps_name.setText(R.string.storage_app_data);
            this.tv_tip.setText(R.string.storage_clean_music_tip);
        } else if (XpAboutManager.isNeteaseMusicApp(this.appStorageBean.getPackageName())) {
            this.tv_apps_name.setText(R.string.storage_app_size);
            this.btn_clean.setText(R.string.storage_uninstall_btn);
            this.tv_tip.setText(R.string.storage_clean_netease_music_tip);
        } else if (XpAboutManager.isCameraApp(this.appStorageBean.getPackageName())) {
            this.tv_apps_name.setText(R.string.storage_app_data);
            this.tv_tip.setText(R.string.storage_clean_camera_tip);
        } else if (XpAboutManager.isCacheClean(this.appStorageBean.getPackageName())) {
            this.tv_apps_name.setText(R.string.storage_cache_data);
            this.tv_tip.setText(R.string.storage_clean_cache_tip);
        }
    }

    private void initEvent() {
        this.x_title_bar.setOnTitleBarClickListener(new XTitleBarLight.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageSysAppDetailDialog.1
            {
                StorageSysAppDetailDialog.this = this;
            }

            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                StorageSysAppDetailDialog.this.dismiss();
            }

            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarBackClick() {
                StorageSysAppDetailDialog.this.dismiss();
            }
        });
        this.ib_close.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$x6S7xjgJJJaDO5hTt2oSLHwLCLs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageSysAppDetailDialog.this.lambda$initEvent$0$StorageSysAppDetailDialog(view);
            }
        });
        this.btn_clean.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$2mrwdeYOVP3Z_wy-cpfpfHYzOZk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageSysAppDetailDialog.this.lambda$initEvent$5$StorageSysAppDetailDialog(view);
            }
        });
        this.xDialogPure.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageSysAppDetailDialog.2
            {
                StorageSysAppDetailDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                StorageSysAppDetailDialog.this.isCleaned = false;
            }
        });
    }

    public /* synthetic */ void lambda$initEvent$0$StorageSysAppDetailDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initEvent$5$StorageSysAppDetailDialog(View view) {
        if (XpAboutManager.isMusicApp(this.appStorageBean.getPackageName())) {
            if (!this.mStorageSettingsViewModel.isGearP()) {
                XToast.show(R.string.clean_not_gear_p);
                return;
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B009");
            new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_clean_btn).setMessage(R.string.storage_clean_music_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$QFiTygzPFCIc6cww_ODKKiFck4s
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    StorageSysAppDetailDialog.this.lambda$null$1$StorageSysAppDetailDialog(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).setCloseVisibility(true).setPositiveButtonInterceptDismiss(true).show();
        } else if (XpAboutManager.isCameraApp(this.appStorageBean.getPackageName())) {
            if (!this.mStorageSettingsViewModel.isGearP()) {
                XToast.show(R.string.clean_not_gear_p);
                return;
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B006");
            new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_clean_btn).setMessage(R.string.storage_clean_camera_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$js-f1G8Mc10iyUNemnU2m6PULws
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    StorageSysAppDetailDialog.this.lambda$null$2$StorageSysAppDetailDialog(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).setCloseVisibility(true).setPositiveButtonInterceptDismiss(true).show();
        } else if (XpAboutManager.isNeteaseMusicApp(this.appStorageBean.getPackageName())) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B014", "name", this.appStorageBean.getName());
            new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_uninstall_btn).setMessage(R.string.storage_clean_netease_music_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$XI_4tp-xQtpmZ2hG0pi1ejvsrA8
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    StorageSysAppDetailDialog.this.lambda$null$3$StorageSysAppDetailDialog(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).setCloseVisibility(true).setPositiveButtonInterceptDismiss(true).show();
        } else if (XpAboutManager.isCacheClean(this.appStorageBean.getPackageName())) {
            if (!this.mStorageSettingsViewModel.isGearP()) {
                XToast.show(R.string.clean_not_gear_p);
            } else if (this.isCleaned) {
                ToastUtils.get().showText(R.string.clean_finished_tip);
            } else {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B003");
                new XDialog(this.context).setSystemDialog(2008).setTitle(R.string.storage_clean_btn).setMessage(R.string.storage_clean_cache_confirm_tip).setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageSysAppDetailDialog$1Fv5E8V8SlO8lLOg-V4v5aC_-k4
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        StorageSysAppDetailDialog.this.lambda$null$4$StorageSysAppDetailDialog(xDialog, i);
                    }
                }).setNegativeButton(R.string.cancel).setCloseVisibility(true).setPositiveButtonInterceptDismiss(true).show();
            }
        }
    }

    public /* synthetic */ void lambda$null$1$StorageSysAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        if (!this.mStorageSettingsViewModel.isGearP()) {
            XToast.show(R.string.clean_not_gear_p);
            return;
        }
        this.mStorageSettingsViewModel.clearMusicBackground(this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageSysAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public /* synthetic */ void lambda$null$2$StorageSysAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        if (!this.mStorageSettingsViewModel.isGearP()) {
            XToast.show(R.string.clean_not_gear_p);
            return;
        }
        this.mStorageSettingsViewModel.clearCameraBackground(this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageSysAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public /* synthetic */ void lambda$null$3$StorageSysAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        this.mStorageSettingsViewModel.unInstallApp(this.context, this.appStorageBean.getPackageName(), this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageSysAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public /* synthetic */ void lambda$null$4$StorageSysAppDetailDialog(XDialog xDialog, int i) {
        if (this.intervalControl.isFrequently(1000)) {
            return;
        }
        if (!this.mStorageSettingsViewModel.isGearP()) {
            XToast.show(R.string.clean_not_gear_p);
            return;
        }
        this.isCleaned = true;
        this.mStorageSettingsViewModel.cleanAllCache(this.storageOptionCallBack);
        xDialog.getClass();
        ThreadUtils.postOnMainThreadDelay(new $$Lambda$StorageSysAppDetailDialog$ER6ca12hkbmmaIl2tUgcwJNzdEs(xDialog), 100L);
    }

    public void setStorageOptionCallBack(StorageOptionCallBack storageOptionCallBack) {
        this.storageOptionCallBack = storageOptionCallBack;
    }

    public void dismiss() {
        this.xDialogPure.dismiss();
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
