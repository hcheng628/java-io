package us.hcheng.javaio.thread.part2.chapter6;

import java.util.Arrays;

public class WorkerReader extends Thread {
    private final SharedData data;

    public WorkerReader(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true)
                System.out.println(Thread.currentThread().getName() + " READS " + Arrays.toString(data.read()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
