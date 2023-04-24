package us.hcheng.javaio.annotation.generic.entity;

import us.hcheng.javaio.annotation.generic.annotation.ClassAnn;

@ClassAnn(tableName = "sys_dog")
public class Dog extends Animal {

    public boolean bark = true;

    public Dog(){}

    public Dog(String name) {
        super(name);
    }


    public boolean getBark() {
        return this.bark;
    }

    public void setBark(boolean bark) {
        this.bark = bark;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "bark=" + bark +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", createdTime=" + createdTime +
                '}';
    }
}
