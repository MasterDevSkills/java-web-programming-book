package com.bazlur.jdbc.cp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class HikariCPExample {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(HikariCPExample.class);

	public static final String DB_URL = "jdbc:mysql://localhost:3306/eShoppers";
	public static final String DB_USER = "root";
	public static final String PASSWORD = "std12345#";

	private static HikariDataSource dataSource;

	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(DB_URL);
		config.setUsername(DB_USER);
		config.setPassword(PASSWORD);

		dataSource = new HikariDataSource(config);
		dataSource.setMaximumPoolSize(20);
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void main(String[] args) {
		var executorService = Executors.newFixedThreadPool(25);
		IntStream.range(0, 200)
						.forEach(iteration -> {
							executorService.submit(() -> {
								executeCountQuery(iteration + 1);
							});
						});
	}

	private static void executeCountQuery(int iteration) {
		LOGGER.info("Iteration: {}", iteration);
		var sql = "select count(*) from product";
		try (var connection = getConnection()) {
			var statement = connection.prepareStatement(sql);
			var resultSet = statement.executeQuery();
			if (resultSet.next()) {
				var count = resultSet.getLong(1);
				LOGGER.info("Total row in product table: {}", count);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
