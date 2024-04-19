package com.xiaopeng.car.settingslibrary.ui.widget.anim;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.animation.AccelerateDecelerateInterpolator;
/* loaded from: classes.dex */
public class LiveAnimInfo extends AnimInfo {
    private int mEndDegree;
    public int mPivotX;
    public int mPivotY;
    private int mStartDegree;
    public int mType;
    Matrix matrix = new Matrix();

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public /* bridge */ /* synthetic */ float getRandomValue(float f, float f2) {
        return super.getRandomValue(f, f2);
    }

    public LiveAnimInfo(int i, int i2, int i3) {
        this.mType = i3;
        this.mStartDegree = i;
        this.mEndDegree = i2;
    }

    public void setPivotXY(int i, int i2) {
        this.mPivotX = i;
        this.mPivotY = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void drawDynamic(Canvas canvas, Paint paint) {
        Bitmap bitmap;
        if (this.infoBitmap == null) {
            return;
        }
        this.matrix.reset();
        float width = (float) (((this.mGlobalWidth / 2.0d) - bitmap.getWidth()) / 2.0d);
        float height = (float) ((this.mGlobalHeight - bitmap.getHeight()) / 2.0d);
        if (this.mType == 1) {
            this.matrix.postTranslate(width, height);
            this.matrix.postRotate(this.infoRotate, width + bitmap.getWidth(), height);
        } else {
            double d = width;
            this.matrix.postTranslate((float) ((this.mGlobalWidth / 2.0d) + d), height);
            this.matrix.postRotate(this.infoRotate, (float) (d + (this.mGlobalWidth / 2.0d)), height);
        }
        canvas.drawBitmap(this.infoBitmap, this.matrix, paint);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void refreshInfo(int i, int i2) {
        this.mGlobalWidth = i;
        this.mGlobalHeight = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void onSizeChanged(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return;
        }
        this.mValueAnimator = ValueAnimator.ofFloat(this.mStartDegree, this.mEndDegree);
        this.mValueAnimator.setDuration(4000L);
        this.mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(2);
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.anim.LiveAnimInfo.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LiveAnimInfo.this.infoRotate = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            }
        });
        this.mValueAnimator.start();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void cancelAnimator() {
        if (this.mValueAnimator != null) {
            this.mValueAnimator.cancel();
            this.mValueAnimator.removeAllUpdateListeners();
        }
    }
}
