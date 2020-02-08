package com.bazlur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Default;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Default
public class MD5PasswordEncryption implements PasswordEncryption {
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5PasswordEncryption.class);

	@Override
	public String encrypt(String password) {
		LOGGER.info("creating password hash  using md5 algorith" );
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return bytesToHex(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
