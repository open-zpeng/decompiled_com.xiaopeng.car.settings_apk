package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ProjectorBluetoothViewModel;
import com.xiaopeng.car.settingslibrary.vm.speech.SpeechPlayViewModel;
/* loaded from: classes.dex */
public class ProjectorSetupFragment extends BaseFragment {
    private static final int NEXT_TIMEOUT_BLE = 60000;
    private ICapsuleCinemaGuideInterface mCinemaGuideListener;
    private SpeechPlayViewModel mSpeechPlayViewModel;
    private ProjectorBluetoothViewModel mViewModel;
    private Handler mHandler = new Handler();
    Handler mToNextTimeoutHandler = new Handler();
    Runnable mToNextRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$D-OttYH61zSUHs90p3iI6RN_iA8
        @Override // java.lang.Runnable
        public final void run() {
            ProjectorSetupFragment.this.toNext();
        }
    };
    private int count = 0;
    private final String TAG = getClass().getSimpleName();

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_cinema_projector_setup;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        view.findViewById(R.id.capsule_cinema_projector_pre_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$A98d3_VrNMwI49i9vttiV59hsaA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectorSetupFragment.this.lambda$initView$0$ProjectorSetupFragment(view2);
            }
        });
        view.findViewById(R.id.capsule_cinema_projector_next_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$3wuOqt_NagMMOK4rw6CtrcsZvxw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectorSetupFragment.this.lambda$initView$1$ProjectorSetupFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$ProjectorSetupFragment(View view) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onScreenSetup();
        }
    }

    public /* synthetic */ void lambda$initView$1$ProjectorSetupFragment(View view) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorBt(true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mViewModel = (ProjectorBluetoothViewModel) ViewModelUtils.getViewModel(this, ProjectorBluetoothViewModel.class);
        this.mSpeechPlayViewModel = (SpeechPlayViewModel) ViewModelUtils.getViewModel(this, SpeechPlayViewModel.class);
        this.mSpeechPlayViewModel.getTtsPlayDoneLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$2o_hsLB1A5rjAVB1roTsWGrVgqs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ProjectorSetupFragment.this.lambda$init$2$ProjectorSetupFragment((String) obj);
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$8DcNhB5kucXsPBy5yBvtoIQK3uc
            @Override // java.lang.Runnable
            public final void run() {
                ProjectorSetupFragment.this.lambda$init$3$ProjectorSetupFragment();
            }
        }, 1000L);
        this.mViewModel.reqBtDevicePairedDevices();
        this.mViewModel = (ProjectorBluetoothViewModel) ViewModelUtils.getViewModel(this, ProjectorBluetoothViewModel.class);
        this.mViewModel.getProjectStatus().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$mSiMyeC7Pc8iX4TQcAOyC3I2x-I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ProjectorSetupFragment.this.lambda$init$4$ProjectorSetupFragment((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$3$ProjectorSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_tips_1_tts));
    }

    public /* synthetic */ void lambda$init$4$ProjectorSetupFragment(Integer num) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface;
        Logs.d("ProjectorSetupFragment projector status:" + num + ", hidden: " + isHidden());
        if (isHidden()) {
            return;
        }
        if (num.intValue() == 4) {
            this.mViewModel.stopScanProjector();
            cancelNextTimeout();
            ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface2 = this.mCinemaGuideListener;
            if (iCapsuleCinemaGuideInterface2 != null) {
                iCapsuleCinemaGuideInterface2.onProjectorBt(false);
            }
        } else if (num.intValue() != 8 || (iCapsuleCinemaGuideInterface = this.mCinemaGuideListener) == null) {
        } else {
            iCapsuleCinemaGuideInterface.onProjectorBtConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processProjectorSetupTts */
    public void lambda$init$2$ProjectorSetupFragment(String str) {
        if (getStringById(R.string.cinema_projector_tips_1_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$1prkVg4mke3Bq1xhrP9IGo6YWiI
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectorSetupFragment.this.lambda$processProjectorSetupTts$5$ProjectorSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_projector_tips_2_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$qAV-N2n_F36i572ILMjNf7o7LRo
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectorSetupFragment.this.lambda$processProjectorSetupTts$6$ProjectorSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_projector_tips_3_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$1vHOn0_GM6mVMrp6rtLfbGsowk0
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectorSetupFragment.this.lambda$processProjectorSetupTts$7$ProjectorSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_projector_tips_4_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ProjectorSetupFragment$UynTogqcAehRWlE_Iaz0gezbIOA
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectorSetupFragment.this.lambda$processProjectorSetupTts$8$ProjectorSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_projector_tips_5_tts).equals(str)) {
            Logs.d("tts play done!");
            if (!this.mViewModel.isProjectorAlreadyConnected()) {
                this.mViewModel.setProjectorBluetoothDoing(true);
                this.mViewModel.registerProjectorStateCallback();
                this.mViewModel.startScanProjector();
                this.mToNextTimeoutHandler.removeCallbacks(this.mToNextRunnable);
                this.mToNextTimeoutHandler.postDelayed(this.mToNextRunnable, 60000L);
                return;
            }
            ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
            if (iCapsuleCinemaGuideInterface != null) {
                iCapsuleCinemaGuideInterface.onProjectorBt(false);
            }
        }
    }

    public /* synthetic */ void lambda$processProjectorSetupTts$5$ProjectorSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_tips_2_tts));
    }

    public /* synthetic */ void lambda$processProjectorSetupTts$6$ProjectorSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_tips_3_tts));
    }

    public /* synthetic */ void lambda$processProjectorSetupTts$7$ProjectorSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_tips_4_tts));
    }

    public /* synthetic */ void lambda$processProjectorSetupTts$8$ProjectorSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_tips_5_tts));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICapsuleCinemaGuideInterface) {
            this.mCinemaGuideListener = (ICapsuleCinemaGuideInterface) context;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toNext() {
        int i = this.count + 1;
        this.count = i;
        if (i < 3) {
            this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_no_found_tts));
            this.mToNextTimeoutHandler.removeCallbacks(this.mToNextRunnable);
            this.mToNextTimeoutHandler.postDelayed(this.mToNextRunnable, 60000L);
            return;
        }
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_projector_bt_no_found_tts));
        this.mToNextTimeoutHandler.removeCallbacks(this.mToNextRunnable);
        this.mViewModel.stopScanProjector();
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorBt(true);
        }
    }

    private void cancelNextTimeout() {
        Handler handler = this.mToNextTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mToNextRunnable);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        this.mSpeechPlayViewModel.stopPlay();
        if (z) {
            this.mHandler.removeCallbacksAndMessages(null);
            leave();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        leave();
        this.mSpeechPlayViewModel.releaseTextSpeech();
    }

    private void leave() {
        cancelNextTimeout();
        this.mViewModel.setProjectorBluetoothDoing(false);
        this.mViewModel.unregisterProjectorStateCallback();
    }
}
