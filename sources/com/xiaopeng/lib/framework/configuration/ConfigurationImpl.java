package com.xiaopeng.lib.framework.configuration;

import android.app.Application;
import com.xiaopeng.lib.framework.configuration.internal.Proxy;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration;
/* loaded from: classes.dex */
public class ConfigurationImpl implements IConfiguration {
    @Override // com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration
    public void init(Application application, String str) {
        Proxy.getInstance().init(application, str);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfiguration
    public String getConfiguration(String str, String str2) {
        return Proxy.getInstance().getConfiguration(str, str2);
    }
}
