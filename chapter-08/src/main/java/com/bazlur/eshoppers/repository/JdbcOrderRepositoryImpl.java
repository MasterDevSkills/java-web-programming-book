package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Order;
import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.jdbc.JDBCTemplate;

import java.util.HashSet;
import java.util.Set;

public class JdbcOrderRepositoryImpl implements OrderRepository {
	private JDBCTemplate jdbcTemplate = new JDBCTemplate();
	private CartRepository cartRepository
					= new JdbcCartRepositoryImpl();
	private ShippingAddressRepository shippingAddressRepository
					= new JdbcShippingAddressRepositoryImpl();

	public static final String FIND_ORDER_BY_USER = "SELECT id" +
					", shipping_address_id" +
					", cart_id" +
					", version" +
					", date_created" +
					", date_last_updated" +
					", shipping_date" +
					", shipped" +
					", user_id FROM `order` WHERE user_id = ?";

	@Override
	public Order save(Order order) {
		//order is a reserved word on mysql,
		// so make sure you quote/back-tic  before using it
		var query = "INSERT INTO `order` (" +
						" shipping_address_id, " +
						" cart_id, " +
						" version," +
						" shipped," +
						" user_id," +
						" date_created," +
						" date_last_updated) VALUES (?,?,?,?,?,?,?)";

		var id = jdbcTemplate.executeInsertQuery(query,
						order.getShippingAddress().getId(),
						order.getCart().getId(),
						0L,
						order.isShipped(),
						order.getUser().getId(),
						order.getDateCreated(),
						order.getDateLastUpdated()
		);
		order.setId(id);

		return order;
	}

	@Override
	public Set<Order> findOrderByUser(User user) {

		var orders = jdbcTemplate.queryForObject(FIND_ORDER_BY_USER,
						user.getId(), resultSet -> {
							var order = new Order();
							order.setId(
											resultSet.getLong("id"));
							order.setVersion(
											resultSet.getLong("version"));
							order.setDateCreated(
											resultSet.getTimestamp("date_created")
															.toLocalDateTime());
							order.setDateLastUpdated(
											resultSet.getTimestamp("date_last_updated")
															.toLocalDateTime());
							order.setShipped(
											resultSet.getBoolean("shipped"));
							order.setShippingDate(
											resultSet.getTimestamp("shipping_date") != null
															? resultSet.getTimestamp("shipping_date")
															.toLocalDateTime()
															: null);

							cartRepository.findOne(resultSet.getLong("cart_id"))
											.ifPresent(order::setCart);

							shippingAddressRepository.findOne(
											resultSet.getLong("shipping_address_id"))
											.ifPresent(order::setShippingAddress);

							order.setUser(user);

							return order;
						});

		return new HashSet<>(orders);
	}
}
