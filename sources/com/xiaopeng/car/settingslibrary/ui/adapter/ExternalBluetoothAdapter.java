package com.xiaopeng.car.settingslibrary.ui.adapter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.backseat.base.ExternalBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.ExternalBluetoothAdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.common.ExternalBluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.common.ExternalBluetoothTypeBean;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.ExternalBluetoothViewModel;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XLoading;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class ExternalBluetoothAdapter extends BaseAdapter<ExternalBluetoothTypeBean> {
    private static final int MSG_PAIR_CONNECT_WHAT = 1100;
    private static final int PAIR_CONNECT_TIMEOUT = 40000;
    public static final String TAG = "ExternalBluetoothAdapter";
    private ExternalBluetoothOperation mBluetoothOperation;
    private boolean mClickingBt = false;
    private View.OnClickListener mDisconnectDeviceOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$ExternalBluetoothAdapter$ftmhSzM_IdHFN0XjAvsxrgsF0ec
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            ExternalBluetoothAdapter.this.lambda$new$3$ExternalBluetoothAdapter(view);
        }
    };
    private ExternalBluetoothAdapterRefreshControl mExternalBluetoothRefreshControl = new ExternalBluetoothAdapterRefreshControl(this);
    private ExternalBluetoothViewModel mExternalBluetoothViewModel;
    private ExternalBluetoothTypeBean mHeaderBonded;
    private ExternalBluetoothTypeBean mHeaderNew;
    private ExternalBluetoothTypeBean mNoneDeviceTip;
    private PairConnectHandler mPairConnectHandler;
    private XGroupHeader mXGroupHeaderNew;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean isControlInterval() {
        return true;
    }

    public ExternalBluetoothAdapter(ExternalBluetoothOperation externalBluetoothOperation) {
        this.mBluetoothOperation = externalBluetoothOperation;
        setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$ExternalBluetoothAdapter$PkLfJriIAKZ8a622QA6f-KprVSQ
            @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
            public final void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, Object obj) {
                ExternalBluetoothAdapter.this.lambda$new$0$ExternalBluetoothAdapter(baseAdapter, viewHolder, (ExternalBluetoothTypeBean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$ExternalBluetoothAdapter(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, ExternalBluetoothTypeBean externalBluetoothTypeBean) {
        Logs.d("ExternalBluetoothAdapter xpbluetooth click " + externalBluetoothTypeBean.getData() + " hashCode:" + externalBluetoothTypeBean.getData().hashCode());
        int type = externalBluetoothTypeBean.getType();
        if (type == 3 || type == 4) {
            ExternalBluetoothDeviceInfo data = externalBluetoothTypeBean.getData();
            List<ExternalBluetoothTypeBean> data2 = getData();
            if (this.mExternalBluetoothViewModel.isUsbDiscovering()) {
                this.mExternalBluetoothViewModel.usbSearchStop();
            }
            if (this.mBluetoothOperation.connectingDevice(viewHolder, externalBluetoothTypeBean, data2)) {
                if (data != null) {
                    data.setConnectingBusy(true);
                    startPairConnectTimeout(data);
                }
                this.mExternalBluetoothRefreshControl.onRefreshItem(externalBluetoothTypeBean);
                refreshNewHeader();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mHeaderBonded = ExternalBluetoothTypeBean.item(null, 0);
        this.mHeaderNew = ExternalBluetoothTypeBean.item(null, 1);
        this.mNoneDeviceTip = ExternalBluetoothTypeBean.item(null, 5);
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected int layoutId(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i == 5) {
                                return R.layout.bluetooth_recycler_item_none_device_tip;
                            }
                            return R.layout.recycler_item_nothing;
                        }
                        return R.layout.bluetooth_recycler_item_new_device;
                    }
                    return R.layout.bluetooth_recycler_item_bonded_device;
                }
                return R.layout.bluetooth_recycler_item_connected_device;
            }
            return R.layout.bluetooth_recycler_item_groupheader_new;
        }
        return R.layout.bluetooth_recycler_item_groupheader_binding;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected int getDefItemViewType(int i) {
        return getItem(i).getType();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean itemClickable(int i) {
        return getDefItemViewType(i) == 4 || getDefItemViewType(i) == 3;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
        int defItemViewType = getDefItemViewType(i);
        if (defItemViewType == 1) {
            this.mXGroupHeaderNew = (XGroupHeader) viewHolder.getView(R.id.gh_new_bluetooth_item);
            Boolean valueOf = Boolean.valueOf(this.mExternalBluetoothViewModel.isUsbDiscovering());
            if (valueOf instanceof Boolean) {
                setScanning(valueOf.booleanValue());
            }
        } else if (defItemViewType == 2) {
            final ExternalBluetoothTypeBean item = getItem(i);
            ExternalBluetoothDeviceInfo data = item.getData();
            XListTwo xListTwo = (XListTwo) viewHolder.getView(R.id.connected_bluetooth_item);
            XImageButton xImageButton = (XImageButton) xListTwo.findViewById(R.id.x_list_action_button_icon);
            xImageButton.setVuiDisableHitEffect(true);
            xImageButton.setImageDrawable(xImageButton.getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            xListTwo.setSelected(true);
            xListTwo.setText(data.getDeviceName());
            setVuiConnectedAttrs(xImageButton, xListTwo, i, data.getDeviceName(), data.getDeviceAddr());
            View findViewById = xListTwo.findViewById(R.id.bluetooth_list_action_button);
            if (findViewById != null) {
                if (findViewById instanceof TextView) {
                    ((TextView) findViewById).setText(findViewById.getContext().getString(R.string.disconnect));
                } else if (findViewById instanceof ImageView) {
                    ((ImageView) findViewById).setImageResource(R.drawable.ic_small_break);
                }
                findViewById.setTag(item);
                if (data != null && data.isDisconnectingBusy()) {
                    findViewById.setEnabled(false);
                } else {
                    findViewById.setEnabled(true);
                }
                findViewById.setOnClickListener(this.mDisconnectDeviceOnClickListener);
            }
            xImageButton.setVisibility(0);
            xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$ExternalBluetoothAdapter$mFVW3HGn2fUInw60zcfyqtJkur8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExternalBluetoothAdapter.this.lambda$convert$1$ExternalBluetoothAdapter(item, view);
                }
            });
            ViewGroup viewGroup = (ViewGroup) xListTwo.findViewById(R.id.device_type_custom);
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
            }
        } else if (defItemViewType != 3) {
            if (defItemViewType != 4) {
                return;
            }
            ExternalBluetoothDeviceInfo data2 = getItem(i).getData();
            XListSingle xListSingle = (XListSingle) viewHolder.getView(R.id.new_device_bluetooth_item);
            xListSingle.setText(data2.getDeviceName());
            setVuiNewDeviceAttrs(xListSingle, i, data2.getDeviceName(), data2.getDeviceAddr());
            ImageView imageView = (ImageView) xListSingle.findViewById(R.id.bluetooth_icon);
            imageView.setImageResource(R.drawable.ic_mid_headset);
            XLoading xLoading = (XLoading) xListSingle.findViewById(R.id.x_list_action_loading);
            if (data2 != null && data2.isConnectingBusy()) {
                xLoading.setVisibility(0);
                imageView.setVisibility(4);
                return;
            }
            xLoading.setVisibility(4);
            imageView.setVisibility(0);
        } else {
            final ExternalBluetoothTypeBean item2 = getItem(i);
            ExternalBluetoothDeviceInfo data3 = item2.getData();
            XListSingle xListSingle2 = (XListSingle) viewHolder.getView(R.id.bonded_bluetooth_item);
            XImageButton xImageButton2 = (XImageButton) xListSingle2.findViewById(R.id.x_list_action_button_icon);
            xImageButton2.setVuiDisableHitEffect(true);
            xImageButton2.setImageDrawable(xImageButton2.getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            xListSingle2.setText(data3.getDeviceName());
            setVuiBondedAttrs(xImageButton2, xListSingle2, i, data3.getDeviceName(), data3.getDeviceAddr());
            xImageButton2.setVisibility(0);
            xImageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$ExternalBluetoothAdapter$Qr2C489r9Dy-_piVu_j0T7baWII
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExternalBluetoothAdapter.this.lambda$convert$2$ExternalBluetoothAdapter(item2, view);
                }
            });
            ImageView imageView2 = (ImageView) xListSingle2.findViewById(R.id.bluetooth_icon);
            imageView2.setImageResource(R.drawable.ic_mid_headsethistory);
            XLoading xLoading2 = (XLoading) xListSingle2.findViewById(R.id.x_list_action_loading);
            if (data3 != null && (data3.isConnectingBusy() || data3.isDisconnectingBusy())) {
                xLoading2.setVisibility(0);
                imageView2.setVisibility(4);
            } else {
                xLoading2.setVisibility(4);
                imageView2.setVisibility(0);
            }
            if (data3 != null) {
                Logs.d("ExternalBluetoothAdapter xpbluetooth ui convert isConnectingBusy:" + data3.isConnectingBusy() + " " + data3.isDisconnectingBusy() + " " + data3.getDeviceName() + " " + item2.hashCode());
            }
        }
    }

    public /* synthetic */ void lambda$convert$1$ExternalBluetoothAdapter(ExternalBluetoothTypeBean externalBluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(externalBluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$convert$2$ExternalBluetoothAdapter(ExternalBluetoothTypeBean externalBluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(externalBluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$new$3$ExternalBluetoothAdapter(View view) {
        ExternalBluetoothDeviceInfo data = ((ExternalBluetoothTypeBean) view.getTag()).getData();
        if (data != null) {
            Logs.d("ExternalBluetoothAdapter xpbluetooth click disconnect " + data + " isDisconnectingBusy:" + data.isDisconnectingBusy() + " " + data.hashCode());
            if (!data.isDisconnectingBusy() && this.mExternalBluetoothViewModel.isConnected(data) && this.mBluetoothOperation.disconnectDevice(data)) {
                data.setDisconnectingBusy(true);
                refreshItemChange(data);
            }
        }
    }

    public void setViewModel(ExternalBluetoothViewModel externalBluetoothViewModel) {
        this.mExternalBluetoothViewModel = externalBluetoothViewModel;
        this.mExternalBluetoothRefreshControl.setBluetoothSettingsViewModel(this.mExternalBluetoothViewModel);
    }

    public void setScanning(boolean z) {
        XGroupHeader xGroupHeader = this.mXGroupHeaderNew;
        if (xGroupHeader != null) {
            if (z) {
                xGroupHeader.showLoading(true);
            } else {
                xGroupHeader.setButtonText(R.string.bluetooth_search);
                this.mXGroupHeaderNew.getRightView().setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.ExternalBluetoothAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ExternalBluetoothAdapter.this.mExternalBluetoothViewModel.lambda$onBluetoothStateChanged$0$ExternalBluetoothViewModel();
                    }
                });
                this.mXGroupHeaderNew.getRightView().post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.ExternalBluetoothAdapter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        XTouchAreaUtils.extendTouchArea(ExternalBluetoothAdapter.this.mXGroupHeaderNew.getRightView(), ExternalBluetoothAdapter.this.mXGroupHeaderNew, new int[]{(112 - ExternalBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - ExternalBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2, (112 - ExternalBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - ExternalBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2});
                    }
                });
            }
            this.mXGroupHeaderNew.getRightView().setEnabled(!hasConnectingDevices());
        }
    }

    private boolean hasConnectingDevices() {
        for (ExternalBluetoothTypeBean externalBluetoothTypeBean : getData()) {
            ExternalBluetoothDeviceInfo data = externalBluetoothTypeBean.getData();
            if (data != null && data.isConnectingBusy()) {
                Logs.d("ExternalBluetoothAdapter xpbluetooth nf current busy ! " + externalBluetoothTypeBean);
                return true;
            }
        }
        return false;
    }

    public void onRefreshData() {
        ArrayList<ExternalBluetoothTypeBean> arrayList = new ArrayList<>();
        boolean isUsbEnable = this.mExternalBluetoothViewModel.isUsbEnable();
        Logs.d("ExternalBluetoothAdapter xpbluetooth onRefreshData mClickingBt:" + this.mClickingBt + " " + isUsbEnable);
        if (this.mClickingBt || !isUsbEnable) {
            this.mExternalBluetoothRefreshControl.onRefreshData(arrayList);
            this.mExternalBluetoothViewModel.showTtsSwitch(false);
            Logs.v("ExternalBluetoothAdapter xpbluetooth onRefreshData bt off!");
            return;
        }
        ArrayList<ExternalBluetoothDeviceInfo> bondedDevicesSorted = this.mExternalBluetoothViewModel.getBondedDevicesSorted();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (bondedDevicesSorted != null) {
            Iterator<ExternalBluetoothDeviceInfo> it = bondedDevicesSorted.iterator();
            while (it.hasNext()) {
                ExternalBluetoothDeviceInfo next = it.next();
                if (this.mExternalBluetoothViewModel.isConnected(next)) {
                    arrayList2.add(next);
                    Logs.d("ExternalBluetoothAdapter xpbluetooth ui connectedDevices " + next + " hasCode:" + next.hashCode());
                } else {
                    arrayList3.add(next);
                    Logs.d("ExternalBluetoothAdapter xpbluetooth ui bondedDevices " + next + " hasCode:" + next.hashCode());
                }
            }
        }
        ArrayList<ExternalBluetoothDeviceInfo> availableDevicesSorted = this.mExternalBluetoothViewModel.getAvailableDevicesSorted();
        if (this.mExternalBluetoothViewModel != null && isUsbEnable && bondedDevicesSorted != null && !bondedDevicesSorted.isEmpty()) {
            arrayList.add(this.mHeaderBonded);
        }
        arrayList.addAll(ExternalBluetoothTypeBean.list(arrayList2, 2));
        arrayList.addAll(ExternalBluetoothTypeBean.list(arrayList4, 3));
        arrayList.addAll(ExternalBluetoothTypeBean.list(arrayList3, 3));
        if (this.mExternalBluetoothViewModel != null && isUsbEnable) {
            arrayList.add(this.mHeaderNew);
            if (!availableDevicesSorted.isEmpty()) {
                arrayList.addAll(ExternalBluetoothTypeBean.list(availableDevicesSorted, 4));
            } else {
                arrayList.add(this.mNoneDeviceTip);
            }
        }
        this.mBluetoothOperation.refreshDevicePopup();
        this.mExternalBluetoothRefreshControl.onRefreshData(arrayList);
        if (arrayList2.size() > 0) {
            this.mExternalBluetoothViewModel.showTtsSwitch(true);
        } else {
            this.mExternalBluetoothViewModel.showTtsSwitch(false);
        }
    }

    public void setClickingBt(boolean z) {
        this.mClickingBt = z;
    }

    private void setVuiConnectedAttrs(XImageButton xImageButton, XListTwo xListTwo, int i, String str, String str2) {
        if (!CarSettingsApp.isVuiFeatureEnable()) {
            xListTwo.showNum(false);
        } else {
            xListTwo.showNum(true);
        }
        VuiManager.instance().supportFeedback(xListTwo);
        xImageButton.setVuiLabel(xListTwo.getContext().getString(R.string.vui_label_detail));
        xImageButton.setVuiElementId(xImageButton.getId() + "_" + i);
        xListTwo.setVuiElementId(xListTwo.getId() + "_" + i);
        xListTwo.setVuiBizId(str2);
        xListTwo.setVuiLabel(String.format(xListTwo.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i), str));
        xListTwo.setNum(i);
        xListTwo.setVuiFatherLabel(xListTwo.getContext().getString(R.string.bluetooth_subtitle_bonded));
    }

    private void setVuiBondedAttrs(XImageButton xImageButton, XListSingle xListSingle, int i, String str, String str2) {
        if (!CarSettingsApp.isVuiFeatureEnable()) {
            xListSingle.showNum(false);
        } else {
            xListSingle.showNum(true);
        }
        VuiManager.instance().supportFeedback(xListSingle);
        xListSingle.setVuiBizId(str2);
        xListSingle.setVuiElementId(xListSingle.getId() + "_" + i);
        xListSingle.setVuiAction(xListSingle.getContext().getString(R.string.vui_action_click_connect));
        xImageButton.setVuiLabel(xListSingle.getContext().getString(R.string.vui_label_detail));
        xImageButton.setVuiElementId(xImageButton.getId() + "_" + i);
        xListSingle.setVuiLabel(String.format(xListSingle.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i), str));
        xListSingle.setNum(i);
        xListSingle.setVuiFatherLabel(xListSingle.getContext().getString(R.string.bluetooth_subtitle_bonded));
    }

    private void setVuiNewDeviceAttrs(XListSingle xListSingle, int i, String str, String str2) {
        if (!CarSettingsApp.isVuiFeatureEnable()) {
            xListSingle.showNum(false);
        } else {
            xListSingle.showNum(true);
        }
        VuiManager.instance().supportFeedback(xListSingle);
        xListSingle.setVuiBizId(str2);
        xListSingle.setVuiElementId(xListSingle.getId() + "_" + i);
        xListSingle.setVuiAction(xListSingle.getContext().getString(R.string.vui_action_click_connect));
        if (getData().get(0).getType() == 0) {
            int i2 = i - 1;
            xListSingle.setVuiLabel(String.format(xListSingle.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i2), str));
            xListSingle.setNum(i2);
        } else {
            xListSingle.setVuiLabel(String.format(xListSingle.getContext().getString(R.string.vui_label_serial_number), Integer.valueOf(i), str));
            xListSingle.setNum(i);
        }
        xListSingle.setVuiFatherLabel(xListSingle.getContext().getString(R.string.bluetooth_subtitle_new));
    }

    private void refreshNewHeader() {
        for (ExternalBluetoothTypeBean externalBluetoothTypeBean : getData()) {
            if (externalBluetoothTypeBean.getType() == 1) {
                lambda$refreshItem$10$BaseAdapter(getData().indexOf(externalBluetoothTypeBean));
                return;
            }
        }
    }

    public void refreshItemChange(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        Iterator<ExternalBluetoothTypeBean> it = getData().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ExternalBluetoothTypeBean next = it.next();
            if (next.getData() != null && next.getData().equals(externalBluetoothDeviceInfo)) {
                updateItemLoadingStatus(externalBluetoothDeviceInfo);
                Logs.d("ExternalBluetoothAdapter xpbluetooth refreshItem " + externalBluetoothDeviceInfo + " isConnecting:" + externalBluetoothDeviceInfo.isConnectingBusy() + " isDisconnecting:" + externalBluetoothDeviceInfo.isDisconnectingBusy() + " " + next.hashCode());
                this.mExternalBluetoothRefreshControl.onRefreshItem(next);
                break;
            }
        }
        refreshNewHeader();
    }

    public void updateAllBusyStatusAndRefresh() {
        for (ExternalBluetoothTypeBean externalBluetoothTypeBean : getData()) {
            ExternalBluetoothDeviceInfo data = externalBluetoothTypeBean.getData();
            if (data != null) {
                updateItemLoadingStatus(data);
            }
        }
        refreshAll();
    }

    private void updateItemLoadingStatus(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        externalBluetoothDeviceInfo.setConnectingBusy(this.mExternalBluetoothViewModel.isConnecting(externalBluetoothDeviceInfo));
        if (this.mExternalBluetoothViewModel.isDisconnecting(externalBluetoothDeviceInfo)) {
            return;
        }
        externalBluetoothDeviceInfo.setDisconnectingBusy(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PairConnectHandler extends Handler {
        private final WeakReference<ExternalBluetoothAdapter> bluetoothAdapter;

        public PairConnectHandler(ExternalBluetoothAdapter externalBluetoothAdapter) {
            this.bluetoothAdapter = new WeakReference<>(externalBluetoothAdapter);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ExternalBluetoothDeviceInfo bluetoothDeviceInfo;
            super.handleMessage(message);
            ExternalBluetoothAdapter externalBluetoothAdapter = this.bluetoothAdapter.get();
            if (externalBluetoothAdapter != null && message.what == ExternalBluetoothAdapter.MSG_PAIR_CONNECT_WHAT) {
                String str = (String) message.obj;
                if (TextUtils.isEmpty(str) || externalBluetoothAdapter.mExternalBluetoothViewModel == null || (bluetoothDeviceInfo = externalBluetoothAdapter.mExternalBluetoothViewModel.getBluetoothDeviceInfo(str)) == null) {
                    return;
                }
                externalBluetoothAdapter.refreshItemChange(bluetoothDeviceInfo);
                Logs.d("ExternalBluetoothAdapter xpbluetooth PairConnectHandler refreshItemChange " + bluetoothDeviceInfo);
            }
        }
    }

    private void startPairConnectTimeout(ExternalBluetoothDeviceInfo externalBluetoothDeviceInfo) {
        if (this.mPairConnectHandler == null) {
            this.mPairConnectHandler = new PairConnectHandler(this);
        }
        Message obtainMessage = this.mPairConnectHandler.obtainMessage();
        obtainMessage.what = MSG_PAIR_CONNECT_WHAT;
        obtainMessage.obj = externalBluetoothDeviceInfo.getDeviceAddr();
        this.mPairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        this.mPairConnectHandler.sendMessageDelayed(obtainMessage, 40000L);
        Logs.d("ExternalBluetoothAdapter xpbluetooth startPairConnectTimeout");
    }

    public void cancelPairConnectTimeout() {
        PairConnectHandler pairConnectHandler = this.mPairConnectHandler;
        if (pairConnectHandler != null) {
            pairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        }
    }
}
