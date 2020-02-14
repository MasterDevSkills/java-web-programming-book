package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.CartItem;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CartItemRepositoryImpl implements CartItemRepository {
	private static final Set<CartItem> CARTS = new CopyOnWriteArraySet<>();

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
}
