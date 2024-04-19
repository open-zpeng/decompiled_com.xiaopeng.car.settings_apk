package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.view.PsnBluetoothView;
/* loaded from: classes.dex */
public class PsnBluetoothFragment extends BaseFragment {
    private static final String TAG = "PsnBluetoothFragment";
    private PsnBluetoothView mPsnBluetoothView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.popup_dropdown_dialog;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mPsnBluetoothView = new PsnBluetoothView(getContext());
        ((ViewGroup) view).addView(this.mPsnBluetoothView);
    }

    public void setDialog(boolean z) {
        PsnBluetoothView psnBluetoothView = this.mPsnBluetoothView;
        if (psnBluetoothView != null) {
            psnBluetoothView.setDialog(z);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        this.mPsnBluetoothView.onStart();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("PsnBluetoothFragment xpbluetooth fragment onHiddenChanged " + z);
        if (!z) {
            this.mPsnBluetoothView.onStart();
        } else {
            this.mPsnBluetoothView.onStop();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (isHidden()) {
            return;
        }
        this.mPsnBluetoothView.onStop();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XpThemeUtils.isThemeChanged(configuration)) {
            this.mPsnBluetoothView.onConfigurationChanged();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        this.mPsnBluetoothView.dismissDialog();
    }
}
