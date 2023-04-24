package us.hcheng.javaio.annotation.generic.entity;

import us.hcheng.javaio.annotation.generic.annotation.ClassAnn;

@ClassAnn(tableName = "sys_animal")
public class Animal extends Organism {

    public Animal(){}

    public Animal(String name) {
        super(name);
    }

}
