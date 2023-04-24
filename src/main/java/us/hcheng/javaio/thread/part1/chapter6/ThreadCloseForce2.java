package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadCloseForce2 {

    public static void main(String[] args) {
        ThreadService service = new ThreadService();
        long startTime = System.currentTimeMillis();
        service.execute(() -> {
            try {
                System.out.println("read DB ...");
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
            }
        });

        service.shutdown(1_000);
        System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
    }

}
