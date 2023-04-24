package us.hcheng.javaio.thread.part2.chapter8;

public class SyncInvoker {

    public static void main(String[] args) throws InterruptedException {
        String result = get();
        System.out.println(result);
    }

    private static String get() throws InterruptedException {
        Thread.sleep(10_000);
        return "COMPLETE";
    }

}
