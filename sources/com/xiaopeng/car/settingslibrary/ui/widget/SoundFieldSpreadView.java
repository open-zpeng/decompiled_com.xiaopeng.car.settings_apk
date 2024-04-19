package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.libtheme.ThemeViewModel;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class SoundFieldSpreadView extends View {
    private static final int ALPHA_DISTANCE = 15;
    private static final int INVALIDATE_TIME = 50;
    private static final int PAINT_ALPHA = 220;
    private static final float SCALE_DISTANCE = 0.06f;
    private final List<Integer> mAlphas;
    private int mCenterX;
    private int mCenterY;
    Path mClipPath;
    Matrix mMatrix;
    private final Path mPath;
    private int[] mSpreadColorRes;
    private Paint mSpreadPaint;
    private final List<Float> mSpreadPath;
    private ThemeViewModel mThemeViewModel;
    Path mTmpPath;
    RectF rectF2;

    public SoundFieldSpreadView(Context context) {
        this(context, null, 0);
    }

    public SoundFieldSpreadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SoundFieldSpreadView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SoundFieldSpreadView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSpreadPath = new ArrayList();
        this.mAlphas = new ArrayList();
        this.mPath = new Path();
        this.mClipPath = new Path();
        this.mMatrix = new Matrix();
        this.mTmpPath = new Path();
        this.rectF2 = new RectF();
        this.mSpreadColorRes = null;
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, i2);
        this.mThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldSpreadView.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("SoundFieldSpreadView onThemeChanged");
            }
        });
        this.mAlphas.add(Integer.valueOf((int) PAINT_ALPHA));
        this.mSpreadPath.add(Float.valueOf(1.0f));
        this.mSpreadPaint = new Paint();
        this.mSpreadPaint.setStyle(Paint.Style.STROKE);
        this.mSpreadPaint.setStrokeWidth(5.0f);
        this.mSpreadPaint.setAntiAlias(true);
        this.mSpreadPaint.setAlpha(PAINT_ALPHA);
        this.mSpreadPaint.setColor(-1);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mCenterX = i / 2;
        this.mCenterY = i2 / 2;
        int i5 = this.mCenterX;
        double width = (float) (((getWidth() / 2) * 6.0d) / 10.0d);
        int i6 = this.mCenterY;
        double height = (float) (((getHeight() / 2) * 2.0d) / 10.0d);
        this.mPath.addOval(new RectF((float) (i5 - width), (float) (i6 - height), (float) (i5 + width), (float) (i6 + height)), Path.Direction.CCW);
        int width2 = getWidth() / 4;
        this.mClipPath.moveTo(width2, this.mCenterY);
        this.mClipPath.lineTo(20.0f, 0.0f);
        this.mClipPath.lineTo(0.0f, 0.0f);
        this.mClipPath.lineTo(0.0f, getHeight());
        this.mClipPath.lineTo(getWidth(), getHeight());
        this.mClipPath.lineTo(getWidth(), 0.0f);
        this.mClipPath.lineTo((float) (getWidth() - 15.0d), 0.0f);
        this.mClipPath.lineTo((float) (getWidth() - width2), this.mCenterY);
        this.mClipPath.close();
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

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        double d;
        super.onDraw(canvas);
        canvas.clipPath(this.mClipPath);
        canvas.drawPath(this.mPath, this.mSpreadPaint);
        for (int i = 0; i < this.mSpreadPath.size(); i++) {
            int intValue = this.mAlphas.get(i).intValue();
            this.mSpreadPaint.setAlpha(intValue);
            float floatValue = this.mSpreadPath.get(i).floatValue();
            this.mMatrix.reset();
            double d2 = floatValue;
            this.mMatrix.postTranslate(0.0f, (float) (d2 * d2 * 2.0d));
            this.mMatrix.postScale(floatValue, floatValue, this.mCenterX, this.mCenterY);
            this.mTmpPath.reset();
            this.mPath.transform(this.mMatrix, this.mTmpPath);
            this.mTmpPath.computeBounds(this.rectF2, true);
            if (this.mSpreadColorRes != null) {
                d = d2;
                this.mSpreadPaint.setShader(new LinearGradient(this.mCenterX, this.rectF2.top, this.mCenterX, this.rectF2.bottom, new int[]{getResources().getColor(this.mSpreadColorRes[0], getContext().getTheme()), getResources().getColor(this.mSpreadColorRes[1], getContext().getTheme())}, (float[]) null, Shader.TileMode.CLAMP));
            } else {
                d = d2;
            }
            canvas.drawPath(this.mTmpPath, this.mSpreadPaint);
            if (intValue > 0 && floatValue < 3.0f) {
                int i2 = intValue - 15;
                this.mAlphas.set(i, Integer.valueOf(i2 > 0 ? i2 : 1));
                this.mSpreadPath.set(i, Float.valueOf((float) (d + 0.05999999865889549d)));
            }
        }
        List<Float> list = this.mSpreadPath;
        if (list.get(list.size() - 1).floatValue() > 1.5f) {
            this.mSpreadPath.add(Float.valueOf(1.0f));
            this.mAlphas.add(Integer.valueOf((int) PAINT_ALPHA));
        }
        if (this.mSpreadPath.size() >= 5) {
            this.mAlphas.remove(0);
            this.mSpreadPath.remove(0);
        }
        postInvalidateDelayed(50L);
    }

    public void setSpreadColor(int[] iArr) {
        if (iArr.length == 2) {
            this.mSpreadColorRes = iArr;
        }
    }
}
