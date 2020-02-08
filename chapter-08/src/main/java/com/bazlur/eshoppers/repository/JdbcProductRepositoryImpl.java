package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Product;
import com.bazlur.eshoppers.jdbc.JDBCTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepositoryImpl implements ProductRepository {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(JdbcProductRepositoryImpl.class);

	private JDBCTemplate jdbcTemplate = new JDBCTemplate();

	private static final String SELECT_ALL_PRODUCTS
					= "select * from product";

	public static final String SELECT_PRODUCT_BY_ID
					= "select * from product where id = ?";

	@Override
	public List<Product> findAllProducts() {

		return jdbcTemplate
						.queryForObject(SELECT_ALL_PRODUCTS, this::extractProduct);
	}

	private Product extractProduct(ResultSet resultSet) throws SQLException {
		var product = new Product();
		product.setId(resultSet.getLong("id"));
		product.setName(resultSet.getString("name"));
		product.setVersion(resultSet.getLong("version"));
		product.setDescription(resultSet.getString("description"));
		product.setPrice(resultSet.getBigDecimal("price"));
		product.setDateCreated(
						resultSet.getTimestamp("date_created")
										.toLocalDateTime());
		product.setDateLastUpdated(
						resultSet.getTimestamp("date_last_updated")
										.toLocalDateTime());
		return product;
	}

	@Override
	public Optional<Product> findById(Long productId) {
		var products = jdbcTemplate
						.queryForObject(SELECT_PRODUCT_BY_ID, productId, this::extractProduct);

		return products.size() > 0
						? Optional.of(products.get(0))
						: Optional.empty();
	}
}
