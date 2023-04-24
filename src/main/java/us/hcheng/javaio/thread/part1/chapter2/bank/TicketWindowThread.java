package us.hcheng.javaio.thread.part1.chapter2.bank;

import java.util.stream.IntStream;

public class TicketWindowThread extends Thread {

    private final String name;

    public TicketWindowThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        IntStream.range(0, 50).forEach(
                i -> {
                    String msg = String.join("", "柜台: ",
                            this.name, "当前的号码是:", String.valueOf(i));
                    System.out.println(msg);
                }
        );
    }
}
