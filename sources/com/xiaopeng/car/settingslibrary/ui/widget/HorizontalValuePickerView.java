package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ScrollerCompat;
import com.xiaopeng.car.settingslibrary.R;
import java.lang.ref.WeakReference;
/* loaded from: classes.dex */
public class HorizontalValuePickerView extends View {
    private static final boolean DEFAULT_CURRENT_ITEM_INDEX_EFFECT = false;
    private static final int DEFAULT_DIVIDER_COLOR = -695533;
    private static final int DEFAULT_DIVIDER_HEIGHT = 2;
    private static final int DEFAULT_DIVIDER_MARGIN_VERTICAL = 0;
    private static final int DEFAULT_INTERVAL_REVISE_DURATION = 300;
    private static final int DEFAULT_ITEM_PADDING_DP_H = 5;
    private static final int DEFAULT_ITEM_PADDING_DP_V = 2;
    private static final int DEFAULT_MARGIN_END_OF_HINT_DP = 8;
    private static final int DEFAULT_MARGIN_START_OF_HINT_DP = 8;
    private static final int DEFAULT_MAX_SCROLL_BY_INDEX_DURATION = 600;
    private static final int DEFAULT_MIN_SCROLL_BY_INDEX_DURATION = 300;
    private static final boolean DEFAULT_RESPOND_CHANGE_IN_MAIN_THREAD = true;
    private static final boolean DEFAULT_RESPOND_CHANGE_ON_DETACH = false;
    private static final int DEFAULT_SHOW_COUNT = 3;
    private static final boolean DEFAULT_SHOW_DIVIDER = true;
    private static final int DEFAULT_TEXT_COLOR_NORMAL = -13421773;
    private static final int DEFAULT_TEXT_COLOR_SELECTED = -695533;
    private static final int DEFAULT_TEXT_SIZE_HINT_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_NORMAL_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_SELECTED_SP = 16;
    private static final boolean DEFAULT_WRAP_SELECTOR_WHEEL = true;
    private static final int HANDLER_INTERVAL_REFRESH = 32;
    private static final int HANDLER_WHAT_LISTENER_VALUE_CHANGED = 2;
    private static final int HANDLER_WHAT_REFRESH = 1;
    private static final int HANDLER_WHAT_REQUEST_LAYOUT = 3;
    private static final float SHADOW_OFFSET = 10.0f;
    private static final String TAG = HorizontalValuePickerView.class.getSimpleName();
    private static final String TEXT_ELLIPSIZE_END = "end";
    private static final String TEXT_ELLIPSIZE_MIDDLE = "middle";
    private static final String TEXT_ELLIPSIZE_START = "start";
    private float currX;
    private float dividerX0;
    private float dividerX1;
    private float downX;
    private float downXGlobal;
    private String mAlterHint;
    private CharSequence[] mAlterTextArrayWithMeasureHint;
    private CharSequence[] mAlterTextArrayWithoutMeasureHint;
    private int mCurrDrawFirstItemIndex;
    private int mCurrDrawFirstItemX;
    private int mCurrDrawGlobalX;
    private boolean mCurrentItemIndexEffect;
    private String[] mDisplayedAttached;
    private String[] mDisplayedValues;
    private int mDividerColor;
    private int mDividerHeight;
    private int mDividerIndex0;
    private int mDividerIndex1;
    private int mDividerMarginB;
    private int mDividerMarginT;
    private String mEmptyItemHint;
    private boolean mFlagMayPress;
    private float mFriction;
    private Handler mHandlerInMainThread;
    private Handler mHandlerInNewThread;
    private HandlerThread mHandlerThread;
    private boolean mHasInit;
    private String mHintText;
    private int mInScrollingPickedNewValue;
    private int mInScrollingPickedOldValue;
    private int mItemPaddingHorizontal;
    private int mItemPaddingVertical;
    private int mItemWidth;
    private int mMarginEndOfHint;
    private int mMarginStartOfHint;
    private int mMaxHeightOfDisplayedValues;
    private int mMaxShowIndex;
    private int mMaxValue;
    private int mMaxWidthOfAlterArrayWithMeasureHint;
    private int mMaxWidthOfAlterArrayWithoutMeasureHint;
    private int mMaxWidthOfDisplayedValues;
    private int mMinShowIndex;
    private int mMinValue;
    private int mMiniVelocityFling;
    private int mNotWrapLimitXBottom;
    private int mNotWrapLimitXTop;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private OnValueChangeListenerInScrolling mOnValueChangeListenerInScrolling;
    private OnValueChangeListenerRelativeToRaw mOnValueChangeListenerRaw;
    private Paint mPaintDivider;
    private Paint mPaintHint;
    private TextPaint mPaintText;
    private TextPaint mPaintTextAttach;
    private boolean mPendingWrapToLinear;
    private int mPrevPickedIndex;
    private boolean mRespondChangeInMainThread;
    private boolean mRespondChangeOnDetach;
    private int mScaledTouchSlop;
    private int mScrollState;
    private ScrollerCompat mScroller;
    private int mShadowDirection;
    private int mShowCount;
    private boolean mShowDivider;
    private int mSpecModeH;
    private int mSpecModeW;
    private int mTextColorHint;
    private int mTextColorNormal;
    private int mTextColorSelected;
    private String mTextEllipsize;
    private int mTextSizeHint;
    private float mTextSizeHintCenterYOffset;
    private int mTextSizeNormal;
    private float mTextSizeNormalCenterYOffset;
    private int mTextSizeSelected;
    private float mTextSizeSelectedCenterYOffset;
    private int mTextSizeSufNormal;
    private int mTextSizeSufSelected;
    private VelocityTracker mVelocityTracker;
    private float mViewCenterY;
    private int mViewHeight;
    private int mViewWidth;
    private int mWidthOfAlterHint;
    private int mWidthOfHintText;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelCheck;

    /* loaded from: classes.dex */
    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(HorizontalValuePickerView horizontalValuePickerView, int i);
    }

    /* loaded from: classes.dex */
    public interface OnValueChangeListener {
        void onTouchDown();

        void onTouchUp();

        void onValueChange(HorizontalValuePickerView horizontalValuePickerView, int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnValueChangeListenerInScrolling {
        void onValueChangeInScrolling(HorizontalValuePickerView horizontalValuePickerView, int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnValueChangeListenerRelativeToRaw {
        void onValueChangeRelativeToRaw(HorizontalValuePickerView horizontalValuePickerView, int i, int i2, String[] strArr);
    }

    private int getEvaluateAlpha(float f, int i) {
        double d = ((-16777216) & i) >>> 24;
        return (((int) (d - (f * d))) << 24) | (((16711680 & i) >>> 16) << 16) | (((65280 & i) >>> 8) << 8) | ((i & 255) >>> 0);
    }

    private int getEvaluateColor(float f, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        double d = f;
        return (((int) (((i & ViewCompat.MEASURED_STATE_MASK) >>> 24) + (((((-16777216) & i2) >>> 24) - i3) * d))) << 24) | (((int) (((i & 16711680) >>> 16) + ((((16711680 & i2) >>> 16) - i4) * d))) << 16) | (((int) (((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8) + ((((65280 & i2) >>> 8) - i5) * d))) << 8) | ((int) (((i & 255) >>> 0) + ((((i2 & 255) >>> 0) - i6) * d)));
    }

    private float getEvaluateSize(float f, float f2, float f3) {
        double d = f2;
        return (float) (d + ((f3 - d) * f));
    }

    public HorizontalValuePickerView(Context context) {
        super(context);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginT = 0;
        this.mDividerMarginB = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = 150;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintTextAttach = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downXGlobal = 0.0f;
        this.downX = 0.0f;
        this.currX = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemX = 0;
        this.mCurrDrawGlobalX = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        init(context);
    }

    public HorizontalValuePickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginT = 0;
        this.mDividerMarginB = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = 150;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintTextAttach = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downXGlobal = 0.0f;
        this.downX = 0.0f;
        this.currX = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemX = 0;
        this.mCurrDrawGlobalX = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        initAttr(context, attributeSet);
        init(context);
    }

    public HorizontalValuePickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginT = 0;
        this.mDividerMarginB = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = 150;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintTextAttach = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downXGlobal = 0.0f;
        this.downX = 0.0f;
        this.currX = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemX = 0;
        this.mCurrDrawGlobalX = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        initAttr(context, attributeSet);
        init(context);
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ValuePickerView);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.ValuePickerView_npv_ShowCount) {
                this.mShowCount = obtainStyledAttributes.getInt(index, 3);
            } else if (index == R.styleable.ValuePickerView_npv_DividerColor) {
                this.mDividerColor = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_DividerHeight) {
                this.mDividerHeight = obtainStyledAttributes.getDimensionPixelSize(index, 2);
            } else if (index == R.styleable.ValuePickerView_npv_DividerMarginLeft) {
                this.mDividerMarginT = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_DividerMarginRight) {
                this.mDividerMarginB = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_TextArray) {
                this.mDisplayedValues = convertCharSequenceArrayToStringArray(obtainStyledAttributes.getTextArray(index));
            } else if (index == R.styleable.ValuePickerView_npv_TextArray_Text) {
                this.mDisplayedAttached = convertCharSequenceArrayToStringArray(obtainStyledAttributes.getTextArray(index));
            } else if (index == R.styleable.ValuePickerView_npv_TextColorNormal) {
                this.mTextColorNormal = obtainStyledAttributes.getColor(index, DEFAULT_TEXT_COLOR_NORMAL);
            } else if (index == R.styleable.ValuePickerView_npv_TextColorSelected) {
                this.mTextColorSelected = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_TextColorHint) {
                this.mTextColorHint = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeNormal) {
                this.mTextSizeNormal = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSelected) {
                this.mTextSizeSelected = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 16.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeHint) {
                this.mTextSizeHint = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSufNormal) {
                this.mTextSizeSufNormal = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSufSelected) {
                this.mTextSizeSufSelected = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 16.0f));
            } else if (index == R.styleable.ValuePickerView_npv_MinValue) {
                this.mMinShowIndex = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_MaxValue) {
                this.mMaxShowIndex = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_WrapSelectorWheel) {
                this.mWrapSelectorWheel = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_ShowDivider) {
                this.mShowDivider = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_HintText) {
                this.mHintText = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeHint) {
                this.mAlterHint = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_EmptyItemHint) {
                this.mEmptyItemHint = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_MarginStartOfHint) {
                this.mMarginStartOfHint = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.ValuePickerView_npv_MarginEndOfHint) {
                this.mMarginEndOfHint = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.ValuePickerView_npv_ItemPaddingVertical) {
                this.mItemPaddingVertical = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 2.0f));
            } else if (index == R.styleable.ValuePickerView_npv_ItemPaddingHorizontal) {
                this.mItemPaddingHorizontal = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 5.0f));
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeTextArrayWithMeasureHint) {
                this.mAlterTextArrayWithMeasureHint = obtainStyledAttributes.getTextArray(index);
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeTextArrayWithoutMeasureHint) {
                this.mAlterTextArrayWithoutMeasureHint = obtainStyledAttributes.getTextArray(index);
            } else if (index == R.styleable.ValuePickerView_npv_RespondChangeOnDetached) {
                this.mRespondChangeOnDetach = obtainStyledAttributes.getBoolean(index, false);
            } else if (index == R.styleable.ValuePickerView_npv_RespondChangeInMainThread) {
                this.mRespondChangeInMainThread = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_TextEllipsize) {
                this.mTextEllipsize = obtainStyledAttributes.getString(index);
            }
        }
        obtainStyledAttributes.recycle();
    }

    private void init(Context context) {
        this.mScroller = ScrollerCompat.create(context);
        this.mMiniVelocityFling = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (this.mTextSizeNormal == 0) {
            this.mTextSizeNormal = sp2px(context, 14.0f);
        }
        if (this.mTextSizeSelected == 0) {
            this.mTextSizeSelected = sp2px(context, 16.0f);
        }
        if (this.mTextSizeHint == 0) {
            this.mTextSizeHint = sp2px(context, 14.0f);
        }
        if (this.mMarginStartOfHint == 0) {
            this.mMarginStartOfHint = dp2px(context, 8.0f);
        }
        if (this.mMarginEndOfHint == 0) {
            this.mMarginEndOfHint = dp2px(context, 8.0f);
        }
        this.mPaintDivider.setColor(this.mDividerColor);
        this.mPaintDivider.setAntiAlias(true);
        this.mPaintDivider.setStyle(Paint.Style.STROKE);
        this.mPaintDivider.setStrokeWidth(this.mDividerHeight);
        this.mPaintText.setColor(this.mTextColorNormal);
        this.mPaintText.setAntiAlias(true);
        this.mPaintText.setTextAlign(Paint.Align.LEFT);
        this.mPaintTextAttach.setColor(this.mTextColorNormal);
        this.mPaintTextAttach.setAntiAlias(true);
        this.mPaintTextAttach.setTextAlign(Paint.Align.LEFT);
        this.mPaintHint.setColor(this.mTextColorHint);
        this.mPaintHint.setAntiAlias(true);
        this.mPaintHint.setTextAlign(Paint.Align.CENTER);
        this.mPaintHint.setTextSize(this.mTextSizeHint);
        int i = this.mShowCount;
        if (i % 2 == 0) {
            this.mShowCount = i + 1;
        }
        if (this.mMinShowIndex == -1 || this.mMaxShowIndex == -1) {
            updateValueForInit();
        }
        initHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class NewThreadHandler extends Handler {
        private final WeakReference<HorizontalValuePickerView> myView;

        public NewThreadHandler(HorizontalValuePickerView horizontalValuePickerView, Looper looper) {
            super(looper);
            this.myView = new WeakReference<>(horizontalValuePickerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int willPickIndexByGlobalX;
            int i;
            HorizontalValuePickerView horizontalValuePickerView = this.myView.get();
            super.handleMessage(message);
            if (horizontalValuePickerView != null) {
                int i2 = message.what;
                if (i2 != 1) {
                    if (i2 != 2) {
                        return;
                    }
                    horizontalValuePickerView.respondPickedValueChanged(message.arg1, message.arg2, message.obj);
                    return;
                }
                int i3 = 0;
                if (!horizontalValuePickerView.mScroller.isFinished()) {
                    if (horizontalValuePickerView.mScrollState == 0) {
                        horizontalValuePickerView.onScrollStateChange(1);
                    }
                    sendMessageDelayed(horizontalValuePickerView.getMsg(1, 0, 0, message.obj), 32L);
                    return;
                }
                if (horizontalValuePickerView.mCurrDrawFirstItemX != 0) {
                    if (horizontalValuePickerView.mScrollState == 0) {
                        horizontalValuePickerView.onScrollStateChange(1);
                    }
                    if (horizontalValuePickerView.mCurrDrawFirstItemX < (-horizontalValuePickerView.mItemWidth) / 2) {
                        i = (int) (((horizontalValuePickerView.mItemWidth + horizontalValuePickerView.mCurrDrawFirstItemX) * 300.0d) / horizontalValuePickerView.mItemWidth);
                        horizontalValuePickerView.mScroller.startScroll(horizontalValuePickerView.mCurrDrawGlobalX, 0, horizontalValuePickerView.mItemWidth + horizontalValuePickerView.mCurrDrawFirstItemX, 0, i * 3);
                        willPickIndexByGlobalX = horizontalValuePickerView.getWillPickIndexByGlobalX(horizontalValuePickerView.mCurrDrawGlobalX + horizontalValuePickerView.mItemWidth + horizontalValuePickerView.mCurrDrawFirstItemX);
                    } else {
                        i = (int) (((-horizontalValuePickerView.mCurrDrawFirstItemX) * 300.0d) / horizontalValuePickerView.mItemWidth);
                        horizontalValuePickerView.mScroller.startScroll(horizontalValuePickerView.mCurrDrawGlobalX, 0, horizontalValuePickerView.mCurrDrawFirstItemX, 0, i * 3);
                        willPickIndexByGlobalX = horizontalValuePickerView.getWillPickIndexByGlobalX(horizontalValuePickerView.mCurrDrawGlobalX + horizontalValuePickerView.mCurrDrawFirstItemX);
                    }
                    i3 = i;
                    horizontalValuePickerView.postInvalidate();
                } else {
                    horizontalValuePickerView.onScrollStateChange(0);
                    willPickIndexByGlobalX = horizontalValuePickerView.getWillPickIndexByGlobalX(horizontalValuePickerView.mCurrDrawGlobalX);
                }
                Message msg = horizontalValuePickerView.getMsg(2, horizontalValuePickerView.mPrevPickedIndex, willPickIndexByGlobalX, message.obj);
                if (horizontalValuePickerView.mRespondChangeInMainThread) {
                    horizontalValuePickerView.mHandlerInMainThread.sendMessageDelayed(msg, i3 * 2);
                } else {
                    sendMessageDelayed(msg, i3 * 2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class MainHandler extends Handler {
        private final WeakReference<HorizontalValuePickerView> myView;

        public MainHandler(HorizontalValuePickerView horizontalValuePickerView) {
            this.myView = new WeakReference<>(horizontalValuePickerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            HorizontalValuePickerView horizontalValuePickerView = this.myView.get();
            super.handleMessage(message);
            if (horizontalValuePickerView != null) {
                int i = message.what;
                if (i == 2) {
                    horizontalValuePickerView.respondPickedValueChanged(message.arg1, message.arg2, message.obj);
                } else if (i != 3) {
                } else {
                    horizontalValuePickerView.requestLayout();
                }
            }
        }
    }

    private void initHandler() {
        this.mHandlerThread = new HandlerThread("HandlerThread-For-Refreshing");
        this.mHandlerThread.start();
        this.mHandlerInNewThread = new NewThreadHandler(this, this.mHandlerThread.getLooper());
        this.mHandlerInMainThread = new MainHandler(this);
    }

    private void respondPickedValueChangedInScrolling(int i, int i2) {
        this.mOnValueChangeListenerInScrolling.onValueChangeInScrolling(this, i, i2);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updateMaxWHOfDisplayedValues(false);
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5;
        super.onSizeChanged(i, i2, i3, i4);
        this.mViewWidth = i;
        this.mViewHeight = i2;
        this.mItemWidth = this.mViewWidth / this.mShowCount;
        this.mViewCenterY = (float) (((this.mViewHeight + getPaddingTop()) - getPaddingBottom()) / 2.0d);
        boolean z = false;
        if (getOneRecycleSize() > 1) {
            if (this.mHasInit) {
                i5 = getValue() - this.mMinValue;
            } else if (this.mCurrentItemIndexEffect) {
                i5 = this.mCurrDrawFirstItemIndex + ((this.mShowCount - 1) / 2);
            }
            if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
                z = true;
            }
            correctPositionByDefaultValue(i5, z);
            updateFontAttr();
            updateNotWrapXLimit();
            updateDividerAttr();
            this.mHasInit = true;
        }
        i5 = 0;
        if (this.mWrapSelectorWheel) {
            z = true;
        }
        correctPositionByDefaultValue(i5, z);
        updateFontAttr();
        updateNotWrapXLimit();
        updateDividerAttr();
        this.mHasInit = true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            initHandler();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandlerThread.quit();
        if (this.mItemWidth == 0) {
            return;
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
            this.mCurrDrawGlobalX = this.mScroller.getCurrX();
            calculateFirstItemParameterByGlobalX();
            int i = this.mCurrDrawFirstItemX;
            if (i != 0) {
                int i2 = this.mItemWidth;
                if (i < (-i2) / 2) {
                    this.mCurrDrawGlobalX = this.mCurrDrawGlobalX + i2 + i;
                } else {
                    this.mCurrDrawGlobalX += i;
                }
                calculateFirstItemParameterByGlobalX();
            }
            onScrollStateChange(0);
        }
        int willPickIndexByGlobalX = getWillPickIndexByGlobalX(this.mCurrDrawGlobalX);
        int i3 = this.mPrevPickedIndex;
        if (willPickIndexByGlobalX != i3 && this.mRespondChangeOnDetach) {
            try {
                if (this.mOnValueChangeListener != null) {
                    this.mOnValueChangeListener.onValueChange(this, i3 + this.mMinValue, this.mMinValue + willPickIndexByGlobalX);
                }
                if (this.mOnValueChangeListenerRaw != null) {
                    this.mOnValueChangeListenerRaw.onValueChangeRelativeToRaw(this, this.mPrevPickedIndex, willPickIndexByGlobalX, this.mDisplayedValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mPrevPickedIndex = willPickIndexByGlobalX;
    }

    public int getOneRecycleSize() {
        return (this.mMaxShowIndex - this.mMinShowIndex) + 1;
    }

    public int getRawContentSize() {
        String[] strArr = this.mDisplayedValues;
        if (strArr != null) {
            return strArr.length;
        }
        return 0;
    }

    public void setDisplayedValuesAndPickedIndex(String[] strArr, int i, boolean z) {
        stopScrolling();
        if (strArr == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        if (i < 0) {
            throw new IllegalArgumentException("pickedIndex should not be negative, now pickedIndex is " + i);
        }
        updateContent(strArr);
        updateMaxWHOfDisplayedValues(true);
        updateNotWrapXLimit();
        updateValue();
        this.mPrevPickedIndex = this.mMinShowIndex + i;
        correctPositionByDefaultValue(i, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        if (z) {
            this.mHandlerInNewThread.sendMessageDelayed(getMsg(1), 0L);
            postInvalidate();
        }
    }

    public void setDisplayedValues(String[] strArr, boolean z) {
        setDisplayedValuesAndPickedIndex(strArr, 0, z);
    }

    public void setDisplayedValues(String[] strArr) {
        stopRefreshing();
        stopScrolling();
        if (strArr == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        boolean z = true;
        if ((this.mMaxValue - this.mMinValue) + 1 > strArr.length) {
            throw new IllegalArgumentException("mMaxValue - mMinValue + 1 should not be greater than mDisplayedValues.length, now ((mMaxValue - mMinValue + 1) is " + ((this.mMaxValue - this.mMinValue) + 1) + " newDisplayedValues.length is " + strArr.length + ", you need to set MaxValue and MinValue before setDisplayedValues(String[])");
        }
        updateContent(strArr);
        updateMaxWHOfDisplayedValues(true);
        this.mPrevPickedIndex = this.mMinShowIndex + 0;
        if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
            z = false;
        }
        correctPositionByDefaultValue(0, z);
        postInvalidate();
        this.mHandlerInMainThread.sendEmptyMessage(3);
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setWrapSelectorWheel(boolean z) {
        if (this.mWrapSelectorWheel != z) {
            if (!z) {
                if (this.mScrollState == 0) {
                    internalSetWrapToLinear();
                    return;
                } else {
                    this.mPendingWrapToLinear = true;
                    return;
                }
            }
            this.mWrapSelectorWheel = z;
            updateWrapStateByContent();
            postInvalidate();
        }
    }

    public void smoothScrollToValue(int i) {
        smoothScrollToValue(getValue(), i, true);
    }

    public void smoothScrollToValue(int i, boolean z) {
        String str = TAG;
        Log.d(str, hashCode() + ":toValue:" + i + ",mItemWidth:" + this.mItemWidth);
        if (this.mItemWidth <= 0) {
            return;
        }
        smoothScrollToValue(getValue(), i, z);
    }

    public void smoothScrollToValue(int i, int i2) {
        smoothScrollToValue(i, i2, true);
    }

    public void smoothScrollToValue(int i, int i2, boolean z) {
        int i3;
        boolean z2 = true;
        int refineValueByLimit = refineValueByLimit(i, this.mMinValue, this.mMaxValue, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        int i4 = this.mMinValue;
        int i5 = this.mMaxValue;
        if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
            z2 = false;
        }
        int refineValueByLimit2 = refineValueByLimit(i2, i4, i5, z2);
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            i3 = refineValueByLimit2 - refineValueByLimit;
            int oneRecycleSize = getOneRecycleSize() / 2;
            if (i3 < (-oneRecycleSize) || oneRecycleSize < i3) {
                int oneRecycleSize2 = getOneRecycleSize();
                i3 = i3 > 0 ? i3 - oneRecycleSize2 : i3 + oneRecycleSize2;
            }
        } else {
            i3 = refineValueByLimit2 - refineValueByLimit;
        }
        setValue(refineValueByLimit);
        if (refineValueByLimit == refineValueByLimit2) {
            return;
        }
        scrollByIndexSmoothlx(i3, z);
    }

    public void refreshByNewDisplayedValues(String[] strArr) {
        int minValue = getMinValue();
        int maxValue = (getMaxValue() - minValue) + 1;
        int length = strArr.length - 1;
        if ((length - minValue) + 1 > maxValue) {
            setDisplayedValues(strArr);
            setMaxValue(length);
            return;
        }
        setMaxValue(length);
        setDisplayedValues(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void respondPickedValueChanged(int i, int i2, Object obj) {
        onScrollStateChange(0);
        if (i != i2 && (!(obj instanceof Boolean) || ((Boolean) obj).booleanValue())) {
            OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
            if (onValueChangeListener != null) {
                int i3 = this.mMinValue;
                onValueChangeListener.onValueChange(this, i + i3, i3 + i2);
            }
            OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw = this.mOnValueChangeListenerRaw;
            if (onValueChangeListenerRelativeToRaw != null) {
                onValueChangeListenerRelativeToRaw.onValueChangeRelativeToRaw(this, i, i2, this.mDisplayedValues);
            }
        }
        this.mPrevPickedIndex = i2;
        if (this.mPendingWrapToLinear) {
            this.mPendingWrapToLinear = false;
            internalSetWrapToLinear();
        }
    }

    private void scrollByIndexSmoothlx(int i) {
        scrollByIndexSmoothlx(i, true);
    }

    private void scrollByIndexSmoothlx(int i, boolean z) {
        int pickedIndexRelativeToRaw;
        int pickedIndexRelativeToRaw2;
        int i2;
        int i3;
        if ((!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) && ((pickedIndexRelativeToRaw2 = (pickedIndexRelativeToRaw = getPickedIndexRelativeToRaw()) + i) > (i2 = this.mMaxShowIndex) || pickedIndexRelativeToRaw2 < (i2 = this.mMinShowIndex))) {
            i = i2 - pickedIndexRelativeToRaw;
        }
        int i4 = this.mCurrDrawFirstItemX;
        int i5 = this.mItemWidth;
        if (i4 < (-i5) / 2) {
            int i6 = i5 + i4;
            int i7 = (int) (((i4 + i5) * 300.0d) / i5);
            i3 = i < 0 ? (-i7) - (i * 300) : i7 + (i * 300);
            i4 = i6;
        } else {
            int i8 = (int) (((-i4) * 300.0d) / i5);
            i3 = i < 0 ? i8 - (i * 300) : i8 + (i * 300);
        }
        int i9 = i4 + (i * this.mItemWidth);
        int i10 = i3 >= 300 ? i3 : 300;
        if (i10 > 600) {
            i10 = 600;
        }
        this.mScroller.startScroll(this.mCurrDrawGlobalX, 0, i9, 0, i10);
        if (z) {
            this.mHandlerInNewThread.sendMessageDelayed(getMsg(1), i10 / 4);
        } else {
            this.mHandlerInNewThread.sendMessageDelayed(getMsg(1, 0, 0, Boolean.valueOf(z)), i10 / 4);
        }
        postInvalidate();
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMinValue(int i) {
        this.mMinValue = i;
        this.mMinShowIndex = 0;
        updateNotWrapXLimit();
    }

    public void setMaxValue(int i) {
        String[] strArr = this.mDisplayedValues;
        if (strArr == null) {
            throw new NullPointerException("mDisplayedValues should not be null");
        }
        int i2 = this.mMinValue;
        if ((i - i2) + 1 > strArr.length) {
            throw new IllegalArgumentException("(maxValue - mMinValue + 1) should not be greater than mDisplayedValues.length now  (maxValue - mMinValue + 1) is " + ((i - this.mMinValue) + 1) + " and mDisplayedValues.length is " + this.mDisplayedValues.length);
        }
        this.mMaxValue = i;
        int i3 = this.mMinShowIndex;
        this.mMaxShowIndex = (this.mMaxValue - i2) + i3;
        setMinAndMaxShowIndex(i3, this.mMaxShowIndex);
        updateNotWrapXLimit();
    }

    public void setValue(int i) {
        int i2 = this.mMinValue;
        if (i < i2) {
            throw new IllegalArgumentException("should not set a value less than mMinValue, value is " + i);
        } else if (i > this.mMaxValue) {
            throw new IllegalArgumentException("should not set a value greater than mMaxValue, value is " + i);
        } else {
            setPickedIndexRelativeToRaw(i - i2);
        }
    }

    public int getValue() {
        return getPickedIndexRelativeToRaw() + this.mMinValue;
    }

    public String getContentByCurrValue() {
        return this.mDisplayedValues[getValue() - this.mMinValue];
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public boolean getWrapSelectorWheelAbsolutely() {
        return this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck;
    }

    public void setHintText(String str) {
        if (isStringEqual(this.mHintText, str)) {
            return;
        }
        this.mHintText = str;
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        this.mHandlerInMainThread.sendEmptyMessage(3);
    }

    public void setPickedIndexRelativeToMin(int i) {
        if (i < 0 || i >= getOneRecycleSize()) {
            return;
        }
        this.mPrevPickedIndex = this.mMinShowIndex + i;
        correctPositionByDefaultValue(i, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public void setNormalTextColor(int i) {
        int color = getResources().getColor(i, getContext().getTheme());
        if (this.mTextColorNormal == color) {
            return;
        }
        this.mTextColorNormal = color;
        postInvalidate();
    }

    public void setSelectedTextColor(int i) {
        int color = getResources().getColor(i, getContext().getTheme());
        if (this.mTextColorSelected == color) {
            return;
        }
        this.mTextColorSelected = color;
        postInvalidate();
    }

    public void setHintTextColor(int i) {
        int color = getResources().getColor(i, getContext().getTheme());
        if (this.mTextColorHint == color) {
            return;
        }
        this.mTextColorHint = color;
        this.mPaintHint.setColor(this.mTextColorHint);
        postInvalidate();
    }

    public void setDividerColor(int i) {
        int color = getResources().getColor(i, getContext().getTheme());
        if (this.mDividerColor == color) {
            return;
        }
        this.mDividerColor = color;
        this.mPaintDivider.setColor(this.mDividerColor);
        postInvalidate();
    }

    public void setPickedIndexRelativeToRaw(int i) {
        String str = TAG;
        Log.d(str, hashCode() + ":setPickedIndexRelativeToRaw:" + i);
        int i2 = this.mMinShowIndex;
        if (i2 <= -1 || i2 > i || i > this.mMaxShowIndex) {
            return;
        }
        this.mPrevPickedIndex = i;
        correctPositionByDefaultValue(i - i2, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public int getPickedIndexRelativeToRaw() {
        int i = this.mCurrDrawFirstItemX;
        if (i != 0) {
            int i2 = this.mItemWidth;
            if (i < (-i2) / 2) {
                return getWillPickIndexByGlobalX(this.mCurrDrawGlobalX + i2 + i);
            }
            return getWillPickIndexByGlobalX(this.mCurrDrawGlobalX + i);
        }
        return getWillPickIndexByGlobalX(this.mCurrDrawGlobalX);
    }

    public void setMinAndMaxShowIndex(int i, int i2) {
        setMinAndMaxShowIndex(i, i2, true);
    }

    public void setMinAndMaxShowIndex(int i, int i2, boolean z) {
        if (i > i2) {
            throw new IllegalArgumentException("minShowIndex should be less than maxShowIndex, minShowIndex is " + i + ", maxShowIndex is " + i2 + ".");
        }
        String[] strArr = this.mDisplayedValues;
        if (strArr == null) {
            throw new IllegalArgumentException("mDisplayedValues should not be null, you need to set mDisplayedValues first.");
        }
        if (i < 0) {
            throw new IllegalArgumentException("minShowIndex should not be less than 0, now minShowIndex is " + i);
        }
        boolean z2 = true;
        if (i > strArr.length - 1) {
            throw new IllegalArgumentException("minShowIndex should not be greater than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " minShowIndex is " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("maxShowIndex should not be less than 0, now maxShowIndex is " + i2);
        } else if (i2 > strArr.length - 1) {
            throw new IllegalArgumentException("maxShowIndex should not be greater than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " maxShowIndex is " + i2);
        } else {
            this.mMinShowIndex = i;
            this.mMaxShowIndex = i2;
            if (z) {
                this.mPrevPickedIndex = this.mMinShowIndex + 0;
                if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
                    z2 = false;
                }
                correctPositionByDefaultValue(0, z2);
                postInvalidate();
            }
        }
    }

    public void setFriction(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("you should set a a positive float friction, now friction is " + f);
        }
        this.mFriction = (float) (ViewConfiguration.getScrollFriction() / f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollStateChange(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, i);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setOnValueChangedListenerRelativeToRaw(OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw) {
        this.mOnValueChangeListenerRaw = onValueChangeListenerRelativeToRaw;
    }

    public void setOnValueChangeListenerInScrolling(OnValueChangeListenerInScrolling onValueChangeListenerInScrolling) {
        this.mOnValueChangeListenerInScrolling = onValueChangeListenerInScrolling;
    }

    public void setContentTextTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
    }

    public void setAttachTextTypefce(Typeface typeface) {
        this.mPaintTextAttach.setTypeface(typeface);
    }

    public void setHintTextTypeface(Typeface typeface) {
        this.mPaintHint.setTypeface(typeface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWillPickIndexByGlobalX(int i) {
        int i2 = this.mItemWidth;
        boolean z = false;
        if (i2 == 0) {
            return 0;
        }
        int i3 = (i / i2) + (this.mShowCount / 2);
        int oneRecycleSize = getOneRecycleSize();
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            z = true;
        }
        int indexByRawIndex = getIndexByRawIndex(i3, oneRecycleSize, z);
        if (indexByRawIndex >= 0 && indexByRawIndex < getOneRecycleSize()) {
            return indexByRawIndex + this.mMinShowIndex;
        }
        throw new IllegalArgumentException("getWillPickIndexByGlobalX illegal index : " + indexByRawIndex + " getOneRecycleSize() : " + getOneRecycleSize() + " mWrapSelectorWheel : " + this.mWrapSelectorWheel);
    }

    private int getIndexByRawIndex(int i, int i2, boolean z) {
        if (i2 <= 0) {
            return 0;
        }
        if (z) {
            int i3 = i % i2;
            return i3 < 0 ? i3 + i2 : i3;
        }
        return i;
    }

    private void internalSetWrapToLinear() {
        correctPositionByDefaultValue(getPickedIndexRelativeToRaw() - this.mMinShowIndex, false);
        this.mWrapSelectorWheel = false;
        postInvalidate();
    }

    private void updateDividerAttr() {
        int i;
        int i2 = this.mShowCount;
        this.mDividerIndex0 = i2 / 2;
        this.mDividerIndex1 = this.mDividerIndex0 + 1;
        int i3 = this.mViewWidth;
        this.dividerX0 = (float) (((i * i3) * 1.0d) / i2);
        this.dividerX1 = (float) (((this.mDividerIndex1 * i3) * 1.0d) / i2);
        if (this.mDividerMarginT < 0) {
            this.mDividerMarginT = 0;
        }
        if (this.mDividerMarginB < 0) {
            this.mDividerMarginB = 0;
        }
        if (this.mDividerMarginT + this.mDividerMarginB != 0 && getPaddingTop() + this.mDividerMarginT >= (this.mViewHeight - getPaddingBottom()) - this.mDividerMarginB) {
            int paddingTop = getPaddingTop() + this.mDividerMarginT + getPaddingBottom();
            int i4 = this.mDividerMarginB;
            int i5 = (paddingTop + i4) - this.mViewHeight;
            int i6 = this.mDividerMarginT;
            double d = i5;
            this.mDividerMarginT = (int) (i6 - ((i6 * d) / (i6 + i4)));
            this.mDividerMarginB = (int) (i4 - ((d * i4) / (this.mDividerMarginT + i4)));
        }
    }

    private void updateFontAttr() {
        int i = this.mTextSizeNormal;
        int i2 = this.mItemWidth;
        if (i > i2) {
            this.mTextSizeNormal = i2;
        }
        int i3 = this.mTextSizeSelected;
        int i4 = this.mItemWidth;
        if (i3 > i4) {
            this.mTextSizeSelected = i4;
        }
        Paint paint = this.mPaintHint;
        if (paint == null) {
            throw new IllegalArgumentException("mPaintHint should not be null.");
        }
        paint.setTextSize(this.mTextSizeHint);
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        TextPaint textPaint = this.mPaintText;
        if (textPaint == null) {
            throw new IllegalArgumentException("mPaintText should not be null.");
        }
        textPaint.setTextSize(this.mTextSizeSelected);
        this.mPaintTextAttach.setTextSize(this.mTextSizeSelected);
        this.mTextSizeSelectedCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
        this.mPaintText.setTextSize(this.mTextSizeNormal);
        this.mPaintTextAttach.setTextSize(this.mTextSizeSelected);
        this.mTextSizeNormalCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
    }

    private void updateNotWrapXLimit() {
        this.mNotWrapLimitXTop = 0;
        this.mNotWrapLimitXBottom = (-this.mShowCount) * this.mItemWidth;
        if (this.mDisplayedValues != null) {
            int oneRecycleSize = getOneRecycleSize();
            int i = this.mShowCount;
            int i2 = this.mItemWidth;
            this.mNotWrapLimitXTop = ((oneRecycleSize - (i / 2)) - 1) * i2;
            this.mNotWrapLimitXBottom = (-(i / 2)) * i2;
        }
    }

    private int limitX(int i) {
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            return i;
        }
        int i2 = this.mNotWrapLimitXBottom;
        if (i < i2) {
            return i2;
        }
        int i3 = this.mNotWrapLimitXTop;
        return i > i3 ? i3 : i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006a, code lost:
        if (r1 < r4) goto L29;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.HorizontalValuePickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void click(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        for (int i = 0; i < this.mShowCount; i++) {
            int i2 = this.mItemWidth;
            if (i2 * i <= x && x < i2 * (i + 1)) {
                clickItem(i);
                return;
            }
        }
    }

    private void clickItem(int i) {
        int i2;
        if (i < 0 || i >= (i2 = this.mShowCount)) {
            return;
        }
        scrollByIndexSmoothlx(i - (i2 / 2));
    }

    private float getTextCenterYOffset(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0.0f;
        }
        return (float) (Math.abs(fontMetrics.bottom - fontMetrics.top) / 2.0d);
    }

    private void correctPositionByDefaultValue(int i, boolean z) {
        this.mCurrDrawFirstItemIndex = i - ((this.mShowCount - 1) / 2);
        this.mCurrDrawFirstItemIndex = getIndexByRawIndex(this.mCurrDrawFirstItemIndex, getOneRecycleSize(), z);
        int i2 = this.mItemWidth;
        if (i2 == 0) {
            this.mCurrentItemIndexEffect = true;
            return;
        }
        int i3 = this.mCurrDrawFirstItemIndex;
        this.mCurrDrawGlobalX = i2 * i3;
        this.mInScrollingPickedOldValue = i3 + (this.mShowCount / 2);
        this.mInScrollingPickedOldValue %= getOneRecycleSize();
        int i4 = this.mInScrollingPickedOldValue;
        if (i4 < 0) {
            this.mInScrollingPickedOldValue = i4 + getOneRecycleSize();
        }
        this.mInScrollingPickedNewValue = this.mInScrollingPickedOldValue;
        calculateFirstItemParameterByGlobalX();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mItemWidth != 0 && this.mScroller.computeScrollOffset()) {
            this.mCurrDrawGlobalX = this.mScroller.getCurrX();
            calculateFirstItemParameterByGlobalX();
            postInvalidate();
        }
    }

    private void calculateFirstItemParameterByGlobalX() {
        this.mCurrDrawFirstItemIndex = (int) Math.floor(this.mCurrDrawGlobalX / this.mItemWidth);
        int i = this.mCurrDrawGlobalX;
        int i2 = this.mCurrDrawFirstItemIndex;
        int i3 = this.mItemWidth;
        this.mCurrDrawFirstItemX = -(i - (i2 * i3));
        if (this.mOnValueChangeListenerInScrolling != null) {
            if ((-this.mCurrDrawFirstItemX) > i3 / 2) {
                this.mInScrollingPickedNewValue = i2 + 1 + (this.mShowCount / 2);
            } else {
                this.mInScrollingPickedNewValue = i2 + (this.mShowCount / 2);
            }
            this.mInScrollingPickedNewValue %= getOneRecycleSize();
            int i4 = this.mInScrollingPickedNewValue;
            if (i4 < 0) {
                this.mInScrollingPickedNewValue = i4 + getOneRecycleSize();
            }
            int i5 = this.mInScrollingPickedOldValue;
            int i6 = this.mInScrollingPickedNewValue;
            if (i5 != i6) {
                respondPickedValueChangedInScrolling(i5, i6);
            }
            this.mInScrollingPickedOldValue = this.mInScrollingPickedNewValue;
        }
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void updateMaxWHOfDisplayedValues(boolean z) {
        updateMaxWidthOfDisplayedValues();
        updateMaxHeightOfDisplayedValues();
        if (z) {
            if (this.mSpecModeW == Integer.MIN_VALUE || this.mSpecModeH == Integer.MIN_VALUE) {
                this.mHandlerInMainThread.sendEmptyMessage(3);
            }
        }
    }

    private int measureWidth(int i) {
        int mode = View.MeasureSpec.getMode(i);
        this.mSpecModeW = mode;
        int size = View.MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int max = Math.max(this.mMaxWidthOfAlterArrayWithMeasureHint, Math.max(this.mMaxWidthOfDisplayedValues, this.mMaxWidthOfAlterArrayWithoutMeasureHint) + (((Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) != 0 ? this.mMarginStartOfHint : 0) + Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) + (Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) == 0 ? 0 : this.mMarginEndOfHint) + (this.mItemPaddingHorizontal * 2)) * 2)) + getPaddingLeft() + getPaddingRight();
        return mode == Integer.MIN_VALUE ? Math.min(max, size) : max;
    }

    private int measureHeight(int i) {
        int mode = View.MeasureSpec.getMode(i);
        this.mSpecModeH = mode;
        int size = View.MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = (this.mShowCount * (this.mMaxHeightOfDisplayedValues + (this.mItemPaddingVertical * 2))) + getPaddingTop() + getPaddingBottom();
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawContent(canvas);
        drawLine(canvas);
        drawHint(canvas);
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02dd A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawContent(android.graphics.Canvas r29) {
        /*
            Method dump skipped, instructions count: 740
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.HorizontalValuePickerView.drawContent(android.graphics.Canvas):void");
    }

    public void setShadowDirection(int i) {
        this.mShadowDirection = i;
        invalidate();
    }

    public int getShadowTextColor(int i) {
        return Color.argb((int) (Color.alpha(i) * 0.06d), Color.red(i), Color.green(i), Color.blue(i));
    }

    private TextUtils.TruncateAt getEllipsizeType() {
        char c;
        String str = this.mTextEllipsize;
        int hashCode = str.hashCode();
        if (hashCode == -1074341483) {
            if (str.equals(TEXT_ELLIPSIZE_MIDDLE)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 100571) {
            if (hashCode == 109757538 && str.equals("start")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("end")) {
                c = 2;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c != 1) {
                if (c == 2) {
                    return TextUtils.TruncateAt.END;
                }
                throw new IllegalArgumentException("Illegal text ellipsize type.");
            }
            return TextUtils.TruncateAt.MIDDLE;
        }
        return TextUtils.TruncateAt.START;
    }

    private void drawLine(Canvas canvas) {
        if (this.mShowDivider) {
            canvas.drawLine(this.dividerX0, (float) (getPaddingTop() + this.mDividerMarginT), this.dividerX0, (float) ((this.mViewWidth - getPaddingRight()) - this.mDividerMarginB), this.mPaintDivider);
            canvas.drawLine(this.dividerX1, (float) (getPaddingLeft() + this.mDividerMarginT), this.dividerX1, (float) ((this.mViewWidth - getPaddingRight()) - this.mDividerMarginB), this.mPaintDivider);
        }
    }

    private void drawHint(Canvas canvas) {
        if (TextUtils.isEmpty(this.mHintText)) {
            return;
        }
        canvas.drawText(this.mHintText, (float) (((this.dividerX0 + this.dividerX1) / 2.0d) + this.mTextSizeHintCenterYOffset), (float) (this.mViewCenterY + ((this.mMaxWidthOfDisplayedValues + this.mWidthOfHintText) / 2.0d) + this.mMarginStartOfHint), this.mPaintHint);
    }

    private void updateMaxWidthOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxWidthOfDisplayedValues = getMaxWidthOfTextArray(this.mDisplayedValues, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithMeasureHint, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithoutMeasureHint, this.mPaintText);
        this.mPaintText.setTextSize(this.mTextSizeHint);
        this.mWidthOfAlterHint = getTextWidth(this.mAlterHint, this.mPaintText);
        this.mPaintText.setTextSize(textSize);
    }

    private int getMaxWidthOfTextArray(CharSequence[] charSequenceArr, Paint paint) {
        if (charSequenceArr == null) {
            return 0;
        }
        int i = 0;
        for (CharSequence charSequence : charSequenceArr) {
            if (charSequence != null) {
                i = Math.max(getTextWidth(charSequence, paint), i);
            }
        }
        return i;
    }

    private int getTextWidth(CharSequence charSequence, Paint paint) {
        if (TextUtils.isEmpty(charSequence)) {
            return 0;
        }
        return (int) (paint.measureText(charSequence.toString()) + 0.5d);
    }

    private void updateMaxHeightOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxHeightOfDisplayedValues = (int) ((this.mPaintText.getFontMetrics().bottom - this.mPaintText.getFontMetrics().top) + 0.5d);
        this.mPaintText.setTextSize(textSize);
    }

    private void updateContentAndIndex(String[] strArr) {
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = strArr.length - 1;
        this.mDisplayedValues = strArr;
        updateWrapStateByContent();
    }

    private void updateContent(String[] strArr) {
        this.mDisplayedValues = strArr;
        updateWrapStateByContent();
    }

    private void updateValue() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = this.mDisplayedValues.length - 1;
    }

    private void updateValueForInit() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        if (this.mMinShowIndex == -1) {
            this.mMinShowIndex = 0;
        }
        if (this.mMaxShowIndex == -1) {
            this.mMaxShowIndex = this.mDisplayedValues.length - 1;
        }
        setMinAndMaxShowIndex(this.mMinShowIndex, this.mMaxShowIndex, false);
    }

    private void inflateDisplayedValuesIfNull() {
        if (this.mDisplayedValues == null) {
            this.mDisplayedValues = new String[1];
            this.mDisplayedValues[0] = "0";
        }
    }

    private void updateWrapStateByContent() {
        this.mWrapSelectorWheelCheck = this.mDisplayedValues.length > this.mShowCount;
    }

    private int refineValueByLimit(int i, int i2, int i3, boolean z) {
        if (!z) {
            return i > i3 ? i3 : i < i2 ? i2 : i;
        } else if (i > i3) {
            return (((i - i3) % getOneRecycleSize()) + i2) - 1;
        } else {
            return i < i2 ? ((i - i2) % getOneRecycleSize()) + i3 + 1 : i;
        }
    }

    private void stopRefreshing() {
        Handler handler = this.mHandlerInNewThread;
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    public void stopScrolling() {
        ScrollerCompat scrollerCompat = this.mScroller;
        if (scrollerCompat == null || scrollerCompat.isFinished()) {
            return;
        }
        ScrollerCompat scrollerCompat2 = this.mScroller;
        scrollerCompat2.startScroll(0, scrollerCompat2.getCurrY(), 0, 0, 1);
        this.mScroller.abortAnimation();
        postInvalidate();
    }

    public void stopScrollingAndCorrectPosition() {
        stopScrolling();
        Handler handler = this.mHandlerInNewThread;
        if (handler != null) {
            handler.sendMessageDelayed(getMsg(1), 0L);
        }
    }

    private Message getMsg(int i) {
        return getMsg(i, 0, 0, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Message getMsg(int i, int i2, int i3, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        return obtain;
    }

    private boolean isStringEqual(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    private int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5d);
    }

    private int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5d);
    }

    private String[] convertCharSequenceArrayToStringArray(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null) {
            return null;
        }
        String[] strArr = new String[charSequenceArr.length];
        for (int i = 0; i < charSequenceArr.length; i++) {
            strArr[i] = charSequenceArr[i].toString();
        }
        return strArr;
    }
}
