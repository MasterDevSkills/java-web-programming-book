package com.bazlur.jdbc.cp;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

	private static JdbcConnectionPool pool = new JdbcConnectionPool();

	public static Connection getConnection() throws SQLException {

		return pool.getConnectionFromPool();
	}
}
