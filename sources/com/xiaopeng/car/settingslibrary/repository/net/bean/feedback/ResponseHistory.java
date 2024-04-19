package com.xiaopeng.car.settingslibrary.repository.net.bean.feedback;
/* loaded from: classes.dex */
public class ResponseHistory {
    private String answer;
    private String answerTime;
    private String content;
    private String createTime;
    private String hasRead;
    private String recordFilePath;
    private String status;
    private String type;

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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public String getAnswerTime() {
        return this.answerTime;
    }

    public void setAnswerTime(String str) {
        this.answerTime = str;
    }

    public String getHasRead() {
        return this.hasRead;
    }

    public void setHasRead(String str) {
        this.hasRead = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
