package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes2.dex */
public class XOffsetView extends XLinearLayout {
    private String exceedMaxText;
    private String exceedMinText;
    private int maxValue;
    private int minValue;
    private OnButtonClickListener onButtonClickListener;
    private String unit;
    private int value;
    private XImageButton x_btn_increase;
    private XImageButton x_btn_reduce;
    private XTextView x_text_content;

    /* loaded from: classes2.dex */
    public interface OnButtonClickListener {
        void onIncrease(int i);

        void onReduce(int i);
    }

    public XOffsetView(Context context) {
        this(context, null);
    }

    public XOffsetView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XOffsetView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XOffsetView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.x_offset_view, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XOffsetView);
        this.value = obtainStyledAttributes.getInt(R.styleable.XOffsetView_offset_view_value, 0);
        this.maxValue = obtainStyledAttributes.getInt(R.styleable.XOffsetView_offset_view_max_value, 0);
        this.minValue = obtainStyledAttributes.getInt(R.styleable.XOffsetView_offset_view_min_value, 0);
        this.unit = obtainStyledAttributes.getString(R.styleable.XOffsetView_offset_view_unit);
        this.exceedMaxText = obtainStyledAttributes.getString(R.styleable.XOffsetView_offset_view_out_exceed_max_text);
        this.exceedMinText = obtainStyledAttributes.getString(R.styleable.XOffsetView_offset_view_out_exceed_min_text);
        if (this.unit == null) {
            this.unit = "";
        }
        obtainStyledAttributes.recycle();
        setBackgroundResource(R.drawable.x_btn_real_secoundary_selector);
        initView();
        refreshContent();
        initEvent();
    }

    private void initView() {
        this.x_btn_reduce = (XImageButton) findViewById(R.id.x_btn_reduce);
        this.x_btn_increase = (XImageButton) findViewById(R.id.x_btn_increase);
        this.x_text_content = (XTextView) findViewById(R.id.x_text_content);
    }

    private void refreshContent() {
        int i = this.value;
        int i2 = this.maxValue;
        if (i >= i2) {
            this.value = i2;
            this.x_btn_increase.setEnabled(false);
        } else {
            int i3 = this.minValue;
            if (i <= i3) {
                this.value = i3;
                this.x_btn_reduce.setEnabled(false);
            } else {
                this.x_btn_increase.setEnabled(true);
                this.x_btn_reduce.setEnabled(true);
            }
        }
        XTextView xTextView = this.x_text_content;
        xTextView.setText(this.value + this.unit);
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
        showExceedToast();
        refreshContent();
    }

    public void setMaxValue(int i) {
        this.maxValue = i;
        refreshContent();
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public void setMinValue(int i) {
        this.minValue = i;
        refreshContent();
    }

    public int getMinValue() {
        return this.minValue;
    }

    private void initEvent() {
        this.x_btn_reduce.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XOffsetView$YKHQd5X6VjOlTwshEZSdFM9LIeM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XOffsetView.this.lambda$initEvent$0$XOffsetView(view);
            }
        });
        this.x_btn_increase.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XOffsetView$b26D0YtKblM0SsIn1qw2KNL-nzY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XOffsetView.this.lambda$initEvent$1$XOffsetView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initEvent$0$XOffsetView(View view) {
        OnButtonClickListener onButtonClickListener = this.onButtonClickListener;
        if (onButtonClickListener != null) {
            onButtonClickListener.onReduce(this.value);
        }
    }

    public /* synthetic */ void lambda$initEvent$1$XOffsetView(View view) {
        OnButtonClickListener onButtonClickListener = this.onButtonClickListener;
        if (onButtonClickListener != null) {
            onButtonClickListener.onIncrease(this.value);
        }
    }

    private void showExceedToast() {
        int i = this.value;
        int i2 = this.maxValue;
        if (i >= i2) {
            this.value = i2;
            if (TextUtils.isEmpty(this.exceedMaxText)) {
                return;
            }
            XToast.show(this.exceedMaxText);
            return;
        }
        int i3 = this.minValue;
        if (i <= i3) {
            this.value = i3;
            if (TextUtils.isEmpty(this.exceedMinText)) {
                return;
            }
            XToast.show(this.exceedMinText);
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
