package us.hcheng.javaio.thread.part2.chapter11.threadlocal;

import us.hcheng.javaio.thread.part2.chapter11.entity.Context;

public class ActionContext {

    private final ThreadLocal<Context> threadLocal;

    private ActionContext() {
        threadLocal = ThreadLocal.withInitial(Context::new);
    }

    private static class ContextHolder {
        private static final ActionContext actionContext = new ActionContext();
    }

    public static ActionContext getInstance() {
        return ContextHolder.actionContext;
    }

    public Context getContext() {
        return threadLocal.get();
    }

}
