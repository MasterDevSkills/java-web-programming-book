package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.User;

import java.util.Optional;


public interface UserRepository {
	void save(User user);

	Optional<User> findByUsername(String username);
}
