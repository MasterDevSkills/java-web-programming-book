package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();
}
