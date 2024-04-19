package com.xiaopeng.car.settingslibrary.interfaceui;
/* loaded from: classes.dex */
public interface ISoundServerListener {
    default void meterAlarmSoundChanged(int i) {
    }

    default void notifySystemSoundChanged() {
    }

    default void onAccountChanged() {
    }

    default void onHeadRestModeChange() {
    }

    default void onSoundEffectChange(boolean z) {
    }

    default void onSoundEffectPlayStateChange(String str, int i) {
    }

    default void onSoundPositionEnable(boolean z) {
    }

    default void onTtsTipsChange() {
    }

    default void onVolumeChanged(int i, int i2) {
    }
}
