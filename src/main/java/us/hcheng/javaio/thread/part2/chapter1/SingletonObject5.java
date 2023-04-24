package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject5 {

    private static volatile SingletonObject5 instance;

    private SingletonObject5(){}

    // lazy load, and double check approach is performant and thread-safe
    public static SingletonObject5 instance() {
        if (instance == null)
            synchronized (SingletonObject5.class) {
                if (instance == null)
                    instance = new SingletonObject5();
            }

        return instance;
    }

}
