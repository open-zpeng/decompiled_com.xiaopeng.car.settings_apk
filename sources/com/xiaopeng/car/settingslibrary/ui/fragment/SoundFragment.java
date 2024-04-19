package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.MicUtils;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.ui.dialog.HeadrestAudioModeDialog;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundSettingsViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogPure;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.slider.XSlider;
/* loaded from: classes.dex */
public class SoundFragment extends BaseFragment implements XSlider.SliderProgressListener, CompoundButton.OnCheckedChangeListener, XTabLayout.OnTabChangeListener, XSlider.ProgressChangeListener, ISoundServerListener {
    private XSwitch mDecreaseSwitch;
    private XSwitch mGearRSwitch;
    private TextView mHeadRestAudioModeView;
    private HeadrestAudioModeDialog mHeadrestAudioModeDialog;
    private XSlider mMediaSeekBarOutSide;
    private XSlider mMediaSeekbar;
    private XSlider mNavigationSeekbar;
    private XSwitch mOpenDoorSwitch;
    private XDialogPure mOutsideModeDialog;
    private XSwitch mPositionSwitch;
    private XImageView mSoundEffectImage1;
    private XImageView mSoundEffectImage2;
    private XImageView mSoundEffectLine;
    private TextView mSoundEffectTypeScene;
    private ViewStub mSoundHeadrestStub;
    private XTextView mSoundOutsideModeStatusText;
    private SoundSettingsViewModel mSoundSettingsViewModel;
    private XSlider mSpeechSeekbar;
    private XSwitch mSystemSwitch;
    private XTabLayout mTabLayoutCar;
    private XTabLayout mTabVolumeBySpeed;
    private XTabLayout mTabVolumeOutsideLowSpeedSimulate;
    private TextView mTtsTips;
    private XListMultiple mVolumeMedia;
    private XListMultiple mVolumeMediaOutside;
    private XDialog mVolumeOutsideShutdownSimulateDialog;
    private boolean mIsSoundSafetyVolumeTimeout = true;
    private boolean mIsSoundSafetyVolumeCallback = true;
    private Handler mHandler = new Handler();
    private Runnable mRunnableTimeout = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$2nh2yuKXfKYYVzxehgQ5ui-7SY0
        @Override // java.lang.Runnable
        public final void run() {
            SoundFragment.this.tabTimeout();
        }
    };
    private Runnable mRunnableMaxTimeout = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$DmknOQA7dXX52m1IHfykb2iZe2g
        @Override // java.lang.Runnable
        public final void run() {
            SoundFragment.this.tabMaxTimeout();
        }
    };
    private boolean mIsRegisterSound = false;
    private IVcuChangeListener mGearChangeListener = new IVcuChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$_0I5Oe24smWedT4CouGiF67wPns
        @Override // com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener
        public final void onValueChange(int i, int i2) {
            SoundFragment.this.onGearChange(i, i2);
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CarSettingsManager.getInstance().addVcuChangeListener(this.mGearChangeListener);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_sound;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRootView = view.findViewById(R.id.layout_sound);
        this.mVolumeMedia = (XListMultiple) view.findViewById(R.id.sound_volume_media);
        this.mVolumeMediaOutside = (XListMultiple) view.findViewById(R.id.sound_volume_media_outside);
        this.mVolumeMediaOutside.setVisibility(8);
        View findViewById = view.findViewById(R.id.sound_volume_speech);
        View findViewById2 = view.findViewById(R.id.sound_volume_tts);
        View findViewById3 = view.findViewById(R.id.sound_volume_system);
        View findViewById4 = view.findViewById(R.id.sound_volume_position);
        XListMultiple xListMultiple = (XListMultiple) view.findViewById(R.id.sound_volume_car);
        XListTwo xListTwo = (XListTwo) view.findViewById(R.id.sound_decrease_volume);
        View findViewById5 = view.findViewById(R.id.sound_open_door);
        View findViewById6 = view.findViewById(R.id.sound_gear_r);
        View findViewById7 = view.findViewById(R.id.sound_tts_settings);
        findViewById7.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$hvQFn7kUaHIOaU-RC-GSO5Ho0C0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SoundFragment.this.lambda$initView$0$SoundFragment(view2);
            }
        });
        if (!CarFunction.isSupportSoundTtsSettingShow()) {
            findViewById7.setVisibility(8);
        }
        if (CarFunction.isSupportPositionSystemTips()) {
            findViewById4.setVisibility(0);
        }
        this.mTtsTips = (TextView) view.findViewById(R.id.sound_tts_tips);
        if (CarFunction.isDecreaseVolume()) {
            xListTwo.setVisibility(0);
        } else {
            xListTwo.setVisibility(8);
        }
        this.mMediaSeekbar = (XSlider) this.mVolumeMedia.findViewById(R.id.x_list_action_slider);
        this.mMediaSeekbar.setSliderProgressListener(this);
        this.mMediaSeekbar.setProgressChangeListener(this);
        this.mMediaSeekBarOutSide = (XSlider) this.mVolumeMediaOutside.findViewById(R.id.x_list_action_slider);
        this.mMediaSeekBarOutSide.setSliderProgressListener(this);
        this.mMediaSeekBarOutSide.setProgressChangeListener(this);
        this.mSpeechSeekbar = (XSlider) findViewById.findViewById(R.id.x_list_action_slider);
        this.mSpeechSeekbar.setSliderProgressListener(this);
        this.mSpeechSeekbar.setProgressChangeListener(this);
        this.mNavigationSeekbar = (XSlider) findViewById2.findViewById(R.id.x_list_action_slider);
        this.mNavigationSeekbar.setSliderProgressListener(this);
        this.mNavigationSeekbar.setProgressChangeListener(this);
        this.mSystemSwitch = (XSwitch) findViewById3.findViewById(R.id.x_list_action_switch);
        this.mPositionSwitch = (XSwitch) findViewById4.findViewById(R.id.x_list_action_switch);
        this.mTabLayoutCar = (XTabLayout) xListMultiple.findViewById(R.id.sound_volume_car_segment);
        this.mTabLayoutCar.setOnTabChangeListener(this);
        this.mOpenDoorSwitch = (XSwitch) findViewById5.findViewById(R.id.x_list_action_switch);
        this.mGearRSwitch = (XSwitch) findViewById6.findViewById(R.id.x_list_action_switch);
        this.mDecreaseSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_action_switch);
        this.mOpenDoorSwitch.setOnCheckedChangeListener(this);
        this.mDecreaseSwitch.setOnCheckedChangeListener(this);
        this.mGearRSwitch.setOnCheckedChangeListener(this);
        this.mSystemSwitch.setOnCheckedChangeListener(this);
        this.mPositionSwitch.setOnCheckedChangeListener(this);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.sound_effect);
        this.mSoundEffectLine = (XImageView) viewGroup.findViewById(R.id.sound_effect_line);
        this.mSoundEffectImage1 = (XImageView) viewGroup.findViewById(R.id.sound_effect_image1);
        this.mSoundEffectImage2 = (XImageView) viewGroup.findViewById(R.id.sound_effect_image2);
        this.mSoundEffectTypeScene = (TextView) viewGroup.findViewById(R.id.sound_effect_type_scene);
        if (!CarFunction.isSupportSoundEffectPreviewShow()) {
            this.mSoundEffectLine.setVisibility(8);
            this.mSoundEffectImage1.setVisibility(8);
            this.mSoundEffectImage2.setVisibility(8);
            this.mSoundEffectTypeScene.setVisibility(8);
        }
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$6xb83w8w74lq-JUbPGrMSuUJnnM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SoundFragment.this.lambda$initView$1$SoundFragment(view2);
            }
        });
        if (CarConfigHelper.hasMainDriverVIP()) {
            this.mSoundHeadrestStub = (ViewStub) view.findViewById(R.id.sound_headrest);
            View inflate = this.mSoundHeadrestStub.inflate();
            inflate.findViewById(R.id.sound_headrest_audio_mode).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$mxS9FxNo9UDuK5U4HVUCxDtVCiA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SoundFragment.this.lambda$initView$2$SoundFragment(view2);
                }
            });
            inflate.findViewById(R.id.sound_headrest_audio_mode).setVisibility(0);
            this.mHeadRestAudioModeView = (TextView) inflate.findViewById(R.id.sound_headrest_audio_mode_msg);
        }
        if (!CarConfigHelper.hasAMP()) {
            xListTwo.setTextSub(getString(R.string.sound_decrease_volume_tip_low));
        } else {
            xListTwo.setTextSub(getString(R.string.sound_decrease_volume_tip_high));
        }
        if (CarFunction.isSupportAutoDriveTts()) {
            xListMultiple.setTextSub(getString(R.string.sound_volume_car_tip));
        } else {
            xListMultiple.setTextSub(getString(R.string.sound_volume_car_tip_low));
        }
        View findViewById8 = view.findViewById(R.id.sound_volume_by_speed);
        if (CarFunction.isSupportSoundVolumeAdjustBySpeed() && CarConfigHelper.hasAMP()) {
            findViewById8.setVisibility(0);
            this.mTabVolumeBySpeed = (XTabLayout) findViewById8.findViewById(R.id.sound_volume_by_speed_tab);
            this.mTabVolumeBySpeed.setOnTabChangeListener(this);
        } else {
            findViewById8.setVisibility(8);
        }
        View findViewById9 = view.findViewById(R.id.sound_volume_outside_low_speed_simulate);
        if (CarFunction.isSupportSoundOutsideLowSpeedSimulate()) {
            findViewById9.setVisibility(0);
            this.mTabVolumeOutsideLowSpeedSimulate = (XTabLayout) findViewById9.findViewById(R.id.sound_volume_outside_low_speed_simulate_tab);
            this.mTabVolumeOutsideLowSpeedSimulate.setOnTabChangeListener(this);
        } else {
            findViewById9.setVisibility(8);
        }
        View findViewById10 = view.findViewById(R.id.sound_outside_mode);
        if (CarFunction.isSupportAvasSpeaker()) {
            findViewById10.setVisibility(0);
            this.mSoundOutsideModeStatusText = (XTextView) findViewById10.findViewById(R.id.sound_outside_mode_status_text);
            findViewById10.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$v3mA02VRTYjJxPP7NxU3BSyGFCc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SoundFragment.this.lambda$initView$3$SoundFragment(view2);
                }
            });
            return;
        }
        findViewById10.setVisibility(8);
    }

    public /* synthetic */ void lambda$initView$0$SoundFragment(View view) {
        Utils.startToSpeechSet(getContext());
    }

    public /* synthetic */ void lambda$initView$1$SoundFragment(View view) {
        showSoundDialog();
    }

    public /* synthetic */ void lambda$initView$2$SoundFragment(View view) {
        showHeadrestAudioMode();
    }

    public /* synthetic */ void lambda$initView$3$SoundFragment(View view) {
        showOutsideModeDialog();
    }

    private void refreshHeadRestAudioMode(int i) {
        TextView textView = this.mHeadRestAudioModeView;
        if (textView == null) {
            return;
        }
        if (i == 0) {
            textView.setText(R.string.sound_headrest_audio_mode_off_title);
        } else if (i == 1) {
            textView.setText(R.string.sound_headrest_audio_mode_drive_title);
        } else if (i != 2) {
        } else {
            textView.setText(R.string.sound_headrest_audio_mode_headrest_title);
        }
    }

    private void showHeadrestAudioMode() {
        if (this.mHeadrestAudioModeDialog == null) {
            this.mHeadrestAudioModeDialog = new HeadrestAudioModeDialog(getContext(), new HeadrestAudioModeDialog.OnHeadRestAudioSetListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$C_PNjjD5KyF_FblyhvoMZxkVjrI
                @Override // com.xiaopeng.car.settingslibrary.ui.dialog.HeadrestAudioModeDialog.OnHeadRestAudioSetListener
                public final void OnHeadRestAudioSet(int i) {
                    SoundFragment.this.lambda$showHeadrestAudioMode$4$SoundFragment(i);
                }
            });
        }
        this.mHeadrestAudioModeDialog.show(XpAccountManager.getInstance().getMainDriverExclusive());
    }

    public /* synthetic */ void lambda$showHeadrestAudioMode$4$SoundFragment(int i) {
        Logs.d("xpsound headrest OnHeadRestAudioSet mode: " + i);
        this.mSoundSettingsViewModel.setMainDriverExclusive(i, false);
        refreshHeadRestAudioMode(i);
    }

    private void showSoundDialog() {
        Utils.startSoundEffectSetting();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mSoundSettingsViewModel = (SoundSettingsViewModel) ViewModelUtils.getViewModel(this, SoundSettingsViewModel.class);
        this.mSoundSettingsViewModel.onCreateVM();
        this.mMediaSeekbar.setAccuracy(0.1f);
        this.mMediaSeekBarOutSide.setAccuracy(0.1f);
        this.mSpeechSeekbar.setAccuracy(0.1f);
        this.mNavigationSeekbar.setAccuracy(0.1f);
        this.mMediaSeekbar.setEndIndex(this.mSoundSettingsViewModel.getMaxVolume(3));
        if (CarFunction.isSupportAvasSpeaker()) {
            this.mMediaSeekBarOutSide.setEndIndex(this.mSoundSettingsViewModel.getMaxVolume(Config.AVAS_STREAM));
        }
        this.mSpeechSeekbar.setEndIndex(this.mSoundSettingsViewModel.getMaxVolume(10));
        this.mNavigationSeekbar.setEndIndex(this.mSoundSettingsViewModel.getMaxVolume(9));
        this.mMediaSeekbar.setInitIndex(this.mSoundSettingsViewModel.getStreamVolume(3));
        if (CarFunction.isSupportAvasSpeaker()) {
            this.mMediaSeekBarOutSide.setInitIndex(this.mSoundSettingsViewModel.getStreamVolume(Config.AVAS_STREAM));
        }
        this.mSpeechSeekbar.setInitIndex(this.mSoundSettingsViewModel.getStreamVolume(10));
        this.mNavigationSeekbar.setInitIndex(this.mSoundSettingsViewModel.getStreamVolume(9));
        this.mSoundSettingsViewModel.getMediaLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$vqbjWcNboTU0qF0FSlqQjPZQixk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$6$SoundFragment((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getAvasLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$CU43OtjS3UEdTD3gN1dF9BCOsCE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$8$SoundFragment((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getSpeechLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$Qo44p1ImMyyOASUi13vE_2ktcec
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$10$SoundFragment((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getNavigationLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$aK2SfMk86aGhp1bO4-4UoEqseps
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$12$SoundFragment((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getSafetySoundLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$smHsJIBcne8Eg_uJ2nn5RpCKods
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$13$SoundFragment((Integer) obj);
            }
        });
        this.mSoundSettingsViewModel.getSystemSoundChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$njQK9jGt5fmOJnZQnH5DjxqKGPQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundFragment.this.lambda$init$14$SoundFragment((Boolean) obj);
            }
        });
        initVui();
    }

    public /* synthetic */ void lambda$init$6$SoundFragment(Integer num) {
        log("observe-info:" + num);
        if (num.intValue() != ((int) this.mMediaSeekbar.getIndicatorValue())) {
            this.mMediaSeekbar.setCurrentIndex(num.intValue());
        }
        this.mMediaSeekbar.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$XFNgVLpiBIRArQDzT8lviKxsO_Y
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$null$5$SoundFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$5$SoundFragment() {
        updateVuiScene(this.sceneId, this.mMediaSeekbar);
    }

    public /* synthetic */ void lambda$init$8$SoundFragment(Integer num) {
        log("observe-avas-info:" + num);
        if (num.intValue() != ((int) this.mMediaSeekBarOutSide.getIndicatorValue())) {
            this.mMediaSeekBarOutSide.setCurrentIndex(num.intValue());
        }
        this.mMediaSeekBarOutSide.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$gNGMoOw7kYtkOUizT8rdOprfTkc
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$null$7$SoundFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$7$SoundFragment() {
        updateVuiScene(this.sceneId, this.mMediaSeekBarOutSide);
    }

    public /* synthetic */ void lambda$init$10$SoundFragment(Integer num) {
        log("observe-info:" + num);
        if (num.intValue() != ((int) this.mSpeechSeekbar.getIndicatorValue())) {
            this.mSpeechSeekbar.setCurrentIndex(num.intValue());
        }
        this.mNavigationSeekbar.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$bB4BIVGUp_HgeE6Gtg8F_-SIztQ
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$null$9$SoundFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$9$SoundFragment() {
        updateVuiScene(this.sceneId, this.mSpeechSeekbar);
    }

    public /* synthetic */ void lambda$init$12$SoundFragment(Integer num) {
        log("observe-info:" + num);
        if (num.intValue() != ((int) this.mNavigationSeekbar.getIndicatorValue())) {
            this.mNavigationSeekbar.setCurrentIndex(num.intValue());
        }
        this.mNavigationSeekbar.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$ecW1D80IAsEgonHxhtGQc2DqsgA
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$null$11$SoundFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$11$SoundFragment() {
        updateVuiScene(this.sceneId, this.mNavigationSeekbar);
    }

    public /* synthetic */ void lambda$init$13$SoundFragment(Integer num) {
        refreshSafetySound(num.intValue(), "callback");
    }

    public /* synthetic */ void lambda$init$14$SoundFragment(Boolean bool) {
        this.mSystemSwitch.setChecked(this.mSoundSettingsViewModel.isSystemSoundEnabled());
    }

    private void refreshHeadRestMode(int i) {
        refreshHeadRestAudioMode(i);
        HeadrestAudioModeDialog headrestAudioModeDialog = this.mHeadrestAudioModeDialog;
        if (headrestAudioModeDialog == null || !headrestAudioModeDialog.isShowing()) {
            return;
        }
        this.mHeadrestAudioModeDialog.refreshUIMode(i);
    }

    private void refreshSafetySound(int i, String str) {
        int i2 = 0;
        if (i != 0) {
            if (i == 1) {
                i2 = 1;
            } else if (i == 2) {
                i2 = 2;
            }
        }
        Logs.d("xpsound tab index:" + i2 + " " + this.mTabLayoutCar.getSelectedTabIndex() + " timeout:" + this.mIsSoundSafetyVolumeTimeout);
        if (i2 != this.mTabLayoutCar.getSelectedTabIndex()) {
            this.mTabLayoutCar.selectTab(i2);
        }
        if ("callback".equals(str)) {
            if (this.mIsSoundSafetyVolumeTimeout) {
                tabLayoutCarEnable(true);
            }
            this.mIsSoundSafetyVolumeCallback = true;
        }
    }

    private void tabLayoutCarEnable(boolean z) {
        if (z) {
            this.mHandler.removeCallbacks(this.mRunnableMaxTimeout);
            this.mTabLayoutCar.setEnabled(true);
            return;
        }
        this.mTabLayoutCar.setEnabled(false);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(final CompoundButton compoundButton, final boolean z) {
        updateVuiScene(this.sceneId, compoundButton);
        if (((XSwitch) compoundButton).isFromUser() || isVuiAction(compoundButton)) {
            if (compoundButton == this.mOpenDoorSwitch) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B004", z);
                this.mSoundSettingsViewModel.setLowerSoundWhenOpenDoor(z);
            } else if (compoundButton == this.mGearRSwitch) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B003", z);
                this.mSoundSettingsViewModel.setLowerSoundWhenRstall(z);
            } else if (compoundButton == this.mDecreaseSwitch) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B010", z);
                this.mSoundSettingsViewModel.setDecreaseVolumeNavigating(z);
            } else if (compoundButton == this.mSystemSwitch) {
                compoundButton.setEnabled(false);
                compoundButton.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$cr9GL1-OSyoWRiOX8ysirmHYOU4
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoundFragment.this.lambda$onCheckedChanged$15$SoundFragment(z, compoundButton);
                    }
                }, 500L);
            } else if (compoundButton == this.mPositionSwitch) {
                this.mSoundSettingsViewModel.setSoundPositionEnable(z);
            }
        }
    }

    public /* synthetic */ void lambda$onCheckedChanged$15$SoundFragment(boolean z, CompoundButton compoundButton) {
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B008", z);
        this.mSoundSettingsViewModel.setSystemSoundEnable(z);
        compoundButton.setEnabled(true);
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        if (xTabLayout == this.mTabVolumeOutsideLowSpeedSimulate && z2 && z && i == 0) {
            showVolumeOutSideSimulateShutdownDialog();
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        if (xTabLayout == this.mTabLayoutCar && z2 && z) {
            Logs.d("onTabChangeStart: " + this.mTabLayoutCar);
            this.mHandler.removeCallbacks(this.mRunnableTimeout);
            this.mHandler.removeCallbacks(this.mRunnableMaxTimeout);
            tabLayoutCarEnable(false);
        }
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        if (xTabLayout == this.mTabLayoutCar && z2 && z) {
            Logs.d("onTabChangeEnd: " + this.mTabLayoutCar);
            this.mSoundSettingsViewModel.setSafetyVolume(i);
            this.mIsSoundSafetyVolumeTimeout = false;
            this.mIsSoundSafetyVolumeCallback = false;
            this.mHandler.removeCallbacks(this.mRunnableTimeout);
            this.mHandler.postDelayed(this.mRunnableTimeout, 300L);
            this.mHandler.removeCallbacks(this.mRunnableMaxTimeout);
            this.mHandler.postDelayed(this.mRunnableMaxTimeout, 1000L);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_PAGE_ID, "B001", i);
        } else if (xTabLayout == this.mTabVolumeBySpeed && z2 && z) {
            SoundManager.getInstance().setSoundSpeedLinkLevel(i + 1);
        } else if (xTabLayout == this.mTabVolumeOutsideLowSpeedSimulate && z2 && z) {
            CarSettingsManager.getInstance().setAvasLowSpeedSound(i);
        }
        updateVuiScene(this.sceneId, xTabLayout);
    }

    private void restoreTabCarStatus() {
        this.mIsSoundSafetyVolumeTimeout = true;
        this.mIsSoundSafetyVolumeCallback = true;
        tabLayoutCarEnable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tabTimeout() {
        Logs.d("xpsound tab timeout callback:" + this.mIsSoundSafetyVolumeCallback);
        this.mIsSoundSafetyVolumeTimeout = true;
        if (this.mIsSoundSafetyVolumeCallback) {
            tabLayoutCarEnable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tabMaxTimeout() {
        Logs.d("xpsound tab tabMaxTimeout");
        tabLayoutCarEnable(true);
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onProgressChanged(XSlider xSlider, float f, String str) {
        sliderProgressChanged(xSlider, f);
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStartTrackingTouch(XSlider xSlider) {
        Logs.d("xpsound onStartTrackingTouch:");
        if (xSlider == this.mMediaSeekbar) {
            this.mSoundSettingsViewModel.setSlidingVolumeType(3);
        } else if (xSlider == this.mMediaSeekBarOutSide) {
            this.mSoundSettingsViewModel.setSlidingVolumeType(Config.AVAS_STREAM);
        } else if (xSlider == this.mNavigationSeekbar) {
            this.mSoundSettingsViewModel.setSlidingVolumeType(9);
        } else if (xSlider == this.mSpeechSeekbar) {
            this.mSoundSettingsViewModel.setSlidingVolumeType(10);
        }
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStopTrackingTouch(XSlider xSlider) {
        Logs.d("xpsound onStopTrackingTouch:");
        doSliderFinalAction(xSlider);
        this.mSoundSettingsViewModel.setSlidingVolumeType(-1);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("xpsound fragment onHiddenChanged " + z + " " + this.mIsRegisterSound);
        if (!z) {
            refreshSoundData();
            return;
        }
        unRefreshSoundData();
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        refreshSoundData();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refreshTtsTips();
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$GMWQuHEZ61mwu_8SrZojd7Hun7o
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$onResume$16$SoundFragment();
            }
        }, 500L);
    }

    private void refreshTtsTips() {
        if (MicUtils.isMicrophoneMute()) {
            this.mTtsTips.setText(R.string.sound_speech_mic_set_tips);
            this.mTtsTips.setBackground(getContext().getDrawable(R.drawable.sound_tts_mic_tips_bg));
            return;
        }
        this.mTtsTips.setText(R.string.sound_speech_set_tips);
        this.mTtsTips.setBackground(getContext().getDrawable(R.drawable.sound_tts_tips_bg));
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    private void refreshSoundData() {
        if (this.mIsRegisterSound) {
            return;
        }
        SoundServerManager.getInstance().addSoundUIListener(this);
        restoreTabCarStatus();
        if (CarConfigHelper.hasMainDriverVIP()) {
            refreshHeadRestAudioMode(XpAccountManager.getInstance().getMainDriverExclusive());
        }
        this.mOpenDoorSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithDoorOpen());
        this.mDecreaseSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithNavigating());
        this.mGearRSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithGearR());
        this.mSystemSwitch.setChecked(XpAccountManager.getInstance().getSystemSoundEnable());
        this.mPositionSwitch.setChecked(this.mSoundSettingsViewModel.getSoundPositionEnable());
        this.mSoundSettingsViewModel.onStartVM();
        lambda$onResume$16$SoundFragment();
        this.mIsRegisterSound = true;
        refreshVolumeBySpeed();
        refreshOutsideLowSpeedSimulate();
        refreshOutsideMode();
        Logs.d("xpsound fragment fresh refreshSoundData ");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onSoundEffectChange(boolean z) {
        lambda$onResume$16$SoundFragment();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onHeadRestModeChange() {
        refreshHeadRestMode(XpAccountManager.getInstance().getMainDriverExclusive());
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XpThemeUtils.isThemeChanged(configuration)) {
            lambda$onResume$16$SoundFragment();
            refreshTtsTips();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshSoundEffectData */
    public void lambda$onResume$16$SoundFragment() {
        if (getContext() == null) {
            Logs.d("xpsound life not resume!");
        } else if (CarFunction.isSupportSoundEffectPreviewShow()) {
            if (CarFunction.isDSeries()) {
                this.mSoundEffectLine.setVisibility(8);
                this.mSoundEffectImage1.setVisibility(0);
                this.mSoundEffectImage2.setVisibility(8);
                this.mSoundEffectImage1.setImageDrawable(getContext().getDrawable(R.drawable.pic_xsound));
                int currentSoundEffectMode = this.mSoundSettingsViewModel.getCurrentSoundEffectMode();
                if (currentSoundEffectMode == 1) {
                    int soundEffectType = this.mSoundSettingsViewModel.getSoundEffectType(1);
                    Logs.d("xpsound refreshSoundEffectData mode:" + currentSoundEffectMode + " type:" + soundEffectType);
                    String soundEffectD21String = getSoundEffectD21String(soundEffectType);
                    if (!TextUtils.isEmpty(soundEffectD21String)) {
                        this.mSoundEffectTypeScene.setText(soundEffectD21String);
                        this.mSoundEffectTypeScene.setVisibility(0);
                        return;
                    }
                    this.mSoundEffectTypeScene.setVisibility(8);
                    return;
                }
                this.mSoundEffectTypeScene.setVisibility(8);
                return;
            }
            this.mSoundEffectLine.setVisibility(0);
            this.mSoundEffectTypeScene.setVisibility(0);
            this.mSoundEffectImage1.setVisibility(0);
            this.mSoundEffectImage2.setVisibility(8);
            int currentSoundEffectMode2 = this.mSoundSettingsViewModel.getCurrentSoundEffectMode();
            Logs.d("xpsound refreshSoundEffectData mode:" + currentSoundEffectMode2);
            if (currentSoundEffectMode2 == 1) {
                this.mSoundEffectImage1.setImageDrawable(getContext().getDrawable(R.drawable.pic_xsound));
                this.mSoundEffectImage2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dts));
                if (!CarFunction.isSupportDts() || CarConfigHelper.isLowSpeaker()) {
                    this.mSoundEffectImage2.setVisibility(8);
                } else if (CarConfigHelper.hasHifiSound() || CarConfigHelper.hasDtsScenes()) {
                    this.mSoundEffectImage2.setVisibility(0);
                } else {
                    this.mSoundEffectImage2.setVisibility(8);
                }
            } else if (currentSoundEffectMode2 == 3) {
                this.mSoundEffectImage1.setImageDrawable(getContext().getDrawable(R.drawable.pic_pic_dynaudio));
                this.mSoundEffectImage2.setVisibility(8);
            }
            this.mSoundEffectTypeScene.setText(getSoundEffectString(currentSoundEffectMode2));
        }
    }

    private String getSoundEffectD21String(int i) {
        if (i == getContext().getResources().getInteger(R.integer.sound_effect_style3)) {
            return getString(R.string.sound_effect3_text);
        }
        if (i == getContext().getResources().getInteger(R.integer.sound_effect_style2)) {
            return getString(R.string.sound_effect2_text);
        }
        if (i == getContext().getResources().getInteger(R.integer.sound_effect_style4)) {
            return getString(R.string.sound_effect4_text);
        }
        if (i == getContext().getResources().getInteger(R.integer.sound_effect_style1)) {
            return getString(R.string.sound_effect1_text);
        }
        if (i == getContext().getResources().getInteger(R.integer.sound_effect_style5)) {
            return getString(R.string.sound_effect5_text);
        }
        return i == getContext().getResources().getInteger(R.integer.sound_effect_style6) ? getString(R.string.sound_custom_text) : "";
    }

    private String getSoundEffectString(int i) {
        String str;
        String str2;
        String str3 = "";
        if (i != 1) {
            if (i == 3) {
                int soundEffectType = this.mSoundSettingsViewModel.getSoundEffectType(3);
                if (soundEffectType == SoundEffectValues.SOUND_STYLE_1_HIFI) {
                    str = getString(CarFunction.isSupportHifiNewEffectStyle() ? R.string.sound_original_text : R.string.sound_real_text);
                } else if (soundEffectType == SoundEffectValues.SOUND_STYLE_2_HIFI) {
                    str = getString(CarFunction.isSupportHifiNewEffectStyle() ? R.string.sound_dynamic_text : R.string.sound_soft_text);
                } else if (soundEffectType == SoundEffectValues.SOUND_STYLE_3_HIFI) {
                    str = getString(CarFunction.isSupportHifiNewEffectStyle() ? R.string.sound_soft2_text : R.string.sound_dynamic_text);
                } else if (soundEffectType == SoundEffectValues.SOUND_STYLE_4_HIFI) {
                    str = getString(CarFunction.isSupportHifiNewEffectStyle() ? R.string.sound_speak_text : R.string.sound_people_text);
                } else {
                    str = "";
                }
                str2 = getCustomText(3);
            }
            str = "";
            str2 = str;
        } else {
            int soundEffectType2 = this.mSoundSettingsViewModel.getSoundEffectType(1);
            if (CarFunction.isSupportRecentEffectMode()) {
                if (soundEffectType2 == 1) {
                    str = getString(R.string.se_type_title_original);
                } else if (soundEffectType2 == 2) {
                    str = getString(R.string.se_type_title_dynamic);
                } else if (soundEffectType2 == 3) {
                    str = getString(R.string.se_type_title_soft);
                } else if (soundEffectType2 == 4) {
                    str = getString(R.string.se_type_title_custom);
                } else {
                    Logs.d("getSoundEffectString, v2, unexpected type: " + soundEffectType2);
                    str = "";
                    str2 = str;
                }
                str2 = "";
            } else {
                if (CarConfigHelper.hasHifiSound()) {
                    if (soundEffectType2 == 2) {
                        str = getString(R.string.sound_hifi_text);
                    } else {
                        str = getString(R.string.sound_common_text);
                    }
                    str2 = getCustomText(1);
                } else {
                    switch (soundEffectType2) {
                        case 0:
                            str = getString(R.string.sound_original_text);
                            str2 = "";
                            break;
                        case 1:
                            str = getString(R.string.sound_common_text);
                            str2 = "";
                            break;
                        case 2:
                            str = getString(R.string.sound_people_text);
                            str2 = "";
                            break;
                        case 3:
                            str = getString(R.string.sound_jazz_text);
                            str2 = "";
                            break;
                        case 4:
                            str = getString(R.string.sound_rock_text);
                            str2 = "";
                            break;
                        case 5:
                            str = getString(R.string.sound_disco_text);
                            str2 = "";
                            break;
                        case 6:
                            str = getString(R.string.sound_classical_text);
                            str2 = "";
                            break;
                        case 7:
                            if (!CarConfigHelper.isLowSpeaker()) {
                                str = getString(R.string.sound_custom_text);
                                str2 = "";
                                break;
                            }
                        default:
                            str = "";
                            str2 = str;
                            break;
                    }
                }
                if (CarFunction.isSupportDts() && CarFunction.isSupportEffectScene()) {
                    int soundEffectScene = this.mSoundSettingsViewModel.getSoundEffectScene(1);
                    if (soundEffectScene == 1) {
                        str3 = getString(R.string.sound_scenes_theater);
                    } else if (soundEffectScene == 2) {
                        str3 = getString(R.string.sound_scenes_live);
                    } else if (soundEffectScene == 3) {
                        str3 = getString(R.string.sound_scenes_ktv);
                    } else if (soundEffectScene == 4) {
                        str3 = getString(R.string.sound_scenes_recording);
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(str3)) {
            str = str + " · " + str3;
        }
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        return str + " · " + str2;
    }

    private String getCustomText(int i) {
        if (this.mSoundSettingsViewModel.getEqualizerSwitch(i) == 1) {
            if (this.mSoundSettingsViewModel.getEqualizerCustomValue(i) == 2) {
                return this.mContext.getString(R.string.sound_effect_equalizer_custom2);
            }
            return this.mContext.getString(R.string.sound_effect_equalizer_custom1);
        }
        return this.mContext.getString(R.string.sound_effect_equalizer_no_work);
    }

    private void unRefreshSoundData() {
        if (this.mIsRegisterSound) {
            this.mHandler.removeCallbacks(this.mRunnableMaxTimeout);
            this.mSoundSettingsViewModel.onStopVM();
            this.mIsRegisterSound = false;
            SoundServerManager.getInstance().removeSoundUIListener(this);
            Logs.d("xpsound fragment fresh unRefreshSoundData ");
        }
    }

    private void dismissDialog() {
        HeadrestAudioModeDialog headrestAudioModeDialog = this.mHeadrestAudioModeDialog;
        if (headrestAudioModeDialog != null) {
            headrestAudioModeDialog.dismiss();
            this.mHeadrestAudioModeDialog = null;
        }
        XDialog xDialog = this.mVolumeOutsideShutdownSimulateDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mVolumeOutsideShutdownSimulateDialog = null;
        }
        XDialogPure xDialogPure = this.mOutsideModeDialog;
        if (xDialogPure != null) {
            xDialogPure.dismiss();
            this.mOutsideModeDialog = null;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (!isHidden() || this.mIsRegisterSound) {
            unRefreshSoundData();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
        this.mSoundSettingsViewModel.onDestroyVM();
        this.mHandler.removeCallbacks(this.mRunnableTimeout);
        this.mHandler.removeCallbacks(this.mRunnableMaxTimeout);
        CarSettingsManager.getInstance().removeVcuChangeListener(this.mGearChangeListener);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onAccountChanged() {
        Logs.d("XpAccountManager-xld sound onUpdateUIEvent ");
        this.mSystemSwitch.setChecked(XpAccountManager.getInstance().getSystemSoundEnable());
        refreshSafetySound(XpAccountManager.getInstance().getSafetyVolume(), "accountSync");
        if (CarConfigHelper.hasMainDriverVIP()) {
            refreshHeadRestMode(XpAccountManager.getInstance().getMainDriverExclusive());
        }
        this.mOpenDoorSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithDoorOpen());
        this.mGearRSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithGearR());
        this.mDecreaseSwitch.setChecked(XpAccountManager.getInstance().getVolumeDownWithNavigating());
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onSoundPositionEnable(boolean z) {
        this.mPositionSwitch.setChecked(z);
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
    public void onProgressChanged(XSlider xSlider, float f, String str, boolean z) {
        sliderProgressChanged(xSlider, f);
        doSliderFinalAction(xSlider);
    }

    private void sliderProgressChanged(XSlider xSlider, float f) {
        if (xSlider == this.mMediaSeekbar) {
            this.mSoundSettingsViewModel.setVolume(3, (int) f);
        } else if (xSlider == this.mMediaSeekBarOutSide) {
            this.mSoundSettingsViewModel.setVolume(Config.AVAS_STREAM, (int) f);
        } else if (xSlider == this.mSpeechSeekbar) {
            this.mSoundSettingsViewModel.setVolume(10, (int) f);
        } else if (xSlider == this.mNavigationSeekbar) {
            this.mSoundSettingsViewModel.setVolume(9, (int) f);
        }
    }

    private void doSliderFinalAction(XSlider xSlider) {
        if (xSlider == this.mMediaSeekbar) {
            this.mSoundSettingsViewModel.playSoundForStreamType(3);
        } else if (xSlider == this.mMediaSeekBarOutSide) {
            this.mSoundSettingsViewModel.playSoundForStreamType(Config.AVAS_STREAM);
        } else if (xSlider == this.mSpeechSeekbar) {
            this.mSoundSettingsViewModel.playSoundForStreamType(10);
        } else if (xSlider == this.mNavigationSeekbar) {
            this.mSoundSettingsViewModel.playSoundForStreamType(9);
        }
        XSlider xSlider2 = this.mMediaSeekbar;
        if (xSlider == xSlider2) {
            this.mSoundSettingsViewModel.setVolumeForce(3, (int) xSlider2.getIndicatorValue());
        } else if (xSlider == this.mMediaSeekBarOutSide) {
            this.mSoundSettingsViewModel.setVolumeForce(Config.AVAS_STREAM, (int) this.mMediaSeekBarOutSide.getIndicatorValue());
        } else {
            XSlider xSlider3 = this.mSpeechSeekbar;
            if (xSlider == xSlider3) {
                this.mSoundSettingsViewModel.setVolumeForce(10, (int) xSlider3.getIndicatorValue());
            } else {
                XSlider xSlider4 = this.mNavigationSeekbar;
                if (xSlider == xSlider4) {
                    this.mSoundSettingsViewModel.setVolumeForce(9, (int) xSlider4.getIndicatorValue());
                }
            }
        }
        updateVuiScene(this.sceneId, xSlider);
    }

    private void initVui() {
        if (this.mVuiHandler == null) {
            return;
        }
        this.mVuiHandler.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.SoundFragment.1
            @Override // java.lang.Runnable
            public void run() {
                SoundFragment.this.mSystemSwitch.setVuiLabel(SoundFragment.this.getString(R.string.sound_volume_system));
                SoundFragment.this.mPositionSwitch.setVuiLabel(SoundFragment.this.getString(R.string.sound_volume_position));
                SoundFragment.this.mDecreaseSwitch.setVuiLabel(SoundFragment.this.getString(R.string.sound_decrease_volume));
                SoundFragment.this.mOpenDoorSwitch.setVuiLabel(SoundFragment.this.getString(R.string.sound_open_door));
                SoundFragment.this.mGearRSwitch.setVuiLabel(SoundFragment.this.getString(R.string.sound_gear_r));
                SoundFragment.this.mMediaSeekbar.setVuiLabel(String.format(SoundFragment.this.getString(R.string.vui_label_multi_format), SoundFragment.this.getString(R.string.sound_volume_media), SoundFragment.this.getString(R.string.sound_volume_media_tip)));
                SoundFragment.this.mMediaSeekBarOutSide.setVuiLabel(String.format(SoundFragment.this.getString(R.string.vui_label_multi_format), SoundFragment.this.getString(R.string.sound_volume_media), SoundFragment.this.getString(R.string.sound_volume_media_tip_out_side)));
                SoundFragment.this.mSpeechSeekbar.setVuiLabel(String.format(SoundFragment.this.getString(R.string.vui_label_multi_format), SoundFragment.this.getString(R.string.sound_volume_speech), SoundFragment.this.getString(R.string.sound_volume_speech_tip)));
                SoundFragment.this.mNavigationSeekbar.setVuiLabel(String.format(SoundFragment.this.getString(R.string.vui_label_multi_format), SoundFragment.this.getString(R.string.sound_volume_tts), SoundFragment.this.getString(R.string.sound_volume_tts_tip)));
                SoundFragment soundFragment = SoundFragment.this;
                soundFragment.supportFeedback(soundFragment.mTabLayoutCar);
                if (CarConfigHelper.hasMainDriverVIP()) {
                    SoundFragment soundFragment2 = SoundFragment.this;
                    soundFragment2.setVuiElementUnSupport(soundFragment2.mHeadRestAudioModeView, false);
                    return;
                }
                SoundFragment soundFragment3 = SoundFragment.this;
                soundFragment3.setVuiElementUnSupport(soundFragment3.mHeadRestAudioModeView, true);
            }
        });
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return new String[]{"/sound/driver_vip"};
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(String str) {
        if ("/sound/driver_vip".equals(str)) {
            showHeadrestAudioMode();
        }
        super.onPageDirectShow(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onTtsTipsChange() {
        refreshTtsTips();
    }

    private void refreshVolumeBySpeed() {
        if (CarFunction.isSupportSoundVolumeAdjustBySpeed() && CarConfigHelper.hasAMP()) {
            this.mTabVolumeBySpeed.selectTab(Math.max(0, SoundManager.getInstance().getSoundSpeedLinkLevel() - 1));
        }
    }

    private void refreshOutsideLowSpeedSimulate() {
        if (CarFunction.isSupportSoundOutsideLowSpeedSimulate()) {
            this.mTabVolumeOutsideLowSpeedSimulate.selectTab(Math.max(0, CarSettingsManager.getInstance().getAvasLowSpeedSound()));
        }
    }

    private void refreshOutsideMode() {
        if (CarFunction.isSupportAvasSpeaker()) {
            boolean avasSpeakerSw = SoundManager.getInstance().getAvasSpeakerSw();
            this.mVolumeMedia.setVisibility(!avasSpeakerSw ? 0 : 8);
            this.mVolumeMediaOutside.setVisibility(avasSpeakerSw ? 0 : 8);
            this.mSoundOutsideModeStatusText.setText(getString(SoundManager.getInstance().getAvasSpeakerSw() ? R.string.btn_on : R.string.btn_off));
        }
    }

    private void showVolumeOutSideSimulateShutdownDialog() {
        if (this.mVolumeOutsideShutdownSimulateDialog == null) {
            this.mVolumeOutsideShutdownSimulateDialog = new XDialog(getContext());
            this.mVolumeOutsideShutdownSimulateDialog.setTitle(getString(R.string.sound_outside_low_speed_simulate)).setMessage(getString(R.string.sound_outside_low_speed_simulate_tips)).setPositiveButton(getString(R.string.confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$ZqkXXHK-Bu1IOYM0eMDMEMaQgZE
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    SoundFragment.this.lambda$showVolumeOutSideSimulateShutdownDialog$17$SoundFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
        }
        this.mVolumeOutsideShutdownSimulateDialog.show();
    }

    public /* synthetic */ void lambda$showVolumeOutSideSimulateShutdownDialog$17$SoundFragment(XDialog xDialog, int i) {
        if (CarSettingsManager.getInstance().setAvasLowSpeedSound(0)) {
            this.mTabVolumeOutsideLowSpeedSimulate.selectTab(0);
        }
    }

    private void showOutsideModeDialog() {
        if (this.mOutsideModeDialog == null) {
            this.mOutsideModeDialog = new XDialogPure(getContext());
            this.mOutsideModeDialog.setContentView(R.layout.sound_outside_mode_dialog);
            this.mOutsideModeDialog.getDialog().findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$sBrFnA2xoKzY2k_9CXGEVEplIEI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundFragment.this.lambda$showOutsideModeDialog$18$SoundFragment(view);
                }
            });
        }
        final XSwitch xSwitch = (XSwitch) this.mOutsideModeDialog.getDialog().findViewById(R.id.sound_outside_mode).findViewById(R.id.x_list_action_switch);
        xSwitch.setChecked(SoundManager.getInstance().getAvasSpeakerSw());
        xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$ko0og-Sm6ETWy96D4rPqYVcq5_c
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SoundFragment.this.lambda$showOutsideModeDialog$19$SoundFragment(compoundButton, z);
            }
        });
        xSwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$eXs5cKDU4ZO-Jqc3kMib25TjG-8
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return SoundFragment.lambda$showOutsideModeDialog$20(view, z);
            }
        });
        final IVcuChangeListener iVcuChangeListener = new IVcuChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$ki-_Wnu5ice1eLA3ZEeOZ2gBHWs
            @Override // com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener
            public final void onValueChange(int i, int i2) {
                SoundFragment.lambda$showOutsideModeDialog$22(XSwitch.this, i, i2);
            }
        };
        CarSettingsManager.getInstance().addVcuChangeListener(iVcuChangeListener);
        this.mOutsideModeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$C9DPlchQ800TQXeRL_vIsacgpdA
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CarSettingsManager.getInstance().removeVcuChangeListener(IVcuChangeListener.this);
            }
        });
        this.mOutsideModeDialog.show();
    }

    public /* synthetic */ void lambda$showOutsideModeDialog$18$SoundFragment(View view) {
        this.mOutsideModeDialog.cancel();
    }

    public /* synthetic */ void lambda$showOutsideModeDialog$19$SoundFragment(CompoundButton compoundButton, boolean z) {
        SoundManager.getInstance().setAvasSpeakerSw(z);
        refreshOutsideMode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$showOutsideModeDialog$20(View view, boolean z) {
        if (!((XSwitch) view).isFromUser() || CarSettingsManager.getInstance().isCarGearP()) {
            return false;
        }
        XToast.showShort(R.string.display_region_park_toast);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showOutsideModeDialog$22(final XSwitch xSwitch, int i, int i2) {
        if (i != 557847045 || i2 == 4) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$zcCS6RY_SOMJgVU0hwC8BegOTrM
            @Override // java.lang.Runnable
            public final void run() {
                XSwitch.this.setChecked(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onGearChange(int i, int i2) {
        if (i != 557847045 || i2 == 4) {
            return;
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$SoundFragment$3xJRtcR9o6lJSy8IBIRphFoBGac
            @Override // java.lang.Runnable
            public final void run() {
                SoundFragment.this.lambda$onGearChange$24$SoundFragment();
            }
        });
    }

    public /* synthetic */ void lambda$onGearChange$24$SoundFragment() {
        if (SoundManager.getInstance().getAvasSpeakerSw()) {
            SoundManager.getInstance().setAvasSpeakerSw(false);
            if (isResumed()) {
                refreshOutsideMode();
            }
            XToast.show(R.string.sound_outside_mode_quit_prompt);
        }
    }
}
