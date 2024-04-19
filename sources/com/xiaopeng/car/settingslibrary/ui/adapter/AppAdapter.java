package com.xiaopeng.car.settingslibrary.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
/* loaded from: classes.dex */
public abstract class AppAdapter<T> extends BaseAdapter<T> implements View.OnClickListener {
    protected abstract Drawable appIcon(T t);

    protected abstract String appName(T t);

    protected void onTagClick(T t) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected int layoutId(int i) {
        return R.layout.app_recycler_item;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
        T item = getItem(i);
        View view = viewHolder.getView(R.id.app_item);
        View view2 = viewHolder.getView(R.id.app_line);
        ((TextView) viewHolder.getView(R.id.app_name)).setText(appName(item));
        ((ImageView) viewHolder.getView(R.id.app_icon)).setImageDrawable(appIcon(item));
        if (getData().size() < 2) {
            view.setBackgroundResource(R.drawable.x_list_item_selector);
            view2.setVisibility(4);
        } else if (i == 0) {
            view.setBackgroundResource(R.drawable.list_item_top_arc_bg);
            view2.setVisibility(0);
        } else if (i == getData().size() - 1) {
            view.setBackgroundResource(R.drawable.list_item_bottom_arc_bg);
            view2.setVisibility(4);
        } else {
            view.setBackgroundResource(R.drawable.list_item_center_bg);
            view2.setVisibility(0);
        }
        View view3 = viewHolder.getView(R.id.app_tag);
        view3.setTag(item);
        view3.setOnClickListener(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.app_tag) {
            onTagClick(view.getTag());
        }
    }
}
