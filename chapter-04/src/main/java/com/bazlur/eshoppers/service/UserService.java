package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.dto.UserDTO;

public interface UserService {
	void saveUser(UserDTO userDTO);

	boolean isNotUniqueUsername(UserDTO user);
}
