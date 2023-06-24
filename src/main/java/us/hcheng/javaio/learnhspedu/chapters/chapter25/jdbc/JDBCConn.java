package us.hcheng.javaio.learnhspedu.chapters.chapter25.jdbc;

import static us.hcheng.javaio.learnhspedu.chapters.chapter25.constant.Constants.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.Test;
import org.mariadb.jdbc.Driver;

public class JDBCConn {

	private Properties basicMariaDBProp() {
		Properties ret = new Properties();
		ret.setProperty(USER_KEY, USER);
		ret.setProperty(PWD_KEY, PWD);
		return ret;
	}

	private java.sql.Driver getDriver() {
		java.sql.Driver ret = null;

		try {
			Class<?> dbDriver = Class.forName(MARIADB_DRIVER_CP);   // from a config file
			ret = (Driver) dbDriver.getConstructor().newInstance();
		} catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
		         IllegalAccessException | NoSuchMethodException ex) {
			ex.printStackTrace();
		}

		return ret;
	}

	@Test
	public void conn1() {
		Driver driver = new Driver();
		try (Connection connection = driver.connect(MARIADB_CONN_URL, basicMariaDBProp())) {
			System.out.println(connection != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void conn2() {
		java.sql.Driver driver = getDriver();
		if (driver == null)
			return;

		try (Connection conn = driver.connect(MARIADB_CONN_URL, basicMariaDBProp())) {
			System.out.println(conn != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void conn3() {
		java.sql.Driver driver = getDriver();
		if (driver == null)
			return;

		try {
			DriverManager.registerDriver(driver);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return;
		}

		try (Connection conn = DriverManager.getConnection(MARIADB_CONN_URL, USER, PWD)) {
			System.out.println(conn != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void conn4() {
		try {
			Class.forName(MARIADB_DRIVER_CP);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return;
		}

		try (Connection conn = DriverManager.getConnection(MARIADB_CONN_URL, USER, PWD)) {
			System.out.println(conn != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void conn5() {
		try (Connection conn = DriverManager.getConnection(MARIADB_CONN_URL, USER, PWD)) {
			System.out.println(conn != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void conn6() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(DB_PROP_PATH));
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

		try {
			Class.forName(prop.getProperty(DRIVER_KEY));
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return;
		}

		try (Connection conn = DriverManager.getConnection(MARIADB_CONN_URL, prop)) {
			System.out.println(conn != null ? "Yes Connected" : "Not Connected");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
