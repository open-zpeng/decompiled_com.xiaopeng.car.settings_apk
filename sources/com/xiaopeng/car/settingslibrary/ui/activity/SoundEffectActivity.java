package com.xiaopeng.car.settingslibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.widget.SoundEffectPopHorizontalViewNew;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XTitleBar;
/* loaded from: classes.dex */
public class SoundEffectActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sound_effect_activity);
        XTitleBar xTitleBar = (XTitleBar) findViewById(R.id.xTitleBar);
        xTitleBar.setCloseVisibility(4);
        xTitleBar.setTitle(getString(R.string.sound_effects_xp_effects));
        xTitleBar.setOnTitleBarClickListener(new XTitleBar.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.SoundEffectActivity.1
            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarActionClick(View view, int i) {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
            }

            @Override // com.xiaopeng.xui.widget.XTitleBar.OnTitleBarClickListener
            public void onTitleBarBackClick() {
                SoundEffectActivity.this.finish();
            }
        });
        XFrameLayout xFrameLayout = (XFrameLayout) findViewById(R.id.soundEffectPopView);
        if (xFrameLayout instanceof SoundEffectPopHorizontalViewNew) {
            final SoundEffectPopHorizontalViewNew soundEffectPopHorizontalViewNew = (SoundEffectPopHorizontalViewNew) xFrameLayout;
            soundEffectPopHorizontalViewNew.setCloseClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$SoundEffectActivity$wLf2_uUA-30cfJ_bQ4TZLc2SbJU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundEffectActivity.this.lambda$onCreate$0$SoundEffectActivity(soundEffectPopHorizontalViewNew, view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onCreate$0$SoundEffectActivity(SoundEffectPopHorizontalViewNew soundEffectPopHorizontalViewNew, View view) {
        if (soundEffectPopHorizontalViewNew.isInEqualizer()) {
            soundEffectPopHorizontalViewNew.switchToEffect();
        } else {
            finish();
        }
    }
}
