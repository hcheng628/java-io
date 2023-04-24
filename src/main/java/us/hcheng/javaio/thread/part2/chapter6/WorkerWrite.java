package us.hcheng.javaio.thread.part2.chapter6;

import java.util.Random;

public class WorkerWrite extends Thread {
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private final SharedData data;
    private final String filler;

    private int index = 0;

    public WorkerWrite(SharedData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.write(nextChat());
                Thread.sleep(RANDOM.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChat() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length())
            index = 0;
        return c;
    }

}
