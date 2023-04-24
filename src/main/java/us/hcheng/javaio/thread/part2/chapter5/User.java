package us.hcheng.javaio.thread.part2.chapter5;

public class User implements Runnable {

    private final String name;
    private final String from;
    private final Gate gate;

    public User(String name, String from, Gate gate) {
        this.name = name;
        this.from = from;
        this.gate = gate;
    }


    @Override
    public void run() {
        System.out.println(name + " - START!");
        while (true)
            this.gate.pass(name, from);
    }
}
