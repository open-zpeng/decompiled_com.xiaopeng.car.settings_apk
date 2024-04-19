package com.xiaopeng.car.settingslibrary.speech;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.activity.TabFragment;
import com.xiaopeng.car.settingslibrary.ui.base.BaseActivity;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes.dex */
public abstract class VuiMultiSceneActivity extends BaseActivity implements IVuiSceneListener {
    public String mCurrentRightVisibleFragment;
    public String mSceneId;
    public String[] subScenes = null;
    public View mRootView = null;

    public abstract void initVui();

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view == null) {
            return;
        }
        VuiFloatingLayerManager.show(view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        VuiFloatingLayerManager.show(view);
        return false;
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        VuiManager.instance().onBuildScene(this.mSceneId, this.mRootView, null, null, this, false);
    }

    public void createVuiScene() {
        VuiManager.instance().createVuiScene(VuiManager.SCENE_TITLE_BAR, this.mRootView, this, this, null);
    }

    public void destroyVuiScene() {
        VuiManager.instance().destroyVuiScene(VuiManager.SCENE_TITLE_BAR, this, this);
    }

    public void initVuiFragmentScene(BaseFragment baseFragment, String str, boolean z, List<String> list, String str2) {
        VuiManager.instance().initVuiScene(baseFragment, str, z, list, str2, false);
    }

    public void updateVuiFragmentInfo(List<Fragment> list, TabFragment tabFragment) {
        if (list != null) {
            Logs.d("updateVuiFragmentInfo fragments " + list);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof BaseFragment) {
                    BaseFragment baseFragment = (BaseFragment) list.get(i);
                    if (baseFragment instanceof TabFragment) {
                        initVuiFragmentScene(baseFragment, VuiManager.SCENE_TAB, false, null, null);
                    } else {
                        TabFragment.TabInfo tabInfo = (baseFragment == null || tabFragment == null) ? null : tabFragment.getTabInfo(baseFragment.getClass().getName());
                        initVuiFragmentScene(baseFragment, tabInfo != null ? tabInfo.getSceneId() : null, true, Arrays.asList(this.subScenes), null);
                    }
                }
            }
        }
    }
}
