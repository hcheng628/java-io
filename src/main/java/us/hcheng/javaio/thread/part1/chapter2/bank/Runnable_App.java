package us.hcheng.javaio.thread.part1.chapter2.bank;

import java.util.stream.IntStream;

public class Runnable_App {

    public static void main(String[] args) {
        TicketWindowRunnable ticketWindow = new TicketWindowRunnable();
        ticketWindow.run();

        /*
        Runnable ticketWindow = new Runnable_App().getTktWindow();
        Arrays.asList("一号柜台", "二号柜台", "三号柜台", "四号柜台", "五号柜台")
                .stream().forEach(each -> {
                    new Thread(ticketWindow, each).start();
                });
         */
    }

    private Runnable getTktWindow() {
        return () -> {
            IntStream.range(33, 67).forEach(
                    each -> {
                        String msg = String.join("", Thread.currentThread().getName(),
                                "的号码是", String.valueOf(each));
                        System.out.println(msg);
                    }
            );
        };
    }
}
