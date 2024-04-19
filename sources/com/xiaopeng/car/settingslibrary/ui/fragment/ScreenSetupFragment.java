package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.vm.speech.SpeechPlayViewModel;
/* loaded from: classes.dex */
public class ScreenSetupFragment extends BaseFragment {
    private ICapsuleCinemaGuideInterface mCinemaGuideListener;
    private SpeechPlayViewModel mSpeechPlayViewModel;
    private Handler mHandler = new Handler();
    private final String TAG = getClass().getSimpleName();

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_cinema_screen_setup;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        view.findViewById(R.id.capsule_cinema_screen_next_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$IUBPEKk0G_kHxANEIJVFwFlckLs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ScreenSetupFragment.this.lambda$initView$0$ScreenSetupFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$ScreenSetupFragment(View view) {
        ICapsuleCinemaGuideInterface iCapsuleCinemaGuideInterface = this.mCinemaGuideListener;
        if (iCapsuleCinemaGuideInterface != null) {
            iCapsuleCinemaGuideInterface.onProjectorSetup();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mSpeechPlayViewModel = (SpeechPlayViewModel) ViewModelUtils.getViewModel(this, SpeechPlayViewModel.class);
        this.mSpeechPlayViewModel.getTtsPlayDoneLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$-QULa9lijxg609Vb01kxkPV4ES4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ScreenSetupFragment.this.lambda$init$1$ScreenSetupFragment((String) obj);
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$dclY_eHPBfXQbq9EH5EwkcrIGBM
            @Override // java.lang.Runnable
            public final void run() {
                ScreenSetupFragment.this.lambda$init$2$ScreenSetupFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$init$2$ScreenSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_screen_tips_1_tts));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processScreenSetupTts */
    public void lambda$init$1$ScreenSetupFragment(String str) {
        if (getStringById(R.string.cinema_screen_tips_1_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$7ULRLY1So_3hl0TpsXksaQj4BZ8
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenSetupFragment.this.lambda$processScreenSetupTts$3$ScreenSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_screen_tips_2_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$VCjfAcGUUMr5eGLhvGuswN_rnkQ
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenSetupFragment.this.lambda$processScreenSetupTts$4$ScreenSetupFragment();
                }
            }, 1000L);
        } else if (getStringById(R.string.cinema_screen_tips_3_tts).equals(str)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$ScreenSetupFragment$XlDTXMTRTzU_r4pcQH_rz2wk918
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenSetupFragment.this.lambda$processScreenSetupTts$5$ScreenSetupFragment();
                }
            }, 1000L);
        }
    }

    public /* synthetic */ void lambda$processScreenSetupTts$3$ScreenSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_screen_tips_2_tts));
    }

    public /* synthetic */ void lambda$processScreenSetupTts$4$ScreenSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_screen_tips_3_tts));
    }

    public /* synthetic */ void lambda$processScreenSetupTts$5$ScreenSetupFragment() {
        this.mSpeechPlayViewModel.playText(getStringById(R.string.cinema_screen_tips_4_tts));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICapsuleCinemaGuideInterface) {
            this.mCinemaGuideListener = (ICapsuleCinemaGuideInterface) context;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.mSpeechPlayViewModel.stopPlay();
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mSpeechPlayViewModel.releaseTextSpeech();
    }
}
