package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.dto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProducts();
}
