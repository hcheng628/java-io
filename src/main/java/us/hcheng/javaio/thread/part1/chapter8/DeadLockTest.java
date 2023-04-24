package us.hcheng.javaio.thread.part1.chapter8;

public class DeadLockTest {

    public static void main(String[] args) {
        DeadLock lock = new DeadLock();
        OtherService service = new OtherService();
        lock.setService(service);
        service.setService(lock);

        new Thread(()->{
            while (true) {
                lock.m1();
            }
        }, "t1").start();

        new Thread(()->{
            while (true) {
                service.s1();
            }
        }, "t2").start();

    }

}
