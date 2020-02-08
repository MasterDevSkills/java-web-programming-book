package com.bazlur.eshoppers.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.util.ResourceBundle;

public final class ConnectionPool {

	@Produces
	public DataSource getDataSource() {
		var dbProp = ResourceBundle.getBundle("db");

		var config = new HikariConfig();
		config.setJdbcUrl(dbProp.getString("db.url"));
		config.setUsername(dbProp.getString("db.user"));
		config.setPassword(dbProp.getString("db.password"));
		config.setDriverClassName(dbProp.getString("db.driver"));
		var maxPoolSize
						= dbProp.getString("db.max.connections");
		config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

		// performance sensitive settings
		config.setMinimumIdle(0);
		config.setConnectionTimeout(30000);
		config.setIdleTimeout(35000);
		config.setMaxLifetime(45000);

		return new HikariDataSource(config);
	}
}
