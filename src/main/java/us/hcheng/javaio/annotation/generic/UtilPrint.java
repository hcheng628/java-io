package us.hcheng.javaio.annotation.generic;

public class UtilPrint<T> {

    T element;

    public UtilPrint(T element) {
        this.element = element;
    }

    public void print() {
        System.out.println(this.element);
    }

    static <F> void printType(F element) {
        System.out.println(element);
    }

}
