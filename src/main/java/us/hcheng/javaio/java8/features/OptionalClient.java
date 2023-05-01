package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Car;
import us.hcheng.javaio.java8.entity.Insurance;
import us.hcheng.javaio.java8.entity.Person;
import java.util.Optional;

public class OptionalClient {

    private static final String UNKNOWN = "UNKNOWN";

    /**
     * ofNullable is a good method to use
     * as it calls of() and empty()
     */

    public <T> Optional<T> createOptional(T val) {
        return Optional.of(val);
    }

    public <T> Optional<T> createEmptyOptional() {
        return Optional.empty();
    }

    public <T> Optional<T> createNullableOptional(T val) {
        return Optional.ofNullable(val);
    }

    public String getInsuranceNameMap(Person p) {
        return Optional.ofNullable(p)
                .map(Person::getCar)
                .map(carOptional -> carOptional.orElse(new Car()))
                .map(Car::getInsurance)
                .map(insOptional -> insOptional.orElse(new Insurance()))
                .map(Insurance::getName)
                .orElse(UNKNOWN);
    }

    public String getInsuranceNameFlatMap(Person p) {
        return Optional.ofNullable(p)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse(UNKNOWN);
    }

}
