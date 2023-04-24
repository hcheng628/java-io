package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueClient {

    public static <T> PriorityBlockingQueue<T> getInstance() {
        return new PriorityBlockingQueue();
    }

}
