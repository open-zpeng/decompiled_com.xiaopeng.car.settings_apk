package com.xiaopeng.car.settingslibrary.vm.laboratory;

import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
/* loaded from: classes.dex */
public enum LluSayHiEffect {
    EffectA,
    EffectB,
    EffectC;
    
    private static final String TAG = "LluSayHiEffect";

    public static LluSayHiEffect fromLluValue(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return EffectC;
                }
                LogUtils.e(TAG, "Unknown llu sayhi effect mode value: " + i + ", return default mode", false);
                return EffectA;
            }
            return EffectB;
        }
        return EffectA;
    }

    public static LluSayHiEffect fromAvasValue(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return EffectC;
                }
                LogUtils.e(TAG, "Unknown llu sayhi effect mode value: " + i + ", return default mode", false);
                return EffectA;
            }
            return EffectB;
        }
        return EffectA;
    }

    /* renamed from: com.xiaopeng.car.settingslibrary.vm.laboratory.LluSayHiEffect$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect = new int[LluSayHiEffect.values().length];

        static {
            try {
                $SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[LluSayHiEffect.EffectA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[LluSayHiEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[LluSayHiEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toLluCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown llu sayhi effect: " + this);
            }
        }
        return i2;
    }

    public int toAvasType() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown avas effect: " + this);
            }
        }
        return i2;
    }

    public int getDescId() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$car$settingslibrary$vm$laboratory$LluSayHiEffect[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return 0;
                }
                return R.string.laboratory_sayhi_mode_3;
            }
            return R.string.laboratory_sayhi_mode_2;
        }
        return R.string.laboratory_sayhi_mode_1;
    }
}
