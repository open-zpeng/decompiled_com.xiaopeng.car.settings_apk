package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.math.MathUtils;
import androidx.core.view.InputDeviceCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class EqualizerProgressView extends View {
    private List<Item> mItems;
    private IEqualizerProgressChangeListener mListener;
    private LottieComposition mLottieComposition;

    /* loaded from: classes.dex */
    public interface IEqualizerProgressChangeListener {
        void onEqualizerChange(int i, int i2, boolean z);
    }

    public EqualizerProgressView(Context context) {
        super(context);
        this.mItems = new ArrayList();
        init(context, null);
    }

    public EqualizerProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList();
        init(context, attributeSet);
    }

    public EqualizerProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mItems = new ArrayList();
        init(context, attributeSet);
    }

    public void setListener(IEqualizerProgressChangeListener iEqualizerProgressChangeListener) {
        this.mListener = iEqualizerProgressChangeListener;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        for (Item item : this.mItems) {
            item.reloadUi(getContext());
        }
        postInvalidate();
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Equalizer);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_line_wide_width, 0);
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_line_wide_height, 0);
            int color = obtainStyledAttributes.getColor(R.styleable.Equalizer_se_eq_line_wide_color_day, 0);
            int color2 = obtainStyledAttributes.getColor(R.styleable.Equalizer_se_eq_line_wide_color_night, 0);
            int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_line_thin_width, 0);
            int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_line_thin_height, 0);
            int color3 = obtainStyledAttributes.getColor(R.styleable.Equalizer_se_eq_line_thin_color_day, 0);
            int color4 = obtainStyledAttributes.getColor(R.styleable.Equalizer_se_eq_line_thin_color_night, 0);
            int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_line_offset, 0);
            int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_progressbar_bg_width, 0);
            int dimensionPixelSize7 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_progressbar_bg_height, 0);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.Equalizer_se_eq_progressbar_bg_drawable, 0);
            int dimensionPixelSize8 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_progressbar_indicator_width, 0);
            int dimensionPixelSize9 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_progressbar_indicator_height, 0);
            int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.Equalizer_se_eq_progressbar_indicator_drawable, 0);
            int i = obtainStyledAttributes.getInt(R.styleable.Equalizer_se_eq_progressbar_value_max, 0);
            int i2 = obtainStyledAttributes.getInt(R.styleable.Equalizer_se_eq_progressbar_selectable_max, 0);
            int i3 = obtainStyledAttributes.getInt(R.styleable.Equalizer_se_eq_progressbar_selectable_min, 0);
            int i4 = obtainStyledAttributes.getInt(R.styleable.Equalizer_se_eq_progressbar_segment_count, 0);
            int dimensionPixelSize10 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_progressbar_touch_compat_size, 0);
            String string = obtainStyledAttributes.getString(R.styleable.Equalizer_se_eq_curve_file_name);
            int dimensionPixelSize11 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_curve_width, 0);
            int dimensionPixelSize12 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Equalizer_se_eq_curve_height, 0);
            String string2 = obtainStyledAttributes.getString(R.styleable.Equalizer_se_eq_offsets);
            if (string2 != null) {
                String[] split = string2.split(";|,|/|\\|");
                int length = split.length;
                int i5 = dimensionPixelSize12;
                int i6 = resourceId;
                int i7 = 0;
                int i8 = 0;
                while (i7 < length) {
                    Integer valueOf = Integer.valueOf(Integer.parseInt(split[i7].trim()));
                    String[] strArr = split;
                    int i9 = length;
                    Item item = new Item();
                    int i10 = i8 + 1;
                    item.mIndex = i8;
                    item.mRulerWideWidth = dimensionPixelSize;
                    item.mRulerWideHeight = dimensionPixelSize2;
                    item.mRulerWideColorDay = color;
                    item.mRulerWideColorNight = color2;
                    item.mRulerThinWidth = dimensionPixelSize3;
                    item.mRulerThinHeight = dimensionPixelSize4;
                    item.mRulerThinColorDay = color3;
                    item.mRulerThinColorNight = color4;
                    item.mRulerMiddleOffset = dimensionPixelSize5;
                    item.mProgressBarWidth = dimensionPixelSize6;
                    item.mProgressBarHeight = dimensionPixelSize7;
                    int i11 = i6;
                    item.mProgressBarResId = i11;
                    item.mIndicatorWidth = dimensionPixelSize8;
                    item.mIndicatorHeight = dimensionPixelSize9;
                    item.mIndicatorResId = resourceId2;
                    item.mValueMax = i;
                    item.mSelectiveMax = i2;
                    item.mSelectiveMin = i3;
                    item.mSegmentCount = i4;
                    item.mTouchCompatSize = dimensionPixelSize10;
                    item.mCurveWidth = dimensionPixelSize11;
                    int i12 = i5;
                    item.mCurveHeight = i12;
                    item.mOffsetX = (int) ((Resources.getSystem().getDisplayMetrics().density * valueOf.intValue()) + 0.5f);
                    item.reloadUi(context);
                    this.mItems.add(item);
                    i7++;
                    dimensionPixelSize = dimensionPixelSize;
                    split = strArr;
                    length = i9;
                    i5 = i12;
                    i6 = i11;
                    i8 = i10;
                }
            }
            obtainStyledAttributes.recycle();
            LottieCompositionFactory.fromAsset(getContext(), string).addListener(new LottieListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$EqualizerProgressView$AbEadfL1hWpz6mFSrQk1NTlLHY8
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    EqualizerProgressView.this.lambda$init$0$EqualizerProgressView((LottieComposition) obj);
                }
            }).addFailureListener(new LottieListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$EqualizerProgressView$GCUVdqPdDM6QmcS4n1RVCB4Oeu4
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    EqualizerProgressView.this.lambda$init$1$EqualizerProgressView((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$init$0$EqualizerProgressView(LottieComposition lottieComposition) {
        for (Item item : this.mItems) {
            item.mCurveDrawable.setComposition(lottieComposition);
        }
        postInvalidate();
    }

    public /* synthetic */ void lambda$init$1$EqualizerProgressView(Throwable th) {
        Logs.log(getClass().getName(), "");
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Item item : this.mItems) {
            item.onDraw(canvas);
        }
    }

    public void setProgress(int i, int i2) {
        setProgress(i, i2, false);
    }

    public void setProgress(int i, int i2, boolean z) {
        setProgress(i, i2, z, false);
    }

    public void setProgress(int i, int i2, boolean z, boolean z2) {
        for (Item item : this.mItems) {
            if (item.mIndex == i) {
                item.setProgress(i2, z, z2);
                return;
            }
        }
    }

    public void submitChange() {
        postInvalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        for (Item item : this.mItems) {
            if (item.onTouch(motionEvent)) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Item {
        private LottieDrawable mCurveDrawable;
        private int mCurveHeight;
        private int mCurveWidth;
        private boolean mHandleEvent;
        private int mIndex;
        private Drawable mIndicator;
        private int mIndicatorHeight;
        private int mIndicatorResId;
        private int mIndicatorWidth;
        private boolean mNightMode;
        private int mOffsetX;
        private Paint mPaintThin;
        private Paint mPaintWide;
        private int mProgress;
        private Drawable mProgressBar;
        private int mProgressBarHeight;
        private int mProgressBarResId;
        private int mProgressBarWidth;
        private int mRulerMiddleOffset;
        private int mRulerThinColorDay;
        private int mRulerThinColorNight;
        private int mRulerThinHeight;
        private int mRulerThinWidth;
        private int mRulerWideColorDay;
        private int mRulerWideColorNight;
        private int mRulerWideHeight;
        private int mRulerWideWidth;
        private int mSegmentCount;
        private int mSelectiveMax;
        private int mSelectiveMin;
        private int mTouchCompatSize;
        private int mValueMax;

        private Item() {
            this.mRulerWideColorDay = SupportMenu.CATEGORY_MASK;
            this.mRulerWideColorNight = SupportMenu.CATEGORY_MASK;
            this.mRulerThinColorDay = InputDeviceCompat.SOURCE_ANY;
            this.mRulerThinColorNight = InputDeviceCompat.SOURCE_ANY;
            this.mPaintWide = new Paint();
            this.mPaintThin = new Paint();
            this.mSegmentCount = 10;
            this.mValueMax = 10;
            this.mSelectiveMax = 9;
            this.mSelectiveMin = -9;
            this.mCurveDrawable = new LottieDrawable();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadUi(Context context) {
            this.mNightMode = (context.getResources().getConfiguration().uiMode & 48) == 32;
            this.mPaintWide.setColor(this.mNightMode ? this.mRulerWideColorNight : this.mRulerWideColorDay);
            this.mPaintThin.setColor(this.mNightMode ? this.mRulerThinColorNight : this.mRulerThinColorDay);
            this.mProgressBar = context.getDrawable(this.mProgressBarResId);
            this.mIndicator = context.getDrawable(this.mIndicatorResId);
        }

        void onDraw(Canvas canvas) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            int i9;
            int i10;
            EqualizerProgressView.this.getWidth();
            int height = EqualizerProgressView.this.getHeight();
            int i11 = this.mOffsetX;
            int i12 = this.mProgressBarWidth;
            int i13 = i11 - (i12 >> 1);
            int i14 = this.mProgressBarHeight;
            int i15 = (height - i14) >> 1;
            Drawable drawable = this.mProgressBar;
            if (drawable != null) {
                drawable.setBounds(0, 0, i12, i14);
                canvas.save();
                canvas.translate(i13, i15);
                this.mProgressBar.draw(canvas);
                canvas.restore();
            }
            int i16 = this.mOffsetX - this.mRulerMiddleOffset;
            int i17 = this.mSegmentCount + 1;
            for (int i18 = 0; i18 < i17; i18++) {
                if (i18 % 5 == 0) {
                    canvas.drawRect(i16 - (this.mRulerWideWidth >> 1), ((int) (i15 + (this.mProgressBarHeight * (i18 / (i17 - 1))))) - (this.mRulerWideHeight >> 1), i7 + i8, i9 + i10, this.mPaintWide);
                } else {
                    canvas.drawRect(i16 - (this.mRulerThinWidth >> 1), ((int) (i15 + (this.mProgressBarHeight * (i18 / (i17 - 1))))) - (this.mRulerThinHeight >> 1), i3 + i4, i5 + i6, this.mPaintThin);
                }
            }
            if (this.mCurveDrawable.getComposition() != null) {
                int i19 = this.mOffsetX;
                int i20 = this.mCurveWidth;
                int i21 = i19 - (i20 >> 1);
                this.mCurveDrawable.setBounds(0, 0, i20, this.mCurveHeight);
                this.mCurveDrawable.setProgress(1.0f - ((i2 - this.mProgress) / (this.mValueMax * 2.0f)));
                canvas.save();
                canvas.translate(i21, (height - i) >> 1);
                this.mCurveDrawable.draw(canvas);
                canvas.restore();
            }
            Drawable drawable2 = this.mIndicator;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, this.mIndicatorWidth, this.mIndicatorHeight);
                canvas.save();
                int i22 = this.mProgressBarHeight;
                canvas.translate(this.mOffsetX - (this.mIndicatorWidth >> 1), ((i15 + (i22 >> 1)) - ((this.mProgress / this.mValueMax) * (i22 >> 1))) - (this.mIndicatorHeight >> 1));
                this.mIndicator.draw(canvas);
                canvas.restore();
            }
        }

        public int getIndicatorCenterX() {
            return this.mOffsetX;
        }

        public int getIndicatorCenterY() {
            int height = EqualizerProgressView.this.getHeight();
            int i = this.mProgressBarHeight;
            return ((height - i) >> 1) + (i >> 1) + ((this.mProgress / this.mValueMax) * (i >> 1));
        }

        private void updateDrawableStatus(boolean z) {
            Drawable drawable = this.mIndicator;
            if (drawable != null) {
                drawable.setState(z ? new int[]{16842919} : StateSet.NOTHING);
            }
        }

        public boolean onTouch(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            boolean z = false;
            if (action == 0) {
                int indicatorCenterX = getIndicatorCenterX();
                if (motionEvent.getX() >= (indicatorCenterX - (this.mIndicatorWidth >> 1)) - this.mTouchCompatSize && motionEvent.getX() <= indicatorCenterX + (this.mIndicatorWidth >> 1) + this.mTouchCompatSize) {
                    z = true;
                }
                this.mHandleEvent = z;
                if (this.mHandleEvent) {
                    updateDrawableStatus(true);
                }
            } else if ((action == 1 || action == 4) && this.mHandleEvent) {
                updateDrawableStatus(false);
            }
            if (this.mHandleEvent) {
                setProgressHint(motionEvent);
                EqualizerProgressView.this.postInvalidate();
            }
            return this.mHandleEvent;
        }

        public void setProgress(int i, boolean z, boolean z2) {
            IEqualizerProgressChangeListener iEqualizerProgressChangeListener;
            int clamp = MathUtils.clamp(i, this.mSelectiveMin, this.mSelectiveMax);
            if (clamp != this.mProgress) {
                this.mProgress = clamp;
                if (!z || (iEqualizerProgressChangeListener = EqualizerProgressView.this.mListener) == null) {
                    return;
                }
                iEqualizerProgressChangeListener.onEqualizerChange(this.mIndex, clamp, z2);
            }
        }

        private void setProgressHint(MotionEvent motionEvent) {
            setProgress(Math.round((((EqualizerProgressView.this.getHeight() >> 1) - ((int) motionEvent.getY())) / (this.mProgressBarHeight >> 1)) * this.mValueMax), true, true);
        }
    }
}
