package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTransaction {

	public static void main(final String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=1234");
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			final String sql = "insert people (id,name)values('03','Jack');";
			stmt.executeUpdate(sql);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final SQLException ex) {
			ex.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
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
