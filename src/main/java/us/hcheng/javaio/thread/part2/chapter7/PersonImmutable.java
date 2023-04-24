package us.hcheng.javaio.thread.part2.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PersonImmutable {
    private final String name;
    private final String from;
    private final List<String> notes;

    public PersonImmutable(String name, String from) {
        this.name = name;
        this.from = from;
        this.notes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getNotes() {
        return Collections.unmodifiableList(this.notes);
    }

    @Override
    public String toString() {
        return "ImmutablePerson{" +
                "name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", notes=" + notes +
                '}';
    }

}
