package com.xiaopeng.car.settingslibrary.repository.net.bean.feedback;
/* loaded from: classes.dex */
public class RequestHistory extends BaseInfo {
    private String channel;
    private String page;
    private String rows;
    private String vin;

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public String getVin() {
        return this.vin;
    }

    public void setVin(String str) {
        this.vin = str;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String str) {
        this.page = str;
    }

    public String getRows() {
        return this.rows;
    }

    public void setRows(String str) {
        this.rows = str;
    }
}
