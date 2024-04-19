package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.PathParser;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.libtheme.ThemeViewModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class SoundFieldView extends View {
    private static final int ALPHA_DISTANCE = 4;
    private static final int BREATHER_AlPHA_MAX = 255;
    private static final int BREATHER_AlPHA_MIN = 180;
    private static final int BREATHER_WIDTH_MAX = 10;
    private static final int BREATHER_WIDTH_MIN = 5;
    private static final int CIRCLE_COUNT = 7;
    public static final int CLICK_PADDING = 20;
    private static final int DEFUALT_DIAMETER = 300;
    private static final int DELTA = 40;
    private static final int INVALIDATE_TIME = 50;
    public static final int ORIENTATION_X = 0;
    public static final int ORIENTATION_Y = 1;
    private static final int PAINT_ALPHA = 180;
    private static final int PAINT_ALPHA_FIRST = 125;
    private static final double SCALE_DISTANCE = 0.06d;
    private static final String TAG = "SoundEffectsView";
    public static final int TYPE_CLASSICAL = 4;
    private static final String TYPE_CLASSICAL_PATH = "M90.385,47.385C85.68,52.09 79.18,55 72,55L50,55L28,55C20.82,55 14.32,52.09 9.615,47.385C4.91,42.68 2,36.18 2,29C2,21.82 4.91,15.32 9.615,10.615C14.32,5.91 20.82,3 28,3L50,3L72,3C79.18,3 85.68,5.91 90.385,10.615C95.09,15.32 98,21.82 98,29C98,36.18 95.09,42.68 90.385,47.385Z";
    public static final int TYPE_COMMON = 0;
    private static final String TYPE_COMMON_PATH = "M7.082,17.455C10.631,13.636 15.794,10.382 22.043,7.926C29.957,4.816 39.599,3 50,3C61.238,3 71.59,5.119 79.832,8.701C85.531,11.178 90.212,14.346 93.419,18.01C96.322,21.327 98,25.046 98,29C98,32.985 96.296,36.73 93.352,40.066C90.099,43.752 85.352,46.933 79.578,49.408C71.378,52.924 61.123,55 50,55C39.609,55 29.974,53.187 22.065,50.083C15.81,47.627 10.641,44.372 7.088,40.552C3.874,37.095 2,33.18 2,29C2,24.822 3.872,20.91 7.082,17.455Z";
    public static final int TYPE_DISCO = 5;
    private static final String TYPE_DISCO_PATH = "M77.0881,44.7996C68.4529,51.4149 57.8779,55 47,55C36.1218,55 25.5466,51.4154 16.9106,44.8008L5.5969,36.1353C3.6267,34.6261 2.4546,32.4612 2.1523,30.181C1.8501,27.9008 2.4177,25.5052 3.9268,23.5348C4.4077,22.9071 4.9692,22.3455 5.5969,21.8647L16.9106,13.1992C25.5466,6.5846 36.1218,3 47,3C57.8779,3 68.4529,6.5851 77.0881,13.2004L88.3984,21.8652C90.3687,23.3746 91.5405,25.5397 91.8425,27.8199C92.1445,30.1002 91.5767,32.4956 90.0674,34.4658C89.5869,35.0931 89.0259,35.6543 88.3984,36.1348L77.0881,44.7996Z";
    public static final int TYPE_DYNAMIC = 6;
    private static final String TYPE_DYNAMIC_PATH = "M79.2922,47.2043C75.934,50.5161 74.6663,51.5557 73.0897,52.5315C71.6194,53.4414 70.1273,54.0533 68.4415,54.4379C66.6337,54.8501 65.0011,55 60.2847,55L47,55L33.7151,55C28.9988,55 27.3661,54.8501 25.5585,54.4379C23.8727,54.0533 22.3806,53.4414 20.9103,52.5315C19.3337,51.5558 18.066,50.5162 14.7078,47.2046L5.8773,38.4967C3.3212,35.9761 2.0288,32.6581 2.0056,29.3312C1.9823,26.0043 3.2282,22.6685 5.7488,20.1124L15.9957,10.436C19.3618,7.2612 20.6178,6.2688 22.1717,5.3389C23.6196,4.4724 25.0793,3.8927 26.7271,3.5295C28.4955,3.1398 30.0903,3 34.7173,3L47,3L59.2826,3C63.9097,3 65.5044,3.1398 67.2728,3.5295C68.9207,3.8927 70.3804,4.4724 71.8283,5.339C73.3822,6.2689 74.6382,7.2614 78.0043,10.4362L87.9142,19.7833C90.5257,22.2465 91.8915,25.5349 91.9888,28.8605C92.086,32.186 90.9145,35.5487 88.4514,38.1602L79.2922,47.2043Z";
    public static final int TYPE_JAZZ = 2;
    private static final String TYPE_JAZZ_PATH = "M74.717,55L50,55L25.283,55C18.823,55 15.168,54.119 11.906,52.374C8.767,50.696 6.304,48.233 4.626,45.094C2.881,41.832 2,38.177 2,31.717L2,29L2,26.283C2,19.823 2.881,16.168 4.626,12.906C6.304,9.767 8.767,7.304 11.906,5.626C15.168,3.881 18.823,3 25.283,3L50,3L74.717,3C81.177,3 84.832,3.881 88.094,5.626C91.233,7.304 93.696,9.767 95.374,12.906C97.119,16.168 98,19.823 98,26.283L98,29L98,31.717C98,38.177 97.119,41.832 95.374,45.094C93.696,48.233 91.233,50.696 88.094,52.374C84.832,54.119 81.177,55 74.717,55Z";
    public static final int TYPE_ORIGINAL = 11;
    private static final String TYPE_ORIGINAL_PATH = "M13.73,9.704C21.712,6.554 39.536,2 50,2C61.318,2 76.338,6.45 85.118,9.718C90.749,11.813 94.009,13.951 95.812,16.557C98.019,19.749 98,23.55 98,28.35C98,33.083 98.157,36.607 96.032,39.543C94.264,41.987 90.97,43.961 85.136,46.07C76.365,49.24 61.325,54 50,54C38.687,54 21.705,49.236 13.73,46.09C8.427,43.998 5.426,42.035 3.818,39.61C1.859,36.656 2,33.119 2,28.35C2,23.482 1.873,19.64 3.919,16.412C5.537,13.858 8.518,11.76 13.73,9.704Z";
    public static final int TYPE_REAL = 7;
    private static final String TYPE_REAL_PATH = "M14.053,10.473C24.077,4.859 38.276,3 50,3C61.724,3 75.923,4.859 85.947,10.473C89.719,12.584 92.889,15.231 95.032,18.554C96.924,21.487 98,24.946 98,29C98,33.054 96.924,36.513 95.032,39.446C92.889,42.769 89.719,45.416 85.947,47.527C75.923,53.141 61.724,55 50,55C38.276,55 24.077,53.141 14.053,47.527C10.281,45.416 7.111,42.769 4.968,39.446C3.076,36.513 2,33.054 2,29C2,24.946 3.076,21.487 4.968,18.554C7.111,15.231 10.281,12.584 14.053,10.473Z";
    public static final int TYPE_ROCK = 3;
    private static final String TYPE_ROCK_PATH = "M87.9077,36.5613L67.5209,50.0658C64.2059,52.2617 63.0697,52.9243 61.7137,53.5364C60.4584,54.103 59.2548,54.4656 57.8953,54.6864C56.4268,54.9249 55.1135,55 51.1372,55L47,55L42.8617,55C38.8857,55 37.5728,54.925 36.1044,54.6865C34.745,54.4657 33.5415,54.1033 32.2863,53.5369C30.9304,52.9248 29.7943,52.2623 26.4794,50.067L6.0876,36.5618C3.9995,35.1788 2.6697,33.0804 2.2075,30.8057C1.7454,28.531 2.151,26.0801 3.5339,23.9919C4.2054,22.9781 5.0739,22.1097 6.0876,21.4382L26.4794,7.933C29.7943,5.7377 30.9304,5.0752 32.2863,4.4631C33.5415,3.8967 34.745,3.5343 36.1044,3.3135C37.5728,3.075 38.8857,3 42.8617,3L47,3L51.1372,3C55.1135,3 56.4268,3.0751 57.8953,3.3136C59.2548,3.5344 60.4584,3.897 61.7137,4.4636C63.0697,5.0757 64.2059,5.7383 67.5209,7.9342L87.9077,21.4387C89.9956,22.8218 91.3253,24.9203 91.7874,27.1951C92.2493,29.4697 91.8434,31.9208 90.4602,34.0087C89.7891,35.022 88.921,35.8901 87.9077,36.5613Z";
    public static final int TYPE_SITE = 10;
    private static final String TYPE_SITE_PATH = "M79.2944,47.2943C75.9338,50.5718 74.6689,51.5996 73.0979,52.564C71.6331,53.463 70.1489,54.0669 68.4724,54.446C66.6746,54.8527 65.0513,55 60.3572,55L47,55L33.6509,55C28.9543,55 27.3301,54.8523 25.531,54.4451C23.8535,54.0654 22.3684,53.4607 20.9031,52.5603C19.3313,51.5948 18.0662,50.5657 14.7058,47.2843L6.1348,38.9148C3.4653,36.308 2.105,32.8658 2.064,29.408C2.0227,25.9502 3.3008,22.4766 5.9075,19.807C6.0518,19.6594 6.1992,19.5151 6.3501,19.3743L15.9961,10.3612C19.3633,7.2151 20.6167,6.2324 22.1655,5.312C23.6086,4.4546 25.0615,3.8815 26.7014,3.5228C28.4614,3.1378 30.0481,3 34.6565,3L47,3L59.3513,3C63.957,3 65.5427,3.1375 67.302,3.522C68.9407,3.8801 70.3926,4.4524 71.8352,5.3087C73.3835,6.2278 74.6365,7.2092 78.0039,10.3518L87.6653,19.3687C90.3933,21.9146 91.8308,25.3251 91.9502,28.781C92.0696,32.2371 90.8704,35.7385 88.3245,38.4664C88.1799,38.6212 88.032,38.7726 87.8804,38.9204L79.2944,47.2943Z";
    public static final int TYPE_SOFT = 8;
    private static final String TYPE_SOFT_PATH = "M74.664,52.773C58.339,55.552 41.661,55.552 25.336,52.773L21.519,52.123C15.883,51.163 11.003,48.241 7.532,44.13C4.062,40.019 2,34.718 2,29C2,23.282 4.062,17.981 7.532,13.87C11.003,9.759 15.883,6.837 21.519,5.877L25.336,5.227C41.661,2.448 58.339,2.448 74.664,5.227L78.481,5.877C84.117,6.837 88.997,9.759 92.468,13.87C95.938,17.981 98,23.282 98,29C98,34.718 95.938,40.019 92.468,44.13C88.997,48.241 84.117,51.163 78.481,52.123L74.664,52.773Z";
    public static final int TYPE_STRESS = 9;
    private static final String TYPE_STRESS_PATH = "M74.717,55L50,55L25.283,55C18.823,55 15.168,54.119 11.906,52.374C8.767,50.696 6.304,48.233 4.626,45.094C2.881,41.832 2,38.177 2,31.717L2,29L2,26.283C2,19.823 2.881,16.168 4.626,12.906C6.304,9.767 8.767,7.304 11.906,5.626C15.168,3.881 18.823,3 25.283,3L50,3L74.717,3C81.177,3 84.832,3.881 88.094,5.626C91.233,7.304 93.696,9.767 95.374,12.906C97.119,16.168 98,19.823 98,26.283L98,29L98,31.717C98,38.177 97.119,41.832 95.374,45.094C93.696,48.233 91.233,50.696 88.094,52.374C84.832,54.119 81.177,55 74.717,55Z";
    public static final int TYPE_VOICE = 1;
    private static final String TYPE_VOICE_PATH = "M54.2863,54.3101C50.7177,55.0164 47.2764,55.0164 43.7078,54.3102C40.0194,53.5802 36.5807,52.2942 30.1255,48.9398L9.0028,37.9639C6.4717,36.6487 4.3563,34.6553 2.8933,32.2067C2.3032,31.2192 2.0082,30.1096 2.0082,29C2.0082,27.8904 2.3032,26.7808 2.8933,25.7933C4.3563,23.3447 6.4717,21.3513 9.0028,20.0361L30.1255,9.0602C36.5807,5.7058 40.0194,4.4198 43.7078,3.6898C47.2764,2.9836 50.7177,2.9837 54.2863,3.6899C57.9746,4.42 61.4133,5.7062 67.8684,9.0607L88.9882,20.0358C91.5197,21.3513 93.6355,23.3448 95.0992,25.7936C95.6895,26.7809 95.9845,27.8905 95.9845,29C95.9845,30.1095 95.6895,31.2191 95.0992,32.2064C93.6355,34.6552 91.5197,36.6487 88.9882,37.9642L67.8684,48.9393C61.4133,52.2938 57.9746,53.58 54.2863,54.3101Z";
    private static final int Y_DELTA = 0;
    private int mAdsorptionIndex;
    private Point[] mAdsorptionPoint;
    private Bitmap[] mAdsorptionPointBitmap;
    private Bitmap[] mAdsorptionPointBitmapSelected;
    private List<Integer> mAlphas;
    private int mBitmapCount;
    private int mBreatheAlpha;
    private ValueAnimator mBreatheAnimator;
    private Paint mBreathePaint;
    private Paint mBreathePaint2;
    private Path mBreathePath;
    private float mBreatheWidth;
    private Bitmap mButtonPressed;
    private int mButtonX;
    private int mButtonY;
    private int mCenterX;
    private int mCenterY;
    private Path mClassicPath;
    private int mCollisionIndex;
    private Path mCommonPath;
    private Path mDiscoPath;
    private Path mDynamicPath;
    private Handler mHandler;
    private Runnable mInvalidateRunnable;
    private boolean mIsAdsorption;
    private boolean mIsDraggingHalf;
    private boolean mIsMoving;
    private boolean mIsSetRecommendationPoint;
    private Path mJazzPath;
    private int mLastButtonX;
    private int mLastButtonY;
    private int mLastX;
    private int mLastY;
    private Point mLeftBottomPoint;
    private Point mLeftTopPoint;
    Matrix mMatrix;
    private Path mOriginalPath;
    private Path mPath;
    private Camera mPathCamera;
    private Matrix mPathMatrix;
    private int mPointCount;
    PositionChangeListener mPositionChangeListener;
    int mPressDownPoint;
    int mPressUpPoint;
    private ValueAnimator mPressedDisplayAnimator;
    private float mPressedScale;
    private Path mRealPath;
    private Point[] mRecommendationPoint;
    private Bitmap[] mRecommendationPointBitmap;
    private Bitmap[] mRecommendationPointBitmapDisable;
    private Point mRightBottomPoint;
    private Point mRightTopPoint;
    private Path mRockPath;
    private Bitmap[] mScaleButtonPressed;
    private Path mSitePath;
    private Path mSoftPath;
    private Paint mSpreadPaint;
    private Paint mSpreadPaint2;
    private List<Float> mSpreadPath;
    private Path mStressPath;
    private ThemeViewModel mThemeViewModel;
    private int mType;
    private Path mVoicePath;
    RectF rectF1;

    /* loaded from: classes.dex */
    public interface PositionChangeListener {
        void onPositionChange(SoundFieldView soundFieldView, int i, int i2);

        void onPressChange(boolean z);
    }

    public boolean isCollisionWithRect(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i < i5 || i < (i7 + i5) - 40) {
            if (i > i5 || (i + i3) - 40 > i5) {
                if (i2 < i6 || i2 < (i8 + i6) - 40) {
                    return i2 > i6 || (i2 + i4) + (-40) > i6;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public void setHifi() {
    }

    public SoundFieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBitmapCount = 6;
        this.mScaleButtonPressed = new Bitmap[this.mBitmapCount];
        this.mAdsorptionPointBitmap = new Bitmap[4];
        this.mAdsorptionPointBitmapSelected = new Bitmap[4];
        this.mCollisionIndex = 2;
        this.mPressDownPoint = -1;
        this.mPressUpPoint = -1;
        this.mAdsorptionPoint = new Point[]{new Point(264, NfDef.GATT_STATUS_MORE), new Point(597, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(TypedValues.Cycle.TYPE_WAVE_PHASE, 410)};
        this.mAdsorptionIndex = 2;
        this.mIsDraggingHalf = false;
        this.mPointCount = 5;
        this.mIsSetRecommendationPoint = false;
        this.mSpreadPath = new ArrayList();
        this.mAlphas = new ArrayList();
        this.mPath = new Path();
        this.mPathMatrix = new Matrix();
        this.mPathCamera = new Camera();
        this.mType = 0;
        this.mInvalidateRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundFieldView$TLeRGwhQw1mXEBYgDUCCrKfTqHo
            @Override // java.lang.Runnable
            public final void run() {
                SoundFieldView.this.invalidate();
            }
        };
        this.mBreathePath = new Path();
        this.mBreatheWidth = 5.0f;
        this.mBreatheAlpha = 180;
        this.mMatrix = new Matrix();
        this.rectF1 = new RectF();
        init();
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("xpsoundeffect SoundFieldView onThemeChanged callback ");
                if (SoundFieldView.this.mIsAdsorption) {
                    SoundFieldView soundFieldView = SoundFieldView.this;
                    soundFieldView.mButtonPressed = BitmapFactory.decodeResource(soundFieldView.getResources(), R.drawable.pic_ripple_4);
                    SoundFieldView.this.initHifiBitmap();
                } else {
                    SoundFieldView soundFieldView2 = SoundFieldView.this;
                    soundFieldView2.mButtonPressed = BitmapFactory.decodeResource(soundFieldView2.getResources(), R.drawable.pic_ripple_2);
                    SoundFieldView.this.initBitmap();
                }
                SoundFieldView.this.generateScaleBitmap();
            }
        });
    }

    private void init() {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mCommonPath = PathParser.createPathFromPathData(TYPE_COMMON_PATH);
        this.mDynamicPath = PathParser.createPathFromPathData(TYPE_DYNAMIC_PATH);
        this.mSoftPath = PathParser.createPathFromPathData(TYPE_SOFT_PATH);
        this.mVoicePath = PathParser.createPathFromPathData(TYPE_VOICE_PATH);
        this.mClassicPath = PathParser.createPathFromPathData(TYPE_CLASSICAL_PATH);
        this.mDiscoPath = PathParser.createPathFromPathData(TYPE_DISCO_PATH);
        this.mJazzPath = PathParser.createPathFromPathData("M74.717,55L50,55L25.283,55C18.823,55 15.168,54.119 11.906,52.374C8.767,50.696 6.304,48.233 4.626,45.094C2.881,41.832 2,38.177 2,31.717L2,29L2,26.283C2,19.823 2.881,16.168 4.626,12.906C6.304,9.767 8.767,7.304 11.906,5.626C15.168,3.881 18.823,3 25.283,3L50,3L74.717,3C81.177,3 84.832,3.881 88.094,5.626C91.233,7.304 93.696,9.767 95.374,12.906C97.119,16.168 98,19.823 98,26.283L98,29L98,31.717C98,38.177 97.119,41.832 95.374,45.094C93.696,48.233 91.233,50.696 88.094,52.374C84.832,54.119 81.177,55 74.717,55Z");
        this.mRealPath = PathParser.createPathFromPathData(TYPE_REAL_PATH);
        this.mRockPath = PathParser.createPathFromPathData(TYPE_ROCK_PATH);
        this.mStressPath = PathParser.createPathFromPathData("M74.717,55L50,55L25.283,55C18.823,55 15.168,54.119 11.906,52.374C8.767,50.696 6.304,48.233 4.626,45.094C2.881,41.832 2,38.177 2,31.717L2,29L2,26.283C2,19.823 2.881,16.168 4.626,12.906C6.304,9.767 8.767,7.304 11.906,5.626C15.168,3.881 18.823,3 25.283,3L50,3L74.717,3C81.177,3 84.832,3.881 88.094,5.626C91.233,7.304 93.696,9.767 95.374,12.906C97.119,16.168 98,19.823 98,26.283L98,29L98,31.717C98,38.177 97.119,41.832 95.374,45.094C93.696,48.233 91.233,50.696 88.094,52.374C84.832,54.119 81.177,55 74.717,55Z");
        this.mSitePath = PathParser.createPathFromPathData(TYPE_SITE_PATH);
        this.mOriginalPath = PathParser.createPathFromPathData(TYPE_ORIGINAL_PATH);
        this.mButtonPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pic_ripple_2);
        initRipple();
        initPressedAnimator();
        initBreatheAnimator();
        generateScaleBitmap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBitmap() {
        int i = this.mPointCount;
        if (i != 5) {
            if (i == 3) {
                this.mRecommendationPointBitmapDisable[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_normal);
                this.mRecommendationPointBitmapDisable[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_in_normal);
                this.mRecommendationPointBitmapDisable[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_normal);
                this.mRecommendationPointBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_disable);
                this.mRecommendationPointBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_in_disable);
                this.mRecommendationPointBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_disable);
                return;
            }
            return;
        }
        this.mRecommendationPointBitmapDisable[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_normal);
        this.mRecommendationPointBitmapDisable[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_normal);
        this.mRecommendationPointBitmapDisable[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_in_normal);
        this.mRecommendationPointBitmapDisable[3] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_normal);
        this.mRecommendationPointBitmapDisable[4] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_normal);
        this.mRecommendationPointBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_disable);
        this.mRecommendationPointBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_far_disable);
        this.mRecommendationPointBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_in_disable);
        this.mRecommendationPointBitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_disable);
        this.mRecommendationPointBitmap[4] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_blue_origin_enter_disable);
    }

    private void initPressedAnimator() {
        this.mPressedDisplayAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
        this.mPressedDisplayAnimator.setDuration(200L);
        this.mPressedDisplayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SoundFieldView.this.mPressedScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SoundFieldView.this.invalidate();
            }
        });
        this.mPressedDisplayAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SoundFieldView.this.mPressedScale = 0.0f;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SoundFieldView.this.mPressedScale = 1.0f;
                SoundFieldView.this.invalidate();
            }
        });
    }

    private void initBreatheAnimator() {
        this.mBreatheAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mBreatheAnimator.setDuration(800L);
        this.mBreatheAnimator.setRepeatCount(-1);
        this.mBreatheAnimator.setRepeatMode(2);
        this.mBreatheAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                double animatedFraction = valueAnimator.getAnimatedFraction();
                SoundFieldView.this.mBreatheWidth = (float) ((animatedFraction * 5.0d) + 5.0d);
                SoundFieldView.this.mBreatheAlpha = (int) ((animatedFraction * 75.0d) + 180.0d);
                SoundFieldView.this.invalidate();
            }
        });
    }

    private void initRipple() {
        this.mAlphas.add(125);
        this.mSpreadPath.add(Float.valueOf(1.0f));
        this.mSpreadPaint = new Paint();
        this.mSpreadPaint.setStyle(Paint.Style.STROKE);
        this.mSpreadPaint.setStrokeWidth(6.2f);
        this.mSpreadPaint.setAntiAlias(true);
        this.mSpreadPaint.setAlpha(180);
        this.mSpreadPaint.setColor(-1);
        this.mSpreadPaint.setPathEffect(new CornerPathEffect(150.0f));
        this.mSpreadPaint2 = new Paint();
        this.mSpreadPaint2.setStyle(Paint.Style.STROKE);
        this.mSpreadPaint2.setStrokeWidth(6.2f);
        this.mSpreadPaint2.setAntiAlias(true);
        this.mSpreadPaint2.setAlpha(180);
        this.mSpreadPaint2.setColor(-1);
        this.mSpreadPaint2.setPathEffect(new CornerPathEffect(150.0f));
        this.mSpreadPaint2.setMaskFilter(new BlurMaskFilter(7.0f, BlurMaskFilter.Blur.NORMAL));
        this.mBreathePaint = new Paint();
        this.mBreathePaint.setStrokeWidth(6.0f);
        this.mBreathePaint.setAntiAlias(true);
        this.mBreathePaint.setStyle(Paint.Style.STROKE);
        this.mBreathePaint2 = new Paint();
        this.mBreathePaint2.setStrokeWidth(5.0f);
        this.mBreathePaint2.setAlpha(180);
        this.mBreathePaint2.setAntiAlias(true);
        this.mBreathePaint2.setStyle(Paint.Style.STROKE);
        this.mBreathePaint2.setMaskFilter(new BlurMaskFilter(7.0f, BlurMaskFilter.Blur.NORMAL));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateScaleBitmap() {
        for (int i = 0; i < this.mBitmapCount; i++) {
            Matrix matrix = new Matrix();
            float f = (float) (((0.30000000000000004d / this.mBitmapCount) * i) + 0.7d);
            matrix.postScale(f, f);
            Bitmap bitmap = this.mButtonPressed;
            this.mScaleButtonPressed[i] = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mButtonPressed.getHeight(), matrix, true);
        }
    }

    public void setDraggingHalf(boolean z) {
        this.mIsDraggingHalf = z;
    }

    public void initPointCount(int i) {
        this.mPointCount = i;
        int i2 = this.mPointCount;
        this.mRecommendationPoint = new Point[i2];
        this.mRecommendationPointBitmap = new Bitmap[i2];
        this.mRecommendationPointBitmapDisable = new Bitmap[i2];
        initBitmap();
        this.mCollisionIndex = i / 2;
    }

    public void setIsAdsorption(boolean z) {
        this.mIsAdsorption = z;
        Logs.d("xpsoundfield setIsAdsorption mIsAdsorption:" + this.mIsAdsorption);
        if (this.mIsAdsorption) {
            this.mButtonPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pic_ripple_4);
            initHifiBitmap();
        }
        setDefaultLocation();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initHifiBitmap() {
        this.mAdsorptionPointBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_far_normal);
        this.mAdsorptionPointBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_far_normal);
        this.mAdsorptionPointBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_in_normal);
        this.mAdsorptionPointBitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_enter_normal);
        this.mAdsorptionPointBitmapSelected[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_far_selected);
        this.mAdsorptionPointBitmapSelected[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_far_selected);
        this.mAdsorptionPointBitmapSelected[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_in_selected);
        this.mAdsorptionPointBitmapSelected[3] = BitmapFactory.decodeResource(getResources(), R.drawable.pic_red_origin_enter_selected);
    }

    public void setLimitArea(Point point, Point point2, Point point3, Point point4) {
        this.mLeftTopPoint = point;
        this.mLeftBottomPoint = point2;
        this.mRightTopPoint = point3;
        this.mRightBottomPoint = point4;
    }

    public void setRecommendationPoint(Point... pointArr) {
        if (pointArr.length != this.mPointCount) {
            throw new IllegalStateException("recommendation number error!!!");
        }
        if (this.mRecommendationPoint != null) {
            for (int i = 0; i < this.mPointCount; i++) {
                this.mRecommendationPoint[i] = pointArr[i];
                this.mIsSetRecommendationPoint = true;
            }
        }
        setDefaultLocation();
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap scaleBitmap = getScaleBitmap(this.mButtonY);
        int width = this.mButtonX - (scaleBitmap.getWidth() / 2);
        int height = this.mButtonY - (scaleBitmap.getHeight() / 2);
        int i = 0;
        if (this.mIsAdsorption) {
            while (true) {
                Point[] pointArr = this.mAdsorptionPoint;
                if (i >= pointArr.length) {
                    break;
                }
                if (i == this.mAdsorptionIndex) {
                    canvas.drawBitmap(this.mAdsorptionPointBitmapSelected[i], pointArr[i].x, this.mAdsorptionPoint[i].y, (Paint) null);
                } else if (this.mPressDownPoint == i) {
                    canvas.drawBitmap(this.mAdsorptionPointBitmapSelected[i], pointArr[i].x, this.mAdsorptionPoint[i].y, (Paint) null);
                } else {
                    canvas.drawBitmap(this.mAdsorptionPointBitmap[i], pointArr[i].x, this.mAdsorptionPoint[i].y, (Paint) null);
                }
                i++;
            }
        } else if (this.mRecommendationPoint != null) {
            while (i < this.mRecommendationPointBitmapDisable.length && (!this.mIsDraggingHalf || i <= 2)) {
                if (i == this.mCollisionIndex) {
                    canvas.drawBitmap(this.mRecommendationPointBitmap[i], this.mRecommendationPoint[i].x, this.mRecommendationPoint[i].y, (Paint) null);
                } else if (this.mPressDownPoint == i) {
                    canvas.drawBitmap(this.mRecommendationPointBitmap[i], this.mRecommendationPoint[i].x, this.mRecommendationPoint[i].y, (Paint) null);
                } else {
                    canvas.drawBitmap(this.mRecommendationPointBitmapDisable[i], this.mRecommendationPoint[i].x, this.mRecommendationPoint[i].y, (Paint) null);
                }
                i++;
            }
        }
        if (this.mIsMoving) {
            this.mMatrix.reset();
            this.mMatrix.postTranslate(width, height);
            Matrix matrix = this.mMatrix;
            float f = this.mPressedScale;
            matrix.postScale(f, f, this.mButtonX, this.mButtonY);
            canvas.drawBitmap(scaleBitmap, this.mMatrix, null);
        }
        if (!this.mIsMoving) {
            drawRippleView(canvas, scaleBitmap);
        } else {
            drawRippleBreatheView(canvas, scaleBitmap);
        }
        super.onDraw(canvas);
    }

    private void drawRippleBreatheView(Canvas canvas, Bitmap bitmap) {
        LinearGradient linearGradient;
        this.mBreathePath.rewind();
        int i = this.mButtonX;
        int i2 = this.mButtonY;
        int width = bitmap.getWidth() / 2;
        this.mBreathePath.addCircle(i, (float) (i2 - dp2px(5.0f)), (float) ((bitmap.getHeight() / 2) + dp2px(8.0f) + dp2px(30.0f)), Path.Direction.CW);
        rotatePathToZ();
        Matrix matrix = this.mPathMatrix;
        float f = this.mPressedScale;
        matrix.postScale(f, f, this.mButtonX, this.mButtonY);
        this.mBreathePath.transform(this.mPathMatrix);
        RectF rectF = new RectF();
        this.mBreathePath.computeBounds(rectF, true);
        if (this.mIsAdsorption) {
            linearGradient = new LinearGradient(this.mButtonX, rectF.top, this.mButtonX, rectF.bottom, new int[]{getResources().getColor(R.color.sound_field_shader_hifi_start), getResources().getColor(R.color.sound_field_shader_hifi_end)}, (float[]) null, Shader.TileMode.CLAMP);
        } else {
            linearGradient = new LinearGradient(this.mButtonX, rectF.top, this.mButtonX, rectF.bottom, new int[]{getResources().getColor(R.color.sound_field_shader_start), getResources().getColor(R.color.sound_field_shader_end)}, (float[]) null, Shader.TileMode.CLAMP);
        }
        this.mBreathePaint.setShader(linearGradient);
        this.mBreathePaint.setAlpha(this.mBreatheAlpha);
        canvas.drawPath(this.mBreathePath, this.mBreathePaint);
        this.mBreathePaint2.setAlpha(this.mBreatheAlpha);
        this.mBreathePaint2.setShader(linearGradient);
        this.mBreathePaint2.setStrokeWidth(this.mBreatheWidth);
        canvas.drawPath(this.mBreathePath, this.mBreathePaint2);
    }

    private void drawRippleView(Canvas canvas, Bitmap bitmap) {
        this.mSpreadPaint.setAlpha(180);
        this.mSpreadPaint2.setAlpha(180);
        this.mSpreadPaint.setStrokeWidth(6.2f);
        this.mSpreadPaint2.setStrokeWidth(6.2f);
        this.mPath.computeBounds(this.rectF1, true);
        LinearGradient linearGradient = new LinearGradient(this.mButtonX, this.rectF1.top, this.mButtonX, this.rectF1.bottom, new int[]{1560281087, -1}, (float[]) null, Shader.TileMode.CLAMP);
        this.mSpreadPaint.setShader(linearGradient);
        this.mSpreadPaint2.setShader(linearGradient);
        canvas.drawPath(this.mPath, this.mSpreadPaint);
        canvas.drawPath(this.mPath, this.mSpreadPaint2);
        for (int i = 0; i < this.mSpreadPath.size(); i++) {
            int intValue = this.mAlphas.get(i).intValue();
            this.mSpreadPaint.setAlpha(intValue);
            this.mSpreadPaint2.setAlpha(intValue);
            this.mSpreadPaint.setStrokeWidth(4.8f);
            this.mSpreadPaint2.setStrokeWidth(4.8f);
            float floatValue = this.mSpreadPath.get(i).floatValue();
            this.mMatrix.reset();
            double d = floatValue;
            this.mMatrix.postTranslate(0.0f, (float) ((d * d) - 0.6d));
            this.mMatrix.postScale(floatValue, floatValue, this.mButtonX, this.mButtonY);
            Path path = new Path();
            this.mPath.transform(this.mMatrix, path);
            path.computeBounds(this.rectF1, true);
            LinearGradient linearGradient2 = new LinearGradient(this.mButtonX, this.rectF1.top, this.mButtonX, this.rectF1.bottom, new int[]{1560281087, -1}, (float[]) null, Shader.TileMode.CLAMP);
            this.mSpreadPaint.setShader(linearGradient2);
            this.mSpreadPaint2.setShader(linearGradient2);
            canvas.drawPath(path, this.mSpreadPaint);
            canvas.drawPath(path, this.mSpreadPaint2);
            if (intValue > 0 && floatValue < 3.0f) {
                int i2 = intValue - 4;
                if (i2 <= 0) {
                    i2 = 1;
                }
                this.mAlphas.set(i, Integer.valueOf(i2));
                this.mSpreadPath.set(i, Float.valueOf((float) (d + SCALE_DISTANCE)));
            }
        }
        List<Float> list = this.mSpreadPath;
        if (list.get(list.size() - 1).floatValue() > 1.6f) {
            this.mSpreadPath.add(Float.valueOf(1.0f));
            this.mAlphas.add(125);
        }
        if (this.mSpreadPath.size() >= 5) {
            this.mAlphas.remove(0);
            this.mSpreadPath.remove(0);
        }
        invalidateView(50);
    }

    private void rotatePathToZ() {
        this.mPathCamera.save();
        this.mPathCamera.rotateX(60.0f);
        this.mPathCamera.getMatrix(this.mPathMatrix);
        this.mPathCamera.restore();
        this.mPathMatrix.preTranslate(-this.mButtonX, -this.mButtonY);
        this.mPathMatrix.postTranslate(this.mButtonX, this.mButtonY);
    }

    private void invalidateView(int i) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mInvalidateRunnable);
            this.mHandler.postDelayed(this.mInvalidateRunnable, i);
        }
    }

    public void setType(int i) {
        this.mType = i;
        generatePath();
        invalidate();
    }

    private void generatePath() {
        Path path;
        float scale = getScale(this.mButtonY);
        this.mPath.rewind();
        this.mMatrix.reset();
        switch (this.mType) {
            case 1:
                path = this.mVoicePath;
                break;
            case 2:
                path = this.mJazzPath;
                break;
            case 3:
                path = this.mRockPath;
                break;
            case 4:
                path = this.mClassicPath;
                break;
            case 5:
                path = this.mDiscoPath;
                break;
            case 6:
                path = this.mDynamicPath;
                break;
            case 7:
                path = this.mRealPath;
                break;
            case 8:
                path = this.mSoftPath;
                break;
            case 9:
                path = this.mStressPath;
                break;
            case 10:
                path = this.mSitePath;
                break;
            case 11:
                path = this.mOriginalPath;
                break;
            default:
                path = this.mCommonPath;
                break;
        }
        path.computeBounds(this.rectF1, true);
        this.mMatrix.postTranslate((float) ((this.mButtonX - (this.rectF1.width() / 2.0d)) - 2.0d), (float) (this.mButtonY - (this.rectF1.height() / 2.0d)));
        this.mMatrix.postScale(scale, scale, this.mButtonX, this.mButtonY);
        path.transform(this.mMatrix, this.mPath);
    }

    private double dp2px(float f) {
        return (f * Resources.getSystem().getDisplayMetrics().density) + 0.5d;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mInvalidateRunnable);
        }
        ValueAnimator valueAnimator = this.mPressedDisplayAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.mBreatheAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
    }

    private Bitmap getScaleBitmap(int i) {
        int scaleLevel = getScaleLevel(getScale(this.mButtonY));
        Bitmap[] bitmapArr = this.mScaleButtonPressed;
        if (scaleLevel >= bitmapArr.length) {
            return this.mButtonPressed;
        }
        return bitmapArr[scaleLevel];
    }

    private float getScale(int i) {
        float height = (float) (((((i - (this.mButtonPressed.getHeight() / 2.0d)) * 1.0d) / (getHeight() - this.mButtonPressed.getHeight())) * 0.30000000000000004d) + 0.7d);
        if (height < 0.7f) {
            height = 0.7f;
        }
        if (height > 1.0f) {
            return 1.0f;
        }
        return height;
    }

    private int getScaleLevel(float f) {
        if (this.mBitmapCount == 0) {
            this.mBitmapCount = 6;
        }
        return (int) ((f - 0.7d) / ((float) (0.30000000000000004d / this.mBitmapCount)));
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCenterX = getWidth() / 2;
        this.mCenterY = getHeight() / 2;
        generatePath();
    }

    private boolean isOutSide(int i, int i2) {
        return ((int) distance((double) i, (double) i2, (double) this.mCenterX, (double) this.mCenterY)) > getWidth() / 2;
    }

    private int isInAnyPoint(int i, int i2) {
        Point[] pointArr;
        int i3 = i2;
        int i4 = 0;
        int i5 = 2;
        if (this.mIsAdsorption) {
            while (true) {
                if (i4 >= this.mAdsorptionPoint.length) {
                    break;
                } else if (((int) distance(i, i3, pointArr[i4].x + (this.mAdsorptionPointBitmap[i4].getWidth() / 2.0d), this.mAdsorptionPoint[i4].y + (this.mAdsorptionPointBitmap[i4].getHeight() / 2.0d))) < (this.mAdsorptionPointBitmap[i4].getWidth() / 2) + 20) {
                    return i4;
                } else {
                    i4++;
                    i3 = i2;
                }
            }
        } else if (this.mRecommendationPoint != null) {
            int i6 = 0;
            while (i6 < this.mRecommendationPoint.length) {
                if (this.mIsDraggingHalf && i6 > i5) {
                    return -1;
                }
                if (((int) distance(i, i2, this.mRecommendationPoint[i6].x + (this.mRecommendationPointBitmap[i6].getWidth() / 2.0d), this.mRecommendationPoint[i6].y + (this.mRecommendationPointBitmap[i6].getHeight() / 2.0d))) < (this.mRecommendationPointBitmap[i6].getWidth() / 2) + 20) {
                    return i6;
                }
                i6++;
                i5 = 2;
            }
        }
        return -1;
    }

    private boolean isInButtonRange(int i, int i2) {
        return ((int) distance((double) i, (double) i2, (double) this.mButtonX, (double) this.mButtonY)) < getScaleBitmap(i2).getWidth() / 2;
    }

    private void move(int i, int i2) {
        int i3 = i - this.mLastX;
        int i4 = i2 - this.mLastY;
        this.mButtonX = this.mLastButtonX + i3;
        this.mButtonY = this.mLastButtonY + i4;
        edgeCorrectionForButton();
        if (this.mIsAdsorption) {
            this.mAdsorptionIndex = checkNearestPoint(this.mAdsorptionPoint, this.mAdsorptionPointBitmap);
        } else {
            Point[] pointArr = this.mRecommendationPoint;
            if (pointArr != null) {
                this.mCollisionIndex = checkNearestPoint(pointArr, this.mRecommendationPointBitmap);
            }
            if (this.mIsDraggingHalf && this.mCollisionIndex > 2) {
                this.mCollisionIndex = 2;
            }
        }
        invalidate();
    }

    private int getCurrentXLeftEdge(int i) {
        if (this.mLeftTopPoint.x == this.mLeftBottomPoint.x || this.mLeftTopPoint.y == this.mLeftBottomPoint.y) {
            return this.mLeftTopPoint.x;
        }
        return Math.round((float) (((((i - this.mLeftTopPoint.y) * 1.0d) / (this.mLeftBottomPoint.y - this.mLeftTopPoint.y)) * (this.mLeftBottomPoint.x - this.mLeftTopPoint.x)) + this.mLeftTopPoint.x));
    }

    private int getCurrentXRightEdge(int i) {
        if (this.mRightTopPoint.x == this.mRightBottomPoint.x || this.mRightTopPoint.y == this.mRightBottomPoint.y) {
            return this.mRightBottomPoint.x;
        }
        return Math.round((float) (((((i - this.mRightTopPoint.y) * 1.0d) / (this.mRightBottomPoint.y - this.mRightTopPoint.y)) * (this.mRightBottomPoint.x - this.mRightTopPoint.x)) + this.mRightTopPoint.x));
    }

    private int getCurrentYTopEdge(int i) {
        if (this.mRightTopPoint.x == this.mLeftTopPoint.x || this.mRightTopPoint.y == this.mLeftTopPoint.y) {
            return this.mRightTopPoint.y;
        }
        return Math.round((float) (((((i - this.mRightTopPoint.x) * 1.0d) / (this.mLeftTopPoint.x - this.mRightTopPoint.x)) * (this.mLeftTopPoint.y - this.mRightTopPoint.y)) + this.mRightTopPoint.y));
    }

    private int getCurrentYBottomEdge(int i) {
        if (this.mRightBottomPoint.x == this.mLeftBottomPoint.x || this.mRightBottomPoint.y == this.mLeftBottomPoint.y) {
            return this.mRightBottomPoint.y;
        }
        return Math.round((float) (((((i - this.mRightBottomPoint.x) * 1.0d) / (this.mLeftBottomPoint.x - this.mRightBottomPoint.x)) * (this.mLeftBottomPoint.y - this.mRightBottomPoint.y)) + this.mRightBottomPoint.y));
    }

    private void edgeCorrectionForButton() {
        Bitmap scaleBitmap = getScaleBitmap(this.mButtonY);
        int width = scaleBitmap.getWidth() / 2;
        int height = scaleBitmap.getHeight() / 2;
        int width2 = getWidth() - (scaleBitmap.getWidth() / 2);
        int height2 = getHeight() - (scaleBitmap.getHeight() / 2);
        if (this.mIsDraggingHalf) {
            height2 = (int) ((height2 * 1.0d) / 2.0d);
        }
        if (this.mButtonX < width) {
            this.mButtonX = width;
        }
        if (this.mButtonY < height) {
            this.mButtonY = height;
        }
        if (this.mButtonX > width2) {
            this.mButtonX = width2;
        }
        if (this.mButtonY > height2) {
            this.mButtonY = height2;
        }
        int currentXLeftEdge = getCurrentXLeftEdge(this.mButtonY);
        if (this.mButtonX < currentXLeftEdge) {
            this.mButtonX = currentXLeftEdge;
        }
        int currentXRightEdge = getCurrentXRightEdge(this.mButtonY);
        if (this.mButtonX > currentXRightEdge) {
            this.mButtonX = currentXRightEdge;
        }
        int currentYTopEdge = getCurrentYTopEdge(this.mButtonX);
        if (this.mButtonY < currentYTopEdge) {
            this.mButtonY = currentYTopEdge;
        }
        int currentYBottomEdge = getCurrentYBottomEdge(this.mButtonX);
        if (this.mButtonY > currentYBottomEdge) {
            this.mButtonY = currentYBottomEdge;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
        if (r7 != 4) goto L14;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 314
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private static double distance(double d, double d2, double d3, double d4) {
        double d5 = d - d3;
        double d6 = d2 - d4;
        return Math.sqrt((d5 * d5) + (d6 * d6));
    }

    private int checkNearestPoint(Point[] pointArr, Bitmap[] bitmapArr) {
        char c = 0;
        Point point = pointArr[0];
        int i = 1;
        float distance = (float) distance(this.mButtonX, this.mButtonY, point.x + (bitmapArr[0].getWidth() / 2.0d), (bitmapArr[0].getHeight() / 2.0d) + point.y);
        int i2 = 0;
        while (i < pointArr.length) {
            double d = distance;
            int i3 = i2;
            float min = (float) Math.min(d, distance(this.mButtonX, this.mButtonY, pointArr[i].x + (bitmapArr[c].getWidth() / 2.0d), pointArr[i].y + (bitmapArr[0].getHeight() / 2.0d)));
            if (BigDecimal.valueOf(d).compareTo(BigDecimal.valueOf(min)) == 0) {
                i2 = i3;
            } else {
                Point point2 = pointArr[i];
                i2 = i;
            }
            i++;
            distance = min;
            c = 0;
        }
        return i2;
    }

    private int indexCollisionRecommendationPoint(int i) {
        if (!this.mIsSetRecommendationPoint) {
            this.mCollisionIndex = -1;
            return -1;
        }
        Bitmap scaleBitmap = getScaleBitmap(i);
        this.mCollisionIndex = -1;
        int width = this.mButtonX - (scaleBitmap.getWidth() / 2);
        int height = this.mButtonY - (scaleBitmap.getHeight() / 2);
        int width2 = scaleBitmap.getWidth();
        int height2 = scaleBitmap.getHeight();
        if (this.mRecommendationPoint != null) {
            int i2 = 0;
            while (true) {
                Point[] pointArr = this.mRecommendationPoint;
                if (i2 >= pointArr.length) {
                    break;
                } else if (isCollisionWithRect(width, height, width2, height2, pointArr[i2].x, this.mRecommendationPoint[i2].y, this.mRecommendationPointBitmapDisable[i2].getWidth(), this.mRecommendationPointBitmapDisable[i2].getHeight())) {
                    this.mCollisionIndex = i2;
                    break;
                } else {
                    i2++;
                }
            }
        }
        return this.mCollisionIndex;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(measureDimension(300, i), measureDimension(300, i2));
    }

    public int measureDimension(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode == 1073741824 ? size : mode == Integer.MIN_VALUE ? Math.max(i, size) : i;
    }

    public void setDefaultLocation() {
        if (!this.mIsAdsorption) {
            Point[] pointArr = this.mRecommendationPoint;
            if (pointArr != null) {
                this.mButtonX = pointArr[this.mCollisionIndex].x + (this.mRecommendationPointBitmap[this.mCollisionIndex].getWidth() / 2);
                this.mButtonY = this.mRecommendationPoint[this.mCollisionIndex].y + (this.mRecommendationPointBitmap[this.mCollisionIndex].getHeight() / 2);
                return;
            }
            return;
        }
        this.mButtonX = this.mAdsorptionPoint[this.mAdsorptionIndex].x + (this.mAdsorptionPointBitmap[this.mAdsorptionIndex].getWidth() / 2);
        this.mButtonY = this.mAdsorptionPoint[this.mAdsorptionIndex].y + (this.mAdsorptionPointBitmap[this.mAdsorptionIndex].getHeight() / 2);
    }

    public void setLocation(int i, int i2) {
        int i3 = 0;
        if (this.mIsAdsorption) {
            this.mAdsorptionIndex = 0;
            Point[] pointArr = Config.mRealAdsorptionPoint;
            int length = pointArr.length;
            while (i3 < length) {
                Point point = pointArr[i3];
                if (i == point.x && i2 == point.y) {
                    break;
                }
                this.mAdsorptionIndex++;
                i3++;
            }
            if (this.mAdsorptionIndex > Config.mRealAdsorptionPoint.length - 1) {
                this.mAdsorptionIndex = 2;
            }
            Logs.d("xpsound field setLocation mAdsorptionIndex:" + this.mAdsorptionIndex);
            this.mButtonX = this.mAdsorptionPoint[this.mAdsorptionIndex].x + (this.mAdsorptionPointBitmap[this.mAdsorptionIndex].getWidth() / 2);
            this.mButtonY = this.mAdsorptionPoint[this.mAdsorptionIndex].y + (this.mAdsorptionPointBitmap[this.mAdsorptionIndex].getHeight() / 2);
        } else {
            Point[] pointArr2 = null;
            if (CarFunction.isSupportEffectFixPoint()) {
                pointArr2 = Config.mD21RealRecommendationPoint;
            } else if (CarFunction.isSupportRecentEffectMode()) {
                pointArr2 = Config.mThreeRecommendationPoint;
            }
            if (pointArr2 != null) {
                Point point2 = new Point(i, i2);
                while (true) {
                    if (i3 >= pointArr2.length) {
                        break;
                    } else if (point2.equals(pointArr2[i3])) {
                        Point[] pointArr3 = this.mRecommendationPoint;
                        if (pointArr3 != null) {
                            this.mButtonX = pointArr3[i3].x + (this.mRecommendationPointBitmap[i3].getWidth() / 2);
                            this.mButtonY = this.mRecommendationPoint[i3].y + (this.mRecommendationPointBitmap[i3].getHeight() / 2);
                        }
                        this.mCollisionIndex = i3;
                    } else {
                        i3++;
                    }
                }
            } else {
                this.mButtonX = getUiCoordinateForTrapezoid(i, i2, 0);
                this.mButtonY = getUiCoordinateForTrapezoid(i2, i2, 1);
                Point[] pointArr4 = this.mRecommendationPoint;
                if (pointArr4 != null) {
                    this.mCollisionIndex = checkNearestPoint(pointArr4, this.mRecommendationPointBitmap);
                    this.mButtonX = this.mRecommendationPoint[this.mCollisionIndex].x + (this.mRecommendationPointBitmap[this.mCollisionIndex].getWidth() / 2);
                    this.mButtonY = this.mRecommendationPoint[this.mCollisionIndex].y + (this.mRecommendationPointBitmap[this.mCollisionIndex].getHeight() / 2);
                }
            }
        }
        generatePath();
        invalidate();
    }

    public void setOnPositionChangeListener(PositionChangeListener positionChangeListener) {
        this.mPositionChangeListener = positionChangeListener;
    }

    private void notifyPositionChange() {
        if (this.mPositionChangeListener != null) {
            if (this.mIsAdsorption) {
                Logs.d("xpsound field notifyPositionChange:" + this.mAdsorptionIndex);
                this.mPositionChangeListener.onPositionChange(this, Config.mRealAdsorptionPoint[this.mAdsorptionIndex].x, Config.mRealAdsorptionPoint[this.mAdsorptionIndex].y);
            } else if (CarFunction.isSupportEffectFixPoint()) {
                Logs.d("xpsound field d21 notifyPositionChange:" + this.mCollisionIndex);
                int i = this.mCollisionIndex;
                if (i < 0 || i >= Config.mD21RealRecommendationPoint.length) {
                    return;
                }
                this.mPositionChangeListener.onPositionChange(this, Config.mD21RealRecommendationPoint[this.mCollisionIndex].x, Config.mD21RealRecommendationPoint[this.mCollisionIndex].y);
            } else if (CarFunction.isSupportRecentEffectMode()) {
                Logs.d("xpsound field new notifyPositionChange:" + this.mCollisionIndex);
                int i2 = this.mCollisionIndex;
                if (i2 < 0 || i2 >= Config.mThreeRecommendationPoint.length) {
                    return;
                }
                this.mPositionChangeListener.onPositionChange(this, Config.mThreeRecommendationPoint[this.mCollisionIndex].x, Config.mThreeRecommendationPoint[this.mCollisionIndex].y);
            } else {
                this.mPositionChangeListener.onPositionChange(this, getRealSoundFieldForTrapezoid(this.mButtonX, this.mButtonY, 0), getRealSoundFieldForTrapezoid(this.mButtonX, this.mButtonY, 1));
            }
        }
    }

    public int getUiCoordinateForTrapezoid(int i, int i2, int i3) {
        int i4;
        int i5 = 0;
        if (i3 == 0) {
            int uiCoordinateForTrapezoid = getUiCoordinateForTrapezoid(i, i2, 1);
            Bitmap scaleBitmap = getScaleBitmap(uiCoordinateForTrapezoid);
            int min = Math.min(getCurrentXRightEdge(uiCoordinateForTrapezoid), getWidth() - (scaleBitmap.getWidth() / 2));
            i4 = Math.max(getCurrentXLeftEdge(uiCoordinateForTrapezoid), scaleBitmap.getWidth() / 2);
            i5 = min;
        } else if (i3 == 1) {
            int height = getHeight();
            Bitmap[] bitmapArr = this.mScaleButtonPressed;
            i5 = height - (bitmapArr[bitmapArr.length - 1].getHeight() / 2);
            i4 = this.mButtonPressed.getHeight() / 2;
        } else {
            i4 = 0;
        }
        int round = (int) Math.round((i * ((i5 - i4) / 100.0d)) + i4);
        StringBuilder sb = new StringBuilder();
        sb.append("xpsound fieldview real:");
        sb.append(i);
        sb.append("--> ui:");
        sb.append(round);
        sb.append(i3 == 0 ? " x" : " y");
        Logs.v(sb.toString());
        return round;
    }

    public int getRealSoundFieldForTrapezoid(int i, int i2, int i3) {
        long round;
        int i4 = 0;
        if (i3 == 0) {
            Bitmap scaleBitmap = getScaleBitmap(i2);
            int min = Math.min(getCurrentXRightEdge(i2), getWidth() - (scaleBitmap.getWidth() / 2));
            int max = Math.max(getCurrentXLeftEdge(i2), scaleBitmap.getWidth() / 2);
            double d = (float) ((min - max) / 100.0d);
            if (BigDecimal.valueOf(d).compareTo(BigDecimal.ZERO) != 0) {
                round = Math.round((i - max) / d);
                i4 = (int) round;
            }
        } else if (i3 == 1) {
            int currentYBottomEdge = getCurrentYBottomEdge(i);
            int height = getHeight();
            Bitmap[] bitmapArr = this.mScaleButtonPressed;
            double min2 = Math.min(currentYBottomEdge, height - (bitmapArr[bitmapArr.length - 1].getHeight() / 2));
            double max2 = Math.max(getCurrentYTopEdge(i), this.mButtonPressed.getHeight() / 2);
            double d2 = (float) ((min2 - max2) / 100.0d);
            if (BigDecimal.valueOf(d2).compareTo(BigDecimal.ZERO) != 0) {
                round = Math.round((i2 - max2) / d2);
                i4 = (int) round;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("xpsound fieldview real:");
        sb.append(i4);
        sb.append("<-- ui:");
        sb.append(i);
        sb.append(i3 == 0 ? " x" : " y");
        Logs.v(sb.toString());
        return i4;
    }

    public Point getRealDefaultPosition() {
        if (CarFunction.isSupportEffectFixPoint()) {
            return Config.mD21RealRecommendationPoint[0];
        }
        if (CarFunction.isSupportRecentEffectMode()) {
            return Config.mThreeRecommendationPoint[0];
        }
        if (this.mIsAdsorption) {
            return Config.mRealAdsorptionPoint[this.mAdsorptionIndex];
        }
        Point point = new Point();
        Point[] pointArr = this.mRecommendationPoint;
        if (pointArr != null) {
            point.x = pointArr[this.mCollisionIndex].x + (this.mRecommendationPointBitmap[this.mCollisionIndex].getWidth() / 2);
            point.y = this.mRecommendationPoint[this.mCollisionIndex].y + (this.mRecommendationPointBitmap[this.mCollisionIndex].getHeight() / 2);
        }
        Point point2 = new Point();
        point2.x = getRealSoundFieldForTrapezoid(point.x, point.y, 0);
        point2.y = getRealSoundFieldForTrapezoid(point.x, point.y, 1);
        Logs.d("xpsoundfield getRealDefaultPosition x:" + point2.x + " y:" + point2.y + " <-- " + point.x + "," + point.y);
        return point2;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
    }
}
