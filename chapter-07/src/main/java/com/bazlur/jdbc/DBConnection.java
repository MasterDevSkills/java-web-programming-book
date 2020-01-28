package com.bazlur.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/eShoppers";
	private static final String USER = "root";
	private static final String PASSWORD = "std12345#";

	public Connection tryConnection() throws SQLException {

		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void main(String[] args) throws Exception {
		var database = new DBConnection();
		var connection = database.tryConnection();

		if (connection.isValid(2)) {
			System.out.println("The attempt to Connection was a SUCCESS");
		} else {
			System.out.println("The attempt to Connection FAILED");
		}
	}
}
