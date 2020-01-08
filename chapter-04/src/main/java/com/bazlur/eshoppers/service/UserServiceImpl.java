package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.dto.UserDTO;
import com.bazlur.eshoppers.repository.UserRepository;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void saveUser(UserDTO userDTO) {
		String encrypted = encryptPassword(userDTO.getPassword());

		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(user.getEmail());
		user.setPassword(encrypted);
		user.setUsername(userDTO.getUsername());

		userRepository.save(user);
	}

	private String encryptPassword(String password) {
		// we will implemented password later.

		return password;
	}
}
