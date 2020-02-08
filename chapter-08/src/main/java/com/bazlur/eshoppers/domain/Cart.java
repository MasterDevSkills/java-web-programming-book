package com.bazlur.eshoppers.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Cart extends Domain {
	private Set<CartItem> cartItems = new HashSet<>();
	private BigDecimal totalPrice = BigDecimal.ZERO;
	private Integer totalItem = 0;
	private User user;

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
