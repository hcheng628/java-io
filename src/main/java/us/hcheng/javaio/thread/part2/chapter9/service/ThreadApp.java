package us.hcheng.javaio.thread.part2.chapter9.service;

import us.hcheng.javaio.thread.part2.chapter9.RequestQueue;
import java.util.Random;

public abstract class ThreadApp<T> extends Thread {

    protected final RequestQueue<T> queue;
    protected final Random rand;

    protected ThreadApp(RequestQueue<T> queue) {
        this.queue = queue;
        this.rand = new Random(System.currentTimeMillis());
    }

}
