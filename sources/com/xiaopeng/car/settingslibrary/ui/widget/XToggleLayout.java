package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
/* loaded from: classes.dex */
public class XToggleLayout extends RelativeLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int[] PRESSED_STATE_SET = {16842919};
    private Drawable mBackground;
    private boolean mChecked;
    private int mId;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    ThemeViewModel mThemeViewModel;

    /* loaded from: classes.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(XToggleLayout xToggleLayout, boolean z);
    }

    public XToggleLayout(Context context) {
        this(context, null);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, 0);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XToggleLayout, i, i2);
        this.mBackground = obtainStyledAttributes.getDrawable(R.styleable.XToggleLayout_android_background);
        this.mId = obtainStyledAttributes.getResourceId(R.styleable.XToggleLayout_android_background, 0);
        obtainStyledAttributes.recycle();
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, i2);
        init(context);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
        if (ThemeManager.isThemeChanged(configuration)) {
            setBackground(getContext().getDrawable(this.mId));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
    }

    private void init(Context context) {
        setClickable(true);
        setBackground(this.mBackground);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        if (isPressed()) {
            mergeDrawableStates(onCreateDrawableState, PRESSED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            refreshDrawableState();
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.view.View
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
}
