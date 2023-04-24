package us.hcheng.javaio.thread.part1.chapter13;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleThreadPool1 extends Thread {
    private final int min;
    private final int active;
    private final int max;
    private int currSize;
    private final int queueSize;
    private volatile boolean alive;
    private static AtomicInteger sequence = new AtomicInteger();
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private static final ThreadGroup GROUP = new ThreadGroup("MyPool_Group");
    private static final List<Task> THREADS = new ArrayList<>();
    private static final LinkedList<Runnable> RUNNABLES = new LinkedList<>();

    public SimpleThreadPool1() {
        this(2, 4, 8, 100);
    }

    public SimpleThreadPool1(int min, int active, int max, int queueSize) {
        this.alive = true;
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.updateThreads(0, this.min);
    }

    public int getMin() {
        return min;
    }

    public int getActive() {
        return active;
    }

    public int getMax() {
        return max;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isPoolAlive() {
        return alive;
    }

    private void updateThreads(int from, int to) {
        IntStream.range(from, to).forEach(i -> this.createTask());
        this.currSize = to;
    }

    public void createTask() {
        Task t = new Task(GROUP, String.join("-", THREAD_PREFIX, String.valueOf(sequence.getAndIncrement())));
        THREADS.add(t);
        t.start();
    }

    public void submit(Runnable runnable) {
        if (!this.alive)
            throw new IllegalThreadStateException("The thread pool has already been destroyed and not allowed to submit tasks.");

        synchronized (RUNNABLES) {
            if (RUNNABLES.size() + 1 > this.queueSize)
                throw new IllegalThreadStateException("TO-DO");

            RUNNABLES.add(runnable);
            RUNNABLES.notifyAll();
        }
    }
    public void shutdown() throws InterruptedException {
        if (!this.alive)
            return;

        this.alive = false;

        while (!RUNNABLES.isEmpty())
            Thread.sleep(50);

        synchronized (THREADS) {
            while (THREADS.size() > 0) {
                for (Iterator<Task> it = THREADS.iterator(); it.hasNext(); ) {
                    Task t = it.next();
                    if (t.taskState == TaskState.BLOCKED) {
                        t.stopTask();
                        t.interrupt();
                    } else if (t.taskState == TaskState.DEAD) {
                        t.interrupt();
                        it.remove();
                    } else {
                        Thread.sleep(10);
                    }
                }

            }
        }

        Thread.sleep(10);
        Thread[] threads = new Thread[GROUP.activeCount()];
        GROUP.enumerate(threads);
        System.out.println(Arrays.toString(threads));

        System.out.println("Group active count is " + GROUP.activeCount() +
                "\nThe thread pool disposed.");
    }

        @Override
    public void run() {
        while (this.alive) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min, this.active, this.max, this.currSize, RUNNABLES.size());

            try {
                Thread.sleep(3_000L);

                if (currSize < active && active < RUNNABLES.size()) {
                    this.updateThreads(currSize, active);
                    System.out.println("The Pool incremented to active.");
                } else if (currSize < max && max < RUNNABLES.size()) {
                    this.updateThreads(currSize, max);
                    System.out.println("The Pool incremented to max.");
                }

                synchronized (THREADS) {
                    if (currSize > active && RUNNABLES.isEmpty()) {
                        for (Iterator<Task> it = THREADS.iterator(); it.hasNext(); ) {
                            Task t = it.next();
                            if (t.taskState == TaskState.FREE) {
                                t.stopTask();
                                t.interrupt();
                                it.remove();
                            }
                        }
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    class Task extends Thread {
        TaskState taskState;

        public Task(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
            taskState = TaskState.FREE;
        }

        public void stopTask() {
            this.taskState = TaskState.DEAD;
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable = null;
                synchronized (RUNNABLES) {
                    while (RUNNABLES.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCKED;
                            RUNNABLES.wait();
                        } catch (InterruptedException e) {
                            System.out.println("exiting...");
                            break OUTER;
                        }
                    }

                    runnable = RUNNABLES.removeFirst();
                }

                if (runnable != null) {
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    System.out.println(Thread.currentThread() + " got a new task ;-)");
                    this.taskState = TaskState.FREE;
                }
            }
        }
    }

    enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool1 threadPool = new SimpleThreadPool1();
        /*SimpleThreadPool threadPool = new SimpleThreadPool(6, 10, SimpleThreadPool.DEFAULT_DISCARD_POLICY);*/
        IntStream.rangeClosed(1, 40).forEach(i -> {
            threadPool.submit(() -> {
                System.out.printf("The runnable %d be serviced by %s start.\n", i, Thread.currentThread().getName());
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("The runnable %d be serviced by %s finished.\n", i, Thread.currentThread().getName());
            });
        });

        Thread.sleep(17_000L);
        threadPool.shutdown();// 关闭线程池中的线程
    }
}