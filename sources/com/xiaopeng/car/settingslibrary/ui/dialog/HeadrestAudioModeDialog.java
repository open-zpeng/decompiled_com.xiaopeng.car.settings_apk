package com.xiaopeng.car.settingslibrary.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.XPSettingsConfig;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.sound.HeadrestPlayEffectHelper;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class HeadrestAudioModeDialog implements CompoundButton.OnCheckedChangeListener {
    private Context mContext;
    private XSwitch mDeputyIntelligentSwitch;
    private HeadrestPlayEffectHelper mHeadrestPlayEffectHelper = new HeadrestPlayEffectHelper();
    private OnHeadRestAudioSetListener mOnHeadRestAudioSetListener;
    private View mViewModeDrive;
    private View mViewModeDriveBackRow;
    private View mViewModeDriveBackRowBtn;
    private View mViewModeDriveBackRowTips;
    private View mViewModeDriveBtn;
    private View mViewModeDriveTips;
    private View mViewModeOff;
    private View mViewModeOffBtn;
    private View mViewModeOffTips;
    private XDialog mXDialog;

    /* loaded from: classes.dex */
    public interface OnHeadRestAudioSetListener {
        void OnHeadRestAudioSet(int i);
    }

    public HeadrestAudioModeDialog(Context context, OnHeadRestAudioSetListener onHeadRestAudioSetListener) {
        this.mContext = context;
        this.mOnHeadRestAudioSetListener = onHeadRestAudioSetListener;
    }

    private void createDialog() {
        Context context;
        if (this.mXDialog != null || (context = this.mContext) == null) {
            return;
        }
        this.mXDialog = new XDialog(context, R.style.XDialogView_Large_HeadrestAudioMode);
        this.mXDialog.setTitle(R.string.sound_subtitle_headrest_audio_mode).setCustomView(R.layout.layout_headrest_audio_mode, false).setCloseVisibility(true);
        this.mViewModeOff = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_01);
        this.mViewModeDrive = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_02);
        this.mViewModeDriveBackRow = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_03);
        this.mViewModeOffBtn = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_01_btn);
        this.mViewModeDriveBtn = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_02_btn);
        this.mViewModeDriveBackRowBtn = this.mXDialog.getContentView().findViewById(R.id.headrest_mode_03_btn);
        this.mViewModeOffTips = this.mXDialog.getContentView().findViewById(R.id.mode1_tips);
        this.mViewModeDriveTips = this.mXDialog.getContentView().findViewById(R.id.mode2_tips);
        this.mViewModeDriveBackRowTips = this.mXDialog.getContentView().findViewById(R.id.mode3_tips);
        this.mDeputyIntelligentSwitch = (XSwitch) this.mXDialog.getContentView().findViewById(R.id.sound_headrest_intelligent_switching).findViewById(R.id.x_list_action_switch);
        this.mDeputyIntelligentSwitch.setOnCheckedChangeListener(this);
        this.mDeputyIntelligentSwitch.setChecked(DataRepository.getInstance().getSettingProvider(this.mContext, XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, false));
        this.mDeputyIntelligentSwitch.setVuiLabel(this.mContext.getString(R.string.sound_headrest_intelligent_switching));
        this.mViewModeOff.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$HeadrestAudioModeDialog$As69ZOoRC6tyA8HBXBjO4FQo2Xc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HeadrestAudioModeDialog.this.lambda$createDialog$0$HeadrestAudioModeDialog(view);
            }
        });
        this.mViewModeDrive.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$HeadrestAudioModeDialog$WXvacGlgbvICrQoFA09MNkkIYd0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HeadrestAudioModeDialog.this.lambda$createDialog$1$HeadrestAudioModeDialog(view);
            }
        });
        this.mViewModeDriveBackRow.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$HeadrestAudioModeDialog$bYbs1dc5KIVNLaFo_U8yChEpObg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HeadrestAudioModeDialog.this.lambda$createDialog$2$HeadrestAudioModeDialog(view);
            }
        });
        this.mXDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.HeadrestAudioModeDialog.1
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                HeadrestAudioModeDialog.this.mHeadrestPlayEffectHelper.createSpeech();
            }
        });
        this.mXDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.HeadrestAudioModeDialog.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                HeadrestAudioModeDialog.this.mHeadrestPlayEffectHelper.destroySpeech();
            }
        });
    }

    public /* synthetic */ void lambda$createDialog$0$HeadrestAudioModeDialog(View view) {
        refreshUIMode(0, true);
    }

    public /* synthetic */ void lambda$createDialog$1$HeadrestAudioModeDialog(View view) {
        refreshUIMode(1, true);
    }

    public /* synthetic */ void lambda$createDialog$2$HeadrestAudioModeDialog(View view) {
        refreshUIMode(2, true);
    }

    public void refreshUIMode(int i) {
        refreshUIMode(i, false);
    }

    private void refreshUIMode(int i, boolean z) {
        String string;
        Logs.d("xpsound headrest dialog refreshUIMode mode: " + i + " fromUser " + z);
        this.mViewModeOff.setSelected(false);
        this.mViewModeDrive.setSelected(false);
        this.mViewModeDriveBackRow.setSelected(false);
        this.mViewModeOff.setBackgroundResource(0);
        this.mViewModeDrive.setBackgroundResource(0);
        this.mViewModeDriveBackRow.setBackgroundResource(0);
        this.mViewModeOffBtn.setVisibility(0);
        this.mViewModeDriveBtn.setVisibility(0);
        this.mViewModeDriveBackRowBtn.setVisibility(0);
        this.mViewModeOffTips.setVisibility(8);
        this.mViewModeDriveTips.setVisibility(8);
        this.mViewModeDriveBackRowTips.setVisibility(8);
        if (i == 0) {
            this.mViewModeOff.setSelected(true);
            this.mViewModeOffBtn.setVisibility(8);
            this.mViewModeOffTips.setVisibility(0);
            this.mViewModeOff.setBackgroundResource(R.drawable.x_list_item_selector);
            if (z) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B010", "type", 0);
            }
        } else if (i == 1) {
            this.mViewModeDrive.setSelected(true);
            this.mViewModeDriveBtn.setVisibility(8);
            this.mViewModeDriveTips.setVisibility(0);
            this.mViewModeDrive.setBackgroundResource(R.drawable.x_list_item_selector);
            if (z) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B010", "type", 1);
            }
        } else if (i == 2) {
            this.mViewModeDriveBackRow.setSelected(true);
            this.mViewModeDriveBackRowBtn.setVisibility(8);
            this.mViewModeDriveBackRowTips.setVisibility(0);
            this.mViewModeDriveBackRow.setBackgroundResource(R.drawable.x_list_item_selector);
            if (z) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B010", "type", 2);
            }
        }
        if (!z || i == SoundManager.getInstance().getMainDriverMode()) {
            return;
        }
        this.mOnHeadRestAudioSetListener.OnHeadRestAudioSet(i);
        if (i == 0) {
            string = this.mContext.getString(R.string.sound_headrest_audio_mode_off_msg);
        } else if (i == 1) {
            string = this.mContext.getString(R.string.sound_headrest_audio_mode_drive_msg);
        } else {
            string = i != 2 ? "" : this.mContext.getString(R.string.sound_headrest_audio_mode_headrest_msg);
        }
        this.mHeadrestPlayEffectHelper.playEffect(string);
    }

    public void show(int i) {
        createDialog();
        this.mXDialog.show();
        refreshUIMode(i);
        VuiManager.instance().initVuiDialog(this.mXDialog, this.mContext, VuiManager.SCENE_SOUND_HEADREST_DIALOG);
        Logs.d("xpsound headrest dialog show mode: " + i);
    }

    public void dismiss() {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }

    public boolean isShowing() {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            return xDialog.getDialog().isShowing();
        }
        return false;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        VuiManager.instance().updateVuiScene(VuiManager.SCENE_SOUND_HEADREST_DIALOG, this.mContext, compoundButton);
        if ((((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) && compoundButton == this.mDeputyIntelligentSwitch) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B002", z);
            DataRepository.getInstance().setSettingProvider(this.mContext, XPSettingsConfig.XP_HEADREST_INTELLIGENT_SWITCH, z);
            this.mOnHeadRestAudioSetListener.OnHeadRestAudioSet(SoundManager.getInstance().getMainDriverExclusive());
        }
    }
}
