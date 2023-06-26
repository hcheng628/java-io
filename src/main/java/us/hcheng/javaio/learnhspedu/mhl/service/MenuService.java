package us.hcheng.javaio.learnhspedu.mhl.service;

import us.hcheng.javaio.learnhspedu.mhl.dao.MenuDao;
import us.hcheng.javaio.learnhspedu.mhl.entity.Menu;
import java.util.List;

public class MenuService {

    private MenuDao menuDao;

    public MenuService() {
        menuDao = new MenuDao();
    }

    public String getAllMenus() {
        List<Menu> tables = menuDao.query("select * from menu;", Menu.class);
        if (tables == null || tables.isEmpty())
            return "===============没有餐桌===============";

        StringBuilder sb = new StringBuilder("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        tables.forEach(m -> sb.append(System.lineSeparator()).append(m.menuText()));
        return sb.append(System.lineSeparator()).append("===============显示完毕===============").toString();
    }

    public Menu getMenuById(int id) {
        return menuDao.queryOne("select * from menu where id = ?", Menu.class, id);
    }

}
