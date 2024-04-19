package com.xiaopeng.car.settingslibrary.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseListView;
import com.xiaopeng.car.settingslibrary.vm.usb.UsbSettingsViewModel;
import com.xiaopeng.car.settingslibrary.vm.usb.XpUsbDeviceInfo;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XProgressBar;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class UsbView extends BaseListView {
    private View mNoDataView;
    private UsbAdapter mUsbAdapter;
    private UsbSettingsViewModel mUsbSettingViewModel;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    public void setSceneId(String str) {
    }

    public UsbView(Context context) {
        super(context);
    }

    public UsbView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public UsbView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public UsbView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected int layoutId() {
        return R.layout.fragment_xdialog_list;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void init(Context context, View view) {
        this.mNoDataView = view.findViewById(R.id.no_data);
        ((TextView) view.findViewById(R.id.no_data_text)).setText(R.string.sm_device_panel_no_data);
        this.mUsbSettingViewModel = new UsbSettingsViewModel(CarSettingsApp.getApp());
        this.mUsbAdapter = new UsbAdapter();
        this.mRecyclerView.setAdapter(this.mUsbAdapter);
        UsbSettingsViewModel usbSettingsViewModel = this.mUsbSettingViewModel;
        if (usbSettingsViewModel == null) {
            Logs.d("init vm null");
        } else {
            usbSettingsViewModel.getListLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$UsbView$RT0qte9H0sxUAo93hImsBHRWtnc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    UsbView.this.lambda$init$0$UsbView((List) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$init$0$UsbView(List list) {
        this.mUsbAdapter.lambda$setData$1$BaseAdapter(list);
        Logs.d("xpusbfloating xpUsbDeviceInfos.size:" + list.size());
        this.mNoDataView.setVisibility(list.size() == 0 ? 0 : 8);
    }

    private List<XpUsbDeviceInfo> mock() {
        ArrayList arrayList = new ArrayList();
        XpUsbDeviceInfo xpUsbDeviceInfo = new XpUsbDeviceInfo();
        xpUsbDeviceInfo.setDeviceName("DISK");
        xpUsbDeviceInfo.setCategory(2);
        xpUsbDeviceInfo.setConnected(false);
        xpUsbDeviceInfo.setCapacity(100000000L);
        arrayList.add(xpUsbDeviceInfo);
        XpUsbDeviceInfo xpUsbDeviceInfo2 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo2.setDeviceName("DISK2");
        xpUsbDeviceInfo2.setCategory(2);
        xpUsbDeviceInfo2.setConnected(true);
        xpUsbDeviceInfo2.setCapacity(23234000L);
        xpUsbDeviceInfo2.setUsedSpace(232323L);
        arrayList.add(xpUsbDeviceInfo2);
        XpUsbDeviceInfo xpUsbDeviceInfo3 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo3.setDeviceName("MICROPHONE");
        xpUsbDeviceInfo3.setCategory(3);
        arrayList.add(xpUsbDeviceInfo3);
        XpUsbDeviceInfo xpUsbDeviceInfo4 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo4.setDeviceName("MICROPHONE");
        xpUsbDeviceInfo4.setCategory(3);
        xpUsbDeviceInfo4.setConnected(true);
        arrayList.add(xpUsbDeviceInfo4);
        XpUsbDeviceInfo xpUsbDeviceInfo5 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo5.setDeviceName("PHONE");
        xpUsbDeviceInfo5.setCategory(1);
        arrayList.add(xpUsbDeviceInfo5);
        XpUsbDeviceInfo xpUsbDeviceInfo6 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo6.setDeviceName("PLAYER");
        xpUsbDeviceInfo6.setCategory(4);
        arrayList.add(xpUsbDeviceInfo6);
        XpUsbDeviceInfo xpUsbDeviceInfo7 = new XpUsbDeviceInfo();
        xpUsbDeviceInfo7.setDeviceName("USB");
        xpUsbDeviceInfo7.setCategory(0);
        arrayList.add(xpUsbDeviceInfo7);
        return arrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void start() {
        if (this.mUsbSettingViewModel == null) {
            Logs.d("resume vm null");
            return;
        }
        Logs.d("xpsettings usb onResume");
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$UsbView$tI4LssuuBoc7F1LK9DFY3EYFj9o
            @Override // java.lang.Runnable
            public final void run() {
                UsbView.this.lambda$start$1$UsbView();
            }
        });
        this.mUsbSettingViewModel.registerBroadcast();
        this.mUsbSettingViewModel.registerListener();
        this.mUsbSettingViewModel.registerOfficialKaraokPowerListener();
    }

    public /* synthetic */ void lambda$start$1$UsbView() {
        this.mUsbSettingViewModel.startQuery();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseListView
    protected void stop() {
        if (this.mUsbSettingViewModel == null) {
            return;
        }
        Logs.d("xpsettings usb onPause");
        this.mUsbSettingViewModel.unRegisterListener();
        this.mUsbSettingViewModel.unRegisterBroadcast();
        this.mUsbSettingViewModel.unRegisterOfficiallKaraokPowerListener();
        this.mUsbSettingViewModel.clear();
    }

    /* loaded from: classes.dex */
    private class UsbAdapter extends BaseAdapter<XpUsbDeviceInfo> {
        private UsbAdapter() {
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.floating_usb_recycler_item_new;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            XLoading xLoading;
            String string;
            double d;
            String str;
            int i2;
            XpUsbDeviceInfo item = getItem(i);
            XListTwo xListTwo = (XListTwo) viewHolder.getView(R.id.usb_item_list);
            xListTwo.setTextSub(UsbView.this.getContext().getString(R.string.sm_device_panel_connect));
            XLoading xLoading2 = (XLoading) xListTwo.findViewById(R.id.xloading);
            ImageView imageView = (ImageView) xListTwo.findViewById(R.id.icon1);
            TextView textView = (TextView) viewHolder.getView(R.id.text2);
            XProgressBar xProgressBar = (XProgressBar) viewHolder.getView(R.id.progressBar);
            if (UsbView.this.getContext() != null) {
                xListTwo.setText(TextUtils.isEmpty(item.getDeviceName().trim()) ? UsbView.this.getContext().getString(R.string.sm_device_panel_unknow_device) : item.getDeviceName().trim());
                if (item.getCategory() != 2) {
                    textView.setVisibility(8);
                    xProgressBar.setVisibility(8);
                    xLoading = xLoading2;
                    i2 = 1;
                } else {
                    textView.setVisibility(0);
                    xProgressBar.setVisibility(0);
                    if (item.isConnected()) {
                        string = Utils.getFormatSize(item.getCapacity());
                        str = Utils.getFormatSize(item.getUsedSpace());
                        d = item.getUsedSpace() / item.getCapacity();
                    } else {
                        string = UsbView.this.getContext().getString(R.string.sm_device_panel_default_capacity);
                        d = 0.0d;
                        xListTwo.setText(UsbView.this.getContext().getString(R.string.sm_device_panel_unknow_device));
                        xListTwo.setTextSub(UsbView.this.getContext().getString(R.string.sm_device_panel_connect));
                        str = string;
                    }
                    Context context = UsbView.this.getContext();
                    int i3 = R.string.sd_space;
                    xLoading = xLoading2;
                    String replaceAll = string.replaceAll(" ", "");
                    i2 = 1;
                    textView.setText(context.getString(i3, str.replaceAll(" ", ""), replaceAll));
                    xProgressBar.setProgress((int) (d * 100.0d));
                }
                int category = item.getCategory();
                if (category == 0) {
                    imageView.setImageResource(R.drawable.ic_mid_drop_usb);
                } else if (category == i2) {
                    imageView.setImageResource(R.drawable.ic_mid_drop_phone);
                } else if (category != 2) {
                    if (category == 3) {
                        imageView.setImageResource(R.drawable.ic_mid_drop_microphone);
                        xListTwo.setText(UsbView.this.getContext().getString(R.string.sm_device_panel_officialmic));
                    } else if (category == 4) {
                        imageView.setImageResource(R.drawable.ic_mid_drop_mp3);
                    }
                } else if (item.isConnected()) {
                    imageView.setImageResource(R.drawable.ic_mid_drop_flashdrive);
                } else {
                    imageView.setImageResource(R.drawable.ic_mid_usb_loading);
                }
            } else {
                xLoading = xLoading2;
            }
            if (item.getCategory() == 3) {
                if (item.isConnected()) {
                    xLoading.setVisibility(4);
                    imageView.setVisibility(0);
                    xListTwo.setTextSub(UsbView.this.getContext().getString(R.string.sm_device_panel_connect));
                    return;
                }
                xLoading.setVisibility(0);
                xListTwo.setTextSub(UsbView.this.getContext().getString(R.string.sm_device_panel_connecting));
                imageView.setVisibility(4);
                return;
            }
            xLoading.setVisibility(4);
            imageView.setVisibility(0);
            xListTwo.setTextSub(UsbView.this.getContext().getString(R.string.sm_device_panel_connect));
        }
    }
}
