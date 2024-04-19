package com.xiaopeng.car.settingslibrary.manager.display;

import android.content.Context;
import android.os.PowerManager;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.LocaleUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import java.util.Locale;
/* loaded from: classes.dex */
public class XpDisplayManager {
    private static final String TAG = "XpDisplayManager";
    private static volatile XpDisplayManager sInstance;
    CarSettingsManager mCarManager = CarSettingsManager.getInstance();
    XuiSettingsManager mXuiManager = XuiSettingsManager.getInstance();
    DataRepository mDataRepository = DataRepository.getInstance();
    final int[] mCentralIntervals = new int[100];

    public static XpDisplayManager getInstance() {
        if (sInstance == null) {
            synchronized (XpDisplayManager.class) {
                if (sInstance == null) {
                    sInstance = new XpDisplayManager();
                }
            }
        }
        return sInstance;
    }

    private XpDisplayManager() {
        initCentralIntervals();
    }

    private void initCentralIntervals() {
        int i = 0;
        while (true) {
            int[] iArr = this.mCentralIntervals;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = ((int) (i * 2.5656567f)) + 1;
            i++;
        }
    }

    public int getRealBrightnessByUI(int i) {
        if (i >= 100) {
            return 255;
        }
        if (i <= 1) {
            return 1;
        }
        return this.mCentralIntervals[i - 1];
    }

    public int getUIProgressByReal(int i) {
        int i2 = 1;
        if (i <= 1) {
            return 1;
        }
        if (i >= 255) {
            return 100;
        }
        while (true) {
            int[] iArr = this.mCentralIntervals;
            if (i2 >= iArr.length) {
                return 100;
            }
            if (i < iArr[i2]) {
                return i2;
            }
            i2++;
        }
    }

    public void setMeterBrightness(Context context, int i) {
        this.mDataRepository.setIcmBrightness(context, i);
    }

    public int getMeterBrightness(Context context) {
        return this.mDataRepository.getIcmBrightness(context);
    }

    public int getNedcSwitchStatus() {
        return this.mCarManager.getNedcSwitchStatus();
    }

    public String requestEnterUserScenario(String str, int i) {
        return this.mXuiManager.requestEnterUserScenario(str, i);
    }

    public String requestExitUserScenario(String str) {
        return this.mXuiManager.requestExitUserScenario(str);
    }

    public String getCurrentLanguage() {
        return LocaleUtils.getCurrentLanguageName();
    }

    public void setLanguage(final String str) {
        final PowerManager powerManager = (PowerManager) CarSettingsApp.getContext().getSystemService("power");
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.display.-$$Lambda$XpDisplayManager$O8hPTMDVkQ3aSdrbYMrThvAwH-c
            @Override // java.lang.Runnable
            public final void run() {
                XpDisplayManager.lambda$setLanguage$1(str, powerManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setLanguage$1(String str, final PowerManager powerManager) {
        LocaleUtils.setLanguage(str);
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.display.-$$Lambda$XpDisplayManager$oQqdg3RWeZ35kcEJcWHy4GtpSTA
            @Override // java.lang.Runnable
            public final void run() {
                powerManager.reboot("");
            }
        }, 100L);
    }

    public String getCurrentRegion() {
        return LocaleUtils.getCountryCode();
    }

    public void setRegion(final String str) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.manager.display.-$$Lambda$XpDisplayManager$BXkbWQySCaL3Jf8ZhXAvdvHEsSo
            @Override // java.lang.Runnable
            public final void run() {
                LocaleUtils.setLocale(str);
            }
        });
    }

    public String getDateSample(String str) {
        return LocaleUtils.getDateSample(new Locale(Locale.getDefault().getLanguage(), str));
    }

    public String getNumSample(String str) {
        return LocaleUtils.getNumberSample(new Locale(Locale.getDefault().getLanguage(), str));
    }

    public void setRearScreenLock(boolean z, boolean z2) {
        if (z2) {
            XpAccountManager.getInstance().saveRearScreenLockEnable(z);
        }
        this.mDataRepository.setSettingProvider(CarSettingsApp.getContext(), XPSettingsConfig.REAR_SCREEN_LOCK, z);
    }

    public boolean getRearScreenLock() {
        return XpAccountManager.getInstance().getScreenChildLock();
    }
}
