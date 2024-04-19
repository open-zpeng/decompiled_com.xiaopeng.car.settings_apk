package com.xiaopeng.car.settingslibrary.manager.car;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.avas.CarAvasManager;
import android.car.hardware.bcm.CarBcmManager;
import android.car.hardware.hvac.CarHvacManager;
import android.car.hardware.icm.CarIcmManager;
import android.car.hardware.mcu.CarMcuManager;
import android.car.hardware.power.CarPowerManager;
import android.car.hardware.scu.CarScuManager;
import android.car.hardware.tbox.CarTboxManager;
import android.car.hardware.vcu.CarVcuManager;
import android.car.hardware.xpu.CarXpuManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.media.audioConfig.AudioConfig;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class CarSettingsManager {
    public static final int ALARM_VOLUME_HIGH = 2;
    public static final int ALARM_VOLUME_LOW = 0;
    public static final int ALARM_VOLUME_MIDDLE = 1;
    private static final int CONNECT_RETRY_COUNT = 5;
    public static final int GEAR_LEVEL_P = 4;
    public static final int GEAR_LEVEL_R = 3;
    public static final int ID_SCU_PHONE_PK_BTN = 557852183;
    public static final int ID_SCU_REMOTE_PK_BTN = 557852186;
    public static final int ID_VCU_GEAR_LEVEL = 557847045;
    public static final int POWER_OFF_COUNTDOWN_INVALID_VALUE = 63;
    public static final int REMOTE_PARKING_TIMEOUT = 300;
    public static final int SOLDIER_SW_LEVEL_1 = 1;
    public static final int SOLDIER_SW_LEVEL_2 = 2;
    public static final int SOLDIER_SW_LEVEL_3 = 3;
    public static final int SOLDIER_SW_OFF = 0;
    private static final String TAG = "CarSettingsManager";
    public static final int XPU_OFF = 0;
    public static final int XPU_ON = 1;
    public static final int XPU_STATUS_FAILURE = 2;
    public static final int XPU_STATUS_START_UP_FAILURE = 5;
    public static final int XPU_TURN_OFF_IN_PROGRESS = 4;
    public static final int XPU_TURN_ON_IN_PROGRESS = 3;
    private static volatile CarSettingsManager sInstance;
    public static ConcurrentHashMap<Integer, Boolean> sSignalLostErrorCacheMap = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<IAvasChangeListener> mAvasChangeListener;
    private List<Integer> mAvasPropertyIds;
    private CopyOnWriteArrayList<IBcmChangeListener> mBcmListenerList;
    private List<Integer> mBcmPropertyIds;
    private Car mCarApiClient;
    private CarAvasManager.CarAvasEventCallback mCarAvasEventCallback;
    private CarAvasManager mCarAvasManager;
    private CarBcmManager.CarBcmEventCallback mCarBcmEventCallback;
    private CarBcmManager mCarBcmManager;
    private final ServiceConnection mCarConnectionCb;
    private CarHvacManager mCarHvacManager;
    private CarIcmManager.CarIcmEventCallback mCarIcmEventCallback;
    private CarIcmManager mCarIcmManager;
    private CarMcuManager.CarMcuEventCallback mCarMcuEventCallback;
    private CarMcuManager mCarMcuManager;
    private CarPowerManager mCarPowerManager;
    private CarScuManager.CarScuEventCallback mCarScuEventCallback;
    private CarScuManager mCarScuManager;
    private CarTboxManager.CarTboxEventCallback mCarTboxEventCallback;
    private CarTboxManager mCarTboxManager;
    private CarVcuManager.CarVcuEventCallback mCarVcuEventCallback;
    private CarVcuManager mCarVcuManager;
    private CarXpuManager.CarXpuEventCallback mCarXpuEventCallback;
    private CarXpuManager mCarXpuManager;
    private List<Integer> mIcmPropertyIds = new CopyOnWriteArrayList();
    private List<Integer> mIcmVolumePropertyIds;
    private List<Integer> mMcuAutoPowerPropertyIds;
    private CopyOnWriteArrayList<IMcuChangeListener> mMcuChangeListener;
    private List<Integer> mMcuPropertyIds;
    private CopyOnWriteArrayList<MeterCallback> mMeterCallbackList;
    private List<OnServiceConnectCompleteListener> mOnServiceConnectCompleteListenerList;
    private final CopyOnWriteArrayList<IPowerChangeListener> mPowerChangeListeners;
    private int mRetry;
    private CopyOnWriteArrayList<IScuChangeListener> mScuListenerList;
    private List<Integer> mScuPropertyIds;
    private List<Integer> mTboxAutoPowerPropertyIds;
    private CopyOnWriteArrayList<ITboxChangeListener> mTboxChangeListener;
    private List<Integer> mTboxPropertyIds;
    private CopyOnWriteArrayList<IVcuChangeListener> mVcuListenerList;
    private List<Integer> mVcuPropertyIds;
    private List<Integer> mWifiKeyPropertyIds;
    private List<Integer> mXpuApRemotePropertyIds;
    private CopyOnWriteArrayList<IXpuChangeListener> mXpuChangeListener;
    private List<Integer> mXpuPropertyIds;

    /* loaded from: classes.dex */
    public interface OnServiceConnectCompleteListener {
        void onServiceConnectComplete();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface PmListenerCallback {
        void callback(int i, CompletableFuture<Void> completableFuture);
    }

    static /* synthetic */ int access$1008(CarSettingsManager carSettingsManager) {
        int i = carSettingsManager.mRetry;
        carSettingsManager.mRetry = i + 1;
        return i;
    }

    public static boolean isDeviceSignalLostFault(int i) {
        for (Map.Entry<Integer, Boolean> entry : sSignalLostErrorCacheMap.entrySet()) {
            if (i == entry.getKey().intValue()) {
                return entry.getValue().booleanValue();
            }
        }
        return false;
    }

    public void addServiceConnectCompleteListener(OnServiceConnectCompleteListener onServiceConnectCompleteListener) {
        if (this.mOnServiceConnectCompleteListenerList.contains(onServiceConnectCompleteListener)) {
            return;
        }
        this.mOnServiceConnectCompleteListenerList.add(onServiceConnectCompleteListener);
    }

    public void removeServiceConnectCompleteListener(OnServiceConnectCompleteListener onServiceConnectCompleteListener) {
        this.mOnServiceConnectCompleteListenerList.remove(onServiceConnectCompleteListener);
    }

    public void notifyServiceConnectComplete() {
        for (OnServiceConnectCompleteListener onServiceConnectCompleteListener : this.mOnServiceConnectCompleteListenerList) {
            onServiceConnectCompleteListener.onServiceConnectComplete();
        }
    }

    public static CarSettingsManager getInstance() {
        if (sInstance == null) {
            synchronized (CarSettingsManager.class) {
                if (sInstance == null) {
                    sInstance = new CarSettingsManager();
                }
            }
        }
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarPowerManager() {
        try {
            this.mCarPowerManager = (CarPowerManager) this.mCarApiClient.getCarManager("power");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
        if (this.mCarPowerManager == null || !CarFunction.isSupportWifiKey()) {
            return;
        }
        CarApi.registerPmListener(this.mCarPowerManager, new PmListenerCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.-$$Lambda$CarSettingsManager$94J-nj5xdoA_laBj_3ewnnM7bck
            @Override // com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.PmListenerCallback
            public final void callback(int i, CompletableFuture completableFuture) {
                CarSettingsManager.this.lambda$initCarPowerManager$0$CarSettingsManager(i, completableFuture);
            }
        });
    }

    public /* synthetic */ void lambda$initCarPowerManager$0$CarSettingsManager(int i, CompletableFuture completableFuture) {
        Iterator<IPowerChangeListener> it = this.mPowerChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onPowerStateChange(i, completableFuture);
        }
    }

    private CarSettingsManager() {
        this.mIcmPropertyIds.add(557848078);
        this.mIcmPropertyIds.add(554702353);
        this.mIcmVolumePropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportMeterVolumeSp()) {
            this.mIcmVolumePropertyIds.add(557848125);
        } else {
            this.mIcmVolumePropertyIds.add(557848069);
        }
        this.mScuPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportRemotePark()) {
            this.mScuPropertyIds.add(557852225);
        }
        if (CarFunction.isSupportKeyPark()) {
            this.mScuPropertyIds.add(Integer.valueOf((int) ID_SCU_REMOTE_PK_BTN));
        }
        if (CarFunction.isSupportRemoteParkAdvanced()) {
            this.mScuPropertyIds.add(Integer.valueOf((int) ID_SCU_PHONE_PK_BTN));
        }
        this.mBcmPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportLeaveLock()) {
            this.mBcmPropertyIds.add(557849716);
        }
        if (CarFunction.isSupportNearUnlock()) {
            this.mBcmPropertyIds.add(557849717);
        }
        if (CarFunction.isSupportInductionLock()) {
            this.mBcmPropertyIds.add(557849646);
        }
        this.mVcuPropertyIds = new CopyOnWriteArrayList();
        this.mVcuPropertyIds.add(Integer.valueOf((int) ID_VCU_GEAR_LEVEL));
        this.mTboxPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportTboxAutoPower()) {
            this.mTboxPropertyIds.add(557846575);
        }
        if (CarFunction.isSupportSoldierModeLevel()) {
            this.mTboxPropertyIds.add(557846578);
            this.mTboxPropertyIds.add(557846596);
        }
        if (CarFunction.isSupportSoldierModeCamera()) {
            this.mTboxPropertyIds.add(557846594);
        }
        this.mTboxAutoPowerPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportTboxAutoPower()) {
            this.mTboxAutoPowerPropertyIds.add(557846576);
            this.mTboxAutoPowerPropertyIds.add(557912113);
        }
        this.mAvasPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportLowSpeedVolumeSlider()) {
            this.mAvasPropertyIds.add(557855244);
        }
        this.mMcuPropertyIds = new CopyOnWriteArrayList();
        this.mMcuPropertyIds.add(557847561);
        this.mMcuPropertyIds.add(557847658);
        this.mWifiKeyPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportWifiKey()) {
            this.mWifiKeyPropertyIds.add(557847660);
        }
        this.mMcuAutoPowerPropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportMcuAutoPower()) {
            this.mMcuAutoPowerPropertyIds.add(557847634);
            this.mMcuAutoPowerPropertyIds.add(557847635);
        }
        this.mXpuPropertyIds = new CopyOnWriteArrayList();
        if (CarConfigHelper.hasXpu()) {
            this.mXpuPropertyIds.add(557856775);
        }
        this.mXpuApRemotePropertyIds = new CopyOnWriteArrayList();
        if (CarFunction.isSupportCarCallAdvanced()) {
            this.mXpuApRemotePropertyIds.add(Integer.valueOf((int) CarApi.ID_XPU_AP_REMOTE_SW));
        }
        this.mMeterCallbackList = new CopyOnWriteArrayList<>();
        this.mScuListenerList = new CopyOnWriteArrayList<>();
        this.mBcmListenerList = new CopyOnWriteArrayList<>();
        this.mVcuListenerList = new CopyOnWriteArrayList<>();
        this.mTboxChangeListener = new CopyOnWriteArrayList<>();
        this.mAvasChangeListener = new CopyOnWriteArrayList<>();
        this.mMcuChangeListener = new CopyOnWriteArrayList<>();
        this.mXpuChangeListener = new CopyOnWriteArrayList<>();
        this.mPowerChangeListeners = new CopyOnWriteArrayList<>();
        this.mRetry = 0;
        this.mOnServiceConnectCompleteListenerList = new ArrayList();
        this.mCarConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                CarSettingsManager.this.initCarManager();
                CarSettingsManager.this.initCarScuManager();
                CarSettingsManager.this.initCarBcmManager();
                CarSettingsManager.this.initCarVcuManager();
                CarSettingsManager.this.initCarTboxManager();
                CarSettingsManager.this.initCarHvacManager();
                CarSettingsManager.this.initCarAvasManager();
                CarSettingsManager.this.initCarMcuManager();
                CarSettingsManager.this.initCarXpuManager();
                CarSettingsManager.this.initCarPowerManager();
                Logs.d("xpcarservice init completed!");
                CarSettingsManager.this.restoreElectronics();
                CarSettingsManager.this.notifyServiceConnectComplete();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Logs.d("xpcarservice onServiceDisconnected! " + CarSettingsManager.this.mRetry);
                if (CarSettingsManager.this.mRetry >= 5) {
                    return;
                }
                CarSettingsManager.this.connectCar();
                CarSettingsManager.access$1008(CarSettingsManager.this);
            }
        };
        HandlerThread handlerThread = new HandlerThread("xpsettings-car");
        handlerThread.start();
        this.mCarApiClient = Car.createCar(CarSettingsApp.getContext(), this.mCarConnectionCb, new Handler(handlerThread.getLooper()));
        connectCar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreElectronics() {
        if (TextUtils.isEmpty(DataRepository.getInstance().getRestElectronics(CarSettingsApp.getContext()))) {
            Logs.d("carsettings start reset electronics!");
            resetElectronicsData();
            DataRepository.getInstance().setRestElectronics(CarSettingsApp.getContext());
        }
    }

    private void resetElectronicsData() {
        int meterDefaultVolume = CarFunction.getMeterDefaultVolume();
        setAlarmVolume(meterDefaultVolume);
        AudioConfig audioConfig = new AudioConfig(CarSettingsApp.getContext());
        int convertDangerousTtsVolume = Utils.convertDangerousTtsVolume(meterDefaultVolume);
        audioConfig.setDangerousTtsVolLevel(convertDangerousTtsVolume);
        Logs.d("resetElectronicsData meterVolume:" + meterDefaultVolume + " dangerousTtsVol:" + convertDangerousTtsVolume);
        if (CarFunction.isSupportLowSpeedVolumeSlider()) {
            setLowSpeedVolume(Config.LOW_SPEED_VOLUME_DEFAULT);
        }
        if (CarFunction.isSupportSoldierModeLevel()) {
            setSoldierSw(0);
        }
        if (CarFunction.isSupportSoldierModeCamera()) {
            setSoldierCameraSw(false);
        }
        if (CarFunction.isSupportKeyPark()) {
            setKeyParkingEnableOnly(false);
        }
        if (CarFunction.isSupportLeaveLock()) {
            setLeavePollingLockSw(false);
        }
        if (CarFunction.isSupportNearUnlock()) {
            setNearPollingUnLockSw(false);
        }
        if (CarFunction.isSupportMcuAutoPower()) {
            setAutoPowerOffSwitch(true);
        } else {
            setAutoPowerOffConfig(true);
        }
        if (CarFunction.isSupportInductionLock()) {
            setPollingOpenCfg(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarHvacManager() {
        try {
            this.mCarHvacManager = (CarHvacManager) this.mCarApiClient.getCarManager("hvac");
            Logs.d("carservice init hvac manager " + this.mCarHvacManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarAvasManager() {
        try {
            this.mCarAvasManager = (CarAvasManager) this.mCarApiClient.getCarManager("xp_avas");
            Logs.d("carservice init avas manager " + this.mCarAvasManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarAvasEventCallback = new CarAvasManager.CarAvasEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.2
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue == null || carPropertyValue.getPropertyId() != 557855244) {
                    return;
                }
                Logs.d("carservice CarAvasEventCallback " + carPropertyValue);
                Iterator it = CarSettingsManager.this.mAvasChangeListener.iterator();
                while (it.hasNext()) {
                    IAvasChangeListener iAvasChangeListener = (IAvasChangeListener) it.next();
                    if (carPropertyValue.getValue() instanceof Integer) {
                        iAvasChangeListener.onValueChange(carPropertyValue.getPropertyId(), ((Integer) carPropertyValue.getValue()).intValue());
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice CarAvasEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
    }

    public void registerLowSpeedVolume(IAvasChangeListener iAvasChangeListener) {
        if (this.mAvasPropertyIds.isEmpty()) {
            return;
        }
        addAvasChangeListener(iAvasChangeListener);
        try {
            if (this.mCarAvasManager == null || this.mCarAvasEventCallback == null) {
                return;
            }
            this.mCarAvasManager.registerPropCallbackWithFlag(this.mAvasPropertyIds, this.mCarAvasEventCallback, 1);
            Logs.d("carservice signal register mAvasPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarVcuManager() {
        try {
            this.mCarVcuManager = (CarVcuManager) this.mCarApiClient.getCarManager("xp_vcu");
            Logs.d("carservice init CarVcu manager " + this.mCarVcuManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarVcuEventCallback = new CarVcuManager.CarVcuEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.3
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue == null || carPropertyValue.getPropertyId() != 557847045) {
                    return;
                }
                Logs.d("carservice mCarVcuEventCallback " + carPropertyValue);
                Iterator it = CarSettingsManager.this.mVcuListenerList.iterator();
                while (it.hasNext()) {
                    IVcuChangeListener iVcuChangeListener = (IVcuChangeListener) it.next();
                    if (carPropertyValue.getValue() instanceof Integer) {
                        iVcuChangeListener.onValueChange(carPropertyValue.getPropertyId(), ((Integer) carPropertyValue.getValue()).intValue());
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarVcuEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
        if (this.mVcuPropertyIds.isEmpty()) {
            return;
        }
        try {
            if (this.mCarVcuManager == null || this.mCarVcuEventCallback == null) {
                return;
            }
            this.mCarVcuManager.registerPropCallback(this.mVcuPropertyIds, this.mCarVcuEventCallback);
            Logs.d("carservice signal register mVcuPropertyIds");
        } catch (CarNotConnectedException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarTboxManager() {
        try {
            this.mCarTboxManager = (CarTboxManager) this.mCarApiClient.getCarManager("xp_tbox");
            Logs.d("carservice init CarTbox manager " + this.mCarTboxManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarTboxEventCallback = new CarTboxManager.CarTboxEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.4
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Integer[] numArr;
                if (carPropertyValue == null) {
                    return;
                }
                if (carPropertyValue.getPropertyId() == 557846575) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it.hasNext()) {
                            ((ITboxChangeListener) it.next()).onAutoPowerOffConfig(intValue == 1);
                        }
                    }
                }
                if (carPropertyValue.getPropertyId() == 557846576) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if ((carPropertyValue.getValue() instanceof Integer) && ((Integer) carPropertyValue.getValue()).intValue() == 1) {
                        Iterator it2 = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it2.hasNext()) {
                            ((ITboxChangeListener) it2.next()).onCancelAutoPowerOff();
                        }
                    }
                }
                if (carPropertyValue.getPropertyId() == 557912113) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if ((carPropertyValue.getValue() instanceof Integer[]) && (numArr = (Integer[]) carPropertyValue.getValue()) != null && numArr.length > 1) {
                        Iterator it3 = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it3.hasNext()) {
                            ((ITboxChangeListener) it3.next()).onAutoPowerOffCountdown(numArr[0].intValue(), numArr[1].intValue());
                        }
                    }
                }
                if (carPropertyValue.getPropertyId() == 557846578) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue2 = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it4 = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it4.hasNext()) {
                            ((ITboxChangeListener) it4.next()).onSoldierSwState(intValue2);
                        }
                    }
                }
                if (carPropertyValue.getPropertyId() == 557846594) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue3 = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it5 = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it5.hasNext()) {
                            ((ITboxChangeListener) it5.next()).onSoldierCameraSwState(intValue3 == 1);
                        }
                    }
                }
                if (carPropertyValue.getPropertyId() == 557846596) {
                    Logs.d("carservice mCarTboxEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue4 = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it6 = CarSettingsManager.this.mTboxChangeListener.iterator();
                        while (it6.hasNext()) {
                            ((ITboxChangeListener) it6.next()).onSoldierCameraEnable(intValue4 == 1);
                        }
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarTboxEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
    }

    public void registerTobx(ITboxChangeListener iTboxChangeListener) {
        if (this.mTboxPropertyIds.isEmpty()) {
            return;
        }
        addTboxChangeListener(iTboxChangeListener);
        try {
            if (this.mCarTboxManager == null || this.mCarTboxEventCallback == null) {
                return;
            }
            this.mCarTboxManager.registerPropCallbackWithFlag(this.mTboxPropertyIds, this.mCarTboxEventCallback, 1);
            Logs.d("carservice signal register mTboxPropertyIds ");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void registerAutoPower(ITboxChangeListener iTboxChangeListener) {
        if (this.mTboxAutoPowerPropertyIds.isEmpty()) {
            return;
        }
        addTboxChangeListener(iTboxChangeListener);
        try {
            if (this.mCarTboxManager == null || this.mCarTboxEventCallback == null) {
                return;
            }
            this.mCarTboxManager.registerPropCallbackWithFlag(this.mTboxAutoPowerPropertyIds, this.mCarTboxEventCallback, 1);
            Logs.d("carservice signal register mTboxAutoPowerPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unregisterAutoPower(ITboxChangeListener iTboxChangeListener) {
        if (this.mTboxAutoPowerPropertyIds.isEmpty()) {
            return;
        }
        removeTboxChangeListener(iTboxChangeListener);
        try {
            if (this.mCarTboxManager == null || this.mCarTboxEventCallback == null) {
                return;
            }
            this.mCarTboxManager.unregisterPropCallback(this.mTboxAutoPowerPropertyIds, this.mCarTboxEventCallback);
            Logs.d("carservice signal unregister mTboxAutoPowerPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarXpuManager() {
        try {
            this.mCarXpuManager = (CarXpuManager) this.mCarApiClient.getCarManager("xp_xpu");
            Logs.d("carservice init CarXPU manager " + this.mCarXpuManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarXpuEventCallback = new CarXpuManager.CarXpuEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.5
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue == null) {
                    return;
                }
                int propertyId = carPropertyValue.getPropertyId();
                if (propertyId == 557856775) {
                    Logs.d("carservice mCarXpuEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it = CarSettingsManager.this.mXpuChangeListener.iterator();
                        while (it.hasNext()) {
                            ((IXpuChangeListener) it.next()).onXpuStatusChange(intValue);
                        }
                    }
                } else if (propertyId != 557856788) {
                } else {
                    Logs.d("carservice mCarXpuEventCallback " + carPropertyValue);
                    if (carPropertyValue.getValue() instanceof Integer) {
                        int intValue2 = ((Integer) carPropertyValue.getValue()).intValue();
                        Iterator it2 = CarSettingsManager.this.mXpuChangeListener.iterator();
                        while (it2.hasNext()) {
                            ((IXpuChangeListener) it2.next()).onXpuApRemoteChange(intValue2);
                        }
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarXpuEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
        if (this.mXpuPropertyIds.isEmpty()) {
            return;
        }
        try {
            if (this.mCarXpuManager == null || this.mCarXpuEventCallback == null) {
                return;
            }
            this.mCarXpuManager.registerPropCallback(this.mXpuPropertyIds, this.mCarXpuEventCallback);
            Logs.d("carservice signal register mXpuPropertyIds");
        } catch (CarNotConnectedException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarMcuManager() {
        try {
            this.mCarMcuManager = (CarMcuManager) this.mCarApiClient.getCarManager("xp_mcu");
            Logs.d("carservice init CarMCU manager " + this.mCarMcuManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarMcuEventCallback = new CarMcuManager.CarMcuEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.6
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue == null) {
                    return;
                }
                switch (carPropertyValue.getPropertyId()) {
                    case 557847561:
                        Logs.d("carservice mCarMcuEventCallback " + carPropertyValue);
                        if (carPropertyValue.getValue() instanceof Integer) {
                            int intValue = ((Integer) carPropertyValue.getValue()).intValue();
                            if (intValue == 0) {
                                Iterator it = CarSettingsManager.this.mMcuChangeListener.iterator();
                                while (it.hasNext()) {
                                    ((IMcuChangeListener) it.next()).onIgOff();
                                }
                                return;
                            } else if (intValue != 1) {
                                return;
                            } else {
                                Iterator it2 = CarSettingsManager.this.mMcuChangeListener.iterator();
                                while (it2.hasNext()) {
                                    ((IMcuChangeListener) it2.next()).onIgOn();
                                }
                                return;
                            }
                        }
                        return;
                    case 557847634:
                        Logs.d("carservice mCarMcuEventCallback " + carPropertyValue);
                        if (carPropertyValue.getValue() instanceof Integer) {
                            int intValue2 = ((Integer) carPropertyValue.getValue()).intValue();
                            Iterator it3 = CarSettingsManager.this.mMcuChangeListener.iterator();
                            while (it3.hasNext()) {
                                ((IMcuChangeListener) it3.next()).onAutoPowerOffConfig(intValue2 == 1);
                            }
                            return;
                        }
                        return;
                    case 557847635:
                        Logs.d("carservice mCarMcuEventCallback " + carPropertyValue);
                        if (carPropertyValue.getValue() instanceof Integer) {
                            int intValue3 = ((Integer) carPropertyValue.getValue()).intValue();
                            if (intValue3 == 0) {
                                Iterator it4 = CarSettingsManager.this.mMcuChangeListener.iterator();
                                while (it4.hasNext()) {
                                    ((IMcuChangeListener) it4.next()).onAutoPowerOffCountdown();
                                }
                                return;
                            } else if (intValue3 != 1) {
                                return;
                            } else {
                                Iterator it5 = CarSettingsManager.this.mMcuChangeListener.iterator();
                                while (it5.hasNext()) {
                                    ((IMcuChangeListener) it5.next()).onCancelAutoPowerOff();
                                }
                                return;
                            }
                        }
                        return;
                    case 557847658:
                        Logs.d("carservice mCarMcuEventCallback " + carPropertyValue);
                        if (carPropertyValue.getValue() instanceof Integer) {
                            SystemProperties.set(Config.PROPERTIES_FACTORY_MODE, String.valueOf(((Integer) carPropertyValue.getValue()).intValue()));
                            return;
                        }
                        return;
                    case 557847660:
                        Logs.d("carservice mCarMcuEventCallback " + carPropertyValue + " igStatus:" + CarSettingsManager.this.getIgStatusFromMcu());
                        if ((carPropertyValue.getValue() instanceof Integer) && ((Integer) carPropertyValue.getValue()).intValue() == 1) {
                            Iterator it6 = CarSettingsManager.this.mMcuChangeListener.iterator();
                            while (it6.hasNext()) {
                                ((IMcuChangeListener) it6.next()).onOpenWifiHotSpot();
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarMcuEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
        if (!this.mMcuPropertyIds.isEmpty()) {
            try {
                if (this.mCarMcuManager != null && this.mCarMcuEventCallback != null) {
                    this.mCarMcuManager.registerPropCallback(this.mMcuPropertyIds, this.mCarMcuEventCallback);
                    Logs.d("carservice signal register mMcuPropertyIds");
                }
            } catch (CarNotConnectedException e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        registerWifiKey();
    }

    public void registerWifiKey() {
        if (this.mWifiKeyPropertyIds.isEmpty()) {
            return;
        }
        try {
            if (this.mCarMcuManager == null || this.mCarMcuEventCallback == null) {
                return;
            }
            this.mCarMcuManager.registerPropCallbackWithFlag(this.mWifiKeyPropertyIds, this.mCarMcuEventCallback, 1);
            Logs.d("carservice signal register mWifiKeyPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unregisterWifiKey() {
        if (this.mWifiKeyPropertyIds.isEmpty()) {
            return;
        }
        try {
            if (this.mCarMcuManager == null || this.mCarMcuEventCallback == null) {
                return;
            }
            this.mCarMcuManager.unregisterPropCallback(this.mWifiKeyPropertyIds, this.mCarMcuEventCallback);
            Logs.d("carservice signal unregister mWifiKeyPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void registerMcuAutoPower(IMcuChangeListener iMcuChangeListener) {
        if (this.mMcuAutoPowerPropertyIds.isEmpty()) {
            return;
        }
        addMcuChangeListener(iMcuChangeListener);
        try {
            if (this.mCarMcuManager == null || this.mCarMcuEventCallback == null) {
                return;
            }
            this.mCarMcuManager.registerPropCallbackWithFlag(this.mMcuAutoPowerPropertyIds, this.mCarMcuEventCallback, 1);
            Logs.d("carservice signal register mMcuAutoPowerPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unregisterMcuAutoPower(IMcuChangeListener iMcuChangeListener) {
        if (this.mMcuAutoPowerPropertyIds.isEmpty()) {
            return;
        }
        removeMcuChangeListener(iMcuChangeListener);
        try {
            if (this.mCarMcuManager == null || this.mCarMcuEventCallback == null) {
                return;
            }
            this.mCarMcuManager.unregisterPropCallback(this.mMcuAutoPowerPropertyIds, this.mCarMcuEventCallback);
            Logs.d("carservice signal unregister mMcuAutoPowerPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarScuManager() {
        try {
            this.mCarScuManager = (CarScuManager) this.mCarApiClient.getCarManager("xp_scu");
            Logs.d("carservice init CarScu manager " + this.mCarScuManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarScuEventCallback = new CarScuManager.CarScuEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.7
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    int propertyId = carPropertyValue.getPropertyId();
                    if (propertyId == 557852183 || propertyId == 557852186 || propertyId == 557852225) {
                        Logs.d("carservice mCarScuEventCallback id:" + carPropertyValue);
                        Iterator it = CarSettingsManager.this.mScuListenerList.iterator();
                        while (it.hasNext()) {
                            IScuChangeListener iScuChangeListener = (IScuChangeListener) it.next();
                            if (carPropertyValue.getValue() instanceof Integer) {
                                iScuChangeListener.onValueChange(carPropertyValue.getPropertyId(), ((Integer) carPropertyValue.getValue()).intValue());
                            }
                        }
                        if (557852186 == carPropertyValue.getPropertyId()) {
                            CarSettingsManager.sSignalLostErrorCacheMap.put(Integer.valueOf((int) CarSettingsManager.ID_SCU_REMOTE_PK_BTN), false);
                        } else if (557852183 == carPropertyValue.getPropertyId()) {
                            CarSettingsManager.sSignalLostErrorCacheMap.put(Integer.valueOf((int) CarSettingsManager.ID_SCU_PHONE_PK_BTN), false);
                        }
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarScuEventCallback onErrorEvent:" + i + " " + i2);
                Integer valueOf = Integer.valueOf((int) CarSettingsManager.ID_SCU_REMOTE_PK_BTN);
                if (i == 557852186) {
                    if (i2 == 6) {
                        CarSettingsManager.sSignalLostErrorCacheMap.put(valueOf, true);
                        Iterator it = CarSettingsManager.this.mScuListenerList.iterator();
                        while (it.hasNext()) {
                            ((IScuChangeListener) it.next()).onSignalLost(CarSettingsManager.ID_SCU_REMOTE_PK_BTN);
                        }
                        return;
                    }
                    CarSettingsManager.sSignalLostErrorCacheMap.put(valueOf, false);
                } else if (i == 557852183) {
                    if (i2 == 6) {
                        CarSettingsManager.sSignalLostErrorCacheMap.put(Integer.valueOf((int) CarSettingsManager.ID_SCU_PHONE_PK_BTN), true);
                        Iterator it2 = CarSettingsManager.this.mScuListenerList.iterator();
                        while (it2.hasNext()) {
                            ((IScuChangeListener) it2.next()).onSignalLost(CarSettingsManager.ID_SCU_PHONE_PK_BTN);
                        }
                        return;
                    }
                    CarSettingsManager.sSignalLostErrorCacheMap.put(Integer.valueOf((int) CarSettingsManager.ID_SCU_PHONE_PK_BTN), false);
                }
            }
        };
    }

    public void registerRemotePark(IScuChangeListener iScuChangeListener) {
        if (this.mScuPropertyIds.isEmpty()) {
            return;
        }
        addScuChangeListener(iScuChangeListener);
        try {
            if (this.mCarScuManager == null || this.mCarScuEventCallback == null) {
                return;
            }
            this.mCarScuManager.registerPropCallbackWithFlag(this.mScuPropertyIds, this.mCarScuEventCallback, 1);
            Logs.d("carservice signal register mScuPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void registerXpuApRemote(IXpuChangeListener iXpuChangeListener) {
        if (this.mXpuApRemotePropertyIds.isEmpty()) {
            return;
        }
        addXpuChangeListener(iXpuChangeListener);
        try {
            if (this.mCarXpuManager == null || this.mCarXpuEventCallback == null) {
                return;
            }
            this.mCarXpuManager.registerPropCallbackWithFlag(this.mXpuApRemotePropertyIds, this.mCarXpuEventCallback, 1);
            Logs.d("carservice signal register mXpuApRemotePropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarBcmManager() {
        try {
            this.mCarBcmManager = (CarBcmManager) this.mCarApiClient.getCarManager("xp_bcm");
            Logs.d("carservice init CarBcmManager " + this.mCarBcmManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarBcmEventCallback = new CarBcmManager.CarBcmEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.8
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    switch (carPropertyValue.getPropertyId()) {
                        case 557849646:
                        case 557849716:
                        case 557849717:
                            Logs.d("carservice CarBcmEventCallback " + carPropertyValue);
                            Iterator it = CarSettingsManager.this.mBcmListenerList.iterator();
                            while (it.hasNext()) {
                                IBcmChangeListener iBcmChangeListener = (IBcmChangeListener) it.next();
                                if (carPropertyValue.getValue() instanceof Integer) {
                                    iBcmChangeListener.onValueChange(carPropertyValue.getPropertyId(), ((Integer) carPropertyValue.getValue()).intValue());
                                }
                            }
                            return;
                        default:
                            return;
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarBcmEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
    }

    public void registerPolling(IBcmChangeListener iBcmChangeListener) {
        if (this.mBcmPropertyIds.isEmpty()) {
            return;
        }
        addBcmChangeListener(iBcmChangeListener);
        try {
            if (this.mCarBcmManager == null || this.mCarBcmEventCallback == null) {
                return;
            }
            this.mCarBcmManager.registerPropCallbackWithFlag(this.mBcmPropertyIds, this.mCarBcmEventCallback, 1);
            Logs.d("carservice signal register mBcmPropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    protected final <E> E getValue(CarPropertyValue<?> carPropertyValue) {
        return (E) carPropertyValue.getValue();
    }

    private final int[] getIntArrayProperty(CarPropertyValue<?> carPropertyValue) {
        Object[] objArr = (Object[]) getValue(carPropertyValue);
        if (objArr != null) {
            int[] iArr = new int[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj instanceof Integer) {
                    iArr[i] = ((Integer) obj).intValue();
                }
            }
            return iArr;
        }
        return null;
    }

    public void initCarManager() {
        try {
            this.mCarIcmManager = (CarIcmManager) this.mCarApiClient.getCarManager("xp_icm");
            Logs.d("carservice init icm manager " + this.mCarIcmManager);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            LogUtils.e(TAG, "Car not connected");
        }
        this.mCarIcmEventCallback = new CarIcmManager.CarIcmEventCallback() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.9
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    switch (carPropertyValue.getPropertyId()) {
                        case 554702353:
                            Logs.d("carservice mCarIcmEventCallback " + carPropertyValue);
                            if (carPropertyValue.getValue() instanceof String) {
                                String str = (String) carPropertyValue.getValue();
                                if (TextUtils.isEmpty(str) || !str.contains("SysReady")) {
                                    return;
                                }
                                CarSettingsManager.this.syncToMeter();
                                return;
                            }
                            return;
                        case 557848069:
                        case 557848125:
                            Logs.d("carservice mCarIcmEventCallback " + carPropertyValue);
                            Iterator it = CarSettingsManager.this.mMeterCallbackList.iterator();
                            while (it.hasNext()) {
                                MeterCallback meterCallback = (MeterCallback) it.next();
                                if (carPropertyValue.getValue() instanceof Integer) {
                                    meterCallback.meterAlarmSoundChange(((Integer) carPropertyValue.getValue()).intValue());
                                }
                            }
                            return;
                        case 557848078:
                            Logs.d("carservice mCarIcmEventCallback " + carPropertyValue);
                            if ((carPropertyValue.getValue() instanceof Integer) && ((Integer) carPropertyValue.getValue()).intValue() == 1) {
                                CarSettingsManager.this.syncToMeter();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }

            public void onErrorEvent(int i, int i2) {
                Logs.d("carservice mCarIcmEventCallback onErrorEvent:" + i + " " + i2);
            }
        };
        if (this.mIcmPropertyIds.isEmpty()) {
            return;
        }
        try {
            if (this.mCarIcmManager == null || this.mCarIcmEventCallback == null) {
                return;
            }
            this.mCarIcmManager.registerPropCallbackWithFlag(this.mIcmPropertyIds, this.mCarIcmEventCallback, 1);
            Logs.d("carservice signal register mIcmPropertyIds");
        } catch (CarNotConnectedException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void registerIcmVolume(MeterCallback meterCallback) {
        if (this.mIcmVolumePropertyIds.isEmpty()) {
            return;
        }
        addMeterCallback(meterCallback);
        try {
            if (this.mCarIcmManager == null || this.mCarIcmEventCallback == null) {
                return;
            }
            this.mCarIcmManager.registerPropCallbackWithFlag(this.mIcmVolumePropertyIds, this.mCarIcmEventCallback, 1);
            Logs.d("carservice signal register mIcmVolumePropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unregisterIcmVolume(MeterCallback meterCallback) {
        if (this.mIcmVolumePropertyIds.isEmpty()) {
            return;
        }
        removeMeterCallback(meterCallback);
        try {
            if (this.mCarIcmManager == null || this.mCarIcmEventCallback == null) {
                return;
            }
            this.mCarIcmManager.unregisterPropCallback(this.mIcmVolumePropertyIds, this.mCarIcmEventCallback);
            Logs.d("carservice signal unregister mIcmVolumePropertyIds");
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncToMeter() {
        if (TextUtils.isEmpty(DataRepository.getInstance().getRestElectronics(CarSettingsApp.getContext()))) {
            return;
        }
        int preferenceForKeyInt = GlobalSettingsSharedPreference.getPreferenceForKeyInt(CarSettingsApp.getContext(), Config.KEY_MEMORY_METER_VOLUME, XpAccountManager.getInstance().getMeterVolume());
        Logs.d("carservice syncToMeter " + preferenceForKeyInt);
        setAlarmVolume(preferenceForKeyInt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectCar() {
        try {
            if (this.mCarApiClient != null) {
                LogUtils.d("xpcarservice carservice connect ");
                this.mCarApiClient.connect();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void releaseICM() {
        if (this.mIcmPropertyIds.isEmpty()) {
            return;
        }
        CarIcmManager carIcmManager = this.mCarIcmManager;
        if (carIcmManager != null) {
            try {
                if (this.mCarIcmEventCallback != null) {
                    carIcmManager.unregisterPropCallback(this.mIcmPropertyIds, this.mCarIcmEventCallback);
                    Logs.d("carservice signal unregister mIcmPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        CopyOnWriteArrayList<MeterCallback> copyOnWriteArrayList = this.mMeterCallbackList;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.clear();
        }
    }

    public void unregisterRemotePark(IScuChangeListener iScuChangeListener) {
        if (this.mScuPropertyIds.isEmpty()) {
            return;
        }
        removeScuChangeListener(iScuChangeListener);
        CarScuManager carScuManager = this.mCarScuManager;
        if (carScuManager != null) {
            try {
                if (this.mCarScuEventCallback != null) {
                    carScuManager.unregisterPropCallback(this.mScuPropertyIds, this.mCarScuEventCallback);
                    Logs.d("carservice signal unregister mScuPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void unregisterXpuApRemote(IXpuChangeListener iXpuChangeListener) {
        if (this.mXpuApRemotePropertyIds.isEmpty()) {
            return;
        }
        removeXpuChangeListener(iXpuChangeListener);
        CarXpuManager carXpuManager = this.mCarXpuManager;
        if (carXpuManager != null) {
            try {
                if (this.mXpuChangeListener != null) {
                    carXpuManager.unregisterPropCallback(this.mXpuApRemotePropertyIds, this.mCarXpuEventCallback);
                    Logs.d("carservice signal unregister mXpuApRemotePropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void releaseVcu() {
        CarVcuManager carVcuManager;
        if (this.mVcuPropertyIds.isEmpty() || (carVcuManager = this.mCarVcuManager) == null) {
            return;
        }
        try {
            if (this.mCarVcuEventCallback != null) {
                carVcuManager.unregisterPropCallback(this.mVcuPropertyIds, this.mCarVcuEventCallback);
                Logs.d("carservice signal unregister mVcuPropertyIds");
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unregisterLowSpeedVolume(IAvasChangeListener iAvasChangeListener) {
        if (this.mAvasPropertyIds.isEmpty()) {
            return;
        }
        removeAvasChangeListener(iAvasChangeListener);
        CarAvasManager carAvasManager = this.mCarAvasManager;
        if (carAvasManager != null) {
            try {
                if (this.mCarAvasEventCallback != null) {
                    carAvasManager.unregisterPropCallback(this.mAvasPropertyIds, this.mCarAvasEventCallback);
                    Logs.d("carservice signal unregister mAvasPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void releaseXpu() {
        CarXpuManager carXpuManager;
        if (this.mXpuPropertyIds.isEmpty() || (carXpuManager = this.mCarXpuManager) == null) {
            return;
        }
        try {
            if (this.mCarXpuEventCallback != null) {
                carXpuManager.unregisterPropCallback(this.mXpuPropertyIds, this.mCarXpuEventCallback);
                Logs.d("carservice signal unregister mXpuPropertyIds");
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void releaseMcu() {
        if (this.mMcuPropertyIds.isEmpty()) {
            return;
        }
        CarMcuManager carMcuManager = this.mCarMcuManager;
        if (carMcuManager != null) {
            try {
                if (this.mCarMcuEventCallback != null) {
                    carMcuManager.unregisterPropCallback(this.mMcuPropertyIds, this.mCarMcuEventCallback);
                    Logs.d("carservice signal unregister mMcuPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        unregisterWifiKey();
    }

    public void unregisterTbox(ITboxChangeListener iTboxChangeListener) {
        if (this.mTboxPropertyIds.isEmpty()) {
            return;
        }
        removeTboxChangeListener(iTboxChangeListener);
        CarTboxManager carTboxManager = this.mCarTboxManager;
        if (carTboxManager != null) {
            try {
                if (this.mCarTboxEventCallback != null) {
                    carTboxManager.unregisterPropCallback(this.mTboxPropertyIds, this.mCarTboxEventCallback);
                    Logs.d("carservice signal unregister mTboxPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void unregisterPolling(IBcmChangeListener iBcmChangeListener) {
        if (this.mBcmPropertyIds.isEmpty()) {
            return;
        }
        removeBcmChangeListener(iBcmChangeListener);
        CarBcmManager carBcmManager = this.mCarBcmManager;
        if (carBcmManager != null) {
            try {
                if (this.mCarBcmEventCallback != null) {
                    carBcmManager.unregisterPropCallback(this.mBcmPropertyIds, this.mCarBcmEventCallback);
                    Logs.d("carservice signal unregister mBcmPropertyIds");
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void addScuChangeListener(IScuChangeListener iScuChangeListener) {
        if (this.mScuListenerList.contains(iScuChangeListener)) {
            return;
        }
        this.mScuListenerList.add(iScuChangeListener);
    }

    private void addBcmChangeListener(IBcmChangeListener iBcmChangeListener) {
        if (this.mBcmListenerList.contains(iBcmChangeListener)) {
            return;
        }
        this.mBcmListenerList.add(iBcmChangeListener);
    }

    public void addVcuChangeListener(IVcuChangeListener iVcuChangeListener) {
        if (this.mVcuListenerList.contains(iVcuChangeListener)) {
            return;
        }
        this.mVcuListenerList.add(iVcuChangeListener);
    }

    private void addTboxChangeListener(ITboxChangeListener iTboxChangeListener) {
        if (this.mTboxChangeListener.contains(iTboxChangeListener)) {
            return;
        }
        this.mTboxChangeListener.add(iTboxChangeListener);
    }

    private void addAvasChangeListener(IAvasChangeListener iAvasChangeListener) {
        if (this.mAvasChangeListener.contains(iAvasChangeListener)) {
            return;
        }
        this.mAvasChangeListener.add(iAvasChangeListener);
    }

    public void addXpuChangeListener(IXpuChangeListener iXpuChangeListener) {
        if (this.mXpuChangeListener.contains(iXpuChangeListener)) {
            return;
        }
        this.mXpuChangeListener.add(iXpuChangeListener);
    }

    public void removeXpuChangeListener(IXpuChangeListener iXpuChangeListener) {
        this.mXpuChangeListener.remove(iXpuChangeListener);
    }

    public void addMcuChangeListener(IMcuChangeListener iMcuChangeListener) {
        if (this.mMcuChangeListener.contains(iMcuChangeListener)) {
            return;
        }
        this.mMcuChangeListener.add(iMcuChangeListener);
    }

    public void removeMcuChangeListener(IMcuChangeListener iMcuChangeListener) {
        this.mMcuChangeListener.remove(iMcuChangeListener);
    }

    private void removeScuChangeListener(IScuChangeListener iScuChangeListener) {
        this.mScuListenerList.remove(iScuChangeListener);
    }

    private void removeBcmChangeListener(IBcmChangeListener iBcmChangeListener) {
        this.mBcmListenerList.remove(iBcmChangeListener);
    }

    public void removeVcuChangeListener(IVcuChangeListener iVcuChangeListener) {
        this.mVcuListenerList.remove(iVcuChangeListener);
    }

    private void removeTboxChangeListener(ITboxChangeListener iTboxChangeListener) {
        this.mTboxChangeListener.remove(iTboxChangeListener);
    }

    private void removeAvasChangeListener(IAvasChangeListener iAvasChangeListener) {
        this.mAvasChangeListener.remove(iAvasChangeListener);
    }

    private void addMeterCallback(MeterCallback meterCallback) {
        if (this.mMeterCallbackList.contains(meterCallback)) {
            return;
        }
        this.mMeterCallbackList.add(meterCallback);
    }

    private void removeMeterCallback(MeterCallback meterCallback) {
        this.mMeterCallbackList.remove(meterCallback);
    }

    public void addPowerChangeListener(IPowerChangeListener iPowerChangeListener) {
        if (this.mPowerChangeListeners.contains(iPowerChangeListener)) {
            return;
        }
        this.mPowerChangeListeners.add(iPowerChangeListener);
    }

    public void removePowerChangeListener(IPowerChangeListener iPowerChangeListener) {
        this.mPowerChangeListeners.remove(iPowerChangeListener);
    }

    protected void handleException(Exception exc) {
        if (exc instanceof IllegalArgumentException) {
            LogUtils.e(TAG, "IllegalArgumentException:" + exc);
        } else if (exc instanceof CarNotConnectedException) {
            LogUtils.e(TAG, "CarNotConnectedException:" + exc);
        } else {
            LogUtils.e(TAG, exc.toString());
        }
    }

    public void setMeterBrightness(int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 100) {
            i = 100;
        }
        if (this.mCarIcmManager == null) {
            return;
        }
        try {
            Logs.d("xpcarservice setMeterBackLightLevel:" + i);
            this.mCarIcmManager.setIcmBrightness(i);
        } catch (Exception e) {
            handleException(e);
        }
    }

    public boolean isKeyParkingEnable() {
        CarScuManager carScuManager = this.mCarScuManager;
        if (carScuManager == null) {
            return false;
        }
        try {
            int keyRemoteSmButton = carScuManager.getKeyRemoteSmButton();
            Logs.d("carservice autopark isKeyParkingEnable over:" + keyRemoteSmButton);
            return keyRemoteSmButton == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setKeyParkingEnable(boolean z) {
        if (z && !getAutoParkSwEnable()) {
            setAutoParkSw(true);
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.10
                @Override // java.lang.Runnable
                public void run() {
                    CarSettingsManager.this.setKeyParkingEnableOnly(true);
                }
            }, 300L);
            return;
        }
        setKeyParkingEnableOnly(z);
    }

    public void setKeyParkingEnableOnly(boolean z) {
        if (z && !getAutoParkSwEnable()) {
            Logs.d("carservice autopark off!");
        } else if (this.mCarScuManager == null) {
        } else {
            int i = z ? 1 : 0;
            try {
                Logs.d("carservice autopark key onOFF:" + i);
                this.mCarScuManager.setKeyRemoteSmButton(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getAutoParkSwEnable() {
        /*
            r4 = this;
            android.car.hardware.scu.CarScuManager r0 = r4.mCarScuManager
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            int r0 = r0.getAutoParkSwitch()     // Catch: java.lang.Exception -> L21
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L1f
            r2.<init>()     // Catch: java.lang.Exception -> L1f
            java.lang.String r3 = "carservice autopark getAutoParkSwitch over:"
            r2.append(r3)     // Catch: java.lang.Exception -> L1f
            r2.append(r0)     // Catch: java.lang.Exception -> L1f
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> L1f
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Exception -> L1f
            goto L26
        L1f:
            r2 = move-exception
            goto L23
        L21:
            r2 = move-exception
            r0 = r1
        L23:
            r2.printStackTrace()
        L26:
            r2 = 1
            if (r0 != r2) goto L2a
            r1 = r2
        L2a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.getAutoParkSwEnable():boolean");
    }

    public void setAutoParkSw(boolean z) {
        if (this.mCarScuManager == null) {
            return;
        }
        try {
            Logs.d("carservice autopark setAutoParkSwitch " + z);
            this.mCarScuManager.setAutoParkSwitch(z ? 1 : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reStoreD21KeyPark(Context context) {
        boolean d21RemoteParking = GlobalSettingsSharedPreference.getD21RemoteParking(context);
        Logs.d("xpSetDeviceWork ig on d21RemoteParking:" + d21RemoteParking + " mCarScuManager:" + this.mCarScuManager);
        setKeyPark(d21RemoteParking);
    }

    public void setKeyPark(boolean z) {
        if (z && !getAutoParkSwEnable()) {
            Logs.d("carservice setKeyPark on, but auto park off!");
        } else if (this.mCarScuManager == null) {
        } else {
            int i = z ? 1 : 0;
            try {
                Logs.d("carservice set key onOFF d21:" + i);
                this.mCarScuManager.setKeyPark(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getKeyPark() {
        CarScuManager carScuManager = this.mCarScuManager;
        if (carScuManager == null) {
            return false;
        }
        try {
            int keyPark = carScuManager.getKeyPark();
            Logs.d("carservice get key onOFF d21:" + keyPark);
            return keyPark == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getChargingGunStatus() {
        CarVcuManager carVcuManager = this.mCarVcuManager;
        if (carVcuManager != null) {
            try {
                return carVcuManager.getChargingGunStatus();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public boolean isCarGearP() {
        CarVcuManager carVcuManager = this.mCarVcuManager;
        if (carVcuManager != null) {
            try {
                int displayGearLevel = carVcuManager.getDisplayGearLevel();
                Logs.d("isCarGearP.. gearLever: " + displayGearLevel);
                return displayGearLevel == 4;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isEbsBatterySatisfied() {
        CarVcuManager carVcuManager = this.mCarVcuManager;
        if (carVcuManager != null) {
            try {
                int ebsBatterySoc = carVcuManager.getEbsBatterySoc();
                Logs.d("isEbsBatterySatisfied.. percent: " + ebsBatterySoc);
                return ebsBatterySoc > 70;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setAlarmVolume(int i) {
        if (this.mCarIcmManager == null) {
            return;
        }
        try {
            Logs.d("carservice setAlarmVolume " + i);
            this.mCarIcmManager.setIcmAlarmVolume(i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public int getAlarmVolume() {
        CarIcmManager carIcmManager = this.mCarIcmManager;
        if (carIcmManager == null) {
            return CarFunction.getMeterDefaultVolume();
        }
        try {
            int icmAlarmVolume = carIcmManager.getIcmAlarmVolume();
            Logs.d("carservice getAlarmVolume " + icmAlarmVolume);
            return icmAlarmVolume;
        } catch (Throwable th) {
            th.printStackTrace();
            return CarFunction.getMeterDefaultVolume();
        }
    }

    public void setAutoPowerOffConfig(boolean z) {
        if (this.mCarTboxManager != null) {
            int i = z ? 1 : 0;
            try {
                this.mCarTboxManager.setAutoPowerOffConfig(i);
                Logs.d("carservice setAutoPowerOffConfig over enable : " + z + " , status : " + i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public boolean getAutoPowerOffConfig() {
        CarTboxManager carTboxManager = this.mCarTboxManager;
        if (carTboxManager != null) {
            try {
                int autoPowerOffSt = carTboxManager.getAutoPowerOffSt();
                Logs.d("carservice getAutoPowerOffConfig over status : " + autoPowerOffSt);
                return autoPowerOffSt == 1;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return true;
    }

    public void cancelPowerOffConfig() {
        CarTboxManager carTboxManager = this.mCarTboxManager;
        if (carTboxManager != null) {
            try {
                carTboxManager.setCancelPowerOffConfig(1);
                Logs.d("carservice setCancelPowerOffConfig over ");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public int[] getPowerOffCountdown() {
        CarTboxManager carTboxManager = this.mCarTboxManager;
        if (carTboxManager != null) {
            try {
                int[] powerOffCountdown = carTboxManager.getPowerOffCountdown();
                if (powerOffCountdown != null) {
                    Logs.d("carservice getPowerOffCountdown over time :" + Arrays.toString(powerOffCountdown));
                }
                return powerOffCountdown;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void setSoldierSw(int i) {
        if (this.mCarTboxManager != null) {
            try {
                Logs.d("carservice setSoldierSw status " + i);
                this.mCarTboxManager.setSoldierSw(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public int getSoldierSwState() {
        CarTboxManager carTboxManager = this.mCarTboxManager;
        int i = 0;
        if (carTboxManager != null) {
            try {
                i = carTboxManager.getSoldierSwState();
                Logs.d("carservice getSoldierSwState over status " + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return 0;
    }

    public void setSoldierCameraSw(boolean z) {
        if (this.mCarTboxManager != null) {
            try {
                Logs.d("carservice setSoldierCameraSw status:" + (z ? 1 : 0));
                this.mCarTboxManager.setTboxSoliderCameraSwitch(z ? 1 : 0);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getSoldierCameraSwState() {
        /*
            r4 = this;
            android.car.hardware.tbox.CarTboxManager r0 = r4.mCarTboxManager
            r1 = 0
            if (r0 == 0) goto L26
            int r0 = r0.getTboxSoliderCameraState()     // Catch: java.lang.Throwable -> L20
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r2.<init>()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r3 = "carservice getTboxSoliderCameraState over status "
            r2.append(r3)     // Catch: java.lang.Throwable -> L1e
            r2.append(r0)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L1e
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Throwable -> L1e
            goto L27
        L1e:
            r2 = move-exception
            goto L22
        L20:
            r2 = move-exception
            r0 = r1
        L22:
            r2.printStackTrace()
            goto L27
        L26:
            r0 = r1
        L27:
            r2 = 1
            if (r0 != r2) goto L2b
            r1 = r2
        L2b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.getSoldierCameraSwState():boolean");
    }

    public void setTboxThresholdSwitch(int i, int i2, int i3) {
        if (this.mCarTboxManager != null) {
            try {
                Logs.d("carservice setTboxThresholdSwitch highLevel " + i + ", middleLevel:" + i2 + ", lowLevel:" + i3);
                this.mCarTboxManager.setTboxThresholdSwitch(i, i2, i3);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean isSoldierCameraEnable() {
        /*
            r4 = this;
            android.car.hardware.tbox.CarTboxManager r0 = r4.mCarTboxManager
            r1 = 1
            if (r0 == 0) goto L26
            int r0 = r0.getSoliderEnableState()     // Catch: java.lang.Throwable -> L20
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r2.<init>()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r3 = "carservice isSoldierCameraEnable over status "
            r2.append(r3)     // Catch: java.lang.Throwable -> L1e
            r2.append(r0)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L1e
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Throwable -> L1e
            goto L27
        L1e:
            r2 = move-exception
            goto L22
        L20:
            r2 = move-exception
            r0 = r1
        L22:
            r2.printStackTrace()
            goto L27
        L26:
            r0 = r1
        L27:
            if (r0 != r1) goto L2a
            goto L2b
        L2a:
            r1 = 0
        L2b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.isSoldierCameraEnable():boolean");
    }

    public int getMinWindSpeed() {
        CarHvacManager carHvacManager = this.mCarHvacManager;
        int i = 0;
        if (carHvacManager != null) {
            try {
                i = carHvacManager.getMinWindSpeedLevel();
                Logs.d("carservice getMinWindSpeed over minWind:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return 0;
    }

    public int getMaxWindSpeed() {
        CarHvacManager carHvacManager = this.mCarHvacManager;
        int i = 0;
        if (carHvacManager != null) {
            try {
                i = carHvacManager.getMaxWindSpeedLevel();
                Logs.d("carservice getMaxWindSpeed over maxWind:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return 0;
    }

    public int getMinTemperature() {
        CarHvacManager carHvacManager = this.mCarHvacManager;
        int i = 0;
        if (carHvacManager != null) {
            try {
                i = carHvacManager.getMinHavcTemperature();
                Logs.d("carservice getMaxWindSpeed over minTemperature:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return 0;
    }

    public int getMaxTemperature() {
        CarHvacManager carHvacManager = this.mCarHvacManager;
        int i = 0;
        if (carHvacManager != null) {
            try {
                i = carHvacManager.getMaxHavcTemperature();
                Logs.d("carservice getMaxWindSpeed over maxTemperature:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return 0;
    }

    public int getLowSpeedVolume() {
        int i = Config.LOW_SPEED_VOLUME_DEFAULT;
        CarAvasManager carAvasManager = this.mCarAvasManager;
        if (carAvasManager != null) {
            try {
                i = carAvasManager.getLowSpeedVolume();
                Logs.d("carservice getLowSpeedVolume over volume:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return i;
    }

    public void setLowSpeedVolume(int i) {
        if (this.mCarAvasManager != null) {
            try {
                Logs.d("carservice setLowSpeedVolume " + i);
                GlobalSettingsSharedPreference.setPreferenceForKeyInt(CarSettingsApp.getContext(), Config.KEY_LOW_SPEED_VOLUME, i);
                if (i < 1) {
                    i = 1;
                }
                if (i > 100) {
                    i = 100;
                }
                this.mCarAvasManager.setLowSpeedVolume(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void setAutoPowerOffSwitch(boolean z) {
        if (this.mCarMcuManager != null) {
            try {
                this.mCarMcuManager.setAutoPowerOffSwitch(z ? 1 : 0);
                Logs.d("carservice setAutoPowerOffSwitch over d21:" + z);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public boolean isAutoPowerOffEnable() {
        CarMcuManager carMcuManager = this.mCarMcuManager;
        if (carMcuManager != null) {
            try {
                int autoPowerOffSwitchState = carMcuManager.getAutoPowerOffSwitchState();
                Logs.d("carservice isAutoPowerOffEnable over d21:" + autoPowerOffSwitchState);
                return autoPowerOffSwitchState == 1;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return true;
    }

    public void setPowerOffCountdownAction(boolean z) {
        if (this.mCarMcuManager != null) {
            int i = z ? 1 : 0;
            try {
                this.mCarMcuManager.setPowerOffCountdownAction(i);
                Logs.d("carservice setPowerOffCountdownAction over d21:" + i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public boolean isPowerOffCountdownNoticeStart() {
        CarMcuManager carMcuManager = this.mCarMcuManager;
        if (carMcuManager != null) {
            try {
                int powerOffCountdownNotice = carMcuManager.getPowerOffCountdownNotice();
                Logs.d("carservice isPowerOffCountdownNoticeStart over d21:" + powerOffCountdownNotice);
                return powerOffCountdownNotice == 0;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public void setLeavePollingLockSw(boolean z) {
        if (this.mCarBcmManager == null) {
            return;
        }
        int i = z ? 1 : 0;
        try {
            this.mCarBcmManager.setLeavePollingLockSw(i);
            Logs.d("xpcarservice setLeavePollingLockSw over cfg:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNearPollingUnLockSw(boolean z) {
        if (this.mCarBcmManager == null) {
            return;
        }
        int i = z ? 1 : 0;
        try {
            this.mCarBcmManager.setNearPollingUnLockSw(i);
            Logs.d("xpcarservice setNearPollingUnLockSw over cfg:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getLeavePollingLockSw() {
        /*
            r4 = this;
            android.car.hardware.bcm.CarBcmManager r0 = r4.mCarBcmManager
            r1 = 0
            if (r0 == 0) goto L26
            int r0 = r0.getLeavePollingLockSw()     // Catch: java.lang.Throwable -> L20
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r2.<init>()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r3 = "carservice getLeavePollingLockSw over:"
            r2.append(r3)     // Catch: java.lang.Throwable -> L1e
            r2.append(r0)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L1e
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Throwable -> L1e
            goto L27
        L1e:
            r2 = move-exception
            goto L22
        L20:
            r2 = move-exception
            r0 = r1
        L22:
            r2.printStackTrace()
            goto L27
        L26:
            r0 = r1
        L27:
            r2 = 1
            if (r0 != r2) goto L2b
            r1 = r2
        L2b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.getLeavePollingLockSw():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getNearePollingUnLockSw() {
        /*
            r4 = this;
            android.car.hardware.bcm.CarBcmManager r0 = r4.mCarBcmManager
            r1 = 0
            if (r0 == 0) goto L26
            int r0 = r0.getNearePollingUnLockSw()     // Catch: java.lang.Throwable -> L20
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r2.<init>()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r3 = "carservice getNearePollingUnLockSw over:"
            r2.append(r3)     // Catch: java.lang.Throwable -> L1e
            r2.append(r0)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L1e
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Throwable -> L1e
            goto L27
        L1e:
            r2 = move-exception
            goto L22
        L20:
            r2 = move-exception
            r0 = r1
        L22:
            r2.printStackTrace()
            goto L27
        L26:
            r0 = r1
        L27:
            r2 = 1
            if (r0 != r2) goto L2b
            r1 = r2
        L2b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.getNearePollingUnLockSw():boolean");
    }

    public boolean hasCiuDevice() {
        CarMcuManager carMcuManager = this.mCarMcuManager;
        int i = 2;
        if (carMcuManager != null) {
            try {
                i = carMcuManager.getCiuState();
                Logs.d("carservice getCiuState:" + i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return i == 1;
    }

    public int getNedcSwitchStatus() {
        CarXpuManager carXpuManager = this.mCarXpuManager;
        int i = -1;
        if (carXpuManager != null) {
            try {
                i = carXpuManager.getNedcSwitchStatus();
                Logs.d("carservice getNedcSwitchStatus over status:" + i);
                return i;
            } catch (Throwable th) {
                th.printStackTrace();
                return i;
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getPollingOpenCfg() {
        /*
            r4 = this;
            android.car.hardware.bcm.CarBcmManager r0 = r4.mCarBcmManager
            r1 = 0
            if (r0 == 0) goto L26
            int r0 = r0.getPollingOpenCfg()     // Catch: java.lang.Throwable -> L20
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r2.<init>()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r3 = "carservice getPollingOpenCfg over status:"
            r2.append(r3)     // Catch: java.lang.Throwable -> L1e
            r2.append(r0)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L1e
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r2)     // Catch: java.lang.Throwable -> L1e
            goto L27
        L1e:
            r2 = move-exception
            goto L22
        L20:
            r2 = move-exception
            r0 = r1
        L22:
            r2.printStackTrace()
            goto L27
        L26:
            r0 = r1
        L27:
            r2 = 1
            if (r0 != r2) goto L2b
            r1 = r2
        L2b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager.getPollingOpenCfg():boolean");
    }

    public void setPollingOpenCfg(boolean z) {
        if (this.mCarBcmManager == null) {
            return;
        }
        int i = z ? 1 : 0;
        try {
            this.mCarBcmManager.setPollingOpenCfg(i);
            Logs.d("xpcarservice setPollingOpenCfg over cfg:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIgStatusFromMcu() {
        CarMcuManager carMcuManager = this.mCarMcuManager;
        if (carMcuManager != null) {
            try {
                int igStatusFromMcu = carMcuManager.getIgStatusFromMcu();
                Logs.d("carservice getIgStatusFromMcu:" + igStatusFromMcu);
                return igStatusFromMcu;
            } catch (Throwable th) {
                th.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public boolean isParkByMemoryEnable() {
        CarScuManager carScuManager = this.mCarScuManager;
        if (carScuManager != null) {
            try {
                int parkByMemorySwSt = carScuManager.getParkByMemorySwSt();
                Logs.d("carservice getParkByMemorySwSt:" + parkByMemorySwSt);
                return parkByMemorySwSt == 1;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public void setRemoteParkingAdvancedEnable(boolean z) {
        if (z && !isParkByMemoryEnable()) {
            Logs.d("carservice park by memory off!");
        } else if (this.mCarScuManager == null) {
        } else {
            int i = z ? 1 : 0;
            try {
                Logs.d("carservice set setPhoneSmButton:" + i);
                this.mCarScuManager.setPhoneSmButton(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRemoteParkingAdvancedEnable() {
        CarScuManager carScuManager = this.mCarScuManager;
        if (carScuManager == null) {
            return false;
        }
        try {
            int phoneSmButton = carScuManager.getPhoneSmButton();
            Logs.d("carservice autopark isRemoteParkingAdvancedEnable over:" + phoneSmButton);
            return phoneSmButton == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendEmergencyWifiBleMessage(String str) {
        if (this.mCarTboxManager == null) {
            return;
        }
        try {
            Logs.d("carservice set sendEmergencyWifiBleMessage:" + str);
            this.mCarTboxManager.sendEmergencyWifiBleMessage(str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void sendOpenWifiHotspotResponse(boolean z) {
        if (this.mCarMcuManager == null) {
            return;
        }
        int i = z ? 1 : 0;
        try {
            Logs.d("carservice set sendOpenWifiHotspotResponse:" + i);
            this.mCarMcuManager.sendOpenWifiHotspotResponse(i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setCarCallAdvancedEnable(boolean z) {
        if (z && !isParkByMemoryEnable()) {
            Logs.d("carservice park by memory off!");
        } else {
            CarApi.setCarCallAdvancedEnable(this.mCarXpuManager, z);
        }
    }

    public boolean isCarCallAdvancedEnable() {
        return CarApi.isCarCallAdvancedEnable(this.mCarXpuManager);
    }

    public boolean isNitzTimeZoneAutomatic(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "auto_time_zone", 1) == 1;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean setNitzTimeZoneAutomatic() {
        try {
            this.mCarTboxManager.setNitzTimezone(0, TimeZone.getDefault().getID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setNitzTimeZone(String str) {
        try {
            this.mCarTboxManager.setNitzTimezone(1, str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNitzTimeZone() {
        return SystemProperties.get("persist.sys.timezone");
    }

    public int getAvasLowSpeedSound() {
        try {
            if (this.mCarAvasManager.getAvasLowSpeedSoundSwitch() == 0) {
                return 0;
            }
            return this.mCarAvasManager.getAvasLowSpeedSound();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean setAvasLowSpeedSound(int i) {
        try {
            if (i == 0) {
                this.mCarAvasManager.setAvasLowSpeedSoundSwitch(0);
            } else {
                this.mCarAvasManager.setAvasLowSpeedSoundSwitch(1);
                this.mCarAvasManager.setAvasLowSpeedSound(i);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isInFactoryMode() {
        try {
            int factoryModeSwitchStatus = this.mCarMcuManager.getFactoryModeSwitchStatus();
            Logs.d("carservice isInFactoryMode status:" + factoryModeSwitchStatus);
            SystemProperties.set(Config.PROPERTIES_FACTORY_MODE, String.valueOf(factoryModeSwitchStatus));
            return factoryModeSwitchStatus == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
