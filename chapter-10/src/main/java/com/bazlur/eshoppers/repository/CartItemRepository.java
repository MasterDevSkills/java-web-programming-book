package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.CartItem;

public interface CartItemRepository {
	CartItem save(CartItem cartItem);

	CartItem update(CartItem cartItem);

	void remove(CartItem cartItem);
}
