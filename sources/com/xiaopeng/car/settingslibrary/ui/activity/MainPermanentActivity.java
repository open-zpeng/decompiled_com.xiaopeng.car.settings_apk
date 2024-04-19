package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.xui.widget.XTitleBar;
@Deprecated
/* loaded from: classes.dex */
public class MainPermanentActivity extends BaseActivity implements BaseFragment.FragmentController {
    private static final String DEFAULT_FRAGMENT = BluetoothFragment.class.getName();
    public static final String EXTRA_FRAGMENT_NAME = "fragment";
    private String mCurrentRightVisibleFragment;
    private FrameLayout mRightFrameLayout;
    private TabFragment mTabFragment;
    private XTitleBar mXTitleBar;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        initView();
        init(bundle);
    }

    private void initView() {
        this.mRightFrameLayout = (FrameLayout) findViewById(R.id.fragment_container_right);
        this.mXTitleBar = (XTitleBar) findViewById(R.id.xTitleBar);
        this.mXTitleBar.setOnTitleBarClickListener(new XTitleBar.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.MainPermanentActivity.1
            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarActionClick(View view, int i) {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarBackClick() {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                MainPermanentActivity.this.finish();
            }
        });
    }

    private void init(Bundle bundle) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container_left);
        if (findFragmentById instanceof TabFragment) {
            this.mTabFragment = (TabFragment) findFragmentById;
        }
        if (this.mTabFragment == null) {
            this.mTabFragment = new TabFragment();
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.fragment_container_left, this.mTabFragment);
            beginTransaction.commit();
        }
        if (bundle == null) {
            String stringExtra = getIntent().getStringExtra("fragment");
            TabFragment tabFragment = this.mTabFragment;
            if (stringExtra == null) {
                stringExtra = DEFAULT_FRAGMENT;
            }
            tabFragment.changePage(stringExtra);
            return;
        }
        String string = bundle.getString("fragment");
        TabFragment tabFragment2 = this.mTabFragment;
        if (string == null) {
            string = DEFAULT_FRAGMENT;
        }
        tabFragment2.changePage(string);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
        }
        this.mTabFragment.changePage(getIntent().getStringExtra("fragment"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("fragment", this.mCurrentRightVisibleFragment);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public boolean launchFragment(Class cls, String str, String str2) {
        String str3;
        Fragment findFragmentByTag;
        if (cls == null || ((str3 = this.mCurrentRightVisibleFragment) != null && str3.equals(cls.getName()))) {
            return true;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment findFragmentByTag2 = supportFragmentManager.findFragmentByTag(cls.getName());
        if (findFragmentByTag2 == null) {
            findFragmentByTag2 = getFragment(cls);
            beginTransaction.add(R.id.fragment_container_right, findFragmentByTag2, cls.getName());
        } else {
            beginTransaction.show(findFragmentByTag2);
        }
        String str4 = this.mCurrentRightVisibleFragment;
        if (str4 != null && (findFragmentByTag = supportFragmentManager.findFragmentByTag(str4)) != null) {
            beginTransaction.hide(findFragmentByTag);
        }
        beginTransaction.commit();
        this.mCurrentRightVisibleFragment = findFragmentByTag2.getClass().getName();
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public void changeTitle(CharSequence charSequence) {
        this.mXTitleBar.setTitle(charSequence);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment.FragmentController
    public void removeFragmentPadding(boolean z) {
        this.mRightFrameLayout.setPadding((int) getResources().getDimension(R.dimen.page_padding_left), (int) getResources().getDimension(R.dimen.page_padding_top), (int) getResources().getDimension(R.dimen.page_padding_right), z ? 0 : (int) getResources().getDimension(R.dimen.page_padding_bottom));
    }

    public Fragment getFragment(Class cls) {
        try {
            return (Fragment) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
