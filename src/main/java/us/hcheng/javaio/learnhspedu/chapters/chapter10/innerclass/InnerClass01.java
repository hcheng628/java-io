package us.hcheng.javaio.learnhspedu.chapters.chapter10.innerclass;

public class InnerClass01 {

    public static void main(String[] args) {
        new Outer(100).m1();
        Outer.Inner inner = new Outer(100).getInner();
        System.out.println(inner);
        System.out.println(inner.name);

        Outer outer = new Outer(1000);
        Outer.Inner in =  outer.new Inner();
        System.out.println(in instanceof Outer.Inner);
        in = new Outer(10000).new Inner();
        System.out.println(in instanceof Outer.Inner);
        System.out.println(Outer.Inner.VERSION);
    }

    private void test() {
        //  Outer.Inner inner = new Outer.Inner();  // error
    }

}

class Outer {
    int n1;

    public Outer(int n1) {//构造器
        this.n1 = n1;
    }

    public void m1() {//方法
        System.out.println("m1()");
        new Inner();
    }

    public Inner getInner() {
        return new Inner();
    }

    public class Inner {
        public static String VERSION = "1.0";
        public String name = "Moka";

        static {
            System.out.println("Loading Inner class ......");
        }

        {
            System.out.println("Inner... Outer n1: " + Outer.this.n1);
        }

        public Inner (String name) {
            this.name = name;
        }
        public Inner () {}
    }

}