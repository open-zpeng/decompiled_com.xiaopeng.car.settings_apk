package com.xiaopeng.lib.apirouter.server.settingsapp;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class SpeechSettingsOverAllObserver_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.car.settingslibrary.airouter.SpeechSettingsOverAllObserver";
    public static final int TRANSACTION_onEvent = 0;
    public static final int TRANSACTION_onQuery = 1;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.car.settingslibrary.airouter.SpeechSettingsOverAllObserver\",\"DESCRIPTOR\":\"com.xiaopeng.car.settingslibrary.airouter.SpeechSettingsOverAllObserver\",\"TRANSACTION\":[{\"path\":\"onEvent\",\"METHOD\":\"onEvent\",\"ID\":0,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]},{\"path\":\"onQuery\",\"METHOD\":\"onQuery\",\"ID\":1,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"},{\"alias\":\"callback\",\"name\":\"callback\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("SpeechSettingsOverAllObserver");
        return hashSet;
    }
}
