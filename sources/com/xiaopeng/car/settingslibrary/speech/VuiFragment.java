package com.xiaopeng.car.settingslibrary.speech;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.xiaopeng.car.settingslibrary.ui.base._LifeFragment;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.List;
import org.json.JSONObject;
/* loaded from: classes.dex */
public abstract class VuiFragment extends _LifeFragment implements IVuiSceneListener {
    private static final String TAG = "VuiFragment";
    private boolean mLastVuiEnable;
    public View mRootView;
    public boolean isWholeScene = true;
    public String sceneId = null;
    public List<View> mBuildViewList = null;
    public String fatherId = null;
    public List<String> mSubSceneList = null;
    public boolean mIsDialog = false;
    public Handler mVuiHandler = null;

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mLastVuiEnable = VuiEngine.getInstance(getContext()).isVuiFeatureDisabled();
        this.mVuiHandler = VuiManager.instance().getVuiHandler();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume:" + this.sceneId);
        if (!this.mIsDialog) {
            VuiManager.instance().enterVuiScene(this.sceneId, this.isWholeScene, getContext());
        }
        boolean isVuiFeatureDisabled = VuiEngine.getInstance(getContext()).isVuiFeatureDisabled();
        if (isVuiFeatureDisabled != this.mLastVuiEnable) {
            this.mLastVuiEnable = isVuiFeatureDisabled;
            onVuiEnableChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVuiEnableChanged() {
        Log.d(TAG, "onVuiEnableChanged:" + this.sceneId);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause:" + this.sceneId);
        if (this.mIsDialog) {
            return;
        }
        VuiManager.instance().exitVuiScene(this.sceneId, this.isWholeScene, getContext());
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop:" + this.sceneId);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mIsDialog) {
            return;
        }
        VuiManager.instance().destroyVuiScene(this.sceneId, getContext(), this);
        List<View> list = this.mBuildViewList;
        if (list != null) {
            list.clear();
        }
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

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        buildVuiScene();
    }

    public void setWholeScene(boolean z) {
        this.isWholeScene = z;
    }

    public void setSceneId(String str) {
        this.sceneId = str;
    }

    public void setSubSceneList(List<String> list) {
        this.mSubSceneList = list;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public boolean isVuiAction(View view) {
        if (view instanceof IVuiElement) {
            IVuiElement iVuiElement = (IVuiElement) view;
            boolean isPerformVuiAction = iVuiElement.isPerformVuiAction();
            if (isPerformVuiAction) {
                iVuiElement.setPerformVuiAction(false);
            }
            return isPerformVuiAction;
        }
        return false;
    }

    public void setFatherId(String str) {
        this.fatherId = str;
    }

    public void isDialog(boolean z) {
        this.mIsDialog = z;
    }

    public void createVuiScene() {
        if (this.mIsDialog) {
            return;
        }
        VuiManager.instance().createVuiScene(this.sceneId, this.mRootView, this, getContext(), this.fatherId);
    }

    public void updateVuiScene(String str, View... viewArr) {
        VuiManager.instance().updateVuiScene(str, getContext(), viewArr);
    }

    public void updateVuiScene(String str, List<View> list) {
        VuiManager.instance().updateVuiScene(str, getContext(), list);
    }

    public void buildVuiScene() {
        VuiManager.instance().onBuildScene(this.sceneId, this.mRootView, this.mBuildViewList, this.mSubSceneList, getContext(), this.isWholeScene);
    }

    public void setVuiElementUnSupport(View view, boolean z) {
        VuiManager.instance().setVuiElementUnSupport(getContext(), view, z);
    }

    public void setVuiElementVisible(View view, boolean z) {
        VuiManager.instance().setVuiElementVisible(getContext(), view, z);
    }

    public Boolean getVuiElementVisible(View view) {
        return VuiManager.instance().getVuiElementVisible(getContext(), view);
    }

    public void vuiFeedback(View view, int i, String str) {
        VuiEngine.getInstance(getContext()).vuiFeedback(view, new VuiFeedback.Builder().state(i).content(str).build());
    }

    public void supportFeedback(VuiView vuiView) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(VuiConstants.PROPS_FEEDBACK, true);
            vuiView.setVuiProps(jSONObject);
        } catch (Exception unused) {
        }
    }

    public void vuiChangeTarget(VuiView vuiView, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("defaultExecElementId", str);
            vuiView.setVuiProps(jSONObject);
        } catch (Exception unused) {
        }
    }
}
