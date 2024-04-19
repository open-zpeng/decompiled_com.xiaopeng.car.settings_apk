package com.xiaopeng.car.settingslibrary.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.speech.VuiDialogFragment;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
/* loaded from: classes.dex */
public abstract class BaseXDialogFragment extends VuiDialogFragment {
    protected XDialogView mXDialog;

    protected boolean useXDialogScroll() {
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mXDialog = new XDialogView(getContext(), getXDialogViewStyle());
        this.mXDialog.setCustomView(LayoutInflater.from(getContext()).inflate(layoutId(), this.mXDialog.getContentView(), false), useXDialogScroll());
        return this.mXDialog.getContentView();
    }

    protected int getXDialogViewStyle() {
        return R.style.XDialogView;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (getDialog() != null) {
            getDialog().setOnCancelListener(null);
        }
        XDialogView xDialogView = this.mXDialog;
        if (xDialogView != null) {
            xDialogView.getContentView().removeAllViews();
            this.mXDialog = null;
        }
        destroyVuiDialogFragment();
    }

    @Override // androidx.fragment.app.DialogFragment
    public int getTheme() {
        return R.style.XAppTheme_XDialog;
    }
}
