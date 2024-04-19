package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes.dex */
public class TabListRecyclerView extends RecyclerView {
    float startY;

    public TabListRecyclerView(Context context) {
        super(context);
    }

    public TabListRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TabListRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startY = motionEvent.getY();
        } else if (action == 2 && Math.abs(motionEvent.getY() - this.startY) < viewConfiguration.getScaledTouchSlop() * 2) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
