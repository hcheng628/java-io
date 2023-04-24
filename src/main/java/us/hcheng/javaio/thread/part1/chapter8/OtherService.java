package us.hcheng.javaio.thread.part1.chapter8;

public class OtherService {

    private final Object MONITOR = new Object();

    private DeadLock service;

    public void setService(DeadLock service) {
        this.service = service;
    }

    public void s1() {
        synchronized (MONITOR) {
            System.out.println("********s1********" + Thread.currentThread().getName());
            this.service.m2();
        }
    }

}
