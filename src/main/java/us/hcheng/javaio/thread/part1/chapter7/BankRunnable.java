package us.hcheng.javaio.thread.part1.chapter7;

import java.util.Arrays;

public class BankRunnable {

    public static void main(String[] args) {
        TicketWindowRunnable runnable = new TicketWindowRunnable();
        TicketWindowRunnable runnable2 = new TicketWindowRunnable();
        Arrays.asList(new Thread(runnable, "1号窗口"),
                new Thread(runnable, "2号窗口"),
                new Thread(runnable2, "3号窗口"))
                .forEach(t -> {
                    t.start();
                });
    }

}
