package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile;
import java.util.List;
/* loaded from: classes.dex */
public class HidProfile implements LocalBluetoothProfile {
    public static final String NAME = "HID";
    private static final int ORDINAL = 3;
    private static final String TAG = "HidProfile";
    private static boolean V = true;
    private static int sServiceListenerCount;
    private IXpBluetoothInterface mBluetoothInterface;
    private boolean mIsProfileReady;
    private BluetoothHidHost mService;

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getOrdinal() {
        return 3;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getProfileId() {
        return 4;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return true;
    }

    public String toString() {
        return NAME;
    }

    static /* synthetic */ int access$108() {
        int i = sServiceListenerCount;
        sServiceListenerCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$110() {
        int i = sServiceListenerCount;
        sServiceListenerCount = i - 1;
        return i;
    }

    /* loaded from: classes.dex */
    private final class HidHostServiceListener implements BluetoothProfile.ServiceListener {
        private HidHostServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HidProfile.this.mService = (BluetoothHidHost) bluetoothProfile;
            HidProfile.access$108();
            if (HidProfile.V) {
                Log.d(HidProfile.TAG, "Bluetooth service hid connected service:" + HidProfile.this.mService + " " + HidProfile.sServiceListenerCount);
            }
            List connectedDevices = HidProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
            }
            if (!connectedDevices.isEmpty()) {
                HidProfile.this.mBluetoothInterface.notifyRefreshListCallback();
            }
            HidProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HidProfile.access$110();
            if (HidProfile.V) {
                Log.d(HidProfile.TAG, "Bluetooth hid service disconnected " + HidProfile.sServiceListenerCount);
            }
            HidProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public HidProfile(Context context, IXpBluetoothInterface iXpBluetoothInterface) {
        Log.d(TAG, "HidProfile init");
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HidHostServiceListener(), 4);
        this.mBluetoothInterface = iXpBluetoothInterface;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return false;
        }
        if (bluetoothDevice != null) {
            Logs.d("xpbluetooth nf profile connect hid service " + bluetoothDevice.getAddress());
        }
        return this.mService.connect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return false;
        }
        Logs.d("xpbluetooth nf profile disconnect hid service");
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.os.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            Logs.d("xpbluetooth hid service null");
            return 0;
        } else if (bluetoothHidHost.getConnectedDevices().isEmpty()) {
            Logs.d("xpbluetooth hid service no connect");
            return 0;
        } else {
            return this.mService.getConnectionState(bluetoothDevice);
        }
    }

    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        return bluetoothHidHost != null && bluetoothHidHost.getPriority(bluetoothDevice) > 0;
    }

    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return 0;
        }
        return bluetoothHidHost.getPriority(bluetoothDevice);
    }

    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return;
        }
        if (z) {
            if (bluetoothHidHost.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothHidHost.setPriority(bluetoothDevice, 0);
    }

    public int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.bluetooth_profile_hid;
    }

    public int getDrawableResource(BluetoothClass bluetoothClass) {
        if (bluetoothClass == null) {
            return -1;
        }
        return getHidClassDrawable(bluetoothClass);
    }

    public static int getHidClassDrawable(BluetoothClass bluetoothClass) {
        int deviceClass = bluetoothClass.getDeviceClass();
        if (deviceClass == 1344 || deviceClass == 1408 || deviceClass != 1472) {
        }
        return -1;
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(4, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("wen", "Error cleaning up HID proxy", th);
            }
        }
    }
}
