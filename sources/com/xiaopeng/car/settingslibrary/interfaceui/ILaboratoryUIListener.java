package com.xiaopeng.car.settingslibrary.interfaceui;

import com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues;
/* loaded from: classes.dex */
public interface ILaboratoryUIListener {
    void getNedcModeStatus(int i);

    void onAppLimitChanged(boolean z);

    void onAutoPowerOffChanged(boolean z);

    void onCarCallAdvancedChanged(boolean z);

    void onConfigChanged(NetConfigValues netConfigValues);

    void onInductionLock(boolean z);

    void onKeyParkAdvancedChanged(boolean z);

    void onKeyParkChanged(boolean z);

    void onLeaveLockChanged(boolean z);

    void onLowSpeedVolumeChanged(int i);

    void onLowSpeedVolumeSwitchChanged(boolean z);

    void onNearUnlockChanged(boolean z);

    void onRemoteParkChanged(boolean z);

    void onSoldierCamera(boolean z);

    void onSoldierCameraEnable(boolean z);

    void onSoldierStatus(int i);
}
