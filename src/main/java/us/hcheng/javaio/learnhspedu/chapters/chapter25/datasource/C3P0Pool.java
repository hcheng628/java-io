package us.hcheng.javaio.learnhspedu.chapters.chapter25.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.constant.Constants;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;
import java.beans.PropertyVetoException;

public class C3P0Pool {

    public static void main(String[] args) {
        System.setProperty("mariadb.logging.disable", "true");
        conn1();
        conn2();
    }

    private static void conn1() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setUser(Constants.USER);
        dataSource.setPassword(Constants.PWD);
        dataSource.setJdbcUrl(Constants.MARIADB_CONN_URL);
        try {
            dataSource.setDriverClass(Constants.MARIADB_DRIVER_CP);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            return;
        }
        dataSource.setAcquireIncrement(5);
        dataSource.setInitialPoolSize(5);
        dataSource.setMaxPoolSize(50);
        DBUtil.timeIt(dataSource, 5000);
    }

    private static void conn2() {
        // not very flexible since it has to be in the root
        DBUtil.timeIt(new ComboPooledDataSource("hspedu"), 5000 * 100); // TimeMillis: 1269
    }

}
