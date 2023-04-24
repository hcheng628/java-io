package us.hcheng.javaio.thread.part1.chapter9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CaptureService {

    private static final int MAX_WORKER = 3;
    private static final List<Control> controls = new ArrayList<>();

    public static Thread createCaptureService(String name) {
        return new Thread(() -> {
            Control control = new Control();

            synchronized (controls) {
                while (controls.size() >= MAX_WORKER) {
                    try {
                        controls.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                controls.add(control);
            }

            System.out.println(Thread.currentThread().getName() + " processing...");
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (controls) {
                controls.remove(control);
                controls.notify();
            }

            //System.out.println(Thread.currentThread().getName() + " completed!");
        }, name);
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> {
            String name = String.join("-", "m", String.valueOf(i));
            Thread t = CaptureService.createCaptureService(name);
            threads.add(t);
            t.start();
        });

        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

class Control {

}
