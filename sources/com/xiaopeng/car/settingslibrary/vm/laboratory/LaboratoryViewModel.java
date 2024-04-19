package com.xiaopeng.car.settingslibrary.vm.laboratory;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.IAvasChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IBcmChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IScuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager;
import com.xiaopeng.car.settingslibrary.repository.net.bean.LaboratoryConfig;
import java.util.List;
/* loaded from: classes.dex */
public class LaboratoryViewModel extends AndroidViewModel implements ITboxChangeListener, IMcuChangeListener, IScuChangeListener, IAvasChangeListener, LaboratoryManager.ConfigChangeListener, IBcmChangeListener, IXpuChangeListener, ISayHiListener {
    private MutableLiveData<Boolean> mAppLimitLiveData;
    private Application mApplication;
    private MutableLiveData<Boolean> mAutoPowerOff;
    private final MutableLiveData<Boolean> mAvasSayHiSwLiveData;
    private MutableLiveData<Boolean> mCarCallAdvancedLiveData;
    private MutableLiveData<Boolean> mInductionLockEnable;
    private MutableLiveData<Boolean> mKeyParkAdvancedLiveData;
    private MutableLiveData<Boolean> mKeyParkLiveData;
    private LaboratoryManager mLaboratoryManager;
    private MutableLiveData<Boolean> mLeaveLockLiveData;
    private final MutableLiveData<Integer> mLluSayHiEffectLiveData;
    private final MutableLiveData<Boolean> mLluSayHiSwLiveData;
    private MutableLiveData<Boolean> mLowSpeedLiveData;
    private ContentObserver mLowSpeedObserver;
    private MutableLiveData<Integer> mLowSpeedVolumeLiveData;
    private MutableLiveData<Boolean> mNearUnlockLiveData;
    private MutableLiveData<Integer> mNedcModeStatus;
    private MediatorLiveData<NetConfigValues> mNetConfigValuesLiveData;
    private MutableLiveData<Boolean> mRearRowReminderLiveData;
    private ContentObserver mRearRowReminderObserver;
    private MutableLiveData<Boolean> mRemoteParkLiveData;
    private ContentObserver mSayHiAVASSwitchObserver;
    private ISayHiListener mSayHiEffectCallback;
    private ContentObserver mSayHiSwitchObserver;
    private MutableLiveData<Boolean> mSoldierCameraEnable;
    private MutableLiveData<Boolean> mSoldierCameraStatus;
    private MutableLiveData<Integer> mSoldierStatus;
    private UIChangeReceiver mUIChangeReceiver;

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onCancelAutoPowerOff() {
    }

    public LaboratoryViewModel(Application application) {
        super(application);
        this.mNetConfigValuesLiveData = new MediatorLiveData<>();
        this.mLowSpeedLiveData = new MutableLiveData<>();
        this.mLowSpeedVolumeLiveData = new MutableLiveData<>();
        this.mNearUnlockLiveData = new MutableLiveData<>();
        this.mLeaveLockLiveData = new MutableLiveData<>();
        this.mRemoteParkLiveData = new MutableLiveData<>();
        this.mAutoPowerOff = new MutableLiveData<>();
        this.mSoldierStatus = new MutableLiveData<>();
        this.mNedcModeStatus = new MutableLiveData<>();
        this.mSoldierCameraStatus = new MutableLiveData<>();
        this.mSoldierCameraEnable = new MutableLiveData<>();
        this.mInductionLockEnable = new MutableLiveData<>();
        this.mKeyParkLiveData = new MutableLiveData<>();
        this.mKeyParkAdvancedLiveData = new MutableLiveData<>();
        this.mCarCallAdvancedLiveData = new MutableLiveData<>();
        this.mAppLimitLiveData = new MutableLiveData<>();
        this.mRearRowReminderLiveData = new MutableLiveData<>();
        this.mAvasSayHiSwLiveData = new MutableLiveData<>();
        this.mLluSayHiSwLiveData = new MutableLiveData<>();
        this.mLluSayHiEffectLiveData = new MutableLiveData<>();
        this.mLowSpeedObserver = new ContentObserver(new Handler()) { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (Settings.System.getUriFor(XPSettingsConfig.XP_LOW_SPEED_SOUND).equals(uri)) {
                    boolean isLowSpeedSoundEnable = LaboratoryViewModel.this.isLowSpeedSoundEnable();
                    Logs.d("xplaboratory lowspeed " + isLowSpeedSoundEnable);
                    LaboratoryViewModel.this.mLowSpeedLiveData.postValue(Boolean.valueOf(isLowSpeedSoundEnable));
                }
            }
        };
        this.mRearRowReminderObserver = new ContentObserver(new Handler()) { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (Settings.Global.getUriFor("rear_row_reminder").equals(uri)) {
                    boolean isRearRowReminder = LaboratoryViewModel.this.mLaboratoryManager.isRearRowReminder();
                    Logs.d("xplaboratory rear row reminder: " + isRearRowReminder);
                    LaboratoryViewModel.this.mRearRowReminderLiveData.postValue(Boolean.valueOf(isRearRowReminder));
                }
            }
        };
        this.mSayHiSwitchObserver = new ContentObserver(new Handler()) { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (Settings.System.getUriFor(XPSettingsConfig.KEY_SAYHI_SW).equals(uri)) {
                    boolean sayHiEnable = LaboratoryViewModel.this.getSayHiEnable();
                    Logs.d("xplaboratory say hi enable : " + sayHiEnable);
                    LaboratoryViewModel.this.mLluSayHiSwLiveData.postValue(Boolean.valueOf(sayHiEnable));
                }
            }
        };
        this.mSayHiAVASSwitchObserver = new ContentObserver(new Handler()) { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.5
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (Settings.System.getUriFor(XPSettingsConfig.SAY_HI_AVAS_SW).equals(uri)) {
                    boolean sayHiAvasEnable = LaboratoryViewModel.this.getSayHiAvasEnable();
                    Logs.d("xplaboratory say hi avas enable : " + sayHiAvasEnable);
                    LaboratoryViewModel.this.mAvasSayHiSwLiveData.postValue(Boolean.valueOf(sayHiAvasEnable));
                }
            }
        };
        this.mSayHiEffectCallback = new ISayHiListener() { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.6
            @Override // com.xiaopeng.car.settingslibrary.vm.laboratory.ISayHiListener
            public void onLluSayHiTypeChanged(int i) {
                LaboratoryViewModel.this.mLluSayHiEffectLiveData.postValue(Integer.valueOf(i));
            }
        };
        this.mUIChangeReceiver = new UIChangeReceiver();
        this.mApplication = application;
        this.mLaboratoryManager = LaboratoryManager.getInstance();
    }

    public MutableLiveData<Boolean> getRearRowReminderLiveData() {
        return this.mRearRowReminderLiveData;
    }

    public MutableLiveData<Boolean> getAppLimitLiveData() {
        return this.mAppLimitLiveData;
    }

    public MutableLiveData<Boolean> getKeyParkLiveData() {
        return this.mKeyParkLiveData;
    }

    public MutableLiveData<Boolean> getKeyParkAdvancedLiveData() {
        return this.mKeyParkAdvancedLiveData;
    }

    public MediatorLiveData<NetConfigValues> getNetConfigValuesLiveData() {
        return this.mNetConfigValuesLiveData;
    }

    public MutableLiveData<Boolean> getCarCallAdvancedLiveData() {
        return this.mCarCallAdvancedLiveData;
    }

    public MutableLiveData<Integer> getNedcModeStatus() {
        return this.mNedcModeStatus;
    }

    public MutableLiveData<Boolean> getLowSpeedLiveData() {
        return this.mLowSpeedLiveData;
    }

    public MutableLiveData<Integer> getLowSpeedVolumeLiveData() {
        return this.mLowSpeedVolumeLiveData;
    }

    public MutableLiveData<Boolean> getAutoPowerOffLiveData() {
        return this.mAutoPowerOff;
    }

    public MutableLiveData<Integer> getSoldierStatusLiveData() {
        return this.mSoldierStatus;
    }

    public MutableLiveData<Boolean> getSoldierCameraEnableLiveData() {
        return this.mSoldierCameraEnable;
    }

    public MutableLiveData<Boolean> getSoldierCameraLiveData() {
        return this.mSoldierCameraStatus;
    }

    public MutableLiveData<Boolean> getRemoteParkLiveData() {
        return this.mRemoteParkLiveData;
    }

    public MutableLiveData<Boolean> getNearUnlockLiveData() {
        return this.mNearUnlockLiveData;
    }

    public MutableLiveData<Boolean> getLeaveLockLiveData() {
        return this.mLeaveLockLiveData;
    }

    public MutableLiveData<Boolean> getInductionLockEnable() {
        return this.mInductionLockEnable;
    }

    public MutableLiveData<Boolean> getLluSayHiSwLiveData() {
        return this.mLluSayHiSwLiveData;
    }

    public MutableLiveData<Integer> getLluSayHiEffectLiveData() {
        return this.mLluSayHiEffectLiveData;
    }

    public MutableLiveData<Boolean> getAvasSayHiSwLiveData() {
        return this.mAvasSayHiSwLiveData;
    }

    public void enableAppLimit(boolean z) {
        this.mLaboratoryManager.setAppUsedLimitEnable(z, true);
    }

    public void enableLowSpeedSound(boolean z) {
        this.mLaboratoryManager.setLowSpeedVolumeEnable(this.mApplication, z);
    }

    public boolean isAppLimitEnable() {
        return this.mLaboratoryManager.getAppUsedLimitEnable();
    }

    public boolean isLowSpeedSoundEnable() {
        return this.mLaboratoryManager.getLowSpeedVolumeEnable(this.mApplication);
    }

    public void isInductionLockEnable() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel.1
            @Override // java.lang.Runnable
            public void run() {
                boolean isInductionLockEnable = LaboratoryViewModel.this.mLaboratoryManager.isInductionLockEnable();
                Logs.d("isInductionLockEnable:" + isInductionLockEnable);
                LaboratoryViewModel.this.mInductionLockEnable.postValue(Boolean.valueOf(isInductionLockEnable));
            }
        });
    }

    public boolean isInductionLock() {
        return this.mLaboratoryManager.isInductionLockEnable();
    }

    public void setInductionLock(boolean z) {
        this.mLaboratoryManager.setInductionLock(z);
    }

    public void setTtsBroadcastType(int i) {
        this.mLaboratoryManager.setTtsBroadcastType(i);
    }

    public int getTtsBroadcastType() {
        return this.mLaboratoryManager.getTtsBroadcastType();
    }

    public void onStartVM() {
        registerLowSpeedChange();
        if (CarFunction.isSupportKeyPark() || CarFunction.isSupportRemotePark() || CarFunction.isSupportRemoteParkAdvanced()) {
            this.mLaboratoryManager.registerRemotePark(this);
        }
        if (CarFunction.isSupportCarCallAdvanced()) {
            this.mLaboratoryManager.registerXpuApRemote(this);
        }
        if (CarFunction.isSupportLeaveLock() || CarFunction.isSupportNearUnlock() || CarFunction.isSupportInductionLock()) {
            this.mLaboratoryManager.registerPolling(this);
        }
        if (CarFunction.isSupportMcuAutoPower()) {
            this.mLaboratoryManager.registerMcuAutoPower(this);
        }
        if (CarFunction.isSupportTboxAutoPower() || CarFunction.isSupportSoldierModeLevel() || CarFunction.isSupportSoldierModeCamera()) {
            this.mLaboratoryManager.registerTobx(this);
        }
        this.mLaboratoryManager.registerLowSpeedVolume(this);
        this.mLaboratoryManager.addConfigChangeListener(this);
        this.mNetConfigValuesLiveData.postValue(this.mLaboratoryManager.getAllConfiguration());
        registerUIChangeReceiver();
        if (CarFunction.isSupportRearRowReminder()) {
            registerRearRowReminderChange();
        }
        if (CarFunction.isSupportSayHi()) {
            registerSayHiChanged();
        }
    }

    public NetConfigValues getAllConfiguration() {
        return this.mLaboratoryManager.getAllConfiguration();
    }

    public void onStopVM() {
        unRegisterLowSpeed();
        if (CarFunction.isSupportKeyPark() || CarFunction.isSupportRemotePark() || CarFunction.isSupportRemoteParkAdvanced()) {
            this.mLaboratoryManager.unregisterRemotePark(this);
        }
        if (CarFunction.isSupportCarCallAdvanced()) {
            this.mLaboratoryManager.unregisterXpuApRemote(this);
        }
        if (CarFunction.isSupportLeaveLock() || CarFunction.isSupportNearUnlock() || CarFunction.isSupportInductionLock()) {
            this.mLaboratoryManager.unregisterPolling(this);
        }
        if (CarFunction.isSupportMcuAutoPower()) {
            this.mLaboratoryManager.unregisterMcuAutoPower(this);
        }
        if (CarFunction.isSupportTboxAutoPower() || CarFunction.isSupportSoldierModeLevel() || CarFunction.isSupportSoldierModeCamera()) {
            this.mLaboratoryManager.unregisterTbox(this);
        }
        this.mLaboratoryManager.unregisterLowSpeedVolume(this);
        this.mLaboratoryManager.removeConfigChangeListener(this);
        unregisterUIChangeReceiver();
        if (CarFunction.isSupportRearRowReminder()) {
            unRegisterRearRowReminder();
        }
        if (CarFunction.isSupportSayHi()) {
            unregisterSayHiChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
    }

    private void registerLowSpeedChange() {
        this.mApplication.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.XP_LOW_SPEED_SOUND), true, this.mLowSpeedObserver);
    }

    private void unRegisterLowSpeed() {
        this.mApplication.getContentResolver().unregisterContentObserver(this.mLowSpeedObserver);
    }

    private void registerRearRowReminderChange() {
        this.mApplication.getContentResolver().registerContentObserver(Settings.Global.getUriFor("rear_row_reminder"), true, this.mRearRowReminderObserver);
    }

    private void unRegisterRearRowReminder() {
        this.mApplication.getContentResolver().unregisterContentObserver(this.mRearRowReminderObserver);
    }

    private void registerSayHiChanged() {
        this.mApplication.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.KEY_SAYHI_SW), true, this.mSayHiSwitchObserver);
        this.mApplication.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.SAY_HI_AVAS_SW), true, this.mSayHiAVASSwitchObserver);
        this.mLaboratoryManager.registerSayHiListener(this.mSayHiEffectCallback);
    }

    private void unregisterSayHiChanged() {
        this.mApplication.getContentResolver().unregisterContentObserver(this.mSayHiSwitchObserver);
        this.mApplication.getContentResolver().unregisterContentObserver(this.mSayHiAVASSwitchObserver);
        this.mLaboratoryManager.unregisterSayHiListener(this.mSayHiEffectCallback);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IScuChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IAvasChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IBcmChangeListener
    public void onValueChange(int i, int i2) {
        Logs.d("xplab onValueChange id:" + i + " value:" + i2);
        if (i == 557852225) {
            this.mRemoteParkLiveData.postValue(Boolean.valueOf(i2 == 1));
        } else if (i == 557855244) {
            this.mLowSpeedVolumeLiveData.postValue(Integer.valueOf(this.mLaboratoryManager.packageLspVolume(i2)));
        } else if (i == 557849716) {
            this.mLeaveLockLiveData.postValue(Boolean.valueOf(i2 == 1));
        } else if (i == 557849717) {
            this.mNearUnlockLiveData.postValue(Boolean.valueOf(i2 == 1));
        } else if (i == 557852186) {
            this.mKeyParkLiveData.postValue(Boolean.valueOf(i2 == 1));
        } else if (i == 557849646) {
            this.mInductionLockEnable.postValue(Boolean.valueOf(i2 == 1));
        } else if (i == 557852183) {
            this.mKeyParkAdvancedLiveData.postValue(Boolean.valueOf(i2 == 1));
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IScuChangeListener
    public void onSignalLost(int i) {
        if (i == 557852186) {
            this.mKeyParkLiveData.postValue(false);
        } else if (i == 557852183) {
            this.mKeyParkAdvancedLiveData.postValue(false);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener
    public void onXpuApRemoteChange(int i) {
        Logs.d("xplaboratory onXpuApRemoteChange status:" + i);
        this.mCarCallAdvancedLiveData.postValue(Boolean.valueOf(i == 1));
    }

    public boolean getAutoPowerOff() {
        return this.mLaboratoryManager.isAutoPowerOff();
    }

    public void enableAutoPowerOff(boolean z) {
        this.mLaboratoryManager.enableAutoPowerOff(z);
    }

    public void refreshSoldierSwState() {
        int soldierSwState = this.mLaboratoryManager.getSoldierSwState();
        Logs.d("xplab -soldier status:" + soldierSwState);
        this.mSoldierStatus.postValue(Integer.valueOf(soldierSwState));
    }

    public int getSoldierSwState() {
        int soldierSwState = this.mLaboratoryManager.getSoldierSwState();
        Logs.d("xplab -soldier status sw:" + soldierSwState);
        return soldierSwState;
    }

    public void refreshSoldierCameraSwState() {
        boolean soldierCameraSw = this.mLaboratoryManager.getSoldierCameraSw();
        Logs.d("xplab -soldier camera:" + soldierCameraSw);
        this.mSoldierCameraStatus.postValue(Boolean.valueOf(soldierCameraSw));
    }

    public boolean getSoldierCameraSw() {
        return this.mLaboratoryManager.getSoldierCameraSw();
    }

    public void refreshSoldierCameraEnable() {
        boolean isSoldierCameraEnable = this.mLaboratoryManager.isSoldierCameraEnable();
        Logs.d("xplab -soldier enable1:" + isSoldierCameraEnable);
        this.mSoldierCameraEnable.postValue(Boolean.valueOf(isSoldierCameraEnable));
    }

    public boolean isSoldierCameraEnable() {
        boolean isSoldierCameraEnable = this.mLaboratoryManager.isSoldierCameraEnable();
        Logs.d("xplab -soldier enable:" + isSoldierCameraEnable);
        return isSoldierCameraEnable;
    }

    public void setSoldierCameraSw(boolean z) {
        this.mLaboratoryManager.setSoldierCameraSw(z);
    }

    public void setSoldierSw(int i) {
        this.mLaboratoryManager.setSoldierSw(i);
        this.mSoldierStatus.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener
    public void onSoldierCameraEnable(boolean z) {
        this.mSoldierCameraEnable.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onAutoPowerOffConfig(boolean z) {
        this.mAutoPowerOff.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener
    public void onSoldierSwState(int i) {
        this.mSoldierStatus.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener
    public void onSoldierCameraSwState(boolean z) {
        this.mSoldierCameraStatus.postValue(Boolean.valueOf(z));
    }

    public boolean isRemoteParkingEnable() {
        return this.mLaboratoryManager.isRemoteParkingEnable();
    }

    public void setRemoteParkingEnable(boolean z) {
        this.mLaboratoryManager.setRemoteParkingEnable(z);
    }

    public int getLowSpeedVolume() {
        return this.mLaboratoryManager.getLowSpeedVolume();
    }

    public void setLowSpeedVolume(int i) {
        this.mLaboratoryManager.setLowSpeedVolume(i);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.laboratory.LaboratoryManager.ConfigChangeListener
    public void onConfigChange(List<LaboratoryConfig> list) {
        this.mNetConfigValuesLiveData.postValue(this.mLaboratoryManager.getNetConfigValues());
    }

    public void setNearUnlockEnable(boolean z) {
        this.mLaboratoryManager.setNearUnlockEnable(z);
    }

    public boolean getNearUnlockEnable() {
        return this.mLaboratoryManager.getNearUnlockEnable();
    }

    public boolean getLeaveLockEnable() {
        return this.mLaboratoryManager.getLeaveLockEnable();
    }

    public void setLeaveLockEnable(boolean z) {
        this.mLaboratoryManager.setLeaveLockEnable(z);
    }

    public boolean isKeyParkingEnable() {
        return this.mLaboratoryManager.isKeyParkingEnable();
    }

    public void setKeyParkingEnable(boolean z) {
        this.mLaboratoryManager.setKeyParkingEnable(z);
    }

    public void setKAuto(boolean z) {
        this.mLaboratoryManager.setAutoParkSw(z);
    }

    public int getNedcSwitchStatus() {
        return this.mLaboratoryManager.getNedcSwitchStatus();
    }

    public void setRemoteParkingAdvancedEnable(boolean z) {
        this.mLaboratoryManager.setRemoteParkingAdvancedEnable(z);
    }

    public boolean isRemoteParkingAdvancedEnable() {
        return this.mLaboratoryManager.isRemoteParkingAdvancedEnable();
    }

    public boolean isParkByMemoryEnable() {
        return this.mLaboratoryManager.isParkByMemoryEnable();
    }

    public void setCarCallAdvancedEnable(boolean z) {
        this.mLaboratoryManager.setCarCallAdvancedEnable(z);
    }

    public boolean isCarCallAdvancedEnable() {
        return this.mLaboratoryManager.isCarCallAdvancedEnable();
    }

    private void registerUIChangeReceiver() {
        LocalBroadcastManager.getInstance(this.mApplication).registerReceiver(this.mUIChangeReceiver, new IntentFilter(LaboratoryManager.APP_USED_LIMITED_CHANGE));
    }

    private void unregisterUIChangeReceiver() {
        LocalBroadcastManager.getInstance(this.mApplication).unregisterReceiver(this.mUIChangeReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class UIChangeReceiver extends BroadcastReceiver {
        private UIChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("xpsound UIChangeReceiver sound action:" + action);
            if (LaboratoryManager.APP_USED_LIMITED_CHANGE.equals(action)) {
                LaboratoryViewModel.this.mAppLimitLiveData.postValue(true);
            }
        }
    }

    public boolean getAutoParkSwEnable() {
        return this.mLaboratoryManager.getAutoParkSwEnable();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IXpuChangeListener
    public void onXpuStatusChange(int i) {
        this.mNedcModeStatus.postValue(Integer.valueOf(i));
    }

    public boolean isInNedcMode() {
        return this.mLaboratoryManager.isInNedcMode();
    }

    public boolean isRearRowReminder() {
        return this.mLaboratoryManager.isRearRowReminder();
    }

    public void setRearRowReminder(boolean z) {
        this.mLaboratoryManager.setRearRowReminder(z);
    }

    public void setSayHiEffect(LluSayHiEffect lluSayHiEffect) {
        this.mLaboratoryManager.setSayHiEffect(lluSayHiEffect);
    }

    public int getLluSayHiEffect() {
        return this.mLaboratoryManager.getLluSayHiEffect();
    }

    public void setSayHiEnable(boolean z) {
        this.mLaboratoryManager.setSayHiEnable(z);
    }

    public boolean getSayHiEnable() {
        return this.mLaboratoryManager.getSayHiEnable();
    }

    public void setSayHiAvasEnable(boolean z) {
        this.mLaboratoryManager.setSayHiAvasEnable(z);
    }

    public boolean getSayHiAvasEnable() {
        return this.mLaboratoryManager.getSayHiAvasEnable();
    }
}
