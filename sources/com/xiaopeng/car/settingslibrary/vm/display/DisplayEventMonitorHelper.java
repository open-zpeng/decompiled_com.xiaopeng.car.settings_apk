package com.xiaopeng.car.settingslibrary.vm.display;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
/* loaded from: classes.dex */
public class DisplayEventMonitorHelper {
    private Context mContext;
    private DisplayEventMonitorListener mDisplayEventMonitorListener;
    private ContentObserver mDayNightObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if ((uri.equals(Settings.Secure.getUriFor(XPSettingsConfig.XP_THEME)) || uri.equals(Settings.Secure.getUriFor(XPSettingsConfig.XP_THEME_DB))) && DisplayEventMonitorHelper.this.mDisplayEventMonitorListener != null) {
                DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
            }
        }
    };
    private ContentObserver mScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mPsnScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.3
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mAutoScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.4
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mMainAutoScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.5
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mPsnAutoScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.6
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS_MODE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mIcmBrightnessCallbackObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.7
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS_CALLBACK)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mIcmBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.8
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mDarkBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.9
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mAutoBrightnessSyncObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.10
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.SCREEN_BRIGHTNESS_MODE_SYNC)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mDarkBrightnessAdjObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.11
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS_ADJ)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mFontScaleChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.12
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor("font_scale")) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mTimeFormatChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.13
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor("time_12_24")) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mAvasSpeakerChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.14
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.AVAS_LOUD_SPEAKER_SW)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mAppScreenFlowChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.15
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor("app_screen_flow")) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mMeterAutoBrightnessChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.16
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.METER_AUTO_BRIGHTNESS_MODE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mRearScreenBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.17
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_BRITHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mCMSAutoBrightnessObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.18
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.CMS_AUTO_BRITHTNESS)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mRearScreenControlObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.19
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CONTROL)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mRearScreenStateObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.20
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_STATE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mRearScreenAngleObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.21
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_ANGLE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };
    private ContentObserver mRearScreenAngleCallbackObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.vm.display.DisplayEventMonitorHelper.22
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (!uri.equals(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CALLBACK_ANGLE)) || DisplayEventMonitorHelper.this.mDisplayEventMonitorListener == null) {
                return;
            }
            DisplayEventMonitorHelper.this.mDisplayEventMonitorListener.onDisplayEventMonitorChange(uri);
        }
    };

    /* loaded from: classes.dex */
    public interface DisplayEventMonitorListener {
        void onDisplayEventMonitorChange(Uri uri);
    }

    public DisplayEventMonitorHelper(Context context, DisplayEventMonitorListener displayEventMonitorListener) {
        this.mContext = context;
        this.mDisplayEventMonitorListener = displayEventMonitorListener;
    }

    public void registerDayNightChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(XPSettingsConfig.XP_THEME), true, this.mDayNightObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(XPSettingsConfig.XP_THEME_DB), true, this.mDayNightObserver);
    }

    public void registerScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS), true, this.mScreenBrightnessObserver);
    }

    public void registerPsnScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS), true, this.mPsnScreenBrightnessObserver);
    }

    public void registerAutoScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE), true, this.mAutoScreenBrightnessObserver);
    }

    public void registerMainAutoScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.MAIN_SCREEN_BRIGHTNESS_MODE), true, this.mMainAutoScreenBrightnessObserver);
    }

    public void registerPsnAutoScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.PSN_SCREEN_BRIGHTNESS_MODE), true, this.mPsnAutoScreenBrightnessObserver);
    }

    public void registerIcmScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS), true, this.mIcmBrightnessObserver);
    }

    public void registerIcmBrightnessCallback() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.XP_ICM_BRIGHTNESS_CALLBACK), true, this.mIcmBrightnessCallbackObserver);
    }

    public void registerDarkBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS), true, this.mDarkBrightnessObserver);
    }

    public void registerAutoBrightnessSync() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.SCREEN_BRIGHTNESS_MODE_SYNC), true, this.mAutoBrightnessSyncObserver);
    }

    public void unRegisterAutoBrightnessSync() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAutoBrightnessSyncObserver);
    }

    public void unRegisterDarkBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mDarkBrightnessObserver);
    }

    public void unRegisterIcmBrightnessCallback() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mIcmBrightnessCallbackObserver);
    }

    public void unRegisterIcmScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mIcmBrightnessObserver);
    }

    public void unRegisterScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mScreenBrightnessObserver);
    }

    public void unRegisterPsnScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mPsnScreenBrightnessObserver);
    }

    public void unRegisterDayNightObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mDayNightObserver);
    }

    public void unRegisterAutoScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAutoScreenBrightnessObserver);
    }

    public void unRegisterMainAutoScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mMainAutoScreenBrightnessObserver);
    }

    public void unRegisterPsnAutoScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mPsnAutoScreenBrightnessObserver);
    }

    public void registerDarkBrightnessAdj() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.XP_DARK_STATE_BRIGHTNESS_ADJ), true, this.mDarkBrightnessAdjObserver);
    }

    public void unRegisterDarkBrightnessAdj() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mDarkBrightnessAdjObserver);
    }

    public void registerFontScaleChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("font_scale"), true, this.mFontScaleChangeObserver);
    }

    public void unregisterFontScaleChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mFontScaleChangeObserver);
    }

    public void registerTimeFormatChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), true, this.mTimeFormatChangeObserver);
    }

    public void unregisterTimeFormatChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mTimeFormatChangeObserver);
    }

    public void registerAvasSpeakerChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.AVAS_LOUD_SPEAKER_SW), true, this.mAvasSpeakerChangeObserver);
    }

    public void unregisterAvasSpeakerChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAvasSpeakerChangeObserver);
    }

    public void registerAppScreenFlowChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("app_screen_flow"), true, this.mAppScreenFlowChangeObserver);
    }

    public void unregisterAppScreenFlowChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAppScreenFlowChangeObserver);
    }

    public void registerMeterAutoBrightnessChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.METER_AUTO_BRIGHTNESS_MODE), true, this.mMeterAutoBrightnessChangeObserver);
    }

    public void unregisterMeterAutoBrightnessChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mMeterAutoBrightnessChangeObserver);
    }

    public void registerRearScreenBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_BRITHTNESS), true, this.mRearScreenBrightnessObserver);
    }

    public void unregisterRearScreenBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRearScreenBrightnessObserver);
    }

    public void registerCMSAutoBrightness() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.CMS_AUTO_BRITHTNESS), true, this.mCMSAutoBrightnessObserver);
    }

    public void unregisterCMSAutoBrightness() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mCMSAutoBrightnessObserver);
    }

    public void registerRearScreenSwitch() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CONTROL), true, this.mRearScreenControlObserver);
    }

    public void unregisterRearScreenSwitch() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRearScreenControlObserver);
    }

    public void registerRearScreenState() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_STATE), true, this.mRearScreenStateObserver);
    }

    public void unregisterRearScreenState() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRearScreenStateObserver);
    }

    public void registerRearScreenAngle() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_ANGLE), true, this.mRearScreenAngleObserver);
    }

    public void unregisterRearScreenAngle() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRearScreenAngleObserver);
    }

    public void registerRearScreenAngleCallback() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(XPSettingsConfig.REAR_SCREEN_CALLBACK_ANGLE), true, this.mRearScreenAngleCallbackObserver);
    }

    public void unregisterRearScreenAngleCallback() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRearScreenAngleCallbackObserver);
    }
}
