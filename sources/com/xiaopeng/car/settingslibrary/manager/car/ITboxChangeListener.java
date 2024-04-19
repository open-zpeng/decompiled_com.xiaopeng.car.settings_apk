package com.xiaopeng.car.settingslibrary.manager.car;
/* loaded from: classes.dex */
public interface ITboxChangeListener {
    default void onAutoPowerOffConfig(boolean z) {
    }

    default void onAutoPowerOffCountdown(int i, int i2) {
    }

    default void onCancelAutoPowerOff() {
    }

    default void onSoldierCameraEnable(boolean z) {
    }

    default void onSoldierCameraSwState(boolean z) {
    }

    default void onSoldierSwState(int i) {
    }
}
