package com.xiaopeng.car.settingslibrary.manager.karaoke;

import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.xuimanager.karaoke.KaraokeManager;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class XpKaraokeManager implements XuiSettingsManager.OnServiceConnectCompleteListener {
    KaraokeManager.MicCallBack mMicCallBack;
    private List<OnKaraokePowerListener> mOnKaraokePowerListenerList;
    XuiSettingsManager mXuiSettingsManager;

    /* loaded from: classes.dex */
    public interface OnKaraokePowerListener {
        void micServiceCallBack(int i);

        void onConnectXui();

        void onErrorEvent(int i, int i2);
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final XpKaraokeManager instance = new XpKaraokeManager();

        private InnerFactory() {
        }
    }

    public static XpKaraokeManager getInstance() {
        return InnerFactory.instance;
    }

    private XpKaraokeManager() {
        this.mXuiSettingsManager = XuiSettingsManager.getInstance();
        this.mMicCallBack = new KaraokeManager.MicCallBack() { // from class: com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.1
            public void micServiceCallBack(int i) {
                for (OnKaraokePowerListener onKaraokePowerListener : XpKaraokeManager.this.mOnKaraokePowerListenerList) {
                    onKaraokePowerListener.micServiceCallBack(i);
                }
            }

            public void onErrorEvent(int i, int i2) {
                for (OnKaraokePowerListener onKaraokePowerListener : XpKaraokeManager.this.mOnKaraokePowerListenerList) {
                    onKaraokePowerListener.onErrorEvent(i, i2);
                }
            }
        };
        this.mOnKaraokePowerListenerList = new ArrayList();
        if (this.mXuiSettingsManager.registerOfficialKaraokePowerListener(this.mMicCallBack)) {
            return;
        }
        this.mXuiSettingsManager.addServiceConnectCompleteListener(this);
    }

    public boolean isMicSdkInvalid() {
        return this.mXuiSettingsManager.getMicSdk() == null;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.OnServiceConnectCompleteListener
    public void onServiceConnectComplete() {
        this.mXuiSettingsManager.registerOfficialKaraokePowerListener(this.mMicCallBack);
        this.mXuiSettingsManager.removeServiceConnectCompleteListener(this);
        for (OnKaraokePowerListener onKaraokePowerListener : this.mOnKaraokePowerListenerList) {
            onKaraokePowerListener.onConnectXui();
        }
    }

    public void unregisterOfficialKaraokePowerListener() {
        this.mXuiSettingsManager.unRegisterOfficialKaraokePowerListener();
    }

    public void addOnKaraokePowerListener(OnKaraokePowerListener onKaraokePowerListener) {
        if (this.mOnKaraokePowerListenerList.contains(onKaraokePowerListener)) {
            return;
        }
        this.mOnKaraokePowerListenerList.add(onKaraokePowerListener);
    }

    public void removeOnKaraokePowerListener(OnKaraokePowerListener onKaraokePowerListener) {
        this.mOnKaraokePowerListenerList.remove(onKaraokePowerListener);
    }
}
