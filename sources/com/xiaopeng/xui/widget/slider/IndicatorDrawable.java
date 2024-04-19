package com.xiaopeng.xui.widget.slider;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;
/* loaded from: classes2.dex */
class IndicatorDrawable extends Drawable implements Drawable.Callback {
    private static final float INDICATOR_TEXT_SIZE = 24.0f;
    private static final int INDICATOR_TEXT_VERTICAL = 10;
    private static final int MIN_INDICATOR_SIZE = 56;
    private static final String TAG = "IndicatorDrawable";
    private static final int TEXT_PADDING = 50;
    private float indicatorCenter;
    private final Rect mBounds;
    private Drawable mTagBg;
    private int mTagBgResId;
    private int slideWidth;
    private int textWidth;
    private final Paint textPaint = new Paint(1);
    private float mTextBaselineY = INDICATOR_TEXT_SIZE;
    private int mTextPadding = 50;
    private String indicatorText = "";
    private boolean isEnabled = true;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public IndicatorDrawable() {
        float f = this.indicatorCenter;
        this.mBounds = new Rect((int) (f - 28.0f), 10, (int) (f + 28.0f), 60);
        this.textPaint.setTextSize(INDICATOR_TEXT_SIZE);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setColor(this.isEnabled ? -1 : 1560281087);
    }

    public void inflateAttr(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes;
        if (attributeSet != null) {
            obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XSlider, 0, i);
        } else {
            obtainStyledAttributes = theme.obtainStyledAttributes(i, R.styleable.XSlider);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(R.styleable.XSlider_slider_text_tag_bg)) {
            this.mTagBgResId = obtainStyledAttributes.getResourceId(R.styleable.XSlider_slider_text_tag_bg, 0);
        }
        this.mBounds.top = obtainStyledAttributes.hasValueOrEmpty(R.styleable.XSlider_slider_text_tag_padding_top) ? obtainStyledAttributes.getDimensionPixelSize(R.styleable.XSlider_slider_text_tag_padding_top, 10) : 10;
        if (obtainStyledAttributes.hasValueOrEmpty(R.styleable.XSlider_slider_text_tag_height)) {
            Rect rect = this.mBounds;
            rect.bottom = (int) (rect.top + obtainStyledAttributes.getDimensionPixelSize(R.styleable.XSlider_slider_text_tag_height, 40));
        }
        float height = this.mBounds.height() / 4.0f;
        if (obtainStyledAttributes.hasValueOrEmpty(R.styleable.XSlider_slider_text_tag_text_baseline_offset)) {
            height = obtainStyledAttributes.getDimension(R.styleable.XSlider_slider_text_tag_text_baseline_offset, height);
        }
        this.mTextBaselineY = this.mBounds.top + (this.mBounds.height() / 2.0f) + height;
        if (obtainStyledAttributes.hasValueOrEmpty(R.styleable.XSlider_slider_text_tag_text_padding)) {
            this.mTextPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XSlider_slider_text_tag_text_padding, 50);
        }
        refreshUI(resources, theme);
        setBounds(this.mBounds);
        obtainStyledAttributes.recycle();
    }

    public void refreshUI(Resources resources, Resources.Theme theme) {
        Drawable drawable = ResourcesCompat.getDrawable(resources, this.mTagBgResId, theme);
        XLogUtils.d(TAG, "refreshUI, newBg:" + drawable + ", oldBg:" + this.mTagBg);
        setTagBg(drawable);
    }

    public void setTagBg(Drawable drawable) {
        Drawable drawable2 = this.mTagBg;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
        }
        this.mTagBg = drawable;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.mTagBg;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.drawText(this.indicatorText, (this.mBounds.left + this.mBounds.right) / 2.0f, this.mTextBaselineY, this.textPaint);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Drawable drawable = this.mTagBg;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        Drawable drawable = this.mTagBg;
        return drawable != null && drawable.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        boolean z2 = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (iArr[i] == 16842910) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (this.isEnabled != z) {
            this.isEnabled = z;
            this.textPaint.setColor(z ? -1 : 1560281087);
            XLogUtils.d(TAG, "onStateChange, isEnabled:" + z);
            z2 = true;
        }
        Drawable drawable = this.mTagBg;
        return (drawable == null || !drawable.isStateful()) ? z2 : z2 | this.mTagBg.setState(iArr);
    }

    public void updateCenter(float f, String str, int i) {
        this.indicatorText = str;
        this.indicatorCenter = f;
        this.textWidth = (int) this.textPaint.measureText(str);
        this.slideWidth = i;
        resetBounds();
        invalidateSelf();
    }

    private void resetBounds() {
        int max = Math.max(this.textWidth + this.mTextPadding, 56);
        float f = this.indicatorCenter;
        float f2 = max / 2.0f;
        int i = this.slideWidth;
        float f3 = (i - f) - f2;
        if (f - f2 < 0.0f) {
            Rect rect = this.mBounds;
            rect.left = 0;
            rect.right = max;
        } else if (f3 < 0.0f) {
            Rect rect2 = this.mBounds;
            rect2.left = i - max;
            rect2.right = i;
        } else {
            Rect rect3 = this.mBounds;
            float f4 = max / 2;
            rect3.left = (int) (f - f4);
            rect3.right = (int) (f + f4);
        }
        setBounds(this.mBounds);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
