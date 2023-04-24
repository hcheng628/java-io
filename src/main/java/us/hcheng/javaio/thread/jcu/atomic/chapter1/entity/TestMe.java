package us.hcheng.javaio.thread.jcu.atomic.chapter1.entity;

public class TestMe {

    public volatile int pubVolatile;
    volatile int defVolatile;
    public int publicNonVolatile;
    protected volatile int priVolatile;
    public volatile Integer pubVolatileInteger;

    @Override
    public String toString() {
        return "TestMe{" +
                "pubVolatile=" + pubVolatile +
                ", defVolatile=" + defVolatile +
                ", publicNonVolatile=" + publicNonVolatile +
                ", priVolatile=" + priVolatile +
                '}';
    }

}
