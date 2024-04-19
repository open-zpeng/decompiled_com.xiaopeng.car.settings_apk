package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XLinearLayout;
import java.math.BigDecimal;
/* loaded from: classes.dex */
public class XSelectorAnimButton extends XLinearLayout {
    private static final int DEFAULT_PADDING = 4;
    private static final int DEFAULT_TEXT_SIZE = 20;
    private static final long DURATION = 300;
    private static final float LINE_HEIGHT = 50.0f;
    private static final long PRESS_DURATION = 100;
    private static final float SCALE_RULES = 0.96f;
    private float defaultRadius;
    private int mBackground;
    private View.OnClickListener mChildClickListener;
    private float mEndLeft;
    private float mEndLeftRadius;
    private float mEndRightRadius;
    private boolean mIsIcon;
    private int mItemCount;
    private int[] mItemIcons;
    private CharSequence[] mItemText;
    private Paint mLinePaint;
    OnSelectChangedListener mOnSelectChangedListener;
    private Paint mPaint;
    Path mPath;
    private float mPropertyRadius;
    float[] mRadius;
    RectF mRect;
    ObjectAnimator mScaleXAnimator;
    ObjectAnimator mScaleYAnimator;
    private int mSelectTabIndex;
    private float mStartLeft;
    private float mStartLeftRadius;
    private float mStartRightRadius;
    private ColorStateList mTextColor;
    private final int mTextSize;
    private float mTmpLeft;
    private float mTmpLeftRadius;
    private float mTmpRightRadius;
    private ValueAnimator mValueAnimator;
    private float roundRadius;

    /* loaded from: classes.dex */
    public interface OnSelectChangedListener {
        void onSelectChanged(XSelectorAnimButton xSelectorAnimButton, int i, boolean z);
    }

    public XSelectorAnimButton(Context context) {
        this(context, null);
    }

    public XSelectorAnimButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.roundRadius = 27.0f;
        this.defaultRadius = 1.0f;
        this.mPropertyRadius = -1.0f;
        this.mBackground = -1;
        this.mRect = new RectF();
        this.mRadius = new float[8];
        this.mIsIcon = false;
        this.mSelectTabIndex = -1;
        this.mPaint = new Paint(1);
        this.mLinePaint = new Paint(1);
        this.mPath = new Path();
        this.mChildClickListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.XSelectorAnimButton.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (XSelectorAnimButton.this.mValueAnimator == null || !XSelectorAnimButton.this.mValueAnimator.isRunning()) {
                    XSelectorAnimButton xSelectorAnimButton = XSelectorAnimButton.this;
                    xSelectorAnimButton.selectTab(xSelectorAnimButton.indexOfChild(view), true, true);
                    if (XSelectorAnimButton.this.mOnSelectChangedListener != null) {
                        OnSelectChangedListener onSelectChangedListener = XSelectorAnimButton.this.mOnSelectChangedListener;
                        XSelectorAnimButton xSelectorAnimButton2 = XSelectorAnimButton.this;
                        onSelectChangedListener.onSelectChanged(xSelectorAnimButton2, xSelectorAnimButton2.indexOfChild(view), true);
                    }
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XSelectorButton);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XSelectorButton_itemIcon, 0);
        if (resourceId != 0) {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(resourceId);
            this.mItemIcons = new int[obtainTypedArray.length()];
            for (int i = 0; i < obtainTypedArray.length(); i++) {
                this.mItemIcons[i] = obtainTypedArray.getResourceId(i, -1);
            }
            obtainTypedArray.recycle();
            this.mIsIcon = true;
        }
        this.mItemText = obtainStyledAttributes.getTextArray(R.styleable.XSelectorButton_itemText);
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XSelectorButton_android_textSize, 20);
        this.mTextColor = obtainStyledAttributes.getColorStateList(R.styleable.XSelectorButton_android_textColor);
        if (this.mTextColor == null) {
            this.mTextColor = ColorStateList.valueOf(context.getColor(17170444));
        }
        this.mBackground = obtainStyledAttributes.getResourceId(R.styleable.XSelectorButton_selector_background, -1);
        this.mPropertyRadius = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XSelectorButton_round_radius, -1);
        if (BigDecimal.valueOf(this.mPropertyRadius).compareTo(BigDecimal.valueOf(-1L)) != 0) {
            float f = this.mPropertyRadius;
            this.defaultRadius = f;
            this.roundRadius = f;
        }
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        init(context);
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
        RectF rectF = this.mRect;
        float f = this.mTmpLeft;
        rectF.left = f;
        rectF.right = (float) (f + getItemWidth());
        this.mRect.top = getPaddingTop();
        this.mRect.bottom = (float) (getHeight() - getPaddingBottom());
        startAnimate(false);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            if (this.mBackground != -1) {
                setBackground(getContext().getDrawable(this.mBackground));
            } else {
                setBackground(getContext().getDrawable(R.drawable.radiobutton_background));
            }
            this.mLinePaint.setColor(getContext().getColor(R.color.divider_line));
            if (BigDecimal.valueOf(this.mPropertyRadius).compareTo(BigDecimal.valueOf(-1L)) != 0) {
                this.mPaint.setColor(getContext().getColor(R.color.quick_menu_ui_checked_color));
            } else {
                this.mPaint.setColor(getContext().getColor(R.color.radiobutton_checked_color));
            }
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setTextColor(this.mTextColor);
                }
                if (childAt instanceof ImageView) {
                    ((ImageView) childAt).setImageResource(this.mItemIcons[i]);
                }
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ObjectAnimator objectAnimator = this.mScaleXAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mScaleYAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
    }

    private void init(Context context) {
        this.mLinePaint.setStrokeWidth(1.0f);
        this.mLinePaint.setColor(getContext().getColor(R.color.divider_line));
        this.mPaint.setStrokeWidth(0.0f);
        if (BigDecimal.valueOf(this.mPropertyRadius).compareTo(BigDecimal.valueOf(-1L)) != 0) {
            this.mPaint.setColor(getContext().getColor(R.color.quick_menu_ui_checked_color));
        } else {
            this.mPaint.setColor(getContext().getColor(R.color.radiobutton_checked_color));
        }
        this.mItemCount = this.mIsIcon ? this.mItemIcons.length : this.mItemText.length;
        if (this.mItemCount < 1) {
            throw new IllegalArgumentException("The number of items cannot be less than 1! ");
        }
        if (this.mBackground != -1) {
            setBackground(getContext().getDrawable(this.mBackground));
        } else {
            setBackground(context.getDrawable(R.drawable.radiobutton_background));
        }
        setPadding(4, 4, 4, 4);
        setLayoutDirection(0);
        setGravity(17);
        for (int i = 0; i < this.mItemCount; i++) {
            if (this.mIsIcon) {
                addChild(i, this.mItemIcons[i]);
            } else {
                addChild(i, this.mItemText[i]);
            }
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            getChildAt(i2).setOnClickListener(this.mChildClickListener);
        }
    }

    private void addChild(int i, CharSequence charSequence) {
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.x_tab_layout_title_view, (ViewGroup) this, false);
        textView.setText(charSequence);
        textView.setTextColor(this.mTextColor);
        textView.setTextSize(0, this.mTextSize);
        textView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        addView(textView, i);
    }

    private void addChild(int i, int i2) {
        ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.x_selector_button_icon, (ViewGroup) this, false);
        imageView.setImageResource(i2);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        addView(imageView, i);
    }

    public int getTabCount() {
        return getChildCount();
    }

    private View getSelectView() {
        return getChildAt(this.mSelectTabIndex);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectTab(int i, boolean z, boolean z2) {
        if (i >= getTabCount() || i < 0 || i == this.mSelectTabIndex) {
            return;
        }
        View childAt = getChildAt(i);
        View selectView = getSelectView();
        if (childAt != selectView) {
            if (childAt != null) {
                childAt.setSelected(true);
            }
            if (selectView != null) {
                selectView.setSelected(false);
            }
            this.mSelectTabIndex = i;
        }
        startAnimate(z);
    }

    private void startAnimate(boolean z) {
        computeEnd();
        if (z) {
            if (this.mValueAnimator == null) {
                this.mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mValueAnimator.setDuration(DURATION);
                this.mValueAnimator.setInterpolator(new DecelerateInterpolator());
                this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.XSelectorAnimButton.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        XSelectorAnimButton xSelectorAnimButton;
                        XSelectorAnimButton xSelectorAnimButton2;
                        XSelectorAnimButton xSelectorAnimButton3;
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        double d = floatValue;
                        XSelectorAnimButton.this.mTmpLeft = (float) (((xSelectorAnimButton.mEndLeft - XSelectorAnimButton.this.mStartLeft) * d) + XSelectorAnimButton.this.mStartLeft);
                        XSelectorAnimButton.this.mTmpLeftRadius = (float) (((xSelectorAnimButton2.mEndLeftRadius - XSelectorAnimButton.this.mStartLeftRadius) * d) + XSelectorAnimButton.this.mStartLeftRadius);
                        XSelectorAnimButton.this.mTmpRightRadius = (float) (((xSelectorAnimButton3.mEndRightRadius - XSelectorAnimButton.this.mStartRightRadius) * d) + XSelectorAnimButton.this.mStartRightRadius);
                        XSelectorAnimButton.this.invalidate();
                    }
                });
                this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.XSelectorAnimButton.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        XSelectorAnimButton.this.correctionData();
                    }
                });
            }
            this.mValueAnimator.start();
            return;
        }
        correctionData();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void correctionData() {
        float f = this.mEndLeft;
        this.mTmpLeft = f;
        this.mStartLeft = f;
        float f2 = this.mEndLeftRadius;
        this.mTmpLeftRadius = f2;
        float f3 = this.mEndRightRadius;
        this.mTmpRightRadius = f3;
        this.mStartLeftRadius = f2;
        this.mStartRightRadius = f3;
    }

    private void computeStart() {
        int i = this.mSelectTabIndex;
        if (i < 0) {
            this.mStartLeft = 0.0f;
        } else if (i < 0 || getWidth() <= 0) {
        } else {
            this.mStartLeft = (float) ((this.mSelectTabIndex * getItemWidth()) + getPaddingLeft());
        }
    }

    private void computeEnd() {
        int i = this.mSelectTabIndex;
        if (i < 0) {
            this.mEndLeft = 0.0f;
        } else if (i >= 0 && getWidth() > 0) {
            this.mEndLeft = (float) ((this.mSelectTabIndex * getItemWidth()) + getPaddingLeft());
        }
        int i2 = this.mSelectTabIndex;
        if (i2 == 0) {
            this.mEndLeftRadius = this.roundRadius;
            this.mEndRightRadius = this.defaultRadius;
        } else if (i2 == this.mItemCount - 1) {
            this.mEndLeftRadius = this.defaultRadius;
            this.mEndRightRadius = this.roundRadius;
        } else {
            float f = this.defaultRadius;
            this.mEndLeftRadius = f;
            this.mEndRightRadius = f;
        }
    }

    private float getItemWidth() {
        return (float) (((getWidth() - getPaddingLeft()) - getPaddingRight()) / this.mItemCount);
    }

    private float getRightLineX() {
        return (float) ((getWidth() - getPaddingRight()) - getItemWidth());
    }

    private float getLeftLineX() {
        return (float) (getPaddingLeft() + getItemWidth());
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        RectF rectF = this.mRect;
        float f = this.mTmpLeft;
        rectF.left = f;
        rectF.right = (float) (f + getItemWidth());
        this.mPath.rewind();
        float[] fArr = this.mRadius;
        float f2 = this.mTmpLeftRadius;
        fArr[1] = f2;
        fArr[0] = f2;
        float f3 = this.mTmpRightRadius;
        fArr[3] = f3;
        fArr[2] = f3;
        fArr[5] = f3;
        fArr[4] = f3;
        fArr[7] = f2;
        fArr[6] = f2;
        this.mPath.addRoundRect(this.mRect, fArr, Path.Direction.CW);
        int i = this.mSelectTabIndex;
        if (i == -1) {
            canvas.drawLine(getRightLineX(), (float) ((getHeight() / 2.0d) - 25.0d), getRightLineX(), (float) ((getHeight() / 2.0d) + 25.0d), this.mLinePaint);
            canvas.drawLine(getLeftLineX(), (float) ((getHeight() / 2.0d) - 25.0d), getLeftLineX(), (float) ((getHeight() / 2.0d) + 25.0d), this.mLinePaint);
        } else if (i == 0) {
            canvas.drawPath(this.mPath, this.mPaint);
            canvas.drawLine(getRightLineX(), (float) ((getHeight() / 2.0d) - 25.0d), getRightLineX(), (float) ((getHeight() / 2.0d) + 25.0d), this.mLinePaint);
        } else if (i == this.mItemCount - 1) {
            canvas.drawPath(this.mPath, this.mPaint);
            canvas.drawLine(getLeftLineX(), (float) ((getHeight() / 2.0d) - 25.0d), getLeftLineX(), (float) ((getHeight() / 2.0d) + 25.0d), this.mLinePaint);
        } else {
            canvas.drawPath(this.mPath, this.mPaint);
        }
        super.dispatchDraw(canvas);
    }

    public void setEnableView(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(z);
        }
    }

    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void setEnabled(boolean z) {
        setEnabled(z, true);
    }

    public void setEnabled(boolean z, boolean z2) {
        super.setEnabled(z);
        if (z2) {
            setChildEnabled(this, z);
        }
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

    public void setSelectorIndex(int i) {
        selectTab(i, true, false);
    }

    public void setAllUnSelect() {
        View selectView = getSelectView();
        if (selectView != null) {
            selectView.setSelected(false);
        }
        this.mSelectTabIndex = -1;
        invalidate();
    }

    public void setOnSelectChangedListener(OnSelectChangedListener onSelectChangedListener) {
        this.mOnSelectChangedListener = onSelectChangedListener;
    }
}
