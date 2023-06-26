package us.hcheng.javaio.learnhspedu.mhl.service;

import us.hcheng.javaio.learnhspedu.mhl.dao.BillDao;
import us.hcheng.javaio.learnhspedu.mhl.entity.Bill;
import us.hcheng.javaio.learnhspedu.mhl.entity.DiningTable;
import us.hcheng.javaio.learnhspedu.mhl.entity.Menu;
import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDao billDao;
    private MenuService menuService;
    private DiningTableService tableService;

    public BillService() {
        billDao = new BillDao();
        menuService = new MenuService();
        tableService = new DiningTableService();
    }

    public boolean orderMenu(int menuId, int orderAmt, int tableId) {
        Menu menu = menuService.getMenuById(menuId);
        if (menu == null || menu.getPrice() == null)
            return false;

        String sql = "insert into bill values(null, ?, ?, ?, ?, ?, now(), '未结账')";
        return billDao.execute(sql, UUID.randomUUID().toString(), menuId, orderAmt, menu.getPrice(), tableId) > 0
        && tableService.updateDiningTableState(tableId, "就餐中");
    }

    public List<Bill> getAllBills() {
        return billDao.query("select * from bill", Bill.class);
    }

    public String getAllBillsStr() {
        List<Bill> bills = getAllBills();
        if (bills == null || bills.isEmpty())
            return "===============显示完毕===============";

        StringBuilder sb = new StringBuilder("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t价格");
        bills.forEach(b -> sb.append(System.lineSeparator()).append(b));
        return sb.append(System.lineSeparator()).toString();
    }

    public boolean hasPayBillByDiningTableId(int id) {
        DiningTable table = tableService.getDiningTableById(id);
        return table != null && "就餐中".equals(table.getState());
    }

    public boolean payBill(int tableId, String paymentType) {
        //如果这里使用事务的话，需要用ThreadLocal来解决 , 框架中比如mybatis 提供了事务支持
        return billDao.execute("update bill set state = ? where diningTableId = ?  and state='未结账'", paymentType, tableId) > 0
                && tableService.freeDiningTableById(tableId);
    }

}
