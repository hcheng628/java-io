package us.hcheng.javaio.thread.part1.chapter5;

import lombok.Data;
import java.util.Arrays;

public class ThreadJoin3 {

    public static void main(String[] args) {
        long startTimer = System.currentTimeMillis();
        Arrays.asList(new Thread(new CaptureRunnable("H1", 10_000)),
                new Thread(new CaptureRunnable("H2", 30_000)),
                new Thread(new CaptureRunnable("H3", 15_000)))
                        .forEach(t -> {
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });

        long endTimestamp = System.currentTimeMillis();
        System.out.printf("Save data begin timestamp is %s, end timestamp is %s\n", startTimer, endTimestamp);
        System.out.printf("Spend time is %s", endTimestamp - startTimer);
    }

}

@Data
class CaptureRunnable implements Runnable {

    private String hostName;
    private long timeSpent;

    public CaptureRunnable(String name, long time) {
        this.hostName = name;
        this.timeSpent = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.timeSpent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf(this.hostName + " completed data capture at timestamp [%s] and successful.\n", System.currentTimeMillis());
    }

}
