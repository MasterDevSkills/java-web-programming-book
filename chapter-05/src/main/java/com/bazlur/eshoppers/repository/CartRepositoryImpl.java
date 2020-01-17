package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.*;

public class CartRepositoryImpl implements CartRepository {
	private static final Map<User, LinkedHashSet<Cart>>
					CARTS = new HashMap<>();

	@Override
	public Optional<Cart> findByUser(User currentUser) {

		var carts = CARTS.get(currentUser);
		if (!carts.isEmpty()) {

			Cart cart = (Cart) carts.toArray()[carts.size() - 1];
			return Optional.of(cart);
		}

		return Optional.empty();
	}

	@Override
	public Cart save(Cart cart) {
		CARTS.computeIfPresent(cart.getUser(),
						(user, carts) -> {
							carts.add(cart);
							return carts;
						});

		CARTS.computeIfAbsent(cart.getUser(),
						user -> {
							var carts = new LinkedHashSet<Cart>();
							carts.add(cart);
							return carts;
						});

		return cart;
	}

	@Override
	public Cart update(Cart cart) {
		CARTS.computeIfAbsent(cart.getUser(),
						user -> {
							var carts = new LinkedHashSet<Cart>();
							carts.add(cart);
							return carts;
						});

		return cart;
	}
}
