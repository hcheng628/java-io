package us.hcheng.javaio.learnhspedu.chapters.chapter25.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class Statements {

	public static void main(String[] args) {
		String name = "cheng";
		String pwd = "mokamokamoka' or '1' = '1";
		doStatement(name, pwd);
		doPreparedStatement(name, pwd);
	}

	/**     SQL Injection... ;-(        */
	public static void doStatement(String name, String pwd) {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			Statement statement = conn.createStatement();
			processResultSet(statement.executeQuery("select * from hsp_user where name = '" + name + "' and pwd = '" + pwd + "'"));
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**     NO SQL Injection... ;-)     */
	public static void doPreparedStatement(String name, String pwd) {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("select * from hsp_user where name = ? and pwd = ?");
			statement.setObject(1, name);
			statement.setObject(2, pwd);
			processResultSet(statement.executeQuery());
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void processResultSet(ResultSet res) {
		try {
			while (res.next())
				System.out.println(res.getObject(1) + " " + res.getObject(2) + " " + res.getObject(3));
			res.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
