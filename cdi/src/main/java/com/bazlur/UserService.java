package com.bazlur;

import javax.inject.Inject;

public class UserService {

	@Inject
	@Remote
	private UserRepository userRepository;

	@Inject
	@Sha256
	private PasswordEncryption encryption;

	public User saveUser(User user) {
		user.setPassword(encryption.encrypt(user.getPassword()));

		return userRepository.save(user);
	}
}
