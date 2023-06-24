package us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.IntStream;

import static us.hcheng.javaio.learnhspedu.chapters.chapter25.constant.Constants.*;

public class DBUtil {

	public static class ConnectionUtil {
		public static Connection getConnection() {
			try {
				return DriverManager.getConnection(MARIADB_CONN_URL, USER, PWD);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public static class DSUtil {
		private static DataSource ds;

		static {
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(DRUID_PROP_PATH));
				ds = DruidDataSourceFactory.createDataSource(prop);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public static Connection getConnection() {
			try {
				return ds.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		public static DataSource getDataSource() {
			return ds;
		}

	}

	public static void close(AutoCloseable... closeables) {
		if (closeables == null)
			return;
		Arrays.stream(closeables).forEach(closable -> {
			try {
				closable.close();
			} catch (Exception e) {}
		});
	}

	public static void timeIt(DataSource ds, int limit) {
		long start = System.currentTimeMillis();
		IntStream.rangeClosed(1, limit).forEach(i -> {
			try (Connection conn = ds.getConnection()) {
				if (i % 1000 == 0)
					System.out.println(i);
			} catch (SQLException ex){}
		});
		System.out.println("TimeMillis: " + (System.currentTimeMillis() - start));
	}

}
