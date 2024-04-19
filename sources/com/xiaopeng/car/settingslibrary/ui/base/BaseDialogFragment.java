package com.xiaopeng.car.settingslibrary.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.common.utils.TimeConsumingLog;
/* loaded from: classes.dex */
public abstract class BaseDialogFragment extends _LifeDialogFragment {
    protected abstract void init(Bundle bundle);

    protected abstract void initView(View view);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int layoutId();

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            getDialog().getWindow().setElevation(0.0f);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getDialog() != null) {
            getDialog().requestWindowFeature(1);
        }
        TimeConsumingLog timeConsumingLog = TimeConsumingLog.get();
        timeConsumingLog.start("BaseDialogFragment onCreateView " + this);
        View inflate = LayoutInflater.from(getContext()).inflate(layoutId(), viewGroup, false);
        TimeConsumingLog timeConsumingLog2 = TimeConsumingLog.get();
        timeConsumingLog2.end("BaseDialogFragment onCreateView end " + this);
        return inflate;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        TimeConsumingLog timeConsumingLog = TimeConsumingLog.get();
        timeConsumingLog.start("BaseDialogFragment onViewCreated " + this);
        initView(view);
        TimeConsumingLog timeConsumingLog2 = TimeConsumingLog.get();
        timeConsumingLog2.end("BaseDialogFragment onViewCreated " + this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        TimeConsumingLog timeConsumingLog = TimeConsumingLog.get();
        timeConsumingLog.start("BaseDialogFragment onActivityCreated " + this);
        init(bundle);
        TimeConsumingLog timeConsumingLog2 = TimeConsumingLog.get();
        timeConsumingLog2.end("BaseDialogFragment onActivityCreated end" + this);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }
}
