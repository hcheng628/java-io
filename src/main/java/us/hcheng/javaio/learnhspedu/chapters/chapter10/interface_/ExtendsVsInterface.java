package us.hcheng.javaio.learnhspedu.chapters.chapter10.interface_;

public class ExtendsVsInterface {

    public static void main(String[] args) {
        LittleMonkey wuKong = new LittleMonkey("悟空");
        wuKong.climbing();
        wuKong.swimming();
        wuKong.flying();
    }

}

class Monkey {
    private String name;

    public Monkey(String name) {
        this.name = name;
    }
    public void climbing() {
        System.out.println(name + " 会爬树...");
    }

    public String getName() {
        return name;
    }
}

interface Fishable {
    void swimming();
}

interface Birdable {
    void flying();
}

class LittleMonkey extends Monkey implements Fishable, Birdable {

    public LittleMonkey(String name) {
        super(name);
    }

    @Override
    public void swimming() {
        System.out.println("LittleMonkey swimming");
    }

    @Override
    public void flying() {
        System.out.println("LittleMonkey flying");
    }

}