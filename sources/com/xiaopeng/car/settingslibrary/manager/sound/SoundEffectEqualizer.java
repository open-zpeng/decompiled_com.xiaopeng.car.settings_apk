package com.xiaopeng.car.settingslibrary.manager.sound;
/* loaded from: classes.dex */
public enum SoundEffectEqualizer {
    SUBWOOFER(1),
    BASS(2),
    ALTO(4),
    HIGHPITCH(8);
    
    int mType;

    SoundEffectEqualizer(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
