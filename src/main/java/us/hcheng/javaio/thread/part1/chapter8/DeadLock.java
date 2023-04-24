package us.hcheng.javaio.thread.part1.chapter8;

public class DeadLock {

    private final Object MONITOR = new Object();

    private OtherService service;

    public void setService(OtherService service) {
        this.service = service;
    }

    public void m1() {
        synchronized (MONITOR) {
            this.service.s1();
            System.out.println("********m1********" + Thread.currentThread().getName());
        }
    }

    public void m2() {
        synchronized (MONITOR) {
            System.out.println("********m2********" + Thread.currentThread().getName());
        }
    }

}
