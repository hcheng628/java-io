package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import static us.hcheng.javaio.learnhspedu.chapters.projects.mhl.constant.Constants.DRUID_PROP_PATH;

public class DBUtil {
	private static ThreadLocal<Connection> connThreadLocal;

	static {
		connThreadLocal = new ThreadLocal<>();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(DRUID_PROP_PATH));
			DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
			if (dataSource != null)
				connThreadLocal.set(dataSource.getConnection());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		Connection ret = connThreadLocal.get();

		if (ret == null)
			throw new RuntimeException("No available connections");

		return ret;
	}

	public static void close(AutoCloseable... closeables) {
		if (closeables == null)
			return;

		Arrays.stream(closeables).filter(Objects::nonNull).forEach(closable -> {
			try {
				closable.close();
			} catch (Exception e) {}
		});
	}

}
