package us.hcheng.javaio.thread.part2.chapter11.nonthreadlocal;

import us.hcheng.javaio.thread.part2.chapter11.entity.Context;

public class ActionWorker implements Runnable {

    @Override
    public void run() {
        Context context = new Context();
        new QueryDBAction().execute(context);
        System.out.println("The name query - OK");
        new QueryHttpAction().execute(context);
        System.out.println("The SSN query - OK");
        System.err.println(context);
    }

}
