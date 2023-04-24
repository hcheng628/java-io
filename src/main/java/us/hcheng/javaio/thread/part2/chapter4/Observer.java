package us.hcheng.javaio.thread.part2.chapter4;

public abstract class Observer {

    Subject subject;

    protected Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    protected abstract void update();
}
