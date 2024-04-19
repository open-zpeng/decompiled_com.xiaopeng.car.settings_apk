package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.AboutSystemsFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.DataPrivacyFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.LaboratoryFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.OldDataPrivacyFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.SoundFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.WlanFragment;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.theme.XThemeManager;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class TabFragment extends BaseListFragment {
    public static ArrayList<TabInfo> sTabInfo = new ArrayList<>();
    private Adapter mAdapter;
    private TabInfo mDefaultInfo;

    static {
        sTabInfo.add(new TabInfo(R.string.title_bluetooth, R.drawable.ic_tab_bluetooth, BluetoothFragment.class, VuiManager.SCENE_BLUETOOTH));
        sTabInfo.add(new TabInfo(R.string.title_wifi, R.drawable.ic_tab_wlan, WlanFragment.class, VuiManager.SCENE_WIFI));
        sTabInfo.add(new TabInfo(R.string.title_sound, R.drawable.ic_tab_sound, SoundFragment.class, VuiManager.SCENE_SOUND));
        sTabInfo.add(new TabInfo(R.string.title_display, R.drawable.ic_tab_display, DisplayFragment.class, VuiManager.SCENE_DISPLAY));
        if (CarFunction.isSupportXpengLaboratory()) {
            sTabInfo.add(new TabInfo(R.string.title_laboratory, R.drawable.ic_tab_xplaboratory, LaboratoryFragment.class, VuiManager.SCENE_LABORATORY));
        }
        if (CarFunction.isSupportFeedback()) {
            sTabInfo.add(new TabInfo(R.string.title_feedback, R.drawable.ic_tab_feedback, FeedbackFragment.class, VuiManager.SCENE_FEEDBACK));
        }
        if (CarFunction.isSupportAboutSystem()) {
            sTabInfo.add(new TabInfo(R.string.title_about_system, R.drawable.ic_tab_about, AboutSystemsFragment.class, VuiManager.SCENE_ABOUT_SYSTEM));
        }
        if (CarFunction.isSupportPrivacyPolicy()) {
            if (CarFunction.isSupportNewPrivacyProtocol()) {
                sTabInfo.add(new TabInfo(R.string.title_about, R.drawable.ic_tab_about, DataPrivacyFragment.class, VuiManager.SCENE_PRIVACY));
            } else {
                sTabInfo.add(new TabInfo(R.string.title_protocol, R.drawable.ic_tab_privacy, OldDataPrivacyFragment.class, VuiManager.SCENE_PRIVACY));
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListFragment, com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.tab_fragment_list;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        initVuiBase();
        this.mAdapter = new Adapter();
        this.mAdapter.lambda$setData$1$BaseAdapter(sTabInfo);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setItemViewCacheSize(0);
        StringBuilder sb = new StringBuilder();
        sb.append("xpsettings-main tab init ");
        TabInfo tabInfo = this.mDefaultInfo;
        sb.append(tabInfo == null ? " info is null " : tabInfo.fragmentClass.getSimpleName());
        Logs.d(sb.toString());
        if (this.mDefaultInfo == null) {
            this.mDefaultInfo = sTabInfo.get(0);
        }
        this.mAdapter.onItemClick((BaseAdapter) null, (BaseAdapter.ViewHolder) null, this.mDefaultInfo);
        Logs.d("TabFragment::init, orientation: " + getResources().getConfiguration().orientation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void changePage(String str) {
        TabInfo tabInfo = getTabInfo(str);
        if (tabInfo != null) {
            Adapter adapter = this.mAdapter;
            if (adapter != null) {
                adapter.onItemClick((BaseAdapter) null, (BaseAdapter.ViewHolder) null, tabInfo);
            } else {
                this.mDefaultInfo = tabInfo;
            }
        }
    }

    public TabInfo getTabInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (int i = 0; i < sTabInfo.size(); i++) {
            TabInfo tabInfo = sTabInfo.get(i);
            if (tabInfo.getFragmentClass().getName().equals(str)) {
                return tabInfo;
            }
        }
        return null;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Adapter adapter;
        super.onConfigurationChanged(configuration);
        Logs.d("TabFragment::onConfigurationChanged, orientation: " + getResources().getConfiguration().orientation);
        if (!XThemeManager.isThemeChanged(configuration) || (adapter = this.mAdapter) == null) {
            return;
        }
        adapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Adapter extends BaseAdapter<TabInfo> implements BaseAdapter.OnItemClickListener<TabInfo> {
        private int checkedPosition;

        Adapter() {
            setOnItemClickListener(this);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.tab_recycler_item;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return super.onCreateViewHolder(viewGroup, i);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            TextView textView = (TextView) viewHolder.getView(R.id.text1);
            textView.setText(TabFragment.this.getString(getItem(i).getNameRes()));
            if (i == this.checkedPosition) {
                textView.setSelected(true);
            } else {
                textView.setSelected(false);
            }
            if (TabFragment.this.getResources().getConfiguration().orientation == 1) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, getItem(i).iconRes, 0, 0);
            } else {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(getItem(i).iconRes, 0, 0, 0);
            }
            textView.setTag(Integer.valueOf(i));
            textView.setTextColor(textView.getContext().getResources().getColorStateList(R.color.x_catalog_bar_text_color_selector, textView.getContext().getTheme()));
            textView.setBackground(TabFragment.this.getResources().getDrawable(R.drawable.x_catalog_bar_bg_selector, textView.getContext().getTheme()));
            TabFragment.this.initVui(textView, i);
        }

        private void setChecked(int i) {
            int i2 = this.checkedPosition;
            if (i2 == i) {
                return;
            }
            lambda$refreshItem$10$BaseAdapter(i2);
            lambda$refreshItem$10$BaseAdapter(i);
            this.checkedPosition = i;
            TabFragment.this.mRecyclerView.scrollToPosition(this.checkedPosition);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
        public void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, TabInfo tabInfo) {
            int indexOf = getData().indexOf(tabInfo);
            if (TabFragment.this.mFragmentController != null) {
                BaseFragment.FragmentController fragmentController = TabFragment.this.mFragmentController;
                Class fragmentClass = tabInfo.getFragmentClass();
                String sceneId = tabInfo.getSceneId();
                if (fragmentController.launchFragment(fragmentClass, sceneId, TabFragment.this.getContext().getResources().getIdentifier("text1", VuiConstants.ELEMENT_ID, TabFragment.this.getContext().getPackageName()) + "_" + indexOf)) {
                    setChecked(indexOf);
                    TabFragment.this.mFragmentController.changeTitle(TabFragment.this.getString(tabInfo.nameRes));
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class TabInfo {
        private Class fragmentClass;
        private int iconRes;
        private int nameRes;
        private String sceneId;

        TabInfo(int i, int i2, Class cls, String str) {
            this.nameRes = i;
            this.iconRes = i2;
            this.fragmentClass = cls;
            this.sceneId = str;
        }

        int getNameRes() {
            return this.nameRes;
        }

        int getIconRes() {
            return this.iconRes;
        }

        public String getSceneId() {
            return this.sceneId;
        }

        public Class getFragmentClass() {
            return this.fragmentClass;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initVui(View view, int i) {
        if (view instanceof IVuiElement) {
            ((IVuiElement) view).setVuiElementId(view.getId() + "_" + i);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiListFragment
    public void vuiUpdateRecyclerViewScene() {
        super.vuiUpdateRecyclerViewScene();
        updateVuiScene(VuiManager.SCENE_TAB, this.mRecyclerView);
    }
}
