package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import static us.hcheng.javaio.learnhspedu.chapters.projects.mhl.constant.Constants.DRUID_PROP_PATH;

public class DBUtil {

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

	public static void close(AutoCloseable... closeables) {
		if (closeables == null)
			return;

		Arrays.stream(closeables).forEach(closable -> {
			try {
				closable.close();
			} catch (Exception e) {}
		});
	}

}
