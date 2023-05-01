package us.hcheng.javaio.java8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Car;
import us.hcheng.javaio.java8.entity.Insurance;
import us.hcheng.javaio.java8.entity.Person;
import us.hcheng.javaio.java8.features.OptionalClient;

import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class OptionalClientTest {

    private static OptionalClient client;

    @BeforeAll
    static void init() {
        client = new OptionalClient();
    }

    @Test
    void createsTest() {
        assertThrows(NoSuchElementException.class, () -> client.createEmptyOptional().get());
        assertThrows(NullPointerException.class, () -> client.createOptional(null).get());
        assertThrows(NoSuchElementException.class, () -> client.createNullableOptional(null).get());
    }

    @Test
    void elsesTest() {
        Optional<Insurance> insuranceOpt = client.createNullableOptional(new Insurance());
        Insurance insurance = insuranceOpt.filter(i -> i.getName() == null)
                .orElse(new Insurance("Geico"));
        assertNull(insurance.getName());

        insuranceOpt = client.createEmptyOptional();
        insurance = insuranceOpt.filter(i -> i.getName() != null)
                .orElse(new Insurance("Geico"));
        assertFalse(insuranceOpt.isPresent());
        assertEquals("Geico", insurance.getName());

        final Optional<Insurance> dontCare1 = insuranceOpt;
        assertThrows(NoSuchElementException.class, () -> dontCare1.get().getName());

        insuranceOpt = client.createOptional(new Insurance("USAA"));
        insurance = insuranceOpt.filter(i -> i.getName() != null)
                .orElseGet(() -> new Insurance("Geico"));
        assertEquals("USAA", insurance.getName());

        Optional<Insurance> allStateOpt = client.createOptional(new Insurance("AllState"));
        assertThrows(RuntimeException.class, () -> allStateOpt.filter(i -> !"AllState".equals(i.getName()))
                .orElseThrow(() -> new RuntimeException("I hate you")));

        allStateOpt.ifPresentOrElse(System.out::println, () -> System.out.println("Not exist"));
    }

    @Test
    void mapsTest() {
        Insurance insurance = new Insurance("Geico");
        Car car = new Car();
        Person person = new Person();
        Optional<Car> carOpt = client.createOptional(car);
        Optional<Insurance> insuranceOpt = client.createOptional(insurance);
        car.setInsurance(insuranceOpt);
        person.setCar(carOpt);

        assertEquals("Geico", client.getInsuranceNameMap(person));
        assertEquals("Geico", client.getInsuranceNameFlatMap(person));

        person.setCar(Optional.empty());
        assertEquals("UNKNOWN", client.getInsuranceNameMap(person));
        assertEquals("UNKNOWN", client.getInsuranceNameFlatMap(person));
    }

}
