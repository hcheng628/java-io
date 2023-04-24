package us.hcheng.javaio.thread.jcu.atomic.chapter2.countdownlatch;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchExample1 {

    public static void main(String[] args) throws InterruptedException {
        // 1 Get Data
        int[] data = query();

        // 2 Process Data
        ExecutorService service = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(data.length);

        IntStream.range(0, data.length).forEach(i -> {
            service.submit(new DataProcessing(data, i, latch));
        });

        // 3 Pint Result

        latch.await();  // Either one is fine
        service.awaitTermination(10, TimeUnit.SECONDS); // Either one is fine

        System.out.println(Arrays.toString(data));
        service.shutdown();
    }
    static class DataProcessing implements Runnable {

        private final int[] data;
        private final int idx;
        private final CountDownLatch latch;

        public DataProcessing(int[] data, int idx, CountDownLatch latch) {
            this.data = data;
            this.idx = idx;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                this.data[this.idx] = this.idx + 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.latch.countDown();
                System.out.println(Thread.currentThread() + " Completed: " + Arrays.toString(data));
            }
        }

    }

    public static int[] query() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new int[10];
    }
}
