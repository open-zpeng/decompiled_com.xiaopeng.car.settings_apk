package com.xiaopeng.car.settingslibrary.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/* loaded from: classes.dex */
public class DemoFragment extends BaseFragment {
    Adapter mAdapter;
    RecyclerView mRecyclerView;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_demo;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mAdapter = new Adapter();
        View inflate = LayoutInflater.from((Context) Objects.requireNonNull(getContext())).inflate(R.layout.fragment_demo_item_tv, (ViewGroup) this.mRecyclerView, false);
        this.mAdapter.setHeaderView(inflate);
        inflate.setBackgroundResource(R.drawable.list_item_checked);
        this.mRecyclerView.setAdapter(this.mAdapter);
        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$No6YFIivC7lRMWYVd79XhovF2b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$0$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$uL3kKRZ-aHWxzIM6nnYAXgxC3io
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$1$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$UZXmf4fUg6Sy8fVd5Mbk1KwabCY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$2$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$6IV_SMAVO1cMP64YgBiiVkr62ZA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$3$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$crwEqqg2jXdusI2fpT7jzksNPcs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$4$DemoFragment(view2);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.DemoFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                Log.d("demo", "onScrollStateChanged newState " + i);
                super.onScrollStateChanged(recyclerView, i);
            }
        });
        view.findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$_FEBsfbB0J5uyKi2ZrS_7kRYHqY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$5$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn11).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$ng_YXMg2Iat8FZjitAXyGYrWghg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$6$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn12).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$YahdQ9o0rD6XZQ6XymbsxrLrG7k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$7$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn13).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$lUa5-ksCXjnk_s-RDJK-ApRmQvY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$8$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn14).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$RjOjKVxdRungINMr85knzC0QkmU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$9$DemoFragment(view2);
            }
        });
        view.findViewById(R.id.btn15).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.-$$Lambda$DemoFragment$yZ3H4_L45_7tasSjiYiZL_3mvKs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DemoFragment.this.lambda$initView$10$DemoFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$DemoFragment(View view) {
        Adapter adapter = this.mAdapter;
        adapter.lambda$moveData$8$BaseAdapter(adapter.getData().size() - 1, 0);
    }

    public /* synthetic */ void lambda$initView$1$DemoFragment(View view) {
        this.mAdapter.addData(2, (int) new DemoBean(String.valueOf(new Random().nextInt(10000))));
    }

    public /* synthetic */ void lambda$initView$2$DemoFragment(View view) {
        this.mAdapter.lambda$removeData$6$BaseAdapter(2);
    }

    public /* synthetic */ void lambda$initView$3$DemoFragment(View view) {
        this.mAdapter.lambda$refreshItem$10$BaseAdapter(2);
    }

    public /* synthetic */ void lambda$initView$4$DemoFragment(View view) {
        this.mAdapter.lambda$refreshItem$10$BaseAdapter(2);
        this.mAdapter.lambda$refreshItem$10$BaseAdapter(2);
        this.mAdapter.lambda$refreshItem$10$BaseAdapter(2);
        this.mAdapter.getData().clear();
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.addData((Adapter) new DemoBean(String.valueOf(new Random().nextInt(10000))));
        this.mAdapter.lambda$removeData$6$BaseAdapter(2);
        this.mAdapter.addData((Adapter) new DemoBean(String.valueOf(new Random().nextInt(10000))));
        this.mAdapter.lambda$removeData$6$BaseAdapter(2);
        this.mAdapter.addData((Adapter) new DemoBean(String.valueOf(new Random().nextInt(10000))));
        this.mAdapter.lambda$removeData$6$BaseAdapter(2);
        this.mAdapter.addData((Adapter) new DemoBean(String.valueOf(new Random().nextInt(10000))));
        this.mAdapter.lambda$removeData$6$BaseAdapter(2);
    }

    public /* synthetic */ void lambda$initView$5$DemoFragment(View view) {
        List<DemoBean> data = this.mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id.equals("8")) {
                this.mRecyclerView.smoothScrollToPosition(i + this.mAdapter.getHeaderCount());
                return;
            }
        }
    }

    public /* synthetic */ void lambda$initView$6$DemoFragment(View view) {
        List<DemoBean> data = this.mAdapter.getData();
        int i = 0;
        while (true) {
            if (i >= data.size()) {
                break;
            } else if (data.get(i).id.equals("8")) {
                this.mRecyclerView.smoothScrollToPosition(i + this.mAdapter.getHeaderCount());
                Log.d("demo", "smoothScrollToPosition");
                break;
            } else {
                i++;
            }
        }
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.DemoFragment.2
            @Override // java.lang.Runnable
            public void run() {
                DemoFragment.this.mAdapter.lambda$addData$5$BaseAdapter(3, (List) DemoFragment.this.createList(30, 5));
            }
        });
    }

    public /* synthetic */ void lambda$initView$7$DemoFragment(View view) {
        List<DemoBean> data = this.mAdapter.getData();
        int i = 0;
        while (true) {
            if (i >= data.size()) {
                break;
            } else if (data.get(i).id.equals("8")) {
                this.mRecyclerView.smoothScrollToPosition(i + this.mAdapter.getHeaderCount());
                break;
            } else {
                i++;
            }
        }
        this.mAdapter.lambda$removeData$6$BaseAdapter(3);
    }

    public /* synthetic */ void lambda$initView$8$DemoFragment(View view) {
        this.mAdapter.lambda$addData$5$BaseAdapter(3, (List) createList(30, 5));
        List<DemoBean> data = this.mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id.equals("8")) {
                this.mRecyclerView.smoothScrollToPosition(i + this.mAdapter.getHeaderCount());
                return;
            }
        }
    }

    public /* synthetic */ void lambda$initView$9$DemoFragment(View view) {
        this.mAdapter.lambda$removeData$6$BaseAdapter(3);
        List<DemoBean> data = this.mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id.equals("8")) {
                this.mRecyclerView.smoothScrollToPosition(i + this.mAdapter.getHeaderCount());
                return;
            }
        }
    }

    public /* synthetic */ void lambda$initView$10$DemoFragment(View view) {
        init(null);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mAdapter.lambda$setData$1$BaseAdapter(createList(0, 20));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<DemoBean> createList(int i, int i2) {
        ArrayList<DemoBean> arrayList = new ArrayList<>();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(new DemoBean(String.valueOf(i3 + i)));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Adapter extends BaseAdapter<DemoBean> {
        private Adapter() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            ((TextView) viewHolder.getView(R.id.text)).setText(getItem(i).toString());
            Log.d("demo-over", "convert");
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.fragment_demo_item_tv;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DemoBean {
        String id;

        DemoBean(String str) {
            this.id = str;
        }

        public int hashCode() {
            return this.id.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DemoBean) && this.id.equals(((DemoBean) obj).id);
        }

        public String toString() {
            return this.id + "";
        }
    }
}
