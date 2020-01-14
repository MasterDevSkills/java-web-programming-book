package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.User;

public interface CartService {

	Cart getCartByUser(User currentUser);

	void addProductToCart(String productId, Cart cart);
}
