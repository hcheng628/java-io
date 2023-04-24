package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3(){}

    // thread-safe but not good to lock it for read
    public synchronized static SingletonObject3 instance() {
        if (instance == null)
            instance = new SingletonObject3();
        return instance;
    }

}
