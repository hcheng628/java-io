package us.hcheng.javaio.thread.part2.chapter4;

import java.util.stream.IntStream;

public class ObserverClient {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new ObserverBinary(subject);
        new ObserverOctal(subject);
        IntStream.range(0, 16).forEach(i -> {
            System.out.println("**********" + i + "**********");
            subject.setState(i);
            System.out.println("*********************\n");
        });
    }

}
