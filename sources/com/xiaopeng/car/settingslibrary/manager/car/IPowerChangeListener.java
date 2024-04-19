package com.xiaopeng.car.settingslibrary.manager.car;

import java.util.concurrent.CompletableFuture;
/* loaded from: classes.dex */
public interface IPowerChangeListener {
    void onPowerStateChange(int i, CompletableFuture<Void> completableFuture);
}
