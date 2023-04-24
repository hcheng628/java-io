package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadCloseForce {

    static class Worker extends Thread {
        @Override
        public void run() {
            System.out.println("run()...");
        }
    }


    public static void main(String[] args) {
        Worker worker = new Worker();
    }

}

