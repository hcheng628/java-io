package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueClient {

    public static <T> SynchronousQueue<T> getInstance() {
        return new SynchronousQueue<>();
    }

}
