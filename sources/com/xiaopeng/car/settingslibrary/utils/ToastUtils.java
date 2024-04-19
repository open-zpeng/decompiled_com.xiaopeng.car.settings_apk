package com.xiaopeng.car.settingslibrary.utils;

import android.content.Context;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes.dex */
public class ToastUtils {
    private static ToastUtils mToastManager;

    private ToastUtils(Context context) {
    }

    public static void init(Context context) {
        if (mToastManager == null) {
            mToastManager = new ToastUtils(context.getApplicationContext());
        }
    }

    public static ToastUtils get() {
        return mToastManager;
    }

    public void showText(String str) {
        XToast.show(str);
    }

    public void showText(String str, int i) {
        XToast.showAt(str, i);
    }

    public void showText(int i) {
        XToast.show(i);
    }

    public void showLongText(String str) {
        XToast.showLong(str);
    }

    public void showShortText(String str) {
        XToast.showShort(str);
    }
}
