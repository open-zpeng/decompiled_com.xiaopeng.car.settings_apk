package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
/* loaded from: classes.dex */
public class SoundEffectBackgroundPositionView extends View {
    private int mAnchorTouchHeight;
    private int mAnchorTouchWidth;
    private Bitmap mBackground;
    private SoundCoordinateInfo mClickTarget;
    private Paint mDefaultPaint;
    private float mDensity;
    private boolean mEditable;
    private Env mEnv;
    private boolean mHandleClick;
    private boolean mHandleDrag;
    private boolean mInTouch;
    private float mLastTouchX;
    private float mLastTouchY;
    private Bitmap mMarker;
    private SoundCoordinateInfo[] mMarkerAnchorCoordinates;
    private Bitmap mMarkerOutline;
    private float mMarkerOutlineOffsetX;
    private float mMarkerOutlineOffsetY;
    private Paint mMarkerOutlinePaint;
    private Rect mMarkerOutlineRect;
    private float mMarkerScale;
    private float mMarkerX;
    private float mMarkerY;
    private IOnSoundEffectPositionViewChangeListener mPositionChangeListener;
    private SoundCoordinateInfo mSelectedCoordinate;
    private int mSession;
    private Bitmap mSpeaker;
    private int mTouchCompatSize;
    private IOnSoundEffectPositionViewTouchStateListener mTouchStateChangeListener;
    private long markerAnimStartTime;

    /* loaded from: classes.dex */
    public interface IOnSoundEffectPositionViewChangeListener {
        void onSoundEffectPositionSelectedChange(SoundEffectBackgroundPositionView soundEffectBackgroundPositionView, SoundCoordinateInfo soundCoordinateInfo);
    }

    /* loaded from: classes.dex */
    public interface IOnSoundEffectPositionViewTouchStateListener {
        void onSoundEffectPositionTouchStateChange(SoundEffectBackgroundPositionView soundEffectBackgroundPositionView, boolean z);
    }

    int distanceSqr(int i, int i2, int i3, int i4) {
        int i5 = i - i3;
        int i6 = i2 - i4;
        return (i5 * i5) + (i6 * i6);
    }

    /* loaded from: classes.dex */
    public static class SoundCoordinateInfo {
        int audioX;
        int audioY;
        int x;
        int y;

        public SoundCoordinateInfo(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        public SoundCoordinateInfo(Point point, Point point2) {
            if (point != null) {
                this.x = point.x;
                this.y = point.y;
            }
            if (point2 != null) {
                this.audioX = point2.x;
                this.audioY = point2.y;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getViewX(int i, float f) {
            return (i >> 1) + ((int) ((this.x * f) + 0.5f));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getViewY(int i, float f) {
            return (i >> 1) - ((int) ((this.y * f) + 0.5f));
        }

        public int getAudioX() {
            return this.audioX;
        }

        public int getAudioY() {
            return this.audioY;
        }
    }

    /* loaded from: classes.dex */
    public static class Env {
        private int mBackgroundResId;
        private SoundCoordinateInfo[] mMarkerAnchorCoordinates;
        private int mMarkerOutlineOffsetX;
        private int mMarkerOutlineOffsetY;
        private int mMarkerOutlineResId;
        private int mMarkerResId;
        private int mSpeakerResId;
        private int mType;
        private int mAnchorTouchWidth = 100;
        private int mAnchorTouchHeight = 100;

        public void setType(int i) {
            this.mType = i;
        }

        public void setBackgroundResId(int i) {
            this.mBackgroundResId = i;
        }

        public void setSpeakerResId(int i) {
            this.mSpeakerResId = i;
        }

        public void setMarkerResId(int i) {
            this.mMarkerResId = i;
        }

        public void setMarkerAnchorCoordinates(SoundCoordinateInfo[] soundCoordinateInfoArr) {
            this.mMarkerAnchorCoordinates = soundCoordinateInfoArr;
        }

        public void setMarkerOutlineResId(int i) {
            this.mMarkerOutlineResId = i;
        }

        public void setMarkerOutlineOffsetX(int i) {
            this.mMarkerOutlineOffsetX = i;
        }

        public void setMarkerOutlineOffsetY(int i) {
            this.mMarkerOutlineOffsetY = i;
        }
    }

    public SoundEffectBackgroundPositionView(Context context) {
        super(context);
        this.mDefaultPaint = new Paint();
        this.mMarkerOutlinePaint = new Paint();
        this.markerAnimStartTime = System.currentTimeMillis();
        this.mMarkerOutlineRect = new Rect();
        this.mMarkerScale = 1.0f;
        init(context, null);
    }

    public SoundEffectBackgroundPositionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDefaultPaint = new Paint();
        this.mMarkerOutlinePaint = new Paint();
        this.markerAnimStartTime = System.currentTimeMillis();
        this.mMarkerOutlineRect = new Rect();
        this.mMarkerScale = 1.0f;
        init(context, attributeSet);
    }

    public SoundEffectBackgroundPositionView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDefaultPaint = new Paint();
        this.mMarkerOutlinePaint = new Paint();
        this.markerAnimStartTime = System.currentTimeMillis();
        this.mMarkerOutlineRect = new Rect();
        this.mMarkerScale = 1.0f;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mDefaultPaint.setAntiAlias(true);
        this.mMarkerOutlinePaint.setAntiAlias(true);
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
    }

    public void setTouchStateChangeListener(IOnSoundEffectPositionViewTouchStateListener iOnSoundEffectPositionViewTouchStateListener) {
        this.mTouchStateChangeListener = iOnSoundEffectPositionViewTouchStateListener;
    }

    public void setPositionChangeListener(IOnSoundEffectPositionViewChangeListener iOnSoundEffectPositionViewChangeListener) {
        this.mPositionChangeListener = iOnSoundEffectPositionViewChangeListener;
    }

    public void setEnv(Env env) {
        setEnv(env, 0, 0);
    }

    public void setEnv(Env env, int i, int i2) {
        SoundCoordinateInfo[] soundCoordinateInfoArr;
        this.mEnv = env;
        SoundCoordinateInfo soundCoordinateInfo = null;
        int i3 = Integer.MAX_VALUE;
        for (SoundCoordinateInfo soundCoordinateInfo2 : env.mMarkerAnchorCoordinates) {
            int i4 = ((i - soundCoordinateInfo2.audioX) * (i - soundCoordinateInfo2.audioX)) + ((i2 - soundCoordinateInfo2.audioY) * (i2 - soundCoordinateInfo2.audioY));
            if (soundCoordinateInfo == null || i4 < i3) {
                soundCoordinateInfo = soundCoordinateInfo2;
                i3 = i4;
            }
        }
        this.mSelectedCoordinate = soundCoordinateInfo;
        if (isAttachedToWindow()) {
            reload();
        }
    }

    public void setEditable(boolean z) {
        this.mEditable = z;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        reload();
    }

    private void reload() {
        final Context context = getContext();
        final int i = this.mSession + 1;
        this.mSession = i;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectBackgroundPositionView$xTDLeF2dfOL4gbR1RQn2FwEcH7M
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectBackgroundPositionView.this.lambda$reload$1$SoundEffectBackgroundPositionView(context, i);
            }
        });
    }

    public /* synthetic */ void lambda$reload$1$SoundEffectBackgroundPositionView(Context context, final int i) {
        final Env env = this.mEnv;
        if (env == null) {
            return;
        }
        final Bitmap decodeResource = env.mBackgroundResId != -1 ? BitmapFactory.decodeResource(context.getResources(), env.mBackgroundResId) : null;
        final Bitmap decodeResource2 = env.mSpeakerResId != -1 ? BitmapFactory.decodeResource(context.getResources(), env.mSpeakerResId) : null;
        final Bitmap decodeResource3 = env.mMarkerResId != -1 ? BitmapFactory.decodeResource(context.getResources(), env.mMarkerResId) : null;
        final Bitmap decodeResource4 = env.mMarkerOutlineResId != -1 ? BitmapFactory.decodeResource(context.getResources(), env.mMarkerOutlineResId) : null;
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectBackgroundPositionView$dupgCm_Ps839pWx6U3aQt_413U8
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectBackgroundPositionView.this.lambda$null$0$SoundEffectBackgroundPositionView(i, decodeResource, decodeResource2, decodeResource3, decodeResource4, env);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$SoundEffectBackgroundPositionView(int i, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Env env) {
        if (i == this.mSession) {
            this.mBackground = bitmap;
            this.mSpeaker = bitmap2;
            this.mMarker = bitmap3;
            this.mMarkerOutline = bitmap4;
            this.mMarkerOutlineOffsetX = env.mMarkerOutlineOffsetX;
            this.mMarkerOutlineOffsetY = env.mMarkerOutlineOffsetY;
            this.mAnchorTouchWidth = env.mAnchorTouchWidth;
            this.mAnchorTouchHeight = env.mAnchorTouchHeight;
            this.mMarkerAnchorCoordinates = env.mMarkerAnchorCoordinates;
            resetMarkerToAnchor(this.mSelectedCoordinate);
        }
    }

    private SoundCoordinateInfo resetMarkerToAnchor(SoundCoordinateInfo soundCoordinateInfo) {
        if (soundCoordinateInfo == null) {
            soundCoordinateInfo = findNearestAnchor(this.mMarkerAnchorCoordinates, (int) this.mMarkerX, (int) this.mMarkerY);
        }
        if (soundCoordinateInfo != null) {
            this.mMarkerX = soundCoordinateInfo.getViewX(getWidth(), this.mDensity);
            this.mMarkerY = soundCoordinateInfo.getViewY(getHeight(), this.mDensity);
            this.mSelectedCoordinate = soundCoordinateInfo;
        }
        postInvalidate();
        return soundCoordinateInfo;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() >> 1;
        int height = getHeight() >> 1;
        Bitmap bitmap = this.mBackground;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, width - (bitmap.getWidth() >> 1), height - (this.mBackground.getHeight() >> 1), this.mDefaultPaint);
        }
        Bitmap bitmap2 = this.mSpeaker;
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2, width - (bitmap2.getWidth() >> 1), height - (this.mSpeaker.getHeight() >> 1), this.mDefaultPaint);
        }
        Bitmap bitmap3 = this.mMarker;
        if (bitmap3 != null) {
            canvas.drawBitmap(bitmap3, this.mMarkerX - (bitmap3.getWidth() >> 1), this.mMarkerY - (this.mMarker.getHeight() >> 1), this.mDefaultPaint);
        }
        if (this.mMarkerOutline != null) {
            calculateMarkerScale();
            this.mMarkerOutlineRect.set((int) ((this.mMarkerX + dpToPix(this.mMarkerOutlineOffsetX)) - ((this.mMarkerOutline.getWidth() >> 1) * this.mMarkerScale)), (int) ((this.mMarkerY + dpToPix(this.mMarkerOutlineOffsetY)) - ((this.mMarkerOutline.getHeight() >> 1) * this.mMarkerScale)), (int) (this.mMarkerX + dpToPix(this.mMarkerOutlineOffsetX) + ((this.mMarkerOutline.getWidth() >> 1) * this.mMarkerScale)), (int) (this.mMarkerY + dpToPix(this.mMarkerOutlineOffsetY) + ((this.mMarkerOutline.getHeight() >> 1) * this.mMarkerScale)));
            canvas.drawBitmap(this.mMarkerOutline, (Rect) null, this.mMarkerOutlineRect, this.mMarkerOutlinePaint);
            if (this.mInTouch) {
                return;
            }
            postInvalidateDelayed(50L);
        }
    }

    private int dpToPix(float f) {
        return (int) ((f * this.mDensity) + 0.5f);
    }

    private void calculateMarkerScale() {
        float f;
        float currentTimeMillis = (((float) (System.currentTimeMillis() - this.markerAnimStartTime)) % 1800.0f) / 1800.0f;
        if (this.mInTouch) {
            f = 1.0f;
        } else {
            float f2 = 1.0f - currentTimeMillis;
            f = ((1.0f - ((f2 * f2) * f2)) * 0.50000006f) + 0.7f;
        }
        this.mMarkerScale = f;
        this.mMarkerOutlinePaint.setAlpha(this.mInTouch ? 1 : (int) ((1.0f - (currentTimeMillis * currentTimeMillis)) * 255.0f));
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        reload();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0013, code lost:
        if (r0 != 3) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 423
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectBackgroundPositionView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    SoundCoordinateInfo findNearestAnchor(SoundCoordinateInfo[] soundCoordinateInfoArr, int i, int i2) {
        SoundCoordinateInfo[] soundCoordinateInfoArr2;
        int width = getWidth();
        int height = getHeight();
        SoundCoordinateInfo soundCoordinateInfo = null;
        if (soundCoordinateInfoArr != null && width != 0 && height != 0) {
            int i3 = 0;
            for (SoundCoordinateInfo soundCoordinateInfo2 : this.mMarkerAnchorCoordinates) {
                int distanceSqr = distanceSqr(soundCoordinateInfo2.getViewX(width, this.mDensity), soundCoordinateInfo2.getViewY(height, this.mDensity), i, i2);
                if (soundCoordinateInfo == null || distanceSqr < i3) {
                    soundCoordinateInfo = soundCoordinateInfo2;
                    i3 = distanceSqr;
                }
            }
        }
        return soundCoordinateInfo;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        resetMarkerToAnchor(this.mSelectedCoordinate);
    }

    private void notifyOnChange(SoundCoordinateInfo soundCoordinateInfo) {
        this.markerAnimStartTime = System.currentTimeMillis();
        this.mPositionChangeListener.onSoundEffectPositionSelectedChange(this, soundCoordinateInfo);
    }
}
