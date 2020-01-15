package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CartRepositoryImpl implements CartRepository {
	private static final Map<User, Cart> CARTS = new HashMap<>();

	@Override
	public Optional<Cart> findByUser(User currentUser) {
		Cart cart = CARTS.get(currentUser);

		return Optional.ofNullable(cart);
	}

	@Override
	public Cart save(Cart cart) {

		return CARTS.put(cart.getUser(), cart);
	}

	@Override
	public Cart update(Cart cart) {

		return CARTS.put(cart.getUser(), cart);
	}
}
