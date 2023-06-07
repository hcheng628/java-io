package us.hcheng.javaio.java8.entity.app;

import java.util.Optional;

public class Person {

    private Optional<Car> car;

    public Optional<Car> getCar() {
        return this.car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

}
