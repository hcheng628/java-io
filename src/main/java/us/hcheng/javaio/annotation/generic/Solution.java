package us.hcheng.javaio.annotation.generic;

import us.hcheng.javaio.annotation.generic.entity.Animal;
import us.hcheng.javaio.annotation.generic.entity.Dog;
import us.hcheng.javaio.annotation.generic.entity.Organism;
import us.hcheng.javaio.annotation.generic.entity.Cat;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<Organism> list = new ArrayList<>();

        list.add(new Organism("Top"));
        list.add(new Dog("Dog"));
        list.add(new Animal("Animal"));
        list.add(new Cat("Cat"));

        Solution app = new Solution();
        app.heyExtends(list);
        app.heySuper(list);
        app.heySuper(list);

        UtilPrint.printType(new Dog("Dog"));
        new UtilPrint<>(new Dog("Dog....")).print();
    }

    private void heyExtends(List<? extends Organism> list) {
        for (Organism l : list)
            System.out.println((l instanceof Dog) + " " + l);
    }

    private void heySuper(List<? super Animal> list) {
        for (Object l : list)
            System.out.println((l instanceof Dog) + " " + l);

        list.add(new Dog("DogDog"));
        list.add(new Animal("DogDog"));
    }

}
