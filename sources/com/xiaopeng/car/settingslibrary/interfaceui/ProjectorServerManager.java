package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ProjectorBluetoothViewModel;
import com.xiaopeng.car.settingslibrary.vm.speech.SpeechPlayViewModel;
/* loaded from: classes.dex */
public class ProjectorServerManager extends ServerBaseManager {
    private static final String TAG = "ProjectorServerManager";
    private ProjectorBluetoothViewModel mProjectorBluetoothViewModel;
    private SpeechPlayViewModel mSpeechPlayViewModel;

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final ProjectorServerManager instance = new ProjectorServerManager();

        private InnerFactory() {
        }
    }

    public static ProjectorServerManager get_instance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getProjectStatus().setValue(-1);
        getViewModel().getProjectStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ProjectorServerManager$2_tmsDZTRe7RDXYUmKvbruBa1Mc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ProjectorServerManager.this.lambda$observeData$0$ProjectorServerManager((Integer) obj);
            }
        });
        getSpeechViewModel().getTtsPlayDoneLiveData().setValue(null);
        getSpeechViewModel().getTtsPlayDoneLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$ProjectorServerManager$dG8PeY0sNCKIUp134AawbkAcYqI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ProjectorServerManager.this.lambda$observeData$1$ProjectorServerManager((String) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$ProjectorServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("ProjectorServerManager onProjectorStatus " + num);
        projectorCallback(InterfaceConstant.ON_PROJECTOR_STATUS, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$ProjectorServerManager(String str) {
        if (str == null) {
            return;
        }
        debugLog("ProjectorServerManager onTtsPlayDone " + str);
        projectorCallback(InterfaceConstant.ON_TTS_PLAY_DONE, str);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
        getSpeechViewModel();
    }

    private synchronized ProjectorBluetoothViewModel getViewModel() {
        if (this.mProjectorBluetoothViewModel == null) {
            this.mProjectorBluetoothViewModel = new ProjectorBluetoothViewModel(CarSettingsApp.getApp());
        }
        return this.mProjectorBluetoothViewModel;
    }

    private synchronized SpeechPlayViewModel getSpeechViewModel() {
        if (this.mSpeechPlayViewModel == null) {
            this.mSpeechPlayViewModel = new SpeechPlayViewModel(CarSettingsApp.getApp());
        }
        return this.mSpeechPlayViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    public boolean isProjectorAlreadyConnected() {
        boolean isProjectorAlreadyConnected = getViewModel().isProjectorAlreadyConnected();
        log("ProjectorServerManager isProjectorAlreadyConnected " + isProjectorAlreadyConnected);
        return isProjectorAlreadyConnected;
    }

    public void startPairOrConnectProjector() {
        log("ProjectorServerManager startPairOrConnectProjector");
        getViewModel().startPairOrConnectProjector();
    }

    public boolean isProjectorAlreadyBonded() {
        boolean isProjectorAlreadyBonded = getViewModel().isProjectorAlreadyBonded();
        log("ProjectorServerManager isProjectorAlreadyBonded " + isProjectorAlreadyBonded);
        return isProjectorAlreadyBonded;
    }

    public void setProjectorBluetoothDoing(boolean z) {
        log("ProjectorServerManager setProjectorBluetoothDoing start:" + z);
        getViewModel().setProjectorBluetoothDoing(z);
    }

    public void registerProjectorStateCallback() {
        log("ProjectorServerManager registerProjectorStateCallback");
        getViewModel().registerProjectorStateCallback();
    }

    public void unregisterProjectorStateCallback() {
        log("ProjectorServerManager unregisterProjectorStateCallback");
        getViewModel().unregisterProjectorStateCallback();
    }

    public boolean isProjectorFounded() {
        boolean isProjectorFounded = getViewModel().isProjectorFounded();
        log("ProjectorServerManager isProjectorFounded " + isProjectorFounded);
        return isProjectorFounded;
    }

    public void startScanProjector() {
        log("ProjectorServerManager startScanProjector");
        getViewModel().startScanProjector();
    }

    public boolean isProjectorConnecting() {
        boolean isProjectorConnecting = getViewModel().isProjectorConnecting();
        log("ProjectorServerManager isProjectorConnecting " + isProjectorConnecting);
        return isProjectorConnecting;
    }

    public boolean isProjectorBonding() {
        boolean isProjectorBonding = getViewModel().isProjectorBonding();
        log("ProjectorServerManager isProjectorBonding " + isProjectorBonding);
        return isProjectorBonding;
    }

    public void stopScanProjector() {
        log("ProjectorServerManager stopScanProjector");
        getViewModel().stopScanProjector();
    }

    public void stopPairOrConnectProjector() {
        log("ProjectorServerManager stopPairOrConnectProjector");
        getViewModel().stopPairOrConnectProjector();
    }

    public void reqBtDevicePairedDevices() {
        log("ProjectorServerManager reqBtDevicePairedDevices");
        getViewModel().reqBtDevicePairedDevices();
    }

    public void createTextSpeech() {
        log("ProjectorServerManager createTextSpeech");
        getSpeechViewModel().createTextSpeech();
    }

    public void playText(String str) {
        log("ProjectorServerManager playText");
        getSpeechViewModel().playText(str);
    }

    public void stopPlay() {
        log("ProjectorServerManager stopPlay");
        getSpeechViewModel().stopPlay();
    }

    public void releaseTextSpeech() {
        log("ProjectorServerManager releaseTextSpeech");
        getSpeechViewModel().releaseTextSpeech();
    }
}
