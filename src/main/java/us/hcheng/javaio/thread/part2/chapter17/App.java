package us.hcheng.javaio.thread.part2.chapter17;

import us.hcheng.javaio.thread.part2.chapter17.processor.TransportThread;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        Stream.of("Moka", "Ma", "Cheng").forEach(each -> {
            new TransportThread(each, channel).start();
        });
    }

}
