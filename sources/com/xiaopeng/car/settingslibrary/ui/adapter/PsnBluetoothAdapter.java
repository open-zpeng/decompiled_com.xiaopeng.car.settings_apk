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
import com.xiaopeng.car.settingslibrary.manager.bluetooth.PsnBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.PsnBluetoothAdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.common.PsnBluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.common.PsnBluetoothTypeBean;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.PsnBluetoothViewModel;
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
public class PsnBluetoothAdapter extends BaseAdapter<PsnBluetoothTypeBean> {
    private static final int MSG_PAIR_CONNECT_WHAT = 1100;
    private static final int PAIR_CONNECT_TIMEOUT = 40000;
    public static final String TAG = "PsnBluetoothAdapter";
    private PsnBluetoothOperation mBluetoothOperation;
    private PsnBluetoothTypeBean mHeaderBonded;
    private PsnBluetoothTypeBean mHeaderNew;
    private PsnBluetoothTypeBean mNoneDeviceTip;
    private PairConnectHandler mPairConnectHandler;
    private PsnBluetoothViewModel mPsnBluetoothViewModel;
    private XGroupHeader mXGroupHeaderNew;
    private boolean mClickingBt = false;
    private View.OnClickListener mDisconnectDeviceOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$PsnBluetoothAdapter$mRYnptqYtUSQaxxsQn28tZTRpVo
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            PsnBluetoothAdapter.this.lambda$new$3$PsnBluetoothAdapter(view);
        }
    };
    private PsnBluetoothAdapterRefreshControl mPsnBluetoothRefreshControl = new PsnBluetoothAdapterRefreshControl(this);

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean isControlInterval() {
        return true;
    }

    public PsnBluetoothAdapter(PsnBluetoothOperation psnBluetoothOperation) {
        this.mBluetoothOperation = psnBluetoothOperation;
        setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$PsnBluetoothAdapter$-5YJ0VYKhXusP3xvqFXBoZELU00
            @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
            public final void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, Object obj) {
                PsnBluetoothAdapter.this.lambda$new$0$PsnBluetoothAdapter(baseAdapter, viewHolder, (PsnBluetoothTypeBean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$PsnBluetoothAdapter(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, PsnBluetoothTypeBean psnBluetoothTypeBean) {
        Logs.d("PsnBluetoothAdapter xpbluetooth click " + psnBluetoothTypeBean.getData() + " hashCode:" + psnBluetoothTypeBean.getData().hashCode());
        int type = psnBluetoothTypeBean.getType();
        if (type == 3 || type == 4) {
            PsnBluetoothDeviceInfo data = psnBluetoothTypeBean.getData();
            List<PsnBluetoothTypeBean> data2 = getData();
            if (this.mPsnBluetoothViewModel.isUsbDiscovering()) {
                this.mPsnBluetoothViewModel.usbSearchStop();
            }
            if (this.mBluetoothOperation.connectingDevice(viewHolder, psnBluetoothTypeBean, data2)) {
                if (data != null) {
                    data.setConnectingBusy(true);
                    startPairConnectTimeout(data);
                }
                this.mPsnBluetoothRefreshControl.onRefreshItem(psnBluetoothTypeBean);
                refreshNewHeader();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mHeaderBonded = PsnBluetoothTypeBean.item(null, 0);
        this.mHeaderNew = PsnBluetoothTypeBean.item(null, 1);
        this.mNoneDeviceTip = PsnBluetoothTypeBean.item(null, 5);
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
            Boolean valueOf = Boolean.valueOf(this.mPsnBluetoothViewModel.isUsbDiscovering());
            if (valueOf instanceof Boolean) {
                setScanning(valueOf.booleanValue());
            }
        } else if (defItemViewType == 2) {
            final PsnBluetoothTypeBean item = getItem(i);
            PsnBluetoothDeviceInfo data = item.getData();
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
            xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$PsnBluetoothAdapter$ixw-Besdl60CxBAQGTH1JKBBuac
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PsnBluetoothAdapter.this.lambda$convert$1$PsnBluetoothAdapter(item, view);
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
            PsnBluetoothDeviceInfo data2 = getItem(i).getData();
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
            final PsnBluetoothTypeBean item2 = getItem(i);
            PsnBluetoothDeviceInfo data3 = item2.getData();
            XListSingle xListSingle2 = (XListSingle) viewHolder.getView(R.id.bonded_bluetooth_item);
            XImageButton xImageButton2 = (XImageButton) xListSingle2.findViewById(R.id.x_list_action_button_icon);
            xImageButton2.setVuiDisableHitEffect(true);
            xImageButton2.setImageDrawable(xImageButton2.getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            xListSingle2.setText(data3.getDeviceName());
            setVuiBondedAttrs(xImageButton2, xListSingle2, i, data3.getDeviceName(), data3.getDeviceAddr());
            xImageButton2.setVisibility(0);
            xImageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$PsnBluetoothAdapter$v1qWV6vh185zKAZsEeRtRzPLPnw
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PsnBluetoothAdapter.this.lambda$convert$2$PsnBluetoothAdapter(item2, view);
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
                Logs.d("PsnBluetoothAdapter xpbluetooth ui convert isConnectingBusy:" + data3.isConnectingBusy() + " " + data3.isDisconnectingBusy() + " " + data3.getDeviceName() + " " + item2.hashCode());
            }
        }
    }

    public /* synthetic */ void lambda$convert$1$PsnBluetoothAdapter(PsnBluetoothTypeBean psnBluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(psnBluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$convert$2$PsnBluetoothAdapter(PsnBluetoothTypeBean psnBluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(psnBluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$new$3$PsnBluetoothAdapter(View view) {
        PsnBluetoothDeviceInfo data = ((PsnBluetoothTypeBean) view.getTag()).getData();
        if (data != null) {
            Logs.d("PsnBluetoothAdapter xpbluetooth click disconnect " + data + " isDisconnectingBusy:" + data.isDisconnectingBusy() + " " + data.hashCode());
            if (!data.isDisconnectingBusy() && this.mPsnBluetoothViewModel.isConnected(data) && this.mBluetoothOperation.disconnectDevice(data)) {
                data.setDisconnectingBusy(true);
                refreshItemChange(data);
            }
        }
    }

    public void setViewModel(PsnBluetoothViewModel psnBluetoothViewModel) {
        this.mPsnBluetoothViewModel = psnBluetoothViewModel;
        this.mPsnBluetoothRefreshControl.setBluetoothSettingsViewModel(this.mPsnBluetoothViewModel);
    }

    public void setScanning(boolean z) {
        XGroupHeader xGroupHeader = this.mXGroupHeaderNew;
        if (xGroupHeader != null) {
            if (z) {
                xGroupHeader.showLoading(true);
            } else {
                xGroupHeader.setButtonText(R.string.bluetooth_search);
                this.mXGroupHeaderNew.getRightView().setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.PsnBluetoothAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PsnBluetoothAdapter.this.mPsnBluetoothViewModel.lambda$onUsbBluetoothStateChanged$0$PsnBluetoothViewModel();
                    }
                });
                this.mXGroupHeaderNew.getRightView().post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.PsnBluetoothAdapter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        XTouchAreaUtils.extendTouchArea(PsnBluetoothAdapter.this.mXGroupHeaderNew.getRightView(), PsnBluetoothAdapter.this.mXGroupHeaderNew, new int[]{(112 - PsnBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - PsnBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2, (112 - PsnBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - PsnBluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2});
                    }
                });
            }
            this.mXGroupHeaderNew.getRightView().setEnabled(!hasConnectingDevices());
        }
    }

    private boolean hasConnectingDevices() {
        for (PsnBluetoothTypeBean psnBluetoothTypeBean : getData()) {
            PsnBluetoothDeviceInfo data = psnBluetoothTypeBean.getData();
            if (data != null && data.isConnectingBusy()) {
                Logs.d("PsnBluetoothAdapter xpbluetooth nf current busy ! " + psnBluetoothTypeBean);
                return true;
            }
        }
        return false;
    }

    public void onRefreshData() {
        ArrayList<PsnBluetoothTypeBean> arrayList = new ArrayList<>();
        boolean isUsbEnable = this.mPsnBluetoothViewModel.isUsbEnable();
        Logs.d("PsnBluetoothAdapter xpbluetooth onRefreshData mClickingBt:" + this.mClickingBt + " " + isUsbEnable);
        if (this.mClickingBt || !isUsbEnable) {
            this.mPsnBluetoothRefreshControl.onRefreshData(arrayList);
            this.mPsnBluetoothViewModel.showTtsSwitch(false);
            Logs.v("PsnBluetoothAdapter xpbluetooth onRefreshData bt off!");
            return;
        }
        ArrayList<PsnBluetoothDeviceInfo> bondedDevicesSorted = this.mPsnBluetoothViewModel.getBondedDevicesSorted();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (bondedDevicesSorted != null) {
            Iterator<PsnBluetoothDeviceInfo> it = bondedDevicesSorted.iterator();
            while (it.hasNext()) {
                PsnBluetoothDeviceInfo next = it.next();
                if (this.mPsnBluetoothViewModel.isConnected(next)) {
                    arrayList2.add(next);
                    Logs.d("PsnBluetoothAdapter xpbluetooth ui connectedDevices " + next + " hasCode:" + next.hashCode());
                } else {
                    arrayList3.add(next);
                    Logs.d("PsnBluetoothAdapter xpbluetooth ui bondedDevices " + next + " hasCode:" + next.hashCode());
                }
            }
        }
        ArrayList<PsnBluetoothDeviceInfo> availableDevicesSorted = this.mPsnBluetoothViewModel.getAvailableDevicesSorted();
        if (this.mPsnBluetoothViewModel != null && isUsbEnable && bondedDevicesSorted != null && !bondedDevicesSorted.isEmpty()) {
            arrayList.add(this.mHeaderBonded);
        }
        arrayList.addAll(PsnBluetoothTypeBean.list(arrayList2, 2));
        arrayList.addAll(PsnBluetoothTypeBean.list(arrayList4, 3));
        arrayList.addAll(PsnBluetoothTypeBean.list(arrayList3, 3));
        if (this.mPsnBluetoothViewModel != null && isUsbEnable) {
            arrayList.add(this.mHeaderNew);
            if (!availableDevicesSorted.isEmpty()) {
                arrayList.addAll(PsnBluetoothTypeBean.list(availableDevicesSorted, 4));
            } else {
                arrayList.add(this.mNoneDeviceTip);
            }
        }
        this.mBluetoothOperation.refreshDevicePopup();
        this.mPsnBluetoothRefreshControl.onRefreshData(arrayList);
        if (arrayList2.size() > 0) {
            this.mPsnBluetoothViewModel.showTtsSwitch(true);
        } else {
            this.mPsnBluetoothViewModel.showTtsSwitch(false);
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
        for (PsnBluetoothTypeBean psnBluetoothTypeBean : getData()) {
            if (psnBluetoothTypeBean.getType() == 1) {
                lambda$refreshItem$10$BaseAdapter(getData().indexOf(psnBluetoothTypeBean));
                return;
            }
        }
    }

    public void refreshItemChange(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        Iterator<PsnBluetoothTypeBean> it = getData().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PsnBluetoothTypeBean next = it.next();
            if (next.getData() != null && next.getData().equals(psnBluetoothDeviceInfo)) {
                updateItemLoadingStatus(psnBluetoothDeviceInfo);
                Logs.d("PsnBluetoothAdapter xpbluetooth refreshItem " + psnBluetoothDeviceInfo + " isConnecting:" + psnBluetoothDeviceInfo.isConnectingBusy() + " isDisconnecting:" + psnBluetoothDeviceInfo.isDisconnectingBusy() + " " + next.hashCode());
                this.mPsnBluetoothRefreshControl.onRefreshItem(next);
                break;
            }
        }
        refreshNewHeader();
    }

    public void updateAllBusyStatusAndRefresh() {
        for (PsnBluetoothTypeBean psnBluetoothTypeBean : getData()) {
            PsnBluetoothDeviceInfo data = psnBluetoothTypeBean.getData();
            if (data != null) {
                updateItemLoadingStatus(data);
            }
        }
        refreshAll();
    }

    private void updateItemLoadingStatus(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        psnBluetoothDeviceInfo.setConnectingBusy(this.mPsnBluetoothViewModel.isConnecting(psnBluetoothDeviceInfo));
        if (this.mPsnBluetoothViewModel.isDisconnecting(psnBluetoothDeviceInfo)) {
            return;
        }
        psnBluetoothDeviceInfo.setDisconnectingBusy(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PairConnectHandler extends Handler {
        private final WeakReference<PsnBluetoothAdapter> bluetoothAdapter;

        public PairConnectHandler(PsnBluetoothAdapter psnBluetoothAdapter) {
            this.bluetoothAdapter = new WeakReference<>(psnBluetoothAdapter);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PsnBluetoothDeviceInfo bluetoothDeviceInfo;
            super.handleMessage(message);
            PsnBluetoothAdapter psnBluetoothAdapter = this.bluetoothAdapter.get();
            if (psnBluetoothAdapter != null && message.what == PsnBluetoothAdapter.MSG_PAIR_CONNECT_WHAT) {
                String str = (String) message.obj;
                if (TextUtils.isEmpty(str) || psnBluetoothAdapter.mPsnBluetoothViewModel == null || (bluetoothDeviceInfo = psnBluetoothAdapter.mPsnBluetoothViewModel.getBluetoothDeviceInfo(str)) == null) {
                    return;
                }
                psnBluetoothAdapter.refreshItemChange(bluetoothDeviceInfo);
                Logs.d("PsnBluetoothAdapter xpbluetooth PairConnectHandler refreshItemChange " + bluetoothDeviceInfo);
            }
        }
    }

    private void startPairConnectTimeout(PsnBluetoothDeviceInfo psnBluetoothDeviceInfo) {
        if (this.mPairConnectHandler == null) {
            this.mPairConnectHandler = new PairConnectHandler(this);
        }
        Message obtainMessage = this.mPairConnectHandler.obtainMessage();
        obtainMessage.what = MSG_PAIR_CONNECT_WHAT;
        obtainMessage.obj = psnBluetoothDeviceInfo.getDeviceAddr();
        this.mPairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        this.mPairConnectHandler.sendMessageDelayed(obtainMessage, 40000L);
        Logs.d("PsnBluetoothAdapter xpbluetooth startPairConnectTimeout");
    }

    public void cancelPairConnectTimeout() {
        PairConnectHandler pairConnectHandler = this.mPairConnectHandler;
        if (pairConnectHandler != null) {
            pairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        }
    }
}
