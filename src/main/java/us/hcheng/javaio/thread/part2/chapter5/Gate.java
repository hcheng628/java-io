package us.hcheng.javaio.thread.part2.chapter5;

public class Gate {

    private String name = "Nobody";
    private String from = "Nowhere";
    private int counter = 0;

    public synchronized void pass(String name, String from) {
        this.counter++;
        this.name = name;
        this.from = from;
        this.verify();
    }

    public void verify() {
        if (name.charAt(0) != from.charAt(0))
            System.err.println("INVALID " + this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NO. ");
        sb.append(counter).append(": ");
        sb.append(name);
        sb.append("[").append(from).append("]");
        return sb.toString();
    }
}
