package com.xiaopeng.car.settingslibrary.vm.laboratory;
/* loaded from: classes.dex */
public interface ISayHiListener {
    public static final int LIGHT_EFFECT_SAYHI = 10;
    public static final int LLU_EFFECT_CLOSE = 0;
    public static final int LLU_EFFECT_MODE_A = 1;
    public static final int LLU_EFFECT_MODE_B = 2;
    public static final int LLU_EFFECT_MODE_C = 3;

    default void onAvasSayHiSwChanged(boolean z) {
    }

    default void onLluSayHiSwChanged(boolean z) {
    }

    default void onLluSayHiTypeChanged(int i) {
    }
}
