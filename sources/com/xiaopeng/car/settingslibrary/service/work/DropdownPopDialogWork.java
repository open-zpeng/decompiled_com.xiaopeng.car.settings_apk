package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.view.BluetoothView;
import com.xiaopeng.car.settingslibrary.ui.view.DownloadView;
import com.xiaopeng.car.settingslibrary.ui.view.ExternalBluetoothView;
import com.xiaopeng.car.settingslibrary.ui.view.PsnBluetoothView;
import com.xiaopeng.car.settingslibrary.ui.view.UsbView;
import com.xiaopeng.car.settingslibrary.ui.view.WlanView;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.Map;
/* loaded from: classes.dex */
public class DropdownPopDialogWork implements WorkService {
    private XDialog mBluetoothDialog;
    private Context mContext;
    private XDialog mDownloadDialog;
    private XDialog mExternalBluetoothDialog;
    private XDialog mPsnBluetoothDialog;
    private XDialog mUsbDialog;
    private XDialog mWlanDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        XDialog xDialog;
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(Config.CLOSE_POPUP_DIALOG)) {
            String stringExtra = intent.getStringExtra(Config.EXTRA_WHICH_DIALOG);
            Logs.d("xppopup close action:" + action + " " + stringExtra);
            if (Config.POPUP_BLUETOOTH.equals(stringExtra)) {
                XDialog xDialog2 = this.mBluetoothDialog;
                if (xDialog2 == null || !xDialog2.isShowing()) {
                    return;
                }
                this.mBluetoothDialog.dismiss();
            } else if (Config.CO_DRIVER_BLUETOOTH.equals(stringExtra)) {
                XDialog xDialog3 = this.mPsnBluetoothDialog;
                if (xDialog3 == null || !xDialog3.isShowing()) {
                    return;
                }
                this.mPsnBluetoothDialog.dismiss();
            } else if (Config.BACK_SEAT_BLUETOOTH.equals(stringExtra)) {
                XDialog xDialog4 = this.mExternalBluetoothDialog;
                if (xDialog4 == null || !xDialog4.isShowing()) {
                    return;
                }
                this.mExternalBluetoothDialog.dismiss();
            } else if (Config.POPUP_WLAN.equals(stringExtra)) {
                XDialog xDialog5 = this.mWlanDialog;
                if (xDialog5 == null || !xDialog5.isShowing()) {
                    return;
                }
                this.mWlanDialog.dismiss();
            } else if (Config.POPUP_DOWNLOAD.equals(stringExtra)) {
                XDialog xDialog6 = this.mDownloadDialog;
                if (xDialog6 == null || !xDialog6.isShowing()) {
                    return;
                }
                this.mDownloadDialog.dismiss();
            } else if (Config.POPUP_USB.equals(stringExtra) && (xDialog = this.mUsbDialog) != null && xDialog.isShowing()) {
                this.mUsbDialog.dismiss();
            }
        } else if (Config.POPUP_BLUETOOTH.equals(action) || Config.POPUP_WLAN.equals(action) || Config.CO_DRIVER_BLUETOOTH.equals(action) || Config.BACK_SEAT_BLUETOOTH.equals(action) || Config.POPUP_USB.equals(action) || Config.POPUP_DOWNLOAD.equals(action)) {
            int intExtra = intent.getIntExtra(Config.EXTRA_POPUP_SCREEN, 0);
            Logs.d("xppopup action:" + action + " screenId:" + intExtra);
            for (Map.Entry<String, Integer> entry : AppServerManager.getInstance().getCurrentShowingDialogs().entrySet()) {
                String key = entry.getKey();
                if (entry.getValue().intValue() == intExtra && !key.equals(action)) {
                    AppServerManager.getInstance().closePopupDialog(key);
                }
            }
            show(action, intExtra);
        }
    }

    protected void show(String str, int i) {
        Logs.d("xppopup init  action : " + str + " screenId:" + i);
        if (Config.POPUP_BLUETOOTH.equals(str)) {
            showBluetoothDialog(i);
        } else if (Config.POPUP_WLAN.equals(str)) {
            showWlanDialog(i);
        } else if (Config.POPUP_USB.equals(str)) {
            showUsbDialog(i);
        } else if (Config.POPUP_DOWNLOAD.equals(str)) {
            showDownloadDialog(i);
        } else if (Config.CO_DRIVER_BLUETOOTH.equals(str)) {
            showPsnBluetoothDialog(i);
        } else if (Config.BACK_SEAT_BLUETOOTH.equals(str)) {
            showExternalBluetoothDialog(i);
        }
    }

    private void showPsnBluetoothDialog(int i) {
        this.mContext.setTheme(R.style.AppTheme);
        if (this.mPsnBluetoothDialog == null) {
            this.mPsnBluetoothDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
            if (CarFunction.isNonSelfPageUI()) {
                this.mPsnBluetoothDialog.setSystemDialog(2008);
            } else {
                this.mPsnBluetoothDialog.setSystemDialog(2048);
            }
            this.mPsnBluetoothDialog.setCloseVisibility(true);
            this.mPsnBluetoothDialog.setTitle(R.string.title_co_driver_bluetooth);
        }
        Utils.popupToScreenId(this.mPsnBluetoothDialog.getDialog(), 1);
        this.mPsnBluetoothDialog.setCustomView(R.layout.psn_bluetooth_popup_dialog, false);
        final PsnBluetoothView psnBluetoothView = (PsnBluetoothView) this.mPsnBluetoothDialog.getContentView().findViewById(R.id.psnBluetoothPop);
        psnBluetoothView.setScreenId(i);
        psnBluetoothView.setDialog(true);
        psnBluetoothView.createView();
        this.mPsnBluetoothDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$mEZ2viiAE0o9G8BJho6Jj1Q5sjc
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showPsnBluetoothDialog$0(PsnBluetoothView.this, dialogInterface);
            }
        });
        this.mPsnBluetoothDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$YcFqzD8hD2PqyXePSokGOXxTpaw
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showPsnBluetoothDialog$1(PsnBluetoothView.this, dialogInterface);
            }
        });
        psnBluetoothView.setSceneId(VuiManager.SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG);
        this.mPsnBluetoothDialog.show();
        VuiManager.instance().initVuiDialog(this.mPsnBluetoothDialog, this.mContext, VuiManager.SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG);
        this.mPsnBluetoothDialog.initVuiScene(VuiManager.SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG, VuiEngine.getInstance(CarSettingsApp.getContext()), new AnonymousClass1(psnBluetoothView));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showPsnBluetoothDialog$0(PsnBluetoothView psnBluetoothView, DialogInterface dialogInterface) {
        psnBluetoothView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.CO_DRIVER_BLUETOOTH, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showPsnBluetoothDialog$1(PsnBluetoothView psnBluetoothView, DialogInterface dialogInterface) {
        psnBluetoothView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.CO_DRIVER_BLUETOOTH);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.service.work.DropdownPopDialogWork$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements IVuiSceneListener {
        final /* synthetic */ PsnBluetoothView val$psnBluetoothView;

        AnonymousClass1(PsnBluetoothView psnBluetoothView) {
            this.val$psnBluetoothView = psnBluetoothView;
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public void onVuiStateChanged() {
            Logs.d("PsnBluetoothView onVuiStateChanged");
            final PsnBluetoothView psnBluetoothView = this.val$psnBluetoothView;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$1$puu08Z53C9HghlH3FOcqrCiIPqg
                @Override // java.lang.Runnable
                public final void run() {
                    PsnBluetoothView.this.getRecyclerView().getAdapter().notifyDataSetChanged();
                }
            });
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
            if (view != null) {
                VuiFloatingLayerManager.show(view);
                return false;
            }
            return false;
        }
    }

    private void showExternalBluetoothDialog(int i) {
        this.mContext.setTheme(R.style.AppTheme);
        if (this.mExternalBluetoothDialog == null) {
            this.mExternalBluetoothDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
            if (CarFunction.isNonSelfPageUI()) {
                this.mExternalBluetoothDialog.setSystemDialog(2008);
            } else {
                this.mExternalBluetoothDialog.setSystemDialog(2048);
            }
            this.mExternalBluetoothDialog.setCloseVisibility(true);
            this.mExternalBluetoothDialog.setTitle(R.string.title_back_seat_bluetooth);
        }
        Utils.popupToScreenId(this.mExternalBluetoothDialog.getDialog(), i);
        this.mExternalBluetoothDialog.setCustomView(R.layout.external_bluetooth_popup_dialog, false);
        final ExternalBluetoothView externalBluetoothView = (ExternalBluetoothView) this.mExternalBluetoothDialog.getContentView().findViewById(R.id.externalBluetoothPop);
        externalBluetoothView.setScreenId(i);
        externalBluetoothView.setDialog(true);
        externalBluetoothView.createView();
        this.mExternalBluetoothDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$wU1-JLPAm5UT2b-jNjI1JEdkvv8
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showExternalBluetoothDialog$2(ExternalBluetoothView.this, dialogInterface);
            }
        });
        this.mExternalBluetoothDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$0uFqzuA5Ot6BZlwaXzOpKCsKoxI
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showExternalBluetoothDialog$3(ExternalBluetoothView.this, dialogInterface);
            }
        });
        externalBluetoothView.setSceneId(VuiManager.SCENE_EXTERNAL_BLUETOOTH_PUBLIC_DIALOG);
        this.mExternalBluetoothDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showExternalBluetoothDialog$2(ExternalBluetoothView externalBluetoothView, DialogInterface dialogInterface) {
        externalBluetoothView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.BACK_SEAT_BLUETOOTH, 3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showExternalBluetoothDialog$3(ExternalBluetoothView externalBluetoothView, DialogInterface dialogInterface) {
        externalBluetoothView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.BACK_SEAT_BLUETOOTH);
    }

    private void showBluetoothDialog(final int i) {
        XDialog xDialog = this.mBluetoothDialog;
        if (xDialog != null && xDialog.isShowing()) {
            Logs.d("dropdown pop bluetooth showing");
            if (Utils.getPopScreenId(this.mBluetoothDialog.getDialog()) == i) {
                return;
            }
            this.mBluetoothDialog.dismiss();
        }
        this.mContext.setTheme(R.style.AppTheme);
        if (this.mBluetoothDialog == null) {
            this.mBluetoothDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
            if (CarFunction.isNonSelfPageUI()) {
                this.mBluetoothDialog.setSystemDialog(2008);
            } else {
                this.mBluetoothDialog.setSystemDialog(2048);
            }
            this.mBluetoothDialog.setCloseVisibility(true);
            this.mBluetoothDialog.setTitle(R.string.title_bluetooth);
        }
        this.mBluetoothDialog.setCustomView(R.layout.bluetooth_popup_dialog, false);
        final BluetoothView bluetoothView = (BluetoothView) this.mBluetoothDialog.getContentView().findViewById(R.id.bluetoothPop);
        bluetoothView.setScreenId(i);
        bluetoothView.setDialog(true);
        bluetoothView.createView();
        this.mBluetoothDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$j1Fap3NW4AL2esAvGQ-MNQw12oU
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showBluetoothDialog$4(BluetoothView.this, i, dialogInterface);
            }
        });
        this.mBluetoothDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$IvJlbH9cZfZPbcQ7ftrHbQKzP38
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                DropdownPopDialogWork.lambda$showBluetoothDialog$5(BluetoothView.this, dialogInterface);
            }
        });
        Utils.popupToScreenId(this.mBluetoothDialog.getDialog(), i);
        this.mBluetoothDialog.show();
        bluetoothView.setSceneId(VuiManager.SCENE_BLUETOOTH_PUBLIC_DIALOG);
        VuiManager.instance().initVuiDialog(this.mBluetoothDialog, this.mContext, VuiManager.SCENE_BLUETOOTH_PUBLIC_DIALOG);
        Logs.d("dropdown pop bluetooth show");
        this.mBluetoothDialog.initVuiScene(VuiManager.SCENE_BLUETOOTH_PUBLIC_DIALOG, VuiEngine.getInstance(CarSettingsApp.getContext()), new AnonymousClass2(bluetoothView));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showBluetoothDialog$4(BluetoothView bluetoothView, int i, DialogInterface dialogInterface) {
        bluetoothView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.POPUP_BLUETOOTH, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showBluetoothDialog$5(BluetoothView bluetoothView, DialogInterface dialogInterface) {
        bluetoothView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.POPUP_BLUETOOTH);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.service.work.DropdownPopDialogWork$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements IVuiSceneListener {
        final /* synthetic */ BluetoothView val$bluetoothView;

        AnonymousClass2(BluetoothView bluetoothView) {
            this.val$bluetoothView = bluetoothView;
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public void onVuiStateChanged() {
            Logs.d("BluetoothView onVuiStateChanged");
            final BluetoothView bluetoothView = this.val$bluetoothView;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$2$k7oxiVS2cuZH7m6jR1KXAJ_IlKw
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothView.this.getRecyclerView().getAdapter().notifyDataSetChanged();
                }
            });
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
            if (view != null) {
                VuiFloatingLayerManager.show(view);
                return false;
            }
            return false;
        }
    }

    private void showWlanDialog(int i) {
        XDialog xDialog = this.mWlanDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            this.mContext.setTheme(R.style.AppTheme);
            if (this.mWlanDialog == null) {
                this.mWlanDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
                if (CarFunction.isNonSelfPageUI()) {
                    this.mWlanDialog.setSystemDialog(2008);
                } else {
                    this.mWlanDialog.setSystemDialog(2048);
                }
                this.mWlanDialog.setCloseVisibility(true);
                this.mWlanDialog.setTitle(R.string.title_wifi);
            }
            Utils.popupToScreenId(this.mWlanDialog.getDialog(), 0);
            this.mWlanDialog.setCustomView(R.layout.wlan_popup_dialog, false);
            final WlanView wlanView = (WlanView) this.mWlanDialog.getContentView().findViewById(R.id.wlanPop);
            wlanView.setSceneId(VuiManager.SCENE_WLAN_PUBLIC_DIALOG);
            wlanView.setDialog(true);
            wlanView.createView();
            this.mWlanDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$QJWnfkwiFSad0RV8JhAd3VeZXcw
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showWlanDialog$6(WlanView.this, dialogInterface);
                }
            });
            this.mWlanDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$TjPjM2RIY1tXhrQtHuVwTQv_BGM
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showWlanDialog$7(WlanView.this, dialogInterface);
                }
            });
            this.mWlanDialog.show();
            VuiManager.instance().initVuiDialog(this.mWlanDialog, this.mContext, VuiManager.SCENE_WLAN_PUBLIC_DIALOG);
            this.mWlanDialog.initVuiScene(VuiManager.SCENE_WLAN_PUBLIC_DIALOG, VuiEngine.getInstance(CarSettingsApp.getContext()), new AnonymousClass3(wlanView));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showWlanDialog$6(WlanView wlanView, DialogInterface dialogInterface) {
        wlanView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.POPUP_WLAN, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showWlanDialog$7(WlanView wlanView, DialogInterface dialogInterface) {
        wlanView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.POPUP_WLAN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.service.work.DropdownPopDialogWork$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements IVuiSceneListener {
        final /* synthetic */ WlanView val$wlanView;

        AnonymousClass3(WlanView wlanView) {
            this.val$wlanView = wlanView;
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public void onVuiStateChanged() {
            Logs.d("WlanView onVuiStateChanged");
            final WlanView wlanView = this.val$wlanView;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$3$GgbKQYtqMtjzSDdEi6FTt-BTxlE
                @Override // java.lang.Runnable
                public final void run() {
                    WlanView.this.getRecyclerView().getAdapter().notifyDataSetChanged();
                }
            });
        }

        @Override // com.xiaopeng.vui.commons.IVuiSceneListener
        public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
            if (view != null) {
                VuiFloatingLayerManager.show(view);
                return false;
            }
            return false;
        }
    }

    private void showDownloadDialog(int i) {
        XDialog xDialog = this.mDownloadDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            this.mContext.setTheme(R.style.AppTheme);
            if (this.mDownloadDialog == null) {
                this.mDownloadDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
                if (CarFunction.isNonSelfPageUI()) {
                    this.mDownloadDialog.setSystemDialog(2008);
                } else {
                    this.mDownloadDialog.setSystemDialog(2048);
                }
                this.mDownloadDialog.setCloseVisibility(true);
                this.mDownloadDialog.setTitle(R.string.title_download);
            }
            Utils.popupToScreenId(this.mDownloadDialog.getDialog(), 0);
            this.mDownloadDialog.setCustomView(R.layout.download_popup_dialog, false);
            final DownloadView downloadView = (DownloadView) this.mDownloadDialog.getContentView().findViewById(R.id.downloadPop);
            this.mDownloadDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$G4PYgnQNogW5SPWVftAT83SM_vI
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showDownloadDialog$8(DownloadView.this, dialogInterface);
                }
            });
            this.mDownloadDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$SMVy14OUueSfKEbKvsQEhQ0jffg
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showDownloadDialog$9(DownloadView.this, dialogInterface);
                }
            });
            this.mDownloadDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDownloadDialog$8(DownloadView downloadView, DialogInterface dialogInterface) {
        downloadView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.POPUP_DOWNLOAD, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDownloadDialog$9(DownloadView downloadView, DialogInterface dialogInterface) {
        downloadView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.POPUP_DOWNLOAD);
    }

    private void showUsbDialog(int i) {
        XDialog xDialog = this.mUsbDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            this.mContext.setTheme(R.style.AppTheme);
            if (this.mUsbDialog == null) {
                this.mUsbDialog = new XDialog(this.mContext, R.style.BluetoothDialog);
                if (CarFunction.isNonSelfPageUI()) {
                    this.mUsbDialog.setSystemDialog(2008);
                } else {
                    this.mUsbDialog.setSystemDialog(2048);
                }
                this.mUsbDialog.setCloseVisibility(true);
                this.mUsbDialog.setTitle(R.string.title_usb);
            }
            Utils.popupToScreenId(this.mUsbDialog.getDialog(), 0);
            this.mUsbDialog.setCustomView(R.layout.usb_popup_dialog, false);
            final UsbView usbView = (UsbView) this.mUsbDialog.getContentView().findViewById(R.id.usbPop);
            this.mUsbDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$5KKUc-PxjrDfejIsQsAGK_-xLrg
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showUsbDialog$10(UsbView.this, dialogInterface);
                }
            });
            this.mUsbDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$DropdownPopDialogWork$r4G8ozj-7o0b13kxJcVBp7Yy0Ss
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    DropdownPopDialogWork.lambda$showUsbDialog$11(UsbView.this, dialogInterface);
                }
            });
            this.mUsbDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showUsbDialog$10(UsbView usbView, DialogInterface dialogInterface) {
        usbView.onStart();
        AppServerManager.getInstance().addPopupDialogShowing(Config.POPUP_USB, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showUsbDialog$11(UsbView usbView, DialogInterface dialogInterface) {
        usbView.onStop();
        AppServerManager.getInstance().removePopupDialogShowing(Config.POPUP_USB);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XDialog xDialog = this.mBluetoothDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mBluetoothDialog = null;
        }
        XDialog xDialog2 = this.mWlanDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
            this.mWlanDialog = null;
        }
        XDialog xDialog3 = this.mDownloadDialog;
        if (xDialog3 != null) {
            xDialog3.dismiss();
            this.mDownloadDialog = null;
        }
        XDialog xDialog4 = this.mPsnBluetoothDialog;
        if (xDialog4 != null) {
            xDialog4.dismiss();
            this.mPsnBluetoothDialog = null;
        }
        XDialog xDialog5 = this.mExternalBluetoothDialog;
        if (xDialog5 != null) {
            xDialog5.dismiss();
            this.mExternalBluetoothDialog = null;
        }
        XDialog xDialog6 = this.mUsbDialog;
        if (xDialog6 != null) {
            xDialog6.dismiss();
            this.mUsbDialog = null;
        }
        Logs.d("DropdownPopDialogWork onDestroy");
    }
}
