package com.bazlur.eshoppers.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {
	private final static Logger log
					= LoggerFactory.getLogger(TransactionTemplate.class);

	@Inject
	private ConnectionHolder connectionHolder;

	public void execute(Transactional transactional) {
		var connection = connectionHolder.getConnection();

		try {
			setAutoCommitFalse(connection);
			transactional.doInTransaction();
			connection.commit();
		} catch (Exception e) {
			rollback(connection, e);
		} finally {
			setAutoCommitTrue(connection);
		}
	}

	public <T> T execute(CallableTransactional<T> callableTransactional) {
		log.info("Executing transactions");

		Connection conn = connectionHolder.getConnection();

		try {
			setAutoCommitFalse(conn);
			T result = callableTransactional.doInTransaction();
			conn.commit();

			return result;
		} catch (Exception e) {
			rollback(conn, e);
			throw new RuntimeException(e);
		} finally {
			setAutoCommitTrue(conn);
		}
	}

	private void setAutoCommitFalse(Connection connection)
					throws SQLException {
		connection.setAutoCommit(false);
	}

	private void setAutoCommitTrue(Connection conn) {
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			log.info("Unable to set auto commit");
			throw new RuntimeException(e);
		}
	}

	private void rollback(Connection conn, Throwable throwable) {
		log.error("Rolling back on application " +
						"exception from transaction callback", throwable);
		try {
			conn.rollback();
		} catch (SQLException ex) {
			log.error("Unable to rollback", ex);
		}
	}

}
