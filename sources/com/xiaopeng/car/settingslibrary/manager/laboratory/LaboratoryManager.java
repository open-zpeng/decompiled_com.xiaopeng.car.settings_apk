package com.xiaopeng.car.settingslibrary.manager.laboratory;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IAvasChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IBcmChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IScuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.repository.net.bean.LaboratoryConfig;
import com.xiaopeng.car.settingslibrary.vm.laboratory.ISayHiListener;
import com.xiaopeng.car.settingslibrary.vm.laboratory.LluSayHiEffect;
import com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.ConfigurationChangeEvent;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfigurationData;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes.dex */
public class LaboratoryManager {
    public static final String APP_USED_LIMITED_CHANGE = "xiaopeng.laboratory.app.limit.change";
    private static volatile LaboratoryManager sInstance;
    private Context mContext;
    private XuiSettingsManager mXuiSettingsManager = XuiSettingsManager.getInstance();
    private CarSettingsManager mCarSettingsManager = CarSettingsManager.getInstance();
    private DataRepository mDataRepository = DataRepository.getInstance();
    private NetConfigValues mNetConfigValues = new NetConfigValues();
    private List<ConfigChangeListener> mConfigChangeListeners = new ArrayList();
    private boolean mIsAlreadyRegisterEventbus = false;

    /* loaded from: classes.dex */
    public interface ConfigChangeListener {
        void onConfigChange(List<LaboratoryConfig> list);
    }

    public static LaboratoryManager getInstance() {
        if (sInstance == null) {
            synchronized (LaboratoryManager.class) {
                if (sInstance == null) {
                    sInstance = new LaboratoryManager(CarSettingsApp.getContext());
                }
            }
        }
        return sInstance;
    }

    private LaboratoryManager(Context context) {
        this.mContext = context;
    }

    public boolean isKeyParkingEnable() {
        return this.mCarSettingsManager.isKeyParkingEnable();
    }

    public void setKeyParkingEnable(boolean z) {
        this.mCarSettingsManager.setKeyParkingEnableOnly(z);
    }

    public void setInductionLock(boolean z) {
        this.mCarSettingsManager.setPollingOpenCfg(z);
    }

    public boolean isInductionLockEnable() {
        return this.mCarSettingsManager.getPollingOpenCfg();
    }

    public void setAppUsedLimitEnable(boolean z, boolean z2) {
        this.mXuiSettingsManager.setAppUsedLimitEnable(z);
        notifyAppUsedLimitChange(z2);
    }

    public void notifyAppUsedLimitChange(boolean z) {
        if (z) {
            return;
        }
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(APP_USED_LIMITED_CHANGE));
        Logs.d("notifyAppUsedLimitChange");
    }

    public boolean getAppUsedLimitEnable() {
        return this.mXuiSettingsManager.getAppUsedLimitEnable();
    }

    public void registerRemotePark(IScuChangeListener iScuChangeListener) {
        this.mCarSettingsManager.registerRemotePark(iScuChangeListener);
    }

    public void registerXpuApRemote(IXpuChangeListener iXpuChangeListener) {
        this.mCarSettingsManager.registerXpuApRemote(iXpuChangeListener);
    }

    public void unregisterRemotePark(IScuChangeListener iScuChangeListener) {
        this.mCarSettingsManager.unregisterRemotePark(iScuChangeListener);
    }

    public void unregisterXpuApRemote(IXpuChangeListener iXpuChangeListener) {
        this.mCarSettingsManager.unregisterXpuApRemote(iXpuChangeListener);
    }

    public void registerPolling(IBcmChangeListener iBcmChangeListener) {
        this.mCarSettingsManager.registerPolling(iBcmChangeListener);
    }

    public void unregisterPolling(IBcmChangeListener iBcmChangeListener) {
        this.mCarSettingsManager.unregisterPolling(iBcmChangeListener);
    }

    public void registerSayHiListener(ISayHiListener iSayHiListener) {
        this.mXuiSettingsManager.registerSayHiListener(iSayHiListener);
    }

    public void unregisterSayHiListener(ISayHiListener iSayHiListener) {
        this.mXuiSettingsManager.unregisterSayHiListener(iSayHiListener);
    }

    public void enableAutoPowerOff(boolean z) {
        Logs.d("xplab enableAutoPowerOff " + z);
        if (CarFunction.isSupportMcuAutoPower()) {
            this.mCarSettingsManager.setAutoPowerOffSwitch(z);
        } else {
            this.mCarSettingsManager.setAutoPowerOffConfig(z);
        }
    }

    public int getSoldierSwState() {
        return this.mCarSettingsManager.getSoldierSwState();
    }

    public boolean isSoldierCameraEnable() {
        return this.mCarSettingsManager.isSoldierCameraEnable();
    }

    public void setSoldierSw(int i) {
        this.mCarSettingsManager.setSoldierSw(i);
    }

    public void setSoldierCameraSw(boolean z) {
        this.mCarSettingsManager.setSoldierCameraSw(z);
    }

    public boolean getSoldierCameraSw() {
        return this.mCarSettingsManager.getSoldierCameraSwState();
    }

    public int packageLspVolume(int i) {
        if (i == 1) {
            int preferenceForKeyInt = GlobalSettingsSharedPreference.getPreferenceForKeyInt(this.mContext, Config.KEY_LOW_SPEED_VOLUME, -1);
            Logs.d("xplaboratory packageLspVolume saveValue:" + preferenceForKeyInt);
            if (preferenceForKeyInt <= 0) {
                return 0;
            }
            return i;
        }
        return i;
    }

    public void unregisterTbox(ITboxChangeListener iTboxChangeListener) {
        this.mCarSettingsManager.unregisterTbox(iTboxChangeListener);
    }

    public void registerTobx(ITboxChangeListener iTboxChangeListener) {
        this.mCarSettingsManager.registerTobx(iTboxChangeListener);
    }

    public void unregisterMcuAutoPower(IMcuChangeListener iMcuChangeListener) {
        this.mCarSettingsManager.unregisterMcuAutoPower(iMcuChangeListener);
    }

    public void registerMcuAutoPower(IMcuChangeListener iMcuChangeListener) {
        this.mCarSettingsManager.registerMcuAutoPower(iMcuChangeListener);
    }

    public boolean isAutoPowerOff() {
        boolean autoPowerOffConfig;
        if (CarFunction.isSupportMcuAutoPower()) {
            autoPowerOffConfig = this.mCarSettingsManager.isAutoPowerOffEnable();
        } else {
            autoPowerOffConfig = this.mCarSettingsManager.getAutoPowerOffConfig();
        }
        Logs.d("xplab isAutoPowerOff " + autoPowerOffConfig);
        return autoPowerOffConfig;
    }

    public boolean isParkByMemoryEnable() {
        return this.mCarSettingsManager.isParkByMemoryEnable();
    }

    public void setRemoteParkingAdvancedEnable(boolean z) {
        this.mCarSettingsManager.setRemoteParkingAdvancedEnable(z);
    }

    public boolean isRemoteParkingAdvancedEnable() {
        return this.mCarSettingsManager.isRemoteParkingAdvancedEnable();
    }

    public boolean isRemoteParkingEnable() {
        return this.mCarSettingsManager.getKeyPark();
    }

    public void setRemoteParkingEnable(boolean z) {
        this.mCarSettingsManager.setKeyPark(z);
        GlobalSettingsSharedPreference.setD21RemoteParking(this.mContext, z);
    }

    public int getLowSpeedVolume() {
        return packageLspVolume(this.mCarSettingsManager.getLowSpeedVolume());
    }

    public void setLowSpeedVolumeEnable(Context context, boolean z) {
        Logs.d("xplaboratory set lowspeed " + z);
        this.mDataRepository.setSettingProvider(context, XPSettingsConfig.XP_LOW_SPEED_SOUND, z);
    }

    public boolean getLowSpeedVolumeEnable(Context context) {
        boolean settingProvider = this.mDataRepository.getSettingProvider(context, XPSettingsConfig.XP_LOW_SPEED_SOUND, true);
        Logs.d("xplaboratory get lowspeed " + settingProvider);
        return settingProvider;
    }

    public void setLowSpeedVolume(int i) {
        this.mCarSettingsManager.setLowSpeedVolume(i);
    }

    private void setTboxThresholdSwitch(int i, int i2, int i3) {
        this.mCarSettingsManager.setTboxThresholdSwitch(i, i2, i3);
    }

    public void unregisterLowSpeedVolume(IAvasChangeListener iAvasChangeListener) {
        this.mCarSettingsManager.unregisterLowSpeedVolume(iAvasChangeListener);
    }

    public void registerLowSpeedVolume(IAvasChangeListener iAvasChangeListener) {
        this.mCarSettingsManager.registerLowSpeedVolume(iAvasChangeListener);
    }

    public NetConfigValues getNetConfigValues() {
        return this.mNetConfigValues;
    }

    public void registerEventBus() {
        if (this.mIsAlreadyRegisterEventbus) {
            return;
        }
        EventBus.getDefault().register(this);
        this.mIsAlreadyRegisterEventbus = true;
    }

    public void unregisterEventBus() {
        if (this.mIsAlreadyRegisterEventbus) {
            EventBus.getDefault().unregister(this);
            this.mIsAlreadyRegisterEventbus = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public synchronized void onConfigurationChanged(ConfigurationChangeEvent configurationChangeEvent) {
        List<IConfigurationData> changeList = configurationChangeEvent.getChangeList();
        if (changeList != null && !changeList.isEmpty()) {
            Logs.d("xplaboratory onConfigurationChanged " + Thread.currentThread().getName());
            ArrayList<LaboratoryConfig> arrayList = new ArrayList();
            for (IConfigurationData iConfigurationData : changeList) {
                if (Config.LaboratoryNetConfig.SOLIDER_THRESHOLD.equals(iConfigurationData.getKey())) {
                    Logs.d("xplaboratory config solider threshold v:" + iConfigurationData.getValue());
                    pushToTbox(iConfigurationData.getValue());
                } else {
                    LaboratoryConfig laboratoryConfig = new LaboratoryConfig();
                    Logs.d("xplaboratory config k:" + iConfigurationData.getKey() + " v:" + iConfigurationData.getValue());
                    laboratoryConfig.setKey(iConfigurationData.getKey());
                    String[] showAndEnable = getShowAndEnable(iConfigurationData.getValue());
                    if (showAndEnable != null && showAndEnable.length >= 2) {
                        laboratoryConfig.setShow(showAndEnable[0]);
                        laboratoryConfig.setEnable(showAndEnable[1]);
                        arrayList.add(laboratoryConfig);
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                for (LaboratoryConfig laboratoryConfig2 : arrayList) {
                    updateNetConfig(laboratoryConfig2);
                }
                for (ConfigChangeListener configChangeListener : this.mConfigChangeListeners) {
                    configChangeListener.onConfigChange(arrayList);
                }
            }
        }
    }

    private LaboratoryConfig getConfiguration(String str) {
        String[] showAndEnable;
        IConfiguration configurationController = CarSettingsApp.getConfigurationController();
        if (configurationController != null) {
            String configuration = configurationController.getConfiguration(str, "");
            Logs.d("xplaboratory getConfiguration value:" + configuration + " key:" + str);
            if (TextUtils.isEmpty(configuration) || (showAndEnable = getShowAndEnable(configuration)) == null || showAndEnable.length < 2) {
                return null;
            }
            LaboratoryConfig laboratoryConfig = new LaboratoryConfig();
            laboratoryConfig.setKey(str);
            laboratoryConfig.setShow(showAndEnable[0]);
            laboratoryConfig.setEnable(showAndEnable[1]);
            return laboratoryConfig;
        }
        return null;
    }

    private void pushToTbox(String str) {
        String[] showAndEnable = getShowAndEnable(str);
        if (showAndEnable == null || showAndEnable.length < 3) {
            return;
        }
        try {
            setTboxThresholdSwitch(Integer.parseInt(showAndEnable[2]), Integer.parseInt(showAndEnable[1]), Integer.parseInt(showAndEnable[0]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getSoldierThreshold() {
        IConfiguration configurationController = CarSettingsApp.getConfigurationController();
        if (configurationController != null) {
            String configuration = configurationController.getConfiguration(Config.LaboratoryNetConfig.SOLIDER_THRESHOLD, "");
            Logs.d("xplaboratory getSoldierThreshold value:" + configuration + " key:" + Config.LaboratoryNetConfig.SOLIDER_THRESHOLD);
            pushToTbox(configuration);
        }
    }

    private String[] getShowAndEnable(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.replaceAll("\"", "").split(",");
    }

    public void addConfigChangeListener(ConfigChangeListener configChangeListener) {
        if (this.mConfigChangeListeners.contains(configChangeListener)) {
            return;
        }
        this.mConfigChangeListeners.add(configChangeListener);
    }

    public void removeConfigChangeListener(ConfigChangeListener configChangeListener) {
        this.mConfigChangeListeners.remove(configChangeListener);
    }

    public void setNearUnlockEnable(boolean z) {
        this.mCarSettingsManager.setNearPollingUnLockSw(z);
    }

    public boolean getNearUnlockEnable() {
        return this.mCarSettingsManager.getNearePollingUnLockSw();
    }

    public boolean getLeaveLockEnable() {
        return this.mCarSettingsManager.getLeavePollingLockSw();
    }

    public void setLeaveLockEnable(boolean z) {
        this.mCarSettingsManager.setLeavePollingLockSw(z);
    }

    public void setTtsBroadcastType(int i) {
        this.mDataRepository.setTtsBroadcastType(this.mContext, i);
    }

    public int getTtsBroadcastType() {
        return this.mDataRepository.getTtsBroadcastType(this.mContext);
    }

    public void setAutoParkSw(boolean z) {
        this.mCarSettingsManager.setAutoParkSw(z);
    }

    public int getNedcSwitchStatus() {
        return this.mCarSettingsManager.getNedcSwitchStatus();
    }

    public boolean isConfigShow(String str) {
        LaboratoryConfig configuration = getConfiguration(str);
        if (configuration == null) {
            return !Config.LaboratoryNetConfig.LOW_SPEED_ALARM_SOUND.equals(str);
        }
        Logs.d("xplaboratory isConfigShow:" + configuration.getShow());
        return "1".equals(configuration.getShow());
    }

    public NetConfigValues getAllConfiguration() {
        LaboratoryConfig configuration = getConfiguration(Config.LaboratoryNetConfig.APP_USED_LIMIT);
        if (configuration != null) {
            updateNetConfig(configuration);
        } else {
            this.mNetConfigValues.setAppUsedLimitNetEnable(true);
        }
        LaboratoryConfig configuration2 = getConfiguration(Config.LaboratoryNetConfig.LOW_SPEED_ALARM_SOUND);
        if (configuration2 != null) {
            updateNetConfig(configuration2);
        } else {
            this.mNetConfigValues.setLowSpeedSoundNetEnable(false);
        }
        LaboratoryConfig configuration3 = getConfiguration(Config.LaboratoryNetConfig.AUTO_POWER_OFF);
        if (configuration3 != null) {
            updateNetConfig(configuration3);
        } else {
            this.mNetConfigValues.setAutoPowerOff(true);
        }
        if (CarFunction.isSupportRemotePark()) {
            LaboratoryConfig configuration4 = getConfiguration(Config.LaboratoryNetConfig.REMOTE_CONTROL_PARKING);
            if (configuration4 != null) {
                updateNetConfig(configuration4);
            } else {
                this.mNetConfigValues.setRemoteControlEnable(true);
            }
        }
        if (CarFunction.isSupportSoldierModeLevel()) {
            LaboratoryConfig configuration5 = getConfiguration(Config.LaboratoryNetConfig.SOLDIER_MODE);
            if (configuration5 != null) {
                updateNetConfig(configuration5);
            } else {
                this.mNetConfigValues.setSoldierEnable(true);
            }
            getSoldierThreshold();
        }
        if (CarFunction.isSupportSoldierModeCamera()) {
            LaboratoryConfig configuration6 = getConfiguration(Config.LaboratoryNetConfig.SOLDIER_CAMERA);
            if (configuration6 != null) {
                updateNetConfig(configuration6);
            } else {
                this.mNetConfigValues.setSoldierCameraEnable(true);
            }
        }
        if (CarFunction.isSupportNearUnlock()) {
            LaboratoryConfig configuration7 = getConfiguration(Config.LaboratoryNetConfig.NEAR_UNLOCK);
            if (configuration7 != null) {
                updateNetConfig(configuration7);
            } else {
                this.mNetConfigValues.setNearUnlockEnable(true);
            }
        }
        if (CarFunction.isSupportLeaveLock()) {
            LaboratoryConfig configuration8 = getConfiguration(Config.LaboratoryNetConfig.LEAVE_LOCK);
            if (configuration8 != null) {
                updateNetConfig(configuration8);
            } else {
                this.mNetConfigValues.setLeaveLockEnable(true);
            }
        }
        if (CarFunction.isSupportKeyPark()) {
            LaboratoryConfig configuration9 = getConfiguration(Config.LaboratoryNetConfig.REMOTE_CONTROL_KEY_PARKING);
            if (configuration9 != null) {
                updateNetConfig(configuration9);
            } else {
                this.mNetConfigValues.setRemoteControlKeyEnable(true);
            }
        }
        if (CarFunction.isSupportInductionLock()) {
            LaboratoryConfig configuration10 = getConfiguration(Config.LaboratoryNetConfig.INDUCTION_LOCK);
            if (configuration10 != null) {
                updateNetConfig(configuration10);
            } else {
                this.mNetConfigValues.setInductionLockEnable(true);
            }
        }
        if (CarFunction.isSupportAutoDriveTts()) {
            LaboratoryConfig configuration11 = getConfiguration(Config.LaboratoryNetConfig.TTS_BROADCAST);
            if (configuration11 != null) {
                updateNetConfig(configuration11);
            } else {
                this.mNetConfigValues.setTtsBroadcastEnable(true);
            }
        }
        if (CarFunction.isSupportRemoteParkAdvanced()) {
            LaboratoryConfig configuration12 = getConfiguration(Config.LaboratoryNetConfig.REMOTE_PARKING_ADVANCED);
            if (configuration12 != null) {
                updateNetConfig(configuration12);
            } else {
                this.mNetConfigValues.setRemoteControlAdvancedEnable(true);
            }
        }
        if (CarFunction.isSupportCarCallAdvanced()) {
            LaboratoryConfig configuration13 = getConfiguration(Config.LaboratoryNetConfig.CAR_CALL_ADVANCED);
            if (configuration13 != null) {
                updateNetConfig(configuration13);
            } else {
                this.mNetConfigValues.setCarCallAdvancedEnable(true);
            }
        }
        if (CarFunction.isSupportAppScreenFlow()) {
            LaboratoryConfig configuration14 = getConfiguration("app_screen_flow");
            if (configuration14 != null) {
                updateNetConfig(configuration14);
            } else {
                this.mNetConfigValues.setAppScreenFlowEnable(true);
            }
        }
        if (CarFunction.isSupportRearRowReminder()) {
            LaboratoryConfig configuration15 = getConfiguration("rear_row_reminder");
            if (configuration15 != null) {
                updateNetConfig(configuration15);
            } else {
                this.mNetConfigValues.setRearRowReminder(true);
            }
        }
        if (CarFunction.isSupportSayHi()) {
            LaboratoryConfig configuration16 = getConfiguration(Config.LaboratoryNetConfig.SAY_HI_LIGHT_LANGUAGE);
            if (configuration16 != null) {
                updateNetConfig(configuration16);
            } else {
                this.mNetConfigValues.setSayHiSupport(true);
            }
        }
        return this.mNetConfigValues;
    }

    private void updateNetConfig(LaboratoryConfig laboratoryConfig) {
        if (laboratoryConfig == null) {
            return;
        }
        String key = laboratoryConfig.getKey();
        char c = 65535;
        switch (key.hashCode()) {
            case -1928861566:
                if (key.equals(Config.LaboratoryNetConfig.SOLDIER_CAMERA)) {
                    c = 5;
                    break;
                }
                break;
            case -1185083019:
                if (key.equals(Config.LaboratoryNetConfig.TTS_BROADCAST)) {
                    c = '\n';
                    break;
                }
                break;
            case -559426654:
                if (key.equals(Config.LaboratoryNetConfig.REMOTE_PARKING_ADVANCED)) {
                    c = 11;
                    break;
                }
                break;
            case -421924482:
                if (key.equals(Config.LaboratoryNetConfig.LOW_SPEED_ALARM_SOUND)) {
                    c = 0;
                    break;
                }
                break;
            case -30160461:
                if (key.equals(Config.LaboratoryNetConfig.LEAVE_LOCK)) {
                    c = 7;
                    break;
                }
                break;
            case 248712195:
                if (key.equals("app_screen_flow")) {
                    c = '\r';
                    break;
                }
                break;
            case 311825629:
                if (key.equals(Config.LaboratoryNetConfig.REMOTE_CONTROL_PARKING)) {
                    c = 3;
                    break;
                }
                break;
            case 457639319:
                if (key.equals(Config.LaboratoryNetConfig.APP_USED_LIMIT)) {
                    c = 1;
                    break;
                }
                break;
            case 829101445:
                if (key.equals(Config.LaboratoryNetConfig.INDUCTION_LOCK)) {
                    c = '\t';
                    break;
                }
                break;
            case 986339131:
                if (key.equals(Config.LaboratoryNetConfig.NEAR_UNLOCK)) {
                    c = 6;
                    break;
                }
                break;
            case 1070928448:
                if (key.equals(Config.LaboratoryNetConfig.SOLDIER_MODE)) {
                    c = 4;
                    break;
                }
                break;
            case 1076242443:
                if (key.equals(Config.LaboratoryNetConfig.SAY_HI_LIGHT_LANGUAGE)) {
                    c = 15;
                    break;
                }
                break;
            case 1610086328:
                if (key.equals(Config.LaboratoryNetConfig.CAR_CALL_ADVANCED)) {
                    c = '\f';
                    break;
                }
                break;
            case 1920954493:
                if (key.equals(Config.LaboratoryNetConfig.REMOTE_CONTROL_KEY_PARKING)) {
                    c = '\b';
                    break;
                }
                break;
            case 2084185253:
                if (key.equals(Config.LaboratoryNetConfig.AUTO_POWER_OFF)) {
                    c = 2;
                    break;
                }
                break;
            case 2124187634:
                if (key.equals("rear_row_reminder")) {
                    c = 14;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mNetConfigValues.setLowSpeedSoundNetEnable("1".equals(laboratoryConfig.getShow()));
                if (this.mNetConfigValues.isLowSpeedSoundNetEnable()) {
                    return;
                }
                if (CarFunction.isSupportLowSpeedVolumeSwitch()) {
                    setLowSpeedVolumeEnable(this.mContext, "1".equals(laboratoryConfig.getEnable()));
                }
                if (CarFunction.isSupportLowSpeedVolumeSlider()) {
                    setLowSpeedVolume(Config.LOW_SPEED_VOLUME_DEFAULT);
                    return;
                }
                return;
            case 1:
                this.mNetConfigValues.setAppUsedLimitNetEnable("1".equals(laboratoryConfig.getShow()));
                if (this.mNetConfigValues.isAppUsedLimitNetEnable()) {
                    return;
                }
                setAppUsedLimitEnable("1".equals(laboratoryConfig.getEnable()), false);
                return;
            case 2:
                this.mNetConfigValues.setAutoPowerOff("1".equals(laboratoryConfig.getShow()));
                if (this.mNetConfigValues.isAutoPowerOff()) {
                    return;
                }
                enableAutoPowerOff("1".equals(laboratoryConfig.getEnable()));
                return;
            case 3:
                if (CarFunction.isSupportRemotePark()) {
                    this.mNetConfigValues.setRemoteControlEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isRemoteControlEnable()) {
                        return;
                    }
                    setRemoteParkingEnable("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case 4:
                if (CarFunction.isSupportSoldierModeLevel()) {
                    this.mNetConfigValues.setSoldierEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isSoldierEnable()) {
                        return;
                    }
                    String enable = laboratoryConfig.getEnable();
                    Logs.d("xplab -soldier netconfig 恢复后台值 level:" + enable);
                    if (TextUtils.isEmpty(enable)) {
                        return;
                    }
                    try {
                        int parseInt = Integer.parseInt(enable);
                        if (parseInt < 0 || parseInt > 3) {
                            return;
                        }
                        setSoldierSw(parseInt);
                        return;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            case 5:
                if (CarFunction.isSupportSoldierModeCamera()) {
                    this.mNetConfigValues.setSoldierCameraEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isSoldierCameraEnable()) {
                        return;
                    }
                    Logs.d("xplab -soldier-camera netconfig 恢复后台值 level:" + laboratoryConfig.getEnable());
                    setSoldierCameraSw(false);
                    return;
                }
                return;
            case 6:
                if (CarFunction.isSupportNearUnlock()) {
                    this.mNetConfigValues.setNearUnlockEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isNearUnlockEnable()) {
                        return;
                    }
                    setNearUnlockEnable("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case 7:
                if (CarFunction.isSupportLeaveLock()) {
                    this.mNetConfigValues.setLeaveLockEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isLeaveLockEnable()) {
                        return;
                    }
                    setLeaveLockEnable("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case '\b':
                if (CarFunction.isSupportKeyPark()) {
                    this.mNetConfigValues.setRemoteControlKeyEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isRemoteControlKeyEnable()) {
                        return;
                    }
                    if ("1".equals(laboratoryConfig.getEnable()) && isInNedcMode()) {
                        Logs.d("xplab no support key-parking enable!");
                        return;
                    } else {
                        setKeyParkingEnable("1".equals(laboratoryConfig.getEnable()));
                        return;
                    }
                }
                return;
            case '\t':
                if (CarFunction.isSupportInductionLock()) {
                    this.mNetConfigValues.setInductionLockEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isInductionLockEnable()) {
                        return;
                    }
                    setInductionLock("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case '\n':
                if (CarFunction.isSupportAutoDriveTts()) {
                    this.mNetConfigValues.setTtsBroadcastEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isTtsBroadcastEnable()) {
                        return;
                    }
                    String enable2 = laboratoryConfig.getEnable();
                    Logs.d("xplab -tts netconfig 恢复后台值 value:" + enable2);
                    if (TextUtils.isEmpty(enable2)) {
                        return;
                    }
                    try {
                        setTtsBroadcastType(Integer.parseInt(enable2));
                        return;
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            case 11:
                if (CarFunction.isSupportRemoteParkAdvanced()) {
                    this.mNetConfigValues.setRemoteControlAdvancedEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isRemoteControlAdvancedEnable()) {
                        return;
                    }
                    if ("1".equals(laboratoryConfig.getEnable()) && isInNedcMode()) {
                        Logs.d("xplab no support key-parking advanced enable!");
                        return;
                    } else {
                        setRemoteParkingAdvancedEnable("1".equals(laboratoryConfig.getEnable()));
                        return;
                    }
                }
                return;
            case '\f':
                if (CarFunction.isSupportCarCallAdvanced()) {
                    this.mNetConfigValues.setCarCallAdvancedEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isCarCallAdvancedEnable()) {
                        return;
                    }
                    if ("1".equals(laboratoryConfig.getEnable()) && isInNedcMode()) {
                        Logs.d("xplab no support car call advanced enable!");
                        return;
                    } else {
                        setCarCallAdvancedEnable("1".equals(laboratoryConfig.getEnable()));
                        return;
                    }
                }
                return;
            case '\r':
                if (CarFunction.isSupportAppScreenFlow()) {
                    this.mNetConfigValues.setAppScreenFlowEnable("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isAppScreenFlowEnable()) {
                        return;
                    }
                    setAppScreenFlow("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case 14:
                if (CarFunction.isSupportRearRowReminder()) {
                    this.mNetConfigValues.setRearRowReminder("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isRearRowReminder()) {
                        return;
                    }
                    setRearRowReminder("1".equals(laboratoryConfig.getEnable()));
                    return;
                }
                return;
            case 15:
                if (CarFunction.isSupportSayHi()) {
                    this.mNetConfigValues.setSayHiSupport("1".equals(laboratoryConfig.getShow()));
                    if (this.mNetConfigValues.isSayHiSupport()) {
                        return;
                    }
                    setSayHiEffect(LluSayHiEffect.EffectA);
                    setSayHiEnable(false);
                    setSayHiAvasEnable(false);
                    return;
                }
                return;
            default:
                Logs.d("xplab no such key:" + laboratoryConfig.getKey());
                return;
        }
    }

    public void setAppScreenFlow(boolean z) {
        this.mDataRepository.setScreenFlow(z);
    }

    public void setRearRowReminder(boolean z) {
        this.mDataRepository.setRearRowReminder(this.mContext, z);
    }

    public boolean isRearRowReminder() {
        return this.mDataRepository.getRearRowReminder(this.mContext);
    }

    public void setCarCallAdvancedEnable(boolean z) {
        this.mCarSettingsManager.setCarCallAdvancedEnable(z);
    }

    public boolean isCarCallAdvancedEnable() {
        return this.mCarSettingsManager.isCarCallAdvancedEnable();
    }

    public boolean getAutoParkSwEnable() {
        return this.mCarSettingsManager.getAutoParkSwEnable();
    }

    public boolean isInNedcMode() {
        int nedcSwitchStatus = getNedcSwitchStatus();
        Logs.d("xplaboratory isInNedcMode :" + nedcSwitchStatus);
        return nedcSwitchStatus == 1 || nedcSwitchStatus == 3 || nedcSwitchStatus == 4 || nedcSwitchStatus == 5;
    }

    public void setSayHiEffect(LluSayHiEffect lluSayHiEffect) {
        this.mXuiSettingsManager.setSayHiEffect(lluSayHiEffect);
    }

    public int getLluSayHiEffect() {
        return this.mXuiSettingsManager.getLluSayHiEffect();
    }

    public void setSayHiEnable(boolean z) {
        this.mXuiSettingsManager.setSayHiEnable(z);
    }

    public boolean getSayHiEnable() {
        return this.mXuiSettingsManager.getSayHiEnable();
    }

    public void setSayHiAvasEnable(boolean z) {
        this.mXuiSettingsManager.setSayHiAvasEnable(z);
    }

    public boolean getSayHiAvasEnable() {
        return this.mXuiSettingsManager.getSayHiAvasEnable();
    }
}
