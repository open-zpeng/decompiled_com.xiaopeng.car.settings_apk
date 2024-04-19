package com.xiaopeng.car.settingslibrary.ui.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class BaseActivity extends AppCompatActivity {
    private boolean mIsStopped = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append("---onCreate ");
        sb.append(bundle == null ? "new" : "reset");
        sb.append(", orientation: ");
        sb.append(getResources().getConfiguration().orientation);
        Logs.logObj("BaseActivity", sb.toString(), this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        Logs.logObj("BaseActivity", "---onStart, orientation: " + getResources().getConfiguration().orientation, this);
        this.mIsStopped = false;
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        Logs.logObj("BaseActivity", "---onRestart, orientation: " + getResources().getConfiguration().orientation, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logs.logObj("BaseActivity", "---onNewIntent, orientation: " + getResources().getConfiguration().orientation, this);
        this.mIsStopped = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Logs.logObj("BaseActivity", "---onResume, orientation: " + getResources().getConfiguration().orientation, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Logs.logObj("BaseActivity", "---onPause, orientation: " + getResources().getConfiguration().orientation, this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Logs.logObj("BaseActivity", "---onWindowFocusChanged " + z + ", orientation: " + getResources().getConfiguration().orientation, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Logs.logObj("BaseActivity", "---onSaveInstanceState , orientation: " + getResources().getConfiguration().orientation, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Logs.logObj("BaseActivity", "---onStop, orientation: " + getResources().getConfiguration().orientation, this);
        this.mIsStopped = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logs.logObj("BaseActivity", "---onDestroy", this);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        Logs.logObj("BaseActivity", "---finalize", this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStopped() {
        return this.mIsStopped;
    }
}
