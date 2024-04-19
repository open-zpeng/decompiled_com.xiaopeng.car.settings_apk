package com.xiaopeng.car.settingslibrary.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.speech.VuiListFragment;
/* loaded from: classes.dex */
public abstract class BaseListFragment extends VuiListFragment {
    protected RecyclerView mRecyclerView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_list;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setItemViewCacheSize(0);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        initVuiView(this.mRecyclerView);
    }

    protected View getHeaderOrFooterView(int i) {
        if (getContext() != null) {
            return LayoutInflater.from(getContext()).inflate(i, (ViewGroup) this.mRecyclerView, false);
        }
        return null;
    }
}
