package us.hcheng.javaio.learnhspedu.projects.property_app.view;

import us.hcheng.javaio.learnhspedu.projects.property_app.entity.House;
import us.hcheng.javaio.learnhspedu.projects.property_app.entity.Validation;
import us.hcheng.javaio.learnhspedu.projects.property_app.service.PropertyService;
import us.hcheng.javaio.learnhspedu.projects.property_app.util.Utils;
import java.util.function.Supplier;

public class PropertyView {
    private PropertyService service;
    private boolean exit;

    public PropertyView() {
        exit = false;
        service = new PropertyService();
        service.createListing(new House("Tom", "111", "AAA", 100, "OK"));
        service.createListing(new House("Jack", "222", "BBB", 200, "OK"));
        service.createListing(new House("Apple", "333", "CCC", 300, "NO"));
    }

    private void printMainMenu() {
        System.out.println("\n=============房屋出租系统菜单=============");
        System.out.println("\t\t\t1 新 增 房 源");
        System.out.println("\t\t\t2 查 找 房 屋");
        System.out.println("\t\t\t3 修 改 房 屋 信 息");
        System.out.println("\t\t\t4 删 除 房 屋 信 息");
        System.out.println("\t\t\t5 房 屋 列 表");
        System.out.println("\t\t\t6 退      出");
        System.out.print("请输入你的选择(1-6): ");
    }

    private void create() {
        System.out.println("=============添加房屋=============");
        String name = readInput("姓名: ",  Utils::readString, new Validation(8, false));
        String phone = readInput("电话: ",  Utils::readString, new Validation(12, false));
        String address = readInput("地址: ",  Utils::readString, new Validation(16, false));
        Integer rent = readInput("月租: ",  Utils::readInt, new Validation(12, false));
        String state = readInput("状态: ",  Utils::readString, new Validation(3, false));
        House house = new House(name, phone, address, rent, state);
        service.createListing(house);
        System.out.println("=============添加房屋成功=============");
    }

    private <T> T readInput(String info,Supplier<T> readFunc, Validation rule) {
        return Utils.getInput(info, System.out::println, readFunc, rule, Utils::isValidInput);
    }

    private void retrieve() {
        System.out.println("=============查找房屋信息=============");
        int findId = readInput("请输入要查找的id: ", Utils::readInt, new Validation(12, false));
        House house = service.retrieveListing(findId);
        if (house == null)
            System.err.println("=============查找房屋信息id不存在=============");
        else
            System.out.println(house);
    }

    private void retrieveAll() {
        System.out.println("=============房屋列表=============");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态(未出租/已出租)");
        service.getAllListings().forEach(System.out::println);
        System.out.println("=============房屋列表显示完毕=============");
    }

    private void update() {
        System.out.println("=============修改房屋信息=============");
        int updateId = readInput("请选择待修改房屋编号id: ", Utils::readInt, new Validation(12, false));
        House house = service.retrieveListing(updateId);
        if (house == null) {
            System.out.println("=============修改房屋信息编号不存在...=============");
            return;
        }

        String name = readInput("姓名(" + house.getName() + ") EMPTY TO SKIP: ", Utils::readString, new Validation(8, true));
        if (!name.isEmpty())
            house.setName(name);

        String phone = readInput("电话(" + house.getPhone() + ") EMPTY TO SKIP: ", Utils::readString, new Validation(12, true));
        if (!phone.isEmpty())
            house.setPhone(phone);

        String address = readInput("地址(" + house.getAddress() + ")EMPTY TO SKIP: ", Utils::readString, new Validation(16, true));
        if (!address.isEmpty())
            house.setAddress(address);

        int rent = readInput("租金(" + house.getRent() + ")-1 TO SKIP:", Utils::readInt, new Validation(12, true));
        if (rent != -1)
            house.setRent(rent);

        String state = readInput("状态(" + house.getState() + ")EMPTY TO SKIP:", Utils::readString, new Validation(3, true));
        if (!state.isEmpty())
            house.setState(state);

        System.out.println("=============修改房屋信息成功============");
    }

    private void delete() {
        System.out.println("=============删除房屋信息=============");
        int delId = readInput("请输入待删除房屋的编号id: ", Utils::readInt, new Validation(12, false));
        if (Utils.readConfirmSelection())
            if (service.deleteListing(delId))
                System.out.println("=============删除房屋信息成功=============");
            else
                System.err.println("=============房屋编号不存在，删除失败=============");
        else
            System.err.println("=============放弃删除房屋信息=============");
    }

    private void exit() {
        exit = true;
    }

    public void mainMenu() {
        do {
            printMainMenu();
            String choice = Utils.readString();
            if (Utils.isValidInput(choice, new String[]{"1", "2", "3", "4", "5", "6"}))
                switch (choice) {
                    case "1" -> create();
                    case "2" -> retrieve();
                    case "3" -> update();
                    case "4" -> delete();
                    case "5" -> retrieveAll();
                    case "6" -> exit();
                }
            else
                System.err.println("注意: 请输入你的选择(1-6)");
        } while (!exit);
    }

}
