package us.hcheng.javaio.thread.jcu.collections.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person implements Comparable<Person> {
    private String name;
    private int age;

    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
