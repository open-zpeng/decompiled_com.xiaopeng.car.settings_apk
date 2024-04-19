package com.xiaopeng.speech.vui.task;

import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.util.List;
/* loaded from: classes2.dex */
public class TaskDispatcher {
    private String TAG = getClass().getSimpleName();

    /* loaded from: classes2.dex */
    public enum TaskType {
        BUILD,
        UPDATE,
        ADD,
        REMOVE,
        UPDATEATTRIBUTE,
        UPDATERECYCLEVIEWITEM
    }

    public void dispatchTask(List<TaskWrapper> list) {
        BaseTask addSceneTask;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (TaskWrapper taskWrapper : list) {
            switch (taskWrapper.getTaskType()) {
                case ADD:
                    addSceneTask = new AddSceneTask(taskWrapper);
                    break;
                case BUILD:
                    addSceneTask = new BuildSceneTask(taskWrapper);
                    break;
                case REMOVE:
                    addSceneTask = new RemoveTask(taskWrapper);
                    break;
                case UPDATE:
                    addSceneTask = new UpdateSceneTask(taskWrapper);
                    break;
                case UPDATEATTRIBUTE:
                    addSceneTask = new UpdateSceneAttributeTask(taskWrapper);
                    break;
                case UPDATERECYCLEVIEWITEM:
                    addSceneTask = new UpdateRecyclerItemTask(taskWrapper);
                    break;
                default:
                    addSceneTask = new BuildSceneTask(taskWrapper);
                    break;
            }
            submitTask(addSceneTask);
        }
    }

    public void submitTask(BaseTask baseTask) {
        if (baseTask == null || baseTask.wrapper == null) {
            return;
        }
        try {
            ThreadPoolProxyFactory.getThreadPool().execute(baseTask);
        } catch (Exception e) {
            String str = this.TAG;
            LogUtils.e(str, "submitTask e:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeTask(String str) {
        ThreadPoolProxyFactory.getThreadPool().removeTask(str);
    }
}
