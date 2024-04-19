package com.xiaopeng.car.settingslibrary.ui.widget.anim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
/* loaded from: classes.dex */
public class KtvAnimInfo extends AnimInfo {
    private float mRandomValueStart = 0.0f;
    private float mRandomValueEnd = 0.9f;

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void cancelAnimator() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void onSizeChanged(int i, int i2) {
        if (i != 0 || i2 == 0) {
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public /* bridge */ /* synthetic */ float getRandomValue(float f, float f2) {
        return super.getRandomValue(f, f2);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void drawDynamic(Canvas canvas, Paint paint) {
        Bitmap bitmap = this.infoBitmap;
        if (bitmap == null) {
            return;
        }
        if (this.currentPoint.x == 0 && this.currentPoint.y == 0) {
            return;
        }
        canvas.drawBitmap(bitmap, this.currentPoint.x, this.currentPoint.y, paint);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.car.settingslibrary.ui.widget.anim.AnimInfo
    public void refreshInfo(int i, int i2) {
        if (this.currentPoint.x == 0 && this.currentPoint.y == 0) {
            this.currentPoint = updateRandomTarget(i, i2);
            this.targetPoint = updateRandomTarget(i, i2);
        }
        if (this.targetPoint.x - this.currentPoint.x > 0) {
            this.currentPoint.x += this.speed;
            if (this.currentPoint.x >= this.targetPoint.x) {
                refreshNewPath(i, i2);
            }
        } else {
            this.currentPoint.x -= this.speed;
            if (this.currentPoint.x <= this.targetPoint.x) {
                refreshNewPath(i, i2);
            }
        }
        if (this.targetPoint.y - this.currentPoint.y > 0) {
            this.currentPoint.y += this.speed;
            if (this.currentPoint.y >= this.targetPoint.y) {
                refreshNewPath(i, i2);
                return;
            }
            return;
        }
        this.currentPoint.y -= this.speed;
        if (this.currentPoint.y <= this.targetPoint.y) {
            refreshNewPath(i, i2);
        }
    }

    private void refreshNewPath(int i, int i2) {
        this.targetPoint = updateRandomTarget(i, i2);
    }

    public Point updateRandomTarget(int i, int i2) {
        Point point = new Point();
        double d = i;
        int randomValue = (int) (getRandomValue(this.mRandomValueStart, this.mRandomValueEnd) * d);
        if (randomValue > 0 && randomValue < i - this.infoBitmap.getWidth()) {
            point.x = randomValue;
        } else {
            point.x = (int) (getRandomValue(this.mRandomValueStart, this.mRandomValueEnd) * d);
        }
        double d2 = i2;
        int randomValue2 = (int) (getRandomValue(this.mRandomValueStart, this.mRandomValueEnd) * d2);
        if (randomValue2 > 0 && randomValue2 < i2 - this.infoBitmap.getHeight()) {
            point.y = randomValue2;
        } else {
            point.y = (int) (getRandomValue(this.mRandomValueStart, this.mRandomValueEnd) * d2);
        }
        return (point.x > i - this.infoBitmap.getWidth() || point.y > i2 - this.infoBitmap.getHeight()) ? updateRandomTarget(i, i2) : point;
    }
}
