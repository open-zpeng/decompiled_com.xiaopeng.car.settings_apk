package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
/* loaded from: classes.dex */
public class HeadrestSwitchWork implements WorkService {
    private Handler mHandler = new Handler();
    private Runnable mRestoreRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$HeadrestSwitchWork$ZT1WCk7IxsD1Pq_owUM8NqbbnqM
        @Override // java.lang.Runnable
        public final void run() {
            HeadrestSwitchWork.this.restore();
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restore() {
        int intelligentHeadrestRestore = GlobalSettingsSharedPreference.getIntelligentHeadrestRestore(CarSettingsApp.getContext());
        int mainDriverExclusive = SoundManager.getInstance().getMainDriverExclusive();
        Logs.d("HeadrestSwitchWork restore type:" + intelligentHeadrestRestore + " realType:" + mainDriverExclusive);
        if (intelligentHeadrestRestore == -1 || mainDriverExclusive != 1 || intelligentHeadrestRestore == 1) {
            return;
        }
        SoundManager.getInstance().setMainDriverExclusive(intelligentHeadrestRestore, true, false, true);
        AppServerManager.getInstance().onPopupToast(getRestoreTips(intelligentHeadrestRestore));
    }

    private String getRestoreTips(int i) {
        return CarFunction.isNonSelfPageUI() ? String.format(CarSettingsApp.getContext().getString(R.string.sound_switch_to_restore_headrest_1), getHeadrestString(i)) : String.format(CarSettingsApp.getContext().getString(R.string.sound_switch_to_restore_headrest), getHeadrestString(i));
    }

    private String getSwitchTips() {
        if (CarFunction.isNonSelfPageUI()) {
            return CarSettingsApp.getContext().getString(R.string.sound_switch_to_driver_headrest_1);
        }
        return CarSettingsApp.getContext().getString(R.string.sound_switch_to_driver_headrest);
    }

    private String getHeadrestString(int i) {
        if (i != 0) {
            if (i != 1) {
                return i != 2 ? "" : CarSettingsApp.getContext().getString(R.string.sound_headrest_audio_mode_headrest_title);
            }
            return CarSettingsApp.getContext().getString(R.string.sound_headrest_audio_mode_drive_title);
        }
        return CarSettingsApp.getContext().getString(R.string.sound_headrest_audio_mode_off_title);
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (Config.HEADREST_ACTION_SWITCH.equals(action)) {
            boolean settingProvider = DataRepository.getInstance().getSettingProvider(context, XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, false);
            Logs.d("HeadrestSwitch ACTION_SWITCH isSwitch:" + settingProvider);
            if (settingProvider) {
                this.mHandler.removeCallbacks(this.mRestoreRunnable);
                int mainDriverExclusive = SoundManager.getInstance().getMainDriverExclusive();
                Logs.d("HeadrestSwitch ACTION_SWITCH realType:" + mainDriverExclusive);
                if (mainDriverExclusive != 1) {
                    SoundManager.getInstance().setMainDriverExclusive(1, true, false, true);
                    AppServerManager.getInstance().onPopupToast(getSwitchTips());
                }
            }
        }
        if (Config.HEADREST_ACTION_RESTORE.equals(action)) {
            Logs.d("HeadrestSwitch ACTION_RESTORE");
            boolean settingProvider2 = DataRepository.getInstance().getSettingProvider(context, XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, false);
            Logs.d("HeadrestSwitch ACTION_RESTORE isSwitch:" + settingProvider2);
            if (settingProvider2) {
                this.mHandler.removeCallbacks(this.mRestoreRunnable);
                this.mHandler.post(this.mRestoreRunnable);
            }
        }
    }
}
