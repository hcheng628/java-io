package us.hcheng.javaio.thread.jcu.atomic.chapter1;

public class Person {
    String name;
    int age;

    public Person() {
        this.name = "";
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}