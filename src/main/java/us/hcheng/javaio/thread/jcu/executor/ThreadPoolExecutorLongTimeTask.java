package us.hcheng.javaio.thread.jcu.executor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorLongTimeTask {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = buildThreadPoolExecutor();
        Data data = new Data();

        pool.submit(() -> {
            System.out.println("about to set name");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException name" + Thread.currentThread().isInterrupted());
            }
            data.name = "Moka";
            System.out.println("name updated..." + data);
        });

        pool.submit(() -> {
            System.out.println("about to set piiCheck");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException piiCheck" + Thread.currentThread().isInterrupted());
            }
            data.piiCheck = true;
            System.out.println("piiCheck updated..." + data);
        });

        pool.submit(() -> {
            System.out.println("about to set creditScore");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException creditScore" + Thread.currentThread().isInterrupted());
            }
            data.creditScore = 833;
            System.out.println("creditScore updated..." + data);
        });

        pool.submit(() -> {
                while (true) {
                if (new Random().nextInt(100000000) == 13)
                    System.out.println(Thread.currentThread().isInterrupted());
            }
        });

        pool.shutdown();
        try {
            boolean flag = pool.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("already stopped? " + flag);
            if (!flag) {
                List<Runnable> list = pool.shutdownNow();
                System.out.println(list);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.err.println("main completed! " + data);
    }

    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(1, 2, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), r -> {
            Thread ret = new Thread(r);
            ret.setDaemon(true);
            return ret;
        }, new ThreadPoolExecutor.AbortPolicy());
    }

    static class Data {
        String name;
        boolean piiCheck;
        int creditScore;

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", piiCheck=" + piiCheck +
                    ", creditScore=" + creditScore +
                    '}';
        }

    }

}


