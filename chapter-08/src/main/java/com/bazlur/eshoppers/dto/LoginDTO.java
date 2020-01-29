package com.bazlur.eshoppers.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {

		return "username=" + username;
	}

}
