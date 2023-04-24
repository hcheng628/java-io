package us.hcheng.javaio.thread.part2.chapter15.service;

import us.hcheng.javaio.thread.part2.chapter15.entity.Message;

import java.util.Random;

public class ThreadPerMessage {

    public void execute(Message msg) {
        new Thread(() -> {
            try {
                Thread.sleep(new Random().nextInt(1000));
                System.out.println(msg);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
