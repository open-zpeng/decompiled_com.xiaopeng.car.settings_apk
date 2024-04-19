package com.xiaopeng.car.settingslibrary.direct;

import com.xiaopeng.car.settingslibrary.direct.support.SupportCheckAction;
/* loaded from: classes.dex */
public class ElementDirectItem {
    private int id;
    private String key;
    private SupportCheckAction mSupportAction;
    private int parentId;

    public ElementDirectItem(String str, int i) {
        this.key = str;
        this.id = i;
    }

    public ElementDirectItem(String str, int i, int i2) {
        this.key = str;
        this.id = i;
        this.parentId = i2;
    }

    public ElementDirectItem(SupportCheckAction supportCheckAction) {
        this.mSupportAction = supportCheckAction;
    }

    public ElementDirectItem(String str, int i, SupportCheckAction supportCheckAction) {
        this.key = str;
        this.id = i;
        this.mSupportAction = supportCheckAction;
    }

    public ElementDirectItem(String str, int i, int i2, SupportCheckAction supportCheckAction) {
        this.key = str;
        this.id = i;
        this.parentId = i2;
        this.mSupportAction = supportCheckAction;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int i) {
        this.parentId = i;
    }

    public SupportCheckAction getSupportAction() {
        return this.mSupportAction;
    }

    public void setSupportAction(SupportCheckAction supportCheckAction) {
        this.mSupportAction = supportCheckAction;
    }

    public String toString() {
        return "ElementDirectItem{key='" + this.key + "', id=" + this.id + ", parentId=" + this.parentId + ", mSupportAction=" + this.mSupportAction + '}';
    }
}
