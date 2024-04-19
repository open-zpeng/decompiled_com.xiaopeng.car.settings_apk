package com.xiaopeng.car.settingslibrary.ui.base;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
/* loaded from: classes.dex */
public class ViewModelUtils {
    public static <T extends ViewModel> T getViewModel(Fragment fragment, Class<T> cls) {
        if (fragment.getActivity() != null) {
            return (T) ViewModelProviders.of(fragment.getActivity()).get(cls);
        }
        return (T) ViewModelProviders.of(fragment).get(cls);
    }
}
