package us.hcheng.javaio.thread.part2.chapter11.threadlocal;

import us.hcheng.javaio.thread.part2.chapter11.entity.Context;

public class QueryDBAction implements IAction {

    @Override
    public void execute() {
        Context ctx = ActionContext.getInstance().getContext();
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.setName(String.join("-", Thread.currentThread().getName(), "Database Moka"));
    }

}
