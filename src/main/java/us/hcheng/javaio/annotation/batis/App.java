package us.hcheng.javaio.annotation.batis;

import us.hcheng.javaio.annotation.batis.dao.EntityDao;
import us.hcheng.javaio.annotation.batis.util.DBUtil;
import us.hcheng.javaio.annotation.generic.entity.Dog;
import us.hcheng.javaio.annotation.generic.entity.Cat;

import java.sql.*;
import java.util.Date;

public class App {

    public static void main(String[] args) throws Exception {
        App app = new App();
        // app.testDB();
        EntityDao dao = new EntityDao();
        app.catTests(dao);
    }

    private void catTests(EntityDao dao) {
        Cat c = new Cat();
        c.setName("T");
        c.setQuiet(true);
        c.setCreatedTime(new Date());
        dao.create(c);

        Cat d = dao.queryById(Cat.class, 2);
//        System.out.println(d);
//        if (d != null)
//            dao.delete(d);

        d.setName("Mokkkkkkkaa");
        dao.update(d);
        System.out.println(dao.update(d));
    }

    private void dogTests(EntityDao dao) {
        Dog d = dao.queryById(Dog.class, 1);
        System.out.println(d);

        d.setName("Think");
        System.out.println(dao.create(d));

        d.setId(2);
        dao.delete(d);
    }

    private void testDB() throws SQLException{
        String SQL_QUERY = "select * from sys_dog";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement( SQL_QUERY );
             ResultSet rs = pst.executeQuery()) {
            int column_count = rs.getMetaData().getColumnCount();
            System.out.println("column_count: " + column_count);

            while ( rs.next() )
                for (int i = 1; i < column_count; i++)
                    System.out.print(rs.getObject(i) + (i != column_count - 1 ? ", " : ""));
        }
    }

}
