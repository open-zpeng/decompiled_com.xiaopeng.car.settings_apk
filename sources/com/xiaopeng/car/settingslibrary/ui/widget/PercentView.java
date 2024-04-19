package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.xiaopeng.car.settingslibrary.R;
/* loaded from: classes.dex */
public class PercentView extends View {
    private static final int[] COLORS = {ViewCompat.MEASURED_STATE_MASK};
    private int[] mColors;
    private Paint mPaint;
    private float[] mPercents;

    public PercentView(Context context) {
        this(context, null);
    }

    public PercentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PercentView);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.PercentView_colors, 0);
        if (resourceId != 0) {
            setColors(getResources().getIntArray(resourceId));
        }
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        if (this.mColors == null) {
            this.mColors = COLORS;
        }
        setClipToOutline(true);
    }

    public void setColors(int... iArr) {
        this.mColors = iArr;
        invalidate();
    }

    public void setPercents(float... fArr) {
        this.mPercents = fArr;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        drawContent(canvas);
    }

    private void drawContent(Canvas canvas) {
        if (this.mPercents == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();
        int i = 0;
        float f = 0.0f;
        while (true) {
            float[] fArr = this.mPercents;
            if (i >= fArr.length) {
                return;
            }
            float f2 = fArr[i] * width;
            if (f2 > 0.0f && f2 < 1.0f) {
                f2 = 1.0f;
            }
            Paint paint = this.mPaint;
            int[] iArr = this.mColors;
            paint.setColor(iArr[i % iArr.length]);
            float f3 = f + f2;
            canvas.drawRect(f, 0.0f, f3, height, this.mPaint);
            i++;
            f = f3;
        }
    }

    public float[] getPercents() {
        return this.mPercents;
    }
}
