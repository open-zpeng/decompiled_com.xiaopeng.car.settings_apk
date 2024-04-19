package com.xiaopeng.car.settingslibrary.adapterDataObserver;

import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes.dex */
public class RecycleViewAdapterDataObserver extends RecyclerView.AdapterDataObserver {
    private boolean itemInserted = false;
    private boolean itemMoved = false;
    private boolean itemRemoved = false;
    private boolean changed = false;

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onChanged() {
        super.onChanged();
        this.changed = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeInserted(int i, int i2) {
        super.onItemRangeInserted(i, i2);
        this.itemInserted = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeMoved(int i, int i2, int i3) {
        super.onItemRangeMoved(i, i2, i3);
        this.itemMoved = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeRemoved(int i, int i2) {
        super.onItemRangeRemoved(i, i2);
        this.itemRemoved = true;
    }

    public boolean isUpdated() {
        return this.changed || this.itemInserted || this.itemMoved || this.itemRemoved;
    }

    public void reset() {
        this.itemInserted = false;
        this.itemMoved = false;
        this.itemRemoved = false;
        this.changed = false;
    }
}
