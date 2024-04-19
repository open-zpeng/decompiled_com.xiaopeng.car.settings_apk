package com.xiaopeng.car.settingslibrary.ui.adapter;

import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.xui.widget.XImageView;
/* loaded from: classes.dex */
public class StorageListAdapter extends BaseAdapter<AppStorageBean> {
    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected int layoutId(int i) {
        if (CarFunction.isXmartOS5()) {
            return R.layout.item_storage_v5;
        }
        return R.layout.item_storage;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
        XImageView xImageView = (XImageView) viewHolder.getView(R.id.xiv_icon);
        ((TextView) viewHolder.getView(R.id.tv_apps_name)).setText(getItem(i).getName());
        ((TextView) viewHolder.getView(R.id.tv_storage_size)).setText(FileUtils.getFileSizeDescription(getItem(i).getTotalSize()));
        if (getItem(i).getIcon() != null) {
            xImageView.setImageDrawable(getItem(i).getIcon());
        }
    }

    public int getPositionByPkgName(String str) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getPackageName().equals(str)) {
                return i;
            }
        }
        return -1;
    }
}
