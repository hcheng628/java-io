package us.hcheng.javaio.thread.part1.chapter3;

import java.util.stream.IntStream;

public class CreateThread5 {

    private static int counter = 0;

    public static void main(String[] args) {
        try {
            IntStream.range(0, Integer.MAX_VALUE)
                    .forEach(each -> {
                        // 增大栈针的宽度
//                        new Thread(() -> {
//                            byte[] data = new byte[1024 * 1024 * 2];
//                            while (true) {
//                                CreateThread5.sleep();
//                                CreateThread5.add(0);
//                            }
//                        }).start();

                        new Thread(null, () -> {
                            byte[] data = new byte[1024 * 1024 * 2];
                            while (true) {
                                CreateThread5.sleep();
                                CreateThread5.add(0);
                            }
                        }, "stackSizeTest", 1 << 32).start();

                    });
        } catch (Error e) {
            e.printStackTrace();
        }
        System.err.println("Total create thread nums => " + counter);
    }

    private static void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int add(int i) {
        return add(i + 1) + 1;
    }
}
