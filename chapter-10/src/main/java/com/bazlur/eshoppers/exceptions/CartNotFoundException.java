package com.bazlur.eshoppers.exceptions;

public class CartNotFoundException extends RuntimeException {
	public CartNotFoundException(String msg) {
		super(msg);
	}
}
