package us.hcheng.javaio.thread.jcu.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorServiceExample5 {

    public static void main(String[] args) {
        getQueueTest();
        prestartWithQueueTest();
    }

    /**
     * needs to trigger the pool to have an active job to get queue jobs processed
     */
    private static void getQueueTest() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        executor.prestartCoreThread();

        executor.getQueue().add(() -> {
            System.out.println("Moka add");
        });

        executor.submit(() -> {
            System.out.println("Moka submit");
        });

        executor.getQueue().add(() -> {
            System.out.println("Moka again");
        });
    }

    /**
     * If there are active threads then it will executes all the queue jobs
     */
    private static void prestartWithQueueTest() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        executor.prestartCoreThread();

        executor.getQueue().add(() -> {
            System.out.println("Moka add 1");
        });

        executor.getQueue().add(() -> {
            System.out.println("Moka add 2");
        });

    }


}
