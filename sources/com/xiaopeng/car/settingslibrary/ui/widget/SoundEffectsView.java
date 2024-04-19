package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.xiaopeng.car.settingslibrary.R;
/* loaded from: classes.dex */
public class SoundEffectsView extends View {
    private static final int CIRCLE_COUNT = 50;
    private static final int DEFUALT_DIAMETER = 300;
    private static final String TAG = "SoundEffectsView";
    private int mCenterX;
    private int mCenterY;
    private Bitmap mCircleButton;
    private int mCircleButtonMaxDistance;
    private int mCircleButtonX;
    private int mCircleButtonY;
    private float mCorrectX;
    private float mCorrectY;
    private boolean mIsMoving;
    private int mLastButtonX;
    private int mLastButtonY;
    private int mLastX;
    private int mLastY;
    private int mPadding;
    private Paint mPaintBg;
    private Paint mPaintCorrect;
    private Paint mPaintRing;
    private Paint mPaintTest;

    private static double[] edgeCorrection(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = (0.0d - d5) / (d5 - d6);
        double d8 = d + (d3 * d7);
        double d9 = 1.0d + d7;
        return new double[]{d8 / d9, (d2 + (d7 * d4)) / d9};
    }

    public SoundEffectsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mCircleButton = BitmapFactory.decodeResource(getResources(), R.drawable.sound_change_button_selector);
        this.mPadding = 0;
        this.mPaintRing = new Paint();
        this.mPaintRing.setStyle(Paint.Style.STROKE);
        this.mPaintRing.setColor(1142838087);
        this.mPaintRing.setStrokeWidth(2.0f);
        this.mPaintRing.setAntiAlias(true);
        this.mPaintBg = new Paint();
        this.mPaintBg.setColor(-2012013027);
        this.mPaintBg.setAntiAlias(true);
        this.mPaintBg.setShader(new LinearGradient(0.0f, 0.0f, getWidth(), getHeight(), 2900277, 922648, Shader.TileMode.MIRROR));
        this.mPaintTest = new Paint(this.mPaintRing);
        this.mPaintCorrect = new Paint();
        this.mPaintCorrect.setStrokeWidth(10.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.mCenterX, this.mCenterY, (float) (getWidth() / 2.0d), this.mPaintBg);
        drawCircle(canvas);
        canvas.drawBitmap(this.mCircleButton, this.mCircleButtonX - (this.mCircleButton.getWidth() / 2), this.mCircleButtonY - (this.mCircleButton.getWidth() / 2), (Paint) null);
        super.onDraw(canvas);
    }

    private void drawCircle(Canvas canvas) {
        double d;
        double d2;
        double d3 = 2.0d;
        double width = ((getWidth() / 2.0d) - (this.mCircleButton.getWidth() / 2.0d)) / 50.0d;
        int i = 0;
        while (i < 50) {
            i++;
            double strokeWidth = ((i * width) - this.mPaintRing.getStrokeWidth()) + (this.mCircleButton.getWidth() / d3);
            double d4 = this.mCircleButtonX;
            double d5 = this.mCircleButtonY;
            double width2 = (getWidth() / d3) - strokeWidth;
            double distance = distance(this.mCenterX, this.mCenterY, d4, d5);
            if (distance > width2) {
                double[] edgeCorrection = edgeCorrection(this.mCenterX, this.mCenterY, d4, d5, width2, distance);
                d = (int) edgeCorrection[0];
                d2 = (int) edgeCorrection[1];
            } else {
                d = d4;
                d2 = d5;
            }
            canvas.drawCircle((float) d, (float) d2, (float) strokeWidth, this.mPaintRing);
            d3 = 2.0d;
        }
    }

    private void drawTest(Canvas canvas) {
        this.mPaintTest.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawCircle(this.mCenterX, this.mCenterY, (float) (getWidth() / 2.0d), this.mPaintTest);
        this.mPaintTest.setColor(SupportMenu.CATEGORY_MASK);
        canvas.drawCircle(this.mCenterX, this.mCenterY, (float) ((getWidth() / 2.0d) - this.mPadding), this.mPaintTest);
        this.mPaintTest.setColor(-16776961);
        canvas.drawCircle(this.mCenterX, this.mCenterY, this.mCircleButtonMaxDistance, this.mPaintTest);
        this.mPaintCorrect.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawPoint(this.mCorrectX, this.mCorrectY, this.mPaintCorrect);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCircleButtonX = getWidth() / 2;
        this.mCircleButtonY = getHeight() / 2;
        this.mCircleButtonMaxDistance = ((getWidth() / 2) - this.mPadding) - (this.mCircleButton.getWidth() / 2);
        this.mCenterX = getWidth() / 2;
        this.mCenterY = getHeight() / 2;
    }

    private boolean isInButtonRange(int i, int i2) {
        return ((int) distance((double) i, (double) i2, (double) this.mCircleButtonX, (double) this.mCircleButtonY)) < this.mCircleButton.getWidth() / 2;
    }

    private boolean isOutSide(int i, int i2) {
        return ((int) distance((double) i, (double) i2, (double) this.mCenterX, (double) this.mCenterY)) > getWidth() / 2;
    }

    private void move(int i, int i2) {
        int i3 = i - this.mLastX;
        int i4 = i2 - this.mLastY;
        this.mCircleButtonX = this.mLastButtonX + i3;
        this.mCircleButtonY = this.mLastButtonY + i4;
        edgeCorrectionForButton();
        invalidate();
    }

    private void edgeCorrectionForButton() {
        int i = this.mCenterX;
        double d = i;
        double d2 = this.mCenterY;
        double d3 = this.mCircleButtonX;
        double d4 = this.mCircleButtonY;
        double distance = distance(d, d2, d3, d4);
        int i2 = this.mCircleButtonMaxDistance;
        if (distance > i2) {
            double[] edgeCorrection = edgeCorrection(d, d2, d3, d4, i2, distance);
            this.mCircleButtonX = (int) edgeCorrection[0];
            this.mCircleButtonY = (int) edgeCorrection[1];
        }
    }

    private static double distance(double d, double d2, double d3, double d4) {
        double d5 = d - d3;
        double d6 = d2 - d4;
        return Math.sqrt((d5 * d5) + (d6 * d6));
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
        if (r5 != 4) goto L14;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            float r0 = r5.getX()
            int r0 = (int) r0
            float r2 = r5.getY()
            int r2 = (int) r2
            int r5 = r5.getAction()
            r3 = 1
            if (r5 == 0) goto L34
            if (r5 == r3) goto L31
            r3 = 2
            if (r5 == r3) goto L25
            r0 = 3
            if (r5 == r0) goto L31
            r0 = 4
            if (r5 == r0) goto L31
            goto L48
        L25:
            boolean r5 = r4.mIsMoving
            if (r5 == 0) goto L48
            r4.move(r0, r2)
            boolean r5 = r4.isOutSide(r0, r2)
            goto L48
        L31:
            r4.mIsMoving = r1
            goto L48
        L34:
            boolean r5 = r4.isInButtonRange(r0, r2)
            if (r5 == 0) goto L48
            r4.mIsMoving = r3
            r4.mLastX = r0
            r4.mLastY = r2
            int r5 = r4.mCircleButtonX
            r4.mLastButtonX = r5
            int r5 = r4.mCircleButtonY
            r4.mLastButtonY = r5
        L48:
            boolean r5 = r4.mIsMoving
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectsView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int measureDimension = measureDimension(300, i);
        int measureDimension2 = measureDimension(300, i2);
        if (measureDimension >= measureDimension2) {
            measureDimension = measureDimension2;
        }
        setMeasuredDimension(measureDimension, measureDimension);
    }

    public int measureDimension(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode == 1073741824 ? size : mode == Integer.MIN_VALUE ? Math.max(i, size) : i;
    }
}
