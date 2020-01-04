package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.dto.ProductDTO;
import org.jsoup.Jsoup;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepositoryImpl implements ProductRepository {

	@Override
	public List<ProductDTO> findAllProducts() {

		return List.of(
						new ProductDTO(
										"Apple iPad",
										"Apple iPad 10.2 32GB",
										BigDecimal.valueOf(369.99)
						),
						new ProductDTO(
										"Headphones",
										"Jabra Elite Bluetooth Headphones",
										BigDecimal.valueOf(249.99)
						),
						new ProductDTO(
										"Microsoft Surface Pro",
										"Microsoft Surface Pro 7 12.3\" 128GB Windows 10 Tablet With 10th Gen Intel Core i3/4GB RAM - Platinum",
										BigDecimal.valueOf(799.99)
						),
						new ProductDTO(
										"Amazon Echo Dot",
										"Amazon Echo Dot (3rd Gen) with Alexa - Charcoal",
										BigDecimal.valueOf(34.99)
						)
		);
	}
}
