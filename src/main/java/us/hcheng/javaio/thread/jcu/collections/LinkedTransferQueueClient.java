package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueClient {

    public static <T> LinkedTransferQueue<T> getInstance() {
        return new LinkedTransferQueue<>();
    }

}
