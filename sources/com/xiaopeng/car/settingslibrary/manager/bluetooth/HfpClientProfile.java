package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public final class HfpClientProfile implements LocalBluetoothProfile {
    public static final String NAME = "HEADSET_CLIENT";
    private static final int ORDINAL = 0;
    public static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.HSP_AG, BluetoothUuid.Handsfree_AG};
    private static final String TAG = "HfpClientProfile";
    private IXpBluetoothInterface mBluetoothInterface;
    private boolean mIsProfileReady;
    private BluetoothHeadsetClient mService;

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getOrdinal() {
        return 0;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getProfileId() {
        return 16;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return true;
    }

    public String toString() {
        return NAME;
    }

    /* loaded from: classes.dex */
    private final class HfpClientServiceListener implements BluetoothProfile.ServiceListener {
        private HfpClientServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HfpClientProfile.this.mService = (BluetoothHeadsetClient) bluetoothProfile;
            List connectedDevices = HfpClientProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
            }
            if (!connectedDevices.isEmpty()) {
                HfpClientProfile.this.mBluetoothInterface.notifyRefreshListCallback();
            }
            HfpClientProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HfpClientProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public HfpClientProfile(Context context, IXpBluetoothInterface iXpBluetoothInterface) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HfpClientServiceListener(), 16);
        this.mBluetoothInterface = iXpBluetoothInterface;
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        return bluetoothHeadsetClient == null ? new ArrayList(0) : bluetoothHeadsetClient.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return false;
        }
        return bluetoothHeadsetClient.connect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return false;
        }
        if (bluetoothHeadsetClient.getPriority(bluetoothDevice) > 100) {
            this.mService.setPriority(bluetoothDevice, 100);
        }
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return 0;
        }
        return bluetoothHeadsetClient.getConnectionState(bluetoothDevice);
    }

    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        return bluetoothHeadsetClient != null && bluetoothHeadsetClient.getPriority(bluetoothDevice) > 0;
    }

    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return 0;
        }
        return bluetoothHeadsetClient.getPriority(bluetoothDevice);
    }

    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return;
        }
        if (z) {
            if (bluetoothHeadsetClient.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothHeadsetClient.setPriority(bluetoothDevice, 0);
    }

    protected void finalize() {
        Log.d(TAG, "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(16, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up HfpClient proxy", th);
            }
        }
    }
}
