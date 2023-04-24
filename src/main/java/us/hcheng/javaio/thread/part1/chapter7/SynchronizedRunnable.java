package us.hcheng.javaio.thread.part1.chapter7;

public class SynchronizedRunnable implements Runnable {
    private int index = 1;
    // readonly shared data.
    private static final int MAX = 500;

    @Override
    public void run() {
        while(true)
            if (!this.doTKT())
                break;
    }

    private synchronized boolean doTKT() {
        if (this.index > MAX)
            return false;

        try {
            Thread.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " 的号码是： " + (index++));
        return true;
    }

}
