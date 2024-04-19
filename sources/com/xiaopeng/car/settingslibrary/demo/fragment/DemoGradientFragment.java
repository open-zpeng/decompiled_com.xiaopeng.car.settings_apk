package com.xiaopeng.car.settingslibrary.demo.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
/* loaded from: classes.dex */
public class DemoGradientFragment extends BaseFragment {
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return 0;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return new DemoGradientView(layoutInflater.getContext());
    }

    /* loaded from: classes.dex */
    private class DemoGradientView extends View {
        private LinearGradient linearGradient1;
        private LinearGradient linearGradient2;
        private LinearGradient linearGradient3;
        private Paint mPaintBg;

        public DemoGradientView(Context context) {
            super(context);
            this.mPaintBg = new Paint();
            this.mPaintBg.setAntiAlias(true);
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            this.linearGradient1 = new LinearGradient(0.0f, 0.0f, getWidth(), 0.0f, -1, -14932172, Shader.TileMode.CLAMP);
            this.linearGradient2 = new LinearGradient(0.0f, 0.0f, getWidth(), 0.0f, (int) ViewCompat.MEASURED_STATE_MASK, -13421773, Shader.TileMode.MIRROR);
            this.linearGradient3 = new LinearGradient(0.0f, 0.0f, getWidth(), 0.0f, -13089702, -14932172, Shader.TileMode.REPEAT);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.mPaintBg.setShader(this.linearGradient1);
            float height = (float) (getHeight() / 3.0d);
            canvas.drawRect(0.0f, 0.0f, getWidth(), height, this.mPaintBg);
            this.mPaintBg.setShader(this.linearGradient2);
            double d = height;
            double d2 = d + d;
            canvas.drawRect(0.0f, (float) (d + 5.0d), getWidth(), (float) d2, this.mPaintBg);
            this.mPaintBg.setShader(this.linearGradient3);
            canvas.drawRect(0.0f, (float) (d2 + 5.0d), getWidth(), getHeight(), this.mPaintBg);
        }
    }
}
