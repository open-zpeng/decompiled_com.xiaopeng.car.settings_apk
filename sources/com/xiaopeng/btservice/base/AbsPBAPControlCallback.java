package com.xiaopeng.btservice.base;
/* loaded from: classes.dex */
public abstract class AbsPBAPControlCallback {
    public void onPbapDownloadNotify(String str, int i, int i2, int i3) {
    }

    public void onPbapServiceReady() {
    }

    public void onPbapStateChanged(String str, int i, int i2, int i3, int i4) {
    }

    public void retPbapCleanDatabaseCompleted(boolean z) {
    }

    public void retPbapDatabaseAvailable(String str) {
    }

    public void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) {
    }

    public void retPbapDatabaseQueryNameByPartialNumber(String str, String str2, String[] strArr, String[] strArr2, boolean z) {
    }

    public void retPbapDeleteDatabaseByAddressCompleted(String str, boolean z) {
    }

    public void retPbapDownloadedCallLog(String str, String str2, String str3, String str4, String str5, int i, String str6) {
    }

    public void retPbapDownloadedContact(String str, String str2, String str3, String str4, String[] strArr, int i, byte[] bArr) {
    }
}
