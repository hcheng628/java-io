package us.hcheng.javaio.thread.part2.chapter6;

public class ReadWriteLock {
    private int readCount;
    private int readWaitCount;
    private int writeCount;
    private int writeWaitCount;
    private boolean preferWrite;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }

    public synchronized void readLock() {
        readWaitCount++;
        try {
            while (writeCount > 0 || (preferWrite && writeWaitCount > 0))
                this.wait();
            readCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWaitCount--;
        }
    }

    public synchronized void readUnlock() {
        readCount--;
        this.notifyAll();
    }

    public synchronized void writeLock() {
        writeWaitCount++;
        try {
            while (readCount > 0 || writeCount > 0)
                this.wait();
            writeCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeWaitCount--;
        }
    }

    public synchronized void writeUnlock() {
        writeCount--;
        this.notifyAll();
    }

}
