package us.hcheng.javaio.thread.part2.chapter8;

import java.util.function.Consumer;

public class FutureService {
    public <T> IFuture<T> submit(IFutureTask<T> task) {
        AsyncFuture<T> ret = new AsyncFuture<>();

        new Thread(() -> {
            T res = task.call();
            ret.complete(res);
        }).start();

        return ret;
    }

    public <T> IFuture<T> submit(IFutureTask<T> task, Consumer<T> consumer) {
        AsyncFuture<T> asyncFuture = new AsyncFuture<>();

        Thread t = new Thread(() -> {
            T res = task.call();
            asyncFuture.complete(res);
            consumer.accept(res);
        });
        t.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("this thread isAlive(): " + t.isAlive());

        new Thread(()->{
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("that thread isInterrupted(): " + t.isInterrupted());
            System.out.println("that thread isAlive(): " + t.isAlive());
        }).start();

        return asyncFuture;
    }
}