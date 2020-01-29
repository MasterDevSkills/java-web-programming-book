package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.User;

import java.util.Optional;

public interface CartRepository {
	Optional<Cart> findByUser(User currentUser);

	Cart save(Cart cart);

	Cart update(Cart cart);
}
