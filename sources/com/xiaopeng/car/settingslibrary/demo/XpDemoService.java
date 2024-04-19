package com.xiaopeng.car.settingslibrary.demo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.develop.SystemPropPoker;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.manager.speech.SpeechCallbackListener;
import com.xiaopeng.car.settingslibrary.manager.wifi.WifiUtils;
import com.xiaopeng.car.settingslibrary.manager.wifi.XpAutoManualManager;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.car.settingslibrary.service.GlobalService;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.WifiKeyWork;
import com.xiaopeng.lib.framework.configuration.ConfigurationDataImpl;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.ConfigurationChangeEvent;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes.dex */
public class XpDemoService extends Service {
    private static final String TAG = "XpDemoService";
    private BroadcastReceiver mPopupReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("index", 0);
            AppServerManager.getInstance().startPopDialog(intExtra != 0 ? intExtra != 1 ? intExtra != 2 ? intExtra != 3 ? intExtra != 4 ? intExtra != 5 ? "" : "externalBluetooth" : "usb" : "download" : "psnBluetooth" : "wifi" : "bluetooth", intent.getIntExtra("screen", 0));
        }
    };
    private BroadcastReceiver mSpeechReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SpeechCallbackListener.getInstance().onHeadsetMode(intent.getIntExtra("index", 0), -1);
        }
    };
    private BroadcastReceiver mDevelopReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.3
        private boolean isEnable;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("layout".equals(intent.getStringExtra("item"))) {
                this.isEnable = !this.isEnable;
                SystemProperties.set(ReflectUtils.getStringValue("DEBUG_LAYOUT_PROPERTY", View.class), this.isEnable ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE);
                SystemPropPoker.getInstance().poke();
            }
        }
    };
    private BroadcastReceiver mFontReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.4
        private boolean isEnable;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            this.isEnable = !this.isEnable;
            Settings.System.putFloat(XpDemoService.this.getApplication().getContentResolver(), "font_scale", this.isEnable ? 1.3f : 0.8f);
        }
    };
    private BroadcastReceiver mLaboratoryConfigReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra("key");
            String stringExtra2 = intent.getStringExtra("value");
            ConfigurationChangeEvent configurationChangeEvent = new ConfigurationChangeEvent();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ConfigurationDataImpl(stringExtra, stringExtra2));
            configurationChangeEvent.setChangeList(arrayList);
            EventBus.getDefault().post(configurationChangeEvent);
        }
    };
    private BroadcastReceiver mCbReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.6
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent("android.karaoke.recommend.CONNECT_ACTION");
            intent2.addFlags(16777216);
            context.sendBroadcast(intent2);
        }
    };
    private BroadcastReceiver mSoundEffectReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.7
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent(CarSettingsApp.getContext(), UIGlobalService.class);
            intent2.setAction(Config.SOUND_EFFECT_ACTION);
            CarSettingsApp.getContext().startService(intent2);
        }
    };
    private BroadcastReceiver mSoundHeadsetReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.8
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SoundManager.getInstance().setMainDriverExclusive(intent.getIntExtra("index", 0), true, true, true);
        }
    };
    private BroadcastReceiver mWifiKeyReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.9
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (CarFunction.isSupportWifiKey()) {
                Intent intent2 = new Intent(context, GlobalService.class);
                intent2.setAction(WifiKeyWork.ACTION);
                XpDemoService.this.startService(intent2);
            }
        }
    };
    private BroadcastReceiver mReflectReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.10
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            WifiUtils.printReflect();
        }
    };
    private BroadcastReceiver mHeadrestReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.11
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("index", 0);
            Intent intent2 = new Intent(context, GlobalService.class);
            if (intExtra == 0) {
                intent2.setAction(Config.HEADREST_ACTION_SWITCH);
            } else {
                intent2.setAction(Config.HEADREST_ACTION_RESTORE);
            }
            context.startService(intent2);
        }
    };
    private BroadcastReceiver mAuthModeReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.12
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XpDemoService.this.log("mAuthModeReceiver ");
            int intExtra = intent.getIntExtra("index", 0);
            XpDemoService xpDemoService = XpDemoService.this;
            xpDemoService.log("mAuthModeReceiver " + intExtra);
            if (intExtra == 0) {
                Intent intent2 = new Intent(Config.AUTH_MODE_ACTION);
                intent2.putExtra(Config.AUTH_MODE_EXTRA_KEY, "12.01");
                intent2.addFlags(16777216);
                context.sendBroadcast(intent2);
                return;
            }
            Intent intent3 = new Intent(Config.AUTH_MODE_ACTION_CLOSE);
            intent3.addFlags(16777216);
            context.sendBroadcast(intent3);
        }
    };
    private BroadcastReceiver mUrgentReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.13
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent(context, GlobalService.class);
            intent2.setAction(Config.EMERGENCY_IG_OFFACTION);
            context.startService(intent2);
        }
    };
    private BroadcastReceiver mRepairReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.14
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent(context, GlobalService.class);
            intent2.setAction(Config.REPAIR_REQUEST_QUIT_ACTION);
            context.startService(intent2);
        }
    };
    private BroadcastReceiver mStorageInitReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.15
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GlobalSettingsSharedPreference.setPreferenceForKeyLong(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_TIME, 0L);
        }
    };
    private BroadcastReceiver mClearXpAutoReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpDemoService.16
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XpAutoManualManager.getInstance().clearAuto();
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        log("onCreate");
        registerReceiver(this.mDevelopReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_DEVELOP"));
        registerReceiver(this.mSpeechReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_SPEECH"));
        registerReceiver(this.mPopupReceiver, new IntentFilter("com.xiaopeng.settings.action.POPUP_DROPDOWN_DIALOG"));
        registerReceiver(this.mFontReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_FONT"));
        registerReceiver(this.mCbReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_CB"));
        registerReceiver(this.mLaboratoryConfigReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_LABORATORY_CONFIG"));
        registerReceiver(this.mSoundEffectReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_SOUND_EFFECT"));
        registerReceiver(this.mSoundHeadsetReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_SOUND_HEADSET"));
        registerReceiver(this.mWifiKeyReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_WIFI_KEY"));
        registerReceiver(this.mReflectReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_WIFI_REFLECT"));
        registerReceiver(this.mHeadrestReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_HEADREST"));
        registerReceiver(this.mAuthModeReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_OPEN_AUTH_MODE_DIALOG"));
        registerReceiver(this.mUrgentReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_URGENT_POWER"));
        registerReceiver(this.mRepairReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_REPAIR"));
        registerReceiver(this.mStorageInitReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_STORAGE_INIT"));
        registerReceiver(this.mClearXpAutoReceiver, new IntentFilter("com.xiaopeng.settings.action.CLEAR_XPAUTO"));
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
        unregisterReceiver(this.mDevelopReceiver);
        unregisterReceiver(this.mSpeechReceiver);
        unregisterReceiver(this.mPopupReceiver);
        unregisterReceiver(this.mFontReceiver);
        unregisterReceiver(this.mCbReceiver);
        unregisterReceiver(this.mLaboratoryConfigReceiver);
        unregisterReceiver(this.mSoundEffectReceiver);
        unregisterReceiver(this.mSoundHeadsetReceiver);
        unregisterReceiver(this.mWifiKeyReceiver);
        unregisterReceiver(this.mReflectReceiver);
        unregisterReceiver(this.mHeadrestReceiver);
        unregisterReceiver(this.mAuthModeReceiver);
        unregisterReceiver(this.mUrgentReceiver);
        unregisterReceiver(this.mRepairReceiver);
        unregisterReceiver(this.mClearXpAutoReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        Log.i(TAG, str);
    }
}
