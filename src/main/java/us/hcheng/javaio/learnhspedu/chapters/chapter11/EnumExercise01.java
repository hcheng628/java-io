package us.hcheng.javaio.learnhspedu.chapters.chapter11;

import us.hcheng.javaio.learnhspedu.chapters.chapter11.enums.Gender;

import java.util.Arrays;

public class EnumExercise01 {

    public static void main(String[] args) {
        Arrays.stream(Gender.values()).forEach(g -> {
            System.out.println("g: " + g);
            System.out.println("g.name(): " + g.name());
            System.out.println("constantName: " + g.describeConstable().get().constantName());
            System.out.println("g.ordinal(): " + g.ordinal());
        });

        try {
            System.out.println(Gender.valueOf("Boy"));
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        try {
            System.out.println(Enum.valueOf(Gender.class, "Boy"));
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        System.out.println(Gender.valueOf("BOY"));
        System.out.println(Gender.valueOf("BOY").getDeclaringClass());


        
    }

}