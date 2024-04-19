package com.xiaopeng.car.settingslibrary.speech;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.xui.vui.IVuiViewScene;
import com.xiaopeng.xui.vui.VuiView;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class VuiManager {
    public static String SCENE_ABOUT_SYSTEM = "AboutSystem";
    public static String SCENE_AUTO_POWER_OFF_DIALOG = "AutoPowerOffWorkDialog";
    public static String SCENE_BLUETOOTH = "Bluetooth";
    public static String SCENE_BLUETOOTH_BOUND_DIALOG = "BluetoothBindingDialog";
    public static String SCENE_BLUETOOTH_DETAIL_DIALOG = "BluetoothDetailDialog";
    public static String SCENE_BLUETOOTH_EDIT_NAME_DIALOG = "BluetoothEditNameDialog";
    public static String SCENE_BLUETOOTH_PUBLIC_DIALOG = "BluetoothPublicDialog";
    public static String SCENE_CLEAN_DIALOG = "CleanDialog";
    public static String SCENE_DARK_BRIGHTNESS_DIALOG = "DarkBrightnessDialog";
    public static String SCENE_DIALOG_TITLE = "DialogTitleLocal";
    public static String SCENE_DISPLAY = "Display";
    public static String SCENE_EXTERNAL_BLUETOOTH_PUBLIC_DIALOG = "ExternalBluetoothPublicDialog";
    public static String SCENE_FEEDBACK = "Feedback";
    public static String SCENE_FEEDBACK_HISTORY_DIALOG = "FeedbackHistoryDialog";
    public static String SCENE_FEEDBACK_NETWORK_DIALOG = "FeedbackLoadErrorDialog";
    public static String SCENE_FEEDBACK_NO_NETWORK_DIALOG = "NetworkAbnormalDialog";
    public static String SCENE_LABORATORY = "Laboratory";
    public static String SCENE_LABORATORY_CAR_CALL_ADVANCED_DIALOG = "CarCallAdvancedDialog";
    public static String SCENE_LABORATORY_INDUCTION_DIALOG = "InductionDialog";
    public static String SCENE_LABORATORY_LIMIT_NOTICE_DIALOG = "AppLimitNoticeDialog";
    public static String SCENE_LABORATORY_NOTICE_DIALOG = "LaboratoryNoticeDialog";
    public static String SCENE_LABORATORY_PARKING_ADVANCED_DIALOG = "ParkingAdvancedDialog";
    public static String SCENE_LABORATORY_RECORD_VIDEO_DIALOG = "RecordVideoDialog";
    public static String SCENE_NEDC_DIALOG = "NedcDialog";
    public static String SCENE_PRIVACY = "Privacy";
    public static String SCENE_PSN_BLUETOOTH_PUBLIC_DIALOG = "PsnBluetoothPublicDialog";
    public static String SCENE_RESTORE_CHECK_DIALOG = "RestoreCheckedDialog";
    public static String SCENE_RESTORE_DIALOG = "RestoreDialog";
    public static String SCENE_SOUND = "Sound";
    public static String SCENE_SOUND_EFFECT_DIALOG = "SoundEffectDialog";
    public static String SCENE_SOUND_HEADREST_DIALOG = "SoundHeadrestDialog";
    public static String SCENE_STORE_DIALOG = "StoreDialog";
    public static String SCENE_TAB = "TabLocal";
    public static String SCENE_TITLE_BAR = "TitleBarLocal";
    public static String SCENE_WAIT_MODE_DIALOG = "WaitModeDialog";
    public static String SCENE_WIFI = "Wifi";
    public static String SCENE_WIFI_CONNECT_DIALOG = "WifiConnectionDialog";
    public static String SCENE_WIFI_DETAIL_DIALOG = "WifiDetailDialog";
    public static String SCENE_WLAN_PUBLIC_DIALOG = "WlanPublicDialog";
    private String TAG;
    private ArrayMap<String, String> fragmentFatherIdMap;
    private ArrayMap<String, String> fragmentSceneMap;
    private HandlerThread mThread;
    private Handler mVuiHandler;

    private VuiManager() {
        this.TAG = "VuiManager";
        this.fragmentSceneMap = new ArrayMap<>();
        this.fragmentFatherIdMap = new ArrayMap<>();
        this.mThread = null;
        this.mVuiHandler = null;
        this.mThread = new HandlerThread("observer-thread");
        this.mThread.start();
        this.mVuiHandler = new Handler(this.mThread.getLooper());
    }

    public static final VuiManager instance() {
        return Holder.Instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Holder {
        private static final VuiManager Instance = new VuiManager();

        private Holder() {
        }
    }

    public void initVuiDialog(IVuiViewScene iVuiViewScene, Context context, String str) {
        if (str != null) {
            initVuiDialog(iVuiViewScene, context, str, null);
        }
    }

    public void initVuiDialog(IVuiViewScene iVuiViewScene, Context context, String str, IVuiElementListener iVuiElementListener) {
        if (str != null) {
            iVuiViewScene.setVuiEngine(VuiEngine.getInstance(context));
            iVuiViewScene.setVuiSceneId(str);
            if (iVuiElementListener != null) {
                iVuiViewScene.setVuiElementListener(iVuiElementListener);
            }
        }
    }

    public void createVuiScene(String str, View view, IVuiSceneListener iVuiSceneListener, Context context, String str2) {
        if (str == null || view == null || iVuiSceneListener == null) {
            return;
        }
        if (str2 != null && (view instanceof IVuiElement)) {
            ((IVuiElement) view).setVuiFatherElementId(str2);
        }
        VuiEngine.getInstance(context).addVuiSceneListener(str, view, iVuiSceneListener);
    }

    public void initVuiScene(BaseFragment baseFragment, String str, boolean z, List<String> list, String str2, boolean z2) {
        if (baseFragment == null) {
            return;
        }
        if (str == null) {
            String name = baseFragment.getClass().getName();
            if (this.fragmentSceneMap.containsKey(name)) {
                str = this.fragmentSceneMap.get(name);
            }
        } else if (!z2) {
            this.fragmentSceneMap.put(baseFragment.getClass().getName(), str);
        }
        if (str2 == null && !z2) {
            String name2 = baseFragment.getClass().getName();
            if (this.fragmentFatherIdMap.containsKey(name2)) {
                str2 = this.fragmentFatherIdMap.get(name2);
            }
        } else {
            this.fragmentFatherIdMap.put(baseFragment.getClass().getName(), str2);
        }
        baseFragment.setSceneId(str);
        baseFragment.setWholeScene(z);
        baseFragment.setSubSceneList(list);
        baseFragment.setFatherId(str2);
        baseFragment.isDialog(z2);
    }

    public void enterVuiScene(String str, boolean z, Context context) {
        if (!z || str == null) {
            return;
        }
        VuiEngine.getInstance(context).enterScene(str);
    }

    public void exitVuiScene(String str, boolean z, Context context) {
        if (!z || str == null) {
            return;
        }
        VuiEngine.getInstance(context).exitScene(str);
    }

    public void onBuildScene(String str, View view, List<View> list, List<String> list2, Context context, boolean z) {
        String str2 = this.TAG;
        Log.d(str2, "onBuildScene sceneId:" + str + "isWholeScene:" + z);
        if (str != null) {
            if (list != null) {
                VuiEngine.getInstance(context).buildScene(str, list, list2, z);
            } else if (view != null) {
                VuiEngine.getInstance(context).buildScene(str, view, list2, z);
            }
        }
    }

    public void destroyVuiScene(String str, Context context, IVuiSceneListener iVuiSceneListener) {
        if (str != null) {
            VuiEngine.getInstance(context).removeVuiSceneListener(str, iVuiSceneListener);
        }
    }

    public void updateVuiScene(String str, Context context, View... viewArr) {
        if (str == null || viewArr == null) {
            return;
        }
        if (viewArr.length == 1) {
            VuiEngine.getInstance(context).updateScene(str, viewArr[0]);
        } else {
            VuiEngine.getInstance(context).updateScene(str, Arrays.asList(viewArr));
        }
    }

    public void updateVuiScene(String str, Context context, List<View> list) {
        if (str == null || list == null) {
            return;
        }
        VuiEngine.getInstance(context).updateScene(str, list);
    }

    public void setVuiElementUnSupport(Context context, View view, boolean z) {
        if (view != null) {
            VuiEngine.getInstance(context).setVuiElementUnSupportTag(view, z);
        }
    }

    public void setVuiElementVisible(Context context, View view, boolean z) {
        if (view != null) {
            VuiEngine.getInstance(context).setVuiElementVisibleTag(view, z);
        }
    }

    public Boolean getVuiElementVisible(Context context, View view) {
        if (view != null) {
            return VuiEngine.getInstance(context).getVuiElementVisibleTag(view);
        }
        return null;
    }

    public boolean isVuiAction(View view) {
        if (view instanceof IVuiElement) {
            IVuiElement iVuiElement = (IVuiElement) view;
            boolean isPerformVuiAction = iVuiElement.isPerformVuiAction();
            if (isPerformVuiAction) {
                iVuiElement.setPerformVuiAction(false);
            }
            return isPerformVuiAction;
        }
        return false;
    }

    public Handler getVuiHandler() {
        return this.mVuiHandler;
    }

    public static void vuiFeedback(View view, int i, String str) {
        VuiEngine.getInstance(CarSettingsApp.getContext()).vuiFeedback(view, new VuiFeedback.Builder().state(i).content(str).build());
    }

    public void supportFeedback(VuiView vuiView) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(VuiConstants.PROPS_FEEDBACK, true);
            vuiView.setVuiProps(jSONObject);
        } catch (Exception unused) {
        }
    }
}
