package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import androidx.lifecycle.Observer;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.MicUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsInfo;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.privacymanager.PrivacyDialogListener;
import com.xiaopeng.privacymanager.PrivacyServiceManager;
import com.xiaopeng.privacymanager.PrivacySettings;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XLoadingDialog;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.utils.XDialogUtils;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTextView;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
/* loaded from: classes.dex */
public class DataPrivacyFragment extends BaseFragment {
    public static final String ACTION_CANCEL_MICROPHONE_UNMUTE = "com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_UNMUTE";
    public static final String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";
    private static final int MIN_OPERATION_INTERVAL = 2000;
    private XListSingle mAboutCleanLayout;
    private XListSingle mAboutRestoreLayout;
    private XImageButton mCleanBtn;
    private XDialog mCleanDialog;
    private XTextView mFreeSpaceTv;
    private IntervalControl mIntervalControl;
    private XLoadingDialog mLoadingDialog;
    private XListTwo mMapServiceLayout;
    private XImageButton mMapServiceTip;
    private XImageButton mPolicyBtn;
    private PrivacyServiceManager mPrivacyServiceManager;
    private XProgressBar mProgressBar;
    private ContentObserver mProtocolContentObserver;
    private XDialog mResetDialog;
    private XImageButton mRestBtn;
    private XListSingle mScreenPrivacyLayout;
    private StorageSettingsViewModel mStorageSettingsViewModel;
    private XSwitch mSwitchMapService;
    private XSwitch mSwitchVoiceService;
    private XListTwo mVoiceServiceLayout;
    private XImageButton mVoiceServiceTip;
    private final IntervalControl mClickIntervalControl = new IntervalControl("clean");
    private boolean isShowMicDialog = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DataPrivacyFragment.2
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
                ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DataPrivacyFragment.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Logs.d("DataPrivacyFragment MICROPHONE_MUTE_CHANGED isMicMute() : " + DataPrivacyFragment.this.isMicMute() + ", isShowMicDialog : " + DataPrivacyFragment.this.isShowMicDialog + ", isAcceptVoice : " + DataPrivacyFragment.this.isAcceptVoice());
                        if (DataPrivacyFragment.this.isMicMute() || DataPrivacyFragment.this.isAcceptVoice() || !DataPrivacyFragment.this.isShowMicDialog) {
                            DataPrivacyFragment.this.mSwitchVoiceService.setEnabled(true);
                            if (DataPrivacyFragment.this.isMicMute()) {
                                DataPrivacyFragment.this.mSwitchVoiceService.setChecked(false);
                                return;
                            } else {
                                DataPrivacyFragment.this.mSwitchVoiceService.setChecked(DataPrivacyFragment.this.isAcceptVoice());
                                return;
                            }
                        }
                        DataPrivacyFragment.this.isShowMicDialog = false;
                        DataPrivacyFragment.this.showAgreeDialog(204, 3, DataPrivacyFragment.this.mSwitchVoiceService);
                    }
                }, 500L);
            } else if (c != 1) {
            } else {
                Logs.d("DataPrivacyFragment ACTION_CANCEL_MICROPHONE_UNMUTE");
                DataPrivacyFragment.this.mSwitchVoiceService.setEnabled(true);
                DataPrivacyFragment.this.isShowMicDialog = false;
            }
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_data_privacy;
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initContentObserver();
        getActivity().getContentResolver().registerContentObserver(PrivacySettings.getProtocolUri(), true, this.mProtocolContentObserver);
    }

    private void initContentObserver() {
        if (this.mProtocolContentObserver != null) {
            return;
        }
        this.mProtocolContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DataPrivacyFragment.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (DataPrivacyFragment.this.getContext() == null || DataPrivacyFragment.this.getView() == null) {
                    return;
                }
                boolean isAcceptMap = DataPrivacyFragment.this.isAcceptMap();
                boolean isChecked = DataPrivacyFragment.this.mSwitchMapService.isChecked();
                boolean isAcceptVoice = DataPrivacyFragment.this.isAcceptVoice();
                boolean isChecked2 = DataPrivacyFragment.this.mSwitchVoiceService.isChecked();
                Logs.d("DataPrivacyFragment isAcceptMap : " + isAcceptMap + ", swMapChecked : " + isChecked + ", isAcceptVoice : " + isAcceptVoice + ", swVoiceChecked : " + isChecked2);
                if (isAcceptMap != isChecked) {
                    DataPrivacyFragment.this.mSwitchMapService.setChecked(isAcceptMap);
                    XSoundEffectManager.get().play(isAcceptMap ? 3 : 4);
                }
                if (isAcceptVoice == isChecked2 || DataPrivacyFragment.this.isMicMute()) {
                    return;
                }
                DataPrivacyFragment.this.mSwitchVoiceService.setChecked(isAcceptVoice);
                XSoundEffectManager.get().play(isAcceptVoice ? 3 : 4);
            }
        };
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mMapServiceLayout = (XListTwo) view.findViewById(R.id.maps_service);
        this.mVoiceServiceLayout = (XListTwo) view.findViewById(R.id.voice_service);
        this.mScreenPrivacyLayout = (XListSingle) view.findViewById(R.id.privacy_screen);
        this.mAboutRestoreLayout = (XListSingle) view.findViewById(R.id.about_restore);
        this.mAboutCleanLayout = (XListSingle) view.findViewById(R.id.about_clean);
        this.mIntervalControl = new IntervalControl("data_privacy");
        this.mSwitchMapService = (XSwitch) this.mMapServiceLayout.findViewById(R.id.x_list_action_switch);
        this.mSwitchMapService.setChecked(isAcceptMap());
        this.mSwitchMapService.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$CAWWWZLpdNDznfkLoSsAjmPMGDo
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return DataPrivacyFragment.this.lambda$initView$0$DataPrivacyFragment(view2, motionEvent);
            }
        });
        this.mSwitchMapService.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$mphZATNMbtkMbuZjoqt80Uzf0TU
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view2, boolean z) {
                return DataPrivacyFragment.this.lambda$initView$1$DataPrivacyFragment(view2, z);
            }
        });
        this.mSwitchVoiceService = (XSwitch) this.mVoiceServiceLayout.findViewById(R.id.x_list_action_switch);
        this.mSwitchVoiceService.setChecked(isAcceptVoice());
        this.mSwitchVoiceService.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$MgSrdgZpSwqUX6quVcFRp1Z1_qg
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return DataPrivacyFragment.this.lambda$initView$2$DataPrivacyFragment(view2, motionEvent);
            }
        });
        this.mSwitchVoiceService.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$q53_4p6yXgwMwKqVOv0AKCfXaQ8
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view2, boolean z) {
                return DataPrivacyFragment.this.lambda$initView$3$DataPrivacyFragment(view2, z);
            }
        });
        this.mMapServiceTip = (XImageButton) this.mMapServiceLayout.findViewById(R.id.x_list_action_button_icon);
        this.mMapServiceTip.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mMapServiceTip.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$PCU3dXWi1lVEo5tKfEDqfIKcxHQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DataPrivacyFragment.this.lambda$initView$4$DataPrivacyFragment(view2);
            }
        });
        this.mVoiceServiceTip = (XImageButton) this.mVoiceServiceLayout.findViewById(R.id.x_list_action_button_icon);
        this.mVoiceServiceTip.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mVoiceServiceTip.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$nRYmeFD6zN3n2AMAfQ0XcHgM5Rw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DataPrivacyFragment.this.lambda$initView$5$DataPrivacyFragment(view2);
            }
        });
        registerReceiver();
        this.mPolicyBtn = (XImageButton) this.mScreenPrivacyLayout.findViewById(R.id.x_list_action_button_icon);
        this.mPolicyBtn.setImageResource(R.drawable.ic_mid_privacy);
        this.mPolicyBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$OFrMVXHmwfULePcBI8bAC07vpNE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DataPrivacyFragment.this.lambda$initView$6$DataPrivacyFragment(view2);
            }
        });
        this.mRestBtn = (XImageButton) this.mAboutRestoreLayout.findViewById(R.id.x_list_action_button_icon);
        this.mRestBtn.setImageResource(R.drawable.ic_icon_reset);
        this.mRestBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$_eUwD73RSvCpfIheNb8vfdXcVeQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DataPrivacyFragment.this.lambda$initView$7$DataPrivacyFragment(view2);
            }
        });
        this.mProgressBar = (XProgressBar) view.findViewById(R.id.view_free_space);
        this.mFreeSpaceTv = (XTextView) view.findViewById(R.id.xtv_free_space);
        this.mCleanBtn = (XImageButton) this.mAboutCleanLayout.findViewById(R.id.x_list_action_button_icon);
        this.mCleanBtn.setImageResource(R.drawable.ic_icon_delete);
        XImageButton xImageButton = this.mCleanBtn;
        xImageButton.setVuiLabel(getString(R.string.about_execute) + "|" + getString(R.string.about_execute) + "" + getString(R.string.about_clean));
        this.mCleanBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$gXEnskelbgjH0ZGcY3L3DTldxFE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DataPrivacyFragment.this.lambda$initView$8$DataPrivacyFragment(view2);
            }
        });
        if (CarFunction.isSupportOneKeyCleanCaches()) {
            view.findViewById(R.id.clean_layout).setVisibility(0);
        }
    }

    public /* synthetic */ boolean lambda$initView$0$DataPrivacyFragment(View view, MotionEvent motionEvent) {
        IntervalControl intervalControl;
        return motionEvent.getAction() == 0 && (intervalControl = this.mIntervalControl) != null && intervalControl.isFrequently(2000);
    }

    public /* synthetic */ boolean lambda$initView$1$DataPrivacyFragment(View view, boolean z) {
        if (((XSwitch) view).isFromUser()) {
            boolean isAcceptMap = isAcceptMap();
            Logs.d("DataPrivacyFragment press map switch, isChecked : " + z + ", isAcceptMap : " + isAcceptMap);
            this.mSwitchMapService.setEnabled(false);
            if (isAcceptMap) {
                showAgreeDialog(2001, 4, this.mSwitchMapService);
                return true;
            }
            showAgreeDialog(2001, 3, this.mSwitchMapService);
            return true;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initView$2$DataPrivacyFragment(View view, MotionEvent motionEvent) {
        IntervalControl intervalControl;
        return motionEvent.getAction() == 0 && (intervalControl = this.mIntervalControl) != null && intervalControl.isFrequently(2000);
    }

    public /* synthetic */ boolean lambda$initView$3$DataPrivacyFragment(View view, boolean z) {
        if (((XSwitch) view).isFromUser()) {
            Logs.d("DataPrivacyFragment press voice switch, isChecked : " + z + ", isAcceptVoice : " + isAcceptVoice());
            this.mSwitchVoiceService.setEnabled(false);
            if (isAcceptVoice() && !isMicMute()) {
                showAgreeDialog(204, 4, this.mSwitchVoiceService);
            } else if (isMicMute()) {
                this.isShowMicDialog = true;
                MicUtils.openMicDialog(getContext());
            } else {
                this.isShowMicDialog = false;
                showAgreeDialog(204, 3, this.mSwitchVoiceService);
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$initView$4$DataPrivacyFragment(View view) {
        showCloseDialog(2001);
    }

    public /* synthetic */ void lambda$initView$5$DataPrivacyFragment(View view) {
        showCloseDialog(204);
    }

    public /* synthetic */ void lambda$initView$6$DataPrivacyFragment(View view) {
        showCloseDialog(201);
    }

    public /* synthetic */ void lambda$initView$7$DataPrivacyFragment(View view) {
        showCheckedInfoDialog();
    }

    public /* synthetic */ void lambda$initView$8$DataPrivacyFragment(View view) {
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            ToastUtils.get().showText(R.string.about_clean_p_toast);
        } else {
            showCleanDialog();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mPrivacyServiceManager = new PrivacyServiceManager(getContext().getApplicationContext());
        this.mPrivacyServiceManager.connect(null);
        this.mStorageSettingsViewModel = (StorageSettingsViewModel) ViewModelUtils.getViewModel(this, StorageSettingsViewModel.class);
        this.mStorageSettingsViewModel.getStorageSettingsInfoLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$ilodm-_rUQLL5yivXnqrosHZAZs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DataPrivacyFragment.this.lambda$init$9$DataPrivacyFragment((StorageSettingsInfo) obj);
            }
        });
        this.mStorageSettingsViewModel.getCleanFinishLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$q9AAWF6wiUdtp6dmBPMmZlpFqX8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DataPrivacyFragment.this.lambda$init$10$DataPrivacyFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$9$DataPrivacyFragment(StorageSettingsInfo storageSettingsInfo) {
        this.mFreeSpaceTv.setText(getString(R.string.about_clean_space_used, new DecimalFormat("00%").format((((float) storageSettingsInfo.getUsedSize()) * 1.0f) / ((float) storageSettingsInfo.getTotalSize()))));
        int round = Math.round((((float) storageSettingsInfo.getUsedSize()) * 100.0f) / ((float) storageSettingsInfo.getTotalSize()));
        this.mProgressBar.setProgressDrawable(getContext().getDrawable(storageSettingsInfo.getTotalSize() - storageSettingsInfo.getUsedSize() < OSSConstants.DEFAULT_FILE_SIZE_LIMIT ? R.drawable.bg_free_space_full : R.drawable.bg_free_space));
        this.mProgressBar.setProgress(round);
    }

    public /* synthetic */ void lambda$init$10$DataPrivacyFragment(Boolean bool) {
        Logs.d("xpabout clean data end " + bool);
        if (bool.booleanValue()) {
            XLoadingDialog xLoadingDialog = this.mLoadingDialog;
            if (xLoadingDialog != null) {
                xLoadingDialog.dismiss();
                this.mLoadingDialog = null;
            }
            ToastUtils.get().showText(R.string.clean_settings_done);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mStorageSettingsViewModel.startVm();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mStorageSettingsViewModel.stopVm();
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
        getActivity().getContentResolver().unregisterContentObserver(this.mProtocolContentObserver);
        this.mProtocolContentObserver = null;
        if (this.mReceiver != null) {
            getContext().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
        PrivacyServiceManager privacyServiceManager = this.mPrivacyServiceManager;
        if (privacyServiceManager != null) {
            privacyServiceManager.disconnect();
        }
        XDialog xDialog = this.mResetDialog;
        if (xDialog != null) {
            if (xDialog.isShowing()) {
                this.mResetDialog.dismiss();
            }
            this.mResetDialog = null;
        }
        XLoadingDialog xLoadingDialog = this.mLoadingDialog;
        if (xLoadingDialog != null) {
            if (xLoadingDialog.isShowing()) {
                this.mLoadingDialog.dismiss();
            }
            this.mLoadingDialog = null;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mSwitchMapService.setOnCheckedChangeListener(null);
        this.mSwitchVoiceService.setOnCheckedChangeListener(null);
    }

    private void showCloseDialog(int i) {
        if (this.mPrivacyServiceManager.isConnected()) {
            this.mPrivacyServiceManager.showPrivacyDialog(i, 64, null);
            return;
        }
        Logs.d("not connected, showDialog type:" + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAgreeDialog(int i, int i2, XSwitch xSwitch) {
        if (this.mPrivacyServiceManager.isConnected()) {
            this.mPrivacyServiceManager.showPrivacyDialog(i, i2, new LocalPrivacyDialogListener(getContext().getApplicationContext(), i2, xSwitch));
            return;
        }
        Logs.d("not connected, showDialog type:" + i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class LocalPrivacyDialogListener implements PrivacyDialogListener {
        Context mAppContext;
        int mFlags;
        WeakReference<XSwitch> mWeakSwitch;

        LocalPrivacyDialogListener(Context context, int i, XSwitch xSwitch) {
            this.mAppContext = context;
            this.mFlags = i;
            this.mWeakSwitch = new WeakReference<>(xSwitch);
        }

        @Override // com.xiaopeng.privacymanager.PrivacyDialogListener
        public void onDialogClosed(int i, boolean z, int i2) {
            XSwitch xSwitch = this.mWeakSwitch.get();
            if (xSwitch != null) {
                xSwitch.setEnabled(true);
            }
            if (z) {
                if (this.mFlags == 4) {
                    PrivacySettings.setEnabled(this.mAppContext, i, false);
                    PrivacySettings.setAgreed(this.mAppContext, i, false);
                    Logs.d("PrivacySettings: showDialog type:" + i + "setEnabled:set false");
                    return;
                }
                PrivacySettings.setEnabled(this.mAppContext, i, true);
                PrivacySettings.setAgreed(this.mAppContext, i, true);
                Logs.d("PrivacySettings: showDialog type:" + i + "setEnabled:set true");
            }
        }
    }

    private void showCheckedInfoDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mResetDialog == null) {
            this.mResetDialog = new XDialog(getContext());
            this.mResetDialog.setTitle(getString(R.string.restore_settings_title)).setMessage(getString(R.string.restore_init_title)).setPositiveButton(getString(R.string.continue_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$TXb1MDxO-jS-IeH5Dw8givPMpko
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DataPrivacyFragment.this.lambda$showCheckedInfoDialog$12$DataPrivacyFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
        }
        this.mResetDialog.show();
    }

    public /* synthetic */ void lambda$showCheckedInfoDialog$12$DataPrivacyFragment(XDialog xDialog, int i) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$XeBszv7R2rLbqMwW7tuwGE-nqk0
            @Override // java.lang.Runnable
            public final void run() {
                DataPrivacyFragment.this.lambda$null$11$DataPrivacyFragment();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$null$11$DataPrivacyFragment() {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), UIGlobalService.class);
            intent.setAction(RestoreCheckedDialogWork.ACTION);
            getContext().startService(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAcceptMap() {
        return PrivacySettings.isAgreed(getContext(), 2001);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAcceptVoice() {
        return PrivacySettings.isAgreed(getContext(), 204);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMicMute() {
        return MicUtils.isMicrophoneMute();
    }

    private void showCleanDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mCleanDialog == null) {
            this.mCleanDialog = new XDialog(getContext());
            this.mCleanDialog.setTitle(getString(R.string.clean_settings_title)).setMessage(getString(R.string.clean_settings_content)).setPositiveButton(getString(R.string.continue_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DataPrivacyFragment$8rpxyUITf7oJoTDVGXFi4nBj33g
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DataPrivacyFragment.this.lambda$showCleanDialog$13$DataPrivacyFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
            this.mCleanDialog.setOnCountDownListener(new XDialogInterface.OnCountDownListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DataPrivacyFragment.3
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnCountDownListener
                public boolean onCountDown(XDialog xDialog, int i) {
                    DataPrivacyFragment.this.mCleanDialog.setPositiveButtonEnable(true);
                    return true;
                }
            });
        }
        this.mCleanDialog.setPositiveButtonEnable(false);
        this.mCleanDialog.show(10, 0);
        VuiManager.instance().initVuiDialog(this.mCleanDialog, getContext(), VuiManager.SCENE_CLEAN_DIALOG);
    }

    public /* synthetic */ void lambda$showCleanDialog$13$DataPrivacyFragment(XDialog xDialog, int i) {
        if (this.mClickIntervalControl.isFrequently(500)) {
            return;
        }
        Logs.d("xpabout clean data start ");
        showCleanLoadingDialog();
        this.mStorageSettingsViewModel.cleanFiles(getString(R.string.clean_tts), getString(R.string.clean_tts_tips));
    }

    private void showCleanLoadingDialog() {
        this.mLoadingDialog = new XLoadingDialog(getContext(), R.style.XAppTheme_XDialog_Loading);
        XDialogUtils.requestFullScreen(this.mLoadingDialog);
        this.mLoadingDialog.create();
        this.mLoadingDialog.setMessage(getString(R.string.clean_settings_doing));
        this.mLoadingDialog.setCancelable(false);
        this.mLoadingDialog.show();
    }
}
