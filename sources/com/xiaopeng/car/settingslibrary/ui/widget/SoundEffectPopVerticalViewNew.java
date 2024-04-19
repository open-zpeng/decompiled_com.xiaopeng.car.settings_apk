package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.slider.XSlider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class SoundEffectPopVerticalViewNew extends XFrameLayout implements View.OnClickListener, ISoundServerListener, XSlider.SliderProgressListener {
    public static final int IN_OUT_TIMEOUT = 400;
    private static final int SOUND_EFFECT_FIELD_MSG_WHAT = 3000;
    private static final int Y_DELTA = 34;
    private View mBottomBg;
    public ObjectAnimator mBottomOutAnimator;
    public ObjectAnimator mCenterOutAnimator;
    private View mCloseButton;
    private View.OnClickListener mCloseClickListener;
    private View mEffectContentView;
    private View mEffectTipsLayout;
    private View mEffectTypeGroup;
    private List<View> mEffectTypeListView;
    private View mEqualizerContentView;
    private XButton mEqualizerRestoreDefaultBtn;
    private ArrayList<XSlider> mEqualizerSliders;
    private ArrayList<XTextView> mEqualizerTexts;
    private View mEqualizerView;
    private boolean mIsInit;
    private SoundFieldView.PositionChangeListener mOnPositionChangeListener;
    private String mSceneId;
    private XRelativeLayout mSoundFieldArea;
    private SoundFieldHandler mSoundFieldHandler;
    private TextView mSoundFieldTips;
    private SoundFieldView mSoundFieldView;
    private SoundManager mSoundManager;
    public ObjectAnimator mTopInAnimator;
    private ThemeViewModel mUIThemeViewModel;
    private ViewGroup mView;
    private TextView mXpTitle;

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStartTrackingTouch(XSlider xSlider) {
    }

    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
    public void onStopTrackingTouch(XSlider xSlider) {
    }

    public SoundEffectPopVerticalViewNew(Context context) {
        this(context, null);
    }

    public SoundEffectPopVerticalViewNew(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEffectTypeListView = new ArrayList();
        this.mEqualizerSliders = new ArrayList<>();
        this.mEqualizerTexts = new ArrayList<>();
        this.mOnPositionChangeListener = new SoundFieldView.PositionChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.6
            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPressChange(boolean z) {
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPositionChange(SoundFieldView soundFieldView, int i, int i2) {
                Logs.v("xpsoundeffect onPositionChange x:" + i + " y:" + i2 + " " + soundFieldView);
                if (soundFieldView == SoundEffectPopVerticalViewNew.this.mSoundFieldView) {
                    if (SoundEffectPopVerticalViewNew.this.mSoundFieldHandler == null) {
                        SoundEffectPopVerticalViewNew soundEffectPopVerticalViewNew = SoundEffectPopVerticalViewNew.this;
                        soundEffectPopVerticalViewNew.mSoundFieldHandler = new SoundFieldHandler(soundEffectPopVerticalViewNew);
                    }
                    Message obtainMessage = SoundEffectPopVerticalViewNew.this.mSoundFieldHandler.obtainMessage();
                    obtainMessage.what = 3000;
                    obtainMessage.arg1 = i;
                    obtainMessage.arg2 = i2;
                    SoundEffectPopVerticalViewNew.this.mSoundFieldHandler.removeMessages(3000);
                    SoundEffectPopVerticalViewNew.this.mSoundFieldHandler.sendMessageDelayed(obtainMessage, 500L);
                }
            }
        };
        this.mSceneId = null;
        this.mView = this;
        this.mUIThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mUIThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("xpsoundeffect vertical dialog onThemeChanged callback");
                if (SoundEffectPopVerticalViewNew.this.mBottomBg != null) {
                    SoundEffectPopVerticalViewNew.this.mBottomBg.setBackground(SoundEffectPopVerticalViewNew.this.getContext().getDrawable(R.drawable.pic_mask_under));
                }
                SoundEffectPopVerticalViewNew.this.initViewSoundEffect();
            }
        });
    }

    private void startInOutAnimator() {
        ObjectAnimator objectAnimator = this.mBottomOutAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
        ObjectAnimator objectAnimator2 = this.mCenterOutAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.reverse();
        }
    }

    private void reverseInOutAnimator() {
        if (this.mBottomOutAnimator == null) {
            this.mBottomOutAnimator = ObjectAnimator.ofFloat(this.mEffectTipsLayout, "alpha", 0.0f, 1.0f);
            this.mBottomOutAnimator.setDuration(400L);
            this.mBottomOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopVerticalViewNew.this.mEffectTipsLayout.setAlpha(1.0f);
                }
            });
        }
        this.mBottomOutAnimator.start();
        if (this.mCenterOutAnimator == null) {
            this.mCenterOutAnimator = ObjectAnimator.ofFloat(this.mEffectTypeGroup, "alpha", 0.0f, 1.0f);
            this.mCenterOutAnimator.setDuration(400L);
            this.mCenterOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopVerticalViewNew.this.mEffectTypeGroup.setAlpha(1.0f);
                }
            });
        }
        this.mCenterOutAnimator.start();
    }

    private void cancelInOutAnimator() {
        ObjectAnimator objectAnimator = this.mBottomOutAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mCenterOutAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
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
            post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopVerticalViewNew$I36TOvB8pWaczl3wwikGM7S7Tq8
                @Override // java.lang.Runnable
                public final void run() {
                    SoundEffectPopVerticalViewNew.this.lambda$onAttachedToWindow$0$SoundEffectPopVerticalViewNew();
                }
            });
        } else {
            log("onAttachedToWindow onStart");
            if (isInEqualizer()) {
                switchToEffect();
            } else {
                onStart();
                initVuiScene();
            }
        }
        lambda$onConfigurationChanged$1$SoundEffectPopVerticalViewNew();
        SoundServerManager.getInstance().addSoundUIListener(this);
    }

    public /* synthetic */ void lambda$onAttachedToWindow$0$SoundEffectPopVerticalViewNew() {
        log("onAttachedToWindow init");
        LayoutInflater.from(this.mView.getContext()).inflate(R.layout.sound_effects, this.mView);
        initView(this.mView);
        init();
        onStart();
        initVuiScene();
    }

    public void log(String str) {
        Logs.d("sound-effect-port" + str);
    }

    protected void initView(View view) {
        this.mEffectTypeGroup = view.findViewById(R.id.sound_effect_types);
        this.mEffectContentView = view.findViewById(R.id.soundeffect_content);
        this.mEqualizerContentView = view.findViewById(R.id.soundeffect_equalizer_content);
        this.mCloseButton = view.findViewById(R.id.dialog_close);
        this.mXpTitle = (TextView) view.findViewById(R.id.sound_effect_title);
        View.OnClickListener onClickListener = this.mCloseClickListener;
        if (onClickListener != null) {
            this.mCloseButton.setOnClickListener(onClickListener);
        }
        this.mEffectTypeListView.add(view.findViewById(R.id.effect1));
        this.mEffectTypeListView.add(view.findViewById(R.id.effect2));
        this.mEffectTypeListView.add(view.findViewById(R.id.effect3));
        this.mEffectTypeListView.add(view.findViewById(R.id.effect4));
        View findViewById = view.findViewById(R.id.effect5);
        if (CarFunction.isSoundEffectHighSpeaker()) {
            findViewById.setVisibility(0);
            this.mEffectTypeListView.add(findViewById);
        } else {
            findViewById.setVisibility(8);
        }
        this.mEffectTypeListView.add(view.findViewById(R.id.effect6));
        initViewSoundEffect();
        this.mBottomBg = view.findViewById(R.id.bottom_bg);
        this.mEffectTipsLayout = view.findViewById(R.id.effect_tips_layout);
        this.mSoundFieldTips = (TextView) view.findViewById(R.id.sound_field_tips);
        this.mSoundFieldView = (SoundFieldView) view.findViewById(R.id.sound_field_view);
        this.mSoundFieldView.initPointCount(CarFunction.isMainPsnSharedSoundField() ? 3 : 5);
        this.mSoundFieldView.setLimitArea(new Point(290, 48), new Point(70, 480), new Point(TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 48), new Point(830, 480));
        this.mSoundFieldView.setRecommendationPoint(new Point(264, NfDef.GATT_STATUS_MORE), new Point(598, NfDef.GATT_STATUS_MORE), new Point(429, 232), new Point(276, 393), new Point(573, 393));
        this.mSoundFieldView.setOnPositionChangeListener(this.mOnPositionChangeListener);
        this.mSoundFieldView.setIsAdsorption(false);
        if (CarFunction.isSoundEffectLowSpeaker()) {
            this.mSoundFieldView.setDraggingHalf(true);
        }
        this.mSoundFieldArea = (XRelativeLayout) view.findViewById(R.id.sound_field_area);
        lambda$onConfigurationChanged$1$SoundEffectPopVerticalViewNew();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: soundFieldAreaBackground */
    public void lambda$onConfigurationChanged$1$SoundEffectPopVerticalViewNew() {
        XRelativeLayout xRelativeLayout;
        if (!CarFunction.isVehicleD55A() || (xRelativeLayout = this.mSoundFieldArea) == null) {
            return;
        }
        xRelativeLayout.setBackgroundResource(R.drawable.pic_model_interior_d55a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initViewSoundEffect() {
        for (View view : this.mEffectTypeListView) {
            view.setOnClickListener(this);
            TextView textView = (TextView) view;
            int id = view.getId();
            if (id == R.id.effect1) {
                textView.setText(R.string.sound_effect1_text);
            } else if (id == R.id.effect2) {
                textView.setText(R.string.sound_effect2_text);
            } else if (id == R.id.effect3) {
                textView.setText(R.string.sound_effect3_text);
            } else if (id == R.id.effect4) {
                textView.setText(R.string.sound_effect4_text);
            } else if (id == R.id.effect5) {
                textView.setText(R.string.sound_effect5_text);
            } else if (id == R.id.effect6) {
                textView.setText(R.string.sound_custom_text);
            }
        }
    }

    protected void init() {
        this.mSoundManager = SoundManager.getInstance();
    }

    public void onStart() {
        long currentTimeMillis = System.currentTimeMillis();
        refreshSoundEffectMode();
        refreshEffectRound();
        log("onStart:" + (System.currentTimeMillis() - currentTimeMillis));
        for (View view : this.mEffectTypeListView) {
            view.setEnabled(true);
        }
    }

    private void refreshSoundEffectMode() {
        if (this.mSoundManager.getSoundEffectMode() != 1) {
            this.mSoundManager.setSoundEffectMode(1, true);
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style1), true);
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.4
                @Override // java.lang.Runnable
                public void run() {
                    Point realDefaultPosition = SoundEffectPopVerticalViewNew.this.mSoundFieldView.getRealDefaultPosition();
                    SoundEffectPopVerticalViewNew.this.mSoundManager.setSoundField(1, realDefaultPosition.x, realDefaultPosition.y, true);
                }
            });
        }
    }

    private void refreshEffectRound() {
        int soundEffectType = this.mSoundManager.getSoundEffectType(1);
        if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style1)) {
            this.mSoundFieldView.setType(0);
            selectEffectTypeView(R.id.effect1);
        } else if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style3)) {
            this.mSoundFieldView.setType(9);
            selectEffectTypeView(R.id.effect3);
        } else if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style4)) {
            this.mSoundFieldView.setType(10);
            selectEffectTypeView(R.id.effect4);
        } else if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style2)) {
            this.mSoundFieldView.setType(1);
            selectEffectTypeView(R.id.effect2);
        } else if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style5)) {
            this.mSoundFieldView.setType(11);
            selectEffectTypeView(R.id.effect5);
        } else if (soundEffectType == this.mContext.getResources().getInteger(R.integer.sound_effect_style6)) {
            this.mSoundFieldView.setType(4);
            selectEffectTypeView(R.id.effect6);
        }
        final SoundFieldValues soundField = this.mSoundManager.getSoundField(1);
        Logs.d("xpsound effect type:" + soundField.getType() + " x:" + soundField.getXSound() + " y:" + soundField.getYSound());
        if (soundField.getType() == 1) {
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalViewNew.5
                @Override // java.lang.Runnable
                public void run() {
                    SoundEffectPopVerticalViewNew.this.mSoundFieldView.setLocation(soundField.getXSound(), soundField.getYSound());
                }
            });
        }
    }

    private void selectEffectTypeView(int i) {
        for (View view : this.mEffectTypeListView) {
            if (view.getId() == i) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
            if (view.getId() == R.id.effect6) {
                refreshCustomBtnVui((XButton) view, view.isSelected());
            }
        }
    }

    private void setEffectType(View view) {
        int id = view.getId();
        if (id == R.id.effect1) {
            this.mSoundFieldView.setType(0);
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style1), true);
            sendButtonDataLog("B003", 0);
        } else if (id == R.id.effect2) {
            this.mSoundFieldView.setType(1);
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style2), true);
            sendButtonDataLog("B003", 3);
        } else if (id == R.id.effect3) {
            if (this.mContext.getResources().getBoolean(R.bool.sound_effect_style_redirect)) {
                this.mSoundFieldView.setType(2);
            } else {
                this.mSoundFieldView.setType(9);
            }
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style3), true);
            sendButtonDataLog("B003", 1);
        } else if (id == R.id.effect4) {
            if (this.mContext.getResources().getBoolean(R.bool.sound_effect_style_redirect)) {
                this.mSoundFieldView.setType(3);
            } else {
                this.mSoundFieldView.setType(10);
            }
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style4), true);
            sendButtonDataLog("B003", 2);
        } else if (id == R.id.effect5) {
            this.mSoundFieldView.setType(11);
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style5), true);
            sendButtonDataLog("B003", 4);
        } else if (id == R.id.effect6) {
            if (view.isSelected()) {
                switchToEqualizer();
                return;
            }
            this.mSoundFieldView.setType(4);
            this.mSoundManager.setSoundEffectType(1, this.mContext.getResources().getInteger(R.integer.sound_effect_style6), true);
        }
    }

    /* loaded from: classes.dex */
    static class SoundFieldHandler extends Handler {
        private final WeakReference<SoundEffectPopVerticalViewNew> effectView;

        public SoundFieldHandler(SoundEffectPopVerticalViewNew soundEffectPopVerticalViewNew) {
            this.effectView = new WeakReference<>(soundEffectPopVerticalViewNew);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (this.effectView.get() != null && message.what == 3000) {
                SoundManager.getInstance().setSoundField(1, message.arg1, message.arg2, true);
            }
        }
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
        SoundServerManager.getInstance().removeSoundUIListener(this);
    }

    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
        postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopVerticalViewNew$QYevCngBvttsFGYxYRjuB51WuO0
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopVerticalViewNew.this.lambda$onConfigurationChanged$1$SoundEffectPopVerticalViewNew();
            }
        }, 100L);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.sound_effect_equalizer_restore_default) {
            restoreEqualizer();
            saveEqualizerValueSp("");
            this.mSoundManager.flushXpCustomizeEffects(new int[9]);
            return;
        }
        setEffectType(view);
        for (View view2 : this.mEffectTypeListView) {
            view2.setSelected(false);
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, view2);
        }
        view.setSelected(true);
        selectEffectTypeView(view.getId());
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, view);
        enableTimeDown(view);
    }

    private void enableTimeDown(View view) {
        for (View view2 : this.mEffectTypeListView) {
            if (view2 != view) {
                view2.setEnabled(false);
            }
        }
        view.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopVerticalViewNew$IkSyuLex1lPy61oFihpJ9Zzg2d0
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopVerticalViewNew.this.lambda$enableTimeDown$2$SoundEffectPopVerticalViewNew();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$enableTimeDown$2$SoundEffectPopVerticalViewNew() {
        for (View view : this.mEffectTypeListView) {
            view.setEnabled(true);
        }
    }

    public void setCloseClickListener(View.OnClickListener onClickListener) {
        this.mCloseClickListener = onClickListener;
        View view = this.mCloseButton;
        if (view != null) {
            view.setOnClickListener(this.mCloseClickListener);
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
        if (this.mSoundFieldView == null) {
            log("no inflate complete!");
            return;
        }
        log("onReceive refreshdata ");
        refreshSoundEffectMode();
        refreshEffectRound();
        initVuiScene();
    }

    public void setSceneId(String str) {
        this.mSceneId = str;
    }

    private void initVuiScene() {
        if (this.mSceneId != null) {
            for (View view : this.mEffectTypeListView) {
                if (view instanceof VuiView) {
                    int id = view.getId();
                    if (id == R.id.effect1) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_effect1_text));
                    } else if (id == R.id.effect2) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_effect2_text));
                    } else if (id == R.id.effect3) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_effect3_text));
                    } else if (id == R.id.effect4) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_effect4_text));
                    } else if (id == R.id.effect5) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_effect5_text));
                    } else if (id == R.id.effect6) {
                        ((VuiView) view).setVuiLabel(this.mContext.getString(R.string.sound_custom_text));
                    }
                }
            }
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mView);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void switchToEqualizer() {
        if (this.mEqualizerView == null) {
            this.mEqualizerView = ((ViewStub) findViewById(R.id.sound_effect_equalizer_mid_stub)).inflate();
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
            this.mEqualizerRestoreDefaultBtn = (XButton) findViewById(R.id.sound_effect_equalizer_restore_default);
            this.mEqualizerRestoreDefaultBtn.setOnClickListener(this);
        }
        this.mEffectContentView.setVisibility(8);
        this.mEqualizerContentView.setVisibility(0);
        this.mXpTitle.setText(getContext().getString(R.string.sound_effects_equalizer_title));
        ((XImageButton) this.mCloseButton).setImageResource(R.drawable.x_ic_small_back);
        ((XImageButton) this.mCloseButton).setVuiLabel(getContext().getString(R.string.sound_effects_back));
        refreshEqualizerValues(1, false);
        initVuiScene();
    }

    private void refreshEqualizerValues(int i, boolean z) {
        int[] convertEqualizerValues = this.mSoundManager.convertEqualizerValues(1, i);
        Iterator<XSlider> it = this.mEqualizerSliders.iterator();
        boolean z2 = true;
        int i2 = 0;
        while (it.hasNext()) {
            XSlider next = it.next();
            next.setCurrentIndex(convertEqualizerValues[i2]);
            this.mEqualizerTexts.get(this.mEqualizerSliders.indexOf(next)).setText(String.valueOf(convertEqualizerValues[i2]));
            if (convertEqualizerValues[i2] != 0) {
                z2 = false;
            }
            i2++;
        }
        this.mEqualizerRestoreDefaultBtn.setEnabled(!z2);
        if (z) {
            this.mSoundManager.flushXpCustomizeEffects(convertEqualizerValues);
        }
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mEqualizerRestoreDefaultBtn);
    }

    private void refreshCustomBtnVui(XButton xButton, boolean z) {
        Context context;
        int i;
        if (z) {
            xButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getContext().getDrawable(R.drawable.ic_soundeffect_equalizer_icon), (Drawable) null);
        } else {
            xButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
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
        Logs.d("sound effect equalizer mid save:" + str);
        this.mSoundManager.saveEqualizerValues(1, 1, str);
    }

    public boolean isInEqualizer() {
        return this.mEqualizerContentView.getVisibility() == 0;
    }

    public void switchToEffect() {
        this.mEffectContentView.setVisibility(0);
        this.mEqualizerContentView.setVisibility(8);
        ((XImageButton) this.mCloseButton).setImageResource(R.drawable.x_ic_small_close);
        ((XImageButton) this.mCloseButton).setVuiLabel(getContext().getString(R.string.sound_effects_close));
        this.mXpTitle.setText(getContext().getString(R.string.sound_effects_xp_effects));
        initVuiScene();
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
}
