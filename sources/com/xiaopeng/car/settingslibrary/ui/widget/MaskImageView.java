package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class MaskImageView extends AppCompatImageView {
    private static final int MASK_COLOR = Color.argb(179, 0, 0, 0);
    private static final String TAG = "MaskImageView";
    private boolean mShowMask;

    public MaskImageView(Context context) {
        super(context);
        this.mShowMask = false;
    }

    public MaskImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowMask = false;
    }

    public MaskImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowMask = false;
    }

    protected int getMaskColor() {
        return MASK_COLOR;
    }

    public void setShowMask(boolean z) {
        Logs.d("setShowMask " + hashCode() + " " + z);
        if (this.mShowMask == z) {
            return;
        }
        this.mShowMask = z;
        if (this.mShowMask) {
            setColorFilter(getMaskColor(), PorterDuff.Mode.SRC_ATOP);
        } else {
            clearColorFilter();
        }
    }
}
