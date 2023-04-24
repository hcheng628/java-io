package us.hcheng.javaio.annotation.batis.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mariadb://localhost:3306/global" );
        config.setUsername( "root" );
        config.setPassword( "admin" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeRS(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex){
                System.out.println(ex.getCause());
            }
        }
    }

}
