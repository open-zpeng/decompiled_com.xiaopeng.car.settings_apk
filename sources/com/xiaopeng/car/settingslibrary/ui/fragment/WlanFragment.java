package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.view.WlanView;
/* loaded from: classes.dex */
public class WlanFragment extends BaseFragment {
    WlanView mWlanView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.popup_dropdown_dialog;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mWlanView = (WlanView) LayoutInflater.from(getContext()).inflate(R.layout.wlan_view, (ViewGroup) null).findViewById(R.id.wlanView);
        this.mWlanView.setSceneId(this.sceneId);
        this.mWlanView.setDialog(this.mIsDialog);
        this.mWlanView.createView();
        ((ViewGroup) view).addView(this.mWlanView);
        this.mRootView = this.mWlanView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment
    public void onVuiEnableChanged() {
        super.onVuiEnableChanged();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        this.mWlanView.onStart();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            this.mWlanView.onStart();
        } else {
            this.mWlanView.onStop();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logs.d("xpwifi fragment onStop " + isHidden());
        if (isHidden()) {
            return;
        }
        this.mWlanView.onStop();
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XpThemeUtils.isThemeChanged(configuration)) {
            this.mWlanView.onConfigurationChanged();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    public void setDialog(boolean z) {
        this.mIsDialog = z;
    }
}
