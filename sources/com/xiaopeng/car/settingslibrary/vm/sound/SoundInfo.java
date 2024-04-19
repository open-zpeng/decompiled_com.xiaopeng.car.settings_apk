package com.xiaopeng.car.settingslibrary.vm.sound;
/* loaded from: classes.dex */
public class SoundInfo {
    private int avasVolume;
    private int musicVolume;
    private int navigationVolume;
    private int psnBTVolume;
    private int speechVolume;

    public int getMusicVolume() {
        return this.musicVolume;
    }

    public void setMusicVolume(int i) {
        this.musicVolume = i;
    }

    public int getSpeechVolume() {
        return this.speechVolume;
    }

    public void setSpeechVolume(int i) {
        this.speechVolume = i;
    }

    public int getNavigationVolume() {
        return this.navigationVolume;
    }

    public void setNavigationVolume(int i) {
        this.navigationVolume = i;
    }

    public int getAvasVolume() {
        return this.avasVolume;
    }

    public void setAvasVolume(int i) {
        this.avasVolume = i;
    }

    public int getPsnBTVolume() {
        return this.psnBTVolume;
    }

    public void setPsnBTVolume(int i) {
        this.psnBTVolume = i;
    }

    public String toString() {
        return "SoundInfo{musicVolume=" + this.musicVolume + ", speechVolume=" + this.speechVolume + ", navigationVolume=" + this.navigationVolume + ", avasVolume=" + this.avasVolume + ", psnBTVolume=" + this.psnBTVolume + '}';
    }
}
