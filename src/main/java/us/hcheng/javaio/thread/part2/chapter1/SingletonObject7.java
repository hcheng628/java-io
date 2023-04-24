package us.hcheng.javaio.thread.part2.chapter1;

import java.util.stream.IntStream;

public class SingletonObject7 {

    private SingletonObject7(){}

    //  Java recommended way doing singleton
    public static SingletonObject7 instance() {
        return Singleton.INSTANCE.instance;
    }

    private enum Singleton {
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton() {
            instance = new SingletonObject7();
        }
    }

    public static void main(String[] args) {
        IntStream.range(0, 100).forEach(i -> new Thread(String.valueOf(i)){
            @Override
            public void run() {
                System.out.println("run: " + i + " " + SingletonObject7.instance());
            }
        }.start());
    }

}
