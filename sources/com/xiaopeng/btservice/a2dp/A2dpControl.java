package com.xiaopeng.btservice.a2dp;

import android.content.Context;
import com.nforetek.bt.aidl.UiCallbackA2dp;
import com.nforetek.bt.aidl.UiCommand;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.btservice.base.AbsA2dpControlCallback;
import com.xiaopeng.btservice.base.AbsControl;
/* loaded from: classes.dex */
public class A2dpControl extends AbsControl {
    private static final String TAG = "A2dpControl";
    private AbsA2dpControlCallback mCallback;
    private UiCallbackA2dp mCallbackA2dp = new UiCallbackA2dp.Stub() { // from class: com.xiaopeng.btservice.a2dp.A2dpControl.1
        @Override // com.nforetek.bt.aidl.UiCallbackA2dp
        public void onA2dpServiceReady() {
            A2dpControl.this.mCallback.onA2dpServiceReady();
        }

        @Override // com.nforetek.bt.aidl.UiCallbackA2dp
        public void onA2dpStateChanged(String str, int i, int i2) {
            A2dpControl.this.mCallback.onA2dpStateChanged(str, i, i2);
        }
    };

    public A2dpControl(Context context, AbsA2dpControlCallback absA2dpControlCallback) {
        this.mContext = context;
        this.mCallback = absA2dpControlCallback;
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void registerCallback(UiCommand uiCommand) {
        try {
            this.nForeService = uiCommand;
            uiCommand.registerA2dpCallback(this.mCallbackA2dp);
        } catch (Exception e) {
            printError(TAG, e);
        }
    }

    @Override // com.xiaopeng.btservice.base.AbsControl
    protected void release() {
        try {
            if (this.nForeService != null) {
                this.nForeService.unregisterA2dpCallback(this.mCallbackA2dp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        if (this.nForeService != null) {
            try {
                this.nForeService.isA2dpServiceReady();
                return false;
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
                return this.nForeService.isA2dpConnected();
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
                return this.nForeService.getA2dpConnectionState();
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
                return this.nForeService.reqA2dpConnect(str);
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
                this.nForeService.reqA2dpDisconnect(str);
                return false;
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
                return this.nForeService.getA2dpConnectedAddress();
            } catch (Exception e) {
                e.printStackTrace();
                return NfDef.DEFAULT_ADDRESS;
            }
        }
        return NfDef.DEFAULT_ADDRESS;
    }

    public boolean setLocalVolume(float f) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.setA2dpLocalVolume(f);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setStreamType(int i) {
        if (this.nForeService != null) {
            try {
                return this.nForeService.setA2dpStreamType(i);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public int getStreamType() {
        if (this.nForeService != null) {
            try {
                return this.nForeService.getA2dpStreamType();
            } catch (Exception e) {
                e.printStackTrace();
                return 3;
            }
        }
        return 3;
    }

    public boolean startRender() {
        if (this.nForeService != null) {
            try {
                this.nForeService.startA2dpRender();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean pauseRender() {
        if (this.nForeService != null) {
            try {
                this.nForeService.pauseA2dpRender();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
