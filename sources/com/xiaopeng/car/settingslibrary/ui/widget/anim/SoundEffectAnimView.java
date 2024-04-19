package com.xiaopeng.car.settingslibrary.ui.widget.anim;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.graphics.PathParser;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class SoundEffectAnimView extends View {
    public static final int SOUND_EFFECT_CINEMA = 4;
    public static final int SOUND_EFFECT_CONCERT_HALL = 2;
    public static final int SOUND_EFFECT_KTV = 1;
    public static final int SOUND_EFFECT_LIVE = 3;
    private List<AnimInfo> mAnimInfoList;
    Canvas mCanvasTmp;
    private List<AnimInfo> mCinemaAnimInfo;
    private int[] mCinemaBitmapRes;
    private Path mClipPath;
    private List<AnimInfo> mConcertHallAnimInfo;
    private Context mContext;
    private int mCurrentType;
    private Rect mDestRect;
    private int[] mHallBitmapRes;
    private List<AnimInfo> mKtvAnimInfoList;
    private int[] mKtvBitmapRes;
    private List<AnimInfo> mLiveAnimInfo;
    private int[] mLiveBitmapRes;
    private Bitmap mNightBrightenBitmap;
    private Paint mPaint;
    Bitmap mResultBitmap;
    private Bitmap mRoofBitmap;
    private Bitmap mSceneBitmap;
    private Bitmap mSceneCinemaBitmap;
    private Bitmap mSceneHallBitmap;
    private Bitmap mSceneKtvBitmap;
    private Bitmap mSceneLiveBitmap;
    private Rect mSrcRect;
    private ThemeViewModel mThemeViewModel;
    private int mTotalHeight;
    private int mTotalWidth;

    public SoundEffectAnimView(Context context) {
        this(context, null);
    }

    public SoundEffectAnimView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SoundEffectAnimView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SoundEffectAnimView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAnimInfoList = new ArrayList();
        this.mKtvBitmapRes = new int[]{R.drawable.pic_light_scenes_ktv1, R.drawable.pic_light_scenes_ktv2, R.drawable.pic_light_scenes_ktv3, R.drawable.pic_light_scenes_ktv4};
        this.mHallBitmapRes = new int[]{R.drawable.pic_light_scenes_concerthall};
        this.mLiveBitmapRes = new int[]{R.drawable.pic_light_scenes_live2, R.drawable.pic_light_scenes_live1};
        this.mCinemaBitmapRes = new int[]{R.drawable.pic_light_scenes_cinema};
        this.mCurrentType = -1;
        this.mKtvAnimInfoList = new ArrayList();
        this.mConcertHallAnimInfo = new ArrayList();
        this.mLiveAnimInfo = new ArrayList();
        this.mCinemaAnimInfo = new ArrayList();
        init(context);
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, i2);
        this.mThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.anim.SoundEffectAnimView.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("SoundEffectAnimView onThemeChanged ");
                SoundEffectAnimView.this.initBitmaps();
                SoundEffectAnimView.this.updateInfoBitmap();
                SoundEffectAnimView soundEffectAnimView = SoundEffectAnimView.this;
                soundEffectAnimView.setType(soundEffectAnimView.mCurrentType);
            }
        });
    }

    private void recycleBitmap() {
        try {
            this.mNightBrightenBitmap.recycle();
            this.mNightBrightenBitmap = null;
            this.mSceneBitmap.recycle();
            this.mSceneBitmap = null;
            this.mRoofBitmap.recycle();
            this.mRoofBitmap = null;
            if (this.mSceneKtvBitmap != null) {
                this.mSceneKtvBitmap.recycle();
                this.mSceneKtvBitmap = null;
            }
            if (this.mSceneHallBitmap != null) {
                this.mSceneHallBitmap.recycle();
                this.mSceneHallBitmap = null;
            }
            if (this.mSceneLiveBitmap != null) {
                this.mSceneLiveBitmap.recycle();
                this.mSceneLiveBitmap = null;
            }
            if (this.mSceneCinemaBitmap != null) {
                this.mSceneCinemaBitmap.recycle();
                this.mSceneCinemaBitmap = null;
            }
            for (int i = 0; i < this.mKtvAnimInfoList.size(); i++) {
                AnimInfo animInfo = this.mKtvAnimInfoList.get(i);
                animInfo.infoBitmap.recycle();
                animInfo.infoBitmap = null;
            }
            for (int i2 = 0; i2 < this.mConcertHallAnimInfo.size(); i2++) {
                AnimInfo animInfo2 = this.mConcertHallAnimInfo.get(i2);
                animInfo2.infoBitmap.recycle();
                animInfo2.infoBitmap = null;
            }
            for (int i3 = 0; i3 < this.mLiveAnimInfo.size(); i3++) {
                AnimInfo animInfo3 = this.mLiveAnimInfo.get(i3);
                animInfo3.infoBitmap.recycle();
                animInfo3.infoBitmap = null;
            }
            for (int i4 = 0; i4 < this.mCinemaAnimInfo.size(); i4++) {
                AnimInfo animInfo4 = this.mCinemaAnimInfo.get(i4);
                animInfo4.infoBitmap.recycle();
                animInfo4.infoBitmap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logs.d("xpsoundeffect exception " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInfoBitmap() {
        for (int i = 0; i < getKtvAnim().size(); i++) {
            try {
                getKtvAnim().get(i).infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mKtvBitmapRes[i])).getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        for (int i2 = 0; i2 < getConcertHallAnim().size(); i2++) {
            getConcertHallAnim().get(i2).infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mHallBitmapRes[i2])).getBitmap();
        }
        for (int i3 = 0; i3 < getLiveAnim().size(); i3++) {
            getLiveAnim().get(i3).infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mLiveBitmapRes[i3])).getBitmap();
        }
        for (int i4 = 0; i4 < getCinemaAnim().size(); i4++) {
            getCinemaAnim().get(i4).infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mCinemaBitmapRes[i4])).getBitmap();
        }
    }

    private void init(Context context) {
        this.mContext = context;
        this.mPaint = new Paint(1);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setDither(true);
        initBitmaps();
        this.mClipPath = PathParser.createPathFromPathData("M568.45,0C597.43,0.36 631.17,3.08 665.27,5.76C665.47,4.81 665.97,4.34 666.78,4.34L667.07,4.34C669.3,4.33 675.41,4.31 680.32,5.35C681.26,5.55 682,6.34 682.54,7.71L688.52,8.06C689.8,8.13 691,8.69 691.88,9.63L703.92,22.45C705.29,23.91 707.17,24.8 709.17,24.95L720.5,25.77C722.33,25.9 724.06,26.66 725.4,27.92L735.96,37.84C736.23,38.09 736.48,38.36 736.71,38.65C759.27,66.59 775.31,86.54 784.83,98.51C785.63,99.51 783.08,110.9 783.08,121.1L783.08,121.48C783.08,124.04 782.95,124.85 780.4,134.19C777.72,143.97 771.78,151.25 771.78,152.94C771.82,152.98 775.05,154.55 779.18,160.67C781.39,163.94 781.22,173.17 782.34,177.37C786.14,182.97 790.71,190.74 796.32,194.61C802.43,198.82 809.63,198.91 814.35,200.08C831.06,186.54 838.39,184.58 849.38,181.8C866.01,204.31 881.95,226.6 897.19,248.66C915.9,275.74 939.17,310.13 970.1,356.39C973.98,362.19 979.85,399.99 979.85,433.94C979.85,452.25 977.76,477.09 973.56,508.48L1002.55,542.7C1004.98,545.57 1007.73,548.15 1010.73,550.4C1060.39,587.59 1075.33,608.03 1055.54,611.74C854.32,649.43 671.54,633.72 542.71,633.91C538.99,633.91 535.41,633.53 531.98,632.76L532.02,632.76C528.59,633.52 525.01,633.91 521.29,633.91C392.46,633.72 209.68,649.43 8.46,611.74C-11.33,608.03 3.61,587.59 53.27,550.4C56.27,548.15 59.02,545.57 61.45,542.7L61.45,542.7L90.44,508.48C86.24,477.09 84.15,452.25 84.15,433.94C84.15,399.99 90.02,362.19 93.9,356.39C124.83,310.13 148.1,275.74 166.81,248.65C182.05,226.59 197.99,204.31 214.62,181.8C225.61,184.57 232.94,186.54 249.65,200.08C254.37,198.91 261.57,198.81 267.68,194.6C273.29,190.74 277.86,182.97 281.66,177.37C282.78,173.16 282.61,163.94 284.82,160.67C288.95,154.54 292.18,152.98 292.22,152.93C292.22,151.25 286.28,143.97 283.6,134.18C281.05,124.84 280.92,124.04 280.92,121.48L280.92,121.1C280.92,110.9 278.37,99.51 279.17,98.5C288.69,86.54 304.73,66.59 327.29,38.64C327.52,38.36 327.77,38.09 328.04,37.84L328.04,37.84L338.6,27.92C339.94,26.66 341.67,25.9 343.5,25.77L343.5,25.77L354.83,24.95C356.83,24.8 358.71,23.91 360.08,22.44L360.08,22.44L372.12,9.62C373,8.69 374.2,8.13 375.48,8.05L375.48,8.05L381.46,7.71C382,6.33 382.74,5.55 383.68,5.35C388.83,4.26 395.3,4.33 397.22,4.33C398.03,4.33 398.53,4.81 398.73,5.76C432.83,3.08 466.57,0.35 495.55,0C496.6,-0.01 497.13,1.05 498.17,1.05C510.4,1.13 522.29,1.48 532.32,1.55C542.21,1.47 553.86,1.13 565.83,1.06C566.87,1.05 567.4,0 568.45,0Z");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBitmaps() {
        this.mNightBrightenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_brighten_scenes);
        this.mSceneBitmap = getSceneHallBitmap();
        this.mRoofBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_innershadow_scenes);
    }

    private Bitmap getSceneKtvBitmap() {
        if (this.mSceneKtvBitmap == null) {
            this.mSceneKtvBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_bg_scenes_ktv);
        }
        return this.mSceneKtvBitmap;
    }

    private Bitmap getSceneHallBitmap() {
        if (this.mSceneHallBitmap == null) {
            this.mSceneHallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_bg_scenes_concerthall);
        }
        return this.mSceneHallBitmap;
    }

    private Bitmap getSceneLiveBitmap() {
        if (this.mSceneLiveBitmap == null) {
            this.mSceneLiveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_bg_scenes_live);
        }
        return this.mSceneLiveBitmap;
    }

    private Bitmap getSceneCinemaBitmap() {
        if (this.mSceneCinemaBitmap == null) {
            this.mSceneCinemaBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_bg_scenes_cinema);
        }
        return this.mSceneCinemaBitmap;
    }

    private List<AnimInfo> getKtvAnim() {
        if (this.mKtvAnimInfoList.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                KtvAnimInfo ktvAnimInfo = new KtvAnimInfo();
                ktvAnimInfo.infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mKtvBitmapRes[i])).getBitmap();
                this.mKtvAnimInfoList.add(ktvAnimInfo);
            }
        }
        return this.mKtvAnimInfoList;
    }

    private List<AnimInfo> getConcertHallAnim() {
        if (this.mConcertHallAnimInfo.isEmpty()) {
            ConcertHallAnimInfo concertHallAnimInfo = new ConcertHallAnimInfo();
            concertHallAnimInfo.infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mHallBitmapRes[0])).getBitmap();
            this.mConcertHallAnimInfo.add(concertHallAnimInfo);
        }
        return this.mConcertHallAnimInfo;
    }

    private List<AnimInfo> getLiveAnim() {
        if (this.mLiveAnimInfo.isEmpty()) {
            LiveAnimInfo liveAnimInfo = new LiveAnimInfo(0, -60, 1);
            liveAnimInfo.infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mLiveBitmapRes[0])).getBitmap();
            liveAnimInfo.setPivotXY(liveAnimInfo.infoBitmap.getWidth(), 0);
            this.mLiveAnimInfo.add(liveAnimInfo);
            LiveAnimInfo liveAnimInfo2 = new LiveAnimInfo(0, 60, 2);
            liveAnimInfo2.infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mLiveBitmapRes[1])).getBitmap();
            liveAnimInfo2.setPivotXY(0, 0);
            this.mLiveAnimInfo.add(liveAnimInfo2);
        }
        return this.mLiveAnimInfo;
    }

    private List<AnimInfo> getCinemaAnim() {
        if (this.mCinemaAnimInfo.isEmpty()) {
            CinemaAnimInfo cinemaAnimInfo = new CinemaAnimInfo();
            cinemaAnimInfo.infoBitmap = ((BitmapDrawable) this.mContext.getResources().getDrawable(this.mCinemaBitmapRes[0])).getBitmap();
            this.mCinemaAnimInfo.add(cinemaAnimInfo);
        }
        return this.mCinemaAnimInfo;
    }

    public void setType(int i) {
        for (int i2 = 0; i2 < this.mAnimInfoList.size(); i2++) {
            this.mAnimInfoList.get(i2).cancelAnimator();
        }
        this.mAnimInfoList.clear();
        this.mCurrentType = i;
        if (i == 1) {
            this.mAnimInfoList.addAll(getKtvAnim());
            for (AnimInfo animInfo : getKtvAnim()) {
                animInfo.onSizeChanged(this.mTotalWidth, this.mTotalHeight);
            }
            this.mSceneBitmap = getSceneKtvBitmap();
        } else if (i == 2) {
            this.mAnimInfoList.addAll(getConcertHallAnim());
            for (AnimInfo animInfo2 : getConcertHallAnim()) {
                animInfo2.onSizeChanged(this.mTotalWidth, this.mTotalHeight);
            }
            this.mSceneBitmap = getSceneHallBitmap();
        } else if (i == 3) {
            this.mAnimInfoList.addAll(getLiveAnim());
            for (AnimInfo animInfo3 : getLiveAnim()) {
                animInfo3.onSizeChanged(this.mTotalWidth, this.mTotalHeight);
            }
            this.mSceneBitmap = getSceneLiveBitmap();
        } else if (i == 4) {
            this.mAnimInfoList.addAll(getCinemaAnim());
            for (AnimInfo animInfo4 : getCinemaAnim()) {
                animInfo4.onSizeChanged(this.mTotalWidth, this.mTotalHeight);
            }
            this.mSceneBitmap = getSceneCinemaBitmap();
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (ThemeManager.isNightMode(this.mContext)) {
            drawNight(canvas);
        } else {
            drawDay(canvas);
        }
        postInvalidate();
    }

    private void drawDay(Canvas canvas) {
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, this.mTotalWidth, this.mTotalHeight, this.mPaint, 31);
        canvas.clipPath(this.mClipPath);
        canvas.drawColor(-9802126);
        for (int i = 0; i < this.mAnimInfoList.size(); i++) {
            this.mAnimInfoList.get(i).drawDynamic(canvas, this.mPaint);
            this.mAnimInfoList.get(i).refreshInfo(this.mTotalWidth, this.mTotalHeight);
        }
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(this.mSceneBitmap, 0.0f, 0.0f, this.mPaint);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(this.mRoofBitmap, 0.0f, 0.0f, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    private void drawNight(Canvas canvas) {
        canvas.clipPath(this.mClipPath);
        this.mCanvasTmp.drawColor(0, PorterDuff.Mode.CLEAR);
        for (int i = 0; i < this.mAnimInfoList.size(); i++) {
            this.mAnimInfoList.get(i).drawDynamic(this.mCanvasTmp, this.mPaint);
            this.mAnimInfoList.get(i).refreshInfo(this.mTotalWidth, this.mTotalHeight);
        }
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        this.mCanvasTmp.drawBitmap(this.mSceneBitmap, 0.0f, 0.0f, this.mPaint);
        canvas.drawBitmap(this.mResultBitmap, 0.0f, 0.0f, this.mPaint);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(this.mNightBrightenBitmap, this.mSrcRect, this.mDestRect, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.drawBitmap(this.mRoofBitmap, 0.0f, 0.0f, this.mPaint);
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
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mTotalWidth = i;
        this.mTotalHeight = i2;
        this.mSrcRect = new Rect(0, 0, this.mNightBrightenBitmap.getWidth(), this.mNightBrightenBitmap.getHeight());
        this.mDestRect = new Rect(0, 0, getWidth(), getHeight());
        if (this.mAnimInfoList != null) {
            for (int i5 = 0; i5 < this.mAnimInfoList.size(); i5++) {
                this.mAnimInfoList.get(i5).onSizeChanged(this.mTotalWidth, this.mTotalHeight);
            }
        }
        this.mResultBitmap = Bitmap.createBitmap(this.mTotalWidth, this.mTotalHeight, Bitmap.Config.ARGB_8888);
        this.mCanvasTmp = new Canvas(this.mResultBitmap);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
        for (int i = 0; i < this.mAnimInfoList.size(); i++) {
            this.mAnimInfoList.get(i).cancelAnimator();
        }
    }
}
