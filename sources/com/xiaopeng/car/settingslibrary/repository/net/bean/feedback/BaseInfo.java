package com.xiaopeng.car.settingslibrary.repository.net.bean.feedback;
/* loaded from: classes.dex */
public class BaseInfo {
    private String requestId;
    private long requestTime;

    public long getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(long j) {
        this.requestTime = j;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }
}
