package com.bazlur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sha256
public class Sha256PasswordEncryption implements PasswordEncryption {
	private static final Logger LOGGER = LoggerFactory.getLogger(Sha256PasswordEncryption.class);

	@Override
	public String encrypt(String password) {
		LOGGER.info("creating password hash  using sha algorith");

		//implement password encryption logic
		return password;
	}
}
