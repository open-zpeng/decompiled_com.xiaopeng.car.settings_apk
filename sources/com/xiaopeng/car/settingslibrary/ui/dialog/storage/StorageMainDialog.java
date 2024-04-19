package com.xiaopeng.car.settingslibrary.ui.dialog.storage;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.view.StorageView;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.app.XDialogPure;
/* loaded from: classes.dex */
public class StorageMainDialog implements LifecycleOwner, StorageView.OnStorageShowListener {
    Context context;
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private final String source;
    StorageView storageView;
    XDialogPure xDialogPure;

    public StorageMainDialog(Context context, String str) {
        this.context = context;
        this.source = str;
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        XDialogPure.Parameters Builder = XDialogPure.Parameters.Builder();
        this.context.setTheme(R.style.AppTheme);
        Builder.setLayoutParams(new ViewGroup.LayoutParams(this.context.getResources().getDimensionPixelSize(R.dimen.storage_dialog_width), this.context.getResources().getDimensionPixelSize(R.dimen.storage_dialog_main_height)));
        this.xDialogPure = new XDialogPure(this.context, Builder);
        this.xDialogPure.setSystemDialog(2008);
        this.xDialogPure.setScreenId(0);
        this.storageView = new StorageView(this.context);
        this.storageView.setOnStorageShowListener(this);
        this.xDialogPure.setContentView(this.storageView);
    }

    private void initData() {
        this.storageView.init(this.source, this);
    }

    private void initEvent() {
        this.xDialogPure.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageMainDialog$GkpuTMTY2CfhGzFIf0Iiot-yQb0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                StorageMainDialog.this.lambda$initEvent$0$StorageMainDialog(dialogInterface);
            }
        });
        this.xDialogPure.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.storage.-$$Lambda$StorageMainDialog$9moNI1fiw9EHh-YCPW5oIdYRH7E
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                StorageMainDialog.this.lambda$initEvent$1$StorageMainDialog(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initEvent$0$StorageMainDialog(DialogInterface dialogInterface) {
        this.storageView.onShowUI();
    }

    public /* synthetic */ void lambda$initEvent$1$StorageMainDialog(DialogInterface dialogInterface) {
        this.storageView.onDismissUI();
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public void show() {
        if (this.xDialogPure.isShowing()) {
            return;
        }
        this.xDialogPure.show();
        this.xDialogPure.initVuiScene(VuiManager.SCENE_STORE_DIALOG, VuiEngine.getInstance(this.xDialogPure.getDialog().getContext()));
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    public void dismiss() {
        this.xDialogPure.dismiss();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.view.StorageView.OnStorageShowListener
    public void onHandleDismiss() {
        this.xDialogPure.dismiss();
    }
}
