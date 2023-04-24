package us.hcheng.javaio.thread.part2.chapter8;


public class AsyncFuture<T> implements IFuture<T> {

    private volatile boolean complete;
    private T res;

    public synchronized void complete(T readyRes) {
        this.res = readyRes;
        complete = true;
        this.notifyAll();
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!complete)
                this.wait();
        }
        return res;
    }

}
