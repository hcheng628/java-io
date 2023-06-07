package us.hcheng.javaio.learnhspedu.projects.property.entity;

import lombok.Data;

@Data
public class House {
    private int id;
    private String name;
    private String phone;
    private String address;
    private int rent;
    private String state;

    public House(String name, String phone, String address, int rent, String state) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.rent = rent;
        this.state = state;
    }

    @Override
    public String toString() {
        return new StringBuilder(String.valueOf(id))
                .append("\t\t").append(name)
                .append("\t\t").append(phone)
                .append("\t\t").append(address)
                .append("\t\t").append(rent)
                .append("\t\t").append(state)
                .toString();
    }

}
