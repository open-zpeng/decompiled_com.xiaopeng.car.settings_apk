package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.vm.system.SystemInfo;
import com.xiaopeng.car.settingslibrary.vm.system.SystemSettingsViewModel;
import java.util.ArrayList;
@Deprecated
/* loaded from: classes.dex */
public class AboutFragment extends BaseDialogFragment {
    private ViewGroup mSystemInfoContainer;
    private SystemSettingsViewModel mSystemSettingsViewModel;

    @Override // androidx.fragment.app.DialogFragment
    public int getTheme() {
        return R.style.SoundDialog;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mSystemSettingsViewModel.refreshDeviceInfo();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment
    protected int layoutId() {
        return R.layout.about_recycler_footer;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment
    protected void initView(View view) {
        this.mSystemInfoContainer = (ViewGroup) view.findViewById(R.id.about_system_info_container);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment
    protected void init(Bundle bundle) {
        initVm();
    }

    private void initVm() {
        this.mSystemSettingsViewModel = (SystemSettingsViewModel) ViewModelUtils.getViewModel(this, SystemSettingsViewModel.class);
        this.mSystemSettingsViewModel.getInfoLivedata().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutFragment$ILHk59_dcOa1qsnCehRf3mgQTsI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AboutFragment.this.lambda$initVm$0$AboutFragment((SystemInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: fillData */
    public void lambda$initVm$0$AboutFragment(SystemInfo systemInfo) {
        ArrayList<String> arrayList = new ArrayList<>(8);
        arrayList.add(systemInfo.getModel());
        arrayList.add(systemInfo.getIP());
        arrayList.add(systemInfo.getWifiMac());
        arrayList.add(systemInfo.getBluetoothMac());
        arrayList.add(systemInfo.getSoftwareVersion());
        arrayList.add(systemInfo.getMcuVersion());
        arrayList.add(systemInfo.getBaseBandVersion());
        arrayList.add(systemInfo.getFirmwareVersion());
        arrayList.add(systemInfo.getTboxVersion());
        arrayList.add(systemInfo.getTboxMcu());
        refreshInfo(arrayList);
    }

    private void refreshInfo(ArrayList<String> arrayList) {
        if (getContext() == null) {
            return;
        }
        this.mSystemInfoContainer.removeAllViews();
        String[] stringArray = getResources().getStringArray(R.array.about_system_info_labels);
        LayoutInflater from = LayoutInflater.from(getContext());
        for (int i = 0; i < stringArray.length; i++) {
            if (i < arrayList.size()) {
                addView(from, String.format("%s: %s", stringArray[i], arrayList.get(i)));
            } else {
                addView(from, String.format("%s: ", stringArray[i]));
            }
        }
    }

    private void addView(LayoutInflater layoutInflater, String str) {
        View inflate = layoutInflater.inflate(R.layout.about_recycler_item_system_info, this.mSystemInfoContainer, false);
        this.mSystemInfoContainer.addView(inflate);
        ((TextView) inflate.findViewById(R.id.about_system_info_name)).setText(str);
    }
}
