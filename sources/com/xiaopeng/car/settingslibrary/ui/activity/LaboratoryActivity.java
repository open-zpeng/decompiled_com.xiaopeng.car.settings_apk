package com.xiaopeng.car.settingslibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment;
import com.xiaopeng.xui.app.ActivityUtils;
import com.xiaopeng.xui.widget.XTitleBar;
/* loaded from: classes.dex */
public class LaboratoryActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!CarFunction.isXmartOS5()) {
            Logs.d("LaboratoryActivity finish");
            finish();
            return;
        }
        setContentView(R.layout.laboratory_container);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, new LaboratoryFragment());
        beginTransaction.commit();
        XTitleBar xTitleBar = (XTitleBar) findViewById(R.id.xTitleBar);
        xTitleBar.setTitle(CarSettingsApp.getContext().getString(R.string.title_laboratory));
        if (CarFunction.isXmartOS5()) {
            xTitleBar.setCloseVisibility(8);
            xTitleBar.setBackVisibility(8);
            xTitleBar.setPadding((int) getResources().getDimension(R.dimen.page_padding_left), 0, 0, 0);
        }
        xTitleBar.setOnTitleBarClickListener(new XTitleBar.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.LaboratoryActivity.1
            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarActionClick(View view, int i) {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarBackClick() {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                ActivityUtils.finish(LaboratoryActivity.this);
            }
        });
    }
}
