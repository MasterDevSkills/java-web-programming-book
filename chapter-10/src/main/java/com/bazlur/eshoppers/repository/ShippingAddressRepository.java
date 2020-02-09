package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepository {
	ShippingAddress save(ShippingAddress convertTo);

	Optional<ShippingAddress> findOne(long shippingAddressId);
}
