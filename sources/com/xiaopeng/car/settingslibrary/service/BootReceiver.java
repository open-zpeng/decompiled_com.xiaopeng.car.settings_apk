package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.audioConfig.AudioConfig;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.service.work.LaboratoryWork;
/* loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Logs.d("xpreceiver action:" + action);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            CarSettingsApp.getBtBoxesUtil().connectBluetooth();
            XpAccountManager.getInstance().addAccountListener();
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.-$$Lambda$BootReceiver$kS_GA93HLlbJ09Nbrt-6dJy2mpE
                @Override // java.lang.Runnable
                public final void run() {
                    BootReceiver.lambda$onReceive$0(context);
                }
            }, 15000L);
            if (CarFunction.isSupportRemotePark()) {
                CarSettingsManager.getInstance().reStoreD21KeyPark(context);
            }
            if (CarConfigHelper.hasHifiSound()) {
                String preferenceForKeyString = GlobalSettingsSharedPreference.getPreferenceForKeyString(CarSettingsApp.getContext(), GlobalSettingsSharedPreference.DTS_DEFAULT_SET, "");
                Logs.d("xpsettings boot receiver dtsDefault:" + preferenceForKeyString + " currentVersion:1.0");
                if (TextUtils.isEmpty(preferenceForKeyString)) {
                    SoundManager.getInstance().setSoundEffectScene(1, 4, false);
                    SoundManager.getInstance().setSoundEffectMode(SoundManager.getInstance().getSoundEffectMode(), true);
                    GlobalSettingsSharedPreference.setPreferenceForKeyString(CarSettingsApp.getContext(), GlobalSettingsSharedPreference.DTS_DEFAULT_SET, String.valueOf(4));
                }
            }
            GlobalSettingsSharedPreference.setPreferenceForKeyString(CarSettingsApp.getContext(), GlobalSettingsSharedPreference.VERSION_NAME, "1.0");
            AudioConfig audioConfig = new AudioConfig(CarSettingsApp.getContext());
            int dangerousTtsVolLevel = audioConfig.getDangerousTtsVolLevel();
            int alarmVolume = CarSettingsManager.getInstance().getAlarmVolume();
            int convertDangerousTtsVolume = Utils.convertDangerousTtsVolume(alarmVolume);
            Logs.d("xpsettings boot receiver currentMeterVol:" + alarmVolume + " currentDangerousVol:" + dangerousTtsVolLevel + " ttsDangerousVol:" + convertDangerousTtsVolume);
            if (convertDangerousTtsVolume != dangerousTtsVolLevel) {
                audioConfig.setDangerousTtsVolLevel(convertDangerousTtsVolume);
                Logs.d("xpsettings boot receiver ttsVol:" + convertDangerousTtsVolume);
            }
            Logs.d("force day mode:false");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onReceive$0(Context context) {
        Intent intent = new Intent(context, GlobalService.class);
        intent.setAction(LaboratoryWork.ACTION);
        context.startService(intent);
        Logs.d("xpsettings configuration get data");
        if (CarFunction.isSupportXpAutoConnect()) {
            Intent intent2 = new Intent(CarSettingsApp.getContext(), GlobalService.class);
            intent2.setAction(Config.ACTION_CONNECT_XPAUTO);
            CarSettingsApp.getContext().startService(intent2);
        }
    }
}
