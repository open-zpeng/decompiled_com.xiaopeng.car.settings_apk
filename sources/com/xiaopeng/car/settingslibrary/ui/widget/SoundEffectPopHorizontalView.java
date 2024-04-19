package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.constraintlayout.core.motion.utils.TypedValues;
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
import com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView;
import com.xiaopeng.car.settingslibrary.ui.widget.anim.SoundEffectAnimView;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SoundEffectPopHorizontalView extends XFrameLayout implements View.OnClickListener, ISoundServerListener {
    public static final int IN_OUT_TIMEOUT = 400;
    private AsyncLayoutInflater mAsyncLayoutInflater;
    private View mBottomBg;
    public ObjectAnimator mBottomOutAnimator;
    private View mCloseButton;
    private View mCloseButtonGroup;
    private View.OnClickListener mCloseClickListener;
    private Context mContext;
    private int mCurrentEffect;
    private int mCurrentScenes;
    ViewGroup mDialogRoot;
    private ImageView mEffectImg1;
    private ImageView mEffectImg2;
    private ValuePickerView mEffectScenes;
    private ViewGroup mEffectScenesGroup;
    private ImageView mEffectScenesMask;
    private ViewGroup mEffectStyleGroup;
    private ImageView mEffectStyleMask;
    private ValuePickerView mEffectStyles;
    private ValuePickerView mEffectStylesHifi;
    private XTextView mEffectSwitchBtn;
    private XTextView mEffectTips;
    private ImageView mEffectTipsIcon;
    private View mEffectTipsLayout;
    private View mEffectTipsLine;
    private ViewStub mFieldStub;
    private SoundFieldValues mHifiSoundFieldValues;
    private int[] mHifiSpreadColor;
    private int mHifiType;
    private IntervalControl mIntervalControl;
    private boolean mIsInit;
    private boolean mIsRestoreData;
    private LayoutInflater mLayoutInflater;
    private View mLeftBg;
    public ObjectAnimator mLeftOutAnimator;
    private SoundFieldSpreadView mMainDriverSpreadView;
    private SoundFieldView.PositionChangeListener mOnPositionChangeListener;
    private ViewStub mPickStub;
    private View mRightBg;
    public ObjectAnimator mRightOutAnimator;
    private SoundFieldValues mRoundSoundFieldValues;
    private int mRoundType;
    private String mSceneId;
    private ViewStub mSceneStub;
    private SoundEffectAnimView mSoundEffectAnimView;
    private ViewGroup mSoundFieldArea;
    private XTextView mSoundFieldTips;
    private SoundFieldView mSoundFieldView;
    private SoundFieldView mSoundFieldViewHifiOriginal;
    private ImageView mSoundFiledRoof;
    private SoundManager mSoundManager;
    private ViewStub mSpreadStub;
    private ObjectAnimator mSwitchBottomAnimator;
    private ObjectAnimator mSwitchLeftAnimator;
    private ObjectAnimator mSwitchTopAnimator1;
    private ObjectAnimator mSwitchTopAnimator2;
    public ObjectAnimator mTopInAnimator;
    public ObjectAnimator mTopInAnimator2;
    public ObjectAnimator mTopInAnimator3;
    public ObjectAnimator mTopInAnimatorClose;
    private ThemeViewModel mUIThemeViewModel;
    private ValuePickerView.OnValueChangeListener mValueChangeListener;
    private ViewGroup mView;
    private int[] mXpSpreadColor;
    private TextView mXpTitle;

    public SoundEffectPopHorizontalView(Context context) {
        this(context, null);
    }

    public SoundEffectPopHorizontalView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentScenes = -1;
        this.mCurrentEffect = -1;
        this.mHifiType = -1;
        this.mRoundType = -1;
        this.mHifiSpreadColor = new int[]{R.color.sound_field_spread_shader_start, R.color.sound_field_spread_shader_end};
        this.mXpSpreadColor = new int[]{R.color.sound_field_spread_shader_xp_start, R.color.sound_field_spread_shader_xp_end};
        this.mIsRestoreData = false;
        this.mValueChangeListener = new ValuePickerView.OnValueChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.17
            @Override // com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView.OnValueChangeListener
            public void onTouchDown() {
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView.OnValueChangeListener
            public void onTouchUp() {
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView.OnValueChangeListener
            public void onValueChange(ValuePickerView valuePickerView, int i, int i2) {
                Logs.d("xpsoundeffect mValueChangeListener onValueChange picker" + valuePickerView + " ," + i + "," + i2);
                if (valuePickerView == SoundEffectPopHorizontalView.this.mEffectScenes) {
                    if (i2 == 0) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectScene(1, 4, true);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 6);
                        SoundEffectPopHorizontalView.this.updateScenesForCar(4);
                    } else if (i2 == 1) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectScene(1, 2, true);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 7);
                        SoundEffectPopHorizontalView.this.updateScenesForCar(2);
                    } else if (i2 == 2) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectScene(1, 1, true);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 9);
                        SoundEffectPopHorizontalView.this.updateScenesForCar(1);
                    } else if (i2 != 3) {
                    } else {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectScene(1, 3, true);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 8);
                        SoundEffectPopHorizontalView.this.updateScenesForCar(3);
                    }
                } else if (valuePickerView == SoundEffectPopHorizontalView.this.mEffectStyles) {
                    if (SoundEffectPopHorizontalView.this.mCurrentEffect == 1) {
                        if (!CarConfigHelper.hasHifiSound()) {
                            switch (i2) {
                                case 0:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 1, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 0);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(0);
                                    break;
                                case 1:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 2, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 1);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(1);
                                    break;
                                case 2:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 3, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 2);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(2);
                                    break;
                                case 3:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 4, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 3);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(3);
                                    break;
                                case 4:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 5, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 4);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(5);
                                    break;
                                case 5:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 6, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 5);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(4);
                                    break;
                                case 6:
                                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 0, true);
                                    SoundEffectPopHorizontalView.this.sendButtonDataLog("B001", 10);
                                    SoundEffectPopHorizontalView.this.mSoundFieldView.setType(11);
                                    break;
                            }
                        } else if (i2 != 1) {
                            SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 1, true);
                            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B001", "type", 0);
                            SoundEffectPopHorizontalView.this.mSoundFieldView.setType(0);
                        } else {
                            SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(1, 2, true);
                            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.SOUND_EFFECT_HIFI_PAGE_ID, "B001", "type", 1);
                            SoundEffectPopHorizontalView.this.mSoundFieldView.setType(9);
                        }
                        SoundEffectPopHorizontalView soundEffectPopHorizontalView = SoundEffectPopHorizontalView.this;
                        soundEffectPopHorizontalView.log("音效选中" + i2);
                    }
                } else if (valuePickerView == SoundEffectPopHorizontalView.this.mEffectStylesHifi && SoundEffectPopHorizontalView.this.mCurrentEffect == 3) {
                    if (i2 == 0) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_1_HIFI, true);
                        SoundEffectPopHorizontalView.this.mSoundFieldViewHifiOriginal.setType(7);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B002", 0);
                    } else if (i2 == 1) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_2_HIFI, true);
                        SoundEffectPopHorizontalView.this.mSoundFieldViewHifiOriginal.setType(8);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B002", 1);
                    } else if (i2 == 2) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_3_HIFI, true);
                        SoundEffectPopHorizontalView.this.mSoundFieldViewHifiOriginal.setType(6);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B002", 2);
                    } else if (i2 != 3) {
                    } else {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundEffectType(3, SoundEffectValues.SOUND_STYLE_4_HIFI, true);
                        SoundEffectPopHorizontalView.this.mSoundFieldViewHifiOriginal.setType(1);
                        SoundEffectPopHorizontalView.this.sendButtonDataLog("B002", 3);
                    }
                }
            }
        };
        this.mOnPositionChangeListener = new SoundFieldView.PositionChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.18
            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPositionChange(SoundFieldView soundFieldView, int i, int i2) {
                Logs.v("xpsoundeffect onPositionChange x:" + i + " y:" + i2 + " " + soundFieldView);
                if (soundFieldView == SoundEffectPopHorizontalView.this.mSoundFieldView) {
                    SoundEffectPopHorizontalView.this.mSoundManager.setSoundField(1, i, i2, true);
                } else if (soundFieldView == SoundEffectPopHorizontalView.this.mSoundFieldViewHifiOriginal) {
                    if (CarConfigHelper.hasHifiSound()) {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundField(3, i, i2, true);
                    } else {
                        SoundEffectPopHorizontalView.this.mSoundManager.setSoundField(4, i, i2, true);
                    }
                }
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPressChange(boolean z) {
                if (!z) {
                    SoundEffectPopHorizontalView.this.mSoundFieldTips.setVisibility(8);
                    SoundEffectPopHorizontalView.this.reverseInOutAnimator();
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView.setVisible(soundEffectPopHorizontalView.mCloseButtonGroup, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView2 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView2.setVisible(soundEffectPopHorizontalView2.mXpTitle, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView3 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView3.setVisible(soundEffectPopHorizontalView3.mEffectSwitchBtn, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView4 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView4.setVisible(soundEffectPopHorizontalView4.mEffectTipsLayout, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView5 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView5.setVisible(soundEffectPopHorizontalView5.mEffectScenesGroup, true);
                    if (SoundEffectPopHorizontalView.this.mCurrentEffect != 1) {
                        if (SoundEffectPopHorizontalView.this.mCurrentEffect == 3) {
                            SoundEffectPopHorizontalView soundEffectPopHorizontalView6 = SoundEffectPopHorizontalView.this;
                            soundEffectPopHorizontalView6.setVisible(soundEffectPopHorizontalView6.mEffectStyleGroup, true);
                            SoundEffectPopHorizontalView soundEffectPopHorizontalView7 = SoundEffectPopHorizontalView.this;
                            soundEffectPopHorizontalView7.setVisible(soundEffectPopHorizontalView7.mEffectStyles, false);
                            SoundEffectPopHorizontalView soundEffectPopHorizontalView8 = SoundEffectPopHorizontalView.this;
                            soundEffectPopHorizontalView8.setVisible(soundEffectPopHorizontalView8.mEffectStylesHifi, true);
                            return;
                        }
                        return;
                    }
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView9 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView9.setVisible(soundEffectPopHorizontalView9.mEffectStyleGroup, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView10 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView10.setVisible(soundEffectPopHorizontalView10.mEffectStyles, true);
                    SoundEffectPopHorizontalView soundEffectPopHorizontalView11 = SoundEffectPopHorizontalView.this;
                    soundEffectPopHorizontalView11.setVisible(soundEffectPopHorizontalView11.mEffectStylesHifi, false);
                    if (CarConfigHelper.hasHifiSound() || CarConfigHelper.isLowSpeaker()) {
                        return;
                    }
                    SoundEffectPopHorizontalView.this.effectScenesShow(true);
                    return;
                }
                SoundEffectPopHorizontalView soundEffectPopHorizontalView12 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView12.setVisible(soundEffectPopHorizontalView12.mEffectSwitchBtn, false);
                SoundEffectPopHorizontalView soundEffectPopHorizontalView13 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView13.setVisible(soundEffectPopHorizontalView13.mEffectStyleGroup, false);
                SoundEffectPopHorizontalView soundEffectPopHorizontalView14 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView14.setVisible(soundEffectPopHorizontalView14.mEffectScenesGroup, false);
                SoundEffectPopHorizontalView soundEffectPopHorizontalView15 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView15.setVisible(soundEffectPopHorizontalView15.mEffectTipsLayout, false);
                SoundEffectPopHorizontalView soundEffectPopHorizontalView16 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView16.setVisible(soundEffectPopHorizontalView16.mXpTitle, false);
                SoundEffectPopHorizontalView soundEffectPopHorizontalView17 = SoundEffectPopHorizontalView.this;
                soundEffectPopHorizontalView17.setVisible(soundEffectPopHorizontalView17.mCloseButtonGroup, false);
                SoundEffectPopHorizontalView.this.mSoundFieldTips.setVisibility(0);
                SoundEffectPopHorizontalView.this.startInOutAnimator();
            }
        };
        this.mSceneId = null;
        this.mView = this;
        this.mContext = context;
        this.mUIThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mUIThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("xpsoundeffect dialog onThemeChanged callback ");
                SoundEffectPopHorizontalView.this.mDialogRoot.setBackground(SoundEffectPopHorizontalView.this.getContext().getDrawable(R.drawable.sound_effect_dialog_bg));
                if (SoundEffectPopHorizontalView.this.mEffectStyles != null) {
                    SoundEffectPopHorizontalView.this.mEffectStyles.setNormalTextColor(R.color.value_picker_text_color);
                    SoundEffectPopHorizontalView.this.mEffectStyles.setSelectedTextColor(R.color.value_picker_text_color_chose);
                }
                if (SoundEffectPopHorizontalView.this.mEffectStylesHifi != null) {
                    SoundEffectPopHorizontalView.this.mEffectStylesHifi.setNormalTextColor(R.color.value_picker_text_color);
                    SoundEffectPopHorizontalView.this.mEffectStylesHifi.setSelectedTextColor(R.color.value_picker_text_color_chose);
                }
                if (SoundEffectPopHorizontalView.this.mEffectScenes != null) {
                    SoundEffectPopHorizontalView.this.mEffectScenes.setNormalTextColor(R.color.value_picker_text_color);
                    SoundEffectPopHorizontalView.this.mEffectScenes.setSelectedTextColor(R.color.value_picker_text_color_chose);
                }
                SoundEffectPopHorizontalView.this.mLeftBg.setBackground(SoundEffectPopHorizontalView.this.getContext().getDrawable(R.drawable.pic_mask_left));
                SoundEffectPopHorizontalView.this.mRightBg.setBackground(SoundEffectPopHorizontalView.this.getContext().getDrawable(R.drawable.pic_mask_right));
                SoundEffectPopHorizontalView.this.mBottomBg.setBackground(SoundEffectPopHorizontalView.this.getContext().getDrawable(R.drawable.pic_mask_under));
                SoundEffectPopHorizontalView.this.updateRightUI();
                SoundEffectPopHorizontalView.this.updateBottomUI();
                SoundEffectPopHorizontalView.this.updateCenterUI();
            }
        });
        this.mLayoutInflater = LayoutInflater.from(getContext());
        this.mLayoutInflater.inflate(R.layout.sound_effects, this.mView);
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
            }
            refreshData();
            long currentTimeMillis = System.currentTimeMillis();
            fillUI();
            log("onStart " + (System.currentTimeMillis() - currentTimeMillis));
            initVuiScene();
        }
        SoundServerManager.getInstance().addSoundUIListener(this);
    }

    private void inflateView(View view) {
        initTopView(view);
        initBottomView(view);
        this.mEffectStyleGroup = (ViewGroup) view.findViewById(R.id.style_group);
        this.mLeftBg = view.findViewById(R.id.left_bg);
        this.mEffectScenesGroup = (ViewGroup) view.findViewById(R.id.scenes_group);
        this.mRightBg = view.findViewById(R.id.right_bg);
        this.mSoundFieldArea = (ViewGroup) view.findViewById(R.id.sound_field_area);
        this.mSoundFiledRoof = (ImageView) view.findViewById(R.id.sound_field_roof);
    }

    private void asyncInflateView() {
        if (this.mAsyncLayoutInflater == null) {
            this.mAsyncLayoutInflater = new AsyncLayoutInflater(getContext());
        }
        this.mAsyncLayoutInflater.inflate(R.layout.sound_effects_left, this.mEffectStyleGroup, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.2
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                SoundEffectPopHorizontalView.this.initLeftView(view);
                SoundEffectPopHorizontalView.this.initLeft();
                if (viewGroup != null) {
                    viewGroup.addView(view);
                }
            }
        });
        if (CarFunction.isSupportEffectScene()) {
            this.mAsyncLayoutInflater.inflate(R.layout.sound_effects_right, this.mEffectScenesGroup, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.3
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                    SoundEffectPopHorizontalView.this.initRightView(view);
                    SoundEffectPopHorizontalView.this.initRight();
                    if (viewGroup != null) {
                        viewGroup.addView(view);
                    }
                }
            });
        }
        this.mAsyncLayoutInflater.inflate(R.layout.sound_effects_center, this.mSoundFieldArea, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.4
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                SoundEffectPopHorizontalView.this.initCenterView(view);
                SoundEffectPopHorizontalView.this.initCenter();
                if (viewGroup != null) {
                    viewGroup.addView(view);
                }
                SoundEffectPopHorizontalView.this.initVuiScene();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
        cancelInOutAnimator();
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
        ObjectAnimator objectAnimator3 = this.mSwitchLeftAnimator;
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
                this.mSwitchTopAnimator1.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                        super.onAnimationRepeat(animator);
                        Logs.d("switch onAnimationRepeat");
                        SoundEffectPopHorizontalView.this.updateEffectContent();
                        SoundEffectPopHorizontalView.this.initVuiScene();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Logs.d("switch onAnimationEnd");
                        super.onAnimationEnd(animator);
                        SoundEffectPopHorizontalView.this.mEffectSwitchBtn.setAlpha(1.0f);
                    }
                });
            }
            this.mSwitchTopAnimator1.start();
        }
        if (this.mSwitchLeftAnimator == null) {
            this.mSwitchLeftAnimator = ObjectAnimator.ofFloat(this.mEffectStyleGroup, "alpha", 1.0f, 0.0f);
            this.mSwitchLeftAnimator.setDuration(400L);
            this.mSwitchLeftAnimator.setRepeatMode(2);
            this.mSwitchLeftAnimator.setRepeatCount(1);
            this.mSwitchLeftAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mEffectStyleGroup.setAlpha(1.0f);
                }
            });
        }
        this.mSwitchLeftAnimator.start();
        if (this.mSwitchTopAnimator2 == null) {
            this.mSwitchTopAnimator2 = ObjectAnimator.ofFloat(this.mXpTitle, "alpha", 1.0f, 0.0f);
            this.mSwitchTopAnimator2.setDuration(400L);
            this.mSwitchTopAnimator2.setRepeatMode(2);
            this.mSwitchTopAnimator2.setRepeatCount(1);
            this.mSwitchTopAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mXpTitle.setAlpha(1.0f);
                }
            });
        }
        this.mSwitchTopAnimator2.start();
        if (this.mSwitchBottomAnimator == null) {
            this.mSwitchBottomAnimator = ObjectAnimator.ofFloat(this.mEffectTipsLayout, "alpha", 1.0f, 0.0f);
            this.mSwitchBottomAnimator.setDuration(400L);
            this.mSwitchBottomAnimator.setRepeatMode(2);
            this.mSwitchBottomAnimator.setRepeatCount(1);
            this.mSwitchBottomAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.8
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mEffectTipsLayout.setAlpha(1.0f);
                }
            });
        }
        this.mSwitchBottomAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInOutAnimator() {
        if (this.mTopInAnimator == null) {
            this.mTopInAnimator = ObjectAnimator.ofFloat(this.mSoundFieldTips, "alpha", 0.0f, 1.0f);
            this.mTopInAnimator.setDuration(400L);
            this.mTopInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.9
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mSoundFieldTips.setAlpha(1.0f);
                }
            });
        }
        this.mTopInAnimator.start();
        ObjectAnimator objectAnimator = this.mTopInAnimator2;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
        ObjectAnimator objectAnimator2 = this.mTopInAnimator3;
        if (objectAnimator2 != null) {
            objectAnimator2.reverse();
        }
        ObjectAnimator objectAnimator3 = this.mTopInAnimatorClose;
        if (objectAnimator3 != null) {
            objectAnimator3.reverse();
        }
        ObjectAnimator objectAnimator4 = this.mLeftOutAnimator;
        if (objectAnimator4 != null) {
            objectAnimator4.reverse();
        }
        ObjectAnimator objectAnimator5 = this.mRightOutAnimator;
        if (objectAnimator5 != null) {
            objectAnimator5.reverse();
        }
        ObjectAnimator objectAnimator6 = this.mBottomOutAnimator;
        if (objectAnimator6 != null) {
            objectAnimator6.reverse();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reverseInOutAnimator() {
        ObjectAnimator objectAnimator = this.mTopInAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
        XTextView xTextView = this.mEffectSwitchBtn;
        if (xTextView != null) {
            if (this.mTopInAnimator2 == null) {
                this.mTopInAnimator2 = ObjectAnimator.ofFloat(xTextView, "alpha", 0.0f, 1.0f);
                this.mTopInAnimator2.setDuration(400L);
                this.mTopInAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.10
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        SoundEffectPopHorizontalView.this.mEffectSwitchBtn.setAlpha(1.0f);
                    }
                });
            }
            this.mTopInAnimator2.start();
        }
        TextView textView = this.mXpTitle;
        if (textView != null) {
            if (this.mTopInAnimator3 == null) {
                this.mTopInAnimator3 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
                this.mTopInAnimator3.setDuration(400L);
                this.mTopInAnimator3.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.11
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        SoundEffectPopHorizontalView.this.mXpTitle.setAlpha(1.0f);
                    }
                });
            }
            this.mTopInAnimator3.start();
        }
        if (this.mTopInAnimatorClose == null) {
            this.mTopInAnimatorClose = ObjectAnimator.ofFloat(this.mCloseButtonGroup, "alpha", 0.0f, 1.0f);
            this.mTopInAnimatorClose.setDuration(400L);
            this.mTopInAnimatorClose.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.12
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mCloseButtonGroup.setAlpha(1.0f);
                }
            });
        }
        this.mTopInAnimatorClose.start();
        if (this.mLeftOutAnimator == null) {
            this.mLeftOutAnimator = ObjectAnimator.ofFloat(this.mEffectStyleGroup, "alpha", 0.0f, 1.0f);
            this.mLeftOutAnimator.setDuration(400L);
            this.mLeftOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.13
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mEffectStyleGroup.setAlpha(1.0f);
                }
            });
        }
        this.mLeftOutAnimator.start();
        if (this.mBottomOutAnimator == null) {
            this.mBottomOutAnimator = ObjectAnimator.ofFloat(this.mEffectTipsLayout, "alpha", 0.0f, 1.0f);
            this.mBottomOutAnimator.setDuration(400L);
            this.mBottomOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.14
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mEffectTipsLayout.setAlpha(1.0f);
                }
            });
        }
        this.mBottomOutAnimator.start();
        if (this.mRightOutAnimator == null) {
            this.mRightOutAnimator = ObjectAnimator.ofFloat(this.mEffectScenesGroup, "alpha", 0.0f, 1.0f);
            this.mRightOutAnimator.setDuration(400L);
            this.mRightOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.15
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopHorizontalView.this.mEffectScenesGroup.setAlpha(1.0f);
                }
            });
        }
        this.mRightOutAnimator.start();
    }

    private void cancelInOutAnimator() {
        ObjectAnimator objectAnimator = this.mTopInAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mTopInAnimator2;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
        ObjectAnimator objectAnimator3 = this.mTopInAnimator3;
        if (objectAnimator3 != null) {
            objectAnimator3.cancel();
        }
        ObjectAnimator objectAnimator4 = this.mTopInAnimatorClose;
        if (objectAnimator4 != null) {
            objectAnimator4.cancel();
        }
        ObjectAnimator objectAnimator5 = this.mLeftOutAnimator;
        if (objectAnimator5 != null) {
            objectAnimator5.cancel();
        }
        ObjectAnimator objectAnimator6 = this.mRightOutAnimator;
        if (objectAnimator6 != null) {
            objectAnimator6.cancel();
        }
        ObjectAnimator objectAnimator7 = this.mBottomOutAnimator;
        if (objectAnimator7 != null) {
            objectAnimator7.cancel();
        }
    }

    private void initTopView(View view) {
        this.mCloseButtonGroup = view.findViewById(R.id.dialog_close_group);
        this.mCloseButton = view.findViewById(R.id.dialog_close);
        View.OnClickListener onClickListener = this.mCloseClickListener;
        if (onClickListener != null) {
            this.mCloseButton.setOnClickListener(onClickListener);
        }
        this.mSoundFieldTips = (XTextView) view.findViewById(R.id.sound_field_tips);
        if (CarConfigHelper.hasHifiSound()) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void initLeftView(View view) {
        this.mEffectStyles = (ValuePickerView) view.findViewById(R.id.effect_styles);
        this.mEffectStyles.setShadowDirection(1);
        this.mEffectStyles.setAttachTextTypeface(getAttachedTypeFace());
        this.mEffectStyles.setContentTextTypeface(getIconTypeFace());
        if (CarConfigHelper.hasHifiSound()) {
            String[] stringArray = getContext().getResources().getStringArray(R.array.round_effect_styles_hifi_xp);
            String[] stringArray2 = getContext().getResources().getStringArray(R.array.round_effect_styles_hifi_xp_text);
            this.mEffectStyles.setMaxValue(stringArray.length - 1);
            this.mEffectStyles.setMinValue(0);
            this.mEffectStyles.setDisplayedValues(stringArray, stringArray2);
        } else {
            String[] stringArray3 = getContext().getResources().getStringArray(R.array.round_effect_styles);
            String[] stringArray4 = getContext().getResources().getStringArray(R.array.round_effect_text);
            this.mEffectStyles.setMaxValue(stringArray3.length - 1);
            this.mEffectStyles.setMinValue(0);
            this.mEffectStyles.setDisplayedValues(stringArray3, stringArray4);
        }
        this.mEffectStyles.setOnValueChangedListener(this.mValueChangeListener);
        this.mEffectStyles.setPivotX(0.0f);
        this.mEffectStyles.setPivotY(-100.0f);
        this.mEffectStyles.setRotationY(10.0f);
        if (CarConfigHelper.hasHifiSound()) {
            this.mPickStub = (ViewStub) view.findViewById(R.id.picker_stub);
            this.mEffectStylesHifi = (ValuePickerView) this.mPickStub.inflate().findViewById(R.id.effect_styles_hifi);
            this.mEffectStylesHifi.setShadowDirection(1);
            this.mEffectStylesHifi.setContentTextTypeface(getIconTypeFace());
            this.mEffectStylesHifi.setAttachTextTypeface(getAttachedTypeFace());
            this.mEffectStylesHifi.setMaxValue(getContext().getResources().getStringArray(R.array.hifi_effect_styles).length - 1);
            this.mEffectStylesHifi.setMinValue(0);
            this.mEffectStylesHifi.setOnValueChangedListener(this.mValueChangeListener);
            this.mEffectStylesHifi.setPivotX(0.0f);
            this.mEffectStylesHifi.setPivotY(-100.0f);
            this.mEffectStylesHifi.setRotationY(10.0f);
        }
        this.mEffectStyleMask = (ImageView) view.findViewById(R.id.effect_styles_mask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRightView(View view) {
        if (!CarFunction.isSupportDts() || CarConfigHelper.isLowSpeaker()) {
            return;
        }
        this.mEffectScenesMask = (ImageView) view.findViewById(R.id.effect_scenes_mask);
        this.mEffectScenes = (ValuePickerView) view.findViewById(R.id.effect_scenes);
        this.mEffectScenes.setShadowDirection(2);
        this.mEffectScenes.setContentTextTypeface(getAttachedTypeFace());
        String[] stringArray = getContext().getResources().getStringArray(R.array.effect_scenes);
        this.mEffectScenes.setMaxValue(stringArray.length - 1);
        this.mEffectScenes.setMinValue(0);
        this.mEffectScenes.setDisplayedValues(stringArray);
        this.mEffectScenes.setOnValueChangedListener(this.mValueChangeListener);
        this.mEffectScenes.setPivotX(257.0f);
        this.mEffectScenes.setPivotY(-100.0f);
        this.mEffectScenes.setRotationY(-10.0f);
        this.mEffectScenes.setVisibility(0);
        this.mEffectScenesMask.setVisibility(0);
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
        if (!CarConfigHelper.isLowSpeaker()) {
            this.mSceneStub = (ViewStub) view.findViewById(R.id.scene_stub);
            this.mSoundEffectAnimView = (SoundEffectAnimView) this.mSceneStub.inflate().findViewById(R.id.sound_field_scenes_anim);
        }
        this.mSoundFieldView = (SoundFieldView) view.findViewById(R.id.sound_field_view);
        this.mSoundFieldView.setRecommendationPoint(new Point(264, NfDef.GATT_STATUS_MORE), new Point(597, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(276, 410), new Point(569, 410));
        this.mSoundFieldView.setLimitArea(new Point(290, 62), new Point(50, 514), new Point(TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 62), new Point(850, 514));
        this.mSoundFieldView.setOnPositionChangeListener(this.mOnPositionChangeListener);
        this.mSoundFieldView.setIsAdsorption(false);
        this.mSoundFieldView.setVisibility(0);
        if (CarConfigHelper.hasHifiSound()) {
            this.mFieldStub = (ViewStub) view.findViewById(R.id.filed_stub);
            View inflate = this.mFieldStub.inflate();
            this.mSpreadStub = (ViewStub) view.findViewById(R.id.spread_stub);
            this.mMainDriverSpreadView = (SoundFieldSpreadView) this.mSpreadStub.inflate().findViewById(R.id.main_driver_spread_view);
            this.mSoundFieldViewHifiOriginal = (SoundFieldView) inflate.findViewById(R.id.sound_field_view_hifi_original);
            this.mSoundFieldViewHifiOriginal.setOnPositionChangeListener(this.mOnPositionChangeListener);
            this.mSoundFieldViewHifiOriginal.setLimitArea(new Point(290, 62), new Point(50, 514), new Point(TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 62), new Point(850, 514));
            this.mSoundFieldViewHifiOriginal.setRecommendationPoint(new Point(264, NfDef.GATT_STATUS_MORE), new Point(597, NfDef.GATT_STATUS_MORE), new Point(430, 219), new Point(276, 410), new Point(569, 410));
            this.mSoundFieldViewHifiOriginal.setIsAdsorption(true);
            this.mSoundFieldViewHifiOriginal.setVisibility(0);
        }
        if (CarConfigHelper.isLowSpeaker()) {
            Logs.d("xpsoundeffect isLowSpeaker");
            SoundFieldView soundFieldView = this.mSoundFieldView;
            if (soundFieldView != null) {
                soundFieldView.setDraggingHalf(true);
            }
            SoundFieldView soundFieldView2 = this.mSoundFieldViewHifiOriginal;
            if (soundFieldView2 != null) {
                soundFieldView2.setDraggingHalf(true);
            }
        }
    }

    private void refreshData() {
        if (this.mSoundManager == null) {
            this.mSoundManager = SoundManager.getInstance();
        }
        this.mCurrentEffect = this.mSoundManager.getSoundEffectMode();
        if (CarConfigHelper.hasHifiSound()) {
            this.mHifiType = this.mSoundManager.getSoundEffectType(3);
            this.mHifiSoundFieldValues = this.mSoundManager.getSoundField(3);
        }
        if (!CarConfigHelper.isLowSpeaker()) {
            this.mCurrentScenes = this.mSoundManager.getSoundEffectScene(1);
        }
        this.mRoundType = this.mSoundManager.getSoundEffectType(1);
        this.mRoundSoundFieldValues = this.mSoundManager.getSoundField(1);
        Logs.d("xpsoundeffect data mCurrentEffect:" + this.mCurrentEffect + " mRoundType:" + this.mRoundType + " mCurrentScenes:" + this.mCurrentScenes + " mRoundSoundFieldValues:" + this.mRoundSoundFieldValues + " mHifiType:" + this.mHifiType + " mHifiSoundFieldValues:" + this.mHifiSoundFieldValues);
        protectDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x000d, code lost:
        if (r8.mRoundType != 2) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void initLeft() {
        /*
            r8 = this;
            boolean r0 = com.xiaopeng.car.settingslibrary.common.CarConfigHelper.hasHifiSound()
            r1 = 3
            r2 = 1
            r3 = 2
            r4 = -1
            r5 = 0
            if (r0 == 0) goto L10
            int r0 = r8.mRoundType
            if (r0 == r3) goto L1f
            goto L21
        L10:
            int r0 = r8.mRoundType
            switch(r0) {
                case 0: goto L23;
                case 1: goto L21;
                case 2: goto L1f;
                case 3: goto L1d;
                case 4: goto L1b;
                case 5: goto L19;
                case 6: goto L17;
                default: goto L15;
            }
        L15:
            r0 = r4
            goto L24
        L17:
            r0 = 5
            goto L24
        L19:
            r0 = 4
            goto L24
        L1b:
            r0 = r1
            goto L24
        L1d:
            r0 = r3
            goto L24
        L1f:
            r0 = r2
            goto L24
        L21:
            r0 = r5
            goto L24
        L23:
            r0 = 6
        L24:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "音乐风格 index"
            r6.append(r7)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            r8.log(r6)
            com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView r6 = r8.mEffectStyles
            if (r6 == 0) goto L4b
            if (r0 == r4) goto L4b
            int r6 = r6.getMaxValue()
            if (r0 <= r6) goto L46
            r0 = r5
        L46:
            com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView r6 = r8.mEffectStyles
            r6.setValue(r0)
        L4b:
            int r0 = r8.mHifiType
            int r6 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_1_HIFI
            if (r0 != r6) goto L53
            r1 = r5
            goto L6b
        L53:
            int r0 = r8.mHifiType
            int r6 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_2_HIFI
            if (r0 != r6) goto L5b
            r1 = r2
            goto L6b
        L5b:
            int r0 = r8.mHifiType
            int r2 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_3_HIFI
            if (r0 != r2) goto L63
            r1 = r3
            goto L6b
        L63:
            int r0 = r8.mHifiType
            int r2 = com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues.SOUND_STYLE_4_HIFI
            if (r0 != r2) goto L6a
            goto L6b
        L6a:
            r1 = r4
        L6b:
            com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView r0 = r8.mEffectStylesHifi
            if (r0 == 0) goto L7d
            if (r1 == r4) goto L7d
            int r0 = r0.getMaxValue()
            if (r1 <= r0) goto L78
            r1 = r5
        L78:
            com.xiaopeng.car.settingslibrary.ui.widget.ValuePickerView r0 = r8.mEffectStylesHifi
            r0.setValue(r1)
        L7d:
            r8.updateLeftUI()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.initLeft():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRight() {
        int i = this.mCurrentScenes;
        int i2 = 3;
        if (i == 1) {
            i2 = 2;
        } else if (i == 2) {
            i2 = 1;
        } else if (i != 3) {
            i2 = i != 4 ? -1 : 0;
        }
        log("音乐场景 index" + i2);
        ValuePickerView valuePickerView = this.mEffectScenes;
        if (valuePickerView != null && i2 != -1) {
            this.mEffectScenes.setValue(i2 <= valuePickerView.getMaxValue() ? i2 : 0);
        }
        updateRightUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCenter() {
        if (CarConfigHelper.hasHifiSound()) {
            if (this.mRoundType == 2) {
                this.mSoundFieldView.setType(9);
            } else {
                this.mSoundFieldView.setType(0);
            }
        } else {
            switch (this.mRoundType) {
                case 0:
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
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView.16
                @Override // java.lang.Runnable
                public void run() {
                    SoundEffectPopHorizontalView.this.mSoundFieldView.setLocation(SoundEffectPopHorizontalView.this.mRoundSoundFieldValues.getXSound(), SoundEffectPopHorizontalView.this.mRoundSoundFieldValues.getYSound());
                }
            });
        }
        SoundFieldValues soundFieldValues2 = this.mHifiSoundFieldValues;
        if (soundFieldValues2 == null || soundFieldValues2.getType() != 3) {
            return;
        }
        this.mSoundFieldViewHifiOriginal.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalView$1joWy2RXH2cbtRk9RJRBqdcgr_U
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopHorizontalView.this.lambda$initCenter$0$SoundEffectPopHorizontalView();
            }
        });
    }

    public /* synthetic */ void lambda$initCenter$0$SoundEffectPopHorizontalView() {
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
            if (!CarConfigHelper.hasHifiSound()) {
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
        if (CarConfigHelper.hasHifiSound()) {
            this.mCurrentEffect = 1;
        } else {
            this.mCurrentEffect = 1;
        }
        this.mIsRestoreData = true;
    }

    private void restoreSoundField() {
        post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalView$bJrLVdCsYab9VcUGR8OFQjz7C7g
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopHorizontalView.this.lambda$restoreSoundField$1$SoundEffectPopHorizontalView();
            }
        });
    }

    public /* synthetic */ void lambda$restoreSoundField$1$SoundEffectPopHorizontalView() {
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
        if (CarConfigHelper.hasHifiSound()) {
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
        if (this.mSoundManager.isMainDriverVip()) {
            Logs.d("xpsound effect main driver vip");
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
        if (CarConfigHelper.hasHifiSound()) {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects_hifi));
        } else if (CarConfigHelper.isLowSpeaker()) {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundtrack1));
        } else {
            this.mSoundFieldArea.setBackground(getContext().getDrawable(R.drawable.pic_model_interior_soundeffects));
        }
        setVisible(this.mSoundEffectAnimView, true);
        this.mSoundFiledRoof.setImageDrawable(getContext().getDrawable(R.drawable.pic_model_roof));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScenesForCar(int i) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void effectScenesShow(boolean z) {
        if (z) {
            setVisible(this.mEffectScenesGroup, true);
            setVisible(this.mEffectScenes, true);
            setVisible(this.mEffectScenesMask, true);
            return;
        }
        setVisible(this.mEffectScenesGroup, true);
        setVisible(this.mEffectScenes, false);
        setVisible(this.mEffectScenesMask, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisible(View view, boolean z) {
        if (view != null) {
            view.setVisibility(z ? 0 : 4);
        }
    }

    private void updateLeftUI() {
        int i = this.mCurrentEffect;
        if (i == 1) {
            setVisible(this.mEffectStylesHifi, false);
            setVisible(this.mEffectStyles, true);
            setVisible(this.mEffectStyleMask, true);
        } else if (i == 3) {
            setVisible(this.mEffectStylesHifi, true);
            setVisible(this.mEffectStyles, false);
            setVisible(this.mEffectStyleMask, true);
        } else {
            setVisible(this.mEffectStylesHifi, false);
            setVisible(this.mEffectStyles, false);
            setVisible(this.mEffectStyleMask, false);
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
        if (this.mIntervalControl.isFrequently(800)) {
            return;
        }
        if (view.getId() == R.id.sound_effect_switch_btn) {
            int i = this.mCurrentEffect;
            if (i == 3) {
                this.mCurrentEffect = 1;
                this.mSoundManager.setSoundEffectMode(1, true);
            } else if (i == 1) {
                this.mCurrentEffect = 3;
                this.mSoundManager.setSoundEffectMode(3, true);
            }
        }
        startSwitchAnimator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightUI() {
        int i = this.mCurrentEffect;
        if (i == 1) {
            effectScenesShow(true);
        } else if (i != 3) {
        } else {
            effectScenesShow(false);
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
            return;
        }
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

    Typeface getIconTypeFace() {
        return Typeface.createFromAsset(getContext().getAssets(), "font/sound_effect_icon.ttf");
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
            if (this.mCurrentEffect == 3) {
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectStylesHifi, false);
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectStyles, true);
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectScenes, true);
            } else {
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectStylesHifi, true);
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectStyles, false);
                if (CarConfigHelper.isLowSpeaker()) {
                    VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectScenes, true);
                } else {
                    VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectScenes, false);
                }
            }
            if (this.mEffectSwitchBtn != null) {
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectSwitchBtn, false);
            } else {
                VuiManager.instance().setVuiElementUnSupport(this.mContext, this.mEffectSwitchBtn, true);
            }
            ValuePickerView valuePickerView = this.mEffectStyles;
            if (valuePickerView != null) {
                valuePickerView.setSceneId(this.mSceneId);
            }
            ValuePickerView valuePickerView2 = this.mEffectStylesHifi;
            if (valuePickerView2 != null) {
                valuePickerView2.setSceneId(this.mSceneId);
            }
            ValuePickerView valuePickerView3 = this.mEffectScenes;
            if (valuePickerView3 != null) {
                valuePickerView3.setSceneId(this.mSceneId);
            }
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendButtonDataLog(String str, int i) {
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
}
