package com.xiaopeng.btservice.pbap;

import android.content.Context;
import android.util.Log;
import com.nforetek.bt.aidl.NfPbapContact;
import com.nforetek.bt.aidl.UiCallbackPbap;
import com.nforetek.bt.aidl.UiCommand;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.btservice.base.AbsPBAPControlCallback;
/* loaded from: classes.dex */
public class PBAPControl extends AbsControl {
    private static final String TAG = "PBAPControl";
    private AbsPBAPControlCallback mCallback;
    private UiCallbackPbap mCallbackPbap = new UiCallbackPbap.Stub() { // from class: com.xiaopeng.btservice.pbap.PBAPControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void onPbapServiceReady() {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.onPbapServiceReady();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void onPbapStateChanged(String str, int i, int i2, int i3, int i4) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.onPbapStateChanged(str, i, i2, i3, i4);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedContact(NfPbapContact nfPbapContact) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDownloadedContact(nfPbapContact.getRemoteAddress(), nfPbapContact.getFirstName(), nfPbapContact.getMiddleName(), nfPbapContact.getLastName(), nfPbapContact.getNumberArray(), nfPbapContact.getPhotoType(), nfPbapContact.getPhotoByteArray());
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedCallLog(String str, String str2, String str3, String str4, String str5, int i, String str6) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDownloadedCallLog(str, str2, str3, str4, str5, i, str6);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void onPbapDownloadNotify(String str, int i, int i2, int i3) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.onPbapDownloadNotify(str, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDatabaseQueryNameByNumber(str, str2, str3, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByPartialNumber(String str, String str2, String[] strArr, String[] strArr2, boolean z) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDatabaseQueryNameByPartialNumber(str, str2, strArr, strArr2, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseAvailable(String str) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDatabaseAvailable(str);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapDeleteDatabaseByAddressCompleted(String str, boolean z) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapDeleteDatabaseByAddressCompleted(str, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackPbap
        public void retPbapCleanDatabaseCompleted(boolean z) {
            if (PBAPControl.this.mCallback != null) {
                PBAPControl.this.mCallback.retPbapCleanDatabaseCompleted(z);
            }
        }
    };

    public PBAPControl(Context context, AbsPBAPControlCallback absPBAPControlCallback) {
        this.mContext = context;
        this.mCallback = absPBAPControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            uiCommand.registerPbapCallback(this.mCallbackPbap);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        printLog(TAG, "release");
        try {
            if (this.nForeService != null) {
                this.nForeService.unregisterPbapCallback(this.mCallbackPbap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setPbapDownloadNotify(int i) {
        if (this.nForeService == null) {
            Log.d(TAG, "nForeService == null");
            return false;
        }
        try {
            return this.nForeService.setPbapDownloadNotify(i);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getPbapConnectionState() {
        printLog(TAG, "getPbapConnectionState");
        if (this.nForeService != null) {
            try {
                return this.nForeService.getPbapConnectionState();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public boolean reqPbapDownload(String str, int i, int i2) {
        printLog(TAG, "reqPbapDownload");
        if (this.nForeService != null) {
            try {
                if (i == 2) {
                    return this.nForeService.reqPbapDownload(str, i, i2);
                }
                return this.nForeService.reqPbapDownloadRange(str, i, i2, 0, 50);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean reqPbapDownloadInterrupt(String str) {
        printLog(TAG, "reqPbapDownloadInterrupt");
        if (this.nForeService != null) {
            try {
                return this.nForeService.reqPbapDownloadInterrupt(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean isPbapDownloading() {
        printLog(TAG, "isPbapDownloading");
        if (this.nForeService != null) {
            try {
                return this.nForeService.isPbapDownloading();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
