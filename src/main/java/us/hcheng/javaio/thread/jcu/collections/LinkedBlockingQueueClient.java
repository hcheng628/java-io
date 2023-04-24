package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueClient {

    public static <T> LinkedBlockingQueue<T> getInstance() {
        return new LinkedBlockingQueue();
    }

    public static <T> LinkedBlockingQueue<T> getInstance(int size) {
        return new LinkedBlockingQueue(size);
    }

}
