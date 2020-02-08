package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepositoryImpl implements UserRepository {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(JdbcUserRepositoryImpl.class);

	@Inject
	private DataSource dataSource;

	private final static String SAVE_USER = "INSERT INTO user " +
					"(username, " +
					" password, " +
					" version, " +
					" date_created, " +
					" date_last_updated, " +
					" email, " +
					" first_name, " +
					" last_name) " +
					" VALUES (?,?,?,?,?,?,?,?)";

	private final static String SELECT_BY_USERNAME = "SELECT " +
					" id, " +
					" username, " +
					" password, " +
					" version, " +
					" date_created, " +
					" date_last_updated, " +
					" email, " +
					" first_name, " +
					" last_name FROM user WHERE username = ?";

	@Override
	public void save(User user) {
		try (var connection = dataSource.getConnection();
				 var preparedStatement = connection.prepareStatement(SAVE_USER)) {

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setLong(3, 0L); //set version
			preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getDateCreated()));
			preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getDateLastUpdated()));
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.setString(7, user.getFirstName());
			preparedStatement.setString(8, user.getLastName());

			preparedStatement.execute();

		} catch (SQLException e) {
			LOGGER.info("Unable to save user", e);
			throw new RuntimeException("Unable to save user", e);
		}
	}

	@Override
	public Optional<User> findByUsername(String username) {
		try (var connection = dataSource.getConnection();
				 var preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME)) {
			preparedStatement.setString(1, username);

			var resultSet = preparedStatement.executeQuery();

			List<User> users = extractUser(resultSet);

			if (users.size() > 0) {
				return Optional.of(users.get(0));
			}

		} catch (SQLException e) {
			LOGGER.info("Unable to save user", e);
			throw new RuntimeException("Unable to save user", e);
		}

		return Optional.empty();
	}

	private List<User> extractUser(ResultSet resultSet) throws SQLException {
		List<User> users = new ArrayList<>();

		while (resultSet.next()) {
			var user = new User();
			user.setId(resultSet.getLong("id"));
			user.setVersion(resultSet.getLong("version"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setDateCreated(resultSet.getTimestamp("date_created")
							.toLocalDateTime());
			user.setDateLastUpdated(resultSet.getTimestamp("date_last_updated")
							.toLocalDateTime());
			user.setFirstName(resultSet.getString("first_name"));
			user.setLastName(resultSet.getString("last_name"));
			user.setEmail(resultSet.getString("email"));

			users.add(user);
		}

		return users;
	}
}
