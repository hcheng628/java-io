package us.hcheng.javaio.thread.part1.chapter3;

public class CreateThread4 {

    private static int counter = 0;

    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                } catch (Error error) {
                    error.printStackTrace();
                    System.out.println("counter: " + counter);
                }
            }

            private void add(int i) {
                counter++;
                add(i + 1);
            }
        }, "stack size test", 1 << 24).start();
    }
}
