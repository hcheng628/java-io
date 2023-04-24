package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject6 {

    private SingletonObject6(){}

    // lazy load, and double check approach is performant and thread-safe
    public static SingletonObject6 instance() {
        return SingletonObject6Holder.instance;
    }

    private static class SingletonObject6Holder {
        public static SingletonObject6 instance = new SingletonObject6();
    }

}
