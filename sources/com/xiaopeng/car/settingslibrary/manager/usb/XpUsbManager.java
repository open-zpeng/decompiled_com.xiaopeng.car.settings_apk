package com.xiaopeng.car.settingslibrary.manager.usb;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.vm.app.entry.StorageSize;
import com.xiaopeng.xuimanager.karaoke.KaraokeManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/* loaded from: classes.dex */
public class XpUsbManager {
    private static final String TAG = "XpUsbManager";
    XuiSettingsManager mManager = XuiSettingsManager.getInstance();
    private StorageManager mStorageManager;
    private StorageStatsManager mStorageStatsManager;
    private UsbManager mUsbManager;

    public XpUsbManager(Context context) {
        this.mUsbManager = (UsbManager) context.getSystemService(UsbManager.class);
        this.mStorageStatsManager = (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
        this.mStorageManager = (StorageManager) context.getSystemService(StorageManager.class);
    }

    public void registerOfficialKaraokePowerListener(KaraokeManager.MicCallBack micCallBack) {
        this.mManager.registerOfficialKaraokePowerListener(micCallBack);
    }

    public void unRegisterOfficialKaraokePowerListener() {
        this.mManager.unRegisterOfficialKaraokePowerListener();
    }

    public int getMicPowerStatus() {
        return this.mManager.getMicPowerStatus();
    }

    public List<UsbDevice> getAttachedUsbDevices() {
        HashMap<String, UsbDevice> deviceList = this.mUsbManager.getDeviceList();
        Logs.d("getAttachedUsbDevices map.size: " + deviceList.size());
        ArrayList arrayList = new ArrayList();
        for (UsbDevice usbDevice : deviceList.values()) {
            Logs.d("getAttachedUsbDevices UsbDevice: " + usbDevice);
            if (isValid(usbDevice)) {
                arrayList.add(usbDevice);
            }
        }
        return arrayList;
    }

    public int getDeviceType(UsbDevice usbDevice) {
        String name = usbDevice.getConfigurationCount() > 0 ? usbDevice.getConfiguration(0).getName() : "";
        if (name != null) {
            String lowerCase = name.toLowerCase();
            if (lowerCase.contains("mtp") || lowerCase.contains("hdb") || lowerCase.contains("adb") || lowerCase.contains("ptp") || lowerCase.contains("mass_storage")) {
                return 1;
            }
        }
        if (usbDevice.getInterfaceCount() == 1 && usbDevice.getInterface(0).getInterfaceClass() == 8) {
            return 2;
        }
        int interfaceCount = usbDevice.getInterfaceCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < interfaceCount; i++) {
            arrayList.add(Integer.valueOf(usbDevice.getInterface(i).getInterfaceClass()));
            Logs.d("getDeviceType InterfaceClass:" + usbDevice.getInterface(i).getInterfaceClass());
        }
        if (arrayList.contains(1) && arrayList.contains(3)) {
            return 3;
        }
        return (arrayList.contains(1) && arrayList.contains(14)) ? 4 : 0;
    }

    public StorageSize getVolumeStorageInfo(VolumeInfo volumeInfo) {
        long j;
        long j2;
        long j3 = 0;
        if (volumeInfo.isMountedReadable()) {
            File path = volumeInfo.getPath();
            if (volumeInfo.getType() == 1) {
                try {
                    j2 = this.mStorageStatsManager.getTotalBytes(volumeInfo.getFsUuid());
                } catch (IOException e) {
                    e = e;
                    j2 = 0;
                }
                try {
                    j3 = this.mStorageStatsManager.getFreeBytes(volumeInfo.getFsUuid());
                } catch (IOException e2) {
                    e = e2;
                    Logs.d("xpsdstorage getSDSize " + e);
                    long j4 = j3;
                    j3 = j2;
                    j = j4;
                    return new StorageSize(j3, j);
                }
                long j42 = j3;
                j3 = j2;
                j = j42;
            } else {
                j3 = path.getTotalSpace();
                j = path.getFreeSpace();
            }
        } else {
            j = 0;
        }
        return new StorageSize(j3, j);
    }

    public String getBestVolumeDescription(VolumeInfo volumeInfo) {
        return this.mStorageManager.getBestVolumeDescription(volumeInfo);
    }

    public List<VolumeInfo> getPublicVolumeInfos() {
        ArrayList arrayList = new ArrayList();
        for (VolumeInfo volumeInfo : this.mStorageManager.getVolumes()) {
            Logs.d("getPublicVolumeInfos info:" + volumeInfo);
            if (volumeInfo.getType() == 0 && volumeInfo.isMountedReadable()) {
                arrayList.add(volumeInfo);
            }
        }
        return arrayList;
    }

    public void registerListener(StorageEventListener storageEventListener) {
        this.mStorageManager.registerListener(storageEventListener);
    }

    public void unregisterListener(StorageEventListener storageEventListener) {
        this.mStorageManager.unregisterListener(storageEventListener);
    }

    public static boolean isValid(UsbDevice usbDevice) {
        if (usbDevice == null || usbDevice.getVendorId() != 2578) {
            UsbInterface[] usbInterfaces = getUsbInterfaces(usbDevice);
            if (usbInterfaces != null) {
                int length = usbInterfaces.length;
                for (int i = 0; i < length; i++) {
                    UsbInterface usbInterface = usbInterfaces[i];
                    int interfaceClass = usbInterface != null ? usbInterface.getInterfaceClass() : -1;
                    if (interfaceClass == 239 || interfaceClass == 11 || interfaceClass == 10 || interfaceClass == 13 || interfaceClass == 224) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static UsbInterface[] getUsbInterfaces(UsbDevice usbDevice) {
        int interfaceCount;
        if (usbDevice == null || (interfaceCount = usbDevice.getInterfaceCount()) <= 0) {
            return null;
        }
        UsbInterface[] usbInterfaceArr = new UsbInterface[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            usbInterfaceArr[i] = usbDevice.getInterface(i);
        }
        return usbInterfaceArr;
    }
}
