package com.bazlur;


@Remote
public class RemoteUserRepository implements UserRepository {
	@Override
	public User save(User user) {
		//remote invocation
		return user;
	}
}
