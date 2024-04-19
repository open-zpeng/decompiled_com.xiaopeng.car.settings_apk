package com.xiaopeng.car.settingslibrary.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.xiaopeng.xui.widget.XFrameLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class SoundEffectPopVerticalView extends XFrameLayout implements View.OnClickListener, ISoundServerListener {
    public static final int IN_OUT_TIMEOUT = 400;
    private static final int SOUND_EFFECT_FIELD_MSG_WHAT = 3000;
    private static final int Y_DELTA = 34;
    private View mBottomBg;
    public ObjectAnimator mBottomOutAnimator;
    public ObjectAnimator mCenterOutAnimator;
    private View mCloseButton;
    private View.OnClickListener mCloseClickListener;
    private View mEffectTipsLayout;
    private View mEffectTypeGroup;
    private List<View> mEffectTypeListView;
    private boolean mIsInit;
    private SoundFieldView.PositionChangeListener mOnPositionChangeListener;
    private String mSceneId;
    private SoundFieldHandler mSoundFieldHandler;
    private TextView mSoundFieldTips;
    private SoundFieldView mSoundFieldView;
    private SoundManager mSoundManager;
    public ObjectAnimator mTopInAnimator;
    private ThemeViewModel mUIThemeViewModel;
    private ViewGroup mView;

    public SoundEffectPopVerticalView(Context context) {
        this(context, null);
    }

    public SoundEffectPopVerticalView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEffectTypeListView = new ArrayList();
        this.mOnPositionChangeListener = new SoundFieldView.PositionChangeListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.7
            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPositionChange(SoundFieldView soundFieldView, int i, int i2) {
                Logs.v("xpsoundeffect onPositionChange x:" + i + " y:" + i2 + " " + soundFieldView);
                if (soundFieldView == SoundEffectPopVerticalView.this.mSoundFieldView) {
                    if (SoundEffectPopVerticalView.this.mSoundFieldHandler == null) {
                        SoundEffectPopVerticalView soundEffectPopVerticalView = SoundEffectPopVerticalView.this;
                        soundEffectPopVerticalView.mSoundFieldHandler = new SoundFieldHandler(soundEffectPopVerticalView);
                    }
                    Message obtainMessage = SoundEffectPopVerticalView.this.mSoundFieldHandler.obtainMessage();
                    obtainMessage.what = 3000;
                    obtainMessage.arg1 = i;
                    obtainMessage.arg2 = i2;
                    SoundEffectPopVerticalView.this.mSoundFieldHandler.removeMessages(3000);
                    SoundEffectPopVerticalView.this.mSoundFieldHandler.sendMessageDelayed(obtainMessage, 500L);
                }
            }

            @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundFieldView.PositionChangeListener
            public void onPressChange(boolean z) {
                if (z) {
                    SoundEffectPopVerticalView.this.mEffectTipsLayout.setVisibility(4);
                    SoundEffectPopVerticalView.this.mSoundFieldTips.setVisibility(0);
                    SoundEffectPopVerticalView.this.mEffectTypeGroup.setVisibility(8);
                    SoundEffectPopVerticalView.this.startInOutAnimator();
                    return;
                }
                SoundEffectPopVerticalView.this.mEffectTipsLayout.setVisibility(0);
                SoundEffectPopVerticalView.this.mSoundFieldTips.setVisibility(8);
                SoundEffectPopVerticalView.this.mEffectTypeGroup.setVisibility(0);
                SoundEffectPopVerticalView.this.reverseInOutAnimator();
            }
        };
        this.mSceneId = null;
        this.mView = this;
        this.mUIThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mUIThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.1
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public void onThemeChanged() {
                Logs.d("xpsoundeffect vertical dialog onThemeChanged callback");
                if (SoundEffectPopVerticalView.this.mBottomBg != null) {
                    SoundEffectPopVerticalView.this.mBottomBg.setBackground(SoundEffectPopVerticalView.this.getContext().getDrawable(R.drawable.pic_mask_under));
                }
                SoundEffectPopVerticalView.this.initViewSoundEffect();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInOutAnimator() {
        if (this.mTopInAnimator == null) {
            this.mTopInAnimator = ObjectAnimator.ofFloat(this.mSoundFieldTips, "alpha", 0.0f, 1.0f);
            this.mTopInAnimator.setDuration(400L);
            this.mTopInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopVerticalView.this.mSoundFieldTips.setAlpha(1.0f);
                }
            });
        }
        this.mTopInAnimator.start();
        ObjectAnimator objectAnimator = this.mBottomOutAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
        ObjectAnimator objectAnimator2 = this.mCenterOutAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.reverse();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reverseInOutAnimator() {
        ObjectAnimator objectAnimator = this.mTopInAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
        if (this.mBottomOutAnimator == null) {
            this.mBottomOutAnimator = ObjectAnimator.ofFloat(this.mEffectTipsLayout, "alpha", 0.0f, 1.0f);
            this.mBottomOutAnimator.setDuration(400L);
            this.mBottomOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopVerticalView.this.mEffectTipsLayout.setAlpha(1.0f);
                }
            });
        }
        this.mBottomOutAnimator.start();
        if (this.mCenterOutAnimator == null) {
            this.mCenterOutAnimator = ObjectAnimator.ofFloat(this.mEffectTypeGroup, "alpha", 0.0f, 1.0f);
            this.mCenterOutAnimator.setDuration(400L);
            this.mCenterOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    SoundEffectPopVerticalView.this.mEffectTypeGroup.setAlpha(1.0f);
                }
            });
        }
        this.mCenterOutAnimator.start();
    }

    private void cancelInOutAnimator() {
        ObjectAnimator objectAnimator = this.mTopInAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mBottomOutAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
        ObjectAnimator objectAnimator3 = this.mCenterOutAnimator;
        if (objectAnimator3 != null) {
            objectAnimator3.cancel();
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
            post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopVerticalView$4ke948pEzRm-ZYCPpft_VEwbLJg
                @Override // java.lang.Runnable
                public final void run() {
                    SoundEffectPopVerticalView.this.lambda$onAttachedToWindow$0$SoundEffectPopVerticalView();
                }
            });
        } else {
            log("onAttachedToWindow onStart");
            onStart();
            initVuiScene();
        }
        SoundServerManager.getInstance().addSoundUIListener(this);
    }

    public /* synthetic */ void lambda$onAttachedToWindow$0$SoundEffectPopVerticalView() {
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
        this.mCloseButton = view.findViewById(R.id.dialog_close);
        View.OnClickListener onClickListener = this.mCloseClickListener;
        if (onClickListener != null) {
            this.mCloseButton.setOnClickListener(onClickListener);
        }
        this.mEffectTypeListView.add(view.findViewById(R.id.effect1));
        this.mEffectTypeListView.add(view.findViewById(R.id.effect2));
        this.mEffectTypeListView.add(view.findViewById(R.id.effect3));
        View findViewById = view.findViewById(R.id.effect4);
        if (CarFunction.isSoundEffectHighSpeaker()) {
            findViewById.setVisibility(0);
            this.mEffectTypeListView.add(findViewById);
        } else {
            findViewById.setVisibility(8);
        }
        this.mEffectTypeListView.add(view.findViewById(R.id.effect5));
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initViewSoundEffect() {
        for (View view : this.mEffectTypeListView) {
            view.setOnClickListener(this);
            ImageView imageView = (ImageView) view.findViewById(R.id.sound_effect_image);
            TextView textView = (TextView) view.findViewById(R.id.sound_effect_text);
            int id = view.getId();
            if (id == R.id.effect1) {
                imageView.setImageDrawable(getContext().getDrawable(R.drawable.d_sound_effect_1_btn));
                textView.setText(R.string.sound_effect1_text);
            } else if (id == R.id.effect2) {
                imageView.setImageDrawable(getContext().getDrawable(R.drawable.d_sound_effect_2_btn));
                textView.setText(R.string.sound_effect2_text);
            } else if (id == R.id.effect3) {
                imageView.setImageDrawable(getContext().getDrawable(R.drawable.d_sound_effect_3_btn));
                textView.setText(R.string.sound_effect3_text);
            } else if (id == R.id.effect4) {
                imageView.setImageDrawable(getContext().getDrawable(R.drawable.d_sound_effect_4_btn));
                textView.setText(R.string.sound_effect4_text);
            } else if (id == R.id.effect5) {
                imageView.setImageDrawable(getContext().getDrawable(R.drawable.d_sound_effect_5_btn));
                textView.setText(R.string.sound_effect5_text);
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
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.5
                @Override // java.lang.Runnable
                public void run() {
                    Point realDefaultPosition = SoundEffectPopVerticalView.this.mSoundFieldView.getRealDefaultPosition();
                    SoundEffectPopVerticalView.this.mSoundManager.setSoundField(1, realDefaultPosition.x, realDefaultPosition.y, true);
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
        }
        final SoundFieldValues soundField = this.mSoundManager.getSoundField(1);
        Logs.d("xpsound effect type:" + soundField.getType() + " x:" + soundField.getXSound() + " y:" + soundField.getYSound());
        if (soundField.getType() == 1) {
            this.mSoundFieldView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopVerticalView.6
                @Override // java.lang.Runnable
                public void run() {
                    SoundEffectPopVerticalView.this.mSoundFieldView.setLocation(soundField.getXSound(), soundField.getYSound());
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
        }
    }

    /* loaded from: classes.dex */
    static class SoundFieldHandler extends Handler {
        private final WeakReference<SoundEffectPopVerticalView> effectView;

        public SoundFieldHandler(SoundEffectPopVerticalView soundEffectPopVerticalView) {
            this.effectView = new WeakReference<>(soundEffectPopVerticalView);
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
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        for (View view2 : this.mEffectTypeListView) {
            view2.setSelected(false);
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, view2);
        }
        view.setSelected(true);
        setEffectType(view);
        VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, view);
        enableTimeDown(view);
    }

    private void enableTimeDown(View view) {
        for (View view2 : this.mEffectTypeListView) {
            if (view2 != view) {
                view2.setEnabled(false);
            }
        }
        view.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopVerticalView$kUsC7vFf0XEtz-fKbPC_c3p3Nqg
            @Override // java.lang.Runnable
            public final void run() {
                SoundEffectPopVerticalView.this.lambda$enableTimeDown$1$SoundEffectPopVerticalView();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$enableTimeDown$1$SoundEffectPopVerticalView() {
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
                    }
                }
            }
            VuiManager.instance().updateVuiScene(this.mSceneId, this.mContext, this.mView);
        }
    }
}
