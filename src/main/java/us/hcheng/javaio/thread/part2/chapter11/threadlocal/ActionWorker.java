package us.hcheng.javaio.thread.part2.chapter11.threadlocal;

public class ActionWorker implements Runnable {

    @Override
    public void run() {
        new QueryDBAction().execute();
        System.out.println("The name query - OK");
        new QueryHttpAction().execute();
        System.out.println("The SSN query - OK");
        System.err.println(String.join(":", "RETURN", ActionContext.getInstance().getContext().toString()));
    }

}
