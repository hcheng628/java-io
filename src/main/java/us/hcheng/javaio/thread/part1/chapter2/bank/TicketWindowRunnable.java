package us.hcheng.javaio.thread.part1.chapter2.bank;

import java.util.stream.IntStream;

public class TicketWindowRunnable implements Runnable {

    @Override
    public void run() {
        IntStream.range(0, 50).forEach(i-> {
            String msg = String.join("", Thread.currentThread().getName(),
                    "的号码是", String.valueOf(i));
            System.out.println(msg);
        });
    }

}
