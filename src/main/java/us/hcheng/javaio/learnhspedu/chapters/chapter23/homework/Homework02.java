package us.hcheng.javaio.learnhspedu.chapters.chapter23.homework;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Homework02 {

    /**
     * 利用Class类的forName方法得到File类的class 对象
     * 在控制台打印File类的所有构造器
     * 通过newInstance的方法创建File对象，并创建E:\mynew.txt文件
     */
    public static void main(String[] args) throws Exception {
        Class<?> fileClass = Class.forName("java.io.File");

        Constructor<?>[] constructors = fileClass.getDeclaredConstructors();
        for (int i = 0; i < constructors.length;) {
            Constructor<?> c = constructors[i];
            System.out.println("constructor #" + (++i));
            Class<?>[] parameterTypes = c.getParameterTypes();
            System.out.println("constructor #" + Arrays.toString(parameterTypes));
        }
        Constructor<?> constructor = fileClass.getDeclaredConstructor(String.class);
        Object file = constructor.newInstance("/Users/nikkima/Desktop/moka.txt");
        System.out.println(fileClass.getMethod("createNewFile").invoke(file));
    }

}
