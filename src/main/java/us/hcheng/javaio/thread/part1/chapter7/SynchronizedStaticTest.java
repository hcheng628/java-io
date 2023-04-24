package us.hcheng.javaio.thread.part1.chapter7;

public class SynchronizedStaticTest {

    public static void main(String[] args) {
        new Thread("t1"){
            @Override
            public void run() {
                SynchronizedStatic.m1();
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                SynchronizedStatic.m2();
            }
        }.start();

        new Thread("t3"){
            @Override
            public void run() {
                SynchronizedStatic.m3();
            }
        }.start();
    }

}
