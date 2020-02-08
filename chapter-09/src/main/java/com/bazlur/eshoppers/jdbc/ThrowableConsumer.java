package com.bazlur.eshoppers.jdbc;

import java.sql.SQLException;

public interface ThrowableConsumer<T> {
	void accept(T t) throws SQLException;
}
