package us.hcheng.javaio.learnhspedu.chapters.chapter23.homework;

import java.lang.reflect.Field;

public class Homework01 {

    /**
     * 定义PrivateTest类，有私有name属性，并且属性值为hellokitty
     * 提供getName的公有方法
     * 创建PrivateTest的类，利用Class类得到私有的name属性，修改私有的name属性值，并调用getName()的方法打印name属性值
     */
    public static void main(String[] args) {
        try {
            Class<?> ptClass = Class.forName("us.hcheng.javaio.learnhspedu.chapters.chapter23.homework.PrivateTest");
            // getDeclaredConstructor works but getConstructor does NOT(NoSuchMethodException)
            // unless manually create public no arg constructor
            PrivateTest pt = (PrivateTest) ptClass.getDeclaredConstructor().newInstance();
            Field nameField = ptClass.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(pt, "Super Moka");
            System.err.println(ptClass.getMethod("getName").invoke(pt));
        } catch (Exception ex) {ex.printStackTrace();}
    }
}

class PrivateTest {
    private String name = "hellokitty";
    public String getName() {
        return name;
    }
}
