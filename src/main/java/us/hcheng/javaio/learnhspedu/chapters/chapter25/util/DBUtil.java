package us.hcheng.javaio.learnhspedu.chapters.chapter25.util;

import static us.hcheng.javaio.learnhspedu.chapters.chapter25.constant.Constants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(MARIADB_CONN_URL, USER, PWD);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
