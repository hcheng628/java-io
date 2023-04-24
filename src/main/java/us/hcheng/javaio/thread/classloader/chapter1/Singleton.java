package us.hcheng.javaio.thread.classloader.chapter1;

public class Singleton {

    public static final int staticFinalValue = 100;
    public final int finalValue = 10;

    private static Singleton instance = new Singleton();    // x = 0, y = 1

    public static int x = 0;
    public static int y;

    // private static Singleton instance = new Singleton();    // x = 1, y = 1

    public static final Object object = new Object();
    public static final String string = "Moka";


    private Singleton() {
        x++;
        y++;
    }

    public static Singleton getInstance() {
        return instance;
    }

    static {
        System.out.println("static block Singleton...");
    }

    public static void main(String[] args) {
        // System.out.println(Singleton.staticFinalValue);
        // System.out.println(Singleton.object);
        // System.out.println(Singleton.string);
        // System.out.println(instance);

        System.out.println(getInstance().x);
        System.out.println(getInstance().y);
    }

}
