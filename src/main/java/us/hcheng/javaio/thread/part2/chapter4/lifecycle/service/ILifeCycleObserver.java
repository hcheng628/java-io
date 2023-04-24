package us.hcheng.javaio.thread.part2.chapter4.lifecycle.service;

import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableEvent;

public interface ILifeCycleObserver {

    void onEvent(RunnableEvent event);

}
