package com.xiaopeng.car.settingslibrary.ui.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XRecyclerView;
/* loaded from: classes.dex */
public abstract class BaseListView extends XFrameLayout implements LifecycleOwner {
    public boolean mIsDialog;
    private final LifecycleRegistry mLifecycleRegistry;
    protected XRecyclerView mRecyclerView;
    public int mScreenId;
    private ThemeViewModel mUIThemeViewModel;
    public String sceneId;

    protected abstract void init(Context context, View view);

    public abstract void setSceneId(String str);

    protected abstract void start();

    protected abstract void stop();

    public BaseListView(Context context) {
        super(context);
        this.mIsDialog = true;
        this.mScreenId = 0;
        this.sceneId = null;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        initView(context);
    }

    public BaseListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDialog = true;
        this.mScreenId = 0;
        this.sceneId = null;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        initAttr(context, attributeSet);
        initView(context);
    }

    public BaseListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDialog = true;
        this.mScreenId = 0;
        this.sceneId = null;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        initAttr(context, attributeSet);
        initView(context);
    }

    public BaseListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsDialog = true;
        this.mScreenId = 0;
        this.sceneId = null;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        initAttr(context, attributeSet);
        initView(context);
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        this.mUIThemeViewModel = ThemeViewModel.create(context, attributeSet);
        this.mUIThemeViewModel.setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseListView$vNbTsgO5cRsJjsOHNY-_Se8GPoE
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public final void onThemeChanged() {
                BaseListView.this.lambda$initAttr$0$BaseListView();
            }
        });
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PopupDropdownSystemDialogType);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.PopupDropdownSystemDialogType_is_system_dialog) {
                this.mIsDialog = obtainStyledAttributes.getBoolean(index, false);
            }
        }
    }

    public /* synthetic */ void lambda$initAttr$0$BaseListView() {
        Logs.d("dialog view theme change");
        XRecyclerView xRecyclerView = this.mRecyclerView;
        if (xRecyclerView == null || xRecyclerView.getAdapter() == null) {
            return;
        }
        this.mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void onStart() {
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        start();
    }

    public void onStop() {
        stop();
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    protected int layoutId() {
        return R.layout.view_list;
    }

    protected void initView(Context context) {
        View inflate = LayoutInflater.from(getContext()).inflate(layoutId(), this);
        this.mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerView);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setItemViewCacheSize(0);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        init(context, inflate);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void paddingRecyclerView() {
        if (this.mIsDialog) {
            this.mRecyclerView.setPadding((int) getResources().getDimension(R.dimen.x_dialog_content_scrollbar_in_padding), 0, (int) getResources().getDimension(R.dimen.x_dialog_content_scrollbar_in_padding), 0);
        } else {
            this.mRecyclerView.setPadding(0, 0, (int) getResources().getDimension(R.dimen.scroll_bar_padding), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public View getHeaderOrFooterView(int i) {
        if (getContext() != null) {
            return LayoutInflater.from(getContext()).inflate(i, (ViewGroup) this.mRecyclerView, false);
        }
        return null;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public void updateVuiScene(String str, View... viewArr) {
        VuiManager.instance().updateVuiScene(str, getContext(), viewArr);
    }

    public int getScreenId() {
        return this.mScreenId;
    }

    public void setScreenId(int i) {
        this.mScreenId = i;
    }

    public XRecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
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
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ThemeViewModel themeViewModel = this.mUIThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, configuration);
        }
    }
}
