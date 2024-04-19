package com.xiaopeng.car.settingslibrary.ui.widget.anim;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import androidx.core.internal.view.SupportMenu;
/* loaded from: classes.dex */
abstract class AnimInfo {
    public float infoAlpha;
    public float infoRotate;
    public float infoScale;
    Rect mDestRect;
    public int mGlobalHeight;
    public int mGlobalWidth;
    Rect mSrcRect;
    ValueAnimator mValueAnimator;
    public Point currentPoint = new Point();
    public Point targetPoint = new Point();
    public Bitmap infoBitmap = null;
    public int speed = 3;

    public abstract void cancelAnimator();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void drawDynamic(Canvas canvas, Paint paint);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void onSizeChanged(int i, int i2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void refreshInfo(int i, int i2);

    protected void debugDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(SupportMenu.CATEGORY_MASK);
        paint.setStrokeWidth(5.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50.0f);
        canvas.drawText("位置", this.mDestRect.left, this.mDestRect.top, paint);
    }

    public float getRandomValue(float f, float f2) {
        float random = (float) Math.random();
        return (f >= random || random >= f2) ? (float) Math.random() : random;
    }
}
