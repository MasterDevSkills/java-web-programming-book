package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProducts();

	Optional<Product> findById(Long productId);
}
