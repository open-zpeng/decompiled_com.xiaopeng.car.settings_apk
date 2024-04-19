package com.xiaopeng.car.settingslibrary.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.TimeConsumingLog;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener;
import com.xiaopeng.car.settingslibrary.speech.VuiFragment;
/* loaded from: classes.dex */
public abstract class BaseFragment extends VuiFragment implements OnPageDirectShowListener {
    protected Context mContext;
    protected FragmentController mFragmentController;

    /* loaded from: classes.dex */
    public interface FragmentController {
        void changeTitle(CharSequence charSequence);

        boolean launchFragment(Class cls, String str, String str2);

        void removeFragmentPadding(boolean z);
    }

    protected void dismissShowingDialog() {
    }

    protected abstract void init(Bundle bundle);

    protected abstract void initView(View view);

    protected abstract int layoutId();

    @Override // com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(String str) {
    }

    protected String[] supportSecondPageForElementDirect() {
        return null;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TimeConsumingLog timeConsumingLog = TimeConsumingLog.get();
        timeConsumingLog.start(getClass().getSimpleName() + hashCode() + "--create");
        View inflate = LayoutInflater.from(getContext()).inflate(layoutId(), viewGroup, false);
        TimeConsumingLog.get().add(" inflate ");
        return inflate;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        createVuiScene();
        TimeConsumingLog.get().add(" initView ");
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        init(bundle);
        TimeConsumingLog.get().add(" init ");
        TimeConsumingLog.get().end("--create");
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (getActivity() instanceof FragmentController) {
            this.mFragmentController = (FragmentController) getActivity();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mFragmentController = null;
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            if (ElementDirectManager.get().showElementItemView(getActivity().getIntent(), getClass().getName(), this.mRootView)) {
                dismissShowingDialog();
                Utils.dismissSoundEffectDialog(CarSettingsApp.getContext());
                getActivity().getIntent().setData(null);
                log("element direct isElement true");
            } else if (ElementDirectManager.get().showSecondPageDirect(getActivity().getIntent(), supportSecondPageForElementDirect(), this)) {
                getActivity().getIntent().setData(null);
                log("element direct isSecondPage true");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getStringById(int i) {
        Context context = this.mContext;
        if (context != null) {
            return context.getString(i);
        }
        return null;
    }
}
