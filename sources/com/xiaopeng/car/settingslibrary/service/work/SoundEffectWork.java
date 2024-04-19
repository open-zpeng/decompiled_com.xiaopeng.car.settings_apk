package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView2;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew;
import com.xiaopeng.xui.app.XDialogPure;
import com.xiaopeng.xui.widget.XFrameLayout;
/* loaded from: classes.dex */
public class SoundEffectWork implements WorkService {
    private Context mContext;
    private long mShowTime;
    private XDialogPure mXDialog;

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$54daaKkLASJCRxe9tSZLNkLcwes
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectWork.this.lambda$onCreate$0$SoundEffectWork();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$onCreate$0$SoundEffectWork() {
        initDialog("onCreate");
    }

    private void initDialog(String str) {
        if (this.mXDialog == null) {
            Logs.d("SoundEffectWork initDialog " + str);
            this.mContext.setTheme(R.style.AppTheme);
            this.mXDialog = new XDialogPure(this.mContext);
            if (CarFunction.isSupportSharedDisplay()) {
                this.mXDialog.setSystemDialog(2008);
            } else {
                this.mXDialog.setSystemDialog(2048);
            }
            showDialogV1();
        }
    }

    private void showDialogV2() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sound_popup_effects_v2, (ViewGroup) null);
        this.mXDialog.setContentView(inflate);
        XFrameLayout xFrameLayout = (XFrameLayout) inflate.findViewById(R.id.soundEffectPopView);
        if (xFrameLayout instanceof SoundEffectPopHorizontalView2) {
            SoundEffectPopHorizontalView2 soundEffectPopHorizontalView2 = (SoundEffectPopHorizontalView2) xFrameLayout;
            soundEffectPopHorizontalView2.init(VuiManager.SCENE_SOUND_EFFECT_DIALOG);
            soundEffectPopHorizontalView2.setCloseClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$UCMEBKCBR_z-D9zn1Ucr8M_II0o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundEffectWork.this.lambda$showDialogV2$1$SoundEffectWork(view);
                }
            });
        }
        this.mXDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.SoundEffectWork.1
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                Logs.d("SoundEffectWork onShow " + (System.currentTimeMillis() - SoundEffectWork.this.mShowTime));
            }
        });
    }

    public /* synthetic */ void lambda$showDialogV2$1$SoundEffectWork(View view) {
        this.mXDialog.dismiss();
    }

    private void showDialogV1() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sound_popup_effects, (ViewGroup) null);
        this.mXDialog.setContentView(inflate);
        final XFrameLayout xFrameLayout = (XFrameLayout) inflate.findViewById(R.id.soundEffectPopView);
        if (xFrameLayout instanceof SoundEffectPopHorizontalViewNew) {
            final SoundEffectPopHorizontalViewNew soundEffectPopHorizontalViewNew = (SoundEffectPopHorizontalViewNew) xFrameLayout;
            soundEffectPopHorizontalViewNew.setSceneId(VuiManager.SCENE_SOUND_EFFECT_DIALOG);
            soundEffectPopHorizontalViewNew.setCloseClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$w9MEE5Sf17nMgo8fY74Qwr4miQU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundEffectWork.this.lambda$showDialogV1$2$SoundEffectWork(soundEffectPopHorizontalViewNew, view);
                }
            });
        } else if (xFrameLayout instanceof SoundEffectPopVerticalViewNew) {
            final SoundEffectPopVerticalViewNew soundEffectPopVerticalViewNew = (SoundEffectPopVerticalViewNew) xFrameLayout;
            soundEffectPopVerticalViewNew.setSceneId(VuiManager.SCENE_SOUND_EFFECT_DIALOG);
            soundEffectPopVerticalViewNew.setCloseClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$GW0XvRwHR01Kk7I4BPCsigVy1xk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundEffectWork.this.lambda$showDialogV1$3$SoundEffectWork(soundEffectPopVerticalViewNew, view);
                }
            });
        }
        this.mXDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$se0tKA8j3kk5JNrVZjNXB_0_X7U
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                SoundEffectWork.this.lambda$showDialogV1$4$SoundEffectWork(dialogInterface);
            }
        });
        this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$rbhQBg27juOrTMSecEXGCHmT59Q
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SoundEffectWork.lambda$showDialogV1$5(dialogInterface);
            }
        });
        this.mXDialog.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.xiaopeng.car.settingslibrary.service.work.-$$Lambda$SoundEffectWork$_A8h3lLazdOeGG4gIEyzc4XnFkk
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return SoundEffectWork.this.lambda$showDialogV1$6$SoundEffectWork(xFrameLayout, dialogInterface, i, keyEvent);
            }
        });
    }

    public /* synthetic */ void lambda$showDialogV1$2$SoundEffectWork(SoundEffectPopHorizontalViewNew soundEffectPopHorizontalViewNew, View view) {
        if (soundEffectPopHorizontalViewNew.isInEqualizer()) {
            soundEffectPopHorizontalViewNew.switchToEffect();
        } else {
            this.mXDialog.dismiss();
        }
    }

    public /* synthetic */ void lambda$showDialogV1$3$SoundEffectWork(SoundEffectPopVerticalViewNew soundEffectPopVerticalViewNew, View view) {
        closeSoundEffect(soundEffectPopVerticalViewNew);
    }

    public /* synthetic */ void lambda$showDialogV1$4$SoundEffectWork(DialogInterface dialogInterface) {
        Logs.d("SoundEffectWork onShow " + (System.currentTimeMillis() - this.mShowTime));
        AppServerManager.getInstance().addPopupDialogShowing(Config.SOUND_EFFECT_ACTION, this.mXDialog.getScreenId());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialogV1$5(DialogInterface dialogInterface) {
        Logs.d("SoundEffectWork onDismiss ");
        AppServerManager.getInstance().removePopupDialogShowing(Config.SOUND_EFFECT_ACTION);
    }

    public /* synthetic */ boolean lambda$showDialogV1$6$SoundEffectWork(XFrameLayout xFrameLayout, DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Logs.d("SoundEffectWork dialog " + i + " " + keyEvent.getAction());
        if (i == 4 && keyEvent.getAction() == 1) {
            if (xFrameLayout instanceof SoundEffectPopHorizontalViewNew) {
                closeSoundEffect((SoundEffectPopHorizontalViewNew) xFrameLayout);
                return true;
            } else if (xFrameLayout instanceof SoundEffectPopVerticalViewNew) {
                closeSoundEffect((SoundEffectPopVerticalViewNew) xFrameLayout);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void closeSoundEffect(SoundEffectPopHorizontalViewNew soundEffectPopHorizontalViewNew) {
        if (soundEffectPopHorizontalViewNew.isInEqualizer()) {
            soundEffectPopHorizontalViewNew.switchToEffect();
        } else {
            this.mXDialog.dismiss();
        }
    }

    private void closeSoundEffect(SoundEffectPopVerticalViewNew soundEffectPopVerticalViewNew) {
        if (soundEffectPopVerticalViewNew.isInEqualizer()) {
            soundEffectPopVerticalViewNew.switchToEffect();
        } else {
            this.mXDialog.dismiss();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
        XDialogPure xDialogPure;
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (Config.SOUND_EFFECT_ACTION.equals(action)) {
            int intExtra = intent.getIntExtra(Config.EXTRA_POPUP_SCREEN, 0);
            Logs.d("SoundEffectWork action " + intExtra);
            initDialog("onStartCommand");
            VuiManager.instance().initVuiDialog(this.mXDialog, context, VuiManager.SCENE_SOUND_EFFECT_DIALOG);
            XDialogPure xDialogPure2 = this.mXDialog;
            if (xDialogPure2 != null) {
                xDialogPure2.setScreenId(intExtra);
                this.mXDialog.show();
                this.mShowTime = System.currentTimeMillis();
            }
        } else if (!Config.SOUND_EFFECT_DISMISS_ACTION.equals(action) || (xDialogPure = this.mXDialog) == null) {
        } else {
            xDialogPure.dismiss();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        XDialogPure xDialogPure = this.mXDialog;
        if (xDialogPure != null) {
            xDialogPure.dismiss();
            this.mXDialog = null;
        }
        Logs.d("SoundEffectWork onDestroy");
    }
}
