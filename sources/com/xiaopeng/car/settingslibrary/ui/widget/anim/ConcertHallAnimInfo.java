package com.xiaopeng.car.settingslibrary.ui.widget.anim;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AccelerateDecelerateInterpolator;
/* loaded from: classes.dex */
public class ConcertHallAnimInfo extends AnimInfo {
    Matrix matrix = new Matrix();

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void refreshInfo(int i, int i2) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public /* bridge */ /* synthetic */ float getRandomValue(float f, float f2) {
        return super.getRandomValue(f, f2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void drawDynamic(Canvas canvas, Paint paint) {
        if (this.infoBitmap == null || this.mSrcRect == null || this.mDestRect == null) {
            return;
        }
        this.matrix.reset();
        this.matrix.postTranslate(this.mDestRect.left, this.mDestRect.top);
        this.matrix.postScale(this.infoScale, this.infoScale, (float) ((this.infoBitmap.getWidth() / 2.0d) + this.mDestRect.left), (float) ((this.infoBitmap.getHeight() / 2.0d) + this.mDestRect.top));
        canvas.drawBitmap(this.infoBitmap, this.matrix, paint);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void onSizeChanged(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return;
        }
        this.mGlobalWidth = i;
        this.mGlobalHeight = i2;
        int abs = Math.abs(this.mGlobalWidth - this.infoBitmap.getWidth()) / 2;
        int abs2 = Math.abs(this.mGlobalHeight - this.infoBitmap.getHeight());
        this.mSrcRect = new Rect(0, 0, this.infoBitmap.getWidth(), this.infoBitmap.getHeight());
        this.mDestRect = new Rect(abs, abs2, this.infoBitmap.getWidth() + abs, this.infoBitmap.getHeight() + abs2);
        this.mValueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
        this.mValueAnimator.setDuration(2000L);
        this.mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(2);
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.anim.ConcertHallAnimInfo.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ConcertHallAnimInfo.this.infoScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
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
