package com.xiaopeng.car.settingslibrary.interfaceui;

import android.content.Intent;
/* loaded from: classes.dex */
public interface IAppUIListener {
    default void onAuthModeAction(String str) {
    }

    default void onAuthModeCloseAction() {
    }

    default void onAutoPowerOffAction() {
    }

    default void onAutoPowerOffCountdown(int i, int i2) {
    }

    default void onBluetoothPairCancel() {
    }

    default void onBluetoothPairShow(String str, String str2, int i) {
    }

    default void onCancelAutoPowerOff() {
    }

    default void onEmergencyIgOff() {
    }

    default void onEnterCleanMode() {
    }

    default void onEnterWaitMode() {
    }

    default void onExitCleanMode() {
    }

    default void onExitWaitMode() {
    }

    default void onJumpAbout() {
    }

    default void onJumpBluetooth() {
    }

    default void onJumpDisplay() {
    }

    default void onJumpSound() {
    }

    default void onJumpWifi() {
    }

    default void onMicDialogShow() {
    }

    default void onMicDialogUpdate(int i) {
    }

    default void onPopupDialog(Intent intent) {
    }

    default void onPopupSoundEffect() {
    }

    default void onPopupToast(String str) {
    }

    default void onPopupToastLong(String str) {
    }

    default void onPopupToastShort(String str) {
    }

    default void onRepairQuestQuit() {
    }
}
