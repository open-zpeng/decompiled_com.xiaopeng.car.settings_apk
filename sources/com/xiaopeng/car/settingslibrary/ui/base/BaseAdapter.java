package com.xiaopeng.car.settingslibrary.ui.base;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.android.service.MqttServiceConstants;
/* loaded from: classes.dex */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 2000;
    private static final int TYPE_HEADER = 1000;
    private View mFooterView;
    private View mHeaderView;
    private OnItemClickListener mOnItemClickListener;
    private RecyclerView mRecyclerView;
    private List<T> mData = new ArrayList();
    public IntervalControl mIntervalControl = new IntervalControl("adapter");

    /* loaded from: classes.dex */
    public interface OnItemClickListener<T> {
        void onItemClick(BaseAdapter baseAdapter, ViewHolder viewHolder, T t);
    }

    private boolean isComputingLayout() {
        return false;
    }

    protected abstract void convert(ViewHolder viewHolder, int i);

    protected boolean isControlInterval() {
        return false;
    }

    protected boolean itemClickable(int i) {
        return true;
    }

    protected abstract int layoutId(int i);

    protected void onBindViewFooter(ViewHolder viewHolder) {
    }

    protected void onBindViewHeader(ViewHolder viewHolder) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setHeaderView(View view) {
        this.mHeaderView = view;
        notifyItemInserted(0);
    }

    public void setFooterView(View view) {
        this.mFooterView = view;
        notifyItemInserted(getHeaderCount() + this.mData.size());
    }

    public int getHeaderCount() {
        return this.mHeaderView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return this.mFooterView != null ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        int headerCount = getHeaderCount();
        int footerCount = getFooterCount();
        if (headerCount <= 0 || i != 0) {
            if (footerCount <= 0 || i < this.mData.size() + headerCount) {
                return getDefItemViewType(i - headerCount);
            }
            return 2000;
        }
        return 1000;
    }

    protected int getDefItemViewType(int i) {
        return super.getItemViewType(i);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        ViewGroup viewGroup2;
        if (i == 1000) {
            view = this.mHeaderView;
        } else if (i == 2000) {
            view = this.mFooterView;
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId(i), viewGroup, false);
        }
        if (view != null && (viewGroup2 = (ViewGroup) view.getParent()) != null) {
            viewGroup2.removeView(view);
            Logs.d("ViewHolder views must not be attached when created, remove item's parent! parent:" + viewGroup2 + " itemview:" + view);
        }
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 1000) {
            onBindViewHeader(viewHolder);
        } else if (itemViewType == 2000) {
            onBindViewFooter(viewHolder);
        } else {
            int headerCount = i - getHeaderCount();
            convert(viewHolder, headerCount);
            if (itemClickable(headerCount)) {
                bindViewClickListener(viewHolder, getItem(headerCount));
            } else {
                unBindViewClickListener(viewHolder);
            }
        }
    }

    private void bindViewClickListener(final ViewHolder viewHolder, final T t) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$Q7cm3i5HdH0y2CCJk8uMlkY8S1E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseAdapter.this.lambda$bindViewClickListener$0$BaseAdapter(t, viewHolder, view);
            }
        });
    }

    public /* synthetic */ void lambda$bindViewClickListener$0$BaseAdapter(Object obj, ViewHolder viewHolder, View view) {
        if (this.mOnItemClickListener != null) {
            if (isControlInterval() && this.mIntervalControl.isFrequently()) {
                return;
            }
            if (obj != null) {
                Logs.log("adapter", obj.toString());
            }
            this.mOnItemClickListener.onItemClick(this, viewHolder, obj);
        }
    }

    private void unBindViewClickListener(ViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(null);
        viewHolder.itemView.setClickable(false);
    }

    /* renamed from: setData */
    public void lambda$setData$1$BaseAdapter(final List<T> list) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "setData");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$pgK-FIA8PoNPibVGtrZIl5sglyo
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$setData$1$BaseAdapter(list);
                }
            });
            return;
        }
        this.mData.clear();
        this.mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(final T t) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "addData data");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$t_MwLxreqxAkq_ScaaiOicN-Kq4
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$addData$2$BaseAdapter(t);
                }
            });
            return;
        }
        this.mData.add(t);
        notifyItemInserted(getHeaderCount() + this.mData.size());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$addData$2$BaseAdapter(Object obj) {
        addData((BaseAdapter<T>) obj);
    }

    public void addData(final int i, final T t) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "addData index data");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$xUqdKskmSnmewpw-QaG-Xos-hyA
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$addData$3$BaseAdapter(i, t);
                }
            });
        } else if (i < 0 || i > this.mData.size()) {
        } else {
            this.mData.add(i, t);
            notifyItemInserted(getHeaderCount() + i);
            lambda$refreshItemRange$11$BaseAdapter(i, this.mData.size() - i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$addData$3$BaseAdapter(int i, Object obj) {
        addData(i, (int) obj);
    }

    /* renamed from: addData */
    public void lambda$addData$4$BaseAdapter(final List<T> list) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "addData list");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$wgjlkeWMfJLm2PBL-schbawLzjw
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$addData$4$BaseAdapter(list);
                }
            });
            return;
        }
        this.mData.addAll(list);
        notifyItemRangeInserted((getHeaderCount() + this.mData.size()) - list.size(), list.size());
    }

    /* renamed from: addData */
    public void lambda$addData$5$BaseAdapter(final int i, final List<T> list) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "addData index list");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$XnEZf-QBKpuYOKcANjfHXO7AxQo
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$addData$5$BaseAdapter(i, list);
                }
            });
        } else if (i < 0 || i > this.mData.size()) {
        } else {
            this.mData.addAll(i, list);
            notifyItemRangeInserted(getHeaderCount() + i, list.size());
            lambda$refreshItemRange$11$BaseAdapter(i, this.mData.size() - i);
        }
    }

    /* renamed from: removeData */
    public void lambda$removeData$6$BaseAdapter(final int i) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "removeData");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$32VBfdv72PT7CfxTSaJ649iEnUo
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$removeData$6$BaseAdapter(i);
                }
            });
        } else if (i < 0 || i >= this.mData.size()) {
        } else {
            this.mData.remove(i);
            notifyItemRemoved(getHeaderCount() + i);
            lambda$refreshItemRange$11$BaseAdapter(i, this.mData.size() - i);
        }
    }

    /* renamed from: removeData */
    public void lambda$removeData$7$BaseAdapter(final int i, final int i2) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "removeData");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$Fka4dFbOdUuHXn_Tx2-uOdK5jzA
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$removeData$7$BaseAdapter(i, i2);
                }
            });
        } else if (i >= 0 && i2 + i <= this.mData.size() && i2 > 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                this.mData.remove(i);
            }
            notifyItemRangeRemoved(getHeaderCount() + i, i2);
            lambda$refreshItemRange$11$BaseAdapter(i, this.mData.size() - i);
        }
    }

    /* renamed from: moveData */
    public void lambda$moveData$8$BaseAdapter(final int i, final int i2) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "moveData");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$pE0Mlgr_46r2qicZhNmlcfhO5X4
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$moveData$8$BaseAdapter(i, i2);
                }
            });
        } else if (i < 0 || i >= this.mData.size() || i2 < 0 || i2 >= this.mData.size()) {
        } else {
            this.mData.add(i2, this.mData.remove(i));
            notifyItemMoved(getHeaderCount() + i, getHeaderCount() + i2);
            if (i > i2) {
                lambda$refreshItemRange$11$BaseAdapter(i2, (i - i2) + 1);
            } else {
                lambda$refreshItemRange$11$BaseAdapter(i, (i2 - i) + 1);
            }
        }
    }

    /* renamed from: refreshItem */
    public void lambda$refreshItem$9$BaseAdapter(final int i, final T t) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "refreshItem data");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$xqexRkwHB9VrYGzNx5aSvjhE1HA
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$refreshItem$9$BaseAdapter(i, t);
                }
            });
        } else if (i < 0 || i >= this.mData.size()) {
        } else {
            this.mData.set(i, t);
            notifyItemChanged(getHeaderCount() + i, MqttServiceConstants.PAYLOAD);
        }
    }

    /* renamed from: refreshItem */
    public void lambda$refreshItem$10$BaseAdapter(final int i) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "refreshItem");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$AW96WwTR6N0tZRsRBMfXeEnWQXs
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$refreshItem$10$BaseAdapter(i);
                }
            });
        } else if (i < 0 || i >= this.mData.size()) {
        } else {
            notifyItemChanged(getHeaderCount() + i, MqttServiceConstants.PAYLOAD);
        }
    }

    public void refreshAll() {
        lambda$refreshItemRange$11$BaseAdapter(0, this.mData.size());
    }

    /* renamed from: refreshItemRange */
    public void lambda$refreshItemRange$11$BaseAdapter(final int i, final int i2) {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "refreshItemRange");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$USfnXEUhYa90WSb1QooP-BVoNj0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$refreshItemRange$11$BaseAdapter(i, i2);
                }
            });
        } else if (i < 0 || i >= this.mData.size()) {
        } else {
            notifyItemRangeChanged(getHeaderCount() + i, i2, MqttServiceConstants.PAYLOAD);
        }
    }

    public void refreshDataWithContrast(List<T> list) {
        if (list == null || list.size() == 0) {
            if (this.mData.size() > 0) {
                lambda$setData$1$BaseAdapter(list);
            }
        } else if (this.mData.size() == 0) {
            lambda$setData$1$BaseAdapter(list);
        } else {
            int i = 0;
            while (i < this.mData.size()) {
                if (list.contains(this.mData.get(i))) {
                    i++;
                } else {
                    lambda$removeData$6$BaseAdapter(i);
                }
            }
            checkAddItems(list);
            for (int i2 = 0; i2 < list.size(); i2++) {
                int indexOf = this.mData.indexOf(list.get(i2));
                if (indexOf != i2) {
                    lambda$moveData$8$BaseAdapter(indexOf, i2);
                }
            }
        }
    }

    private void checkMoveItems(List<T> list) {
        int i;
        int i2 = 0;
        loop0: while (true) {
            i = -1;
            while (i2 < this.mData.size()) {
                if (list.contains(this.mData.get(i2))) {
                    if (i != -1) {
                        break;
                    }
                } else if (i == -1) {
                    i = i2;
                }
                i2++;
            }
            lambda$removeData$7$BaseAdapter(i, i2 - i);
            i2 = i + 1;
        }
        if (i != -1) {
            lambda$removeData$7$BaseAdapter(i, i2 - i);
        }
    }

    private void checkAddItems(List<T> list) {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (int i2 = 0; i2 < list.size(); i2++) {
            T t = list.get(i2);
            if (!this.mData.contains(t)) {
                if (i == -1) {
                    i = i2;
                }
                arrayList.add(t);
            } else if (i != -1) {
                lambda$addData$5$BaseAdapter(i, (List) arrayList);
                arrayList.clear();
                i = -1;
            }
        }
        if (i != -1) {
            lambda$addData$5$BaseAdapter(i, (List) arrayList);
            arrayList.clear();
        }
    }

    /* renamed from: clear */
    public void lambda$clear$12$BaseAdapter() {
        if (isComputingLayout()) {
            Log.d("isComputingLayout", "clear");
            this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.base.-$$Lambda$BaseAdapter$udZBi_Eq-Zava-VgzL8xkB4zMAo
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAdapter.this.lambda$clear$12$BaseAdapter();
                }
            });
        } else if (this.mData.size() > 0) {
            lambda$removeData$7$BaseAdapter(0, this.mData.size());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size() + getHeaderCount() + getFooterCount();
    }

    public T getItem(int i) {
        return this.mData.get(i);
    }

    public List<T> getData() {
        return this.mData;
    }

    private void log(String str) {
        Logs.log("BaseAdapter", str);
    }

    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View convertView;
        private Object tag;
        private SparseArray<View> views;

        public ViewHolder(View view) {
            super(view);
            this.views = new SparseArray<>();
            this.convertView = view;
        }

        /* JADX WARN: Incorrect return type in method signature: <T:Landroid/view/View;>(I)TT; */
        public View getView(int i) {
            View view = this.views.get(i);
            if (view == null) {
                View findViewById = this.convertView.findViewById(i);
                this.views.put(i, findViewById);
                return findViewById;
            }
            return view;
        }

        public Object getTag() {
            return this.tag;
        }

        public void setTag(Object obj) {
            this.tag = obj;
        }
    }
}
