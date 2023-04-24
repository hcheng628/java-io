package us.hcheng.javaio.thread.part2.chapter4.lifecycle.entity;

public class RunnableEvent {
    private final RunnableState state;
    private Thread thread;
    private Throwable cause;

    public RunnableEvent(RunnableState state, Thread t, Throwable cause) {
        this.state = state;
        this.thread = t;
        this.cause = cause;
    }

    public RunnableState getState() {
        return state;
    }

    public Thread getThread() {
        return thread;
    }

    public Throwable getCause() {
        return cause;
    }
}
