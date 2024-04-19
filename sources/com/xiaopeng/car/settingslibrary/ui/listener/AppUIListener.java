package com.xiaopeng.car.settingslibrary.ui.listener;

import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener;
import com.xiaopeng.car.settingslibrary.service.GlobalService;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.cb.MicConnectDialog;
import com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogInfo;
import com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogWork;
import com.xiaopeng.car.settingslibrary.ui.activity.MainActivity;
import com.xiaopeng.car.settingslibrary.ui.dialog.BluetoothPairDialog;
import com.xiaopeng.car.settingslibrary.ui.fragment.AboutSystemsFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.BluetoothFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.DisplayFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.SoundFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.WlanFragment;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
/* loaded from: classes.dex */
public class AppUIListener implements IAppUIListener {
    public static final String EXTRA_FRAGMENT_NAME = "fragment";
    private final Context mContext;
    public static final String EXTRA_WIFI_FRAGMENT_NAME = WlanFragment.class.getName();
    public static final String EXTRA_BLUETOOTH_FRAGMENT_NAME = BluetoothFragment.class.getName();
    public static final String EXTRA_STORAGE_FRAGMENT_NAME = AboutSystemsFragment.class.getName();
    public static final String EXTRA_SOUND_FRAGMENT_NAME = SoundFragment.class.getName();
    public static final String EXTRA_DISPLAY_FRAGMENT_NAME = DisplayFragment.class.getName();

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onAuthModeAction(String str) {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onAuthModeCloseAction() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onEmergencyIgOff() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onRepairQuestQuit() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final AppUIListener instance = new AppUIListener(CarSettingsApp.getContext());

        private InnerFactory() {
        }
    }

    public static AppUIListener getInstance() {
        return InnerFactory.instance;
    }

    private AppUIListener(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onAutoPowerOffAction() {
        if (!CarFunction.isNonSelfPageUI() || CarFunction.isXmartOS5()) {
            Intent intent = new Intent(this.mContext, UIGlobalService.class);
            intent.setAction(Config.AUTO_POWER_OFF_ACTION);
            this.mContext.startService(intent);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onPopupToast(String str) {
        ToastUtils.get().showText(str, 0);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onPopupToastShort(String str) {
        ToastUtils.get().showShortText(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onPopupToastLong(String str) {
        ToastUtils.get().showLongText(str);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onPopupDialog(Intent intent) {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        XDialogInfo parse = XDialogInfo.parse(intent);
        if (parse != null) {
            Intent intent2 = new Intent(CarSettingsApp.getContext(), GlobalService.class);
            intent2.putExtra(XDialogWork.EXTRA_KEY, parse);
            intent2.setAction(Config.XUI_DIALOG_ACTION);
            CarSettingsApp.getContext().startService(intent2);
            return;
        }
        Logs.d("XDialogReceiver receiver info is null ");
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onPopupSoundEffect() {
        if (CarFunction.isXmartOS5()) {
            try {
                Intent intent = new Intent("com.xiaopeng.action.soundeffect");
                intent.setFlags(268435456);
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                LogUtils.e("no activity found:" + e);
                e.printStackTrace();
            }
        } else if (CarFunction.isUnityEffectPopupDialog()) {
        } else {
            try {
                Intent intent2 = new Intent(this.mContext, UIGlobalService.class);
                intent2.setAction(Config.SOUND_EFFECT_ACTION);
                this.mContext.startService(intent2);
            } catch (ActivityNotFoundException e2) {
                LogUtils.e("no activity found:" + e2);
                e2.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onMicDialogShow() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        MicConnectDialog.getInstance().showDialog(this.mContext);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onMicDialogUpdate(int i) {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        MicConnectDialog.getInstance().updateViewByEvent(i);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onBluetoothPairShow(String str, String str2, int i) {
        Intent intent = new Intent();
        intent.setClass(CarSettingsApp.getContext(), UIGlobalService.class);
        intent.setAction("android.bluetooth.device.action.PAIRING_REQUEST");
        intent.putExtra(BluetoothPairDialog.DEVICE_NAME, str);
        intent.putExtra(BluetoothPairDialog.DEVICE_ADDRESS, str2);
        intent.putExtra(BluetoothPairDialog.DEVICE_PAIR_TYPE, i);
        this.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onBluetoothPairCancel() {
        Intent intent = new Intent();
        intent.setClass(CarSettingsApp.getContext(), UIGlobalService.class);
        intent.setAction(Config.ACTION_PAIRING_REQUEST_CANCEL);
        this.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
    }

    public static Intent getPairingDialogIntent(Context context, Intent intent) {
        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
        Intent intent2 = new Intent();
        intent2.setClass(context, UIGlobalService.class);
        intent2.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
        intent2.putExtra("android.bluetooth.device.extra.PAIRING_VARIANT", intExtra);
        LogUtils.d("xpbluetooth pair type:" + intExtra + " device:" + bluetoothDevice);
        if (intExtra == 2 || intExtra == 4 || intExtra == 5) {
            intent2.putExtra("android.bluetooth.device.extra.PAIRING_KEY", intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", Integer.MIN_VALUE));
        }
        intent2.setAction("android.bluetooth.device.action.PAIRING_REQUEST");
        return intent2;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onAutoPowerOffCountdown(int i, int i2) {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, UIGlobalService.class);
        intent.putExtra(Config.EXTRA_AUTO_POWER_OFF, new int[]{i, i2});
        intent.setAction(Config.AUTO_POWER_OFF_TIME_ACTION);
        this.mContext.startService(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onCancelAutoPowerOff() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, UIGlobalService.class);
        intent.setAction(Config.AUTO_POWER_OFF_CLOSE_ACTION);
        this.mContext.startService(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onJumpBluetooth() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra("fragment", EXTRA_BLUETOOTH_FRAGMENT_NAME);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onJumpWifi() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra("fragment", EXTRA_WIFI_FRAGMENT_NAME);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onJumpSound() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra("fragment", EXTRA_SOUND_FRAGMENT_NAME);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onJumpDisplay() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra("fragment", EXTRA_DISPLAY_FRAGMENT_NAME);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.IAppUIListener
    public void onJumpAbout() {
        if (CarFunction.isNonSelfPageUI()) {
            return;
        }
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra("fragment", EXTRA_STORAGE_FRAGMENT_NAME);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }
}
