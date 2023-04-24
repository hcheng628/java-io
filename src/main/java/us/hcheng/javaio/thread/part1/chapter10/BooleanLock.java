package us.hcheng.javaio.thread.part1.chapter10;

import java.util.*;

public class BooleanLock implements Lock {

    private boolean taken;
    private Thread currThread;
    private Set<Thread> blockingThreads = new HashSet<>();


    @Override
    public synchronized void lock() throws InterruptedException {
        while(this.taken) {
            this.blockingThreads.add(Thread.currentThread());
            System.out.println(blockingThreads);
            this.wait();
        }

        this.setTaken();
        System.out.println(this.currThread.getName() + " got the lock!!!");
    }

    private void setTaken() {
        this.blockingThreads.remove(this.currThread);
        this.taken = true;
        this.currThread = Thread.currentThread();
    }

    private void unsetTaken() {
        this.taken = false;
        this.currThread = null;
    }


    @Override
    public synchronized void lock(long mills) throws InterruptedException, MokaTimeoutException {
        if (mills < 1)
            this.lock();
        else {
            long end = System.currentTimeMillis() + mills;
            boolean timeout = false;
            while (this.taken && !timeout) {
                this.blockingThreads.add(Thread.currentThread());
                if (System.currentTimeMillis() > end)
                    timeout = true;
                this.wait(mills);
            }

            if (timeout) {
                this.blockingThreads.remove(Thread.currentThread());
                throw new MokaTimeoutException("Timeout ...");
            } else
                this.setTaken();
        }

    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread() != this.currThread)
            return;
        this.unsetTaken();
        this.notifyAll();
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(this.blockingThreads);
    }

    @Override
    public int getBlockedSize() {
        return this.blockingThreads.size();
    }
}
