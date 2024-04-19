package com.xiaopeng.car.settingslibrary.interfaceui.constant;
/* loaded from: classes.dex */
public class SoundBean {
    private boolean fromUI;
    private int mode;
    private String path;
    private int playState;
    private int progress;
    private int streamType;
    private String text;
    private int type;
    private String utteranceId;
    private int xSound;
    private int ySound;

    public SoundBean() {
    }

    public SoundBean(int i, int i2) {
        this.streamType = i;
        this.progress = i2;
    }

    public SoundBean(int i, String str, String str2) {
        this.streamType = i;
        this.text = str;
        this.utteranceId = str2;
    }

    public SoundBean(int i, int i2, int i3, boolean z) {
        this.mode = i;
        this.xSound = i2;
        this.ySound = i3;
        this.fromUI = z;
    }

    public SoundBean(int i, boolean z, int i2) {
        this.mode = i;
        this.fromUI = z;
        this.type = i2;
    }

    public SoundBean(int i, boolean z) {
        this.mode = i;
        this.fromUI = z;
    }

    public SoundBean(String str, int i) {
        this.path = str;
        this.playState = i;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public void setStreamType(int i) {
        this.streamType = i;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getUtteranceId() {
        return this.utteranceId;
    }

    public void setUtteranceId(String str) {
        this.utteranceId = str;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
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

    public boolean isFromUI() {
        return this.fromUI;
    }

    public void setFromUI(boolean z) {
        this.fromUI = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getPlayState() {
        return this.playState;
    }

    public void setPlayState(int i) {
        this.playState = i;
    }

    public String toString() {
        return "SoundBean{streamType=" + this.streamType + ", progress=" + this.progress + ", text='" + this.text + "', utteranceId='" + this.utteranceId + "', mode=" + this.mode + ", xSound=" + this.xSound + ", ySound=" + this.ySound + ", fromUI=" + this.fromUI + ", type=" + this.type + ", path='" + this.path + "', playState=" + this.playState + '}';
    }
}
