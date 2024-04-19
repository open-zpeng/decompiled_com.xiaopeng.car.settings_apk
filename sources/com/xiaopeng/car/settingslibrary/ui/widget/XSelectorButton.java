package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
/* loaded from: classes.dex */
public class XSelectorButton extends RadioGroup {
    private static final int DEFAULT_PADDING = 4;
    private static final int DEFAULT_TEXT_COMPENSATE = 32;
    private static final int DEFAULT_TEXT_SIZE = 20;
    private static final int DEFAULT_WEIGHT = 1;
    RadioButton[] mButtonArray;
    int[] mDrawableArray;
    private boolean mIsIcon;
    private int mItemCount;
    private Drawable[] mItemIcons;
    private CharSequence[] mItemText;
    OnSelectChangedListener mOnSelectChangedListener;
    RadioButton mRadioButton;
    private ColorStateList mTextColor;
    private final int mTextSize;
    ThemeViewModel mThemeViewModel;

    /* loaded from: classes.dex */
    public interface OnSelectChangedListener {
        void onSelectChanged(XSelectorButton xSelectorButton, int i, boolean z);
    }

    public XSelectorButton(Context context) {
        this(context, null);
    }

    public XSelectorButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsIcon = false;
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XSelectorButton);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XSelectorButton_itemIcon, 0);
        if (resourceId != 0) {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(resourceId);
            this.mItemIcons = new Drawable[obtainTypedArray.length()];
            for (int i = 0; i < obtainTypedArray.length(); i++) {
                this.mItemIcons[i] = obtainTypedArray.getDrawable(i);
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
        obtainStyledAttributes.recycle();
        init(context);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
        if (ThemeManager.isThemeChanged(configuration)) {
            setBackground(getContext().getDrawable(R.drawable.radiobutton_background));
            int i2 = 0;
            int i3 = 0;
            while (true) {
                i = this.mItemCount;
                if (i2 >= i) {
                    break;
                }
                if (i2 == 0) {
                    this.mButtonArray[i2].setBackground(getContext().getDrawable(this.mDrawableArray[i2]));
                } else if (i2 == i - 1) {
                    this.mButtonArray[i2].setBackground(getContext().getDrawable(this.mDrawableArray[i2]));
                } else {
                    this.mButtonArray[i2].setBackground(getContext().getDrawable(this.mDrawableArray[i2]));
                }
                if (this.mButtonArray[i2].isChecked()) {
                    i3 = i2;
                }
                i2++;
            }
            if (i3 < i - 1) {
                this.mButtonArray[i3 + 1].setBackground(null);
            }
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

    private void init(final Context context) {
        this.mItemCount = this.mIsIcon ? this.mItemIcons.length : this.mItemText.length;
        if (this.mItemCount < 1) {
            throw new IllegalArgumentException("The number of items cannot be less than 1! ");
        }
        setBackground(context.getDrawable(R.drawable.radiobutton_background));
        setPadding(4, 4, 4, 4);
        setLayoutDirection(0);
        setOrientation(0);
        int i = this.mItemCount;
        this.mButtonArray = new RadioButton[i];
        this.mDrawableArray = new int[i];
        for (int i2 = 0; i2 < this.mItemCount; i2++) {
            this.mRadioButton = new RadioButton(getContext());
            addView(this.mRadioButton, new LinearLayout.LayoutParams(-2, -1, 1.0f));
            if (i2 == 0) {
                this.mDrawableArray[i2] = R.drawable.selector_radio;
            } else if (i2 == this.mItemCount - 1) {
                this.mDrawableArray[i2] = R.drawable.selector_radio_right;
            } else {
                this.mDrawableArray[i2] = R.drawable.selector_radio_center;
            }
            if (!this.mIsIcon) {
                this.mRadioButton.setTextColor(this.mTextColor);
                this.mRadioButton.setTextSize(this.mTextSize);
                this.mRadioButton.setText(this.mItemText[i2]);
            } else {
                this.mRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, this.mItemIcons[i2], (Drawable) null, (Drawable) null);
                this.mRadioButton.setPadding(0, 32, 0, 0);
            }
            this.mRadioButton.setButtonDrawable((Drawable) null);
            this.mRadioButton.setBackground(context.getDrawable(this.mDrawableArray[i2]));
            this.mRadioButton.setGravity(17);
            this.mButtonArray[i2] = this.mRadioButton;
        }
        setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.XSelectorButton.1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i3) {
                if (i3 == -1) {
                    for (int i4 = 0; i4 < XSelectorButton.this.mItemCount; i4++) {
                        if (i4 != 0) {
                            if (i4 == XSelectorButton.this.mItemCount - 1) {
                                XSelectorButton.this.mButtonArray[i4].setBackground(context.getDrawable(R.drawable.selector_radio_right));
                            } else {
                                XSelectorButton.this.mButtonArray[i4].setBackground(context.getDrawable(R.drawable.selector_radio_center));
                            }
                        } else {
                            XSelectorButton.this.mButtonArray[i4].setBackground(context.getDrawable(R.drawable.selector_radio));
                        }
                    }
                    return;
                }
                boolean isPressed = radioGroup.findViewById(i3).isPressed();
                int i5 = 0;
                for (int i6 = 0; i6 < XSelectorButton.this.mItemCount; i6++) {
                    if (XSelectorButton.this.mButtonArray[i6].getId() == i3) {
                        XSelectorButton.this.mButtonArray[i6].setSelected(true);
                        if (i6 == 0) {
                            XSelectorButton.this.mButtonArray[0].setBackground(context.getDrawable(R.drawable.selector_radio));
                            XSelectorButton.this.mButtonArray[1].setBackground(null);
                            XSelectorButton.this.mButtonArray[2].setBackground(context.getDrawable(R.drawable.selector_radio_right));
                        } else if (i6 == 1) {
                            XSelectorButton.this.mButtonArray[2].setBackground(null);
                            XSelectorButton.this.mButtonArray[1].setBackground(context.getDrawable(R.drawable.selector_radio_center));
                            XSelectorButton.this.mButtonArray[0].setBackground(context.getDrawable(R.drawable.selector_radio));
                        } else {
                            XSelectorButton.this.mButtonArray[0].setBackground(context.getDrawable(R.drawable.selector_radio));
                            XSelectorButton.this.mButtonArray[1].setBackground(context.getDrawable(R.drawable.selector_radio_center));
                            XSelectorButton.this.mButtonArray[2].setBackground(context.getDrawable(R.drawable.selector_radio_right));
                        }
                        i5 = i6;
                    } else {
                        XSelectorButton.this.mButtonArray[i6].setSelected(false);
                    }
                }
                if (XSelectorButton.this.mOnSelectChangedListener != null) {
                    XSelectorButton.this.mOnSelectChangedListener.onSelectChanged(XSelectorButton.this, i5, isPressed);
                }
            }
        });
    }

    public void setOnSelectChangedListener(OnSelectChangedListener onSelectChangedListener) {
        this.mOnSelectChangedListener = onSelectChangedListener;
    }

    public void setSelectorIndex(int i) {
        if (i >= 0) {
            RadioButton[] radioButtonArr = this.mButtonArray;
            if (i > radioButtonArr.length - 1) {
                return;
            }
            radioButtonArr[i].setChecked(true);
        }
    }

    public void setAllUnSelect() {
        clearCheck();
    }

    public void setEnableView(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(z);
        }
    }

    @Override // android.view.View
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
}
