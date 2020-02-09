package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository {
	private static final Set<User> USERS = new CopyOnWriteArraySet<>();

	@Override
	public void save(User user) {
		USERS.add(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {

		return USERS.stream()
						.filter(user -> Objects.equals(user.getUsername(), username))
						.findFirst();
	}
}
