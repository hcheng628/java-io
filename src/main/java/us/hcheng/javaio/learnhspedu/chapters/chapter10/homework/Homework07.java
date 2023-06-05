package us.hcheng.javaio.learnhspedu.chapters.chapter10.homework;

import java.util.stream.Stream;

/**
 * 有一个Car类，有属性temperature（温度），车内有Air（空调）类，有吹风的功能flow，
 * Air会监视车内的温度，如果温度超过40度则吹冷气。如果温度低于0度则吹暖气，
 * 如果在这之间则关掉空调。实例化具有不同温度的Car对象，调用空调的flow方法，
 * 测试空调吹的风是否正确 . //体现 类与类的包含关系的案例 类(内部类【成员内部类】)
 */
public class Homework07 {

    public static void main(String[] args) {
        Stream.of(-10, 0, 10, 40, 50).forEach(t -> {
            System.out.print(t + " : \t");
            new Car(t);
        });
    }

}


class Car {

    private int temperature;

    public Car(int temperature) {
        this.temperature = temperature;
        new Air().flow();
    }

    private class Air {

        public void flow() {
            String verbage = null;

            if (temperature > 40)
                verbage = "COLD";
            else if (temperature < 0)
                verbage = "HOT";

            System.out.println(String.join("AC: ", verbage == null ? "OFF" : verbage));
        }

    }

}