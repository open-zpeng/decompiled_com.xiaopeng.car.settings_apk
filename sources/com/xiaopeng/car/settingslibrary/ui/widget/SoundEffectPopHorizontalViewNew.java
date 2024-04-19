package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.airbnb.lottie.LottieAnimationView;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView;
import com.xiaopeng.car.settingslibrary.ui.widget.anim.SoundEffectAnimView;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSegmented;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.slider.XSlider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class SoundEffectPopHorizontalViewNew extends XFrameLayout implements View.OnClickListener, ISoundServerListener, XSlider.SliderProgressListener, CompoundButton.OnCheckedChangeListener {
    public static final int IN_OUT_TIMEOUT = 400;
    private AsyncLayoutInflater mAsyncLayoutInflater;
    private View mBottomBg;
    private View mCloseButton;
    private View mCloseButtonGroup;
    private View.OnClickListener mCloseClickListener;
    private View mCloseImg;
    private Context mContext;
    private int mCurrentEffect;
    private int mCurrentScenes;
    ViewGroup mDialogRoot;
    private View mDynaModeGroup;
    private View mEffectContentView;
    private ArrayList<XButton> mEffectDynaModeBtns;
    private ArrayList<XButton> mEffectHifiModeBtns;
    private ImageView mEffectImg1;
    private ImageView mEffectImg2;
    private View mEffectLeftGroup;
    private ArrayList<XButton> mEffectModeBtns;
    private ArrayList<XButton> mEffectRecentModeBtns;
    private ArrayList<XButton> mEffectSceneBtns;
    private XTextView mEffectSwitchBtn;
    private XTextView mEffectTips;
    private ImageView mEffectTipsIcon;
    private View mEffectTipsLayout;
    private View mEffectTipsLine;
    private XButton mEqualizerBtn;
    private View mEqualizerContentView;
    private XButton mEqualizerCustom1Btn;
    private XButton mEqualizerCustom2Btn;
    private View mEqualizerGroup;
    private XButton mEqualizerRestoreDefaultBtn;
    private ArrayList<XSlider> mEqualizerSliders;
    private XSwitch mEqualizerStartSwitch;
    private TextView mEqualizerStartSwitchTips;
    private ArrayList<XTextView> mEqualizerTexts;
    private View mEqualizerView;
    private ViewStub mFieldStub;
    private View mHifiModeGroup;
    private SoundFieldValues mHifiSoundFieldValues;
    private int[] mHifiSpreadColor;
    private int mHifiType;
    private IntervalControl mIntervalControl;
    private boolean mIsInit;
    private boolean mIsRestoreData;
    private LayoutInflater mLayoutInflater;
    private View mLeftBg;
    private ObjectAnimator mLeftGroupAnimator;
    private LottieAnimationView mLottieSpaceEffect;
    private SoundFieldSpreadView mMainDriverSpreadView;
    private View mModeGroup;
    private SoundFieldView.PositionChangeListener mOnPositionChangeListener;
    private ViewStub mPickStub;
    private View mRecentModeGroup;
    private View mRightBg;
    private SoundFieldValues mRoundSoundFieldValues;
    private int mRoundType;
    private View mSceneGroup;
    private String mSceneId;
    private ViewStub mSceneStub;
    private SoundEffectAnimView mSoundEffectAnimView;
    private ViewGroup mSoundFieldArea;
    private XTextView mSoundFieldTips;
    private SoundFieldView mSoundFieldView;
    private SoundFieldView mSoundFieldViewHifiOriginal;
    private ImageView mSoundFiledRoof;
    private SoundManager mSoundManager;
    private View mSpaceGroup;
    private XSegmented mSpaceTabs;
    private ViewStub mSpreadStub;
    private ObjectAnimator mSwitchBottomAnimator;
    private ObjectAnimator mSwitchTopAnimator1;
    private ObjectAnimator mSwitchTopAnimator2;
    private XSegmented.SegmentListener mTabBarClickListener;
    private ThemeViewModel mUIThemeViewModel;
    private ViewGroup mView;
    private int[] mXpSpreadColor;
    private TextView mXpTitle;

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStopTrackingTouch(XSlider xSlider) {
    }

    public SoundEffectPopHorizontalViewNew(Context context) {
        this(context, null);
    }

    public SoundEffectPopHorizontalViewNew(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEffectModeBtns = new ArrayList<>();
        this.mEffectRecentModeBtns = new ArrayList<>();
        this.mEffectHifiModeBtns = new ArrayList<>();
        this.mEffectDynaModeBtns = new ArrayList<>();
        this.mEffectSceneBtns = new ArrayList<>();
        this.mEqualizerSliders = new ArrayList<>();
        this.mEqualizerTexts = new ArrayList<>();
        this.mCurrentScenes = -1;
        this.mCurrentEffect = -1;
        this.mHifiType = -1;
        this.mRoundType = -1;
        this.mHifiSpreadColor = new int[]{R.color.sound_field_spread_shader_start, R.color.sound_field_spread_shader_end};
        this.mXpSpreadColor = new int[]{R.color.sound_field_spread_shader_xp_start, R.color.sound_field_spread_shader_xp_end};
        this.mIsRestoreData = false;
        this.mOnPositionChangeListener = new SoundFieldView.PositionChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.8
            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPressChange(boolean z) {
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPositionChange(SoundFieldView soundFieldView, int i, int i2) {
                Logs.v("xpsoundeffect onPositionChange x:" + i + " y:" + i2 + " " + soundFieldView);
                if (soundFieldView == SoundEffectPopHorizontalViewNew.this.mSoundFieldView) {
                    SoundEffectPopHorizontalViewNew.this.mSoundManager.setSoundField(1, i, i2, true);
                } else if (soundFieldView == SoundEffectPopHorizontalViewNew.this.mSoundFieldViewHifiOriginal) {
                    if (CarFunction.isSupportDyna()) {
                        SoundEffectPopHorizontalViewNew.this.mSoundManager.setSoundField(3, i, i2, true);
                    } else {
                        SoundEffectPopHorizontalViewNew.this.mSoundManager.setSoundField(4, i, i2, true);
                    }
                }
            }
        };
        this.mSceneId = null;
        this.mTabBarClickListener = new XSegmented.SegmentListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.9
            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public boolean onInterceptChange(XSegmented xSegmented, int i) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public void onSelectionChange(XSegmented xSegmented, int i, boolean z) {
                VuiManager.instance().updateVuiScene(SoundEffectPopHorizontalViewNew.this.mSceneId, SoundEffectPopHorizontalViewNew.this.getContext(), xSegmented);
                if (z || VuiManager.instance().isVuiAction(xSegmented)) {
                    SoundEffectPopHorizontalViewNew.this.mSoundManager.setDyn3dEffectLevel(i);
                }
                if (SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect != null && SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.isAnimating()) {
                    SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.cancelAnimation();
                }
                String str = i != 1 ? i != 2 ? i != 3 ? "" : "lottie/Sound_high.json" : "lottie/Sound_medium.json" : "lottie/Sound_low.json";
                if (!TextUtils.isEmpty(str)) {
                    SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.setVisibility(0);
                    SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.setAnimation(str);
                    SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.setRepeatCount(-1);
                    SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.playAnimation();
                    return;
                }
                SoundEffectPopHorizontalViewNew.this.mLottieSpaceEffect.setVisibility(8);
            }
        };
        this.mView = this;
        this.mContext = context;
        this.mUIThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mUIThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("xpsoundeffect dialog onThemeChanged callback ");
                SoundEffectPopHorizontalViewNew.this.mDialogRoot.setBackground(SoundEffectPopHorizontalViewNew.this.getContext().getDrawable(R.drawable.sound_effect_dialog_bg));
                SoundEffectPopHorizontalViewNew.this.mLeftBg.setBackground(SoundEffectPopHorizontalViewNew.this.getContext().getDrawable(R.drawable.pic_mask_left));
                SoundEffectPopHorizontalViewNew.this.mRightBg.setBackground(SoundEffectPopHorizontalViewNew.this.getContext().getDrawable(R.drawable.pic_mask_right));
                SoundEffectPopHorizontalViewNew.this.mBottomBg.setBackground(SoundEffectPopHorizontalViewNew.this.getContext().getDrawable(R.drawable.pic_mask_under));
                SoundEffectPopHorizontalViewNew.this.updateRightUI();
                SoundEffectPopHorizontalViewNew.this.updateBottomUI();
                SoundEffectPopHorizontalViewNew.this.updateCenterUI();
            }
        });
        this.mLayoutInflater = LayoutInflater.from(getContext());
        this.mLayoutInflater.inflate(R.layout.sound_effects_new, this.mView);
        this.mDialogRoot = (ViewGroup) this.mView.findViewById(R.id.dialog_root);
        inflateView(this.mDialogRoot);
        log("inflateView");
        this.mIntervalControl = new IntervalControl("soundeffect");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
        if (!this.mIsInit) {
            this.mIsInit = true;
            log("onAttachedToWindow init");
            refreshData();
            initTop();
            initBottom();
            setBackground(this.mSoundFieldArea, this.mSoundFiledRoof);
            asyncInflateView();
        } else {
            log("onAttachedToWindow onStart");
            if (this.mSoundFieldView == null && this.mSoundFieldViewHifiOriginal == null) {
                log("no inflate complete!");
                return;
            } else if (isInEqualizer()) {
                switchToEffect();
            } else {
                refreshData();
                long currentTimeMillis = System.currentTimeMillis();
                fillUI();
                log("onStart " + (System.currentTimeMillis() - currentTimeMillis));
                initVuiScene();
            }
        }
        SoundServerManager.getInstance().addSoundUIListener(this);
    }

    private void inflateView(View view) {
        initTopView(view);
        initBottomView(view);
        this.mLeftBg = view.findViewById(R.id.left_bg);
        this.mRightBg = view.findViewById(R.id.right_bg);
        this.mSoundFieldArea = (ViewGroup) view.findViewById(R.id.sound_field_area);
        this.mLottieSpaceEffect = (LottieAnimationView) view.findViewById(R.id.lottie_space_effect);
        this.mSoundFiledRoof = (ImageView) view.findViewById(R.id.sound_field_roof);
        if (CarFunction.isSupportDts() && CarFunction.isSupportEffectScene()) {
            this.mSceneGroup = ((ViewStub) findViewById(R.id.sound_effect_scene_layout)).inflate();
        }
        if (CarConfigHelper.hasHifiSound()) {
            this.mEqualizerGroup = ((ViewStub) findViewById(R.id.sound_effect_equalizer_layout)).inflate();
            if (CarFunction.isSupportRecentEffectMode()) {
                this.mSpaceGroup = ((ViewStub) findViewById(R.id.sound_effect_space_layout)).inflate();
                this.mSpaceTabs = (XSegmented) this.mSpaceGroup.findViewById(R.id.sound_effect_space_segment);
                this.mSpaceTabs.setSegmentListener(this.mTabBarClickListener);
            }
        }
        if (CarFunction.isSupportDyna()) {
            this.mHifiModeGroup = ((ViewStub) findViewById(R.id.sound_effect_mode_layout_hifi)).inflate();
            this.mDynaModeGroup = ((ViewStub) findViewById(R.id.sound_effect_mode_layout_dyna)).inflate();
        } else if (CarFunction.isSupportRecentEffectMode()) {
            if (CarConfigHelper.hasHifiSound()) {
                this.mRecentModeGroup = ((ViewStub) findViewById(R.id.sound_effect_recent_mode_layout_high)).inflate();
            } else {
                this.mRecentModeGroup = ((ViewStub) findViewById(R.id.sound_effect_recent_mode_layout)).inflate();
            }
        } else {
            this.mModeGroup = ((ViewStub) findViewById(R.id.sound_effect_mode_layout)).inflate();
        }
        this.mEffectContentView = view.findViewById(R.id.soundeffect_content);
        this.mEqualizerContentView = view.findViewById(R.id.soundeffect_equalizer_content);
        this.mEffectLeftGroup = view.findViewById(R.id.sound_effect_left_layout);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEffectLeftGroup.getLayoutParams();
        if (!CarConfigHelper.hasHifiSound() && !CarFunction.isSoundEffectLowSpeaker() && !CarFunction.isEU()) {
            marginLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.sound_effect_left_top_margin_mid);
        } else {
            marginLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.sound_effect_left_top_margin);
        }
        this.mEffectLeftGroup.setLayoutParams(marginLayoutParams);
    }

    private void asyncInflateView() {
        if (this.mAsyncLayoutInflater == null) {
            this.mAsyncLayoutInflater = new AsyncLayoutInflater(getContext());
        }
        initLeftView();
        initLeft();
        if (CarFunction.isSupportEffectScene()) {
            initRight();
        }
        this.mAsyncLayoutInflater.inflate(R.layout.sound_effects_center, this.mSoundFieldArea, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.2
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                SoundEffectPopHorizontalViewNew.this.initCenterView(view);
                SoundEffectPopHorizontalViewNew.this.initCenter();
                if (viewGroup != null) {
                    viewGroup.addView(view, 1);
                }
                SoundEffectPopHorizontalViewNew.this.initVuiScene();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logs.d("onDetachedFromWindow soundeffect");
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
        LottieAnimationView lottieAnimationView = this.mLottieSpaceEffect;
        if (lottieAnimationView != null && lottieAnimationView.isAnimating()) {
            this.mLottieSpaceEffect.cancelAnimation();
        }
        cancelSwitchAnimator();
        SoundServerManager.getInstance().removeSoundUIListener(this);
    }

    private void cancelSwitchAnimator() {
        ObjectAnimator objectAnimator = this.mSwitchTopAnimator1;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mSwitchTopAnimator2;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
        ObjectAnimator objectAnimator3 = this.mLeftGroupAnimator;
        if (objectAnimator3 != null) {
            objectAnimator3.cancel();
        }
        ObjectAnimator objectAnimator4 = this.mSwitchBottomAnimator;
        if (objectAnimator4 != null) {
            objectAnimator4.cancel();
        }
    }

    private void startSwitchAnimator() {
        XTextView xTextView = this.mEffectSwitchBtn;
        if (xTextView != null) {
            if (this.mSwitchTopAnimator1 == null) {
                this.mSwitchTopAnimator1 = ObjectAnimator.ofFloat(xTextView, "alpha", 1.0f, 0.0f);
                this.mSwitchTopAnimator1.setDuration(400L);
                this.mSwitchTopAnimator1.setRepeatMode(2);
                this.mSwitchTopAnimator1.setRepeatCount(1);
                this.mSwitchTopAnimator1.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                        super.onAnimationRepeat(animator);
                        Logs.d("switch onAnimationRepeat");
                        SoundEffectPopHorizontalViewNew.this.updateEffectContent();
                        SoundEffectPopHorizontalViewNew.this.initVuiScene();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Logs.d("switch onAnimationEnd");
                        super.onAnimationEnd(animator);
                        SoundEffectPopHorizontalViewNew.this.mEffectSwitchBtn.setAlpha(1.0f);
                    }
                });
            }
            this.mSwitchTopAnimator1.start();
        }
        if (this.mSwitchTopAnimator2 == null) {
            this.mSwitchTopAnimator2 = ObjectAnimator.ofFloat(this.mXpTitle, "alpha", 1.0f, 0.0f);
            this.mSwitchTopAnimator2.setDuration(400L);
            this.mSwitchTopAnimator2.setRepeatMode(2);
            this.mSwitchTopAnimator2.setRepeatCount(1);
            this.mSwitchTopAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalViewNew.this.mXpTitle.setAlpha(1.0f);
                }
            });
        }
        this.mSwitchTopAnimator2.start();
        if (this.mLeftGroupAnimator == null) {
            this.mLeftGroupAnimator = ObjectAnimator.ofFloat(this.mEffectLeftGroup, "alpha", 1.0f, 0.0f);
            this.mLeftGroupAnimator.setDuration(400L);
            this.mLeftGroupAnimator.setRepeatMode(2);
            this.mLeftGroupAnimator.setRepeatCount(1);
            this.mLeftGroupAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalViewNew.this.mEffectLeftGroup.setAlpha(1.0f);
                }
            });
        }
        this.mLeftGroupAnimator.start();
        if (this.mSwitchBottomAnimator == null) {
            this.mSwitchBottomAnimator = ObjectAnimator.ofFloat(this.mEffectTipsLayout, "alpha", 1.0f, 0.0f);
            this.mSwitchBottomAnimator.setDuration(400L);
            this.mSwitchBottomAnimator.setRepeatMode(2);
            this.mSwitchBottomAnimator.setRepeatCount(1);
            this.mSwitchBottomAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalViewNew.this.mEffectTipsLayout.setAlpha(1.0f);
                }
            });
        }
        this.mSwitchBottomAnimator.start();
    }

    public void setTitleVisibility(int i) {
        this.mCloseButtonGroup.setVisibility(i);
        this.mXpTitle.setVisibility(i);
    }

    private void initTopView(View view) {
        this.mCloseButtonGroup = view.findViewById(R.id.dialog_close_group);
        this.mCloseButton = view.findViewById(R.id.dialog_close);
        this.mCloseImg = view.findViewById(R.id.dialog_close_img);
        if (CarFunction.isSupportRecentEffectMode()) {
            this.mCloseImg.setVisibility(8);
        }
        View.OnClickListener onClickListener = this.mCloseClickListener;
        if (onClickListener != null) {
            this.mCloseButton.setOnClickListener(onClickListener);
        }
        this.mSoundFieldTips = (XTextView) view.findViewById(R.id.sound_field_tips);
        if (CarFunction.isSupportDyna()) {
            this.mEffectSwitchBtn = (XTextView) view.findViewById(R.id.sound_effect_switch_btn);
            this.mEffectSwitchBtn.setOnClickListener(this);
            this.mEffectSwitchBtn.setVisibility(0);
            this.mXpTitle = (TextView) view.findViewById(R.id.xp_title);
            this.mXpTitle.setText(getContext().getString(R.string.sound_high_fidelity));
            this.mXpTitle.setVisibility(0);
            return;
        }
        this.mXpTitle = (TextView) view.findViewById(R.id.xp_title);
        this.mXpTitle.setVisibility(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void initLeftView() {
        if (CarFunction.isSupportDyna()) {
            View view = this.mHifiModeGroup;
            if (view != null) {
                view.setVisibility(0);
            }
            View view2 = this.mModeGroup;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            View view3 = this.mRecentModeGroup;
            if (view3 != null) {
                view3.setVisibility(8);
            }
        } else {
            View view4 = this.mHifiModeGroup;
            if (view4 != null) {
                view4.setVisibility(8);
            }
            if (CarFunction.isSupportRecentEffectMode()) {
                View view5 = this.mRecentModeGroup;
                if (view5 != null) {
                    view5.setVisibility(0);
                }
            } else {
                View view6 = this.mModeGroup;
                if (view6 != null) {
                    view6.setVisibility(0);
                }
            }
        }
        View view7 = this.mModeGroup;
        if (view7 != null) {
            this.mEffectModeBtns.add(view7.findViewById(R.id.sound_effect_mode_common));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_rock));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_original));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_disco));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_people));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_classic));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_jazz));
            this.mEffectModeBtns.add(this.mModeGroup.findViewById(R.id.sound_effect_mode_custom));
        }
        View view8 = this.mRecentModeGroup;
        if (view8 != null) {
            this.mEffectRecentModeBtns.add(view8.findViewById(R.id.sound_effect_recent_mode_dynamic));
            this.mEffectRecentModeBtns.add(this.mRecentModeGroup.findViewById(R.id.sound_effect_recent_mode_original));
            this.mEffectRecentModeBtns.add(this.mRecentModeGroup.findViewById(R.id.sound_effect_recent_mode_cinema));
            if (!CarConfigHelper.hasHifiSound()) {
                this.mEffectRecentModeBtns.add(this.mRecentModeGroup.findViewById(R.id.sound_effect_recent_mode_custom));
            }
        }
        View view9 = this.mHifiModeGroup;
        if (view9 != null) {
            this.mEffectHifiModeBtns.add(view9.findViewById(R.id.sound_effect_mode_common_hifi));
            this.mEffectHifiModeBtns.add(this.mHifiModeGroup.findViewById(R.id.sound_effect_mode_hifi));
        }
        View view10 = this.mDynaModeGroup;
        if (view10 != null) {
            this.mEffectDynaModeBtns.add(view10.findViewById(R.id.sound_effect_mode_real));
            this.mEffectDynaModeBtns.add(this.mDynaModeGroup.findViewById(R.id.sound_effect_mode_soft));
            this.mEffectDynaModeBtns.add(this.mDynaModeGroup.findViewById(R.id.sound_effect_mode_human));
            this.mEffectDynaModeBtns.add(this.mDynaModeGroup.findViewById(R.id.sound_effect_mode_dynamic));
        }
        View view11 = this.mSceneGroup;
        if (view11 != null) {
            this.mEffectSceneBtns.add(view11.findViewById(R.id.sound_effect_scene_cinema));
            this.mEffectSceneBtns.add(this.mSceneGroup.findViewById(R.id.sound_effect_scene_ktv));
            this.mEffectSceneBtns.add(this.mSceneGroup.findViewById(R.id.sound_effect_scene_recording));
            this.mEffectSceneBtns.add(this.mSceneGroup.findViewById(R.id.sound_effect_scene_live));
        }
        View view12 = this.mEqualizerGroup;
        if (view12 != null) {
            this.mEqualizerBtn = (XButton) view12.findViewById(R.id.sound_effect_equalizer);
            this.mEqualizerBtn.setSelected(true);
            this.mEqualizerBtn.setOnClickListener(this);
        }
        Iterator<XButton> it = this.mEffectModeBtns.iterator();
        while (it.hasNext()) {
            it.next().setOnClickListener(this);
        }
        Iterator<XButton> it2 = this.mEffectRecentModeBtns.iterator();
        while (it2.hasNext()) {
            it2.next().setOnClickListener(this);
        }
        Iterator<XButton> it3 = this.mEffectHifiModeBtns.iterator();
        while (it3.hasNext()) {
            it3.next().setOnClickListener(this);
        }
        Iterator<XButton> it4 = this.mEffectDynaModeBtns.iterator();
        while (it4.hasNext()) {
            it4.next().setOnClickListener(this);
        }
        Iterator<XButton> it5 = this.mEffectSceneBtns.iterator();
        while (it5.hasNext()) {
            it5.next().setOnClickListener(this);
        }
    }

    private void initBottomView(View view) {
        this.mBottomBg = view.findViewById(R.id.bottom_bg);
        this.mEffectTipsLayout = view.findViewById(R.id.effect_tips_layout);
        this.mEffectImg1 = (ImageView) view.findViewById(R.id.effect_img1);
        this.mEffectImg2 = (ImageView) view.findViewById(R.id.effect_img2);
        this.mEffectTipsLine = view.findViewById(R.id.effect_tips_line);
        this.mEffectTips = (XTextView) view.findViewById(R.id.effect_tips);
        this.mEffectTipsIcon = (ImageView) view.findViewById(R.id.effect_tips_icon);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCenterView(View view) {
        if (!CarFunction.isSoundEffectLowSpeaker() && !CarFunction.isSupportRecentEffectMode()) {
            this.mSceneStub = (ViewStub) view.findViewById(R.id.scene_stub);
            this.mSoundEffectAnimView = (SoundEffectAnimView) this.mSceneStub.inflate().findViewById(R.id.sound_field_scenes_anim);
        }
        this.mSoundFieldView = (SoundFieldView) view.findViewById(R.id.sound_field_view);
        this.mSoundFieldView.initPointCount(CarFunction.isMainPsnSharedSoundField() ? 3 : 5);
        if (CarFunction.isMainPsnSharedSoundField()) {
            this.mSoundFieldView.setRecommendationPoint(new Point(430, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(430, 410));
        } else {
            this.mSoundFieldView.setRecommendationPoint(new Point(264, NfDef.GATT_STATUS_MORE), new Point(597, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(276, 410), new Point(569, 410));
        }
        this.mSoundFieldView.setLimitArea(new Point(290, 62), new Point(50, 514), new Point(TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 62), new Point(850, 514));
        this.mSoundFieldView.setOnPositionChangeListener(this.mOnPositionChangeListener);
        this.mSoundFieldView.setVisibility(0);
        if (CarConfigHelper.hasHifiSound()) {
            this.mSpreadStub = (ViewStub) view.findViewById(R.id.spread_stub);
            this.mMainDriverSpreadView = (SoundFieldSpreadView) this.mSpreadStub.inflate().findViewById(R.id.main_driver_spread_view);
        }
        if (CarFunction.isSupportDyna()) {
            this.mFieldStub = (ViewStub) view.findViewById(R.id.filed_stub);
            this.mSoundFieldViewHifiOriginal = (SoundFieldView) this.mFieldStub.inflate().findViewById(R.id.sound_field_view_hifi_original);
            this.mSoundFieldViewHifiOriginal.setOnPositionChangeListener(this.mOnPositionChangeListener);
            this.mSoundFieldViewHifiOriginal.setLimitArea(new Point(290, 62), new Point(50, 514), new Point(TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 62), new Point(850, 514));
            this.mSoundFieldViewHifiOriginal.setRecommendationPoint(new Point(264, NfDef.GATT_STATUS_MORE), new Point(597, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(276, 410), new Point(569, 410));
            this.mSoundFieldViewHifiOriginal.setIsAdsorption(true);
            this.mSoundFieldViewHifiOriginal.setVisibility(0);
        }
        if (CarFunction.isSoundEffectLowSpeaker()) {
            Logs.d("xpsoundeffect isLowSpeaker");
            SoundFieldView soundFieldView = this.mSoundFieldView;
            if (soundFieldView != null) {
                soundFieldView.setDraggingHalf(true);
            }
        }
    }

    private void refreshData() {
        if (this.mSoundManager == null) {
            this.mSoundManager = SoundManager.getInstance();
        }
        this.mCurrentEffect = this.mSoundManager.getSoundEffectMode();
        if (CarFunction.isSupportDyna()) {
            this.mHifiType = this.mSoundManager.getSoundEffectType(3);
            this.mHifiSoundFieldValues = this.mSoundManager.getSoundField(3);
        }
        if (CarFunction.isSupportEffectScene()) {
            this.mCurrentScenes = this.mSoundManager.getSoundEffectScene(1);
        }
        this.mRoundType = this.mSoundManager.getSoundEffectType(1);
        this.mRoundSoundFieldValues = this.mSoundManager.getSoundField(1);
        Logs.d("xpsoundeffect data mCurrentEffect:" + this.mCurrentEffect + " mRoundType:" + this.mRoundType + " mCurrentScenes:" + this.mCurrentScenes + " mRoundSoundFieldValues:" + this.mRoundSoundFieldValues + " mHifiType:" + this.mHifiType + " mHifiSoundFieldValues:" + this.mHifiSoundFieldValues);
        protectDefault();
    }

    private void initLeft() {
        int i;
        int i2;
        if (CarFunction.isSupportDyna()) {
            Iterator<XButton> it = this.mEffectHifiModeBtns.iterator();
            while (it.hasNext()) {
                it.next().setSelected(false);
            }
            if (this.mRoundType == 2) {
                setHifiModeBtnSelected(R.id.sound_effect_mode_hifi, true);
            } else {
                setHifiModeBtnSelected(R.id.sound_effect_mode_common_hifi, true);
            }
        } else if (CarFunction.isSupportRecentEffectMode()) {
            int i3 = this.mRoundType;
            if (i3 == 0) {
                i2 = R.id.sound_effect_recent_mode_original;
            } else if (i3 == 1) {
                i2 = R.id.sound_effect_recent_mode_dynamic;
            } else if (i3 == 2) {
                i2 = R.id.sound_effect_recent_mode_cinema;
            } else if (i3 != 3) {
                i2 = -1;
            } else {
                i2 = R.id.sound_effect_recent_mode_custom;
                if (CarConfigHelper.hasHifiSound()) {
                    this.mRoundType = 1;
                    i2 = R.id.sound_effect_recent_mode_dynamic;
                    this.mSoundManager.setSoundEffectType(1, 1, false);
                }
            }
            setModeBtnSelected(i2, true);
            refreshModeBtns(this.mEffectRecentModeBtns, i2, R.id.sound_effect_recent_mode_custom);
        } else {
            switch (this.mRoundType) {
                case 0:
                    i = R.id.sound_effect_mode_original;
                    break;
                case 1:
                    i = R.id.sound_effect_mode_common;
                    break;
                case 2:
                    i = R.id.sound_effect_mode_people;
                    break;
                case 3:
                    i = R.id.sound_effect_mode_jazz;
                    break;
                case 4:
                    i = R.id.sound_effect_mode_rock;
                    break;
                case 5:
                    i = R.id.sound_effect_mode_disco;
                    break;
                case 6:
                    i = R.id.sound_effect_mode_classic;
                    break;
                case 7:
                    i = R.id.sound_effect_mode_custom;
                    refreshCustomBtnAndVui(true);
                    break;
                default:
                    i = -1;
                    break;
            }
            setModeBtnSelected(i, true);
            refreshModeBtns(this.mEffectModeBtns, i, R.id.sound_effect_mode_custom);
        }
        log("音乐风格 index-1");
        if (CarFunction.isSupportDyna()) {
            Iterator<XButton> it2 = this.mEffectDynaModeBtns.iterator();
            while (it2.hasNext()) {
                it2.next().setSelected(false);
            }
            if (this.mHifiType == SoundEffectValues.SOUND_STYLE_1_HIFI) {
                setDynaModeBtnSelected(R.id.sound_effect_mode_real, true);
            } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_2_HIFI) {
                setDynaModeBtnSelected(R.id.sound_effect_mode_soft, true);
            } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_3_HIFI) {
                setDynaModeBtnSelected(R.id.sound_effect_mode_dynamic, true);
            } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_4_HIFI) {
                setDynaModeBtnSelected(R.id.sound_effect_mode_human, true);
            }
        }
        XSegmented xSegmented = this.mSpaceTabs;
        if (xSegmented != null) {
            xSegmented.setSelection(this.mSoundManager.getDyn3dEffectLevel());
        }
        updateLeftUI();
    }

    private void refreshCustomBtnAndVui(boolean z) {
        XButton xButton;
        if (CarFunction.isSoundEffectLowSpeaker() || CarFunction.isSupportDyna()) {
            return;
        }
        if (CarFunction.isSupportRecentEffectMode() && CarConfigHelper.hasHifiSound()) {
            return;
        }
        View view = this.mModeGroup;
        if (view != null) {
            xButton = (XButton) view.findViewById(R.id.sound_effect_mode_custom);
        } else {
            View view2 = this.mRecentModeGroup;
            xButton = view2 != null ? (XButton) view2.findViewById(R.id.sound_effect_recent_mode_custom) : null;
        }
        if (xButton == null || !z) {
            return;
        }
        xButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mContext.getDrawable(R.drawable.ic_soundeffect_equalizer_icon), (Drawable) null);
        refreshCustomBtnVui(xButton, true);
    }

    private void setModeBtnSelected(int i, boolean z) {
        View view = this.mModeGroup;
        if (view != null && i != -1) {
            view.findViewById(i).setSelected(z);
        }
        View view2 = this.mRecentModeGroup;
        if (view2 == null || i == -1) {
            return;
        }
        view2.findViewById(i).setSelected(z);
    }

    private void setSceneBtnSelected(int i, boolean z) {
        View view = this.mSceneGroup;
        if (view != null) {
            view.findViewById(i).setSelected(z);
        }
    }

    private void setHifiModeBtnSelected(int i, boolean z) {
        View view = this.mHifiModeGroup;
        if (view != null) {
            view.findViewById(i).setSelected(z);
        }
    }

    private void setDynaModeBtnSelected(int i, boolean z) {
        View view = this.mDynaModeGroup;
        if (view != null) {
            view.findViewById(i).setSelected(z);
        }
    }

    private void refreshCustomBtnVui(XButton xButton, boolean z) {
        Context context;
        int i;
        if (this.mModeGroup == null && this.mRecentModeGroup == null) {
            return;
        }
        xButton.setVuiDisableHitEffect(z);
        if (z) {
            context = this.mContext;
            i = R.string.sound_effects_equalizer_title;
        } else {
            context = this.mContext;
            i = R.string.sound_custom_text;
        }
        xButton.setVuiLabel(context.getString(i));
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, xButton);
        Logs.v("xpsound effect refreshCustomBtnVui " + xButton.getVuiLabel());
    }

    private void initRight() {
        Iterator<XButton> it = this.mEffectSceneBtns.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        int i = this.mCurrentScenes;
        if (i == 1) {
            setSceneBtnSelected(R.id.sound_effect_scene_cinema, true);
        } else if (i == 2) {
            setSceneBtnSelected(R.id.sound_effect_scene_live, true);
        } else if (i == 3) {
            setSceneBtnSelected(R.id.sound_effect_scene_ktv, true);
        } else if (i == 4) {
            setSceneBtnSelected(R.id.sound_effect_scene_recording, true);
        }
        log("音乐场景 index-1");
        updateRightUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCenter() {
        if (CarFunction.isSupportDyna()) {
            if (this.mRoundType == 2) {
                this.mSoundFieldView.setType(9);
            } else {
                this.mSoundFieldView.setType(0);
            }
        } else {
            switch (this.mRoundType) {
                case 0:
                case 7:
                    this.mSoundFieldView.setType(11);
                    break;
                case 1:
                    this.mSoundFieldView.setType(0);
                    break;
                case 2:
                    this.mSoundFieldView.setType(1);
                    break;
                case 3:
                    this.mSoundFieldView.setType(2);
                    break;
                case 4:
                    this.mSoundFieldView.setType(3);
                    break;
                case 5:
                    this.mSoundFieldView.setType(5);
                    break;
                case 6:
                    this.mSoundFieldView.setType(4);
                    break;
            }
        }
        if (this.mHifiType == SoundEffectValues.SOUND_STYLE_1_HIFI) {
            this.mSoundFieldViewHifiOriginal.setType(7);
        } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_2_HIFI) {
            this.mSoundFieldViewHifiOriginal.setType(8);
        } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_3_HIFI) {
            this.mSoundFieldViewHifiOriginal.setType(6);
        } else if (this.mHifiType == SoundEffectValues.SOUND_STYLE_4_HIFI) {
            this.mSoundFieldViewHifiOriginal.setType(1);
        }
        updateCenterUI();
        long currentTimeMillis = System.currentTimeMillis();
        updateScenesForCar(this.mCurrentScenes);
        Logs.d("updateScenesForCar " + (System.currentTimeMillis() - currentTimeMillis));
        if (this.mIsRestoreData) {
            restoreSoundField();
            return;
        }
        SoundFieldValues soundFieldValues = this.mRoundSoundFieldValues;
        if (soundFieldValues != null && soundFieldValues.getType() == 1) {
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew.7
                @Override // java.lang.Runnable
                public void run() {
                    SoundEffectPopHorizontalViewNew.this.mSoundFieldView.setLocation(SoundEffectPopHorizontalViewNew.this.mRoundSoundFieldValues.getXSound(), SoundEffectPopHorizontalViewNew.this.mRoundSoundFieldValues.getYSound());
                }
            });
        }
        SoundFieldValues soundFieldValues2 = this.mHifiSoundFieldValues;
        if (soundFieldValues2 == null || soundFieldValues2.getType() != 3) {
            return;
        }
        this.mSoundFieldViewHifiOriginal.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalViewNew$DdVe4p4-LZig0HbaKNEkngRokuk
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopHorizontalViewNew.this.lambda$initCenter$0$SoundEffectPopHorizontalViewNew();
            }
        });
    }

    public /* synthetic */ void lambda$initCenter$0$SoundEffectPopHorizontalViewNew() {
        this.mSoundFieldViewHifiOriginal.setLocation(this.mHifiSoundFieldValues.getXSound(), this.mHifiSoundFieldValues.getYSound());
    }

    private void initTop() {
        int i = this.mCurrentEffect;
        if (i == 1) {
            XTextView xTextView = this.mEffectSwitchBtn;
            if (xTextView != null) {
                xTextView.setText(this.mContext.getString(R.string.sound_effects_switch_effects, getContext().getString(R.string.sound_high_fidelity)));
                updateVuiSceneForSwitch(getContext().getString(R.string.sound_high_fidelity));
            }
            TextView textView = this.mXpTitle;
            if (textView != null) {
                textView.setText(getContext().getString(R.string.sound_effects_xp_effects));
            }
        } else if (i != 3) {
        } else {
            XTextView xTextView2 = this.mEffectSwitchBtn;
            if (xTextView2 != null) {
                xTextView2.setText(this.mContext.getString(R.string.sound_effects_switch_effects, getContext().getString(R.string.sound_effects_xp_effects)));
                updateVuiSceneForSwitch(getContext().getString(R.string.sound_effects_xp_effects));
            }
            TextView textView2 = this.mXpTitle;
            if (textView2 != null) {
                textView2.setText(getContext().getString(R.string.sound_high_fidelity));
            }
        }
    }

    private void initBottom() {
        updateBottomUI();
    }

    private void fillUI() {
        initTop();
        initBottom();
        initLeft();
        initRight();
        initCenter();
    }

    private void protectDefault() {
        int i = this.mCurrentEffect;
        if (i == 1) {
            this.mIsRestoreData = false;
        } else if (i == 3) {
            if (!CarFunction.isSupportDyna()) {
                restoreDefault();
            } else {
                this.mIsRestoreData = false;
            }
        } else {
            restoreDefault();
        }
    }

    public void log(String str) {
        Logs.d("SoundEffect-effect-" + str);
    }

    private void restoreDefault() {
        this.mSoundManager.setDefaultEffect();
        this.mCurrentEffect = 1;
        this.mIsRestoreData = true;
    }

    private void restoreSoundField() {
        post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalViewNew$3Xj6uWDCzcuScBhgP8fG1rUT7po
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopHorizontalViewNew.this.lambda$restoreSoundField$1$SoundEffectPopHorizontalViewNew();
            }
        });
    }

    public /* synthetic */ void lambda$restoreSoundField$1$SoundEffectPopHorizontalViewNew() {
        SoundFieldView soundFieldView = this.mSoundFieldViewHifiOriginal;
        if (soundFieldView != null) {
            Point realDefaultPosition = soundFieldView.getRealDefaultPosition();
            this.mSoundManager.setSoundField(3, realDefaultPosition.x, realDefaultPosition.y, true);
        }
        SoundFieldView soundFieldView2 = this.mSoundFieldView;
        if (soundFieldView2 != null) {
            Point realDefaultPosition2 = soundFieldView2.getRealDefaultPosition();
            this.mSoundManager.setSoundField(1, realDefaultPosition2.x, realDefaultPosition2.y, true);
        }
    }

    private void setBackground(View view, ImageView imageView) {
        if (this.mSoundManager.isMainDriverVip()) {
            if (this.mCurrentEffect == 3) {
                view.setBackground(getContext().getDrawable(R.drawable.pic_highmodel_interior_headrest));
            } else {
                view.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_headrest));
            }
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.pic_highmodel_roof));
            return;
        }
        int i = this.mCurrentEffect;
        if (i != 1) {
            if (i != 3) {
                return;
            }
            view.setBackground(getContext().getDrawable(R.drawable.pic_highmodel_interior));
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.pic_highmodel_roof));
            return;
        }
        if (CarFunction.isSupportDyna()) {
            view.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects_hifi));
        } else {
            view.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects));
        }
        imageView.setImageDrawable(getContext().getDrawable(R.drawable.pic_model_roof));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCenterUI() {
        if (this.mSoundManager == null) {
            this.mSoundManager = SoundManager.getInstance();
        }
        boolean isMainDriverVip = this.mSoundManager.isMainDriverVip();
        Logs.d("xpsound effect main driver vip " + isMainDriverVip);
        if (isMainDriverVip) {
            this.mSoundFieldTips.setText(R.string.sound_effect_field_cannot_adjustment);
            SoundFieldSpreadView soundFieldSpreadView = this.mMainDriverSpreadView;
            if (soundFieldSpreadView != null) {
                if (this.mCurrentEffect == 3) {
                    soundFieldSpreadView.setSpreadColor(this.mHifiSpreadColor);
                } else {
                    soundFieldSpreadView.setSpreadColor(this.mXpSpreadColor);
                }
                setVisible(this.mMainDriverSpreadView, true);
            }
            setVisible(this.mSoundEffectAnimView, false);
            setVisible(this.mSoundFieldView, false);
            setVisible(this.mSoundFieldViewHifiOriginal, false);
            if (this.mCurrentEffect == 3) {
                this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_highmodel_interior_headrest));
            } else {
                this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_headrest));
            }
            this.mSoundFiledRoof.setImageDrawable(getContext().getDrawable(R.drawable.pic_highmodel_roof));
            return;
        }
        this.mSoundFieldTips.setText(R.string.sound_field_tips);
        setVisible(this.mMainDriverSpreadView, false);
        int i = this.mCurrentEffect;
        if (i != 1) {
            if (i != 3) {
                return;
            }
            setVisible(this.mSoundFieldView, false);
            setVisible(this.mSoundFieldViewHifiOriginal, true);
            setVisible(this.mSoundEffectAnimView, false);
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_highmodel_interior));
            this.mSoundFiledRoof.setImageDrawable(getContext().getDrawable(R.drawable.pic_highmodel_roof));
            return;
        }
        setVisible(this.mSoundFieldView, true);
        setVisible(this.mSoundFieldViewHifiOriginal, false);
        if (CarFunction.isSupportDyna()) {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects_hifi));
        } else if (CarFunction.isSoundEffectLowSpeaker()) {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundtrack1));
        } else {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects));
        }
        setVisible(this.mSoundEffectAnimView, true);
        this.mSoundFiledRoof.setImageDrawable(getContext().getDrawable(R.drawable.pic_model_roof));
    }

    private void updateScenesForCar(int i) {
        SoundEffectAnimView soundEffectAnimView = this.mSoundEffectAnimView;
        if (soundEffectAnimView == null) {
            return;
        }
        if (i == 1) {
            soundEffectAnimView.setType(4);
        } else if (i == 2) {
            soundEffectAnimView.setType(3);
        } else if (i == 3) {
            soundEffectAnimView.setType(1);
        } else if (i == 4) {
            soundEffectAnimView.setType(2);
        } else {
            Logs.d("xpsound scene no support! " + i);
        }
    }

    private void setVisible(View view, boolean z) {
        if (view != null) {
            view.setVisibility(z ? 0 : 4);
        }
    }

    private void updateLeftUI() {
        int i = this.mCurrentEffect;
        if (i == 1) {
            View view = this.mDynaModeGroup;
            if (view != null) {
                view.setVisibility(8);
            }
            if (CarFunction.isSupportDyna()) {
                View view2 = this.mHifiModeGroup;
                if (view2 != null) {
                    view2.setVisibility(0);
                }
                View view3 = this.mModeGroup;
                if (view3 != null) {
                    view3.setVisibility(8);
                }
                View view4 = this.mRecentModeGroup;
                if (view4 != null) {
                    view4.setVisibility(8);
                }
                View view5 = this.mSceneGroup;
                if (view5 != null) {
                    view5.setVisibility(0);
                }
            } else {
                View view6 = this.mHifiModeGroup;
                if (view6 != null) {
                    view6.setVisibility(8);
                }
                View view7 = this.mModeGroup;
                if (view7 != null) {
                    view7.setVisibility(0);
                    this.mModeGroup.findViewById(R.id.sound_effect_mode_custom).setVisibility(CarFunction.isSoundEffectLowSpeaker() ? 8 : 0);
                }
                View view8 = this.mRecentModeGroup;
                if (view8 != null) {
                    view8.setVisibility(0);
                    if (!CarConfigHelper.hasHifiSound()) {
                        this.mRecentModeGroup.findViewById(R.id.sound_effect_recent_mode_custom).setVisibility(0);
                    }
                }
                View view9 = this.mSceneGroup;
                if (view9 != null) {
                    view9.setVisibility(!CarFunction.isSupportEffectScene() ? 8 : 0);
                }
            }
        } else if (i == 3) {
            View view10 = this.mDynaModeGroup;
            if (view10 != null) {
                view10.setVisibility(0);
            }
            View view11 = this.mHifiModeGroup;
            if (view11 != null) {
                view11.setVisibility(8);
            }
            View view12 = this.mModeGroup;
            if (view12 != null) {
                view12.setVisibility(8);
            }
            View view13 = this.mRecentModeGroup;
            if (view13 != null) {
                view13.setVisibility(8);
            }
            View view14 = this.mSceneGroup;
            if (view14 != null) {
                view14.setVisibility(8);
            }
        }
        View view15 = this.mEqualizerGroup;
        if (view15 != null) {
            view15.setVisibility(CarConfigHelper.hasHifiSound() ? 0 : 8);
        }
        if (CarConfigHelper.hasHifiSound()) {
            refreshEqualizerCustom(null, null);
            if (getEqualizerSwitch() == 1) {
                this.mSoundManager.flushXpCustomizeEffects(getCurrentEqualizerValues(null));
            } else {
                this.mSoundManager.flushXpCustomizeEffects(new int[9]);
            }
        } else if ((CarFunction.isSoundEffectLowSpeaker() || this.mRoundType != 4) && !(CarFunction.isSupportRecentEffectMode() && this.mRoundType == 3)) {
        } else {
            this.mSoundManager.flushXpCustomizeEffects(getCurrentEqualizerValues(1));
        }
    }

    public void onValueChange(View view) {
        Logs.d("xpsoundeffect mValueChangeListener onValueChange picker");
        int id = view.getId();
        if (id == R.id.sound_effect_scene_recording) {
            this.mSoundManager.setSoundEffectScene(1, 4, true);
            sendButtonDataLog("B001", 6);
            updateScenesForCar(4);
        } else if (id == R.id.sound_effect_scene_live) {
            this.mSoundManager.setSoundEffectScene(1, 2, true);
            sendButtonDataLog("B001", 7);
            updateScenesForCar(2);
        } else if (id == R.id.sound_effect_scene_cinema) {
            this.mSoundManager.setSoundEffectScene(1, 1, true);
            sendButtonDataLog("B001", 9);
            updateScenesForCar(1);
        } else if (id == R.id.sound_effect_scene_ktv) {
            this.mSoundManager.setSoundEffectScene(1, 3, true);
            sendButtonDataLog("B001", 8);
            updateScenesForCar(3);
        } else if (id == R.id.sound_effect_mode_hifi) {
            this.mSoundManager.setSoundEffectType(1, 2, true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B001", "type", 1);
            this.mSoundFieldView.setType(9);
        } else if (id == R.id.sound_effect_mode_common_hifi) {
            this.mSoundManager.setSoundEffectType(1, 1, true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B001", "type", 0);
            this.mSoundFieldView.setType(0);
        } else if (id == R.id.sound_effect_mode_common) {
            this.mSoundManager.setSoundEffectType(1, 1, true);
            sendButtonDataLog("B001", 0);
            this.mSoundFieldView.setType(0);
        } else if (id == R.id.sound_effect_mode_people) {
            this.mSoundManager.setSoundEffectType(1, 2, true);
            sendButtonDataLog("B001", 1);
            this.mSoundFieldView.setType(1);
        } else if (id == R.id.sound_effect_mode_jazz) {
            this.mSoundManager.setSoundEffectType(1, 3, true);
            sendButtonDataLog("B001", 2);
            this.mSoundFieldView.setType(2);
        } else if (id == R.id.sound_effect_mode_rock) {
            this.mSoundManager.setSoundEffectType(1, 4, true);
            sendButtonDataLog("B001", 3);
            this.mSoundFieldView.setType(3);
        } else if (id == R.id.sound_effect_mode_disco) {
            this.mSoundManager.setSoundEffectType(1, 5, true);
            sendButtonDataLog("B001", 4);
            this.mSoundFieldView.setType(5);
        } else if (id == R.id.sound_effect_mode_classic) {
            this.mSoundManager.setSoundEffectType(1, 6, true);
            sendButtonDataLog("B001", 5);
            this.mSoundFieldView.setType(4);
        } else if (id == R.id.sound_effect_mode_original) {
            this.mSoundManager.setSoundEffectType(1, 0, true);
            sendButtonDataLog("B001", 10);
            this.mSoundFieldView.setType(11);
        } else if (id == R.id.sound_effect_mode_custom || id == R.id.sound_effect_recent_mode_custom) {
            if (view.isSelected()) {
                switchToEqualizer();
            } else {
                ((XButton) view).setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mContext.getDrawable(R.drawable.ic_soundeffect_equalizer_icon), (Drawable) null);
                this.mSoundManager.setSoundEffectType(1, CarFunction.isSupportRecentEffectMode() ? 3 : 4, true);
                this.mSoundFieldView.setType(11);
            }
            if (!view.isSelected()) {
                refreshCustomBtnVui((XButton) view, true);
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B011", "type", 0);
        } else if (id == R.id.sound_effect_mode_real) {
            this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_1_HIFI, true);
            this.mSoundFieldViewHifiOriginal.setType(7);
            sendButtonDataLog("B002", 0);
        } else if (id == R.id.sound_effect_mode_soft) {
            this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_2_HIFI, true);
            this.mSoundFieldViewHifiOriginal.setType(8);
            sendButtonDataLog("B002", 1);
        } else if (id == R.id.sound_effect_mode_dynamic) {
            this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_3_HIFI, true);
            this.mSoundFieldViewHifiOriginal.setType(6);
            sendButtonDataLog("B002", 2);
        } else if (id == R.id.sound_effect_mode_human) {
            this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_4_HIFI, true);
            this.mSoundFieldViewHifiOriginal.setType(1);
            sendButtonDataLog("B002", 3);
        } else if (id == R.id.sound_effect_recent_mode_dynamic) {
            this.mSoundManager.setSoundEffectType(1, 1, true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B004", "type", 1);
            this.mSoundFieldView.setType(6);
        } else if (id == R.id.sound_effect_recent_mode_original) {
            this.mSoundManager.setSoundEffectType(1, 0, true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B004", "type", 0);
            this.mSoundFieldView.setType(11);
        } else if (id == R.id.sound_effect_recent_mode_cinema) {
            this.mSoundManager.setSoundEffectType(1, 2, true);
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B004", "type", 2);
            this.mSoundFieldView.setType(8);
        }
    }

    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.sound_effect_switch_btn) {
            if (this.mIntervalControl.isFrequently(800)) {
                return;
            }
            int i = this.mCurrentEffect;
            if (i == 3) {
                this.mCurrentEffect = 1;
                this.mSoundManager.setSoundEffectMode(1, true);
            } else if (i == 1) {
                this.mCurrentEffect = 3;
                this.mSoundManager.setSoundEffectMode(3, true);
            }
            startSwitchAnimator();
        } else if (id == R.id.sound_effect_equalizer_custom1) {
            saveEqualizerCustom(1);
            if (!this.mEqualizerStartSwitch.isChecked()) {
                saveEqualizerSwitch(true, true);
            }
            refreshEqualizerCustom(1, 1);
            refreshEqualizerValues(1, true);
            if (CarConfigHelper.hasHifiSound()) {
                if (this.mCurrentEffect == 3) {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B015", "type", 1);
                } else {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B014", "type", 1);
                }
            }
        } else if (id == R.id.sound_effect_equalizer_custom2) {
            saveEqualizerCustom(2);
            if (!this.mEqualizerStartSwitch.isChecked()) {
                saveEqualizerSwitch(true, true);
            }
            refreshEqualizerCustom(1, 2);
            refreshEqualizerValues(2, true);
            if (CarConfigHelper.hasHifiSound()) {
                if (this.mCurrentEffect == 3) {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B015", "type", 2);
                } else {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B014", "type", 2);
                }
            }
        } else if (id == R.id.sound_effect_equalizer) {
            switchToEqualizer();
            if (CarConfigHelper.hasHifiSound()) {
                if (this.mCurrentEffect == 3) {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B015", "type", 0);
                } else {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B014", "type", 0);
                }
            }
        } else if (id == R.id.sound_effect_equalizer_restore_default) {
            restoreEqualizer();
            if (!this.mEqualizerStartSwitch.isChecked() && CarConfigHelper.hasHifiSound()) {
                saveEqualizerSwitch(true, true);
                refreshEqualizerCustom(1, Integer.valueOf(getEqualizerCustomValue()));
            }
            saveEqualizerValueSp("");
            this.mSoundManager.flushXpCustomizeEffects(new int[9]);
            if (CarConfigHelper.hasHifiSound()) {
                if (this.mCurrentEffect == 3) {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B015", "type", 3);
                    return;
                } else {
                    BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B014", "type", 3);
                    return;
                }
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, "B011", "type", 1);
        } else {
            onValueChange(view);
            if (this.mEffectModeBtns.contains(view)) {
                refreshModeBtns(this.mEffectModeBtns, view.getId(), R.id.sound_effect_mode_custom);
            } else if (this.mEffectRecentModeBtns.contains(view)) {
                refreshModeBtns(this.mEffectRecentModeBtns, view.getId(), R.id.sound_effect_recent_mode_custom);
            } else if (this.mEffectHifiModeBtns.contains(view)) {
                Iterator<XButton> it = this.mEffectHifiModeBtns.iterator();
                while (it.hasNext()) {
                    it.next().setSelected(false);
                }
            } else if (this.mEffectSceneBtns.contains(view)) {
                Iterator<XButton> it2 = this.mEffectSceneBtns.iterator();
                while (it2.hasNext()) {
                    it2.next().setSelected(false);
                }
            } else if (this.mEffectDynaModeBtns.contains(view)) {
                Iterator<XButton> it3 = this.mEffectDynaModeBtns.iterator();
                while (it3.hasNext()) {
                    it3.next().setSelected(false);
                }
            }
            view.setSelected(true);
        }
    }

    private void refreshModeBtns(List<XButton> list, int i, int i2) {
        for (XButton xButton : list) {
            if (xButton.getId() != i) {
                if (xButton.getId() == i2 && xButton.isSelected()) {
                    xButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    refreshCustomBtnVui(xButton, false);
                }
                xButton.setSelected(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightUI() {
        View view;
        int i = this.mCurrentEffect;
        if (i != 1) {
            if (i == 3 && (view = this.mSceneGroup) != null) {
                view.setVisibility(8);
                return;
            }
            return;
        }
        View view2 = this.mSceneGroup;
        if (view2 != null) {
            view2.setVisibility(CarFunction.isSupportEffectScene() ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBottomUI() {
        int i = this.mCurrentEffect;
        if (i != 1) {
            if (i != 3) {
                return;
            }
            this.mEffectImg1.setImageDrawable(getContext().getDrawable(R.drawable.pic_pic_dynaudio));
            if (CarConfigHelper.hasDolby()) {
                this.mEffectImg2.setVisibility(0);
                this.mEffectImg2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dolby));
                this.mEffectTipsLine.setVisibility(0);
            } else {
                this.mEffectImg2.setVisibility(8);
                this.mEffectTipsLine.setVisibility(8);
            }
            this.mEffectTips.setText(R.string.effect_hifi_tips);
            this.mEffectTipsIcon.setVisibility(8);
            this.mEffectTips.setVisibility(0);
        } else if (CarFunction.isSupportRecentEffectMode()) {
            this.mEffectImg1.setImageDrawable(getContext().getDrawable(R.drawable.xopera));
            this.mEffectImg2.setVisibility(8);
            this.mEffectImg1.setVisibility(CarConfigHelper.hasHifiSound() ? 0 : 8);
            this.mEffectTips.setVisibility(8);
            this.mEffectTipsIcon.setVisibility(8);
            this.mEffectTipsLine.setVisibility(8);
        } else {
            this.mEffectImg1.setImageDrawable(getContext().getDrawable(R.drawable.pic_xsound));
            if (CarConfigHelper.hasDolby()) {
                this.mEffectTips.setText(R.string.effect_round_tips_no_dts);
                this.mEffectTips.setVisibility(0);
                this.mEffectTipsIcon.setVisibility(8);
                this.mEffectImg2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dolby));
                this.mEffectImg2.setVisibility(0);
                this.mEffectTipsLine.setVisibility(0);
            } else if (!CarConfigHelper.hasHifiSound()) {
                if (!CarConfigHelper.isLowSpeaker()) {
                    if (CarFunction.isSupportDts() && CarConfigHelper.hasDtsScenes()) {
                        this.mEffectImg2.setVisibility(0);
                        this.mEffectImg2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dts));
                        this.mEffectTips.setText(R.string.effect_low_speaker_round_tips);
                        this.mEffectTipsLine.setVisibility(0);
                    } else {
                        this.mEffectImg2.setVisibility(8);
                        this.mEffectTips.setText(R.string.effect_round_tips_no_dts);
                        this.mEffectTipsLine.setVisibility(8);
                    }
                    this.mEffectTipsIcon.setVisibility(8);
                    this.mEffectTips.setVisibility(0);
                    return;
                }
                this.mEffectTips.setText(R.string.effect_round_tips_no_dts);
                this.mEffectImg2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dts));
                this.mEffectImg2.setVisibility(8);
                this.mEffectTipsLine.setVisibility(8);
                this.mEffectTipsIcon.setVisibility(8);
                this.mEffectTips.setVisibility(0);
            } else if (CarFunction.isSupportDts()) {
                this.mEffectTips.setText(R.string.effect_high_speaker_round_tips);
                this.mEffectTipsIcon.setVisibility(0);
                this.mEffectTips.setVisibility(8);
                this.mEffectImg2.setImageDrawable(getContext().getDrawable(R.drawable.pic_dts));
                this.mEffectImg2.setVisibility(0);
                this.mEffectTipsLine.setVisibility(0);
            } else {
                this.mEffectTips.setText(R.string.effect_round_tips_no_dts);
                this.mEffectTips.setVisibility(0);
                this.mEffectTipsIcon.setVisibility(8);
                this.mEffectImg2.setVisibility(8);
                this.mEffectTipsLine.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEffectContent() {
        initTop();
        updateLeftUI();
        updateRightUI();
        updateBottomUI();
        updateCenterUI();
    }

    Typeface getAttachedTypeFace() {
        return Typeface.create(getResources().getString(com.xiaopeng.xpui.R.string.x_font_typeface_medium), 0);
    }

    public void setSceneId(String str) {
        this.mSceneId = str;
    }

    public void setCloseClickListener(View.OnClickListener onClickListener) {
        this.mCloseClickListener = onClickListener;
        View view = this.mCloseButton;
        if (view != null) {
            view.setOnClickListener(this.mCloseClickListener);
        }
    }

    private void updateVuiSceneForSwitch(String str) {
        this.mEffectSwitchBtn.setVuiLabel(str);
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEffectSwitchBtn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initVuiScene() {
        if (this.mSceneId != null) {
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mView);
        }
    }

    private void sendButtonDataLog(String str, int i) {
        BuriedPointUtils.sendButtonDataLog("P00005", str, "type", i);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onSoundEffectChange(boolean z) {
        if (z) {
            return;
        }
        if (this.mSoundFieldView == null && this.mSoundFieldViewHifiOriginal == null) {
            log("no inflate complete!");
            return;
        }
        log("onReceive refreshdata and fill ui");
        refreshData();
        fillUI();
        initVuiScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onHeadRestModeChange() {
        initCenter();
    }

    public boolean isInEqualizer() {
        return this.mEqualizerContentView.getVisibility() == 0;
    }

    private void restoreEqualizer() {
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        while (it.hasNext()) {
            XSlider next = it.next();
            next.setCurrentIndex(0);
            XTextView xTextView = this.mEqualizerTexts.get(this.mEqualizerSliders.indexOf(next));
            if (xTextView != null) {
                xTextView.setText("0");
            }
        }
        this.mEqualizerRestoreDefaultBtn.setEnabled(false);
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEqualizerRestoreDefaultBtn);
    }

    private void refreshEqualizerRestoreBtn() {
        boolean z;
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            } else if (((int) it.next().getIndicatorValue()) != 0) {
                z = false;
                break;
            }
        }
        if (z) {
            this.mEqualizerRestoreDefaultBtn.setEnabled(false);
        } else {
            this.mEqualizerRestoreDefaultBtn.setEnabled(true);
        }
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEqualizerRestoreDefaultBtn);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void switchToEqualizer() {
        if (this.mEqualizerView == null) {
            this.mEqualizerView = ((ViewStub) findViewById(R.id.sound_effect_equalizer_mid_stub)).inflate();
            if (CarConfigHelper.hasHifiSound()) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEqualizerView.getLayoutParams();
                marginLayoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.sound_effect_amp_left_margin);
                this.mEqualizerView.setLayoutParams(marginLayoutParams);
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mEqualizerView.getLayoutParams();
                marginLayoutParams2.leftMargin = getResources().getDimensionPixelSize(R.dimen.sound_effect_left_margin);
                this.mEqualizerView.setLayoutParams(marginLayoutParams2);
            }
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider1));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider1_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider2));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider2_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider3));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider3_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider4));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider4_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider5));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider5_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider6));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider6_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider7));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider7_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider8));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider8_value));
            this.mEqualizerSliders.add(this.mEqualizerView.findViewById(R.id.equalizer_slider9));
            this.mEqualizerTexts.add(this.mEqualizerView.findViewById(R.id.equalizer_slider9_value));
            Iterator<XSlider> it = this.mEqualizerSliders.iterator();
            while (it.hasNext()) {
                XSlider next = it.next();
                next.setStartIndex(-9);
                next.setEndIndex(9);
                next.setInitIndex(0);
                next.setAccuracy(1.0f);
                next.setSliderProgressListener(this);
                XTextView xTextView = this.mEqualizerTexts.get(this.mEqualizerSliders.indexOf(next));
                if (xTextView != null) {
                    xTextView.setText("0");
                }
            }
            this.mEqualizerStartSwitchTips = (TextView) findViewById(R.id.sound_effect_equalizer_switch_tips);
            this.mEqualizerStartSwitch = (XSwitch) findViewById(R.id.sound_effect_equalizer_switch);
            this.mEqualizerStartSwitch.setOnCheckedChangeListener(this);
            this.mEqualizerStartSwitch.setVuiLabel(this.mContext.getString(R.string.sound_subtitle_effects_equalizer));
            this.mEqualizerCustom1Btn = (XButton) findViewById(R.id.sound_effect_equalizer_custom1);
            this.mEqualizerCustom1Btn.setOnClickListener(this);
            this.mEqualizerCustom2Btn = (XButton) findViewById(R.id.sound_effect_equalizer_custom2);
            this.mEqualizerCustom2Btn.setOnClickListener(this);
            this.mEqualizerRestoreDefaultBtn = (XButton) findViewById(R.id.sound_effect_equalizer_restore_default);
            this.mEqualizerRestoreDefaultBtn.setOnClickListener(this);
            if (CarConfigHelper.hasHifiSound()) {
                this.mEqualizerStartSwitchTips.setVisibility(0);
                this.mEqualizerStartSwitch.setVisibility(0);
                this.mEqualizerCustom1Btn.setVisibility(0);
                this.mEqualizerCustom2Btn.setVisibility(0);
            } else {
                this.mEqualizerStartSwitchTips.setVisibility(8);
                this.mEqualizerStartSwitch.setVisibility(8);
                this.mEqualizerCustom1Btn.setVisibility(8);
                this.mEqualizerCustom2Btn.setVisibility(8);
            }
        }
        this.mEffectContentView.setVisibility(8);
        this.mEqualizerContentView.setVisibility(0);
        this.mXpTitle.setText(getContext().getString(R.string.sound_effects_equalizer_title));
        ((XImageButton) this.mCloseButton).setImageResource(R.drawable.x_ic_small_back);
        ((XImageButton) this.mCloseButton).setVuiLabel(getContext().getString(R.string.sound_effects_back));
        ((XImageView) this.mCloseImg).setImageResource(R.drawable.x_ic_small_back);
        if (CarConfigHelper.hasHifiSound()) {
            int equalizerCustomValue = getEqualizerCustomValue();
            refreshEqualizerCustom(null, Integer.valueOf(equalizerCustomValue));
            refreshEqualizerValues(equalizerCustomValue, false);
            XSwitch xSwitch = this.mEqualizerStartSwitch;
            if (xSwitch != null) {
                refreshEqualizerStyle(xSwitch.isChecked());
            }
        } else {
            refreshEqualizerValues(1, false);
        }
        initVuiScene();
    }

    private void refreshEqualizerCustom(Integer num, Integer num2) {
        if (num == null) {
            num = Integer.valueOf(getEqualizerSwitch());
        }
        XSwitch xSwitch = this.mEqualizerStartSwitch;
        if (xSwitch != null) {
            xSwitch.setChecked(num.intValue() != 0);
        }
        if (num.intValue() == 0) {
            XButton xButton = this.mEqualizerBtn;
            if (xButton != null) {
                xButton.setText(R.string.sound_effect_equalizer_no_work);
                this.mEqualizerBtn.setSelected(false);
            }
            XButton xButton2 = this.mEqualizerCustom1Btn;
            if (xButton2 != null) {
                xButton2.setSelected(false);
            }
            XButton xButton3 = this.mEqualizerCustom2Btn;
            if (xButton3 != null) {
                xButton3.setSelected(false);
                return;
            }
            return;
        }
        if (num2 == null) {
            num2 = Integer.valueOf(getEqualizerCustomValue());
        }
        if (num2.intValue() == 2) {
            XButton xButton4 = this.mEqualizerBtn;
            if (xButton4 != null) {
                xButton4.setText(R.string.sound_effect_equalizer_custom2);
                this.mEqualizerBtn.setSelected(true);
            }
            XButton xButton5 = this.mEqualizerCustom1Btn;
            if (xButton5 != null) {
                xButton5.setSelected(false);
            }
            XButton xButton6 = this.mEqualizerCustom2Btn;
            if (xButton6 != null) {
                xButton6.setSelected(true);
                return;
            }
            return;
        }
        XButton xButton7 = this.mEqualizerBtn;
        if (xButton7 != null) {
            xButton7.setText(R.string.sound_effect_equalizer_custom1);
            this.mEqualizerBtn.setSelected(true);
        }
        XButton xButton8 = this.mEqualizerCustom1Btn;
        if (xButton8 != null) {
            xButton8.setSelected(true);
        }
        XButton xButton9 = this.mEqualizerCustom2Btn;
        if (xButton9 != null) {
            xButton9.setSelected(false);
        }
    }

    private int[] getCurrentEqualizerValues(Integer num) {
        if (num == null) {
            return this.mSoundManager.convertEqualizerValues(this.mCurrentEffect, getEqualizerCustomValue());
        }
        return this.mSoundManager.convertEqualizerValues(this.mCurrentEffect, num.intValue());
    }

    private void refreshEqualizerValues(int i, boolean z) {
        int[] currentEqualizerValues = getCurrentEqualizerValues(Integer.valueOf(i));
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        int i2 = 0;
        boolean z2 = true;
        while (it.hasNext()) {
            XSlider next = it.next();
            next.setCurrentIndex(currentEqualizerValues[i2]);
            this.mEqualizerTexts.get(this.mEqualizerSliders.indexOf(next)).setText(String.valueOf(currentEqualizerValues[i2]));
            if (currentEqualizerValues[i2] != 0) {
                z2 = false;
            }
            i2++;
        }
        this.mEqualizerRestoreDefaultBtn.setEnabled(!z2);
        if (z) {
            this.mSoundManager.flushXpCustomizeEffects(currentEqualizerValues);
        }
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEqualizerRestoreDefaultBtn);
    }

    public void switchToEffect() {
        this.mEffectContentView.setVisibility(0);
        this.mEqualizerContentView.setVisibility(8);
        initTop();
        ((XImageButton) this.mCloseButton).setImageResource(R.drawable.x_ic_small_close);
        ((XImageButton) this.mCloseButton).setVuiLabel(getContext().getString(R.string.sound_effects_close));
        ((XImageView) this.mCloseImg).setImageResource(R.drawable.x_ic_small_close);
        if (CarConfigHelper.hasHifiSound()) {
            refreshEqualizerCustom(null, null);
        }
        initVuiScene();
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onProgressChanged(XSlider xSlider, float f, String str) {
        refreshEqualizerRestoreBtn();
        saveEqualizerValues();
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        int i = 0;
        while (it.hasNext()) {
            XSlider next = it.next();
            if (next == xSlider) {
                int i2 = (int) f;
                this.mSoundManager.setXpCustomizeEffect(i, i2);
                this.mEqualizerTexts.get(this.mEqualizerSliders.indexOf(next)).setText(String.valueOf(i2));
            }
            i++;
        }
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStartTrackingTouch(XSlider xSlider) {
        if (!CarConfigHelper.hasHifiSound() || this.mEqualizerStartSwitch.isChecked()) {
            return;
        }
        saveEqualizerSwitch(true, true);
        refreshEqualizerCustom(1, null);
        this.mSoundManager.flushXpCustomizeEffects(getCurrentEqualizerValues(null));
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEqualizerStartSwitch);
        if (((XSwitch) compoundButton).isFromUser() || VuiManager.instance().isVuiAction(compoundButton)) {
            saveEqualizerSwitch(z, false);
            refreshEqualizerCustom(Integer.valueOf(z ? 1 : 0), Integer.valueOf(getEqualizerCustomValue()));
            if (z) {
                this.mSoundManager.flushXpCustomizeEffects(getCurrentEqualizerValues(null));
            } else {
                this.mSoundManager.flushXpCustomizeEffects(new int[9]);
            }
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_STATUS_PAGE_ID, this.mCurrentEffect == 3 ? "B017" : "B016", z);
        }
    }

    private void popToastEqualizerStart() {
        ToastUtils.get().showText(R.string.sound_effect_equalizer_start_work);
    }

    private void saveEqualizerValues() {
        StringBuilder sb = new StringBuilder();
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        while (it.hasNext()) {
            sb.append((int) it.next().getIndicatorValue());
            sb.append(",");
        }
        saveEqualizerValueSp(sb.substring(0, sb.length() - 1));
    }

    private void saveEqualizerValueSp(String str) {
        if (CarConfigHelper.hasHifiSound()) {
            if (this.mEqualizerCustom1Btn.isSelected()) {
                Logs.d("sound effect equalizer hifi save1:" + str);
                saveEqualizerValues(1, str);
                return;
            } else if (this.mEqualizerCustom2Btn.isSelected()) {
                Logs.d("sound effect equalizer hifi save2:" + str);
                saveEqualizerValues(2, str);
                return;
            } else {
                int equalizerCustomValue = getEqualizerCustomValue();
                Logs.d("sound effect equalizer hifi saveValue error!" + equalizerCustomValue);
                return;
            }
        }
        Logs.d("sound effect equalizer mid save:" + str);
        this.mSoundManager.saveEqualizerValues(1, 1, str);
    }

    private void saveEqualizerSwitch(boolean z, boolean z2) {
        this.mSoundManager.saveEqualizerSwitch(this.mCurrentEffect, z, true);
        if (z2) {
            popToastEqualizerStart();
        }
        refreshEqualizerStyle(z);
    }

    private void refreshEqualizerStyle(boolean z) {
        ArrayList<XSlider> arrayList = this.mEqualizerSliders;
        if (arrayList != null) {
            Iterator<XSlider> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().setStyle(z ? R.style.XSlider_Vertical : R.style.XSlider_Invalid_Vertical);
            }
        }
    }

    private int getEqualizerSwitch() {
        return this.mSoundManager.getEqualizerSwitch(this.mCurrentEffect);
    }

    private void saveEqualizerCustom(int i) {
        this.mSoundManager.saveEqualizerCustom(this.mCurrentEffect, i, true);
    }

    private int getEqualizerCustomValue() {
        return this.mSoundManager.getEqualizerCustomValue(this.mCurrentEffect);
    }

    private String getEqualizerValues(int i) {
        return this.mSoundManager.getEqualizerValues(this.mCurrentEffect, i);
    }

    private void saveEqualizerValues(int i, String str) {
        this.mSoundManager.saveEqualizerValues(this.mCurrentEffect, i, str);
    }
}
