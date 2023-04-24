package us.hcheng.javaio.thread.part2.chapter8;


public interface IFutureTask<T> {
    // Future<T> execute(Runnable runnable);
    T call();
}
