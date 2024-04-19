package com.xiaopeng.car.settingslibrary.manager.bluetooth.nf;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.xiaopeng.btservice.bluetooth.BluetoothControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.HidProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class NFBluetoothDevice extends AbsBluetoothDevice {
    public static boolean sDebugLog = true;
    private String mAddress;
    BluetoothControl mBluetoothControl;
    BluetoothDevice mBluetoothDevice;
    private int mCategory;
    HidProfile mHidProfile;
    private String mName;
    private int mRemoteSupportProfiles;
    Map<Integer, NFConnectState> mProfileConnectStateMap = new HashMap();
    ArrayList<Integer> mConnectedProfileList = new ArrayList<>();
    private int mBondState = 330;

    public NFBluetoothDevice(String str, String str2, int i, BluetoothControl bluetoothControl, HidProfile hidProfile) {
        this.mRemoteSupportProfiles = -1;
        this.mName = str;
        this.mAddress = str2;
        this.mCategory = i;
        this.mBluetoothControl = bluetoothControl;
        this.mHidProfile = hidProfile;
        this.mRemoteSupportProfiles = this.mBluetoothControl.getBtRemoteUuids(this.mAddress);
        Logs.d("xpbluetooth nf getBtRemoteUuids init " + this.mRemoteSupportProfiles + " " + this.mName + " address:" + str2 + " mCategory:" + this.mCategory);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            this.mBluetoothDevice = defaultAdapter.getRemoteDevice(this.mAddress);
        }
        printSupportProfiles();
        updateConnectState();
    }

    public void updateConnectState() {
        HidProfile hidProfile;
        BluetoothDevice bluetoothDevice;
        int hFPConnectState = this.mBluetoothControl.getHFPConnectState(this.mAddress);
        int a2DPConnectState = this.mBluetoothControl.getA2DPConnectState(this.mAddress);
        Logs.d("xpbluetooth nf device hfpState:" + hFPConnectState + " a2dpState:" + a2DPConnectState + " name:" + this.mName + " addr:" + this.mAddress);
        setProfileConnectState(1, -1, hFPConnectState);
        setProfileConnectState(2, -1, a2DPConnectState);
        if ((!isHIDSupportedProfile() && this.mCategory != 2) || (hidProfile = this.mHidProfile) == null || (bluetoothDevice = this.mBluetoothDevice) == null) {
            return;
        }
        int connectionStatus = hidProfile.getConnectionStatus(bluetoothDevice);
        Logs.d("xpbluetooth device hidState:" + connectionStatus + " " + this.mAddress);
        setProfileConnectState(256, -1, connectionStatus);
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public void setCategory(int i) {
        this.mCategory = i;
    }

    public void setSupportProfiles(int i) {
        this.mRemoteSupportProfiles = i;
        if (i == -1) {
            this.mRemoteSupportProfiles = this.mBluetoothControl.getBtRemoteUuids(this.mAddress);
            Logs.d("xpbluetooth nf getBtRemoteUuids setSupportProfiles " + this.mRemoteSupportProfiles + " " + this.mName);
        }
        printSupportProfiles();
    }

    private boolean printSupportProfiles() {
        if (this.mRemoteSupportProfiles == -1) {
            Logs.d("xpbluetooth nf profile null");
            return false;
        } else if (sDebugLog) {
            Logs.d("xpbluetooth nf profile support[hfp:" + isHFPSupportedProfile() + " a2dp:" + isA2DPSupportedProfile() + " avrcp:" + isAVRCPSupportedProfile() + " pbap:" + isPBAPSupportedProfile() + " map:" + isMAPSupportedProfile() + " spp:" + isSPPSupportedProfile() + " hid:" + isHIDSupportedProfile() + " nap:" + isNAPSupportedProfile() + " dun:" + isDUNSupportedProfile() + " ftp:" + isFTPSupportedProfile() + " iap:" + isIAPSupportedProfile() + "] name:" + this.mName);
            return true;
        } else {
            return true;
        }
    }

    public boolean isHFPA2DPSupportedProfile() {
        return isHFPSupportedProfile() || isA2DPSupportedProfile() || isAVRCPSupportedProfile();
    }

    public boolean isHFPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 1) > 0;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2DPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 2) > 0;
    }

    public boolean isAVRCPSupportedProfile() {
        int i = this.mRemoteSupportProfiles;
        return (i & 16) > 0 || (i & 8) > 0 || (i & 4) > 0;
    }

    public boolean isPBAPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 32) > 0;
    }

    public boolean isMAPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 64) > 0;
    }

    public boolean isSPPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 128) > 0;
    }

    public boolean isHIDSupportedProfile() {
        return (this.mRemoteSupportProfiles & 256) > 0;
    }

    public boolean isNAPSupportedProfile() {
        int i = this.mRemoteSupportProfiles;
        return (i & 512) > 0 || (i & 1024) > 0;
    }

    public boolean isDUNSupportedProfile() {
        return (this.mRemoteSupportProfiles & 2048) > 0;
    }

    public boolean isFTPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 4096) > 0;
    }

    public boolean isIAPSupportedProfile() {
        return (this.mRemoteSupportProfiles & 8192) > 0;
    }

    public boolean connect(boolean z) {
        boolean z2;
        BluetoothDevice bluetoothDevice;
        Logs.d("xpbluetooth nf profile isHFPA2DPSupportedProfile:" + isHFPA2DPSupportedProfile() + " mCategory:" + this.mCategory);
        boolean z3 = false;
        if (isHFPA2DPSupportedProfile() || this.mCategory == 1) {
            int reqBtConnectHfpA2dp = this.mBluetoothControl.reqBtConnectHfpA2dp(this.mAddress);
            z2 = reqBtConnectHfpA2dp > 0;
            Logs.d("xpbluetooth nf profile connect basic:" + reqBtConnectHfpA2dp);
            if (z2) {
                this.mConnectedProfileList.clear();
                this.mProfileConnectStateMap.clear();
                if ((reqBtConnectHfpA2dp & 1) > 0) {
                    this.mConnectedProfileList.add(1);
                    Logs.v("xpbluetooth nf profile connect hfp");
                }
                if ((reqBtConnectHfpA2dp & 2) > 0) {
                    this.mConnectedProfileList.add(2);
                    Logs.v("xpbluetooth nf profile connect a2dp");
                }
                if (((reqBtConnectHfpA2dp & 16) > 0) | ((reqBtConnectHfpA2dp & 4) > 0) | ((reqBtConnectHfpA2dp & 8) > 0)) {
                    this.mConnectedProfileList.add(4);
                    Logs.v("xpbluetooth nf profile connect avrcp");
                }
                if (!isConnectedProfile(1) || !isConnectedProfile(2)) {
                    Logs.d("xpbluetooth nf profile connect not all");
                    updateConnectState();
                }
            }
        } else {
            z2 = false;
        }
        if (isHIDSupportedProfile() || this.mCategory == 2) {
            HidProfile hidProfile = this.mHidProfile;
            if (hidProfile != null && (bluetoothDevice = this.mBluetoothDevice) != null) {
                z3 = hidProfile.connect(bluetoothDevice);
            }
            if (!z2 && z3) {
                this.mConnectedProfileList.clear();
                this.mProfileConnectStateMap.clear();
            }
            z2 |= z3;
            Logs.d("xpbluetooth nf profile connect hid success:" + z3);
            if (z3) {
                this.mConnectedProfileList.add(256);
            }
        }
        return z2;
    }

    public void checkConnectedProfile(int i) {
        if (isConnectedProfile(i)) {
            return;
        }
        this.mConnectedProfileList.add(Integer.valueOf(i));
    }

    public void setProfileConnectState(int i, int i2, int i3) {
        this.mProfileConnectStateMap.put(Integer.valueOf(i), new NFConnectState(i2, i3));
    }

    public int getProfileConnectState(int i) {
        if (this.mProfileConnectStateMap.containsKey(Integer.valueOf(i))) {
            return this.mProfileConnectStateMap.get(Integer.valueOf(i)).getNewState();
        }
        return -1;
    }

    public NFConnectState getConnectState(int i) {
        if (this.mProfileConnectStateMap.containsKey(Integer.valueOf(i))) {
            return this.mProfileConnectStateMap.get(Integer.valueOf(i));
        }
        return null;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isConnecting() {
        if (isConnected()) {
            return false;
        }
        return getProfileConnectState(1) == 120 || getProfileConnectState(2) == 120 || getProfileConnectState(256) == 1;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isDisConnecting() {
        return getProfileConnectState(1) == 125 || getProfileConnectState(2) == 125 || getProfileConnectState(256) == 3;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isParing() {
        return getBondState() == 331;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isConnected() {
        if (sDebugLog) {
            int profileConnectState = getProfileConnectState(1);
            int profileConnectState2 = getProfileConnectState(2);
            int profileConnectState3 = getProfileConnectState(256);
            if (profileConnectState != -1 || profileConnectState2 != -1 || profileConnectState3 != -1) {
                Logs.d("xpbluetooth nf isConnected hfp:" + profileConnectState + " a2dp:" + profileConnectState2 + " hid:" + profileConnectState3 + " name:" + this.mName + " addr:" + this.mAddress);
            }
        }
        return getProfileConnectState(1) == 140 || getProfileConnectState(2) >= 140 || getProfileConnectState(256) == 2;
    }

    public boolean isReady() {
        if (sDebugLog) {
            int profileConnectState = getProfileConnectState(1);
            int profileConnectState2 = getProfileConnectState(2);
            int profileConnectState3 = getProfileConnectState(256);
            if (profileConnectState != -1 || profileConnectState2 != -1 || profileConnectState3 != -1) {
                Logs.d("xpbluetooth nf isConnected hfp:" + profileConnectState + " a2dp:" + profileConnectState2 + " hid:" + profileConnectState3 + " name:" + this.mName + " addr:" + this.mAddress);
            }
        }
        return (isConnected() || isConnecting() || isDisConnecting()) ? false : true;
    }

    public ArrayList getConnectedProfile() {
        return this.mConnectedProfileList;
    }

    public boolean isConnectedProfile(int i) {
        return this.mConnectedProfileList.contains(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isBtPhoneConnected() {
        return getProfileConnectState(1) == 140;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2dpConnected() {
        return getProfileConnectState(2) >= 140;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isA2dpConnecting() {
        NFConnectState connectState = getConnectState(2);
        return connectState != null && connectState.getPrevState() == 110 && connectState.getNewState() == 120;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public boolean isHidConnected() {
        return getProfileConnectState(256) == 2;
    }

    public void setBondState(int i) {
        Logs.d("xpbluetooth nf setBondState:" + i + " name:" + this.mName);
        this.mBondState = i;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothDevice
    public int getBondState() {
        return this.mBondState;
    }

    public boolean isBusy() {
        if (sDebugLog) {
            Logs.v("xpbluetooth nf isBusy hfp:" + getProfileConnectState(1) + " a2dp:" + getProfileConnectState(2) + " hid:" + getProfileConnectState(256) + " name:" + this.mName);
        }
        if (isConnected()) {
            return false;
        }
        return getProfileConnectState(1) == 120 || getProfileConnectState(1) == 125 || getProfileConnectState(2) == 120 || getProfileConnectState(2) == 125 || getProfileConnectState(256) == 1 || getProfileConnectState(256) == 3 || getBondState() == 331;
    }

    public String toString() {
        return "NFBluetoothDevice{mName='" + this.mName + "', mAddress='" + this.mAddress + "', mCategory=" + this.mCategory + ", mRemoteSupportProfiles=" + this.mRemoteSupportProfiles + ", mBondState=" + this.mBondState + '}';
    }

    public boolean disconnectHid() {
        HidProfile hidProfile;
        BluetoothDevice bluetoothDevice;
        Logs.d("xpbluetooth nf disconnectHid isHIDSupportedProfile:" + isHIDSupportedProfile() + " mCategory:" + this.mCategory);
        if ((!isHIDSupportedProfile() && this.mCategory != 2) || (hidProfile = this.mHidProfile) == null || (bluetoothDevice = this.mBluetoothDevice) == null) {
            return false;
        }
        boolean disconnect = hidProfile.disconnect(bluetoothDevice);
        Logs.d("xpbluetooth profile disconnect hid " + disconnect);
        return disconnect;
    }
}
