package com.xiaopeng.car.settingslibrary.interfaceui;

import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
/* loaded from: classes.dex */
public class KaraokeServerManager extends ServerBaseManager implements XpKaraokeManager.OnKaraokePowerListener {
    private static final String TAG = "KaraokeServerManager";

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final KaraokeServerManager instance = new KaraokeServerManager();

        private InnerFactory() {
        }
    }

    public static KaraokeServerManager getInstance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void micServiceCallBack(int i) {
        debugLog("KaraokeServerManager micServiceCallBack " + i);
        karaokeCallback(InterfaceConstant.ON_MIC_SERVICE_CALLBACK, String.valueOf(i));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void onErrorEvent(int i, int i2) {
        debugLog("KaraokeServerManager onErrorEvent " + i + " " + i2);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(",");
        sb.append(i2);
        karaokeCallback(InterfaceConstant.ON_MIC_ERROR_EVENT, sb.toString());
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void onConnectXui() {
        debugLog("KaraokeServerManager onConnectXui ");
        karaokeCallback(InterfaceConstant.ON_CONNECT_XUI, "");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("KaraokeServerManager startVm");
        addOnKaraokePowerListener();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("KaraokeServerManager stopVm");
        removeOnKaraokePowerListener();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    private void addOnKaraokePowerListener() {
        XpKaraokeManager.getInstance().addOnKaraokePowerListener(this);
    }

    public boolean isMicSdkInvalid() {
        boolean isMicSdkInvalid = XpKaraokeManager.getInstance().isMicSdkInvalid();
        log("KaraokeServerManager isMicSdkInvalid " + isMicSdkInvalid);
        return isMicSdkInvalid;
    }

    private void removeOnKaraokePowerListener() {
        XpKaraokeManager.getInstance().removeOnKaraokePowerListener(this);
    }

    public String getBuyMicUrl() {
        String buyMicUrl = XuiSettingsManager.getInstance().getBuyMicUrl();
        log("KaraokeServerManager getBuyMicUrl " + buyMicUrl);
        return buyMicUrl;
    }

    public int getMicStatus() {
        int micStatus = XuiSettingsManager.getInstance().getMicStatus();
        log("KaraokeServerManager getMicStatus " + micStatus);
        return micStatus;
    }

    public int getMicPowerStatus() {
        int micPowerStatus = XuiSettingsManager.getInstance().getMicPowerStatus();
        log("KaraokeServerManager getMicPowerStatus " + micPowerStatus);
        return micPowerStatus;
    }
}
