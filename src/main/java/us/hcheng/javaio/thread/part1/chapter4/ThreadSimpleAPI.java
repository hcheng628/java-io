package us.hcheng.javaio.thread.part1.chapter4;

import java.util.Optional;

public class ThreadSimpleAPI {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            Optional.of("Hello").ifPresent(System.out::println);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" - ", t1.getName(), "name", "\n"));
        sb.append(String.join(" - ", String.valueOf(t1.getId()), "id", "\n"));
        sb.append(String.join(" - ", String.valueOf(t1.getPriority()), "Priority", "\n"));

        System.out.println(sb);
    }

}
