package us.hcheng.javaio.thread.part2.chapter11.nonthreadlocal;

import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 4).forEach(i -> {
            new Thread(new ActionWorker(), "T" + i).start();
        });
    }

}
