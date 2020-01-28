package com.bazlur.jdbc;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
	private DBConnection dbConnection = new DBConnection();

	@Override
	public void save(Product product) {
		var sql = "INSERT INTO product (" +
						" name, " +
						" description, " +
						" price, " +
						" version, " +
						" date_created, " +
						" date_last_updated" +
						") " +
						" VALUES (?,?,?,?,?,?)";

		try (var connection = dbConnection.tryConnection();
				 PreparedStatement pstmnt = connection.prepareStatement(sql)) {

			pstmnt.setString(1, product.getName());
			pstmnt.setString(2, product.getDescription());
			pstmnt.setBigDecimal(3, product.getPrice());
			pstmnt.setLong(4, product.getVersion());
			pstmnt.setTimestamp(5, Timestamp.valueOf(product.getDateCreated()));
			pstmnt.setTimestamp(6, Timestamp.valueOf(product.getDateLastUpdated()));

			pstmnt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(
							"Unable to insert product information into database",
							e);
		}
	}

	@Override
	public List<Product> findAll() {
		var sql = "select * from product";

		List<Product> products = new ArrayList<>();

		try (var connection = dbConnection.tryConnection();
				 PreparedStatement pstmnt = connection.prepareStatement(sql)) {

			ResultSet resultSet = pstmnt.executeQuery();

			while (resultSet.next()) {
				Product product = extractProduct(resultSet);

				products.add(product);
			}

		} catch (SQLException e) {
			throw new RuntimeException(
							"Unable to insert product information into database",
							e);
		}

		return products;
	}

	@Override
	public void update(Product product) {
		var sql = "UPDATE product SET  name = ?, " +
						" description = ?, " +
						" price = ?, " +
						" version = ?, " +
						" date_last_updated = ? " +
						" WHERE id = ? ";

		try (var connection = dbConnection.tryConnection();
				 PreparedStatement pstmnt = connection.prepareStatement(sql)) {
			pstmnt.setString(1, product.getName());
			pstmnt.setString(2, product.getDescription());
			pstmnt.setBigDecimal(3, product.getPrice());
			pstmnt.setLong(4, product.getVersion() + 1);
			pstmnt.setTimestamp(5, Timestamp.valueOf(product.getDateLastUpdated()));
			pstmnt.setLong(6, product.getId());

			pstmnt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(
							"Unable to insert product information into database",
							e);
		}
	}

	@Override
	public void delete(long id) {
		var sql = "DELETE FROM product WHERE id = ? ";

		try (var connection = dbConnection.tryConnection();
				 PreparedStatement pstmnt = connection.prepareStatement(sql)) {
			pstmnt.setLong(1, id);

			pstmnt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(
							"Unable to insert product information into database",
							e);
		}
	}

	private Product extractProduct(ResultSet resultSet) throws SQLException {
		var product = new Product();
		product.setId(resultSet.getLong("id"));
		product.setName(resultSet.getString("name"));
		product.setDescription(resultSet.getString("description"));
		product.setPrice(resultSet.getBigDecimal("price"));
		product.setVersion(resultSet.getLong("version"));
		var dateCreated = resultSet.getTimestamp("date_created");
		var dateLastUpdated = resultSet.getTimestamp("date_last_updated");
		product.setDateCreated(dateCreated.toLocalDateTime());
		product.setDateLastUpdated(dateLastUpdated.toLocalDateTime());

		return product;
	}

	public static void main(String[] args) {
		var product = new Product();
		product.setName("Google Pixel 4 XL");
		product.setDescription(
						"Google Pixel 4 XL - Oh So Orange - 128GB - Unlocked"
		);
		product.setVersion(0L);
		product.setPrice(BigDecimal.valueOf(999.00));
		product.setDateCreated(LocalDateTime.now());
		product.setDateLastUpdated(LocalDateTime.now());

		var productRepository = new ProductRepositoryImpl();
		//save new product
		productRepository.save(product);

		//find all products
		final List<Product> products = productRepository.findAll();
		System.out.println(products);

		final Product product1 = products.get(0);
		product1.setName("Google Pixel");
		product1.setDescription(
						"Google Pixel 4 - Oh So Orange - 128GB - Unlocked"
		);
		product1.setVersion(0L);
		product1.setPrice(BigDecimal.valueOf(699.00));
		product1.setDateLastUpdated(LocalDateTime.now());

		//update  product
		productRepository.update(product1);
	}
}
