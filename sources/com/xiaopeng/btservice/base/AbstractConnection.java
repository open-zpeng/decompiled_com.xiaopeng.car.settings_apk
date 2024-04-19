package com.xiaopeng.btservice.base;

import com.nforetek.bt.aidl.UiCommand;
import java.util.Iterator;
import java.util.LinkedList;
/* loaded from: classes.dex */
public abstract class AbstractConnection {
    private LinkedList<Runnable> mToDoList = new LinkedList<>();

    /* loaded from: classes.dex */
    public interface ConnecteCompletedCallback {
        void connectServiceCompleted();
    }

    public abstract void onDisconnected();

    public abstract void register(UiCommand uiCommand);

    public abstract void registerConnecteCompletedCallback(ConnecteCompletedCallback connecteCompletedCallback);

    public abstract void unRegister();

    public abstract void unregisterConnecteCompletedCallback(ConnecteCompletedCallback connecteCompletedCallback);

    void post(Runnable runnable) {
        this.mToDoList.add(runnable);
    }

    void remove(Runnable runnable) {
        this.mToDoList.remove(runnable);
    }

    public void onConnected(UiCommand uiCommand) {
        register(uiCommand);
        work();
    }

    void work() {
        Iterator<Runnable> it = this.mToDoList.iterator();
        while (it.hasNext()) {
            it.next().run();
            it.remove();
        }
    }
}
