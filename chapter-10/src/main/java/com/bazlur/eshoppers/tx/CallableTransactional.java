package com.bazlur.eshoppers.tx;

import java.sql.SQLException;

@FunctionalInterface
public interface CallableTransactional<T> {
	T doInTransaction() throws SQLException;
}
