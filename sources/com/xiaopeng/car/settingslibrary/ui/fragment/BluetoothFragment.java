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
import com.xiaopeng.car.settingslibrary.ui.view.BluetoothView;
/* loaded from: classes.dex */
public class BluetoothFragment extends BaseFragment {
    private static final String TAG = "BluetoothFragment";
    private BluetoothView mBluetoothView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.popup_dropdown_dialog;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mBluetoothView = (BluetoothView) LayoutInflater.from(getContext()).inflate(R.layout.bluetooth_view, (ViewGroup) null).findViewById(R.id.bluetoothView);
        this.mBluetoothView.setSceneId(this.sceneId);
        this.mBluetoothView.setDialog(this.mIsDialog);
        this.mBluetoothView.createView();
        ((ViewGroup) view).addView(this.mBluetoothView);
        this.mRootView = this.mBluetoothView;
    }

    public void setDialog(boolean z) {
        this.mIsDialog = z;
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
        this.mBluetoothView.onStart();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("xpbluetooth fragment onHiddenChanged " + z);
        if (!z) {
            this.mBluetoothView.onStart();
        } else {
            this.mBluetoothView.onStop();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (isHidden()) {
            return;
        }
        this.mBluetoothView.onStop();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XpThemeUtils.isThemeChanged(configuration)) {
            this.mBluetoothView.onConfigurationChanged();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        BluetoothView bluetoothView = this.mBluetoothView;
        if (bluetoothView != null) {
            bluetoothView.dismissShowingDialog();
        }
    }
}
