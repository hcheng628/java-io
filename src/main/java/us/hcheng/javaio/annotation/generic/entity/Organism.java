package us.hcheng.javaio.annotation.generic.entity;

import us.hcheng.javaio.annotation.generic.annotation.ClassAnn;
import us.hcheng.javaio.annotation.generic.annotation.FieldAnn;

import java.io.Serializable;
import java.util.Date;

@ClassAnn(tableName = "sys_organism")
public class Organism {

    Serializable id;

    String name;
    String type;
    int age;

    @FieldAnn(fieldName = "sys_created_on")
    Date createdTime;

    public Organism() {}

    public Organism(String name) {
        this.name = name;
        this.type = this.getClass().getSimpleName();
    }

    public Organism(String name, int age) {
        this(name);
        this.age = age;
    }

    public Organism(String name, int age, Date createdTime) {
        this(name, age);
        this.createdTime = createdTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Organism{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", createdTime=" + createdTime +
                '}';
    }
}

