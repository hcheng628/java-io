package us.hcheng.javaio.thread.part2.chapter6;

import java.util.Arrays;
public class SharedData {

    private final ReadWriteLock lock;
    private final char[] buffer;

    public SharedData(int size) {
        this.lock = new ReadWriteLock();
        size = Math.max(size,6);
        this.buffer = new char[size];
        Arrays.fill(buffer, '*');
    }

    public char[] read() {
        lock.readLock();

        char[] ret = new char[this.buffer.length];
        Arrays.fill(ret, buffer[0]);
        processing(10);

        lock.readUnlock();
        return ret;
    }

    public void write(char c) {
        lock.writeLock();

        Arrays.fill(buffer, c);
        processing(50);

        lock.writeUnlock();
    }

    private void processing(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
