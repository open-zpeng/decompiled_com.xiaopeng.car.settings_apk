package com.xiaopeng.car.settingslibrary.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import java.util.Objects;
/* loaded from: classes.dex */
public class FallbackHome extends Activity {
    private static final int PROGRESS_TIMEOUT = 2000;
    private static final String TAG = "FallbackHome";
    private volatile boolean isStartOOBE;
    private boolean mProvisioned;
    private final Runnable mProgressTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$FallbackHome$v8SifTiS6WSyVLLOjPDa_gtgvgs
        @Override // java.lang.Runnable
        public final void run() {
            FallbackHome.this.lambda$new$0$FallbackHome();
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.FallbackHome.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            FallbackHome.this.maybeFinish();
        }
    };
    private Handler mHandler = new Handler() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.FallbackHome.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            FallbackHome.this.maybeFinish();
        }
    };

    public /* synthetic */ void lambda$new$0$FallbackHome() {
        getWindow().addFlags(128);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$FallbackHome$tLf7dzHFrHjytEOa_ydtt-_jXFc
            @Override // java.lang.Runnable
            public final void run() {
                FallbackHome.this.lambda$onCreate$2$FallbackHome();
            }
        });
        this.mProvisioned = Settings.Global.getInt(getContentResolver(), "device_provisioned", 0) != 0;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        registerReceiver(this.mReceiver, intentFilter);
        maybeFinish();
    }

    public /* synthetic */ void lambda$onCreate$2$FallbackHome() {
        this.isStartOOBE = CarFunction.isNeedStartOOBE() ? isOOBEComponent() : false;
        Logs.d("xpsettings fallbackhome onCreate bootComplete:" + isBootCompleted() + ",isStartOOBE:" + this.isStartOOBE);
        if (this.isStartOOBE) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$FallbackHome$wobraDJ7AabWfmmv-YSeniNrLko
                @Override // java.lang.Runnable
                public final void run() {
                    FallbackHome.this.lambda$null$1$FallbackHome();
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$1$FallbackHome() {
        getWindow().getDecorView().setBackgroundColor(getColor(R.color.x_theme_bg_below));
        setContentView(R.layout.fallback_home_finishing_boot);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        Logs.d("fallbackhome onResume");
        if (this.mProvisioned) {
            this.mHandler.removeCallbacks(this.mProgressTimeoutRunnable);
            this.mHandler.postDelayed(this.mProgressTimeoutRunnable, 2000L);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        Logs.d("fallbackhome onWindowFocusChanged:" + z);
        super.onWindowFocusChanged(z);
    }

    @Override // android.app.Activity
    public void recreate() {
        Logs.d("fallbackhome recreate");
        super.recreate();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        Logs.d("fallbackhome onNewIntent");
        super.onNewIntent(intent);
    }

    @Override // android.app.Activity
    protected void onRestart() {
        Logs.d("fallbackhome onRestart");
        super.onRestart();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mHandler.removeCallbacks(this.mProgressTimeoutRunnable);
    }

    @Override // android.app.Activity
    protected void onStop() {
        Logs.d("fallbackhome onStop");
        super.onStop();
        if (this.isStartOOBE) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
        Logs.d("xpsettings fallbackhome onDestroy");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinish() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$FallbackHome$5Jj6KEPsJ9CbM22h2Bj7zhqUPWc
            @Override // java.lang.Runnable
            public final void run() {
                FallbackHome.this.lambda$maybeFinish$3$FallbackHome();
            }
        });
    }

    public /* synthetic */ void lambda$maybeFinish$3$FallbackHome() {
        Logs.d("xpsettings fallbackhome maybeFinish ");
        if (((UserManager) getSystemService(UserManager.class)).isUserUnlocked()) {
            ResolveInfo resolveActivity = getPackageManager().resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0);
            Logs.d("xpsettings fallbackhome maybeFinish equals " + getPackageName() + " " + resolveActivity.activityInfo.packageName);
            if (Objects.equals(getPackageName(), resolveActivity.activityInfo.packageName)) {
                if (UserManager.isSplitSystemUser() && UserHandle.myUserId() == 0) {
                    return;
                }
                Log.d(TAG, "User unlocked but no home; let's hope someone enables one soon?");
                this.mHandler.sendEmptyMessageDelayed(0, this.isStartOOBE ? 100L : 500L);
                return;
            }
            Log.d(TAG, "User unlocked and real home found; let's go!");
            ((PowerManager) getSystemService(PowerManager.class)).userActivity(SystemClock.uptimeMillis(), false);
            if (this.isStartOOBE) {
                startHome();
            } else {
                finish();
            }
        }
    }

    private boolean isOOBEComponent() {
        try {
            int componentEnabledSetting = getPackageManager().getComponentEnabledSetting(new ComponentName("com.xiaopeng.oobe", "com.xiaopeng.oobe.OOBEActivity"));
            Logs.d("fallbackhome isOOBEComponent enable=" + componentEnabledSetting);
            return 1 == componentEnabledSetting || componentEnabledSetting == 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isBootCompleted() {
        return SystemProperties.getInt("service.bootanim.exit", 0) == 1;
    }

    private void startHome() {
        Logs.d("fallbackhome startHome");
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.addFlags(270532608);
            startActivity(intent);
        } catch (Exception e) {
            Logs.d("fallbackhome startHome e=" + e);
        }
    }
}
