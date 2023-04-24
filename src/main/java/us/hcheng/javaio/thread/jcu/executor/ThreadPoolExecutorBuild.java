package us.hcheng.javaio.thread.jcu.executor;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = buildThreadPoolExecutor();
        System.out.println("ThreadPoolExecutor: " + pool);
        test(pool);
        new Thread(() -> ThreadPoolExecutorBuild.doMonitor(pool)).start();
    }

    private static void test(ThreadPoolExecutor pool) {
        IntStream.range(0, 4).forEach(i -> {
            try {
                pool.submit(() -> {
                    SleepUtil.sleep(5000);
                    System.err.println(Thread.currentThread() + " working... " + i);
                });
                System.out.println("succeed submitted " + i);
            } catch (Exception ex) {
                System.err.println("failed submitted " + i);
            }
        });
    }

    private static void doMonitor(ThreadPoolExecutor pool) {
        System.out.println("doMonitor...");
        long timestamp = System.currentTimeMillis();
        for (int activeCount = -1, queueSize = -1;;)
            if (activeCount != pool.getActiveCount() || queueSize != pool.getQueue().size()) {
                activeCount = pool.getActiveCount();
                queueSize = pool.getQueue().size();
                StringBuilder sb = new StringBuilder();
                sb.append("\ngetActiveCount: ").append(activeCount);
                sb.append("\ngetCorePoolSize: ").append(pool.getCorePoolSize());
                sb.append("\nqueueSize: ").append(queueSize);
                sb.append("\ngetMaximumPoolSize: ").append(pool.getMaximumPoolSize());
                sb.append("\n******" + (System.currentTimeMillis() - timestamp) + "******");
                timestamp = System.currentTimeMillis();
                System.out.println(sb);
            }
    }

    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(1, 2, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.AbortPolicy());
    }

}
