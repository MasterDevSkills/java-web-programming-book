package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Product;
import com.bazlur.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepositoryImpl implements ProductRepository {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(JdbcProductRepositoryImpl.class);

	private DataSource dataSource
					= ConnectionPool.getInstance().getDataSource();

	private static final String SELECT_ALL_PRODUCTS
					= "select * from product";

	public static final String SELECT_PRODUCT_BY_ID
					= "select * from product where id = ?";

	@Override
	public List<Product> findAllProducts() {
		try (var connection = dataSource.getConnection();
				 var prepareStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
			var resultSet = prepareStatement.executeQuery();

			return extractProducts(resultSet);
		} catch (SQLException e) {
			LOGGER.info("Unable to fetch products from database", e);
		}
		return Collections.emptyList();
	}

	private List<Product> extractProducts(ResultSet resultSet) throws SQLException {
		List<Product> products = new ArrayList<>();
		while (resultSet.next()) {
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
			products.add(product);
		}
		return products;
	}

	@Override
	public Optional<Product> findById(Long productId) {
		try (var connection = dataSource.getConnection();
				 var prepareStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {

			prepareStatement.setLong(1, productId);

			var products = extractProducts(prepareStatement.executeQuery());

			if (products.size() > 0) {

				return Optional.of(products.get(0));
			}

		} catch (SQLException e) {
			LOGGER.info("Unable to fetch product by id: {}", productId, e);
		}
		return Optional.empty();
	}
}
