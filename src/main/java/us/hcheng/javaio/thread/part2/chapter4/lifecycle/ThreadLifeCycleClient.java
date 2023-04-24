package us.hcheng.javaio.thread.part2.chapter4.lifecycle;

import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableEvent;
import us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity.RunnableState;
import us.hcheng.javaio.thread.part2.chapter4.lifecycle.service.ObservableRunnable;
import us.hcheng.javaio.thread.part2.chapter4.lifecycle.service.ThreadLifeCycleObserver;

import java.util.stream.Stream;

public class ThreadLifeCycleClient {

    public static void main(String[] args) {
        ThreadLifeCycleObserver observer = new ThreadLifeCycleObserver();

        Stream.of(1, 2, 3).forEach(i -> {
            new Thread(new ObservableRunnable(observer) {
                @Override
                public void run() {
                    try {
                        notifyUpdate(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                        Thread.sleep(2_000 * i);
                        if (i == 1) {
                            int exception = 3 / 0;
                        }
                        notifyUpdate(new RunnableEvent(RunnableState.COMPLETE, Thread.currentThread(), null));
                    } catch (Exception e) {
                        notifyUpdate(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                    }
                }
            }, String.join("-", "T", String.valueOf(i))).start();
        });
    }

}
