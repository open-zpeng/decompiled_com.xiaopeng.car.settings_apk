package com.xiaopeng.car.settingslibrary.manager.bluetooth.os;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.A2dpSinkProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HfpClientProfile;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HidProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class OsBluetoothDevice extends AbsBluetoothDevice {
    private static final long MAX_UUID_DELAY_FOR_AUTO_CONNECT = 5000;
    private BluetoothAdapter mAdapter;
    private long mConnectAttempted;
    BluetoothDevice mDevice;
    private OsBluetoothManager mOsBluetoothManager;
    private final Object mProfileLock = new Object();
    private final Collection<LocalBluetoothProfile> mProfiles = new CopyOnWriteArrayList();

    public OsBluetoothDevice(BluetoothAdapter bluetoothAdapter, BluetoothDevice bluetoothDevice, OsBluetoothManager osBluetoothManager) {
        this.mDevice = bluetoothDevice;
        this.mAdapter = bluetoothAdapter;
        this.mOsBluetoothManager = osBluetoothManager;
        updateProfiles();
    }

    public int getProfileConnectionState(LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile != null) {
            return localBluetoothProfile.getConnectionStatus(this.mDevice);
        }
        return 0;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isConnected() {
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (getProfileConnectionState(localBluetoothProfile) == 2) {
                    return true;
                }
            }
            return false;
        }
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public String getAddress() {
        return this.mDevice.getAddress();
    }

    public String getName() {
        String name = this.mDevice.getName();
        return TextUtils.isEmpty(name) ? getAddress() : name;
    }

    public int getBatteryLevel() {
        return this.mDevice.getBatteryLevel();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public int getBondState() {
        return this.mDevice.getBondState();
    }

    public boolean isConnectedProfile(LocalBluetoothProfile localBluetoothProfile) {
        return getProfileConnectionState(localBluetoothProfile) == 2;
    }

    public boolean isBusy() {
        int profileConnectionState;
        synchronized (this.mProfileLock) {
            Iterator<LocalBluetoothProfile> it = this.mProfiles.iterator();
            do {
                boolean z = true;
                if (it.hasNext()) {
                    profileConnectionState = getProfileConnectionState(it.next());
                    if (profileConnectionState == 1) {
                        break;
                    }
                } else {
                    if (getBondState() != 11) {
                        z = false;
                    }
                    return z;
                }
            } while (profileConnectionState != 3);
            return true;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isParing() {
        return getBondState() == 11;
    }

    public boolean isDisconnecting() {
        if (this.mDevice.isConnected()) {
            return false;
        }
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (getProfileConnectionState(localBluetoothProfile) == 3) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isConnecting() {
        if (this.mDevice.isConnected()) {
            return false;
        }
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (getProfileConnectionState(localBluetoothProfile) == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isDisConnecting() {
        if (this.mDevice.isConnected()) {
            return false;
        }
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (getProfileConnectionState(localBluetoothProfile) == 3) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<LocalBluetoothProfile> getConnectableProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.accessProfileEnabled()) {
                    arrayList.add(localBluetoothProfile);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onBondingStateChanged(int i) {
        if (i == 10) {
            synchronized (this.mProfileLock) {
                this.mProfiles.clear();
                LogUtils.d("xpBluetooth os profiles mProfiles.clear();");
            }
            this.mDevice.setPhonebookAccessPermission(0);
            this.mDevice.setMessageAccessPermission(0);
            this.mDevice.setSimAccessPermission(0);
        }
    }

    private boolean updateProfiles() {
        BluetoothClass bluetoothClass;
        ParcelUuid[] uuids = this.mDevice.getUuids();
        if (uuids == null) {
            Logs.d("xpbluetooth os uuid null " + this.mDevice.getName() + " " + this.mDevice.getAddress());
            return false;
        }
        ParcelUuid[] uuids2 = this.mAdapter.getUuids();
        if (uuids2 == null) {
            Logs.d("xpbluetooth os localuuid null " + this.mDevice.getName() + " " + this.mDevice.getAddress());
            return false;
        }
        synchronized (this.mProfileLock) {
            this.mOsBluetoothManager.updateProfiles(this.mDevice, uuids, uuids2, this.mProfiles);
        }
        Logs.d("xpbluetooth os updating profiles for " + this.mDevice.getName());
        if (this.mDevice.getBluetoothClass() != null) {
            Logs.d("xpbluetooth os Class: " + bluetoothClass.toString());
        }
        Logs.d("xpbluetooth os device UUID:");
        for (ParcelUuid parcelUuid : uuids) {
            Logs.d("  " + parcelUuid);
        }
        Logs.d("xpbluetooth os localUuids:");
        for (ParcelUuid parcelUuid2 : uuids) {
            Logs.d("  " + parcelUuid2);
        }
        Logs.d("xpbluetooth os profiles:");
        Iterator<LocalBluetoothProfile> it = this.mProfiles.iterator();
        while (it.hasNext()) {
            Logs.d(" " + it.next());
        }
        return true;
    }

    public boolean disConnect() {
        boolean disconnectDevice;
        synchronized (this.mProfileLock) {
            disconnectDevice = disconnectDevice(this.mDevice);
        }
        return disconnectDevice;
    }

    private boolean disconnectDevice(BluetoothDevice bluetoothDevice) {
        Logs.d("xpbluetooth os disconnectDevice:" + bluetoothDevice.getAddress() + " " + bluetoothDevice.getName());
        return ((Boolean) ReflectUtils.invokeMethod(this.mAdapter, "disconnectAllEnabledProfiles", new Class[]{BluetoothDevice.class}, new Object[]{bluetoothDevice})).booleanValue();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2DPSupportedProfile() {
        ParcelUuid[] uuids = this.mDevice.getUuids();
        if (uuids == null) {
            return false;
        }
        return BluetoothUuid.containsAnyUuid(uuids, A2dpSinkProfile.SRC_UUIDS);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isHidConnected() {
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.equals(HidProfile.NAME) && getProfileConnectionState(localBluetoothProfile) == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2dpConnected() {
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.equals(A2dpSinkProfile.NAME) && getProfileConnectionState(localBluetoothProfile) == 2) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2dpConnecting() {
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.equals(A2dpSinkProfile.NAME) && getProfileConnectionState(localBluetoothProfile) == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isBtPhoneConnected() {
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.equals(HfpClientProfile.NAME) && getProfileConnectionState(localBluetoothProfile) == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onUuidChanged() {
        updateProfiles();
        this.mDevice.getUuids();
        if (this.mConnectAttempted + 5000 > SystemClock.elapsedRealtime()) {
            Logs.d("xpbluetooth os onUuidChanged: triggering connectAllEnabledProfiles");
            connectAllEnabledProfiles();
        }
    }

    public boolean connectAllEnabledProfiles() {
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        synchronized (this.mProfileLock) {
            if (this.mProfiles.isEmpty()) {
                Logs.d("xpbluetooth os No profiles. Maybe we will connect later for device " + this.mDevice);
                return false;
            }
            for (BluetoothDevice bluetoothDevice : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
                if (!bluetoothDevice.getAddress().equals(this.mDevice.getAddress()) && bluetoothDevice.isConnected() && Utils.isPhone(bluetoothDevice)) {
                    disconnectDevice(bluetoothDevice);
                }
            }
            Logs.d("xpbluetooth os connectAllEnabledProfiles device:" + this.mDevice.getName() + " " + this.mDevice.getAddress());
            return ((Boolean) ReflectUtils.invokeMethod(this.mAdapter, "connectAllEnabledProfiles", new Class[]{BluetoothDevice.class}, new Object[]{this.mDevice})).booleanValue();
        }
    }
}
