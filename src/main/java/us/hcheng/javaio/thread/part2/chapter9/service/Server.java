package us.hcheng.javaio.thread.part2.chapter9.service;

import us.hcheng.javaio.thread.part2.chapter9.RequestQueue;
import us.hcheng.javaio.thread.part2.chapter9.entity.Request;

public class Server<T> extends ThreadApp {

    private volatile boolean running;

    public Server(RequestQueue queue) {
        super(queue);
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            Request<T> request = queue.getRequest();
            if (request == null) {
                System.out.println("Received empty request...");
                continue;
            }

            System.out.println(Thread.currentThread().getName() + " server processing..." + request);
            try {
                //Thread.sleep(this.rand.nextInt(1000));
                if (1 == 1)
                    Thread.sleep(this.rand.nextInt(100));
            } catch (InterruptedException e) {
                System.err.println("Server stop interrupted...");
                return;
            }
        }
    }

    public void stopServer() {
        this.running = false;
        this.interrupt();
    }

}
