package us.hcheng.javaio.thread.part2.chapter8;


public class AsyncInvoker1 {

    public static void main(String[] args) throws InterruptedException {
        FutureService service = new FutureService();
        IFuture<String> future = service.submit(new IFutureTask<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "MOKA MOKA MOKA";
            }
        });

        System.out.println("=====");
        System.out.println("do other thing...");
        Thread.sleep(1000);
        System.out.println("=====");

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
