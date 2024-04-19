package com.xiaopeng.car.settingslibrary.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.xui.app.ActivityUtils;
/* loaded from: classes.dex */
public class NotFoundActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() != null) {
            Logs.log("NotFoundActivity", getIntent().toString());
        }
        ToastUtils.get().showText(getString(R.string.not_found_activity));
        ActivityUtils.finish(this);
    }
}
