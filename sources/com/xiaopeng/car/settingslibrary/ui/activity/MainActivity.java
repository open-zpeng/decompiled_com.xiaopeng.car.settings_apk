package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.direct.PageDirectItem;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.speech.VuiMultiSceneActivity;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.common.DebugUtils;
import com.xiaopeng.car.settingslibrary.ui.fragment.AboutFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.xui.app.ActivityUtils;
import com.xiaopeng.xui.widget.XTitleBar;
import java.util.Arrays;
/* loaded from: classes.dex */
public class MainActivity extends VuiMultiSceneActivity implements BaseFragment.FragmentController {
    private static final String DEFAULT_FRAGMENT = BluetoothFragment.class.getName();
    public static final String EXTRA_FRAGMENT_NAME = "fragment";
    private static final boolean HOLD_FRAGMENT = true;
    private static final String TAG = "xpsettings-main";
    private FrameLayout mRightFrameLayout;
    private TabFragment mTabFragment;
    private XTitleBar mXTitleBar;
    private Choreographer.FrameCallback callback = new Choreographer.FrameCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.MainActivity.1
        private boolean isFirstFrame = true;

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (this.isFirstFrame) {
                Choreographer.getInstance().postFrameCallback(MainActivity.this.callback);
                this.isFirstFrame = false;
            }
        }
    };
    private Runnable mRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$MainActivity$NjuiRFEF_ONQyMQU-w5ZARd2W6A
        @Override // java.lang.Runnable
        public final void run() {
            MainActivity.this.lambda$new$2$MainActivity();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(final Bundle bundle) {
        Choreographer.getInstance().postFrameCallback(this.callback);
        StartPerfUtils.onCreateBegin();
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        initView();
        changeAction(getIntent());
        if (bundle != null) {
            lambda$onCreate$0$MainActivity(bundle);
        } else {
            this.mRightFrameLayout.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$MainActivity$QX1nXblK_QxVd1h2540Gmbtb50I
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.lambda$onCreate$0$MainActivity(bundle);
                }
            });
        }
        StartPerfUtils.onCreateEnd();
        createVuiScene();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            changeAction(intent);
            setIntent(intent);
            String stringExtra = getIntent().getStringExtra("fragment");
            Logs.log(TAG, " onNewIntent " + stringExtra);
            TabFragment tabFragment = this.mTabFragment;
            if (tabFragment != null) {
                tabFragment.changePage(stringExtra);
            } else {
                Logs.log(TAG, " onNewIntent mTabFragment is null");
            }
        }
    }

    private void changeAction(Intent intent) {
        PageDirectItem page;
        if (!ElementDirectManager.get().isPageDirectFromIntent(intent) || (page = ElementDirectManager.get().getPage(intent.getData())) == null || page.getData() == null) {
            return;
        }
        intent.putExtra("fragment", page.getData().toString());
        Logs.log(TAG, " changeAction uri :" + intent.getData());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        destroyVuiScene();
        System.gc();
    }

    private void initView() {
        this.mRightFrameLayout = (FrameLayout) findViewById(R.id.fragment_container_right);
        this.mXTitleBar = (XTitleBar) findViewById(R.id.xTitleBar);
        this.mXTitleBar.setCloseVisibility(4);
        this.mXTitleBar.setOnTitleBarClickListener(new XTitleBar.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.MainActivity.2
            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarActionClick(View view, int i) {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarBackClick() {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                if (CarFunction.isSupportSharedDisplay()) {
                    ActivityUtils.finish(MainActivity.this);
                } else {
                    ActivityUtils.moveTaskToBack(MainActivity.this, true);
                }
            }
        });
        if (!Utils.isUserRelease()) {
            final DebugUtils debugUtils = new DebugUtils(3);
            findViewById(R.id.demoImage).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$MainActivity$wUtuDO8xMctTVKLpQJ9GNxEEt-g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.this.lambda$initView$1$MainActivity(debugUtils, view);
                }
            });
        }
        initVui();
    }

    public /* synthetic */ void lambda$initView$1$MainActivity(DebugUtils debugUtils, View view) {
        if (debugUtils.onClick()) {
            new AboutFragment().show(getSupportFragmentManager(), "aboutFragment");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: init */
    public void lambda$onCreate$0$MainActivity(Bundle bundle) {
        if (isStopped()) {
            Logs.d("xpsettings-main main init stopped return!!");
            if (isFinishing()) {
                return;
            }
            ActivityUtils.finish(this);
            return;
        }
        Logs.d("xpsettings-main main init ");
        if (CarFunction.isSupportXTitle()) {
            this.mXTitleBar.setCloseVisibility(0);
        }
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container_left);
        if (findFragmentById instanceof TabFragment) {
            this.mTabFragment = (TabFragment) findFragmentById;
        }
        if (this.mTabFragment == null) {
            this.mTabFragment = new TabFragment();
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            initVuiFragmentScene(this.mTabFragment, VuiManager.SCENE_TAB, false, null, null);
            beginTransaction.replace(R.id.fragment_container_left, this.mTabFragment).commit();
        }
        updateVuiFragmentInfo(getSupportFragmentManager().getFragments(), this.mTabFragment);
        if (bundle == null) {
            String stringExtra = getIntent().getStringExtra("fragment");
            Logs.d("xpsettings-main main init first :  " + stringExtra);
            TabFragment tabFragment = this.mTabFragment;
            if (stringExtra == null) {
                stringExtra = DEFAULT_FRAGMENT;
            }
            tabFragment.changePage(stringExtra);
            return;
        }
        String string = bundle.getString("fragment");
        Logs.d("xpsettings-main main init reset :  " + string + ", intent : " + getIntent().getStringExtra("fragment"));
        TabFragment tabFragment2 = this.mTabFragment;
        if (string == null) {
            string = DEFAULT_FRAGMENT;
        }
        tabFragment2.changePage(string);
    }

    public /* synthetic */ void lambda$new$2$MainActivity() {
        String localVersionName = Utils.getLocalVersionName(CarSettingsApp.getContext());
        Logs.log(TAG, "verName : " + localVersionName + " , fragment size is " + getSupportFragmentManager().getFragments().size());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mXTitleBar.postDelayed(this.mRunnable, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mXTitleBar.removeCallbacks(this.mRunnable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        Logs.d("xpsettings onSaveInstanceState mCurrentRightVisibleFragment:" + this.mCurrentRightVisibleFragment);
        bundle.putString("fragment", this.mCurrentRightVisibleFragment);
        getIntent().putExtra("fragment", this.mCurrentRightVisibleFragment);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public boolean launchFragment(Class cls, String str, String str2) {
        BaseFragment baseFragment;
        Logs.d("xpsettings-main launchFragment c " + cls);
        if (cls != null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            if (supportFragmentManager.isStateSaved()) {
                Logs.d("xpsettings-main main isStateSaved stopped return!!");
                if (!isFinishing()) {
                    ActivityUtils.finish(this);
                }
                return false;
            }
            FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
            BaseFragment baseFragment2 = (BaseFragment) supportFragmentManager.findFragmentByTag(cls.getName());
            Logs.log(TAG, "launchFragment " + cls.getSimpleName() + " cur : " + this.mCurrentRightVisibleFragment + " fragment:" + baseFragment2);
            if (this.mCurrentRightVisibleFragment != null && this.mCurrentRightVisibleFragment.equals(cls.getName())) {
                return true;
            }
            if (this.mCurrentRightVisibleFragment != null) {
                BaseFragment baseFragment3 = (BaseFragment) supportFragmentManager.findFragmentByTag(this.mCurrentRightVisibleFragment);
                if (baseFragment3 != null) {
                    beginTransaction.hide(baseFragment3);
                    beginTransaction.setMaxLifecycle(baseFragment3, Lifecycle.State.STARTED);
                } else {
                    Logs.log(TAG, "launchFragment  hide fragment is null");
                    return false;
                }
            }
            if (baseFragment2 == null) {
                baseFragment = (BaseFragment) getFragment(cls);
                initVuiFragmentScene(baseFragment, str, true, Arrays.asList(this.subScenes), str2);
                beginTransaction.add(R.id.fragment_container_right, baseFragment, cls.getName());
            } else {
                initVuiFragmentScene(baseFragment2, str, true, Arrays.asList(this.subScenes), str2);
                beginTransaction.show(baseFragment2);
                beginTransaction.setMaxLifecycle(baseFragment2, Lifecycle.State.RESUMED);
                baseFragment = baseFragment2;
            }
            beginTransaction.commit();
            this.mCurrentRightVisibleFragment = baseFragment.getClass().getName();
        }
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Logs.log(TAG, "uiMode:" + configuration.uiMode);
        XpThemeUtils.setWindowBackgroundResource(configuration, getWindow(), R.drawable.x_bg_app);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public void changeTitle(CharSequence charSequence) {
        this.mXTitleBar.setTitle(charSequence);
    }

    public Fragment getFragment(Class cls) {
        try {
            return (Fragment) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public void removeFragmentPadding(boolean z) {
        this.mRightFrameLayout.setPadding((int) getResources().getDimension(R.dimen.page_padding_left), (int) getResources().getDimension(R.dimen.page_padding_top), (int) getResources().getDimension(R.dimen.page_padding_right), z ? 0 : (int) getResources().getDimension(R.dimen.page_padding_bottom));
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiMultiSceneActivity
    public void initVui() {
        this.mRootView = this.mXTitleBar;
        this.subScenes = new String[]{VuiManager.SCENE_TITLE_BAR, VuiManager.SCENE_TAB};
        this.mSceneId = VuiManager.SCENE_TITLE_BAR;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (Config.IS_SDK_HIGHER_P && this.mCurrentRightVisibleFragment == FeedbackFragment.class.getName() && motionEvent.getAction() == 1) {
            Logs.d("xpsettings-main action up");
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.mRootView.getWindowToken(), 2);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
