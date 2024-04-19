package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.speech.VuiActivity;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.DownLoadFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.PsnBluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.USBFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.WlanFragment;
import com.xiaopeng.xui.app.ActivityUtils;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
/* loaded from: classes.dex */
public class PopupActivity extends VuiActivity {
    FinishReceiver mFinishReceiver = new FinishReceiver();
    private Fragment mFragment;
    protected XDialogView mXDialog;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        XDialogView xDialogView = new XDialogView(this, R.style.BluetoothDialog);
        xDialogView.setCustomView(R.layout.activity_popup, false);
        xDialogView.setCloseVisibility(true);
        xDialogView.getContentView().findViewById(R.id.x_dialog_close).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$PopupActivity$JwqQBCmL5tyrCR7MyigvKpMYIp0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupActivity.this.lambda$onCreate$0$PopupActivity(view);
            }
        });
        setContentView(xDialogView.getContentView());
        this.mXDialog = xDialogView;
        Window window = getWindow();
        window.setGravity(17);
        window.setLayout((int) getResources().getDimension(R.dimen.x_dialog_large_width), (int) getResources().getDimension(R.dimen.x_dialog_xlarge_height));
        getWindow().setCloseOnTouchOutside(true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.POPUP_FINISH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFinishReceiver, intentFilter);
    }

    public /* synthetic */ void lambda$onCreate$0$PopupActivity(View view) {
        ActivityUtils.finish(this);
    }

    private void hide() {
        getWindow().getDecorView().setSystemUiVisibility(4871);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void show(String str) {
        BaseFragment psnBluetoothFragment;
        Logs.d("xppopup init  action : " + str);
        if (Config.POPUP_BLUETOOTH.equals(str)) {
            psnBluetoothFragment = new BluetoothFragment();
            ((BluetoothFragment) psnBluetoothFragment).setDialog(true);
            this.mXDialog.setTitle(R.string.title_bluetooth);
            this.mSceneId = VuiManager.SCENE_BLUETOOTH_PUBLIC_DIALOG;
        } else if (Config.POPUP_WLAN.equals(str)) {
            psnBluetoothFragment = new WlanFragment();
            ((WlanFragment) psnBluetoothFragment).setDialog(true);
            this.mXDialog.setTitle(R.string.title_wifi);
            this.mSceneId = VuiManager.SCENE_WLAN_PUBLIC_DIALOG;
        } else if (Config.POPUP_USB.equals(str)) {
            psnBluetoothFragment = new USBFragment();
            this.mXDialog.setTitle(R.string.title_usb);
        } else if (Config.POPUP_DOWNLOAD.equals(str)) {
            psnBluetoothFragment = new DownLoadFragment();
            this.mXDialog.setTitle(R.string.title_download);
        } else if (Config.CO_DRIVER_BLUETOOTH.equals(str)) {
            psnBluetoothFragment = new PsnBluetoothFragment();
            ((PsnBluetoothFragment) psnBluetoothFragment).setDialog(true);
            this.mXDialog.setTitle(R.string.title_co_driver_bluetooth);
            this.mSceneId = VuiManager.SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG;
        } else {
            ActivityUtils.finish(this);
            return;
        }
        BaseFragment baseFragment = psnBluetoothFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, baseFragment);
        beginTransaction.commit();
        this.mFragment = baseFragment;
        if (this.mSceneId != null) {
            VuiManager.instance().initVuiScene(baseFragment, this.mSceneId, false, null, null, true);
            this.mRootView = this.mXDialog.getContentView();
            this.isWholeScene = true;
            createVuiScene();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiActivity, com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (CarFunction.isSupportSharedDisplay()) {
            return;
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.0f;
        getWindow().setAttributes(attributes);
        if (this.mFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(this.mFragment).commit();
        }
        XDialogView xDialogView = this.mXDialog;
        if (xDialogView != null) {
            xDialogView.getContentView().removeAllViews();
        }
        ActivityUtils.finish(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XpThemeUtils.setWindowBackgroundResource(configuration, getWindow(), R.drawable.x_bg_dialog);
    }

    /* loaded from: classes.dex */
    class FinishReceiver extends BroadcastReceiver {
        FinishReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("popup finish Receiver " + action);
            if (Config.POPUP_FINISH_ACTION.equals(action)) {
                if (PopupActivity.this.getClass().getName().equals(intent.getStringExtra(Config.EXTRA_POPUP_FINISH))) {
                    XActivityUtils.finish(PopupActivity.this);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.speech.VuiActivity, com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFinishReceiver);
    }
}
