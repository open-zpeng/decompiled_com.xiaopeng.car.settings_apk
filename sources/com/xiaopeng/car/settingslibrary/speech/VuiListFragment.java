package com.xiaopeng.car.settingslibrary.speech;

import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.adapterDataObserver.RecycleViewAdapterDataObserver;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.vui.commons.IVuiElement;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class VuiListFragment extends BaseFragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public BaseAdapter mAdapter;
    public RecyclerView mRecyclerView;
    public RecycleViewAdapterDataObserver mAdapterDataObserver = null;
    private VuiRecyclerViewScrollListener mScrollListener = null;
    private List<View> mHeaderViewList = null;
    private View mHeaderView = null;
    private List<View> mFooterViewList = null;
    private View mFooterView = null;

    public void vuiUpdateRecyclerViewScene() {
    }

    public void initVuiView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mRootView = this.mRecyclerView;
    }

    public void initVuiBase() {
        if (this.mBuildViewList == null) {
            this.mBuildViewList = new ArrayList();
        }
        this.mAdapterDataObserver = new RecycleViewAdapterDataObserver();
        this.mScrollListener = new VuiRecyclerViewScrollListener(this);
        this.mRecyclerView.addOnScrollListener(this.mScrollListener);
        this.mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.mBuildViewList.add(this.mRecyclerView);
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        BaseAdapter baseAdapter;
        super.onPause();
        RecycleViewAdapterDataObserver recycleViewAdapterDataObserver = this.mAdapterDataObserver;
        if (recycleViewAdapterDataObserver == null || (baseAdapter = this.mAdapter) == null) {
            return;
        }
        baseAdapter.unregisterAdapterDataObserver(recycleViewAdapterDataObserver);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        BaseAdapter baseAdapter;
        super.onResume();
        RecycleViewAdapterDataObserver recycleViewAdapterDataObserver = this.mAdapterDataObserver;
        if (recycleViewAdapterDataObserver == null || (baseAdapter = this.mAdapter) == null) {
            return;
        }
        baseAdapter.registerAdapterDataObserver(recycleViewAdapterDataObserver);
        updateVuiScene(this.sceneId, this.mRecyclerView);
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        VuiRecyclerViewScrollListener vuiRecyclerViewScrollListener = this.mScrollListener;
        if (vuiRecyclerViewScrollListener != null) {
            vuiRecyclerViewScrollListener.onDestroy();
        }
        if (this.mAdapterDataObserver != null) {
            this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
            this.mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            this.mAdapterDataObserver = null;
        }
    }

    public void intVuiHeaderFooter(List<View> list, View view, boolean z) {
        if (list == null || view == null) {
            return;
        }
        if (z) {
            this.mHeaderViewList = list;
            this.mHeaderView = view;
        } else {
            this.mFooterViewList = list;
            this.mFooterView = view;
        }
        setVuiElementVisible(view, z);
        for (View view2 : list) {
            setVuiElementVisible(view2, z);
            this.mBuildViewList.add(view2);
            if (this.fatherId != null && (view2 instanceof IVuiElement)) {
                IVuiElement iVuiElement = (IVuiElement) view2;
                if (iVuiElement.getVuiFatherLabel() == null) {
                    iVuiElement.setVuiFatherElementId(this.fatherId);
                }
            }
        }
    }

    public void updateVuiHeaderFooterAndRecyclerView() {
        boolean updateVuiHeaderFooterScene = updateVuiHeaderFooterScene(this.mHeaderViewList, this.mHeaderView);
        boolean updateVuiHeaderFooterScene2 = updateVuiHeaderFooterScene(this.mFooterViewList, this.mFooterView);
        ArrayList arrayList = new ArrayList();
        if (!updateVuiHeaderFooterScene && !updateVuiHeaderFooterScene2) {
            updateVuiScene(this.sceneId, this.mRecyclerView);
            return;
        }
        if (updateVuiHeaderFooterScene) {
            arrayList.addAll(this.mHeaderViewList);
        }
        if (updateVuiHeaderFooterScene2) {
            arrayList.addAll(this.mFooterViewList);
        }
        arrayList.add(this.mRecyclerView);
        updateVuiScene(this.sceneId, arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[Catch: Exception -> 0x006c, LOOP:0: B:19:0x005b->B:21:0x0061, LOOP_END, TRY_LEAVE, TryCatch #0 {Exception -> 0x006c, blocks: (B:7:0x0010, B:9:0x0018, B:14:0x002b, B:16:0x004e, B:18:0x0054, B:19:0x005b, B:21:0x0061), top: B:27:0x0010 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean updateVuiHeaderFooterScene(java.util.List<android.view.View> r6, android.view.View r7) {
        /*
            r5 = this;
            r0 = 0
            if (r6 == 0) goto L70
            if (r7 == 0) goto L70
            androidx.recyclerview.widget.RecyclerView r1 = r5.mRecyclerView
            androidx.recyclerview.widget.RecyclerView$LayoutManager r1 = r1.getLayoutManager()
            androidx.recyclerview.widget.LinearLayoutManager r1 = (androidx.recyclerview.widget.LinearLayoutManager) r1
            r2 = 1
            if (r1 == 0) goto L2a
            androidx.recyclerview.widget.RecyclerView r3 = r5.mRecyclerView     // Catch: java.lang.Exception -> L6c
            androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = r3.getChildViewHolder(r7)     // Catch: java.lang.Exception -> L6c
            if (r3 == 0) goto L2a
            int r3 = r1.findFirstVisibleItemPosition()     // Catch: java.lang.Exception -> L6c
            int r4 = r1.findLastVisibleItemPosition()     // Catch: java.lang.Exception -> L6c
            int r1 = r1.getPosition(r7)     // Catch: java.lang.Exception -> L6c
            if (r1 < r3) goto L2a
            if (r1 > r4) goto L2a
            r1 = r2
            goto L2b
        L2a:
            r1 = r0
        L2b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6c
            r3.<init>()     // Catch: java.lang.Exception -> L6c
            java.lang.String r4 = "updateVuiHeaderFooterScene view:"
            r3.append(r4)     // Catch: java.lang.Exception -> L6c
            r3.append(r7)     // Catch: java.lang.Exception -> L6c
            java.lang.String r4 = ",visible:"
            r3.append(r4)     // Catch: java.lang.Exception -> L6c
            r3.append(r1)     // Catch: java.lang.Exception -> L6c
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L6c
            com.xiaopeng.car.settingslibrary.common.utils.Logs.d(r3)     // Catch: java.lang.Exception -> L6c
            java.lang.Boolean r3 = r5.getVuiElementVisible(r7)     // Catch: java.lang.Exception -> L6c
            if (r3 == 0) goto L70
            boolean r3 = r3.booleanValue()     // Catch: java.lang.Exception -> L6c
            if (r3 == r1) goto L70
            r5.setVuiElementVisible(r7, r1)     // Catch: java.lang.Exception -> L6c
            java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Exception -> L6c
        L5b:
            boolean r7 = r6.hasNext()     // Catch: java.lang.Exception -> L6c
            if (r7 == 0) goto L6b
            java.lang.Object r7 = r6.next()     // Catch: java.lang.Exception -> L6c
            android.view.View r7 = (android.view.View) r7     // Catch: java.lang.Exception -> L6c
            r5.setVuiElementVisible(r7, r1)     // Catch: java.lang.Exception -> L6c
            goto L5b
        L6b:
            return r2
        L6c:
            r6 = move-exception
            r6.printStackTrace()
        L70:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.speech.VuiListFragment.updateVuiHeaderFooterScene(java.util.List, android.view.View):boolean");
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        Logs.d("onGlobalLayout update vui");
        vuiUpdateRecyclerViewScene();
    }

    public Handler getVuiHandler() {
        return this.mVuiHandler;
    }
}
