package us.hcheng.javaio.learnhspedu.chapters.chapter10.homework;

import java.util.Arrays;

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