package com.xiaopeng.car.settingslibrary.common;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.repository.GlobalSettingsSharedPreference;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.libconfig.ipc.bean.MessageCenterBean;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class IpcRouterService implements IServicePublisher {
    private static final String AUTHORITY_AI = "com.xiaopeng.aiassistant.IpcRouterService";
    private static final String BUNDLE = "bundle";
    private static final String ID = "id";
    private static final String PATH = "onReceiverData";
    private static final String STRING_MSG = "string_msg";
    private static final String TAG = "IpcRouterService";

    public static void sendMessageToMessageCenter(int i, MessageCenterBean messageCenterBean) {
        if (CarFunction.isSupportAPIRouterPush()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("string_msg", new Gson().toJson(messageCenterBean));
            String jsonObject2 = jsonObject.toString();
            Logs.d("xpsettings sendInfoFlowMsg=======bundle:" + jsonObject2);
            try {
                ApiRouter.route(new Uri.Builder().authority(AUTHORITY_AI).path(PATH).appendQueryParameter("id", String.valueOf((int) IpcConfig.MessageCenterConfig.IPC_ID_APP_PUSH_MESSAGE)).appendQueryParameter(BUNDLE, jsonObject2).build());
                if (i == 29001) {
                    GlobalSettingsSharedPreference.setPreferenceForKeyString(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_ID, messageCenterBean.getMessageId());
                    return;
                }
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("string_msg", new Gson().toJson(messageCenterBean));
        ((IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class)).sendData(IpcConfig.MessageCenterConfig.IPC_ID_APP_PUSH_MESSAGE, bundle, IpcConfig.App.MESSAGE_CENTER);
        if (i == 29001) {
            GlobalSettingsSharedPreference.setPreferenceForKeyString(CarSettingsApp.getContext(), Config.LAST_PUSH_TO_AI_STORE_ID, messageCenterBean.getMessageId());
        }
    }

    public static void cancelSendMsgToMessageCenter(String str) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("string_msg", str);
        String jsonObject2 = jsonObject.toString();
        Logs.d("xpsettings cancelSendInfoFlowMsg=======bundle:" + jsonObject2);
        try {
            ApiRouter.route(new Uri.Builder().authority(AUTHORITY_AI).path(PATH).appendQueryParameter("id", String.valueOf((int) IpcConfig.AIAssistantConfig.IPC_MESSAGE_CLOSE)).appendQueryParameter(BUNDLE, jsonObject2).build());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onReceiverData(int i, String str) {
        Logs.d("xpsettings onReceiverData=======id:" + i + ", bundle:" + str);
        if (i != 10010 && i == 10011 && parseBundle(str) == 29001) {
            AppServerManager.getInstance().startPopDialog("storage", 0, null);
        }
    }

    public String onGetData(int i, String str) {
        Logs.d("xpsettings onGetData=======id:" + i + ", bundle:" + str);
        return null;
    }

    private int parseBundle(String str) {
        try {
            return Integer.parseInt(new JSONObject(str).getString("string_msg"));
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
