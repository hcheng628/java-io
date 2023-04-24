package us.hcheng.javaio.thread.part2.chapter4.lifecycle.service;

import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableEvent;

public abstract class ObservableRunnable implements Runnable {

    private final ILifeCycleObserver listener;

    protected ObservableRunnable(ILifeCycleObserver listener) {
        this.listener = listener;
    }

    protected void notifyUpdate(RunnableEvent e) {
        listener.onEvent(e);
    }

}
