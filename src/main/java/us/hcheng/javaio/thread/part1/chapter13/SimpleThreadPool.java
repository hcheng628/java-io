package us.hcheng.javaio.thread.part1.chapter13;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool {
    private final int size;
    private final int queueSize;
    private final static int DEFAULT_SIZE = 10;
    private final static int DEFAULT_TASK_QUEUE_SIZE = 2_000;
    private static volatile int seq = 0;
    private final static String THREAD_PREFIX = "SimpleThreadPool";
    private final static ThreadGroup GROUP = new ThreadGroup("SimpleThreadPoolGroup");
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    private volatile boolean alive = true;
    private final DiscardPolicy discardPolicy;
    private final static DiscardPolicy DEFAULT_DISCARD_POLICY = ()->{
        throw new DiscardException("Discard this task..." + Thread.currentThread());
    };

    public SimpleThreadPool() {
        this(DEFAULT_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int size, int queueSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        this.init();
    }

    private void init() {
        IntStream.range(0, this.size).forEach(i-> this.createWorkTask());
    }

    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, String.join("-", THREAD_PREFIX, String.valueOf(++seq)));
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void submit(Runnable runnable) {
        if (!this.alive)
            throw new IllegalStateException("thread pool end of life...");

        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                System.out.println("moka...." + TASK_QUEUE.size() + " vs " + queueSize + " Thread: " + Thread.currentThread());
                discardPolicy.discard();
            }

            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isAlive() {
        return alive;
    }

    public void shutdown() throws InterruptedException {
        this.alive = false;
        while (!TASK_QUEUE.isEmpty())
            Thread.sleep(50);

        int total = THREAD_QUEUE.size();

        while (total > 0) {
            for (WorkerTask task : THREAD_QUEUE) {
                if (task.state == TaskState.BLOCKED) {
                    task.close();
                    task.interrupt();
                    total--;
                } else {
                    Thread.sleep(100);
                }
            }
        }

        System.out.println("thread pool disposed.");
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String msg) {
            super(msg);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState state = TaskState.FREE;

        public TaskState state() {
            return this.state;
        }

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (this.state != TaskState.DEAD) {
                Runnable runnable = null;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.state = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread() + " exiting...");
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.removeFirst();
                    TASK_QUEUE.notifyAll();
                }

                if (runnable != null) {
                    state = TaskState.RUNNING;
                    runnable.run();
                    state = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.state = TaskState.DEAD;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(6, 40, SimpleThreadPool.DEFAULT_DISCARD_POLICY);
        IntStream.range(0, 40).forEach(i-> {
            simpleThreadPool.submit(() -> {
                System.out.println("Start: task id: " + i + " processed by " + Thread.currentThread());
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("End: task id: " + i + " processed by " + Thread.currentThread());
            });
        });

        Thread.sleep(10_000);
        System.out.println("Calling shutdown");
        simpleThreadPool.shutdown();
        System.out.println("Called shutdown");
        simpleThreadPool.submit(() -> {
            System.out.println("moka");
        });
    }
}
