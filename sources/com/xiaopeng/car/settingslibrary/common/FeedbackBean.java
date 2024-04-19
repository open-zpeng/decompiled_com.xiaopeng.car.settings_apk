package com.xiaopeng.car.settingslibrary.common;

import android.text.TextUtils;
/* loaded from: classes.dex */
public class FeedbackBean {
    public static final int MEDIA_NONE = -1;
    public static final int MEDIA_PAUSE = 0;
    public static final int MEDIA_PLAYING = 1;
    public static final String TYPE_FUNCTION = "0";
    public static final String TYPE_OTHER = "2";
    public static final String TYPE_SPEECH = "1";
    public static final String TYPE_SUGGEST = "1";
    public static final String TYPE_TEXT = "0";
    private String type = "";
    private String content = "";
    private String createTime = "";
    private String category = "0";
    private String recordFilePath = "";
    private String status = "";
    private String hasRead = "";
    private String answerTime = "";
    private String answer = "";
    private String updateTime = "";

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        if (TextUtils.isEmpty(str)) {
            this.type = "";
        } else {
            this.type = str;
        }
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        if (TextUtils.isEmpty(str)) {
            this.content = "";
        } else {
            this.content = str;
        }
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        if (TextUtils.isEmpty(str)) {
            this.createTime = "";
        } else {
            this.createTime = str;
        }
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        if (TextUtils.isEmpty(str)) {
            this.category = "";
        } else {
            this.category = str;
        }
    }

    public String getRecordFilePath() {
        return this.recordFilePath;
    }

    public void setRecordFilePath(String str) {
        if (TextUtils.isEmpty(str)) {
            setType("0");
            this.recordFilePath = "";
            return;
        }
        setType("1");
        this.recordFilePath = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        if (TextUtils.isEmpty(str)) {
            this.status = "";
        } else {
            this.status = str;
        }
    }

    public String getHasRead() {
        return this.hasRead;
    }

    public void setHasRead(String str) {
        if (TextUtils.isEmpty(str)) {
            this.hasRead = "";
        } else {
            this.hasRead = str;
        }
    }

    public String getAnswerTime() {
        return this.answerTime;
    }

    public void setAnswerTime(String str) {
        if (TextUtils.isEmpty(str)) {
            this.answerTime = "";
        } else {
            this.answerTime = str;
        }
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String str) {
        if (TextUtils.isEmpty(str)) {
            this.answer = "";
        } else {
            this.answer = str;
        }
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        if (TextUtils.isEmpty(str)) {
            this.updateTime = "";
        } else {
            this.updateTime = str;
        }
    }

    public String toString() {
        return "FeedbackBean{type='" + this.type + "', content='" + this.content + "', createTime='" + this.createTime + "', category='" + this.category + "', recordFilePath='" + this.recordFilePath + "', status='" + this.status + "', hasRead='" + this.hasRead + "', answerTime='" + this.answerTime + "', answer='" + this.answer + "', updateTime='" + this.updateTime + "'}";
    }
}
