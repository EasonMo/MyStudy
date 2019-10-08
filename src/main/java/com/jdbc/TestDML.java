package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDML {

	public static void main(final String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=1234");
			stmt = conn.createStatement();
			final String sql = "insert people (id,name)values('03','Jack');";
			stmt.executeUpdate(sql);
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}

		}
	}

}
