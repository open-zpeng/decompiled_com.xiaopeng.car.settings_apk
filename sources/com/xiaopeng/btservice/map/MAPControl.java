package com.xiaopeng.btservice.map;

import android.content.Context;
import android.os.RemoteException;
import com.nforetek.bt.aidl.UiCallbackMap;
import com.nforetek.bt.aidl.UiCommand;
import com.xiaopeng.btservice.base.AbsControl;
import com.xiaopeng.btservice.base.AbsMAPControlCallback;
/* loaded from: classes.dex */
public class MAPControl extends AbsControl {
    private static final String TAG = "PBAPControl";
    private AbsMAPControlCallback mCallback;
    private UiCallbackMap mCallbackMap = new UiCallbackMap.Stub() { // from class: com.xiaopeng.btservice.map.MAPControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapServiceReady() throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapServiceReady();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapStateChanged(String str, int i, int i2, int i3) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapStateChanged(str, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapDownloadedMessage(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, boolean z, String str7, String str8) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapDownloadedMessage(str, str2, str3, str4, str5, str6, i, i2, z, str7, str8);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapNewMessageReceivedEvent(String str, String str2, String str3, String str4) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapNewMessageReceivedEvent(str, str2, str3, str4);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapDownloadNotify(String str, int i, int i2, int i3) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapDownloadNotify(str, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapDatabaseAvailable() throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapDatabaseAvailable();
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapDeleteDatabaseByAddressCompleted(String str, boolean z) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapDeleteDatabaseByAddressCompleted(str, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapCleanDatabaseCompleted(boolean z) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapCleanDatabaseCompleted(z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapSendMessageCompleted(String str, String str2, int i) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapSendMessageCompleted(str, str2, i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapDeleteMessageCompleted(String str, String str2, int i) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapDeleteMessageCompleted(str, str2, i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void retMapChangeReadStatusCompleted(String str, String str2, int i) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.retMapChangeReadStatusCompleted(str, str2, i);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapMemoryAvailableEvent(String str, int i, boolean z) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapMemoryAvailableEvent(str, i, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapMessageSendingStatusEvent(String str, String str2, boolean z) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapMessageSendingStatusEvent(str, str2, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapMessageDeliverStatusEvent(String str, String str2, boolean z) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapMessageDeliverStatusEvent(str, str2, z);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapMessageShiftedEvent(String str, String str2, int i, int i2, int i3) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapMessageShiftedEvent(str, str2, i, i2, i3);
            }
        }

        @Override // com.nforetek.bt.aidl.UiCallbackMap
        public void onMapMessageDeletedEvent(String str, String str2, int i, int i2) throws RemoteException {
            if (MAPControl.this.mCallback != null) {
                MAPControl.this.mCallback.onMapMessageDeletedEvent(str, str2, i, i2);
            }
        }
    };

    public MAPControl(Context context, AbsMAPControlCallback absMAPControlCallback) {
        this.mContext = context;
        this.mCallback = absMAPControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            uiCommand.registerMapCallback(this.mCallbackMap);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            if (this.nForeService != null) {
                this.nForeService.unregisterMapCallback(this.mCallbackMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
