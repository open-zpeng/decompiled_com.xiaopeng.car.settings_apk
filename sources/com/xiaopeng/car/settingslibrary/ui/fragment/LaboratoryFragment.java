package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.laboratory.LaboratoryViewModel;
import com.xiaopeng.car.settingslibrary.vm.laboratory.NetConfigValues;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XCompoundButton;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.slider.XSlider;
/* loaded from: classes.dex */
public class LaboratoryFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, XSlider.SliderProgressListener, XSlider.ProgressChangeListener {
    private XDialog mAppLimitDialog;
    private IntervalControl mAppLimitIntervalControl;
    private XSwitch mAppLimitSwitch;
    private View mAppLimitView;
    private XSwitch mAutoPowerOffSwitch;
    private View mAutoPowerOffView;
    private XCompoundButton mCameraCheckboxButton;
    private XDialog mCarCallAdvancedDialog;
    private IntervalControl mCarCallAdvancedIntervalControl;
    private XSwitch mCarCallAdvancedSwitch;
    private View mCarCallAdvancedView;
    private XCompoundButton mCheckboxButton;
    private View mEmptyView;
    private XDialog mInductionLockDialog;
    private XSwitch mInductionLockSwitch;
    private View mInductionLockView;
    private View mInductiveUnlockingGroup;
    private IntervalControl mKeyParkAdvancedIntervalControl;
    private IntervalControl mKeyParkIntervalControl;
    private LaboratoryViewModel mLaboratoryViewModel;
    private XSwitch mLeaveLockSwitch;
    private View mLeaveLockView;
    private XSlider mLowSpeedSlider;
    private View mLowSpeedSliderView;
    private XSwitch mLowSpeedSwitch;
    private View mLowSpeedViewD21;
    private View mMoreGroupHeader;
    private XDialog mNEDCDialog;
    private XSwitch mNearUnlockSwitch;
    private View mNearUnlockView;
    private NetConfigValues mNetConfigValues;
    private XDialog mRemoteControlAdvancedDialog;
    private XSwitch mRemoteControlAdvancedSwitch;
    private View mRemoteControlAdvancedView;
    private XSwitch mRemoteControlKeySwitch;
    private View mRemoteControlKeyView;
    private XSwitch mRemoteControlSwitch;
    private View mRemoteControlViewD21;
    private View mSecurityGroupHeader;
    private XListTwo mSoldierCamera;
    private IntervalControl mSoldierCameraIntervalControl;
    private XSwitch mSoldierCameraSwitch;
    private XDialog mSoldierDialog;
    private IntervalControl mSoldierIntervalControl;
    private XListMultiple mSoldierLevel;
    private XTabLayout mSoldierTabLayout;
    private View mTipsView;
    private XListMultiple mTtsBroadcast;
    private XTabLayout mTtsBroadcastTabLayout;
    private XDialog mXDialog;
    private Handler mHandler = new Handler();
    private Runnable mTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$YCO5e8TovlhbLIJHitta3QzkwrI
        @Override // java.lang.Runnable
        public final void run() {
            LaboratoryFragment.this.timeout();
        }
    };
    private boolean mIsRegisterFresh = false;

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onProgressChanged(XSlider xSlider, float f, String str) {
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStartTrackingTouch(XSlider xSlider) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void timeout() {
        Logs.d("xplaboratory remote parking timeout");
        this.mRemoteControlKeySwitch.setEnabled(true);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_laboratory;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRootView = view.findViewById(R.id.layout_laboratory);
        this.mEmptyView = view.findViewById(R.id.laboratory_no_views);
        this.mTipsView = view.findViewById(R.id.laboratory_tips_view);
        this.mSecurityGroupHeader = view.findViewById(R.id.laboratory_security_group);
        this.mMoreGroupHeader = view.findViewById(R.id.laboratory_more_header);
        this.mAppLimitView = view.findViewById(R.id.app_limit);
        this.mLowSpeedViewD21 = view.findViewById(R.id.low_speed_d21);
        this.mLowSpeedSliderView = view.findViewById(R.id.low_speed_e28);
        this.mAutoPowerOffView = view.findViewById(R.id.auto_power_off);
        this.mRemoteControlViewD21 = view.findViewById(R.id.remote_control_d21);
        this.mSoldierLevel = (XListMultiple) view.findViewById(R.id.soldier_level);
        this.mTtsBroadcast = (XListMultiple) view.findViewById(R.id.tts_broadcast);
        this.mSoldierCamera = (XListTwo) view.findViewById(R.id.soldier_camera);
        this.mInductiveUnlockingGroup = view.findViewById(R.id.inductive_unlocking);
        this.mNearUnlockView = view.findViewById(R.id.laboratory_near_unlock);
        this.mLeaveLockView = view.findViewById(R.id.laboratory_leave_lock);
        this.mRemoteControlKeyView = view.findViewById(R.id.remote_control_key);
        this.mRemoteControlAdvancedView = view.findViewById(R.id.remote_control_advanced);
        this.mCarCallAdvancedView = view.findViewById(R.id.car_call_advanced);
        this.mInductionLockView = view.findViewById(R.id.d21_induction_lock);
        this.mAppLimitSwitch = (XSwitch) this.mAppLimitView.findViewById(R.id.x_list_action_switch);
        this.mLowSpeedSwitch = (XSwitch) this.mLowSpeedViewD21.findViewById(R.id.x_list_action_switch);
        this.mLowSpeedSlider = (XSlider) this.mLowSpeedSliderView.findViewById(R.id.x_list_action_slider);
        this.mAutoPowerOffSwitch = (XSwitch) this.mAutoPowerOffView.findViewById(R.id.x_list_action_switch);
        this.mRemoteControlSwitch = (XSwitch) this.mRemoteControlViewD21.findViewById(R.id.x_list_action_switch);
        this.mSoldierCameraSwitch = (XSwitch) this.mSoldierCamera.findViewById(R.id.x_list_action_switch);
        this.mSoldierTabLayout = (XTabLayout) this.mSoldierLevel.findViewById(R.id.soldier_level_segment);
        this.mTtsBroadcastTabLayout = (XTabLayout) this.mTtsBroadcast.findViewById(R.id.tts_broadcast_segment);
        this.mNearUnlockSwitch = (XSwitch) this.mNearUnlockView.findViewById(R.id.x_list_action_switch);
        this.mLeaveLockSwitch = (XSwitch) this.mLeaveLockView.findViewById(R.id.x_list_action_switch);
        this.mRemoteControlKeySwitch = (XSwitch) this.mRemoteControlKeyView.findViewById(R.id.x_list_action_switch);
        this.mRemoteControlAdvancedSwitch = (XSwitch) this.mRemoteControlAdvancedView.findViewById(R.id.x_list_action_switch);
        this.mCarCallAdvancedSwitch = (XSwitch) this.mCarCallAdvancedView.findViewById(R.id.x_list_action_switch);
        this.mInductionLockSwitch = (XSwitch) this.mInductionLockView.findViewById(R.id.x_list_action_switch);
        this.mLeaveLockSwitch.setOnCheckedChangeListener(this);
        this.mNearUnlockSwitch.setOnCheckedChangeListener(this);
        this.mAppLimitSwitch.setOnCheckedChangeListener(this);
        this.mLowSpeedSwitch.setOnCheckedChangeListener(this);
        this.mLowSpeedSlider.setSliderProgressListener(this);
        this.mLowSpeedSlider.setProgressChangeListener(this);
        this.mAutoPowerOffSwitch.setOnCheckedChangeListener(this);
        this.mRemoteControlKeySwitch.setOnCheckedChangeListener(this);
        this.mRemoteControlAdvancedSwitch.setOnCheckedChangeListener(this);
        this.mRemoteControlSwitch.setOnCheckedChangeListener(this);
        this.mCarCallAdvancedSwitch.setOnCheckedChangeListener(this);
        this.mInductionLockSwitch.setOnCheckedChangeListener(this);
        this.mInductionLockSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptInductionLock(view2, false);
            }
        });
        this.mRemoteControlSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptRemotePark(view2, false);
            }
        });
        this.mKeyParkIntervalControl = new IntervalControl("key park");
        this.mRemoteControlKeySwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptKeyPark(view2, false);
            }
        });
        this.mKeyParkAdvancedIntervalControl = new IntervalControl("key park advanced");
        this.mRemoteControlAdvancedSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptKeyParkAdvanced(view2, false);
            }
        });
        this.mCarCallAdvancedIntervalControl = new IntervalControl("car call advanced");
        this.mCarCallAdvancedSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptCarCallAdvanced(view2, false);
            }
        });
        this.mAppLimitIntervalControl = new IntervalControl("app limit");
        this.mAppLimitSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptAppLimit(view2, false);
            }
        });
        this.mSoldierCameraSwitch.setOnCheckedChangeListener(this);
        this.mSoldierCameraIntervalControl = new IntervalControl("soldier camera");
        this.mSoldierCameraSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.7
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return motionEvent.getAction() == 1 && LaboratoryFragment.this.interceptSoliderCamera(view2, false);
            }
        });
        this.mSoldierIntervalControl = new IntervalControl("soldier");
        this.mSoldierTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.8
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
                return LaboratoryFragment.this.mSoldierIntervalControl.isFrequently();
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
                if (z2) {
                    Logs.d("xplab soldier onTabChangeStart");
                    if (i == 0) {
                        LaboratoryFragment.this.mSoldierCamera.setEnabled(false);
                        LaboratoryFragment.this.mSoldierCameraSwitch.setChecked(false);
                        return;
                    }
                    LaboratoryFragment.this.mSoldierCamera.setEnabled(true);
                }
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
                if (z2) {
                    Logs.d("laboratory-soldier onTabChangeEnd : " + i);
                    LaboratoryFragment.this.mLaboratoryViewModel.setSoldierSw(i);
                    LaboratoryFragment.this.checkSoldierCameraSwitchEnable();
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B012", i);
                }
                LaboratoryFragment laboratoryFragment = LaboratoryFragment.this;
                laboratoryFragment.updateVuiScene(laboratoryFragment.sceneId, xTabLayout);
            }
        });
        this.mTtsBroadcastTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.9
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
                if (z2) {
                    Logs.d("laboratory-tts-broadcast onTabChangeEnd : " + i);
                    if (i == 0) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B001", "type", 0);
                        LaboratoryFragment.this.mLaboratoryViewModel.setTtsBroadcastType(0);
                    } else if (i == 1) {
                        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_NEW_ID, "B001", "type", 1);
                        LaboratoryFragment.this.mLaboratoryViewModel.setTtsBroadcastType(1);
                    }
                }
                LaboratoryFragment laboratoryFragment = LaboratoryFragment.this;
                laboratoryFragment.updateVuiScene(laboratoryFragment.sceneId, xTabLayout);
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.laboratory_tips_list).findViewById(R.id.x_list_action_button);
        textView.setText(R.string.laboratory_view);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$djyyk8EC2XTpzJf23rmdSyVvdIA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LaboratoryFragment.this.lambda$initView$0$LaboratoryFragment(view2);
            }
        });
        checkShowOrHidden(new NetConfigValues());
        initVui();
    }

    public /* synthetic */ void lambda$initView$0$LaboratoryFragment(View view) {
        showProtocolDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view == null) {
            return false;
        }
        VuiFloatingLayerManager.show(view);
        if (view == this.mSoldierCameraSwitch) {
            if (interceptSoliderCamera(view, true)) {
                return true;
            }
        } else if (view == this.mInductionLockSwitch) {
            if (interceptInductionLock(view, true)) {
                return true;
            }
        } else if (view == this.mRemoteControlKeySwitch) {
            if (interceptKeyPark(view, true)) {
                return true;
            }
        } else if (view == this.mRemoteControlSwitch) {
            if (interceptRemotePark(view, true)) {
                return true;
            }
        } else if (view == this.mRemoteControlAdvancedSwitch) {
            if (interceptKeyParkAdvanced(view, true)) {
                return true;
            }
        } else if (view == this.mCarCallAdvancedSwitch) {
            if (interceptCarCallAdvanced(view, true)) {
                return true;
            }
        } else if (view == this.mAppLimitSwitch && interceptAppLimit(view, true)) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptAppLimit(View view, boolean z) {
        if (view == null) {
            return false;
        }
        IntervalControl intervalControl = this.mAppLimitIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently()) {
            if (z) {
                isVuiAction(view);
            }
            return true;
        }
        boolean z2 = !this.mAppLimitSwitch.isChecked();
        Logs.d("xplaboratory mAppLimitSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            return false;
        }
        showAppLimitProtocolDialog();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptKeyParkAdvanced(View view, boolean z) {
        if (view == null) {
            return false;
        }
        IntervalControl intervalControl = this.mKeyParkAdvancedIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently()) {
            Logs.d("xplaboratory onInterceptCheck keypark isFrequently");
            if (z) {
                isVuiAction(view);
            }
            return true;
        }
        boolean z2 = !this.mRemoteControlAdvancedSwitch.isChecked();
        Logs.d("xplaboratory mRemoteControlAdvancedSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            if (remoteParkAdvanceIntercept(z, view)) {
                return true;
            }
            showRemoteControlAdvancedDialog(z, view);
            return true;
        }
        return false;
    }

    private boolean remoteParkAdvanceIntercept(boolean z, View view) {
        if (nedcIntercept(z, view)) {
            return true;
        }
        if (!this.mLaboratoryViewModel.isParkByMemoryEnable()) {
            if (z) {
                isVuiAction(view);
                vuiFeedback(view, -1, getString(R.string.laboratory_remote_control_advanced_disable_pop));
            }
            ToastUtils.get().showText(R.string.laboratory_remote_control_advanced_disable_pop);
            return true;
        } else if (CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_PHONE_PK_BTN)) {
            ToastUtils.get().showText(R.string.laboratory_key_parking_device_fault);
            if (z) {
                vuiFeedback(view, -1, getString(R.string.laboratory_key_parking_device_fault));
                isVuiAction(view);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean nedcIntercept(boolean z, View view) {
        int nedcSwitchStatus = this.mLaboratoryViewModel.getNedcSwitchStatus();
        Logs.d("xplaboratory keypark_advanced getNedcSwitchStatus:" + nedcSwitchStatus);
        if (nedcSwitchStatus == 1 || nedcSwitchStatus == 4 || nedcSwitchStatus == 5) {
            showNEDCDialog(R.string.nedc_mode_dialog_title, R.string.nedc_mode_dialog_tips);
            if (z) {
                vuiFeedback(view, nedcSwitchStatus, getString(R.string.nedc_mode_dialog_tips));
                isVuiAction(view);
            }
            return true;
        } else if (nedcSwitchStatus == 3) {
            showNEDCDialog(R.string.nedc_mode_turning_off_dialog_title, R.string.nedc_mode_turning_off_dialog_tips);
            if (z) {
                vuiFeedback(view, nedcSwitchStatus, getString(R.string.nedc_mode_turning_off_dialog_tips));
                isVuiAction(view);
            }
            return true;
        } else {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptCarCallAdvanced(View view, boolean z) {
        if (view == null) {
            return false;
        }
        IntervalControl intervalControl = this.mCarCallAdvancedIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently()) {
            Logs.d("xplaboratory onInterceptCheck carcall isFrequently");
            if (z) {
                isVuiAction(view);
            }
            return true;
        }
        boolean z2 = !this.mCarCallAdvancedSwitch.isChecked();
        Logs.d("xplaboratory mCarCallAdvancedSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            if (carCallAdvanceIntercept(z, view)) {
                return true;
            }
            showCarCallAdvancedDialog(z, view);
            return true;
        }
        return false;
    }

    private boolean carCallAdvanceIntercept(boolean z, View view) {
        if (nedcIntercept(z, view)) {
            return true;
        }
        if (this.mLaboratoryViewModel.isParkByMemoryEnable()) {
            return false;
        }
        if (z) {
            isVuiAction(view);
            vuiFeedback(view, -1, getString(R.string.laboratory_remote_control_advanced_disable_pop));
        }
        ToastUtils.get().showText(R.string.laboratory_remote_control_advanced_disable_pop);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptRemotePark(View view, boolean z) {
        if (view == null) {
            return false;
        }
        boolean z2 = !this.mRemoteControlSwitch.isChecked();
        Logs.d("xplaboratory mRemoteControlSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (!z2 || this.mLaboratoryViewModel.getAutoParkSwEnable()) {
            return false;
        }
        if (z) {
            isVuiAction(view);
            vuiFeedback(view, -1, getString(R.string.laboratory_remote_control_key_disable_pop));
        }
        ToastUtils.get().showText(R.string.laboratory_remote_control_key_disable_pop);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptKeyPark(View view, boolean z) {
        if (view == null) {
            return false;
        }
        IntervalControl intervalControl = this.mKeyParkIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently()) {
            Logs.d("xplaboratory onInterceptCheck isFrequently");
            if (z) {
                isVuiAction(view);
            }
            return true;
        }
        boolean z2 = !this.mRemoteControlKeySwitch.isChecked();
        Logs.d("xplaboratory mRemoteControlKeySwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            if (nedcIntercept(z, view)) {
                return true;
            }
            if (!this.mLaboratoryViewModel.getAutoParkSwEnable()) {
                if (z) {
                    isVuiAction(view);
                    vuiFeedback(view, -1, getString(R.string.laboratory_remote_control_key_disable_pop));
                }
                ToastUtils.get().showText(R.string.laboratory_remote_control_key_disable_pop);
                return true;
            } else if (CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_REMOTE_PK_BTN)) {
                ToastUtils.get().showText(R.string.laboratory_key_parking_device_fault);
                if (z) {
                    vuiFeedback(view, -1, getString(R.string.laboratory_key_parking_device_fault));
                    isVuiAction(view);
                }
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptInductionLock(View view, boolean z) {
        if (view == null) {
            return false;
        }
        boolean z2 = !this.mInductionLockSwitch.isChecked();
        Logs.d("xplaboratory mInductionLockSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            showInductionLockDialog();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptSoliderCamera(View view, boolean z) {
        if (view == null) {
            return false;
        }
        IntervalControl intervalControl = this.mSoldierCameraIntervalControl;
        if (intervalControl != null && intervalControl.isFrequently(TypedValues.Motion.TYPE_STAGGER)) {
            if (z) {
                isVuiAction(view);
            }
            return true;
        }
        boolean z2 = !this.mSoldierCameraSwitch.isChecked();
        Logs.d("xplaboratory mSoldierCameraSwitch.onInterceptCheck b:" + z2 + " view.isPressed:" + view.isPressed());
        if (z2) {
            if (this.mLaboratoryViewModel.getSoldierSwState() == 0) {
                Logs.d("laboratory-soldier checkSoldierCameraSwitchEnable OFF ");
                ToastUtils.get().showText(R.string.laboratory_soldier_toast);
                if (z) {
                    vuiFeedback(view, 0, getString(R.string.laboratory_soldier_toast));
                    isVuiAction(view);
                }
            } else {
                boolean isSoldierCameraEnable = this.mLaboratoryViewModel.isSoldierCameraEnable();
                Logs.d("laboratory-soldier checkSoldierCameraSwitchEnable ON cameraEnable " + isSoldierCameraEnable);
                if (isSoldierCameraEnable) {
                    if (CarFunction.isSupportSoldierModeCameraNewDialog()) {
                        showSoldierDialogNewUI();
                    } else {
                        showSoldierDialog();
                    }
                } else {
                    ToastUtils.get().showText(R.string.laboratory_soldier_camera_tips_disable);
                    if (z) {
                        vuiFeedback(view, 1, getString(R.string.laboratory_soldier_camera_tips_disable));
                        isVuiAction(view);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void showInductionLockDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mInductionLockDialog == null) {
            this.mInductionLockDialog = new XDialog(getContext(), R.style.XDialogView_Large_AppLimit);
            this.mInductionLockDialog.setTitle(R.string.laboratory_induction_title);
            this.mInductionLockDialog.setMessage(R.string.laboratory_induction_lock_pop);
            this.mInductionLockDialog.setPositiveButton(getContext().getString(R.string.confirm_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$LHww5rnT71nsexWe8yYlV815TGU
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showInductionLockDialog$1$LaboratoryFragment(xDialog, i);
                }
            });
            this.mInductionLockDialog.setNegativeButton(getContext().getString(R.string.cancel));
        }
        this.mInductionLockDialog.show();
        VuiManager.instance().initVuiDialog(this.mInductionLockDialog, getContext(), VuiManager.SCENE_LABORATORY_INDUCTION_DIALOG);
    }

    public /* synthetic */ void lambda$showInductionLockDialog$1$LaboratoryFragment(XDialog xDialog, int i) {
        this.mInductionLockSwitch.setChecked(true);
        this.mLaboratoryViewModel.setInductionLock(true);
    }

    private void checkShowOrHidden(NetConfigValues netConfigValues) {
        this.mNetConfigValues = netConfigValues;
        int i = 0;
        this.mAppLimitView.setVisibility(netConfigValues.isAppUsedLimitNetEnable() ? 0 : 8);
        this.mLowSpeedViewD21.setVisibility((netConfigValues.isLowSpeedSoundNetEnable() && CarFunction.isSupportLowSpeedVolumeSwitch()) ? 0 : 8);
        this.mLowSpeedSliderView.setVisibility((netConfigValues.isLowSpeedSoundNetEnable() && CarFunction.isSupportLowSpeedVolumeSlider()) ? 0 : 8);
        this.mSoldierLevel.setVisibility((netConfigValues.isSoldierEnable() && CarFunction.isSupportSoldierModeLevel()) ? 0 : 8);
        this.mSoldierCamera.setVisibility((netConfigValues.isSoldierCameraEnable() && CarFunction.isSupportSoldierModeCamera()) ? 0 : 8);
        this.mRemoteControlViewD21.setVisibility((netConfigValues.isRemoteControlEnable() && CarFunction.isSupportRemotePark()) ? 0 : 8);
        this.mRemoteControlKeyView.setVisibility((netConfigValues.isRemoteControlKeyEnable() && CarFunction.isSupportKeyPark()) ? 0 : 8);
        this.mRemoteControlAdvancedView.setVisibility((netConfigValues.isRemoteControlAdvancedEnable() && CarFunction.isSupportRemoteParkAdvanced()) ? 0 : 8);
        this.mCarCallAdvancedView.setVisibility((netConfigValues.isCarCallAdvancedEnable() && CarFunction.isSupportCarCallAdvanced()) ? 0 : 8);
        if (this.mLowSpeedViewD21.getVisibility() == 8 && this.mRemoteControlViewD21.getVisibility() == 8) {
            this.mSecurityGroupHeader.setVisibility(8);
        } else {
            this.mSecurityGroupHeader.setVisibility(0);
            combineBg(this.mLowSpeedViewD21, this.mRemoteControlViewD21);
        }
        if (this.mLowSpeedSliderView.getVisibility() == 8 && this.mSoldierLevel.getVisibility() == 8 && this.mSoldierCamera.getVisibility() == 8 && this.mRemoteControlKeyView.getVisibility() == 8 && this.mRemoteControlAdvancedView.getVisibility() == 8 && this.mCarCallAdvancedView.getVisibility() == 8) {
            this.mSecurityGroupHeader.setVisibility(8);
        } else {
            this.mSecurityGroupHeader.setVisibility(0);
            if (this.mSoldierCamera.getVisibility() != 0) {
                ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$girEHi1Ny-8YAftpZhFaxl5_fdY
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaboratoryFragment.this.lambda$checkShowOrHidden$2$LaboratoryFragment();
                    }
                }, 50L);
            } else {
                ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$rRiZc8zbQ-kKtPjRTQhISdz2e1M
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaboratoryFragment.this.lambda$checkShowOrHidden$3$LaboratoryFragment();
                    }
                }, 50L);
            }
        }
        if (this.mLowSpeedViewD21.getVisibility() == 8 && this.mRemoteControlViewD21.getVisibility() == 8 && this.mLowSpeedSliderView.getVisibility() == 8 && this.mSoldierLevel.getVisibility() == 8 && this.mSoldierCamera.getVisibility() == 8 && this.mRemoteControlKeyView.getVisibility() == 8 && this.mRemoteControlAdvancedView.getVisibility() == 8 && this.mCarCallAdvancedView.getVisibility() == 8) {
            this.mSecurityGroupHeader.setVisibility(8);
        } else {
            this.mSecurityGroupHeader.setVisibility(0);
        }
        this.mInductionLockView.setVisibility((netConfigValues.isInductionLockEnable() && CarFunction.isSupportInductionLock()) ? 0 : 8);
        this.mNearUnlockView.setVisibility((netConfigValues.isNearUnlockEnable() && CarFunction.isSupportNearUnlock()) ? 0 : 8);
        this.mLeaveLockView.setVisibility((netConfigValues.isLeaveLockEnable() && CarFunction.isSupportLeaveLock()) ? 0 : 8);
        if (this.mNearUnlockView.getVisibility() == 8 && this.mLeaveLockView.getVisibility() == 8) {
            this.mInductiveUnlockingGroup.setVisibility(8);
        } else {
            this.mInductiveUnlockingGroup.setVisibility(0);
            combineBg(this.mNearUnlockView, this.mLeaveLockView);
        }
        if (this.mNearUnlockView.getVisibility() == 8 && this.mLeaveLockView.getVisibility() == 8 && this.mInductionLockView.getVisibility() == 8) {
            this.mInductiveUnlockingGroup.setVisibility(8);
        } else {
            this.mInductiveUnlockingGroup.setVisibility(0);
        }
        this.mAutoPowerOffView.setVisibility(netConfigValues.isAutoPowerOff() ? 0 : 8);
        XListMultiple xListMultiple = this.mTtsBroadcast;
        if (!netConfigValues.isTtsBroadcastEnable() || !CarFunction.isSupportAutoDriveTts()) {
            i = 8;
        }
        xListMultiple.setVisibility(i);
        refreshAllView();
    }

    public /* synthetic */ void lambda$checkShowOrHidden$2$LaboratoryFragment() {
        if (getContext() != null) {
            this.mSoldierLevel.setBackgroundResource(R.drawable.x_list_item_selector);
        }
    }

    public /* synthetic */ void lambda$checkShowOrHidden$3$LaboratoryFragment() {
        if (getContext() != null) {
            this.mSoldierLevel.setBackgroundResource(R.drawable.list_item_top_arc_bg);
        }
    }

    private void refreshAllView() {
        this.mTipsView.setVisibility(0);
        if (this.mAppLimitView.getVisibility() == 8 && this.mAutoPowerOffView.getVisibility() == 8 && this.mTtsBroadcast.getVisibility() == 8 && this.mInductiveUnlockingGroup.getVisibility() == 8 && this.mSecurityGroupHeader.getVisibility() == 8) {
            this.mEmptyView.setVisibility(0);
            this.mTipsView.setVisibility(8);
            return;
        }
        this.mEmptyView.setVisibility(8);
        this.mTipsView.setVisibility(0);
    }

    private void combineBg(View view, View view2) {
        if (view.getVisibility() == 8) {
            view2.setBackground(getContext().getDrawable(R.drawable.x_list_item_selector));
        } else if (view2.getVisibility() == 8) {
            view.setBackground(getContext().getDrawable(R.drawable.x_list_item_selector));
        } else {
            view.setBackground(getContext().getDrawable(R.drawable.list_item_top_arc_bg));
            view2.setBackground(getContext().getDrawable(R.drawable.list_item_bottom_arc_bg));
        }
    }

    private void showSoldierDialogNewUI() {
        if (getContext() == null) {
            return;
        }
        if (this.mSoldierDialog == null) {
            this.mSoldierDialog = new XDialog(getContext(), R.style.XDialogView_Large_AppLimit);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.laboratory_camera_protocol, this.mSoldierDialog.getContentView(), false);
            ((TextView) inflate.findViewById(R.id.camera_protocol_text)).setText(getString(R.string.laboratory_camera_open_tips));
            this.mCameraCheckboxButton = (XCompoundButton) inflate.findViewById(R.id.camera_checkboxbtn);
            this.mCameraCheckboxButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.10
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (((XCompoundButton) compoundButton).isFromUser() || ((compoundButton instanceof IVuiElement) && ((IVuiElement) compoundButton).isPerformVuiAction())) {
                        if (z) {
                            LaboratoryFragment.this.mSoldierDialog.setPositiveButtonEnable(true);
                        } else {
                            LaboratoryFragment.this.mSoldierDialog.setPositiveButtonEnable(false);
                        }
                        LaboratoryFragment.this.updateVuiScene(VuiManager.SCENE_LABORATORY_RECORD_VIDEO_DIALOG, LaboratoryFragment.this.mCameraCheckboxButton);
                    }
                }
            });
            this.mSoldierDialog.setTitle(getString(R.string.laboratory_soldier_dialog_title)).setCustomView(inflate, false).setPositiveButton(getString(R.string.laboratory_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$5d4P3jX9j0s8HvBgK7v8Kyat1aQ
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showSoldierDialogNewUI$4$LaboratoryFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel), (XDialogInterface.OnClickListener) null);
        }
        this.mSoldierDialog.setPositiveButtonEnable(false);
        this.mCameraCheckboxButton.setChecked(false);
        this.mSoldierDialog.show();
        VuiManager.instance().initVuiDialog(this.mSoldierDialog, getContext(), VuiManager.SCENE_LABORATORY_RECORD_VIDEO_DIALOG);
    }

    public /* synthetic */ void lambda$showSoldierDialogNewUI$4$LaboratoryFragment(XDialog xDialog, int i) {
        if (this.mCameraCheckboxButton.isChecked()) {
            if (this.mSoldierTabLayout.getSelectedTabIndex() == 0) {
                Logs.d("xplab current soldier off return!");
                return;
            }
            this.mSoldierCameraSwitch.setChecked(true);
            this.mLaboratoryViewModel.setSoldierCameraSw(true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B013", true);
        }
    }

    private void showSoldierDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mSoldierDialog == null) {
            this.mSoldierDialog = new XDialog(getContext());
            this.mSoldierDialog.setTitle(R.string.laboratory_soldier_dialog_title).setMessage(R.string.laboratory_soldier_dialog_msg).setPositiveButton(getResources().getString(R.string.confirm_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$yLvQvqOwGr3VUev1KM7ybPJHm2Q
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showSoldierDialog$5$LaboratoryFragment(xDialog, i);
                }
            }).setNegativeButton(getResources().getString(R.string.cancel), (XDialogInterface.OnClickListener) null);
        }
        this.mSoldierDialog.show();
        VuiManager.instance().initVuiDialog(this.mSoldierDialog, getContext(), VuiManager.SCENE_LABORATORY_RECORD_VIDEO_DIALOG);
    }

    public /* synthetic */ void lambda$showSoldierDialog$5$LaboratoryFragment(XDialog xDialog, int i) {
        if (this.mSoldierTabLayout.getSelectedTabIndex() == 0) {
            Logs.d("xplab current soldier off return!");
            return;
        }
        this.mSoldierCameraSwitch.setChecked(true);
        this.mLaboratoryViewModel.setSoldierCameraSw(true);
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B013", true);
    }

    private void showRemoteControlAdvancedDialog(final boolean z, final View view) {
        if (getContext() == null) {
            return;
        }
        if (this.mRemoteControlAdvancedDialog == null) {
            this.mRemoteControlAdvancedDialog = new XDialog(getContext());
            this.mRemoteControlAdvancedDialog.setTitle(R.string.laboratory_remote_control_advanced_dialog_title).setMessage(R.string.laboratory_remote_control_advanced_dialog_msg).setPositiveButton(getResources().getString(R.string.confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$EML4oWCRZJ95Mw9rtcwFaog3c8s
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showRemoteControlAdvancedDialog$6$LaboratoryFragment(z, view, xDialog, i);
                }
            }).setNegativeButton(getResources().getString(R.string.cancel), (XDialogInterface.OnClickListener) null);
        }
        this.mRemoteControlAdvancedDialog.show();
        VuiManager.instance().initVuiDialog(this.mRemoteControlAdvancedDialog, getContext(), VuiManager.SCENE_LABORATORY_PARKING_ADVANCED_DIALOG);
    }

    public /* synthetic */ void lambda$showRemoteControlAdvancedDialog$6$LaboratoryFragment(boolean z, View view, XDialog xDialog, int i) {
        if (remoteParkAdvanceIntercept(z, view)) {
            Logs.d("xplab remoteParkAdvanceIntercept return!");
            return;
        }
        this.mRemoteControlAdvancedSwitch.setChecked(true);
        this.mLaboratoryViewModel.setRemoteParkingAdvancedEnable(true);
    }

    private void showCarCallAdvancedDialog(final boolean z, final View view) {
        if (getContext() == null) {
            return;
        }
        if (this.mCarCallAdvancedDialog == null) {
            this.mCarCallAdvancedDialog = new XDialog(getContext());
            this.mCarCallAdvancedDialog.setTitle(R.string.laboratory_car_call_advanced_dialog_title).setMessage(R.string.laboratory_car_call_advanced_dialog_msg).setPositiveButton(getResources().getString(R.string.confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$3ltHD49IHh-B9eMIST_5MxlQav4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showCarCallAdvancedDialog$7$LaboratoryFragment(z, view, xDialog, i);
                }
            }).setNegativeButton(getResources().getString(R.string.cancel), (XDialogInterface.OnClickListener) null);
        }
        this.mCarCallAdvancedDialog.show();
        VuiManager.instance().initVuiDialog(this.mCarCallAdvancedDialog, getContext(), VuiManager.SCENE_LABORATORY_CAR_CALL_ADVANCED_DIALOG);
    }

    public /* synthetic */ void lambda$showCarCallAdvancedDialog$7$LaboratoryFragment(boolean z, View view, XDialog xDialog, int i) {
        if (carCallAdvanceIntercept(z, view)) {
            Logs.d("xplab carcall return!");
            return;
        }
        this.mCarCallAdvancedSwitch.setChecked(true);
        this.mLaboratoryViewModel.setCarCallAdvancedEnable(true);
    }

    private void showProtocolDialog() {
        if (getContext() == null) {
            return;
        }
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.show();
            VuiManager.instance().initVuiDialog(this.mXDialog, getContext(), VuiManager.SCENE_LABORATORY_NOTICE_DIALOG);
            return;
        }
        this.mXDialog = new XDialog(getContext(), R.style.XDialogView_Large);
        this.mXDialog.setTitle(getString(R.string.laboratory_tips)).setCloseVisibility(true).setMessage(getString(R.string.laboratory_tips_content)).show();
        VuiManager.instance().initVuiDialog(this.mXDialog, getContext(), VuiManager.SCENE_LABORATORY_NOTICE_DIALOG);
    }

    private void showNEDCDialog(int i, int i2) {
        if (getContext() == null) {
            return;
        }
        if (this.mNEDCDialog == null) {
            this.mNEDCDialog = new XDialog(getContext());
            this.mNEDCDialog.setPositiveButton(getString(R.string.dialog_know), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$f4VTer3zHKRGpfFv_fwI9vNq1Uk
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i3) {
                    LaboratoryFragment.this.lambda$showNEDCDialog$8$LaboratoryFragment(xDialog, i3);
                }
            });
        }
        this.mNEDCDialog.setTitle(i);
        this.mNEDCDialog.setMessage(i2);
        this.mNEDCDialog.show();
        VuiManager.instance().initVuiDialog(this.mNEDCDialog, getContext(), VuiManager.SCENE_NEDC_DIALOG);
    }

    public /* synthetic */ void lambda$showNEDCDialog$8$LaboratoryFragment(XDialog xDialog, int i) {
        this.mNEDCDialog.dismiss();
    }

    private void showAppLimitProtocolDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mAppLimitDialog == null) {
            this.mAppLimitDialog = new XDialog(getContext(), R.style.XDialogView_Large_AppLimit);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.laboratory_unread_protocol, this.mAppLimitDialog.getContentView(), false);
            ((TextView) inflate.findViewById(R.id.protocol_text)).setText(getString(R.string.laboratory_app_limit_tips));
            this.mCheckboxButton = (XCompoundButton) inflate.findViewById(R.id.checkboxbtn);
            this.mCheckboxButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.11
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (((XCompoundButton) compoundButton).isFromUser() || ((compoundButton instanceof IVuiElement) && ((IVuiElement) compoundButton).isPerformVuiAction())) {
                        if (z) {
                            LaboratoryFragment.this.mAppLimitDialog.setPositiveButtonEnable(true);
                        } else {
                            LaboratoryFragment.this.mAppLimitDialog.setPositiveButtonEnable(false);
                        }
                        LaboratoryFragment.this.updateVuiScene(VuiManager.SCENE_LABORATORY_LIMIT_NOTICE_DIALOG, LaboratoryFragment.this.mCheckboxButton);
                    }
                }
            });
            this.mAppLimitDialog.setTitle(getString(R.string.laboratory_app_limit_title)).setCustomView(inflate, false).setPositiveButton(getString(R.string.laboratory_close), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$BRmbYpkI87xjsMTnY8jS3U-WREs
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LaboratoryFragment.this.lambda$showAppLimitProtocolDialog$9$LaboratoryFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel), (XDialogInterface.OnClickListener) null);
        }
        this.mAppLimitDialog.setPositiveButtonEnable(false);
        this.mCheckboxButton.setChecked(false);
        this.mAppLimitDialog.show();
        VuiManager.instance().initVuiDialog(this.mAppLimitDialog, getContext(), VuiManager.SCENE_LABORATORY_LIMIT_NOTICE_DIALOG);
    }

    public /* synthetic */ void lambda$showAppLimitProtocolDialog$9$LaboratoryFragment(XDialog xDialog, int i) {
        if (this.mCheckboxButton.isChecked()) {
            this.mAppLimitSwitch.setChecked(false);
            this.mLaboratoryViewModel.enableAppLimit(false);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B002", false);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mLaboratoryViewModel = (LaboratoryViewModel) ViewModelUtils.getViewModel(this, LaboratoryViewModel.class);
        this.mLaboratoryViewModel.getNetConfigValuesLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$7S4An3nxNaCBDbYMpBQvSNeUBGY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$10$LaboratoryFragment((NetConfigValues) obj);
            }
        });
        this.mLaboratoryViewModel.getLowSpeedVolumeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$P7rU2d0e6DUnD7zgFMswvw-dyyg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$11$LaboratoryFragment((Integer) obj);
            }
        });
        this.mLaboratoryViewModel.getLowSpeedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$RU996OaXp-lxuSyi_DX7FEDptso
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$12$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getRemoteParkLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$wDr49StMuPcLHMuSvDpwvl-QZoI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$13$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getKeyParkLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$rrEAtRi2dsawC3ySNNp_YWoEx7A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$14$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getAppLimitLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$QLNUn2d9yOA4VUCFHiTlHT95YVc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$15$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getKeyParkAdvancedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$Gk0DBqXF83DUvVBTTKO0SkGFZcE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$16$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getCarCallAdvancedLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$3W6Pl5vAM3XTDMjvkmYSOrVMLwk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$17$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getAutoPowerOffLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$UxT-d3KEIslCyW_2f3xxXfwzBKk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$18$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getNearUnlockLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$DZ1mu3tWWmt2dHsguy27TMWHuD8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$19$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getLeaveLockLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$ohUlt1ZjoZatDtWAh_Lgmrw2LiY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$20$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getInductionLockEnable().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$9IXwGuyH-B-finHEB3SbDEDxKLs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$21$LaboratoryFragment((Boolean) obj);
            }
        });
        this.mLaboratoryViewModel.getNedcModeStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$v_6kiEHF66kLMd4fHa6wd2tBWhI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LaboratoryFragment.this.lambda$init$22$LaboratoryFragment((Integer) obj);
            }
        });
        if (CarFunction.isSupportLowSpeedVolumeSlider()) {
            this.mLowSpeedSlider.setAccuracy(0.1f);
            this.mLowSpeedSlider.setStartIndex(0);
            this.mLowSpeedSlider.setEndIndex(100);
            this.mLowSpeedSlider.setInitIndex(this.mLaboratoryViewModel.getLowSpeedVolume());
        }
        if (this.mSoldierLevel.getVisibility() == 0) {
            this.mLaboratoryViewModel.getSoldierStatusLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$f-vPPMwknTP1uS_EfgQLAYsM9aE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LaboratoryFragment.this.lambda$init$23$LaboratoryFragment((Integer) obj);
                }
            });
            this.mLaboratoryViewModel.getSoldierCameraLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$6lsMcxL-uOEZ_UtBnbqVAFkZWhM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LaboratoryFragment.this.lambda$init$24$LaboratoryFragment((Boolean) obj);
                }
            });
            this.mLaboratoryViewModel.getSoldierCameraEnableLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$LaboratoryFragment$aNAZoK-KJKdfAvNP9CiAo0y5gl8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LaboratoryFragment.this.lambda$init$25$LaboratoryFragment((Boolean) obj);
                }
            });
            checkSoldierCameraSwitchEnable();
        }
    }

    public /* synthetic */ void lambda$init$10$LaboratoryFragment(NetConfigValues netConfigValues) {
        Logs.d("xplaboratory getNetConfig:" + netConfigValues);
        checkShowOrHidden(netConfigValues);
        updateVuiElementUnSupportAttr(netConfigValues);
        updateVuiScene(this.sceneId, this.mAppLimitView, this.mLowSpeedViewD21, this.mLowSpeedSliderView, this.mNearUnlockView, this.mLeaveLockView, this.mInductionLockView, this.mRemoteControlViewD21, this.mSoldierCamera, this.mSoldierLevel, this.mTtsBroadcast, this.mTipsView, this.mRemoteControlKeyView, this.mRemoteControlAdvancedView, this.mCarCallAdvancedView);
    }

    public /* synthetic */ void lambda$init$11$LaboratoryFragment(Integer num) {
        this.mLowSpeedSlider.setCurrentIndex(num.intValue());
    }

    public /* synthetic */ void lambda$init$12$LaboratoryFragment(Boolean bool) {
        this.mLowSpeedSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$13$LaboratoryFragment(Boolean bool) {
        this.mRemoteControlSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$14$LaboratoryFragment(Boolean bool) {
        if (isInNedcMode()) {
            Logs.d("xplaboratory RemoteControlKey check false");
            this.mRemoteControlKeySwitch.setChecked(false);
            return;
        }
        this.mRemoteControlKeySwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$15$LaboratoryFragment(Boolean bool) {
        this.mAppLimitSwitch.setChecked(this.mLaboratoryViewModel.isAppLimitEnable());
    }

    public /* synthetic */ void lambda$init$16$LaboratoryFragment(Boolean bool) {
        if (isInNedcMode()) {
            Logs.d("xplaboratory RemoteControlAdvance check false");
            this.mRemoteControlAdvancedSwitch.setChecked(false);
            return;
        }
        this.mRemoteControlAdvancedSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$17$LaboratoryFragment(Boolean bool) {
        if (isInNedcMode()) {
            Logs.d("xplaboratory carcallAdvance check false");
            this.mCarCallAdvancedSwitch.setChecked(false);
            return;
        }
        this.mCarCallAdvancedSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$18$LaboratoryFragment(Boolean bool) {
        this.mAutoPowerOffSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$19$LaboratoryFragment(Boolean bool) {
        this.mNearUnlockSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$20$LaboratoryFragment(Boolean bool) {
        this.mLeaveLockSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$21$LaboratoryFragment(Boolean bool) {
        this.mInductionLockSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$22$LaboratoryFragment(Integer num) {
        Logs.d("xplaboratory getNedcModeStatus status:" + num);
        if (isInNedcMode()) {
            offNedcSwitch();
        }
    }

    public /* synthetic */ void lambda$init$23$LaboratoryFragment(Integer num) {
        Logs.d("laboratory-soldier level observe integer : " + num);
        this.mSoldierTabLayout.selectTab(num.intValue(), true);
        int intValue = num.intValue();
        if (intValue == 0) {
            this.mSoldierCamera.setEnabled(false);
            this.mSoldierCameraSwitch.setChecked(false);
        } else if (intValue == 1 || intValue == 2 || intValue == 3) {
            this.mSoldierCamera.setEnabled(true);
        }
        checkSoldierCameraSwitchEnable();
    }

    public /* synthetic */ void lambda$init$24$LaboratoryFragment(Boolean bool) {
        Logs.d("laboratory-soldier camera observe aBoolean : " + bool);
        this.mSoldierCameraSwitch.setChecked(bool.booleanValue());
    }

    public /* synthetic */ void lambda$init$25$LaboratoryFragment(Boolean bool) {
        Logs.d("laboratory-soldier camera enable observe aBoolean : " + bool);
        if (!bool.booleanValue()) {
            this.mSoldierCameraSwitch.setChecked(false);
        }
        checkSoldierCameraSwitchEnable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkSoldierCameraSwitchEnable() {
        boolean isSoldierCameraEnable = this.mLaboratoryViewModel.isSoldierCameraEnable();
        Logs.d("laboratory-soldier checkSoldierCameraSwitchEnable cameraEnable " + isSoldierCameraEnable);
        this.mSoldierLevel.setTextSub(getString(isSoldierCameraEnable ? R.string.laboratory_soldier_tips : R.string.laboratory_soldier_tips_disable));
        this.mSoldierTabLayout.setEnabled(isSoldierCameraEnable);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("xplaboratory fragment onHiddenChanged " + isHidden() + " " + this.mIsRegisterFresh);
        if (!z) {
            refreshLaboratoryData();
            return;
        }
        unRefreshLaboratoryData();
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        refreshLaboratoryData();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (!isHidden() || this.mIsRegisterFresh) {
            unRefreshLaboratoryData();
        }
    }

    private void unRefreshLaboratoryData() {
        if (this.mIsRegisterFresh) {
            this.mLaboratoryViewModel.onStopVM();
            this.mIsRegisterFresh = false;
            Logs.d("xplaboratory fragment fresh unRefreshLaboratoryData");
        }
    }

    private void dismissDialog() {
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        XDialog xDialog2 = this.mAppLimitDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
        }
        XDialog xDialog3 = this.mSoldierDialog;
        if (xDialog3 != null) {
            xDialog3.dismiss();
        }
        XDialog xDialog4 = this.mNEDCDialog;
        if (xDialog4 != null) {
            xDialog4.dismiss();
        }
        XDialog xDialog5 = this.mInductionLockDialog;
        if (xDialog5 != null) {
            xDialog5.dismiss();
        }
        XDialog xDialog6 = this.mRemoteControlAdvancedDialog;
        if (xDialog6 != null) {
            xDialog6.dismiss();
        }
        XDialog xDialog7 = this.mCarCallAdvancedDialog;
        if (xDialog7 != null) {
            xDialog7.dismiss();
        }
    }

    private void refreshLaboratoryData() {
        if (this.mIsRegisterFresh) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mLaboratoryViewModel.onStartVM();
        this.mAppLimitSwitch.setChecked(this.mLaboratoryViewModel.isAppLimitEnable());
        this.mAutoPowerOffSwitch.setChecked(this.mLaboratoryViewModel.getAutoPowerOff());
        if (CarFunction.isSupportLowSpeedVolumeSwitch()) {
            this.mLowSpeedSwitch.setChecked(this.mLaboratoryViewModel.isLowSpeedSoundEnable());
        }
        if (CarFunction.isSupportRemotePark()) {
            this.mRemoteControlSwitch.setChecked(this.mLaboratoryViewModel.isRemoteParkingEnable());
        }
        if (CarFunction.isSupportLowSpeedVolumeSlider()) {
            this.mLowSpeedSlider.setCurrentIndex(this.mLaboratoryViewModel.getLowSpeedVolume());
        }
        if (CarFunction.isSupportLeaveLock()) {
            this.mLeaveLockSwitch.setChecked(this.mLaboratoryViewModel.getLeaveLockEnable());
        }
        if (CarFunction.isSupportNearUnlock()) {
            this.mNearUnlockSwitch.setChecked(this.mLaboratoryViewModel.getNearUnlockEnable());
        }
        if (CarFunction.isSupportSoldierModeLevel()) {
            this.mLaboratoryViewModel.refreshSoldierSwState();
        }
        if (CarFunction.isSupportSoldierModeCamera()) {
            this.mLaboratoryViewModel.refreshSoldierCameraSwState();
            this.mLaboratoryViewModel.refreshSoldierCameraEnable();
        }
        if (isInNedcMode()) {
            Logs.d("xplaboratory isInNedcMode!");
            offNedcSwitch();
        } else {
            if (CarFunction.isSupportKeyPark()) {
                if (CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_REMOTE_PK_BTN)) {
                    Logs.d("xplaboratory key park lost!");
                    this.mRemoteControlKeySwitch.setChecked(false);
                } else {
                    this.mRemoteControlKeySwitch.setChecked(this.mLaboratoryViewModel.isKeyParkingEnable());
                }
            }
            if (CarFunction.isSupportRemoteParkAdvanced()) {
                if (CarSettingsManager.isDeviceSignalLostFault(CarSettingsManager.ID_SCU_PHONE_PK_BTN)) {
                    Logs.d("xplaboratory advanced park lost!");
                    this.mRemoteControlAdvancedSwitch.setChecked(false);
                } else {
                    this.mRemoteControlAdvancedSwitch.setChecked(this.mLaboratoryViewModel.isRemoteParkingAdvancedEnable());
                }
            }
            if (CarFunction.isSupportCarCallAdvanced()) {
                this.mCarCallAdvancedSwitch.setChecked(this.mLaboratoryViewModel.isCarCallAdvancedEnable());
            }
        }
        if (CarFunction.isSupportInductionLock()) {
            this.mLaboratoryViewModel.isInductionLockEnable();
        }
        if (CarFunction.isSupportAutoDriveTts()) {
            refreshTtsBroadcast();
        }
        Logs.d("xplaboratory fragment fresh refreshLaboratoryData cost time:" + (System.currentTimeMillis() - currentTimeMillis));
        this.mIsRegisterFresh = true;
    }

    private void refreshTtsBroadcast() {
        int ttsBroadcastType = this.mLaboratoryViewModel.getTtsBroadcastType();
        if (ttsBroadcastType == 0) {
            this.mTtsBroadcastTabLayout.selectTab(0, false);
        } else if (ttsBroadcastType != 1) {
        } else {
            this.mTtsBroadcastTabLayout.selectTab(1, false);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        updateVuiScene(this.sceneId, compoundButton);
        if (((XSwitch) compoundButton).isFromUser() || isVuiAction(compoundButton)) {
            if (compoundButton == this.mAppLimitSwitch) {
                this.mLaboratoryViewModel.enableAppLimit(z);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B002", z);
            } else if (compoundButton == this.mLowSpeedSwitch) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B005", z);
                this.mLaboratoryViewModel.enableLowSpeedSound(z);
            } else if (compoundButton == this.mAutoPowerOffSwitch) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B003", z);
                this.mLaboratoryViewModel.enableAutoPowerOff(z);
            } else if (compoundButton == this.mRemoteControlSwitch) {
                this.mLaboratoryViewModel.setRemoteParkingEnable(z);
            } else if (compoundButton == this.mSoldierCameraSwitch) {
                this.mLaboratoryViewModel.setSoldierCameraSw(z);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B013", z);
            } else if (compoundButton == this.mNearUnlockSwitch) {
                this.mLaboratoryViewModel.setNearUnlockEnable(z);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B010", z);
            } else if (compoundButton == this.mLeaveLockSwitch) {
                this.mLaboratoryViewModel.setLeaveLockEnable(z);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B011", z);
            } else {
                XSwitch xSwitch = this.mRemoteControlKeySwitch;
                if (compoundButton == xSwitch) {
                    xSwitch.setEnabled(false);
                    this.mHandler.removeCallbacks(this.mTimeoutRunnable);
                    this.mHandler.postDelayed(this.mTimeoutRunnable, 300L);
                    this.mLaboratoryViewModel.setKeyParkingEnable(z);
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B015", z);
                } else if (compoundButton == this.mInductionLockSwitch) {
                    this.mLaboratoryViewModel.setInductionLock(z);
                } else if (compoundButton == this.mRemoteControlAdvancedSwitch) {
                    this.mLaboratoryViewModel.setRemoteParkingAdvancedEnable(z);
                } else if (compoundButton == this.mCarCallAdvancedSwitch) {
                    this.mLaboratoryViewModel.setCarCallAdvancedEnable(z);
                }
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStopTrackingTouch(XSlider xSlider) {
        if (xSlider == this.mLowSpeedSlider) {
            this.mLaboratoryViewModel.setLowSpeedVolume((int) xSlider.getIndicatorValue());
            updateVuiScene(this.sceneId, xSlider);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.LABORATORY_PAGE_ID, "B014", BuriedPointUtils.COUNT_KEY, (int) xSlider.getIndicatorValue());
        }
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
    public void onProgressChanged(XSlider xSlider, float f, String str, boolean z) {
        if (xSlider == this.mLowSpeedSlider) {
            this.mLaboratoryViewModel.setLowSpeedVolume((int) xSlider.getIndicatorValue());
            updateVuiScene(this.sceneId, xSlider);
        }
    }

    private void initVui() {
        if (this.mVuiHandler != null) {
            this.mVuiHandler.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment.12
                @Override // java.lang.Runnable
                public void run() {
                    LaboratoryFragment.this.mAppLimitSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_app_used_limit));
                    LaboratoryFragment.this.mLowSpeedSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.low_speed_analog_cue_tone));
                    LaboratoryFragment.this.mLowSpeedSlider.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_low_speed_alarm_sound));
                    LaboratoryFragment.this.mAutoPowerOffSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_auto_power_off));
                    LaboratoryFragment.this.mRemoteControlSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_remote_control));
                    LaboratoryFragment.this.mSoldierCameraSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_soldier_camera_level));
                    LaboratoryFragment.this.mNearUnlockSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_near_unlocking_title));
                    LaboratoryFragment.this.mLeaveLockSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_leave_locking_title));
                    LaboratoryFragment.this.mInductionLockSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_induction_lock));
                    LaboratoryFragment.this.mRemoteControlKeySwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_remote_control_key));
                    LaboratoryFragment.this.mRemoteControlAdvancedSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_remote_control_advanced));
                    LaboratoryFragment.this.mCarCallAdvancedSwitch.setVuiLabel(LaboratoryFragment.this.getString(R.string.laboratory_car_call_advanced));
                    LaboratoryFragment laboratoryFragment = LaboratoryFragment.this;
                    laboratoryFragment.supportFeedback(laboratoryFragment.mRemoteControlKeySwitch);
                    LaboratoryFragment laboratoryFragment2 = LaboratoryFragment.this;
                    laboratoryFragment2.supportFeedback(laboratoryFragment2.mRemoteControlSwitch);
                    LaboratoryFragment laboratoryFragment3 = LaboratoryFragment.this;
                    laboratoryFragment3.supportFeedback(laboratoryFragment3.mSoldierCameraSwitch);
                    LaboratoryFragment laboratoryFragment4 = LaboratoryFragment.this;
                    laboratoryFragment4.supportFeedback(laboratoryFragment4.mSoldierTabLayout);
                    LaboratoryFragment laboratoryFragment5 = LaboratoryFragment.this;
                    laboratoryFragment5.supportFeedback(laboratoryFragment5.mRemoteControlAdvancedSwitch);
                    LaboratoryFragment laboratoryFragment6 = LaboratoryFragment.this;
                    laboratoryFragment6.supportFeedback(laboratoryFragment6.mCarCallAdvancedSwitch);
                    LaboratoryFragment laboratoryFragment7 = LaboratoryFragment.this;
                    laboratoryFragment7.supportFeedback(laboratoryFragment7.mSoldierCameraSwitch);
                    LaboratoryFragment.this.updateVuiElementUnSupportAttr(new NetConfigValues());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVuiElementUnSupportAttr(NetConfigValues netConfigValues) {
        boolean z = true;
        setVuiElementUnSupport(this.mAppLimitView, !netConfigValues.isAppUsedLimitNetEnable());
        setVuiElementUnSupport(this.mLowSpeedViewD21, (netConfigValues.isLowSpeedSoundNetEnable() && CarFunction.isSupportLowSpeedVolumeSwitch()) ? false : true);
        setVuiElementUnSupport(this.mLowSpeedSliderView, (netConfigValues.isLowSpeedSoundNetEnable() && CarFunction.isSupportLowSpeedVolumeSlider()) ? false : true);
        setVuiElementUnSupport(this.mAutoPowerOffView, !netConfigValues.isAutoPowerOff());
        setVuiElementUnSupport(this.mRemoteControlViewD21, (netConfigValues.isRemoteControlEnable() && CarFunction.isSupportRemotePark()) ? false : true);
        setVuiElementUnSupport(this.mSoldierLevel, (netConfigValues.isSoldierEnable() && CarFunction.isSupportSoldierModeLevel()) ? false : true);
        setVuiElementUnSupport(this.mSoldierCamera, (netConfigValues.isSoldierCameraEnable() && CarFunction.isSupportSoldierModeCamera()) ? false : true);
        setVuiElementUnSupport(this.mInductionLockView, (netConfigValues.isInductionLockEnable() && CarFunction.isSupportInductionLock()) ? false : true);
        setVuiElementUnSupport(this.mNearUnlockView, (netConfigValues.isNearUnlockEnable() && CarFunction.isSupportNearUnlock()) ? false : true);
        setVuiElementUnSupport(this.mLeaveLockView, (netConfigValues.isLeaveLockEnable() && CarFunction.isSupportLeaveLock()) ? false : true);
        setVuiElementUnSupport(this.mLeaveLockView, (netConfigValues.isLeaveLockEnable() && CarFunction.isSupportLeaveLock()) ? false : true);
        setVuiElementUnSupport(this.mRemoteControlKeyView, (CarFunction.isSupportKeyPark() && netConfigValues.isRemoteControlKeyEnable()) ? false : true);
        setVuiElementUnSupport(this.mRemoteControlAdvancedView, (CarFunction.isSupportRemoteParkAdvanced() && netConfigValues.isRemoteControlAdvancedEnable()) ? false : true);
        setVuiElementUnSupport(this.mCarCallAdvancedView, (CarFunction.isSupportCarCallAdvanced() && netConfigValues.isCarCallAdvancedEnable()) ? false : true);
        XListMultiple xListMultiple = this.mTtsBroadcast;
        if (netConfigValues.isTtsBroadcastEnable() && CarFunction.isSupportAutoDriveTts()) {
            z = false;
        }
        setVuiElementUnSupport(xListMultiple, z);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return new String[]{"/laboratory/agreement_pop"};
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(String str) {
        if ("/laboratory/agreement_pop".equals(str)) {
            showProtocolDialog();
        }
        super.onPageDirectShow(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        dismissDialog();
    }

    private boolean isInNedcMode() {
        return this.mLaboratoryViewModel.isInNedcMode();
    }

    private void offNedcSwitch() {
        if (CarFunction.isSupportKeyPark()) {
            this.mRemoteControlKeySwitch.setChecked(false);
        }
        if (CarFunction.isSupportRemoteParkAdvanced()) {
            this.mRemoteControlAdvancedSwitch.setChecked(false);
        }
        if (CarFunction.isSupportCarCallAdvanced()) {
            this.mCarCallAdvancedSwitch.setChecked(false);
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!XpThemeUtils.isThemeChanged(configuration) || this.mNetConfigValues == null) {
            return;
        }
        Logs.d("onConfigurationChanged mNetConfigValues:" + this.mNetConfigValues);
        checkShowOrHidden(this.mNetConfigValues);
    }
}
