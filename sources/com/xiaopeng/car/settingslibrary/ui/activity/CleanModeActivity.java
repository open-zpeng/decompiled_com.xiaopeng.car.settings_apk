package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.xui.app.ActivityUtils;
import com.xiaopeng.xui.utils.XActivityUtils;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class CleanModeActivity extends BaseActivity {
    private Button mButton;
    private Button mButtonPsn;
    private TextView mCleanTxt;
    private TextView mCleanTxtPsn;
    private View mPsnView;
    private long mStartTime;
    private Set<View> mTouchingView = new HashSet();
    Handler mHandler = new Handler(new Handler.Callback() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.CleanModeActivity.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                int currentTimeMillis = 3 - ((int) ((System.currentTimeMillis() - CleanModeActivity.this.mStartTime) / 1000));
                if (currentTimeMillis > 0) {
                    CleanModeActivity cleanModeActivity = CleanModeActivity.this;
                    cleanModeActivity.setText(cleanModeActivity.getResources().getQuantityString(R.plurals.clean_str_num, currentTimeMillis, Integer.valueOf(currentTimeMillis)));
                    CleanModeActivity.this.mHandler.sendEmptyMessageDelayed(1, 500L);
                } else {
                    String requestExitUserScenario = XuiSettingsManager.getInstance().requestExitUserScenario(XuiSettingsManager.USER_SCENARIO_CLEAN_MODE);
                    Logs.d("request exit clean mode ret:" + requestExitUserScenario);
                    ActivityUtils.finish(CleanModeActivity.this);
                }
            }
            return true;
        }
    });
    FinishReceiver mFinishReceiver = new FinishReceiver();
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.CleanModeActivity.2
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                Logs.d("xpsettings mOnTouchListener ACTION_DOWN");
                if (CleanModeActivity.this.mTouchingView.size() == 0) {
                    CleanModeActivity.this.mStartTime = System.currentTimeMillis();
                    CleanModeActivity cleanModeActivity = CleanModeActivity.this;
                    cleanModeActivity.setText(cleanModeActivity.getResources().getQuantityString(R.plurals.clean_str_num, 3, 3));
                    CleanModeActivity cleanModeActivity2 = CleanModeActivity.this;
                    cleanModeActivity2.setTextColor(cleanModeActivity2.getResources().getColor(R.color.x_theme_text_06, CleanModeActivity.this.getTheme()));
                    CleanModeActivity.this.mHandler.sendEmptyMessageDelayed(1, 500L);
                    CleanModeActivity.this.setPressed(true);
                }
                CleanModeActivity.this.mTouchingView.add(view);
            } else if (action == 1 || action == 3 || action == 4) {
                Logs.d("xpsettings mOnTouchListener ACTION_UP");
                CleanModeActivity.this.mTouchingView.remove(view);
                if (CleanModeActivity.this.mTouchingView.size() == 0) {
                    CleanModeActivity.this.setPressed(false);
                    CleanModeActivity.this.mHandler.removeMessages(1);
                    CleanModeActivity cleanModeActivity3 = CleanModeActivity.this;
                    cleanModeActivity3.setText(cleanModeActivity3.getString(R.string.display_clean_mode_title));
                    CleanModeActivity cleanModeActivity4 = CleanModeActivity.this;
                    cleanModeActivity4.setTextColor(cleanModeActivity4.getResources().getColor(R.color.x_theme_text_07, CleanModeActivity.this.getTheme()));
                }
            }
            return true;
        }
    };

    private void setScreenBrightnessLow() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = 0.05f;
            window.setAttributes(attributes);
        }
    }

    private void restoreScreenBrightness() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = -1.0f;
            window.setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null || intent.getAction() == null) {
            Logs.d("xpsettings cleanmode finish!");
            ActivityUtils.finish(this);
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        setContentView(R.layout.activity_clear_mode);
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.CLEAN_MODE_FINISH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFinishReceiver, intentFilter);
    }

    /* loaded from: classes.dex */
    class FinishReceiver extends BroadcastReceiver {
        FinishReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("cleanmode finish Receiver " + action);
            XActivityUtils.finish(CleanModeActivity.this);
        }
    }

    private void initView() {
        this.mCleanTxt = (TextView) findViewById(R.id.tv_long_press_exit_clean_mode);
        this.mCleanTxtPsn = (TextView) findViewById(R.id.tv_long_press_exit_clean_mode_psn);
        this.mButton = (Button) findViewById(R.id.btn_long_press_exit);
        this.mButtonPsn = (Button) findViewById(R.id.btn_long_press_exit_psn);
        this.mButton.setOnTouchListener(this.mOnTouchListener);
        this.mButtonPsn.setOnTouchListener(this.mOnTouchListener);
        this.mPsnView = findViewById(R.id.psn_view);
        if (CarFunction.isSupportDoubleScreen()) {
            this.mPsnView.setVisibility(0);
        } else {
            this.mPsnView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        restoreScreenBrightness();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        setScreenBrightnessLow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (isFinishing()) {
            return;
        }
        String requestExitUserScenario = XuiSettingsManager.getInstance().requestExitUserScenario(XuiSettingsManager.USER_SCENARIO_CLEAN_MODE);
        Logs.d("onStop request exit clean mode ret:" + requestExitUserScenario);
        ActivityUtils.finish(this);
    }

    private void hide() {
        getWindow().getDecorView().setSystemUiVisibility(4871);
    }

    private void show() {
        getWindow().getDecorView().setSystemUiVisibility(1536);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (4 == i) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFinishReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setText(String str) {
        this.mCleanTxt.setText(str);
        this.mCleanTxtPsn.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextColor(int i) {
        this.mCleanTxt.setTextColor(i);
        this.mCleanTxtPsn.setTextColor(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPressed(boolean z) {
        this.mButton.setPressed(z);
        this.mButtonPsn.setPressed(z);
    }
}
