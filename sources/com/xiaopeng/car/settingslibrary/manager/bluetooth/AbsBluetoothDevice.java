package com.xiaopeng.car.settingslibrary.manager.bluetooth;
/* loaded from: classes.dex */
public abstract class AbsBluetoothDevice {
    public void dispatchAttributesChanged() {
    }

    public abstract int getBondState();

    public abstract boolean isA2DPSupportedProfile();

    public abstract boolean isA2dpConnected();

    public abstract boolean isA2dpConnecting();

    public abstract boolean isBtPhoneConnected();

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isDisConnecting();

    public abstract boolean isHidConnected();

    public abstract boolean isParing();
}
