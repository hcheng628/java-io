package us.hcheng.javaio.thread.part2.chapter4;

public class ObserverOctal extends Observer {

    public ObserverOctal(Subject subject) {
        super(subject);
    }

    @Override
    protected void update() {
        String s = String.join(":", "Octal String", Integer.toOctalString(subject.getState()));
        System.out.println(s);
    }
}
