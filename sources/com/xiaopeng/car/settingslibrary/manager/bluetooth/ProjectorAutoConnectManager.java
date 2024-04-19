package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class ProjectorAutoConnectManager {
    public static final int PROFILE_A2DP = 2;
    public static final int PROFILE_HFP = 1;
    public static final int PROFILE_HID = 3;
    private static final int RETRY_PAIR_OR_CONNECT_TIME_OUT = 3000;
    private static final int RETRY_SCAN_TIME_OUT = 3000;
    private IXpBluetoothInterface mBluetoothInterface;
    private Handler mReTryPairOrConnectHandler;
    private Handler mReTryScanHandler;
    ArrayList<IProjectorBluetoothCallback> mProjectorCallbacks = new ArrayList<>();
    private Runnable mRetryScanRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.-$$Lambda$QooTRdgWiCTdNgYcPGT6KNMv_fo
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorAutoConnectManager.this.retryScan();
        }
    };
    private Runnable mRetryPairOrConnectRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.-$$Lambda$Dya15XN1dgDc62qULBPBm3gIxVU
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorAutoConnectManager.this.retryPairOrConnect();
        }
    };
    private boolean mBleWhiteListModeEnable = false;
    private boolean mIsScanTimeout = false;
    private boolean mIsPairOrConnectTimeout = false;

    public ProjectorAutoConnectManager(IXpBluetoothInterface iXpBluetoothInterface) {
        this.mBluetoothInterface = iXpBluetoothInterface;
    }

    public void timeoutForceStopPairOrConnectRetry() {
        Logs.d("xpbluetooth nf timeoutForceStopPairOrConnectRetry");
        this.mIsPairOrConnectTimeout = true;
    }

    public void forceStartPairOrConnectRetry() {
        if (isAlreadyConnectedWhiteList()) {
            Logs.d("xpbluetooth nf isAlreadyConnectedWhiteList forceStartPairOrConnectRetry");
            return;
        }
        Logs.d("xpbluetooth nf forceStartPairOrConnectRetry");
        this.mIsPairOrConnectTimeout = false;
        pairOrConnectReal();
    }

    public void retryPairOrConnect() {
        if (isAlreadyConnectedWhiteList()) {
            Logs.d("xpbluetooth nf isAlreadyConnectedWhiteList retryPairOrConnect");
            return;
        }
        Logs.d("xpbluetooth nf retryPairOrConnect mIsPairOrConnectTimeout:" + this.mIsPairOrConnectTimeout);
        if (this.mIsPairOrConnectTimeout || !this.mBluetoothInterface.isEnable()) {
            if (!isAlreadyBoundedWhiteList()) {
                Iterator<IProjectorBluetoothCallback> it = this.mProjectorCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().onProjectorConnectFailed(2);
                }
            } else {
                Iterator<IProjectorBluetoothCallback> it2 = this.mProjectorCallbacks.iterator();
                while (it2.hasNext()) {
                    it2.next().onProjectorConnectFailed(3);
                }
            }
            this.mIsPairOrConnectTimeout = false;
            Handler handler = this.mReTryPairOrConnectHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRetryPairOrConnectRunnable);
                return;
            }
            return;
        }
        pairOrConnectReal();
    }

    private void pairOrConnectReal() {
        boolean pair;
        if (!isInWhiteListModeAndCinema()) {
            Logs.d("xpbluetooth nf forcePair return!");
            Handler handler = this.mReTryPairOrConnectHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRetryPairOrConnectRunnable);
                return;
            }
            return;
        }
        XpBluetoothDeviceInfo whiteListDevice = BluetoothConnectWhiteList.getInstance().getWhiteListDevice();
        if (whiteListDevice != null) {
            if (isAlreadyBoundedWhiteList()) {
                pair = whiteListDevice.isA2dpConnected() ? false : this.mBluetoothInterface.connect(whiteListDevice);
                Logs.d("xpbluetooth nf pairOrConnectReal connect success:" + pair);
                pairOrConnectRetryHandler(pair);
                return;
            }
            pair = whiteListDevice.getPairState() == 10 ? this.mBluetoothInterface.pair(whiteListDevice, whiteListDevice.getDeviceAddr()) : false;
            Logs.d("xpbluetooth nf pairOrConnectReal pair success:" + pair + " " + whiteListDevice);
            pairOrConnectRetryHandler(pair);
        }
    }

    private void pairOrConnectRetryHandler(boolean z) {
        if (z) {
            return;
        }
        if (this.mReTryPairOrConnectHandler == null) {
            HandlerThread handlerThread = new HandlerThread("retry-pairconnect");
            handlerThread.start();
            this.mReTryPairOrConnectHandler = new Handler(handlerThread.getLooper());
        }
        this.mReTryPairOrConnectHandler.removeCallbacks(this.mRetryPairOrConnectRunnable);
        this.mReTryPairOrConnectHandler.postDelayed(this.mRetryPairOrConnectRunnable, 3000L);
    }

    public void retryScan() {
        if (isAlreadyConnectedWhiteList()) {
            Logs.d("xpbluetooth nf isAlreadyConnectedWhiteList retryScan");
            return;
        }
        Logs.d("xpbluetooth nf retryScan mIsScanTimeout:" + this.mIsScanTimeout);
        if (this.mIsScanTimeout) {
            Iterator<IProjectorBluetoothCallback> it = this.mProjectorCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onProjectorConnectFailed(1);
            }
            this.mIsScanTimeout = false;
            Handler handler = this.mReTryScanHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRetryScanRunnable);
                return;
            }
            return;
        }
        scanReal();
    }

    private void scanReal() {
        if (!isInWhiteListModeAndCinema()) {
            Logs.d("xpbluetooth nf forceStartScan return!");
            Handler handler = this.mReTryScanHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRetryScanRunnable);
            }
        } else if (this.mBluetoothInterface.isEnable()) {
            boolean startScanList = this.mBluetoothInterface.startScanList();
            Logs.d("xpbluetooth nf scanReal success:" + startScanList);
            if (startScanList) {
                return;
            }
            if (this.mReTryScanHandler == null) {
                HandlerThread handlerThread = new HandlerThread("retry-scan");
                handlerThread.start();
                this.mReTryScanHandler = new Handler(handlerThread.getLooper());
            }
            this.mReTryScanHandler.removeCallbacks(this.mRetryScanRunnable);
            this.mReTryScanHandler.postDelayed(this.mRetryScanRunnable, 3000L);
        } else {
            Logs.d("xpbluetooth nf forceStartScan start enable bluetooth");
            this.mBluetoothInterface.setBluetoothEnabled(true);
        }
    }

    public void forceStartScanRetry() {
        if (isAlreadyConnectedWhiteList()) {
            Logs.d("xpbluetooth nf isAlreadyConnectedWhiteList forceStartScanRetry");
            return;
        }
        Logs.d("xpbluetooth nf forceStartScanRetry");
        this.mIsScanTimeout = false;
        scanReal();
    }

    public boolean isActiveProjectorDevice(String str) {
        return isInWhiteListModeAndCinema() && BluetoothConnectWhiteList.getInstance().isInWhiteList(str);
    }

    private boolean isActiveProjectorDeviceByAddress(String str) {
        XpBluetoothDeviceInfo whiteListDevice;
        return isInWhiteListModeAndCinema() && (whiteListDevice = BluetoothConnectWhiteList.getInstance().getWhiteListDevice()) != null && whiteListDevice.getDeviceAddr().equals(str);
    }

    public boolean isInWhiteListModeAndCinema() {
        return this.mBleWhiteListModeEnable && XuiSettingsManager.getInstance().isInMode(XuiSettingsManager.USER_SCENARIO_CINEMA_MODE);
    }

    public boolean isAlreadyConnectedWhiteList() {
        Iterator<XpBluetoothDeviceInfo> it = this.mBluetoothInterface.getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            if (BluetoothConnectWhiteList.getInstance().isInWhiteList(next.getDeviceName()) && next.isA2dpConnected()) {
                Logs.d("xpbluetooth nf isAlreadyConnectedWhiteList");
                XuiSettingsManager.getInstance().playBtMedia();
                return true;
            }
        }
        return false;
    }

    public boolean isFoundWhiteListDevice() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mBluetoothInterface.getAvailableDevicesSorted());
        arrayList.addAll(this.mBluetoothInterface.getBondedDevicesSorted());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo xpBluetoothDeviceInfo = (XpBluetoothDeviceInfo) it.next();
            XpBluetoothDeviceInfo whiteListDevice = BluetoothConnectWhiteList.getInstance().getWhiteListDevice();
            if (whiteListDevice != null && !TextUtils.isEmpty(whiteListDevice.getDeviceName()) && whiteListDevice.getDeviceName().equals(xpBluetoothDeviceInfo.getDeviceName()) && !TextUtils.isEmpty(whiteListDevice.getDeviceAddr()) && whiteListDevice.getDeviceAddr().equals(xpBluetoothDeviceInfo.getDeviceAddr())) {
                return true;
            }
        }
        return false;
    }

    public boolean isTmpFoundWhiteListDevice(ArrayList<XpBluetoothDeviceInfo> arrayList) {
        Iterator<XpBluetoothDeviceInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if (BluetoothConnectWhiteList.getInstance().isInWhiteList(it.next().getDeviceName())) {
                return true;
            }
        }
        return false;
    }

    public void setBleWhiteListMode(boolean z) {
        this.mBleWhiteListModeEnable = z;
        Logs.d("xpbluetooth nf setBleWhiteListMode:" + z);
    }

    public void timeoutForceStopScanRetry() {
        Logs.d("xpbluetooth nf timeoutForceStopScanRetry");
        this.mIsScanTimeout = true;
    }

    public boolean isAlreadyBoundedWhiteList() {
        Iterator<XpBluetoothDeviceInfo> it = this.mBluetoothInterface.getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            if (BluetoothConnectWhiteList.getInstance().isInWhiteList(it.next().getDeviceName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectingWhiteList() {
        Iterator<XpBluetoothDeviceInfo> it = this.mBluetoothInterface.getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            if (BluetoothConnectWhiteList.getInstance().isInWhiteList(next.getDeviceName())) {
                return next.isA2dpConnecting();
            }
        }
        return false;
    }

    public boolean isBondingWhiteList() {
        Iterator<XpBluetoothDeviceInfo> it = this.mBluetoothInterface.getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            if (BluetoothConnectWhiteList.getInstance().isInWhiteList(next.getDeviceName())) {
                return next.isParingBusy();
            }
        }
        return false;
    }

    public void notifyBondChanged(int i, int i2) {
        Iterator<IProjectorBluetoothCallback> it = this.mProjectorCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onProjectorBoundStatus(i, i2);
        }
    }

    public void parseScanResult(ArrayList<XpBluetoothDeviceInfo> arrayList) {
        if (isInWhiteListModeAndCinema()) {
            if (!isTmpFoundWhiteListDevice(arrayList)) {
                retryScan();
                return;
            }
            Iterator<XpBluetoothDeviceInfo> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                XpBluetoothDeviceInfo next = it.next();
                if (BluetoothConnectWhiteList.getInstance().isInWhiteList(next.getDeviceName())) {
                    BluetoothConnectWhiteList.getInstance().fillWhiteList(next);
                    Logs.d("xpbluetooth nf now in capsule mode and guide page, found device:" + next);
                    break;
                }
            }
            Iterator<IProjectorBluetoothCallback> it2 = this.mProjectorCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onProjectorFounded();
            }
        }
    }

    public void a2dpChanged(String str, int i, int i2) {
        if (isActiveProjectorDeviceByAddress(str)) {
            if (i == 1 && i2 == 0) {
                retryPairOrConnect();
            }
            Iterator<IProjectorBluetoothCallback> it = this.mProjectorCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onProjectorConnectStatus(i, i2);
            }
            if (i == 1 && i2 == 2) {
                XuiSettingsManager.getInstance().playBtMedia();
            }
        }
    }

    public void refreshBleConnectedData(int i, XpBluetoothDeviceInfo xpBluetoothDeviceInfo, int i2) {
        if (xpBluetoothDeviceInfo == null) {
            return;
        }
        if (i == 2 || i == 2) {
            setConnectedBleDevice(xpBluetoothDeviceInfo);
        } else if (i == 0 || i == 0) {
            if (1 == i2 && !xpBluetoothDeviceInfo.isA2dpConnected() && !xpBluetoothDeviceInfo.isHidConnected()) {
                setConnectedBleDeviceUpdate();
            } else if (2 == i2 && !xpBluetoothDeviceInfo.isHfpConnected() && !xpBluetoothDeviceInfo.isHidConnected()) {
                setConnectedBleDeviceUpdate();
            } else if (3 != i2 || xpBluetoothDeviceInfo.isHfpConnected() || xpBluetoothDeviceInfo.isA2dpConnected()) {
            } else {
                setConnectedBleDeviceUpdate();
            }
        }
    }

    public void setConnectedBleDeviceUpdate() {
        Iterator<XpBluetoothDeviceInfo> it = this.mBluetoothInterface.getBondedDevicesSorted().iterator();
        while (it.hasNext()) {
            XpBluetoothDeviceInfo next = it.next();
            if (next != null && (next.isA2dpConnected() || next.isHfpConnected() || next.isHidConnected())) {
                if (setConnectedBleDevice(next)) {
                    return;
                }
            }
        }
        DataRepository.getInstance().setConnectedBleDevice(CarSettingsApp.getContext(), "");
    }

    private boolean setConnectedBleDevice(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (xpBluetoothDeviceInfo == null || !xpBluetoothDeviceInfo.isA2DPSupportedProfile() || TextUtils.isEmpty(xpBluetoothDeviceInfo.getDeviceName())) {
            return false;
        }
        if (xpBluetoothDeviceInfo.getDeviceName().equals(DataRepository.getInstance().getConnectedBleDevice(CarSettingsApp.getContext()))) {
            return true;
        }
        DataRepository.getInstance().setConnectedBleDevice(CarSettingsApp.getContext(), xpBluetoothDeviceInfo.getDeviceName());
        return true;
    }

    public void registerProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        if (iProjectorBluetoothCallback == null || this.mProjectorCallbacks.contains(iProjectorBluetoothCallback)) {
            return;
        }
        this.mProjectorCallbacks.add(iProjectorBluetoothCallback);
    }

    public void unregisterProjectorStateCallback(IProjectorBluetoothCallback iProjectorBluetoothCallback) {
        if (iProjectorBluetoothCallback != null) {
            this.mProjectorCallbacks.remove(iProjectorBluetoothCallback);
        }
    }
}
