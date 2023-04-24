package us.hcheng.javaio.thread.part1.chapter2.bank;

import java.util.Arrays;

public class Thread_App {

    public static void main(String[] args) {
        Arrays.asList("一号柜台", "二号柜台", "三号柜台", "四号柜台", "五号柜台")
                .stream().forEach(each -> {
                    new TicketWindowThread(each).start();
                });
    }

}
