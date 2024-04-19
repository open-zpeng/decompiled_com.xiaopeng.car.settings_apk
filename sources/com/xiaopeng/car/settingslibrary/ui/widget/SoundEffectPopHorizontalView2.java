package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener;
import com.xiaopeng.car.settingslibrary.interfaceui.SoundServerManager;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundEffectEqualizer;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView;
import com.xiaopeng.car.settingslibrary.ui.widget.EqualizerProgressView;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectBackgroundPositionView;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalView2;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundFieldValues;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SoundEffectPopHorizontalView2 extends XFrameLayout implements SoundEffectBackgroundPositionView.IOnSoundEffectPositionViewTouchStateListener, EqualizerProgressView.IEqualizerProgressChangeListener, ISoundServerListener {
    private SoundEffectBackgroundPositionView mBackgroundView;
    private View mEqualizer;
    private EqualizerProgressView mEqualizerProgressView;
    private AdaptivePickerView mLeftView;
    private View mMain;
    private boolean mMainDriveVipMode;
    private String mSceneId;
    private SoundManager mSoundManager;
    private int[] mXSoundTypes;

    public SoundEffectPopHorizontalView2(Context context) {
        super(context);
        this.mSoundManager = SoundManager.getInstance();
        this.mXSoundTypes = CarConfigHelper.hasAMP() ? new int[]{1, 2, 3, 4} : new int[]{1, 2, 3};
    }

    public SoundEffectPopHorizontalView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSoundManager = SoundManager.getInstance();
        this.mXSoundTypes = CarConfigHelper.hasAMP() ? new int[]{1, 2, 3, 4} : new int[]{1, 2, 3};
    }

    public SoundEffectPopHorizontalView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSoundManager = SoundManager.getInstance();
        this.mXSoundTypes = CarConfigHelper.hasAMP() ? new int[]{1, 2, 3, 4} : new int[]{1, 2, 3};
    }

    public void init(String str) {
        this.mSceneId = str;
        if (isAttachedToWindow()) {
            return;
        }
        reload();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        reload();
        SoundServerManager.getInstance().addSoundUIListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mMain.setVisibility(0);
        this.mEqualizer.setVisibility(4);
        SoundServerManager.getInstance().removeSoundUIListener(this);
    }

    private void reload() {
        this.mMainDriveVipMode = this.mSoundManager.isMainDriverVip();
        this.mMain = findViewById(R.id.page_main);
        this.mEqualizer = findViewById(R.id.page_equalizer);
        initMain();
        initEqualizer();
        VuiManager.instance().updateVuiScene(this.mSceneId, getContext(), this);
    }

    private void initMain() {
        initMainBackground();
        initMainLeft();
        initMainBottom();
    }

    public void setCloseClickListener(View.OnClickListener onClickListener) {
        findViewById(R.id.close).setOnClickListener(onClickListener);
    }

    private void initMainBackground() {
        this.mBackgroundView = (SoundEffectBackgroundPositionView) this.mMain.findViewById(R.id.position_view);
        this.mBackgroundView.setTouchStateChangeListener(this);
        this.mBackgroundView.setPositionChangeListener(new XSoundEffectFieldPositionChangeListener());
        this.mBackgroundView.setEditable(!this.mMainDriveVipMode);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectBackgroundPositionView.IOnSoundEffectPositionViewTouchStateListener
    public void onSoundEffectPositionTouchStateChange(SoundEffectBackgroundPositionView soundEffectBackgroundPositionView, boolean z) {
        View findViewById = this.mMain.findViewById(R.id.function_area);
        View findViewById2 = this.mMain.findViewById(R.id.sound_field_tips);
        if (z) {
            findViewById2.setVisibility(0);
            findViewById.setVisibility(4);
            return;
        }
        findViewById2.setVisibility(4);
        findViewById.setVisibility(0);
    }

    public void changeXSoundEffectToSoft() {
        if (this.mMainDriveVipMode) {
            SoundEffectBackgroundPositionView.Env env = new SoundEffectBackgroundPositionView.Env();
            env.setType(0);
            env.setBackgroundResId(R.drawable.se_bg_md_soft);
            env.setSpeakerResId(-1);
            env.setMarkerResId(R.drawable.se_marker_md_soft);
            env.setMarkerOutlineResId(R.drawable.se_marker_outline_md_soft);
            env.setMarkerOutlineOffsetY(6);
            env.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44))});
            this.mBackgroundView.setEnv(env);
            return;
        }
        SoundEffectBackgroundPositionView.Env env2 = new SoundEffectBackgroundPositionView.Env();
        env2.setType(0);
        env2.setBackgroundResId(R.drawable.se_bg_soft);
        env2.setSpeakerResId(R.drawable.se_speakers_soft);
        env2.setMarkerResId(R.drawable.se_marker_soft);
        env2.setMarkerOutlineResId(R.drawable.se_marker_outline_soft);
        env2.setMarkerOutlineOffsetY(6);
        env2.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44), Config.mUnityRealRecommendationPoint[0]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(167, 44), Config.mUnityRealRecommendationPoint[1]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -29), Config.mUnityRealRecommendationPoint[2]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -240), Config.mUnityRealRecommendationPoint[3])});
        SoundFieldValues soundField = SoundManager.getInstance().getSoundField(1);
        this.mBackgroundView.setEnv(env2, soundField.getXSound(), soundField.getYSound());
    }

    public void changeXSoundEffectToDynamic() {
        if (this.mMainDriveVipMode) {
            SoundEffectBackgroundPositionView.Env env = new SoundEffectBackgroundPositionView.Env();
            env.setType(0);
            env.setBackgroundResId(R.drawable.se_bg_md_dynamic);
            env.setSpeakerResId(-1);
            env.setMarkerResId(R.drawable.se_marker_md_dynamic);
            env.setMarkerOutlineResId(R.drawable.se_marker_outline_md_dynamic);
            env.setMarkerOutlineOffsetY(6);
            env.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44))});
            this.mBackgroundView.setEnv(env);
            return;
        }
        SoundEffectBackgroundPositionView.Env env2 = new SoundEffectBackgroundPositionView.Env();
        env2.setType(0);
        env2.setBackgroundResId(R.drawable.se_bg_dynamic);
        env2.setSpeakerResId(R.drawable.se_speakers_dynamic);
        env2.setMarkerResId(R.drawable.se_marker_dynamic);
        env2.setMarkerOutlineResId(R.drawable.se_marker_outline_dynamic);
        env2.setMarkerOutlineOffsetY(6);
        env2.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44), Config.mUnityRealRecommendationPoint[0]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(167, 44), Config.mUnityRealRecommendationPoint[1]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -29), Config.mUnityRealRecommendationPoint[2]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -240), Config.mUnityRealRecommendationPoint[3])});
        SoundFieldValues soundField = SoundManager.getInstance().getSoundField(1);
        this.mBackgroundView.setEnv(env2, soundField.getXSound(), soundField.getYSound());
    }

    public void changeXSoundEffectToOrign() {
        if (this.mMainDriveVipMode) {
            SoundEffectBackgroundPositionView.Env env = new SoundEffectBackgroundPositionView.Env();
            env.setType(0);
            env.setBackgroundResId(R.drawable.se_bg_md_original);
            env.setSpeakerResId(-1);
            env.setMarkerResId(R.drawable.se_marker_md_original);
            env.setMarkerOutlineResId(R.drawable.se_marker_outline_md_original);
            env.setMarkerOutlineOffsetY(6);
            env.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44))});
            this.mBackgroundView.setEnv(env);
            return;
        }
        SoundEffectBackgroundPositionView.Env env2 = new SoundEffectBackgroundPositionView.Env();
        env2.setType(0);
        env2.setBackgroundResId(R.drawable.se_bg_original);
        env2.setSpeakerResId(R.drawable.se_speakers_original);
        env2.setMarkerResId(R.drawable.se_marker_original);
        env2.setMarkerOutlineResId(R.drawable.se_marker_outline_original);
        env2.setMarkerOutlineOffsetY(6);
        env2.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44), Config.mUnityRealRecommendationPoint[0]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(167, 44), Config.mUnityRealRecommendationPoint[1]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -29), Config.mUnityRealRecommendationPoint[2]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -240), Config.mUnityRealRecommendationPoint[3])});
        SoundFieldValues soundField = SoundManager.getInstance().getSoundField(1);
        this.mBackgroundView.setEnv(env2, soundField.getXSound(), soundField.getYSound());
    }

    public void changeXSoundEffectToCustom() {
        if (this.mMainDriveVipMode) {
            SoundEffectBackgroundPositionView.Env env = new SoundEffectBackgroundPositionView.Env();
            env.setType(0);
            env.setBackgroundResId(R.drawable.se_bg_md_custom);
            env.setSpeakerResId(-1);
            env.setMarkerResId(R.drawable.se_marker_md_custom);
            env.setMarkerOutlineResId(R.drawable.se_marker_outline_md_custom);
            env.setMarkerOutlineOffsetY(6);
            env.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44))});
            this.mBackgroundView.setEnv(env);
            return;
        }
        SoundEffectBackgroundPositionView.Env env2 = new SoundEffectBackgroundPositionView.Env();
        env2.setType(0);
        env2.setBackgroundResId(R.drawable.se_bg_custom);
        env2.setSpeakerResId(R.drawable.se_speakers_custom);
        env2.setMarkerResId(R.drawable.se_marker_custom);
        env2.setMarkerOutlineResId(R.drawable.se_marker_outline_custom);
        env2.setMarkerOutlineOffsetY(6);
        env2.setMarkerAnchorCoordinates(new SoundEffectBackgroundPositionView.SoundCoordinateInfo[]{new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(-167, 44), Config.mUnityRealRecommendationPoint[0]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(167, 44), Config.mUnityRealRecommendationPoint[1]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -29), Config.mUnityRealRecommendationPoint[2]), new SoundEffectBackgroundPositionView.SoundCoordinateInfo(new Point(0, -240), Config.mUnityRealRecommendationPoint[3])});
        SoundFieldValues soundField = SoundManager.getInstance().getSoundField(1);
        this.mBackgroundView.setEnv(env2, soundField.getXSound(), soundField.getYSound());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class XSoundEffectFieldPositionChangeListener implements SoundEffectBackgroundPositionView.IOnSoundEffectPositionViewChangeListener {
        XSoundEffectFieldPositionChangeListener() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectBackgroundPositionView.IOnSoundEffectPositionViewChangeListener
        public void onSoundEffectPositionSelectedChange(SoundEffectBackgroundPositionView soundEffectBackgroundPositionView, SoundEffectBackgroundPositionView.SoundCoordinateInfo soundCoordinateInfo) {
            Logs.v("xpsoundeffect onPositionChange x:" + soundCoordinateInfo.audioX + " y:" + soundCoordinateInfo.audioY + " " + soundEffectBackgroundPositionView);
            if (SoundEffectPopHorizontalView2.this.mSoundManager.isMainDriverVip()) {
                return;
            }
            SoundManager.getInstance().setSoundField(1, soundCoordinateInfo.audioX, soundCoordinateInfo.audioY, true);
        }
    }

    private void initMainLeft() {
        this.mLeftView = (AdaptivePickerView) this.mMain.findViewById(R.id.picker_view_left);
        this.mLeftView.setListener(new XSoundItemChangeListener());
        this.mLeftView.setAdapter(new XSoundTypeAdapter());
        int soundEffectType = SoundManager.getInstance().getSoundEffectType(1);
        int i = 0;
        while (true) {
            int[] iArr = this.mXSoundTypes;
            if (i >= iArr.length) {
                return;
            }
            if (iArr[i] == soundEffectType) {
                this.mLeftView.setSelectIndex(i);
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class XSoundTypeAdapter extends AdaptivePickerView.Adapter {
        XSoundTypeAdapter() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView.Adapter
        public int getSize() {
            return SoundEffectPopHorizontalView2.this.mXSoundTypes.length;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView.Adapter
        public AdaptivePickerView.ItemHolder onNewItem(AdaptivePickerView adaptivePickerView) {
            return new AdaptivePickerView.ItemHolder(adaptivePickerView, R.layout.sound_popup_effects_v2_main_left_item);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView.Adapter
        public void onItemUpdated(final AdaptivePickerView adaptivePickerView, final AdaptivePickerView.ItemHolder itemHolder) {
            XImageView xImageView = (XImageView) itemHolder.findViewById(R.id.image);
            XTextView xTextView = (XTextView) itemHolder.findViewById(R.id.text1);
            XTextView xTextView2 = (XTextView) itemHolder.findViewById(R.id.text2);
            View findViewById = itemHolder.findViewById(R.id.btn_equalizer);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalView2$XSoundTypeAdapter$s74tytLHrnLtkju8CjJ8_FIlm-w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundEffectPopHorizontalView2.XSoundTypeAdapter.this.lambda$onItemUpdated$0$SoundEffectPopHorizontalView2$XSoundTypeAdapter(itemHolder, adaptivePickerView, view);
                }
            });
            int i = SoundEffectPopHorizontalView2.this.mXSoundTypes[itemHolder.getIndex()];
            if (i == 1) {
                xTextView.setText(R.string.se_type_title_original);
                xImageView.setImageResource(R.drawable.se_type_logo_orign);
                findViewById.setVisibility(8);
            } else if (i == 2) {
                xTextView.setText(R.string.se_type_title_dynamic);
                xImageView.setImageResource(R.drawable.se_type_logo_dynamic);
                findViewById.setVisibility(8);
            } else if (i == 3) {
                xTextView.setText(R.string.se_type_title_soft);
                xImageView.setImageResource(R.drawable.se_type_logo_soft);
                findViewById.setVisibility(8);
            } else if (i == 4) {
                xTextView.setText(R.string.se_type_title_custom);
                xImageView.setImageResource(R.drawable.se_type_logo_custom);
                findViewById.setVisibility(0);
            }
            xTextView2.setText(xTextView.getText());
            ((VuiView) itemHolder.mView).setVuiLabel(xTextView.getText().toString());
        }

        public /* synthetic */ void lambda$onItemUpdated$0$SoundEffectPopHorizontalView2$XSoundTypeAdapter(AdaptivePickerView.ItemHolder itemHolder, AdaptivePickerView adaptivePickerView, View view) {
            if (itemHolder.isCenterItem()) {
                SoundEffectPopHorizontalView2.this.mMain.setVisibility(4);
                SoundEffectPopHorizontalView2.this.mEqualizer.setVisibility(0);
                return;
            }
            adaptivePickerView.scrollToIndex(itemHolder.getRealIndex(), 200);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView.Adapter
        public void onUpdateTransform(AdaptivePickerView adaptivePickerView, AdaptivePickerView.ItemHolder itemHolder) {
            float abs = Math.abs(itemHolder.getLayoutParams().mY);
            float f = itemHolder.getLayoutParams().mHeight;
            float f2 = 0.5f;
            if (abs <= f) {
                float f3 = 1.0f - (abs / f);
                f2 = 0.5f + (0.5f * f3 * f3);
            }
            itemHolder.mView.setAlpha(f2);
            itemHolder.getLayoutParams().mY = (int) (itemHolder.getLayoutParams().mY * 0.8f);
        }
    }

    /* loaded from: classes.dex */
    public class XSoundItemChangeListener implements AdaptivePickerView.ISelectedItemChangeListener {
        public XSoundItemChangeListener() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.widget.AdaptivePickerView.ISelectedItemChangeListener
        public void onPickerViewItemChanged(AdaptivePickerView adaptivePickerView, int i, int i2, boolean z) {
            int i3 = SoundEffectPopHorizontalView2.this.mXSoundTypes[i];
            if (i3 == 1) {
                SoundEffectPopHorizontalView2.this.changeXSoundEffectToOrign();
                if (z) {
                    SoundManager.getInstance().setSoundEffectType(1, 1, z);
                    BuriedPointUtils.sendButtonDataLog("P00005", "B001", "type", 10);
                }
            } else if (i3 == 2) {
                SoundEffectPopHorizontalView2.this.changeXSoundEffectToDynamic();
                if (z) {
                    SoundManager.getInstance().setSoundEffectType(1, 2, z);
                    BuriedPointUtils.sendButtonDataLog("P00005", "B001", "type", 2);
                }
            } else if (i3 == 3) {
                SoundEffectPopHorizontalView2.this.changeXSoundEffectToSoft();
                if (z) {
                    SoundManager.getInstance().setSoundEffectType(1, 3, z);
                    BuriedPointUtils.sendButtonDataLog("P00005", "B001", "type", 1);
                }
            } else if (i3 != 4) {
            } else {
                SoundEffectPopHorizontalView2.this.changeXSoundEffectToCustom();
                if (z) {
                    SoundManager.getInstance().setSoundEffectType(1, 4, z);
                    BuriedPointUtils.sendButtonDataLog("P00005", "B001", "type", 1);
                }
            }
        }
    }

    private void initMainBottom() {
        View findViewById = this.mMain.findViewById(R.id.bottom_effect_high);
        View findViewById2 = this.mMain.findViewById(R.id.bottom_effect_low);
        if (CarConfigHelper.hasAMP()) {
            findViewById.setVisibility(0);
            findViewById2.setVisibility(8);
            return;
        }
        findViewById.setVisibility(8);
        findViewById2.setVisibility(0);
    }

    private void initEqualizer() {
        View findViewById = this.mEqualizer.findViewById(R.id.back);
        View findViewById2 = this.mEqualizer.findViewById(R.id.reset);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalView2$zqmA-AAQ2EfkOEJlcUisUfmSwuw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SoundEffectPopHorizontalView2.this.lambda$initEqualizer$0$SoundEffectPopHorizontalView2(view);
            }
        });
        this.mEqualizerProgressView = (EqualizerProgressView) this.mEqualizer.findViewById(R.id.equalizer_progress_view);
        this.mEqualizerProgressView.setListener(this);
        this.mEqualizerProgressView.setProgress(0, this.mSoundManager.getXpCustomizeEffect(SoundEffectEqualizer.SUBWOOFER.getType()), true);
        this.mEqualizerProgressView.setProgress(1, this.mSoundManager.getXpCustomizeEffect(SoundEffectEqualizer.BASS.getType()), true);
        this.mEqualizerProgressView.setProgress(2, this.mSoundManager.getXpCustomizeEffect(SoundEffectEqualizer.ALTO.getType()), true);
        this.mEqualizerProgressView.setProgress(3, this.mSoundManager.getXpCustomizeEffect(SoundEffectEqualizer.HIGHPITCH.getType()), true);
        this.mEqualizerProgressView.submitChange();
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$SoundEffectPopHorizontalView2$dEg_K2VSacb7zlwP7DRk92IIi6Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SoundEffectPopHorizontalView2.this.lambda$initEqualizer$1$SoundEffectPopHorizontalView2(view);
            }
        });
    }

    public /* synthetic */ void lambda$initEqualizer$0$SoundEffectPopHorizontalView2(View view) {
        this.mMain.setVisibility(0);
        this.mEqualizer.setVisibility(4);
    }

    public /* synthetic */ void lambda$initEqualizer$1$SoundEffectPopHorizontalView2(View view) {
        this.mEqualizerProgressView.setProgress(0, 0, true, true);
        this.mEqualizerProgressView.setProgress(1, 0, true, true);
        this.mEqualizerProgressView.setProgress(2, 0, true, true);
        this.mEqualizerProgressView.setProgress(3, 0, true, true);
        this.mEqualizerProgressView.submitChange();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.widget.EqualizerProgressView.IEqualizerProgressChangeListener
    public void onEqualizerChange(int i, int i2, boolean z) {
        if (i == 0) {
            ((XTextView) this.mEqualizer.findViewById(R.id.title0)).setText("" + i2);
            if (z) {
                this.mSoundManager.setXpCustomizeEffect(SoundEffectEqualizer.SUBWOOFER.getType(), i2);
            }
        } else if (i == 1) {
            ((XTextView) this.mEqualizer.findViewById(R.id.title1)).setText("" + i2);
            if (z) {
                this.mSoundManager.setXpCustomizeEffect(SoundEffectEqualizer.BASS.getType(), i2);
            }
        } else if (i == 2) {
            ((XTextView) this.mEqualizer.findViewById(R.id.title2)).setText("" + i2);
            if (z) {
                this.mSoundManager.setXpCustomizeEffect(SoundEffectEqualizer.ALTO.getType(), i2);
            }
        } else if (i != 3) {
        } else {
            ((XTextView) this.mEqualizer.findViewById(R.id.title3)).setText("" + i2);
            if (z) {
                this.mSoundManager.setXpCustomizeEffect(SoundEffectEqualizer.HIGHPITCH.getType(), i2);
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onSoundEffectChange(boolean z) {
        if (z) {
            return;
        }
        reload();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ISoundServerListener
    public void onHeadRestModeChange() {
        reload();
    }
}
