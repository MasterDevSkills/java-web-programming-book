package com.bazlur.eshoppers.tx;

import java.sql.SQLException;

@FunctionalInterface
public interface Transactional {
	void doInTransaction() throws SQLException;
}
