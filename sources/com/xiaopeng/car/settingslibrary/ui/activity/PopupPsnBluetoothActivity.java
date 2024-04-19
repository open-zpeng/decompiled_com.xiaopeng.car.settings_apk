package com.xiaopeng.car.settingslibrary.ui.activity;

import android.os.Bundle;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class PopupPsnBluetoothActivity extends PopupActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.activity.PopupActivity, com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logs.d("xppopup psn-blue onCreate ");
        show(Config.CO_DRIVER_BLUETOOTH);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.activity.PopupActivity, com.xiaopeng.car.settingslibrary.speech.VuiActivity, com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logs.d("xppopup psn-blue onDestroy ");
    }
}
