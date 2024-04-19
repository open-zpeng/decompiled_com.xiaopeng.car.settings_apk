package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settings.utils.TimeZoneUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.LocaleUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.DisplayServerManager;
import com.xiaopeng.car.settingslibrary.interfaceui.IDisplayServerListener;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment;
import com.xiaopeng.car.settingslibrary.ui.widget.TimeZoneChooseDialog;
import com.xiaopeng.car.settingslibrary.vm.datetime.DatetimeSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.display.DisplaySettingsViewModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XNumberPickerDialog;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XNumberPicker;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.slider.XSlider;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
/* loaded from: classes.dex */
public class DisplayFragment extends BaseFragment implements IDisplayServerListener {
    private static final int FONT_SIZE_TIMEOUT = 800;
    private XListTwo mBrightnessAutoListTwo;
    private XListTwo mBrightnessAutoListTwoMain;
    private XListTwo mBrightnessAutoListTwoPsn;
    private XImageButton mBrightnessTips;
    private XImageButton mBrightnessTipsMain;
    private XImageButton mBrightnessTipsPsn;
    private XListMultiple mCenterLinkingLayout;
    private XButton mCleanModeButton;
    private IntervalControl mCleanModeIntervalControl;
    private XListSingle mClearLayout;
    private XButton mClosePsnScreen;
    private View mClosePsnScreenContainer;
    private XDialog mConfirmDialog;
    private XListTwo mCurrentLanguage;
    private XListSingle mCurrentRegion;
    private XDialog mDarkStateDialog;
    private DatetimeSettingsViewModel mDatetimeSettingsViewModel;
    private DisplaySettingsViewModel mDisplaySettingsViewModel;
    private XButton mEditLanguage;
    private XButton mEditRegionBtn;
    private IntervalControl mIntervalControl;
    private View mLanguageLayout;
    private XNumberPickerDialog mLanguagePicker;
    private XListMultiple mListTime;
    private XListMultiple mMeterLinkingLayout;
    private XDialog mNEDCDialog;
    private XImageView mRegionIcon;
    private View mRegionLayout;
    private XNumberPickerDialog mRegionPickerDialog;
    private XListTwo mRegionSample;
    private XSlider mSeekBarCenter;
    private XSlider mSeekBarMeter;
    private XSwitch mSwitchBrightnessAuto;
    private XSwitch mSwitchBrightnessAutoMain;
    private XSwitch mSwitchBrightnessAutoPsn;
    private XTabLayout mTabLayoutDay;
    private XTabLayout mTabLayoutFontSize;
    private XTabLayout mTabLayoutTime;
    private XListTwo mTimeZoneAutomatic;
    private XSwitch mTimeZoneAutomaticSwitch;
    private TimeZoneChangeBrocastReceiver mTimeZoneChangeBrocastReceiver;
    private TimeZoneChooseDialog mTimeZoneChooseDialog;
    private XListSingle mTimeZoneManual;
    private XButton mTimeZoneManualBtn;
    private XButton mWaitModeBtn;
    private XDialog mWaitModeDialog;
    private IntervalControl mWaitModeIntervalControl;
    private XListSingle mWaitModeLayout;
    private ViewStub mWaitModeStub;
    private XImageButton mWaitModeTips;
    private String mBrightnessText = "";
    private String mBrightnessSubText = "";
    private String mBrightnessTextMain = "";
    private String mBrightnessSubTextMain = "";
    private String mBrightnessTextPsn = "";
    private String mBrightnessSubTextPsn = "";
    private Handler mHandler = new Handler();
    private boolean mIsRegisterFresh = false;
    private int mAutoBrightnessCarFeature = -1;
    private boolean mSupportPsnScreen = CarFunction.isSupportDoubleScreen();
    private Runnable mFontRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.1
        @Override // java.lang.Runnable
        public void run() {
            DisplayFragment.this.mTabLayoutFontSize.setEnabled(true);
        }
    };
    private XSlider.SliderProgressListener mSeekBarCenterListener = new XSlider.SliderProgressListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.6
        int currentProgress;
        boolean mInPsnScreen;

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onProgressChanged(XSlider xSlider, float f, String str) {
            int i = (int) f;
            this.currentProgress = i;
            if (!DisplayFragment.this.mSupportPsnScreen) {
                DisplayFragment.this.mDisplaySettingsViewModel.setScreenBrightness(i);
            } else if (this.mInPsnScreen) {
                DisplayFragment.this.mDisplaySettingsViewModel.setPsnScreenBrightness(i);
            } else {
                DisplayFragment.this.mDisplaySettingsViewModel.setScreenBrightness(i);
            }
        }

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onStartTrackingTouch(XSlider xSlider) {
            Logs.d("xpdisplay onStartTrackingTouch mSeekBarCenterListener");
            if (!DisplayFragment.this.mSupportPsnScreen) {
                DisplayFragment.this.mDisplaySettingsViewModel.unregisterBrightness();
                return;
            }
            this.mInPsnScreen = Utils.isCurrentAppInPsnScreen(xSlider.getContext());
            DisplayFragment.this.mDisplaySettingsViewModel.unregisterPsnBrightness();
            DisplayFragment.this.mDisplaySettingsViewModel.unregisterBrightness();
        }

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onStopTrackingTouch(XSlider xSlider) {
            Logs.d("xpdisplay onStopTrackingTouch mSeekBarCenterListener");
            if (!DisplayFragment.this.mSupportPsnScreen) {
                DisplayFragment.this.mDisplaySettingsViewModel.setScreenBrightness(this.currentProgress);
                DisplayFragment.this.mDisplaySettingsViewModel.registerBrightness();
            } else {
                if (this.mInPsnScreen) {
                    DisplayFragment.this.mDisplaySettingsViewModel.setPsnScreenBrightness(this.currentProgress);
                } else {
                    DisplayFragment.this.mDisplaySettingsViewModel.setScreenBrightness(this.currentProgress);
                }
                DisplayFragment.this.mDisplaySettingsViewModel.registerBrightness();
                DisplayFragment.this.mDisplaySettingsViewModel.registerPsnBrightness();
            }
            DisplayFragment.this.updateBrightnessSliderScene(xSlider, false);
        }
    };
    private XSlider.ProgressChangeListener mSliderCenterListener = new XSlider.ProgressChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.7
        @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
        public void onProgressChanged(XSlider xSlider, float f, String str, boolean z) {
            DisplayFragment.this.mDisplaySettingsViewModel.setScreenBrightness((int) f);
            DisplayFragment.this.updateBrightnessSliderScene(xSlider, true);
        }
    };
    private XSlider.ProgressChangeListener mSliderMeterListener = new XSlider.ProgressChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.8
        @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
        public void onProgressChanged(XSlider xSlider, float f, String str, boolean z) {
            DisplayFragment.this.mDisplaySettingsViewModel.notifyMeterBrightnessChanged((int) f, true);
            DisplayFragment.this.updateBrightnessSliderScene(xSlider, true);
        }
    };
    private XSlider.SliderProgressListener mSeekBarMeterListener = new XSlider.SliderProgressListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.9
        int currentProgress;

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onProgressChanged(XSlider xSlider, float f, String str) {
            int i = (int) f;
            this.currentProgress = i;
            DisplayFragment.this.mDisplaySettingsViewModel.notifyMeterBrightnessChanged(i, false);
        }

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onStartTrackingTouch(XSlider xSlider) {
            Logs.d("xpdisplay onStartTrackingTouch mSeekBarMeterListener");
            DisplayFragment.this.mDisplaySettingsViewModel.unregisterICMBrightness();
            DisplayFragment.this.mDisplaySettingsViewModel.registerCarCallback();
        }

        @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
        public void onStopTrackingTouch(XSlider xSlider) {
            Logs.d("xpdisplay onStopTrackingTouch mSeekBarMeterListener");
            DisplayFragment.this.mDisplaySettingsViewModel.notifyMeterBrightnessChanged(this.currentProgress, true);
            DisplayFragment.this.mDisplaySettingsViewModel.registerICMBrightness(true);
            DisplayFragment.this.mDisplaySettingsViewModel.releaseCarCallback();
            DisplayFragment.this.updateBrightnessSliderScene(xSlider, false);
        }
    };
    private View.OnClickListener mClearListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$97CQzh9Ey3Ay_tltGP_9Mf9LNaI
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DisplayFragment.this.lambda$new$28$DisplayFragment(view);
        }
    };
    private View.OnClickListener mLanguageChooseListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$L9OieGlc98Jzl75uLOL2470Ip4I
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DisplayFragment.this.lambda$new$30$DisplayFragment(view);
        }
    };
    private View.OnClickListener mRegionChooseListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$WEbOOGhKnixeAEgLZkTlP-oK9TY
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DisplayFragment.this.lambda$new$34$DisplayFragment(view);
        }
    };
    private View.OnClickListener mWaitModeListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$CQHqDGG3O-UPcf5mJ5JbWJEZmGA
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DisplayFragment.this.lambda$new$35$DisplayFragment(view);
        }
    };
    private CompoundButton.OnCheckedChangeListener mSwitchListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.13
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            DisplayFragment displayFragment = DisplayFragment.this;
            displayFragment.updateVuiScene(displayFragment.sceneId, compoundButton);
            if (((XSwitch) compoundButton).isFromUser() || DisplayFragment.this.isVuiAction(compoundButton)) {
                if (compoundButton == DisplayFragment.this.mSwitchBrightnessAuto) {
                    int i = DisplayFragment.this.mAutoBrightnessCarFeature;
                    if (i == -1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B002", z);
                    } else if (i == 0 || i == 1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B001", z);
                    }
                    DisplayFragment.this.mDisplaySettingsViewModel.setAdaptiveBrightness(z);
                }
                if (compoundButton == DisplayFragment.this.mSwitchBrightnessAutoMain) {
                    int i2 = DisplayFragment.this.mAutoBrightnessCarFeature;
                    if (i2 == -1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B002", z);
                    } else if (i2 == 0 || i2 == 1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B001", z);
                    }
                    DisplayFragment.this.mDisplaySettingsViewModel.setMainAutoBrightnessModeEnable(z);
                }
                if (compoundButton == DisplayFragment.this.mSwitchBrightnessAutoPsn) {
                    int i3 = DisplayFragment.this.mAutoBrightnessCarFeature;
                    if (i3 == -1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B002", z);
                    } else if (i3 == 0 || i3 == 1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B001", z);
                    }
                    DisplayFragment.this.mDisplaySettingsViewModel.setPsnAutoBrightnessModeEnable(z);
                }
            }
        }
    };
    private XTabLayout.OnTabChangeListener mOnTabChangeListener = new AnonymousClass14();

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_display;
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mHandler.removeCallbacks(this.mFontRunnable);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRootView = view.findViewById(R.id.scrollView);
        View findViewById = view.findViewById(R.id.display_theme_dayNight);
        View findViewById2 = view.findViewById(R.id.display_font_size);
        this.mClearLayout = (XListSingle) view.findViewById(R.id.display_btn_clear);
        this.mListTime = (XListMultiple) view.findViewById(R.id.display_time_format);
        this.mMeterLinkingLayout = (XListMultiple) view.findViewById(R.id.display_brightness_meter);
        this.mCenterLinkingLayout = (XListMultiple) view.findViewById(R.id.display_brightness_center);
        this.mSeekBarCenter = (XSlider) this.mCenterLinkingLayout.findViewById(R.id.x_list_action_slider);
        this.mSeekBarCenter.setSliderProgressListener(this.mSeekBarCenterListener);
        this.mSeekBarCenter.setProgressChangeListener(this.mSliderCenterListener);
        this.mSeekBarMeter = (XSlider) this.mMeterLinkingLayout.findViewById(R.id.x_list_action_slider);
        this.mSeekBarMeter.setSliderProgressListener(this.mSeekBarMeterListener);
        this.mSeekBarMeter.setProgressChangeListener(this.mSliderMeterListener);
        this.mIntervalControl = new IntervalControl("auto-brightness");
        this.mAutoBrightnessCarFeature = CarConfigHelper.autoBrightness();
        this.mBrightnessAutoListTwo = (XListTwo) view.findViewById(R.id.display_brightness_auto);
        this.mSwitchBrightnessAuto = (XSwitch) this.mBrightnessAutoListTwo.findViewById(R.id.x_list_action_switch);
        this.mBrightnessTips = (XImageButton) this.mBrightnessAutoListTwo.findViewById(R.id.x_list_action_button_icon);
        this.mBrightnessTips.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mBrightnessTips.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$N3Svo-9Q0jYxBDS1SmA1_xALW68
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DisplayFragment.this.lambda$initView$0$DisplayFragment(view2);
            }
        });
        this.mSwitchBrightnessAuto.setOnCheckedChangeListener(this.mSwitchListener);
        this.mSwitchBrightnessAuto.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && DisplayFragment.this.interceptBrightness((XSwitch) view2, false);
            }
        });
        this.mBrightnessAutoListTwoMain = (XListTwo) view.findViewById(R.id.display_brightness_auto_main);
        this.mSwitchBrightnessAutoMain = (XSwitch) this.mBrightnessAutoListTwoMain.findViewById(R.id.x_list_action_switch);
        this.mBrightnessTipsMain = (XImageButton) this.mBrightnessAutoListTwoMain.findViewById(R.id.x_list_action_button_icon);
        this.mBrightnessTipsMain.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mBrightnessTipsMain.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$bKY3kmruhyOje9WxcbQfJ5fV30k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DisplayFragment.this.lambda$initView$1$DisplayFragment(view2);
            }
        });
        this.mSwitchBrightnessAutoMain.setOnCheckedChangeListener(this.mSwitchListener);
        this.mSwitchBrightnessAutoMain.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && DisplayFragment.this.interceptBrightness((XSwitch) view2, false);
            }
        });
        this.mBrightnessAutoListTwoPsn = (XListTwo) view.findViewById(R.id.display_brightness_auto_psn);
        this.mSwitchBrightnessAutoPsn = (XSwitch) this.mBrightnessAutoListTwoPsn.findViewById(R.id.x_list_action_switch);
        this.mBrightnessTipsPsn = (XImageButton) this.mBrightnessAutoListTwoPsn.findViewById(R.id.x_list_action_button_icon);
        this.mBrightnessTipsPsn.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
        this.mBrightnessTipsPsn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$650aHBLBpCM6MT1QatmzFRiJVR4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DisplayFragment.this.lambda$initView$2$DisplayFragment(view2);
            }
        });
        this.mSwitchBrightnessAutoPsn.setOnCheckedChangeListener(this.mSwitchListener);
        this.mSwitchBrightnessAutoPsn.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && DisplayFragment.this.interceptBrightness((XSwitch) view2, false);
            }
        });
        this.mCleanModeIntervalControl = new IntervalControl("clean_mode");
        this.mTabLayoutDay = (XTabLayout) findViewById.findViewById(R.id.display_day_night_segment);
        this.mTabLayoutDay.setOnTabChangeListener(this.mOnTabChangeListener);
        Locale locale = Locale.getDefault();
        this.mLanguageLayout = view.findViewById(R.id.display_language);
        if (CarFunction.isSupportLanguageSet()) {
            this.mLanguageLayout.setVisibility(0);
            this.mCurrentLanguage = (XListTwo) view.findViewById(R.id.display_current_language);
            this.mEditLanguage = (XButton) this.mCurrentLanguage.findViewById(R.id.x_list_action_button);
            this.mEditLanguage.setText(R.string.edit);
            this.mCurrentLanguage.setText(getString(R.string.display_current_language, getLanguageName(locale.getLanguage())));
            this.mEditLanguage.setOnClickListener(this.mLanguageChooseListener);
        } else {
            this.mLanguageLayout.setVisibility(8);
        }
        this.mRegionLayout = view.findViewById(R.id.display_region);
        if (CarFunction.isSupportRegionSet()) {
            this.mRegionLayout.setVisibility(0);
            this.mCurrentRegion = (XListSingle) view.findViewById(R.id.display_cur_region);
            this.mEditRegionBtn = (XButton) this.mCurrentRegion.findViewById(R.id.x_list_action_button);
            this.mEditRegionBtn.setText(R.string.edit);
            this.mEditRegionBtn.setOnClickListener(this.mRegionChooseListener);
            this.mCurrentRegion.setText(getString(R.string.display_current_region, getCountryName(locale.getCountry())));
            this.mRegionSample = (XListTwo) view.findViewById(R.id.display_sample_region);
            this.mRegionSample.setTextSub(getString(R.string.display_format_sample, LocaleUtils.getDateSample(locale), LocaleUtils.getNumberSample(locale)));
            this.mRegionIcon = (XImageView) this.mRegionSample.findViewById(R.id.x_list_action_icon);
            this.mRegionIcon.setImageResource(R.drawable.ic_mid_earth);
            this.mTimeZoneAutomatic = (XListTwo) this.mRootView.findViewById(R.id.display_timezone_auto);
            this.mTimeZoneAutomaticSwitch = (XSwitch) this.mTimeZoneAutomatic.findViewById(R.id.x_list_action_switch);
            this.mTimeZoneAutomaticSwitch.setChecked(CarSettingsManager.getInstance().isNitzTimeZoneAutomatic(getContext()));
            this.mTimeZoneAutomaticSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$jWoZ3df9LsJnhsxbuJduA13yyxU
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    DisplayFragment.this.lambda$initView$4$DisplayFragment(compoundButton, z);
                }
            });
            this.mTimeZoneManual = (XListSingle) this.mRootView.findViewById(R.id.display_timezone_manual);
            this.mTimeZoneManual.setEnabled(!this.mTimeZoneAutomaticSwitch.isChecked());
            this.mTimeZoneManualBtn = (XButton) this.mTimeZoneManual.findViewById(R.id.x_list_action_button);
            this.mTimeZoneManualBtn.setText(R.string.edit);
            this.mTimeZoneManualBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$epnpqY-xlA-5-Z9UXLpP1w-dUgY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DisplayFragment.this.lambda$initView$5$DisplayFragment(view2);
                }
            });
        } else {
            this.mRegionLayout.setVisibility(8);
        }
        this.mCleanModeButton = (XButton) this.mClearLayout.findViewById(R.id.x_list_action_button);
        this.mCleanModeButton.setText(R.string.enter);
        this.mCleanModeButton.setOnClickListener(this.mClearListener);
        if (CarFunction.isSupportWaitMode()) {
            this.mWaitModeStub = (ViewStub) view.findViewById(R.id.display_wait);
            this.mWaitModeLayout = (XListSingle) this.mWaitModeStub.inflate().findViewById(R.id.display_wait_mode);
            this.mWaitModeBtn = (XButton) this.mWaitModeLayout.findViewById(R.id.x_list_action_button);
            this.mWaitModeBtn.setText(R.string.enter);
            this.mWaitModeBtn.setOnClickListener(this.mWaitModeListener);
            this.mWaitModeTips = (XImageButton) this.mWaitModeLayout.findViewById(R.id.x_list_action_button_icon);
            this.mWaitModeTips.setImageDrawable(getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            this.mWaitModeTips.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$Zd0KvOTT5sN-ln6qhw2P6FuSJjM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DisplayFragment.this.lambda$initView$6$DisplayFragment(view2);
                }
            });
            this.mWaitModeIntervalControl = new IntervalControl("wait_mode");
        }
        this.mTabLayoutFontSize = (XTabLayout) findViewById2.findViewById(R.id.display_font_segment);
        this.mTabLayoutFontSize.setOnTabChangeListener(this.mOnTabChangeListener);
        this.mTabLayoutTime = (XTabLayout) this.mListTime.findViewById(R.id.display_time_segment);
        this.mTabLayoutTime.setOnTabChangeListener(this.mOnTabChangeListener);
        int i = this.mAutoBrightnessCarFeature;
        if (i == -1) {
            this.mBrightnessTips.setVisibility(0);
            this.mBrightnessText = getContext().getString(R.string.display_brightness_auto_low_conf_tip);
            this.mBrightnessSubText = getContext().getString(R.string.display_brightness_low_config_tips);
            this.mBrightnessTipsMain.setVisibility(0);
            this.mBrightnessTextMain = getContext().getString(R.string.display_brightness_auto_low_conf_tip_main);
            this.mBrightnessSubTextMain = getContext().getString(R.string.display_brightness_low_config_tips);
            this.mBrightnessTipsPsn.setVisibility(0);
            this.mBrightnessTextPsn = getContext().getString(R.string.display_brightness_auto_low_conf_tip_psn);
            this.mBrightnessSubTextPsn = getContext().getString(R.string.display_brightness_low_config_tips);
        } else if (i == 0) {
            this.mBrightnessTips.setVisibility(8);
            this.mBrightnessText = getContext().getString(R.string.display_brightness_auto);
            this.mBrightnessSubText = getContext().getString(R.string.display_brightness_auto_tip);
            this.mBrightnessTipsMain.setVisibility(8);
            this.mBrightnessTextMain = getContext().getString(R.string.display_brightness_auto_main);
            this.mBrightnessSubTextMain = getContext().getString(R.string.display_brightness_auto_tip);
            this.mBrightnessTipsPsn.setVisibility(8);
            this.mBrightnessTextPsn = getContext().getString(R.string.display_brightness_auto_psn);
            this.mBrightnessSubTextPsn = getContext().getString(R.string.display_brightness_auto_tip);
        } else if (i == 1) {
            this.mBrightnessTips.setVisibility(8);
            this.mBrightnessText = getContext().getString(R.string.display_brightness_auto);
            this.mBrightnessSubText = getContext().getString(R.string.display_brightness_auto_ciu_tip);
            this.mBrightnessTipsMain.setVisibility(8);
            this.mBrightnessTextMain = getContext().getString(R.string.display_brightness_auto_main);
            this.mBrightnessSubTextMain = getContext().getString(R.string.display_brightness_auto_ciu_tip);
            this.mBrightnessTipsPsn.setVisibility(8);
            this.mBrightnessTextPsn = getContext().getString(R.string.display_brightness_auto_psn);
            this.mBrightnessSubTextPsn = getContext().getString(R.string.display_brightness_auto_ciu_tip);
        }
        this.mBrightnessAutoListTwo.setText(this.mBrightnessText);
        this.mBrightnessAutoListTwo.setTextSub(this.mBrightnessSubText);
        this.mBrightnessAutoListTwoMain.setText(this.mBrightnessTextMain);
        this.mBrightnessAutoListTwoMain.setTextSub(this.mBrightnessSubTextMain);
        this.mBrightnessAutoListTwoPsn.setText(this.mBrightnessTextPsn);
        this.mBrightnessAutoListTwoPsn.setTextSub(this.mBrightnessSubTextPsn);
        this.mClosePsnScreenContainer = view.findViewById(R.id.display_brightness_close_psn_screen_container);
        this.mClosePsnScreen = (XButton) this.mClosePsnScreenContainer.findViewById(R.id.display_brightness_close_psn_screen);
        this.mClosePsnScreen.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Utils.setPsnScreenOn(view2.getContext(), false);
            }
        });
        initVui();
        if (this.mSupportPsnScreen) {
            this.mBrightnessAutoListTwo.setVisibility(8);
            this.mBrightnessAutoListTwoMain.setVisibility(0);
            this.mBrightnessAutoListTwoPsn.setVisibility(0);
            this.mClosePsnScreenContainer.setVisibility(0);
            return;
        }
        this.mBrightnessAutoListTwo.setVisibility(0);
        this.mBrightnessAutoListTwoMain.setVisibility(8);
        this.mBrightnessAutoListTwoPsn.setVisibility(8);
        this.mClosePsnScreenContainer.setVisibility(8);
    }

    public /* synthetic */ void lambda$initView$0$DisplayFragment(View view) {
        showDarkBrightnessDialog();
    }

    public /* synthetic */ void lambda$initView$1$DisplayFragment(View view) {
        showDarkBrightnessDialog();
    }

    public /* synthetic */ void lambda$initView$2$DisplayFragment(View view) {
        showDarkBrightnessDialog();
    }

    public /* synthetic */ void lambda$initView$4$DisplayFragment(CompoundButton compoundButton, boolean z) {
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            XToast.showShort(R.string.display_region_park_toast);
            compoundButton.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$kcMWwqMfaaMBOh0hakTaGuUaBdQ
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFragment.this.lambda$null$3$DisplayFragment();
                }
            });
            return;
        }
        this.mTimeZoneManual.setEnabled(!z);
        if (z) {
            CarSettingsManager.getInstance().setNitzTimeZoneAutomatic();
        } else {
            CarSettingsManager.getInstance().setNitzTimeZone(TimeZone.getDefault().getID());
        }
        updateTimeZoneUi();
    }

    public /* synthetic */ void lambda$null$3$DisplayFragment() {
        this.mTimeZoneAutomaticSwitch.setChecked(CarSettingsManager.getInstance().isNitzTimeZoneAutomatic(getContext()));
    }

    public /* synthetic */ void lambda$initView$5$DisplayFragment(View view) {
        showTimeZoneChoose();
    }

    public /* synthetic */ void lambda$initView$6$DisplayFragment(View view) {
        showWaitModeDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
        return (view == this.mSwitchBrightnessAuto || view == this.mSwitchBrightnessAutoMain || view == this.mSwitchBrightnessAutoPsn) && interceptBrightness((XSwitch) view, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptBrightness(XSwitch xSwitch, boolean z) {
        IntervalControl intervalControl = this.mIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently(1000)) {
            Logs.d("onInterceptCheck isFrequently");
            if (z) {
                isVuiAction(xSwitch);
            }
            return true;
        } else if (this.mAutoBrightnessCarFeature == 0) {
            boolean z2 = !xSwitch.isChecked();
            Logs.d("onInterceptCheck b:" + z2);
            if (z2) {
                int nedcSwitchStatus = this.mDisplaySettingsViewModel.getNedcSwitchStatus();
                Logs.d("xpdisplay getNedcSwitchStatus:" + nedcSwitchStatus);
                if (nedcSwitchStatus == 1 || nedcSwitchStatus == 4 || nedcSwitchStatus == 5) {
                    showNEDCDialog(R.string.nedc_mode_dialog_title, R.string.nedc_mode_dialog_tips);
                    if (z) {
                        vuiFeedback(xSwitch, nedcSwitchStatus, getString(R.string.nedc_mode_dialog_tips));
                        isVuiAction(xSwitch);
                    }
                    return true;
                } else if (nedcSwitchStatus == 3) {
                    showNEDCDialog(R.string.nedc_mode_turning_off_dialog_title, R.string.nedc_mode_turning_off_dialog_tips);
                    if (z) {
                        vuiFeedback(xSwitch, nedcSwitchStatus, getString(R.string.nedc_mode_turning_off_dialog_tips));
                        isVuiAction(xSwitch);
                    }
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private void showWaitModeDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mWaitModeDialog == null) {
            this.mWaitModeDialog = new XDialog(getContext());
            this.mWaitModeDialog.setPositiveButton(getString(R.string.dialog_know), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$pT7WLMRmu3EGKgwInOP8BQLA6kQ
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DisplayFragment.this.lambda$showWaitModeDialog$7$DisplayFragment(xDialog, i);
                }
            });
        }
        this.mWaitModeDialog.setTitle(R.string.display_wait_mode);
        this.mWaitModeDialog.setMessage(R.string.display_wait_mode_tips);
        this.mWaitModeDialog.show();
        VuiManager.instance().initVuiDialog(this.mWaitModeDialog, getContext(), VuiManager.SCENE_WAIT_MODE_DIALOG);
    }

    public /* synthetic */ void lambda$showWaitModeDialog$7$DisplayFragment(XDialog xDialog, int i) {
        this.mWaitModeDialog.dismiss();
    }

    private void showDarkBrightnessDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mDarkStateDialog == null) {
            this.mDarkStateDialog = new XDialog(getContext(), R.style.XDialogView_Large);
            this.mDarkStateDialog.setPositiveButton(getString(R.string.dialog_know), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$_BmfU_LMd-zZyfiW5kskDNAYXaY
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DisplayFragment.this.lambda$showDarkBrightnessDialog$8$DisplayFragment(xDialog, i);
                }
            });
        }
        this.mDarkStateDialog.setTitle(R.string.display_brightness_auto_low_conf_tip);
        this.mDarkStateDialog.setMessage(R.string.display_dark_brightness_tips);
        this.mDarkStateDialog.setIcon(R.drawable.pic_dark_light);
        this.mDarkStateDialog.show();
        VuiManager.instance().initVuiDialog(this.mDarkStateDialog, getContext(), VuiManager.SCENE_DARK_BRIGHTNESS_DIALOG);
    }

    public /* synthetic */ void lambda$showDarkBrightnessDialog$8$DisplayFragment(XDialog xDialog, int i) {
        this.mDarkStateDialog.dismiss();
    }

    private void showNEDCDialog(int i, int i2) {
        if (getContext() == null) {
            return;
        }
        if (this.mNEDCDialog == null) {
            this.mNEDCDialog = new XDialog(getContext());
            this.mNEDCDialog.setPositiveButton(getString(R.string.dialog_know), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$O6gsXQaItPXG8yezwuIBmLE_jaE
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i3) {
                    DisplayFragment.this.lambda$showNEDCDialog$9$DisplayFragment(xDialog, i3);
                }
            });
        }
        this.mNEDCDialog.setTitle(i);
        this.mNEDCDialog.setMessage(i2);
        this.mNEDCDialog.show();
        VuiManager.instance().initVuiDialog(this.mNEDCDialog, getContext(), VuiManager.SCENE_NEDC_DIALOG);
    }

    public /* synthetic */ void lambda$showNEDCDialog$9$DisplayFragment(XDialog xDialog, int i) {
        this.mNEDCDialog.dismiss();
    }

    private void dismissDialog() {
        XDialog xDialog = this.mNEDCDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        XDialog xDialog2 = this.mDarkStateDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
        }
        XDialog xDialog3 = this.mWaitModeDialog;
        if (xDialog3 != null) {
            xDialog3.dismiss();
        }
        TimeZoneChooseDialog timeZoneChooseDialog = this.mTimeZoneChooseDialog;
        if (timeZoneChooseDialog != null) {
            timeZoneChooseDialog.dismiss();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        if (bundle != null) {
            this.mTabLayoutFontSize.setEnabled(false);
            fontSizeSwitching();
        }
        this.mDisplaySettingsViewModel = (DisplaySettingsViewModel) ViewModelUtils.getViewModel(this, DisplaySettingsViewModel.class);
        this.mDatetimeSettingsViewModel = (DatetimeSettingsViewModel) ViewModelUtils.getViewModel(this, DatetimeSettingsViewModel.class);
        this.mSeekBarCenter.setStartIndex(1);
        this.mSeekBarCenter.setEndIndex(100);
        this.mSeekBarMeter.setStartIndex(1);
        this.mSeekBarMeter.setEndIndex(100);
        this.mSeekBarCenter.setInitIndex(this.mDisplaySettingsViewModel.getScreenBrightness());
        this.mSeekBarMeter.setInitIndex(this.mDisplaySettingsViewModel.getMeterDisplayProgress());
        this.mDisplaySettingsViewModel.getScreenBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$AW5JNQoC-Y9mpGe0i05Ag57jWOU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$11$DisplayFragment((Integer) obj);
            }
        });
        if (this.mSupportPsnScreen) {
            this.mDisplaySettingsViewModel.getPsnScreenBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$_20v91xyyHs_tB_kOSMtYhCK45c
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    DisplayFragment.this.lambda$init$13$DisplayFragment((Integer) obj);
                }
            });
        }
        this.mDisplaySettingsViewModel.getMeterBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$vPAs-aLJ0PQbjEX7W8XLi4p3udQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$15$DisplayFragment((Integer) obj);
            }
        });
        this.mDisplaySettingsViewModel.getAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$xmIfoMz973XEf0agoOWH7Zqg7VM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$16$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getMainAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$2VlixwOc1udH1Q7KW6mDOxV4L-8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$17$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getPsnAutoBrightnessLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$ouGyZgkBrkLY8-sozsH7HlUHkWA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$18$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getThemeSwitchInfoLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$C1kfilpCi_vszTVXPArKq1g5zlo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$19$DisplayFragment((Integer) obj);
            }
        });
        this.mDatetimeSettingsViewModel.getDatetimeModelMutableLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$LNyCKP48buneVrRdVgwHddsiEsg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$20$DisplayFragment((String) obj);
            }
        });
        this.mDisplaySettingsViewModel.getDayNightChangeComplete().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$htvk0jHyjLy-Pa9ZIK3JJPEbdqA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$21$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getIsDarkStateLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$S_1bxutyhSR6ON6ugxJ5YgXCs7I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$22$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getFontScaleLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$t0Xwp0kfgWnWT-9eW1L08V8EH0Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$23$DisplayFragment((Boolean) obj);
            }
        });
        this.mDisplaySettingsViewModel.getTimeFormatLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$i3F0ZSJK3VgM0BKDNzjtUuVqEDE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DisplayFragment.this.lambda$init$24$DisplayFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$11$DisplayFragment(Integer num) {
        log("observe-screen-brightness :" + num);
        if (num.intValue() != ((int) this.mSeekBarCenter.getIndicatorValue())) {
            this.mSeekBarCenter.setCurrentIndex(num.intValue());
        }
        this.mSeekBarCenter.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$PtyGutt9Gcg70am59K9MlVOgq7k
            @Override // java.lang.Runnable
            public final void run() {
                DisplayFragment.this.lambda$null$10$DisplayFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$10$DisplayFragment() {
        updateVuiScene(this.sceneId, this.mSeekBarCenter);
    }

    public /* synthetic */ void lambda$init$13$DisplayFragment(Integer num) {
        log("observe-screen-brightness-psn :" + num);
        if (Utils.isCurrentAppInPsnScreen(getContext())) {
            if (num.intValue() != ((int) this.mSeekBarCenter.getIndicatorValue())) {
                this.mSeekBarCenter.setCurrentIndex(num.intValue());
            }
            this.mSeekBarCenter.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$Mn5VlzMAAmAwtFBD-QJa_WBAtUs
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFragment.this.lambda$null$12$DisplayFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$12$DisplayFragment() {
        updateVuiScene(this.sceneId, this.mSeekBarCenter);
    }

    public /* synthetic */ void lambda$init$15$DisplayFragment(Integer num) {
        log("observe-meter-brightness :" + num);
        if (num.intValue() != ((int) this.mSeekBarMeter.getIndicatorValue())) {
            this.mSeekBarMeter.setCurrentIndex(num.intValue());
        }
        this.mSeekBarMeter.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$7I04VPnLKKiAFi6Hki_mZfRh1X4
            @Override // java.lang.Runnable
            public final void run() {
                DisplayFragment.this.lambda$null$14$DisplayFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$14$DisplayFragment() {
        updateVuiScene(this.sceneId, this.mSeekBarMeter);
    }

    public /* synthetic */ void lambda$init$16$DisplayFragment(Boolean bool) {
        log("observe-auto-brightness :" + bool);
        if (Utils.isPowerSavingMode()) {
            this.mSwitchBrightnessAuto.setChecked(false);
        } else {
            this.mSwitchBrightnessAuto.setChecked(bool.booleanValue());
        }
    }

    public /* synthetic */ void lambda$init$17$DisplayFragment(Boolean bool) {
        log("observe-auto-brightness, main:" + bool);
        if (Utils.isPowerSavingMode()) {
            this.mSwitchBrightnessAutoMain.setChecked(false);
        } else {
            this.mSwitchBrightnessAutoMain.setChecked(bool.booleanValue());
        }
    }

    public /* synthetic */ void lambda$init$18$DisplayFragment(Boolean bool) {
        log("observe-auto-brightness, main:" + bool);
        if (Utils.isPowerSavingMode()) {
            this.mSwitchBrightnessAutoPsn.setChecked(false);
        } else {
            this.mSwitchBrightnessAutoPsn.setChecked(bool.booleanValue());
        }
    }

    public /* synthetic */ void lambda$init$19$DisplayFragment(Integer num) {
        log("observe-theme :" + num + ",---" + XpThemeUtils.getDayNightMode(getContext()));
        if (num.intValue() == 0) {
            this.mTabLayoutDay.selectTab(2, false);
        } else if (num.intValue() == 2) {
            this.mTabLayoutDay.selectTab(1, false);
        } else {
            this.mTabLayoutDay.selectTab(0, false);
        }
        refreshTipsText();
    }

    public /* synthetic */ void lambda$init$20$DisplayFragment(String str) {
        this.mListTime.setTextSub(str);
    }

    public /* synthetic */ void lambda$init$21$DisplayFragment(Boolean bool) {
        this.mTabLayoutDay.setEnabled(bool.booleanValue());
        this.mCleanModeButton.setEnabled(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$22$DisplayFragment(Boolean bool) {
        if (bool.booleanValue()) {
            this.mBrightnessAutoListTwo.setText(getContext().getString(R.string.display_brightness_auto_low_conf_active_tip));
            this.mBrightnessAutoListTwoMain.setText(getContext().getString(R.string.display_brightness_auto_low_conf_active_tip_main));
            this.mBrightnessAutoListTwoPsn.setText(getContext().getString(R.string.display_brightness_auto_low_conf_active_tip_psn));
            return;
        }
        this.mBrightnessAutoListTwo.setText(getContext().getString(R.string.display_brightness_auto_low_conf_tip));
        this.mBrightnessAutoListTwoMain.setText(getContext().getString(R.string.display_brightness_auto_low_conf_tip_main));
        this.mBrightnessAutoListTwoPsn.setText(getContext().getString(R.string.display_brightness_auto_low_conf_tip_psn));
    }

    public /* synthetic */ void lambda$init$23$DisplayFragment(Boolean bool) {
        refreshFontSize(XpAccountManager.getInstance().getFontSize());
    }

    public /* synthetic */ void lambda$init$24$DisplayFragment(Boolean bool) {
        refreshTimeFormat(XpAccountManager.getInstance().is24HourFormat());
    }

    private void refreshTipsText() {
        if (XpThemeUtils.isNightMode(getContext())) {
            this.mCenterLinkingLayout.setTextSub(getString(R.string.display_night_brightness_tips));
            this.mMeterLinkingLayout.setTextSub(getString(R.string.display_night_meter_brightness_tips));
            return;
        }
        this.mCenterLinkingLayout.setTextSub(getString(R.string.display_day_brightness_tips));
        this.mMeterLinkingLayout.setTextSub(getString(R.string.display_day_meter_brightness_tips));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("xpdisplay fragment onHiddenChanged " + isHidden() + " " + this.mIsRegisterFresh);
        if (!z) {
            registerFreshDisplay();
            return;
        }
        unRegisterFreshDisplay();
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        registerFreshDisplay();
    }

    private void registerFreshDisplay() {
        if (this.mIsRegisterFresh) {
            return;
        }
        DisplayServerManager.getInstance().addDisplayUIListener(this);
        refreshFontSize(XpAccountManager.getInstance().getFontSize());
        refreshTimeFormat(XpAccountManager.getInstance().is24HourFormat());
        this.mDisplaySettingsViewModel.onStartVM();
        this.mDatetimeSettingsViewModel.onStartVM();
        if (this.mSupportPsnScreen) {
            this.mSwitchBrightnessAutoMain.setChecked(this.mDisplaySettingsViewModel.isMainAutoBrightnessModeEnable());
            this.mSwitchBrightnessAutoPsn.setChecked(this.mDisplaySettingsViewModel.isPsnAutoBrightnessModeEnable());
            this.mClosePsnScreen.setEnabled(Utils.isPsnScreenOn(CarSettingsApp.getContext()));
            this.mDisplaySettingsViewModel.registerMainAutoBrightnessMode();
            this.mDisplaySettingsViewModel.registerPsnAutoBrightnessMode();
        }
        this.mIsRegisterFresh = true;
        Logs.d("xpdisplay fragment fresh registerFreshDisplay");
        if (CarFunction.isSupportRegionSet()) {
            updateTimeZoneUi();
            registTimeZoneChangeListener();
        }
    }

    private void unRegisterFreshDisplay() {
        if (this.mIsRegisterFresh) {
            this.mDisplaySettingsViewModel.onStopVM();
            this.mDatetimeSettingsViewModel.onStopVM();
            DisplayServerManager.getInstance().removeDisplayUIListener(this);
            if (this.mSupportPsnScreen) {
                this.mDisplaySettingsViewModel.unRegisterMainAutoBrightnessMode();
                this.mDisplaySettingsViewModel.unRegisterPsnAutoBrightnessMode();
            }
            this.mIsRegisterFresh = false;
            Logs.d("xpdisplay fragment fresh unRegisterFreshDisplay");
            if (CarFunction.isSupportRegionSet()) {
                unregistTimeZoneChangeListener();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (!isHidden() || this.mIsRegisterFresh) {
            unRegisterFreshDisplay();
        }
    }

    private void refreshTimeFormat(final boolean z) {
        this.mTabLayoutTime.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$g29OcW-g9RJw-Xr1Of-7Eu39qEg
            @Override // java.lang.Runnable
            public final void run() {
                DisplayFragment.this.lambda$refreshTimeFormat$25$DisplayFragment(z);
            }
        });
    }

    public /* synthetic */ void lambda$refreshTimeFormat$25$DisplayFragment(boolean z) {
        log("post observe-24hour :" + z);
        this.mTabLayoutTime.selectTab(!z ? 1 : 0, false);
    }

    private void refreshFontSize(float f) {
        if (f == 0.9f) {
            this.mTabLayoutFontSize.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$I6_jWkQsG94HL9xvOZ0mvX1teJo
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFragment.this.lambda$refreshFontSize$26$DisplayFragment();
                }
            });
        } else {
            this.mTabLayoutFontSize.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$NGvMOv56jDWGM47bncGW9-jw4_k
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFragment.this.lambda$refreshFontSize$27$DisplayFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$refreshFontSize$26$DisplayFragment() {
        this.mTabLayoutFontSize.selectTab(1, false);
    }

    public /* synthetic */ void lambda$refreshFontSize$27$DisplayFragment() {
        this.mTabLayoutFontSize.selectTab(0, false);
    }

    public /* synthetic */ void lambda$new$28$DisplayFragment(View view) {
        IntervalControl intervalControl = this.mCleanModeIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently(3000)) {
            Logs.d("clean mode isFrequently!");
        } else if (getActivity() != null) {
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_ID, "B006");
            String requestEnterUserScenario = this.mDisplaySettingsViewModel.requestEnterUserScenario(XuiSettingsManager.USER_SCENARIO_CLEAN_MODE, 0);
            Logs.d("enter clean mode ret:" + requestEnterUserScenario);
            if (XuiSettingsManager.RET_FAIL_GEAR_NOT_P.equals(requestEnterUserScenario) && (view instanceof IVuiElement) && ((IVuiElement) view).isPerformVuiAction()) {
                isVuiAction(view);
                vuiFeedback(view, -1, getString(R.string.display_wait_mode_toast));
            }
        }
    }

    public /* synthetic */ void lambda$new$30$DisplayFragment(View view) {
        if (getContext() == null) {
            return;
        }
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            XToast.showShort(R.string.display_region_park_toast);
            return;
        }
        final String[] stringArray = getContext().getResources().getStringArray(R.array.supported_languages_code);
        String[] stringArray2 = getContext().getResources().getStringArray(R.array.supported_languages_display_name);
        final String language = Locale.getDefault().getLanguage();
        if (this.mLanguagePicker == null) {
            this.mLanguagePicker = new XNumberPickerDialog(getContext(), R.style.XAppTheme_XDialog, stringArray2);
            this.mLanguagePicker.getXNumberPicker().setWrapSelectorWheel(false);
        }
        this.mLanguagePicker.setXNumberSetListener(new XNumberPickerDialog.OnXNumberSetListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$oFKK3BzbmkRutFEYyBXaA3ISUVg
            @Override // com.xiaopeng.xui.app.XNumberPickerDialog.OnXNumberSetListener
            public final void onXNumberSet(XNumberPicker xNumberPicker, CharSequence charSequence) {
                DisplayFragment.this.lambda$null$29$DisplayFragment(stringArray, language, xNumberPicker, charSequence);
            }
        });
        int i = 0;
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            if (stringArray[i2].equalsIgnoreCase(language)) {
                i = i2;
            }
        }
        this.mLanguagePicker.getXNumberPicker().setValue(i + 1);
        this.mLanguagePicker.setTitle(R.string.display_choose_language);
        this.mLanguagePicker.setPositiveButton(R.string.confirm);
        this.mLanguagePicker.setNegativeButton(R.string.cancel);
        this.mLanguagePicker.setCloseVisibility(false);
        this.mLanguagePicker.show();
    }

    public /* synthetic */ void lambda$null$29$DisplayFragment(String[] strArr, String str, XNumberPicker xNumberPicker, CharSequence charSequence) {
        String str2 = strArr[xNumberPicker.getValue() - 1];
        if (!str.equals(str2)) {
            showLanguageChooseConfirmDialog(str2);
        } else {
            this.mLanguagePicker.dismiss();
        }
    }

    private void showLanguageChooseConfirmDialog(String str) {
        if (getContext() == null) {
            return;
        }
        if (this.mConfirmDialog == null) {
            this.mConfirmDialog = new XDialog(getContext());
            this.mConfirmDialog.setTitle(getString(R.string.confirm_language_title)).setMessage(getString(R.string.confirm_language_init_title)).setPositiveButton(getString(R.string.confirm), new AnonymousClass10(str, (PowerManager) getContext().getSystemService("power"))).setNegativeButton(getString(R.string.cancel));
        }
        this.mConfirmDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment$10  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass10 implements XDialogInterface.OnClickListener {
        final /* synthetic */ PowerManager val$pManager;
        final /* synthetic */ String val$targetLanguage;

        AnonymousClass10(String str, PowerManager powerManager) {
            this.val$targetLanguage = str;
            this.val$pManager = powerManager;
        }

        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
        public void onClick(XDialog xDialog, int i) {
            final String str = this.val$targetLanguage;
            final PowerManager powerManager = this.val$pManager;
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$10$yrLnD7fQf4qb2vfe2hF8U2SxA-c
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFragment.AnonymousClass10.this.lambda$onClick$0$DisplayFragment$10(str, powerManager);
                }
            });
        }

        public /* synthetic */ void lambda$onClick$0$DisplayFragment$10(String str, final PowerManager powerManager) {
            LocaleUtils.setLanguage(str);
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.10.1
                @Override // java.lang.Runnable
                public void run() {
                    powerManager.reboot("");
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$new$34$DisplayFragment(View view) {
        if (getContext() == null) {
            return;
        }
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            XToast.showShort(R.string.display_region_park_toast);
            return;
        }
        final IVcuChangeListener iVcuChangeListener = new IVcuChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.11
            @Override // com.xiaopeng.car.settingslibrary.manager.car.IVcuChangeListener
            public void onValueChange(int i, int i2) {
                if (DisplayFragment.this.mRegionPickerDialog != null) {
                    DisplayFragment.this.mRegionPickerDialog.dismiss();
                }
            }
        };
        CarSettingsManager.getInstance().addVcuChangeListener(iVcuChangeListener);
        final String[] stringArray = getContext().getResources().getStringArray(R.array.supported_countries_code);
        final String[] stringArray2 = getContext().getResources().getStringArray(R.array.supported_countries_display_name);
        String country = Locale.getDefault().getCountry();
        if (this.mRegionPickerDialog == null) {
            this.mRegionPickerDialog = new XNumberPickerDialog(getContext(), R.style.XAppTheme_XDialog, stringArray2);
        }
        this.mRegionPickerDialog.setXNumberSetListener(new XNumberPickerDialog.OnXNumberSetListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$WmENU6Tg9EnYAjZ7KlmDWmtDyik
            @Override // com.xiaopeng.xui.app.XNumberPickerDialog.OnXNumberSetListener
            public final void onXNumberSet(XNumberPicker xNumberPicker, CharSequence charSequence) {
                DisplayFragment.this.lambda$null$32$DisplayFragment(stringArray, stringArray2, xNumberPicker, charSequence);
            }
        });
        XNumberPicker xNumberPicker = this.mRegionPickerDialog.getXNumberPicker();
        int i = 0;
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            if (stringArray[i2].equalsIgnoreCase(country)) {
                i = i2;
            }
        }
        xNumberPicker.setWrapSelectorWheel(false);
        xNumberPicker.setValue(i + 1);
        this.mRegionPickerDialog.setTitle(R.string.display_choose_region);
        this.mRegionPickerDialog.setPositiveButton(R.string.confirm);
        this.mRegionPickerDialog.setNegativeButton(R.string.cancel);
        this.mRegionPickerDialog.setCloseVisibility(false);
        this.mRegionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$z-jm4_mokBsa-jADY1orT0hqECI
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CarSettingsManager.getInstance().removeVcuChangeListener(IVcuChangeListener.this);
            }
        });
        this.mRegionPickerDialog.show();
    }

    public /* synthetic */ void lambda$null$32$DisplayFragment(final String[] strArr, final String[] strArr2, XNumberPicker xNumberPicker, CharSequence charSequence) {
        final int value = xNumberPicker.getValue() - 1;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$k6Q_5DCpDEWqbGbBQoasxi1QJy4
            @Override // java.lang.Runnable
            public final void run() {
                LocaleUtils.setLocale(strArr[value]);
            }
        });
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.12
            @Override // java.lang.Runnable
            public void run() {
                DisplayFragment.this.mCurrentRegion.setText(DisplayFragment.this.getString(R.string.display_current_region, strArr2[value]));
            }
        }, 300L);
    }

    public /* synthetic */ void lambda$new$35$DisplayFragment(View view) {
        IntervalControl intervalControl = this.mWaitModeIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently(1000)) {
            Logs.d("wait mode isFrequently");
            return;
        }
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.DISPLAY_PAGE_NEW_ID, "B001");
        String requestEnterUserScenario = this.mDisplaySettingsViewModel.requestEnterUserScenario(XuiSettingsManager.USER_SCENARIO_WAITING_MODE, 0);
        Logs.d("enter waiting mode ret:" + requestEnterUserScenario);
        if (XuiSettingsManager.RET_FAIL_GEAR_NOT_P.equals(requestEnterUserScenario) && (view instanceof IVuiElement) && ((IVuiElement) view).isPerformVuiAction()) {
            isVuiAction(view);
            vuiFeedback(view, -1, getString(R.string.display_wait_mode_toast));
        }
    }

    private void setViewWidth(View view, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        view.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment$14  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass14 implements XTabLayout.OnTabChangeListener {
        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
            return false;
        }

        AnonymousClass14() {
        }

        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
            if (z2 && z) {
                if (xTabLayout != DisplayFragment.this.mTabLayoutDay) {
                    if (xTabLayout == DisplayFragment.this.mTabLayoutFontSize) {
                        DisplayFragment.this.mTabLayoutFontSize.setEnabled(false);
                        return;
                    }
                    return;
                }
                Logs.log(ThemeManager.AttributeSet.THEME, "xpdisplay onTabChangeStart ");
                DisplayFragment.this.mTabLayoutDay.setEnabled(false);
                DisplayFragment.this.mCleanModeButton.setEnabled(false);
            }
        }

        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeEnd(XTabLayout xTabLayout, final int i, boolean z, boolean z2) {
            DisplayFragment displayFragment = DisplayFragment.this;
            boolean z3 = false;
            displayFragment.updateVuiScene(displayFragment.sceneId, xTabLayout);
            if (z2 && z) {
                if (xTabLayout != DisplayFragment.this.mTabLayoutDay) {
                    if (xTabLayout != DisplayFragment.this.mTabLayoutFontSize) {
                        if (xTabLayout == DisplayFragment.this.mTabLayoutTime) {
                            Logs.log("display", "time change " + i);
                            if (i == 0) {
                                DisplayFragment.this.mDatetimeSettingsViewModel.set24HourFormat(true);
                                return;
                            } else if (i != 1) {
                                return;
                            } else {
                                DisplayFragment.this.mDatetimeSettingsViewModel.set24HourFormat(false);
                                return;
                            }
                        }
                        return;
                    }
                    Logs.log("display-onTabChangeEnd", "font change " + i);
                    DisplayFragment.this.mTabLayoutFontSize.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$14$sBu79S9fm7I44BXLSKDu1304piM
                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayFragment.AnonymousClass14.this.lambda$onTabChangeEnd$0$DisplayFragment$14(i);
                        }
                    });
                    DisplayFragment.this.fontSizeSwitching();
                    return;
                }
                if (i == 0) {
                    z3 = DisplayFragment.this.mDisplaySettingsViewModel.setThemeDayNightMode(1);
                } else if (i == 1) {
                    z3 = DisplayFragment.this.mDisplaySettingsViewModel.setThemeDayNightMode(2);
                } else if (i == 2) {
                    z3 = DisplayFragment.this.mDisplaySettingsViewModel.setThemeDayNightMode(0);
                }
                Logs.log(ThemeManager.AttributeSet.THEME, "xpdisplay index:" + i + ",isSuccess:" + z3);
                return;
            }
            Logs.log("display-onTabChangeEnd", "return  " + i + ", xTabLayout " + xTabLayout);
        }

        public /* synthetic */ void lambda$onTabChangeEnd$0$DisplayFragment$14(int i) {
            if (i == 0) {
                DisplayFragment.this.mDisplaySettingsViewModel.setFontSize(1.0f);
            } else if (i != 1) {
            } else {
                DisplayFragment.this.mDisplaySettingsViewModel.setFontSize(0.9f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fontSizeSwitching() {
        this.mHandler.removeCallbacks(this.mFontRunnable);
        this.mHandler.postDelayed(this.mFontRunnable, 800L);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IDisplayServerListener
    public void onAccountChanged() {
        Logs.d("XpAccountManager-xld onUpdateUIEvent ");
        refreshFontSize(XpAccountManager.getInstance().getFontSize());
        refreshTimeFormat(XpAccountManager.getInstance().is24HourFormat());
        if (Utils.isPowerSavingMode()) {
            this.mSwitchBrightnessAuto.setChecked(false);
        } else if (CarConfigHelper.hasAutoBrightness()) {
            this.mSwitchBrightnessAuto.setChecked(XpAccountManager.getInstance().getAutoBrightness());
        } else {
            this.mSwitchBrightnessAuto.setChecked(XpAccountManager.getInstance().getDarkLightAdaptation());
        }
        if (!this.mSupportPsnScreen || this.mDisplaySettingsViewModel == null) {
            return;
        }
        if (Utils.isPowerSavingMode()) {
            this.mSwitchBrightnessAutoMain.setChecked(false);
            this.mSwitchBrightnessAutoPsn.setChecked(false);
            return;
        }
        this.mSwitchBrightnessAutoMain.setChecked(this.mDisplaySettingsViewModel.isMainAutoBrightnessModeEnable());
        this.mSwitchBrightnessAutoPsn.setChecked(this.mDisplaySettingsViewModel.isPsnAutoBrightnessModeEnable());
    }

    private void initVui() {
        if (this.mVuiHandler == null) {
            return;
        }
        this.mVuiHandler.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.15
            @Override // java.lang.Runnable
            public void run() {
                DisplayFragment.this.mSeekBarCenter.setVuiLabel(DisplayFragment.this.getString(R.string.display_brightness_center));
                DisplayFragment.this.mSeekBarMeter.setVuiLabel(DisplayFragment.this.getString(R.string.display_brightness_meter));
                DisplayFragment.this.mSwitchBrightnessAuto.setVuiLabel(DisplayFragment.this.mBrightnessText);
                if (DisplayFragment.this.mSupportPsnScreen) {
                    DisplayFragment.this.mSwitchBrightnessAutoMain.setVuiLabel(DisplayFragment.this.mBrightnessTextMain);
                    DisplayFragment.this.mSwitchBrightnessAutoPsn.setVuiLabel(DisplayFragment.this.mBrightnessTextPsn);
                }
                if (!CarConfigHelper.hasAutoBrightness()) {
                    DisplayFragment.this.mBrightnessTips.setVuiLabel(DisplayFragment.this.getContext().getString(R.string.vui_label_detail));
                    DisplayFragment.this.mBrightnessTips.setVuiFatherLabel(DisplayFragment.this.getContext().getString(R.string.display_brightness_auto_low_conf_tip));
                    DisplayFragment.this.mBrightnessTipsMain.setVuiLabel(DisplayFragment.this.getContext().getString(R.string.vui_label_detail));
                    DisplayFragment.this.mBrightnessTipsMain.setVuiFatherLabel(DisplayFragment.this.getContext().getString(R.string.display_brightness_auto_low_conf_tip));
                    DisplayFragment.this.mBrightnessTipsPsn.setVuiLabel(DisplayFragment.this.getContext().getString(R.string.vui_label_detail));
                    DisplayFragment.this.mBrightnessTipsPsn.setVuiFatherLabel(DisplayFragment.this.getContext().getString(R.string.display_brightness_auto_low_conf_tip));
                }
                if (DisplayFragment.this.mWaitModeTips != null) {
                    DisplayFragment.this.mWaitModeTips.setVuiLabel(DisplayFragment.this.getContext().getString(R.string.vui_label_detail));
                    DisplayFragment.this.mWaitModeTips.setVuiFatherLabel(DisplayFragment.this.getContext().getString(R.string.display_wait_mode));
                }
                DisplayFragment displayFragment = DisplayFragment.this;
                displayFragment.supportFeedback(displayFragment.mSwitchBrightnessAuto);
                if (DisplayFragment.this.mSupportPsnScreen) {
                    DisplayFragment displayFragment2 = DisplayFragment.this;
                    displayFragment2.supportFeedback(displayFragment2.mSwitchBrightnessAutoMain);
                    DisplayFragment displayFragment3 = DisplayFragment.this;
                    displayFragment3.supportFeedback(displayFragment3.mSwitchBrightnessAutoPsn);
                }
                DisplayFragment displayFragment4 = DisplayFragment.this;
                displayFragment4.supportFeedback(displayFragment4.mTabLayoutDay);
                DisplayFragment displayFragment5 = DisplayFragment.this;
                displayFragment5.supportFeedback(displayFragment5.mTabLayoutFontSize);
                DisplayFragment displayFragment6 = DisplayFragment.this;
                displayFragment6.supportFeedback(displayFragment6.mCleanModeButton);
                DisplayFragment displayFragment7 = DisplayFragment.this;
                displayFragment7.supportFeedback(displayFragment7.mWaitModeBtn);
                DisplayFragment displayFragment8 = DisplayFragment.this;
                displayFragment8.setVuiElementUnSupport(displayFragment8.mBrightnessAutoListTwo, false);
                DisplayFragment displayFragment9 = DisplayFragment.this;
                displayFragment9.setVuiElementUnSupport(displayFragment9.mBrightnessAutoListTwoMain, false);
                DisplayFragment displayFragment10 = DisplayFragment.this;
                displayFragment10.setVuiElementUnSupport(displayFragment10.mBrightnessAutoListTwoPsn, false);
                String str = DisplayFragment.this.mClearLayout.getId() + "_" + DisplayFragment.this.mCleanModeButton.getId();
                DisplayFragment.this.mCleanModeButton.setVuiElementId(str);
                DisplayFragment displayFragment11 = DisplayFragment.this;
                displayFragment11.vuiChangeTarget(displayFragment11.mClearLayout, str);
                if (DisplayFragment.this.mWaitModeLayout == null || DisplayFragment.this.mWaitModeBtn == null) {
                    return;
                }
                String str2 = DisplayFragment.this.mWaitModeLayout.getId() + "_" + DisplayFragment.this.mWaitModeBtn.getId();
                DisplayFragment.this.mWaitModeBtn.setVuiElementId(str2);
                DisplayFragment displayFragment12 = DisplayFragment.this;
                displayFragment12.vuiChangeTarget(displayFragment12.mWaitModeLayout, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBrightnessSliderScene(final XSlider xSlider, boolean z) {
        if (z) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment.16
                @Override // java.lang.Runnable
                public void run() {
                    DisplayFragment displayFragment = DisplayFragment.this;
                    displayFragment.updateVuiScene(displayFragment.sceneId, xSlider);
                }
            }, 100L);
        } else {
            updateVuiScene(this.sceneId, xSlider);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return new String[]{"/display/dark_brightness"};
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(String str) {
        if ("/display/dark_brightness".equals(str)) {
            showDarkBrightnessDialog();
        }
        super.onPageDirectShow(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IDisplayServerListener
    public void onDayNightChanged() {
        this.mTabLayoutDay.setEnabled(false);
        this.mCleanModeButton.setEnabled(false);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IDisplayServerListener
    public void onPsnScreenOnStatusChange(boolean z) {
        this.mClosePsnScreen.setEnabled(z);
    }

    private String getCountryName(String str) {
        String[] stringArray = getContext().getResources().getStringArray(R.array.supported_countries_display_name);
        String[] stringArray2 = getContext().getResources().getStringArray(R.array.supported_countries_code);
        for (int i = 0; i < stringArray2.length; i++) {
            if (stringArray2[i].equalsIgnoreCase(str)) {
                return stringArray[i];
            }
        }
        return Locale.getDefault().getDisplayCountry();
    }

    private String getLanguageName(String str) {
        String[] stringArray = getContext().getResources().getStringArray(R.array.supported_languages_display_name);
        String[] stringArray2 = getContext().getResources().getStringArray(R.array.supported_languages_code);
        for (int i = 0; i < stringArray2.length; i++) {
            if (stringArray2[i].equalsIgnoreCase(str)) {
                return stringArray[i];
            }
        }
        return Locale.getDefault().getDisplayLanguage();
    }

    private void showTimeZoneChoose() {
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            XToast.showShort(R.string.display_region_park_toast);
            return;
        }
        TimeZoneChooseDialog timeZoneChooseDialog = this.mTimeZoneChooseDialog;
        if (timeZoneChooseDialog != null) {
            timeZoneChooseDialog.dismiss();
        }
        this.mTimeZoneChooseDialog = new TimeZoneChooseDialog(getContext());
        this.mTimeZoneChooseDialog.show();
        this.mTimeZoneChooseDialog.setOnChooseListener(new BiConsumer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$ynB6f-ynlEn2qPBTeZi8IsTVoDE
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                DisplayFragment.this.lambda$showTimeZoneChoose$36$DisplayFragment((TimeZoneChooseDialog) obj, (TimeZone) obj2);
            }
        });
        this.mTimeZoneChooseDialog.setOnDismissListener(new Consumer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$DisplayFragment$OTyP269BG8UB6Wt9p8QnweYHW5E
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DisplayFragment.this.lambda$showTimeZoneChoose$37$DisplayFragment((TimeZoneChooseDialog) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showTimeZoneChoose$36$DisplayFragment(TimeZoneChooseDialog timeZoneChooseDialog, TimeZone timeZone) {
        CarSettingsManager.getInstance().setNitzTimeZone(timeZone.getID());
        updateTimeZoneUi();
    }

    public /* synthetic */ void lambda$showTimeZoneChoose$37$DisplayFragment(TimeZoneChooseDialog timeZoneChooseDialog) {
        this.mTimeZoneChooseDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimeZoneUi() {
        String nitzTimeZone = CarSettingsManager.getInstance().getNitzTimeZone();
        this.mTimeZoneAutomatic.setText(getString(R.string.display_timezone_auto_title));
        this.mTimeZoneManual.setText(nitzTimeZone != null ? getString(R.string.display_timezone_manual_title, TimeZoneUtils.getDisplayName(this.mTimeZoneManual.getContext(), nitzTimeZone)) : getString(R.string.display_timezone_manual_title_unset));
    }

    private void registTimeZoneChangeListener() {
        if (this.mTimeZoneChangeBrocastReceiver != null) {
            unregistTimeZoneChangeListener();
        }
        this.mTimeZoneChangeBrocastReceiver = new TimeZoneChangeBrocastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        getContext().registerReceiver(this.mTimeZoneChangeBrocastReceiver, intentFilter);
    }

    private void unregistTimeZoneChangeListener() {
        if (this.mTimeZoneChangeBrocastReceiver != null) {
            getContext().unregisterReceiver(this.mTimeZoneChangeBrocastReceiver);
            this.mTimeZoneChangeBrocastReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class TimeZoneChangeBrocastReceiver extends BroadcastReceiver {
        WeakReference<DisplayFragment> ref;

        TimeZoneChangeBrocastReceiver(DisplayFragment displayFragment) {
            this.ref = new WeakReference<>(displayFragment);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            DisplayFragment displayFragment = this.ref.get();
            if (displayFragment != null) {
                displayFragment.updateTimeZoneUi();
            }
        }
    }
}
