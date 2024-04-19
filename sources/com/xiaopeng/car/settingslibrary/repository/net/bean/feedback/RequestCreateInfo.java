package com.xiaopeng.car.settingslibrary.repository.net.bean.feedback;
/* loaded from: classes.dex */
public class RequestCreateInfo extends BaseInfo {
    private String channel;
    private String content;
    private String logUrl;
    private String recordFilePath;
    private String type;
    private String uid;
    private String vin;

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getVin() {
        return this.vin;
    }

    public void setVin(String str) {
        this.vin = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getRecordFilePath() {
        return this.recordFilePath;
    }

    public void setRecordFilePath(String str) {
        this.recordFilePath = str;
    }

    public String getLogUrl() {
        return this.logUrl;
    }

    public void setLogUrl(String str) {
        this.logUrl = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String toString() {
        return "RequestCreateInfo{uid='" + this.uid + "', vin='" + this.vin + "', content='" + this.content + "', recordFilePath='" + this.recordFilePath + "', logUrl='" + this.logUrl + "', type='" + this.type + "'}";
    }
}
