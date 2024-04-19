package com.xiaopeng.lib.apirouter.server.settingsapp;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class IpcRouterService_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.car.settingslibrary.common.IpcRouterService";
    public static final int TRANSACTION_onGetData = 1;
    public static final int TRANSACTION_onReceiverData = 0;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.car.settingslibrary.common.IpcRouterService\",\"DESCRIPTOR\":\"com.xiaopeng.car.settingslibrary.common.IpcRouterService\",\"TRANSACTION\":[{\"path\":\"onReceiverData\",\"METHOD\":\"onReceiverData\",\"ID\":0,\"parameter\":[{\"alias\":\"id\",\"name\":\"id\"},{\"alias\":\"bundle\",\"name\":\"bundle\"}]},{\"path\":\"onGetData\",\"METHOD\":\"onGetData\",\"ID\":1,\"parameter\":[{\"alias\":\"id\",\"name\":\"id\"},{\"alias\":\"bundle\",\"name\":\"bundle\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("IpcRouterService");
        return hashSet;
    }
}
