package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadService {

    private Thread executeThread;
    private boolean finished;

    public void execute(Runnable task) {
        this.executeThread = new Thread(() -> {
           Thread runner = new Thread(task);
           runner.setDaemon(true);
           runner.start();

           try {
               runner.join();
               this.finished = true;
           } catch (InterruptedException e) {
               System.out.println("giving up the task, ran out of time...");
           }
        });
        this.executeThread.start();
    }

    public void shutdown(long mills) {
        long currTime = System.currentTimeMillis();

        while (!finished) {
            if (System.currentTimeMillis() - currTime > mills) {
                System.out.println("timeout so interrupt now....");
                this.executeThread.interrupt();
                break;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("current thread interrupted...");
                break;
            }
        }

    }

}
