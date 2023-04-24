package us.hcheng.javaio.annotation.generic.entity;


public class Cat extends Animal {

    private boolean quiet = true;

    public Cat() {}

    public Cat(String name) {
        super(name);
    }

    public boolean getQuiet() {
        return quiet;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "quiet=" + quiet +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", createdTime=" + createdTime +
                '}';
    }

}
