package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.CartItem;

import java.util.HashSet;
import java.util.Set;

public class CartItemRepositoryImpl implements CartItemRepository {
	private static final Set<CartItem> CARTS = new HashSet<>();

	@Override
	public CartItem save(CartItem cartItem) {
		CARTS.add(cartItem);
		return cartItem;
	}

	@Override
	public CartItem update(CartItem cartItem) {
		CARTS.add(cartItem);
		return cartItem;
	}

	@Override
	public void remove(CartItem cartItem) {
		CARTS.remove(cartItem);
	}
}
