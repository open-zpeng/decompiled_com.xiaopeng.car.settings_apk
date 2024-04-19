package com.xiaopeng.btservice.base;
/* loaded from: classes.dex */
public abstract class AbsPhoneControlCallback {
    public void onHfpAudioStateChanged(String str, int i, int i2) {
    }

    public void onHfpCallChanged(String str, int i, int i2, String str2, boolean z, boolean z2) {
    }

    public void onHfpErrorResponse(String str, int i) {
    }

    public void onHfpRemoteBatteryIndicator(String str, int i, int i2, int i3) {
    }

    public void onHfpRemoteRoamingStatus(String str, boolean z) {
    }

    public void onHfpRemoteSignalStrength(String str, int i, int i2, int i3) {
    }

    public void onHfpRemoteTelecomService(String str, boolean z) {
    }

    public void onHfpServiceReady() {
    }

    public void onHfpStateChanged(String str, int i, int i2) {
    }

    public void onHfpVoiceDial(String str, boolean z) {
    }

    public void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) {
    }
}
