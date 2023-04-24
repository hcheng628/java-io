package us.hcheng.javaio.thread.part2.chapter4;

public class ObserverBinary extends Observer {

    public ObserverBinary(Subject subject) {
        super(subject);
    }

    @Override
    protected void update() {
        String s = String.join(":", "Binary String", Integer.toBinaryString(subject.getState()));
        System.out.println(s);
    }

}
