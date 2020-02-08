package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.CartItem;
import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.exceptions.CartNotFoundException;
import com.bazlur.eshoppers.exceptions.OptimisticLockingFailureException;
import com.bazlur.eshoppers.jdbc.JDBCTemplate;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JdbcCartRepositoryImpl implements CartRepository {

	@Inject
	private JDBCTemplate jdbcTemplate;

	@Inject
	private ProductRepository productRepository;

	private static final String INSERT_CART = "INSERT INTO cart (" +
					" total_price, " +
					" total_item, " +
					" version, " +
					" date_created, " +
					" date_last_updated, " +
					" user_id) VALUES (?,?,?,?,?,?)";

	public static final String FIND_BY_USER = "SELECT c.*" +
					" FROM cart c" +
					"         INNER JOIN user u on (c.user_id = u.id)" +
					" WHERE u.id = ? " +
					" AND (c.id not in (select cart_id from `order`))";

	public static final String FIND_BY_ID = "SELECT id, " +
					" total_price, " +
					" total_item, " +
					" version, " +
					" date_created, " +
					" date_last_updated, " +
					" user_id FROM cart WHERE id = ? ";

	public static final String FIND_ALL_CART_ITEMS = "select *" +
					" from cart_item c" +
					" where c.cart_id = ?";

	public static final String UPDATE_CART = "UPDATE cart " +
					" SET total_price = ? " +
					", total_item = ? " +
					", version = ? " +
					", date_last_updated = ? WHERE id = ?";

	@Override
	public Optional<Cart> findByUser(User currentUser) {
		var carts = jdbcTemplate.queryForObject(FIND_BY_USER, currentUser.getId(),
						resultSet -> {
							var cart = extractCart(resultSet);
							var allCartItems = findAllCartItems(cart.getId());
							cart.getCartItems().addAll(allCartItems);

							return cart;
						}
		);

		return carts.size() > 0
						? Optional.of(carts.get(0))
						: Optional.empty();
	}

	private List<CartItem> findAllCartItems(Long id) {

		return jdbcTemplate.queryForObject(FIND_ALL_CART_ITEMS,
						id, resultSet -> {
							var cartItem = new CartItem();
							cartItem.setId(resultSet.getLong("id"));
							cartItem.setQuantity(resultSet.getInt("quantity"));
							cartItem.setPrice(resultSet.getBigDecimal("price"));
							cartItem.setVersion(resultSet.getLong("version"));
							cartItem.setDateCreated(
											resultSet.getTimestamp("date_created")
															.toLocalDateTime());
							cartItem.setDateLastUpdated(
											resultSet.getTimestamp("date_last_updated")
															.toLocalDateTime());

							var productId = resultSet.getLong("product_id");
							productRepository.findById(productId)
											.ifPresent(cartItem::setProduct);

							return cartItem;
						});
	}

	@Override
	public Cart save(Cart cart) {
		var id = jdbcTemplate.executeInsertQuery(INSERT_CART,
						cart.getTotalPrice(),
						cart.getTotalItem(),
						0L,
						cart.getDateCreated(),
						cart.getDateLastUpdated(),
						cart.getUser().getId());

		cart.setId(id);

		return cart;
	}

	@Override
	public Cart update(Cart cart) {
		cart.setVersion(cart.getVersion() + 1);
		var cartToUpdate = findOne(cart.getId())
						.orElseThrow(() ->
										new CartNotFoundException(
														"Shopping Cart not found by Id "
																		+ cart.getId()));

		if (cart.getVersion() <= (cartToUpdate.getVersion())) {
			throw new OptimisticLockingFailureException(
							"Shopping cart is already updated by another request"
			);
		}

		cartToUpdate.setTotalPrice(cart.getTotalPrice());
		cartToUpdate.setTotalItem(cart.getTotalItem());
		cartToUpdate.setVersion(cart.getVersion());
		cartToUpdate.setDateLastUpdated(LocalDateTime.now());
		cartToUpdate.getCartItems().addAll(cart.getCartItems());

		jdbcTemplate.updateQuery(UPDATE_CART,
						cartToUpdate.getTotalPrice(),
						cartToUpdate.getTotalItem(),
						cartToUpdate.getVersion(),
						cartToUpdate.getDateLastUpdated(),
						cartToUpdate.getId());

		return cartToUpdate;
	}

	@Override
	public Optional<Cart> findOne(long cartId) {

		var carts = jdbcTemplate.queryForObject(FIND_BY_ID, cartId,
						this::extractCart
		);

		return carts.size() > 0
						? Optional.of(carts.get(0))
						: Optional.empty();
	}

	private Cart extractCart(ResultSet resultSet)
					throws SQLException {
		var cart = new Cart();
		cart.setId(resultSet.getLong("id"));
		cart.setTotalPrice(resultSet.getBigDecimal("total_price"));
		cart.setTotalItem(resultSet.getInt("total_item"));
		cart.setVersion(resultSet.getLong("version"));
		cart.setDateCreated(
						resultSet.getTimestamp("date_created")
										.toLocalDateTime());
		cart.setDateLastUpdated(
						resultSet.getTimestamp("date_last_updated")
										.toLocalDateTime());

		return cart;
	}
}
