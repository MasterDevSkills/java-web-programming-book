package com.bazlur.eshoppers.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCTemplate {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(JDBCTemplate.class);

	@Inject
	private DataSource dataSource;

	public void updateQuery(String query, Object... parameters) {
		try (var connection = dataSource.getConnection();
				 var statement = connection.prepareStatement(query)) {

			addParameters(statement, parameters);

			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.info("Unable to execute update", e);
			throw new RuntimeException("Unable to execute query for result", e);
		}
	}

	public void query(String query, ThrowableConsumer<ResultSet> consumer) {
		try (var connection = dataSource.getConnection();
				 var statement = connection.prepareStatement(query)) {

			consumer.accept(statement.executeQuery());

		} catch (SQLException e) {
			LOGGER.info("Unable to execute query for result", e);
			throw new RuntimeException("Unable to execute query for result", e);
		}
	}

	public long executeInsertQuery(String query, Object... parameters) {
		try (var connection = dataSource.getConnection();
				 var preparedStatement
								 = connection.prepareStatement(query,
								 Statement.RETURN_GENERATED_KEYS)) {
			addParameters(preparedStatement, parameters);

			final int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {

					return generatedKeys.getLong(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			LOGGER.error("Unable to insert query", e);
			throw new RuntimeException(e);
		}
	}

	public <E> List<E> queryForObject(String query, Object primaryKey,
																		ObjectRowMapper<E> objectRowMapper) {
		try (var connection = dataSource.getConnection();
				 var statement = connection.prepareStatement(query)) {

			addParameters(statement, new Object[]{primaryKey});
			var resultSet = statement.executeQuery();
			List<E> listOfE = new ArrayList<>();

			while (resultSet.next()) {
				listOfE.add(objectRowMapper.mapRowToObject(resultSet));
			}

			return listOfE;
		} catch (SQLException e) {
			LOGGER.info("Unable to execute query for result", e);
			throw new RuntimeException("Unable to execute query for result", e);
		}
	}

	public <E> List<E> queryForObject(String query,
																		ObjectRowMapper<E> objectRowMapper) {

		try (var connection = dataSource.getConnection();
				 var statement = connection.prepareStatement(query)) {

			var resultSet = statement.executeQuery(query);
			List<E> listOfE = new ArrayList<>();

			while (resultSet.next()) {
				listOfE.add(objectRowMapper.mapRowToObject(resultSet));
			}

			return listOfE;
		} catch (SQLException e) {
			LOGGER.info("Unable to execute query for result", e);
			throw new RuntimeException("Unable to execute query for result", e);
		}
	}

	private void addParameters(PreparedStatement preparedStatement,
														 Object[] parameters) throws SQLException {
		int idx = 1;

		for (Object parameter : parameters) {
			if (parameter instanceof String) {
				preparedStatement.setString(idx, (String) parameter);
			} else if (parameter instanceof Integer) {
				preparedStatement.setInt(idx, (Integer) parameter);
			} else if (parameter instanceof Long) {
				preparedStatement.setLong(idx, (Long) parameter);
			} else if (parameter instanceof Float) {
				preparedStatement.setFloat(idx, (Float) parameter);
			} else if (parameter instanceof Double) {
				preparedStatement.setDouble(idx, (Double) parameter);
			} else if (parameter instanceof LocalDateTime) {
				preparedStatement.setTimestamp(idx, Timestamp.valueOf(((LocalDateTime) parameter)));
			} else if (parameter instanceof Blob) {
				preparedStatement.setBlob(idx, (Blob) parameter);
			} else if (parameter instanceof BigDecimal) {
				preparedStatement.setBigDecimal(idx, (BigDecimal) parameter);
			} else if (parameter instanceof Boolean) {
				preparedStatement.setBoolean(idx, (Boolean) parameter);
			}
			idx++;
		}
	}

	public void deleteById(String query, Long id) {
		try (var connection = dataSource.getConnection();
				 var statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);

			statement.execute();

		} catch (SQLException e) {
			LOGGER.error("Unable to execute delete by id: {}", id, e);
			throw new RuntimeException("Unable to execute delete", e);
		}
	}
}
