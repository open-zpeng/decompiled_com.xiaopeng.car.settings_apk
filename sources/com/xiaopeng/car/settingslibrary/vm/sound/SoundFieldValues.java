package com.xiaopeng.car.settingslibrary.vm.sound;
/* loaded from: classes.dex */
public class SoundFieldValues {
    public static final int SOUND_FIELD_HIFI = 3;
    public static final int SOUND_FIELD_ORIGINAL = 4;
    public static final int SOUND_FIELD_ROUND = 1;
    private int type;
    private int xSound;
    private int ySound;

    public int getXSound() {
        return this.xSound;
    }

    public void setXSound(int i) {
        this.xSound = i;
    }

    public int getYSound() {
        return this.ySound;
    }

    public void setYSound(int i) {
        this.ySound = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getxSound() {
        return this.xSound;
    }

    public void setxSound(int i) {
        this.xSound = i;
    }

    public int getySound() {
        return this.ySound;
    }

    public void setySound(int i) {
        this.ySound = i;
    }

    public String toString() {
        return "SoundFieldValues{type=" + this.type + ", xSound=" + this.xSound + ", ySound=" + this.ySound + '}';
    }
}
