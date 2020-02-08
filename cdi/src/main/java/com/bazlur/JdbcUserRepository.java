package com.bazlur;


import javax.enterprise.inject.Default;

@Default
public class JdbcUserRepository implements UserRepository {

	@Override
	public User save(User user) {
		//store in db
		return user;
	}
}
