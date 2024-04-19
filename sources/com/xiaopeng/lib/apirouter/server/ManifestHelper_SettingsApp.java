package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Pair;
import com.xiaopeng.lib.apirouter.server.settingsapp.IpcRouterService_Manifest;
import com.xiaopeng.lib.apirouter.server.settingsapp.IpcRouterService_Stub;
import com.xiaopeng.lib.apirouter.server.settingsapp.SettingsVuiObserver_Manifest;
import com.xiaopeng.lib.apirouter.server.settingsapp.SettingsVuiObserver_Stub;
import com.xiaopeng.lib.apirouter.server.settingsapp.SpeechSettingsOverAllObserver_Manifest;
import com.xiaopeng.lib.apirouter.server.settingsapp.SpeechSettingsOverAllObserver_Stub;
import java.util.HashMap;
/* loaded from: classes.dex */
public class ManifestHelper_SettingsApp implements IManifestHelper {
    public HashMap<String, Pair<IBinder, String>> mapping = new HashMap<>();

    @Override // com.xiaopeng.lib.apirouter.server.IManifestHelper
    public HashMap<String, Pair<IBinder, String>> getMapping() {
        Pair<IBinder, String> pair = new Pair<>(new SettingsVuiObserver_Stub(), SettingsVuiObserver_Manifest.toJsonManifest());
        for (String str : SettingsVuiObserver_Manifest.getKey()) {
            this.mapping.put(str, pair);
        }
        Pair<IBinder, String> pair2 = new Pair<>(new SpeechSettingsOverAllObserver_Stub(), SpeechSettingsOverAllObserver_Manifest.toJsonManifest());
        for (String str2 : SpeechSettingsOverAllObserver_Manifest.getKey()) {
            this.mapping.put(str2, pair2);
        }
        Pair<IBinder, String> pair3 = new Pair<>(new IpcRouterService_Stub(), IpcRouterService_Manifest.toJsonManifest());
        for (String str3 : IpcRouterService_Manifest.getKey()) {
            this.mapping.put(str3, pair3);
        }
        return this.mapping;
    }
}
