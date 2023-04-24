package us.hcheng.javaio.thread.part2.chapter4;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void setState(int state) {
        if (this.state == state)
            return;
        this.state = state;
        notifyAllObservers();
    }

    private void notifyAllObservers() {
        observers.forEach(Observer::update);
    }

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public int getState() {
        return state;
    }

}
