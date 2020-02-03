package com.bazlur.eshoppers.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product extends Domain {
	private String name;
	private String description;
	private BigDecimal price;

	public Product() {
	}

	public Product(Long id, String name,
								 String description,
								 BigDecimal price) {
		setId(id);
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(o instanceof Product)) return false;
		Product product = (Product) o;
		return Objects.equals(getId(), product.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
