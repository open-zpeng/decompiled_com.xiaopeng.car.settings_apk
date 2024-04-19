package com.xiaopeng.car.settings.service.work;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.service.work.WorkService;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.Arrays;
/* loaded from: classes.dex */
public class AutoPowerOffWork implements WorkService {
    private static final int MAX_SECOND = 600;
    private XDialog mXDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (Config.AUTO_POWER_OFF_ACTION.equals(action)) {
            Logs.d("xpservice power action:");
            int[] powerOffCountdown = AppServerManager.getInstance().getPowerOffCountdown();
            if (powerOffCountdown != null) {
                Logs.d("xpservice power off time:" + Arrays.toString(powerOffCountdown));
            }
            if (powerOffCountdown == null || powerOffCountdown.length <= 1) {
                return;
            }
            if (effectiveTimeCheck(powerOffCountdown[0], powerOffCountdown[1])) {
                if (this.mXDialog == null) {
                    context.setTheme(R.style.AppTheme);
                    this.mXDialog = new XDialog(context);
                    this.mXDialog.getDialog().setCancelable(false);
                    this.mXDialog.setSystemDialog(2008);
                    this.mXDialog.setMessage(R.string.laboratory_auto_power_off_dialog_message).setNegativeButton(context.getString(R.string.laboratory_auto_power_off_dialog_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settings.service.work.-$$Lambda$AutoPowerOffWork$V5CjjqjklutCovUiAGtkzirIp9c
                        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                        public final void onClick(XDialog xDialog, int i) {
                            AutoPowerOffWork.this.lambda$onStartCommand$0$AutoPowerOffWork(xDialog, i);
                        }
                    });
                    this.mXDialog.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.xiaopeng.car.settings.service.work.-$$Lambda$AutoPowerOffWork$rO64WvrfIKHZvyk7ALqCT1RkG40
                        @Override // android.content.DialogInterface.OnKeyListener
                        public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                            return AutoPowerOffWork.this.lambda$onStartCommand$1$AutoPowerOffWork(dialogInterface, i, keyEvent);
                        }
                    });
                    this.mXDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settings.service.work.-$$Lambda$AutoPowerOffWork$v_E8JeN_KMGc41mXqURhiIRDAXA
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            AppServerManager.getInstance().registerAutoPowerOffChangeListener();
                        }
                    });
                    this.mXDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settings.service.work.-$$Lambda$AutoPowerOffWork$bVmf2hMEJFRME6CUIgxhP9pIb-M
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            AutoPowerOffWork.lambda$onStartCommand$3(dialogInterface);
                        }
                    });
                }
                XDialog xDialog = this.mXDialog;
                if (xDialog != null) {
                    xDialog.setTitle(countDownString(powerOffCountdown[0], powerOffCountdown[1]));
                    this.mXDialog.show();
                }
                VuiManager.instance().initVuiDialog(this.mXDialog, context, VuiManager.SCENE_AUTO_POWER_OFF_DIALOG);
                return;
            }
            XDialog xDialog2 = this.mXDialog;
            if (xDialog2 != null) {
                xDialog2.dismiss();
            }
        } else if (Config.AUTO_POWER_OFF_CLOSE_ACTION.equals(action)) {
            onCancelAutoPowerOff();
        } else if (Config.AUTO_POWER_OFF_TIME_ACTION.equals(action)) {
            int[] intArrayExtra = intent.getIntArrayExtra(Config.EXTRA_AUTO_POWER_OFF);
            Logs.d("auto power off time:" + intArrayExtra.length);
            if (intArrayExtra == null || intArrayExtra.length <= 1) {
                return;
            }
            Logs.d("auto power off time:" + intArrayExtra[0] + " , " + intArrayExtra[1]);
            onAutoPowerOffCountdown(intArrayExtra[0], intArrayExtra[1]);
        }
    }

    public /* synthetic */ void lambda$onStartCommand$0$AutoPowerOffWork(XDialog xDialog, int i) {
        cancelPowerOff();
    }

    public /* synthetic */ boolean lambda$onStartCommand$1$AutoPowerOffWork(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 1015 || i == 66) {
            this.mXDialog.dismiss();
            cancelPowerOff();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onStartCommand$3(DialogInterface dialogInterface) {
        AppServerManager.getInstance().unregisterAutoPowerOffChangeListener();
        AppServerManager.getInstance().cancelTimer();
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mXDialog = null;
        }
        AppServerManager.getInstance().unregisterAutoPowerOffChangeListener();
        AppServerManager.getInstance().cancelTimer();
        Logs.d("autopower off onDestroy");
    }

    private void cancelPowerOff() {
        AppServerManager.getInstance().cancelPowerOff();
    }

    private String countDownString(int i) {
        return String.format("%s : %02d", Integer.valueOf(i / 60), Integer.valueOf(i % 60));
    }

    private String countDownString(int i, int i2) {
        return String.format("%s : %02d", Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void onAutoPowerOffCountdown(int i, int i2) {
        if (this.mXDialog != null) {
            if (effectiveTimeCheck(i, i2)) {
                this.mXDialog.setTitle(countDownString(i, i2));
            } else {
                this.mXDialog.dismiss();
            }
        }
    }

    private boolean effectiveTimeCheck(int i, int i2) {
        if (i == 63 && i2 == 63) {
            Logs.d("xpservice power off 3F:");
            return false;
        }
        int i3 = (i * 60) + i2;
        if (i3 > 600) {
            Logs.d("xpservice power off 超过10分钟:" + i3);
            return false;
        } else if (i == 0 && i2 == 0) {
            Logs.d("xpservice power off 倒计时结束:" + i + ":" + i2);
            return false;
        } else {
            return true;
        }
    }

    public void onCancelAutoPowerOff() {
        Logs.d("xpservice power off 取消下电");
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }
}
