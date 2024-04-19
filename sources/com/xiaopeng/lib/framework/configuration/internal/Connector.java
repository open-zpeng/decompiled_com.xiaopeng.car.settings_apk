package com.xiaopeng.lib.framework.configuration.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.lib.framework.configuration.ConfigurationDataImpl;
import com.xiaopeng.lib.framework.configuration.IConfigurationServiceCallback;
import com.xiaopeng.lib.framework.configuration.IConfigurationServiceInterface;
import com.xiaopeng.lib.framework.configuration.internal.Proxy;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.ConfigurationChangeEvent;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes.dex */
final class Connector {
    private static final int MSG_BIND_REQUEST = 1;
    private static final int MSG_BIND_SUCCESS = 2;
    private static final int MSG_BIND_TIMEOUT = 3;
    private static final String REMOTE_CLASS_NAME = "com.xiaopeng.configurationcenter.connector.RemoteService";
    private static final String REMOTE_PACKAGE_NAME = "com.xiaopeng.configurationcenter";
    private static final String REMOTE_SERVICE_ACTION = "com.xiaopeng.configurationcenter.connector.RemoteService.CONNECT";
    private static final String TAG = "Connector";
    private Handler mHandler;
    private Proxy.ContextProvider mProvider;
    private volatile IConfigurationServiceInterface mService;
    private ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.lib.framework.configuration.internal.Connector.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Connector.this.mService = IConfigurationServiceInterface.Stub.asInterface(iBinder);
            Log.i(Connector.TAG, "onServiceConnected: service:" + Connector.this.mService);
            if (Connector.this.mService != null) {
                try {
                    Connector.this.mService.subscribe(Connector.this.mProvider.getAppID(), Connector.this.mProvider.getAppVersionName(), Connector.this.mProvider.getAppVersionCode(), Connector.this.mCallback);
                    Connector.this.mService.asBinder().linkToDeath(Connector.this.mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Connector.this.mHandler.sendEmptyMessage(2);
                EventBus.getDefault().post(new ConfigServiceConnectEvent(true));
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(Connector.TAG, "onServiceDisconnected: service:" + Connector.this.mService);
            if (Connector.this.mService != null) {
                try {
                    Connector.this.mService.unsubscribe(Connector.this.mProvider.getAppID());
                    Connector.this.mService.asBinder().unlinkToDeath(Connector.this.mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new ConfigServiceConnectEvent(false));
            }
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaopeng.lib.framework.configuration.internal.Connector.3
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.i(Connector.TAG, "DeathRecipient binderDied, service:" + Connector.this.mService);
            if (Connector.this.mService != null) {
                Connector.this.mService.asBinder().unlinkToDeath(Connector.this.mDeathRecipient, 0);
                Connector.this.mService = null;
            }
            Connector.this.connect();
        }
    };
    private IConfigurationServiceCallback mCallback = new IConfigurationServiceCallback.Stub() { // from class: com.xiaopeng.lib.framework.configuration.internal.Connector.4
        @Override // com.xiaopeng.lib.framework.configuration.IConfigurationServiceCallback
        public void onConfigurationChanged(List<ConfigurationDataImpl> list) throws RemoteException {
            ArrayList arrayList;
            ConfigurationChangeEvent configurationChangeEvent = new ConfigurationChangeEvent();
            if (list != null) {
                arrayList = new ArrayList(list.size());
                arrayList.addAll(list);
            } else {
                arrayList = null;
            }
            configurationChangeEvent.setChangeList(arrayList);
            Log.i(Connector.TAG, "onConfigurationChanged event:" + configurationChangeEvent);
            EventBus.getDefault().post(configurationChangeEvent);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public Connector(Proxy.ContextProvider contextProvider) {
        this.mProvider = contextProvider;
        HandlerThread handlerThread = new HandlerThread("thread-connector");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: com.xiaopeng.lib.framework.configuration.internal.Connector.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                Log.i(Connector.TAG, "handleMessage: msg=" + message.what);
                int i = message.what;
                if (i == 1) {
                    Connector.this.bindService();
                    Connector.this.mHandler.sendEmptyMessageDelayed(1, UILooperObserver.ANR_TRIGGER_TIME);
                    return false;
                } else if (i == 2 || i == 3) {
                    Connector.this.mHandler.removeCallbacksAndMessages(null);
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void connect() {
        Log.i(TAG, "connect");
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        this.mHandler.sendEmptyMessageDelayed(3, 120000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getConfiguration(String str) {
        String str2 = null;
        if (this.mService != null) {
            try {
                str2 = this.mService.getConfiguration(this.mProvider.getAppID(), str);
                Log.i(TAG, "getConfiguration: key=" + str + "; value=" + str2);
                return str2;
            } catch (RemoteException e) {
                e.printStackTrace();
                return str2;
            }
        }
        Log.e(TAG, "getConfiguration key:" + str + " but service is null, has the service been connected?");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindService() {
        Context context = this.mProvider.getContext();
        Log.i(TAG, "bindService context:" + context);
        if (context != null) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaopeng.configurationcenter", REMOTE_CLASS_NAME));
            intent.setAction(REMOTE_SERVICE_ACTION);
            context.bindService(intent, this.mServiceConnection, 1);
        }
    }
}
