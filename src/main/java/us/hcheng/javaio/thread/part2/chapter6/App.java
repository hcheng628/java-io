package us.hcheng.javaio.thread.part2.chapter6;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        // WorkerReader
        IntStream.range(0, 5).forEach(each -> new WorkerReader(sharedData).start());
        ;
        // WorkerWrite
        Stream.of("qwertyuiopasdfg", "QWERTYUIOPASDFG").forEach(each -> new WorkerWrite(sharedData, each).start());
    }

}
