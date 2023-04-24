package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject1 {
    private static final SingletonObject1 singleton = new SingletonObject1();

    private SingletonObject1(){}

    private static SingletonObject1 instance() {
        return singleton;
    }
}
