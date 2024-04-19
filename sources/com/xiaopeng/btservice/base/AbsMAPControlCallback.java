package com.xiaopeng.btservice.base;
/* loaded from: classes.dex */
public abstract class AbsMAPControlCallback {
    public void onMapDownloadNotify(String str, int i, int i2, int i3) {
    }

    public void onMapMemoryAvailableEvent(String str, int i, boolean z) {
    }

    public void onMapMessageDeletedEvent(String str, String str2, int i, int i2) {
    }

    public void onMapMessageDeliverStatusEvent(String str, String str2, boolean z) {
    }

    public void onMapMessageSendingStatusEvent(String str, String str2, boolean z) {
    }

    public void onMapMessageShiftedEvent(String str, String str2, int i, int i2, int i3) {
    }

    public void onMapNewMessageReceivedEvent(String str, String str2, String str3, String str4) {
    }

    public void onMapServiceReady() {
    }

    public void onMapStateChanged(String str, int i, int i2, int i3) {
    }

    public void retMapChangeReadStatusCompleted(String str, String str2, int i) {
    }

    public void retMapCleanDatabaseCompleted(boolean z) {
    }

    public void retMapDatabaseAvailable() {
    }

    public void retMapDeleteDatabaseByAddressCompleted(String str, boolean z) {
    }

    public void retMapDeleteMessageCompleted(String str, String str2, int i) {
    }

    public void retMapDownloadedMessage(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, boolean z, String str7, String str8) {
    }

    public void retMapSendMessageCompleted(String str, String str2, int i) {
    }
}
