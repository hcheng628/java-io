package us.hcheng.javaio.learnhspedu.chapters.chapter25.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class JDBCBatch {

	private static final int LIMIT = 5000;
	private static final String SQL = "insert into acct values(?, ?, ?)";

	public static void main(String[] args) {
		System.setProperty("mariadb.logging.disable", "true");
		//time(v -> JDBCBatch.noBatch()); // 26957 Millis
		time(v -> JDBCBatch.batch());   // 28 Millis
	}

	private static void time(Consumer<Void> consumer) {
		long start = System.currentTimeMillis();
		consumer.accept(null);
		System.out.println(System.currentTimeMillis() - start);
	}


	public static void batch() {
		try (Connection conn = DBUtil.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(SQL);
			for (int i = 1; i <= LIMIT; i++) {
				statement.setObject(1, i);
				statement.setObject(2, "name-" + i);
				statement.setObject(3, i * 100);

				if (i % 1000 == 0) {
					statement.executeBatch();
					statement.clearBatch();
				}
			}

			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void noBatch() {
		try (Connection conn = DBUtil.getConnection()) {
			for (int i = 1; i <= LIMIT; i++) {
				PreparedStatement statement = conn.prepareStatement(SQL);
				statement.setObject(1, i);
				statement.setObject(2, "name-" + i);
				statement.setObject(3, i * 100);
				statement.executeUpdate();
				statement.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
