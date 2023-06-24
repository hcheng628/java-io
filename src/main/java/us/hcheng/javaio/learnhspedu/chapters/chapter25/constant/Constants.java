package us.hcheng.javaio.learnhspedu.chapters.chapter25.constant;

import us.hcheng.javaio.learnhspedu.chapters.chapter25.jdbc.JDBCConn;

public class Constants {

	public static final String USER_KEY = "user";
	public static final String USER = "root";
	public static final String PWD_KEY = "password";
	public static final String PWD = "admin";
	public static final String DRIVER_KEY = "driver";
	public static final String MARIADB_DRIVER_CP = "org.mariadb.jdbc.Driver";
	public static final String MARIADB_CONN_URL = "jdbc:mariadb://192.168.31.21:3306/hspedu";
	public static final String DB_PROP_PATH = Constants.class.getResource("/").getPath() + "db/MariaDB.properties";

}
