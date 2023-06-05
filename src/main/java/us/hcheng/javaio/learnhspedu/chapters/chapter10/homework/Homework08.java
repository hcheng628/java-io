package us.hcheng.javaio.learnhspedu.chapters.chapter10.homework;

import java.util.Arrays;

/**
 * 枚举类
 * 创建一个Color枚举类
 * 1.有 RED,BLUE,BLACK,YELLOW,GREEN这个五个枚举值/对象；
 * 2.Color有三个属性redValue，greenValue，blueValue，
 * 3.创建构造方法，参数包括这三个属性，
 * 4.每个枚举值都要给这三个属性赋值，三个属性对应的值分别是
 * red：255,0,0  blue:0,0,255  black:0,0,0  yellow:255,255,0  green:0,255,0
 * 5.定义接口，里面有方法show，要求Color实现该接口
 * 6.show方法中显示三属性的值
 * 7. 将枚举对象在switch语句中匹配使用
 */
public class Homework08 {

    public static void main(String[] args) {
        Arrays.stream(Color.values()).forEach(Color::show);
    }

}

interface IColor {
    void show();
}

enum Color implements IColor {
    RED(255,0,0),BLUE(0,0,255),BLACK(0,0,0),YELLOW(255,255,0),GREEN(0,255,0);
    private int redValue;
    private int greenValue;
    private int blueValue;

    Color(int r, int g, int b) {
        redValue = r;
        greenValue = g;
        blueValue = b;
    }

    @Override
    public void show() {
        System.out.println(new StringBuilder("RGB[")
                .append(redValue).append(',')
                .append(greenValue).append(',')
                .append(blueValue).append(']'));
    }

}