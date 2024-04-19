package com.xiaopeng.car.settingslibrary.common.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;
/* loaded from: classes.dex */
public class InputMethodUtil {
    private KeyboardChangeListener mKeyboardChangeListener;
    ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaopeng.car.settingslibrary.common.utils.InputMethodUtil.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            InputMethodUtil.this.rootView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.common.utils.InputMethodUtil.1.1
                @Override // java.lang.Runnable
                public void run() {
                    Rect rect = new Rect();
                    InputMethodUtil.this.rootView.getWindowVisibleDisplayFrame(rect);
                    int i = InputMethodUtil.this.screenHeight - rect.bottom;
                    Logs.d("xpinputmethod " + InputMethodUtil.this.screenHeight + " " + rect.bottom);
                    if (i <= 0) {
                        if (InputMethodUtil.this.mKeyboardChangeListener != null) {
                            InputMethodUtil.this.mKeyboardChangeListener.onKeyboardHide();
                        }
                    } else if (InputMethodUtil.this.mKeyboardChangeListener != null) {
                        InputMethodUtil.this.mKeyboardChangeListener.onKeyboardShow();
                    }
                }
            });
        }
    };
    private View rootView;
    private int screenHeight;

    /* loaded from: classes.dex */
    public interface KeyboardChangeListener {
        void onKeyboardHide();

        void onKeyboardShow();
    }

    public InputMethodUtil(AppCompatActivity appCompatActivity) {
        this.screenHeight = appCompatActivity.getResources().getDisplayMetrics().heightPixels;
        this.rootView = appCompatActivity.getWindow().getDecorView();
    }

    public void setOnKeyboardChangeListener(KeyboardChangeListener keyboardChangeListener) {
        if (keyboardChangeListener != null) {
            this.mKeyboardChangeListener = keyboardChangeListener;
            this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
    }

    public void removeOnKeyboardChangeListener() {
        this.rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        this.mKeyboardChangeListener = null;
    }
}
