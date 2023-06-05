package us.hcheng.javaio.learnhspedu.chapters.chapter10.homework;

import java.util.Arrays;

/**
 * 1.有一个交通工具接口类Vehicle，有work方法
 * 2.有Horse类和Boat类分别实现Vehicle
 * 3.创建交通工具工厂类，有两个方法分别获得交通工具Horse和Boat
 * 4.有Person类，有name和Vehicles属性，在构造器中为两个属性赋值
 * 5.实例化Person对象“唐僧”，要求一般情况下用Horse作为交通工具，遇到大河时用Boat作为交通工具
 * 6.增加一个情况，如果唐僧过火焰山, 使用 飞机 ==> 程序扩展性, 我们前面的程序结构就非常好扩展 10min
 * 使用代码实现上面的要求
 * 编程 需求---->理解---->代码-->优化
 */
public class Homework06 {

    public static void main(String[] args) {
        Person p = new Person("唐僧");
        Arrays.stream(TravelType.values()).forEach(p::travel);
    }

}


interface Vehicle {
    void travel();
}

class Horse implements Vehicle {

    @Override
    public void travel() {
        System.out.println("Horse...");
    }

}

class Boat implements Vehicle {

    @Override
    public void travel() {
        System.out.println("Boat...");
    }

}

class Plane implements Vehicle {

    @Override
    public void travel() {
        System.out.println("Plane...");
    }

}

class VehicleFactory {
    public static final Boat BOAT = new Boat();
    public static final Horse HORSE = new Horse();
    public static final Plane PLANE = new Plane();

    private VehicleFactory(){}
}

class Person {
    private final String name;
    private Vehicle vehicle;

    public Person(String name) {
        this.name = name;
        setVehicle(VehicleFactory.HORSE);
    }

    public void travel(TravelType type) {
        switch (type) {
            case AIR -> setVehicle(VehicleFactory.PLANE);
            case WATER -> setVehicle(VehicleFactory.BOAT);
            default -> setVehicle(VehicleFactory.HORSE);
        }

        System.out.print(this.name + " travels with ");
        this.vehicle.travel();
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}

enum TravelType {
    WATER, AIR, OTHER
}