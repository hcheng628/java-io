package us.hcheng.javaio.thread.part2.chapter9;

import us.hcheng.javaio.thread.part2.chapter9.entity.Request;
import us.hcheng.javaio.thread.part2.chapter9.service.Client;
import us.hcheng.javaio.thread.part2.chapter9.service.Server;

import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue<String> requestQueue = new RequestQueue<>();
        Stream.of("Moka", "Cheng", "Ma").forEach(
                each -> new Client(requestQueue, new Request<>(each)).start());
        Server<String> server1 = new Server(requestQueue);
        server1.start();

        Thread.sleep(3_000);
        server1.stopServer();
    }

}
