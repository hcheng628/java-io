package us.hcheng.javaio.thread.part2.chapter8;

/**
 *  Future设计模式
 *  调用者线程A调用任务执行线程B的时候，
 *  首先线程B返回一个Future类，然后线程A通过get()方法获取执行结果
 *
 */
public interface IFuture<T> {
    T get() throws InterruptedException;
}
