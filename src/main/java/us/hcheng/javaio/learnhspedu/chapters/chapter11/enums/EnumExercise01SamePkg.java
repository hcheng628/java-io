package us.hcheng.javaio.learnhspedu.chapters.chapter11.enums;

import java.util.Arrays;

public class EnumExercise01SamePkg {

    public static void main(String[] args) {
        Arrays.stream(Gender.values()).forEach(g -> {
            System.out.println(g);
        });
    }

}