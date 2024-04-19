package com.xiaopeng.car.settingslibrary.manager.about.beans;
/* loaded from: classes.dex */
public class OTAInfo {
    private int auto;
    private int schedule;
    private String scheduleTime;
    private int state;
    private String versionName;

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public int getSchedule() {
        return this.schedule;
    }

    public void setSchedule(int i) {
        this.schedule = i;
    }

    public String getScheduleTime() {
        return this.scheduleTime;
    }

    public void setScheduleTime(String str) {
        this.scheduleTime = str;
    }

    public int getAuto() {
        return this.auto;
    }

    public void setAuto(int i) {
        this.auto = i;
    }

    public String toString() {
        return "OTAInfo{state=" + this.state + ", versionName='" + this.versionName + "', schedule=" + this.schedule + ", scheduleTime='" + this.scheduleTime + "', auto=" + this.auto + '}';
    }
}
