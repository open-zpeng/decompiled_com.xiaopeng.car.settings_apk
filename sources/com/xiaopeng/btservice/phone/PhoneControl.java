package com.xiaopeng.btservice.phone;

import android.content.Context;
import android.util.Log;
import com.nforetek.bt.aidl.NfHfpClientCall;
import com.nforetek.bt.aidl.UiCallbackHfp;
import com.nforetek.bt.aidl.UiCommand;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.btservice.base.AbsPhoneControlCallback;
import java.util.Collections;
import java.util.List;
/* loaded from: classes.dex */
public class PhoneControl extends AbsControl {
    private static final String TAG = "PhoneControl";
    private AbsPhoneControlCallback mCallback;
    private UiCallbackHfp mCallbackHfp = new UiCallbackHfp.Stub() { // from class: com.xiaopeng.btservice.phone.PhoneControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpServiceReady() {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpServiceReady();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpStateChanged(String str, int i, int i2) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpAudioStateChanged(String str, int i, int i2) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpAudioStateChanged(str, i, i2);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpVoiceDial(String str, boolean z) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpVoiceDial(str, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpErrorResponse(String str, int i) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpErrorResponse(str, i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteTelecomService(String str, boolean z) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpRemoteTelecomService(str, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteRoamingStatus(String str, boolean z) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpRemoteRoamingStatus(str, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteBatteryIndicator(String str, int i, int i2, int i3) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpRemoteBatteryIndicator(str, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteSignalStrength(String str, int i, int i2, int i3) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpRemoteSignalStrength(str, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void onHfpCallChanged(String str, NfHfpClientCall nfHfpClientCall) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.onHfpCallChanged(str, nfHfpClientCall.getId(), nfHfpClientCall.getState(), nfHfpClientCall.getNumber(), nfHfpClientCall.isMultiParty(), nfHfpClientCall.isOutgoing());
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackHfp
        public void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) {
            if (PhoneControl.this.mCallback != null) {
                PhoneControl.this.mCallback.retPbapDatabaseQueryNameByNumber(str, str2, str3, z);
            }
        }
    };

    public PhoneControl(Context context, AbsPhoneControlCallback absPhoneControlCallback) {
        this.mContext = context;
        this.mCallback = absPhoneControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            uiCommand.registerHfpCallback(this.mCallbackHfp);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            if (this.nForeService != null) {
                this.nForeService.unregisterHfpCallback(this.mCallbackHfp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHfpConnectionState() {
        printLog(TAG, "getHfpConnectionState");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getHfpConnectionState();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public boolean isHfpConnected() {
        printLog(TAG, "isHfpConnected");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isHfpConnected();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean dialNumber(String str) {
        printLog(TAG, "dialNumber");
        if (this.nForeService == null) {
            return false;
        }
        try {
            boolean isHfpRemoteTelecomServiceOn = this.nForeService.isHfpRemoteTelecomServiceOn();
            Log.v("yfl", "isRemoteTelecomServiceOn = " + isHfpRemoteTelecomServiceOn);
            return this.nForeService.reqHfpDialCall(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpReDial() {
        printLog(TAG, "reqHfpReDial");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpReDial();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpRejectIncomingCall() {
        printLog(TAG, "reqHfpRejectIncomingCall");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpRejectIncomingCall();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpTerminateCurrentCall() {
        printLog(TAG, "reqHfpTerminateCurrentCall");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpTerminateCurrentCall();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpAnswerCall(int i) {
        printLog(TAG, "reqHfpAnswerCall");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpAnswerCall(i);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpAudioTransferToCarkit() {
        printLog(TAG, "reqHfpAudioTransferToCarkit");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpAudioTransferToCarkit();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpAudioTransferToPhone() {
        printLog(TAG, "reqHfpAudioTransferToPhone");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpAudioTransferToPhone();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpVoiceDial(boolean z) {
        printLog(TAG, "reqHfpVoiceDial");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpVoiceDial(z);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public boolean reqHfpSendDtmf(String str) {
        printLog(TAG, "reqHfpSendDtmf");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.reqHfpSendDtmf(str);
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public String getHfpRemoteSubscriberNumber() {
        printLog(TAG, "getHfpRemoteSubscriberNumber");
        if (this.nForeService == null) {
            return null;
        }
        try {
            return this.nForeService.getHfpRemoteSubscriberNumber();
        } catch (Exception e) {
            printError(TAG, e);
            return null;
        }
    }

    public String getHfpRemoteNetworkOperator() {
        printLog(TAG, "getHfpRemoteNetworkOperator");
        if (this.nForeService == null) {
            return null;
        }
        try {
            return this.nForeService.getHfpRemoteNetworkOperator();
        } catch (Exception e) {
            printError(TAG, e);
            return null;
        }
    }

    public boolean isCurrentInCall(OnCurrentInCallListener onCurrentInCallListener) {
        printLog(TAG, "isCurrentInCall");
        if (this.nForeService == null) {
            return false;
        }
        try {
            List<NfHfpClientCall> hfpCallList = this.nForeService.getHfpCallList();
            if (hfpCallList != null && hfpCallList.size() > 0) {
                if (onCurrentInCallListener != null) {
                    NfHfpClientCall nfHfpClientCall = hfpCallList.get(0);
                    onCurrentInCallListener.onHfpCallChanged(getHfpConnectedAddress(TAG), nfHfpClientCall.getId(), nfHfpClientCall.getState(), nfHfpClientCall.getNumber(), nfHfpClientCall.isMultiParty(), nfHfpClientCall.isOutgoing());
                    return true;
                }
                return true;
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return false;
    }

    public boolean isHas2ndCall() {
        printLog(TAG, "isHas2ndCall");
        if (this.nForeService == null) {
            return false;
        }
        try {
            List<NfHfpClientCall> hfpCallList = this.nForeService.getHfpCallList();
            if (hfpCallList != null) {
                if (hfpCallList.size() > 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            printError(TAG, e);
        }
        return false;
    }

    public boolean isHfpInBandRingtoneSupport() {
        printLog(TAG, "isHfpInBandRingtoneSupport");
        if (this.nForeService == null) {
            return false;
        }
        try {
            return this.nForeService.isHfpInBandRingtoneSupport();
        } catch (Exception e) {
            printError(TAG, e);
            return false;
        }
    }

    public int getHfpRemoteBatteryIndicator() {
        printLog(TAG, "getHfpRemoteBatteryIndicator");
        if (this.nForeService == null) {
            return -1;
        }
        try {
            return this.nForeService.getHfpRemoteBatteryIndicator();
        } catch (Exception e) {
            printError(TAG, e);
            return -1;
        }
    }

    public List<NfHfpClientCall> getCallList() {
        printLog(TAG, "getCallList");
        if (this.nForeService != null) {
            try {
                return this.nForeService.getHfpCallList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    public int getAudioConnectionState() {
        printLog(TAG, "getAudioConnectionState");
        if (this.nForeService != null) {
            try {
                return this.nForeService.getHfpAudioConnectionState();
            } catch (Exception e) {
                e.printStackTrace();
                return 100;
            }
        }
        return 100;
    }
}
