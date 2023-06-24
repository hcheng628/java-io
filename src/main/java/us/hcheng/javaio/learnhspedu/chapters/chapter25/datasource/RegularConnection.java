package us.hcheng.javaio.learnhspedu.chapters.chapter25.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.IntStream;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class RegularConnection {
	private static final int LIMIT = 5000;

	public static void main(String[] args) {
		tooManyConnections();
		timeConsuming();
	}

	/**
	 * Too many connections after 258 connections
	 */
	private static void tooManyConnections() {
		IntStream.rangeClosed(1, LIMIT).forEach(i -> System.out.println("Connection # " + i + " " + DBUtil.ConnectionUtil.getConnection()));
	}

	private static void timeConsuming() {
		long start = System.currentTimeMillis();
		IntStream.rangeClosed(1, LIMIT).forEach(i -> {
			try (Connection conn = DBUtil.ConnectionUtil.getConnection()) {
				if (i % 1000 == 0)
					System.out.println(i);
			} catch (SQLException ex){}
		});
		System.out.println(System.currentTimeMillis() - start);
	}

}
