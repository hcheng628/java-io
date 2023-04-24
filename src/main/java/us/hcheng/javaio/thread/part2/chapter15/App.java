package us.hcheng.javaio.thread.part2.chapter15;

import us.hcheng.javaio.thread.part2.chapter15.entity.Message;
import us.hcheng.javaio.thread.part2.chapter15.service.ThreadPerMessage;
import us.hcheng.javaio.thread.part2.chapter15.service.ThreadPerMessagePool;

import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws InterruptedException {
        boolean pool = true;

        ThreadPerMessage service = new ThreadPerMessage();
        ThreadPerMessagePool poolService = new ThreadPerMessagePool();

        IntStream.range(0, 100)
                .forEach(i -> {
                    if (pool) {
                        poolService.execute(new Message(String.join("-", "Moka", String.valueOf(i))));
                    } else {
                        service.execute(new Message(String.join("-", "Moka", String.valueOf(i))));
                    }
                });



        if (pool) {
            Thread.sleep(3000);
            poolService.close();
        }
    }
}
