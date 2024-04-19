package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ProjectorBluetoothViewModel;
import com.xiaopeng.car.settingslibrary.vm.speech.SpeechPlayViewModel;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class ProjectorConnectFragment extends BaseFragment {
    public static final String FROM_LAYOUT_FLAT = "space_cinema_guide_flat";
    public static final String FROM_LAYOUT_USER = "space_cinema_from_user";
    private static final int PAIR_CONNECT_TIMEOUT_BLE = 80000;
    private static final int SCAN_TIMEOUT_BLE = 60000;
    private XImageView mBtConnectImg;
    private ICapsuleCinemaGuideInterface mCinemaGuideListener;
    private XButton mCompleteBtBtn;
    private XButton mOpenBtBtn;
    private Handler mPairAndConnectTimeoutHandler;
    private XLoading mParingLoading;
    private XButton mPreBtBtn;
    private XTextView mResultTv;
    private Handler mScanHalfTimeoutHandler;
    private Handler mScanTimeoutHandler;
    private SpeechPlayViewModel mSpeechPlayViewModel;
    ProjectorBluetoothViewModel mViewModel;
    private Handler mHandler = new Handler();
    private Runnable mScanTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$AFsVYb6CkRLcXh_BoSZxM7SFkus
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorConnectFragment.this.searchTimeout();
        }
    };
    private Runnable mScanHalfTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$lnGhQ6IsGKLF6qGHGNgUf6qjDG8
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorConnectFragment.this.searchHalfTimeout();
        }
    };
    private Runnable mPairAndConnectTimeoutRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$Wtu12nRc-LZmWttbfl9IJ9BDhd0
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorConnectFragment.this.pairAndConnectTimeout();
        }
    };
    private boolean mIsConnectTtsPlayed = false;
    private boolean mIsAlreadyFounded = false;
    private boolean mLayoutFlat = false;
    private boolean mFromUser = false;
    private IntervalControl mBondingTtsIntervalControl = new IntervalControl("bonding-tts");
    private IntervalControl mConnectedTtsIntervalControl = new IntervalControl("connected-tts");

    public static ProjectorConnectFragment newInstance(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(FROM_LAYOUT_FLAT, z);
        bundle.putBoolean(FROM_LAYOUT_USER, z2);
        ProjectorConnectFragment projectorConnectFragment = new ProjectorConnectFragment();
        projectorConnectFragment.setArguments(bundle);
        return projectorConnectFragment;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_cinema_projector_bt;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mParingLoading = (XLoading) view.findViewById(R.id.capsule_cinema_projector_connect_loading);
        this.mResultTv = (XTextView) view.findViewById(R.id.capsule_cinema_projector_connect_result);
        this.mOpenBtBtn = (XButton) view.findViewById(R.id.capsule_cinema_projector_open_bt);
        this.mOpenBtBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$SpnIDULilx9DF4mXEaOhpN6Rjtk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectorConnectFragment.this.lambda$initView$0$ProjectorConnectFragment(view2);
            }
        });
        this.mBtConnectImg = (XImageView) view.findViewById(R.id.capsule_cinema_projector_bt_connect_img);
        this.mPreBtBtn = (XButton) view.findViewById(R.id.capsule_cinema_projector_bt_pre_btn);
        this.mPreBtBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$y5Uuh1WkhqMvgT0fsg3NQWSMM0Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectorConnectFragment.this.lambda$initView$1$ProjectorConnectFragment(view2);
            }
        });
        this.mCompleteBtBtn = (XButton) view.findViewById(R.id.capsule_cinema_projector_bt_complete_btn);
        this.mCompleteBtBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$s-y1XpuNUwCDYC8E3IqI-xU4yco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectorConnectFragment.this.lambda$initView$2$ProjectorConnectFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$ProjectorConnectFragment(View view) {
        Intent intent = new Intent(Config.POPUP_BLUETOOTH);
        intent.setFlags(268435456);
        getContext().startActivity(intent);
    }

    public /* synthetic */ void lambda$initView$1$ProjectorConnectFragment(View view) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorSetup();
        }
    }

    public /* synthetic */ void lambda$initView$2$ProjectorConnectFragment(View view) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorBtConnected();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        Bundle arguments = getArguments();
        this.mLayoutFlat = arguments.getBoolean(FROM_LAYOUT_FLAT, false);
        this.mFromUser = arguments.getBoolean(FROM_LAYOUT_USER, false);
        this.mViewModel = (ProjectorBluetoothViewModel) ViewModelUtils.getViewModel(this, ProjectorBluetoothViewModel.class);
        this.mSpeechPlayViewModel = (SpeechPlayViewModel) ViewModelUtils.getViewModel(this, SpeechPlayViewModel.class);
        enter();
        this.mViewModel.getProjectStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$TdrPNwzLzXGQBth81k76XfItnX8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ProjectorConnectFragment.this.lambda$init$3$ProjectorConnectFragment((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$3$ProjectorConnectFragment(Integer num) {
        Logs.d("ProjectorConnectFragment projector status:" + num + ", isHidden: " + isHidden());
        if (isHidden()) {
            return;
        }
        if (num.intValue() == 4) {
            cancelScanTimeout();
            if (!this.mViewModel.isProjectorAlreadyConnected()) {
                Logs.d("ProjectorConnectFragment PROJECTOR_FOUNDED  mIsAlreadyFounded:" + this.mIsAlreadyFounded);
                if (this.mIsAlreadyFounded) {
                    return;
                }
                this.mViewModel.startPairOrConnectProjector();
                startPairAndConnectTimeout();
                this.mIsAlreadyFounded = true;
                return;
            }
            switchToSuccessStatus();
            cancelPairAndConnectTimeout();
            cancelScanTimeout();
        } else if (num.intValue() == 5) {
            if (this.mBondingTtsIntervalControl.isFrequently(10000)) {
                Logs.d("ProjectorConnectFragment bonding tts frequently.");
                return;
            }
            switchToPairingStatus();
            cancelScanTimeout();
        } else if (num.intValue() == 8) {
            switchToSuccessStatus();
            cancelPairAndConnectTimeout();
            cancelScanTimeout();
        } else if (num.intValue() == 7 || num.intValue() == 6) {
            if (!this.mIsConnectTtsPlayed && this.mViewModel.isProjectorAlreadyBonded()) {
                this.mIsConnectTtsPlayed = true;
                if (!this.mViewModel.isProjectorAlreadyConnected()) {
                    switchToConnectingStatus();
                }
            }
            cancelScanTimeout();
        } else if (num.intValue() == 1 || num.intValue() == 2 || num.intValue() == 3) {
            switchToOvertimeStatus();
            cancelPairAndConnectTimeout();
            cancelScanTimeout();
        }
    }

    private void enter() {
        this.mIsAlreadyFounded = true;
        if (!this.mViewModel.isProjectorAlreadyConnected()) {
            switchToSearchStatus();
            this.mViewModel.setProjectorBluetoothDoing(true);
            this.mViewModel.registerProjectorStateCallback();
            Logs.d("ProjectorConnectFragment enter mFromUser:" + this.mFromUser + " isProjectorFounded:" + this.mViewModel.isProjectorFounded());
            if (this.mFromUser) {
                if (!this.mViewModel.isProjectorFounded()) {
                    this.mViewModel.startScanProjector();
                    startScanTimeout();
                    this.mIsAlreadyFounded = false;
                    return;
                } else if (this.mViewModel.isProjectorConnecting()) {
                    switchToConnectingStatus();
                    cancelScanTimeout();
                    return;
                } else if (this.mViewModel.isProjectorBonding()) {
                    switchToPairingStatus();
                    cancelScanTimeout();
                    return;
                } else {
                    this.mViewModel.startPairOrConnectProjector();
                    cancelScanTimeout();
                    startPairAndConnectTimeout();
                    return;
                }
            }
            this.mViewModel.startPairOrConnectProjector();
            cancelScanTimeout();
            startPairAndConnectTimeout();
            return;
        }
        switchToSuccessStatus();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICapsuleCinemaGuideInterface) {
            this.mCinemaGuideListener = (ICapsuleCinemaGuideInterface) context;
        }
    }

    private void switchToSearchStatus() {
        this.mParingLoading.setVisibility(0);
        this.mBtConnectImg.setVisibility(8);
        this.mResultTv.setText(R.string.cinema_projector_bt_search);
        this.mOpenBtBtn.setVisibility(8);
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$IxlWjefTE3YGovGFU3E9mI_DkvI
            @Override // java.lang.Runnable
            public final void run() {
                ProjectorConnectFragment.this.lambda$switchToSearchStatus$4$ProjectorConnectFragment();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$switchToSearchStatus$4$ProjectorConnectFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_search_tts));
    }

    private void switchToPairingStatus() {
        this.mParingLoading.setVisibility(0);
        this.mBtConnectImg.setVisibility(8);
        this.mResultTv.setText(R.string.cinema_projector_bt_pairing);
        this.mOpenBtBtn.setVisibility(8);
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$PIQycyy_fOMN3U25lQNglqP9IGY
            @Override // java.lang.Runnable
            public final void run() {
                ProjectorConnectFragment.this.lambda$switchToPairingStatus$5$ProjectorConnectFragment();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$switchToPairingStatus$5$ProjectorConnectFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_pairing_tts));
    }

    private void switchToConnectingStatus() {
        this.mParingLoading.setVisibility(0);
        this.mBtConnectImg.setVisibility(8);
        this.mResultTv.setText(R.string.cinema_projector_bt_connecting);
        this.mOpenBtBtn.setVisibility(8);
    }

    private void switchToOvertimeStatus() {
        this.mParingLoading.setVisibility(8);
        this.mBtConnectImg.setVisibility(0);
        this.mBtConnectImg.setImageResource(R.drawable.capsule_cinema_projector_bt_connect_fail);
        this.mResultTv.setText(R.string.cinema_projector_bt_connect_fail);
        this.mOpenBtBtn.setVisibility(0);
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$oFxCqNeAGjfn4undvZ7dwywXWA8
            @Override // java.lang.Runnable
            public final void run() {
                ProjectorConnectFragment.this.lambda$switchToOvertimeStatus$6$ProjectorConnectFragment();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$switchToOvertimeStatus$6$ProjectorConnectFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_connect_fail_tts));
    }

    private void switchToSuccessStatus() {
        if (this.mConnectedTtsIntervalControl.isFrequently(5000)) {
            Logs.d("ProjectorConnectFragment connected tts frequently.");
            return;
        }
        this.mParingLoading.setVisibility(8);
        this.mOpenBtBtn.setVisibility(8);
        this.mBtConnectImg.setVisibility(0);
        this.mBtConnectImg.setImageResource(R.drawable.capsule_cinema_projector_bt_connect_success);
        this.mResultTv.setText(R.string.cinema_projector_bt_connect_success);
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_connect_success_tts));
        if (this.mLayoutFlat) {
            ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorConnectFragment$D0W4g7jKzFgYIpbz4hUqdzlvMh4
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectorConnectFragment.this.lambda$switchToSuccessStatus$7$ProjectorConnectFragment();
                }
            }, 8000L);
        }
    }

    public /* synthetic */ void lambda$switchToSuccessStatus$7$ProjectorConnectFragment() {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorBtConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchTimeout() {
        this.mViewModel.stopScanProjector();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pairAndConnectTimeout() {
        switchToOvertimeStatus();
        this.mViewModel.stopPairOrConnectProjector();
    }

    private void startScanTimeout() {
        if (this.mScanTimeoutHandler == null) {
            this.mScanTimeoutHandler = new Handler();
        }
        this.mScanTimeoutHandler.removeCallbacks(this.mScanTimeoutRunnable);
        this.mScanTimeoutHandler.postDelayed(this.mScanTimeoutRunnable, 60000L);
        startScanHalfTimeout();
    }

    private void cancelScanTimeout() {
        Handler handler = this.mScanTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mScanTimeoutRunnable);
        }
        this.mViewModel.stopScanProjector();
        cancelScanHalfTimeout();
    }

    private void startScanHalfTimeout() {
        if (this.mScanHalfTimeoutHandler == null) {
            this.mScanHalfTimeoutHandler = new Handler();
        }
        this.mScanHalfTimeoutHandler.removeCallbacks(this.mScanHalfTimeoutRunnable);
        this.mScanHalfTimeoutHandler.postDelayed(this.mScanHalfTimeoutRunnable, 30000L);
    }

    private void cancelScanHalfTimeout() {
        Handler handler = this.mScanHalfTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mScanHalfTimeoutRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchHalfTimeout() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_search_wait_tts));
    }

    private void startPairAndConnectTimeout() {
        if (this.mPairAndConnectTimeoutHandler == null) {
            this.mPairAndConnectTimeoutHandler = new Handler();
        }
        this.mPairAndConnectTimeoutHandler.removeCallbacks(this.mPairAndConnectTimeoutRunnable);
        this.mPairAndConnectTimeoutHandler.postDelayed(this.mPairAndConnectTimeoutRunnable, 80000L);
    }

    private void cancelPairAndConnectTimeout() {
        Handler handler = this.mPairAndConnectTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mPairAndConnectTimeoutRunnable);
        }
        this.mViewModel.stopPairOrConnectProjector();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.mSpeechPlayViewModel.stopPlay();
            this.mHandler.removeCallbacksAndMessages(null);
            leave();
            return;
        }
        enter();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        leave();
        this.mSpeechPlayViewModel.releaseTextSpeech();
    }

    private void leave() {
        cancelPairAndConnectTimeout();
        cancelScanTimeout();
        this.mViewModel.setProjectorBluetoothDoing(false);
        this.mViewModel.unregisterProjectorStateCallback();
    }
}
