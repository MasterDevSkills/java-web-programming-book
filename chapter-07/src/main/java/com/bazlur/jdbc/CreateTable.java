package com.bazlur.jdbc;

import java.sql.*;

public class CreateTable {

	private static final String URL
					= "jdbc:mysql://localhost:3306/eShoppers";
	private static final String USER = "root";
	private static final String PASSWORD = "std12345#";

	public Connection tryConnection() throws SQLException {

		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public void createTable() {

		String dml
						= "create table product" +
						"(" +
						"    id bigint auto_increment primary key," +
						"    name varchar(100) not null," +
						"    description varchar(500) null," +
						"    price decimal not null," +
						"    version bigint not null," +
						"    date_created timestamp not null," +
						"    date_last_updated timestamp null" +
						");";


		try (Connection connection = tryConnection();
				 Statement statement = connection.createStatement()) {

			statement.execute(dml);

			String sql = "show tables  like 'product'";

			ResultSet resultSet
							= statement.executeQuery(sql);
			if (resultSet.next()) {
				System.out.println("Table successfully created");
			} else {
				System.out.println("Table creation failed");
			}
		} catch (SQLException e) {
			throw new RuntimeException(
							"Unable create table"
			);
		}
	}

	public static void main(String[] args) throws Exception {
		var createTable = new CreateTable();

		createTable.createTable();
	}
}
