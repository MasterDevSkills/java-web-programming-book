package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.CartItem;
import com.bazlur.eshoppers.exceptions.CartNotFoundException;
import com.bazlur.eshoppers.exceptions.OptimisticLockingFailureException;
import com.bazlur.eshoppers.jdbc.JDBCTemplate;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

public class JdbcCartItemRepositoryImpl implements CartItemRepository {

	@Inject
	private JDBCTemplate jdbcTemplate;

	@Inject
	private ProductRepository productRepository;

	private static final String INSERT_CART_ITEM = "INSERT INTO cart_item (" +
					" quantity, " +
					" price, " +
					" product_id, " +
					" version, " +
					" date_created, " +
					" date_last_updated, " +
					" cart_id) " +
					"VALUES (?,?,?,?,?,?,?)";

	public static final String UPDATE_CART_ITEM = "UPDATE cart_item " +
					"SET quantity = ?, " +
					"price = ?, " +
					"version = ?, " +
					"date_last_updated = ? WHERE id = ? ";

	public static final String SELECT_CART_ITEM = "SELECT id, " +
					"quantity, " +
					"price, " +
					"product_id, " +
					"version, " +
					"date_created, " +
					"date_last_updated, " +
					"cart_id " +
					"FROM cart_item WHERE id = ? ";

	public static final String DELETE_CART
					= "DELETE FROM cart_item WHERE id = ?";

	@Override
	public CartItem save(CartItem cartItem) {

		var cartItemId = jdbcTemplate.executeInsertQuery(INSERT_CART_ITEM,
						cartItem.getQuantity(),
						cartItem.getPrice(),
						cartItem.getProduct().getId(),
						0L,
						cartItem.getDateCreated(),
						cartItem.getDateLastUpdated(),
						cartItem.getCart().getId());
		cartItem.setId(cartItemId);

		return cartItem;
	}

	@Override
	public CartItem update(CartItem cartItem) {
		cartItem.setVersion(cartItem.getVersion() + 1);

		var cartItemToUpdate = findOne(cartItem.getId())
						.orElseThrow(()
										-> new CartNotFoundException(
										"Cart item not found by Id, + "
														+ cartItem.getId()));

		if (cartItem.getVersion() <= (cartItemToUpdate.getVersion())) {
			throw new OptimisticLockingFailureException(
							"CartItem is already updated by another request");
		}

		cartItemToUpdate.setDateLastUpdated(LocalDateTime.now());
		cartItemToUpdate.setVersion(cartItem.getVersion());
		cartItemToUpdate.setQuantity(cartItem.getQuantity());
		cartItemToUpdate.setPrice(cartItem.getPrice());

		jdbcTemplate.updateQuery(UPDATE_CART_ITEM,
						cartItemToUpdate.getQuantity(),
						cartItemToUpdate.getPrice(),
						cartItemToUpdate.getVersion(),
						cartItemToUpdate.getDateLastUpdated(),
						cartItemToUpdate.getId());

		return cartItemToUpdate;
	}

	private Optional<CartItem> findOne(Long id) {
		var cartItems = jdbcTemplate.queryForObject(SELECT_CART_ITEM,
						id, resultSet -> {
							var cartItem = new CartItem();
							cartItem.setId(resultSet.getLong("id"));
							cartItem.setQuantity(resultSet.getInt("quantity"));
							cartItem.setPrice(resultSet.getBigDecimal("price"));
							cartItem.setVersion(resultSet.getLong("version"));
							cartItem.setDateCreated(resultSet.getTimestamp("date_created")
											.toLocalDateTime());
							cartItem.setDateLastUpdated(resultSet.getTimestamp("date_last_updated")
											.toLocalDateTime());
							var productId = resultSet.getLong("product_id");

							productRepository.findById(productId)
											.ifPresent(cartItem::setProduct);

							return cartItem;
						});

		return cartItems.size() > 0 ? Optional.of(cartItems.get(0)) : Optional.empty();
	}

	@Override
	public void remove(CartItem cartItem) {
		jdbcTemplate.deleteById(DELETE_CART, cartItem.getId());
	}
}
