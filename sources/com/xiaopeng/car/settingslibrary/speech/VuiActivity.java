package com.xiaopeng.car.settingslibrary.speech;

import android.util.Log;
import android.view.View;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
/* loaded from: classes.dex */
public abstract class VuiActivity extends BaseActivity implements IVuiSceneListener {
    public String mSceneId = null;
    public boolean isWholeScene = true;
    public View mRootView = null;

    public void createVuiScene() {
        if (this.mSceneId != null) {
            VuiManager.instance().createVuiScene(this.mSceneId, this.mRootView, this, this, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        destroyVuiScene();
        System.gc();
    }

    public void destroyVuiScene() {
        if (this.mSceneId != null) {
            VuiManager.instance().destroyVuiScene(this.mSceneId, this, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isWholeScene) {
            VuiManager.instance().enterVuiScene(this.mSceneId, this.isWholeScene, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (this.isWholeScene) {
            VuiManager.instance().exitVuiScene(this.mSceneId, this.isWholeScene, this);
        }
        VuiFloatingLayerManager.hide(0);
        VuiFloatingLayerManager.hide(1);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        VuiManager.instance().onBuildScene(this.mSceneId, this.mRootView, null, null, this, this.isWholeScene);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d("VuiActivity", "onVuiEvent");
        if (view == null) {
            return;
        }
        VuiFloatingLayerManager.show(view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d("VuiActivity", "onInterceptVuiEvent");
        if (view == null || view.getId() == R.id.x_dialog_close) {
            return false;
        }
        VuiFloatingLayerManager.show(view);
        return false;
    }
}
