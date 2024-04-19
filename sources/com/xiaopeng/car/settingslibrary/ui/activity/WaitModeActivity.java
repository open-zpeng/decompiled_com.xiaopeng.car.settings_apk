package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.xui.utils.XActivityUtils;
/* loaded from: classes.dex */
public class WaitModeActivity extends BaseActivity {
    private static final String PM_STATUS_CHANGE = "com.xiaopeng.broadcast.ACTION_PM_STATUS_CHANGE";
    private static final String SCREEN_STATUS_CHANGE_ACTION = "com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE";
    private static final String TAG = WaitModeActivity.class.getSimpleName();
    FinishReceiver mFinishReceiver = new FinishReceiver();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaopeng.broadcast.ACTION_PM_STATUS_CHANGE");
        intentFilter.addAction("com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE");
        intentFilter.addAction(Config.WAIT_MODE_FINISH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFinishReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Logs.d(TAG + " onNewIntent");
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Logs.d(TAG + " xpsettings wait mode ev:" + motionEvent);
        if (motionEvent.getAction() == 0) {
            XActivityUtils.finish(this);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFinishReceiver);
    }

    /* loaded from: classes.dex */
    class FinishReceiver extends BroadcastReceiver {
        FinishReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d(WaitModeActivity.TAG + " finish Receiver " + action);
            XActivityUtils.finish(WaitModeActivity.this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            Logs.d(TAG + " xpsettings keyDown back!");
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}
