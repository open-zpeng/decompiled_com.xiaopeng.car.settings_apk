package com.xiaopeng.car.settingslibrary.speech;

import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes.dex */
public class VuiRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private VuiListFragment mFragment;
    private Runnable mScrollUpdateRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.speech.-$$Lambda$VuiRecyclerViewScrollListener$QglU7-vGO3gw4pfi5FpsgSvWwk0
        @Override // java.lang.Runnable
        public final void run() {
            VuiRecyclerViewScrollListener.this.updateScrollVui();
        }
    };

    public VuiRecyclerViewScrollListener(VuiListFragment vuiListFragment) {
        this.mFragment = null;
        this.mFragment = vuiListFragment;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        VuiListFragment vuiListFragment;
        super.onScrollStateChanged(recyclerView, i);
        if (i != 0 || (vuiListFragment = this.mFragment) == null || vuiListFragment.getVuiHandler() == null) {
            return;
        }
        this.mFragment.getVuiHandler().removeCallbacks(this.mScrollUpdateRunnable);
        this.mFragment.getVuiHandler().postDelayed(this.mScrollUpdateRunnable, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScrollVui() {
        VuiListFragment vuiListFragment = this.mFragment;
        if (vuiListFragment != null) {
            vuiListFragment.updateVuiHeaderFooterAndRecyclerView();
        }
    }

    public void onDestroy() {
        this.mFragment = null;
    }
}
