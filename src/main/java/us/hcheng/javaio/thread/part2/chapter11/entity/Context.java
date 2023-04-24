package us.hcheng.javaio.thread.part2.chapter11.entity;

public class Context {
    private String name;
    private String ssn;

    public Context() {
        this("", "");
    }

    public Context(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public String toString() {
        return "Context{" +
                "name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }

}
