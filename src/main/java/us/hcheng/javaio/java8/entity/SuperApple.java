package us.hcheng.javaio.java8.entity;

import lombok.Data;

@Data
public class SuperApple extends Apple {

    private String name;

    public SuperApple(String color, long weight, String name) {
        super(color, weight);
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SuperApple(color=");
        sb.append(getColor()).append(", weight=");
        sb.append(getWeight()).append(", name=");
        sb.append(getName()).append(")");
        return sb.toString();
    }

}
