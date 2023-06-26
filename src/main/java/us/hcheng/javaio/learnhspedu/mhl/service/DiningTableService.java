package us.hcheng.javaio.learnhspedu.mhl.service;

import us.hcheng.javaio.learnhspedu.mhl.dao.DiningTableDao;
import us.hcheng.javaio.learnhspedu.mhl.entity.DiningTable;
import java.util.List;

public class DiningTableService {
    private DiningTableDao tableDao;

    public DiningTableService() {
        tableDao = new DiningTableDao();
    }

    public String getAllDiningTables() {
        List<DiningTable> tables = tableDao.query("select id, state from dining_table;", DiningTable.class);
        if (tables == null || tables.isEmpty())
            return "===============没有餐桌===============";

        StringBuilder sb = new StringBuilder("\n餐桌编号\t\t餐桌状态");
        tables.forEach(t -> sb.append(System.lineSeparator()).append(String.join("\t\t", String.valueOf(t.getId()), t.getState())));
        return sb.append(System.lineSeparator()).append("===============显示完毕===============").toString();
    }

    public DiningTable getDiningTableById(int id) {
        return tableDao.queryOne("select * from dining_table where id = ?", DiningTable.class, id);
    }

    public boolean reserveDiningTable(int id, String bookedBy, String tel) {
        String sql = "UPDATE dining_table SET bookedby = ?, bookedbytel = ?, state = '预定' WHERE id = ? and state = '空';";
        return tableDao.execute(sql, bookedBy, tel, id) > 0;
    }

    public boolean freeDiningTableById(int id) {
        return tableDao.execute("update dining_table set bookedby = '', bookedbytel = '', state = '空' where id = ?", id) > 0;
    }

    public boolean updateDiningTableState(int id, String state) {
        return tableDao.execute("update dining_table set state = ? where id = ?", state, id) > 0;
    }

}
