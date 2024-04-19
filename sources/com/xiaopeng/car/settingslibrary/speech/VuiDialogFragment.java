package com.xiaopeng.car.settingslibrary.speech;

import android.os.Bundle;
import android.view.View;
import com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
/* loaded from: classes.dex */
public abstract class VuiDialogFragment extends BaseDialogFragment implements IVuiSceneListener {
    public String mSceneId = null;
    public XDialogView mDialog = null;
    public boolean isWholeScene = true;
    public View mRootView = null;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mSceneId != null) {
            if (this.mDialog != null) {
                if (this.isWholeScene) {
                    VuiManager.instance().initVuiDialog(this.mDialog, getContext(), this.mSceneId);
                    return;
                } else {
                    VuiManager.instance().createVuiScene(this.mSceneId, this.mRootView, this, getContext(), null);
                    return;
                }
            }
            VuiManager.instance().createVuiScene(this.mSceneId, this.mRootView, this, getContext(), null);
        }
    }

    public void destroyVuiDialogFragment() {
        if (this.mSceneId == null || this.mDialog != null) {
            return;
        }
        VuiManager.instance().destroyVuiScene(this.mSceneId, getContext(), this);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseDialogFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isWholeScene && this.mDialog == null) {
            VuiManager.instance().enterVuiScene(this.mSceneId, this.isWholeScene, getContext());
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeDialogFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.isWholeScene && this.mDialog == null) {
            VuiManager.instance().exitVuiScene(this.mSceneId, this.isWholeScene, getContext());
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        VuiManager.instance().onBuildScene(this.mSceneId, this.mRootView, null, null, getContext(), this.isWholeScene);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view == null) {
            return;
        }
        VuiFloatingLayerManager.show(view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            return false;
        }
        return false;
    }
}
