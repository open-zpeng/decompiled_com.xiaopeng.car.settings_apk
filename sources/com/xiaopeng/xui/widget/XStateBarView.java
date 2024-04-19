package com.xiaopeng.xui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.xiaopeng.xui.view.XView;
/* loaded from: classes2.dex */
public class XStateBarView extends XView {
    View scrollableView;

    private void setViewStyle() {
    }

    public XStateBarView(Context context) {
        this(context, null);
    }

    public XStateBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XStateBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XStateBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void bindScrollableView(View view) {
        this.scrollableView = view;
        if (this.scrollableView == null) {
            return;
        }
        view.getScrollY();
        View view2 = this.scrollableView;
        if (view2 instanceof ScrollView) {
            view2.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.xiaopeng.xui.widget.XStateBarView.1
                @Override // android.view.View.OnScrollChangeListener
                public void onScrollChange(View view3, int i, int i2, int i3, int i4) {
                    float f = i2 / 100.0f;
                    if (f < 0.0f) {
                        f = 0.0f;
                    }
                    if (f > 255.0f) {
                        f = 255.0f;
                    }
                    view3.setAlpha(f);
                }
            });
        }
    }
}
