package us.hcheng.javaio.learnhspedu.chapters.chapter10.interface_;

public class InterfacePolyPass {

    public static void main(String[] args) {
        //接口类型的变量可以指向，实现了该接口的类的对象实例
        IBase base = new Teacher();
        base.methodIBase();
        //如果IG 继承了 IH 接口，而Teacher 类实现了 IG接口
        //那么，实际上就相当于 Teacher 类也实现了 IH接口.
        //这就是所谓的 接口多态传递现象.
        ISub sub = new Teacher();
        sub.methodISub();
    }

}

class Teacher implements ISub {

    @Override
    public void methodIBase() {

    }

    @Override
    public void methodISub() {

    }
}

interface IBase {
    void methodIBase();
}

interface ISub extends IBase {
    void methodISub();
}