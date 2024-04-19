package com.xiaopeng.btservice.base;
/* loaded from: classes.dex */
public abstract class AbsAvrcpControlCallback {
    public void onAvrcp13EventBatteryStatusChanged(byte b) {
    }

    public void onAvrcp13EventPlaybackPosChanged(long j) {
    }

    public void onAvrcp13EventPlaybackStatusChanged(byte b) {
    }

    public void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2) {
    }

    public void onAvrcp13EventSystemStatusChanged(byte b) {
    }

    public void onAvrcp13EventTrackChanged(long j) {
    }

    public void onAvrcp13EventTrackReachedEnd() {
    }

    public void onAvrcp13EventTrackReachedStart() {
    }

    public void onAvrcp13RegisterEventWatcherFail(byte b) {
    }

    public void onAvrcp13RegisterEventWatcherSuccess(byte b) {
    }

    public void onAvrcp14EventAddressedPlayerChanged(int i, int i2) {
    }

    public void onAvrcp14EventAvailablePlayerChanged() {
    }

    public void onAvrcp14EventNowPlayingContentChanged() {
    }

    public void onAvrcp14EventUidsChanged(int i) {
    }

    public void onAvrcp14EventVolumeChanged(byte b) {
    }

    public void onAvrcpErrorResponse(int i, int i2, byte b) {
    }

    public void onAvrcpServiceReady() {
    }

    public void onAvrcpStateChanged(String str, int i, int i2) {
    }

    public void retAvrcp13CapabilitiesSupportEvent(byte[] bArr) {
    }

    public void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr) {
    }

    public void retAvrcp13PlayStatus(long j, long j2, byte b) {
    }

    public void retAvrcp13PlayerSettingAttributesList(byte[] bArr) {
    }

    public void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2) {
    }

    public void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr) {
    }

    public void retAvrcp13SetPlayerSettingValueSuccess() {
    }

    public void retAvrcp14AddToNowPlayingSuccess() {
    }

    public void retAvrcp14ChangePathSuccess(long j) {
    }

    public void retAvrcp14FolderItems(int i, long j) {
    }

    public void retAvrcp14ItemAttributes(int[] iArr, String[] strArr) {
    }

    public void retAvrcp14MediaItems(int i, long j) {
    }

    public void retAvrcp14PlaySelectedItemSuccess() {
    }

    public void retAvrcp14SearchResult(int i, long j) {
    }

    public void retAvrcp14SetAbsoluteVolumeSuccess(byte b) {
    }

    public void retAvrcp14SetAddressedPlayerSuccess() {
    }

    public void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j) {
    }

    public void retAvrcpUpdateSongStatus(String str, String str2, String str3) {
    }
}
