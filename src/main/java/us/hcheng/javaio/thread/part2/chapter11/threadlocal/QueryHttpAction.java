package us.hcheng.javaio.thread.part2.chapter11.threadlocal;

import us.hcheng.javaio.thread.part2.chapter11.entity.Context;

public class QueryHttpAction implements IAction {
    @Override
    public void execute() {
        Context ctx = ActionContext.getInstance().getContext();
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.setSsn(String.join("-", Thread.currentThread().getName(), "HTTP Moka"));
    }

}
