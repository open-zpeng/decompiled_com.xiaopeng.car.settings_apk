package com.xiaopeng.car.settingslibrary.vm.usb;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.VolumeInfo;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.usb.XpUsbManager;
import com.xiaopeng.car.settingslibrary.vm.app.entry.StorageSize;
import com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel;
import com.xiaopeng.xuimanager.karaoke.KaraokeManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class UsbSettingsViewModel extends AndroidViewModel {
    public static final int OFFICIAL_MICROPHONE_VENDORID = 3190;
    private static final int POWERED_OFF = 0;
    private static final int POWERED_ON = 1;
    private static final String TAG = "UsbSettingsViewModel";
    private Application mApplication;
    private MutableLiveData<Boolean> mFinishUILiveData;
    private IntentFilter mIntentFilter;
    private List<XpUsbDeviceInfo> mList;
    private MutableLiveData<List<XpUsbDeviceInfo>> mListLiveData;
    private final Object mLock;
    private final StorageEventListener mStorageListener;
    private XpUsbManager mUsbManager;
    private final KaraokeManager.MicCallBack micCallBack;
    private final BroadcastReceiver usbReceiver;

    public UsbSettingsViewModel(Application application) {
        super(application);
        this.mIntentFilter = new IntentFilter();
        this.mList = new CopyOnWriteArrayList();
        this.mListLiveData = new MutableLiveData<>();
        this.mFinishUILiveData = new MutableLiveData<>();
        this.mLock = new Object();
        this.mStorageListener = new AnonymousClass1();
        this.micCallBack = new KaraokeManager.MicCallBack() { // from class: com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel.2
            public void onErrorEvent(int i, int i2) {
            }

            public void micServiceCallBack(int i) {
                Logs.d("UsbSettingsViewModel micServiceCallBack = " + i);
                if (i == 5) {
                    UsbSettingsViewModel.this.refreshMicrophonePowerStatus(true);
                } else if (i == 6 || i == 4) {
                    UsbSettingsViewModel.this.refreshMicrophonePowerStatus(false);
                }
            }
        };
        this.usbReceiver = new AnonymousClass3();
        this.mApplication = application;
        this.mUsbManager = new XpUsbManager(application);
        this.mIntentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        this.mIntentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mIntentFilter.setPriority(Integer.MAX_VALUE);
    }

    public MutableLiveData<Boolean> getFinishUILiveData() {
        return this.mFinishUILiveData;
    }

    public MutableLiveData<List<XpUsbDeviceInfo>> getListLiveData() {
        return this.mListLiveData;
    }

    public void startQuery() {
        try {
            synchronized (this.mLock) {
                this.mList.clear();
                Logs.d("xpUsbDeviceInfo startQuery");
                ArrayList arrayList = new ArrayList();
                List<VolumeInfo> publicVolumeInfos = this.mUsbManager.getPublicVolumeInfos();
                initDeviceInfo(this.mUsbManager.getAttachedUsbDevices(), publicVolumeInfos);
                mergeStorageDevice(publicVolumeInfos);
                Logs.d("UsbSettingsViewModel mVolumeInfoList " + publicVolumeInfos.toString());
                Logs.d("UsbSettingsViewModel mList " + this.mList.toString());
                postValue(this.mList);
                for (XpUsbDeviceInfo xpUsbDeviceInfo : this.mList) {
                    if (xpUsbDeviceInfo.getCategory() == 2 && arrayList.size() <= publicVolumeInfos.size()) {
                        arrayList.add(xpUsbDeviceInfo);
                    }
                }
                startLoader(publicVolumeInfos, arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mergeStorageDevice(List<VolumeInfo> list) {
        for (VolumeInfo volumeInfo : list) {
            XpUsbDeviceInfo xpUsbDeviceInfo = new XpUsbDeviceInfo();
            xpUsbDeviceInfo.setConnected(true);
            xpUsbDeviceInfo.setCategory(2);
            setVolumeInfo(xpUsbDeviceInfo, volumeInfo);
            this.mList.add(xpUsbDeviceInfo);
        }
    }

    private void postValue(List<XpUsbDeviceInfo> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (XpUsbDeviceInfo xpUsbDeviceInfo : list) {
            if (!xpUsbDeviceInfo.isOfficial()) {
                arrayList.add(xpUsbDeviceInfo);
            } else {
                arrayList2.add(xpUsbDeviceInfo);
            }
        }
        Collections.reverse(arrayList);
        arrayList2.addAll(arrayList);
        Logs.d("UsbSettingsViewModel tempList1 " + arrayList2.toString());
        if (ThreadUtils.isMainThread()) {
            this.mListLiveData.setValue(arrayList2);
        } else {
            this.mListLiveData.postValue(arrayList2);
        }
    }

    private void initDeviceInfo(List<UsbDevice> list, List<VolumeInfo> list2) {
        int i = 0;
        for (UsbDevice usbDevice : list) {
            XpUsbDeviceInfo xpUsbDeviceInfo = new XpUsbDeviceInfo();
            int deviceType = this.mUsbManager.getDeviceType(usbDevice);
            if (deviceType == 3 && usbDevice.getVendorId() != 3190) {
                deviceType = 0;
            }
            if (deviceType == 2 && (i = i + 1) > list2.size()) {
                return;
            }
            int micPowerStatus = getMicPowerStatus();
            Logs.d("UsbSettingsViewModel getMicPowerStatus " + micPowerStatus);
            Logs.d("initDeviceInfo device:" + usbDevice + " getProductName:" + usbDevice.getProductName());
            xpUsbDeviceInfo.setUsbDevice(usbDevice);
            xpUsbDeviceInfo.setOfficial(false);
            xpUsbDeviceInfo.setDeviceName(usbDevice.getProductName());
            xpUsbDeviceInfo.setCapacity(0L);
            xpUsbDeviceInfo.setUsedSpace(0L);
            xpUsbDeviceInfo.setCategory(deviceType);
            if (micPowerStatus == 0) {
                xpUsbDeviceInfo.setConnected(false);
            } else {
                xpUsbDeviceInfo.setConnected(true);
            }
            if (usbDevice.getVendorId() == 3190) {
                xpUsbDeviceInfo.setOfficial(true);
                this.mList.add(0, xpUsbDeviceInfo);
            } else if (deviceType != 2) {
                this.mList.add(xpUsbDeviceInfo);
            }
        }
    }

    private void startLoader(List<VolumeInfo> list, List<XpUsbDeviceInfo> list2) {
        Logs.d("xpUsbDeviceInfo startLoader volume:");
        int i = 0;
        for (VolumeInfo volumeInfo : list) {
            if (i <= list2.size() - 1) {
                int i2 = i + 1;
                XpUsbDeviceInfo xpUsbDeviceInfo = list2.get(i);
                setVolumeInfo(xpUsbDeviceInfo, volumeInfo);
                if (xpUsbDeviceInfo.getCapacity() == 0) {
                    xpUsbDeviceInfo.setConnected(true);
                } else {
                    xpUsbDeviceInfo.setConnected(true);
                }
                i = i2;
            }
        }
        postValue(this.mList);
    }

    private void setVolumeInfo(XpUsbDeviceInfo xpUsbDeviceInfo, VolumeInfo volumeInfo) {
        String bestVolumeDescription = this.mUsbManager.getBestVolumeDescription(volumeInfo);
        StorageSize volumeStorageInfo = this.mUsbManager.getVolumeStorageInfo(volumeInfo);
        Logs.d("setVolumeInfo name:" + bestVolumeDescription + " xp:" + xpUsbDeviceInfo);
        xpUsbDeviceInfo.setVolumeInfo(volumeInfo);
        xpUsbDeviceInfo.setDeviceName(bestVolumeDescription);
        xpUsbDeviceInfo.setCapacity(volumeStorageInfo.getTotalSize());
        xpUsbDeviceInfo.setUsedSpace(volumeStorageInfo.getUsedSize());
    }

    public void registerBroadcast() {
        this.mApplication.registerReceiver(this.usbReceiver, this.mIntentFilter);
    }

    public void unRegisterBroadcast() {
        Logs.d("xpUsbDeviceInfo unRegisterBroadcast ");
        this.mApplication.unregisterReceiver(this.usbReceiver);
    }

    public void registerListener() {
        this.mUsbManager.registerListener(this.mStorageListener);
    }

    public void unRegisterListener() {
        this.mUsbManager.unregisterListener(this.mStorageListener);
    }

    public void registerOfficialKaraokPowerListener() {
        this.mUsbManager.registerOfficialKaraokePowerListener(this.micCallBack);
    }

    public void unRegisterOfficiallKaraokPowerListener() {
        this.mUsbManager.unRegisterOfficialKaraokePowerListener();
    }

    private int getMicPowerStatus() {
        return this.mUsbManager.getMicPowerStatus();
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mList.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 extends StorageEventListener {
        AnonymousClass1() {
        }

        public void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            Logs.d("xpstorage StorageEventListener onVolumeStateChanged oldState:" + i + " newState:" + i2 + " vol:" + volumeInfo);
            if (i != 1 || i2 != 2) {
                if (i == 5 && i2 == 0 && UsbSettingsViewModel.isInteresting(volumeInfo)) {
                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.usb.-$$Lambda$UsbSettingsViewModel$1$hV0KCoSggnHuFyacUv7Lz80I71M
                        @Override // java.lang.Runnable
                        public final void run() {
                            UsbSettingsViewModel.AnonymousClass1.this.lambda$onVolumeStateChanged$1$UsbSettingsViewModel$1();
                        }
                    });
                }
            } else if (UsbSettingsViewModel.isInteresting(volumeInfo)) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.usb.-$$Lambda$UsbSettingsViewModel$1$-YQDQetRVqXAbKt-2xRo2ussXMo
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbSettingsViewModel.AnonymousClass1.this.lambda$onVolumeStateChanged$0$UsbSettingsViewModel$1();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onVolumeStateChanged$0$UsbSettingsViewModel$1() {
            UsbSettingsViewModel.this.startQuery();
        }

        public /* synthetic */ void lambda$onVolumeStateChanged$1$UsbSettingsViewModel$1() {
            UsbSettingsViewModel.this.startQuery();
        }

        public void onDiskDestroyed(DiskInfo diskInfo) {
            Logs.d("xpstorage StorageEventListener onDiskDestroyed disk:" + diskInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInteresting(VolumeInfo volumeInfo) {
        return volumeInfo.getType() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMicrophonePowerStatus(boolean z) {
        synchronized (this.mLock) {
            XpUsbDeviceInfo xpUsbDeviceInfo = null;
            Iterator<XpUsbDeviceInfo> it = this.mList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                XpUsbDeviceInfo next = it.next();
                if (next.isOfficial()) {
                    xpUsbDeviceInfo = next;
                    break;
                }
            }
            if (z) {
                if (xpUsbDeviceInfo != null) {
                    xpUsbDeviceInfo.setCategory(3);
                    xpUsbDeviceInfo.setOfficial(true);
                    xpUsbDeviceInfo.setConnected(true);
                }
            } else if (xpUsbDeviceInfo != null) {
                xpUsbDeviceInfo.setConnected(false);
            }
            postValue(this.mList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 extends BroadcastReceiver {
        AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(Config.ACTION_SCREEN_STATUS_CHANGE_DEVICE_EXTRA);
            String action = intent.getAction();
            Logs.d("xpUsbDeviceInfo on receive device:" + usbDevice);
            XpUsbManager unused = UsbSettingsViewModel.this.mUsbManager;
            if (!XpUsbManager.isValid(usbDevice)) {
                Logs.d("xpUsbDeviceInfo onReceive return!");
                return;
            }
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.usb.-$$Lambda$UsbSettingsViewModel$3$eUTS_PkfLyr0cCrHeGe7L8f5Fzc
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbSettingsViewModel.AnonymousClass3.this.lambda$onReceive$0$UsbSettingsViewModel$3();
                    }
                });
            }
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                Logs.d("UsbSettingViewModel broadcast ->attach diak.");
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.usb.-$$Lambda$UsbSettingsViewModel$3$TbmeFUpmmzOkYaeYssJAtC8DC78
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbSettingsViewModel.AnonymousClass3.this.lambda$onReceive$1$UsbSettingsViewModel$3();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onReceive$0$UsbSettingsViewModel$3() {
            UsbSettingsViewModel.this.startQuery();
        }

        public /* synthetic */ void lambda$onReceive$1$UsbSettingsViewModel$3() {
            UsbSettingsViewModel.this.startQuery();
        }
    }
}
