package com.xiaopeng.car.settingslibrary.vm.sound;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
/* loaded from: classes.dex */
public class SoundEffectValues {
    public static final int SOUND_EFEECT_EQ = 2;
    public static final int SOUND_EFEECT_HIFI = 3;
    public static final int SOUND_EFEECT_ORIGINAL = 4;
    public static final int SOUND_EFEECT_ROUND = 1;
    public static final int SOUND_SCENES_KTV = 3;
    public static final int SOUND_SCENES_LIVE = 2;
    public static final int SOUND_SCENES_RECORDING = 4;
    public static final int SOUND_SCENES_THEATER = 1;
    public static final int SOUND_STYLE_1_HIFI;
    public static final int SOUND_STYLE_2_HIFI;
    public static final int SOUND_STYLE_3_HIFI;
    public static final int SOUND_STYLE_4_HIFI;
    public static final int SOUND_STYLE_CLASSICAL = 6;
    public static final int SOUND_STYLE_COMMON = 1;
    public static final int SOUND_STYLE_CUSTOM = 4;
    public static final int SOUND_STYLE_CUSTOM_NEW = 7;
    public static final int SOUND_STYLE_DISCO = 5;
    public static final int SOUND_STYLE_DYNA = 2;
    public static final int SOUND_STYLE_GENTLE = 3;
    public static final int SOUND_STYLE_HIFI = 2;
    public static final int SOUND_STYLE_JAZZ = 3;
    public static final int SOUND_STYLE_LOCAL = 1;
    public static final int SOUND_STYLE_ORIGINAL = 0;
    public static final int SOUND_STYLE_PEOPLE = 2;
    public static final int SOUND_STYLE_RECENT_CINEMA = 2;
    public static final int SOUND_STYLE_RECENT_CUSTOM = 3;
    public static final int SOUND_STYLE_RECENT_DYNAMIC = 1;
    public static final int SOUND_STYLE_RECENT_LOCAL = 0;
    public static final int SOUND_STYLE_ROCK = 4;
    private int innervationSound;
    private int nativeSound;
    private int softSound;
    public final int EFFECT_TYPE_XSOUND = 1;
    public final int EFFECT_TYPE_DTS = 2;
    public final int TYPE_EQ = 1;
    public final int TYPE_EXPLOSIVE_STRESS = 2;
    public final int TYPE_DYNAMIC_SCENE = 4;
    public final int TYPE_PANORAMIC_SURROUND = 8;

    static {
        CarFunction.isSupportHifiNewEffectStyle();
        SOUND_STYLE_1_HIFI = 1;
        CarFunction.isSupportHifiNewEffectStyle();
        SOUND_STYLE_2_HIFI = 2;
        CarFunction.isSupportHifiNewEffectStyle();
        SOUND_STYLE_3_HIFI = 3;
        CarFunction.isSupportHifiNewEffectStyle();
        SOUND_STYLE_4_HIFI = 4;
    }

    public int getNativeSound() {
        return this.nativeSound;
    }

    public void setNativeSound(int i) {
        this.nativeSound = i;
    }

    public int getSoftSound() {
        return this.softSound;
    }

    public void setSoftSound(int i) {
        this.softSound = i;
    }

    public int getInnervationSound() {
        return this.innervationSound;
    }

    public void setInnervationSound(int i) {
        this.innervationSound = i;
    }
}
