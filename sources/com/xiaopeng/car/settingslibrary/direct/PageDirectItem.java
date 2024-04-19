package com.xiaopeng.car.settingslibrary.direct;

import com.xiaopeng.car.settingslibrary.direct.action.PageAction;
import com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction;
/* loaded from: classes.dex */
public class PageDirectItem {
    private Object data;
    private PageAction mPageAction;
    private SupportCheckAction mSupportAction;
    private String secondName;

    public PageDirectItem(PageAction pageAction) {
        this.mPageAction = pageAction;
    }

    public PageDirectItem(SupportCheckAction supportCheckAction) {
        this.mSupportAction = supportCheckAction;
    }

    public PageDirectItem(Object obj, PageAction pageAction) {
        this.data = obj;
        this.mPageAction = pageAction;
    }

    public PageDirectItem(PageAction pageAction, SupportCheckAction supportCheckAction) {
        this.mPageAction = pageAction;
        this.mSupportAction = supportCheckAction;
    }

    public PageDirectItem(String str, Object obj, PageAction pageAction) {
        this.data = obj;
        this.mPageAction = pageAction;
        this.secondName = str;
    }

    public PageDirectItem(Object obj, PageAction pageAction, SupportCheckAction supportCheckAction) {
        this.data = obj;
        this.mPageAction = pageAction;
        this.mSupportAction = supportCheckAction;
    }

    public PageDirectItem(String str, Object obj, PageAction pageAction, SupportCheckAction supportCheckAction) {
        this.data = obj;
        this.mPageAction = pageAction;
        this.mSupportAction = supportCheckAction;
        this.secondName = str;
    }

    public SupportCheckAction getSupportAction() {
        return this.mSupportAction;
    }

    public void setSupportAction(SupportCheckAction supportCheckAction) {
        this.mSupportAction = supportCheckAction;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public PageAction getPageAction() {
        return this.mPageAction;
    }

    public void setPageAction(PageAction pageAction) {
        this.mPageAction = pageAction;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String str) {
        this.secondName = str;
    }

    public String toString() {
        return "PageDirectItem{data=" + this.data + ", mPageAction=" + this.mPageAction + ", mSupportAction=" + this.mSupportAction + ", secondName='" + this.secondName + "'}";
    }
}
