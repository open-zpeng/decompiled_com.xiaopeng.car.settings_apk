package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.FeedbackBean;
import com.xiaopeng.car.settingslibrary.common.utils.DatetimeUtils;
import com.xiaopeng.car.settingslibrary.common.utils.InputMethodUtil;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.NetworkUtils;
import com.xiaopeng.car.settingslibrary.common.utils.XpThemeUtils;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.feedback.FeedbackViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.Date;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class FeedbackFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, View.OnTouchListener, InputMethodUtil.KeyboardChangeListener {
    private static final int HISTORY_ERROR = 3;
    private static final int HISTORY_HAS_DATA = 1;
    private static final int HISTORY_LOADING = 2;
    private static final int HISTORY_NO_DATA = 0;
    private static final int INPUT_LIMIT = 255;
    private static final int INPUT_LIMIT_LEAST = 5;
    public static final long ONE_HOUR_MILLIONS = 3600000;
    public static final long ONE_MINUTE_MILLIONS = 60000;
    public static final long ONE_SECOND_MILLIONS = 1000;
    public static final long THREE_HOUR_MILLIONS = 10800000;
    private Button mButton;
    private TextView mCountTv;
    private EditText mEditText;
    private View mEmptyDataView;
    private FeedbackAdapter mFeedbackAdapter;
    private FeedbackViewModel mFeedbackViewModel;
    private XDialog mHistoryDialog;
    private View mHistoryLoadingView;
    private XRecyclerView mHistoryRecyclerView;
    private InputMethodUtil mInputMethodUtil;
    private boolean mIsLoading;
    private String mLastQuiz;
    private View mLoading;
    private View mNetErrorView;
    private XDialog mNetworkWeakDialog;
    private View mNoMoreView;
    private XDialog mNoNetworkDialog;
    private RadioGroup mRadioGroup;
    private ScrollView mScrollView;
    private String mFeedbackType = "0";
    private boolean mIsRegisterFresh = false;
    private Runnable mToastRunnable = new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.2
        @Override // java.lang.Runnable
        public void run() {
            Logs.d("afterTextChanged.. postDelayed...");
            if (FeedbackFragment.this.getActivity() != null) {
                ToastUtils.get().showText(FeedbackFragment.this.getString(R.string.feedback_auto_save));
            }
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_feedback;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        this.mRootView = this.mScrollView;
        Button button = (Button) view.findViewById(R.id.feedback_history).findViewById(R.id.x_list_action_button);
        button.setText(getContext().getString(R.string.feedback_review));
        button.setOnClickListener(this);
        this.mEditText = (EditText) view.findViewById(R.id.feedback_input);
        this.mEditText.addTextChangedListener(new InputTextWatcher());
        this.mEditText.setOnTouchListener(this);
        this.mButton = (Button) view.findViewById(R.id.feedback_submit);
        this.mButton.setOnClickListener(this);
        this.mRadioGroup = (RadioGroup) view.findViewById(R.id.feedback_input_feature);
        this.mRadioGroup.setOnCheckedChangeListener(this);
        this.mRadioGroup.check(R.id.feedback_feature_function);
        this.mCountTv = (TextView) view.findViewById(R.id.feedback_input_count);
        this.mCountTv.setText("0/255");
        this.mButton.setEnabled(false);
        final ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.commit_layout);
        this.mButton.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.1
            @Override // java.lang.Runnable
            public void run() {
                XTouchAreaUtils.extendTouchArea(FeedbackFragment.this.mButton, viewGroup, new int[]{0, (-(112 - FeedbackFragment.this.mButton.getHeight())) / 2, 0, (112 - FeedbackFragment.this.mButton.getHeight()) / 2});
            }
        });
        this.mInputMethodUtil = new InputMethodUtil((AppCompatActivity) getActivity());
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mFeedbackAdapter = new FeedbackAdapter();
        this.mFeedbackViewModel = (FeedbackViewModel) ViewModelUtils.getViewModel(this, FeedbackViewModel.class);
        this.mFeedbackViewModel.getFeedbackBeanLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$FeedbackFragment$s-lBG8HkIbOiYrSc8C7CKb3j1vw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackFragment.this.lambda$init$0$FeedbackFragment((Integer) obj);
            }
        });
        this.mFeedbackViewModel.getCommitResultLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$FeedbackFragment$Y9s_WJVkd8ZMbWRzVJs4afV3sLM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackFragment.this.lambda$init$1$FeedbackFragment((Integer) obj);
            }
        });
        this.mFeedbackViewModel.getNetworkChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$FeedbackFragment$YQuOWtv_lxOfJI6eMM1MCK4TAv4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackFragment.this.lambda$init$2$FeedbackFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$FeedbackFragment(Integer num) {
        Logs.d("feedback history observe status:" + num);
        if (num.intValue() == 0) {
            showHistory(3);
        } else {
            List<FeedbackBean> feedbackBeans = this.mFeedbackViewModel.getFeedbackBeans();
            if (feedbackBeans != null && feedbackBeans.size() > 0) {
                showHistory(1);
                this.mFeedbackAdapter.lambda$setData$1$BaseAdapter(feedbackBeans);
            } else {
                showHistory(0);
            }
        }
        View view = this.mLoading;
        if (view != null) {
            view.setVisibility(8);
        }
        View view2 = this.mNoMoreView;
        if (view2 != null) {
            view2.setVisibility(this.mFeedbackViewModel.isNoData() ? 0 : 8);
        }
        Logs.v("xpfeedback getFeedbackBeanLiveData");
        this.mIsLoading = false;
    }

    public /* synthetic */ void lambda$init$1$FeedbackFragment(Integer num) {
        if (isHidden()) {
            return;
        }
        if (num.intValue() == 0) {
            if (getActivity() != null) {
                ToastUtils.get().showText(getString(R.string.commit_fail));
            }
        } else if (num.intValue() == 1) {
            if (getActivity() != null) {
                ToastUtils.get().showText(getString(R.string.commit_success));
            }
            this.mFeedbackViewModel.saveLocalInputFeedback("");
            this.mEditText.getText().clear();
        }
        if (!TextUtils.isEmpty(this.mEditText.getText().toString().trim())) {
            this.mButton.setEnabled(true);
        } else {
            this.mButton.setEnabled(false);
        }
    }

    public /* synthetic */ void lambda$init$2$FeedbackFragment(Boolean bool) {
        if (bool.booleanValue()) {
            dismissDialog();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isHidden()) {
            return;
        }
        refreshFeedbackData();
    }

    private void refreshFeedbackData() {
        InputMethodUtil inputMethodUtil;
        if (this.mIsRegisterFresh) {
            return;
        }
        this.mLastQuiz = this.mFeedbackViewModel.getInputFeedback();
        if (!TextUtils.isEmpty(this.mLastQuiz)) {
            this.mEditText.setText(this.mLastQuiz);
        }
        if (!isHidden() && (inputMethodUtil = this.mInputMethodUtil) != null) {
            inputMethodUtil.setOnKeyboardChangeListener(this);
        }
        this.mFeedbackViewModel.onStartVM();
        this.mIsRegisterFresh = true;
        Logs.d("xpfeedback fragment fresh refreshFeedbackData " + Config.getFeedbackBaseUrl());
    }

    private void unRefreshFeedbackData() {
        if (this.mIsRegisterFresh) {
            InputMethodUtil inputMethodUtil = this.mInputMethodUtil;
            if (inputMethodUtil != null) {
                inputMethodUtil.removeOnKeyboardChangeListener();
            }
            this.mFeedbackViewModel.onStopVM();
            this.mIsRegisterFresh = false;
            Logs.d("xpfeedback fragment fresh unRefreshFeedbackData");
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        hiddenInputMethod();
    }

    private void dismissAllDialog() {
        dismissDialog();
        XDialog xDialog = this.mHistoryDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (!isHidden() || this.mIsRegisterFresh) {
            unRefreshFeedbackData();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logs.d("xpfeedback onHiddenChanged " + z + " " + this.mIsRegisterFresh);
        if (!z) {
            refreshFeedbackData();
            return;
        }
        unRefreshFeedbackData();
        dismissAllDialog();
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == this.mRadioGroup) {
            if (i == R.id.feedback_feature_function) {
                this.mFeedbackType = "0";
            } else if (i == R.id.feedback_feature_suggest) {
                this.mFeedbackType = "1";
            } else if (i == R.id.feedback_feature_other) {
                this.mFeedbackType = "2";
            }
            updateVuiScene(this.sceneId, this.mRadioGroup);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.x_list_action_button) {
            showHistoryPopup();
        } else if (id == R.id.feedback_submit) {
            String obj = this.mEditText.getText().toString();
            if (obj.length() < 5) {
                if (getActivity() != null) {
                    ToastUtils.get().showText(getString(R.string.feedback_input_least));
                    return;
                }
                return;
            }
            this.mFeedbackViewModel.uploadFeedbackContent(this.mFeedbackType, obj);
            this.mButton.setEnabled(false);
        }
    }

    private void showNoNetworkDialog() {
        XDialog xDialog = this.mNoNetworkDialog;
        if (xDialog != null) {
            xDialog.show();
            VuiManager.instance().initVuiDialog(this.mNoNetworkDialog, getContext(), VuiManager.SCENE_FEEDBACK_NO_NETWORK_DIALOG);
            return;
        }
        this.mNoNetworkDialog = new XDialog(getContext());
        this.mNoNetworkDialog.setMessage(R.string.feedback_no_network).setPositiveButton(getString(R.string.ok), (XDialogInterface.OnClickListener) null).show();
        VuiManager.instance().initVuiDialog(this.mNoNetworkDialog, getContext(), VuiManager.SCENE_FEEDBACK_NO_NETWORK_DIALOG);
    }

    private void dismissDialog() {
        XDialog xDialog = this.mNetworkWeakDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        XDialog xDialog2 = this.mNoNetworkDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XpThemeUtils.isThemeChanged(configuration)) {
            if (this.mHistoryDialog != null) {
                this.mHistoryDialog = null;
            }
            if (this.mNoNetworkDialog != null) {
                this.mNoNetworkDialog = null;
            }
            if (this.mNetworkWeakDialog != null) {
                this.mNetworkWeakDialog = null;
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        dismissAllDialog();
        if (!TextUtils.isEmpty(this.mEditText.getText().toString())) {
            this.mFeedbackViewModel.saveLocalInputFeedback(this.mEditText.getText().toString());
        }
        this.mInputMethodUtil = null;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewParent parent;
        if (view == this.mEditText) {
            if (motionEvent.getAction() == 0) {
                this.mScrollView.setVerticalFadingEdgeEnabled(false);
            }
            if ((this.mEditText.canScrollVertically(-1) || this.mEditText.canScrollVertically(0)) && (parent = this.mEditText.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getAction() == 1) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }
        }
        return false;
    }

    @Override // com.xiaopeng.car.settingslibrary.common.utils.InputMethodUtil.KeyboardChangeListener
    public void onKeyboardShow() {
        Logs.d("xpfeedback inputmethod show");
        this.mScrollView.setVerticalFadingEdgeEnabled(false);
        if (this.mFragmentController != null) {
            this.mFragmentController.removeFragmentPadding(true);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.common.utils.InputMethodUtil.KeyboardChangeListener
    public void onKeyboardHide() {
        Logs.d("xpfeedback inputmethod hide");
        this.mScrollView.setVerticalFadingEdgeEnabled(true);
        if (this.mFragmentController != null) {
            this.mFragmentController.removeFragmentPadding(false);
        }
    }

    /* loaded from: classes.dex */
    class InputTextWatcher implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        InputTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String obj = editable.toString();
            if (obj.trim().length() > 0) {
                FeedbackFragment.this.mButton.setEnabled(true);
            } else {
                FeedbackFragment.this.mButton.setEnabled(false);
            }
            if (obj.length() > 255) {
                obj = obj.substring(0, 255);
                editable.clear();
                editable.append((CharSequence) obj);
            }
            TextView textView = FeedbackFragment.this.mCountTv;
            textView.setText(obj.length() + MqttTopic.TOPIC_LEVEL_SEPARATOR + 255);
            String obj2 = FeedbackFragment.this.mEditText.getText().toString();
            Logs.v("xpfeedback afterTextChanged.. text: " + obj2 + ", mLastQuiz: " + FeedbackFragment.this.mLastQuiz);
            if (obj2.equals(FeedbackFragment.this.mLastQuiz)) {
                return;
            }
            FeedbackFragment.this.mLastQuiz = obj2;
            FeedbackFragment.this.mFeedbackViewModel.saveLocalInputFeedback(obj2);
            TextUtils.isEmpty(obj2);
        }
    }

    private void showHistoryPopup() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            XDialog xDialog = this.mNetworkWeakDialog;
            if (xDialog != null) {
                xDialog.show();
                VuiManager.instance().initVuiDialog(this.mNetworkWeakDialog, getContext(), VuiManager.SCENE_FEEDBACK_NETWORK_DIALOG);
                return;
            }
            this.mNetworkWeakDialog = new XDialog(getContext(), R.style.XDialogView_Large);
            this.mNetworkWeakDialog.setCloseVisibility(true);
            this.mNetworkWeakDialog.setIcon(R.drawable.x_ic_xxlarge_nowifi).setTitle(R.string.feedback_history).setMessage(R.string.feedback_network_weak).show();
            VuiManager.instance().initVuiDialog(this.mNetworkWeakDialog, getContext(), VuiManager.SCENE_FEEDBACK_NETWORK_DIALOG);
            return;
        }
        this.mFeedbackViewModel.clearHistory();
        this.mFeedbackViewModel.resetCurrentPage();
        this.mFeedbackViewModel.downloadFeedbackList();
        if (this.mHistoryDialog != null) {
            showHistory(2);
            this.mHistoryDialog.show();
            VuiManager.instance().initVuiDialog(this.mHistoryDialog, getContext(), VuiManager.SCENE_FEEDBACK_HISTORY_DIALOG);
            return;
        }
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.feedback_list, (ViewGroup) null);
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.feedback_history_loading, (ViewGroup) null);
        this.mLoading = inflate2.findViewById(R.id.feedback_loading);
        this.mNoMoreView = inflate2.findViewById(R.id.feedback_no_more);
        this.mHistoryRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerView);
        this.mHistoryLoadingView = inflate.findViewById(R.id.loading_view);
        this.mEmptyDataView = inflate.findViewById(R.id.empty_view);
        this.mNetErrorView = inflate.findViewById(R.id.net_error_view);
        this.mNetErrorView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FeedbackFragment.this.mFeedbackViewModel.downloadFeedbackList();
                FeedbackFragment.this.showHistory(2);
            }
        });
        showHistory(2);
        this.mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (this.mHistoryRecyclerView.getItemAnimator() != null) {
            this.mHistoryRecyclerView.getItemAnimator().setChangeDuration(0L);
        }
        this.mHistoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        this.mHistoryRecyclerView.setAdapter(this.mFeedbackAdapter);
        this.mHistoryRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.4
            @Override // com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.EndlessRecyclerOnScrollListener
            public void onLoadMore() {
                FeedbackFragment.this.mLoading.setVisibility(0);
                FeedbackFragment.this.mNoMoreView.setVisibility(8);
                FeedbackFragment.this.mFeedbackViewModel.downloadFeedbackList();
            }
        });
        this.mHistoryDialog = new XDialog(getContext(), R.style.XDialogView_Large);
        this.mHistoryDialog.setCloseVisibility(true);
        this.mHistoryDialog.setTitle(R.string.feedback_history).setCustomView(inflate, false).show();
        this.mHistoryDialog.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.5
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                if (FeedbackFragment.this.mHistoryRecyclerView != null) {
                    FeedbackFragment.this.mHistoryRecyclerView.scrollToPosition(0);
                }
            }
        });
        this.mHistoryDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                FeedbackFragment.this.mFeedbackViewModel.cancelRequestHistory();
                FeedbackFragment.this.mFeedbackViewModel.clearHistory();
            }
        });
        this.mFeedbackAdapter.setFooterView(inflate2);
        VuiManager.instance().initVuiDialog(this.mHistoryDialog, getContext(), VuiManager.SCENE_FEEDBACK_HISTORY_DIALOG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHistory(int i) {
        XRecyclerView xRecyclerView;
        if (this.mHistoryLoadingView == null || (xRecyclerView = this.mHistoryRecyclerView) == null || this.mHistoryDialog == null || this.mEmptyDataView == null || this.mNetErrorView == null) {
            return;
        }
        boolean z = xRecyclerView.getVisibility() == 0;
        if (i == 0) {
            this.mHistoryLoadingView.setVisibility(8);
            this.mHistoryRecyclerView.setVisibility(8);
            this.mEmptyDataView.setVisibility(0);
            this.mNetErrorView.setVisibility(8);
        } else if (i == 1) {
            this.mHistoryLoadingView.setVisibility(8);
            this.mHistoryRecyclerView.setVisibility(0);
            this.mEmptyDataView.setVisibility(8);
            this.mNetErrorView.setVisibility(8);
        } else if (i == 2) {
            this.mHistoryLoadingView.setVisibility(0);
            this.mHistoryRecyclerView.setVisibility(8);
            this.mEmptyDataView.setVisibility(8);
            this.mNetErrorView.setVisibility(8);
        } else if (i == 3) {
            this.mHistoryLoadingView.setVisibility(8);
            this.mHistoryRecyclerView.setVisibility(8);
            this.mEmptyDataView.setVisibility(8);
            this.mNetErrorView.setVisibility(0);
        }
        if ((this.mHistoryRecyclerView.getVisibility() == 0) == z || !this.mHistoryDialog.getDialog().isShowing()) {
            return;
        }
        this.mVuiHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment.7
            @Override // java.lang.Runnable
            public void run() {
                FeedbackFragment.this.updateVuiScene(VuiManager.SCENE_FEEDBACK_HISTORY_DIALOG, FeedbackFragment.this.mHistoryRecyclerView);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class FeedbackAdapter extends BaseAdapter<FeedbackBean> {
        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected boolean itemClickable(int i) {
            return false;
        }

        private FeedbackAdapter() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.feedback_history_item;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            FeedbackBean item = getItem(i);
            if (item == null) {
                return;
            }
            TextView textView = (TextView) viewHolder.getView(R.id.feedback_answer);
            TextView textView2 = (TextView) viewHolder.getView(R.id.feedback_content);
            ((TextView) viewHolder.getView(R.id.feedback_type)).setText(getFeedbackCategory(item));
            ((TextView) viewHolder.getView(R.id.feedback_time)).setText(timeToStr(item.getUpdateTime()));
            textView2.setText(item.getContent());
            if (!TextUtils.isEmpty(item.getAnswer())) {
                textView.setText(FeedbackFragment.this.getString(R.string.feedback_customer) + item.getAnswer());
                textView.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView2.getLayoutParams();
            layoutParams.bottomMargin = (int) FeedbackFragment.this.getResources().getDimension(R.dimen.feedback_content_padding_bottom);
            textView2.setLayoutParams(layoutParams);
        }

        private String timeToStr(String str) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            long currentTimeMillis = System.currentTimeMillis();
            long dateToTimeStamp = DatetimeUtils.dateToTimeStamp(str);
            long j = currentTimeMillis - dateToTimeStamp;
            if (j < 60000) {
                return FeedbackFragment.this.getString(R.string.feedback_just_now);
            }
            if (j < FeedbackFragment.ONE_HOUR_MILLIONS) {
                return (j / 60000) + FeedbackFragment.this.getString(R.string.feedback_before_minute);
            } else if (j <= FeedbackFragment.THREE_HOUR_MILLIONS) {
                return (j / FeedbackFragment.ONE_HOUR_MILLIONS) + FeedbackFragment.this.getString(R.string.feedback_before_hour);
            } else if (dateToTimeStamp >= DatetimeUtils.getStartDay()) {
                return "" + ((Object) DateFormat.format(DatetimeUtils.FORMAT_DATE_TIME_HOUR, new Date(dateToTimeStamp)));
            } else {
                return DateFormat.format(DatetimeUtils.FORMAT_DATE_TIME_SHORT, new Date(dateToTimeStamp)).toString();
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int getFeedbackCategory(FeedbackBean feedbackBean) {
            char c;
            String category = feedbackBean.getCategory();
            switch (category.hashCode()) {
                case 48:
                    if (category.equals("0")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 49:
                    if (category.equals("1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 50:
                    if (category.equals("2")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c != 0) {
                if (c != 1) {
                    if (c == 2) {
                        return R.string.feedback_other_question;
                    }
                    return R.string.feedback_function_fault;
                }
                return R.string.feedback_suggest_optimization;
            }
            return R.string.feedback_function_fault;
        }
    }

    /* loaded from: classes.dex */
    public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        private boolean isSlidingUpward = false;

        public abstract void onLoadMore();

        public EndlessRecyclerOnScrollListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0) {
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() + 3 >= FeedbackFragment.this.mFeedbackAdapter.getItemCount() && this.isSlidingUpward) {
                    Logs.d("xpfeedback adapter mIsLoading:" + FeedbackFragment.this.mIsLoading);
                    if (!FeedbackFragment.this.mIsLoading && !FeedbackFragment.this.mFeedbackViewModel.isNoData()) {
                        FeedbackFragment.this.mIsLoading = true;
                        onLoadMore();
                        FeedbackFragment.this.mHistoryRecyclerView.setCanVuiScrollDown(true);
                    }
                }
                FeedbackFragment.this.updateVuiScene(VuiManager.SCENE_FEEDBACK_HISTORY_DIALOG, FeedbackFragment.this.mHistoryRecyclerView);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            this.isSlidingUpward = i2 > 0;
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        EditText editText = this.mEditText;
        if (editText == null || editText.getText().toString().length() <= 0) {
            return;
        }
        EditText editText2 = this.mEditText;
        editText2.setSelection(editText2.getText().toString().length());
    }

    private void hiddenInputMethod() {
        InputMethodManager inputMethodManager;
        if (this.mEditText == null || (inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method")) == null) {
            return;
        }
        boolean isActive = inputMethodManager.isActive();
        Logs.d("xpfeedback hiddenInputMethod " + isActive);
        if (isActive) {
            inputMethodManager.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 2);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return new String[]{"/feedback/history_pop"};
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(String str) {
        if ("/feedback/history_pop".equals(str)) {
            showHistoryPopup();
        }
        super.onPageDirectShow(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        dismissDialog();
    }
}
