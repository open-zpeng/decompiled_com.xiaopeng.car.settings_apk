package com.xiaopeng.btservice.avrcp;

import android.content.Context;
import com.nforetek.bt.aidl.UiCallbackAvrcp;
import com.nforetek.bt.aidl.UiCommand;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.btservice.base.AbsAvrcpControlCallback;
import com.xiaopeng.btservice.base.AbsControl;
/* loaded from: classes.dex */
public class AvrcpControl extends AbsControl {
    private static final String TAG = "AvrcpControl";
    private AbsAvrcpControlCallback mCallback;
    private UiCallbackAvrcp mCallbackAvrcp = new UiCallbackAvrcp.Stub() { // from class: com.xiaopeng.btservice.avrcp.AvrcpControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpServiceReady() {
            AvrcpControl.this.mCallback.onAvrcpServiceReady();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpStateChanged(String str, int i, int i2) {
            AvrcpControl.this.mCallback.onAvrcpStateChanged(str, i, i2);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr) {
            AvrcpControl.this.mCallback.retAvrcp13PlayerSettingValuesList(b, bArr);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2) {
            AvrcpControl.this.mCallback.retAvrcp13PlayerSettingCurrentValues(bArr, bArr2);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() {
            AvrcpControl.this.mCallback.retAvrcp13SetPlayerSettingValueSuccess();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr) {
            AvrcpControl.this.mCallback.retAvrcp13ElementAttributesPlaying(iArr, strArr);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayStatus(long j, long j2, byte b) {
            AvrcpControl.this.mCallback.retAvrcp13PlayStatus(j, j2, b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte b) {
            AvrcpControl.this.mCallback.onAvrcp13RegisterEventWatcherSuccess(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte b) {
            AvrcpControl.this.mCallback.onAvrcp13RegisterEventWatcherFail(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte b) {
            AvrcpControl.this.mCallback.onAvrcp13EventPlaybackStatusChanged(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long j) {
            AvrcpControl.this.mCallback.onAvrcp13EventTrackChanged(j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() {
            AvrcpControl.this.mCallback.onAvrcp13EventTrackReachedEnd();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() {
            AvrcpControl.this.mCallback.onAvrcp13EventTrackReachedStart();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long j) {
            AvrcpControl.this.mCallback.onAvrcp13EventPlaybackPosChanged(j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte b) {
            AvrcpControl.this.mCallback.onAvrcp13EventBatteryStatusChanged(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte b) {
            AvrcpControl.this.mCallback.onAvrcp13EventSystemStatusChanged(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2) {
            AvrcpControl.this.mCallback.onAvrcp13EventPlayerSettingChanged(bArr, bArr2);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() {
            AvrcpControl.this.mCallback.onAvrcp14EventNowPlayingContentChanged();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() {
            AvrcpControl.this.mCallback.onAvrcp14EventAvailablePlayerChanged();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int i, int i2) {
            AvrcpControl.this.mCallback.onAvrcp14EventAddressedPlayerChanged(i, i2);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int i) {
            AvrcpControl.this.mCallback.onAvrcp14EventUidsChanged(i);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte b) {
            AvrcpControl.this.mCallback.onAvrcp14EventVolumeChanged(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() {
            AvrcpControl.this.mCallback.retAvrcp14SetAddressedPlayerSuccess();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j) {
            AvrcpControl.this.mCallback.retAvrcp14SetBrowsedPlayerSuccess(strArr, i, j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14FolderItems(int i, long j) {
            AvrcpControl.this.mCallback.retAvrcp14FolderItems(i, j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14MediaItems(int i, long j) {
            AvrcpControl.this.mCallback.retAvrcp14MediaItems(i, j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long j) {
            AvrcpControl.this.mCallback.retAvrcp14ChangePathSuccess(j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] iArr, String[] strArr) {
            AvrcpControl.this.mCallback.retAvrcp14ItemAttributes(iArr, strArr);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() {
            AvrcpControl.this.mCallback.retAvrcp14PlaySelectedItemSuccess();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SearchResult(int i, long j) {
            AvrcpControl.this.mCallback.retAvrcp14SearchResult(i, j);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() {
            AvrcpControl.this.mCallback.retAvrcp14AddToNowPlayingSuccess();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte b) {
            AvrcpControl.this.mCallback.retAvrcp14SetAbsoluteVolumeSuccess(b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpErrorResponse(int i, int i2, byte b) {
            AvrcpControl.this.mCallback.onAvrcpErrorResponse(i, i2, b);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] bArr) {
            AvrcpControl.this.mCallback.retAvrcp13CapabilitiesSupportEvent(bArr);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] bArr) {
            AvrcpControl.this.mCallback.retAvrcp13PlayerSettingAttributesList(bArr);
        }

        @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpUpdateSongStatus(String str, String str2, String str3) {
            AvrcpControl.this.mCallback.retAvrcpUpdateSongStatus(str, str2, str3);
        }
    };

    public AvrcpControl(Context context, AbsAvrcpControlCallback absAvrcpControlCallback) {
        this.mContext = context;
        this.mCallback = absAvrcpControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            uiCommand.registerAvrcpCallback(this.mCallbackAvrcp);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            if (this.nForeService != null) {
                this.nForeService.unregisterAvrcpCallback(this.mCallbackAvrcp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.isAvrcpServiceReady();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean isConnected() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.isAvrcpConnected();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public int getConnectionState(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.getAvrcpConnectionState();
            } catch (Exception e) {
                e.printStackTrace();
                return 100;
            }
        }
        return 100;
    }

    public boolean connect(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpConnect(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean disconnect(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpDisconnect(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public String getConnectedAddress() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.getAvrcpConnectedAddress();
            } catch (Exception e) {
                e.printStackTrace();
                return NfDef.DEFAULT_ADDRESS;
            }
        }
        return NfDef.DEFAULT_ADDRESS;
    }

    public boolean is13Supported(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.isAvrcp13Supported(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean is14Supported(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.isAvrcp14Supported(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean play() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpPlay();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean stop() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpStop();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean pause() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpPause();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean forward() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpForward();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean backward() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpBackward();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean volumeUp() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpVolumeUp();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean volumeDown() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpVolumeDown();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean startFastForward() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpStartFastForward();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean stopFastForward() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpStopFastForward();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean startRewind() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpStartRewind();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean stopRewind() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpStopRewind();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getCapabilitiesSupportEvents() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetCapabilitiesSupportEvent();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getPlayerSettingAttributesList() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetPlayerSettingAttributesList();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getPlayerSettingValuesList(byte b) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetPlayerSettingValuesList(b);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getPlayerSettingCurrentValues() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetPlayerSettingCurrentValues();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setPlayerSettingValue(byte b, byte b2) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13SetPlayerSettingValue(b, b2);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getElementAttributesPlaying() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetElementAttributesPlaying();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getPlayStatus() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13GetPlayStatus();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean registerEventWatcher(byte b, long j) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpRegisterEventWatcher(b, j);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean unregisterEventWatcher(byte b) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcpUnregisterEventWatcher(b);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean nextGroup() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13NextGroup();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean previousGroup() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp13PreviousGroup();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean browsingChannelEstablished() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.isAvrcp14BrowsingChannelEstablished();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setAddressedPlayer(int i) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14SetAddressedPlayer(i);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setBrowsedPlayer(int i) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14SetBrowsedPlayer(i);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getFolderItems(byte b) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14GetFolderItems(b);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean changePath(int i, long j, byte b) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14ChangePath(i, j, b);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean getItemAttributes(byte b, int i, long j) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14GetItemAttributes(b, i, j);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean requestPlaySelectedItem(byte b, int i, long j) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14PlaySelectedItem(b, i, j);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean search(String str) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14Search(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean addToNowPlaying(byte b, int i, long j) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14AddToNowPlaying(b, i, j);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setAbsoluteVolume(byte b) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqAvrcp14SetAbsoluteVolume(b);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
