package us.hcheng.javaio.thread.part1.chapter3;

public class CreateThread3 {

    private static int counter = 0;

    public static void main(String[] args) {
        try {
            add(0);
        } catch (Error error) {
            error.printStackTrace();
            System.out.println("counter: " + counter);
        }
    }

    private static void add(int i) {
        byte[] bytes = new byte[1024];
        counter++;
        add(i + 1);
    }

}
