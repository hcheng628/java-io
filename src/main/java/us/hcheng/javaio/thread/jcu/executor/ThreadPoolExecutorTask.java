package us.hcheng.javaio.thread.jcu.executor;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolExecutorTask {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = buildThreadPoolExecutor();

        IntStream.range(0, 20).forEach(i -> {
            try {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //System.out.println(Thread.currentThread().getName() + " doing work");
                            Thread.sleep(3000);
                            System.err.println(Thread.currentThread().getName() + " work done " + i + " " + Thread.interrupted());
                        } catch (Exception e) {
                            System.out.println(Thread.currentThread().getName() + " Exception: " + e);
                        }
                    }
                };
                System.out.println(i + " info some runnable: " + runnable);
                pool.submit(runnable);
            } catch (Exception ex) {
                System.out.println("submit Exception: " + ex);
            }
        });

        new Thread(() -> {
            doMonitor(pool);
        }).start();

        /**
         * It will not block and set isShutdown to true
         * Often used with awaitTermination
         */
        pool.shutdown();
        try {
            boolean flag = pool.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("awaitTermination: " + flag);
            if (!flag) {
                List<Runnable> list = pool.shutdownNow();

                list.forEach(runnable -> {
                    System.out.println("returned runnable: " + runnable);
                });
            }
        } catch (InterruptedException e) {
            System.err.println("awaitTermination InterruptedException: " + e);
        }

        /**
         * This submission will be rejected as this isShutdown is true
         */
        /*
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("moka visited store but closed");
            }
        });
         */
        System.out.println("main completed!");
    }

    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 20, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.AbortPolicy());
    }

    private static void doMonitor(ThreadPoolExecutor pool) {
        long timestamp = System.currentTimeMillis();
        for (int activeCount = -1, queueSize = -1; !pool.isShutdown() || !pool.isTerminated() ;)
            if (activeCount != pool.getActiveCount() || queueSize != pool.getQueue().size()) {
                activeCount = pool.getActiveCount();
                queueSize = pool.getQueue().size();
                StringBuilder sb = new StringBuilder();
                sb.append("\ngetActiveCount: ").append(activeCount);
                sb.append("\ngetCorePoolSize: ").append(pool.getCorePoolSize());
                sb.append("\nqueueSize: ").append(queueSize);
                sb.append("\ngetMaximumPoolSize: ").append(pool.getMaximumPoolSize());
                sb.append("\nisShutdown: ").append(pool.isShutdown());
                sb.append("\nisTerminated: ").append(pool.isTerminated());
                sb.append("\n******" + (System.currentTimeMillis() - timestamp) + "******");
                timestamp = System.currentTimeMillis();
                System.out.println(sb);
            }
    }
}


