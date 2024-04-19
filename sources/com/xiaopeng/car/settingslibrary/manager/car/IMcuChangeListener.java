package com.xiaopeng.car.settingslibrary.manager.car;
/* loaded from: classes.dex */
public interface IMcuChangeListener {
    default void onAutoPowerOffConfig(boolean z) {
    }

    default void onAutoPowerOffCountdown() {
    }

    default void onCancelAutoPowerOff() {
    }

    default void onIgOff() {
    }

    default void onIgOn() {
    }

    default void onOpenWifiHotSpot() {
    }
}
