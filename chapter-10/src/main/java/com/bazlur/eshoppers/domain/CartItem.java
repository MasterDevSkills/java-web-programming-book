package com.bazlur.eshoppers.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem extends Domain {
	private Product product;
	private Integer quantity;
	private BigDecimal price;
	private Cart cart;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CartItem)) return false;
		CartItem cartItem = (CartItem) o;

		return Objects.equals(getId(), cartItem.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
