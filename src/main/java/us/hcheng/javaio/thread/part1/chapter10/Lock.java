package us.hcheng.javaio.thread.part1.chapter10;

import java.util.Collection;

public interface Lock {

    class MokaTimeoutException extends Exception {
        public MokaTimeoutException(String msg) {
            super(msg);
        }
    }

    void lock() throws InterruptedException;
    void lock(long mills) throws InterruptedException, MokaTimeoutException;
    void unlock();
    Collection<Thread> getBlockedThread();
    int getBlockedSize();
}
