package com.xiaopeng.speech;

import android.content.Context;
import android.os.HandlerThread;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.actor.ActorBridge;
import com.xiaopeng.speech.asr.Recognizer;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.proxy.ASREngineProxy;
import com.xiaopeng.speech.proxy.AgentProxy;
import com.xiaopeng.speech.proxy.AppMgrProxy;
import com.xiaopeng.speech.proxy.CarSystemPropertyProxy;
import com.xiaopeng.speech.proxy.HotwordEngineProxy;
import com.xiaopeng.speech.proxy.QueryInjectorProxy;
import com.xiaopeng.speech.proxy.RecordEngineProxy;
import com.xiaopeng.speech.proxy.SoundLockStateProxy;
import com.xiaopeng.speech.proxy.SpeechStateProxy;
import com.xiaopeng.speech.proxy.SubscriberProxy;
import com.xiaopeng.speech.proxy.TTSEngineProxy;
import com.xiaopeng.speech.proxy.VADEngineProxy;
import com.xiaopeng.speech.proxy.WakeupEngineProxy;
import com.xiaopeng.speech.proxy.WindowEngineProxy;
/* loaded from: classes.dex */
public class SpeechClient implements ConnectManager.OnConnectCallback {
    private ASREngineProxy mASREngineProxy;
    private ActorBridge mActorBridge;
    private AgentProxy mAgentProxy;
    private AppMgrProxy mAppMgrProxy;
    private CarSystemPropertyProxy mCarSystemPropertyProxy;
    private ConnectManager mConnectManager;
    private Context mContext;
    private HotwordEngineProxy mHotwordEngineProxy;
    private NodeManager mNodeManager;
    private QueryInjectorProxy mQueryInjectorProxy;
    private QueryManager mQueryManager;
    private Recognizer mRecognizer;
    private RecordEngineProxy mRecordEngineProxy;
    private SoundLockStateProxy mSoundLockStateProxy;
    private SpeechStateProxy mSpeechStateProxy;
    private SubscriberProxy mSubscriberProxy;
    private TTSEngineProxy mTTSEngineProxy;
    private VADEngineProxy mVADEngineProxy;
    private WakeupEngineProxy mWakeupEngineProxy;
    private WindowEngineProxy mWindowEngineProxy;
    private WorkerHandler mWorkerHandler;

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Holder {
        private static final SpeechClient Instance = new SpeechClient();

        private Holder() {
        }
    }

    public static final SpeechClient instance() {
        return Holder.Instance;
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, ConnectManager.OnConnectCallback onConnectCallback) {
        LogUtils.i(this, "SpeechClient(%s) Start In %s, connectCallback: %s", "1.0", context.getPackageName(), onConnectCallback);
        HandlerThread handlerThread = new HandlerThread("NodeWorker");
        handlerThread.start();
        this.mWorkerHandler = new WorkerHandler(handlerThread.getLooper());
        this.mContext = context;
        this.mConnectManager = new ConnectManager(context);
        this.mConnectManager.init(this.mWorkerHandler);
        this.mNodeManager = new NodeManager();
        this.mNodeManager.init(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mNodeManager);
        this.mQueryManager = new QueryManager();
        this.mQueryManager.init(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mQueryManager);
        this.mActorBridge = new ActorBridge(context);
        this.mActorBridge.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mActorBridge);
        this.mTTSEngineProxy = new TTSEngineProxy();
        this.mTTSEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mTTSEngineProxy);
        this.mWakeupEngineProxy = new WakeupEngineProxy();
        this.mWakeupEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mWakeupEngineProxy);
        this.mHotwordEngineProxy = new HotwordEngineProxy();
        this.mHotwordEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mHotwordEngineProxy);
        this.mAgentProxy = new AgentProxy();
        this.mAgentProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mAgentProxy);
        this.mSubscriberProxy = new SubscriberProxy();
        this.mSubscriberProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mSubscriberProxy);
        this.mAppMgrProxy = new AppMgrProxy(context);
        this.mAppMgrProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mAppMgrProxy);
        this.mSpeechStateProxy = new SpeechStateProxy();
        this.mConnectManager.addCallback(this.mSpeechStateProxy);
        this.mSoundLockStateProxy = new SoundLockStateProxy();
        this.mConnectManager.addCallback(this.mSoundLockStateProxy);
        this.mASREngineProxy = new ASREngineProxy();
        this.mASREngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mASREngineProxy);
        this.mRecordEngineProxy = new RecordEngineProxy();
        this.mRecordEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mRecordEngineProxy);
        this.mQueryInjectorProxy = new QueryInjectorProxy();
        this.mQueryInjectorProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mQueryInjectorProxy);
        this.mWindowEngineProxy = new WindowEngineProxy();
        this.mConnectManager.addCallback(this.mWindowEngineProxy);
        this.mRecognizer = new Recognizer(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mRecognizer.getConnectCallback());
        this.mCarSystemPropertyProxy = new CarSystemPropertyProxy();
        this.mConnectManager.addCallback(this.mCarSystemPropertyProxy);
        this.mVADEngineProxy = new VADEngineProxy();
        this.mVADEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mVADEngineProxy);
        if (onConnectCallback == null) {
            this.mConnectManager.addCallback(this);
        } else {
            this.mConnectManager.addCallback(onConnectCallback);
        }
        this.mConnectManager.connect();
        this.mConnectManager.registerReceiver();
    }

    public void setAppName(String... strArr) {
        for (String str : strArr) {
            getAppMgr().registerApp(this.mContext.getPackageName(), str);
        }
    }

    public ActorBridge getActorBridge() {
        return this.mActorBridge;
    }

    public SubscriberProxy getSubscriber() {
        return this.mSubscriberProxy;
    }

    public TTSEngineProxy getTTSEngine() {
        return this.mTTSEngineProxy;
    }

    public WakeupEngineProxy getWakeupEngine() {
        return this.mWakeupEngineProxy;
    }

    public HotwordEngineProxy getHotwordEngine() {
        return this.mHotwordEngineProxy;
    }

    public AgentProxy getAgent() {
        return this.mAgentProxy;
    }

    public AppMgrProxy getAppMgr() {
        return this.mAppMgrProxy;
    }

    public SpeechStateProxy getSpeechState() {
        return this.mSpeechStateProxy;
    }

    public SoundLockStateProxy getSoundLockState() {
        return this.mSoundLockStateProxy;
    }

    public ASREngineProxy getASREngine() {
        return this.mASREngineProxy;
    }

    public QueryInjectorProxy getQueryInjector() {
        return this.mQueryInjectorProxy;
    }

    public RecordEngineProxy getRecordEngine() {
        return this.mRecordEngineProxy;
    }

    public WindowEngineProxy getWindowEngine() {
        return this.mWindowEngineProxy;
    }

    public Recognizer getRecognizer() {
        return this.mRecognizer;
    }

    public VADEngineProxy getVadEngine() {
        return this.mVADEngineProxy;
    }

    public NodeManager getNodeManager() {
        return this.mNodeManager;
    }

    public QueryManager getQueryManager() {
        return this.mQueryManager;
    }

    public CarSystemPropertyProxy getCarSystemProperty() {
        return this.mCarSystemPropertyProxy;
    }

    public void sendActor(Actor actor) {
        this.mActorBridge.send(actor);
    }
}
