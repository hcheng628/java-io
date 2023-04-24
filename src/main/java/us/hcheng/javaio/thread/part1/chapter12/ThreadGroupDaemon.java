package us.hcheng.javaio.thread.part1.chapter12;

public class ThreadGroupDaemon {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup");
        new Thread(threadGroup, () -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t").start();

        threadGroup.setDaemon(true);

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadGroup.isDestroyed());
        // 手动回收ThreadGroup

        if (!threadGroup.isDestroyed()) {
            threadGroup.destroy();
            System.out.println(threadGroup.isDestroyed());
        }
    }

}
