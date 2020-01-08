package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.User;

import java.util.HashSet;
import java.util.Set;

public class UserRepositoryImpl implements UserRepository {
	private static final Set<User> USERS = new HashSet<>();

	@Override
	public void save(User user) {
		USERS.add(user);
	}
}
