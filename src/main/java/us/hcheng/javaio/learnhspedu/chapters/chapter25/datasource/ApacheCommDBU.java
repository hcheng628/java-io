package us.hcheng.javaio.learnhspedu.chapters.chapter25.datasource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.entity.HspUser;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ApacheCommDBU {
    private static final String SELECT_SQL = "select * from hsp_user where id > ?";
    private static final String UPDATE_SQL = "update hsp_user set pwd = ? where id = ?";
    private static final int ID = 100;

    static {
        System.setProperty("mariadb.logging.disable", "true");
    }

    public static void main(String[] args) {
        queryMany();
        System.err.println("*************");
        querySingle();
        System.err.println("*************");
        doScalar();
        System.err.println("*************");
        doDML();
    }


    public static void queryMany() {
        Connection conn = DBUtil.DSUtil.getConnection();
        QueryRunner query = new QueryRunner();

        try {
            List<HspUser> users = query.query(conn, SELECT_SQL, new BeanListHandler<>(HspUser.class), ID);
            users.forEach(System.err::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtil.close(conn); // pass DataSource to QueryRunner -> dont have to close here
    }

    public static void querySingle() {
        Connection conn = DBUtil.DSUtil.getConnection();

        try {
            System.err.println(new QueryRunner().query(conn, SELECT_SQL, new BeanHandler<>(HspUser.class), ID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtil.close(new AutoCloseable[]{conn, null}); // pass DataSource to QueryRunner -> dont have to close here
    }

    public static void doScalar() {
        try {
            Object res = new QueryRunner(DBUtil.DSUtil.getDataSource()).query(SELECT_SQL, new ScalarHandler<>(), ID);
            System.err.println(res);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDML() {
        String SQL = UPDATE_SQL;
        String newPwd = "mmmmooookkkkaa";
        int id = 200;

        try {
            System.err.println("DB count: " + new QueryRunner(DBUtil.DSUtil.getDataSource()).update(SQL, newPwd, id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
