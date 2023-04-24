package us.hcheng.javaio.thread.part2.chapter9.service;

import us.hcheng.javaio.thread.part2.chapter9.RequestQueue;
import us.hcheng.javaio.thread.part2.chapter9.entity.Request;
import java.util.stream.IntStream;

public class Client<T> extends ThreadApp {

    private final T data;

    public Client(RequestQueue queue, T data) {
        super(queue);
        this.data = data;
    }

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            System.out.println(Thread.currentThread().getName() + " client put request...");
            queue.putRequest(new Request(data));
            try {
                //Thread.sleep(1_000);
                Thread.sleep(1_00);
            } catch (InterruptedException e) {
                System.out.println("Client: " + Thread.currentThread().getName() + " interrupted and exit...");
            }
        });
    }

}
