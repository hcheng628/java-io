
package us.hcheng.javaio.thread.part2.chapter16.complex;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer server = new AppServer();
        server.start();

        TimeUnit.SECONDS.sleep(15);
        System.err.println("!!!SERVER SHUTDOWN!!!");
        server.shutdown();
    }

}
