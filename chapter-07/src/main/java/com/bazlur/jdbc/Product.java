package com.bazlur.jdbc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class Product {
	private Long id;
	private Long version;
	private String name;
	private String description;
	private BigDecimal price;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
						.add("id=" + id)
						.add("version=" + version)
						.add("name='" + name + "'")
						.add("description='" + description + "'")
						.add("price=" + price)
						.add("dateCreated=" + dateCreated)
						.add("dateLastUpdated=" + dateLastUpdated)
						.toString();
	}
}
