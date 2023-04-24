package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4(){}

    // lazy load, and double check approach is performant but not thread-safe
    public static SingletonObject4 instance() {
        if (instance == null)
            synchronized (SingletonObject4.class) {
                if (instance == null)
                    instance = new SingletonObject4();
            }

        return instance;
    }

}
