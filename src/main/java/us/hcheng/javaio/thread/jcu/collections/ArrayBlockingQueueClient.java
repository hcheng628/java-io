package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is just a ArrayBlockingQueue placeholder
 * in this package jcu.collections the tests will be
 * implemented in test src
 */
public class ArrayBlockingQueueClient {

    public static <T> ArrayBlockingQueue<T> getInstance(int size) {
        return new ArrayBlockingQueue<>(size);
    }

}
