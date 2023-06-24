package us.hcheng.javaio.learnhspedu.chapters.chapter25.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class JDBCTransaction {
	private static final String SQL1 = "update acct set money = money - 100 where id = 1";
	private static final String SQL2 = "update acct set money = money + 100 where id = 2";

	public static void main(String[] args) {
		Stream.of(true, false).forEach(each -> {
			try {
				noTransaction(each);
			} catch (Exception ex){}
			try {
				transaction(each);
			} catch (Exception ex){}
		});
	}

	private static void noTransaction(boolean exception) {
		PreparedStatement s1 = null;
		PreparedStatement s2 = null;

		try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
			s1 = conn.prepareStatement(SQL1);
			System.out.println("Transfered " + (s1.executeUpdate() > 0 ? "succeed" : "failed") );

			if (exception)
				throw new RuntimeException("Haha");

			s2 = conn.prepareStatement(SQL2);
			System.out.println("Received " + (s2.executeUpdate() > 0 ? "succeed" : "failed") );
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			Stream.of(s1, s2).forEach(s -> {
				if (s != null)
					try {
						s.close();
					} catch (SQLException e) {}
			});
		}
	}

	private static void transaction(boolean exception) {
		Connection conn = DBUtil.ConnectionUtil.getConnection();
		if (conn == null)
			return;

		PreparedStatement s1 = null;
		PreparedStatement s2 = null;
		try {
			conn.setAutoCommit(false);

			s1 = conn.prepareStatement(SQL1);
			System.out.println("Transfered " + (s1.executeUpdate() > 0 ? "succeed" : "failed") );

			if (exception)
				throw new RuntimeException("Haha");

			s2 = conn.prepareStatement(SQL2);
			System.out.println("Received " + (s2.executeUpdate() > 0 ? "succeed" : "failed") );
			conn.commit();
		} catch (SQLException ex) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e) {}

			ex.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}

			Stream.of(s1, s2).forEach(s -> {
				if (s != null)
					try {
						s.close();
					} catch (SQLException e) {}
			});
		}
	}

}
