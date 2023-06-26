package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.view;

import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.util.Utility;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity.DiningTable;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service.BillService;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service.DiningTableService;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service.EmployeeService;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity.Employee;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service.MenuService;

import static us.hcheng.javaio.learnhspedu.chapters.projects.mhl.constant.Constants.MARIADB_LOGGING_DISABLE_KEY;
import static us.hcheng.javaio.learnhspedu.chapters.projects.mhl.constant.Constants.MARIADB_LOGGING_DISABLE_VAL;

public class MHLView {
    private EmployeeService employeeService;
    private DiningTableService tableService;
    private MenuService menuService;
    private BillService billService;
    private boolean running;

    static {
        System.setProperty(MARIADB_LOGGING_DISABLE_KEY, MARIADB_LOGGING_DISABLE_VAL);
    }

    public MHLView() {
        employeeService = new EmployeeService();
        tableService = new DiningTableService();
        menuService = new MenuService();
        billService = new BillService();
        running = true;
    }

    public void mainMenu() {
        while (running) {
            System.out.println("\n===============满汉楼===============");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.print("请输入你的选择: ");
            String choice = Utility.readString(1);
            switch (choice) {
                case "1" -> {
                    System.out.print("输入员工号: ");
                    String empId = Utility.readString(50);
                    System.out.print("输入密  码: ");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if (employee != null)
                        secondaryMenu(employee);
                    else
                        System.out.println("===============登录失败===============");
                }
                case "2" -> running = false;
                default -> System.out.println("你输入有误，请重新输入.");
            }
        }

        System.out.println("你退出了满汉楼系统~");
    }

    private void secondaryMenu(Employee employee) {
        System.out.println("===============登录成功[" + employee.getName() + "]===============\n");
        while (running) {
            System.out.println("\n===============满汉楼(二级菜单)===============");
            System.out.println("\t\t 1 显示餐桌状态");
            System.out.println("\t\t 2 预定餐桌");
            System.out.println("\t\t 3 显示所有菜品");
            System.out.println("\t\t 4 点餐服务");
            System.out.println("\t\t 5 查看账单");
            System.out.println("\t\t 6 结账");
            System.out.println("\t\t 9 退出满汉楼");
            System.out.print("请输入你的选择: ");
            String choice = Utility.readString(1);
            switch (choice) {
                case "1" -> listDiningTables();
                case "2" -> reserveDiningTable();
                case "3" -> listMenus();
                case "4" -> orderMenu();
                case "5" -> listBills();
                case "6" -> payBill();
                case "9" -> running = false;
                default -> System.out.println("你的输入有误，请重新输入");
            }
        }
    }

    private void listDiningTables() {
        System.out.println(tableService.getAllDiningTables());
    }

    private void listMenus() {
        System.out.println(menuService.getAllMenus());
    }

    private void listBills() {
        System.out.println(billService.getAllBillsStr());
    }

    public void payBill() {
        System.out.println("===============结账服务===============");
        System.out.print("请选择要结账的餐桌编号(-1退出): ");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            System.out.println("===============取消结账===============");
            return;
        }

        if (tableService.getDiningTableById(tableId) == null) {
            System.out.println("===============结账的餐桌不存在===============");
            return;
        }

        if (!billService.hasPayBillByDiningTableId(tableId)) {
            System.out.println("===============该餐位没有未结账账单===============");
            return;
        }

        System.out.print("结账方式(现金/支付宝/微信)回车表示退出: ");
        String payMode = Utility.readString(20, "");
        if ("".equals(payMode) || Utility.readConfirmSelection() == 'N') {
            System.out.println("===============取消结账===============");
            return;
        }

        System.out.println("===============结账" + (billService.payBill(tableId, payMode) ? "成功" : "失败") + "===============");
    }

    private void orderMenu() {
        System.out.println("===============点餐服务===============");
        System.out.print("请输入点餐的桌号(-1退出): ");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            System.out.println("===============取消点餐===============");
            return;
        }

        System.out.print("请输入点餐的菜品号(-1退出): ");
        int menuId = Utility.readInt();
        if (menuId == -1) {
            System.out.println("===============取消点餐===============");
            return;
        }

        System.out.print("请输入点餐的菜品量(-1退出): ");
        int orderAmt = Utility.readInt();
        if (orderAmt == -1) {
            System.out.println("===============取消点餐===============");
            return;
        }

        if (tableService.getDiningTableById(tableId) == null) {
            System.out.println("===============餐桌号不存在===============");
            return;
        }

        if (menuService.getMenuById(menuId) == null) {
            System.out.println("===============菜品号不存在===============");
            return;
        }

        System.out.println("===============点餐" + (billService.orderMenu(menuId, orderAmt, tableId) ? "成功" : "失败") + "===============");
    }

    private void reserveDiningTable() {
        System.out.println("===============预定餐桌===============");
        System.out.print("请选择要预定的餐桌编号(-1退出): ");
        int orderId = Utility.readInt();
        if (orderId == -1 || 'N' == Utility.readConfirmSelection()) {
            System.out.println("===============取消预订餐桌===============");
            return;
        }

        System.out.print("预定人的名字: ");
        String bookedBy = Utility.readString(50);
        System.out.print("预定人的电话: ");
        String bookedByTel = Utility.readString(50);

        DiningTable table = tableService.getDiningTableById(orderId);
        if (table == null) {
            System.out.println("===============预订餐桌不存在===============");
            return;
        } else if (!("空".equals(table.getState()))) {
            System.out.println("===============该餐桌已经预定或者就餐中===============");
            return;
        }

        System.out.println("===============预订餐桌" + (tableService.reserveDiningTable(orderId, bookedBy, bookedByTel) ? "成功" : "失败") + "===============");
    }

}
