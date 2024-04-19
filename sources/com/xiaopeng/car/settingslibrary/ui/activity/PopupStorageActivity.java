package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.speech.VuiActivity;
import com.xiaopeng.car.settingslibrary.ui.view.StorageView;
import com.xiaopeng.xui.utils.XActivityUtils;
/* loaded from: classes.dex */
public class PopupStorageActivity extends VuiActivity implements StorageView.OnStorageShowListener {
    public static final String STORAGE_SOURCE = "storage_source";
    Context context;
    FinishReceiver mFinishReceiver = new FinishReceiver();
    private String source;
    StorageView storageView;

    public static void start(Context context, String str) {
        Intent intent = new Intent(context, PopupStorageActivity.class);
        intent.putExtra(STORAGE_SOURCE, str);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.storage_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        this.storageView = (StorageView) findViewById(R.id.storage_view);
    }

    private void initData() {
        this.source = getIntent().getStringExtra(STORAGE_SOURCE);
        this.storageView.init(this.source, this);
    }

    private void initEvent() {
        this.storageView.setOnStorageShowListener(new StorageView.OnStorageShowListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$PopupStorageActivity$1mffEN90s3V5acKgYGbw2rKsjHQ
            @Override // com.xiaopeng.car.settingslibrary.ui.view.StorageView.OnStorageShowListener
            public final void onHandleDismiss() {
                PopupStorageActivity.this.finish();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.POPUP_FINISH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFinishReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.speech.VuiActivity, com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.storageView.onDismissUI();
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFinishReceiver);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.view.StorageView.OnStorageShowListener
    public void onHandleDismiss() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class FinishReceiver extends BroadcastReceiver {
        FinishReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("popup finish Receiver " + action);
            if (Config.POPUP_FINISH_ACTION.equals(action)) {
                if (PopupStorageActivity.this.getClass().getName().equals(intent.getStringExtra(Config.EXTRA_POPUP_FINISH))) {
                    XActivityUtils.finish(PopupStorageActivity.this);
                }
            }
        }
    }
}
