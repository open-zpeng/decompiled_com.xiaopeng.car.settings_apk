package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.math.MathUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public class QcSliderLayout extends ConstraintLayout {
    private static final int DEFAULT_BG_COLOR = 1555808977;
    private static final int DEFAULT_BG_CORNER_RADIUS = 32;
    private static final int DEFAULT_SLIDER_COLOR = 16777215;
    private static final int DEFAULT_SLIDER_INNER_CORNER_RADIUS = 27;
    private static final int DEFAULT_SLIDER_OUTER_CORNER_RADIUS = 5;
    private static final int MAX_LEVEL = 10000;
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;
    private static final int NO_ALPHA = 255;
    public static final int SLIDE_FROM_BOTTOM = 3;
    public static final int SLIDE_FROM_LEFT = 0;
    public static final int SLIDE_FROM_RIGHT = 2;
    public static final int SLIDE_FROM_TOP = 1;
    private static final String TAG = "QcSliderLayout";
    private int mBgColor;
    private int mBgColorId;
    private RectF mBounds;
    private Rect mContentBounds;
    private int mCornerRadius;
    private float mDisabledAlpha;
    private boolean mDispatchTouchDirectly;
    private boolean mDragging;
    private boolean mEnabled;
    private int mInset;
    private float mLastMoveX;
    private float mLastMoveY;
    private boolean mLimitTouchOnly;
    private boolean mMaxInitialized;
    private int mMaxLevel;
    private int mMaxLimit;
    private boolean mMaxLimitInit;
    private float mMaxPercentLimit;
    private int mMaxProgress;
    private boolean mMinInitialized;
    private int mMinLimit;
    private boolean mMinLimitInit;
    private float mMinPercentLimit;
    private int mMinProgress;
    private OnSlideChangeListener mOnSlideChangeListener;
    private int mProgress;
    private QcSliderDrawable mQcSliderDrawable;
    private float mSlideScale;
    private int mSliderColor;
    private int mSliderColorId;
    private int mSliderFrom;
    private int mSliderInnerCornerRadius;
    private int mSliderInset;
    private int mSliderOuterCornerRadius;
    private ThemeViewModel mThemeViewModel;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;

    /* loaded from: classes.dex */
    public interface OnSlideChangeListener {
        void onProgressChanged(QcSliderLayout qcSliderLayout, int i);

        void onStartTrackingTouch(QcSliderLayout qcSliderLayout);

        void onStopTrackingTouch(QcSliderLayout qcSliderLayout);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface SliderFrom {
    }

    private boolean shouldActivate(View view) {
        return true;
    }

    public QcSliderLayout(Context context) {
        this(context, null);
    }

    public QcSliderLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.QcSliderLayout);
    }

    public QcSliderLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.QcSliderLayout);
    }

    public QcSliderLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxLevel = 10000;
        this.mProgress = 0;
        this.mMinProgress = 0;
        this.mMaxProgress = 100;
        this.mMaxInitialized = false;
        this.mMinInitialized = false;
        this.mMinLimit = -1;
        this.mMaxLimit = -1;
        this.mMinLimitInit = false;
        this.mMaxLimitInit = false;
        this.mMinPercentLimit = 0.0f;
        this.mMaxPercentLimit = 1.0f;
        this.mLimitTouchOnly = false;
        this.mSlideScale = 1.0f;
        this.mDispatchTouchDirectly = false;
        this.mDragging = false;
        this.mSliderFrom = 0;
        this.mInset = 0;
        this.mSliderInset = 0;
        init(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setEnabled(this.mEnabled);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.QcSliderLayout, i, i2);
        this.mSliderFrom = obtainStyledAttributes.getInt(R.styleable.QcSliderLayout_sliderLayout_slide_from, 0);
        this.mBgColor = obtainStyledAttributes.getColor(R.styleable.QcSliderLayout_sliderLayout_bg_color, DEFAULT_BG_COLOR);
        this.mSliderColor = obtainStyledAttributes.getColor(R.styleable.QcSliderLayout_sliderLayout_slider_color, 16777215);
        this.mBgColorId = obtainStyledAttributes.getResourceId(R.styleable.QcSliderLayout_sliderLayout_bg_color, 0);
        this.mSliderColorId = obtainStyledAttributes.getResourceId(R.styleable.QcSliderLayout_sliderLayout_slider_color, 0);
        this.mCornerRadius = obtainStyledAttributes.getDimensionPixelSize(R.styleable.QcSliderLayout_sliderLayout_corner_radius, 32);
        this.mSliderOuterCornerRadius = obtainStyledAttributes.getDimensionPixelSize(R.styleable.QcSliderLayout_sliderLayout_slider_outer_corner_radius, 5);
        this.mSliderInnerCornerRadius = obtainStyledAttributes.getDimensionPixelSize(R.styleable.QcSliderLayout_sliderLayout_slider_inner_corner_radius, 27);
        this.mInset = obtainStyledAttributes.getDimensionPixelSize(R.styleable.QcSliderLayout_sliderLayout_inset, 0);
        this.mSliderInset = obtainStyledAttributes.getDimensionPixelSize(R.styleable.QcSliderLayout_sliderLayout_slider_inset, 0);
        this.mSlideScale = obtainStyledAttributes.getFloat(R.styleable.QcSliderLayout_sliderLayout_slide_scale, 1.0f);
        this.mEnabled = obtainStyledAttributes.getBoolean(R.styleable.QcSliderLayout_android_enabled, true);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(R.styleable.QcSliderLayout_android_disabledAlpha, 0.36f);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        this.mContentBounds = new Rect();
        this.mBounds = new RectF();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        QcSliderDrawableBuilder sliderFrom = new QcSliderDrawableBuilder().setColor(this.mBgColor, this.mSliderColor).setCornerRadius(this.mCornerRadius).setSliderOuterCornerRadius(this.mSliderOuterCornerRadius).setSliderInnerCornerRadius(this.mSliderInnerCornerRadius).setSliderFrom(this.mSliderFrom);
        int i3 = this.mSliderInset;
        QcSliderDrawableBuilder sliderInset = sliderFrom.setSliderInset(i3, i3);
        int i4 = this.mInset;
        this.mQcSliderDrawable = sliderInset.setInset(i4, i4).build();
        this.mQcSliderDrawable.setCallback(this);
    }

    public int getMin() {
        return this.mMinProgress;
    }

    public void setMin(int i) {
        int i2;
        if (this.mMaxInitialized && i > (i2 = this.mMaxProgress)) {
            i = i2;
        }
        this.mMinInitialized = true;
        if (this.mMaxInitialized && i != this.mMinProgress) {
            this.mMinProgress = i;
            postInvalidate();
            if (this.mProgress < i) {
                this.mProgress = i;
            }
            setProgress(this.mProgress);
            return;
        }
        this.mMinProgress = i;
    }

    public int getMax() {
        return this.mMaxProgress;
    }

    public void setMax(int i) {
        int i2;
        if (this.mMinInitialized && i < (i2 = this.mMinProgress)) {
            i = i2;
        }
        this.mMaxInitialized = true;
        if (this.mMinInitialized && i != this.mMaxProgress) {
            this.mMaxProgress = i;
            postInvalidate();
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            setProgress(this.mProgress);
            return;
        }
        this.mMaxProgress = i;
    }

    public void setMinLimit(int i) {
        assetLimit(i);
        if (this.mMinLimitInit && i == this.mMinLimit) {
            return;
        }
        this.mMinLimit = i;
        this.mMinPercentLimit = (float) ((i - getMin()) / getRange());
        if (this.mLimitTouchOnly || this.mProgress >= i) {
            return;
        }
        setProgress(i);
    }

    public void setMaxLimit(int i) {
        assetLimit(i);
        if (this.mMaxLimitInit && i == this.mMaxLimit) {
            return;
        }
        this.mMaxLimit = i;
        this.mMaxPercentLimit = (float) ((i - getMin()) / getRange());
        if (this.mLimitTouchOnly || this.mProgress <= i) {
            return;
        }
        setProgress(i);
    }

    private void assetLimit(int i) {
        if (i > getMax()) {
            throw new IllegalArgumentException(String.format("limit(%s) is larger than max(%s), Please set max first", Integer.valueOf(i), Integer.valueOf(getMax())));
        }
        if (i < getMin()) {
            throw new IllegalArgumentException(String.format("limit(%s) is less than min(%s), Please set min first", Integer.valueOf(i), Integer.valueOf(getMin())));
        }
    }

    public void setLimitTouchOnly(boolean z) {
        this.mLimitTouchOnly = z;
    }

    public int getRange() {
        return this.mMaxProgress - this.mMinProgress;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setSliderFrom(int i) {
        this.mSliderFrom = i;
    }

    public void setOnSlideChangeListener(OnSlideChangeListener onSlideChangeListener) {
        this.mOnSlideChangeListener = onSlideChangeListener;
    }

    public void setProgress(int i) {
        int limitProgress = limitProgress(i, !this.mLimitTouchOnly);
        Log.d(TAG, "setProgress=" + limitProgress + ", max=" + this.mMaxProgress + ", min=" + this.mMinProgress);
        setDrawablePercent(limitDrawablePercent(convertProgress(limitProgress), this.mLimitTouchOnly ^ true));
        this.mProgress = limitProgress;
    }

    public void setColor(int i, int i2) {
        this.mBgColor = i;
        this.mSliderColor = i2;
        QcSliderDrawable qcSliderDrawable = this.mQcSliderDrawable;
        if (qcSliderDrawable != null) {
            qcSliderDrawable.setColor(i, i2);
            invalidateDrawable(this.mQcSliderDrawable);
        }
    }

    public void setColor(int i) {
        this.mSliderColor = i;
        QcSliderDrawable qcSliderDrawable = this.mQcSliderDrawable;
        if (qcSliderDrawable != null) {
            qcSliderDrawable.setColor(this.mBgColor, i);
            invalidateDrawable(this.mQcSliderDrawable);
        }
    }

    public float getSlideScale() {
        return this.mSlideScale;
    }

    public void setSlideScale(float f) {
        this.mSlideScale = f;
    }

    public void setDispatchTouchDirectly(boolean z) {
        this.mDispatchTouchDirectly = z;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).setEnabled(z);
            }
        }
        super.setEnabled(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        QcSliderDrawable qcSliderDrawable = this.mQcSliderDrawable;
        if (qcSliderDrawable != null) {
            qcSliderDrawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0d));
        }
        super.drawableStateChanged();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        Context context;
        super.onConfigurationChanged(configuration);
        this.mThemeViewModel.onConfigurationChanged(this, configuration);
        if (!ThemeManager.isThemeChanged(configuration) || (context = getContext()) == null) {
            return;
        }
        setColor(context.getColor(this.mBgColorId), context.getColor(this.mSliderColorId));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mThemeViewModel.onAttachedToWindow(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mThemeViewModel.onDetachedFromWindow(this);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mQcSliderDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                setPressed(true);
            } else if (actionMasked == 1 || actionMasked == 3) {
                setPressed(false);
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
        if (r0 != 3) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            r10 = this;
            boolean r0 = r10.isEnabled()
            r1 = 1
            if (r0 != 0) goto L8
            return r1
        L8:
            int r0 = r11.getActionMasked()
            if (r0 == 0) goto L76
            if (r0 == r1) goto L6b
            r2 = 2
            if (r0 == r2) goto L17
            r11 = 3
            if (r0 == r11) goto L6b
            goto L8a
        L17:
            float r0 = r11.getX()
            float r11 = r11.getY()
            double r2 = (double) r0
            float r4 = r10.mTouchX
            double r4 = (double) r4
            double r4 = r2 - r4
            double r4 = java.lang.Math.abs(r4)
            int r6 = r10.mTouchSlop
            double r6 = (double) r6
            double r4 = r4 - r6
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 > 0) goto L44
            double r4 = (double) r11
            float r8 = r10.mTouchY
            double r8 = (double) r8
            double r4 = r4 - r8
            double r4 = java.lang.Math.abs(r4)
            int r8 = r10.mTouchSlop
            double r8 = (double) r8
            double r4 = r4 - r8
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L8a
        L44:
            android.view.ViewParent r4 = r10.getParent()
            r4.requestDisallowInterceptTouchEvent(r1)
            float r4 = r10.mLastMoveX
            double r4 = (double) r4
            double r2 = r2 - r4
            float r2 = (float) r2
            double r3 = (double) r11
            float r5 = r10.mLastMoveY
            double r5 = (double) r5
            double r3 = r3 - r5
            float r3 = (float) r3
            boolean r4 = r10.mDragging
            if (r4 != 0) goto L5f
            r10.mDragging = r1
            r10.onStartTrackingTouch()
        L5f:
            float r4 = r10.mSlideScale
            float r2 = r2 * r4
            float r3 = r3 * r4
            r10.trackMove(r2, r3)
            r10.mLastMoveX = r0
            r10.mLastMoveY = r11
            goto L8a
        L6b:
            boolean r11 = r10.mDragging
            if (r11 == 0) goto L8a
            r11 = 0
            r10.mDragging = r11
            r10.onStopTrackingTouch()
            goto L8a
        L76:
            float r0 = r11.getX()
            r10.mTouchX = r0
            float r11 = r11.getY()
            r10.mTouchY = r11
            float r11 = r10.mTouchX
            r10.mLastMoveX = r11
            float r11 = r10.mTouchY
            r10.mLastMoveY = r11
        L8a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mBounds.set(0.0f, 0.0f, i, i2);
        this.mContentBounds.set(0, 0, i, i2);
        this.mQcSliderDrawable.setBounds(this.mContentBounds);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mQcSliderDrawable.draw(canvas);
        activateChildren();
    }

    private void activateChildren() {
        Rect sliderBounds = this.mQcSliderDrawable.getSliderBounds();
        int i = this.mSliderFrom;
        if (i == 0) {
            activateChildFromLeft(this, sliderBounds.right, 0);
        } else if (i == 1) {
            activateChildFromTop(this, sliderBounds.bottom, 0);
        } else if (i == 2) {
            activateChildFromRight(this, sliderBounds.left, 0);
        } else if (i != 3) {
        } else {
            activateChildFromBottom(this, sliderBounds.top, 0);
        }
    }

    private void activateChildFromLeft(ViewGroup viewGroup, float f, int i) {
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    activateChildFromLeft((ViewGroup) childAt, f, childAt.getLeft());
                } else if (childAt.getLeft() + i < f) {
                    childAt.setActivated(true);
                } else {
                    childAt.setActivated(false);
                }
            }
        }
    }

    private void activateChildFromRight(ViewGroup viewGroup, float f, int i) {
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    activateChildFromRight((ViewGroup) childAt, f, childAt.getLeft());
                } else if (shouldActivate(childAt) && childAt.getRight() + i > f) {
                    childAt.setActivated(true);
                } else {
                    childAt.setActivated(false);
                }
            }
        }
    }

    private void activateChildFromTop(ViewGroup viewGroup, float f, int i) {
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    activateChildFromTop((ViewGroup) childAt, f, childAt.getTop());
                } else if (childAt.getBottom() + i < f) {
                    childAt.setActivated(true);
                } else {
                    childAt.setActivated(false);
                }
            }
        }
    }

    private void activateChildFromBottom(ViewGroup viewGroup, float f, int i) {
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    activateChildFromBottom((ViewGroup) childAt, f, childAt.getTop());
                } else if (childAt.getBottom() + i > f) {
                    childAt.setActivated(true);
                } else {
                    childAt.setActivated(false);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void trackMove(float r7, float r8) {
        /*
            r6 = this;
            com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout$QcSliderDrawable r0 = r6.mQcSliderDrawable
            android.graphics.Rect r0 = r0.getContainerBounds()
            com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout$QcSliderDrawable r1 = r6.mQcSliderDrawable
            int r1 = r1.getLevel()
            float r1 = (float) r1
            r2 = 1176256512(0x461c4000, float:10000.0)
            float r1 = r1 / r2
            int r2 = r6.mSliderFrom
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 == r3) goto L30
            r4 = 2
            if (r2 == r4) goto L26
            r7 = 3
            if (r2 == r7) goto L1f
            goto L41
        L1f:
            double r1 = (double) r1
            double r7 = (double) r8
            int r0 = r0.height()
            goto L2c
        L26:
            double r1 = (double) r1
            double r7 = (double) r7
            int r0 = r0.width()
        L2c:
            double r4 = (double) r0
            double r7 = r7 / r4
            double r1 = r1 - r7
            goto L40
        L30:
            double r1 = (double) r1
            double r7 = (double) r8
            int r0 = r0.height()
            goto L3d
        L37:
            double r1 = (double) r1
            double r7 = (double) r7
            int r0 = r0.width()
        L3d:
            double r4 = (double) r0
            double r7 = r7 / r4
            double r1 = r1 + r7
        L40:
            float r1 = (float) r1
        L41:
            float r7 = r6.limitDrawablePercent(r1, r3)
            r8 = 0
            int r8 = r6.convertPercent(r7, r8)
            boolean r0 = r6.mDispatchTouchDirectly
            java.lang.String r1 = ", percent="
            java.lang.String r2 = ", oldProgress="
            java.lang.String r3 = "QcSliderLayout"
            if (r0 == 0) goto L7f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "trackMove: dispatchTouchDirectly, newProgress="
            r0.append(r4)
            r0.append(r8)
            r0.append(r2)
            int r2 = r6.mProgress
            r0.append(r2)
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            android.util.Log.d(r3, r7)
            com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout$OnSlideChangeListener r7 = r6.mOnSlideChangeListener
            if (r7 == 0) goto Lb4
            r7.onProgressChanged(r6, r8)
            goto Lb4
        L7f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "trackMove: newProgress="
            r0.append(r4)
            r0.append(r8)
            r0.append(r2)
            int r2 = r6.mProgress
            r0.append(r2)
            r0.append(r1)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r3, r0)
            r6.setDrawablePercent(r7)
            int r7 = r6.mProgress
            if (r8 == r7) goto Lb4
            r6.mProgress = r8
            com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout$OnSlideChangeListener r7 = r6.mOnSlideChangeListener
            if (r7 == 0) goto Lb4
            int r8 = r6.mProgress
            r7.onProgressChanged(r6, r8)
        Lb4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.QcSliderLayout.trackMove(float, float):void");
    }

    private void onStartTrackingTouch() {
        OnSlideChangeListener onSlideChangeListener = this.mOnSlideChangeListener;
        if (onSlideChangeListener != null) {
            onSlideChangeListener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        OnSlideChangeListener onSlideChangeListener = this.mOnSlideChangeListener;
        if (onSlideChangeListener != null) {
            onSlideChangeListener.onStopTrackingTouch(this);
        }
    }

    private void setDrawablePercent(float f) {
        int i = (int) (f * this.mMaxLevel);
        Log.v(TAG, "setDrawablePercent: percent=" + f + ", currentLevel=" + this.mQcSliderDrawable.getLevel() + ", level=" + i);
        boolean level = this.mQcSliderDrawable.setLevel(i);
        StringBuilder sb = new StringBuilder();
        sb.append("setDrawablePercent: levelChanged=");
        sb.append(level);
        Log.v(TAG, sb.toString());
    }

    private float limitDrawablePercent(float f, boolean z) {
        return MathUtils.clamp(f, !z ? 0.0f : this.mMinPercentLimit, !z ? 1.0f : this.mMaxPercentLimit);
    }

    private int limitProgress(int i, boolean z) {
        return MathUtils.clamp(i, (z && this.mMinLimitInit) ? this.mMinLimit : this.mMinProgress, (z && this.mMaxLimitInit) ? this.mMaxLimit : this.mMaxProgress);
    }

    private int convertPercent(float f, boolean z) {
        float range = (float) ((f * getRange()) + getMin());
        return limitProgress((int) (z ? Math.floor(range) : Math.ceil(range)), true);
    }

    private float convertProgress(int i) {
        int range = getRange();
        if (range > 0) {
            return (float) ((i - getMin()) / range);
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class QcSliderDrawable extends Drawable {
        private static final int MAX_LEVEL = 10000;
        private static final String TAG = "QcSliderDrawable";
        private int mBgColor;
        private Paint mBgPaint;
        private float mBgRadius;
        private Rect mContainerBounds;
        private boolean mEnabled;
        private int mInsetX;
        private int mInsetY;
        private Paint mPaint;
        private Bitmap mSliderBitmap;
        private Rect mSliderBounds;
        private Canvas mSliderCanvas;
        private int mSliderColor;
        private int mSliderFrom;
        private float mSliderInnerRadius;
        private int mSliderInsetX;
        private int mSliderInsetY;
        private float mSliderOuterRadius;
        private Paint mSliderPaint;
        private BitmapShader mSliderShader;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        QcSliderDrawable() {
            this.mBgRadius = 0.0f;
            this.mSliderOuterRadius = 0.0f;
            this.mSliderInnerRadius = 0.0f;
            this.mSliderFrom = 0;
            this.mSliderInsetX = 0;
            this.mSliderInsetY = 0;
            this.mInsetX = 0;
            this.mInsetY = 0;
            this.mPaint = new Paint(5);
            this.mSliderPaint = new Paint(5);
            this.mBgPaint = new Paint(1);
            this.mSliderBounds = new Rect();
            this.mContainerBounds = new Rect();
        }

        QcSliderDrawable(int i, int i2) {
            this();
            setColor(i, i2);
        }

        void setCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mBgRadius = f;
        }

        void setSliderOuterCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mSliderOuterRadius = f;
        }

        void setSliderInnerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mSliderInnerRadius = f;
        }

        void setColor(int i, int i2) {
            this.mBgColor = i;
            this.mSliderColor = i2;
            this.mBgPaint.setColor(this.mBgColor);
            this.mSliderPaint.setColor(this.mSliderColor);
        }

        void setSliderFrom(int i) {
            this.mSliderFrom = i;
        }

        void setSliderInset(int i, int i2) {
            this.mSliderInsetX = i;
            this.mSliderInsetY = i2;
        }

        void setInset(int i, int i2) {
            this.mInsetX = i;
            this.mInsetY = i2;
        }

        Rect getContainerBounds() {
            return this.mContainerBounds;
        }

        Rect getSliderBounds() {
            return this.mSliderBounds;
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] iArr) {
            boolean z;
            int length = iArr.length;
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
            boolean z2 = this.mEnabled != z;
            this.mEnabled = z;
            return z2;
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onLevelChange(int i) {
            if (this.mContainerBounds != null) {
                refreshSliderBounds(i);
                invalidateSelf();
                return true;
            }
            return super.onLevelChange(i);
        }

        private void refreshSliderBounds(int i) {
            if (this.mContainerBounds.isEmpty() && getBounds().isEmpty()) {
                return;
            }
            Rect rect = this.mContainerBounds;
            if (rect == null) {
                rect = getBounds();
            }
            float f = (float) (i / 10000.0d);
            int i2 = this.mSliderFrom;
            if (i2 == 0) {
                int width = (int) (rect.left + (rect.width() * f));
                this.mSliderBounds.set(rect.left, rect.top, Math.min(width, rect.right), rect.bottom);
                Log.d(TAG, "refreshSliderBounds: level=" + i + " scale=" + f + " width=" + rect.width() + " x=" + width + " bounds=" + rect);
            } else if (i2 == 1) {
                int height = (int) (rect.top + (rect.height() * f));
                this.mSliderBounds.set(rect.left, rect.top, rect.right, Math.min(height, rect.bottom));
                Log.d(TAG, "refreshSliderBounds: level=" + i + " scale=" + f + " height=" + rect.height() + " y=" + height + " bounds=" + rect);
            } else if (i2 == 2) {
                int width2 = (int) (rect.right - (rect.width() * f));
                this.mSliderBounds.set(Math.max(width2, rect.left), rect.top, rect.right, rect.bottom);
                Log.d(TAG, "refreshSliderBounds: level=" + i + " scale=" + f + " width=" + rect.width() + " x=" + width2 + " bounds=" + rect);
            } else if (i2 != 3) {
            } else {
                int height2 = (int) (rect.bottom - (rect.height() * f));
                this.mSliderBounds.set(rect.left, Math.max(height2, rect.top), rect.right, rect.bottom);
                Log.d(TAG, "refreshSliderBounds: level=" + i + " scale=" + f + " height=" + rect.height() + " y=" + height2 + " bounds=" + rect);
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mSliderBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = this.mSliderCanvas;
            if (canvas == null) {
                this.mSliderCanvas = new Canvas(this.mSliderBitmap);
            } else {
                canvas.setBitmap(this.mSliderBitmap);
            }
            this.mSliderShader = new BitmapShader(this.mSliderBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            rect.inset(this.mInsetX, this.mInsetY);
            this.mContainerBounds.set(rect);
            this.mContainerBounds.inset(this.mSliderInsetX, this.mSliderInsetY);
            refreshSliderBounds(getLevel());
        }

        @Override // android.graphics.drawable.Drawable
        public void getOutline(Outline outline) {
            Rect bounds = getBounds();
            float f = this.mBgRadius;
            float min = f > 0.0f ? (float) Math.min(f, Math.min(bounds.width(), bounds.height()) * 0.5d) : 0.0f;
            if (min > 0.0f) {
                outline.setRoundRect(bounds, min);
            } else {
                outline.setRect(bounds);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public Rect getDirtyBounds() {
            return this.mContainerBounds;
        }

        @Override // android.graphics.drawable.Drawable
        public void invalidateSelf() {
            Drawable.Callback callback = getCallback();
            if (callback != null) {
                Log.d(TAG, "invalidateSelf: callback className=" + callback.getClass().getSimpleName() + " hashCode=" + callback.hashCode());
            } else {
                Log.d(TAG, "invalidateSelf: callback=null");
            }
            super.invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
            if (this.mSliderCanvas != null) {
                this.mSliderBitmap.eraseColor(0);
                int save = this.mSliderCanvas.save();
                drawRect(this.mSliderCanvas, this.mSliderInnerRadius, this.mSliderBounds, this.mSliderPaint);
                this.mSliderCanvas.restoreToCount(save);
            }
            int save2 = canvas.save();
            drawRect(canvas, this.mBgRadius, bounds, this.mBgPaint);
            this.mPaint.setShader(this.mSliderShader);
            drawRect(canvas, this.mSliderOuterRadius, this.mContainerBounds, this.mPaint);
            if (save2 != 0) {
                canvas.restoreToCount(save2);
            }
        }

        private void drawRect(Canvas canvas, float f, Rect rect, Paint paint) {
            if (f > 0.0f) {
                float min = (float) Math.min(f, Math.min(rect.width(), rect.height()) * 0.5d);
                canvas.drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, min, min, paint);
                return;
            }
            canvas.drawRect(rect, paint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.mPaint.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class QcSliderDrawableBuilder {
        private int mBgColor;
        private int mSliderColor;
        private float mBgRadius = 0.0f;
        private float mSliderOuterRadius = 0.0f;
        private float mSliderInnerRadius = 0.0f;
        private int mSliderFrom = 0;
        private int mSliderInsetX = 0;
        private int mSliderInsetY = 0;
        private int mInsetX = 0;
        private int mInsetY = 0;

        QcSliderDrawableBuilder() {
        }

        QcSliderDrawableBuilder setColor(int i, int i2) {
            this.mBgColor = i;
            this.mSliderColor = i2;
            return this;
        }

        QcSliderDrawableBuilder setCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mBgRadius = f;
            return this;
        }

        QcSliderDrawableBuilder setSliderOuterCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mSliderOuterRadius = f;
            return this;
        }

        QcSliderDrawableBuilder setSliderInnerCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mSliderInnerRadius = f;
            return this;
        }

        QcSliderDrawableBuilder setSliderFrom(int i) {
            this.mSliderFrom = i;
            return this;
        }

        QcSliderDrawableBuilder setSliderInset(int i, int i2) {
            this.mSliderInsetX = i;
            this.mSliderInsetY = i2;
            return this;
        }

        QcSliderDrawableBuilder setInset(int i, int i2) {
            this.mInsetX = i;
            this.mInsetY = i2;
            return this;
        }

        QcSliderDrawable build() {
            QcSliderDrawable qcSliderDrawable = new QcSliderDrawable(this.mBgColor, this.mSliderColor);
            qcSliderDrawable.setCornerRadius(this.mBgRadius);
            qcSliderDrawable.setSliderOuterCornerRadius(this.mSliderOuterRadius);
            qcSliderDrawable.setSliderInnerRadius(this.mSliderInnerRadius);
            qcSliderDrawable.setSliderFrom(this.mSliderFrom);
            qcSliderDrawable.setSliderInset(this.mSliderInsetX, this.mSliderInsetY);
            qcSliderDrawable.setInset(this.mInsetX, this.mInsetY);
            return qcSliderDrawable;
        }
    }
}
