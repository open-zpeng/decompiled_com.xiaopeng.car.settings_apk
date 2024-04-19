package com.xiaopeng.car.settingslibrary.common.net;
/* loaded from: classes.dex */
public class XpApiResponse<T> {
    public String code;
    public T data;
    public String msg;

    public boolean isSuccessful() {
        return "200".equals(this.code);
    }

    public String toString() {
        return "XpApiResponse{code='" + this.code + "', msg='" + this.msg + "', body=" + this.data + '}';
    }
}
