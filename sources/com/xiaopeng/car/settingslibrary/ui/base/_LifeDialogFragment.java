package com.xiaopeng.car.settingslibrary.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public abstract class _LifeDialogFragment extends DialogFragment {
    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append("life-onCreate : ");
        sb.append(bundle == null ? "new" : "reset");
        log(sb.toString());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        log("life-onDestroy : ");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        log("life-onAttach : ");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        log("life-onDetach : ");
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        log("life-onViewCreated : ");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        log("life-onStart : " + isHidden());
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        log("life-onResume : " + isHidden());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        log("life-onPause : ");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        log("life-onStop : " + isHidden());
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        log("life-onDestroyView : ");
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        log("life-onHiddenChanged : " + z);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        log("life-finalize : ");
    }

    protected void log(String str) {
        Logs.log("Fragment-" + getClass().getSimpleName(), str + "   " + hashCode());
    }
}
