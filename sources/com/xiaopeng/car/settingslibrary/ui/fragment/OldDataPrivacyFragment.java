package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.MicUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class OldDataPrivacyFragment extends BaseFragment {
    public static final int ACCEPT = 1;
    public static final String ACTION_CANCEL_MICROPHONE_UNMUTE = "com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_UNMUTE";
    public static final String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";
    private static final int INTENT_FLAG_NOT_RETURN_MAP = 512;
    private static final int MIN_OPERATION_INTERVAL = 2000;
    protected static final int POPUP_MAP_CLOSE = 2;
    public static final String POPUP_MAP_PROTOCAL_ACTION = "com.xiaopeng.intent.action.POPUP_MAP_PROTOCAL";
    protected static final int POPUP_MAP_REQUEST_CODE = 256;
    protected static final int POPUP_MAP_SHOW = 3;
    public static final String POPUP_PROTOCOL_INTENT = "com.xiaopeng.intent.action.POPUP_PROTOCOL";
    public static final String POPUP_PROTOCOL_INTENT_TYPE = "type";
    public static final String POPUP_SPEECH_PROTOCAL_ACTION = "com.xiaopeng.intent.action.POPUP_SPEECH_PROTOCAL";
    protected static final int POPUP_VOICE_CLOSE = 5;
    protected static final int POPUP_VOICE_REQUEST_CODE = 257;
    protected static final int POPUP_VOICE_SHOW = 6;
    protected static final int POPUP_XMART_PROTOCAL = 0;
    public static final int REFUSE = 0;
    public static final String XP_PROTOCAL_MAP_ACCEPT_FLAG = "xp_protocal_map_accept_flag";
    public static final String XP_PROTOCAL_SPEECH_ACCEPT_FLAG = "xp_protocal_speech_accept_flag";
    private XListSingle mAboutRestoreLayout;
    private IntervalControl mIntervalControl;
    private XListSingle mMapServiceLayout;
    private XImageButton mMapServiceTip;
    private XButton mPolicyBtn;
    private XDialog mResetDialog;
    private XButton mRestBtn;
    private XListSingle mScreenPrivacyLayout;
    private XSwitch mSwitchMapService;
    private XSwitch mSwitchVoiceService;
    private XListSingle mVoiceServiceLayout;
    private XImageButton mVoiceServiceTip;
    private boolean isShowMicDialog = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.OldDataPrivacyFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            int hashCode = action.hashCode();
            if (hashCode != -1068236332) {
                if (hashCode == 835336980 && action.equals("android.media.action.MICROPHONE_MUTE_CHANGED")) {
                    c = 0;
                }
                c = 65535;
            } else {
                if (action.equals("com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_UNMUTE")) {
                    c = 1;
                }
                c = 65535;
            }
            if (c == 0) {
                ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.OldDataPrivacyFragment.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Logs.d("DataPrivacyFragment MICROPHONE_MUTE_CHANGED isMicMute() : " + OldDataPrivacyFragment.this.isMicMute() + ", isShowMicDialog : " + OldDataPrivacyFragment.this.isShowMicDialog + ", isAcceptVoice() : " + OldDataPrivacyFragment.this.isAcceptVoice());
                        if (OldDataPrivacyFragment.this.isMicMute() || OldDataPrivacyFragment.this.isAcceptVoice() || !OldDataPrivacyFragment.this.isShowMicDialog) {
                            OldDataPrivacyFragment.this.mSwitchVoiceService.setEnabled(true);
                            if (OldDataPrivacyFragment.this.isMicMute()) {
                                OldDataPrivacyFragment.this.mSwitchVoiceService.setChecked(false);
                                return;
                            } else {
                                OldDataPrivacyFragment.this.mSwitchVoiceService.setChecked(OldDataPrivacyFragment.this.isAcceptVoice());
                                return;
                            }
                        }
                        OldDataPrivacyFragment.this.isShowMicDialog = false;
                        OldDataPrivacyFragment.this.popupWithAction(OldDataPrivacyFragment.POPUP_SPEECH_PROTOCAL_ACTION, 257);
                    }
                }, 500L);
            } else if (c != 1) {
            } else {
                Logs.d("DataPrivacyFragment ACTION_CANCEL_MICROPHONE_UNMUTE");
                OldDataPrivacyFragment.this.mSwitchVoiceService.setEnabled(true);
                OldDataPrivacyFragment.this.isShowMicDialog = false;
            }
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_data_privacy_old;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mMapServiceLayout = (XListSingle) view.findViewById(R.id.maps_service);
        this.mVoiceServiceLayout = (XListSingle) view.findViewById(R.id.voice_service);
        this.mScreenPrivacyLayout = (XListSingle) view.findViewById(R.id.privacy_screen);
        this.mAboutRestoreLayout = (XListSingle) view.findViewById(R.id.about_restore);
        this.mIntervalControl = new IntervalControl("data_privacy");
        this.mSwitchMapService = (XSwitch) this.mMapServiceLayout.findViewById(R.id.x_list_action_switch);
        this.mSwitchMapService.setChecked(isAcceptMap());
        this.mSwitchMapService.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$ywFJ6v4kyCKrtHAXwqpgGDrMZ_Q
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return OldDataPrivacyFragment.this.lambda$initView$0$OldDataPrivacyFragment(view2, motionEvent);
            }
        });
        this.mSwitchMapService.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$QzdHr9B5f6FlKKgsetclv7sXUqA
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view2, boolean z) {
                return OldDataPrivacyFragment.this.lambda$initView$1$OldDataPrivacyFragment(view2, z);
            }
        });
        this.mSwitchVoiceService = (XSwitch) this.mVoiceServiceLayout.findViewById(R.id.x_list_action_switch);
        this.mSwitchVoiceService.setChecked(isAcceptVoice());
        this.mSwitchVoiceService.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$PYv9DfwcELPRXFQ13QhpZJbwet8
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return OldDataPrivacyFragment.this.lambda$initView$2$OldDataPrivacyFragment(view2, motionEvent);
            }
        });
        this.mSwitchVoiceService.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$YyLzQX-cW_k-MOjU_Qq6lIfoR9s
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view2, boolean z) {
                return OldDataPrivacyFragment.this.lambda$initView$3$OldDataPrivacyFragment(view2, z);
            }
        });
        this.mMapServiceTip = (XImageButton) this.mMapServiceLayout.findViewById(R.id.x_list_action_button_icon);
        this.mMapServiceTip.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mMapServiceTip.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$P7rwsBcIINQC76i-8HEk3ByqoB4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                OldDataPrivacyFragment.this.lambda$initView$4$OldDataPrivacyFragment(view2);
            }
        });
        this.mVoiceServiceTip = (XImageButton) this.mVoiceServiceLayout.findViewById(R.id.x_list_action_button_icon);
        this.mVoiceServiceTip.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mVoiceServiceTip.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$lWDdSR2PuudsqCWBzRmtwE93o8U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                OldDataPrivacyFragment.this.lambda$initView$5$OldDataPrivacyFragment(view2);
            }
        });
        registerReceiver();
        this.mPolicyBtn = (XButton) this.mScreenPrivacyLayout.findViewById(R.id.x_list_action_button);
        this.mPolicyBtn.setText(R.string.privacy_protocol_check);
        this.mPolicyBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$LTxq67FAOREfrGU3LDhMNpfm3KM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                OldDataPrivacyFragment.this.lambda$initView$6$OldDataPrivacyFragment(view2);
            }
        });
        this.mRestBtn = (XButton) this.mAboutRestoreLayout.findViewById(R.id.x_list_action_button);
        this.mRestBtn.setText(getString(R.string.about_execute));
        this.mRestBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$RouVpQGel_HKcOfzxziKS6zxiso
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                OldDataPrivacyFragment.this.lambda$initView$7$OldDataPrivacyFragment(view2);
            }
        });
    }

    public /* synthetic */ boolean lambda$initView$0$OldDataPrivacyFragment(View view, MotionEvent motionEvent) {
        IntervalControl intervalControl;
        return motionEvent.getAction() == 0 && (intervalControl = this.mIntervalControl) != null && intervalControl.isFrequently(2000);
    }

    public /* synthetic */ boolean lambda$initView$1$OldDataPrivacyFragment(View view, boolean z) {
        if (((XSwitch) view).isFromUser()) {
            boolean isAcceptMap = isAcceptMap();
            Logs.d("DataPrivacyFragment press map switch, isChecked : " + z + ", isAcceptMap : " + isAcceptMap);
            this.mSwitchMapService.setEnabled(false);
            if (isAcceptMap) {
                popupWithReqCode(2, 256);
                return true;
            }
            popupWithAction(POPUP_MAP_PROTOCAL_ACTION, 256);
            return true;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initView$2$OldDataPrivacyFragment(View view, MotionEvent motionEvent) {
        IntervalControl intervalControl;
        return motionEvent.getAction() == 0 && (intervalControl = this.mIntervalControl) != null && intervalControl.isFrequently(2000);
    }

    public /* synthetic */ boolean lambda$initView$3$OldDataPrivacyFragment(View view, boolean z) {
        if (((XSwitch) view).isFromUser()) {
            Logs.d("DataPrivacyFragment press voice switch, isChecked : " + z + ", isAcceptVoice : " + isAcceptVoice());
            this.mSwitchVoiceService.setEnabled(false);
            if (isAcceptVoice() && !isMicMute()) {
                popupWithReqCode(5, 257);
            } else if (isMicMute()) {
                this.isShowMicDialog = true;
                MicUtils.openMicDialog(getContext());
            } else {
                this.isShowMicDialog = false;
                popupWithAction(POPUP_SPEECH_PROTOCAL_ACTION, 257);
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$initView$4$OldDataPrivacyFragment(View view) {
        popup(3);
    }

    public /* synthetic */ void lambda$initView$5$OldDataPrivacyFragment(View view) {
        popup(6);
    }

    public /* synthetic */ void lambda$initView$6$OldDataPrivacyFragment(View view) {
        popup(0);
    }

    public /* synthetic */ void lambda$initView$7$OldDataPrivacyFragment(View view) {
        showCheckedInfoDialog();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.action.MICROPHONE_MUTE_CHANGED");
        intentFilter.addAction("com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_UNMUTE");
        getContext().registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mReceiver != null) {
            getContext().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        boolean isAcceptMap = isAcceptMap();
        boolean isChecked = this.mSwitchMapService.isChecked();
        boolean isAcceptVoice = isAcceptVoice();
        boolean isChecked2 = this.mSwitchVoiceService.isChecked();
        Logs.d("DataPrivacyFragment onResume isAcceptMap : " + isAcceptMap + ", swMapChecked : " + isChecked + ", isAcceptVoice : " + isAcceptVoice + ", swVoiceChecked : " + isChecked2);
        if (isAcceptMap != isChecked) {
            this.mSwitchMapService.setChecked(isAcceptMap);
            XSoundEffectManager.get().play(isAcceptMap ? 3 : 4);
        }
        if (isAcceptVoice == isChecked2 || isMicMute()) {
            return;
        }
        this.mSwitchVoiceService.setChecked(isAcceptVoice);
        XSoundEffectManager.get().play(isAcceptVoice ? 3 : 4);
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mSwitchMapService.setOnCheckedChangeListener(null);
        this.mSwitchVoiceService.setOnCheckedChangeListener(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 256) {
            boolean isAcceptMap = isAcceptMap();
            Logs.d("DataPrivacyFragment onActivityResult req popup map isAcceptMap : " + isAcceptMap);
            this.mSwitchMapService.setChecked(isAcceptMap);
            this.mSwitchMapService.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$9tOWEi6XY0ku8Quugivxq9XuPRs
                @Override // java.lang.Runnable
                public final void run() {
                    OldDataPrivacyFragment.this.lambda$onActivityResult$8$OldDataPrivacyFragment();
                }
            });
        } else if (i != 257) {
        } else {
            boolean isAcceptVoice = isAcceptVoice();
            Logs.d("DataPrivacyFragment onActivityResult req popup voice isAcceptVoice : " + isAcceptVoice);
            this.mSwitchVoiceService.setChecked(isAcceptVoice);
            this.mSwitchVoiceService.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$CArePG94R2c3GN9JusNrdDZ6hBI
                @Override // java.lang.Runnable
                public final void run() {
                    OldDataPrivacyFragment.this.lambda$onActivityResult$9$OldDataPrivacyFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onActivityResult$8$OldDataPrivacyFragment() {
        this.mSwitchMapService.setEnabled(true);
    }

    public /* synthetic */ void lambda$onActivityResult$9$OldDataPrivacyFragment() {
        this.mSwitchVoiceService.setEnabled(true);
    }

    private void popup(int i) {
        IntervalControl intervalControl = this.mIntervalControl;
        if (intervalControl == null || !intervalControl.isFrequently(2000)) {
            Logs.d("DataPrivacyFragment popup type : " + i);
            Intent intent = new Intent(POPUP_PROTOCOL_INTENT);
            intent.addFlags(512);
            intent.setFlags(268435456);
            intent.putExtra("type", i);
            startActivity(intent);
        }
    }

    private void popupWithReqCode(int i, int i2) {
        Logs.d("DataPrivacyFragment popupWithReqCode type : " + i + ", reqCode : " + i2);
        Intent intent = new Intent(POPUP_PROTOCOL_INTENT);
        intent.putExtra("type", i);
        intent.setFlags(268435456);
        intent.addFlags(512);
        startActivityForResult(intent, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popupWithAction(String str, int i) {
        Logs.d("DataPrivacyFragment popupWithAction action : " + str + ", reqCode : " + i);
        Intent intent = new Intent(str);
        intent.setFlags(268435456);
        intent.addFlags(512);
        startActivityForResult(intent, i);
    }

    private void showCheckedInfoDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mResetDialog == null) {
            this.mResetDialog = new XDialog(getContext());
            this.mResetDialog.setTitle(getString(R.string.restore_settings_title)).setMessage(getString(R.string.restore_init_title)).setPositiveButton(getString(R.string.continue_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$r_g6gx17K4Ed29E7owslSFN1Za4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    OldDataPrivacyFragment.this.lambda$showCheckedInfoDialog$11$OldDataPrivacyFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
        }
        this.mResetDialog.show();
    }

    public /* synthetic */ void lambda$showCheckedInfoDialog$11$OldDataPrivacyFragment(XDialog xDialog, int i) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$OldDataPrivacyFragment$ZqmRujkK2xTPFhZ1wjqPBZ2_qXs
            @Override // java.lang.Runnable
            public final void run() {
                OldDataPrivacyFragment.this.lambda$null$10$OldDataPrivacyFragment();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$null$10$OldDataPrivacyFragment() {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), UIGlobalService.class);
            intent.setAction(RestoreCheckedDialogWork.ACTION);
            getContext().startService(intent);
        }
    }

    private boolean isAcceptMap() {
        return 1 == Settings.Global.getInt(getActivity().getContentResolver(), XP_PROTOCAL_MAP_ACCEPT_FLAG, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAcceptVoice() {
        return 1 == Settings.Global.getInt(getActivity().getContentResolver(), XP_PROTOCAL_SPEECH_ACCEPT_FLAG, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMicMute() {
        return MicUtils.isMicrophoneMute();
    }
}
