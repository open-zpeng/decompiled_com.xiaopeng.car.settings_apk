package com.xiaopeng.car.settingslibrary.service.work;

import android.os.CountDownTimer;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener;
/* loaded from: classes.dex */
public class AutoPowerOffAdapter implements ITboxChangeListener, IMcuChangeListener {
    private static final int COUNTDOWN_TIME = 600000;
    private static final boolean IS_MCU = CarFunction.isSupportMcuAutoPower();
    private IAutoPowerOffChangeListener mAutoPowerOffChangeListener;
    private long mCurrentTime = 600000;
    private CountDownTimer mTimer;

    /* loaded from: classes.dex */
    public interface IAutoPowerOffChangeListener {
        void onAutoPowerOffCountdown(int i, int i2);

        void onCancelAutoPowerOff();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onAutoPowerOffConfig(boolean z) {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final AutoPowerOffAdapter instance = new AutoPowerOffAdapter();

        private InnerFactory() {
        }
    }

    public static AutoPowerOffAdapter getInstance() {
        return InnerFactory.instance;
    }

    public void registerCarListener(IAutoPowerOffChangeListener iAutoPowerOffChangeListener) {
        this.mAutoPowerOffChangeListener = iAutoPowerOffChangeListener;
        if (IS_MCU) {
            CarSettingsManager.getInstance().registerMcuAutoPower(this);
        } else {
            CarSettingsManager.getInstance().registerAutoPower(this);
        }
    }

    public void unregisterCarListener() {
        this.mAutoPowerOffChangeListener = null;
        if (IS_MCU) {
            CarSettingsManager.getInstance().unregisterMcuAutoPower(this);
        } else {
            CarSettingsManager.getInstance().unregisterAutoPower(this);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener
    public void onAutoPowerOffCountdown(final int i, final int i2) {
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$AutoPowerOffAdapter$Bp3FBVxrZuCnP9nY6b72PIw909s
            @Override // java.lang.Runnable
            public final void run() {
                AutoPowerOffAdapter.this.lambda$onAutoPowerOffCountdown$0$AutoPowerOffAdapter(i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$onAutoPowerOffCountdown$0$AutoPowerOffAdapter(int i, int i2) {
        IAutoPowerOffChangeListener iAutoPowerOffChangeListener = this.mAutoPowerOffChangeListener;
        if (iAutoPowerOffChangeListener != null) {
            iAutoPowerOffChangeListener.onAutoPowerOffCountdown(i, i2);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onAutoPowerOffCountdown() {
        if (IS_MCU) {
            Logs.d("autopoweroff start!!!");
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.car.ITboxChangeListener, com.xiaopeng.car.settingslibrary.manager.car.IMcuChangeListener
    public void onCancelAutoPowerOff() {
        Logs.d("autopower off onCancelAutoPowerOff");
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$AutoPowerOffAdapter$CP3GXcOkbQS_JFUB2YrjhkW4zDI
            @Override // java.lang.Runnable
            public final void run() {
                AutoPowerOffAdapter.this.lambda$onCancelAutoPowerOff$1$AutoPowerOffAdapter();
            }
        });
    }

    public /* synthetic */ void lambda$onCancelAutoPowerOff$1$AutoPowerOffAdapter() {
        cancelTimer();
        IAutoPowerOffChangeListener iAutoPowerOffChangeListener = this.mAutoPowerOffChangeListener;
        if (iAutoPowerOffChangeListener != null) {
            iAutoPowerOffChangeListener.onCancelAutoPowerOff();
        }
    }

    public int[] getPowerOffCountdown() {
        int[] iArr = new int[2];
        if (IS_MCU) {
            if (getTimer() != null) {
                Logs.d("autopower cancel and start");
                getTimer().cancel();
                getTimer().start();
            }
            iArr[0] = 10;
            iArr[1] = 0;
            return iArr;
        }
        return CarSettingsManager.getInstance().getPowerOffCountdown();
    }

    public void cancelPowerOffConfig() {
        if (IS_MCU) {
            CarSettingsManager.getInstance().setPowerOffCountdownAction(true);
        } else {
            CarSettingsManager.getInstance().cancelPowerOffConfig();
        }
        Logs.d("autopoweroff cancel");
    }

    public void cancelTimer() {
        if (getTimer() != null) {
            Logs.d("autopower cancelTimer");
            getTimer().cancel();
        }
    }

    private synchronized CountDownTimer getTimer() {
        if (this.mTimer == null) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.AutoPowerOffAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    AutoPowerOffAdapter.this.mTimer = new CountDownTimer(600000L, 1000L) { // from class: com.xiaopeng.car.settingslibrary.service.work.AutoPowerOffAdapter.1.1
                        @Override // android.os.CountDownTimer
                        public void onTick(long j) {
                            AutoPowerOffAdapter.this.mCurrentTime = j;
                            if (AutoPowerOffAdapter.this.mAutoPowerOffChangeListener != null) {
                                int i = (int) ((AutoPowerOffAdapter.this.mCurrentTime / 1000) / 60);
                                int i2 = (int) ((AutoPowerOffAdapter.this.mCurrentTime / 1000) % 60);
                                Logs.d("autopower minute:" + i + " second:" + i2);
                                AutoPowerOffAdapter.this.mAutoPowerOffChangeListener.onAutoPowerOffCountdown(i, i2);
                                if (i == 0 && i2 == 0) {
                                    CarSettingsManager.getInstance().setPowerOffCountdownAction(false);
                                }
                            }
                        }

                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            if (AutoPowerOffAdapter.IS_MCU) {
                                Logs.d("autopoweroff done!!");
                                cancel();
                            }
                        }
                    };
                }
            });
        }
        return this.mTimer;
    }
}
