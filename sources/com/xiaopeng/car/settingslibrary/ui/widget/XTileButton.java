package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XFrameLayout;
/* loaded from: classes.dex */
public class XTileButton extends XFrameLayout {
    public static final int HORIZONTAL = 1;
    private static final long PRESS_DURATION = 100;
    private static final float SCALE_RULES = 0.96f;
    public static final int VERTICAL = 0;
    private int mBackground;
    private int mImage;
    ImageView mImageView;
    private int mOrientation;
    ObjectAnimator mScaleXAnimator;
    ObjectAnimator mScaleYAnimator;
    private String mText;
    private int mTextColor;
    TextView mTextView;
    RelativeLayout mTileLayout;

    public XTileButton(Context context) {
        this(context, null);
    }

    public XTileButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTileButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XTileButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mImage = -1;
        this.mBackground = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XTileButton, i, i2);
        this.mImage = obtainStyledAttributes.getResourceId(R.styleable.XTileButton_tile_image, -1);
        this.mBackground = obtainStyledAttributes.getResourceId(R.styleable.XTileButton_tile_background, -1);
        this.mText = obtainStyledAttributes.getString(R.styleable.XTileButton_tile_text);
        this.mTextColor = obtainStyledAttributes.getResourceId(R.styleable.XTileButton_tile_textColor, -1);
        this.mOrientation = obtainStyledAttributes.getInt(R.styleable.XTileButton_tile_orientation, 0);
        obtainStyledAttributes.recycle();
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        ImageView imageView;
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            RelativeLayout relativeLayout = this.mTileLayout;
            if (relativeLayout != null && this.mBackground != -1) {
                relativeLayout.setBackground(getContext().getDrawable(this.mBackground));
            }
            if (this.mTextView != null && this.mTextColor > 0) {
                this.mTextView.setTextColor(getResources().getColorStateList(this.mTextColor, getContext().getTheme()));
            }
            if (this.mImage == -1 || (imageView = this.mImageView) == null) {
                return;
            }
            imageView.setImageDrawable(getContext().getDrawable(this.mImage));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ObjectAnimator objectAnimator = this.mScaleXAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mScaleYAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
    }

    private void init() {
        if (this.mOrientation == 0) {
            this.mTileLayout = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_toggle_tile, (ViewGroup) this, false);
        } else {
            this.mTileLayout = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_toggle_tile_horizontal, (ViewGroup) this, false);
        }
        if (this.mBackground != -1) {
            this.mTileLayout.setBackground(getContext().getDrawable(this.mBackground));
        }
        this.mImageView = (ImageView) this.mTileLayout.findViewById(R.id.btn);
        if (this.mImage != -1) {
            this.mImageView.setImageDrawable(getContext().getDrawable(this.mImage));
        }
        this.mTextView = (TextView) this.mTileLayout.findViewById(R.id.toggle_button_text);
        this.mTextView.setText(this.mText);
        if (this.mTextView != null && this.mTextColor > 0) {
            this.mTextView.setTextColor(getResources().getColorStateList(this.mTextColor, getContext().getTheme()));
        }
        addView(this.mTileLayout);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        setPivotX((float) (i / 2.0d));
        setPivotY((float) (i2 / 2.0d));
        this.mScaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, SCALE_RULES);
        this.mScaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, SCALE_RULES);
        this.mScaleXAnimator.setDuration(PRESS_DURATION);
        this.mScaleYAnimator.setDuration(PRESS_DURATION);
        this.mScaleXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mScaleYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mScaleXAnimator.start();
                this.mScaleYAnimator.start();
            } else if (action == 1 || action == 3) {
                this.mScaleXAnimator.reverse();
                this.mScaleYAnimator.reverse();
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    public void setImageResource(int i) {
        this.mImage = i;
        if (this.mImage != -1) {
            this.mImageView.setImageDrawable(getContext().getDrawable(this.mImage));
        }
    }

    public void setTextRes(int i) {
        this.mTextView.setText(i);
    }

    public void setBackgroundRes(int i) {
        this.mBackground = i;
        if (this.mBackground != -1) {
            this.mTileLayout.setBackground(getContext().getDrawable(this.mBackground));
        }
    }

    public void setTextColor(int i) {
        this.mTextView.setTextColor(getResources().getColorStateList(i, getContext().getTheme()));
        this.mTextColor = i;
    }

    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        setChildSelected(this, z);
    }

    private void setChildSelected(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildSelected((ViewGroup) childAt, z);
            }
            childAt.setSelected(z);
        }
    }

    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setChildEnabled(this, z);
    }

    private void setChildEnabled(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildEnabled((ViewGroup) childAt, z);
            }
            childAt.setEnabled(z);
        }
    }
}
