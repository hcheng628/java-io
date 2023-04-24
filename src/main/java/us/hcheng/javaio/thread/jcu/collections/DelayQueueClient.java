package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class DelayQueueClient {

    public static <T extends Delayed> DelayQueue<T> getInstance() {
        return new DelayQueue<>();
    }

}
