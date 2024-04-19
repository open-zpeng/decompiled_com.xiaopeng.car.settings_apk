package com.xiaopeng.btservice.base;

import android.content.Context;
import android.util.Log;
import com.nforetek.bt.aidl.UiCommand;
import com.xiaopeng.btservice.base.AbstractConnection;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class AbsControl {
    List<AbstractConnection.ConnecteCompletedCallback> mConnectCompletedCallbacks = new ArrayList();
    public AbstractConnection mConnection = new AbstractConnection() { // from class: com.xiaopeng.btservice.base.AbsControl.1
        @Override // com.xiaopeng.btservice.base.AbstractConnection
        public void registerConnecteCompletedCallback(AbstractConnection.ConnecteCompletedCallback connecteCompletedCallback) {
            synchronized (AbsControl.this.mConnectCompletedCallbacks) {
                AbsControl.this.mConnectCompletedCallbacks.add(connecteCompletedCallback);
            }
        }

        @Override // com.xiaopeng.btservice.base.AbstractConnection
        public void unregisterConnecteCompletedCallback(AbstractConnection.ConnecteCompletedCallback connecteCompletedCallback) {
            synchronized (AbsControl.this.mConnectCompletedCallbacks) {
                AbsControl.this.mConnectCompletedCallbacks.remove(connecteCompletedCallback);
            }
        }

        private void notifyConnecteCompletedCallback() {
            synchronized (AbsControl.this.mConnectCompletedCallbacks) {
                for (AbstractConnection.ConnecteCompletedCallback connecteCompletedCallback : AbsControl.this.mConnectCompletedCallbacks) {
                    connecteCompletedCallback.connectServiceCompleted();
                }
            }
        }

        @Override // com.xiaopeng.btservice.base.AbstractConnection
        public void register(UiCommand uiCommand) {
            AbsControl.this.registerCallback(uiCommand);
            notifyConnecteCompletedCallback();
        }

        @Override // com.xiaopeng.btservice.base.AbstractConnection
        public void unRegister() {
            AbsControl.this.release();
        }

        @Override // com.xiaopeng.btservice.base.AbstractConnection
        public void onDisconnected() {
            AbsControl.this.release();
        }
    };
    public Context mContext;
    public UiCommand nForeService;

    protected abstract void registerCallback(UiCommand uiCommand);

    protected abstract void release();

    public String getHfpConnectedAddress(String str) {
        printLog(str, "getHfpConnectedAddress");
        UiCommand uiCommand = this.nForeService;
        if (uiCommand == null) {
            return "";
        }
        try {
            return uiCommand.getHfpConnectedAddress();
        } catch (Exception e) {
            printError(str, e);
            return "";
        }
    }

    public void printError(String str, Exception exc) {
        Log.e(str, "" + exc);
    }

    public void printLog(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(" pkg:");
        Context context = this.mContext;
        sb.append(context != null ? context.getPackageName() : "");
        Log.d(str, sb.toString());
        if (this.nForeService == null) {
            Log.d(str, "nForeService == null");
        }
    }
}
