package us.hcheng.javaio.thread.part2.chapter7;

public class PersonRunnable implements Runnable {

    private PersonImmutable personImmutable;

    public PersonRunnable(PersonImmutable personImmutable) {
        this.personImmutable = personImmutable;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        while (!t.isInterrupted()) {
            System.out.println(personImmutable + " " + t.getName() + " " + t.isInterrupted());
            if (personImmutable.getName().charAt(0) == 'M')
                break;
        }
    }

}
