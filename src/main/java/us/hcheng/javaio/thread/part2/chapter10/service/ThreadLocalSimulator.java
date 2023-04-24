package us.hcheng.javaio.thread.part2.chapter10.service;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalSimulator<T> {

    private Map<Thread, T> map;

    public ThreadLocalSimulator() {
        map = new HashMap<>();
    }

    public T initialValue() {
        return null;
    }

    public T get() {
        if (!map.containsKey(Thread.currentThread()))
            return initialValue();
        return map.get(Thread.currentThread());
    }

    public void set(T val) {
        map.put(Thread.currentThread(), val);
    }

}
