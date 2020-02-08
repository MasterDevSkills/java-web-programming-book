package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.Product;
import com.bazlur.eshoppers.dto.ProductDTO;
import com.bazlur.eshoppers.repository.ProductRepository;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	@Inject
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductDTO> findAllProductSortedByName() {

		return productRepository.findAllProducts()
						.stream()
						.map(this::convertToDTO)
						.sorted(Comparator.comparing(ProductDTO::getName))
						.collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {

		return new ProductDTO(
						product.getId(),
						product.getName(),
						product.getDescription(),
						product.getPrice()
		);
	}
}
