package us.hcheng.javaio.thread.part2.chapter4.lifecycle.service;

import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableEvent;
import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableState;

public class ThreadLifeCycleObserver implements ILifeCycleObserver {

    @Override
    public synchronized void onEvent(RunnableEvent event) {
        StringBuilder sb = new StringBuilder(Thread.currentThread().getName());
        sb.append(" state changed -> ");
        sb.append(event.getState());

        if (event.getState() == RunnableState.ERROR) {
            sb.append(" \n");
            sb.append(event.getCause().getMessage());
            sb.append("\n");
        }

        System.out.println(sb);
    }

}
