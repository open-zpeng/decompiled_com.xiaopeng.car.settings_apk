package com.xiaopeng.car.settingslibrary.direct;

import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.direct.action.BluetoothPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.PsnBluetoothPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.SoundEffectPageAction;
import com.xiaopeng.car.settingslibrary.direct.action.StoragePageAction;
import com.xiaopeng.car.settingslibrary.direct.action.WifiPageAction;
import com.xiaopeng.car.settingslibrary.direct.support.AppUsedLimitCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.AutoPowerOffCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.CarOutPlayModeCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.DirectionAlertSupportAction;
import com.xiaopeng.car.settingslibrary.direct.support.HeadrestCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.LeaveLockCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.LowSpeedSliderCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.NearUnlockCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.PsnBluetoothCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.SoldierCameraCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.SoldierCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.SpeedVolumeLinkSupportAction;
import com.xiaopeng.car.settingslibrary.direct.support.WaitModeCheckAction;
import com.xiaopeng.car.settingslibrary.direct.support.XoperaCheckAction;
import java.util.HashMap;
/* loaded from: classes.dex */
public class UnityDirectData {
    public static HashMap<String, PageDirectItem> loadPageData() {
        HashMap<String, PageDirectItem> hashMap = new HashMap<>();
        if (CarFunction.isNonSelfPageUI()) {
            hashMap.put("/CarControl.CarControl_OutCar_PlayMode", new PageDirectItem(new CarOutPlayModeCheckAction()));
            hashMap.put("/CarControl.CarControl_Xopera_SoundEffect", new PageDirectItem(new XoperaCheckAction()));
            hashMap.put("/CarControl.CarControl_L03_DriverExclusive", new PageDirectItem(new HeadrestCheckAction()));
            hashMap.put("/XPPlugin_SystemUIApp.BluetoothEarphoneParingListUI", new PageDirectItem(new PsnBluetoothPageAction(), new PsnBluetoothCheckAction()));
            hashMap.put("/CarControl.BluetoothDialog", new PageDirectItem(new BluetoothPageAction()));
            hashMap.put("/SystemSetting.CarControl_Popup_WlanParingList", new PageDirectItem(new WifiPageAction()));
            hashMap.put("/SystemSetting.StorageManagerDialog", new PageDirectItem(new StoragePageAction()));
            hashMap.put("/CarControl.CarControl_L03_SoundEffect", new PageDirectItem(new SoundEffectPageAction()));
        }
        return hashMap;
    }

    public static HashMap<String, ElementDirectItem> loadItemData() {
        HashMap<String, ElementDirectItem> hashMap = new HashMap<>();
        if (CarFunction.isNonSelfPageUI()) {
            hashMap.put("/CarControl.CarControl_L02_Sound/direction_alert", new ElementDirectItem(new DirectionAlertSupportAction()));
            hashMap.put("/CarControl.CarControl_L02_Sound/speed_volume_link", new ElementDirectItem(new SpeedVolumeLinkSupportAction()));
            hashMap.put("/CarControl.CarControl_L02_Display/wait_mode", new ElementDirectItem(new WaitModeCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/app_limit", new ElementDirectItem(new AppUsedLimitCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/low_speed_volume", new ElementDirectItem(new LowSpeedSliderCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/soldier_sensitivity", new ElementDirectItem(new SoldierCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/soldier_camera", new ElementDirectItem(new SoldierCameraCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/near_unlock", new ElementDirectItem(new NearUnlockCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/leave_lock", new ElementDirectItem(new LeaveLockCheckAction()));
            hashMap.put("/XPLab.XPLaboratory_L01/auto_poweroff", new ElementDirectItem(new AutoPowerOffCheckAction()));
        }
        return hashMap;
    }
}
