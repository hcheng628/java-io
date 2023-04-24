package us.hcheng.javaio.thread.part1.chapter1;

public abstract class BaseTemplateMethod {

    public static void main(String[] args) {
        new BaseTemplateMethod() {
            @Override
            protected void wrapperPrint(String msg) {
                System.out.println(String.join(msg, "*", "*"));
            }
        }.print("Moka");

        new BaseTemplateMethod() {
            @Override
            protected void wrapperPrint(String msg) {
                System.out.println(String.join(msg, "+", "+"));
            }
        }.print("Cheng");

    }

    public final void print(String msg) {
        System.out.println("##########");
        this.wrapperPrint(msg);
        System.out.println("##########");
    }

    protected abstract void wrapperPrint(String msg);
}
