package com.bazlur.jdbc.cp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JdbcConnectionPool {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(JdbcConnectionPool.class);

	public static final String DB_URL = "jdbc:mysql://localhost:3306/eShoppers";
	public static final String DB_USER = "root";
	public static final String PASSWORD = "std12345#";

	private static final int MAX_POOL_SIZE = 20;
	private static final int INITIAL_POOL_SIZE = 10;

	private BlockingQueue<ConnectionProxy> availableConnections
					= new ArrayBlockingQueue<>(MAX_POOL_SIZE);

	public JdbcConnectionPool() {
		initializeConnectionPool();
	}

	private void initializeConnectionPool() {
		while (!(availableConnections.size() >= INITIAL_POOL_SIZE)) {
			try {
				availableConnections.put(createNewConnection());
			} catch (InterruptedException e) {
				throw new RuntimeException("Unable to put connection", e);
			}
		}
	}

	private ConnectionProxy createNewConnection() {
		try {
			var connection = DriverManager.getConnection(DB_URL, DB_USER, PASSWORD);
			return new ConnectionProxy(connection, this);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create a new connection", e);
		}
	}

	public Connection getConnectionFromPool() {
		try {
			LOGGER.info("Total available connection in the pool: {}", availableConnections.size());
			createNewConnectionIfNoneFree();

			return availableConnections.take();
		} catch (InterruptedException e) {
			throw new RuntimeException("Unable to get connection", e);
		}
	}

	private void createNewConnectionIfNoneFree() throws InterruptedException {
		if (availableConnections.size() == 0) {
			LOGGER.info("Creating new Connection");
			ConnectionProxy newConnection = createNewConnection();
			availableConnections.put(newConnection);
		}
	}

	public void returnConnectionToPool(ConnectionProxy connection) {
		try {
			availableConnections.put(connection);
		} catch (InterruptedException e) {
			throw new RuntimeException("Unable to return connection", e);
		}
	}
}
