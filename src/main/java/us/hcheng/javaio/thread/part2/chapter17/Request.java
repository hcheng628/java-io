package us.hcheng.javaio.thread.part2.chapter17;

public final class Request {

    private final int number;
    private final String name;

    public Request(int n, String name) {
        this.number = n;
        this.name = name;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executed " + this);
    }

    @Override
    public String toString() {
        return "Request{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

}
