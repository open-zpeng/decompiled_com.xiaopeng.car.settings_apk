package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public final class A2dpSinkProfile implements LocalBluetoothProfile {
    public static final String NAME = "A2DPSink";
    private static final int ORDINAL = 5;
    public static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.AudioSource, BluetoothUuid.AdvAudioDist};
    private static final String TAG = "A2dpSinkProfile";
    private IXpBluetoothInterface mBluetoothInterface;
    private boolean mIsProfileReady;
    private BluetoothA2dpSink mService;

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getOrdinal() {
        return 5;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getProfileId() {
        return 11;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return true;
    }

    public String toString() {
        return NAME;
    }

    /* loaded from: classes.dex */
    private final class A2dpSinkServiceListener implements BluetoothProfile.ServiceListener {
        private A2dpSinkServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            A2dpSinkProfile.this.mService = (BluetoothA2dpSink) bluetoothProfile;
            List connectedDevices = A2dpSinkProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
            }
            if (!connectedDevices.isEmpty()) {
                A2dpSinkProfile.this.mBluetoothInterface.notifyRefreshListCallback();
            }
            A2dpSinkProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            A2dpSinkProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public A2dpSinkProfile(Context context, IXpBluetoothInterface iXpBluetoothInterface) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new A2dpSinkServiceListener(), 11);
        this.mBluetoothInterface = iXpBluetoothInterface;
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink == null ? new ArrayList(0) : bluetoothA2dpSink.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        return bluetoothA2dpSink.connect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        if (bluetoothA2dpSink.getPriority(bluetoothDevice) > 100) {
            this.mService.setPriority(bluetoothDevice, 100);
        }
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getConnectionState(bluetoothDevice);
    }

    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink != null && bluetoothA2dpSink.getPriority(bluetoothDevice) > 0;
    }

    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getPriority(bluetoothDevice);
    }

    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return;
        }
        if (z) {
            if (bluetoothA2dpSink.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothA2dpSink.setPriority(bluetoothDevice, 0);
    }

    boolean isA2dpPlaying() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        List connectedDevices = bluetoothA2dpSink.getConnectedDevices();
        return !connectedDevices.isEmpty() && this.mService.isA2dpPlaying((BluetoothDevice) connectedDevices.get(0));
    }

    protected void finalize() {
        Log.d(TAG, "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(11, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up A2DP proxy", th);
            }
        }
    }
}
