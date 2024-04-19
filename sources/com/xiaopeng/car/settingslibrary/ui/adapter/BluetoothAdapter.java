package com.xiaopeng.car.settingslibrary.ui.adapter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothDeviceInfo;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.BluetoothAdapterRefreshControl;
import com.xiaopeng.car.settingslibrary.ui.common.BluetoothOperation;
import com.xiaopeng.car.settingslibrary.ui.common.BluetoothTypeBean;
import com.xiaopeng.car.settingslibrary.ui.view.BluetoothView;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.bluetooth.BluetoothSettingsViewModel;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XTextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public class BluetoothAdapter extends BaseAdapter<BluetoothTypeBean> {
    private static final int MSG_PAIR_CONNECT_WHAT = 1100;
    private static final int PAIR_CONNECT_TIMEOUT = 40000;
    private FrameLayout.LayoutParams indexLayoutParams;
    private BluetoothOperation mBluetoothOperation;
    private BluetoothSettingsViewModel mBluetoothSettingsViewModel;
    private BluetoothView mBluetoothView;
    private BluetoothTypeBean mHeaderBonded;
    private BluetoothTypeBean mHeaderNew;
    private boolean mIsDialog;
    private BluetoothTypeBean mNoneDeviceTip;
    private PairConnectHandler mPairConnectHandler;
    private Button mSearchButton;
    private XGroupHeader mXGroupHeaderNew;
    private boolean mClickingBt = false;
    private View.OnClickListener mDisconnectDeviceOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$PYfOIzxGv9h6oroE4kREw86DO74
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            BluetoothAdapter.this.lambda$new$5$BluetoothAdapter(view);
        }
    };
    private BluetoothAdapterRefreshControl mBluetoothAdapterRefreshControl = new BluetoothAdapterRefreshControl(this);

    private String bondStatus(int i) {
        return "";
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
    protected boolean isControlInterval() {
        return true;
    }

    public BluetoothAdapter(BluetoothOperation bluetoothOperation, boolean z, BluetoothView bluetoothView) {
        this.mBluetoothView = bluetoothView;
        this.mIsDialog = z;
        this.mBluetoothOperation = bluetoothOperation;
        setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$XSVOAlaT0XdPtUFbZ7DfX6_mg7M
            @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
            public final void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, Object obj) {
                BluetoothAdapter.this.lambda$new$0$BluetoothAdapter(baseAdapter, viewHolder, (BluetoothTypeBean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$BluetoothAdapter(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, BluetoothTypeBean bluetoothTypeBean) {
        Logs.d("xpbluetooth click " + bluetoothTypeBean.getData());
        this.mBluetoothSettingsViewModel.stopScanning();
        int type = bluetoothTypeBean.getType();
        if (type == 3) {
            XpBluetoothDeviceInfo data = bluetoothTypeBean.getData();
            if (this.mBluetoothOperation.connectingDevice(viewHolder, bluetoothTypeBean, getData())) {
                if (data != null) {
                    data.setConnectingBusy(true);
                    startPairConnectTimeout(data);
                }
                this.mBluetoothAdapterRefreshControl.onRefreshItem(bluetoothTypeBean);
                refreshNewHeader();
            }
        } else if (type != 4) {
        } else {
            XpBluetoothDeviceInfo data2 = bluetoothTypeBean.getData();
            if (this.mBluetoothOperation.bondingDevice(viewHolder, bluetoothTypeBean, getData())) {
                if (data2 != null) {
                    data2.setParingBusy(true);
                    startPairConnectTimeout(data2);
                }
                this.mBluetoothAdapterRefreshControl.onRefreshItem(bluetoothTypeBean);
                refreshNewHeader();
                if (CarFunction.isNonSelfPageUI() && data2 != null && data2.getCategory() == 1) {
                    String deviceName = data2.getDeviceName();
                    if (!TextUtils.isEmpty(deviceName) && deviceName.length() > 15) {
                        deviceName = deviceName.substring(0, 15) + "...";
                    }
                    ToastUtils.get().showText(CarSettingsApp.getContext().getString(R.string.bluetooth_pairing_request_local, deviceName), this.mBluetoothView.getScreenId());
                }
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mHeaderBonded = BluetoothTypeBean.item(null, 0);
        this.mHeaderNew = BluetoothTypeBean.item(null, 1);
        this.mNoneDeviceTip = BluetoothTypeBean.item(null, 5);
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
            Object isScanning = this.mBluetoothSettingsViewModel.isScanning();
            if (isScanning instanceof Boolean) {
                setScanning(((Boolean) isScanning).booleanValue());
            }
        } else if (defItemViewType != 2) {
            if (defItemViewType != 3) {
                if (defItemViewType != 4) {
                    return;
                }
                XpBluetoothDeviceInfo data = getItem(i).getData();
                XListSingle xListSingle = (XListSingle) viewHolder.getView(R.id.new_device_bluetooth_item);
                xListSingle.setText(data.getDeviceName() + bondStatus(this.mBluetoothSettingsViewModel.getBondState(data)));
                setVuiNewDeviceAttrs(xListSingle, i, data.getDeviceName(), data.getDeviceAddr());
                ImageView imageView = (ImageView) xListSingle.findViewById(R.id.bluetooth_icon);
                imageView.setImageResource(this.mBluetoothOperation.deviceIcon(data, false));
                XLoading xLoading = (XLoading) xListSingle.findViewById(R.id.x_list_action_loading);
                if (data != null && data.isParingBusy()) {
                    xLoading.setVisibility(0);
                    imageView.setVisibility(4);
                    return;
                }
                xLoading.setVisibility(4);
                imageView.setVisibility(0);
                return;
            }
            final BluetoothTypeBean item = getItem(i);
            XpBluetoothDeviceInfo data2 = item.getData();
            XListSingle xListSingle2 = (XListSingle) viewHolder.getView(R.id.bonded_bluetooth_item);
            XImageButton xImageButton = (XImageButton) xListSingle2.findViewById(R.id.x_list_action_button_icon);
            xImageButton.setVuiDisableHitEffect(true);
            xImageButton.setImageDrawable(xImageButton.getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            xListSingle2.setText(data2.getDeviceName() + bondStatus(this.mBluetoothSettingsViewModel.getBondState(data2)));
            setVuiBondedAttrs(xImageButton, xListSingle2, i, data2.getDeviceName(), data2.getDeviceAddr());
            if (this.mIsDialog && !CarFunction.isSupportBlueToothWifiDialogRightInfoIcon()) {
                xImageButton.setVisibility(8);
            } else {
                xImageButton.setVisibility(0);
                xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$8V8BU1R3qt9ii0CsHZVbr08liB8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothAdapter.this.lambda$convert$4$BluetoothAdapter(item, view);
                    }
                });
            }
            ImageView imageView2 = (ImageView) xListSingle2.findViewById(R.id.bluetooth_icon);
            imageView2.setImageResource(this.mBluetoothOperation.deviceIcon(data2, true));
            XLoading xLoading2 = (XLoading) xListSingle2.findViewById(R.id.x_list_action_loading);
            if (data2 != null && (data2.isConnectingBusy() || data2.isDisconnectingBusy())) {
                xLoading2.setVisibility(0);
                imageView2.setVisibility(4);
                return;
            }
            xLoading2.setVisibility(4);
            imageView2.setVisibility(0);
        } else {
            final BluetoothTypeBean item2 = getItem(i);
            XpBluetoothDeviceInfo data3 = item2.getData();
            XListTwo xListTwo = (XListTwo) viewHolder.getView(R.id.connected_bluetooth_item);
            XImageButton xImageButton2 = (XImageButton) xListTwo.findViewById(R.id.x_list_action_button_icon);
            xImageButton2.setImageDrawable(xImageButton2.getContext().getDrawable(R.drawable.x_ic_small_doubt_blue));
            xImageButton2.setVuiDisableHitEffect(true);
            xListTwo.setSelected(true);
            xListTwo.setText(data3.getDeviceName() + bondStatus(this.mBluetoothSettingsViewModel.getBondState(data3)));
            setVuiConnectedAttrs(xImageButton2, xListTwo, i, data3.getDeviceName(), data3.getDeviceAddr());
            View findViewById = xListTwo.findViewById(R.id.bluetooth_list_action_button);
            if (findViewById != null) {
                if (findViewById instanceof TextView) {
                    ((TextView) findViewById).setText(findViewById.getContext().getString(R.string.disconnect));
                } else if (findViewById instanceof ImageView) {
                    ((ImageView) findViewById).setImageResource(R.drawable.ic_small_break);
                }
                findViewById.setTag(item2);
                if (data3 != null && data3.isDisconnectingBusy()) {
                    findViewById.setEnabled(false);
                } else {
                    findViewById.setEnabled(true);
                }
                findViewById.setOnClickListener(this.mDisconnectDeviceOnClickListener);
            }
            if (this.mIsDialog && !CarFunction.isSupportBlueToothWifiDialogRightInfoIcon()) {
                xImageButton2.setVisibility(8);
            } else {
                xImageButton2.setVisibility(0);
                xImageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$AaHWRn52kn2vm1_CDeYk0x13F3Y
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothAdapter.this.lambda$convert$1$BluetoothAdapter(item2, view);
                    }
                });
            }
            ViewGroup viewGroup = (ViewGroup) xListTwo.findViewById(R.id.device_type_custom);
            if (viewGroup != null) {
                XTextView xTextView = (XTextView) viewGroup.findViewById(R.id.icon1);
                XTextView xTextView2 = (XTextView) viewGroup.findViewById(R.id.icon2);
                xTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$uHqBsGCq33gffexgw1UZeBJGucA
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothAdapter.this.lambda$convert$2$BluetoothAdapter(view);
                    }
                });
                xTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.-$$Lambda$BluetoothAdapter$C1xRk6amnI7Zn2SLYKsvm1VfVrI
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothAdapter.this.lambda$convert$3$BluetoothAdapter(view);
                    }
                });
                XTouchAreaUtils.extendTouchArea(new View[]{xTextView, xTextView2}, viewGroup, new int[]{4, 40, 4, 40});
                boolean isBtPhoneConnected = this.mBluetoothSettingsViewModel.isBtPhoneConnected(item2.getData().getDeviceAddr());
                if (isBtPhoneConnected) {
                    xTextView.setSelected(false);
                    xTextView.setEnabled(false);
                    xTextView.setText("");
                } else {
                    xTextView.setSelected(true);
                    xTextView.setEnabled(true);
                    xTextView.setText(R.string.bluetooth_profile_unavailable);
                }
                boolean isA2dpConnected = this.mBluetoothSettingsViewModel.isA2dpConnected(item2.getData().getDeviceAddr());
                if (this.mBluetoothSettingsViewModel.isSourceInBluetooth()) {
                    xTextView2.setSelected(false);
                    xTextView2.setEnabled(false);
                    xTextView2.setText("");
                } else {
                    xTextView2.setSelected(true);
                    xTextView2.setEnabled(true);
                    xTextView2.setText(R.string.bluetooth_profile_unavailable);
                }
                if (isBtPhoneConnected || isA2dpConnected) {
                    viewGroup.setVisibility(0);
                } else {
                    viewGroup.setVisibility(8);
                }
            }
        }
    }

    public /* synthetic */ void lambda$convert$1$BluetoothAdapter(BluetoothTypeBean bluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(bluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$convert$2$BluetoothAdapter(View view) {
        if (view.isSelected()) {
            ToastUtils.get().showText(CarSettingsApp.getContext().getString(R.string.bluetooth_phone_tips), this.mBluetoothView.getScreenId());
        }
    }

    public /* synthetic */ void lambda$convert$3$BluetoothAdapter(View view) {
        if (view.isSelected()) {
            ToastUtils.get().showText(CarSettingsApp.getContext().getString(R.string.bluetooth_a2dp_tips), this.mBluetoothView.getScreenId());
        }
    }

    public /* synthetic */ void lambda$convert$4$BluetoothAdapter(BluetoothTypeBean bluetoothTypeBean, View view) {
        this.mBluetoothOperation.showDevicePopup(bluetoothTypeBean.getData());
    }

    public /* synthetic */ void lambda$new$5$BluetoothAdapter(View view) {
        XpBluetoothDeviceInfo data = ((BluetoothTypeBean) view.getTag()).getData();
        if (data != null) {
            Logs.d("xpbluetooth click disconnect " + data + " isDisconnectingBusy:" + data.isDisconnectingBusy() + " " + data.hashCode());
            if (!data.isDisconnectingBusy() && this.mBluetoothSettingsViewModel.isConnected(data) && this.mBluetoothOperation.disconnectDevice(data)) {
                data.setDisconnectingBusy(true);
                refreshItemChange(data);
            }
        }
    }

    public void setViewModel(BluetoothSettingsViewModel bluetoothSettingsViewModel) {
        this.mBluetoothSettingsViewModel = bluetoothSettingsViewModel;
        this.mBluetoothAdapterRefreshControl.setBluetoothSettingsViewModel(this.mBluetoothSettingsViewModel);
    }

    public void setScanning(boolean z) {
        XGroupHeader xGroupHeader = this.mXGroupHeaderNew;
        if (xGroupHeader != null) {
            if (z) {
                xGroupHeader.showLoading(true);
            } else {
                xGroupHeader.setButtonText(R.string.bluetooth_search);
                this.mXGroupHeaderNew.getRightView().setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.BluetoothAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (BluetoothAdapter.this.mBluetoothSettingsViewModel.startScanList()) {
                            return;
                        }
                        ToastUtils.get().showText(CarSettingsApp.getContext().getString(R.string.bluetooth_busy_tips), BluetoothAdapter.this.mBluetoothView.getScreenId());
                    }
                });
                this.mXGroupHeaderNew.getRightView().post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.adapter.BluetoothAdapter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        XTouchAreaUtils.extendTouchArea(BluetoothAdapter.this.mXGroupHeaderNew.getRightView(), BluetoothAdapter.this.mXGroupHeaderNew, new int[]{(112 - BluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - BluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2, (112 - BluetoothAdapter.this.mXGroupHeaderNew.getRightView().getWidth()) / 2, (112 - BluetoothAdapter.this.mXGroupHeaderNew.getRightView().getHeight()) / 2});
                    }
                });
            }
            this.mXGroupHeaderNew.getRightView().setEnabled(!hasConnectingDevices());
            VuiManager.instance().updateVuiScene(this.mBluetoothView.sceneId, CarSettingsApp.getContext(), this.mXGroupHeaderNew.getRightView());
        }
    }

    private boolean hasConnectingDevices() {
        for (BluetoothTypeBean bluetoothTypeBean : getData()) {
            XpBluetoothDeviceInfo data = bluetoothTypeBean.getData();
            if (data != null && (data.isConnectingBusy() || data.isParingBusy())) {
                return true;
            }
        }
        return false;
    }

    public void onRefreshData() {
        ArrayList<BluetoothTypeBean> arrayList = new ArrayList<>();
        boolean isBtOn = this.mBluetoothSettingsViewModel.isBtOn();
        Logs.d("xpbluetooth onRefreshData mClickingBt:" + this.mClickingBt + " " + isBtOn);
        if (this.mClickingBt || !isBtOn) {
            this.mBluetoothAdapterRefreshControl.onRefreshData(arrayList);
            Logs.v("xpbluetooth onRefreshData bt off!");
            return;
        }
        ArrayList<XpBluetoothDeviceInfo> bondedDevicesSorted = this.mBluetoothSettingsViewModel.getBondedDevicesSorted();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (bondedDevicesSorted != null) {
            Iterator<XpBluetoothDeviceInfo> it = bondedDevicesSorted.iterator();
            while (it.hasNext()) {
                XpBluetoothDeviceInfo next = it.next();
                if (this.mBluetoothSettingsViewModel.isConnected(next)) {
                    arrayList2.add(next);
                    Logs.v("xpbluetooth ui connectedDevices " + next);
                } else {
                    arrayList3.add(next);
                    Logs.v("xpbluetooth ui bondedDevices " + next);
                }
            }
        }
        ArrayList<XpBluetoothDeviceInfo> availableDevicesSorted = this.mBluetoothSettingsViewModel.getAvailableDevicesSorted();
        if (this.mBluetoothSettingsViewModel != null && isBtOn && bondedDevicesSorted != null && !bondedDevicesSorted.isEmpty()) {
            arrayList.add(this.mHeaderBonded);
        }
        arrayList.addAll(BluetoothTypeBean.list(arrayList2, 2));
        arrayList.addAll(BluetoothTypeBean.list(arrayList4, 3));
        arrayList.addAll(BluetoothTypeBean.list(arrayList3, 3));
        if (this.mBluetoothSettingsViewModel != null && isBtOn) {
            arrayList.add(this.mHeaderNew);
            if (!availableDevicesSorted.isEmpty()) {
                arrayList.addAll(BluetoothTypeBean.list(availableDevicesSorted, 4));
            } else {
                arrayList.add(this.mNoneDeviceTip);
            }
        }
        this.mBluetoothOperation.refreshDevicePopup();
        this.mBluetoothAdapterRefreshControl.onRefreshData(arrayList);
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
        for (BluetoothTypeBean bluetoothTypeBean : getData()) {
            if (bluetoothTypeBean.getType() == 1) {
                lambda$refreshItem$10$BaseAdapter(getData().indexOf(bluetoothTypeBean));
                return;
            }
        }
    }

    public void refreshItemChange(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        Iterator<BluetoothTypeBean> it = getData().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BluetoothTypeBean next = it.next();
            if (next.getData() != null && next.getData().equals(xpBluetoothDeviceInfo)) {
                updateItemLoadingStatus(xpBluetoothDeviceInfo);
                this.mBluetoothAdapterRefreshControl.onRefreshItem(next);
                break;
            }
        }
        refreshNewHeader();
    }

    public void updateAllBusyStatusAndRefresh() {
        for (BluetoothTypeBean bluetoothTypeBean : getData()) {
            XpBluetoothDeviceInfo data = bluetoothTypeBean.getData();
            if (data != null) {
                updateItemLoadingStatus(data);
            }
        }
        refreshAll();
    }

    private void updateItemLoadingStatus(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        xpBluetoothDeviceInfo.setConnectingBusy(this.mBluetoothSettingsViewModel.isConnecting(xpBluetoothDeviceInfo));
        if (!this.mBluetoothSettingsViewModel.isDisconnecting(xpBluetoothDeviceInfo)) {
            xpBluetoothDeviceInfo.setDisconnectingBusy(false);
        }
        xpBluetoothDeviceInfo.setParingBusy(this.mBluetoothSettingsViewModel.isParing(xpBluetoothDeviceInfo));
        if (this.mBluetoothSettingsViewModel.isConnected(xpBluetoothDeviceInfo)) {
            xpBluetoothDeviceInfo.setParingBusy(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PairConnectHandler extends Handler {
        private final WeakReference<BluetoothAdapter> bluetoothAdapter;

        public PairConnectHandler(BluetoothAdapter bluetoothAdapter) {
            this.bluetoothAdapter = new WeakReference<>(bluetoothAdapter);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            XpBluetoothDeviceInfo bluetoothDeviceInfo;
            super.handleMessage(message);
            BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter.get();
            if (bluetoothAdapter != null && message.what == BluetoothAdapter.MSG_PAIR_CONNECT_WHAT) {
                String str = (String) message.obj;
                if (TextUtils.isEmpty(str) || bluetoothAdapter.mBluetoothSettingsViewModel == null || (bluetoothDeviceInfo = bluetoothAdapter.mBluetoothSettingsViewModel.getBluetoothDeviceInfo(str)) == null) {
                    return;
                }
                bluetoothAdapter.refreshItemChange(bluetoothDeviceInfo);
                Logs.d("xpbluetooth PairConnectHandler refreshItemChange " + bluetoothDeviceInfo);
            }
        }
    }

    private void startPairConnectTimeout(XpBluetoothDeviceInfo xpBluetoothDeviceInfo) {
        if (this.mPairConnectHandler == null) {
            this.mPairConnectHandler = new PairConnectHandler(this);
        }
        Message obtainMessage = this.mPairConnectHandler.obtainMessage();
        obtainMessage.what = MSG_PAIR_CONNECT_WHAT;
        obtainMessage.obj = xpBluetoothDeviceInfo.getDeviceAddr();
        this.mPairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        this.mPairConnectHandler.sendMessageDelayed(obtainMessage, 40000L);
        Logs.d("xpbluetooth startPairConnectTimeout");
    }

    public void cancelPairConnectTimeout() {
        PairConnectHandler pairConnectHandler = this.mPairConnectHandler;
        if (pairConnectHandler != null) {
            pairConnectHandler.removeMessages(MSG_PAIR_CONNECT_WHAT);
        }
    }
}
