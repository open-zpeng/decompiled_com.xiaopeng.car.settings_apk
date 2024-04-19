package com.xiaopeng.car.settingslibrary.ui.view;

import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.download.DownLoadNotificationBean;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.ui.widget.MaskImageView;
import com.xiaopeng.car.settingslibrary.vm.download.DownLoadMonitorViewModel;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import com.xiaopeng.xui.widget.XListTwo;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class DownloadView extends BaseListView {
    public static final String TAG = "DownloadView";
    private DownLoadAdapter mDownLoadAdapter;
    private DownLoadMonitorViewModel mDownLoadMonitorViewModel;
    private View mNoDataView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
    }

    public DownloadView(Context context) {
        super(context);
    }

    public DownloadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DownloadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DownloadView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void init(Context context, View view) {
        this.mNoDataView = view.findViewById(R.id.no_data);
        ((TextView) view.findViewById(R.id.no_data_text)).setText(R.string.download_no_data);
        this.mRecyclerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$DownloadView$b9GF_KStXdCny5vZnP95KCJX7qE
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return DownloadView.this.lambda$init$0$DownloadView(view2, motionEvent);
            }
        });
        this.mDownLoadMonitorViewModel = new DownLoadMonitorViewModel();
        this.mDownLoadAdapter = new DownLoadAdapter();
        this.mRecyclerView.setAdapter(this.mDownLoadAdapter);
        DownLoadMonitorViewModel downLoadMonitorViewModel = this.mDownLoadMonitorViewModel;
        if (downLoadMonitorViewModel == null) {
            Logs.d("init vm null");
            return;
        }
        downLoadMonitorViewModel.getData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$DownloadView$lWch9kEvi1osIWJVusepzel4qE0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DownloadView.this.onChanged((List) obj);
            }
        });
        this.mDownLoadMonitorViewModel.getItemChange().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$DownloadView$xXMAokLvAhG4a4uN3PNgk4NQlXk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DownloadView.this.lambda$init$1$DownloadView((DownLoadNotificationBean) obj);
            }
        });
    }

    public /* synthetic */ boolean lambda$init$0$DownloadView(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mDownLoadAdapter.removePendingCancel();
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$init$1$DownloadView(DownLoadNotificationBean downLoadNotificationBean) {
        int indexOf = this.mDownLoadAdapter.getData().indexOf(downLoadNotificationBean);
        if (indexOf > -1) {
            this.mDownLoadAdapter.lambda$refreshItem$9$BaseAdapter(indexOf, downLoadNotificationBean);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected int layoutId() {
        return R.layout.fragment_xdialog_list;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        this.mDownLoadMonitorViewModel.onResume();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        this.mDownLoadMonitorViewModel.onPause();
        DownLoadAdapter downLoadAdapter = this.mDownLoadAdapter;
        if (downLoadAdapter != null) {
            downLoadAdapter.lambda$clear$12$BaseAdapter();
        }
    }

    public void onConfigurationChanged() {
        DownLoadAdapter downLoadAdapter = this.mDownLoadAdapter;
        if (downLoadAdapter != null) {
            downLoadAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChanged(List<DownLoadNotificationBean> list) {
        this.mDownLoadAdapter.refreshDataWithContrast(list);
        Logs.log("xpdownload-fragment", list.toString());
        this.mNoDataView.setVisibility(list.size() == 0 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DownLoadAdapter extends BaseAdapter<DownLoadNotificationBean> implements View.OnClickListener {
        private String mPendingCancelKey;

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected boolean isControlInterval() {
            return true;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected boolean itemClickable(int i) {
            return false;
        }

        private DownLoadAdapter() {
            this.mPendingCancelKey = null;
        }

        public void removePendingCancel() {
            if (TextUtils.isEmpty(this.mPendingCancelKey)) {
                Logs.log("xpdownload", "removePendingCancel, no pending cancel item");
            } else {
                setPendingCancel(null);
            }
        }

        private void setPendingCancel(DownLoadNotificationBean downLoadNotificationBean) {
            StringBuilder sb = new StringBuilder();
            sb.append("setPendingCancel, current:");
            sb.append(this.mPendingCancelKey);
            sb.append(", setTo:");
            sb.append(downLoadNotificationBean != null ? downLoadNotificationBean.getKey() : "null");
            Logs.log(DownloadView.TAG, sb.toString());
            List<DownLoadNotificationBean> data = DownloadView.this.mDownLoadAdapter.getData();
            String str = this.mPendingCancelKey;
            this.mPendingCancelKey = null;
            if (data != null && !data.isEmpty()) {
                int i = 0;
                ArrayList<Integer> arrayList = new ArrayList();
                for (DownLoadNotificationBean downLoadNotificationBean2 : data) {
                    if (downLoadNotificationBean2.equals(downLoadNotificationBean)) {
                        if (!downLoadNotificationBean2.getKey().equals(this.mPendingCancelKey)) {
                            arrayList.add(Integer.valueOf(i));
                        }
                        this.mPendingCancelKey = downLoadNotificationBean2.getKey();
                    } else if (!TextUtils.isEmpty(str) && downLoadNotificationBean2.getKey().equals(str)) {
                        arrayList.add(Integer.valueOf(i));
                    }
                    i++;
                }
                Logs.log(DownloadView.TAG, "setPendingCancel, notifyChange:" + arrayList);
                for (Integer num : arrayList) {
                    lambda$refreshItem$10$BaseAdapter(num.intValue());
                }
                return;
            }
            this.mPendingCancelKey = null;
            Logs.log("xpdownload", "setPendingCancel, download list is empty.");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(BaseAdapter.ViewHolder viewHolder) {
            XTouchAreaUtils.extendTouchAreaAsParentSameSize((XButton) viewHolder.getView(R.id.download_item_button), (ViewGroup) viewHolder.getView(R.id.download_item_button_layout));
            super.onViewAttachedToWindow((DownLoadAdapter) viewHolder);
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            DownLoadNotificationBean item = getItem(i);
            Logs.log("xpdownload-convert", item.toString() + ", pendingCancel:" + this.mPendingCancelKey);
            XListTwo xListTwo = (XListTwo) viewHolder.getView(R.id.download_item_list);
            XButton xButton = (XButton) viewHolder.getView(R.id.download_item_button);
            MaskImageView maskImageView = (MaskImageView) viewHolder.getView(R.id.download_icon);
            XCircularProgressBar xCircularProgressBar = (XCircularProgressBar) viewHolder.getView(R.id.download_circularProgressBar);
            xCircularProgressBar.setMax(item.getProgressMax());
            maskImageView.setImageIcon(item.getIcon());
            xListTwo.setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.download_size)).setText(item.getSizeString());
            xButton.setEnabled(item.getButtonStatus() == 1);
            xCircularProgressBar.setEnabled(item.getButtonStatus() == 1);
            View view = viewHolder.getView(R.id.download_item_action);
            view.setEnabled(item.getButtonStatus() == 1);
            xButton.setText(R.string.download_action_stop);
            xCircularProgressBar.setVisibility(0);
            maskImageView.setShowMask(true);
            refreshTextSub(xListTwo, item);
            int status = item.getStatus();
            if (status == 1) {
                xCircularProgressBar.setIndeterminate(false);
                xCircularProgressBar.setIndicatorType(2);
            } else if (status == 2) {
                xCircularProgressBar.setIndeterminate(true);
                xCircularProgressBar.setIndicatorType(2);
            } else if (status == 3) {
                xCircularProgressBar.setIndeterminate(false);
                xCircularProgressBar.setIndicatorType(0);
            } else if (status == 4) {
                xCircularProgressBar.setVisibility(4);
                maskImageView.setShowMask(false);
                if (item.getSuccessAction() != null && !TextUtils.isEmpty(item.getSuccessAction().title)) {
                    xButton.setText(item.getSuccessAction().title);
                } else {
                    xButton.setText(R.string.download_action_success);
                }
            } else if (status == 5) {
                xCircularProgressBar.setIndeterminate(false);
                xCircularProgressBar.setIndicatorType(3);
            }
            xCircularProgressBar.setProgress(item.getProgress());
            xButton.setTag(item);
            xButton.setOnClickListener(this);
            view.setTag(item);
            view.setOnClickListener(this);
            refreshButtonCancelConfirm(viewHolder, item);
        }

        private void refreshTextSub(XListTwo xListTwo, DownLoadNotificationBean downLoadNotificationBean) {
            if (!TextUtils.isEmpty(downLoadNotificationBean.getStatusDesc())) {
                xListTwo.setTextSub(downLoadNotificationBean.getStatusDesc());
                Logs.log("xpdownload-refreshTextSub", "use custom text:" + downLoadNotificationBean);
                return;
            }
            int status = downLoadNotificationBean.getStatus();
            if (status == 1) {
                xListTwo.setTextSub(DownloadView.this.surplusTime(downLoadNotificationBean.getRemainingTime()));
            } else if (status == 2) {
                xListTwo.setTextSub(DownloadView.this.getResources().getString(R.string.download_status_waiting));
            } else if (status == 3) {
                xListTwo.setTextSub(DownloadView.this.getResources().getString(R.string.download_status_pausing));
            } else if (status == 4) {
                xListTwo.setTextSub(DownloadView.this.getResources().getString(R.string.download_status_success));
            } else if (status != 5) {
            } else {
                xListTwo.setTextSub(DownloadView.this.getResources().getString(R.string.download_status_fail));
            }
        }

        private void refreshButtonCancelConfirm(BaseAdapter.ViewHolder viewHolder, DownLoadNotificationBean downLoadNotificationBean) {
            XButton xButton = (XButton) viewHolder.getView(R.id.download_item_button);
            View view = viewHolder.getView(R.id.pending_cancel_mask);
            view.setOnClickListener(this);
            view.setVisibility(8);
            if (downLoadNotificationBean.getStatus() != 4) {
                if (downLoadNotificationBean.getKey().equals(this.mPendingCancelKey)) {
                    xButton.setBackgroundResource(R.drawable.x_btn_real_negative_selector);
                    xButton.setText(R.string.download_action_stop_confirm);
                    xButton.setTextColor(CarSettingsApp.getContext().getColorStateList(R.color.x_btn_real_color_selector));
                    view.setVisibility(0);
                    return;
                }
                Logs.log(DownloadView.TAG, "refreshButtonCancelConfirm status success, not pending cancel:" + downLoadNotificationBean.getTitle());
            }
            xButton.setBackgroundResource(R.drawable.x_btn_ghost_positive_selector);
            xButton.setTextColor(CarSettingsApp.getContext().getColorStateList(R.color.x_btn_ghost_small_color_selector));
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.download_recycler_item;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() == R.id.download_item_action) {
                DownLoadNotificationBean downLoadNotificationBean = (DownLoadNotificationBean) view.getTag();
                Logs.log("xpdownload-fragment-onClick", downLoadNotificationBean.toString());
                int status = downLoadNotificationBean.getStatus();
                if (status == 1 || status == 2) {
                    if (downLoadNotificationBean.getPausePendingIntent() != null) {
                        try {
                            downLoadNotificationBean.getPausePendingIntent().send();
                            Logs.log("xpdownload-fragment-onClick", "pause " + downLoadNotificationBean.getPausePendingIntent().getIntent());
                            return;
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    Logs.log("xpdownload-fragment-onClick", "pause null action");
                } else if (status == 3) {
                    if (downLoadNotificationBean.getResumePendingIntent() != null) {
                        try {
                            downLoadNotificationBean.getResumePendingIntent().send();
                            Logs.log("xpdownload-fragment-onClick", "resume " + downLoadNotificationBean.getResumePendingIntent().getIntent());
                            return;
                        } catch (PendingIntent.CanceledException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    Logs.log("xpdownload-fragment-onClick", "resume null action");
                } else if (status != 5) {
                } else {
                    if (downLoadNotificationBean.getRetryPendingIntent() != null) {
                        try {
                            downLoadNotificationBean.getRetryPendingIntent().send();
                            Logs.log("xpdownload-fragment-onClick", "retry " + downLoadNotificationBean.getRetryPendingIntent().getIntent());
                            return;
                        } catch (PendingIntent.CanceledException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    }
                    Logs.log("xpdownload-fragment-onClick", "retry null action");
                }
            } else if (view.getId() == R.id.download_item_button) {
                DownLoadNotificationBean downLoadNotificationBean2 = (DownLoadNotificationBean) view.getTag();
                Logs.log("xpdownload-fragment-onClick", downLoadNotificationBean2.toString());
                if (downLoadNotificationBean2.getStatus() == 4) {
                    if (downLoadNotificationBean2.getSuccessAction() != null && downLoadNotificationBean2.getSuccessAction().actionIntent != null) {
                        try {
                            downLoadNotificationBean2.getSuccessAction().actionIntent.send();
                            AppServerManager.getInstance().closePopupDialog(Config.POPUP_DOWNLOAD);
                            Logs.log("xpdownload-fragment-onClick", "success " + downLoadNotificationBean2.getSuccessAction().actionIntent.getIntent());
                            return;
                        } catch (PendingIntent.CanceledException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    Logs.log("xpdownload-fragment-onClick", "success null action");
                } else if (downLoadNotificationBean2.getKey().equals(this.mPendingCancelKey)) {
                    if (downLoadNotificationBean2.getCancelPendingIntent() != null) {
                        try {
                            downLoadNotificationBean2.getCancelPendingIntent().send();
                            Logs.log("xpdownload-fragment-onClick", "cancel " + downLoadNotificationBean2.getCancelPendingIntent().getIntent());
                            return;
                        } catch (PendingIntent.CanceledException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    }
                    Logs.log("xpdownload-fragment-onClick", "cancel null action");
                } else {
                    setPendingCancel(downLoadNotificationBean2);
                }
            } else if (view.getId() == R.id.pending_cancel_mask) {
                removePendingCancel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String surplusTime(int i) {
        if (i < 0) {
            return getContext().getString(R.string.download_remaining_time_none);
        }
        int i2 = i / 3600;
        int i3 = i % 3600;
        int i4 = i3 / 60;
        int i5 = i3 % 60;
        if (i2 > 12) {
            return getContext().getString(R.string.download_remaining_time_greater_hour12);
        }
        return i2 > 1 ? getContext().getString(R.string.download_remaining_time_greater_hour1, Integer.valueOf(i2), Integer.valueOf(i4), Integer.valueOf(i5)) : i4 > 0 ? getContext().getString(R.string.download_remaining_time_greater_minute1, Integer.valueOf(i4), Integer.valueOf(i5)) : getContext().getString(R.string.download_remaining_time_minute0, Integer.valueOf(i5));
    }
}
