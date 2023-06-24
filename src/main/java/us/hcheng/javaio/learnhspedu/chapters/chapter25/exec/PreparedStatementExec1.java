package us.hcheng.javaio.learnhspedu.chapters.chapter25.exec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class PreparedStatementExec1 {

	public static void main(String[] args) {
		PreparedStatementExec1 app = new PreparedStatementExec1();
		app.inserts();
		app.query();
		app.update();
		app.query();
		app.delete();
		app.query();
	}

	private void inserts() {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("insert into hsp_user values(?, ?, ?)");
			for (int i = 2; i < 7; i++) {
				statement.setObject(1, i * 100);
				statement.setObject(2, "user-" + i);
				statement.setObject(3, "pwd-" + i);
				System.out.println(statement.executeUpdate() > 0);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void update() {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("update hsp_user set pwd = ? where id = ?");
			statement.setObject(1, "pwd-moka");
			statement.setObject(2, "200");
			System.out.println(statement.executeUpdate() > 0);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void delete() {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("delete from hsp_user where id = ?");
			statement.setObject(1, "300");
			System.out.println(statement.executeUpdate() > 0);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void query() {
		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("select * from hsp_user");
			ResultSet res = statement.executeQuery();
			ResultSetMetaData metaData = res.getMetaData();
			StringBuilder sb = new StringBuilder();

			for (int colCount = metaData.getColumnCount(); res.next(); ) {
				for (int i = 1; i <= colCount; i++)
					sb.append(metaData.getColumnName(i)).append('(').append(res.getObject(i)).append(i == colCount ? ')' : ") ");
				sb.append(System.lineSeparator());
			}

			System.out.println(sb);
			statement.close();
			res.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
