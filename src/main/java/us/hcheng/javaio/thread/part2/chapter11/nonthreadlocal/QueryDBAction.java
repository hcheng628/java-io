package us.hcheng.javaio.thread.part2.chapter11.nonthreadlocal;

import us.hcheng.javaio.thread.part2.chapter11.entity.Context;

public class QueryDBAction implements IAction {
    @Override
    public void execute(Context context) {
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setName(String.join("-", Thread.currentThread().getName(), "Database Moka"));
    }

}
